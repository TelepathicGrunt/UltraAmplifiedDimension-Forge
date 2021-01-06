package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.RegionManager;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer.transformers.CenterWithPositionTransformer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;


public class OceanBiomeLayer implements CenterWithPositionTransformer {

    private final Registry<Biome> dynamicRegistry;
    private final RegionManager regionManager;

    public OceanBiomeLayer(Registry<Biome> dynamicRegistry, RegionManager regionManager){
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
    public int apply(INoiseRandom noise, int center, int x, int z) {

        // Use a copy of the base region layer noise to make ocean temperatures match land temperatures.
        double baseRegionNoiseCopy = (noise.getNoiseGenerator().func_215456_a(
                (double)x / 4.0D,
                (double)z / 4.0D,
                0.0D,
                0.0D,
                0.0D)
                * 0.6D) + 0.5D; // -0.1 to 1.1

        if(center == 0){
            return dynamicRegistry.getId(regionManager.getBiomeByTemperature(regionManager.getOceanList().getFirst(), baseRegionNoiseCopy).get());
        }

        return center;
    }
}