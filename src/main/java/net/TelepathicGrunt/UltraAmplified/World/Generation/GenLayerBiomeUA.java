package net.TelepathicGrunt.UltraAmplified.World.Generation;

import java.util.Collection;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.common.BiomeManager.BiomeEntry;

public class GenLayerBiomeUA implements IC0Transformer 
{
	
    @SuppressWarnings("unchecked")
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry>[] biomes = new java.util.ArrayList[net.minecraftforge.common.BiomeManager.BiomeType.values().length];

    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> jungleReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> megaTaigaReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> mesaReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> oceanReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private boolean noOcean = false;
    
    
    public GenLayerBiomeUA()
    {
        //not sure what this does... Came with forge for the normal GenLayer class
        for (net.minecraftforge.common.BiomeManager.BiomeType type : net.minecraftforge.common.BiomeManager.BiomeType.values())
        {
            com.google.common.collect.ImmutableList<net.minecraftforge.common.BiomeManager.BiomeEntry> biomesToAdd = net.minecraftforge.common.BiomeManager.getBiomes(type);
            int idx = type.ordinal();

            if (biomes[idx] == null) biomes[idx] = new java.util.ArrayList<net.minecraftforge.common.BiomeManager.BiomeEntry>();
            if (biomesToAdd != null) biomes[idx].addAll(biomesToAdd);
        }
      
        int desertIdx = net.minecraftforge.common.BiomeManager.BiomeType.DESERT.ordinal();
        int warmIdx = net.minecraftforge.common.BiomeManager.BiomeType.WARM.ordinal();
        int coolIdx = net.minecraftforge.common.BiomeManager.BiomeType.COOL.ordinal();
        int icyIdx = net.minecraftforge.common.BiomeManager.BiomeType.ICY.ordinal();
        

        /*
        UltraAmplified.Logger.log(Level.DEBUG, "WARM");
        for(int i = 0; i < biomes[warmIdx].size(); i++) {
            UltraAmplified.Logger.log(Level.DEBUG, biomes[warmIdx].get(i).biome.getDisplayName());
        }
        UltraAmplified.Logger.log(Level.DEBUG, "COOL");
        for(int i = 0; i < biomes[coolIdx].size(); i++) {
            UltraAmplified.Logger.log(Level.DEBUG, biomes[coolIdx].get(i).biome.getDisplayName());
        } 
        UltraAmplified.Logger.log(Level.DEBUG, "ICY");
        for(int i = 0; i < biomes[icyIdx].size(); i++) {
            UltraAmplified.Logger.log(Level.DEBUG, biomes[icyIdx].get(i).biome.getDisplayName());
        } 
        UltraAmplified.Logger.log(Level.DEBUG, "DESERT");
        for(int i = 0; i < biomes[desertIdx].size(); i++) {
            UltraAmplified.Logger.log(Level.DEBUG, biomes[desertIdx].get(i).biome.getDisplayName());
        }*/
        
        //removes vanilla biomes from biome list but should leave other mod's biome just fine. "theoretically"
        for(int i = 0; i < 6; i++) {
        	biomes[warmIdx].remove(0);
        }
        for(int i = 0; i < 4; i++) {
        	biomes[coolIdx].remove(0);
        }
        for(int i = 0; i < 2; i++) {
        	biomes[icyIdx].remove(0);
        }
        
        
        //adds our ultra amplified version of the vanilla biomes while checking to see if they are allowed by the user through the config
        
        //deserts
        if(ConfigUA.desert)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.DESERT, 50));
        if(ConfigUA.savanna)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.SAVANNA, 50));
        if(ConfigUA.plains)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.PLAINS, 20));
        if(ConfigUA.nether)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.NETHER, 26));
        if(ConfigUA.mushroom) 
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MUSHROOM_FIELDS, 10));

        //warm
        if(ConfigUA.forest)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.FOREST, 26));
        if(ConfigUA.darkForest)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.DARK_FOREST, 26));
        if(ConfigUA.mountains)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MOUNTAINS, 26));
        if(ConfigUA.plains)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.PLAINS, 26));
        if(ConfigUA.birchForest)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BIRCH_FOREST, 26));
        if(ConfigUA.swamplands)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.SWAMP, 26));
        if(ConfigUA.mushroom) 
        	biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MUSHROOM_FIELDS, 10));
        
        //cool
        if(ConfigUA.forest)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.FOREST, 26));
        if(ConfigUA.mountains)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MOUNTAINS, 26));
        if(ConfigUA.taiga)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.TAIGA, 26));
        if(ConfigUA.plains)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.PLAINS, 15));
        if(ConfigUA.stoneBeach)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.STONE_SHORE, 20));
        if(ConfigUA.end)
        	biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.END, 20));
        if(ConfigUA.mushroom) 
        	biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MUSHROOM_FIELDS, 10));

        //icy
        if(ConfigUA.snowyTundra) {
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.SNOWY_TUNDRA, 60));
        }else {
        	//turns snowy tundra into ice spike only if config has snowy tundra off and ice spike on
        	if(ConfigUA.iceSpike) 
        		biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.ICE_SPIKES, 26));
        	
        	//turns snowy tundra into ice mountain only if config has snowy tundra off and ice mountain on
            if(ConfigUA.iceMountain)
    	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.ICE_MOUNTAIN, 26));
        }

        if(ConfigUA.iceMountain)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.ICE_MOUNTAIN, 17));
        if(ConfigUA.snowyTaiga)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.SNOWY_TAIGA, 26));
        if(ConfigUA.coldBeach)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.SNOWY_BEACH, 17));
        if(ConfigUA.mushroom) 
        	biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MUSHROOM_FIELDS, 10));
        
       
        
        //special biomes lists used to replace vanilla ones such as mesa, jungles, etc
        
        if(ConfigUA.jungle)
		    jungleReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.JUNGLE, 10));

        if(ConfigUA.giantSpruceTaiga)
		    megaTaigaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.GIANT_TREE_TAIGA, 10));

        if(ConfigUA.badlands) {
		    mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BADLANDS, 10));
        }
        else if(ConfigUA.erodedBadlands) {
        	//turns mesa completely into eroded badlands only if config has mesa off and eroded badlands on
        	mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.ERODED_BADLANDS, 10));
        }

        
        
		    
        //this is used to help fill any biome list that is empty due to player turning off all of its biome.
        //otherwise, we will crash if a biome list is empty
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> temporaryBiomeList = new java.util.ArrayList<BiomeEntry>();
        
        //set temp biome list to biome list that isn't empty. Otherwise, set temp to null
        //the biome list will store up to 5 biomes but could go over which means it will need to be trimmed afterwards
        if(biomes[warmIdx].size() != 0) {
        	temporaryBiomeList.addAll(biomes[warmIdx]);
        }
        if(biomes[desertIdx].size() != 0) {
        	temporaryBiomeList.addAll(biomes[desertIdx]);
        }
        if(biomes[coolIdx].size() != 0) {
        	temporaryBiomeList.addAll(biomes[coolIdx]);
        }
        if(biomes[icyIdx].size() != 0) {
        	temporaryBiomeList.addAll(biomes[icyIdx]);
        }
        if(jungleReplacedBiomes.size() != 0) {
        	temporaryBiomeList.addAll(jungleReplacedBiomes);
        }
        if(megaTaigaReplacedBiomes.size() != 0) {
        	temporaryBiomeList.addAll(megaTaigaReplacedBiomes);
        }
        if(mesaReplacedBiomes.size() != 0) {
        	temporaryBiomeList.addAll(mesaReplacedBiomes);
        }
        
        
        //for debugging purposes
        
