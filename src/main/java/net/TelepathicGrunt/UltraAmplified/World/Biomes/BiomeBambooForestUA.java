package net.TelepathicGrunt.UltraAmplified.World.Biomes;


import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenVinesLongUA;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeBambooForestUA extends BiomeExtendedUA
{
	private static final WorldGenVinesLongUA LONG_VINES = new WorldGenVinesLongUA();
	protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
	
    public BiomeBambooForestUA()
    {
    	//set properties
        super(new BiomeProperties("Bamboo Forest").setBaseHeight(0.0F).setHeightVariation(0.025F).setTemperature(0.8F).setRainfall(0.4F).setWaterColor(10613441));
       
        this.decorator = new BiomeDecoratorUA();
        
        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
        
        this.decorator.treesPerChunk = -999;
        this.decorator.deadBushPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.cactiPerChunk = 0;
        this.decorator.clayPerChunk = 0;
        this.decorator.waterlilyPerChunk = 3;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.flowersPerChunk = 0;
        
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityLlama.class, 2, 3, 5));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityParrot.class, 4, 1, 2));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPig.class, 5, 2, 4));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 4, 2, 3));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityBat.class, 1, 5, 10));
    }
    
    //generates terrain a specific way with 1/4 water and rest a mixture of grass and course dirt
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
    	if(rand.nextInt(4) == 0) 
    	{
            this.topBlock = Blocks.WATER.getDefaultState();
    	}
    	else 
    	{
    		
    		if (noiseVal%2 < 0.7D)
            {
    			this.topBlock = Blocks.GRASS.getDefaultState();
            }
            else
            {
            	this.topBlock = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
            }
    	}

    	//runs general terrain gen use for all biomes
        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
    
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
    	//generates super tall reeds only when there is a water block next to it
    	if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.REED)) {
    		
    		BlockPos blockpos1;
    		BlockPos blockpos2;
    		
    		//the rand.nextInt controls how dense the reeds will be. Height number foe less dense
    		for (int x = 8; x < 24; x += (rand.nextInt(3)+1)) {
				for (int z = 8; z < 24; z += (rand.nextInt(2)+1)) {
					for (int y = 75; y < 255; ++y) {
						
						//set blockpos to a random spot in chunk
						blockpos1 = pos.add(x, y, z);
	
						//if space is empty for reed
						if (worldIn.isAirBlock(blockpos1)) {
							blockpos2 = blockpos1.down();
	
							//checks to see if water is around to allow reed to be placed
							if (worldIn.getBlockState(blockpos2.west()) == WATER
									|| worldIn.getBlockState(blockpos2.east()) == WATER
									|| worldIn.getBlockState(blockpos2.north()) == WATER
									|| worldIn.getBlockState(blockpos2.south()) == WATER) {
								
								//controls height of reed
								int maxHeight = 5 + rand.nextInt(rand.nextInt(14)+1);
	
								for (int currentHeight = 0; currentHeight < maxHeight; ++currentHeight) {
									
									if (Blocks.REEDS.canBlockStay(worldIn, blockpos1)) {
										//places the reed
										worldIn.setBlockState(blockpos1.up(currentHeight), Blocks.REEDS.getDefaultState(), 2);
									}
								}
							}
						}
					}
				}
			}
    	}
			

		//generates LONG vines between y = 100 and y = 250
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS)) {
	       
        	//how many vines to generate per chunk. set to 18 by default
        	for (int currentCount = 0; currentCount < 18; ++currentCount)
	        {
        		int x = rand.nextInt(16) + 8;
                int z = rand.nextInt(16) + 8;
                int y = rand.nextInt(150) + 100;
                
                LONG_VINES.generate(worldIn, rand, pos.add(x, y, z));
	        }
        }
        
        super.decorate(worldIn, rand, pos);
	}
}
