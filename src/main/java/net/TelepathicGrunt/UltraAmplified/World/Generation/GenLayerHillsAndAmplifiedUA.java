package net.TelepathicGrunt.UltraAmplified.World.Generation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;

public enum GenLayerHillsAndAmplifiedUA implements IAreaTransformer2, IDimOffset1Transformer {
   INSTANCE;
/*
 * This class generates M variants and Hills variants of biomes if they exist for that biome
 */
   


   public int apply(IContext context, AreaDimension dimensionIn, IArea area1, IArea area2, int x, int z) {
      int biomeId1 = area1.getValue(x + 1, z + 1);
      int biomeId2 = area2.getValue(x + 1, z + 1);

      
      int remainder = (biomeId2 - 2) % 29;
      if (!BiomeGenHelper.isShallowOcean(biomeId1) && biomeId2 >= 2 && remainder != 0 && remainder <= ConfigUA.mutatedBiomeSpawnrate) {
         Biome biome = IRegistry.field_212624_m.get(biomeId1);
         if (biome == null || !biome.isMutation()) {
            Biome biome2 = BiomeInit.BASE_TO_MUTATION_MAP.get(biome);
            return biome2 == null ? biomeId1 : IRegistry.field_212624_m.getId(biome2);
         }
      }
      

      if (context.random(3) == 0 || remainder == 0) {
         int l = biomeId1;
         
         if (BiomeInit.BASE_TO_HILLS_MAP.containsKey(biomeId1)) {
        	 l = BiomeInit.BASE_TO_HILLS_MAP.get(biomeId1);
         }
         else if (biomeId1 == BiomeGenHelper.DARK_FOREST && ConfigUA.plains) {
         	//makes sure plains is selected in the config setting before allowing it to spawn through here
            l = BiomeGenHelper.PLAINS;
         }else if (biomeId1 == BiomeGenHelper.PLAINS && ConfigUA.forest) {
        	//makes sure forest is selected in the config setting before allowing it to spawn through here
            l = context.random(3) == 0 ? BiomeGenHelper.WOODED_HILLS : BiomeGenHelper.FOREST;
         } 
         else if (biomeId1 == BiomeGenHelper.SNOWY_TUNDRA && ConfigUA.iceMountain) {
            l = BiomeGenHelper.SNOWY_MOUNTAINS;
         } 
         else if (biomeId1 == BiomeGenHelper.OCEAN) {
            l = BiomeGenHelper.DEEP_OCEAN;
         } 
         else if (biomeId1 == BiomeGenHelper.LUKEWARM_OCEAN) {
            l = BiomeGenHelper.DEEP_LUKEWARM_OCEAN;
         } 
         else if (biomeId1 == BiomeGenHelper.COLD_OCEAN) {
            l = BiomeGenHelper.DEEP_COLD_OCEAN;
         } 
         else if (biomeId1 == BiomeGenHelper.FROZEN_OCEAN) {
            l = BiomeGenHelper.DEEP_FROZEN_OCEAN;
         }
         else if (biomeId1 == BiomeGenHelper.WARM_OCEAN) {
             l = BiomeGenHelper.DEEP_WARM_OCEAN;
          }
         else if (LayerUtil.areBiomesSimilar(biomeId1, BiomeGenHelper.WOODED_BADLANDS_PLATEAU)) {
            l = BiomeGenHelper.BADLANDS;
         }

//         if (remainder == 0 && l != biomeId1) {
//            Biome biome1 = BiomeInit.BASE_TO_MUTATION_MAP.get(IRegistry.field_212624_m.get(l));
//            l = biome1 == null ? biomeId1 : IRegistry.field_212624_m.getId(biome1);
//         }

         if (l != biomeId1) {
            int i1 = 0;
            if (LayerUtil.areBiomesSimilar(area1.getValue(x + 1, z + 0), biomeId1)) {
               ++i1;
            }

            if (LayerUtil.areBiomesSimilar(area1.getValue(x + 2, z + 1), biomeId1)) {
               ++i1;
            }

            if (LayerUtil.areBiomesSimilar(area1.getValue(x + 0, z + 1), biomeId1)) {
               ++i1;
            }

            if (LayerUtil.areBiomesSimilar(area1.getValue(x + 1, z + 2), biomeId1)) {
               ++i1;
            }

            if (i1 >= 3) {
               return l;
            }
         }
      }

      return biomeId1;
   }
}