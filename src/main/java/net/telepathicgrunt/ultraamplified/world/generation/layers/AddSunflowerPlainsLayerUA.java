package net.telepathicgrunt.ultraamplified.world.generation.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;


public enum AddSunflowerPlainsLayerUA implements IC1Transformer
{
	INSTANCE;

	@Override
	public int apply(INoiseRandom context, int value)
	{
		return (context.random(200) == 0 && UltraAmplified.UAConfig.mutatedBiomeSpawnrate.get() != 0) && value == BiomeGenHelper.PLAINS ? BiomeGenHelper.SUNFLOWER_PLAINS : value;
	}
}