package net.telepathicgrunt.ultraamplified.world.generation.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;


public enum AddBambooJungleLayerUA implements IC1Transformer
{
	INSTANCE;

	@Override
	public int apply(INoiseRandom context, int value)
	{
		return (context.random(8) == 0 && UltraAmplified.UAConfig.bambooJungle.get()) && value == BiomeGenHelper.BAMBOO_JUNGLE ? BiomeGenHelper.JUNGLE : value;
	}
}