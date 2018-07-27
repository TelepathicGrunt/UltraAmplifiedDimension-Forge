package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.UAConfig;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenFossils;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeSavannaMutatedUA extends BiomeSavannaUA
{
    private static final WorldGenerator fossil = new WorldGenFossils();
	
    public BiomeSavannaMutatedUA(Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.decorator = new BiomeDecoratorUA();
        
        this.decorator.treesPerChunk = 2;
        this.decorator.flowersPerChunk = 2;
        this.decorator.grassPerChunk = 5;
    }

    //generates grass with patches of coarse dirt and stone
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();

        if (noiseVal > 1.75D)
        {
            this.topBlock = Blocks.STONE.getDefaultState();
            this.fillerBlock = Blocks.STONE.getDefaultState();
        }
        else if (noiseVal > -0.5D)
        {
            this.topBlock = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
        }

        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
    	//generates fossils at an insane rate!
    	if(UAConfig.StructuresOptions.biomeBasedStructuresOptions.miniStructureGeneration && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FOSSIL))
	        if (rand.nextInt(4) == 0)
	        {
	        	fossil.generate(worldIn, rand, pos);
	        }
    	
    	//runs the other default decorations
        this.decorator.decorate(worldIn, rand, this, pos);
    }
}
