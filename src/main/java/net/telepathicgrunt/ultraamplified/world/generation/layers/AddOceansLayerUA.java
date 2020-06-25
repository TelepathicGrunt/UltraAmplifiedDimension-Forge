package net.telepathicgrunt.ultraamplified.world.generation.layers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.telepathicgrunt.ultraamplified.world.biome.UABiomes;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;


public class AddOceansLayerUA implements IAreaTransformer0
{

	private static List<Integer> oceanList;
	private static float listSize;


	public AddOceansLayerUA()
	{
	    
	}
	
	
	public static void syncOceanList() {
		oceanList = new ArrayList<Integer>();
		
		//grabs all oceans to gen and what their ID is
		for(BiomeEntry biome : BiomeLayerSetupUA.oceanBiomesList) {
		    oceanList.add(BiomeGenHelper.BiomeRegistry.getID(biome.biome));
		}
		
		listSize = oceanList.size();
	}


	@Override
	public int apply(INoiseRandom context, int x, int z)
	{
		ImprovedNoiseGenerator noisegeneratorimproved = context.getNoiseGenerator();
		double d0 = noisegeneratorimproved.func_215456_a((x) / 8.0D, (z) / 8.0D, 0.0D, 0.0D, 0.0D);

		//dynamic code that will try to equally space oceans depending on how many is allowed
		if (listSize == 0)
		{
			//no oceans allowed
			return 0;
		}
		else
		{
			//converts the noise range into the equivalent int range for our list
			//
			//If all oceans allowed...
			//less than -0.399 becomes 0 or less.
			//-0.399 to -0.133 becomes 1.
			//-0.133 to 0.133 becomes 2
			//0.133 to 0.399 becomes 3
			//greater than 0.39 becomes 4 
			int index = (int) ((d0 / 0.266D) + 2.5D);

			//trims the extremes to be within 0 - 4
			if (index < 0)
			{
				index = 0;
			}
			else if (index > 4)
			{
				index = 4;
			}

			//shrinks the range of ints to the size of the list so all remaining ocean still has close to equal distribution.
			index = (int) (index * (listSize / 5));
			int biomeID = oceanList.get(index);

			//makes deep oceans
			double deepOceanNoise = noisegeneratorimproved.func_215456_a((x + 54443) / 5.0D, (z + 34445) / 5.0D, 0.0D, 0.0D, 0.0D);

			if (Math.abs(deepOceanNoise % 0.1D) < 0.03D && UABiomes.BASE_TO_HILLS_MAP.containsKey(biomeID))
			{
			    //Turns the shallow ocean to their deep variant
			    return UABiomes.BASE_TO_HILLS_MAP.get(biomeID);
			}

			return biomeID;
		}

	}
}