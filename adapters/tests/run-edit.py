"""Build and run an isolated FastNBT item-editor probe on Paper 26.3 or 1.20.4."""
import argparse
import hashlib
import socket
import json
import os
import shutil
import signal
import subprocess
import time
import zipfile
from pathlib import Path

REPO = Path(__file__).resolve().parents[2]
PROBE = Path(__file__).with_name('EditProbe.java')
ROOT = None
LIB_CACHE = Path('/mnt/scratch/cache/codex/itemsadder/runtime/paper-libraries/equipment-paper-26.3-40')
VERSION_CACHE = Path('/mnt/scratch/cache/codex/itemsadder/runtime/paper-versions/equipment-paper-26.3-40')
PAPER = Path('/mnt/scratch/cache/codex/itemsadder-paper/paper-26.3-40.jar')
BOOTSTRAP = Path('/mnt/scratch/cache/codex/itemsadder/runtime/paper-mojang/equipment-paper-26.3-40')
JAVA = Path('/usr/lib/jvm/java-25-openjdk/bin/java')
JAVAC = Path('/usr/lib/jvm/java-25-openjdk/bin/javac')

def sha256(path):
    digest = hashlib.sha256()
    with path.open('rb') as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b''):
            digest.update(block)
    return digest.hexdigest()

def legacy_config():
    base = Path('/mnt/scratch/cache/codex/nativepackets/np010-registry-ops/paper-association-only')
    return dict(paper=Path('/mnt/scratch/cache/codex/proxypack-paper/paper-1.20.4-499.jar'),
                libraries=base/'libraries', versions=base/'versions',
                bootstrap=Path('/mnt/scratch/cache/codex/fastnbt-bootstrap/mojang_1.20.4.jar'),
                java=Path('/usr/lib/jvm/java-21-temurin-jdk/bin/java'), api='1.20')

def config(version):
    if version == '1.20.4':
        return legacy_config()
    return dict(paper=PAPER, libraries=LIB_CACHE, versions=VERSION_CACHE,
                bootstrap=BOOTSTRAP, java=JAVA, api='26.3')

def verify_and_link_manifest(archive, manifest_name, cache, destination):
    with zipfile.ZipFile(archive) as paper:
        lines = paper.read(manifest_name).decode().splitlines()
    for line in lines:
        digest, _, relative = line.split('\t')
        source = cache/relative
        if not source.is_file() or sha256(source) != digest:
            raise RuntimeError(f'Cached Paper file missing or hash mismatch: {source}')
        target = destination/relative
        target.parent.mkdir(parents=True, exist_ok=True)
        target.symlink_to(source.resolve())

def compile_probe(version):
    selected = config(version)
    paper_api = list((selected['libraries']).rglob('paper-api-*.jar'))
    paper_api = [p for p in paper_api if ('26.3.build.40' in p.name if version == '26.3' else '1.20.4-R0.1-SNAPSHOT' in p.name)]
    if len(paper_api) != 1:
        raise RuntimeError(f'Expected one Paper API jar for {version}, found {paper_api}')
    fastnbt = REPO/'output/FastNbt.jar'
    if not fastnbt.is_file():
        raise RuntimeError(f'Build FastNBT first: {fastnbt}')
    classes = ROOT/'classes'
    classes.mkdir(exist_ok=True)
    dependencies = sorted(selected['libraries'].rglob('*.jar'))
    classpath = os.pathsep.join(map(str, [fastnbt, *paper_api, *dependencies]))
    subprocess.run(['rtk', str(JAVAC), '--release', '21', '-proc:none', '-cp', classpath,
                    '-d', str(classes), str(PROBE)], check=True, timeout=90)
    jar = ROOT/'EditProbe.jar'
    with zipfile.ZipFile(jar, 'w', zipfile.ZIP_DEFLATED) as output:
        with zipfile.ZipFile(fastnbt) as library:
            for info in library.infolist():
                if info.filename == 'META-INF/MANIFEST.MF' or info.filename == 'plugin.yml' or info.filename.endswith(('.SF', '.RSA', '.DSA')):
                    continue
                output.writestr(info, library.read(info.filename))
        output.writestr('plugin.yml', f"name: EditProbe\nversion: 1.0\nmain: EditProbe\napi-version: '{selected['api']}'\n")
        for path in classes.rglob('*.class'):
            output.write(path, path.relative_to(classes))
    return jar

def stop(process):
    if process.poll() is None:
        os.killpg(process.pid, signal.SIGTERM)
        try:
            process.wait(timeout=20)
        except subprocess.TimeoutExpired:
            os.killpg(process.pid, signal.SIGKILL)
            process.wait(timeout=10)
    try:
        os.killpg(process.pid, 0)
    except ProcessLookupError:
        return
    os.killpg(process.pid, signal.SIGKILL)
    for _ in range(50):
        try: os.killpg(process.pid, 0)
        except ProcessLookupError: return
        time.sleep(.1)
    raise RuntimeError(f'Process group {process.pid} survived shutdown')


