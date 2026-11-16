# Sharpness VI

A configurable **Fabric** mod that lets players and server owners raise enchantment maximum levels beyond vanilla caps and fix vanilla's plateauing Unbreaking scaling on armor. Supports **Minecraft 1.12.2 through 26.3**.

> **Safe by default:** Leaving the default values in `config/sharpness6.json` preserves vanilla behavior until you edit the caps you want to raise.

## What it does

- Raises maximum levels for vanilla enchantments (Sharpness, Protection, Efficiency, Fortune, Looting, Unbreaking, and many more) via a per-enchantment config.
- Optionally fixes armor Unbreaking so higher levels keep scaling instead of plateauing at 60%.
- Supports custom/datapack enchantments.
- Works on both Java Edition clients and dedicated servers.

## Supported Minecraft Versions

1.12.2, 1.13.2, 1.14.4, 1.15.2, 1.16.5, 1.17.1, 1.18.2, 1.19.2, 1.19.4, 1.20.1, 1.20.4, 1.20.6, 1.21.1, 1.21.4, 1.21.8, 1.21.11, 26.1.2, 26.2, 26.3

Each version has its own jar. Pick the one matching your Minecraft version.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for your Minecraft version.
2. Download the matching Sharpness VI jar from the [releases page](https://github.com/mastery3/sharpness6/releases).
3. Place the jar in your `mods/` folder.
4. Start the game once to generate `config/sharpness6.json`, then edit it to taste.

## Configuration (`config/sharpness6.json`)

The config file is created automatically on startup with vanilla baseline values. Per-enchantment caps (e.g. `sharpness`, `protection`, `efficiency`) default to vanilla levels; raise them to allow higher enchantments. `smite`/`bane_of_arthropods` inherit `sharpness` when `null`; the protection family inherits `protection`.

- **`unbreakingArmorFix`** (`false` by default): replaces vanilla's armor Unbreaking formula (`60% + 40%/(level + 1)`, which weakens above level 1) with `level/(level + 2)` so high Unbreaking levels scale asymptotically.
- **`customMaxLevels`** (`{}` by default): map of enchantment ID to max level for modded/datapack enchantments.

Example:

```json
{
  "sharpness": 10,
  "protection": 10,
  "efficiency": 10,
  "unbreaking": 10,
  "unbreakingArmorFix": true,
  "fortune": 10,
  "looting": 10
}
```

Caps are clamped to `[1, 255]` on 1.20.5+ and `[1, 32767]` on 1.12.2–1.20.4.

## Important limitations

- Only 26.3 has been runtime-tested. Other versions compile and pass bytecode checks but have not each been launched.
- On 1.12.2 and 1.13.2, enchantment levels above 255 can display raw translation keys. Modern versions render them correctly.
- Clients without the mod display raw `enchantment.level.11`+ keys for levels above 10 unless they install the mod or its resource pack.

## License

Licensed under Creative Commons Attribution 4.0. See [`LICENSE`](LICENSE).

## More information

- Build and architecture details: [`ARCHITECTURE.md`](ARCHITECTURE.md)
- Source: https://github.com/mastery3/sharpness6