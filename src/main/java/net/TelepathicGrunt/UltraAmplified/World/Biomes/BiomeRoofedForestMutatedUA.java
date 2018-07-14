package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenDarkOakMTree;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeRoofedForestMutatedUA extends BiomeForestUA
{
    protected static final WorldGenDarkOakMTree ROOFED_M_TREE_GENERATOR = new WorldGenDarkOakMTree(false);
    
    
	public BiomeRoofedForestMutatedUA(Biome.BiomeProperties properties)
    {
        super(BiomeForestUA.Type.ROOFED, properties);
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
    	if (rand.nextInt(3) > 0)
		{
    		if(rand.nextInt(5)== 0) {
    			return ROOFED_M_TREE_GENERATOR;
    		}
    		else {
    			return ROOF_TREE;
    		}
		}
		else if (rand.nextInt(5) != 0)
		{
		    return (WorldGenAbstractTree)(rand.nextInt(10) == 0 ? BIG_TREE_FEATURE : TREE_FEATURE);
		}
		else
		{
		    return BIRCH_TREE;
		}
    }
}
