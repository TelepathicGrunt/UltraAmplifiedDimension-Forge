package net.telepathicgrunt.ultraamplified.config;


import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
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
    public static double xzTerrainModifier = 684.412D;
    public static double xzScaleModifier = 8.55515F;
    public static double yTerrainModifier = 68419.786D;
    public static double yScaleModifier = 428.613D;
    public static int yMaximum = 256;
    public static int dungeonSpawnrate = 30;
    public static int ravineSpawnrate = 25;
    public static int caveCavitySpawnrate = 5;
    public static int oceanCaveSpawnrate = 20;
    public static boolean pillarGen = true;
    public static boolean slimeLakeGen = true;
    public static boolean waterLakeGen = true;
    public static boolean lavaLakeGen = true;
    public static boolean chestGeneration = true;
    public static int sunShrineSpawnrate = 50;
    public static int stonehengeSpawnrate = 10;
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
    public static int oceanMonumentSpawnrate = 22;
    public static int oceanRuinsSpawnrate = 7;
    public static int shipwreckSpawnrate = 7;
    public static int strongholdSpawnrate = 62;
    public static double silverfishStrongholdSpawnrate = 4D;
    public static int netherFortressSpawnrate = 14;
    public static boolean netherFortressAboveground = true;
    public static boolean netherFortressUnderground = true;
    public static int endCitySpawnrate = 18;
    public static int pillageOutpostRarity = 20;
    public static boolean secretSetting = false;
    public static boolean heavyFog = false;
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
    public static boolean mountains = true;
    public static boolean swamplands = true;
    public static boolean nether = true;
    public static boolean end = true;
    public static boolean snowyTundra = true;
    public static boolean iceMountain = true;
    public static boolean mushroom = true;
    public static boolean stoneBeach = true;
    public static boolean bambooJungle = true;
    public static boolean jungle = true;
    public static boolean coldBeach = true;
    public static boolean birchForest = true;
    public static boolean darkForest = true;
    public static boolean snowyTaiga = true;
    public static boolean giantTreeTaiga = true;
    public static boolean savanna = true;
    public static boolean badlands = true;
    public static boolean erodedBadlands = true;
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
    					
	    public final ForgeConfigSpec.BooleanValue importModdedFeatures;
	    public final ForgeConfigSpec.BooleanValue importModdedStructure;
	    public final ForgeConfigSpec.BooleanValue importModdedMobs;
	    public final ForgeConfigSpec.BooleanValue importModdedBiomes;
	    public final ForgeConfigSpec.BooleanValue importAllModdedBiomes;
	    public final ForgeConfigSpec.ConfigValue<String> blacklistedBiomeList;
        public final ForgeConfigSpec.DoubleValue xzTerrainModifier;
        public final ForgeConfigSpec.DoubleValue xzScaleModifier;
        public final ForgeConfigSpec.DoubleValue yTerrainModifier;
        public final ForgeConfigSpec.DoubleValue yScaleModifier;
        public final ForgeConfigSpec.IntValue yMaximum;
        public final ForgeConfigSpec.IntValue dungeonSpawnrate;
        public final ForgeConfigSpec.IntValue ravineSpawnrate;
        public final ForgeConfigSpec.IntValue caveCavitySpawnrate;
        public final ForgeConfigSpec.IntValue oceanCaveSpawnrate;
        public final ForgeConfigSpec.BooleanValue pillarGen;
        public final ForgeConfigSpec.BooleanValue slimeLakeGen;
        public final ForgeConfigSpec.BooleanValue waterLakeGen;
        public final ForgeConfigSpec.BooleanValue lavaLakeGen;
		public final ForgeConfigSpec.BooleanValue chestGeneration;
		public final ForgeConfigSpec.IntValue sunShrineSpawnrate;
		public final ForgeConfigSpec.IntValue stonehengeSpawnrate;
		public final ForgeConfigSpec.BooleanValue miniStructureGeneration;
		public final ForgeConfigSpec.IntValue villageSpawnrate;
		public final ForgeConfigSpec.IntValue villageZombieSpawnrate;
		public final ForgeConfigSpec.IntValue mineshaftSpawnrate;
		public final ForgeConfigSpec.BooleanValue mineshaftAbovegroundAllowed;
		public final ForgeConfigSpec.BooleanValue mineshaftUndergroundAllowed;
		public final ForgeConfigSpec.IntValue mansionSpawnrate;
		public final ForgeConfigSpec.IntValue desertTempleSpawnrate;
		public final ForgeConfigSpec.IntValue jungleTempleSpawnrate;
		public final ForgeConfigSpec.IntValue iglooSpawnrate;
		public final ForgeConfigSpec.IntValue witchHutSpawnrate;
		public final ForgeConfigSpec.IntValue oceanMonumentSpawnrate;
		public final ForgeConfigSpec.IntValue oceanRuinsSpawnrate;
		public final ForgeConfigSpec.IntValue shipwreckSpawnrate;
		public final ForgeConfigSpec.IntValue strongholdSpawnrate;
		public final ForgeConfigSpec.DoubleValue silverfishStrongholdSpawnrate;
		public final ForgeConfigSpec.IntValue netherFortressSpawnrate;
		public final ForgeConfigSpec.BooleanValue netherFortressAboveground;
		public final ForgeConfigSpec.BooleanValue netherFortressUnderground;
		public final ForgeConfigSpec.IntValue endCitySpawnrate;
		public final ForgeConfigSpec.IntValue pillageOutpostRarity;
		public final ForgeConfigSpec.BooleanValue secretSetting;
		public final ForgeConfigSpec.BooleanValue heavyFog;
		public final ForgeConfigSpec.IntValue biomeSize;
		public final ForgeConfigSpec.IntValue mutatedBiomeSpawnrate;
		public final ForgeConfigSpec.IntValue seaLevel;
		public final ForgeConfigSpec.BooleanValue lavaOcean;
		public final ForgeConfigSpec.IntValue waterfallSpawnrate;
		public final ForgeConfigSpec.IntValue lavafallSpawnrate;
		public final ForgeConfigSpec.IntValue endIslandSpawnrate;
		public final ForgeConfigSpec.BooleanValue plains;
		public final ForgeConfigSpec.BooleanValue desert;
		public final ForgeConfigSpec.BooleanValue forest;
		public final ForgeConfigSpec.BooleanValue taiga;
		public final ForgeConfigSpec.BooleanValue mountains;
		public final ForgeConfigSpec.BooleanValue swamplands;
		public final ForgeConfigSpec.BooleanValue nether;
		public final ForgeConfigSpec.BooleanValue end;
		public final ForgeConfigSpec.BooleanValue snowyTundra;
		public final ForgeConfigSpec.BooleanValue iceMountain;
		public final ForgeConfigSpec.BooleanValue mushroom;
		public final ForgeConfigSpec.BooleanValue stoneBeach;
		public final ForgeConfigSpec.BooleanValue bambooJungle;
		public final ForgeConfigSpec.BooleanValue jungle;
		public final ForgeConfigSpec.BooleanValue coldBeach;
		public final ForgeConfigSpec.BooleanValue birchForest;
		public final ForgeConfigSpec.BooleanValue darkForest;
		public final ForgeConfigSpec.BooleanValue snowyTaiga;
		public final ForgeConfigSpec.BooleanValue giantTreeTaiga;
		public final ForgeConfigSpec.BooleanValue savanna;
		public final ForgeConfigSpec.BooleanValue badlands;
		public final ForgeConfigSpec.BooleanValue erodedBadlands;
		public final ForgeConfigSpec.BooleanValue iceSpike;
		public final ForgeConfigSpec.BooleanValue frozenOcean;
		public final ForgeConfigSpec.BooleanValue coldOcean;
		public final ForgeConfigSpec.BooleanValue ocean;
		public final ForgeConfigSpec.BooleanValue lukewarmOcean;
		public final ForgeConfigSpec.BooleanValue warmOcean;
		public final ForgeConfigSpec.IntValue coalOreSpawnrate;
		public final ForgeConfigSpec.IntValue ironOreSpawnrate;
		public final ForgeConfigSpec.IntValue redstoneOreSpawnrate;
		public final ForgeConfigSpec.IntValue lapisOreSpawnrate;
		public final ForgeConfigSpec.IntValue diamondOreSpawnrate;
		public final ForgeConfigSpec.IntValue goldOreSpawnrate;
		public final ForgeConfigSpec.IntValue emeraldOreSpawnrate;
		public final ForgeConfigSpec.IntValue silverfishSpawnrate;
		public final ForgeConfigSpec.IntValue quartzOreSpawnrate;
		public final ForgeConfigSpec.IntValue glowstoneSpawnrate;
		public final ForgeConfigSpec.IntValue magmaSpawnrate;
		public final ForgeConfigSpec.IntValue lavaSpawnrate;
		public final ForgeConfigSpec.IntValue glowstoneVariantsSpawnrate;
		public final ForgeConfigSpec.BooleanValue rootGen;

        ServerConfig(ForgeConfigSpec.Builder builder) 
        {

            builder.push("Mod Compatibility Options");
            
            		importModdedFeatures = builder
                    .comment("\r\nAttempt to add modded features from vanilla biomes into Ultra Amplified version of that biome.\r\n"
                    		+"Only works if other mod added the feature by addFeature(...) to vanilla biome and registered the feature correctly without the 'minecraft' namespace.")
                    .translation("ultraamplified.config.structure.importmoddedfeatures")
                    .define("importModdedFeatures", false);
            		
            		importModdedStructure = builder
                    .comment("\r\nAttempt to add modded structures from vanilla biomes into Ultra Amplified version of that biome.\r\n"
                    		+"Only works if other mod added the structure by addFeature(...) to vanilla biome and registered the structure correctly without the 'minecraft' namespace.")
                    .translation("ultraamplified.config.structure.importmoddedstructure")
                    .define("importModdedStructure", false);
            		
            		importModdedMobs = builder
                    .comment("\r\nAttempt to add modded mobs from vanilla biomes into Ultra Amplified version of that biome.\r\n"
                    		+"Only works if other mod added the mob by addSpawn(...) to vanilla biome and registered the mob correctly without the 'minecraft' namespace.")
                    .translation("ultraamplified.config.structure.importmoddedmobs")
                    .define("importModdedMobs", false);
            		
            		importModdedBiomes = builder
                    .comment("\r\nAttempt to add modded biomes from Overworld into Ultra Amplified dimension. (or into Overworld in Ultra Amplified worldtype)\r\n"
                    		+"Only works if other mod added the biome to the BiomeDictionary with the BiomeType of DESERT, WARM, COOL, or ICY type.")
                    .translation("ultraamplified.config.structure.importmoddedbiomes")
                    .define("importModdedBiomes", false);

            		importAllModdedBiomes = builder
                    .comment("\r\nAttempt to add all registered modded biomes into Ultra Amplified dimension or worldtype.\r\n"
                    		+"You may want to turn up biome size to 4 or 5 as this may make biomes very crowded. Also overrides importModdedBiomes setting.")
                    .translation("ultraamplified.config.structure.importmoddedbiomes")
                    .define("importAllModdedBiomes", false);
            		
            		blacklistedBiomeList = builder
                    .comment("\r\nBlacklist either modded mods or its specific biomes from being imported into Ultra Amplified dimension/worldtype.\r\n"
                    		+"To blacklist a mod, type out its id like so with :* attached at end. Example: \"example_mod_id:*\"\r\n"
                    		+"To blacklist a mod's biome, type out the resourcelocation. Example: \"example_mod_id:lava_desert\"\r\n"
                    		+"NOTE: Seperate each entry with a comma. Example: \"example_mod_id_1:lava_desert, example_mod_id_2:*, example_mod_id_1:ender_forest\"\r\n"
                    		+"Also, any entry using ultra_amplified_mod or minecraft id will be ignored as I already handle those ids internally.")
                    .translation("ultraamplified.config.structure.blacklistedbiomelist")
                    .define("blacklistedBiomeList", "");
            		
            builder.pop();
            
            builder.push("General Structure Options");
            
            			dungeonSpawnrate = builder
	                    .comment("\r\nHow often Dungeons will spawn.\r\n" 
	                    		+"0 for no Dungeons and 300 for max spawnrate.")
	                    .translation("ultraamplified.config.structure.dungeonspawnrate")
	                    .defineInRange("dungeonSpawnrate", 30, 0, 300);
	            
        	   			ravineSpawnrate = builder
	                    .comment("\r\nHow often Ravines will spawn.\r\n"
	                    		+"0 for no Ravines and 100 for max spawnrate.")
	                    .translation("ultraamplified.config.structure.ravinespawnrate")
	                    .defineInRange("ravineSpawnrate", 25, 0, 100);

	            		caveCavitySpawnrate = builder
	                    .comment("\r\nHow often Cave Cavity will spawn.\r\n"
	                    		+"0 for no Cave Cavity and 22 for max spawnrate.")
	                    .translation("ultraamplified.config.structure.cavecavityspawnrate")
	                    .defineInRange("caveCavitySpawnrate", 5, 0, 22);

	            		oceanCaveSpawnrate = builder
	                    .comment("\r\nHow often Underwater Caves will spawn in ocean biomes.\r\n"
	                    		+"0 for no Underwater Caves and 100 for max spawnrate.")
	                    .translation("ultraamplified.config.structure.oceancavespawnrate")
	                    .defineInRange("oceanCaveSpawnrate", 20, 0, 100);

	            		pillarGen = builder
	                    .comment("\r\nControls whether pillars features (both ramp and straight kind) spawn or not.")
	                    .translation("ultraamplified.config.structure.pillargen")
	                    .define("pillarGen", true);

	            		slimeLakeGen = builder
	                    .comment("\r\nControls whether Slime Lakes spawn or not.")
	                    .translation("ultraamplified.config.structure.slimelakegen")
	                    .define("slimeLakeGen", true);

	            		waterLakeGen = builder
	                    .comment("\r\nControls whether Water Lakes spawn or not.")
	                    .translation("ultraamplified.config.structure.waterlakegen")
	                    .define("waterLakeGen", true);

	            		lavaLakeGen = builder
	                    .comment("\r\nControls whether Lava Lakes spawn or not.")
	                    .translation("ultraamplified.config.structure.lavalakegen")
	                    .define("lavaLakeGen", true);

	            		glowstoneVariantsSpawnrate = builder
	                    .comment("\r\nControls how often patches of Glowdirt and other modded Glowstone variants spawn.\r\n"
	                    		+"0 for no patches and 1000 for max amount of patches.")
	                    .translation("ultraamplified.config.structure.glowstonevariantsspawnrate")
	                    .defineInRange("glowstoneVariantsSpawnrate", 50, 0, 1000);
	            		

	            		rootGen = builder
	                    .comment("\r\nControls whether roots and short vines spawn or not on the underside of the floating land.")
	                    .translation("ultraamplified.config.structure.rootgen")
	                    .define("rootGen", true);

	                    
            builder.push("Biome-Based Structure Options");
            
            		chestGeneration = builder
                    .comment("\r\nControls whether loot chests spawn or not in all structures.")
                    .translation("ultraamplified.config.structure.chestgeneration")
                    .define("chestGeneration", true);


            		sunShrineSpawnrate = builder
                    .comment("\r\nHow rare are Sun Shrines.\r\n"
                    		 +"1 for Sun Shrines spawning in most chunks and 1000 for very rare spawn.\r\n"
                    		 +"Spawns mainly in hills variant of biomes.")
                    .translation("ultraamplified.config.structure.sunshrinespawnrate")
                    .defineInRange("sunShrineSpawnrate", 130, 1, 100);

            		stonehengeSpawnrate = builder
                    .comment("\r\nHow rare are Stonehenges.\r\n"
                    		 +"1 for Stonehenges spawning in most chunks and 1000 for very rare spawn.\r\n"
                    		 +"Spawns mainly in hills variant of biomes.")
                    .translation("ultraamplified.config.structure.stonehengespawnrate")
                    .defineInRange("stonehengeSpawnrate", 15, 1, 1000);
            		
            		
            		miniStructureGeneration = builder
                    .comment("\r\nControls whether Desert Wells, Hay Piles, Sun Shrines, Stonehenges, and Crosses spawn or not.")
                    .translation("ultraamplified.config.structure.ministructuregeneration")
                    .define("miniStructureGeneration", true);
            
            		
            		villageSpawnrate = builder
                    .comment("\r\nHow rare are Villages.\r\n"
                    		 +"1 for Village spawning in most chunks and 101 for no spawn.")
                    .translation("ultraamplified.config.structure.villagespawnrate")
                    .defineInRange("villageSpawnrate", 16, 1, 101);

            		villageZombieSpawnrate = builder
                    .comment("\r\nWhat percentage of Classic Styled Villages are Zombie Villages.\r\n"
                    		 +"0 for no Zombie Village spawning and 100 for all Villages being zombified.")
                    .translation("ultraamplified.config.structure.villagezombiespawnrate")
                    .defineInRange("villageZombieSpawnrate", 10, 0, 100);

            		
            		mineshaftSpawnrate = builder
                    .comment("\r\nHow often Mineshafts will spawn.\r\n" 
                    		 +"0 for no Mineshafts and 1000 for max spawnrate.")
                    .translation("ultraamplified.config.structure.mineshaftspawnrate")
                    .defineInRange("mineshaftSpawnrate", 22, 0, 1000);

            		mineshaftAbovegroundAllowed = builder
                    .comment("\r\nCan aboveground floating Mineshafts spawn?")
                    .translation("ultraamplified.config.structure.mineshaftabovegroundallowed")
                    .define("mineshaftAbovegroundAllowed", true);
            
            		mineshaftUndergroundAllowed = builder
                    .comment("\r\nCan undergound giant pit Mineshafts spawn?")
                    .translation("ultraamplified.config.structure.mineshaftundergroundallowed")
                    .define("mineshaftUndergroundAllowed", true);
            
            		
            		mansionSpawnrate = builder
            		.comment("\r\nHow rare are Woodland Mansion." + "\n" + "1 for Woodland Mansion spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.mansionspawnrate")
            		.defineInRange("mansionSpawnrate", 18, 1, 101);


            		desertTempleSpawnrate = builder
            		.comment("\r\nHow rare are Desert Temples." + "\n" + "1 for spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.deserttemplespawnrate")
            		.defineInRange("desertTempleSpawnrate", 20, 1, 101);

            		
            		jungleTempleSpawnrate = builder
            		.comment("\r\nHow rare are Jungle Temples." + "\n" + "1 for a spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.jungletemplespawnrate")
            		.defineInRange("jungleTempleSpawnrate", 20, 1, 101);

            		
            		iglooSpawnrate = builder
            		.comment("\r\nHow rare are igloos." + "\n" + "1 for spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.igloospawnrate")
            		.defineInRange("iglooSpawnrate", 14, 1, 101);

            		
            		witchHutSpawnrate = builder
            		.comment("\r\nHow rare are Witch Huts." + "\n" + "1 for spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.witchhutspawnrate")
            		.defineInRange("witchHutSpawnrate", 14, 1, 101);

            		
            		oceanMonumentSpawnrate = builder
            		.comment("\r\nHow rare are Ocean Monuments." + "\n" + "1 for spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.oceanmonumentspawnrate")
            		.defineInRange("oceanMonumentSpawnrate", 22, 1, 101);

            		
            		oceanRuinsSpawnrate = builder
            		.comment("\r\nHow rare are Ocean Ruins." + "\n" + "1 for spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.oceanruinsspawnrate")
            		.defineInRange("oceanRuinsSpawnrate", 7, 1, 101);

            		
            		shipwreckSpawnrate = builder
            		.comment("\r\nHow rare are Shipwrecks." + "\n" + "1 for spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.shipwreckspawnrate")
            		.defineInRange("shipwreckSpawnrate", 7, 1, 101);


            		strongholdSpawnrate = builder
            		.comment("\r\nHow rare are Strongholds." + "\n" + "1 for spawning in most chunks and 501 for no spawn.")
            		.translation("ultraamplified.config.structure.strongholdspawnrate")
            		.defineInRange("strongholdSpawnrate", 62, 1, 501);


            		silverfishStrongholdSpawnrate = builder
            		.comment("\r\nHow often Silverfish Blocks will generate in Strongholds." + "\n" + "0 for no Silverfish Blocks and 100 for max spawnrate.")
            		.translation("ultraamplified.config.structure.silverfishstrongholdspawnrate")
            		.defineInRange("silverfishStrongholdSpawnrate", 4D, 0, 100);


            		netherFortressSpawnrate = builder
            		.comment("\r\nHow rare are Nether Fortresses." + "\n" + "1 for spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.netherfortressspawnrate")
            		.defineInRange("netherFortressSpawnrate", 14, 1, 101);

            		netherFortressAboveground = builder
            		.comment("\r\nCan Nether Fortresses spawn aboveground in the Nether biome (between y = 85 and 130).")
            		.translation("ultraamplified.config.structure.netherfortressaboveground")
            		.define("netherFortressAboveground", true);
            		
            		netherFortressUnderground = builder
            		.comment("\r\nCan stone variant Nether Fortresses spawn underground or not (below y = 60)." + "\n" + "(Stone variant Nether Fortress spawn underground in all biomes except End biome)")
            		.translation("ultraamplified.config.structure.netherfortressunderground")
            		.define("netherFortressUnderground", true);
            		

            		endCitySpawnrate = builder
            		.comment("\r\nHow rare are End Cities." + "\n" + "1 for spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.endcityspawnrate")
            		.defineInRange("endCitySpawnrate", 22, 1, 101);
            		
            	    
            	    pillageOutpostRarity = builder
            		.comment("\r\nHow rare are Pillager Outposts." + "\n" + "1 for spawning in most chunks and 101 for no spawn.")
            		.translation("ultraamplified.config.structure.pillageoutpostrarity")
            		.defineInRange("pillageOutpostRarity", 20, 1, 101);
            		
            
            builder.pop().pop();

            builder.push("Biome and Terrain Options");
            
	  				secretSetting = builder
            		.comment("\r\nDoes something neat! Give it a try lol\n" +
            		  		  "You might want to use this on a fresh new world... ;)\n"+
            		  		  "Changes the terrain's look!")
            		.translation("ultraamplified.config.structure.secretsettings")
            		.define("secretSettings", false);

	  				heavyFog = builder
            		.comment("\r\nAdds very heavy fog to make the world look more spoky and limit visibility.\n"+
            				"This is not the same as distance fog which does not make chunks near you foggy.")
            		.translation("ultraamplified.config.structure.heavyfog")
            		.define("heavyFog", false);


	  				yMaximum = builder
            		.comment("\r\nMaxium height the terrain can generate up to.")
            		.translation("ultraamplified.config.structure.ymaximum")
            		.defineInRange("yMaximum", 248, 100, 256);
	  		        
	  		        
	  		        xzTerrainModifier = builder
            		.comment("\r\nChanges the xz terrain modifier.\n" +
            		  		  "I believe lower numbers will make the layers longer in the xz plane.\n"+
            		  		  "Default value is 684.412D")
            		.translation("ultraamplified.config.structure.xzterrainmodifier")
            		.defineInRange("xzTerrainModifier", 684.412D, 1D, 10000000D);

	  		        
	  		        xzScaleModifier = builder
            		.comment("\r\nChanges the xz terrain scale.\n" +
            		  		  "Not exactly sure what this does.\n"+
            		  		  "Default value is 8.55515F")
            		.translation("ultraamplified.config.structure.xzscalemodifier")
            		.defineInRange("xzScaleModifier", 8.55515F, 1D, 10000000D);

	  		        
	  		      	yTerrainModifier = builder
            		.comment("\r\nChanges the y terrain modifier.\n" +
            		  		  "I believe lower numbers will make less layers and thicken layers that do spawn.\n"+
            		  		  "Default value is 68419.786D")
            		.translation("ultraamplified.config.structure.yterrainmodifier")
            		.defineInRange("yTerrainModifier", 68419.786D, 1D, 10000000D);
	  		        
	  		        
	  		        yScaleModifier = builder
            		.comment("\r\nChanges the y terrain scale.\n" +
            		  		  "Not exactly sure what this does.\n"+
            		  		  "Default value is 428.613D")
            		.translation("ultraamplified.config.structure.yscalemodifier")
            		.defineInRange("yScaleModifier", 428.613D, 1D, 10000000D);
	  		        
  		
            		biomeSize = builder
            		.comment("\r\nHow large the biomes are." + "\n" + "Bigger number means bigger biomes.")
            		.translation("ultraamplified.config.structure.biomesize")
            		.defineInRange("biomeSize", 3, 1, 8);


            		mutatedBiomeSpawnrate = builder
            		.comment("\r\nHow often the mutated form of a biome will generate" + "\n" + "0 for no mutated biomes and 10 for all biomes to be mutated.")
            		.translation("ultraamplified.config.structure.mutatedbiomespawnrate")
            		.defineInRange("mutatedBiomeSpawnrate", 2, 0, 10);


            		seaLevel = builder
            		.comment("\r\nSea Level")
            		.translation("ultraamplified.config.structure.sealevel")
            		.defineInRange("seaLevel", 75, 0, 250);


            		lavaOcean = builder
            		.comment("\r\nReplace the water at sea level with lava instead. DO NOT CHANGE THIS OPTION IN AN ALREADY GENERATED WORLD. IT WILL CRASH DUE TO LAVA OCEAN UPDATING NEXT TO OCEAN WATER!!")
            		.translation("ultraamplified.config.structure.lavaocean")
            		.define("lavaOcean", false);


            		waterfallSpawnrate = builder
            		.comment("\r\nHow often waterfalls will spawn." + "\n" + "0 for no waterfalls and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.waterfallspawnrate")
            		.defineInRange("waterfallSpawnrate", 35, 0, 1000);


            		lavafallSpawnrate = builder
            		.comment("\r\nHow often lavafalls will spawn." + "\n" + "0 for no lavafalls and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.lavafallspawnrate")
            		.defineInRange("lavafallSpawnrate", 14, 0, 1000);


            		endIslandSpawnrate = builder
            		.comment("\r\nHow often End Islands will spawn in the End Biome." + "\n" + "0 for no End Islands and 100 for max spawnrate.")
            		.translation("ultraamplified.config.structure.endislandspawnrate")
            		.defineInRange("endIslandSpawnrate", 6, 0, 100);
            
            builder.push("Biome Selection Options");
            
            		plains = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.plains")
            		.define("plains", true);


            		desert = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.desert")
            		.define("desert", true);


            		forest = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.forest")
            		.define("forest", true);


            		taiga = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.taiga")
            		.define("taiga", true);


            		mountains = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.mountains")
            		.define("mountains", true);


            		swamplands = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.swamplands")
            		.define("swamplands", true);


            		nether = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.nether")
            		.define("nether", true);


            		end = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.end")
            		.define("end", true);


            		snowyTundra = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.snowytundra")
            		.define("snowyTundra", true);


            		iceMountain = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.icemountain")
            		.define("iceMountain", true);


            		mushroom = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.mushroom")
            		.define("mushroom", true);


            		stoneBeach = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.stonebeach")
            		.define("stoneBeach", true);


            		bambooJungle = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.bamboojungle")
            		.define("bambooJungle", true);


            		jungle = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.jungle")
            		.define("jungle", true);


            		coldBeach = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.coldbeach")
            		.define("coldBeach", true);


            		birchForest = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.birchforest")
            		.define("birchForest", true);


            		darkForest = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.darkforest")
            		.define("darkForest", true);


            		snowyTaiga = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.snowytaiga")
            		.define("snowyTaiga", true);


            		giantTreeTaiga = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.gianttreetaiga")
            		.define("giantTreeTaiga", true);


            		savanna = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.savanna")
            		.define("savanna", true);


            		badlands = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.badlands")
            		.define("badlands", true);


            		erodedBadlands = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.erodedbadlands")
            		.define("erodedBadlands", true);

            		
            		iceSpike = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.icespike")
            		.define("iceSpike", true);

            		
            		frozenOcean = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.frozenocean")
            		.define("frozenOcean", true);
            		
            		
            		coldOcean = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.coldocean")
            		.define("coldOcean", true);
            		
            		
            		ocean = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.ocean")
            		.define("ocean", true);
            		
            		
            		lukewarmOcean = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.lukewarmocean")
            		.define("lukewarmOcean", true);

            		
            		warmOcean = builder
            		.comment("\r\nShould this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.warmocean")
            		.define("warmOcean", true);
            		
            builder.pop().pop();
            
            builder.push("Main Ores Options");
            
            		coalOreSpawnrate = builder
            		.comment("\r\nHow often Coal Ores will spawn." + "\n" + "0 for no Coal Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.coalorespawnrate")
            		.defineInRange("coalOreSpawnrate", 35, 0, 1000);


            		ironOreSpawnrate = builder
            		.comment("\r\nHow often Iron Ores will spawn." + "\n" + "0 for no Iron Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.ironorespawnrate")
            		.defineInRange("ironOreSpawnrate", 50, 0, 1000);


            		redstoneOreSpawnrate = builder
            		.comment("\r\nHow often Redstone Ores will spawn." + "\n" + "0 for no Redstone Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.redstoneorespawnrate")
            		.defineInRange("redstoneOreSpawnrate", 12, 0, 1000);


            		lapisOreSpawnrate = builder
            		.comment("\r\nHow often Lapis Lazuli Ores will spawn." + "\n" + "0 for no Lapis Lazuli Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.lapisorespawnrate")
            		.defineInRange("lapisOreSpawnrate", 2, 0, 1000);


            		diamondOreSpawnrate = builder
            		.comment("\r\nHow often Diamond Ores will spawn." + "\n" + "0 for no Diamond Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.diamondorespawnrate")
            		.defineInRange("diamondOreSpawnrate", 1, 0, 1000);


            		goldOreSpawnrate = builder
            		.comment("\r\nHow often Gold Ores will spawn." + "\n" + "0 for no Gold Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.goldorespawnrate")
            		.defineInRange("goldOreSpawnrate", 2, 0, 1000);
            
            builder.push("Extreme Hills Ores and Features Options");
            
            		emeraldOreSpawnrate = builder
            		.comment("\r\nHow often Emerald Ores will spawn in Extreme Hills Biome in the form of a percentage." + "\n" + "0 for no Emerald Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.emeraldorespawnrate")
            		.defineInRange("emeraldOreSpawnrate", 100, 0, 1000);


            		silverfishSpawnrate = builder
            		.comment("\r\nHow often Silverfish Blocks will spawn in Extreme Hills Biome." + "\n" + "0 for no Silverfish Blocks and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.silverfishspawnrate")
            		.defineInRange("silverfishSpawnrate", 18, 0, 1000);

            builder.push("Nether Ores and Features Options");
            
           			quartzOreSpawnrate = builder
            		.comment("\r\nHow often Quartz Ores will spawn." + "\n" + "0 for no Quartz Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.quartzorespawnrate")
            		.defineInRange("quartzOreSpawnrate", 14, 0, 1000);


            		glowstoneSpawnrate = builder
            		.comment("\r\nHow often Glowstone will spawn in Nether biome." + "\n" + "0 for no Glowstone and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.glowstonespawnrate")
            		.defineInRange("glowstoneSpawnrate", 20, 0, 1000);


            		magmaSpawnrate = builder
            		.comment("\r\nHow often Magma Blocks will spawn below Y = 100 in Nether biome." + "\n" + "0 for no Magma Blocks and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.magmaspawnrate")
            		.defineInRange("magmaSpawnrate", 5, 0, 1000);


            		lavaSpawnrate = builder
            		.comment("\r\nHow often single Lava Blocks will spawn in Nether biome." + "\n" + "0 for no single Lava Blocks and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.lavaspawnrate")
            		.defineInRange("lavaSpawnrate", 70, 0, 1000);
            		
            builder.pop().pop().pop();
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
	    xzTerrainModifier = SERVER.xzTerrainModifier.get();
	    xzScaleModifier = SERVER.xzScaleModifier.get();
	    yScaleModifier = SERVER.yScaleModifier.get();
	    yScaleModifier = SERVER.yScaleModifier.get();
	    yMaximum = SERVER.yMaximum.get();
    	dungeonSpawnrate = SERVER.dungeonSpawnrate.get();
    	ravineSpawnrate = SERVER.ravineSpawnrate.get();
    	caveCavitySpawnrate = SERVER.caveCavitySpawnrate.get();
    	oceanCaveSpawnrate = SERVER.oceanCaveSpawnrate.get();
    	pillarGen = SERVER.pillarGen.get();
    	slimeLakeGen = SERVER.slimeLakeGen.get();
    	waterLakeGen = SERVER.waterLakeGen.get();
    	lavaLakeGen = SERVER.lavaLakeGen.get();
    	chestGeneration = SERVER.chestGeneration.get();
    	sunShrineSpawnrate = SERVER.sunShrineSpawnrate.get();
    	stonehengeSpawnrate = SERVER.stonehengeSpawnrate.get();
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
    	witchHutSpawnrate = SERVER.witchHutSpawnrate.get();
    	oceanMonumentSpawnrate = SERVER.oceanMonumentSpawnrate.get();
    	oceanRuinsSpawnrate = SERVER.oceanRuinsSpawnrate.get();
    	shipwreckSpawnrate = SERVER.shipwreckSpawnrate.get();
		strongholdSpawnrate = SERVER.strongholdSpawnrate.get();
    	silverfishStrongholdSpawnrate = SERVER.silverfishStrongholdSpawnrate.get();
    	netherFortressSpawnrate = SERVER.netherFortressSpawnrate.get();
    	netherFortressAboveground = SERVER.netherFortressAboveground.get();
    	netherFortressUnderground = SERVER.netherFortressUnderground.get();
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
    	mountains = SERVER.mountains.get();
    	swamplands = SERVER.swamplands.get();
    	nether = SERVER.nether.get();
    	end = SERVER.end.get();
    	snowyTundra = SERVER.snowyTundra.get();
    	iceMountain = SERVER.iceMountain.get();
    	mushroom = SERVER.mushroom.get();
    	stoneBeach = SERVER.stoneBeach.get();
    	bambooJungle = SERVER.bambooJungle.get();
    	jungle = SERVER.jungle.get();
    	coldBeach = SERVER.coldBeach.get();
    	birchForest = SERVER.birchForest.get();
    	darkForest = SERVER.darkForest.get();
    	snowyTaiga = SERVER.snowyTaiga.get();
    	giantTreeTaiga = SERVER.giantTreeTaiga.get();
    	savanna = SERVER.savanna.get();
    	badlands = SERVER.badlands.get();
    	erodedBadlands = SERVER.erodedBadlands.get();
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
