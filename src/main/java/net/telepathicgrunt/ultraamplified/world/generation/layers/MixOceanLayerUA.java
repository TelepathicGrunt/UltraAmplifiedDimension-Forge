package net.telepathicgrunt.ultraamplified.world.generation.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.biome.UABiomes;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;


public enum MixOceanLayerUA implements IAreaTransformer2, IDimOffset0Transformer
{
	INSTANCE;

	@Override
	public int apply(INoiseRandom context, IArea area1, IArea area2, int x, int z)
	{
		int biome1 = area1.getValue(x, z);
		int biome2 = area2.getValue(x, z);
		if (!BiomeGenHelper.isOcean(biome1))
		{
			return biome1;
		}
		else
		{
			for (int xOffset = -8; xOffset <= 8; xOffset += 4)
			{
				for (int zOffset = -8; zOffset <= 8; zOffset += 4)
				{
					int nearbyBiome = area1.getValue(x + xOffset, z + zOffset);
					if (!BiomeGenHelper.isOcean(nearbyBiome))
					{
						if (biome2 == BiomeGenHelper.WARM_OCEAN)
						{
							if (UltraAmplified.UAConfig.lukewarmOcean.get())
								return BiomeGenHelper.LUKEWARM_OCEAN;
						}

						if (biome2 == BiomeGenHelper.FROZEN_OCEAN)
						{
							if (UltraAmplified.UAConfig.coldOcean.get())
								return BiomeGenHelper.COLD_OCEAN;
						}
					}
				}
			}

			if (biome1 == BiomeGenHelper.DEEP_OCEAN)
			{
				if (biome2 != BiomeGenHelper.WARM_OCEAN)
				{
				    //Turns the shallow ocean to their deep variant
				    return UABiomes.BASE_TO_HILLS_MAP.get(biome2);
				}
			}

			return biome2;
		}
	}
}
