package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeHillsUA extends BiomeExtendedUA
{
	private final WorldGenTaiga2 spruceGenerator = new WorldGenTaiga2(false);
    private final BiomeHillsUA.Type type;

    public BiomeHillsUA(BiomeHillsUA.Type p_i46710_1_, Biome.BiomeProperties properties)
    {
        super(properties);
        
        if (p_i46710_1_ == BiomeHillsUA.Type.EXTRA_TREES)
        {
            this.decorator.treesPerChunk = 3;
        }

        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityLlama.class, 5, 4, 6));
        this.type = p_i46710_1_;
    }
    
    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeHillsDecoratorUA());
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return (WorldGenAbstractTree)(rand.nextInt(3) > 0 ? this.spruceGenerator : super.getRandomTreeFeature(rand));
    }

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

        this.generateBiomeTerrain2(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
    public static enum Type
    {
        NORMAL,
        EXTRA_TREES,
        MUTATED;
    }
}
