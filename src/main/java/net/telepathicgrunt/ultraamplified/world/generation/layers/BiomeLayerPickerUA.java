package net.telepathicgrunt.ultraamplified.world.generation.layers;

import java.util.List;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;

public class BiomeLayerPickerUA implements IC0Transformer 
{
	BiomeLayerSetupUA biomeLayer;
    private static ForgeRegistry<Biome> BiomeRegistry = ((ForgeRegistry<Biome>)ForgeRegistries.BIOMES);
    
    public BiomeLayerPickerUA()
    {
    	biomeLayer = new BiomeLayerSetupUA();
    }
    
    public int apply(INoiseRandom context, int value) {
   
       int i = (value & 3840) >> 8;
       value = value & -3841;
       if (!BiomeGenHelper.isOcean(value) && value != BiomeGenHelper.MUSHROOM_FIELDS) {
          switch(value) {
          case 1:
             if (i > 0) {
                return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.mesaReplacedBiomes, context).biome);
             }

             return BiomeRegistry.getID(getWeightedBiomeEntry(BiomeType.DESERT, context).biome);
          case 2:
             if (i > 0) {
                return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.jungleReplacedBiomes, context).biome);
             }

             return BiomeRegistry.getID(getWeightedBiomeEntry(BiomeType.WARM, context).biome);
          case 3:
             if (i > 0) {
                return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.megaTaigaReplacedBiomes, context).biome);
             }

             return BiomeRegistry.getID(getWeightedBiomeEntry(BiomeType.COOL, context).biome);
          case 4:
             return BiomeRegistry.getID(getWeightedBiomeEntry(BiomeType.ICY, context).biome);
          default:
        	  
        	  if(BiomeLayerSetupUA.noOcean) {
          		return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.oceanReplacedBiomes, context).biome);
          	  }
        	  
        	 //return 0 which will later be replaced by our oceans in GenLayerMixedOcean
             return value;
          }
       } else {
    	   
    	  if(BiomeLayerSetupUA.noOcean) {
       		return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(BiomeLayerSetupUA.oceanReplacedBiomes, context).biome);
       	  }
    	   
    	 //return 0 which will later be replaced by our oceans in GenLayerMixedOcean
          return value;
       }
    
     }
    
    //returns a biome with its weight impacting how often it appears
    //This is a forge method
    protected BiomeEntry getWeightedBiomeEntry(BiomeType type, INoiseRandom context) {
        List<BiomeEntry> biomeList = BiomeLayerSetupUA.biomeListsByTemperature[type.ordinal()];
        int totalWeight = WeightedRandom.getTotalWeight(biomeList);
        int weight = context.random(totalWeight);
        return WeightedRandom.getRandomItem(biomeList, weight);
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
