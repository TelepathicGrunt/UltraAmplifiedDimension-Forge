package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.UADBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;


public class ReduceOceanNoiseAndMagnifyEndNetherLayer implements ICastleTransformer {

    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {

        // Reduces amount of oceans stuck inland
        if(center == UADBiomeProvider.REGIONS.OCEAN.ordinal()){
            if(north != UADBiomeProvider.REGIONS.OCEAN.ordinal() &&
                    west != UADBiomeProvider.REGIONS.OCEAN.ordinal() &&
                    east != UADBiomeProvider.REGIONS.OCEAN.ordinal() &&
                    south != UADBiomeProvider.REGIONS.OCEAN.ordinal() &&
                    context.random(2) == 0)
            {
                return north;
            }
        }

        // removes end or nether touching ocean
        else if(north == UADBiomeProvider.REGIONS.OCEAN.ordinal() ||
                west == UADBiomeProvider.REGIONS.OCEAN.ordinal() ||
                east == UADBiomeProvider.REGIONS.OCEAN.ordinal() ||
                south == UADBiomeProvider.REGIONS.OCEAN.ordinal())
        {
            if((center == UADBiomeProvider.REGIONS.END.ordinal()) ||
                    (center == UADBiomeProvider.REGIONS.NETHER.ordinal()))
            {
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
                    return UADBiomeProvider.REGIONS.OCEAN.ordinal();
                }
            }
        }

        // Magnify end or nether so they are larger (no neighboring oceans either and center isn't ocean now.)
        else if ((north == UADBiomeProvider.REGIONS.END.ordinal() ||
                west == UADBiomeProvider.REGIONS.END.ordinal() ||
                east == UADBiomeProvider.REGIONS.END.ordinal() ||
                south == UADBiomeProvider.REGIONS.END.ordinal()) &&
                context.random(2) != 0)
        {
            return UADBiomeProvider.REGIONS.END.ordinal();
        }
        else if ((north == UADBiomeProvider.REGIONS.NETHER.ordinal() ||
                west == UADBiomeProvider.REGIONS.NETHER.ordinal() ||
                east == UADBiomeProvider.REGIONS.NETHER.ordinal() ||
                south == UADBiomeProvider.REGIONS.NETHER.ordinal()) &&
                context.random(3) != 0)
        {
            return UADBiomeProvider.REGIONS.NETHER.ordinal();
        }

        return center;
    }
}