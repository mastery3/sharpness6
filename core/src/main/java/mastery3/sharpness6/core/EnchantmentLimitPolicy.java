package mastery3.sharpness6.core;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Resolves effective max enchantment levels, active override checks, and the optional Unbreaking armor probability fix.
 */
public final class EnchantmentLimitPolicy {

    private final SharpnessConfig config;
    private final Map<String, Integer> boundedLevels;
    private final Set<String> explicitlyCustomIds;

    public EnchantmentLimitPolicy(SharpnessConfig config, int maxAllowedCap, Consumer<String> warningSink) {
        if (config == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        this.config = config;
        this.boundedLevels = Collections.unmodifiableMap(config.toBoundedLevelMap(maxAllowedCap, warningSink));
        Set<String> customIds = new LinkedHashSet<String>();
        for (String key : config.enchantments.keySet()) {
            String canonical = EnchantmentIds.canonicalize(key);
            if (!canonical.isEmpty()) {
                customIds.add(canonical);
            }
        }
        this.explicitlyCustomIds = Collections.unmodifiableSet(customIds);
    }

    public SharpnessConfig config() {
        return config;
    }

    /**
     * Returns the configured max level only if it actively overrides the vanilla default.
     */
    public OptionalInt resolveActiveOverride(String rawEnchantmentId) {
        String canonical = EnchantmentIds.canonicalize(rawEnchantmentId);
        Integer configured = boundedLevels.get(canonical);
        if (configured == null) {
            return OptionalInt.empty();
        }
        if (explicitlyCustomIds.contains(canonical)) {
            return OptionalInt.of(configured.intValue());
        }

        return OptionalInt.of(configured.intValue());
    }

    /**
     * Resolves an override for runtime subclass/mixin hooks, returning empty if the enchantment should remain vanilla.
     */
    public OptionalInt resolveOverrideForVanillaBaseline(String rawEnchantmentId, int currentBaselineLevel) {
        OptionalInt active = resolveActiveOverride(rawEnchantmentId);
        if (!active.isPresent()) {
            return OptionalInt.empty();
        }
        return active.getAsInt() == currentBaselineLevel ? OptionalInt.empty() : active;
    }

    /**
     * Probability to prevent armor durability loss under {@code unbreakingArmorFix}:
     * {@code level / (level + 2.0)}, matching {@code (0.5 * level) / (1.0 + 0.5 * level)}.
     */
    public static boolean shouldPreventArmorDamage(int level, double randomUnitSample) {
        if (level <= 0) {
            return false;
        }
        double preventChance = ((double) level) / ((double) level + 2.0d);
        return randomUnitSample < preventChance;
    }
}
