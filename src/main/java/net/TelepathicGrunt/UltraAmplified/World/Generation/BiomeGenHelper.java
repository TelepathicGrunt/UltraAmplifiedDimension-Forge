package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;

@SuppressWarnings("deprecation")
public enum BiomeGenHelper {
   instance;
	
   public static final int PLAINS = IRegistry.field_212624_m.getId(BiomeInit.PLAINS);
   public static final int FOREST = IRegistry.field_212624_m.getId(BiomeInit.FOREST);
   public static final int WOODED_HILLS = IRegistry.field_212624_m.getId(BiomeInit.WOODED_HILLS);
   public static final int BIRCH_FOREST = IRegistry.field_212624_m.getId(BiomeInit.BIRCH_FOREST);
   public static final int BIRCH_FOREST_HILLS = IRegistry.field_212624_m.getId(BiomeInit.BIRCH_FOREST_HILLS);
   public static final int DESERT = IRegistry.field_212624_m.getId(BiomeInit.DESERT);
   public static final int DESERT_HILLS = IRegistry.field_212624_m.getId(BiomeInit.DESERT_HILLS);
   public static final int MOUNTAINS = IRegistry.field_212624_m.getId(BiomeInit.MOUNTAINS);
   public static final int WOODED_MOUNTAINS = IRegistry.field_212624_m.getId(BiomeInit.WOODED_MOUNTAINS);
   public static final int SNOWY_TUNDRA = IRegistry.field_212624_m.getId(BiomeInit.SNOWY_TUNDRA);
   public static final int SNOWY_MOUNTAINS = IRegistry.field_212624_m.getId(BiomeInit.ICE_MOUNTAIN);
   public static final int JUNGLE = IRegistry.field_212624_m.getId(BiomeInit.JUNGLE);
   public static final int JUNGLE_HILLS = IRegistry.field_212624_m.getId(BiomeInit.JUNGLE_HILLS);
   public static final int JUNGLE_EDGE = IRegistry.field_212624_m.getId(BiomeInit.JUNGLE_EDGE);
   public static final int BADLANDS = IRegistry.field_212624_m.getId(BiomeInit.BADLANDS);
   public static final int WOODED_BADLANDS_PLATEAU = IRegistry.field_212624_m.getId(BiomeInit.WOODED_BADLANDS_PLATEAU);
   public static final int BADLANDS_PLATEAU = IRegistry.field_212624_m.getId(BiomeInit.BADLANDS_PLATEAU);
   public static final int ERODED_BADLANDS = IRegistry.field_212624_m.getId(BiomeInit.ERODED_BADLANDS);
   public static final int MODIFIED_BADLANDS_PLATEAU = IRegistry.field_212624_m.getId(BiomeInit.MODIFIED_BADLANDS_PLATEAU);
   public static final int MODIFIED_WOODED_BADLANDS_PLATEAU = IRegistry.field_212624_m.getId(BiomeInit.MODIFIED_WOODED_BADLANDS_PLATEAU);
   public static final int MUSHROOM_FIELDS = IRegistry.field_212624_m.getId(BiomeInit.MUSHROOM_FIELDS);
   public static final int GIANT_TREE_TAIGA = IRegistry.field_212624_m.getId(BiomeInit.GIANT_TREE_TAIGA);
   public static final int GIANT_TREE_TAIGA_HILLS = IRegistry.field_212624_m.getId(BiomeInit.GIANT_TREE_TAIGA_HILLS);
   public static final int DARK_FOREST = IRegistry.field_212624_m.getId(BiomeInit.DARK_FOREST);
   public static final int SAVANNA = IRegistry.field_212624_m.getId(BiomeInit.SAVANNA);
   public static final int SAVANA_PLATEAU = IRegistry.field_212624_m.getId(BiomeInit.SAVANNA_PLATEAU);
   public static final int TAIGA = IRegistry.field_212624_m.getId(BiomeInit.TAIGA);
   public static final int SNOWY_TAIGA = IRegistry.field_212624_m.getId(BiomeInit.SNOWY_TAIGA);
   public static final int SNOWY_TAIGA_HILLS = IRegistry.field_212624_m.getId(BiomeInit.SNOWY_TAIGA_HILLS);
   public static final int TAIGA_HILLS = IRegistry.field_212624_m.getId(BiomeInit.TAIGA);
   public static final int WARM_OCEAN = IRegistry.field_212624_m.getId(BiomeInit.WARM_OCEAN);
   public static final int LUKEWARM_OCEAN = IRegistry.field_212624_m.getId(BiomeInit.LUKEWARM_OCEAN);
   public static final int OCEAN = IRegistry.field_212624_m.getId(BiomeInit.OCEAN);
   public static final int COLD_OCEAN = IRegistry.field_212624_m.getId(BiomeInit.COLD_OCEAN);
   public static final int FROZEN_OCEAN = IRegistry.field_212624_m.getId(BiomeInit.FROZEN_OCEAN);
   public static final int DEEP_WARM_OCEAN = IRegistry.field_212624_m.getId(BiomeInit.DEEP_WARM_OCEAN);
   public static final int DEEP_LUKEWARM_OCEAN = IRegistry.field_212624_m.getId(BiomeInit.DEEP_LUKEWARM_OCEAN);
   public static final int DEEP_OCEAN = IRegistry.field_212624_m.getId(BiomeInit.DEEP_OCEAN);
   public static final int DEEP_COLD_OCEAN = IRegistry.field_212624_m.getId(BiomeInit.DEEP_COLD_OCEAN);
   public static final int DEEP_FROZEN_OCEAN = IRegistry.field_212624_m.getId(BiomeInit.DEEP_FROZEN_OCEAN);
   public static final int SNOWY_BEACH = IRegistry.field_212624_m.getId(BiomeInit.SNOWY_BEACH);
   public static final int STONE_SHORE = IRegistry.field_212624_m.getId(BiomeInit.STONE_SHORE);

   public static final int VANILLA_OCEAN = IRegistry.field_212624_m.getId(Biomes.OCEAN);
   public static final int VANILLA_DEEP_OCEAN = IRegistry.field_212624_m.getId(Biomes.DEEP_OCEAN);
	
   public static boolean isOcean(int biomeIn) {
      return biomeIn == VANILLA_OCEAN || biomeIn == VANILLA_DEEP_OCEAN || biomeIn == WARM_OCEAN || biomeIn == LUKEWARM_OCEAN || biomeIn == OCEAN || biomeIn == COLD_OCEAN || biomeIn == FROZEN_OCEAN || biomeIn == DEEP_WARM_OCEAN || biomeIn == DEEP_LUKEWARM_OCEAN || biomeIn == DEEP_OCEAN || biomeIn == DEEP_COLD_OCEAN || biomeIn == DEEP_FROZEN_OCEAN;
   }

   public static boolean isShallowOcean(int biomeIn) {
      return biomeIn == VANILLA_OCEAN || biomeIn == WARM_OCEAN || biomeIn == LUKEWARM_OCEAN || biomeIn == OCEAN || biomeIn == COLD_OCEAN || biomeIn == FROZEN_OCEAN;
   }
	
}
