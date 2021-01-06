package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.mojang.datafixers.util.Pair;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.BiomeGroup;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.RegionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

import java.util.List;


public class MainBiomeLayer implements IC1Transformer {
    private final Registry<Biome> dynamicRegistry;
    private final RegionManager regionManager;

    public MainBiomeLayer(Registry<Biome> dynamicRegistry, RegionManager regionManager){
        this.dynamicRegistry = dynamicRegistry;
        this.regionManager = regionManager;
    }

    /*
     * LAYER KEY FOR MYSELF:
     * 0 = ocean region
     * 1 = end region
     * 2 = nether region
     * 3 = hot region
     * 4 = warm region
     * 5 = cool region
     * 6 = icy region
     */
    public int apply(INoiseRandom context, int regionID) {
        Pair<List<BiomeGroup>, Integer> listToUse;

        if(regionID == 0){
            listToUse = regionManager.getOceanList();
        }
        else if(regionID == 1){
            listToUse = regionManager.getEndList();
        }
        else if(regionID == 2){
            listToUse = regionManager.getNetherList();
        }
        else if(regionID == 3){
            listToUse =regionManager.getHotList();
        }
        else if(regionID == 4){
            listToUse = regionManager.getWarmRegion();
        }
        else if(regionID == 5){
            listToUse = regionManager.getCoolList();
        }
        else if(regionID == 6){
            listToUse = regionManager.getIcyList();
        }
        else{
            return regionID;
        }

        // return dynamicRegistry.getId(dynamicRegistry.getOrDefault(regionManager.getRandomMainBiome(listToUse, context)));

        // For debugging purposes
        ResourceLocation biomeRL = regionManager.getRandomMainBiome(listToUse, context);
        int id = dynamicRegistry.getId(dynamicRegistry.getOrDefault(biomeRL));
        if (id == -1){
            UltraAmplifiedDimension.LOGGER.warn("Invalid Biome: " + biomeRL + "  Region ID: " + regionID);
        }
        return id;
    }
}