package net.telepathicgrunt.ultraamplified.world.generation.layers;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.telepathicgrunt.ultraamplified.world.biome.UABiomes;


public class BiomeDebugLayer implements IC0Transformer
{

	@SuppressWarnings("deprecation")
	private static final int TESTING_BIOME = Registry.BIOME.getId(UABiomes.BARREN_END_FIELD);


	public BiomeDebugLayer(WorldType p_i48641_1_, OverworldGenSettings p_i48641_2_)
	{
	}


	@Override
	public int apply(INoiseRandom context, int value)
	{
		return TESTING_BIOME;
	}

}