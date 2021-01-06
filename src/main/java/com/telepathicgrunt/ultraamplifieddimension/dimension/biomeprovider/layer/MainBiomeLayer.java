package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.RegionManager;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer.transformers.CastleWithPositionTransformer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;


public class MainBiomeLayer implements CastleWithPositionTransformer {
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
    public int apply(INoiseRandom context, int north, int west, int south, int east, int regionID, int x, int z) {
        Biome biomeToUse;

        if(regionID == 1){
            biomeToUse = regionManager.getRandomMainBiome(regionManager.getEndList(), context).get();
        }
        else if(regionID == 2){
            biomeToUse = regionManager.getRandomMainBiome(regionManager.getNetherList(), context).get();
        }
        else if(regionID == 3){
            biomeToUse = regionManager.getRandomMainBiome(regionManager.getHotList(), context).get();
        }
        else if(regionID == 4){
            biomeToUse = regionManager.getRandomMainBiome(regionManager.getWarmRegion(), context).get();
        }
        else if(regionID == 5){
            biomeToUse = regionManager.getRandomMainBiome(regionManager.getCoolList(), context).get();
        }
        else if(regionID == 6){
            biomeToUse = regionManager.getRandomMainBiome(regionManager.getIcyList(), context).get();
        }
        else{
            return regionID;
        }

        return dynamicRegistry.getId(biomeToUse);

//        // For debugging purposes
//        ResourceLocation biomeRL = regionManager.getRandomMainBiome(listToUse, context);
//        int id = dynamicRegistry.getId(dynamicRegistry.getOrDefault(biomeRL));
//        if (id == -1){
//            UltraAmplifiedDimension.LOGGER.warn("Invalid Biome: " + biomeRL + "  Region ID: " + regionID);
//        }
//        return id;
    }
}