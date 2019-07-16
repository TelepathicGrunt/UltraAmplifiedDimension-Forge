package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IBishopTransformer;

public enum GenLayerAddMushroomBiomeUA implements IBishopTransformer {
	   INSTANCE;

	   private static final int MUSHROOM_FIELDS = BiomeGenHelper.MUSHROOM_FIELDS;

	   public int apply(INoiseRandom context, int x, int p_202792_3_, int p_202792_4_, int p_202792_5_, int p_202792_6_) {
	      return BiomeGenHelper.isShallowOcean(p_202792_6_) && BiomeGenHelper.isShallowOcean(p_202792_5_) && BiomeGenHelper.isShallowOcean(x) && BiomeGenHelper.isShallowOcean(p_202792_4_) && BiomeGenHelper.isShallowOcean(p_202792_3_) && context.random(100) == 0 ? MUSHROOM_FIELDS : p_202792_6_;
	   }
	}