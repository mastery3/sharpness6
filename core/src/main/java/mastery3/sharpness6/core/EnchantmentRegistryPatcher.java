package mastery3.sharpness6.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.OptionalInt;

/**
 * Pure Gson transformer for data-driven {@code minecraft:enchantment} definitions (MC 1.21 through 26.3).
 */
public final class EnchantmentRegistryPatcher {

    private EnchantmentRegistryPatcher() {
    }

    /**
     * Immutable outcome describing whether an enchantment JSON definition was mutated by sharpness6.
     */
    public static final class PatchResult {
        private final boolean modified;
        private final int resultingMaxLevel;
        private final boolean unbreakingTransformed;

        public PatchResult(boolean modified, int resultingMaxLevel, boolean unbreakingTransformed) {
            this.modified = modified;
            this.resultingMaxLevel = resultingMaxLevel;
            this.unbreakingTransformed = unbreakingTransformed;
        }

        public boolean modified() {
            return modified;
        }

        public int resultingMaxLevel() {
            return resultingMaxLevel;
        }

        public boolean unbreakingTransformed() {
            return unbreakingTransformed;
        }
    }

    /**
     * Applies configured max-level overrides and (if enabled) the Unbreaking armor probability transform in place.
     */
    public static PatchResult patchEnchantmentJson(
            String enchantmentId,
            JsonElement rootElement,
            EnchantmentLimitPolicy policy) {
        if (rootElement == null || !rootElement.isJsonObject() || policy == null) {
            return new PatchResult(false, -1, false);
        }

        JsonObject json = rootElement.getAsJsonObject();
        int currentMax = readIntOrDefault(json, "max_level", -1);
        boolean modified = false;

        OptionalInt override = policy.resolveActiveOverride(enchantmentId);
        if (override.isPresent()) {
            int targetLevel = override.getAsInt();
            if (targetLevel != currentMax) {
                json.addProperty("max_level", Integer.valueOf(targetLevel));
                currentMax = targetLevel;
                modified = true;
            }
        }

        boolean unbreakingTransformed = false;
        if ("minecraft:unbreaking".equals(EnchantmentIds.canonicalize(enchantmentId))
                && policy.config().unbreakingArmorFix) {
            unbreakingTransformed = transformUnbreakingArmorDamageJson(json);
            modified = modified || unbreakingTransformed;
        }

        return new PatchResult(modified, currentMax, unbreakingTransformed);
    }

    /**
     * Updates the armor-specific {@code minecraft:item_damage} chance inside {@code unbreaking.json}
     * while preserving each MC version's predicate schema ({@code "condition"} in 1.21.x, {@code "type"} in 26.x).
     */
    static boolean transformUnbreakingArmorDamageJson(JsonObject unbreakingRoot) {
        JsonObject effects = getObject(unbreakingRoot, "effects");
        if (effects == null) {
            return false;
        }
        JsonElement itemDamageElem = effects.get("minecraft:item_damage");
        if (itemDamageElem == null || !itemDamageElem.isJsonArray()) {
            return false;
        }

        JsonArray entries = itemDamageElem.getAsJsonArray();
        boolean changed = false;
        for (int i = 0; i < entries.size(); i++) {
            JsonElement elem = entries.get(i);
            if (!elem.isJsonObject()) {
                continue;
            }
            JsonObject conditionalEffect = elem.getAsJsonObject();
            if (!isDirectArmorMatchRequirement(conditionalEffect.get("requirements"))) {
                continue;
            }
            JsonObject effect = getObject(conditionalEffect, "effect");
            if (effect == null) {
                continue;
            }
            JsonObject chance = getObject(effect, "chance");
            if (chance == null) {
                continue;
            }
            setLinear(getObject(chance, "numerator"), 0.5d, 0.5d);
            setLinear(getObject(chance, "denominator"), 1.5d, 0.5d);
            changed = true;
        }
        return changed;
    }

    private static boolean isDirectArmorMatchRequirement(JsonElement requirementsElem) {
        if (requirementsElem == null || !requirementsElem.isJsonObject()) {
            return false;
        }
        JsonObject req = requirementsElem.getAsJsonObject();
        String kind = readStringOrEmpty(req, "type");
        if (kind.isEmpty()) {
            kind = readStringOrEmpty(req, "condition");
        }
        if (!"minecraft:match_tool".equals(kind)) {
            return false;
        }
        JsonObject predicate = getObject(req, "predicate");
        if (predicate == null) {
            return false;
        }
        String items = readStringOrEmpty(predicate, "items");
        return "#minecraft:enchantable/armor".equals(items);
    }

    private static void setLinear(JsonObject linearObj, double base, double perLevelAboveFirst) {
        if (linearObj == null) {
            return;
        }
        linearObj.addProperty("type", "minecraft:linear");
        linearObj.addProperty("base", Double.valueOf(base));
        linearObj.addProperty("per_level_above_first", Double.valueOf(perLevelAboveFirst));
    }

    private static JsonObject getObject(JsonObject parent, String memberName) {
        if (parent == null || !parent.has(memberName)) {
            return null;
        }
        JsonElement child = parent.get(memberName);
        return child != null && child.isJsonObject() ? child.getAsJsonObject() : null;
    }

    private static String readStringOrEmpty(JsonObject obj, String key) {
        if (!obj.has(key)) {
            return "";
        }
        JsonElement el = obj.get(key);
        return el != null && el.isJsonPrimitive() ? el.getAsString() : "";
    }

    private static int readIntOrDefault(JsonObject obj, String key, int fallback) {
        if (!obj.has(key)) {
            return fallback;
        }
        JsonElement el = obj.get(key);
        if (el != null && el.isJsonPrimitive() && el.getAsJsonPrimitive().isNumber()) {
            return el.getAsInt();
        }
        return fallback;
    }
}
