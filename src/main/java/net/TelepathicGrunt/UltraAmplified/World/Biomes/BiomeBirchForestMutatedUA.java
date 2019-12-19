package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenBirchMTree;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeBirchForestMutatedUA extends BiomeForestUA
{
	//Only ran when generating theM variant of Birch forests.
	//This is for generating massive Birch trees and uses the BiomeForestUA for all other generations.
	
    
    //identifies this biome as a Birch biome
    public BiomeBirchForestMutatedUA(Biome.BiomeProperties properties)
    {
        super(BiomeForestUA.Type.BIRCH, properties);
    }

    
    //generates the massive Birch Trees
    protected static final WorldGenBirchMTree BIRCH_M_TREE_GENERATOR = new WorldGenBirchMTree(false);
    
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return rand.nextBoolean() ? BIRCH_M_TREE_GENERATOR : BiomeForestUA.TALL_BIRCH_TREE;
    }
}
