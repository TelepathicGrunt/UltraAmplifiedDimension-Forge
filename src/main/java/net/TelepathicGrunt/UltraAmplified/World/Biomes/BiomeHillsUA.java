package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeHillsUA extends BiomeExtendedUA
{
	private final WorldGenTaiga2 spruceGenerator = new WorldGenTaiga2(false);
    private final BiomeHillsUA.Type type;

    public static enum Type
    {
        NORMAL,
        EXTRA_TREES,
        MUTATED;
    }
    
    public BiomeHillsUA(BiomeHillsUA.Type hillType, Biome.BiomeProperties properties)
    {
        super(properties);
        
    	//sets more trees to spawn if this is extreme hills + 
        if (hillType == BiomeHillsUA.Type.EXTRA_TREES)
        {
            this.decorator.treesPerChunk = 3;
        }

        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityLlama.class, 5, 4, 6));
        this.type = hillType;
    }
    
    //we use the decorator specifically for Extreme Hills instead of BiomeDecoratorUA so we can spawn emeralds and silverfish blocks.
    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeHillsDecoratorUA());
    }

    //spawns the trees for this biome
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return (WorldGenAbstractTree)(rand.nextInt(3) > 0 ? this.spruceGenerator : super.getRandomTreeFeature(rand));
    }

    
    //generates the terrain as grass if normal extreme hills, grass and gravel in mutated, and grass and stone for extreme hills +.
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();

        if ((noiseVal < -1.0D || noiseVal > 2.0D) && this.type == BiomeHillsUA.Type.MUTATED)
        {
            this.topBlock = Blocks.GRAVEL.getDefaultState();
            this.fillerBlock = Blocks.GRAVEL.getDefaultState();
        }
        else if (noiseVal > 1.0D && this.type != BiomeHillsUA.Type.EXTRA_TREES)
        {
            this.topBlock = Blocks.STONE.getDefaultState();
            this.fillerBlock = Blocks.STONE.getDefaultState();
        }

        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
}