//        for(BiomeEntry biome : temporaryBiomeList){
//        	UltraAmplified.Logger.log(Level.DEBUG, biome.biome.getRegistryName());
//        }
        
        
        //grabs all allowed biomes into ocean replaced list in case we disallow oceans later 
        oceanReplacedBiomes.addAll(temporaryBiomeList);
        
        //trims temporaryBiomeList if it exceeds 5 biomes
        while(temporaryBiomeList.size() > 5) {
        	temporaryBiomeList.remove(temporaryBiomeList.size()-1);
        }
        
        
        //if temp is empty (which means user made no biomes allowed), 
        //set all biome list to have vanilla ocean so we do not crash due to all biome list not having any biome.
        //This will also make it so that our ocean can replace the entire world too of they are enabled
        if(temporaryBiomeList.size() == 0) {
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
        	biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
        	biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
        	biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
        	jungleReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
        	megaTaigaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
        	mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
        	oceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
        }
        
        //otherwise, set any empty biome list to have temp's biome list so it is not empty 
        else {
            if(biomes[desertIdx].size() == 0) {
            	biomes[desertIdx] = temporaryBiomeList;
            }
        	if(biomes[warmIdx].size() == 0) {
            	biomes[warmIdx] = temporaryBiomeList;
            }
            if(biomes[coolIdx].size() == 0) {
            	biomes[coolIdx] = temporaryBiomeList;
            }
            if(biomes[icyIdx].size() == 0) {
            	biomes[icyIdx] = temporaryBiomeList;
            }
            if(jungleReplacedBiomes.size() == 0) {
            	jungleReplacedBiomes = temporaryBiomeList;
            }
            if(megaTaigaReplacedBiomes.size() == 0) {
            	megaTaigaReplacedBiomes = temporaryBiomeList;
            }
            if(mesaReplacedBiomes.size() == 0) {
            	mesaReplacedBiomes = temporaryBiomeList;
            }
        }

		if (!ConfigUA.ocean && !ConfigUA.coldOcean && !ConfigUA.frozenOcean && !ConfigUA.lukewarmOcean && !ConfigUA.warmOcean) {
			noOcean = true;
		}
    }

    
    public int apply(INoiseRandom context, int value) {
   
       int i = (value & 3840) >> 8;
       value = value & -3841;
       if (!BiomeGenHelper.isOcean(value) && value != BiomeGenHelper.MUSHROOM_FIELDS) {
          switch(value) {
          case 1:
             if (i > 0) {
                return Registry.BIOME.getId(getWeightedSpecialBiomeEntry(mesaReplacedBiomes, context).biome);
             }

             return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.DESERT, context).biome);
          case 2:
             if (i > 0) {
                return  Registry.BIOME.getId(getWeightedSpecialBiomeEntry(jungleReplacedBiomes, context).biome);
             }

             return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.WARM, context).biome);
          case 3:
             if (i > 0) {
                return Registry.BIOME.getId(getWeightedSpecialBiomeEntry(megaTaigaReplacedBiomes, context).biome);
             }

             return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.COOL, context).biome);
          case 4:
             return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.ICY, context).biome);
          default:
        	  
        	  if(noOcean) {
          		return Registry.BIOME.getId(getWeightedSpecialBiomeEntry(oceanReplacedBiomes, context).biome);
          	  }
        	  
        	 //return 0 which will later be replaced by our oceans in GenLayerMixedOcean
             return value;
          }
       } else {
    	   
    	  if(noOcean) {

    		   
       		return Registry.BIOME.getId(getWeightedSpecialBiomeEntry(oceanReplacedBiomes, context).biome);
       	  }
    	   
    	 //return 0 which will later be replaced by our oceans in GenLayerMixedOcean
          return value;
       }
    
     }
    
    //returns a biome with its weight impacting how often it appears
    //This is a forge method
    protected net.minecraftforge.common.BiomeManager.BiomeEntry getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType type, INoiseRandom context) {
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = biomes[type.ordinal()];
        int totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(biomeList);
        int weight = net.minecraftforge.common.BiomeManager.isTypeListModded(type)?context.random(totalWeight):context.random(totalWeight / 10) * 10;
        return net.minecraft.util.WeightedRandom.getRandomItem(biomeList, weight);
    }

    //returns a biome with its weight impacting how often it appears
    //this is a modified forge method to work with my own biome lists that are passed in
    protected net.minecraftforge.common.BiomeManager.BiomeEntry getWeightedSpecialBiomeEntry(java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> list, INoiseRandom context)
    {
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = list;
        int totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(biomeList);
        int weight = list.size()!=0?context.random(totalWeight):context.random(totalWeight / 10) * 10;
        return net.minecraft.util.WeightedRandom.getRandomItem(biomeList, weight);
    }
}