def run(version, fork, output, checks_only=False):
    selected = config(version)
    evidence = output/f'{version}-fork-{fork}'
    evidence.mkdir()
    server = evidence/'server'
    plugins = server/'plugins'
    plugins.mkdir(parents=True)
    shutil.copyfile(ROOT/'EditProbe.jar', plugins/'EditProbe.jar')
    with zipfile.ZipFile(selected['paper']) as paper:
        for manifest, cache, destination in [('META-INF/libraries.list', selected['libraries'], server/'libraries'),
                                             ('META-INF/versions.list', selected['versions'], server/'versions')]:
            verify_and_link_manifest(selected['paper'], manifest, cache, destination)
        expected, _, filename = paper.read('META-INF/download-context').decode().strip().split('\t')
    bootstrap = selected['bootstrap']/filename if selected['bootstrap'].is_dir() else selected['bootstrap']
    if sha256(bootstrap) != expected:
        raise RuntimeError(f'Paper bootstrap checksum mismatch: {selected["bootstrap"]}')
    (server/'cache').mkdir()
    (server/'cache'/filename).symlink_to(bootstrap.resolve())
    for name in ('bStats', 'pStats'):
        (plugins/name).mkdir()
        (plugins/name/'config.yml').write_text('enabled: false\n')
    (server/'eula.txt').write_text('eula=true\n')
    with socket.socket() as sock:
        sock.bind(('127.0.0.1', 0))
        port = sock.getsockname()[1]
    (server/'server.properties').write_text(f'server-ip=127.0.0.1\nserver-port={port}\nonline-mode=false\nmax-tick-time=-1\nview-distance=2\nsimulation-distance=2\nmax-players=2\npause-when-empty-seconds=0\nlevel-type=minecraft:flat\ngenerate-structures=false\n')
    (server/'bukkit.yml').write_text('settings:\n  allow-end: false\nspawn-limits:\n  monsters: 0\n  animals: 0\n')
    (server/'spigot.yml').write_text('settings:\n  timeout-time: 360\n')
    (server/'config').mkdir()
    (server/'config/paper-global.yml').write_text('spark:\n  enabled: false\nupdate-checker:\n  enabled: false\nwatchdog:\n  early-warning-delay: 360000\n  early-warning-every: 360000\n')
    java = selected['java']
    command = [str(java), '-Dterminal.jline=false', '-Dterminal.ansi=false', '-Xms1G', '-Xmx1G',
               '-XX:+UseG1GC', '-XX:ActiveProcessorCount=2', f'-Dbenchmark.seed={9262026+fork}',
               f'-Djava.io.tmpdir={server}', f'-Dprobe.checksOnly={str(checks_only).lower()}', '-DIReallyKnowWhatIAmDoingISwear=true', '-jar',
               str(selected['paper']), '--nogui']
    process = None
    with (evidence/'stdout.log').open('w') as log:
        try:
            process = subprocess.Popen(command, cwd=server, stdin=subprocess.DEVNULL,
                                       stdout=log, stderr=subprocess.STDOUT, start_new_session=True)
            (evidence/'process.json').write_text(json.dumps(dict(pid=process.pid, pgid=process.pid, command=command)))
            process.wait(timeout=420)
        except BaseException:
            if process:
                stop(process)
            raise
        finally:
            if process:
                stop(process)
                (evidence/'cleanup.json').write_text(json.dumps(dict(pid=process.pid, exited=True, returncode=process.returncode)))
    text = (evidence/'stdout.log').read_text(errors='replace')
    passed = text.count('EDIT_PROBE CHECK PASS ')
    if process.returncode != 0 or 'EDIT_PROBE COMPLETE' not in text or 'EDIT_PROBE FAILED' in text or passed < 8:
        raise RuntimeError(f'Probe failed: version={version}, fork={fork}, checks={passed}, log={evidence/"stdout.log"}')
    data = plugins/'EditProbe'
    if not checks_only:
        shutil.copyfile(data/'edit-benchmark.csv', evidence/'edit-benchmark.csv')
    (evidence/'verdict.json').write_text(json.dumps(dict(success=True, checks=passed, version=version, fork=fork)))
    shutil.rmtree(server)
    return evidence

def main():
    global ROOT
    parser = argparse.ArgumentParser()
    parser.add_argument('--version', choices=('26.3', '1.20.4'), default='26.3')
    parser.add_argument('--forks', type=int, default=5)
    parser.add_argument('--output', type=Path)
    parser.add_argument('--work-dir', required=True, type=Path, help='Private scratch directory for classes and probe JAR')
    parser.add_argument('--compile-only', action='store_true')
    parser.add_argument('--checks-only', action='store_true')
    args = parser.parse_args()
    ROOT = args.work_dir.resolve()
    ROOT.mkdir(parents=True, exist_ok=True)
    if args.output is None: args.output = ROOT/'runs'
    compile_probe(args.version)
    if args.compile_only:
        return
    args.output.mkdir(parents=True, exist_ok=False)
    run_hash = sha256(config(args.version)['paper'])
    (args.output/'run.json').write_text(json.dumps(dict(version=args.version, forks=args.forks, paper_sha256=run_hash, fastnbt_sha256=sha256(REPO/"output/FastNbt.jar"), probe_sha256=sha256(ROOT/"EditProbe.jar"), source_sha256=sha256(PROBE), runner_sha256=sha256(Path(__file__))), indent=2))
    shutil.copyfile(PROBE, args.output/"EditProbe.java")
    shutil.copyfile(Path(__file__), args.output/"run.py")
    shutil.copyfile(ROOT/"EditProbe.jar", args.output/"EditProbe.jar")
    for fork in range(args.forks):
        print(f"Starting {args.version} fork {fork}", flush=True)
        run(args.version, fork, args.output, args.checks_only)

if __name__ == '__main__':
    try:
        main()
    except subprocess.CalledProcessError as error:
        raise SystemExit(f'Child command failed with exit {error.returncode}; see compiler/server output above') from None
