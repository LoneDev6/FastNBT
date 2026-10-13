# NItem.edit: implementation and measured comparison

2026-10-13. Implemented locally; no release, push or deployment. Existing NItem setters/save keep their semantics. ItemsAdder production callers were not migrated to an API absent from its declared FastNBT 1.4.26 / NativePackets 0.1.6 artifacts. The earlier ItemsAdder save optimizations remain separate.

`NItem.edit(item, editor -> ...)` opens without converting the item, resolves reads on demand, copies on first mutation and applies once after successful return. Read-only callbacks do not apply. Callback exceptions discard the draft. Compound/list views expire on return and detect replacement/removal/index shifts; arrays, raw tags, inserted compounds/lists and key sets cannot escape as mutable aliases. Retained views release their item/draft references at close. JSON custom name, item name and lore are included. The native Craft commit restores the complete component patch (legacy: item tag); plain Bukkit uses one setItemMeta. No type/amount editor API and no global behavior change to save.

Modern read views borrow custom data read-only; plain Bukkit still requires a native wrapper conversion. Legacy item copying follows the platform's copy semantics. Same-value setters still create a draft; this is mutation tracking, not an expensive final equality comparison. Use only on the owning thread, without modifying or recursively editing the original item during the callback.

The existing NItem constructor no longer allocates an unused empty compound. The scoped list's item getter decodes compound entries written by addItemStack/setItemStack; ordinary NList behavior was not changed.

## Results

Paper 26.3 build 40, OpenJDK 25.0.4, Intel Core i9-14900KF, G1, fixed 1 GiB heap, ActiveProcessorCount=2. Five independent sequential JVMs. Both APIs run in each JVM in seeded shuffled order per round. Per case/JVM: 5 warmup windows and 8 measurement windows, each at least 100 ms. 480 measured windows plus 300 warmups. No sample trimming.

Each case has its own 32-item pool, plain Bukkit or Craft. Identical initial fixture: PAPER, two names, lore, 32 additional string tags, scalar keys and nested compound/list sentinels. Setup and validation are outside timing; wrapper/scope creation, key/JSON construction and required saves/commits are inside. Values change per write operation. Material, amount, payload, names and scalar outcomes are validated after every window; plain-item commit counts are asserted (names 2→1, eight writes 1→1, reads 0→0). Craft commits use NMS, so setItemMeta is not a valid total-commit counter there.

- `names`: set custom name and item name. Direct setters already save, so there is no extra save in this baseline.
- `writes`: eight root integer setters, followed by one save for direct NItem, or one edit callback.
- `reads`: eight existing root integers and an observable checksum.

Both sides use the same final local FastNBT build. “Direct” is the existing NItem API, not the released JAR and not NBT API. It has weaker transaction guarantees on Craft. These results must not be combined numerically with the earlier different-workload NBT API comparison in FastNBT-Benchmark/PAIRED_RESULTS.md.

| Item | Operation | Direct ns/op | edit ns/op | Direct/edit (95% CI) | B/op direct → edit | Fork CV direct/edit |
|---|---|---:|---:|---:|---:|---:|
| bukkit | names | 7712 | 5123 | 1.49× (1.44–1.53) | 28617 → 22881 | 3.7% / 2.1% |
| bukkit | writes | 7629 | 3279 | 2.34× (2.27–2.40) | 17065 → 8161 | 1.7% / 3.1% |
| bukkit | reads | 679 | 160 | 4.29× (4.14–4.45) | 1865 → 689 | 1.7% / 3.7% |
| craft | names | 1976 | 2766 | 0.72× (0.70–0.75) | 15265 → 17241 | 2.4% / 2.7% |
| craft | writes | 4705 | 880 | 5.24× (5.13–5.33) | 9633 → 2521 | 2.7% / 4.3% |
| craft | reads | 95 | 131 | 0.75× (0.73–0.77) | 417 → 561 | 3.4% / 2.1% |

