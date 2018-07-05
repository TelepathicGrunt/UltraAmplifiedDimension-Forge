package net.TelepathicGrunt.UltraAmplified.World.Biomes;


import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenVinesLongUA;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenVines;

public class BiomeBambooForestUA extends BiomeExtendedUA
{
    public BiomeBambooForestUA()
    {
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
        
        //new
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.flowersPerChunk = 0;
    }
    
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
    	if(rand.nextInt(4) == 0) 
    	{
            this.topBlock = Blocks.WATER.getDefaultState();
    	}
    	else 
    	{
            if (Math.abs(noiseVal%3) > 2) 
    		{
    			this.topBlock = Blocks.GRASS.getDefaultState();
    		}
    		else
    		{
    			this.topBlock = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
    		}
    	}

        this.generateBiomeTerrain2(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
    
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
    	
    	if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.REED)) {
			for (int x = 8; x < 24; x += (rand.nextInt(2)+1)) {
				for (int z = 8; z < 24; z += (rand.nextInt(2)+1)) {
					for (int y = 75; y < 255; ++y) {
						
						//set blockpos to a random spot in chunk (theoretically)
						BlockPos blockpos = pos.add(x, y, z);
	
						//if space is empty for reed
						if (worldIn.isAirBlock(blockpos)) {
							BlockPos blockpos1 = blockpos.down();
	
							//checks to see if water is around to allow reed to be placed
							if (worldIn.getBlockState(blockpos1.west()).getMaterial() == Material.WATER
									|| worldIn.getBlockState(blockpos1.east()).getMaterial() == Material.WATER
									|| worldIn.getBlockState(blockpos1.north()).getMaterial() == Material.WATER
									|| worldIn.getBlockState(blockpos1.south()).getMaterial() == Material.WATER) {
								
								//controls height of reed
								int j = 5 + rand.nextInt(rand.nextInt(20)+1);
	
								for (int k = 0; k < j; ++k) {
									
									if (Blocks.REEDS.canBlockStay(worldIn, blockpos)) {
										//places the reed
										worldIn.setBlockState(blockpos.up(k), Blocks.REEDS.getDefaultState(), 2);
									}
								}
							}
						}
					}
				}
			}
    	}
			
		WorldGenVinesLongUA worldgenvines = new WorldGenVinesLongUA();

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS)) {
	       
        	//how many per chunk
        	for (int j1 = 0; j1 < 25; ++j1)
	        {
        		int x = rand.nextInt(16) + 8;
                int z = rand.nextInt(16) + 8;
                int y = rand.nextInt(150) + 100;
                
                worldgenvines.generate(worldIn, rand, pos.add(x, y, z));
	        }
        }
        
        super.decorate(worldIn, rand, pos);
	}
}
