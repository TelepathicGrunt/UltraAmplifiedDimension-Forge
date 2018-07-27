package net.TelepathicGrunt.UltraAmplified.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.TelepathicGrunt.UltraAmplified.UltraAmplified;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Config.LangKey("ultra_amplified.config.title")
@Config(modid = UltraAmplified.MOD_ID, type = Type.INSTANCE, name = UltraAmplified.MOD_ID + "_0.4.4")
public class UAConfig {

	/*
	 * Config to control all sorts of settings used for world generation with this mod.
	 * This ranges from ore rarity, what biomes spawn, structure spawning, and more.
	 */
	
	//Putting this at start of option will put it at top of all options: '\0'+
	// '\u00a7'+ is used to replace § since § will be turned to gibberish when this mod is ran on Minecraft outside a development environment.

	
	
	@Name("Structure Options")
	@Config.Comment({
		"Control generation of structures", 
		'\u00a7'+"c(Warning: Takes effect right away for all unloaded chunks in all Ultra Amplified worlds!)"
		})
	public static StructureSubCategory StructuresOptions = new StructureSubCategory();
	public static class StructureSubCategory {
	//main category  
		  
		  	
		    @Name('\0'+"Dungeon Spawnrate")
		    @Config.Comment({
				"How often Dungeons will spawn.",
				'\u00a7'+"60 for no Dungeons and 1200 for max spawnrate."
				})
			@Config.RangeInt(min = 0, max = 1200)
			public int dungeonSpawnrate = 650;
		  
		    @Name('\0'+""+'\0'+"Ravine Spawnrate")
			@Config.Comment({
				"How often Ravines will spawn.",
				'\u00a7'+"60 for no Ravines and 8 for max spawnrate."
				})
			@Config.RangeInt(min = 0, max = 8)
			public int ravineSpawnrate = 5;
			
			@Name('\0'+""+'\0'+"Cave Cavity Spawnrate")
			@Config.Comment({
				"How often Cave Cavity will spawn.",
				'\u00a7'+"60 for no Cave Cavity and 700 for max spawnrate."
				})
			@Config.RangeInt(min = 0, max = 700)
			public int caveCavitySpawnrate = 350;
			
			
			
			@Name("Slime Lakes Allowed?")
			@Config.Comment("Controls whether Slime Lakes spawn or not.")
			public boolean slimeLakeGen = true;
			
			@Name('\0'+"Water Lakes Allowed?")
			@Config.Comment("Controls whether Water Lakes spawn or not.")
			public boolean waterLakeGen = true;
			
			@Name('\0'+"Lava Lakes Allowed?")
			@Config.Comment("Controls whether Lava Lakes spawn or not.")
			public boolean lavaLakeGen = true;
			
			
			
		    @Name("Biome-Based Structure Options")
			@Config.Comment("Control generation of biome-based structures plus Strongholds")
			public MainStructureSubCategory biomeBasedStructuresOptions = new MainStructureSubCategory();
			public class MainStructureSubCategory {
			//subcategory
				
				
				@Name('\0'+""+'\0'+"Loot Chests Allowed?")
				@Config.Comment("Controls whether loot chests spawn or not in all structures.")
				public boolean chestGeneration = true;
				
				
				@Name('\0'+""+'\0'+"Mini-Structures Allowed?")
				@Config.Comment("Controls whether Desert Wells, Hay Piles, and Crosses spawn or not.")
				public boolean miniStructureGeneration = true;
				
				
				@Name("Village Rarity")
				@Config.Comment({
					"How rare are Villages.",
					'\u00a7'+"61 for Village spawning in most chunk and 100 for lowest spawnrate."
					})
				@Config.RangeInt(min = 1, max = 100)
				public int villageSpawnrate = 18;
				
				@Name("Village Allowed?")
				@Config.Comment("Controls whether Villages spawn or not.")
				public boolean villageGeneration = true;
				
				@Name("Village Zombified Chance")
				@Config.Comment({
					"What percentage of Villages are Zombie Villages.",
					'\u00a7'+"60 for no Zombie Village spawning and 100 for all Villages being zombified."
					})
				@Config.RangeInt(min = 0, max = 100)
				public int villageZombieSpawnrate = 10;
				
				
				
				@Name('\0'+"Mineshaft Spawnrate")
				@Config.Comment({
					"How often Mineshafts will spawn.",
					'\u00a7'+"60 for no Mineshafts and 1000 for max spawnrate."
					})
				@Config.RangeDouble(min = 0, max = 1000)
				public double mineshaftSpawnrate = 25;
				
