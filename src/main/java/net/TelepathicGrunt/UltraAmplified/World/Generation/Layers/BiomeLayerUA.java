package net.telepathicgrunt.ultraamplified.world.generation.layers;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;

@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeLayerUA implements IC0Transformer 
{
	
    private static java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry>[] biomeListsByTemperature;
    private static java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> jungleReplacedBiomes;
    private static java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> megaTaigaReplacedBiomes;
    private static java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> mesaReplacedBiomes;
    private static java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> oceanReplacedBiomes;
    private static boolean noOcean = false;
    private static ForgeRegistry<Biome> BiomeRegistry = ((ForgeRegistry<Biome>)ForgeRegistries.BIOMES);
    
    
    public BiomeLayerUA()
    {
    }

    @Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents {
		
		@SuppressWarnings("unchecked")
		@SubscribeEvent
		public static void Load(WorldEvent.Load event) {
			
			
			biomeListsByTemperature = new java.util.ArrayList[net.minecraftforge.common.BiomeManager.BiomeType.values().length];
		    jungleReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
		    megaTaigaReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
		    mesaReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
		    oceanReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
		    noOcean = false;
		    
			
	    	//Came with forge for the normal GenLayer class. Adds all biomes to list by temperature 
	        for (net.minecraftforge.common.BiomeManager.BiomeType type : net.minecraftforge.common.BiomeManager.BiomeType.values())
	        {
	            com.google.common.collect.ImmutableList<net.minecraftforge.common.BiomeManager.BiomeEntry> biomesToAdd = net.minecraftforge.common.BiomeManager.getBiomes(type);
	            int idx = type.ordinal();

	            if (biomeListsByTemperature[idx] == null) biomeListsByTemperature[idx] = new java.util.ArrayList<net.minecraftforge.common.BiomeManager.BiomeEntry>();
	            if (ConfigUA.importModdedBiomes && biomesToAdd != null) biomeListsByTemperature[idx].addAll(biomesToAdd);
	        }
		    
	        //used to manually add biomes to a certain temperature list
	        int desertIdx = net.minecraftforge.common.BiomeManager.BiomeType.DESERT.ordinal();
	        int warmIdx = net.minecraftforge.common.BiomeManager.BiomeType.WARM.ordinal();
	        int coolIdx = net.minecraftforge.common.BiomeManager.BiomeType.COOL.ordinal();
	        int icyIdx = net.minecraftforge.common.BiomeManager.BiomeType.ICY.ordinal();
	        

	        //debugging
//	        UltraAmplified.LOGGER.log(Level.DEBUG, "WARM");
//	        for(int i = 0; i < biomeListsByTemperature[warmIdx].size(); i++) {
//	            UltraAmplified.LOGGER.log(Level.DEBUG, biomeListsByTemperature[warmIdx].get(i).biome.getDisplayName());
//	        }
//	        UltraAmplified.LOGGER.log(Level.DEBUG, "COOL");
//	        for(int i = 0; i < biomeListsByTemperature[coolIdx].size(); i++) {
//	            UltraAmplified.LOGGER.log(Level.DEBUG, biomeListsByTemperature[coolIdx].get(i).biome.getDisplayName());
//	        } 
//	        UltraAmplified.LOGGER.log(Level.DEBUG, "ICY");
//	        for(int i = 0; i < biomeListsByTemperature[icyIdx].size(); i++) {
//	            UltraAmplified.LOGGER.log(Level.DEBUG, biomeListsByTemperature[icyIdx].get(i).biome.getDisplayName());
//	        } 
//	        UltraAmplified.LOGGER.log(Level.DEBUG, "DESERT");
//	        for(int i = 0; i < biomeListsByTemperature[desertIdx].size(); i++) {
//	            UltraAmplified.LOGGER.log(Level.DEBUG, biomeListsByTemperature[desertIdx].get(i).biome.getDisplayName());
//	        }
	        
	        //removes vanilla and UA biomes from biome list but should leave other mod's biome just fine. "theoretically"
	        for(int tempIndex = 0; tempIndex < biomeListsByTemperature.length; tempIndex++) {
		        for(int biomeIndex = biomeListsByTemperature[tempIndex].size()-1; biomeIndex >= 0; biomeIndex--) {
		        	ResourceLocation rl = BiomeRegistry.getKey(biomeListsByTemperature[tempIndex].get(biomeIndex).biome);
					if(rl == null) {
						continue;
					}
					
					String namespace = rl.getNamespace();
					
					//scrape out vanilla and UA biome by namespace
					if(namespace.equals("minecraft") || namespace.equals(UltraAmplified.MODID)) {
						biomeListsByTemperature[tempIndex].remove(biomeListsByTemperature[tempIndex].get(biomeIndex));
					}
		        }
	        }
	        
	        
	        //makes biome weight not less than 10 so my biomes don't overshadow it
	        for(int indexOfList = 0; indexOfList < biomeListsByTemperature.length; indexOfList++) {
		        for(int indexOfEntry = 0; indexOfEntry < biomeListsByTemperature[indexOfList].size(); indexOfEntry++) {
		        	if(biomeListsByTemperature[indexOfList].get(indexOfEntry).itemWeight < 10) {
		        		BiomeEntry biomeEntry = biomeListsByTemperature[indexOfList].get(indexOfEntry);
		        		biomeListsByTemperature[indexOfList].remove(indexOfEntry);
		        		
		        		//I'll be amazed if someone set their biome weight to 0 or less but I'm sure someone had done so...
		        		if(biomeEntry.itemWeight <= 0) {
		        			biomeListsByTemperature[indexOfList].add(indexOfEntry, new BiomeEntry(biomeEntry.biome, 10));
		        		}else {
		        			biomeListsByTemperature[indexOfList].add(indexOfEntry, new BiomeEntry(biomeEntry.biome, biomeEntry.itemWeight*10));
		        		}
		        	}
		        }
	        }
	        
	        
	        //adds our ultra amplified version of the vanilla biomes while checking to see if they are allowed by the user through the config
	       
	        //deserts
	        if(ConfigUA.desert)
	        	biomeListsByTemperature[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.DESERT, 40));
	        if(ConfigUA.savanna)
	        	biomeListsByTemperature[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.SAVANNA, 40));
	        if(ConfigUA.plains)
	        	biomeListsByTemperature[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.PLAINS, 20));
	        if(ConfigUA.nether)
	        	biomeListsByTemperature[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.NETHER, 26));
	        if(ConfigUA.mushroom) 
	        	biomeListsByTemperature[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MUSHROOM_FIELDS, 10));

	        //warm
	        if(ConfigUA.forest)
		        biomeListsByTemperature[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.FOREST, 26));
	        if(ConfigUA.darkForest)
		        biomeListsByTemperature[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.DARK_FOREST, 26));
	        if(ConfigUA.mountains)
		        biomeListsByTemperature[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MOUNTAINS, 26));
	        if(ConfigUA.plains)
		        biomeListsByTemperature[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.PLAINS, 26));
	        if(ConfigUA.birchForest)
		        biomeListsByTemperature[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BIRCH_FOREST, 26));
	        if(ConfigUA.swamplands)
		        biomeListsByTemperature[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.SWAMP, 26));
	        if(ConfigUA.mushroom) 
	        	biomeListsByTemperature[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MUSHROOM_FIELDS, 10));
	        
	        //cool
	        if(ConfigUA.forest)
		        biomeListsByTemperature[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.FOREST, 30));
	        if(ConfigUA.mountains)
		        biomeListsByTemperature[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MOUNTAINS, 30));
	        if(ConfigUA.taiga)
		        biomeListsByTemperature[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.TAIGA, 30));
	        if(ConfigUA.plains)
		        biomeListsByTemperature[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.PLAINS, 13));
	        if(ConfigUA.stoneBeach)
		        biomeListsByTemperature[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.STONE_SHORE, 10));
	        if(ConfigUA.end)
	        	biomeListsByTemperature[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.END, 20));
	        if(ConfigUA.mushroom) 
	        	biomeListsByTemperature[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MUSHROOM_FIELDS, 10));

	        //icy
	        if(ConfigUA.snowyTundra) {
		        biomeListsByTemperature[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.SNOWY_TUNDRA, 45));
	        }else {
	        	//turns snowy tundra into ice spike only if config has snowy tundra off and ice spike on
	        	if(ConfigUA.iceSpike) 
	        		biomeListsByTemperature[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.ICE_SPIKES, 26));
	        	
	        	//turns snowy tundra into ice mountain only if config has snowy tundra off and ice mountain on
	            if(ConfigUA.iceMountain)
	    	        biomeListsByTemperature[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.ICE_MOUNTAIN, 26));
	        }

	        if(ConfigUA.iceMountain)
		        biomeListsByTemperature[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.ICE_MOUNTAIN, 17));
	        if(ConfigUA.snowyTaiga)
		        biomeListsByTemperature[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.SNOWY_TAIGA, 26));
	        if(ConfigUA.coldBeach)
		        biomeListsByTemperature[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.SNOWY_BEACH, 10));
	        if(ConfigUA.mushroom) 
	        	biomeListsByTemperature[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.MUSHROOM_FIELDS, 10));
	        
	       
	        
	        //special biomes lists used to replace vanilla ones such as mesa, jungles, etc
	        
	        if(ConfigUA.jungle) {
			    jungleReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.JUNGLE, 70));
	        }
	        
			if(ConfigUA.bambooJungle) {
			    jungleReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BAMBOO_JUNGLE, 30));
			}

	        if(ConfigUA.giantTreeTaiga)
			    megaTaigaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.GIANT_TREE_TAIGA, 10));

	        if(ConfigUA.badlands) {
			    mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BADLANDS_PLATEAU, 20));
			    mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.WOODED_BADLANDS_PLATEAU, 10));
	        }
	        else if(ConfigUA.erodedBadlands) {
	        	//turns mesa completely into eroded badlands only if config has mesa off and eroded badlands on
	        	mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.ERODED_BADLANDS, 10));
	        }

	        
	        
			    
	        //this is used to help fill any biome list that is empty due to player turning off all of its biome.
	        //otherwise, we will crash if a biome list is empty
	        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> temporaryBiomeList = new java.util.ArrayList<BiomeEntry>();
	        
	        //set temp biome list to biome list that isn't empty. Otherwise, set temp to null
	        //the biome list will store up to 5 different biomes but could go over which means it will need to be trimmed afterwards
	        for(List<BiomeEntry> biomeList : biomeListsByTemperature) {
	        	for(BiomeEntry biomeEntry : biomeList) {
	        		boolean alreadyInTemp = false;
	        		
	        		for(BiomeEntry tempBiomeEntry : temporaryBiomeList) {
	        			if(tempBiomeEntry.biome == biomeEntry.biome) {
	        				alreadyInTemp = true;
	        			}
	        		}
	        		
	        		if(!alreadyInTemp) {
	        			temporaryBiomeList.add(biomeEntry);
	        		}
	        	}
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
	        
	        for(BiomeEntry biome : temporaryBiomeList){
	        	UltraAmplified.LOGGER.log(Level.WARN, biome.biome.getRegistryName());
	        }
	        
	        
	        //grabs all allowed biomes into ocean replaced list in case we disallow oceans later. 
	        //Oceans take up a large area so they can have more than 6 biomes replacing them.
	        oceanReplacedBiomes.addAll(temporaryBiomeList);
	        
	        //trims temporaryBiomeList if it exceeds 6 biomes
	        while(temporaryBiomeList.size() > 6) {
	        	temporaryBiomeList.remove(temporaryBiomeList.size()-1);
	        }
	        
	        
	        //if temp is empty (which means user made no biomes allowed), 
	        //set all biome list to have vanilla ocean so we do not crash due to all biome list not having any biome.
	        //This will also make it so that our ocean can replace the entire world too of they are enabled
	        if(temporaryBiomeList.size() == 0) {
	        	biomeListsByTemperature[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
	        	biomeListsByTemperature[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
	        	biomeListsByTemperature[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
	        	biomeListsByTemperature[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
	        	jungleReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
	        	megaTaigaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
	        	mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
	        	oceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.OCEAN, 10));
	        }
	        
	        //otherwise, set any empty biome list to have temp's biome list so it is not empty 
	        //or set it to temp if temp is 5 or less biomes so regions are not dominated by one or two biomes only
	        else {
	            if(biomeListsByTemperature[desertIdx].size() == 0 || temporaryBiomeList.size() < 6) {
	            	biomeListsByTemperature[desertIdx] = temporaryBiomeList;
	            }
	        	if(biomeListsByTemperature[warmIdx].size() == 0 || temporaryBiomeList.size() < 6) {
	            	biomeListsByTemperature[warmIdx] = temporaryBiomeList;
	            }
	            if(biomeListsByTemperature[coolIdx].size() == 0 || temporaryBiomeList.size() < 6) {
	            	biomeListsByTemperature[coolIdx] = temporaryBiomeList;
	            }
	            if(biomeListsByTemperature[icyIdx].size() == 0 || temporaryBiomeList.size() < 6) {
	            	biomeListsByTemperature[icyIdx] = temporaryBiomeList;
	            }
	            if(jungleReplacedBiomes.size() == 0 || temporaryBiomeList.size() < 6) {
	            	jungleReplacedBiomes = temporaryBiomeList;
	            }
	            if(megaTaigaReplacedBiomes.size() == 0 || temporaryBiomeList.size() < 6) {
	            	megaTaigaReplacedBiomes = temporaryBiomeList;
	            }
	            if(mesaReplacedBiomes.size() == 0 || temporaryBiomeList.size() < 6) {
	            	mesaReplacedBiomes = temporaryBiomeList;
	            }
	        }

			if (!ConfigUA.ocean && !ConfigUA.coldOcean && !ConfigUA.frozenOcean && !ConfigUA.lukewarmOcean && !ConfigUA.warmOcean) {
				noOcean = true;
			}
		}
    }
    
    
    public int apply(INoiseRandom context, int value) {
   
       int i = (value & 3840) >> 8;
       value = value & -3841;
       if (!BiomeGenHelper.isOcean(value) && value != BiomeGenHelper.MUSHROOM_FIELDS) {
          switch(value) {
          case 1:
             if (i > 0) {
                return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(mesaReplacedBiomes, context).biome);
             }

             return BiomeRegistry.getID(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.DESERT, context).biome);
          case 2:
             if (i > 0) {
                return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(jungleReplacedBiomes, context).biome);
             }

             return BiomeRegistry.getID(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.WARM, context).biome);
          case 3:
             if (i > 0) {
                return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(megaTaigaReplacedBiomes, context).biome);
             }

             return BiomeRegistry.getID(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.COOL, context).biome);
          case 4:
             return BiomeRegistry.getID(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.ICY, context).biome);
          default:
        	  
        	  if(noOcean) {
          		return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(oceanReplacedBiomes, context).biome);
          	  }
        	  
        	 //return 0 which will later be replaced by our oceans in GenLayerMixedOcean
             return value;
          }
       } else {
    	   
    	  if(noOcean) {
       		return BiomeRegistry.getID(getWeightedSpecialBiomeEntry(oceanReplacedBiomes, context).biome);
       	  }
    	   
    	 //return 0 which will later be replaced by our oceans in GenLayerMixedOcean
          return value;
       }
    
     }
    
    //returns a biome with its weight impacting how often it appears
    //This is a forge method
    protected net.minecraftforge.common.BiomeManager.BiomeEntry getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType type, INoiseRandom context) {
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = biomeListsByTemperature[type.ordinal()];
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
