package net.TelepathicGrunt.UltraAmplified.World.Generation.Layers;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.TelepathicGrunt.UltraAmplified.World.Generation.BiomeGenHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public enum HillsAndAmplifiedLayerUA implements IAreaTransformer2, IDimOffset1Transformer {
   INSTANCE;
/*
 * This class generates M variants and Hills variants of biomes if they exist for that biome
 */

    private static ForgeRegistry<Biome> BiomeRegistry = ((ForgeRegistry<Biome>)ForgeRegistries.BIOMES);


   public int apply(INoiseRandom context, IArea area1, IArea area2, int x, int z) {
      int biomeId1 = area1.getValue(x + 1, z + 1);

      //first way to create m variant biomes of base biomes
      int mFormValue = context.random(30 - ConfigUA.mutatedBiomeSpawnrate);
      if (!BiomeGenHelper.isShallowOcean(biomeId1) && (mFormValue == 1 || ConfigUA.mutatedBiomeSpawnrate == 28)) {
         Biome biome = BiomeRegistry.getValue(biomeId1);
         if (biome == null || !biome.isMutation()) {
            Biome biome2 = BiomeInit.BASE_TO_MUTATION_MAP.get(biome);
            return biome2 == null ? biomeId1 : BiomeRegistry.getID(biome2);
         }
      }
      

      //creates hills variants of biomes
      if (context.random(3) == 0 || mFormValue == 0) {
         int biomeIdToReturn = biomeId1;
         
         if (BiomeInit.BASE_TO_HILLS_MAP.containsKey(biomeId1)) {
        	 biomeIdToReturn = BiomeInit.BASE_TO_HILLS_MAP.get(biomeId1);
         }
         else if (biomeId1 == BiomeGenHelper.DARK_FOREST && ConfigUA.plains) {
         	//makes sure plains is selected in the config setting before allowing it to spawn through here
            biomeIdToReturn = BiomeGenHelper.PLAINS;
         }else if (biomeId1 == BiomeGenHelper.PLAINS && ConfigUA.forest) {
        	//makes sure forest is selected in the config setting before allowing it to spawn through here
            biomeIdToReturn = context.random(3) == 0 ? BiomeGenHelper.WOODED_HILLS : BiomeGenHelper.FOREST;
         } 
         else if (biomeId1 == BiomeGenHelper.SNOWY_TUNDRA && ConfigUA.iceMountain) {
            biomeIdToReturn = BiomeGenHelper.ICE_MOUNTAINS;
         } 
         else if (BiomeGenHelper.areUABiomesSimilar(biomeId1, BiomeGenHelper.WOODED_BADLANDS_PLATEAU)) {
            biomeIdToReturn = BiomeGenHelper.BADLANDS;
         }

         
         //second way to create m variant biomes of both base and hills biomes
         if (ConfigUA.mutatedBiomeSpawnrate != 0 && mFormValue == 0 && biomeIdToReturn != biomeId1) {
            Biome biomeTemp = BiomeInit.BASE_TO_MUTATION_MAP.get(BiomeRegistry.getValue(biomeIdToReturn));
            biomeIdToReturn = biomeTemp == null ? biomeId1 : BiomeRegistry.getID(biomeTemp);
         }

         
         //returns hills and m variant biome if it is surrounded by similar biomes on atleast 3 sides
         if (biomeIdToReturn != biomeId1) {
            int i1 = 0;
            if (BiomeGenHelper.areUABiomesSimilar(area1.getValue(x + 1, z + 0), biomeId1)) {
               ++i1;
            }

            if (BiomeGenHelper.areUABiomesSimilar(area1.getValue(x + 2, z + 1), biomeId1)) {
               ++i1;
            }

            if (BiomeGenHelper.areUABiomesSimilar(area1.getValue(x + 0, z + 1), biomeId1)) {
               ++i1;
            }

            if (BiomeGenHelper.areUABiomesSimilar(area1.getValue(x + 1, z + 2), biomeId1)) {
               ++i1;
            }

            if (i1 >= 3) {
               return biomeIdToReturn;
            }
         }
      }

      return biomeId1;
   }
   

}