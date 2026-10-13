"""Summarize all measurement windows; no sample trimming."""
import collections, csv, json, math, random, statistics, sys
from pathlib import Path

def ratio_interval(values):
    logs=[math.log(v) for v in values]
    rng=random.Random(410)
    boot=sorted(math.exp(statistics.mean(rng.choices(logs,k=len(logs)))) for _ in range(10000))
    return math.exp(statistics.mean(logs)), boot[249], boot[9749]

def cv(values):
    return 100*statistics.stdev(values)/statistics.mean(values) if len(values)>1 else 0

def summarize(root):
    groups=collections.defaultdict(lambda: collections.defaultdict(list))
    all_rows=[]
    for file in sorted(root.glob('*-fork-*/edit-benchmark.csv')):
        verdict=json.loads((file.parent/'verdict.json').read_text())
        assert verdict['success']
        for row in csv.DictReader(file.open()):
            all_rows.append(row)
            if row['phase']=='measure':groups[(row['variant'],row['workload'])][file.parent.name].append(row)
    rows=[]
    for variant in ('bukkit','craft'):
        for workload in ('names','writes','reads'):
            baseline=groups[(variant,workload+'-legacy')]; candidate=groups[(variant,workload+'-edit')]
            assert baseline.keys()==candidate.keys() and baseline
            forks=sorted(baseline)
            def median(group,key,fork):return statistics.median(float(r[key]) for r in group[fork])
            before=[median(baseline,'ns_op',f) for f in forks]; after=[median(candidate,'ns_op',f) for f in forks]
            ratio,lo,hi=ratio_interval([a/b for a,b in zip(before,after)])
            rows.append(dict(variant=variant,workload=workload,before=statistics.median(before),after=statistics.median(after),ratio=ratio,lo=lo,hi=hi,
                bytes_before=statistics.median(median(baseline,'bytes_op',f) for f in forks),bytes_after=statistics.median(median(candidate,'bytes_op',f) for f in forks),
                cv_before=cv(before),cv_after=cv(after),forks=len(forks),samples=sum(map(len,baseline.values())),
                window_cv_before=max(cv([float(r['ns_op']) for r in baseline[f]]) for f in forks),window_cv_after=max(cv([float(r['ns_op']) for r in candidate[f]]) for f in forks)))
    lines=['| Item | Operation | Direct ns/op | edit ns/op | Direct/edit (95% CI) | B/op direct → edit | Fork CV direct/edit |',
           '|---|---|---:|---:|---:|---:|---:|']
    for r in rows:
        lines.append(f"| {r['variant']} | {r['workload']} | {r['before']:.0f} | {r['after']:.0f} | {r['ratio']:.2f}× ({r['lo']:.2f}–{r['hi']:.2f}) | {r['bytes_before']:.0f} → {r['bytes_after']:.0f} | {r['cv_before']:.1f}% / {r['cv_after']:.1f}% |")
    measured=[r for r in all_rows if r['phase']=='measure']
    summary=dict(results=rows,measurement_windows=len(measured),warmup_windows=len(all_rows)-len(measured),
        gc_count=sum(int(r.get('gc_count',0)) for r in measured),gc_millis=sum(int(r.get('gc_millis',0)) for r in measured))
    (root/'summary.json').write_text(json.dumps(summary,indent=2)+'\n')
    (root/'table.md').write_text('\n'.join(lines)+'\n')
    print('\n'.join(lines));print(json.dumps({k:v for k,v in summary.items() if k!='results'}))

if __name__=='__main__':
    assert ratio_interval([2,2,2,2,2])==(2,2,2)
    assert cv([1,1,1])==0
    summarize(Path(sys.argv[1]))
