package mastery3.sharpness6.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

final class EnchantmentLimitPolicyTest {

    @Test
    void untouchedVanillaValuesReturnEmptyOverride() {
        SharpnessConfig config = new SharpnessConfig();
        EnchantmentLimitPolicy policy = new EnchantmentLimitPolicy(
                config, EnchantmentIds.DATA_DRIVEN_MAX_LEVEL_CAP, null);

        assertFalse(policy.resolveActiveOverride("minecraft:sharpness").isPresent());
        assertFalse(policy.resolveActiveOverride("minecraft:lunge").isPresent());
        assertFalse(policy.resolveActiveOverride("nova_structures:gravity").isPresent());
    }

    @Test
    void resolvesModifiedVanillaLegacyAliasAndCustomEnchantments() {
        SharpnessConfig config = new SharpnessConfig();
        config.sharpness = 10;
        config.sweeping_edge = 6;
        config.customMaxLevels.put("nova_structures:gravity", 7);

        EnchantmentLimitPolicy policy = new EnchantmentLimitPolicy(
                config, EnchantmentIds.DATA_DRIVEN_MAX_LEVEL_CAP, null);

        assertEquals(OptionalInt.of(10), policy.resolveActiveOverride("minecraft:sharpness"));
        assertEquals(OptionalInt.of(10), policy.resolveActiveOverride("smite"));
        // Pre-1.20.5 "minecraft:sweeping" canonicalizes to "minecraft:sweeping_edge".
        assertEquals(OptionalInt.of(6), policy.resolveActiveOverride("minecraft:sweeping"));
        assertEquals(OptionalInt.of(7), policy.resolveActiveOverride("NOVA_STRUCTURES:Gravity"));
    }

    @Test
    void clampsLevelsToCodecBoundsWithWarnings() {
        SharpnessConfig config = new SharpnessConfig();
        config.sharpness = 999;
        config.smite = 5;
        config.bane_of_arthropods = 5;
        config.mending = 0;
        List<String> warnings = new ArrayList<String>();

        EnchantmentLimitPolicy policy = new EnchantmentLimitPolicy(
                config, EnchantmentIds.DATA_DRIVEN_MAX_LEVEL_CAP, warnings::add);

        assertEquals(OptionalInt.of(255), policy.resolveActiveOverride("minecraft:sharpness"));
        assertFalse(policy.resolveActiveOverride("minecraft:mending").isPresent()); // clamped 0 -> 1 equals vanilla 1
        assertEquals(2, warnings.size());
    }

    @Test
    void armorFixProbabilityFollowsExactHalfLinearFormula() {
        // Level 1: 1 / (1 + 2) = 1/3 (~0.3333)
        assertTrue(EnchantmentLimitPolicy.shouldPreventArmorDamage(1, 0.30));
        assertFalse(EnchantmentLimitPolicy.shouldPreventArmorDamage(1, 0.34));
        // Level 3: 3 / (3 + 2) = 0.60
        assertTrue(EnchantmentLimitPolicy.shouldPreventArmorDamage(3, 0.59));
        assertFalse(EnchantmentLimitPolicy.shouldPreventArmorDamage(3, 0.61));
        assertFalse(EnchantmentLimitPolicy.shouldPreventArmorDamage(0, 0.0));
    }
}
