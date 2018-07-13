package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenVinesShortUA;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeJungleUA extends BiomeExtendedUA
{
    private final boolean isEdge;
    private static final IBlockState JUNGLE_LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
    private static final IBlockState JUNGLE_LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    /** The block state for the Oak leaf */
    private static final IBlockState OAK_LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    public BiomeJungleUA(boolean isEdgeIn, Biome.BiomeProperties properties)
    {
        super(properties);
        this.decorator = new BiomeDecoratorUA();
        
        this.isEdge = isEdgeIn;

        if (isEdgeIn)
        {
            this.decorator.treesPerChunk = 2;
        }
        else
        {
            this.decorator.treesPerChunk = 50;
        }

        this.decorator.grassPerChunk = 25;
        this.decorator.flowersPerChunk = 4;

        if (!isEdgeIn)
        {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
        }

        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 10, 4, 4));
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
    	  if (rand.nextInt(10) == 0)
          {
              return BIG_TREE_FEATURE;
          }
          else if (rand.nextInt(2) == 0)
          {
              return new WorldGenShrub(JUNGLE_LOG, OAK_LEAF);
          }
          else
          {
              return (WorldGenAbstractTree)(!this.isEdge && rand.nextInt(3) == 0 ? new WorldGenMegaJungle(false, 20, 60, JUNGLE_LOG, JUNGLE_LEAF) : new WorldGenTrees(false, 4 + rand.nextInt(7), JUNGLE_LOG, JUNGLE_LEAF, true));
          }
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return rand.nextInt(4) == 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }
    
    
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrain2(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
    	super.decorate(worldIn, rand, pos);
        int i = rand.nextInt(16) + 8;
        int j = rand.nextInt(16) + 8;
        int height = 255;
        int k = rand.nextInt(height);
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), pos.add(i, k, j), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
        (new WorldGenMelon()).generate(worldIn, rand, pos.add(i, k, j));
        
        WorldGenVinesShortUA worldgenvines = new WorldGenVinesShortUA();

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
        {	
        	//how many per chunk
        	for (int j1 = 0; j1 < 50; ++j1)
	        {
        		int x = rand.nextInt(16) + 8;
                int z = rand.nextInt(16) + 8;
                int y = rand.nextInt(150) + 100;
                
                worldgenvines.generate(worldIn, rand, pos.add(x, y, z));
	        }
        }
    }
}
