package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.RegionManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;


public class BaseRegionLayer implements IAreaTransformer0 {

    int oceanThreshold = 20;
    int endThreshold = 7;
    int netherThreshold = 7;
    int hotThreshold = 14;
    int warmThreshold = 14;
    int coolThreshold = 14;
    int icyThreshold = 14;
    int totalThreshold;

    public BaseRegionLayer() {
        totalThreshold = 0;
        totalThreshold += oceanThreshold;
        totalThreshold += endThreshold;
        totalThreshold += netherThreshold;
        totalThreshold += hotThreshold;
        totalThreshold += warmThreshold;
        totalThreshold += coolThreshold;
        totalThreshold += icyThreshold;
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
    public int apply(INoiseRandom noise, int x, int z) {
        int randInt = noise.random(totalThreshold);

        if(randInt < oceanThreshold)
            return 0;
        randInt -= oceanThreshold;

        if(randInt < endThreshold)
            return 1;
        randInt -= endThreshold;

        if(randInt < netherThreshold)
            return 2;
        randInt -= netherThreshold;

        if(randInt < hotThreshold)
            return 3;
        randInt -= hotThreshold;

        if(randInt < warmThreshold)
            return 4;
        randInt -= warmThreshold;

        if(randInt < coolThreshold)
            return 5;
        randInt -= coolThreshold;

        if(randInt < icyThreshold)
            return 6;

        return -1;
    }
}