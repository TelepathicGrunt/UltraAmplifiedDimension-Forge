package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;


public class BaseRegionLayer implements IAreaTransformer0 {

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
    public int apply(INoiseRandom noise, int x, int z) {
        double regionNoise = (noise.getNoiseGenerator().func_215456_a(
                        (double)x / 4.0D,
                        (double)z / 4.0D,
                        0.0D,
                        0.0D,
                        0.0D)
                      * 0.5D) + 0.5D;

        if(regionNoise < 0.3D){
            if(noise.random(25) == 0){
                return 2;
            }
            return 3;
        }
        else if(regionNoise < 0.5D){
            return 4;
        }
        else if(regionNoise < 0.7D){
            if(noise.random(30) == 0){
                return 1;
            }
            return 5;
        }
        else{
            return 6;
        }
    }
}