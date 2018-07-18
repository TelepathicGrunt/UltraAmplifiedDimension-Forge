package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.ArrayList;
import java.util.Random;

import jline.internal.Log;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenGreenPowConcretePatchUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenHayBalePileUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenVinesShortUA;
import net.minecraft.block.Block;
import net.minecraft.block.BlockConcretePowder;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
    private static final IBlockState OAK_LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private static final IBlockState WATER = Blocks.WATER.getDefaultState();
    
    protected WorldGenerator concretePowderGen = new WorldGenGreenPowConcretePatchUA();
    protected WorldGenerator smallHayPile = new WorldGenHayBalePileUA(false);
    protected WorldGenerator largeHayPile = new WorldGenHayBalePileUA(true);
    
    protected final ArrayList<Block> acceptableBlocksForWaterGen = new ArrayList<Block>();
    
    
    public BiomeJungleUA(boolean isEdgeIn, Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.decorator = new BiomeDecoratorUA();
        this.decorator.grassPerChunk = 25;
        this.decorator.flowersPerChunk = this.isMutation() ? 300 : 4;
        
        this.isEdge = isEdgeIn;
        if (isEdgeIn)
        {
            this.decorator.treesPerChunk = 2;
        }
        else
        {
        	this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
            this.decorator.treesPerChunk = 50;
        }
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 10, 4, 4));
        
        if (this.isMutation()) //Needs to be done here so we have access to this.type
        {
            this.decorator.sandPatchesPerChunk = 0;
            this.decorator.clayPerChunk = 0;
            this.decorator.gravelPatchesPerChunk = 0;
            this.decorator.treesPerChunk = this.decorator.treesPerChunk > 10 ? 10 : 2;
            
            this.flowers.clear();
            for (BlockFlower.EnumFlowerType type : BlockFlower.EnumFlowerType.values())
            {
                if (type.getBlockType() == BlockFlower.EnumFlowerColor.YELLOW) continue;
                if (type == BlockFlower.EnumFlowerType.BLUE_ORCHID) type = BlockFlower.EnumFlowerType.POPPY;
                addFlower(net.minecraft.init.Blocks.RED_FLOWER.getDefaultState().withProperty(net.minecraft.init.Blocks.RED_FLOWER.getTypeProperty(), type), 10);
            }
        }
        
        acceptableBlocksForWaterGen.add(Blocks.GRASS);
        acceptableBlocksForWaterGen.add(Blocks.DIRT);
        acceptableBlocksForWaterGen.add(Blocks.WATER);
        acceptableBlocksForWaterGen.add(Blocks.CONCRETE_POWDER);
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
    	//decorate with water first before any other decoration
    	//Creates water pools that are not right at edge of cliffs.
    	if(this.isMutation()) 
	    {
    		//initalize variables that we will need
        	BlockPos position;
        	
        	Block north;
        	Block south;
        	Block west;
        	Block east;
        	Block northeast;
        	Block northwest;
        	Block southeast;
        	Block southwest;
	    	
        	//loop through a 16 by 16 area that is safely inside loaded chunks
	    	for(int q = 8; q < 24; q++) {
	    		for(int w = 8; w < 24; w++) {
	    			
	    			//uses the grass color noise to figure out what areas to spawn water in (so that the water is pooled together)
	    			double d0 = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue((double)pos.east(q).getX() / 60.0D, (double)pos.south(w).getZ() / 60.0D)) / 3.0D, 0.0D, 0.9999D);
			        
	    			//used for debugging
			    	//Log.warn(" d0: "+d0 + " remainder: "+d0%1.5+" result: " + (d0%1.5 < 0.3));
			    	
	    			//determines if we should attempt to generate water at this coordinate
		    		if (d0%1.5 < 0.20)
			        {
		    			//grabs highest solid block position
		    			position = worldIn.getHeight(pos.add(q, 0, w)).down();
		    			
		    			//checks to see if the block is within our height range we want
				    	if (position.getY() >= 150 && position.getY() <= 250)
				        {
				    		//grabs all surrounding blocks that we will need to check to make sure water is placed in a nicer looking spot
				    		north = worldIn.getBlockState(position.north()).getBlock();
				    		south = worldIn.getBlockState(position.south()).getBlock();
				    		west = worldIn.getBlockState(position.west()).getBlock();
				    		east = worldIn.getBlockState(position.east()).getBlock();
				    		northeast = worldIn.getBlockState(position.north().east()).getBlock();
				    		northwest = worldIn.getBlockState(position.north().west()).getBlock();
				    		southwest = worldIn.getBlockState(position.south().east()).getBlock();
				    		southeast = worldIn.getBlockState(position.south().west()).getBlock();
				    		
				    		//makes sure all surrounding blocks are acceptable (water is not on cliff edges or replacing structures' blocks)
				            if (
			            		worldIn.getBlockState(position.down()).getBlock() == Blocks.DIRT && 
			            		acceptableBlocksForWaterGen.contains(worldIn.getBlockState(position).getBlock()) && 
			            		acceptableBlocksForWaterGen.contains(north) && 
			            		acceptableBlocksForWaterGen.contains(south) && 
			            		acceptableBlocksForWaterGen.contains(west) && 
			            		acceptableBlocksForWaterGen.contains(east) &&
			            		acceptableBlocksForWaterGen.contains(northeast) &&
			            		acceptableBlocksForWaterGen.contains(northwest) &&
			            		acceptableBlocksForWaterGen.contains(southeast) &&
			            		acceptableBlocksForWaterGen.contains(southwest)
				            	) 
				            {
			                    worldIn.setBlockState(position, WATER, 2);
				            }
				        }
				    	
		    		}
		    		
		    	}
	    	 
	        }
	    }
    	
    	
    	
    	super.decorate(worldIn, rand, pos);
    	
    	
        int i = rand.nextInt(16) + 8;
        int j = rand.nextInt(16) + 8;
        int height = 255;
        int k = rand.nextInt(height);
        
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), pos.add(i, k, j), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
        {
        	(new WorldGenMelon()).generate(worldIn, rand, pos.add(i, k, j));
        }
        
        
    	if(this.isMutation() ? rand.nextInt(10) == 0 : rand.nextInt(25) == 0){
            int v = rand.nextInt(10) + 11;
            int n = rand.nextInt(10) + 11;
            concretePowderGen.generate(worldIn, rand, worldIn.getTopSolidOrLiquidBlock(pos.add(v, 0, n)));
    	}
        
        if (this.isMutation())
        {
        	if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), pos.add(i, k, j), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
            {
            	(new WorldGenMelon()).generate(worldIn, rand, pos.add(i, k, j));
            }
        	
        	if(rand.nextInt(20) == 0) 
        	{
        		int v = rand.nextInt(16) + 8;
                int n = rand.nextInt(16) + 8;
	            BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(pos.add(v, 0, n));
	            
	        	if(rand.nextBoolean()) 
	        	{
	        		smallHayPile.generate(worldIn, rand, blockpos);
	        	}
	        	else 
	        	{
	        		largeHayPile.generate(worldIn, rand, blockpos);
	        	}
        	}
        	
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
	        {
		        int b = rand.nextInt(10) - 10;
		        this.addDoublePlants(worldIn, rand, pos, b);
	        }
        }
        
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
    
    protected void addDoublePlants(World p_185378_1_, Random p_185378_2_, BlockPos p_185378_3_, int p_185378_4_)
    {
        for (int i = 0; i < p_185378_4_; ++i)
        {
            int j = p_185378_2_.nextInt(3);

            if (j == 0)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
            }
            else if (j == 1)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);
            }
            else if (j == 2)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
            }

            for (int k = 0; k < 5; ++k)
            {
                int l = p_185378_2_.nextInt(16) + 8;
                int i1 = p_185378_2_.nextInt(16) + 8;
                int j1 = p_185378_2_.nextInt(p_185378_1_.getHeight(p_185378_3_.add(l, 0, i1)).getY() + 32);

                if (DOUBLE_PLANT_GENERATOR.generate(p_185378_1_, p_185378_2_, new BlockPos(p_185378_3_.getX() + l, j1, p_185378_3_.getZ() + i1)))
                {
                    break;
                }
            }
        }
    }
    
    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos)
    {
        if (this.isMutation())
        {
            double d0 = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue((double)pos.getX() / 48.0D, (double)pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
            BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(d0 * (double)BlockFlower.EnumFlowerType.values().length)];
            return blockflower$enumflowertype == BlockFlower.EnumFlowerType.RED_TULIP || blockflower$enumflowertype == BlockFlower.EnumFlowerType.POPPY || blockflower$enumflowertype == BlockFlower.EnumFlowerType.DANDELION ? (rand.nextBoolean() ? (rand.nextBoolean() ? BlockFlower.EnumFlowerType.BLUE_ORCHID : BlockFlower.EnumFlowerType.ORANGE_TULIP) : BlockFlower.EnumFlowerType.ALLIUM) : blockflower$enumflowertype;
        }
        else
        {
            return super.pickRandomFlower(rand, pos);
        }
    }
}