				@Name('\0'+"Mineshaft Can Spawn Underground?")
				@Config.Comment("Can undergound giant pit Mineshafts spawn?")
				public boolean mineshaftUndergroundAllowed = true;
				
				@Name('\0'+"Mineshaft Can Spawn Aboveground?")
				@Config.Comment("Can aboveground floating Mineshafts spawn?")
				public boolean mineshaftAbovegroundAllowed = true;
				
				
				
				@Name("Woodland Mansion Rarity")
				@Config.Comment({
					"How rare are Woodland Mansion.",
					'\u00a7'+"61 for Woodland Mansion spawning in most chunk and 100 for lowest spawnrate."
					})
				@Config.RangeInt(min = 1, max = 100)
				public int mansionSpawnrate = 18;
				
				@Name("Woodland Mansion Allowed?")
				@Config.Comment("Controls whether Woodland Mansion spawn or not.")
				public boolean mansionGeneration = true;
				
				
				
				@Name('\0'+"Scattered Structure Rarity")
				@Config.Comment({
					"How rare are Temples, End Cities, Nether Fortresses, Igloos, and Witch Huts.",
					'\u00a7'+"61 for a Scattered Structure spawning in most chunk and 100 for lowest spawnrate."
					})
				@Config.RangeInt(min = 1, max = 100)
				public int scatteredSpawnrate = 22;
				
				@Name('\0'+"Scattered Structure Allowed?")
				@Config.Comment("Controls whether Temples, End Cities, Nether Fortresses, Igloos, and Witch Huts spawn or not.")
				public boolean scatteredGeneration = true;
				
				
				
				@Name('\0'+"Stronghold Distance")
				@Config.Comment({
					"Minimum closest distance Strongholds can spawn to each other.",
					'\u00a7'+"61 for closest distance and 1000 for max distance between Strongholds."
					})
				@Config.RangeDouble(min = 1, max = 1000)
				public float strongholdDistance = 10.0F;
				
				@Name('\0'+"Stronghold Count")
				@Config.Comment({
					"How many Strongholds spawn in a world.",
					'\u00a7'+"61 for 1 Strongholds in an entire world and 5000 for maximum number of Strongholds."
					})
				@Config.RangeInt(min = 1, max = 5000)
				public int strongholdNumberPerWorld = 128;
				
				@Name('\0'+"Stronghold Spread")
				@Config.Comment({
					"How clustered towards spawn the Strongholds will be.",
					'\u00a7'+"61 for Strongholds to be farthest from spawn and 100 for closest spawn distance."
					})
				@Config.RangeInt(min = 1, max = 100)
				public int strongholdSpread = 4;
				
				@Name("Stronghold Silverfish Block Spawnrate")
				@Config.Comment({ 
					"How often Silverfish Blocks will generate in Strongholds.",
					'\u00a7'+"60 for no Silverfish Blocks and 100 for max spawnrate." 
					})
				@Config.RangeDouble(min = 0, max = 100)
				public double silverfishStrongholdSpawnrate = 4;
				
				@Name('\0'+"Stronghold Allowed?")
				@Config.Comment("Controls whether Strongholds spawn or not.")
				public boolean strongholdGeneration = true;
				
				
				
				@Name('\0'+"Ocean Monument Rarity")
				@Config.Comment({
					"How rare are Ocean Monuments.",
					'\u00a7'+"61 for a Ocean Monument spawning in most Jungle chunks and 100 for lowest spawnrate."
					})
				@Config.RangeInt(min = 1, max = 100)
				public int monumentRarity = 16;
				
				@Name('\0'+"Ocean Monument Allowed?")
				@Config.Comment({
					"Controls whether Ocean Monuments spawn or not.",
					'\u00a7'+"6(Ocean Monuments spawn in Jungle biomes)"
					})
				public boolean monumentGeneration = true;
			}
			//end of structure config
	  }
	
	
	@Name("Biome and Terrain Options")
	@Config.Comment({
		"Control generation of biomes and terrain.",
		'\u00a7'+"c(Warning: Takes effect right away for all unloaded chunks in all Ultra Amplified worlds!)"
		})
	public static BiomeSubCategory biomeOptions = new BiomeSubCategory();
	public static class BiomeSubCategory {
	//main category
		 
		 
		  @Name('\0'+""+'\0'+""+'\0'+""+'\0'+"Super Secret Setting!")
		  @Config.Comment({
		  "Does something neat! Give it a try lol",
		  "You might want to use this on a fresh new world... ;)",
		  '\u00a7'+"kChanges the terrain's look!"
		  })
		  public boolean secretSettings = false;
		 
		
		  @Name('\0'+""+'\0'+""+'\0'+"Biome Size")
		  @Config.Comment("How large the biomes are. Bigger number means bigger biomes.")
		  @Config.RangeInt(min = 1, max = 8)
		  public int biomeSize = 4;
		  
