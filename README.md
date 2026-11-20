# Sharpness VI
Have you ever tried to combine two Sharpness V swords to make a Sharpness VI one, only to be disappointed? Sharpness VI is a Fabric mod that uncaps your weapons' potential and allows exceeding vanilla enchantment limits.

## Installation
> [!NOTE]
> This mod does not do anything by default until it is configured.
1. Install [Fabric Loader](https://fabricmc.net/use/) for your Minecraft version.
2. Download the matching Sharpness VI jar from the [releases page](https://github.com/mastery3/sharpness6/releases).
3. Place the jar in your `mods/` folder.
4. Start Minecraft.

The mod is supported and tested on the following Java edition versions: 1.14.4, 1.18.2, 1.19.2, 1.19.4, 1.20.1, 1.20.4, 1.20.6, 1.21.1, 1.21.4, 1.21.8, 1.21.11, 26.1.2, 26.2, 26.3. It is additionally available, but untested on 1.12.2, 1.13.2, 1.15.2, 1.16.5, and 1.17.1. 

It is available for both clients and servers. If installed on a server, it should also be installed on its clients, if any enchantments are configured to have a maximum level of more than 10. (Minecraft does not have language keys for roman numerals exceeding X.)

Alternatively, the mod may be compiled locally:
```sh
# latest minecraft version
./gradlew build

# all minecraft versions
./gradlew buildAll
```

## Configuration
To increase Sharpness's maximum level to 6, add a key to `enchantments`:
```jsonc
{
  "enchantments": {
    "minecraft:sharpness": 6
  }
}
```
This mod supports every vanilla Minecraft enchantment. On versions 1.20.6 and later, it also supports custom enchantments. Levels up to 255 are supported (1.12.2–1.20.4 support levels up to 32767).

It also includes modifications for certain enchantments that previously didn't scale well with higher levels. They may be specified like this:
```jsonc
{
  "enchantments": {
    "minecraft:sharpness": 6,
  },
  "enhancements": {
    "unbreaking": {
      "modifyArmorFormula": true, // adjust the armor damage prevention formula
    }
  }
}
```

## License

Licensed under Creative Commons Attribution 4.0. See [`LICENSE`](LICENSE).

## More information

- Build and architecture details: [`ARCHITECTURE.md`](ARCHITECTURE.md)
- Source: https://github.com/mastery3/sharpness6
