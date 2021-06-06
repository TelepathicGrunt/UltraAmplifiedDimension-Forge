package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.UADBiomeProvider;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer.transformers.CenterWithPositionTransformer;
import net.minecraft.world.gen.INoiseRandom;


public class OceanBaseRegionLayer implements CenterWithPositionTransformer {

    public int apply(INoiseRandom noise, int center, int x, int z) {
        double oceanThresholdNoise = (noise.getNoiseGenerator().func_215456_a(
                (double)x / 3.0D,
                (double)z / 3.0D,
                132361.0D,
                0.0D,
                0.0D)
                + 1) * 0.5D;

        // Use a separate noise to make ocean spots separate from the temperature noise
        if(oceanThresholdNoise < 0.2D){
            return UADBiomeProvider.REGIONS.OCEAN.ordinal();
        }

        return center;
    }
}