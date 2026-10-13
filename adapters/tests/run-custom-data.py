#!/usr/bin/env python3
"""Run an isolated, one-shot semantic probe against an exact local server bundle."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import shutil
import signal
import subprocess
import tempfile
import time
import zipfile


ROOT = Path(__file__).resolve().parents[2]


def sha256(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def stop(process):
    if process.poll() is None:
        try:
            process.stdin.write('stop\n')
            process.stdin.flush()
            process.wait(timeout=30)
        except (BrokenPipeError, subprocess.TimeoutExpired):
            os.killpg(process.pid, signal.SIGTERM)
            try:
                process.wait(timeout=5)
            except subprocess.TimeoutExpired:
                os.killpg(process.pid, signal.SIGKILL)
                process.wait()


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('version')
    parser.add_argument('--server', required=True, type=Path)
    parser.add_argument('--java', type=Path, default=Path('/usr/lib/jvm/java-25-openjdk/bin/java'))
    parser.add_argument('--itemsadder', type=Path)
    parser.add_argument('--itemsadder-jar', type=Path, help='Existing consumer artifact; defaults to its normal build output')
    parser.add_argument('--artifact', type=Path, default=ROOT / 'output/FastNbt.jar')
    parser.add_argument('--port', type=int, default=25659)
    parser.add_argument('--platform', choices=['spigot', 'paper'], default='spigot')
    parser.add_argument('--bootstrap-cache', type=Path)
    parser.add_argument('--evidence-root', type=Path, default=ROOT / 'build/legacy-custom-data')
    args = parser.parse_args()
    mode = 'itemsadder' if args.itemsadder else 'standalone'
    evidence = args.evidence_root / (mode + '-' + args.version
                                                  + ('-paper' if args.platform == 'paper' else ''))
    evidence.mkdir(parents=True, exist_ok=True)
    result = {'version': args.version, 'mode': mode, 'platform': args.platform, 'server': str(args.server),
              'server_sha256': sha256(args.server), 'fastnbt_sha256': sha256(args.artifact)}
    process = None
    with tempfile.TemporaryDirectory(prefix='server-', dir=evidence) as temporary:
        server = Path(temporary)
        plugins = server / 'plugins'
        plugins.mkdir()
        for folder in ['pStats', 'bStats']:
            (plugins / folder).mkdir()
            (plugins / folder / 'config.yml').write_text('enabled: false\n')
        if args.bootstrap_cache:
            with zipfile.ZipFile(args.server) as bundle:
                expected, _, filename = bundle.read('META-INF/download-context').decode().strip().split('\t')
            if sha256(args.bootstrap_cache) != expected:
                raise ValueError('Paper bootstrap cache hash mismatch')
            (server / 'cache').mkdir()
            shutil.copyfile(args.bootstrap_cache, server / 'cache' / filename)
        classes = server / 'probe-classes'
        classes.mkdir()
        api = next((Path.home() / '.gradle/caches/modules-2/files-2.1/org.spigotmc/spigot-api/1.17.1-R0.1-SNAPSHOT').glob('*/*.jar'))
        subprocess.run([str(args.java.with_name('javac')), '--release', '17', '-classpath', str(api),
                        '-d', str(classes), str(ROOT / 'adapters/tests/CustomDataProbe.java')], check=True, timeout=30)
        with zipfile.ZipFile(plugins / 'CustomDataProbe.jar', 'w') as jar:
            if not args.itemsadder:
                with zipfile.ZipFile(args.artifact) as library:
                    for entry in library.infolist():
                        if entry.filename != 'META-INF/MANIFEST.MF':
                            jar.writestr(entry, library.read(entry))
            for path in classes.glob('*.class'):
                jar.write(path, path.name)
            jar.writestr('plugin.yml', 'name: CustomDataProbe\nversion: 1\nmain: CustomDataProbe\n'
                         'api-version: "1.17"\n' + ('depend: [ItemsAdder]\n' if args.itemsadder else ''))
            jar.writestr('META-INF/MANIFEST.MF', 'Manifest-Version: 1.0\npaperweight-mappings-namespace: spigot\n\n')

        if args.itemsadder:
            # Use the consumer's normal runtime dependency cache; never modify its repository.
            import yaml
            repo = args.itemsadder
            source = repo / 'run/server/plugins/ItemsAdder'
            target = plugins / 'ItemsAdder'
            target.mkdir()
            for name in ['lib', 'lang']:
                shutil.copytree(source / name, target / name, ignore=shutil.ignore_patterns('*-modified.jar'))
            shutil.copyfile(source / 'ia_gui.yml', target / 'ia_gui.yml')
            config = yaml.safe_load((source / 'config.yml').read_text())
            config.update(metrics=False, **{'update-checker': False,
                          'server': {'address': '127.0.0.1', 'port': args.port}})
            for hosting in config['resource-pack']['hosting'].values():
                if isinstance(hosting, dict):
                    hosting['enabled'] = False
            (target / 'config.yml').write_text(yaml.safe_dump(config, sort_keys=False))
            fixture = repo / '.tasks/nativepackets-migration/live/render-fixture/itemsadder-content/t9'
            shutil.copytree(fixture, target / 'contents/t9')
            models = target / 'contents/t9/models/item'
            models.mkdir(parents=True, exist_ok=True)
            (models / 'test_trident.json').write_text('{"parent":"minecraft:item/generated","textures":{"layer0":"minecraft:item/paper"}}')
            cache = target / 'lib/beer/devs/FastNbt-jar/1.4.22/FastNbt-jar-1.4.22.jar'
            cache.parent.mkdir(parents=True, exist_ok=True)
            shutil.copyfile(args.artifact, cache)
            for original, name in [(args.itemsadder_jar or repo / 'ItemsAdder-jar/build/libs/ItemsAdder.jar', 'ItemsAdder.jar'),
                                   (repo.parent / 'NativePackets/dist/build/libs/NativePackets-0.1.0-SNAPSHOT.jar', 'NativePackets.jar')]:
                shutil.copyfile(original, plugins / name)
                result[name + '_sha256'] = sha256(original)

        (server / 'eula.txt').write_text('eula=true\n')
        (server / 'server.properties').write_text(
            'server-ip=127.0.0.1\nserver-port=' + str(args.port) + '\n'
            'level-type=minecraft:flat\ngenerator-settings={"layers":[{"block":"minecraft:bedrock","height":1}],"biome":"minecraft:plains"}\n'
            'generate-structures=false\nonline-mode=false\nenforce-secure-profile=false\n'
            'view-distance=2\nsimulation-distance=2\nmax-players=1\n')
        (server / 'bukkit.yml').write_text('settings:\n  allow-end: false\n')
        logfile = evidence / 'server.log'
        try:
            with logfile.open('w') as output:
                process = subprocess.Popen([str(args.java), '-Xms256m', '-Xmx1536m',
                                            '-Djava.io.tmpdir=' + str(server), '-jar',
                                            str(args.server.resolve()), '--nogui'], cwd=server,
                                           stdin=subprocess.PIPE, stdout=output, stderr=subprocess.STDOUT,
                                           text=True, start_new_session=True)
                result['pid'] = process.pid
                (evidence / 'process.json').write_text(json.dumps({'pid': process.pid}))
                deadline = time.monotonic() + 180
                while process.poll() is None and time.monotonic() < deadline:
                    log = logfile.read_text(errors='replace')
                    if 'FASTNBT_COMPLETE' in log or 'Error occurred while enabling ItemsAdder' in log:
                        break
                    time.sleep(.5)
        finally:
            if process is not None:
                stop(process)
                result['exit_code'] = process.returncode
                result['process_stopped'] = process.poll() is not None
            log = logfile.read_text(errors='replace')
            result['checks'] = re.findall(r'FASTNBT_CHECK (\S+) (PASS|FAIL)', log)
            result['members'] = re.findall(r'FASTNBT_MEMBER (.*)', log)
            result['complete'] = 'FASTNBT_COMPLETE' in log
            result['passed'] = sum(status == 'PASS' for _, status in result['checks'])
            result['failed'] = sum(status == 'FAIL' for _, status in result['checks'])
            result['status'] = 'PASS' if result['complete'] and result['passed'] == (5 if args.itemsadder else 3) and not result['failed'] else 'FAIL'
    result['runtime_removed'] = not server.exists()
    (evidence / 'result.json').write_text(json.dumps(result, indent=2) + '\n')
    print(json.dumps(result), flush=True)
    return 0 if result['status'] == 'PASS' else 1


if __name__ == '__main__':
    signal.signal(signal.SIGTERM, lambda *_: (_ for _ in ()).throw(KeyboardInterrupt()))
    raise SystemExit(main())
