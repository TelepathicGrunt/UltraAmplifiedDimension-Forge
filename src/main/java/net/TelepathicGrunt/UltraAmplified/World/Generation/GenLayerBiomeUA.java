package net.TelepathicGrunt.UltraAmplified.World.Generation;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.common.BiomeManager.BiomeEntry;

public class GenLayerBiomeUA implements IC0Transformer 
{
	
    @SuppressWarnings("unchecked")
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry>[] biomes = new java.util.ArrayList[net.minecraftforge.common.BiomeManager.BiomeType.values().length];

    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> oceanReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> deepOceanReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> jungleReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> megaTaigaReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> mesaReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> mushroomReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    
    
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
        
        if(Config.desert)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeDesert, 30));
        if(Config.savanna)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeSavanna, 20));
        if(Config.plains)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomePlains, 10));
        //if(ConfigUA.SERVER.bambooForest)
        //	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeBambooForest, 10));
        
        if(Config.forest)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeForest, 10));
        if(Config.roofedForest)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeRoofedForest, 10));
        if(Config.extremeHills)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeExtremeHills, 10));
        if(Config.plains)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomePlains, 10));
        if(Config.birchForest)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeBirchForest, 10));
        if(Config.swamplands)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeSwampland, 10));
        

        if(Config.forest)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeForest, 10));
        if(Config.extremeHills)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeExtremeHills, 10));
        if(Config.taiga)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeTaiga, 10));
        if(Config.plains)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomePlains, 10));
        if(Config.stoneBeach)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeStoneBeach, 4));

        if(Config.iceFlats)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeIceFlats, 30));
        if(Config.iceMountain)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeIceMountain, 20));
        if(Config.coldTaiga)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeColdTaiga, 10));
        if(Config.coldBeach)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeColdBeach, 10));
        
        
        //special biomes lists used to replace vanilla ones such as oceans, jungles, etc
        //if(ConfigUA.SERVER.bambooForest)
        //	oceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeBambooForest, 10));
        if(Config.iceSpike)
		    oceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeIceSpike, 10));
        if(Config.end)
		    oceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeEnd, 10));

        if(Config.mesaBryce)
		    deepOceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeMesaBryce, 20));
        if(Config.nether)
		    deepOceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeNether, 10));

        if(Config.jungle)
		    jungleReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeJungle, 10));

        if(Config.megaTaiga)
		    megaTaigaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeRedwoodTaiga, 10));

        if(Config.mesa) {
		    mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeMesaRock, 20));
		    mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeMesaClearRock, 10));
        }

        if(Config.mushroom)
		    mushroomReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeMushroomIsland, 10));
		    
        //this is used to help fill any biome list that is empty due to player turning off all of its biome.
        //otherwise, we will crash if a biome list is empty
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> temporaryBiomeList = new java.util.ArrayList<BiomeEntry>();
        
        //set temp biome list to biome list that isn't empty. Otherwise, set temp to null
        //the biome list will store up to 5 biomes but could go over which means it will need to be trimmed afterwards
        if(biomes[warmIdx].size() != 0) {
        	temporaryBiomeList.addAll(biomes[warmIdx]);
        }
        if(biomes[desertIdx].size() != 0 && temporaryBiomeList.size() < 6) {
        	temporaryBiomeList.addAll(biomes[desertIdx]);
        }
        if(biomes[coolIdx].size() != 0 && temporaryBiomeList.size() < 6) {
        	temporaryBiomeList.addAll(biomes[coolIdx]);
        }
        if(biomes[icyIdx].size() != 0 && temporaryBiomeList.size() < 6) {
        	temporaryBiomeList.addAll(biomes[icyIdx]);
        }
        if(jungleReplacedBiomes.size() != 0 && temporaryBiomeList.size() < 6) {
        	temporaryBiomeList.addAll(jungleReplacedBiomes);
        }
        if(megaTaigaReplacedBiomes.size() != 0 && temporaryBiomeList.size() < 6) {
        	temporaryBiomeList.addAll(megaTaigaReplacedBiomes);
        }
        if(mesaReplacedBiomes.size() != 0 && temporaryBiomeList.size() < 6) {
        	temporaryBiomeList.addAll(mesaReplacedBiomes);
        }
        if(mushroomReplacedBiomes.size() != 0 && temporaryBiomeList.size() < 6) {
        	temporaryBiomeList.addAll(mushroomReplacedBiomes);
        }
        if(deepOceanReplacedBiomes.size() != 0 && temporaryBiomeList.size() < 6) {
        	temporaryBiomeList.addAll(deepOceanReplacedBiomes);
        }
        if(oceanReplacedBiomes.size() != 0 && temporaryBiomeList.size() < 6) {
        	temporaryBiomeList.addAll(oceanReplacedBiomes);
        }
        
        
        //for debugging purposes
        /*
        for(BiomeEntry biome : temporaryBiomeList){
        	UltraAmplified.Logger.log(Level.DEBUG, biome.biome.getRegistryName());
        }*/
        
        
        //trims temporaryBiomeList if it exceeds 5 biomes
        while(temporaryBiomeList.size() > 5) {
        	temporaryBiomeList.remove(temporaryBiomeList.size()-1);
        }
        
        
        //if temp is empty (which means user made no biomes allowed), 
        //set all biome list to have deep ocean so we do not crash due to all biome list not having any biome.
        if(temporaryBiomeList.size() == 0) {
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DEEP_OCEAN, 10));
        	biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DEEP_OCEAN, 10));
        	biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DEEP_OCEAN, 10));
        	biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DEEP_OCEAN, 10));
        	oceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DEEP_OCEAN, 10));
        	deepOceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DEEP_OCEAN, 10));
        	jungleReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DEEP_OCEAN, 10));
        	megaTaigaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DEEP_OCEAN, 10));
        	mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DEEP_OCEAN, 10));
        	mushroomReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DEEP_OCEAN, 10));
        }
        //otherwise, set any empty biome list to have temp's biome list so it is not empty 
        else {
        	if(biomes[warmIdx].size() == 0) {
            	biomes[warmIdx] = temporaryBiomeList;
            }
            if(biomes[desertIdx].size() == 0) {
            	biomes[desertIdx] = temporaryBiomeList;
            }
            if(biomes[coolIdx].size() == 0) {
            	biomes[coolIdx] = temporaryBiomeList;
            }
            if(biomes[icyIdx].size() == 0) {
            	biomes[icyIdx] = temporaryBiomeList;
            }
            if(oceanReplacedBiomes.size() == 0) {
            	oceanReplacedBiomes = temporaryBiomeList;
            }
            if(deepOceanReplacedBiomes.size() == 0) {
            	deepOceanReplacedBiomes = temporaryBiomeList;
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
            if(mushroomReplacedBiomes.size() == 0) {
            	mushroomReplacedBiomes = temporaryBiomeList;
            }
        }
    }

    
    public int apply(IContext context, int value) {
   
       int i = (value & 3840) >> 8;
       value = value & -3841;
       if (!BiomeGenHelper.isOcean(value) && value != BiomeGenHelper.MUSHROOM_FIELDS) {
          switch(value) {
          case 1:
             if (i > 0) {
                return IRegistry.field_212624_m.getId(getWeightedSpecialBiomeEntry(mesaReplacedBiomes, context).biome);
             }

             return IRegistry.field_212624_m.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.DESERT, context).biome);
          case 2:
             if (i > 0) {
                return  IRegistry.field_212624_m.getId(getWeightedSpecialBiomeEntry(jungleReplacedBiomes, context).biome);
             }

             return IRegistry.field_212624_m.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.WARM, context).biome);
          case 3:
             if (i > 0) {
                return IRegistry.field_212624_m.getId(getWeightedSpecialBiomeEntry(megaTaigaReplacedBiomes, context).biome);
             }

             return IRegistry.field_212624_m.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.COOL, context).biome);
          case 4:
             return IRegistry.field_212624_m.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.ICY, context).biome);
          default:
             return IRegistry.field_212624_m.getId(getWeightedSpecialBiomeEntry(mushroomReplacedBiomes, context).biome);
          }
       } else {
    	   
    	   if(value == BiomeGenHelper.OCEAN) 
    	   {
    		   return IRegistry.field_212624_m.getId(getWeightedSpecialBiomeEntry(oceanReplacedBiomes, context).biome); 
    	   }
    	   else if (value == BiomeGenHelper.FROZEN_OCEAN || value == BiomeGenHelper.DEEP_OCEAN)
           {
    		   return IRegistry.field_212624_m.getId(getWeightedSpecialBiomeEntry(deepOceanReplacedBiomes, context).biome); 
           }
          return value;
       }
    
     }
    
    //returns a biome with its weight impacting how often it appears
    //This is a forge method
    protected net.minecraftforge.common.BiomeManager.BiomeEntry getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType type, IContext context) {
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = biomes[type.ordinal()];
        int totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(biomeList);
        int weight = net.minecraftforge.common.BiomeManager.isTypeListModded(type)?context.random(totalWeight):context.random(totalWeight / 10) * 10;
        return net.minecraft.util.WeightedRandom.getRandomItem(biomeList, weight);
    }

    //returns a biome with its weight impacting how often it appears
    //this is a modified forge method to work with my own biome lists that are passed in
    protected net.minecraftforge.common.BiomeManager.BiomeEntry getWeightedSpecialBiomeEntry(java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> list, IContext context)
    {
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = list;
        int totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(biomeList);
        int weight = list.size()!=0?context.random(totalWeight):context.random(totalWeight / 10) * 10;
        return net.minecraft.util.WeightedRandom.getRandomItem(biomeList, weight);
    }
}
