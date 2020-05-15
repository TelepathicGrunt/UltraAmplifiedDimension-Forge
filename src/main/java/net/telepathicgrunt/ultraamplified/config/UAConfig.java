package net.telepathicgrunt.ultraamplified.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.utils.ConfigHelper;
import net.telepathicgrunt.ultraamplified.utils.ConfigHelper.ConfigValueListener;


@Mod.EventBusSubscriber
public class UAConfig
{
	/*
	 * Config to control all sorts of settings used for world generation with this mod. This ranges from ore rarity, what
	 * biomes spawn, structure spawning, and more.
	 */
	public static class UAConfigValues
	{

		public ConfigValueListener<Boolean> importModdedFeatures;
		public ConfigValueListener<Boolean> importModdedStructure;
		public ConfigValueListener<Boolean> importModdedMobs;
		public ConfigValueListener<Boolean> importModdedBiomes;
		public ConfigValueListener<Boolean> importAllModdedBiomes;
		public ConfigValueListener<String> blacklistedBiomeList;
		public ConfigValueListener<String> blacklistedStructureList;
		
		public ConfigValueListener<Double> xzTerrainModifier;
		public ConfigValueListener<Double> xzScaleModifier;
		public ConfigValueListener<Double> yTerrainModifier;
		public ConfigValueListener<Double> yScaleModifier;
		public ConfigValueListener<Integer> yMaximum;
		public ConfigValueListener<Boolean> secretSetting;
		
		public ConfigValueListener<Boolean> bedExplodes;
		public ConfigValueListener<Boolean> heavyFog;
		public ConfigValueListener<Boolean> forceExitToOverworld;
		public ConfigValueListener<Boolean> allowNetherPortal;
		public ConfigValueListener<String> portalActivationItem;
		public ConfigValueListener<String> portalCornerBlocks;
		public ConfigValueListener<String> portalCeilingBlocks;
		public ConfigValueListener<String> portalFloorBlocks;

		public ConfigValueListener<Integer> coalOreSpawnrate;
		public ConfigValueListener<Integer> ironOreSpawnrate;
		public ConfigValueListener<Integer> redstoneOreSpawnrate;
		public ConfigValueListener<Integer> lapisOreSpawnrate;
		public ConfigValueListener<Integer> diamondOreSpawnrate;
		public ConfigValueListener<Integer> goldOreSpawnrate;
		public ConfigValueListener<Integer> emeraldOreSpawnrate;
		public ConfigValueListener<Integer> silverfishSpawnrate;
		public ConfigValueListener<Integer> quartzOreSpawnrate;
		public ConfigValueListener<Integer> glowstoneSpawnrate;
		public ConfigValueListener<Integer> magmaSpawnrate;
		public ConfigValueListener<Integer> lavaSpawnrate;
		public ConfigValueListener<Integer> glowstoneVariantsSpawnrate;
		public ConfigValueListener<Boolean> rootGen;
		
		public ConfigValueListener<Integer> dungeonSpawnrate;
		public ConfigValueListener<Integer> ravineSpawnrate;
		public ConfigValueListener<Integer> caveCavitySpawnrate;
		public ConfigValueListener<Integer> oceanCaveSpawnrate;
		public ConfigValueListener<Boolean> pillarGen;
		public ConfigValueListener<Boolean> honeyLakeGen;
		public ConfigValueListener<Boolean> slimeLakeGen;
		public ConfigValueListener<Boolean> waterLakeGen;
		public ConfigValueListener<Boolean> lavaLakeGen;
		public ConfigValueListener<Integer> waterfallSpawnrate;
		public ConfigValueListener<Integer> lavafallSpawnrate;
		public ConfigValueListener<Integer> endIslandSpawnrate;
		public ConfigValueListener<Boolean> chestGeneration;
		public ConfigValueListener<Integer> sunShrineSpawnrate;
		public ConfigValueListener<Integer> stonehengeSpawnrate;
		public ConfigValueListener<Integer> hangingRuinsSpawnrate;
		public ConfigValueListener<Boolean> miniStructureGeneration;
		
