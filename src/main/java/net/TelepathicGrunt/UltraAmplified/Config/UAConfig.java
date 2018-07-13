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
@Config(modid = UltraAmplified.MOD_ID, type = Type.INSTANCE, name = UltraAmplified.MOD_ID + "_types")
public class UAConfig {

	//TBH configs are black magic to me lol. I'm just following a tutorial.
	
	

	@Name("Structure Options")
	@Config.Comment("Control generation of structures (Warning: Takes effect right away for all unloaded chunks in all Ultra Amplified worlds!)")
	public static StructureSubCategory StructuresOptions = new StructureSubCategory();
	  public static class StructureSubCategory {
		  
		    @Name("Biome-Based Structure Options")
			@Config.Comment("Control generation of biome-based structures")
			public MainStructureSubCategory biomeBasedStructuresOptions = new MainStructureSubCategory();
			public class MainStructureSubCategory {
				
				@Name("Loot Chests Allowed?")
				@Config.Comment("Controls whether loot chests spawn or not in all structures.")
				public boolean chestGeneration = true;
				
				
				@Name("Village Rarity")
				@Config.Comment({
					"How rare are Villages.",
					"9 for Village spawning in most chunk and 12 for default spawnrate."
					})
				@Config.RangeInt(min = 1, max = 100)
				public int villageSpawnrate = 12;
				
				@Name("Village Allowed?")
				@Config.Comment("Controls whether Villages spawn or not.")
				public boolean villageGeneration = true;
				
				
				
				@Name("Mineshaft Spawnrate")
				@Config.Comment({
					"How often Mineshafts will spawn.",
					"0 for no Mineshafts and 25 for default spawnrate."
					})
				@Config.RangeDouble(min = 0, max = 1000)
				public double mineshaftSpawnrate = 25;
				
				@Name("Mineshaft Allowed?")
				@Config.Comment("Controls whether Mineshafts spawn or not.")
				public boolean mineshaftGeneration = true;
				
				
				
				@Name("Woodland Mansion Rarity")
				@Config.Comment({
					"How rare are Woodland Mansion.",
					"9 for Woodland Mansion spawning in most chunk and 16 for default spawnrate."
					})
				@Config.RangeInt(min = 1, max = 100)
				public int mansionSpawnrate = 16;
				
				@Name("Woodland Mansion Allowed?")
				@Config.Comment("Controls whether Woodland Mansion spawn or not.")
				public boolean mansionGeneration = true;
				
				
				
				@Name("Scattered Structure Rarity")
				@Config.Comment({
					"How rare are Temples, End Cities, Nether Fortresses, Igloos, and Witch Huts.",
					"9 for a Scattered Structure spawning in most chunk and 16 for default spawnrate."
					})
				@Config.RangeInt(min = 1, max = 100)
				public int scatteredSpawnrate = 16;
				
				@Name("Scattered Structure Allowed?")
				@Config.Comment("Controls whether Temples, End Cities, Nether Fortresses, Igloos, and Witch Huts spawn or not.")
				public boolean scatteredGeneration = true;
				
				
				
				@Name("Strongholds Distance")
				@Config.Comment({
					"Minimum closest distance Strongholds can spawn to each other.",
					"1 for clostest distance and 10 for default distance between Strongholds."
					})
				@Config.RangeDouble(min = 1, max = 1000)
				public float strongholdDistance = 10.0F;
				
				@Name("Strongholds Count")
				@Config.Comment({
					"How many Strongholds spawn in a world.",
					"1 for 1 Strongholds to spawn and 128 for default number of Strongholds."
					})
				@Config.RangeInt(min = 1, max = 10000)
				public int strongholdNumberPerWorld = 128;
				
				@Name("Strongholds Spread")
				@Config.Comment({
					"How clustered towards spawn the Strongholds will be.",
					"1 for Strongholds to be farthest from spawn and 4 for default spawn distance."
					})
				@Config.RangeInt(min = 1, max = 100)
				public int strongholdSpread = 4;
				
				@Name("Strongholds Allowed?")
				@Config.Comment("Controls whether Strongholds spawn or not.")
				public boolean strongholdGeneration = true;
				
				
				
				@Name("Ocean Monument Rarity")
				@Config.Comment({
					"How rare are Ocean Monuments.",
					"9 for a Ocean Monument spawning in most Jungle chunks and 18 for default spawnrate."
					})
				@Config.RangeInt(min = 1, max = 100)
				public int monumentRarity = 10;
				
				@Name("Ocean Monuments Allowed?")
				@Config.Comment("Controls whether Ocean Monuments spawn or not. (These spawn in Jungle biomes)")
				public boolean monumentGeneration = true;
			}
			
		  
			