		  @Name('\0'+""+'\0'+""+'\0'+"Mutated Biome Spawnrate")
		  @Config.Comment({
			  "How often the mutated form of a biome will generate",
			  '\u00a7'+"60 for no mutated biomes and 29 for all biomes to be mutated."
			  })
		  @Config.RangeInt(min = 0, max = 29)
		  public int mutatedBiomeSpawnrate = 5;
		  
		  @Name('\0'+"Sea Level")
		  @Config.Comment("Height at which air is replace with water aboveground.")
		  @Config.RangeInt(min = 0, max = 250)
		  public int seaLevel = 75;
		  
		  @Name("Replace Sea Level with Lava?")
		  @Config.Comment("Replace the water at sea level with lava instead.")
		  public boolean lavaOcean = false;
		  
		  @Name('\0'+""+'\0'+"Waterfall Spawnrate")
		  @Config.Comment({
				"How often waterfalls will spawn.",
				'\u00a7'+"60 for no waterfalls and 1000 for max spawnrate."
				})
		  @Config.RangeDouble(min = 0, max = 1000)
		  public double waterfallSpawnrate = 100;
		  
		  @Name('\0'+"Lavafall Spawnrate")
		  @Config.Comment({
				"How often lavafalls will spawn.",
				'\u00a7'+"60 for no lavafalls and 1000 for max spawnrate."
				})
		  @Config.RangeDouble(min = 0, max = 1000)
		  public double lavafallSpawnrate = 100;
		  
		  @Name('\0'+""+'\0'+"End Islands")
		  @Config.Comment({
				"How often End Islands will spawn in the End Biome.",
				'\u00a7'+"60 for no End Islands and 500 for max spawnrate."
				})
	      @Config.RangeDouble(min = 0, max = 500)
		  public double endIslandSpawnrate = 100;
		  
		  
		  
		  @Name("Biome Selection Options")
		  @Config.Comment({
				"Control what biomes to generate.",
				'\u00a7'+"c(Warning: Takes effect at start when entering any Ultra Amplified world for all unloaded chunks!)",
				'\u00a7'+"6Turning off all biomes will set world gen to be Deep Ocean Biome only if no other mod is providing a biome."
				})
		  public MainStructureSubCategory biomeSelectionOptions = new MainStructureSubCategory();
		  public class MainStructureSubCategory {
		  //subcategory
			  
				 @Name("Bamboo Forest")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean bambooForest = true;
				
				 @Name("Plains")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean plains = true;
				
				 @Name("Desert")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean desert = true;
				 
				 @Name("Forest")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean forest = true;

				 @Name("Taiga")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean taiga = true;
				 
				 @Name("Extreme Hills")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean extremeHills = true;
				 
				 @Name("Swamplands")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean swamplands = true;
				 
				 @Name("Nether")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean nether = true;
				 
				 @Name("End")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean end = true;
				 
				 @Name("Ice Flats")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean iceFlats = true;
				 
				 @Name("Ice Mountain")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean iceMountain = true;
				 
				 @Name("Mushroom")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean mushroom = true;
				 
				 @Name("Stone Beach")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean stoneBeach = true;
				 
				 @Name("Jungle")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean jungle = true;
				 
				 @Name("Cold Beach")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean coldBeach = true;
				 
				 @Name("Birch Forest")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean birchForest = true;
				 
				 @Name("Roofed Forest")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean roofedForest = true;
				 
				 @Name("Cold Taiga")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean coldTaiga = true;
				 
				 @Name("Mega Taiga")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean megaTaiga = true;
				 
				 @Name("Savanna")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean savanna = true;
				 
				 @Name("Mesa")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean mesa = true;
				 
				 @Name("Mesa Bryce")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean mesaBryce = true; 
				 
				 @Name("Ice Spike")
				 @Config.Comment("Should this biome be allowed to spawn?")
				 public boolean iceSpike = true; 
			}
		  
		  //end of biome/terrain configS
	  }
	  
	  
	
