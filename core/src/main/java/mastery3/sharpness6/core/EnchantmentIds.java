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

    private static final String MINECRAFT_NAMESPACE = "minecraft";
    private static final Map<String, String> LEGACY_ALIASES;

    static {
        Map<String, String> aliases = new LinkedHashMap<String, String>();
        // Sweeping Edge used the registry path "sweeping" in MC 1.12 through 1.20.4.
        aliases.put("minecraft:sweeping", "minecraft:sweeping_edge");
        LEGACY_ALIASES = Collections.unmodifiableMap(aliases);
    }

    private EnchantmentIds() {}

    /**
     * Normalizes an enchantment identifier or path to a lowercase {@code namespace:path} key and resolves legacy aliases.
     */
    public static String canonicalize(String rawId) {
        if (rawId == null) { return null; }

        String trimmed = rawId.trim().toLowerCase(Locale.ROOT);
        if (trimmed.isEmpty()) { return ""; }

        String namespaced = trimmed.indexOf(':') >= 0 ? trimmed : (MINECRAFT_NAMESPACE + ":" + trimmed);
        String alias = LEGACY_ALIASES.get(namespaced);
        return alias != null ? alias : namespaced;
    }

    /**
     * Combines namespace and path into a canonical lowercase {@code namespace:path} key.
     */
    public static String canonicalize(String namespace, String path) {
        if (path == null) { return null; }
        if (namespace == null) { namespace = MINECRAFT_NAMESPACE; }

        return canonicalize(namespace.trim() + ":" + path.trim());
    }
}
