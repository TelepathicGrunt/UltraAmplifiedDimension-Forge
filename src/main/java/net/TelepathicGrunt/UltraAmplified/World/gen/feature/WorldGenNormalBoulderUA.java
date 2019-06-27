package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;

public class WorldGenNormalBoulderUA extends Feature<BlockBlobConfig> 
{
    private final static Block mossyCobblestone = Blocks.MOSSY_COBBLESTONE;
    private final static Block cobblestone = Blocks.COBBLESTONE;
    private final static Block andesite = Blocks.ANDESITE;
    private final static Block coalOre = Blocks.COAL_ORE;
    private final static Block ironOre = Blocks.IRON_ORE;
    private final static Block diamondOre = Blocks.DIAMOND_ORE;
    private final int startRadius = 3;


    public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> p_212245_2_, Random rand, BlockPos position, BlockBlobConfig p_212245_5_) {
        
        while (true)
        {
            label0:
            { 
        		if (position.getY() <= 6 || position.getY() >= 250)
                {
                    return false;
                }
            
        		//this and position = position.down(); will keeps moving down position until it finds ground to generate on
                if (worldIn.isAirBlock(position.down()))
                {
                    break label0;
                }
                Block block = worldIn.getBlockState(position.down()).getBlock();

            
            	//boulder will ignore other boulders and will generate only on ground
            	if (block != Blocks.GRASS_BLOCK && block != Blocks.DIRT && block != Blocks.PODZOL)
                {
                    break label0;
                }

                int radius = startRadius;

                for (int currentCount = 0; radius >= 0 && currentCount < 3; ++currentCount)
                {
                    int x = radius + rand.nextInt(2);
                    int y = radius + rand.nextInt(2);
                    int z = radius + rand.nextInt(2);
                    float calculatedDistance = (float)(x + y + z) * 0.333F + 0.5F;

                    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(position.add(-x, -y, -z), position.add(x, y, z)))
                    {
                        if (blockpos.distanceSq(position) <= (double)(calculatedDistance * calculatedDistance))
                        {
                        	//adds the blocks for generation in this boulder
                        	//note, if user turns off an ore, that ore's chance is dumped into the below ore for generation
                        	int randomChance = rand.nextInt(1400);
                        	
                        	// 1/1400th chance for diamond ore
                        	if(Config.diamondOreSpawnrate != 0 && randomChance == 0) {
                        		worldIn.setBlockState(blockpos, diamondOre.getDefaultState(), 4);
                        	}
                        	
                        	// 39/1400th chance for iron ore
                        	else if(Config.ironOreSpawnrate != 0 && randomChance <= 40){
                        		worldIn.setBlockState(blockpos, ironOre.getDefaultState(), 4);
                        	}
                        	
                        	// 60/1400th chance for coal ore
                        	else if(Config.coalOreSpawnrate != 0 && randomChance <= 100){
                        		worldIn.setBlockState(blockpos, coalOre.getDefaultState(), 4);
                        	}
                        	
                        	// 300/1400th chance for andesite
                        	else if(randomChance <= 400){
                        		worldIn.setBlockState(blockpos, andesite.getDefaultState(), 4);
                        	}
                        	
                        	// 300/1400th chance for cobblestone
                        	else if(randomChance <= 700){
                        		worldIn.setBlockState(blockpos, cobblestone.getDefaultState(), 4);
                        	}
                        	
                        	// 700/1400th chance for mossyCobblestone
                        	else {
                        		worldIn.setBlockState(blockpos, mossyCobblestone.getDefaultState(), 4);
                        	}
                        }
                    }
                    position = position.add(-(radius + 1) + rand.nextInt(2 + radius * 2), 0 - rand.nextInt(2), -(radius + 1) + rand.nextInt(2 + radius * 2));
               
                    
                }
                //finished generating the boulder
                return true;
            }
        

			//this and worldIn.isAirBlock(position.down()) will keeps moving down position until it finds ground to generate on
            position = position.down();
        }
    }
}
