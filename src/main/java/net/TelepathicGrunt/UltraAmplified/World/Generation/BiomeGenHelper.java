package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;

public enum BiomeGenHelper {
   instance;
	
   public static final int BIRCH_FOREST = IRegistry.field_212624_m.getId(BiomeInit.BiomeBirchForest);
   public static final int BIRCH_FOREST_HILLS = IRegistry.field_212624_m.getId(BiomeInit.BiomeBirchForestHills);
   public static final int DESERT = IRegistry.field_212624_m.getId(BiomeInit.BiomeDesert);
   public static final int DESERT_HILLS = IRegistry.field_212624_m.getId(BiomeInit.BiomeDesertHills);
   public static final int MOUNTAINS = IRegistry.field_212624_m.getId(BiomeInit.BiomeExtremeHills);
   public static final int WOODED_MOUNTAINS = IRegistry.field_212624_m.getId(BiomeInit.BiomeExtremeHillsPlus);
   public static final int FOREST = IRegistry.field_212624_m.getId(BiomeInit.BiomeForest);
   public static final int WOODED_HILLS = IRegistry.field_212624_m.getId(BiomeInit.BiomeWoodedHills);
   public static final int SNOWY_TUNDRA = IRegistry.field_212624_m.getId(BiomeInit.BiomeIceFlats);
   public static final int SNOWY_MOUNTAINS = IRegistry.field_212624_m.getId(BiomeInit.BiomeIceMountain);
   public static final int JUNGLE = IRegistry.field_212624_m.getId(BiomeInit.BiomeJungle);
   public static final int JUNGLE_HILLS = IRegistry.field_212624_m.getId(BiomeInit.BiomeJungleHills);
   public static final int BADLANDS = IRegistry.field_212624_m.getId(BiomeInit.BiomeMesa);
   public static final int WOODED_BADLANDS_PLATEAU = IRegistry.field_212624_m.getId(BiomeInit.BiomeMesaRock);
   public static final int BADLANDS_PLATEAU = IRegistry.field_212624_m.getId(BiomeInit.BiomeMesaClearRock);
   public static final int MUSHROOM_FIELDS = IRegistry.field_212624_m.getId(BiomeInit.BiomeMushroomIsland);
   public static final int PLAINS = IRegistry.field_212624_m.getId(BiomeInit.BiomePlains);
   public static final int GIANT_TREE_TAIGA = IRegistry.field_212624_m.getId(BiomeInit.BiomeRedwoodTaiga);
   public static final int GIANT_TREE_TAIGA_HILLS = IRegistry.field_212624_m.getId(BiomeInit.BiomeRedwoodTaigaHills);
   public static final int DARK_FOREST = IRegistry.field_212624_m.getId(BiomeInit.BiomeRoofedForest);
   public static final int SAVANNA = IRegistry.field_212624_m.getId(BiomeInit.BiomeSavanna);
   public static final int SAVANA_PLATEAU = IRegistry.field_212624_m.getId(BiomeInit.BiomeSavannaRock);
   public static final int TAIGA = IRegistry.field_212624_m.getId(BiomeInit.BiomeTaiga);
   public static final int SNOWY_TAIGA = IRegistry.field_212624_m.getId(BiomeInit.BiomeColdTaiga);
   public static final int SNOWY_TAIGA_HILLS = IRegistry.field_212624_m.getId(BiomeInit.BiomeColdTaigaHills);
   public static final int TAIGA_HILLS = IRegistry.field_212624_m.getId(BiomeInit.BiomeTaiga);
   public static final int WARM_OCEAN = IRegistry.field_212624_m.getId(Biomes.WARM_OCEAN);
   public static final int LUKEWARM_OCEAN = IRegistry.field_212624_m.getId(Biomes.LUKEWARM_OCEAN);
   public static final int OCEAN = IRegistry.field_212624_m.getId(Biomes.OCEAN);
   public static final int COLD_OCEAN = IRegistry.field_212624_m.getId(Biomes.COLD_OCEAN);
   public static final int FROZEN_OCEAN = IRegistry.field_212624_m.getId(Biomes.FROZEN_OCEAN);
   public static final int DEEP_WARM_OCEAN = IRegistry.field_212624_m.getId(Biomes.DEEP_WARM_OCEAN);
   public static final int DEEP_LUKEWARM_OCEAN = IRegistry.field_212624_m.getId(Biomes.DEEP_LUKEWARM_OCEAN);
   public static final int DEEP_OCEAN = IRegistry.field_212624_m.getId(Biomes.DEEP_OCEAN);
   public static final int DEEP_COLD_OCEAN = IRegistry.field_212624_m.getId(Biomes.DEEP_COLD_OCEAN);
   public static final int DEEP_FROZEN_OCEAN = IRegistry.field_212624_m.getId(Biomes.DEEP_FROZEN_OCEAN);
	
   public static boolean isOcean(int biomeIn) {
      return biomeIn == WARM_OCEAN || biomeIn == LUKEWARM_OCEAN || biomeIn == OCEAN || biomeIn == COLD_OCEAN || biomeIn == FROZEN_OCEAN || biomeIn == DEEP_WARM_OCEAN || biomeIn == DEEP_LUKEWARM_OCEAN || biomeIn == DEEP_OCEAN || biomeIn == DEEP_COLD_OCEAN || biomeIn == DEEP_FROZEN_OCEAN;
   }

   public static boolean isShallowOcean(int biomeIn) {
      return biomeIn == WARM_OCEAN || biomeIn == LUKEWARM_OCEAN || biomeIn == OCEAN || biomeIn == COLD_OCEAN || biomeIn == FROZEN_OCEAN;
   }
	
}
