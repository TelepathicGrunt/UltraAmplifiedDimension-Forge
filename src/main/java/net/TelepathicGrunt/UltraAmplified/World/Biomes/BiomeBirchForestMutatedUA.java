package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import jline.internal.Log;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeBirchForestMutatedUA extends BiomeForestUA
{
    
    public BiomeBirchForestMutatedUA(Biome.BiomeProperties properties)
    {
        super(BiomeForestUA.Type.BIRCH, properties);
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return rand.nextBoolean() ? BiomeForestUA.BIRCH_M_TREE_GENERATOR : BiomeForestUA.TALL_BIRCH_TREE;
    }
}
