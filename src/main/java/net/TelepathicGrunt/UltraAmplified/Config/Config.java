package net.TelepathicGrunt.UltraAmplified.Config;


import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {
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
    
    public static int dungeonSpawnrate = 650;
    public static int ravineSpawnrate = 37;
    public static int caveCavitySpawnrate = 1;
    public static boolean slimeLakeGen = true;
    public static boolean waterLakeGen = true;
    public static boolean lavaLakeGen = true;
    public static boolean chestGeneration = true;
    public static boolean miniStructureGeneration = true;
    public static int villageSpawnrate = 18;
    public static boolean villageGeneration = true;
    public static int villageZombieSpawnrate = 10;
    public static int mineshaftSpawnrate = 25;
    public static boolean mineshaftAbovegroundAllowed = true;
    public static boolean mineshaftUndergroundAllowed = true;
    public static int mansionSpawnrate = 20;
    public static boolean mansionGeneration = true;
    public static int scatteredSpawnrate = 22;
    public static boolean scatteredGeneration = true;
    public static double strongholdDistance = 10.0;
    public static int strongholdNumberPerWorld = 128;
    public static int strongholdSpread = 4;
    public static double silverfishStrongholdSpawnrate = 4D;
    public static boolean strongholdGeneration = true;
    public static int monumentRarity = 16;
    public static boolean monumentGeneration = true;
    public static boolean secretSetting = false;
    public static int biomeSize = 3;
    public static int mutatedBiomeSpawnrate = 3;
    public static int seaLevel = 75;
    public static boolean lavaOcean = false;
    public static int waterfallSpawnrate = 35;
    public static int lavafallSpawnrate = 14;
    public static int endIslandSpawnrate = 6;
    public static boolean bambooForest = true;
    public static boolean plains = true;
    public static boolean desert = true;
    public static boolean forest = true;
    public static boolean taiga = true;
    public static boolean extremeHills = true;
    public static boolean swamplands = true;
    public static boolean nether = true;
    public static boolean end = true;
    public static boolean iceFlats = true;
    public static boolean iceMountain = true;
    public static boolean mushroom = true;
    public static boolean stoneBeach = true;
    public static boolean jungle = true;
    public static boolean coldBeach = true;
    public static boolean birchForest = true;
    public static boolean roofedForest = true;
    public static boolean coldTaiga = true;
    public static boolean megaTaiga = true;
    public static boolean savanna = true;
    public static boolean mesa = true;
    public static boolean mesaBryce = true;
    public static boolean iceSpike = true;
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

    
    public static class ServerConfig
    {
        public final ForgeConfigSpec.IntValue dungeonSpawnrate;
        public final ForgeConfigSpec.IntValue ravineSpawnrate;
        public final ForgeConfigSpec.IntValue caveCavitySpawnrate;
        public final ForgeConfigSpec.BooleanValue slimeLakeGen;
        public final ForgeConfigSpec.BooleanValue waterLakeGen;
        public final ForgeConfigSpec.BooleanValue lavaLakeGen;
		public final ForgeConfigSpec.BooleanValue chestGeneration;
		public final ForgeConfigSpec.BooleanValue miniStructureGeneration;
		public final ForgeConfigSpec.IntValue villageSpawnrate;
		public final ForgeConfigSpec.BooleanValue villageGeneration;
		public final ForgeConfigSpec.IntValue villageZombieSpawnrate;
		public final ForgeConfigSpec.IntValue mineshaftSpawnrate;
		public final ForgeConfigSpec.BooleanValue mineshaftAbovegroundAllowed;
		public final ForgeConfigSpec.BooleanValue mineshaftUndergroundAllowed;
		public final ForgeConfigSpec.IntValue mansionSpawnrate;
		public final ForgeConfigSpec.BooleanValue mansionGeneration;
		public final ForgeConfigSpec.IntValue scatteredSpawnrate;
		public final ForgeConfigSpec.BooleanValue scatteredGeneration;
		public final ForgeConfigSpec.DoubleValue strongholdDistance;
		public final ForgeConfigSpec.IntValue strongholdNumberPerWorld;
		public final ForgeConfigSpec.IntValue strongholdSpread;
		public final ForgeConfigSpec.DoubleValue silverfishStrongholdSpawnrate;
		public final ForgeConfigSpec.BooleanValue strongholdGeneration;
		public final ForgeConfigSpec.IntValue monumentRarity;
		public final ForgeConfigSpec.BooleanValue monumentGeneration;
		public final ForgeConfigSpec.BooleanValue secretSetting;
		public final ForgeConfigSpec.IntValue biomeSize;
		public final ForgeConfigSpec.IntValue mutatedBiomeSpawnrate;
		public final ForgeConfigSpec.IntValue seaLevel;
		public final ForgeConfigSpec.BooleanValue lavaOcean;
		public final ForgeConfigSpec.IntValue waterfallSpawnrate;
		public final ForgeConfigSpec.IntValue lavafallSpawnrate;
		public final ForgeConfigSpec.IntValue endIslandSpawnrate;
		public final ForgeConfigSpec.BooleanValue bambooForest;
		public final ForgeConfigSpec.BooleanValue plains;
		public final ForgeConfigSpec.BooleanValue desert;
		public final ForgeConfigSpec.BooleanValue forest;
		public final ForgeConfigSpec.BooleanValue taiga;
		public final ForgeConfigSpec.BooleanValue extremeHills;
		public final ForgeConfigSpec.BooleanValue swamplands;
		public final ForgeConfigSpec.BooleanValue nether;
		public final ForgeConfigSpec.BooleanValue end;
		public final ForgeConfigSpec.BooleanValue iceFlats;
		public final ForgeConfigSpec.BooleanValue iceMountain;
		public final ForgeConfigSpec.BooleanValue mushroom;
		public final ForgeConfigSpec.BooleanValue stoneBeach;
		public final ForgeConfigSpec.BooleanValue jungle;
		public final ForgeConfigSpec.BooleanValue coldBeach;
		public final ForgeConfigSpec.BooleanValue birchForest;
		public final ForgeConfigSpec.BooleanValue roofedForest;
		public final ForgeConfigSpec.BooleanValue coldTaiga;
		public final ForgeConfigSpec.BooleanValue megaTaiga;
		public final ForgeConfigSpec.BooleanValue savanna;
		public final ForgeConfigSpec.BooleanValue mesa;
		public final ForgeConfigSpec.BooleanValue mesaBryce;
		public final ForgeConfigSpec.BooleanValue iceSpike;
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

        ServerConfig(ForgeConfigSpec.Builder builder) {
        	
            builder.push("General Structure Options");
            
            			dungeonSpawnrate = builder
	                    .comment("How often Dungeons will spawn.\r\n" 
	                    		+"0 for no Dungeons and 1200 for max spawnrate.")
	                    .translation("ultraamplified.config.structure.dungeonspawnrate")
	                    .defineInRange("dungeonSpawnrate", 650, 0, 1200);
	            
        	   			ravineSpawnrate = builder
	                    .comment("How often Ravines will spawn.\r\n"
	                    		+"0 for no Ravines and 100 for max spawnrate.")
	                    .translation("ultraamplified.config.structure.ravinespawnrate")
	                    .defineInRange("ravineSpawnrate", 37, 0, 100);

	            		caveCavitySpawnrate = builder
	                    .comment("How often Cave Cavity will spawn.\r\n"
	                    		+"0 for no Cave Cavity and 10 for max spawnrate.")
	                    .translation("ultraamplified.config.structure.cavecavityspawnrate")
	                    .defineInRange("caveCavitySpawnrate", 1, 0, 10);
	            
	            		slimeLakeGen = builder
	                    .comment("Controls whether Slime Lakes spawn or not.\r\n")
	                    .translation("ultraamplified.config.structure.slimelakegen")
	                    .define("slimeLakeGen", true);

	            		waterLakeGen = builder
	                    .comment("Controls whether Water Lakes spawn or not.\r\n")
	                    .translation("ultraamplified.config.structure.waterlakegen")
	                    .define("waterLakeGen", true);

	            		lavaLakeGen = builder
	                    .comment("Controls whether Lava Lakes spawn or not.\r\n")
	                    .translation("ultraamplified.config.structure.lavalakegen")
	                    .define("lavaLakeGen", true);
	            
            builder.push("Biome-Based Structure Options");
            
            		chestGeneration = builder
                    .comment("Controls whether loot chests spawn or not in all structures.\r\n")
                    .translation("ultraamplified.config.structure.chestgeneration")
                    .define("chestGeneration", true);
            
            		miniStructureGeneration = builder
                    .comment("Controls whether Desert Wells, Hay Piles, Sun Shrines, Stonehenges, and Crosses spawn or not.\r\n")
                    .translation("ultraamplified.config.structure.ministructuregeneration")
                    .define("miniStructureGeneration", true);
            
            		villageSpawnrate = builder
                    .comment("How rare are Villages.\r\n"
                    		 +"1 for Village spawning in most chunk and 100 for lowest spawnrate.")
                    .translation("ultraamplified.config.structure.villagespawnrate")
                    .defineInRange("villageSpawnrate", 18, 1, 100);

            		villageGeneration = builder
                    .comment("Controls whether Villages spawn or not.\r\n")
                    .translation("ultraamplified.config.structure.villagegeneration")
                    .define("villageGeneration", true);
            
            		villageZombieSpawnrate = builder
                    .comment("What percentage of Villages are Zombie Villages.\r\n"
                    		 +"0 for no Zombie Village spawning and 100 for all Villages being zombified.")
                    .translation("ultraamplified.config.structure.villagezombiespawnrate")
                    .defineInRange("villageZombieSpawnrate", 10, 0, 100);

            		mineshaftSpawnrate = builder
                    .comment("How often Mineshafts will spawn.\r\n" 
                    		 +"0 for no Mineshafts and 1000 for max spawnrate.")
                    .translation("ultraamplified.config.structure.mineshaftspawnrate")
                    .defineInRange("mineshaftSpawnrate", 25, 0, 1000);

            		mineshaftAbovegroundAllowed = builder
                    .comment("Can aboveground floating Mineshafts spawn?\r\n")
                    .translation("ultraamplified.config.structure.mineshaftabovegroundallowed")
                    .define("mineshaftAbovegroundAllowed", true);
            
            		mineshaftUndergroundAllowed = builder
                    .comment("Can undergound giant pit Mineshafts spawn?\r\n")
                    .translation("ultraamplified.config.structure.mineshaftundergroundallowed")
                    .define("mineshaftUndergroundAllowed", true);
            
            		mansionSpawnrate = builder
            		.comment("How rare are Woodland Mansion." + "\n" + "1 for Woodland Mansion spawning in most chunk and 100 for lowest spawnrate.")
            		.translation("ultraamplified.config.structure.mansionspawnrate")
            		.defineInRange("mansionSpawnrate", 18, 1, 100);


            		mansionGeneration = builder
            		.comment("Controls whether Woodland Mansion spawn or not.")
            		.translation("ultraamplified.config.structure.mansiongeneration")
            		.define("mansionGeneration", true);


            		scatteredSpawnrate = builder
            		.comment("How rare are Temples, End Cities, Nether Fortresses, Igloos, and Witch Huts." + "\n" + "1 for a Scattered Structure spawning in most chunk and 100 for lowest spawnrate.")
            		.translation("ultraamplified.config.structure.scatteredspawnrate")
            		.defineInRange("scatteredSpawnrate", 22, 1, 100);


            		scatteredGeneration = builder
            		.comment("Controls whether Temples, End Cities, Nether Fortresses, Igloos, and Witch Huts spawn or not.")
            		.translation("ultraamplified.config.structure.scatteredgeneration")
            		.define("scatteredGeneration", true);


            		strongholdDistance = builder
            		.comment("Minimum closest distance Strongholds can spawn to each other." + "\n" + "1 for closest distance and 1000 for max distance between Strongholds.")
            		.translation("ultraamplified.config.structure.strongholddistance")
            		.defineInRange("strongholdDistance", 10.0, 1, 1000);


            		strongholdNumberPerWorld = builder
            		.comment("How many Strongholds spawn in a world." + "\n" + "1 for 1 Strongholds in an entire world and 5000 for maximum number of Strongholds.")
            		.translation("ultraamplified.config.structure.strongholdnumberperworld")
            		.defineInRange("strongholdNumberPerWorld", 128, 1, 5000);


            		strongholdSpread = builder
            		.comment("How clustered towards spawn the Strongholds will be." + "\n" + "1 for Strongholds to be farthest from spawn and 100 for closest spawn distance.")
            		.translation("ultraamplified.config.structure.strongholdspread")
            		.defineInRange("strongholdSpread", 4, 1, 100);


            		silverfishStrongholdSpawnrate = builder
            		.comment("How often Silverfish Blocks will generate in Strongholds." + "\n" + "0 for no Silverfish Blocks and 100 for max spawnrate.")
            		.translation("ultraamplified.config.structure.silverfishstrongholdspawnrate")
            		.defineInRange("silverfishStrongholdSpawnrate", 4D, 0, 100);


            		strongholdGeneration = builder
            		.comment("Controls whether Strongholds spawn or not.")
            		.translation("ultraamplified.config.structure.strongholdgeneration")
            		.define("strongholdGeneration", true);


            		monumentRarity = builder
            		.comment("How rare are Ocean Monuments." + "\n" + "1 for a Ocean Monument spawning in most Jungle chunks and 100 for lowest spawnrate.")
            		.translation("ultraamplified.config.structure.monumentrarity")
            		.defineInRange("monumentRarity", 16, 1, 100);


            		monumentGeneration = builder
            		.comment("Controls whether Ocean Monuments spawn or not (Ocean Monuments spawn in Jungle biomes)")
            		.translation("ultraamplified.config.structure.monumentgeneration")
            		.define("monumentGeneration", true);
            
            builder.pop().pop();

            builder.push("Biome and Terrain Options");
            
	  				secretSetting = builder
            		.comment("Does something neat! Give it a try lol\n" +
            		  		  "You might want to use this on a fresh new world... ;)\n"+
            		  		  "Changes the terrain's look!")
            		.translation("ultraamplified.config.structure.secretsettings")
            		.define("secretSettings", false);
  		
            		biomeSize = builder
            		.comment("How large the biomes are." + "\n" + "Bigger number means bigger biomes.")
            		.translation("ultraamplified.config.structure.biomesize")
            		.defineInRange("biomeSize", 3, 1, 8);


            		mutatedBiomeSpawnrate = builder
            		.comment("How often the mutated form of a biome will generate" + "\n" + "0 for no mutated biomes and 29 for all biomes to be mutated.")
            		.translation("ultraamplified.config.structure.mutatedbiomespawnrate")
            		.defineInRange("mutatedBiomeSpawnrate", 3, 0, 29);


            		seaLevel = builder
            		.comment("Sea Level")
            		.translation("ultraamplified.config.structure.sealevel")
            		.defineInRange("seaLevel", 75, 0, 250);


            		lavaOcean = builder
            		.comment("Replace the water at sea level with lava instead.")
            		.translation("ultraamplified.config.structure.lavaocean")
            		.define("lavaOcean", false);


            		waterfallSpawnrate = builder
            		.comment("How often waterfalls will spawn." + "\n" + "0 for no waterfalls and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.waterfallspawnrate")
            		.defineInRange("waterfallSpawnrate", 35, 0, 1000);


            		lavafallSpawnrate = builder
            		.comment("How often lavafalls will spawn." + "\n" + "0 for no lavafalls and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.lavafallspawnrate")
            		.defineInRange("lavafallSpawnrate", 14, 0, 1000);


            		endIslandSpawnrate = builder
            		.comment("How often End Islands will spawn in the End Biome." + "\n" + "0 for no End Islands and 500 for max spawnrate.")
            		.translation("ultraamplified.config.structure.endislandspawnrate")
            		.defineInRange("endIslandSpawnrate", 6, 0, 500);
            
            builder.push("Biome Selection Options");
            
           			bambooForest = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.bambooforest")
            		.define("bambooForest", true);


            		plains = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.plains")
            		.define("plains", true);


            		desert = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.desert")
            		.define("desert", true);


            		forest = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.forest")
            		.define("forest", true);


            		taiga = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.taiga")
            		.define("taiga", true);


            		extremeHills = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.extremehills")
            		.define("extremeHills", true);


            		swamplands = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.swamplands")
            		.define("swamplands", true);


            		nether = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.nether")
            		.define("nether", true);


            		end = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.end")
            		.define("end", true);


            		iceFlats = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.iceflats")
            		.define("iceFlats", true);


            		iceMountain = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.icemountain")
            		.define("iceMountain", true);


            		mushroom = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.mushroom")
            		.define("mushroom", true);


            		stoneBeach = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.stonebeach")
            		.define("stoneBeach", true);


            		jungle = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.jungle")
            		.define("jungle", true);


            		coldBeach = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.coldbeach")
            		.define("coldBeach", true);


            		birchForest = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.birchforest")
            		.define("birchForest", true);


            		roofedForest = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.roofedforest")
            		.define("roofedForest", true);


            		coldTaiga = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.coldtaiga")
            		.define("coldTaiga", true);


            		megaTaiga = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.megataiga")
            		.define("megaTaiga", true);


            		savanna = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.savanna")
            		.define("savanna", true);


            		mesa = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.mesa")
            		.define("mesa", true);


            		mesaBryce = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.mesabryce")
            		.define("mesaBryce", true);


            		iceSpike = builder
            		.comment("Should this biome be allowed to spawn?")
            		.translation("ultraamplified.config.structure.icespike")
            		.define("iceSpike", true);
            
            builder.pop().pop();
            
            builder.push("Main Ores Options");
            
            		coalOreSpawnrate = builder
            		.comment("How often Coal Ores will spawn." + "\n" + "0 for no Coal Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.coalorespawnrate")
            		.defineInRange("coalOreSpawnrate", 35, 0, 1000);


            		ironOreSpawnrate = builder
            		.comment("How often Iron Ores will spawn." + "\n" + "0 for no Iron Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.ironorespawnrate")
            		.defineInRange("ironOreSpawnrate", 50, 0, 1000);


            		redstoneOreSpawnrate = builder
            		.comment("How often Redstone Ores will spawn." + "\n" + "0 for no Redstone Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.redstoneorespawnrate")
            		.defineInRange("redstoneOreSpawnrate", 12, 0, 1000);


            		lapisOreSpawnrate = builder
            		.comment("How often Lapis Lazuli Ores will spawn." + "\n" + "0 for no Lapis Lazuli Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.lapisorespawnrate")
            		.defineInRange("lapisOreSpawnrate", 2, 0, 1000);


            		diamondOreSpawnrate = builder
            		.comment("How often Diamond Ores will spawn." + "\n" + "0 for no Diamond Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.diamondorespawnrate")
            		.defineInRange("diamondOreSpawnrate", 1, 0, 1000);


            		goldOreSpawnrate = builder
            		.comment("How often Gold Ores will spawn." + "\n" + "0 for no Gold Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.goldorespawnrate")
            		.defineInRange("goldOreSpawnrate", 2, 0, 1000);
            
            builder.push("Extreme Hills Ores and Features Options");
            
            		emeraldOreSpawnrate = builder
            		.comment("How often Emerald Ores will spawn in Extreme Hills Biome in the form of a percentage." + "\n" + "0 for no Emerald Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.emeraldorespawnrate")
            		.defineInRange("emeraldOreSpawnrate", 100, 0, 1000);


            		silverfishSpawnrate = builder
            		.comment("How often Silverfish Blocks will spawn in Extreme Hills Biome." + "\n" + "0 for no Silverfish Blocks and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.silverfishspawnrate")
            		.defineInRange("silverfishSpawnrate", 18, 0, 1000);

            builder.push("Nether Ores and Features Options");
            
           			quartzOreSpawnrate = builder
            		.comment("How often Quartz Ores will spawn." + "\n" + "0 for no Quartz Ores and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.quartzorespawnrate")
            		.defineInRange("quartzOreSpawnrate", 14, 0, 1000);


            		glowstoneSpawnrate = builder
            		.comment("How often Glowstone will spawn in Nether biome." + "\n" + "0 for no Glowstone and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.glowstonespawnrate")
            		.defineInRange("glowstoneSpawnrate", 20, 0, 1000);


            		magmaSpawnrate = builder
            		.comment("How often Magma Blocks will spawn below Y = 100 in Nether biome." + "\n" + "0 for no Magma Blocks and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.magmaspawnrate")
            		.defineInRange("magmaSpawnrate", 5, 0, 1000);


            		lavaSpawnrate = builder
            		.comment("How often single Lava Blocks will spawn in Nether biome." + "\n" + "0 for no single Lava Blocks and 1000 for max spawnrate.")
            		.translation("ultraamplified.config.structure.lavaspawnrate")
            		.defineInRange("lavaSpawnrate", 70, 0, 1000);
            		
            builder.pop().pop().pop();
        }
    } 
}
