# Sharpness VI

A configurable **Fabric** mod that lets players and server owners customize enchantment maximum levels beyond vanilla caps, optionally bypass the anvil `"Too Expensive!"` limit, and fix vanilla's plateauing Unbreaking scaling on armor across **Minecraft 1.12.2 through 26.3**.

> **Safe by default:** Leaving default values in `config/sharpness6.json` preserves vanilla behavior until you edit the caps you want to raise.

## Supported Minecraft Versions

| Era | Minecraft Versions | Loader / Mappings | Adapter Strategy |
| :--- | :--- | :--- | :--- |
| Unobfuscated Modern | `26.1.2`, `26.2`, `26.3` | Fabric Loader `>=0.20.1`, Mojmap, Java 25+ | `unobf-26` (Registry JSON patcher + `AnvilMenuMixin`) |
| Data-Driven | `1.21.1`, `1.21.4`, `1.21.8`, `1.21.11` | Fabric Loader, Yarn, Java 21+ | `datapack-1.21` (`RegistryLoaderMixin` + `AnvilScreenHandlerMixin`) |
| Data Components | `1.20.6` | Fabric Loader `>=0.15.10`, Yarn, Java 21+ | `properties-1.20.6` (`EnchantmentMixin` + `UnbreakingEnchantmentMixin`) |
| ScreenHandler Era | `1.16.5`, `1.17.1`, `1.18.2`, `1.19.2`, `1.19.4`, `1.20.1`, `1.20.4` | Fabric Loader `>=0.15.11`, Yarn | Composable `subclass-*` mixins across `Enchantment` & overrides |
| Pre-ScreenHandler | `1.14.4`, `1.15.2` | Fabric Loader `>=0.15.11`, Yarn, Java 8+ | `subclass-1.14` (`getMaximumLevel` + `AnvilContainer`) |
| Legacy Fabric | `1.12.2`, `1.13.2` | Legacy Fabric Loader `>=0.14.0`, Legacy Yarn | Isolated `legacy/` Gradle build (`en_us.lang` in `1.12.2`, `en_us.json` in `1.13.2`) |

## Configuration (`config/sharpness6.json`)

The config file is created automatically on startup with vanilla baseline values:

- **Per-enchantment caps** (e.g., `sharpness`, `protection`, `efficiency`, `fortune`, `looting`, `unbreaking`, `density`, `breach`, `wind_burst`, `lunge`, etc.):
  - `smite` and `bane_of_arthropods` inherit `sharpness` when `null`.
  - `fire_protection`, `blast_protection`, `projectile_protection`, and `feather_falling` inherit `protection` when `null`.
  - Caps are safely clamped to `[1, 255]` on `1.20.5+` (data components/codecs) and `[1, 32767]` on `1.12.2–1.20.4` (NBT shorts).
- **`unbreakingArmorFix`** (`false` by default): replaces vanilla's armor Unbreaking formula (`60% + 40%/(level + 1)`, which weakens above level 1) with `level/(level + 2)` so high Unbreaking levels scale asymptotically without becoming invulnerable.
- **`bypassTooExpensive`** (`false` by default) & **`anvilMaxCost`** (`39` by default):
  - When `bypassTooExpensive` is `true` and `anvilMaxCost` is between `1` and `39`, anvil operations costing 40+ levels are clamped to `anvilMaxCost` so unmodified vanilla and Bedrock/Geyser clients can claim the item without desync.
  - Set `anvilMaxCost` to `0` (with `bypassTooExpensive: true`) for uncapped anvil costs.
  - Stacked input checks (`count > 1`) remain protected by vanilla's anti-duplication guard.
- **`customMaxLevels`** (`{}` by default): optional map of `namespace:enchantment_id` (or short `minecraft:` path) to max level for modded/datapack enchantments or explicit overrides. Legacy `minecraft:sweeping` aliases automatically to `minecraft:sweeping_edge`.

Example high-level server config:

```json
{
  "sharpness": 10,
  "protection": 10,
  "efficiency": 10,
  "unbreaking": 10,
  "unbreakingArmorFix": true,
  "bypassTooExpensive": true,
  "anvilMaxCost": 39,
  "fortune": 10,
  "looting": 10
}
```

Includes Roman numeral localization (`enchantment.level.11`–`255`) so levels XI and above render cleanly in item tooltips.

## Project Structure & Building

- [`core/`](core) — Minecraft-agnostic config store, policy normalization, anvil cost calculation, Roman numerals, Gson registry patcher, and JUnit 5 unit tests.
- [`adapters/`](adapters) — Minimal, composable per-era Fabric integration modules sharing `core/` and [`shared-resources/`](shared-resources).
- [`versions/`](versions) — Declarative per-version build definitions (`gradle.properties`).
- [`legacy/`](legacy) — Isolated Legacy Fabric Gradle build for `1.12.2` and `1.13.2` sharing `../core` and `../shared-resources`.

Requires JDK 25+ (`javac --release` handles older target bytecodes automatically):

```bash
# Run core JUnit tests + build latest Minecraft (26.3) jar into build/libs/
./gradlew build

# Run only core unit tests
./gradlew :core:test

# Build every supported Minecraft version (1.12.2 - 26.3) into build/dist/
./gradlew buildAll

# Build only a specific version
./gradlew :versions:1.21.11:build
```

## FAQ

- **Does installing this without editing the config change gameplay?**
  No. All default values match vanilla Minecraft (`bypassTooExpensive: false`, vanilla max levels).
- **Can I use this in a modpack or server-side?**
  Yes (licensed under Creative Commons Attribution 4.0; see [`LICENSE`](LICENSE)). On servers running `26.3`, clients do not strictly require the mod when `anvilMaxCost` stays at `39` (though clients without the mod display `enchantment.level.11`+ raw translation keys for levels above 10 unless they install the mod/resource pack).