	@Name("Ores and Similar Generations")
	@Config.Comment({
		"Control spawnrate of ores and other similar generations.",
		'\u00a7'+"c(Warning: Takes effect right away for all unloaded chunks in all Ultra Amplified worlds!)"
		})
	public static OreSubCategory oreAndFeatures = new OreSubCategory();
	public static class OreSubCategory {
	//main category
		
		  
	    @Name("Main Ores")
		@Config.Comment("Ores that spawn in the majority of biomes.")
		public MainStructureSubCategory mainOresOptions = new MainStructureSubCategory();
		public class MainStructureSubCategory {
		//subcategory
			
		
			@Name('\0'+""+'\0'+"Coal Ore Spawnrate")
			@Config.Comment({ 
				"How often Coal Ores will spawn.", 
				'\u00a7'+"60 for no Coal Ores and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int coalOreSpawnrate = 35;
			
			@Name('\0'+""+'\0'+"Iron Ore Spawnrate")
			@Config.Comment({ 
				"How often Iron Ores will spawn.", 
				'\u00a7'+"60 for no Iron Ores and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int ironOreSpawnrate = 50;
			
			@Name('\0'+""+'\0'+"Redstone Ore Spawnrate")
			@Config.Comment({ 
				"How often Redstone Ores will spawn.", 
				'\u00a7'+"60 for no Redstone Ores and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int redstoneOreSpawnrate = 12;
			
			@Name('\0'+"Lapis Lazuli Ore Spawnrate")
			@Config.Comment({ 
				"How often Lapis Lazuli Ores will spawn.", 
				'\u00a7'+"60 for no Lapis Lazuli Ores and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int lapisOreSpawnrate = 2;
			
			@Name("Diamond Ore Spawnrate")
			@Config.Comment({ 
				"How often Diamond Ores will spawn.", 
				'\u00a7'+"60 for no Diamond Ores and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int diamondOreSpawnrate = 1;
			
			@Name('\0'+"Gold Ore Spawnrate")
			@Config.Comment({ 
				"How often Gold Ores will spawn.", 
				'\u00a7'+"60 for no Gold Ores and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int goldOreSpawnrate = 2;
		}
		
		
		
		@Name("Extreme Hills Ore and Feature")
		@Config.Comment("Ores and features that spawns in Extreme Hills Biome.")
		public MainStructureSubCategory2 extremeHillsOresOptions = new MainStructureSubCategory2();
		public class MainStructureSubCategory2 {
		//subcategory
			
			
			@Name('\0'+"Emerald Ore Spawnrate Percentage")
			@Config.Comment({ 
				"How often Emerald Ores will spawn in Extreme Hills Biome in the form of a percentage.", 
				'\u00a7'+"60 for no Emerald Ores and 1000 for max spawnrate." 
				})
			@Config.RangeDouble(min = 0, max = 1000)
			public float emeraldOreSpawnrate = 100;

			@Name('\0'+"Silverfish Block Spawnrate")
			@Config.Comment({ 
				"How often Silverfish Blocks will spawn in Extreme Hills Biome.",
				'\u00a7'+"60 for no Silverfish Blocks and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int silverfishSpawnrate = 30;
		}
		
		
		
		@Name("Nether Ores and Features")
		@Config.Comment("Ores and features that spawns in Nether Biome.")
		public MainStructureSubCategory3 netherOresOptions = new MainStructureSubCategory3();
		public class MainStructureSubCategory3 {
		//subcategory
			
				
			@Name('\0'+"Quartz Ore Spawnrate")
			@Config.Comment({ 
				"How often Quartz Ores will spawn.", 
				'\u00a7'+"60 for no Quartz Ores and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int quartzOreSpawnrate = 14;
	
			@Name('\0'+"Glowstone Spawnrate")
			@Config.Comment({ 
				"How often Glowstone will spawn in Nether biome.",
				'\u00a7'+"60 for no Glowstone and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int glowstoneSpawnrate = 20;
	
			@Name("Magma Block Spawnrate")
			@Config.Comment({ 
				"How often Magma Blocks will spawn below Y = 100 in Nether biome.",
				'\u00a7'+"60 for no Magma Blocks and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int magmaSpawnrate = 5;
	
			@Name("Single Lava Block Spawnrate")
			@Config.Comment({ 
				"How often single Lava Blocks will spawn in Nether biome.",
				'\u00a7'+"60 for no single Lava Blocks and 1000 for max spawnrate." 
				})
			@Config.RangeInt(min = 0, max = 1000)
			public int lavaSpawnrate = 70;

		}
	}
	
	
	
	//used to save any config changes
	@Mod.EventBusSubscriber
	private static class EventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(UltraAmplified.MOD_ID)) {
				
				ConfigManager.sync(UltraAmplified.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}
