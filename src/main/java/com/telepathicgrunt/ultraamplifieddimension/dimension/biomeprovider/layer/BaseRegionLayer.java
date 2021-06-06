package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.UADBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;


public class BaseRegionLayer implements IAreaTransformer0 {

    public int apply(INoiseRandom noise, int x, int z) {
        double regionNoise = (noise.getNoiseGenerator().func_215456_a(
                (double)x / 4.2D,
                (double)z / 4.2D,
                0.0D,
                0.0D,
                0.0D)
                * 0.75D) + 0.5D; // -0.25 to 1.25

        if(regionNoise < 0.3D){
            if(noise.random(25) == 0){
                return UADBiomeProvider.REGIONS.NETHER.ordinal();
            }
            return UADBiomeProvider.REGIONS.HOT.ordinal();
        }
        else if(regionNoise < 0.5D){
            return UADBiomeProvider.REGIONS.WARM.ordinal();
        }
        else if(regionNoise < 0.7D){
            if(noise.random(30) == 0){
                return UADBiomeProvider.REGIONS.END.ordinal();
            }
            return UADBiomeProvider.REGIONS.COOL.ordinal();
        }
        else{
            return UADBiomeProvider.REGIONS.ICY.ordinal();
        }
    }
}