		    @Name("Dungeon Spawnrate")
		    @Config.Comment({
				"How often Dungeons will spawn.",
				"0 for no Dungeons and 650 for default spawnrate."
				})
			@Config.RangeInt(min = 0, max = 1200)
			public int dungeonSpawnrate = 650;
		  
		    @Name("Ravine Spawnrate")
			@Config.Comment({
				"How often Ravines will spawn.",
				"0 for no Ravines and 5 for default spawnrate."
				})
			@Config.RangeInt(min = 0, max = 8)
			public int ravineSpawnrate = 5;
			
			@Name("Cave Cavity Spawnrate")
			@Config.Comment({
				"How often Cave Cavity will spawn.",
				"0 for no Cave Cavity and 350 for default spawnrate."
				})
			@Config.RangeInt(min = 0, max = 700)
			public int caveCavitySpawnrate = 350;
			
			
			
			@Name("Slime Lakes Allowed?")
			@Config.Comment("Controls whether Slime Lakes spawn or not.")
			public boolean slimeLakeGen = true;
			
			@Name("Water Lakes Allowed?")
			@Config.Comment("Controls whether Water Lakes spawn or not.")
			public boolean waterLakeGen = true;
			
			@Name("Lava Lakes Allowed?")
			@Config.Comment("Controls whether Lava Lakes spawn or not. (DOES NOT AFFECT NETHER BIOME)")
			public boolean lavaLakeGen = true;
	  }
	
	
	@Name("Biome and Terrain Options")
	@Config.Comment("Control generation of biomes and terrain. (Warning: Takes effect right away for all unloaded chunks in all Ultra Amplified worlds!)")
	public static BiomeSubCategory biomeOptions = new BiomeSubCategory();
	  public static class BiomeSubCategory {
		  @Name("Biome Size")
		  @Config.Comment("How large the biomes are. Bigger number means bigger biomes.")
		  @Config.RangeInt(min = 2, max = 8)
		  public int biomeSize = 4;
		  
		  @Name("Sea Level")
		  @Config.Comment("Height at which air is replace with water aboveground.")
		  @Config.RangeInt(min = 65, max = 250)
		  public int seaLevel = 75;
		  
		  @Name("Replace Sea Level with Lava?")
		  @Config.Comment("Replace the water at sea leve with lava instead.")
		  public boolean lavaOcean = false;
		  
		  @Name("Replace Sea Level with Lava?")
		  @Config.Comment({
				"How often waterfalls will spawn.",
				"0 for no waterfalls and 100 for default spawnrate."
				})
		  @Config.RangeDouble(min = 0, max = 500)
		  public double waterfallSpawnrate = 100;
		  
		  @Name("Replace Sea Level with Lava?")
		  @Config.Comment({
				"How often lavafalls will spawn. (Does not affect Nether Biome)",
				"0 for no lavafalls and 100 for default spawnrate."
				})
		  @Config.RangeDouble(min = 0, max = 500)
		  public double lavafallSpawnrate = 100;
		  
		  @Name("End Islands")
		  @Config.Comment({
				"How often End Islands will spawn in the End Biome.",
				"0 for no End Islands and 100 for default spawnrate."
				})
	      @Config.RangeDouble(min = 0, max = 500)
		  public double endIslandSpawnrate = 100;
		  
		  
		  @Name("Biome Selection Options")
			@Config.Comment("Control what biomes to generate. (Warning: Takes effect at start when entering any Ultra Amplified world for all unloaded chunks!)")
			public MainStructureSubCategory biomeSelectionOptions = new MainStructureSubCategory();
			public class MainStructureSubCategory {
				
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
	  }
	  
	  
	
	@Name("Ores and Similar Generations")
	@Config.Comment("Control spawnrate of ores and other similar generations.  (Warning: Takes effect right away for all unloaded chunks in all Ultra Amplified worlds!)")
	public static OreSubCategory oreAndFeatures = new OreSubCategory();
	  public static class OreSubCategory {
		  
		  @Config.Comment({
				"How often ores will spawn.",
				"0 for no ores and 100 for default spawnrate."
				})
			@Config.RangeDouble(min = 0, max = 500)
			public double oreSpawnrate = 100;
			
			@Config.Comment({
				"How often Glowstone will spawn in Nether biome.",
				"0 for no Glowstone and 100 for default spawnrate."
				})
			@Config.RangeDouble(min = 0, max = 500)
			public double glowstoneSpawnrate = 100;
			
			@Config.Comment({
				"How often Silverfish Blocks will spawn in Extreme Hills and Strongholds.",
				"0 for no ores and 100 for default spawnrate."
				})
			@Config.RangeDouble(min = 0, max = 500)
			public double silverfishSpawnrate = 100;
			
			
	  }
	
	
	
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
