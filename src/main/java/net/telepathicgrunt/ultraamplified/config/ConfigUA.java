package net.telepathicgrunt.ultraamplified.config;


import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ConfigUA {
  /*
   * Config to control all sorts of settings used for world generation with this mod.
   * This ranges from ore rarity, what biomes spawn, structure spawning, and more.
   */

   //Putting this at start of option will put it at top of all options: '\0'+
   // '\u00a7'+ is used to replace § since § will be turned to gibberish when this mod is ran on Minecraft outside a development environment.

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;
    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public static boolean importModdedFeatures = false;
    public static boolean importModdedStructure = false;
    public static boolean importModdedMobs = false;
    public static boolean importModdedBiomes = false;
    public static boolean importAllModdedBiomes = false;
    public static String blacklistedBiomeList = "";
    public static String blacklistedStructureList = "";
    public static double xzTerrainModifier = 684.412D;
    public static double xzScaleModifier = 8.55515F;
    public static double yTerrainModifier = 68419.786D;
    public static double yScaleModifier = 428.613D;
    public static boolean bedExplodes = false;
    public static Boolean forceExitToOverworld;
    public static int yMaximum = 245;
    public static int dungeonSpawnrate = 30;
    public static int ravineSpawnrate = 25;
    public static int caveCavitySpawnrate = 5;
    public static int oceanCaveSpawnrate = 20;
    public static boolean pillarGen = true;
    public static boolean honeyLakeGen = true;
    public static boolean slimeLakeGen = true;
    public static boolean waterLakeGen = true;
    public static boolean lavaLakeGen = true;
    public static boolean chestGeneration = true;
    public static int sunShrineSpawnrate = 50;
    public static int stonehengeSpawnrate = 10;
    public static int hangingRuinsSpawnrate = 40;
    public static boolean miniStructureGeneration = true;
    public static int villageSpawnrate = 16;
    public static int villageZombieSpawnrate = 10;
    public static int mineshaftSpawnrate = 22;
    public static boolean mineshaftAbovegroundAllowed = true;
    public static boolean mineshaftUndergroundAllowed = true;
    public static int mansionSpawnrate = 18;
    public static int desertTempleSpawnrate = 20;
    public static int jungleTempleSpawnrate = 20;
    public static int iglooSpawnrate = 14;
    public static int witchHutSpawnrate = 14;
    public static int mushroomTempleSpawnrate = 12;
    public static int iceSpikeTempleSpawnrate = 20;
    public static int oceanMonumentSpawnrate = 22;
    public static int oceanRuinsSpawnrate = 7;
    public static int shipwreckSpawnrate = 7;
    public static int strongholdSpawnrate = 62;
    public static double silverfishStrongholdSpawnrate = 4D;
    public static boolean allowSilverfishSpawnerStronghold = true;
    public static int netherFortressSpawnrate = 14;
    public static boolean netherFortressAboveground = true;
    public static boolean netherFortressUnderground = true;
    public static boolean allowSilverfishSpawnerFortress = true;
    public static boolean allowNaturalSilverfishFortress = true;
    public static int endCitySpawnrate = 18;
    public static int pillageOutpostRarity = 20;
    public static boolean secretSetting = false;
    public static boolean heavyFog = false;
    public static String portalCornerBlocks = "";
    public static String portalCeilingBlocks = "";
    public static String portalFloorBlocks = "";
    public static int biomeSize = 3;
    public static int mutatedBiomeSpawnrate = 2;
    public static int seaLevel = 75;
    public static boolean lavaOcean = false;
    public static int waterfallSpawnrate = 35;
    public static int lavafallSpawnrate = 14;
    public static int endIslandSpawnrate = 6;
    public static boolean plains = true;
    public static boolean desert = true;
    public static boolean forest = true;
    public static boolean taiga = true;
    public static boolean rockyField = true;
    public static boolean swamplands = true;
    public static boolean nether = true;
    public static boolean end = true;
    public static boolean snowyTundra = true;
    public static boolean icedTerrain = true;
    public static boolean mushroom = true;
    public static boolean stonePlains = true;
    public static boolean bambooJungle = true;
    public static boolean jungle = true;
    public static boolean frozenDesert = true;
    public static boolean birchForest = true;
    public static boolean darkForest = true;
    public static boolean snowyTaiga = true;
    public static boolean giantTreeTaiga = true;
    public static boolean savanna = true;
    public static boolean badlands = true;
    public static boolean spikyBadlands = true;
    public static boolean iceSpike = true;
    public static boolean frozenOcean = true;
    public static boolean coldOcean = true;
    public static boolean ocean = true;
    public static boolean lukewarmOcean = true;
    public static boolean warmOcean = true;
    public static int coalOreSpawnrate = 35;
    public static int ironOreSpawnrate = 50;
    public static int redstoneOreSpawnrate = 12;
    public static int lapisOreSpawnrate = 2;
    public static int diamondOreSpawnrate = 1;
    public static int goldOreSpawnrate = 2;
    public static int emeraldOreSpawnrate = 100;
    public static int silverfishSpawnrate = 18;
    public static int quartzOreSpawnrate = 14;
    public static int glowstoneSpawnrate = 20;
    public static int magmaSpawnrate = 5;
    public static int lavaSpawnrate = 70;
    public static int glowstoneVariantsSpawnrate = 50;
    public static boolean rootGen = true;

    
    public static class ServerConfig
    {
    					
	    public final BooleanValue importModdedFeatures;
	    public final BooleanValue importModdedStructure;
	    public final BooleanValue importModdedMobs;
	    public final BooleanValue importModdedBiomes;
	    public final BooleanValue importAllModdedBiomes;
	    public final ConfigValue<String> blacklistedBiomeList;
	    public final ConfigValue<String> blacklistedStructureList;
        public final DoubleValue xzTerrainModifier;
        public final DoubleValue xzScaleModifier;
        public final DoubleValue yTerrainModifier;
        public final DoubleValue yScaleModifier;
	    public final BooleanValue bedExplodes;
	    public final BooleanValue forceExitToOverworld;
        public final IntValue yMaximum;
        public final IntValue dungeonSpawnrate;
        public final IntValue ravineSpawnrate;
        public final IntValue caveCavitySpawnrate;
        public final IntValue oceanCaveSpawnrate;
        public final BooleanValue pillarGen;
        public final BooleanValue honeyLakeGen;
        public final BooleanValue slimeLakeGen;
        public final BooleanValue waterLakeGen;
        public final BooleanValue lavaLakeGen;
		public final BooleanValue chestGeneration;
		public final IntValue sunShrineSpawnrate;
		public final IntValue stonehengeSpawnrate;
		public final IntValue hangingRuinsSpawnrate;
		public final BooleanValue miniStructureGeneration;
		public final IntValue villageSpawnrate;
		public final IntValue villageZombieSpawnrate;
		public final IntValue mineshaftSpawnrate;
		public final BooleanValue mineshaftAbovegroundAllowed;
		public final BooleanValue mineshaftUndergroundAllowed;
		public final IntValue mansionSpawnrate;
		public final IntValue desertTempleSpawnrate;
		public final IntValue jungleTempleSpawnrate;
		public final IntValue iglooSpawnrate;
		public final IntValue mushroomTempleSpawnrate;
		public final IntValue iceSpikeTempleSpawnrate;
		public final IntValue witchHutSpawnrate;
		public final IntValue oceanMonumentSpawnrate;
		public final IntValue oceanRuinsSpawnrate;
		public final IntValue shipwreckSpawnrate;
		public final IntValue strongholdSpawnrate;
		public final DoubleValue silverfishStrongholdSpawnrate;
		public final BooleanValue allowSilverfishSpawnerStronghold;
		public final IntValue netherFortressSpawnrate;
		public final BooleanValue netherFortressAboveground;
		public final BooleanValue netherFortressUnderground;
		public final BooleanValue allowSilverfishSpawnerFortress;
		public final BooleanValue allowNaturalSilverfishFortress;
		public final IntValue endCitySpawnrate;
		public final IntValue pillageOutpostRarity;
		public final BooleanValue secretSetting;
		public final BooleanValue heavyFog;
	    public final ConfigValue<String> portalCornerBlocks;
	    public final ConfigValue<String> portalCeilingBlocks;
	    public final ConfigValue<String> portalFloorBlocks;
		public final IntValue biomeSize;
		public final IntValue mutatedBiomeSpawnrate;
		public final IntValue seaLevel;
		public final BooleanValue lavaOcean;
		public final IntValue waterfallSpawnrate;
		public final IntValue lavafallSpawnrate;
		public final IntValue endIslandSpawnrate;
		public final BooleanValue plains;
		public final BooleanValue desert;
		public final BooleanValue forest;
		public final BooleanValue taiga;
		public final BooleanValue rockyField;
		public final BooleanValue swamplands;
		public final BooleanValue netherland;
		public final BooleanValue endField;
		public final BooleanValue snowyTundra;
		public final BooleanValue icedTerrain;
		public final BooleanValue mushroom;
		public final BooleanValue stonePlains;
		public final BooleanValue bambooJungle;
		public final BooleanValue jungle;
		public final BooleanValue frozenDesert;
		public final BooleanValue birchForest;
		public final BooleanValue darkForest;
		public final BooleanValue snowyTaiga;
		public final BooleanValue giantTreeTaiga;
		public final BooleanValue savanna;
		public final BooleanValue badlands;
		public final BooleanValue spikyBadlands;
		public final BooleanValue iceSpike;
		public final BooleanValue frozenOcean;
		public final BooleanValue coldOcean;
		public final BooleanValue ocean;
		public final BooleanValue lukewarmOcean;
		public final BooleanValue warmOcean;
		public final IntValue coalOreSpawnrate;
		public final IntValue ironOreSpawnrate;
		public final IntValue redstoneOreSpawnrate;
		public final IntValue lapisOreSpawnrate;
		public final IntValue diamondOreSpawnrate;
		public final IntValue goldOreSpawnrate;
		public final IntValue emeraldOreSpawnrate;
		public final IntValue silverfishSpawnrate;
		public final IntValue quartzOreSpawnrate;
		public final IntValue glowstoneSpawnrate;
		public final IntValue magmaSpawnrate;
		public final IntValue lavaSpawnrate;
		public final IntValue glowstoneVariantsSpawnrate;
		public final BooleanValue rootGen;

        ServerConfig(ForgeConfigSpec.Builder builder) 
        {

            builder.push("Mod Compatibility Options");
            
            		importModdedFeatures = builder
                    .comment("\r\n Attempt to add modded features from vanilla biomes into Ultra Amplified version of that biome.\r\n "
                    		+"Only works if other mod added the feature by addFeature(...) to vanilla biome and registered the feature correctly without the 'minecraft' namespace.")
                    .translation("ultraamplified.config.compatibility.importmoddedfeatures")
                    .define("importModdedFeatures", false);
            		
            		importModdedStructure = builder
                    .comment("\r\n Attempt to add modded structures from vanilla biomes into Ultra Amplified version of that biome.\r\n "
                    		+"Only works if other mod added the structure by addFeature(...) to vanilla biome and registered the structure correctly without the 'minecraft' namespace.")
                    .translation("ultraamplified.config.compatibility.importmoddedstructure")
                    .define("importModdedStructure", false);
            		
            		importModdedMobs = builder
                    .comment("\r\n Attempt to add modded mobs from vanilla biomes into Ultra Amplified version of that biome.\r\n "
                    		+"Only works if other mod added the mob by addSpawn(...) to vanilla biome and registered the mob correctly without the 'minecraft' namespace.")
                    .translation("ultraamplified.config.compatibility.importmoddedmobs")
                    .define("importModdedMobs", false);
            		
            		importModdedBiomes = builder
                    .comment("\r\n Attempt to add modded biomes from Overworld into Ultra Amplified dimension. (or into Overworld in Ultra Amplified worldtype)\r\n "
                    		+"Only works if other mod added the biome to the BiomeDictionary with the BiomeType of DESERT, WARM, COOL, or ICY type.")
                    .translation("ultraamplified.config.compatibility.importmoddedbiomes")
                    .define("importModdedBiomes", false);

            		importAllModdedBiomes = builder
                    .comment("\r\n Attempt to add all registered modded biomes into Ultra Amplified dimension or worldtype.\r\n "
                    		+"You may want to turn up biome size to 4 or 5 as this may make biomes very crowded. Also overrides importModdedBiomes setting.")
                    .translation("ultraamplified.config.compatibility.importmoddedbiomes")
                    .define("importAllModdedBiomes", false);
            		
            		blacklistedBiomeList = builder
                    .comment("\r\n Blacklist either all of a mod's biomes or its specific biomes from being imported into Ultra Amplified dimension/worldtype.\r\n "
                    		+"To blacklist all of a mod's biomes, type out its id like so with :* attached at end. Example: \"example_mod_id:*\"\r\n "
                    		+"To blacklist a specific mod's biome, type out the resourcelocation. Example: \"example_mod_id:lava_desert\"\r\n "
                    		+"NOTE: Seperate each entry with a comma. Example: \"example_mod_id_1:lava_desert, example_mod_id_2:*, example_mod_id_1:ender_forest\"\r\n "
                    		+"Also, any entry using ultra_amplified_dimension or minecraft id will be ignored as I already handle those ids internally.")
                    .translation("ultraamplified.config.compatibility.blacklistedbiomelist")
                    .define("blacklistedBiomeList", "");

            		
            		blacklistedStructureList = builder
                    .comment("\r\n Blacklist either all of a mod's structures or specific structures from being imported into Ultra Amplified dimension/worldtype.\r\n "
                    		+"To blacklist all structures in a mod, type out its id like so with :* attached at end. Example: \"example_mod_id:*\"\r\n "
                    		+"To blacklist a specific mod's structure, type out the resourcelocation. Example: \"example_mod_id:wizard_tower\"\r\n "
                    		+"NOTE: Seperate each entry with a comma. Example: \"example_mod_id_1:wizard_tower, example_mod_id_2:*, example_mod_id_1:super_village\"\r\n "
                    		+"Also, any entry using ultra_amplified_dimension or minecraft id will be ignored as I already handle those ids internally.")
                    .translation("ultraamplified.config.compatibility.blacklistedfeaturelist")
                    .define("blacklistedFeatureList", "");

            builder.pop();

            builder.push("General Dimension Options");

	  				heavyFog = builder
            		.comment("\r\n Adds very heavy fog to make the world look more spoky and limit visibility.\n "+
            				"This is not the same as distance fog which does not make chunks near you foggy.")
            		.translation("ultraamplified.config.dimension.heavyfog")
            		.define("heavyFog", false);

            		bedExplodes = builder
                    .comment("\r\n Should beds explode in the Ultra Amplified Dimension?")
                    .translation("ultraamplified.config.dimension.bedexplodes")
                    .define("bedExplodes", false);

            		forceExitToOverworld = builder
                    .comment("\r\n Should beds explode in the Ultra Amplified Dimension?"
                    		+" Makes leaving the Ultra Amplified dimension always places you back\r\n "
                    		+" in the Overworld regardless of which dimension you originally \r\n"
                    		+" came from. Use this option if this dimension becomes locked in  \r\n"
                    		+" with another dimension so you are stuck teleporting between the \r\n"
                    		+" two and cannot get back to the Overworld\r\n")
                    .translation("ultraamplified.config.dimension.forceexittooverworld")
                    .define("forceExitToOverworld", false);

            		portalCornerBlocks = builder
                    .comment("\r\n What corner blocks should we use to make a portal frame instead of Polish Granite?\r\n "
                    		+ "Example entry: minecraft:polish_granite")
                    .translation("ultraamplified.config.dimension.portalcornerblocks")
                    .define("portalCornerBlocks", "");

            		portalCeilingBlocks = builder
                    .comment("\r\n What ceiling blocks should we use to make a portal frame instead of Polish Andesite Slab?\r\n "
                    		+ "Example entry: minecraft:polish_andesite")
                    .translation("ultraamplified.config.dimension.portalceilingblocks")
                    .define("portalCeilingBlocks", "");
            		
            		portalFloorBlocks = builder
                    .comment("\r\n What floor blocks should we use to make a portal frame instead of Polish Andesite Slab?\r\n "
                    		+ "Example entry: minecraft:polish_andesite")
                    .translation("ultraamplified.config.dimension.portalfloorblocks")
                    .define("portalFloorBlocks", "");
            builder.pop();
            
            builder.push("Terrain Options");

            
					secretSetting = builder
		    		.comment("\r\n Does something neat! Give it a try lol\n " +
		    		  		  "You might want to use this on a fresh new world... ;)\n "+
		    		  		  "Changes the terrain's look!")
		    		.translation("ultraamplified.config.terrain.secretsettings")
		    		.define("secretSettings", false);
					

	  				yMaximum = builder
            		.comment("\r\n Maxium height the terrain can generate up to.")
            		.translation("ultraamplified.config.terrain.ymaximum")
            		.defineInRange("yMaximum", 245, 100, 256);
	  		        
	  		        
	  		        xzTerrainModifier = builder
            		.comment("\r\n Changes the xz terrain modifier.\n " +
            		  		  "I believe lower numbers will make the layers longer in the xz plane.\n "+
            		  		  "Default value is 684.412D")
            		.translation("ultraamplified.config.terrain.xzterrainmodifier")
            		.defineInRange("xzTerrainModifier", 684.412D, 1D, 10000000D);

	  		        
	  		        xzScaleModifier = builder
            		.comment("\r\n Changes the xz terrain scale.\n " +
            		  		  "Not exactly sure what this does.\n "+
            		  		  "Default value is 8.55515F")
            		.translation("ultraamplified.config.terrain.xzscalemodifier")
            		.defineInRange("xzScaleModifier", 8.55515F, 1D, 10000000D);

	  		        
	  		      	yTerrainModifier = builder
            		.comment("\r\n Changes the y terrain modifier.\n " +
            		  		  "I believe lower numbers will make less layers and thicken layers that do spawn.\n "+
            		  		  "Default value is 68419.786D")
            		.translation("ultraamplified.config.terrain.yterrainmodifier")
            		.defineInRange("yTerrainModifier", 68419.786D, 1D, 10000000D);
	  		        
	  		        
	  		        yScaleModifier = builder
            		.comment("\r\n Changes the y terrain scale.\n " +
            		  		  "Not exactly sure what this does.\n "+
            		  		  "Default value is 428.613D")
            		.translation("ultraamplified.config.terrain.yscalemodifier")
            		.defineInRange("yScaleModifier", 428.613D, 1D, 10000000D);
	  		        

            		seaLevel = builder
            		.comment("\r\n Sea Level. Default is 75.")
            		.translation("ultraamplified.config.terrain.sealevel")
            		.defineInRange("seaLevel", 75, 0, 250);


            		lavaOcean = builder
            		.comment("\r\n Replace the water at sea level with lava instead. DO NOT CHANGE THIS OPTION IN AN ALREADY GENERATED DIMENSION. IT WILL CRASH DUE TO LAVA OCEAN UPDATING NEXT TO OCEAN WATER!!")
            		.translation("ultraamplified.config.terrain.lavaocean")
            		.define("lavaOcean", false);


            		endIslandSpawnrate = builder
            		.comment("\r\n How often End Islands will spawn in the End Biome." + "\n " + "0 for no End Islands and 100 for max spawnrate.")
            		.translation("ultraamplified.config.terrain.endislandspawnrate")
            		.defineInRange("endIslandSpawnrate", 6, 0, 100);
            
				
            builder.pop();
            		
            builder.push("Structure/Feature Options");

            	builder.push("Feature Options");
            
	        			dungeonSpawnrate = builder
	                    .comment("\r\n How often Dungeons will spawn.\r\n " 
	                    		+"0 for no Dungeons and 300 for max spawnrate.")
	                    .translation("ultraamplified.config.feature.dungeonspawnrate")
	                    .defineInRange("dungeonSpawnrate", 30, 0, 300);
	            
	    	   			ravineSpawnrate = builder
	                    .comment("\r\n How often Ravines will spawn.\r\n "
	                    		+"0 for no Ravines and 100 for max spawnrate.")
	                    .translation("ultraamplified.config.feature.ravinespawnrate")
	                    .defineInRange("ravineSpawnrate", 25, 0, 100);
	
	            		caveCavitySpawnrate = builder
	                    .comment("\r\n How often Cave Cavity will spawn.\r\n "
	                    		+"0 for no Cave Cavity and 22 for max spawnrate.")
	                    .translation("ultraamplified.config.feature.cavecavityspawnrate")
	                    .defineInRange("caveCavitySpawnrate", 5, 0, 22);
	
	            		oceanCaveSpawnrate = builder
	                    .comment("\r\n How often Underwater Caves will spawn in ocean biomes.\r\n "
	                    		+"0 for no Underwater Caves and 100 for max spawnrate.")
	                    .translation("ultraamplified.config.feature.oceancavespawnrate")
	                    .defineInRange("oceanCaveSpawnrate", 20, 0, 100);
	
	            		pillarGen = builder
	                    .comment("\r\n Controls whether pillars features (both ramp and straight kind) spawn or not.")
	                    .translation("ultraamplified.config.feature.pillargen")
	                    .define("pillarGen", true);
	
	            		honeyLakeGen = builder
	                    .comment("\r\n Controls whether Honey Lakes spawn or not.")
	                    .translation("ultraamplified.config.feature.honeylakegen")
	                    .define("honeyLakeGen", true);
	
	            		slimeLakeGen = builder
	                    .comment("\r\n Controls whether Slime Lakes spawn or not.")
	                    .translation("ultraamplified.config.feature.slimelakegen")
	                    .define("slimeLakeGen", true);
	
	            		waterLakeGen = builder
	                    .comment("\r\n Controls whether Water Lakes spawn or not.")
	                    .translation("ultraamplified.config.feature.waterlakegen")
	                    .define("waterLakeGen", true);
	
	            		lavaLakeGen = builder
	                    .comment("\r\n Controls whether Lava Lakes spawn or not.")
	                    .translation("ultraamplified.config.feature.lavalakegen")
	                    .define("lavaLakeGen", true);
	
	            		glowstoneVariantsSpawnrate = builder
	                    .comment("\r\n Controls how often patches of Glowdirt and other modded Glowstone variants spawn.\r\n "
	                    		+"0 for no patches and 1000 for max amount of patches.")
	                    .translation("ultraamplified.config.feature.glowstonevariantsspawnrate")
	                    .defineInRange("glowstoneVariantsSpawnrate", 50, 0, 1000);
	            		
	            		rootGen = builder
	                    .comment("\r\n Controls whether roots and short vines spawn or not on the underside of the floating land.")
	                    .translation("ultraamplified.config.feature.rootgen")
	                    .define("rootGen", true);

	            		waterfallSpawnrate = builder
	            		.comment("\r\n How often waterfalls will spawn." + "\n " + "0 for no waterfalls and 1000 for max spawnrate.")
	            		.translation("ultraamplified.config.terrain.waterfallspawnrate")
	            		.defineInRange("waterfallSpawnrate", 35, 0, 1000);


	            		lavafallSpawnrate = builder
	            		.comment("\r\n How often lavafalls will spawn." + "\n " + "0 for no lavafalls and 1000 for max spawnrate.")
	            		.translation("ultraamplified.config.terrain.lavafallspawnrate")
	            		.defineInRange("lavafallSpawnrate", 14, 0, 1000);
	
	            builder.pop();
	            
	            builder.push("Mini-structure Options");
	    		
			    		miniStructureGeneration = builder
			            .comment("\r\n Controls whether Desert Wells, Hay Piles, Sun Shrines, Stonehenges, and Crosses spawn or not.")
			            .translation("ultraamplified.config.structure.ministructuregeneration")
			            .define("miniStructureGeneration", true);
			    		
			    		sunShrineSpawnrate = builder
			            .comment("\r\n How rare are Sun Shrines.\r\n "
			            		 +"1 for Sun Shrines spawning in most chunks and 1000 for very rare spawn.\r\n "
			            		 +"Spawns mainly in relic variant of biomes.")
			            .translation("ultraamplified.config.structure.sunshrinespawnrate")
			            .defineInRange("sunShrineSpawnrate", 130, 1, 100);
			
			    		stonehengeSpawnrate = builder
			            .comment("\r\n How rare are Stonehenges.\r\n "
			            		 +"1 for Stonehenges spawning in most chunks and 1000 for very rare spawn.\r\n "
			            		 +"Spawns mainly in relic variant of biomes.")
			            .translation("ultraamplified.config.structure.stonehengespawnrate")
			            .defineInRange("stonehengeSpawnrate", 15, 1, 100);
			            
			    		hangingRuinsSpawnrate = builder
	                    .comment("\r\n How rare are Hanging Ruins.\r\n "
	                    		 +"1000 for Hanging Ruins spawning in most chunks and 0 for no spawn.\r\n "
	                    		 +"NOTE: this is backwards than other spawnrates.\r\n "
	                    		 +"This is because other structures need have finer control on chances less than 1%.\r\n "
	                    		 +"Spawns in most biomes except for oceans, Netherland, and Iced Terrain Biomes and more often in Rocky Fields variants and Stone Fields biomes.")
	                    .translation("ultraamplified.config.structure.hangingruinsspawnrate")
	                    .defineInRange("hangingRuinsSpawnrate", 60, 0, 1000);
			    		
	            builder.pop();
            
            
	            builder.push("Structure Options");
	            
	            		chestGeneration = builder
	                    .comment("\r\n Controls whether loot chests spawn or not in all structures.")
	                    .translation("ultraamplified.config.structure.chestgeneration")
	                    .define("chestGeneration", true);


			            builder.push("Villages");
			            		
			            		villageSpawnrate = builder
			                    .comment("\r\n How rare are Villages.\r\n "
			                    		 +"1 for Village spawning in most chunks and 101 for no spawn.")
			                    .translation("ultraamplified.config.structure.villagespawnrate")
			                    .defineInRange("villageSpawnrate", 16, 1, 101);
			
			            		villageZombieSpawnrate = builder
			                    .comment("\r\n What percentage of Classic Styled Villages are Zombie Villages.\r\n "
			                    		 +"0 for no Zombie Village spawning and 100 for all Villages being zombified.")
			                    .translation("ultraamplified.config.structure.villagezombiespawnrate")
			                    .defineInRange("villageZombieSpawnrate", 10, 0, 100);
			
			            builder.pop();
			
			            builder.push("Mineshaft");
			            		
			            		mineshaftSpawnrate = builder
			                    .comment("\r\n How often Mineshafts will spawn.\r\n " 
			                    		 +"0 for no Mineshafts and 1000 for max spawnrate.")
			                    .translation("ultraamplified.config.structure.mineshaftspawnrate")
			                    .defineInRange("mineshaftSpawnrate", 22, 0, 1000);
			
			            		mineshaftAbovegroundAllowed = builder
			                    .comment("\r\n Can aboveground floating Mineshafts spawn?")
			                    .translation("ultraamplified.config.structure.mineshaftabovegroundallowed")
			                    .define("mineshaftAbovegroundAllowed", true);
			            
			            		mineshaftUndergroundAllowed = builder
			                    .comment("\r\n Can undergound giant pit Mineshafts spawn?")
			                    .translation("ultraamplified.config.structure.mineshaftundergroundallowed")
			                    .define("mineshaftUndergroundAllowed", true);
			
			            builder.pop();
			
			            builder.push("Woodland Mansion");
			            		
				
			            		mansionSpawnrate = builder
			            		.comment("\r\n How rare are Woodland Mansion." + "\n " + "1 for Woodland Mansion spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.mansionspawnrate")
			            		.defineInRange("mansionSpawnrate", 18, 1, 101);
			
			            builder.pop();
			
			            builder.push("Desert Temple");
			            
			            		desertTempleSpawnrate = builder
			            		.comment("\r\n How rare are Desert Temples." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.deserttemplespawnrate")
			            		.defineInRange("desertTempleSpawnrate", 20, 1, 101);
			            		
			            builder.pop();
			
			            builder.push("Jungle Temple");
			                    		
			            		jungleTempleSpawnrate = builder
			            		.comment("\r\n How rare are Jungle Temples." + "\n " + "1 for a spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.jungletemplespawnrate")
			            		.defineInRange("jungleTempleSpawnrate", 20, 1, 101);
			
			            builder.pop();
			
			            builder.push("Igloo");
			                    		
			            		iglooSpawnrate = builder
			            		.comment("\r\n How rare are igloos." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.igloospawnrate")
			            		.defineInRange("iglooSpawnrate", 14, 1, 101);
			
			            builder.pop();
			
			            builder.push("Mushroom Temple");
			                    		
			            		mushroomTempleSpawnrate = builder
			            		.comment("\r\n How rare are Mushroom Temples." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.mushroomtemplespawnrate")
			            		.defineInRange("mushroomTempleSpawnrate", 12, 1, 101);
			
			            builder.pop();
			
			            builder.push("Ice Spike Temple");
			            		
			            		iceSpikeTempleSpawnrate = builder
			            		.comment("\r\n How rare are Ice Spike Temples." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.icespiketemplespawnrate")
			            		.defineInRange("iceSpikeTempleSpawnrate", 20, 1, 101);
			
			            builder.pop();
			
			            builder.push("Witch Hut");
			                    		
			            		witchHutSpawnrate = builder
			            		.comment("\r\n How rare are Witch Huts." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.witchhutspawnrate")
			            		.defineInRange("witchHutSpawnrate", 14, 1, 101);
			
			            builder.pop();
			
			            builder.push("Ocean Monument");
			                    		
			            		oceanMonumentSpawnrate = builder
			            		.comment("\r\n How rare are Ocean Monuments." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.oceanmonumentspawnrate")
			            		.defineInRange("oceanMonumentSpawnrate", 22, 1, 101);
			
			            builder.pop();
			
			            builder.push("Ocean Ruins");
			                    		
			            		oceanRuinsSpawnrate = builder
			            		.comment("\r\n How rare are Ocean Ruins." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.oceanruinsspawnrate")
			            		.defineInRange("oceanRuinsSpawnrate", 7, 1, 101);
			
			            builder.pop();
			
			            builder.push("Shipwreck");
			                    		
			            		shipwreckSpawnrate = builder
			            		.comment("\r\n How rare are Shipwrecks." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.shipwreckspawnrate")
			            		.defineInRange("shipwreckSpawnrate", 7, 1, 101);
			
			            builder.pop();
			
			            builder.push("Stronghold");
			                    		
			            		strongholdSpawnrate = builder
			            		.comment("\r\n How rare are Strongholds." + "\n " + "1 for spawning in most chunks and 501 for no spawn.")
			            		.translation("ultraamplified.config.structure.strongholdspawnrate")
			            		.defineInRange("strongholdSpawnrate", 62, 1, 501);
			
			            		silverfishStrongholdSpawnrate = builder
			            		.comment("\r\n How often Silverfish Blocks will generate in Strongholds." + "\n " + "0 for no Silverfish Blocks and 100 for max spawnrate.")
			            		.translation("ultraamplified.config.structure.silverfishstrongholdspawnrate")
			            		.defineInRange("silverfishStrongholdSpawnrate", 4D, 0, 100);
			
			            		allowSilverfishSpawnerStronghold = builder
			            		.comment("\r\n Can Silverfish Mob Spawners generate in Stronghold?.")
			            		.translation("ultraamplified.config.structure.allowsilverfishspawnerstronghold")
			            		.define("allowSilverfishSpawnerStronghold", true);
			
			            builder.pop();
			
			            builder.push("Nether/Stone Fortress");
			                    		
			            		netherFortressSpawnrate = builder
			            		.comment("\r\n How rare are Nether/Stone Fortresses." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.netherfortressspawnrate")
			            		.defineInRange("netherFortressSpawnrate", 14, 1, 101);
			
			            		netherFortressAboveground = builder
			            		.comment("\r\n Can Nether Fortresses spawn aboveground in the Netherland biome (between y = 85 and 130).")
			            		.translation("ultraamplified.config.structure.netherfortressaboveground")
			            		.define("netherFortressAboveground", true);
			            		
			            		netherFortressUnderground = builder
			            		.comment("\r\n Can stone-styled Nether Fortresses spawn underground or not (below y = 60)." + "\n " + "(Stone Fortresses spawn underground in all biomes except End biome)")
			            		.translation("ultraamplified.config.structure.netherfortressunderground")
			            		.define("netherFortressUnderground", true);
			
			            		allowSilverfishSpawnerFortress = builder
			            		.comment("\r\n Can Silverfish Mob Spawners generate in Stone Fortresses?")
			            		.translation("ultraamplified.config.structure.allowsilverfishspawnerfortress")
			            		.define("allowSilverfishSpawnerFortress", true);
			            		
			            		allowNaturalSilverfishFortress = builder
			            		.comment("\r\n Can Silverfish spawn naturally over time in Stone Fortresses?")
			            		.translation("ultraamplified.config.structure.allownaturalsilverfishfortress")
			            		.define("allowNaturalSilverfishFortress", true);
			
			            builder.pop();
			
			            builder.push("End City");
			                    		
			            		endCitySpawnrate = builder
			            		.comment("\r\n How rare are End Cities." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.endcityspawnrate")
			            		.defineInRange("endCitySpawnrate", 22, 1, 101);
			
			            builder.pop();
			
			            builder.push("Outpost");
			            		
			            	    pillageOutpostRarity = builder
			            		.comment("\r\n How rare are Pillager Outposts." + "\n " + "1 for spawning in most chunks and 101 for no spawn.")
			            		.translation("ultraamplified.config.structure.pillageoutpostrarity")
			            		.defineInRange("pillageOutpostRarity", 20, 1, 101);

			            builder.pop();
	            builder.pop();
            builder.pop();

            builder.push("Biome Options");

		    		biomeSize = builder
		    		.comment("\r\n How large the biomes are." + "\n " + "Bigger number means bigger biomes.")
		    		.translation("ultraamplified.config.biome.biomesize")
		    		.defineInRange("biomeSize", 3, 1, 8);
		
		    		mutatedBiomeSpawnrate = builder
		    		.comment("\r\n How often the mutated form of a biome will generate" + "\n " + "0 for no mutated biomes and 10 for all biomes to be mutated.")
		    		.translation("ultraamplified.config.biome.mutatedbiomespawnrate")
		    		.defineInRange("mutatedBiomeSpawnrate", 2, 0, 10);

		    		
				    builder.push("Allowed Biome Options");
				            
		            		plains = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.plains")
		            		.define("plains", true);
		
		
		            		desert = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.desert")
		            		.define("desert", true);
		
		
		            		forest = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.forest")
		            		.define("forest", true);
		
		
		            		taiga = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.taiga")
		            		.define("taiga", true);
		
		
		            		rockyField = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.rockyfield")
		            		.define("rockyField", true);
		
		
		            		swamplands = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.swamplands")
		            		.define("swamplands", true);
		
		
		            		netherland = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.netherland")
		            		.define("netherland", true);
		
		
		            		endField = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.end")
		            		.define("end", true);
		
		
		            		snowyTundra = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.snowytundra")
		            		.define("snowyTundra", true);
		
		
		            		icedTerrain = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.icedterrain")
		            		.define("icedTerrain", true);
		
		
		            		mushroom = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.mushroom")
		            		.define("mushroom", true);
		
		
		            		stonePlains = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.stoneplains")
		            		.define("stonePlains", true);
		
		
		            		bambooJungle = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.bamboojungle")
		            		.define("bambooJungle", true);
		
		
		            		jungle = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.jungle")
		            		.define("jungle", true);
		
		
		            		frozenDesert = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.frozendesert")
		            		.define("frozenDesert", true);
		
		
		            		birchForest = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.birchforest")
		            		.define("birchForest", true);
		
		
		            		darkForest = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.darkforest")
		            		.define("darkForest", true);
		
		
		            		snowyTaiga = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.snowytaiga")
		            		.define("snowyTaiga", true);
		
		
		            		giantTreeTaiga = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.gianttreetaiga")
		            		.define("giantTreeTaiga", true);
		
		
		            		savanna = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.savanna")
		            		.define("savanna", true);
		
		
		            		badlands = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.badlands")
		            		.define("badlands", true);
		
		
		            		spikyBadlands = builder
		            		.comment("\r\n Should Spiky Badlands and Dissected Plateau Badlands be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.spikybadlands")
		            		.define("spikyBadlands", true);
		
		            		
		            		iceSpike = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.icespike")
		            		.define("iceSpike", true);
		
		            		
		            		frozenOcean = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.frozenocean")
		            		.define("frozenOcean", true);
		            		
		            		
		            		coldOcean = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.coldocean")
		            		.define("coldOcean", true);
		            		
		            		
		            		ocean = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.ocean")
		            		.define("ocean", true);
		            		
		            		
		            		lukewarmOcean = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.lukewarmocean")
		            		.define("lukewarmOcean", true);
		
		            		
		            		warmOcean = builder
		            		.comment("\r\n Should this biome be allowed to spawn?")
		            		.translation("ultraamplified.config.biome.warmocean")
		            		.define("warmOcean", true);

		            builder.pop();
            builder.pop();
            
            builder.push("Main Ores Options");
            
            		coalOreSpawnrate = builder
            		.comment("\r\n How often Coal Ores will spawn." + "\n " + "0 for no Coal Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.ore.coalorespawnrate")
            		.defineInRange("coalOreSpawnrate", 35, 0, 1000);


            		ironOreSpawnrate = builder
            		.comment("\r\n How often Iron Ores will spawn." + "\n " + "0 for no Iron Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.ore.ironorespawnrate")
            		.defineInRange("ironOreSpawnrate", 50, 0, 1000);


            		redstoneOreSpawnrate = builder
            		.comment("\r\n How often Redstone Ores will spawn." + "\n " + "0 for no Redstone Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.ore.redstoneorespawnrate")
            		.defineInRange("redstoneOreSpawnrate", 12, 0, 1000);


            		lapisOreSpawnrate = builder
            		.comment("\r\n How often Lapis Lazuli Ores will spawn." + "\n " + "0 for no Lapis Lazuli Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.ore.lapisorespawnrate")
            		.defineInRange("lapisOreSpawnrate", 2, 0, 1000);


            		diamondOreSpawnrate = builder
            		.comment("\r\n How often Diamond Ores will spawn." + "\n " + "0 for no Diamond Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.ore.diamondorespawnrate")
            		.defineInRange("diamondOreSpawnrate", 1, 0, 1000);


            		goldOreSpawnrate = builder
            		.comment("\r\n How often Gold Ores will spawn." + "\n " + "0 for no Gold Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.ore.goldorespawnrate")
            		.defineInRange("goldOreSpawnrate", 2, 0, 1000);
            
		            builder.push("Rocky Fields Ores and Features Options");
		            
		            		emeraldOreSpawnrate = builder
		            		.comment("\r\n How often Emerald Ores will spawn in all Rocky Fields variant biomes in the form of a percentage." + "\n " + "0 for no Emerald Ores and 1000 for max spawnrate.")
		            		.translation("ultraamplified.config.ore.emeraldorespawnrate")
		            		.defineInRange("emeraldOreSpawnrate", 100, 0, 1000);
		
		
		            		silverfishSpawnrate = builder
		            		.comment("\r\n How often Silverfish Blocks will spawn in all Rocky Fields variant biomes." + "\n " + "0 for no Silverfish Blocks and 1000 for max spawnrate.")
		            		.translation("ultraamplified.config.ore.silverfishspawnrate")
		            		.defineInRange("silverfishSpawnrate", 18, 0, 1000);
		            		
		    		builder.pop();
		    		
		            builder.push("Netherland Ores and Features Options");
		            
		           			quartzOreSpawnrate = builder
		            		.comment("\r\n How often Quartz Ores will spawn." + "\n " + "0 for no Quartz Ores and 1000 for max spawnrate.")
		            		.translation("ultraamplified.config.ore.quartzorespawnrate")
		            		.defineInRange("quartzOreSpawnrate", 14, 0, 1000);
		
		
		            		glowstoneSpawnrate = builder
		            		.comment("\r\n How often Glowstone will spawn in Netherland biome." + "\n " + "0 for no Glowstone and 1000 for max spawnrate.")
		            		.translation("ultraamplified.config.ore.glowstonespawnrate")
		            		.defineInRange("glowstoneSpawnrate", 20, 0, 1000);
		
		
		            		magmaSpawnrate = builder
		            		.comment("\r\n How often Magma Blocks will spawn below Y = 100 in Netherland biome." 
		            				+ "\n " + "0 for no Magma Blocks and 1000 for max spawnrate."
		            				+ "\n " + "Note: Will not affect the flat Magma layer separating water from lava below sealevel.")
		            		.translation("ultraamplified.config.ore.magmaspawnrate")
		            		.defineInRange("magmaSpawnrate", 5, 0, 1000);
		
		
		            		lavaSpawnrate = builder
		            		.comment("\r\n How often single Lava Blocks will spawn in Netherland biome." + "\n " + "0 for no single Lava Blocks and 1000 for max spawnrate.")
		            		.translation("ultraamplified.config.ore.lavaspawnrate")
		            		.defineInRange("lavaSpawnrate", 70, 0, 1000);

		    		builder.pop();
            builder.pop();
        }
    } 
    
    public static void refreshServer()
    {
    	importModdedFeatures = SERVER.importModdedFeatures.get();
    	importModdedStructure = SERVER.importModdedStructure.get();
    	importModdedMobs = SERVER.importModdedMobs.get();
    	importModdedBiomes = SERVER.importModdedBiomes.get();
    	importAllModdedBiomes = SERVER.importAllModdedBiomes.get();
    	blacklistedBiomeList = SERVER.blacklistedBiomeList.get();
    	blacklistedStructureList = SERVER.blacklistedStructureList.get();
    	portalCornerBlocks = SERVER.portalCornerBlocks.get();
    	portalCeilingBlocks = SERVER.portalCeilingBlocks.get();
    	portalFloorBlocks = SERVER.portalFloorBlocks.get();
	    xzTerrainModifier = SERVER.xzTerrainModifier.get();
	    xzScaleModifier = SERVER.xzScaleModifier.get();
	    yScaleModifier = SERVER.yScaleModifier.get();
	    yScaleModifier = SERVER.yScaleModifier.get();
	    bedExplodes = SERVER.bedExplodes.get();
	    forceExitToOverworld = SERVER.forceExitToOverworld.get();
	    yMaximum = SERVER.yMaximum.get();
    	dungeonSpawnrate = SERVER.dungeonSpawnrate.get();
    	ravineSpawnrate = SERVER.ravineSpawnrate.get();
    	caveCavitySpawnrate = SERVER.caveCavitySpawnrate.get();
    	oceanCaveSpawnrate = SERVER.oceanCaveSpawnrate.get();
    	pillarGen = SERVER.pillarGen.get();
    	honeyLakeGen = SERVER.honeyLakeGen.get();
    	slimeLakeGen = SERVER.slimeLakeGen.get();
    	waterLakeGen = SERVER.waterLakeGen.get();
    	lavaLakeGen = SERVER.lavaLakeGen.get();
    	chestGeneration = SERVER.chestGeneration.get();
    	sunShrineSpawnrate = SERVER.sunShrineSpawnrate.get();
    	stonehengeSpawnrate = SERVER.stonehengeSpawnrate.get();
        hangingRuinsSpawnrate = SERVER.hangingRuinsSpawnrate.get();
    	miniStructureGeneration = SERVER.miniStructureGeneration.get();
    	villageSpawnrate = SERVER.villageSpawnrate.get();
    	villageZombieSpawnrate = SERVER.villageZombieSpawnrate.get();
    	mineshaftSpawnrate = SERVER.mineshaftSpawnrate.get();
    	mineshaftAbovegroundAllowed = SERVER.mineshaftAbovegroundAllowed.get();
    	mineshaftUndergroundAllowed = SERVER.mineshaftUndergroundAllowed.get();
    	mansionSpawnrate = SERVER.mansionSpawnrate.get();
    	desertTempleSpawnrate = SERVER.desertTempleSpawnrate.get();
    	jungleTempleSpawnrate = SERVER.jungleTempleSpawnrate.get();
    	iglooSpawnrate = SERVER.iglooSpawnrate.get();
    	mushroomTempleSpawnrate = SERVER.mushroomTempleSpawnrate.get();
    	iceSpikeTempleSpawnrate = SERVER.iceSpikeTempleSpawnrate.get();
    	witchHutSpawnrate = SERVER.witchHutSpawnrate.get();
    	oceanMonumentSpawnrate = SERVER.oceanMonumentSpawnrate.get();
    	oceanRuinsSpawnrate = SERVER.oceanRuinsSpawnrate.get();
    	shipwreckSpawnrate = SERVER.shipwreckSpawnrate.get();
		strongholdSpawnrate = SERVER.strongholdSpawnrate.get();
    	silverfishStrongholdSpawnrate = SERVER.silverfishStrongholdSpawnrate.get();
    	allowSilverfishSpawnerStronghold = SERVER.allowSilverfishSpawnerStronghold.get();
    	netherFortressSpawnrate = SERVER.netherFortressSpawnrate.get();
    	netherFortressAboveground = SERVER.netherFortressAboveground.get();
    	netherFortressUnderground = SERVER.netherFortressUnderground.get();
    	allowSilverfishSpawnerFortress = SERVER.allowSilverfishSpawnerFortress.get();
    	allowNaturalSilverfishFortress = SERVER.allowNaturalSilverfishFortress.get();
    	endCitySpawnrate = SERVER.endCitySpawnrate.get();
    	pillageOutpostRarity = SERVER.pillageOutpostRarity.get();
    	secretSetting = SERVER.secretSetting.get();
    	heavyFog = SERVER.heavyFog.get();
    	biomeSize = SERVER.biomeSize.get();
    	mutatedBiomeSpawnrate = SERVER.mutatedBiomeSpawnrate.get();
    	seaLevel = SERVER.seaLevel.get();
    	lavaOcean = SERVER.lavaOcean.get();
    	waterfallSpawnrate = SERVER.waterfallSpawnrate.get();
    	lavafallSpawnrate = SERVER.lavafallSpawnrate.get();
    	endIslandSpawnrate = SERVER.endIslandSpawnrate.get();
    	plains = SERVER.plains.get();
    	desert = SERVER.desert.get();
    	forest = SERVER.forest.get();
    	taiga = SERVER.taiga.get();
    	rockyField = SERVER.rockyField.get();
    	swamplands = SERVER.swamplands.get();
    	nether = SERVER.netherland.get();
    	end = SERVER.endField.get();
    	snowyTundra = SERVER.snowyTundra.get();
    	icedTerrain = SERVER.icedTerrain.get();
    	mushroom = SERVER.mushroom.get();
    	stonePlains = SERVER.stonePlains.get();
    	bambooJungle = SERVER.bambooJungle.get();
    	jungle = SERVER.jungle.get();
    	frozenDesert = SERVER.frozenDesert.get();
    	birchForest = SERVER.birchForest.get();
    	darkForest = SERVER.darkForest.get();
    	snowyTaiga = SERVER.snowyTaiga.get();
    	giantTreeTaiga = SERVER.giantTreeTaiga.get();
    	savanna = SERVER.savanna.get();
    	badlands = SERVER.badlands.get();
    	spikyBadlands = SERVER.spikyBadlands.get();
    	iceSpike = SERVER.iceSpike.get();
    	frozenOcean = SERVER.frozenOcean.get();
    	coldOcean = SERVER.coldOcean.get();
    	ocean = SERVER.ocean.get();
    	lukewarmOcean = SERVER.lukewarmOcean.get();
    	warmOcean = SERVER.warmOcean.get();
    	coalOreSpawnrate = SERVER.coalOreSpawnrate.get();
    	ironOreSpawnrate = SERVER.ironOreSpawnrate.get();
    	redstoneOreSpawnrate = SERVER.redstoneOreSpawnrate.get();
    	lapisOreSpawnrate = SERVER.lapisOreSpawnrate.get();
    	diamondOreSpawnrate = SERVER.diamondOreSpawnrate.get();
    	goldOreSpawnrate = SERVER.goldOreSpawnrate.get();
    	emeraldOreSpawnrate = SERVER.emeraldOreSpawnrate.get();
    	silverfishSpawnrate = SERVER.silverfishSpawnrate.get();
    	quartzOreSpawnrate = SERVER.quartzOreSpawnrate.get();
    	glowstoneSpawnrate = SERVER.glowstoneSpawnrate.get();
    	magmaSpawnrate = SERVER.magmaSpawnrate.get();
    	lavaSpawnrate = SERVER.lavaSpawnrate.get();
    	glowstoneVariantsSpawnrate = SERVER.glowstoneVariantsSpawnrate.get();
    	rootGen = SERVER.rootGen.get();
    }
}
