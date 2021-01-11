package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.BiomeGroup;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.RegionManager;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.UADBiomeProvider;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer.transformers.CastleWithPositionTransformer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;


public class MainBiomeLayer implements CastleWithPositionTransformer {
    private final Registry<Biome> dynamicRegistry;
    private final RegionManager regionManager;

    public MainBiomeLayer(Registry<Biome> dynamicRegistry, RegionManager regionManager){
        this.dynamicRegistry = dynamicRegistry;
        this.regionManager = regionManager;
    }

    public int apply(INoiseRandom context, int north, int west, int south, int east, int regionID, int x, int z) {
        BiomeGroup selectedBiomeGroup;

        if(regionID == UADBiomeProvider.REGIONS.END.ordinal()){
            selectedBiomeGroup = regionManager.getRandomBiomeGroup(regionManager.getEndList(), context);
        }
        else if(regionID == UADBiomeProvider.REGIONS.NETHER.ordinal()){
            selectedBiomeGroup = regionManager.getRandomBiomeGroup(regionManager.getNetherList(), context);
        }
        else if(regionID == UADBiomeProvider.REGIONS.HOT.ordinal()){
            selectedBiomeGroup = regionManager.getRandomBiomeGroup(regionManager.getHotList(), context);
        }
        else if(regionID == UADBiomeProvider.REGIONS.WARM.ordinal()){
            selectedBiomeGroup = regionManager.getRandomBiomeGroup(regionManager.getWarmRegion(), context);
        }
        else if(regionID == UADBiomeProvider.REGIONS.COOL.ordinal()){
            selectedBiomeGroup = regionManager.getRandomBiomeGroup(regionManager.getCoolList(), context);
        }
        else if(regionID == UADBiomeProvider.REGIONS.ICY.ordinal()){
            selectedBiomeGroup = regionManager.getRandomBiomeGroup(regionManager.getIcyList(), context);
        }
        else{
            // Use a copy of the base region layer noise to make ocean temperatures match land temperatures.
            // It is /16 instead of /4 due to the two zoom layers after the base layer.
            double baseRegionNoiseCopy = (context.getNoiseGenerator().func_215456_a(
                    (double)x / 16.0D,
                    (double)z / 16.0D,
                    0.0D,
                    0.0D,
                    0.0D)
                    * 0.7D) + 0.55D; // -0.1 to 1.1

            selectedBiomeGroup = regionManager.getWeightedBiomeGroupByTemperature(regionManager.getOceanList(), baseRegionNoiseCopy);
        }

        return dynamicRegistry.getId(selectedBiomeGroup.getMainBiome().get());

//        // For debugging purposes
//        ResourceLocation biomeRL = regionManager.getRandomMainBiome(listToUse, context);
//        int id = dynamicRegistry.getId(dynamicRegistry.getOrDefault(biomeRL));
//        if (id == -1){
//            UltraAmplifiedDimension.LOGGER.warn("Invalid Biome: " + biomeRL + "  Region ID: " + regionID);
//        }
//        return id;
    }
}