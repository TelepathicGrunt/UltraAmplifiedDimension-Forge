package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeForestMutatedUA extends BiomeForestUA
{
	
    public BiomeForestMutatedUA(Biome.BiomeProperties properties)
    {
        super(BiomeForestUA.Type.BIRCH, properties);
    }

    public WorldGenAbstractTree genBigTreeChance(Random rand)
    {
        return rand.nextBoolean() ? BiomeForestUA.SUPER_BIRCH_TREE : BiomeForestUA.BIRCH_TREE;
    }
}
