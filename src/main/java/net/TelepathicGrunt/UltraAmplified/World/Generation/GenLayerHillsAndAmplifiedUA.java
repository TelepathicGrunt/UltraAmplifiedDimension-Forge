package net.TelepathicGrunt.UltraAmplified.World.Generation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
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
   private static final Logger LOGGER = LogManager.getLogger();
   


   public int apply(IContext context, AreaDimension dimensionIn, IArea area1, IArea area2, int x, int z) {
      int i = area1.getValue(x + 1, z + 1);
      int j = area2.getValue(x + 1, z + 1);
      if (i > 255) {
         LOGGER.debug("old! {}", (int)i);
      }

      int remainder = (j - 2) % 29;
      
      if ((!BiomeGenHelper.isShallowOcean(i) && i != 0 && j >= 2 && remainder != 0 && remainder <= Config.mutatedBiomeSpawnrate - 1) || Config.mutatedBiomeSpawnrate == 29) {
         Biome biome = IRegistry.field_212624_m.get(i);
         if (biome == null || !biome.isMutation()) {
            Biome biome2 = BiomeInit.BASE_TO_MUTATION_MAP.get(biome);
            return biome2 == null ? i : IRegistry.field_212624_m.getId(biome2);
         }
      }

      if (context.random(3) == 0 || remainder == 0) {
         int l = i;
         
         if (BiomeInit.BASE_TO_HILLS_MAP.containsKey(i)) {
        	 l = BiomeInit.BASE_TO_HILLS_MAP.get(i);
         }
         else if (i == BiomeGenHelper.DARK_FOREST && Config.plains) {
         	//makes sure plains is selected in the config setting before allowing it to spawn through here
            l = BiomeGenHelper.PLAINS;
         }else if (i == BiomeGenHelper.PLAINS && Config.forest) {
        	//makes sure forest is selected in the config setting before allowing it to spawn through here
            l = context.random(3) == 0 ? BiomeGenHelper.WOODED_HILLS : BiomeGenHelper.FOREST;
         } 
         else if (i == BiomeGenHelper.SNOWY_TUNDRA && Config.iceMountain) {
            l = BiomeGenHelper.SNOWY_MOUNTAINS;
         } 
         else if (i == BiomeGenHelper.OCEAN) {
            l = BiomeGenHelper.DEEP_OCEAN;
         } 
         else if (i == BiomeGenHelper.LUKEWARM_OCEAN) {
            l = BiomeGenHelper.DEEP_LUKEWARM_OCEAN;
         } 
         else if (i == BiomeGenHelper.COLD_OCEAN) {
            l = BiomeGenHelper.DEEP_COLD_OCEAN;
         } 
         else if (i == BiomeGenHelper.FROZEN_OCEAN) {
            l = BiomeGenHelper.DEEP_FROZEN_OCEAN;
         }
         else if (LayerUtil.areBiomesSimilar(i, BiomeGenHelper.WOODED_BADLANDS_PLATEAU)) {
            l = BiomeGenHelper.BADLANDS;
         } 
         else if ((i == BiomeGenHelper.DEEP_OCEAN || i == BiomeGenHelper.DEEP_LUKEWARM_OCEAN || i == BiomeGenHelper.DEEP_COLD_OCEAN || i == BiomeGenHelper.DEEP_FROZEN_OCEAN) && context.random(3) == 0) {
            l = context.random(2) == 0 ? BiomeGenHelper.PLAINS : BiomeGenHelper.FOREST;
         }

         if (remainder == 0 && l != i) {
            Biome biome1 = BiomeInit.BASE_TO_MUTATION_MAP.get(IRegistry.field_212624_m.get(l));
            l = biome1 == null ? i : IRegistry.field_212624_m.getId(biome1);
         }

         if (l != i) {
            int i1 = 0;
            if (LayerUtil.areBiomesSimilar(area1.getValue(x + 1, z + 0), i)) {
               ++i1;
            }

            if (LayerUtil.areBiomesSimilar(area1.getValue(x + 2, z + 1), i)) {
               ++i1;
            }

            if (LayerUtil.areBiomesSimilar(area1.getValue(x + 0, z + 1), i)) {
               ++i1;
            }

            if (LayerUtil.areBiomesSimilar(area1.getValue(x + 1, z + 2), i)) {
               ++i1;
            }

            if (i1 >= 3) {
               return l;
            }
         }
      }

      return i;
   }
}