package net.telepathicgrunt.ultraamplified.world.generation.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;


public enum ReduceOceansLayerUA implements ICastleTransformer {
    INSTANCE;

    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
	int oceanCount = 0;

	if(BiomeGenHelper.isShallowOcean(north)) oceanCount++;
	if(BiomeGenHelper.isShallowOcean(west)) oceanCount++;
	if(BiomeGenHelper.isShallowOcean(east)) oceanCount++;
	if(BiomeGenHelper.isShallowOcean(south)) oceanCount++;

	return BiomeGenHelper.isShallowOcean(center) && oceanCount < 3 && oceanCount != 0 ? 1 : center;
    }
}