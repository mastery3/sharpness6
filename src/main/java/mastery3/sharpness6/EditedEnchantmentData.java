package mastery3.sharpness6;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class EditedEnchantmentData {
	public String unbreakingArmorFix;
	public EditedEnchantmentData(String unbreakingArmorFix) {
		this.unbreakingArmorFix = unbreakingArmorFix;
	}
	public static EditedEnchantmentData instance = new EditedEnchantmentData("{\n" + //
				"    \"minecraft:item_damage\": [\n" + //
				"      {\n" + //
				"        \"effect\": {\n" + //
				"          \"type\": \"minecraft:remove_binomial\",\n" + //
				"          \"chance\": {\n" + //
				"            \"type\": \"minecraft:fraction\",\n" + //
				"            \"denominator\": {\n" + //
				"              \"type\": \"minecraft:linear\",\n" + //
				"              \"base\": 1.5,\n" + //
				"              \"per_level_above_first\": 0.5\n" + //
				"            },\n" + //
				"            \"numerator\": {\n" + //
				"              \"type\": \"minecraft:linear\",\n" + //
				"              \"base\": 0.5,\n" + //
				"              \"per_level_above_first\": 0.5\n" + //
				"            }\n" + //
				"          }\n" + //
				"        },\n" + //
				"        \"requirements\": {\n" + //
				"          \"condition\": \"minecraft:match_tool\",\n" + //
				"          \"predicate\": {\n" + //
				"            \"items\": \"#minecraft:enchantable/armor\"\n" + //
				"          }\n" + //
				"        }\n" + //
				"      },\n" + //
				"      {\n" + //
				"        \"effect\": {\n" + //
				"          \"type\": \"minecraft:remove_binomial\",\n" + //
				"          \"chance\": {\n" + //
				"            \"type\": \"minecraft:fraction\",\n" + //
				"            \"denominator\": {\n" + //
				"              \"type\": \"minecraft:linear\",\n" + //
				"              \"base\": 2.0,\n" + //
				"              \"per_level_above_first\": 1.0\n" + //
				"            },\n" + //
				"            \"numerator\": {\n" + //
				"              \"type\": \"minecraft:linear\",\n" + //
				"              \"base\": 1.0,\n" + //
				"              \"per_level_above_first\": 1.0\n" + //
				"            }\n" + //
				"          }\n" + //
				"        },\n" + //
				"        \"requirements\": {\n" + //
				"          \"condition\": \"minecraft:inverted\",\n" + //
				"          \"term\": {\n" + //
				"            \"condition\": \"minecraft:match_tool\",\n" + //
				"            \"predicate\": {\n" + //
				"              \"items\": \"#minecraft:enchantable/armor\"\n" + //
				"            }\n" + //
				"          }\n" + //
				"        }\n" + //
				"      }\n" + //
				"    ]\n" + //
				"  }");
};