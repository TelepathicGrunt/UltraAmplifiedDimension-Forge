package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.minecraft.init.Biomes;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;

public class GenLayerBiomeUA extends GenLayer
{
    @SuppressWarnings("unchecked")
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry>[] biomes = new java.util.ArrayList[net.minecraftforge.common.BiomeManager.BiomeType.values().length];

    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> oceanReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> deepOceanReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> jungleReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> megaTaigaReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> mesaReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> mushroomReplacedBiomes = new java.util.ArrayList<BiomeEntry>();
    
    
    public GenLayerBiomeUA(long p_i45560_1_, GenLayer p_i45560_3_, WorldType p_i45560_4_, ChunkGeneratorSettingsUA settings)
    {
        super(p_i45560_1_);
        this.parent = p_i45560_3_;

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
        
        
        //adds our ultra amplified version of the vanilla biomes while checking to see if they are allowed by the user through the settings
        
        if(settings.desert)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeDesert, 30));
        if(settings.savanna)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeSavanna, 20));
        if(settings.plains)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomePlains, 10));
        if(settings.bambooForest)
        	biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeBambooForest, 10));
        
        if(settings.forest)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeForest, 10));
        if(settings.roofedForest)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeRoofedForest, 10));
        if(settings.extremeHills)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeExtremeHills, 10));
        if(settings.plains)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomePlains, 10));
        if(settings.birchForest)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeBirchForest, 10));
        if(settings.swamplands)
	        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeSwampland, 10));
        

        if(settings.forest)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeForest, 10));
        if(settings.extremeHills)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeExtremeHills, 10));
        if(settings.taiga)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeTaiga, 10));
        if(settings.plains)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomePlains, 10));
        if(settings.stoneBeach)
	        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeStoneBeach, 10));

        if(settings.iceFlats)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeIceFlats, 30));
        if(settings.iceMountain)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeIceMountain, 20));
        if(settings.coldTaiga)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeColdTaiga, 10));
        if(settings.coldBeach)
	        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeColdBeach, 10));
        
        
        //special biomes lists used to replace vanilla ones such as oceans, jungles, etc
        if(settings.bambooForest)
        	oceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeBambooForest, 10));
        if(settings.iceSpike)
		    oceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeIceSpike, 10));
        if(settings.end)
		    oceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeEnd, 10));

        if(settings.mesaBryce)
		    deepOceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeMesaBryce, 20));
        if(settings.nether)
		    deepOceanReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeNether, 10));

        if(settings.jungle)
		    jungleReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeJungle, 10));

        if(settings.megaTaiga)
		    megaTaigaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeRedwoodTaiga, 10));

        if(settings.mesa) {
		    mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeMesaRock, 20));
		    mesaReplacedBiomes.add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeMesaClearRock, 10));
        }

        if(settings.mushroom)
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
        	UltraAmplified.logger.warn(biome.biome.getBiomeName());
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

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or Biome ID's based on the particular GenLayer subclass.
     */
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] aint = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

        for (int i = 0; i < areaHeight; ++i)
        {
            for (int j = 0; j < areaWidth; ++j)
            {
                this.initChunkSeed((long)(j + areaX), (long)(i + areaY));
                int k = aint[j + i * areaWidth];
                int l = (k & 3840) >> 8;
                k = k & -3841;

                
                //replaces biomes with our modded versions in specific biome lists.
                if (k == Biome.getIdForBiome(Biomes.OCEAN))
                {
                	aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedSpecialBiomeEntry(oceanReplacedBiomes).biome);                
                }
                else if (k == Biome.getIdForBiome(Biomes.FROZEN_OCEAN) || k == Biome.getIdForBiome(Biomes.DEEP_OCEAN))
                {
                	aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedSpecialBiomeEntry(deepOceanReplacedBiomes).biome);     
                }
                else if (k == 1)
                {
                    if (l > 0)
                    {
                    	aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedSpecialBiomeEntry(mesaReplacedBiomes).biome);     
                    }
                    else
                    {
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.DESERT).biome);
                    }
                }
                else if (k == 2)
                {
                    if (l > 0)
                    {
                    	aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedSpecialBiomeEntry(jungleReplacedBiomes).biome);     
                    }
                    else
                    {
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.WARM).biome);
                    }
                }
                else if (k == 3)
                {
                    if (l > 0)
                    {
                    	aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedSpecialBiomeEntry(megaTaigaReplacedBiomes).biome);     
                    }
                    else
                    {
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.COOL).biome);
                    }
                }
                else if (k == 4)
                {
                    aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.ICY).biome);
                }
                else
                {
                	aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedSpecialBiomeEntry(mushroomReplacedBiomes).biome);           
                }
            }
        }

        return aint1;
    }

    
    //returns a biome with its weight impacting how often it appears
    //This is a forge method
    protected net.minecraftforge.common.BiomeManager.BiomeEntry getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType type)
    {
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = biomes[type.ordinal()];
        int totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(biomeList);
        int weight = net.minecraftforge.common.BiomeManager.isTypeListModded(type)?nextInt(totalWeight):nextInt(totalWeight / 10) * 10;
        return net.minecraft.util.WeightedRandom.getRandomItem(biomeList, weight);
    }

    //returns a biome with its weight impacting how often it appears
    //this is a modified forge method to work with my own biome lists that are passed in
    protected net.minecraftforge.common.BiomeManager.BiomeEntry getWeightedSpecialBiomeEntry(java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> list)
    {
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = list;
        int totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(biomeList);
        int weight = list.size()!=0?nextInt(totalWeight):nextInt(totalWeight / 10) * 10;
        return net.minecraft.util.WeightedRandom.getRandomItem(biomeList, weight);
    }
}
