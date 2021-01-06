package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.RegionManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;


public class ReduceOceanNoiseAndMagnifyEndNetherLayer implements ICastleTransformer {

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
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {

        // Reduces amount of oceans stuck inland
        if(center == 0){
            if(north != 0 && west != 0 && east != 0 && south != 0 && context.random(2) == 0){
                return north;
            }
        }

        // removes end or nether touching ocean
        else if(north == 0 || west == 0 || east == 0 || south == 0){
            if((center == 1 && context.random(4) == 0) || center == 2){
                // get non-nether, non-end, non-ocean biome to use
                int nonOcean = -1;
                if(north > 2){
                    nonOcean = north;
                }
                else if(west > 2){
                    nonOcean = west;
                }
                else if(south > 2){
                    nonOcean = south;
                }
                else if(east > 2){
                    nonOcean = east;
                }

                if(nonOcean != -1){
                    return nonOcean;
                }
                else {
                    // return ocean instead
                    return 0;
                }
            }
        }

        // Magnify end or nether so they are larger (no neighboring oceans either and center isn't ocean now.)
        else if (north == 1 || west == 1 || east == 1 || south == 1){
            return 1;
        }
        else if (north == 2 || west == 2 || east == 2 || south == 2){
            return 2;
        }

        return center;
    }
}