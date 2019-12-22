package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
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
import net.TelepathicGrunt.UltraAmplified.Config.UAConfig;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenGreenPowConcretePatchUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenHayBalePileUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenStonehenge;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenSunShrine;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenVinesShortUA;

public class BiomeJungleUA extends BiomeExtendedUA
{
    private final boolean isEdge;
    
    private static final IBlockState JUNGLE_LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
    private static final IBlockState JUNGLE_LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private static final IBlockState OAK_LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private static final IBlockState WATER = Blocks.WATER.getDefaultState();
    
    private static final WorldGenerator shrine = new WorldGenSunShrine();
    private static final WorldGenerator stonehenge = new WorldGenStonehenge();
    protected WorldGenerator smallHayPile = new WorldGenHayBalePileUA(false);
    protected WorldGenerator largeHayPile = new WorldGenHayBalePileUA(true);
    
    protected WorldGenerator concretePowderGen = new WorldGenGreenPowConcretePatchUA();
    protected WorldGenVinesShortUA worldgenvines = new WorldGenVinesShortUA();
    
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
        
        if (this.isMutation()) //blends some flower forest stuff into jungle m biome to make it better
        {
            this.decorator.sandPatchesPerChunk = 0;
            this.decorator.clayPerChunk = 0;
            this.decorator.gravelPatchesPerChunk = 0;
            
            //make Jungle M has 10 trees per chunk but Jungle Edge M have 2 trees per chunk
            this.decorator.treesPerChunk = this.decorator.treesPerChunk > 10 ? 10 : 2;
            
            this.flowers.clear();
            for (BlockFlower.EnumFlowerType type : BlockFlower.EnumFlowerType.values())
            {
                if (type.getBlockType() == BlockFlower.EnumFlowerColor.YELLOW) continue;
                if (type == BlockFlower.EnumFlowerType.BLUE_ORCHID) type = BlockFlower.EnumFlowerType.POPPY;
                addFlower(net.minecraft.init.Blocks.RED_FLOWER.getDefaultState().withProperty(net.minecraft.init.Blocks.RED_FLOWER.getTypeProperty(), type), 10);
            }
        }
        