		public ConfigValueListener<Integer> villageSpawnrate;
		public ConfigValueListener<Integer> villageZombieSpawnrate;
		public ConfigValueListener<Integer> mineshaftSpawnrate;
		public ConfigValueListener<Boolean> mineshaftAbovegroundAllowed;
		public ConfigValueListener<Boolean> mineshaftUndergroundAllowed;
		public ConfigValueListener<Integer> mansionSpawnrate;
		public ConfigValueListener<Integer> desertTempleSpawnrate;
		public ConfigValueListener<Integer> jungleTempleSpawnrate;
		public ConfigValueListener<Integer> iglooSpawnrate;
		public ConfigValueListener<Integer> mushroomTempleSpawnrate;
		public ConfigValueListener<Integer> iceSpikeTempleSpawnrate;
		public ConfigValueListener<Integer> witchHutSpawnrate;
		public ConfigValueListener<Integer> oceanMonumentSpawnrate;
		public ConfigValueListener<Integer> oceanRuinsSpawnrate;
		public ConfigValueListener<Integer> shipwreckSpawnrate;
		public ConfigValueListener<Integer> strongholdSpawnrate;
		public ConfigValueListener<Double> silverfishStrongholdSpawnrate;
		public ConfigValueListener<Boolean> allowSilverfishSpawnerStronghold;
		public ConfigValueListener<Double> strongholdSizeSH;
		public ConfigValueListener<Integer> netherFortressSpawnrate;
		public ConfigValueListener<Integer> stoneFortressSpawnrate;
		public ConfigValueListener<Boolean> allowSilverfishSpawnerFortress;
		public ConfigValueListener<Boolean> allowNaturalSilverfishFortress;
		public ConfigValueListener<Integer> endCitySpawnrate;
		public ConfigValueListener<Integer> pillageOutpostRarity;
		
		public ConfigValueListener<Integer> biomeSize;
		public ConfigValueListener<Integer> mutatedBiomeSpawnrate;
		public ConfigValueListener<Integer> seaLevel;
		public ConfigValueListener<Boolean> lavaOcean;
		public ConfigValueListener<Boolean> plains;
		public ConfigValueListener<Boolean> desert;
		public ConfigValueListener<Boolean> forest;
		public ConfigValueListener<Boolean> taiga;
		public ConfigValueListener<Boolean> rockyField;
		public ConfigValueListener<Boolean> swamplands;
		public ConfigValueListener<Boolean> netherland;
		public ConfigValueListener<Boolean> endField;
		public ConfigValueListener<Boolean> snowyTundra;
		public ConfigValueListener<Boolean> icedTerrain;
		public ConfigValueListener<Boolean> mushroom;
		public ConfigValueListener<Boolean> stonePlains;
		public ConfigValueListener<Boolean> bambooJungle;
		public ConfigValueListener<Boolean> jungle;
		public ConfigValueListener<Boolean> frozenDesert;
		public ConfigValueListener<Boolean> birchForest;
		public ConfigValueListener<Boolean> darkForest;
		public ConfigValueListener<Boolean> snowyTaiga;
		public ConfigValueListener<Boolean> giantTreeTaiga;
		public ConfigValueListener<Boolean> savanna;
		public ConfigValueListener<Boolean> badlands;
		public ConfigValueListener<Boolean> spikyBadlands;
		public ConfigValueListener<Boolean> iceSpike;
		public ConfigValueListener<Boolean> frozenOcean;
		public ConfigValueListener<Boolean> coldOcean;
		public ConfigValueListener<Boolean> ocean;
		public ConfigValueListener<Boolean> lukewarmOcean;
		public ConfigValueListener<Boolean> warmOcean;


		public UAConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
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
	
