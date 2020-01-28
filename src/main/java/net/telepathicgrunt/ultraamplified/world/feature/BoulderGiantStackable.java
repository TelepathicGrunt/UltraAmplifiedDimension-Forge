package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class BoulderGiantStackable extends Feature<BlockBlobConfig> 
{
    public BoulderGiantStackable(Function<Dynamic<?>, ? extends BlockBlobConfig> configFactory) {
		super(configFactory);
	}


    private final static int startRadius = 4;

    protected static final Set<BlockState> acceptableBlocks = ImmutableSet.of(
    		Blocks.DIRT.getDefaultState(),
    		Blocks.GRASS_BLOCK.getDefaultState(),
			Blocks.GRASS_PATH.getDefaultState(),
    		Blocks.PODZOL.getDefaultState(),
    		Blocks.MOSSY_COBBLESTONE.getDefaultState(),
    		Blocks.COBBLESTONE.getDefaultState(),
    		Blocks.ANDESITE.getDefaultState(),
    		Blocks.COAL_ORE.getDefaultState(),
    		Blocks.IRON_ORE.getDefaultState(),
    		Blocks.DIAMOND_ORE.getDefaultState());
	
    
    
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> p_212245_2_, Random rand, BlockPos position, BlockBlobConfig p_212245_5_) {
        
    	int boulderSpawned = 0;
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX()+rand.nextInt(3), 249, position.getZ()+rand.nextInt(3));
    	
        while (boulderSpawned < 15)
        {
        	//exit if there is a boulder already at 249
        	BlockState block = world.getBlockState(blockpos$Mutable);
            if (acceptableBlocks.contains(block))
            {
               break;
            }
    		
            
    		//this will keeps moving down position until it finds ground to generate on
            while(blockpos$Mutable.getY() > 7)
            {
            	blockpos$Mutable.move(Direction.DOWN);
            	if (blockpos$Mutable.getY() <= 8 || blockpos$Mutable.getY() >= 250)
                {
                    return false;
                }
            	

                block = world.getBlockState(blockpos$Mutable);
                
            	//allows the boulder to be set on top of another boulder
                if (acceptableBlocks.contains(block))
                {
                   break;
                }
            }
            blockpos$Mutable.move(Direction.UP); //move back up to just above land
            
            for (int currentCount = 0; startRadius >= 0 && currentCount < 3; ++currentCount)
            {
                int x = startRadius + rand.nextInt(2);
                int y = startRadius + rand.nextInt(2);
                int z = startRadius + rand.nextInt(2);
                float calculatedDistance = (float)(x + y + z) * 0.333F + 0.5F;

                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(blockpos$Mutable.add(-x, -y, -z), blockpos$Mutable.add(x, y, z)))
                {
                    if (blockpos.distanceSq(blockpos$Mutable) <= (double)(calculatedDistance * calculatedDistance))
                    {
                    	//adds the blocks for generation in this boulder
                    	//note, if user turns off an ore, that ore's chance is dumped into the below ore for generation
                    	int randomChance = rand.nextInt(1400);
                    	
                    	// 2/1400th chance for diamond ore
                    	if(ConfigUA.diamondOreSpawnrate != 0 && randomChance <= 1) {
                    		world.setBlockState(blockpos, Blocks.DIAMOND_ORE.getDefaultState(), 4);
                    	}
                    	
                    	// 48/1400th chance for iron ore
                    	else if(ConfigUA.ironOreSpawnrate != 0 && randomChance <= 50){
                    		world.setBlockState(blockpos, Blocks.IRON_ORE.getDefaultState(), 4);
                    	}
                    	
                    	// 82/1400th chance for coal ore
                    	else if(ConfigUA.coalOreSpawnrate != 0 && randomChance <= 130){
                    		world.setBlockState(blockpos, Blocks.COAL_ORE.getDefaultState(), 4);
                    	}
                    	
                    	// 398/1400th chance for andesite
                    	else if(randomChance <= 480){
                    		world.setBlockState(blockpos, Blocks.ANDESITE.getDefaultState(), 4);
                    	}
                    	
                    	// 352/1400th chance for cobblestone
                    	else if(randomChance <= 750){
                    		world.setBlockState(blockpos, Blocks.COBBLESTONE.getDefaultState(), 4);
                    	}
                    	
                    	// 650/1400th chance for mossyCobblestone
                    	else {
                    		world.setBlockState(blockpos, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 4);
                    	}
                    }
                }
                blockpos$Mutable.move(-(startRadius + 1) + rand.nextInt((int) (startRadius * 1.2)), 0 - rand.nextInt(2), -(startRadius + 1) + rand.nextInt((int) (startRadius * 1.2)));
           
                
            }
            boulderSpawned++;
            blockpos$Mutable.setPos(position.getX()+rand.nextInt(3), 249, position.getZ()+rand.nextInt(3));
        }
        
        //finished generating the boulder
        return true;
    }
}