Times and allocations are medians of five per-JVM window medians. The ratio is the geometric mean of five paired per-JVM ratios, so it need not equal the ratio of displayed aggregate medians. CI: 10,000 seeded bootstrap resamples of JVM pairs, conditional on this host and these five JVMs. CV reports variation between JVM medians. The full summary also includes maximum within-JVM CV. Close or universal claims require more hosts/workloads. GC remains included: 411 collections / 714 ms reported during measured windows.

The editor benefits these NBT batches and Bukkit reads. It is slower for Craft reads and the two-name-only Craft case: scoped safety/copying costs more than direct access there. Do not replace every existing NItem access mechanically. For ItemsAdder, adopt controlled multi-write sequences after updating its actual runtime library; preserve public callback/partial-failure contracts. This is API cost, not a TPS or complete ItemsAdder loader measurement.

## Verification and reproduction

- Full Gradle check + shadowJar succeeded across configured adapters: 10 core tests, 14 native merge checks, zero failures. A subsequent core test/shadowJar build verified the final scoped item-list getter.
- Real-server probe: 15 named checks plus lazy counters on Paper 1.20.4 build 499 / Java21 and every Paper26.3 fork. Covers read-only/no commit, exactly one copy on first write, rebinding, all list mutator overloads, item-list round trip, arrays/raw inputs, rollback, expired/stale views, clone isolation, lore removal and missing custom data. Benchmark outcomes are additionally asserted outside each window.
- All task-owned JVMs/process groups exited; runtime worlds were removed.
- The local fixture logs include a flat-generator `No key layers in MapLike[{}]` startup diagnostic before probe execution, config-version and offline-mode warnings; legacy also warns about timings. These did not fail startup or the probe. No claim of completely clean server logs. No client/TPS testing or runtime matrix across every supported version/Spigot.

Final FastNBT SHA-256: `86fec8b49ec5e8c70d8e742d406e5bb8afad703ba65c07058d8eb2b55859382a`.

Sources: `adapters/tests/EditProbe.java`, `run-edit.py`, `summarize-edit.py`. Runner deliberately uses this host's verified immutable Paper/JDK caches. Create a private directory on the verified scratch mount, then:

```bash
rtk python3 adapters/tests/run-edit.py --work-dir "$EDIT_WORK" --forks 5
rtk python3 adapters/tests/summarize-edit.py "$EDIT_WORK/runs"
rtk python3 adapters/tests/run-edit.py --work-dir "$LEGACY_WORK" --version 1.20.4 --forks 1 --checks-only
```

Retained evidence: `/mnt/scratch/tmp/codex/task.e6xESc`. `measured/` contains all CSV windows, exact probe source/JAR, runner, hashes, original server stdout, verdicts and process cleanup records; `legacy-final/` holds the legacy regression evidence. `FastNbt-tested.jar`, source snapshot, environment and validation records support a concrete reproduction or regression follow-up. Earlier smoke/superseded run environments and redundant build logs were removed. Keep this directory while this comparison is needed; it may then be deleted as one task-owned artifact directory.

## Explicit editor lifecycle (2026-10-13)

Added `NItem.edit(item)` returning an AutoCloseable, single-use NItemEditor.
`save()` commits and closes; `close()` discards unsaved changes and is idempotent.
Both invalidate views. Read-only save does not commit. A failed platform apply closes
this editor but does not promise rollback of a partially applied platform operation.
Callback-managed editors reject manual save/close to preserve callback rollback.
Existing save semantics and NMS adapters are unchanged by this follow-up.

Core tests and shadowJar succeeded. Real-server probes passed 18 named checks each
on Paper 26.3 and 1.20.4, including explicit save/close, pre-save isolation, exception
discard, read-only save, stale views and callback lifecycle protection on Bukkit/Craft.
Both servers stopped; disposable worlds and failed socket-setup environments removed.
Known flat-generator startup diagnostic remains in the existing test runner.
No new performance claim: the benchmark above used the preceding library build.
ItemsAdder callers remain unmigrated. Evidence for that follow-up:
`/mnt/scratch/tmp/codex/task.LP0Do6`. Current FastNBT SHA-256: `c9b7993f837c7db197cc1bdba537e33ea3c3dd0667e74c7a1733fc0836545c05`.