				importModdedBiomes = subscriber.subscribe(builder
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

			builder.push("General Dimension Options");
			
				heavyFog = subscriber.subscribe(builder
		                .comment("\r\n Adds very heavy fog to make the world look more spoky and limit visibility.\n" 
		                		+" This is not the same as distance fog which does not make chunks near you foggy.")
		                .translation("ultraamplified.config.dimension.heavyfog")
		                .define("heavyFog", false));
	
				bedExplodes = subscriber.subscribe(builder
		                .comment("\r\n Should beds explode in the Ultra Amplified Dimension?")
		                .translation("ultraamplified.config.dimension.bedexplodes")
		                .define("bedExplodes", false));
				
				allowNetherPortal = subscriber.subscribe(builder
		                .comment("\r\n Lets Nether Portals be able to be created in Ultra Amplified DImension.\n" 
		                		+" Using the portal in this dimension will take you to the Nether but Nether \n"
		                		+" Portals in the Nether will take you to the Overworld instead. So this option \n"
		                		+" is good if you want a second way of escaping the Ultra Amplified Dimension.")
		                .translation("ultraamplified.config.dimension.allownetherportal")
		                .define("allowNetherPortal", false));
	
				forceExitToOverworld = subscriber.subscribe(builder
		                .comment("\r\n Should beds explode in the Ultra Amplified Dimension?" 
		                		+ " Makes leaving the Ultra Amplified dimension always places you back\r\n" 
		                		+ " in the Overworld regardless of which dimension you originally \r\n" 
		                		+ " came from. Use this option if this dimension becomes locked in  \r\n" 
		                		+ " with another dimension so you are stuck teleporting between the \r\n" 
		                		+ " two and cannot get back to the Overworld\r\n")
						.translation("ultraamplified.config.dimension.forceexittooverworld")
		                .define("forceExitToOverworld", false));
	
				portalActivationItem = subscriber.subscribe(builder
		                .comment("\r\n What item the player needs to hold and right click on the\r\n" 
		                		+" Polished Diorite to create the portal?\r\n" 
		                		+" The default entry is minecraft:flint_and_steel\r\n"
		                		+" Note: set this to \"\" to disable portal creation completely.")
		                .translation("ultraamplified.config.dimension.portalactivationitem")
		                .define("portalActivationItem", "minecraft:flint_and_steel"));
	
				portalCornerBlocks = subscriber.subscribe(builder
		                .comment("\r\n What corner blocks should we use to make a portal frame instead of Polish Granite?\r\n " 
		                		+ "The default entry is minecraft:polish_granite")
		                .translation("ultraamplified.config.dimension.portalcornerblocks")
		                .define("portalCornerBlocks", "minecraft:polished_granite"));
	
				portalCeilingBlocks = subscriber.subscribe(builder
		                .comment("\r\n What ceiling blocks should we use to make a portal frame instead of Polish Andesite Slab?\r\n " 
		                		+ "The default entry is minecraft:polish_andesite")
		                .translation("ultraamplified.config.dimension.portalceilingblocks")
		                .define("portalCeilingBlocks", "minecraft:polished_andesite_slab"));
	
				portalFloorBlocks = subscriber.subscribe(builder
		                .comment("\r\n What floor blocks should we use to make a portal frame instead of Polish Andesite Slab?\r\n " 
		                		+ "The default entry is minecraft:polish_andesite")
		                .translation("ultraamplified.config.dimension.portalfloorblocks")
		                .define("portalFloorBlocks", "minecraft:polished_andesite_slab"));
				
			builder.pop();

			builder.push("Terrain Options");
	
				secretSetting = subscriber.subscribe(builder
		                .comment("\r\n Does something neat! Give it a try lol\n " 
		                		+ "You might want to use this on a fresh new world...\n " 
		                		+ "Changes the terrain's look!")
		                .translation("ultraamplified.config.terrain.secretsettings")
		                .define("secretSettings", false));
	
				yMaximum = subscriber.subscribe(builder
		                .comment("\r\n Maxium height the terrain can generate up to."
		                	+"\r\n Default is 245.")
		                .translation("ultraamplified.config.terrain.ymaximum")
		                .defineInRange("yMaximum", 245, 100, 2147483646));
	
				xzTerrainModifier = subscriber.subscribe(builder
		                .comment("\r\n Changes the xz terrain modifier.\n " 
		                		+ "I believe lower numbers will make the layers longer in the xz plane.\n " 
		                		+ "Default value is 684.412D")
		                .translation("ultraamplified.config.terrain.xzterrainmodifier")
		                .defineInRange("xzTerrainModifier", 684.412D, 1D, 10000000D));
	
				xzScaleModifier = subscriber.subscribe(builder
		                .comment("\r\n Changes the xz terrain scale.\n " 
		                		+ "Not exactly sure what this does.\n " 
		                		+ "Default value is 8.55515F")
		                .translation("ultraamplified.config.terrain.xzscalemodifier")
		                .defineInRange("xzScaleModifier", 8.55515F, 1D, 10000000D));
	
				yTerrainModifier = subscriber.subscribe(builder
		                .comment("\r\n Changes the y terrain modifier.\n " 
		                		+ "I believe lower numbers will make less layers and thicken layers that do spawn.\n " 
		                		+ "Default value is 68419.786D")
		                .translation("ultraamplified.config.terrain.yterrainmodifier")
		                .defineInRange("yTerrainModifier", 68419.786D, 1D, 10000000D));
	
				yScaleModifier = subscriber.subscribe(builder
		                .comment("\r\n Changes the y terrain scale.\n " 
		                		+ "Not exactly sure what this does.\n " 
		                		+ "Default value is 428.613D")
		                .translation("ultraamplified.config.terrain.yscalemodifier")
		                .defineInRange("yScaleModifier", 428.613D, 1D, 10000000D));
	
				seaLevel = subscriber.subscribe(builder
		                .comment("\r\n Sea Level. Default is 75.")
		                .translation("ultraamplified.config.terrain.sealevel")
		                .defineInRange("seaLevel", 75, 0, 250));
	
				lavaOcean = subscriber.subscribe(builder
		                .comment("\r\n Replace the water at sea level with lava instead. DO NOT CHANGE THIS OPTION IN AN ALREADY GENERATED DIMENSION. IT WILL CRASH DUE TO LAVA OCEAN UPDATING NEXT TO OCEAN WATER!!")
		                .translation("ultraamplified.config.terrain.lavaocean")
		                .define("lavaOcean", false));
	
				endIslandSpawnrate = subscriber.subscribe(builder
		                .comment("\r\n How often End Islands will spawn in the End Biome." 
		                		+ "\n " 
		                		+ "0 for no End Islands and 100 for max spawnrate.")
		                .translation("ultraamplified.config.terrain.endislandspawnrate")
		                .defineInRange("endIslandSpawnrate", 6, 0, 100));

			builder.pop();

			builder.push("Structure/Feature Options");
	
				builder.push("Feature Options");
			
					dungeonSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Dungeons will spawn.\r\n " 
			                		+ "0 for no Dungeons and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.feature.dungeonspawnrate")
			                .defineInRange("dungeonSpawnrate", 30, 0, 1000));
		
					ravineSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Ravines will spawn.\r\n " 
			                		+ "0 for no Ravines and 100 for max spawnrate.")
			                .translation("ultraamplified.config.feature.ravinespawnrate")
			                .defineInRange("ravineSpawnrate", 25, 0, 100));
		
					caveCavitySpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Cave Cavity will spawn.\r\n " 
			                		+ "0 for no Cave Cavity and 22 for max spawnrate.")
			                .translation("ultraamplified.config.feature.cavecavityspawnrate")
			                .defineInRange("caveCavitySpawnrate", 5, 0, 22));
		
					oceanCaveSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Underwater Caves will spawn in ocean biomes.\r\n " 
			                		+ "0 for no Underwater Caves and 100 for max spawnrate.")
			                .translation("ultraamplified.config.feature.oceancavespawnrate")
			                .defineInRange("oceanCaveSpawnrate", 20, 0, 100));
		
					pillarGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether pillars features (both ramp and straight kind) spawn or not.")
			                .translation("ultraamplified.config.feature.pillargen")
			                .define("pillarGen", true));
		
					honeyLakeGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether Honey Lakes spawn or not.")
			                .translation("ultraamplified.config.feature.honeylakegen")
			                .define("honeyLakeGen", true));
		
					slimeLakeGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether Slime Lakes spawn or not.")
			                .translation("ultraamplified.config.feature.slimelakegen")
			                .define("slimeLakeGen", true));
		
					waterLakeGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether Water Lakes spawn or not.")
			                .translation("ultraamplified.config.feature.waterlakegen")
			                .define("waterLakeGen", true));
		
					lavaLakeGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether Lava Lakes spawn or not.")
			                .translation("ultraamplified.config.feature.lavalakegen")
			                .define("lavaLakeGen", true));
		
					glowstoneVariantsSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n Controls how often patches of Glowdirt and other modded Glowstone variants spawn.\r\n " 
			                		+ "0 for no patches and 1000 for max amount of patches.")
			                .translation("ultraamplified.config.feature.glowstonevariantsspawnrate")
			                .defineInRange("glowstoneVariantsSpawnrate", 50, 0, 1000));
		
					rootGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether roots and short vines spawn or not on the underside of the floating land.")
			                .translation("ultraamplified.config.feature.rootgen")
			                .define("rootGen", true));
		
					waterfallSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often waterfalls will spawn." 
			                		+ "\n " 
			                		+ "0 for no waterfalls and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.terrain.waterfallspawnrate")
			                .defineInRange("waterfallSpawnrate", 35, 0, 1000));
		
					lavafallSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often lavafalls will spawn." 
			                		+ "\n " 
			                		+ "0 for no lavafalls and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.terrain.lavafallspawnrate")
			                .defineInRange("lavafallSpawnrate", 14, 0, 1000));
	
				builder.pop();
	
				builder.push("Mini-structure Options");
		
					miniStructureGeneration = subscriber.subscribe(builder
			                .comment("\r\n Controls whether Desert Wells, Hay Piles, Sun Shrines, Stonehenges, and Crosses spawn or not.")
			                .translation("ultraamplified.config.structure.ministructuregeneration")
			                .define("miniStructureGeneration", true));
		
					sunShrineSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How rare are Sun Shrines.\r\n " 
			                		+ "1 for Sun Shrines spawning in most chunks and 1000 for very rare spawn.\r\n " 
			                		+ "Spawns mainly in relic variant of biomes.")
			                .translation("ultraamplified.config.structure.sunshrinespawnrate")
			                .defineInRange("sunShrineSpawnrate", 130, 1, 100));
		
					stonehengeSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How rare are Stonehenges.\r\n " 
			                		+ "1 for Stonehenges spawning in most chunks and 1000 for very rare spawn.\r\n " 
			                		+ "Spawns mainly in relic variant of biomes.")
			                .translation("ultraamplified.config.structure.stonehengespawnrate")
			                .defineInRange("stonehengeSpawnrate", 15, 1, 100));
		
					hangingRuinsSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How rare are Hanging Ruins.\r\n " 
			                		+ "1000 for Hanging Ruins spawning in most chunks and 0 for no spawn.\r\n " 
			                		+ "NOTE: this is backwards than other spawnrates.\r\n " 
			                		+ "This is because other structures need have finer control on chances less than 1%.\r\n " 
			                		+ "Spawns in most biomes except for oceans, Netherland, and Iced Terrain Biomes and more often in Rocky Fields variants and Stone Fields biomes.")
							.translation("ultraamplified.config.structure.hangingruinsspawnrate")
			                .defineInRange("hangingRuinsSpawnrate", 60, 0, 1000));
	
				builder.pop();
	
				builder.push("Structure Options");
		
					chestGeneration = subscriber.subscribe(builder
			                .comment("\r\n Controls whether loot chests spawn or not in all structures.")
			                .translation("ultraamplified.config.structure.chestgeneration")
			                .define("chestGeneration", true));
		
					builder.push("Villages");
			
						villageSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Villages.\r\n " 
				                		+ "1 for Village spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.villagespawnrate")
				                .defineInRange("villageSpawnrate", 16, 1, 101));
			
						villageZombieSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n What percentage of Classic Styled Villages are Zombie Villages.\r\n " 
				                		+ "0 for no Zombie Village spawning and 100 for all Villages being zombified.")
				                .translation("ultraamplified.config.structure.villagezombiespawnrate")
				                .defineInRange("villageZombieSpawnrate", 10, 0, 100));
		
					builder.pop();
		
					builder.push("Mineshaft");
			
						mineshaftSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How often Mineshafts will spawn.\r\n " 
				                		+ "0 for no Mineshafts and 1000 for max spawnrate.")
				                .translation("ultraamplified.config.structure.mineshaftspawnrate")
				                .defineInRange("mineshaftSpawnrate", 22, 0, 1000));
			
						mineshaftAbovegroundAllowed = subscriber.subscribe(builder
				                .comment("\r\n Can aboveground floating Mineshafts spawn?")
				                .translation("ultraamplified.config.structure.mineshaftabovegroundallowed")
				                .define("mineshaftAbovegroundAllowed", true));
			
						mineshaftUndergroundAllowed = subscriber.subscribe(builder
				                .comment("\r\n Can undergound giant pit Mineshafts spawn?")
				                .translation("ultraamplified.config.structure.mineshaftundergroundallowed")
				                .define("mineshaftUndergroundAllowed", true));
		
					builder.pop();
		
					builder.push("Woodland Mansion");
			
						mansionSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Woodland Mansion." 
				                		+ "\n " 
				                		+ "1 for Woodland Mansion spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.mansionspawnrate")
				                .defineInRange("mansionSpawnrate", 18, 1, 101));
		
					builder.pop();
		
					builder.push("Desert Temple");
			
						desertTempleSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Desert Temples." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.deserttemplespawnrate")
				                .defineInRange("desertTempleSpawnrate", 20, 1, 101));
		
					builder.pop();
		
					builder.push("Jungle Temple");
			
						jungleTempleSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Jungle Temples." 
				                		+ "\n " 
				                		+ "1 for a spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.jungletemplespawnrate")
				                .defineInRange("jungleTempleSpawnrate", 20, 1, 101));
		
					builder.pop();
		
					builder.push("Igloo");
			
						iglooSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are igloos." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.igloospawnrate")
				                .defineInRange("iglooSpawnrate", 14, 1, 101));
		
					builder.pop();
		
					builder.push("Mushroom Temple");
			
						mushroomTempleSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Mushroom Temples." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.mushroomtemplespawnrate")
				                .defineInRange("mushroomTempleSpawnrate", 12, 1, 101));
		
					builder.pop();
		
					builder.push("Ice Spike Temple");
				
						iceSpikeTempleSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Ice Spike Temples." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.icespiketemplespawnrate")
				                .defineInRange("iceSpikeTempleSpawnrate", 20, 1, 101));
		
					builder.pop();
		
					builder.push("Witch Hut");
			
						witchHutSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Witch Huts." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.witchhutspawnrate")
				                .defineInRange("witchHutSpawnrate", 14, 1, 101));
		
					builder.pop();
		
					builder.push("Ocean Monument");
			
						oceanMonumentSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Ocean Monuments." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.oceanmonumentspawnrate")
				                .defineInRange("oceanMonumentSpawnrate", 22, 1, 101));
		
					builder.pop();
		
					builder.push("Ocean Ruins");
			
						oceanRuinsSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Ocean Ruins." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.oceanruinsspawnrate")
				                .defineInRange("oceanRuinsSpawnrate", 7, 1, 101));
		
					builder.pop();
		
					builder.push("Shipwreck");
				
						shipwreckSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Shipwrecks." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.shipwreckspawnrate")
				                .defineInRange("shipwreckSpawnrate", 7, 1, 101));
		
					builder.pop();
		
					builder.push("Stronghold");
			
						strongholdSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Strongholds." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 501 for no spawn.")
				                .translation("ultraamplified.config.structure.strongholdspawnrate")
				                .defineInRange("strongholdSpawnrate", 62, 1, 501));
			
						silverfishStrongholdSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How often Silverfish Blocks will generate in Strongholds." 
				                		+ "\n " 
				                		+ "0 for no Silverfish Blocks and 100 for max spawnrate.")
				                .translation("ultraamplified.config.structure.silverfishstrongholdspawnrate")
				                .defineInRange("silverfishStrongholdSpawnrate", 4D, 0, 100));
			
						allowSilverfishSpawnerStronghold = subscriber.subscribe(builder
				                .comment("\r\n Can Silverfish Mob Spawners generate in Stronghold?.")
				                .translation("ultraamplified.config.structure.allowsilverfishspawnerstronghold")
				                .define("allowSilverfishSpawnerStronghold", true));
			
						strongholdSizeSH = subscriber.subscribe(builder
								.comment("\r\n How large the Stronghold is on average as a percentage." 
										+ "\r\n Note: The Stonghold is much larger by default. To get something "
										+ "\r\n closer to vanilla stronghold size, use the value of 60."
										+ "\n "
										+ "10 for supertiny Strongholds and 2000 for supermassive Strongholds.")
								.translation("repurposedstructures.config.structure.stronghold.strongholdsizesh")
								.defineInRange("strongholdSizeSH", 100D, 10, 2000));
					
					builder.pop();
		
					builder.push("Nether Fortress");
			
						netherFortressSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Nether Fortresses that spawn "
				                	+"\r\n aboveground in the Netherland biome (between y = 85 and 130)." 
				                	+ "\n " 
				                	+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.netherfortressspawnrate")
				                .defineInRange("netherFortressSpawnrate", 6, 1, 101));
			
					builder.pop();
		
					builder.push("Stone Fortress");
			
						stoneFortressSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Stone Fortresses that spawn underground (below y = 65)." 
				                	+ "\n " 
				                	+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.stonefortressspawnrate")
				                .defineInRange("stoneFortressSpawnrate", 14, 1, 101));
			
						allowSilverfishSpawnerFortress = subscriber.subscribe(builder
				                .comment("\r\n Can Silverfish Mob Spawners generate in Stone Fortresses?")
				                .translation("ultraamplified.config.structure.allowsilverfishspawnerfortress")
				                .define("allowSilverfishSpawnerFortress", true));
			
						allowNaturalSilverfishFortress = subscriber.subscribe(builder
				                .comment("\r\n Can Silverfish spawn naturally over time in Stone Fortresses?")
				                .translation("ultraamplified.config.structure.allownaturalsilverfishfortress")
				                .define("allowNaturalSilverfishFortress", true));
				
					builder.pop();
		
					builder.push("End City");
				
						endCitySpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are End Cities." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.endcityspawnrate")
				                .defineInRange("endCitySpawnrate", 22, 1, 101));
				
					builder.pop();
		
					builder.push("Outpost");
			
						pillageOutpostRarity = subscriber.subscribe(builder
				                .comment("\r\n How rare are Pillager Outposts." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.pillageoutpostrarity")
				                .defineInRange("pillageOutpostRarity", 20, 1, 101));
					
					builder.pop();
				builder.pop();
			builder.pop();

			builder.push("Biome Options");
	
				biomeSize = subscriber.subscribe(builder
		                .comment("\r\n How large the biomes are." 
		                		+ "\n " 
		                		+ "Bigger number means bigger biomes.")
		                .translation("ultraamplified.config.biome.biomesize")
		                .defineInRange("biomeSize", 3, 1, 8));
	
				mutatedBiomeSpawnrate = subscriber.subscribe(builder
		                .comment("\r\n How often the mutated form of a biome will generate" 
		                		+ "\n " 
		                		+ "0 for no mutated biomes and 10 for all biomes to be mutated.")
		                .translation("ultraamplified.config.biome.mutatedbiomespawnrate")
		                .defineInRange("mutatedBiomeSpawnrate", 2, 0, 10));
	
				builder.push("Allowed Biome Options");
	
				plains = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.plains")
		                .define("plains", true));
	
				desert = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.desert")
		                .define("desert", true));
	
				forest = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.forest")
		                .define("forest", true));
	
				taiga = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.taiga")
		                .define("taiga", true));
	
				rockyField = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.rockyfield")
		                .define("rockyField", true));
	
				swamplands = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.swamplands")
		                .define("swamplands", true));
	
				netherland = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.netherland")
		                .define("netherland", true));
	
				endField = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.end")
		                .define("end", true));
	
				snowyTundra = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.snowytundra")
		                .define("snowyTundra", true));
	
				icedTerrain = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.icedterrain")
		                .define("icedTerrain", true));
	
				mushroom = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.mushroom")
		                .define("mushroom", true));
	
				stonePlains = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.stoneplains")
		                .define("stonePlains", true));
	
				bambooJungle = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.bamboojungle")
		                .define("bambooJungle", true));
	
				jungle = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.jungle")
		                .define("jungle", true));
	
				frozenDesert = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.frozendesert")
		                .define("frozenDesert", true));
	
				birchForest = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.birchforest")
		                .define("birchForest", true));
	
				darkForest = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.darkforest")
		                .define("darkForest", true));
	
				snowyTaiga = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.snowytaiga")
		                .define("snowyTaiga", true));
	
				giantTreeTaiga = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.gianttreetaiga")
		                .define("giantTreeTaiga", true));
	
				savanna = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.savanna")
		                .define("savanna", true));
	
				badlands = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.badlands")
		                .define("badlands", true));
	
				spikyBadlands = subscriber.subscribe(builder
		                .comment("\r\n Should Spiky Badlands and Dissected Plateau Badlands be allowed to spawn?")
		                .translation("ultraamplified.config.biome.spikybadlands")
		                .define("spikyBadlands", true));
	
				iceSpike = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.icespike")
		                .define("iceSpike", true));
	
				frozenOcean = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.frozenocean")
		                .define("frozenOcean", true));
	
				coldOcean = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.coldocean")
		                .define("coldOcean", true));
	
				ocean = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.ocean")
		                .define("ocean", true));
	
				lukewarmOcean = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.lukewarmocean")
		                .define("lukewarmOcean", true));
	
				warmOcean = subscriber.subscribe(builder
		                .comment("\r\n Should this biome be allowed to spawn?")
		                .translation("ultraamplified.config.biome.warmocean")
		                .define("warmOcean", true));

			builder.pop();
			builder.pop();
	
				builder.push("Main Ores Options");

					coalOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Coal Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Coal Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.coalorespawnrate")
			                .defineInRange("coalOreSpawnrate", 35, 0, 1000));
		
					ironOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Iron Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Iron Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.ironorespawnrate")
			                .defineInRange("ironOreSpawnrate", 50, 0, 1000));
		
					redstoneOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Redstone Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Redstone Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.redstoneorespawnrate")
			                .defineInRange("redstoneOreSpawnrate", 12, 0, 1000));
		
					lapisOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Lapis Lazuli Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Lapis Lazuli Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.lapisorespawnrate")
			                .defineInRange("lapisOreSpawnrate", 2, 0, 1000));
		
					diamondOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Diamond Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Diamond Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.diamondorespawnrate")
			                .defineInRange("diamondOreSpawnrate", 1, 0, 1000));
		
					goldOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Gold Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Gold Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.goldorespawnrate")
			                .defineInRange("goldOreSpawnrate", 2, 0, 1000));

				builder.push("Rocky Fields Ores and Features Options");
		
					emeraldOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Emerald Ores will spawn in all Rocky Fields variant biomes in the form of a percentage." 
			                		+ "\n " 
			                		+ "0 for no Emerald Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.emeraldorespawnrate")
			                .defineInRange("emeraldOreSpawnrate", 100, 0, 1000));
		
					silverfishSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Silverfish Blocks will spawn in all Rocky Fields variant biomes." 
			                		+ "\n " 
			                		+ "0 for no Silverfish Blocks and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.silverfishspawnrate")
			                .defineInRange("silverfishSpawnrate", 18, 0, 1000));
		
				builder.pop();

				builder.push("Netherland Ores and Features Options");
	
					quartzOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Quartz Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Quartz Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.quartzorespawnrate")
			                .defineInRange("quartzOreSpawnrate", 14, 0, 1000));
		
					glowstoneSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Glowstone will spawn in Netherland biome." 
			                		+ "\n " 
			                		+ "0 for no Glowstone and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.glowstonespawnrate")
			                .defineInRange("glowstoneSpawnrate", 20, 0, 1000));
		
					magmaSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Magma Blocks will spawn below Y = 100 in Netherland biome." 
			                		+ "\n " 
			                		+ "0 for no Magma Blocks and 1000 for max spawnrate." 
			                		+ "\n " 
			                		+ "Note: Will not affect the flat Magma layer separating water from lava below sealevel.")
			                .translation("ultraamplified.config.ore.magmaspawnrate")
			                .defineInRange("magmaSpawnrate", 5, 0, 1000));
		
					lavaSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often single Lava Blocks will spawn in Netherland biome." 
			                		+ "\n " 
			                		+ "0 for no single Lava Blocks and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.lavaspawnrate")
			                .defineInRange("lavaSpawnrate", 70, 0, 1000));
		
				builder.pop();
			builder.pop();
		}
	}
}
