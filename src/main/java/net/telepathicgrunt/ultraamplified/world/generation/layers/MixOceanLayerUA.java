package net.telepathicgrunt.ultraamplified.world.generation.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;

public enum MixOceanLayerUA implements IAreaTransformer2, IDimOffset0Transformer {
   INSTANCE;

   public int apply(INoiseRandom context, IArea area1, IArea area2, int x, int z) {
      int i = area1.getValue(x, z);
      int j = area2.getValue(x, z);
      if (!BiomeGenHelper.isOcean(i)) {
         return i;
      } else {
         for(int i1 = -8; i1 <= 8; i1 += 4) {
            for(int j1 = -8; j1 <= 8; j1 += 4) {
               int k1 = area1.getValue(x + i1, z + j1);
               if (!BiomeGenHelper.isOcean(k1)) {
                  if (j == BiomeGenHelper.WARM_OCEAN) {
          			if(ConfigUA.lukewarmOcean) 
                     return BiomeGenHelper.LUKEWARM_OCEAN;
                  }

                  if (j == BiomeGenHelper.FROZEN_OCEAN) {
          			if(ConfigUA.coldOcean) 
                     return BiomeGenHelper.COLD_OCEAN;
                  }
               }
            }
         }

         if (i == BiomeGenHelper.DEEP_OCEAN) {
        	 if(j == BiomeGenHelper.LUKEWARM_OCEAN) {
               return BiomeGenHelper.DEEP_LUKEWARM_OCEAN;
            }

            if (j == BiomeGenHelper.OCEAN) {
               return BiomeGenHelper.DEEP_OCEAN;
            }

            if (j == BiomeGenHelper.COLD_OCEAN) {
               return BiomeGenHelper.DEEP_COLD_OCEAN;
            }

            if (j == BiomeGenHelper.FROZEN_OCEAN) {
               return BiomeGenHelper.DEEP_FROZEN_OCEAN;
            }
         }

         return j;
      }
   }
}
