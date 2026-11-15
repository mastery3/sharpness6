package mastery3.sharpness6.core;

/**
 * Policy calculations for anvil repair cost uncapping across all supported Minecraft versions.
 */
public final class AnvilPolicy {

    /** Vanilla survival anvil "Too Expensive!" cutoff level. */
    public static final int VANILLA_ANVIL_CAP = 40;

    private AnvilPolicy() {
    }

    /**
     * Adjusts a computed anvil level cost when {@code bypassTooExpensive} and a finite {@code anvilMaxCost} (&lt; 40)
     * are enabled, allowing unmodified vanilla Java and Bedrock/Geyser clients to claim results without desync.
     * Always leaves stacked inputs ({@code inputStackCount > 1}) untouched so vanilla's stack safeguard stays intact.
     */
    public static int adjustAnvilCost(int computedCost, int inputStackCount, SharpnessConfig config) {
        if (config == null || !config.bypassTooExpensive || inputStackCount > 1) {
            return computedCost;
        }
        if (computedCost < VANILLA_ANVIL_CAP) {
            return computedCost;
        }
        if (config.anvilMaxCost > 0 && config.anvilMaxCost < VANILLA_ANVIL_CAP) {
            return config.anvilMaxCost;
        }
        return computedCost;
    }

    /**
     * Returns {@code true} when the vanilla {@code >= 40} anvil failure check should be bypassed for a single input item.
     */
    public static boolean shouldBypassExpensiveCheck(int inputStackCount, SharpnessConfig config) {
        return config != null && config.bypassTooExpensive && inputStackCount <= 1;
    }
}
