package mastery3.sharpness6.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Serializable configuration for {@code config/sharpness6.json}.
 * Preserves backward compatibility with legacy config keys while adding 26.3, anvil, and custom enchantment support.
 */
public final class SharpnessConfig {
    public Map<String, Integer> enchantments = new LinkedHashMap<String, Integer>();
    public Enhancements enhancements = new Enhancements();

    public static final class Enhancements {
        public Unbreaking unbreaking = new Unbreaking();
        public static final class Unbreaking {
            public Boolean modifyArmorFormula = false;

            void resetNull() {
                if (modifyArmorFormula == null) { modifyArmorFormula = false; }
            }
        }

        void resetNull() {
            if (unbreaking == null) { unbreaking = new Unbreaking(); }
            unbreaking.resetNull();
        }
    }

    /**
     * Populates null fallback fields (damage family -> sharpness, protection family -> protection)
     * and normalizes nullable containers.
     */
    public void migrate() {
        if (enchantments == null) { enchantments = new LinkedHashMap<String, Integer>(); }
        if (enhancements == null) { enhancements = new Enhancements(); }
        enhancements.resetNull();

        if (smite == null) { smite = sharpness; }
        if (bane_of_arthropods == null) { bane_of_arthropods = sharpness; }
        if (fire_protection == null) { fire_protection = protection; }
        if (blast_protection == null) { blast_protection = protection; }
        if (projectile_protection == null) { projectile_protection = protection; }
        if (feather_falling == null) { feather_falling = protection; }

        if (aqua_affinity != null && !enchantments.containsKey("minecraft:aqua_affinity")) {
            enchantments.put("minecraft:aqua_affinity", aqua_affinity);
            aqua_affinity = null;
        }

        if (bane_of_arthropods != null && !enchantments.containsKey("minecraft:bane_of_arthropods")) {
            enchantments.put("minecraft:bane_of_arthropods", bane_of_arthropods);
            bane_of_arthropods = null;
        }

        if (binding_curse != null && !enchantments.containsKey("minecraft:binding_curse")) {
            enchantments.put("minecraft:binding_curse", binding_curse);
            binding_curse = null;
        }

        if (blast_protection != null && !enchantments.containsKey("minecraft:blast_protection")) {
            enchantments.put("minecraft:blast_protection", blast_protection);
            blast_protection = null;
        }

        if (breach != null && !enchantments.containsKey("minecraft:breach")) {
            enchantments.put("minecraft:breach", breach);
            breach = null;
        }

        if (channeling != null && !enchantments.containsKey("minecraft:channeling")) {
            enchantments.put("minecraft:channeling", channeling);
            channeling = null;
        }

        if (density != null && !enchantments.containsKey("minecraft:density")) {
            enchantments.put("minecraft:density", density);
            density = null;
        }

        if (depth_strider != null && !enchantments.containsKey("minecraft:depth_strider")) {
            enchantments.put("minecraft:depth_strider", depth_strider);
            depth_strider = null;
        }

        if (efficiency != null && !enchantments.containsKey("minecraft:efficiency")) {
            enchantments.put("minecraft:efficiency", efficiency);
            efficiency = null;
        }

        if (feather_falling != null && !enchantments.containsKey("minecraft:feather_falling")) {
            enchantments.put("minecraft:feather_falling", feather_falling);
            feather_falling = null;
        }

        if (fire_aspect != null && !enchantments.containsKey("minecraft:fire_aspect")) {
            enchantments.put("minecraft:fire_aspect", fire_aspect);
            fire_aspect = null;
        }

        if (fire_protection != null && !enchantments.containsKey("minecraft:fire_protection")) {
            enchantments.put("minecraft:fire_protection", fire_protection);
            fire_protection = null;
        }

        if (flame != null && !enchantments.containsKey("minecraft:flame")) {
            enchantments.put("minecraft:flame", flame);
            flame = null;
        }

        if (fortune != null && !enchantments.containsKey("minecraft:fortune")) {
            enchantments.put("minecraft:fortune", fortune);
            fortune = null;
        }

        if (frost_walker != null && !enchantments.containsKey("minecraft:frost_walker")) {
            enchantments.put("minecraft:frost_walker", frost_walker);
            frost_walker = null;
        }

        if (impaling != null && !enchantments.containsKey("minecraft:impaling")) {
            enchantments.put("minecraft:impaling", impaling);
            impaling = null;
        }

        if (infinity != null && !enchantments.containsKey("minecraft:infinity")) {
            enchantments.put("minecraft:infinity", infinity);
            infinity = null;
        }

        if (knockback != null && !enchantments.containsKey("minecraft:knockback")) {
            enchantments.put("minecraft:knockback", knockback);
            knockback = null;
        }

        if (looting != null && !enchantments.containsKey("minecraft:looting")) {
            enchantments.put("minecraft:looting", looting);
            looting = null;
        }

        if (loyalty != null && !enchantments.containsKey("minecraft:loyalty")) {
            enchantments.put("minecraft:loyalty", loyalty);
            loyalty = null;
        }

        if (luck_of_the_sea != null && !enchantments.containsKey("minecraft:luck_of_the_sea")) {
            enchantments.put("minecraft:luck_of_the_sea", luck_of_the_sea);
            luck_of_the_sea = null;
        }

        if (lunge != null && !enchantments.containsKey("minecraft:lunge")) {
            enchantments.put("minecraft:lunge", lunge);
            lunge = null;
        }

        if (lure != null && !enchantments.containsKey("minecraft:lure")) {
            enchantments.put("minecraft:lure", lure);
            lure = null;
        }

        if (mending != null && !enchantments.containsKey("minecraft:mending")) {
            enchantments.put("minecraft:mending", mending);
            mending = null;
        }

        if (multishot != null && !enchantments.containsKey("minecraft:multishot")) {
            enchantments.put("minecraft:multishot", multishot);
            multishot = null;
        }

        if (piercing != null && !enchantments.containsKey("minecraft:piercing")) {
            enchantments.put("minecraft:piercing", piercing);
            piercing = null;
        }

        if (power != null && !enchantments.containsKey("minecraft:power")) {
            enchantments.put("minecraft:power", power);
            power = null;
        }

        if (projectile_protection != null && !enchantments.containsKey("minecraft:projectile_protection")) {
            enchantments.put("minecraft:projectile_protection", projectile_protection);
            projectile_protection = null;
        }

        if (protection != null && !enchantments.containsKey("minecraft:protection")) {
            enchantments.put("minecraft:protection", protection);
            protection = null;
        }

        if (punch != null && !enchantments.containsKey("minecraft:punch")) {
            enchantments.put("minecraft:punch", punch);
            punch = null;
        }

        if (quick_charge != null && !enchantments.containsKey("minecraft:quick_charge")) {
            enchantments.put("minecraft:quick_charge", quick_charge);
            quick_charge = null;
        }

        if (respiration != null && !enchantments.containsKey("minecraft:respiration")) {
            enchantments.put("minecraft:respiration", respiration);
            respiration = null;
        }

        if (riptide != null && !enchantments.containsKey("minecraft:riptide")) {
            enchantments.put("minecraft:riptide", riptide);
            riptide = null;
        }

        if (sharpness != null && !enchantments.containsKey("minecraft:sharpness")) {
            enchantments.put("minecraft:sharpness", sharpness);
            sharpness = null;
        }

        if (silk_touch != null && !enchantments.containsKey("minecraft:silk_touch")) {
            enchantments.put("minecraft:silk_touch", silk_touch);
            silk_touch = null;
        }

        if (smite != null && !enchantments.containsKey("minecraft:smite")) {
            enchantments.put("minecraft:smite", smite);
            smite = null;
        }

        if (soul_speed != null && !enchantments.containsKey("minecraft:soul_speed")) {
            enchantments.put("minecraft:soul_speed", soul_speed);
            soul_speed = null;
        }

        if (sweeping_edge != null && !enchantments.containsKey("minecraft:sweeping_edge")) {
            enchantments.put("minecraft:sweeping_edge", sweeping_edge);
            sweeping_edge = null;
        }

        if (swift_sneak != null && !enchantments.containsKey("minecraft:swift_sneak")) {
            enchantments.put("minecraft:swift_sneak", swift_sneak);
            swift_sneak = null;
        }

        if (thorns != null && !enchantments.containsKey("minecraft:thorns")) {
            enchantments.put("minecraft:thorns", thorns);
            thorns = null;
        }

        if (unbreaking != null && !enchantments.containsKey("minecraft:unbreaking")) {
            enchantments.put("minecraft:unbreaking", unbreaking);
            unbreaking = null;
        }

        if (vanishing_curse != null && !enchantments.containsKey("minecraft:vanishing_curse")) {
            enchantments.put("minecraft:vanishing_curse", vanishing_curse);
            vanishing_curse = null;
        }

        if (wind_burst != null && !enchantments.containsKey("minecraft:wind_burst")) {
            enchantments.put("minecraft:wind_burst", wind_burst);
            wind_burst = null;
        }

        if (Boolean.TRUE.equals(unbreakingArmorFix)) {
            enhancements.unbreaking.modifyArmorFormula = true;
            unbreakingArmorFix = null;
        }

        // Canonicalize shorthand keys (e.g. "sharpness") to namespaced keys
        // ("minecraft:sharpness") without mutating the map during iteration.
        // An explicitly namespaced entry always wins over its shorthand alias.
        Map<String, Integer> additions = new LinkedHashMap<String, Integer>();
        java.util.Set<String> removals = new java.util.LinkedHashSet<String>();
        for (Map.Entry<String, Integer> entry : new java.util.ArrayList<Map.Entry<String, Integer>>(enchantments.entrySet())) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (key == null) {
                continue;
            }
            if (value == null) {
                removals.add(key);
                continue;
            }

            String canonical = EnchantmentIds.canonicalize(key);
            if (key.equals(canonical)) {
                continue;
            }
            if (value != null && !enchantments.containsKey(canonical) && !additions.containsKey(canonical)) {
                additions.put(canonical, value);
                removals.add(key);
            }
        }
        for (Map.Entry<String, Integer> e : additions.entrySet()) {
            enchantments.put(e.getKey(), e.getValue());
        }
        for (String k : removals) {
            enchantments.remove(k);
        }
    }

    /**
     * Builds a normalized, bounded map from canonical enchantment IDs to configured maximum levels.
     */
    public Map<String, Integer> toBoundedLevelMap(int maxAllowedCap, Consumer<String> warningSink) {
        migrate();
        Map<String, Integer> levels = new LinkedHashMap<String, Integer>();

        for (Map.Entry<String, Integer> entry : enchantments.entrySet()) {
            String key = entry.getKey();
            String canonical = EnchantmentIds.canonicalize(key);
            Integer value = entry.getValue();
            if (canonical.isEmpty() || value == null) {
                continue;
            }
            // If a shorthand alias and its namespaced form both exist, the
            // namespaced form wins; skip the alias to avoid overriding it.
            if (!key.equals(canonical) && enchantments.containsKey(canonical)) {
                continue;
            }
            putBounded(levels, canonical, value.intValue(), maxAllowedCap, warningSink);
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

    // Legacy
    public Integer aqua_affinity = null;
    public Integer bane_of_arthropods = null;
    public Integer binding_curse = null;
    public Integer blast_protection = null;
    public Integer breach = null;
    public Integer channeling = null;
    public Integer density = null;
    public Integer depth_strider = null;
    public Integer efficiency = null;
    public Integer feather_falling = null;
    public Integer fire_aspect = null;
    public Integer fire_protection = null;
    public Integer flame = null;
    public Integer fortune = null;
    public Integer frost_walker = null;
    public Integer impaling = null;
    public Integer infinity = null;
    public Integer knockback = null;
    public Integer looting = null;
    public Integer loyalty = null;
    public Integer luck_of_the_sea = null;
    public Integer lunge = null;
    public Integer lure = null;
    public Integer mending = null;
    public Integer multishot = null;
    public Integer piercing = null;
    public Integer power = null;
    public Integer projectile_protection = null;
    public Integer protection = null;
    public Integer punch = null;
    public Integer quick_charge = null;
    public Integer respiration = null;
    public Integer riptide = null;
    public Integer sharpness = null;
    public Integer silk_touch = null;
    public Integer smite = null;
    public Integer soul_speed = null;
    public Integer sweeping_edge = null;
    public Integer swift_sneak = null;
    public Integer thorns = null;
    public Integer unbreaking = null;
    public Boolean unbreakingArmorFix = null;
    public Integer vanishing_curse = null;
    public Integer wind_burst = null;
}
