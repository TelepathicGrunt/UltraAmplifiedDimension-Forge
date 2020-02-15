package net.telepathicgrunt.ultraamplified.world.generation.layers;

import com.mojang.datafixers.util.Pair;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;


public enum BiomeEdgeLayerUA implements ICastleTransformer
{
	INSTANCE;
	

	public int apply(INoiseRandom context, int north, int west, int south, int east, int currentBiomeID)
	{
		// Check if north made a biome edge
		int borderBiome = BiomeGenHelper.biomesComboToEdge.getOrDefault(new Pair<Integer, Integer>(currentBiomeID, north), -1);
		if(borderBiome != -1)
		{
			return borderBiome;
		}

		// Check if west made a biome edge
		borderBiome = BiomeGenHelper.biomesComboToEdge.getOrDefault(new Pair<Integer, Integer>(currentBiomeID, west), -1);
		if(borderBiome != -1)
		{
			return borderBiome;
		}
		
		// Check if south made a biome edge
		borderBiome = BiomeGenHelper.biomesComboToEdge.getOrDefault(new Pair<Integer, Integer>(currentBiomeID, south), -1);
		if(borderBiome != -1)
		{
			return borderBiome;
		}
		
		// Check if east made a biome edge
		borderBiome = BiomeGenHelper.biomesComboToEdge.getOrDefault(new Pair<Integer, Integer>(currentBiomeID, east), -1);
		if(borderBiome != -1)
		{
			return borderBiome;
		}

		// No biome edge was made.
		return currentBiomeID;
	}

}