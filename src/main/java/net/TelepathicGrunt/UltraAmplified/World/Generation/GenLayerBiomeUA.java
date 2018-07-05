package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.TelepathicGrunt.UltraAmplified.World.WorldSettingsUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.init.Biomes;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeUA extends GenLayer
{
    //@SuppressWarnings("unchecked")
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry>[] biomes = new java.util.ArrayList[net.minecraftforge.common.BiomeManager.BiomeType.values().length];
    private final WorldSettingsUA settings;

    public GenLayerBiomeUA(long p_i45560_1_, GenLayer p_i45560_3_, WorldType p_i45560_4_, WorldSettingsUA p_i45560_5_)
    {
        super(p_i45560_1_);
        this.parent = p_i45560_3_;

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
        
        biomes[desertIdx].clear();
        biomes[warmIdx].clear();
        biomes[coolIdx].clear();
        biomes[icyIdx].clear();
        
        biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeDesert, 30));
        biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeSavanna, 20));
        biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomePlains, 10));
        biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeBambooForest, 10));
        
        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeForest, 10));
        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeRoofedForest, 10));
        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeExtremeHills, 10));
        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomePlains, 10));
        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeBirchForest, 10));
        biomes[warmIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeSwampland, 10));
        
        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeForest, 10));
        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeExtremeHills, 10));
        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeTaiga, 10));
        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomePlains, 10));
        biomes[coolIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeStoneBeach, 10));
        
        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeIceFlats, 30));
        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeIceMountain, 20));
        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeColdTaiga, 10));
        biomes[icyIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(BiomeInit.BiomeColdBeach, 10));
        
        
        
        if (p_i45560_4_ == WorldType.DEFAULT_1_1)
        {
            biomes[desertIdx].clear();
            biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.DESERT, 10));
            biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.FOREST, 10));
            biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.EXTREME_HILLS, 10));
            biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.SWAMPLAND, 10));
            biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.PLAINS, 10));
            biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(Biomes.TAIGA, 10));
            this.settings = null;
        }
        else
        {
            this.settings = p_i45560_5_;
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

                if (k == Biome.getIdForBiome(Biomes.OCEAN))
                {
                	if(this.nextInt(3) == 0) {
                		aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomeInit.BiomeBambooForest);
                	}
                	else if(this.nextInt(2) == 0) {
                		aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomeInit.BiomeIceSpike);
                	}
                	else {
                		aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomeInit.BiomeEnd);
                	}
                }
                else if (k == Biome.getIdForBiome(Biomes.FROZEN_OCEAN) || k == Biome.getIdForBiome(Biomes.DEEP_OCEAN))
                {
                	if(this.nextInt(3) == 0) {
                		aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomeInit.BiomeNether);
                	}
                	else {
                		aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomeInit.BiomeMesaBryce);
                	}
                }
                else if (k == 1)
                {
                    if (l > 0)
                    {
                        if (this.nextInt(3) == 0)
                        {
                            aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomeInit.BiomeMesaClearRock);
                        }
                        else
                        {
                            aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomeInit.BiomeMesaRock);
                        }
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
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomeInit.BiomeJungle);
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
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomeInit.BiomeRedwoodTaiga);
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
                	aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomeInit.BiomeMushroomIsland);
                }
            }
        }

        return aint1;
    }

    protected net.minecraftforge.common.BiomeManager.BiomeEntry getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType type)
    {
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = biomes[type.ordinal()];
        int totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(biomeList);
        int weight = net.minecraftforge.common.BiomeManager.isTypeListModded(type)?nextInt(totalWeight):nextInt(totalWeight / 10) * 10;
        return (net.minecraftforge.common.BiomeManager.BiomeEntry)net.minecraft.util.WeightedRandom.getRandomItem(biomeList, weight);
    }
}
