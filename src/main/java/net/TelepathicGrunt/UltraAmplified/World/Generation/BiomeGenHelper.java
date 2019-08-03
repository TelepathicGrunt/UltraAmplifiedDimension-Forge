package net.TelepathicGrunt.UltraAmplified.World.Generation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

@SuppressWarnings("deprecation")
public enum BiomeGenHelper {
   instance;
	
   public static final int PLAINS = Registry.BIOME.getId(BiomeInit.PLAINS);
   public static final int SWAMP = Registry.BIOME.getId(BiomeInit.SWAMP);
   public static final int FOREST = Registry.BIOME.getId(BiomeInit.FOREST);
   public static final int WOODED_HILLS = Registry.BIOME.getId(BiomeInit.WOODED_HILLS);
   public static final int BIRCH_FOREST = Registry.BIOME.getId(BiomeInit.BIRCH_FOREST);
   public static final int BIRCH_FOREST_HILLS = Registry.BIOME.getId(BiomeInit.BIRCH_FOREST_HILLS);
   public static final int DESERT = Registry.BIOME.getId(BiomeInit.DESERT);
   public static final int DESERT_HILLS = Registry.BIOME.getId(BiomeInit.DESERT_HILLS);
   public static final int MOUNTAINS = Registry.BIOME.getId(BiomeInit.MOUNTAINS);
   public static final int WOODED_MOUNTAINS = Registry.BIOME.getId(BiomeInit.WOODED_MOUNTAINS);
   public static final int SNOWY_TUNDRA = Registry.BIOME.getId(BiomeInit.SNOWY_TUNDRA);
   public static final int ICE_MOUNTAINS = Registry.BIOME.getId(BiomeInit.ICE_MOUNTAIN);
   public static final int JUNGLE = Registry.BIOME.getId(BiomeInit.JUNGLE);
   public static final int JUNGLE_HILLS = Registry.BIOME.getId(BiomeInit.JUNGLE_HILLS);
   public static final int JUNGLE_EDGE = Registry.BIOME.getId(BiomeInit.JUNGLE_EDGE);
   public static final int BADLANDS = Registry.BIOME.getId(BiomeInit.BADLANDS);
   public static final int WOODED_BADLANDS_PLATEAU = Registry.BIOME.getId(BiomeInit.WOODED_BADLANDS_PLATEAU);
   public static final int BADLANDS_PLATEAU = Registry.BIOME.getId(BiomeInit.BADLANDS_PLATEAU);
   public static final int ERODED_BADLANDS = Registry.BIOME.getId(BiomeInit.ERODED_BADLANDS);
   public static final int MODIFIED_BADLANDS_PLATEAU = Registry.BIOME.getId(BiomeInit.MODIFIED_BADLANDS_PLATEAU);
   public static final int MODIFIED_WOODED_BADLANDS_PLATEAU = Registry.BIOME.getId(BiomeInit.MODIFIED_WOODED_BADLANDS_PLATEAU);
   public static final int MUSHROOM_FIELDS = Registry.BIOME.getId(BiomeInit.MUSHROOM_FIELDS);
   public static final int GIANT_TREE_TAIGA = Registry.BIOME.getId(BiomeInit.GIANT_TREE_TAIGA);
   public static final int GIANT_TREE_TAIGA_HILLS = Registry.BIOME.getId(BiomeInit.GIANT_TREE_TAIGA_HILLS);
   public static final int DARK_FOREST = Registry.BIOME.getId(BiomeInit.DARK_FOREST);
   public static final int SAVANNA = Registry.BIOME.getId(BiomeInit.SAVANNA);
   public static final int SAVANA_PLATEAU = Registry.BIOME.getId(BiomeInit.SAVANNA_PLATEAU);
   public static final int TAIGA = Registry.BIOME.getId(BiomeInit.TAIGA);
   public static final int SNOWY_TAIGA = Registry.BIOME.getId(BiomeInit.SNOWY_TAIGA);
   public static final int SNOWY_TAIGA_HILLS = Registry.BIOME.getId(BiomeInit.SNOWY_TAIGA_HILLS);
   public static final int TAIGA_HILLS = Registry.BIOME.getId(BiomeInit.TAIGA);
   public static final int WARM_OCEAN = Registry.BIOME.getId(BiomeInit.WARM_OCEAN);
   public static final int LUKEWARM_OCEAN = Registry.BIOME.getId(BiomeInit.LUKEWARM_OCEAN);
   public static final int OCEAN = Registry.BIOME.getId(BiomeInit.OCEAN);
   public static final int COLD_OCEAN = Registry.BIOME.getId(BiomeInit.COLD_OCEAN);
   public static final int FROZEN_OCEAN = Registry.BIOME.getId(BiomeInit.FROZEN_OCEAN);
   public static final int DEEP_WARM_OCEAN = Registry.BIOME.getId(BiomeInit.DEEP_WARM_OCEAN);
   public static final int DEEP_LUKEWARM_OCEAN = Registry.BIOME.getId(BiomeInit.DEEP_LUKEWARM_OCEAN);
   public static final int DEEP_OCEAN = Registry.BIOME.getId(BiomeInit.DEEP_OCEAN);
   public static final int DEEP_COLD_OCEAN = Registry.BIOME.getId(BiomeInit.DEEP_COLD_OCEAN);
   public static final int DEEP_FROZEN_OCEAN = Registry.BIOME.getId(BiomeInit.DEEP_FROZEN_OCEAN);
   public static final int SNOWY_BEACH = Registry.BIOME.getId(BiomeInit.SNOWY_BEACH);
   public static final int STONE_SHORE = Registry.BIOME.getId(BiomeInit.STONE_SHORE);
   public static final int SUNFLOWER_PLAINS = Registry.BIOME.getId(BiomeInit.SUNFLOWER_PLAINS);
   public static final int NETHER = Registry.BIOME.getId(BiomeInit.NETHER);

