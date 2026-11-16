# Architecture

Engineering reference for Sharpness VI. Player-facing documentation is in [`README.md`](README.md).

## Layout

- `core/` — Minecraft-agnostic config store, policy normalization, Roman numerals, Gson registry patcher, and JUnit 5 unit tests.
- `adapters/` — per-era Fabric integration modules sharing `core/` and `shared-resources/`.
- `versions/` — declarative per-version build definitions (`gradle.properties`).
- `legacy/` — isolated Legacy Fabric Gradle build for 1.12.2 and 1.13.2.
- `shared-resources/` — common assets, translations, and `fabric.mod.json` template.

## Adapter eras

| Era | Versions | Adapter |
|---|---|---|
| Unobfuscated Modern | 26.1.2, 26.2, 26.3 | `unobf-26` |
| Data-Driven | 1.21.1, 1.21.4, 1.21.8, 1.21.11 | `datapack-1.21` |
| Data Components | 1.20.6 | `properties-1.20.6` |
| ScreenHandler | 1.16.5–1.20.4 | `subclass-1.16/1.19.2/1.19.4` + `subclass-common` |
| Pre-ScreenHandler | 1.14.4, 1.15.2 | `subclass-1.14` |
| Legacy Fabric | 1.12.2, 1.13.2 | `legacy/` (isolated build) |

Shared adapters are composed via `shared_adapter=` in each version's `gradle.properties`.

## Building

Requires JDK 25+ (`javac --release` handles older target bytecodes).

```bash
./gradlew build                      # core tests + latest (26.3) jar
./gradlew :core:test                # core unit tests only
./gradlew buildAll                  # all 19 versions into build/dist/
./gradlew :versions:1.21.11:build   # single version
./gradlew buildLegacy               # legacy 1.12.2/1.13.2
```

## Version-range verification

`scripts/verify-version-ranges.py` audits every built jar's `fabric.mod.json` Minecraft dependency against the 19-version support matrix. Each version must match exactly one jar (no overlaps, no gaps). Run after `buildAll`.

## Adding a new Minecraft version

1. Add `versions/<version>/gradle.properties` with `minecraft_version`, `adapter`, `shared_adapter`, `java_version`, `mappings_spec`, `loader_version`, `loader_dep`, `mc_dep`, `remap`, and `level_suffix_mixin`.
2. Add the version to the `SUPPORTED` list in `scripts/verify-version-ranges.py`.
3. Build and verify: `./gradlew :versions:<version>:build`.

## Testing expectations

- Core unit tests must pass (`./gradlew :core:test`).
- All 19 versions must build (`./gradlew buildAll`).
- Version-range script must pass.
- Runtime testing is currently limited to 26.3; other versions are compile- and bytecode-verified only.

## Configuration compatibility

`SharpnessConfig` supports both the original per-field format and the Modrinth alpha's nested format (`enchantments` map + `enhancements.unbreaking.modifyArmorFormula`). Alpha configs are migrated in memory on load; the file is rewritten in the canonical format only when saved.