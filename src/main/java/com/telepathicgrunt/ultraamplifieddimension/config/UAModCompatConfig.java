package com.telepathicgrunt.ultraamplifieddimension.config;

import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper;
import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class UAModCompatConfig
{
	public static class UAModCompatConfigValues
	{

		public ConfigValueListener<Boolean> importModdedFeatures;
		public ConfigValueListener<Boolean> importModdedStructure;
		public ConfigValueListener<Boolean> importModdedMobs;
		public ConfigValueListener<Boolean> importOverworldModdedBiomes;
		public ConfigValueListener<Boolean> importAllModdedBiomes;
		public ConfigValueListener<String> blacklistedBiomeList;
		public ConfigValueListener<String> blacklistedStructureList;
		
		public UAModCompatConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{

			builder.push("Mod Compatibility Options");
	
				importModdedFeatures = subscriber.subscribe(builder
		                .comment("\r\n Attempt to add modded features from vanilla biomes into Ultra Amplified version of that biome.\r\n " 
		                		+ "Only works if other mod added the feature by addFeature(...) to vanilla biome and registered the feature correctly without the 'minecraft' namespace.")
		                .translation("ultraamplified.config.compatibility.importmoddedfeatures")
		                .define("importModdedFeatures", false));
	
				importModdedStructure = subscriber.subscribe(builder
		                .comment("\r\n Attempt to add modded structures from vanilla biomes into Ultra Amplified version of that biome.\r\n " 
		                		+ "Only works if other mod added the structure by addFeature(...) to vanilla biome and registered the structure correctly without the 'minecraft' namespace.")
		                .translation("ultraamplified.config.compatibility.importmoddedstructure")
		                .define("importModdedStructure", false));
	
				importModdedMobs = subscriber.subscribe(builder
		                .comment("\r\n Attempt to add modded mobs from vanilla biomes into Ultra Amplified version of that biome.\r\n " 
		                		+ "Only works if other mod added the mob by addSpawn(...) to vanilla biome and registered the mob correctly without the 'minecraft' namespace.")
		                .translation("ultraamplified.config.compatibility.importmoddedmobs")
		                .define("importModdedMobs", false));
	
				importOverworldModdedBiomes = subscriber.subscribe(builder
		                .comment("\r\n Attempt to add modded biomes from Overworld into Ultra Amplified dimension. (or into Overworld in Ultra Amplified worldtype)\r\n " 
		                		+ "Only works if other mod added the biome to the BiomeDictionary with the BiomeType of DESERT, WARM, COOL, or ICY type.")
		                .translation("ultraamplified.config.compatibility.importmoddedbiomes")
		                .define("importModdedBiomes", false));
	
				importAllModdedBiomes = subscriber.subscribe(builder
		                .comment("\r\n Attempt to add all registered modded biomes into Ultra Amplified dimension or worldtype.\r\n " 
		                		+ "You may want to turn up biome size to 4 or 5 as this may make biomes very crowded. Also overrides importModdedBiomes setting.")
		                .translation("ultraamplified.config.compatibility.importmoddedbiomes")
		                .define("importAllModdedBiomes", false));
	
				blacklistedBiomeList = subscriber.subscribe(builder
		                .comment("\r\n Blacklist either all of a mod's biomes or its specific biomes from being imported into Ultra Amplified dimension/worldtype.\r\n " 
		                		+ "To blacklist all of a mod's biomes, type out its id like so with :* attached at end. Example: \"example_mod_id:*\"\r\n " 
		                		+ "To blacklist a specific mod's biome, type out the resourcelocation. Example: \"example_mod_id:lava_desert\"\r\n "
						+ "NOTE: Seperate each entry with a comma. Example: \"example_mod_id_1:lava_desert, example_mod_id_2:*, example_mod_id_1:ender_forest\"\r\n " 
		                		+ "Also, any entry using ultra_amplified_dimension or minecraft id will be ignored as I already handle those ids internally.")
		                .translation("ultraamplified.config.compatibility.blacklistedbiomelist")
		                .define("blacklistedBiomeList", ""));
	
				blacklistedStructureList = subscriber.subscribe(builder
		                .comment("\r\n Blacklist either all of a mod's structures or specific structures from being imported into Ultra Amplified dimension/worldtype.\r\n " 
		                		+ "To blacklist all structures in a mod, type out its id like so with :* attached at end. Example: \"example_mod_id:*\"\r\n " 
		                		+ "To blacklist a specific mod's structure, type out the resourcelocation. Example: \"example_mod_id:wizard_tower\"\r\n "
						+ "NOTE: Seperate each entry with a comma. Example: \"example_mod_id_1:wizard_tower, example_mod_id_2:*, example_mod_id_1:super_village\"\r\n " 
		                		+ "Also, any entry using ultra_amplified_dimension or minecraft id will be ignored as I already handle those ids internally.")
		                .translation("ultraamplified.config.compatibility.blacklistedfeaturelist")
		                .define("blacklistedFeatureList", ""));
	
			builder.pop();
		}
	}
}