        //needed for corrrectly decorating this biome with water ponds 
        acceptableBlocksForWaterGen.add(Blocks.GRASS);
        acceptableBlocksForWaterGen.add(Blocks.DIRT);
        acceptableBlocksForWaterGen.add(Blocks.WATER);
        acceptableBlocksForWaterGen.add(Blocks.CONCRETE_POWDER);
    }

    //generates the trees for this biome. 
    //giant jungle trees do not spawn in edge but regular and shrubs spawn in both jungle biomes
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

    
    //alternate between fern and tallgrass when spawning grassy plants
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return rand.nextInt(4) == 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }
    
    
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
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
	    	for(int x = 8; x < 24; x++) {
	    		for(int z = 8; z < 24; z++) {
	    			
	    			//uses the grass color noise to figure out what areas to spawn water in (so that the water is pooled together)
	    			double noise = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue((double)pos.east(x).getX() / 60.0D, (double)pos.south(z).getZ() / 60.0D)) / 3.0D, 0.0D, 0.9999D);
			        
	    			//used for debugging
			    	//UltraAmplified.logger.warn(" d0: "+d0 + " remainder: "+d0%1.5+" result: " + (d0%1.5 < 0.3));
			    	
	    			//determines if we should attempt to generate water at this coordinate
		    		if (noise%1.5 < 0.20)
			        {
		    			//grabs highest solid block position
		    			position = worldIn.getHeight(pos.add(x, 0, z)).down();
		    			
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
	    	
	    	//generates another patch of melons if in mutated biome
	    	int x = rand.nextInt(16) + 8;
            int z = rand.nextInt(16) + 8;
            int y = rand.nextInt(255);
        	if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), pos.add(x, y, z), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
            {
            	(new WorldGenMelon()).generate(worldIn, rand, pos.add(x, y, z));
            }
        	
        	//generates a hay bale pile in mutated biome if config option allows it. (half are small piles and other half are large piles)
        	if(UAConfig.StructuresOptions.biomeBasedStructuresOptions.miniStructureGeneration && rand.nextInt(20) == 0) 
        	{
        		x = rand.nextInt(16) + 8;
                z = rand.nextInt(16) + 8;
	            BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(pos.add(x, 0, z));
	            
	        	if(rand.nextBoolean()) 
	        	{
	        		smallHayPile.generate(worldIn, rand, blockpos);
	        	}
	        	else 
	        	{
	        		largeHayPile.generate(worldIn, rand, blockpos);
	        	}
        	}
        	
        	//generates double tall plants at high spawnrate
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
	        {
		        int howMany = rand.nextInt(10) - 10;
		        this.addDoublePlants(worldIn, rand, pos, howMany);
	        }
	    }
    	//end of mutated biome code
    	

    	//runs the normal decorations for biomes such as mushrooms and reeds and etc.
    	//done after water gen code so less plants generate on water but before stones so ores/dirt/etc does not replace stone's blocks
    	super.decorate(worldIn, rand, pos);
    	
    	
    	//if height is 0.6, this is a hills variant biome and thus, sun shrine and Stones can have a chance to generate
	    if (UAConfig.StructuresOptions.biomeBasedStructuresOptions.miniStructureGeneration && this.getBaseHeight() == 0.6F) 
	    {
	      	if(rand.nextInt(130) == 0) {
	  	        int x2 = rand.nextInt(16) + 8;
	  	        int z2 = rand.nextInt(16) + 8;
	  	        BlockPos position = worldIn.getTopSolidOrLiquidBlock(pos.add(x2, 0, z2));
		        
		        //attempt to generate sun shrine but the shrine code will check to make sure the position is ok for it to spawn
	  	        shrine.generate(worldIn, rand, position);
	      	}

        	if(rand.nextInt(15) == 0) {
		        BlockPos position = worldIn.getTopSolidOrLiquidBlock(pos.add(16, 0, 16));
		        
		        //attempt to generate sun shrine but the shrine code will check to make sure the position is ok for it to spawn
		        stonehenge.generate(worldIn, rand, position);
        	}
	    }
	    
	    
        //generates melons
	    int x = rand.nextInt(16) + 8;
        int z = rand.nextInt(16) + 8;
        int y = rand.nextInt(255);
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), pos.add(x, y, z), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
        {
        	(new WorldGenMelon()).generate(worldIn, rand, pos.add(x, y, z));
        }
        
        
      
        //generates green concrete powder and increases spawn rate if in mutated biome
    	if(this.isMutation() ? rand.nextInt(10) == 0 : rand.nextInt(25) == 0){
            x = rand.nextInt(10) + 11;
            z = rand.nextInt(10) + 11;
            concretePowderGen.generate(worldIn, rand, worldIn.getTopSolidOrLiquidBlock(pos.add(x, 0, z)));
    	}
        

        //generates short vines from Y = 100 to Y = 250
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
        {	
        	//how many per chunk
        	for (int currentCount = 0; currentCount < 50; ++currentCount)
	        {
        		x = rand.nextInt(16) + 8;
                z = rand.nextInt(16) + 8;
                y = rand.nextInt(150) + 100;
                
                worldgenvines.generate(worldIn, rand, pos.add(x, y, z));
	        }
        }
    }
    
    protected void addDoublePlants(World worldIn, Random rand, BlockPos blockPos, int numberToSpawn)
    {
        for (int currentCount = 0; currentCount < numberToSpawn; ++currentCount)
        {
            int plantType = rand.nextInt(3);

            if (plantType == 0)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
            }
            else if (plantType == 1)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);
            }
            else if (plantType == 2)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
            }

            for (int i = 0; i < 5; ++i)
            {
                int x = rand.nextInt(16) + 8;
                int z = rand.nextInt(16) + 8;
                int y = rand.nextInt(worldIn.getHeight(blockPos.add(x, 0, z)).getY() + 32);

                if (DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, new BlockPos(blockPos.getX() + x, y, blockPos.getZ() + z)))
                {
                    break;
                }
            }
        }
    }
    
    //stolen from flower forest to make all kinds of flowers spawn in mutated jungle biomes but with no red flowers
    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos)
    {
        if (this.isMutation())
        {
            double noise = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue((double)pos.getX() / 48.0D, (double)pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
            BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(noise * (double)BlockFlower.EnumFlowerType.values().length)];
            return blockflower$enumflowertype == BlockFlower.EnumFlowerType.RED_TULIP || blockflower$enumflowertype == BlockFlower.EnumFlowerType.POPPY || blockflower$enumflowertype == BlockFlower.EnumFlowerType.DANDELION ? (rand.nextBoolean() ? (rand.nextBoolean() ? BlockFlower.EnumFlowerType.BLUE_ORCHID : BlockFlower.EnumFlowerType.ORANGE_TULIP) : BlockFlower.EnumFlowerType.ALLIUM) : blockflower$enumflowertype;
        }
        else
        {
            return super.pickRandomFlower(rand, pos);
        }
    }
}
