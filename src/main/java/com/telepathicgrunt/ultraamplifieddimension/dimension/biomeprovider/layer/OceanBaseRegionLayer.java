package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer.transformers.CenterWithPositionTransformer;
import net.minecraft.world.gen.INoiseRandom;


public class OceanBaseRegionLayer implements CenterWithPositionTransformer {

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
        double oceanThresholdNoise = (noise.getNoiseGenerator().func_215456_a(
                        (double)x / 8.0D,
                        (double)z / 8.0D,
                        12361.0D,
                        0.0D,
                        0.0D)
                     + 1) * 0.5D;

        // Use a separate noise to make ocean spots separate from the temperature noise
        if(oceanThresholdNoise < 0.2D){
            return 0;
        }

        return center;
    }
}