   public static final int VANILLA_OCEAN = Registry.BIOME.getId(Biomes.OCEAN);
   public static final int VANILLA_DEEP_OCEAN = Registry.BIOME.getId(Biomes.DEEP_OCEAN);
	
   private static final Biome[] OCEAN_VALUES = new Biome[] { 
		   BiomeInit.WARM_OCEAN,
		   BiomeInit.LUKEWARM_OCEAN,
		   BiomeInit.OCEAN,
		   BiomeInit.COLD_OCEAN,
		   BiomeInit.FROZEN_OCEAN,
		   BiomeInit.DEEP_WARM_OCEAN,
		   BiomeInit.DEEP_LUKEWARM_OCEAN,
		   BiomeInit.DEEP_OCEAN,
		   BiomeInit.DEEP_COLD_OCEAN,
		   BiomeInit.DEEP_FROZEN_OCEAN
		   };
   private static final Set<Biome> OCEAN_LIST = new HashSet<Biome>(Arrays.asList(OCEAN_VALUES)); 
   
   public static boolean isOcean(Biome biomeIn) {
	   return OCEAN_LIST.contains(biomeIn);
   }
   
   public static boolean isOcean(int biomeIn) {
      return biomeIn == VANILLA_OCEAN || biomeIn == VANILLA_DEEP_OCEAN || biomeIn == WARM_OCEAN || biomeIn == LUKEWARM_OCEAN || biomeIn == OCEAN || biomeIn == COLD_OCEAN || biomeIn == FROZEN_OCEAN || biomeIn == DEEP_WARM_OCEAN || biomeIn == DEEP_LUKEWARM_OCEAN || biomeIn == DEEP_OCEAN || biomeIn == DEEP_COLD_OCEAN || biomeIn == DEEP_FROZEN_OCEAN;
   }

   public static boolean isShallowOcean(int biomeIn) {
      return biomeIn == VANILLA_OCEAN || biomeIn == WARM_OCEAN || biomeIn == LUKEWARM_OCEAN || biomeIn == OCEAN || biomeIn == COLD_OCEAN || biomeIn == FROZEN_OCEAN;
   }
	
}
