package net.TelepathicGrunt.UltraAmplified.World.Feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;

public class BoulderTiny extends Feature<BlockBlobConfig> 
{
    public BoulderTiny(Function<Dynamic<?>, ? extends BlockBlobConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	private final static BlockState mossyCobblestone = Blocks.MOSSY_COBBLESTONE.getDefaultState();
    private final static BlockState cobblestone = Blocks.COBBLESTONE.getDefaultState();
    private final static BlockState andesite = Blocks.ANDESITE.getDefaultState();
    private final static BlockState coalOre = Blocks.COAL_ORE.getDefaultState();
    private final static BlockState ironOre = Blocks.IRON_ORE.getDefaultState();
    private final static int startRadius = 0;

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> p_212245_2_, Random rand, BlockPos position, BlockBlobConfig p_212245_5_) {
        
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
            	if (block != Blocks.GRASS && !Block.isDirt(block))
                {
                    break label0;
                }
                

                for (int currentCount = 0; startRadius >= 0 && currentCount < 3; ++currentCount)
                {
                    int x = startRadius + rand.nextInt(2);
                    int y = startRadius + rand.nextInt(2);
                    int z = startRadius + rand.nextInt(2);
                    float calculatedDistance = (float)(x + y + z) * 0.333F + 0.5F;

                    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(position.add(-x, -y, -z), position.add(x, y, z)))
                    {
                        if (blockpos.distanceSq(position) <= (double)(calculatedDistance * calculatedDistance))
                        {
                        	//adds the blocks for generation in this boulder
                        	//note, if user turns off an ore, that ore's chance is dumped into the below ore for generation
                        	int randomChance = rand.nextInt(1400);
                        	
                        	// 40/1400th chance for iron ore
                        	if(ConfigUA.ironOreSpawnrate != 0 && randomChance <= 40){
                        		worldIn.setBlockState(blockpos, ironOre, 4);
                        	}
                        	
                        	// 60/1400th chance for coal ore
                        	else if(ConfigUA.coalOreSpawnrate != 0 && randomChance <= 100){
                        		worldIn.setBlockState(blockpos, coalOre, 4);
                        	}
                        	
                        	// 300/1400th chance for andesite
                        	else if(randomChance <= 400){
                        		worldIn.setBlockState(blockpos, andesite, 4);
                        	}
                        	
                        	// 300/1400th chance for cobblestone
                        	else if(randomChance <= 700){
                        		worldIn.setBlockState(blockpos, cobblestone, 4);
                        	}
                        	
                        	// 700/1400th chance for mossyCobblestone
                        	else {
                        		worldIn.setBlockState(blockpos, mossyCobblestone, 4);
                        	}
                        }
                    }
                    position = position.add(-(startRadius + 1) + rand.nextInt(2 + startRadius * 2), 0 - rand.nextInt(2), -(startRadius + 1) + rand.nextInt(2 + startRadius * 2));
               
                    
                }
                //finished generating the boulder
                return true;
            }
        

			//this and worldIn.isAirBlock(position.down()) will keeps moving down position until it finds ground to generate on
            position = position.down();
        }
    }
}
