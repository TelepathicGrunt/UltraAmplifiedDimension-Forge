package net.telepathicgrunt.ultraamplified.world.generation.layers;

import java.util.List;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;


public class BiomeLayerPickerUA implements IC0Transformer
{
	BiomeLayerSetupUA biomeLayer;
	private static ForgeRegistry<Biome> BiomeRegistry = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES);


	public BiomeLayerPickerUA()
	{
		biomeLayer = new BiomeLayerSetupUA();
	}


	@Override
	public int apply(INoiseRandom context, int biomeID)
	{

		int biomeValueModified = (biomeID & 3840) >> 8;
		biomeID = biomeID & -3841;
		if (!BiomeGenHelper.isOcean(biomeID) && biomeID != BiomeGenHelper.MUSHROOM_FIELDS)
		{
			switch (biomeID)
			{
				case 1:
					if (biomeValueModified > 0)
					{
						return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.badlandsBiomesList, context).biome);
					}

					return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.hotBiomesList, context).biome);
				case 2:
					if (biomeValueModified > 0)
					{
						return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.jungleBiomesList, context).biome);
					}

					return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.warmBiomesList, context).biome);
				case 3:
					if (biomeValueModified > 0)
					{
						return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.giantTreeTaigaBiomesList, context).biome);
					}

					return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.coolBiomesList, context).biome);
				case 4:
					return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.icyBiomesList, context).biome);
				default:

					if (BiomeLayerSetupUA.noOcean)
					{
						return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.oceanBiomesList, context).biome);
					}

					//return 0 which will later be replaced by our oceans in GenLayerMixedOcean
					return biomeID;
			}
		}
		else
		{

			if (BiomeLayerSetupUA.noOcean)
			{
				return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.oceanBiomesList, context).biome);
			}

			//return 0 which will later be replaced by our oceans in GenLayerMixedOcean
			return biomeID;
		}

	}


	//returns a biome with its weight impacting how often it appears
	//this is a modified forge method to work with my own biome lists that are passed in
	protected BiomeEntry getWeightedSpecialBiomeEntry(List<BiomeEntry> list, INoiseRandom context)
	{
		List<BiomeEntry> biomeList = list;
		int totalWeight = WeightedRandom.getTotalWeight(biomeList);
		int weight = context.random(totalWeight);
		return WeightedRandom.getRandomItem(biomeList, weight);
	}
}
