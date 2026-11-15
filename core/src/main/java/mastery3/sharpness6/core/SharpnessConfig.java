package mastery3.sharpness6.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Serializable configuration for {@code config/sharpness6.json}.
 * Preserves backward compatibility with legacy config keys while adding 26.3, anvil, and custom enchantment support.
 */
public final class SharpnessConfig {

    public int aqua_affinity = 1;
    public Integer bane_of_arthropods = null;
    public int binding_curse = 1;
    public Integer blast_protection = null;
    public int breach = 4;
    public int channeling = 1;
    public int density = 5;
    public int depth_strider = 3;
    public int efficiency = 5;
    public Integer feather_falling = null;
    public int fire_aspect = 2;
    public Integer fire_protection = null;
    public int flame = 1;
    public int fortune = 3;
    public int frost_walker = 2;
    public int impaling = 5;
    public int infinity = 1;
    public int knockback = 2;
    public int looting = 3;
    public int loyalty = 3;
    public int luck_of_the_sea = 3;
    public int lunge = 3;
    public int lure = 3;
    public int mending = 1;
    public int multishot = 1;
    public int piercing = 4;
    public int power = 5;
    public Integer projectile_protection = null;
    public int protection = 4;
    public int punch = 2;
    public int quick_charge = 3;
    public int respiration = 3;
    public int riptide = 3;
    public int sharpness = 5;
    public int silk_touch = 1;
    public Integer smite = null;
    public int soul_speed = 3;
    public int sweeping_edge = 3;
    public int swift_sneak = 3;
    public int thorns = 3;
    public int unbreaking = 3;
    public boolean unbreakingArmorFix = false;
    public int vanishing_curse = 1;
    public int wind_burst = 3;

    public boolean bypassTooExpensive = false;
    public int anvilMaxCost = 39;
    public Map<String, Integer> customMaxLevels = new LinkedHashMap<String, Integer>();

    /**
     * Populates null fallback fields (damage family -> sharpness, protection family -> protection)
     * and normalizes nullable containers.
     */
    public void normalizeFallbacks() {
        if (smite == null) {
            smite = sharpness;
        }
        if (bane_of_arthropods == null) {
            bane_of_arthropods = sharpness;
        }
        if (fire_protection == null) {
            fire_protection = protection;
        }
        if (blast_protection == null) {
            blast_protection = protection;
        }
        if (projectile_protection == null) {
            projectile_protection = protection;
        }
        if (feather_falling == null) {
            feather_falling = protection;
        }
        if (customMaxLevels == null) {
            customMaxLevels = new LinkedHashMap<String, Integer>();
        }
    }

    /**
     * Builds a normalized, bounded map from canonical enchantment IDs to configured maximum levels.
     */
    public Map<String, Integer> toBoundedLevelMap(int maxAllowedCap, Consumer<String> warningSink) {
        normalizeFallbacks();
        Map<String, Integer> levels = new LinkedHashMap<String, Integer>();

        putBounded(levels, "minecraft:aqua_affinity", aqua_affinity, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:bane_of_arthropods", bane_of_arthropods, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:binding_curse", binding_curse, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:blast_protection", blast_protection, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:breach", breach, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:channeling", channeling, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:density", density, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:depth_strider", depth_strider, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:efficiency", efficiency, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:feather_falling", feather_falling, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:fire_aspect", fire_aspect, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:fire_protection", fire_protection, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:flame", flame, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:fortune", fortune, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:frost_walker", frost_walker, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:impaling", impaling, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:infinity", infinity, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:knockback", knockback, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:looting", looting, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:loyalty", loyalty, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:luck_of_the_sea", luck_of_the_sea, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:lunge", lunge, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:lure", lure, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:mending", mending, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:multishot", multishot, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:piercing", piercing, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:power", power, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:projectile_protection", projectile_protection, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:protection", protection, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:punch", punch, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:quick_charge", quick_charge, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:respiration", respiration, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:riptide", riptide, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:sharpness", sharpness, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:silk_touch", silk_touch, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:smite", smite, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:soul_speed", soul_speed, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:sweeping_edge", sweeping_edge, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:swift_sneak", swift_sneak, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:thorns", thorns, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:unbreaking", unbreaking, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:vanishing_curse", vanishing_curse, maxAllowedCap, warningSink);
        putBounded(levels, "minecraft:wind_burst", wind_burst, maxAllowedCap, warningSink);

        for (Map.Entry<String, Integer> entry : customMaxLevels.entrySet()) {
            String key = EnchantmentIds.canonicalize(entry.getKey());
            Integer value = entry.getValue();
            if (!key.isEmpty() && value != null) {
                putBounded(levels, key, value.intValue(), maxAllowedCap, warningSink);
            }
        }
        return levels;
    }

    private static void putBounded(
            Map<String, Integer> target,
            String canonicalId,
            int rawValue,
            int maxAllowedCap,
            Consumer<String> warningSink) {
        int clamped = clampLevel(canonicalId, rawValue, maxAllowedCap, warningSink);
        target.put(canonicalId, Integer.valueOf(clamped));
    }

    private static int clampLevel(String id, int value, int maxAllowedCap, Consumer<String> warningSink) {
        if (value < 1) {
            if (warningSink != null) {
                warningSink.accept("Configured max level " + value + " for " + id + " is below 1; clamping to 1.");
            }
            return 1;
        }
        if (value > maxAllowedCap) {
            if (warningSink != null) {
                warningSink.accept("Configured max level " + value + " for " + id + " exceeds supported cap "
                        + maxAllowedCap + "; clamping to " + maxAllowedCap + ".");
            }
            return maxAllowedCap;
        }
        return value;
    }
}
