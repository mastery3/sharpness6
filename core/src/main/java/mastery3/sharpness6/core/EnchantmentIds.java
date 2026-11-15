package mastery3.sharpness6.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Canonical enchantment identifiers, legacy-name aliases, and vanilla cap metadata across MC 1.12.2 through 26.3.
 */
public final class EnchantmentIds {

    /** Maximum enchantment level encodable in 1.20.5+ data components and 1.21+ data-driven codecs. */
    public static final int DATA_DRIVEN_MAX_LEVEL_CAP = 255;

    /** Safe upper bound for pre-1.20.5 NBT short-backed enchantment levels. */
    public static final int LEGACY_MAX_LEVEL_CAP = 32767;

    private static final String MINECRAFT_NAMESPACE = "minecraft:";
    private static final Map<String, String> LEGACY_ALIASES;
    private static final Map<String, Integer> VANILLA_DEFAULTS;

    static {
        Map<String, String> aliases = new LinkedHashMap<String, String>();
        // Sweeping Edge used the registry path "sweeping" in MC 1.12 through 1.20.4.
        aliases.put("minecraft:sweeping", "minecraft:sweeping_edge");
        LEGACY_ALIASES = Collections.unmodifiableMap(aliases);

        Map<String, Integer> defaults = new LinkedHashMap<String, Integer>();
        defaults.put("minecraft:aqua_affinity", 1);
        defaults.put("minecraft:bane_of_arthropods", 5);
        defaults.put("minecraft:binding_curse", 1);
        defaults.put("minecraft:blast_protection", 4);
        defaults.put("minecraft:breach", 4);
        defaults.put("minecraft:channeling", 1);
        defaults.put("minecraft:density", 5);
        defaults.put("minecraft:depth_strider", 3);
        defaults.put("minecraft:efficiency", 5);
        defaults.put("minecraft:feather_falling", 4);
        defaults.put("minecraft:fire_aspect", 2);
        defaults.put("minecraft:fire_protection", 4);
        defaults.put("minecraft:flame", 1);
        defaults.put("minecraft:fortune", 3);
        defaults.put("minecraft:frost_walker", 2);
        defaults.put("minecraft:impaling", 5);
        defaults.put("minecraft:infinity", 1);
        defaults.put("minecraft:knockback", 2);
        defaults.put("minecraft:looting", 3);
        defaults.put("minecraft:loyalty", 3);
        defaults.put("minecraft:luck_of_the_sea", 3);
        defaults.put("minecraft:lunge", 3);
        defaults.put("minecraft:lure", 3);
        defaults.put("minecraft:mending", 1);
        defaults.put("minecraft:multishot", 1);
        defaults.put("minecraft:piercing", 4);
        defaults.put("minecraft:power", 5);
        defaults.put("minecraft:projectile_protection", 4);
        defaults.put("minecraft:protection", 4);
        defaults.put("minecraft:punch", 2);
        defaults.put("minecraft:quick_charge", 3);
        defaults.put("minecraft:respiration", 3);
        defaults.put("minecraft:riptide", 3);
        defaults.put("minecraft:sharpness", 5);
        defaults.put("minecraft:silk_touch", 1);
        defaults.put("minecraft:smite", 5);
        defaults.put("minecraft:soul_speed", 3);
        defaults.put("minecraft:sweeping_edge", 3);
        defaults.put("minecraft:swift_sneak", 3);
        defaults.put("minecraft:thorns", 3);
        defaults.put("minecraft:unbreaking", 3);
        defaults.put("minecraft:vanishing_curse", 1);
        defaults.put("minecraft:wind_burst", 3);
        VANILLA_DEFAULTS = Collections.unmodifiableMap(defaults);
    }

    private EnchantmentIds() {
    }

    /**
     * Normalizes an enchantment identifier or path to a lowercase {@code namespace:path} key and resolves legacy aliases.
     */
    public static String canonicalize(String rawId) {
        if (rawId == null) {
            return "";
        }
        String trimmed = rawId.trim().toLowerCase(Locale.ROOT);
        if (trimmed.isEmpty()) {
            return "";
        }
        String namespaced = trimmed.indexOf(':') >= 0 ? trimmed : (MINECRAFT_NAMESPACE + trimmed);
        String alias = LEGACY_ALIASES.get(namespaced);
        return alias != null ? alias : namespaced;
    }

    /**
     * Combines namespace and path into a canonical lowercase {@code namespace:path} key.
     */
    public static String canonicalize(String namespace, String path) {
        if (namespace == null || path == null) {
            return "";
        }
        return canonicalize(namespace.trim() + ":" + path.trim());
    }

    /**
     * Returns the vanilla default max level for known vanilla enchantments, or {@code null} if unknown/custom.
     */
    public static Integer vanillaDefaultMaxLevel(String canonicalId) {
        return VANILLA_DEFAULTS.get(canonicalize(canonicalId));
    }

    /**
     * Returns an unmodifiable view of all vanilla enchantments mapped to their vanilla max levels.
     */
    public static Map<String, Integer> vanillaDefaults() {
        return VANILLA_DEFAULTS;
    }
}
