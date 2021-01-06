package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.RegionManager;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer.transformers.CastleWithPositionTransformer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;


public class ShoreEdgeHillsAndMutatationsBiomeLayer implements CastleWithPositionTransformer {
    private final Registry<Biome> dynamicRegistry;
    private final RegionManager regionManager;
    private final float subBiomeThreshold;
    private final float mutatedThreshold;
    private final float biomeSize;

    public ShoreEdgeHillsAndMutatationsBiomeLayer(Registry<Biome> dynamicRegistry, RegionManager regionManager, float subBiomeThreshold, float mutatedThreshold, int biomeSize){
        this.dynamicRegistry = dynamicRegistry;
        this.regionManager = regionManager;
        this.subBiomeThreshold = subBiomeThreshold;
        this.mutatedThreshold = mutatedThreshold;
        this.biomeSize = biomeSize;
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
    public int apply(INoiseRandom context, int north, int west, int south, int east, int originalBiomeID, int x, int z) {
        Biome currentBiome = dynamicRegistry.getByValue(originalBiomeID);
        Biome nbiome = dynamicRegistry.getByValue(north);
        Biome wbiome = dynamicRegistry.getByValue(west);
        Biome ebiome = dynamicRegistry.getByValue(east);
        Biome sbiome = dynamicRegistry.getByValue(south);
        Biome newBiome = null;

        if(currentBiome == null || nbiome == null || wbiome == null || ebiome == null || sbiome == null){
            UltraAmplifiedDimension.LOGGER.error("Error: ShoreEdgeHillsAndMutationsBiomeLayer received an unknown biome ID it cannot work with | C:" + originalBiomeID + " N:" + nbiome + " E:" + ebiome + " S:" + sbiome + " W:" + wbiome);
            return originalBiomeID;
        }

        // Adds the shore biome between ocean and non-ocean biomes (shrinks current biome)
        if(currentBiome.getCategory() == Biome.Category.OCEAN){
            if(nbiome.getCategory() != Biome.Category.OCEAN ||
                wbiome.getCategory() != Biome.Category.OCEAN ||
                ebiome.getCategory() != Biome.Category.OCEAN ||
                sbiome.getCategory() != Biome.Category.OCEAN)
            {
                newBiome = regionManager.getShore(currentBiome);
            }
        }
        else{
            if(nbiome.getCategory() == Biome.Category.OCEAN ||
                wbiome.getCategory() == Biome.Category.OCEAN ||
                ebiome.getCategory() == Biome.Category.OCEAN ||
                sbiome.getCategory() == Biome.Category.OCEAN)
            {
                newBiome = regionManager.getShore(currentBiome);
            }
        }

        // break early for shore biomes
        if(newBiome != null){
            return dynamicRegistry.getId(newBiome);
        }

        // Creates the sub, mutated, and mutated sub biomes
        double subBiomeNoise = (context.getNoiseGenerator().func_215456_a(
                (double)x / biomeSize,
                (double)z / biomeSize,
                2314.0D,
                0.0D,
                0.0D)
                * 0.5D) + 0.5D; // 0 to 1

        double mutatedNoise = (context.getNoiseGenerator().func_215456_a(
                (double)x / (biomeSize + 3D),
                (double)z / (biomeSize + 3D),
                9001.0D,
                0.0D,
                0.0D)
                * 0.5D) + 0.5D; // 0 to 1

        // adds border biome if on border (shrinks current biome)
        if(nbiome.getCategory() != currentBiome.getCategory() ||
            wbiome.getCategory() != currentBiome.getCategory() ||
            ebiome.getCategory() != currentBiome.getCategory() ||
            sbiome.getCategory() != currentBiome.getCategory())
        {
            newBiome = regionManager.getBorder(currentBiome);
        }

        // There's no sub border so we can do else if here safely
        else if(subBiomeNoise < subBiomeThreshold){
            newBiome = regionManager.getSubBiome(currentBiome);

            if(mutatedNoise < mutatedThreshold) {
                // return early as there is no other biome modification we can do after this point
                Biome mutatedSubBiome = regionManager.getMutatedSubBiome(newBiome);
                if(mutatedSubBiome != null){
                    return dynamicRegistry.getId(mutatedSubBiome);
                }
            }
        }

        // Only main biome and border biome will reach here.
        if(mutatedNoise < mutatedThreshold){
            if(newBiome == null){
                newBiome = regionManager.getMutated(currentBiome);
            }
            else{
                // This is only reached if border biome was set
                newBiome = regionManager.getMutatedBorderBiome(newBiome);
            }
        }

        // Return the new biome or old one if no new one was set.
        if(newBiome != null){
            return dynamicRegistry.getId(newBiome);
        }
        return originalBiomeID;
    }
}