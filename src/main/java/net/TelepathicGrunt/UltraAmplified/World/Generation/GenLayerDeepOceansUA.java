package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum GenLayerDeepOceansUA implements ICastleTransformer {
	   INSTANCE;

	   public int apply(IContext context, int north, int south, int west, int east, int currentBiomeID) {
	      if (BiomeGenHelper.isShallowOcean(currentBiomeID)) {
	         int i = 0;
	         if (BiomeGenHelper.isShallowOcean(north)) {
	            ++i;
	         }

	         if (BiomeGenHelper.isShallowOcean(south)) {
	            ++i;
	         }

	         if (BiomeGenHelper.isShallowOcean(east)) {
	            ++i;
	         }

	         if (BiomeGenHelper.isShallowOcean(west)) {
	            ++i;
	         }

	         if (i > 3) {
	            if (currentBiomeID == BiomeGenHelper.WARM_OCEAN) {
	               return BiomeGenHelper.DEEP_WARM_OCEAN;
	            }

	            if (currentBiomeID == BiomeGenHelper.LUKEWARM_OCEAN) {
	               return BiomeGenHelper.DEEP_LUKEWARM_OCEAN;
	            }

	            if (currentBiomeID == BiomeGenHelper.OCEAN) {
	               return BiomeGenHelper.DEEP_OCEAN;
	            }

	            if (currentBiomeID == BiomeGenHelper.COLD_OCEAN) {
	               return BiomeGenHelper.DEEP_COLD_OCEAN;
	            }

	            if (currentBiomeID == BiomeGenHelper.FROZEN_OCEAN) {
	               return BiomeGenHelper.DEEP_FROZEN_OCEAN;
	            }

	            return BiomeGenHelper.DEEP_OCEAN;
	         }
	      }

	      return currentBiomeID;
	   }
	
}