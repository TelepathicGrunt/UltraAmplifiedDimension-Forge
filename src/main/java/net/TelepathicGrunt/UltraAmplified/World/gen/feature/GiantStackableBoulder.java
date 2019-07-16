package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;

public class GiantStackableBoulder extends Feature<BlockBlobConfig> 
{
    public GiantStackableBoulder(Function<Dynamic<?>, ? extends BlockBlobConfig> configFactoryIn) {
		super(configFactoryIn);
	}



	private final static BlockState mossyCobblestone = Blocks.MOSSY_COBBLESTONE.getDefaultState();
    private final static BlockState cobblestone = Blocks.COBBLESTONE.getDefaultState();
    private final static BlockState andesite = Blocks.ANDESITE.getDefaultState();
    private final static BlockState coalOre = Blocks.COAL_ORE.getDefaultState();
    private final static BlockState ironOre = Blocks.IRON_ORE.getDefaultState();
    private final static BlockState diamondOre = Blocks.DIAMOND_ORE.getDefaultState();
    private final static int startRadius = 4;

    protected static final Set<BlockState> acceptableBlocks = 
    		Stream.of(
	    		Blocks.DIRT.getDefaultState(),
	    		Blocks.GRASS_BLOCK.getDefaultState(),
				Blocks.GRASS_PATH.getDefaultState(),
	    		Blocks.PODZOL.getDefaultState(),
    			mossyCobblestone,
	    		cobblestone,
	    		andesite,
	    		coalOre,
	    		ironOre,
	    		diamondOre
    		).collect(Collectors.toCollection(HashSet::new));
	
    
    
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> p_212245_2_, Random rand, BlockPos position, BlockBlobConfig p_212245_5_) {
        
    	int boulderSpawned = 0;
    	BlockPos tempPos = new BlockPos(position.getX()+rand.nextInt(3), 249, position.getZ()+rand.nextInt(3));
    	
        while (boulderSpawned < 15)
        {
        	//exit if there is a boulder already at 249
        	BlockState block = worldIn.getBlockState(tempPos);
            if (acceptableBlocks.contains(block))
            {
               break;
            }
    		
            
    		//this will keeps moving down position until it finds ground to generate on
            while(tempPos.getY() > 7)
            {
            	tempPos = tempPos.down();
            	if (tempPos.getY() <= 8 || tempPos.getY() >= 250)
                {
                    return false;
                }
            	

                block = worldIn.getBlockState(tempPos.down());
                
            	//allows the boulder to be set on top of another boulder
                if (acceptableBlocks.contains(block))
                {
                   break;
                }
            }
            
            
            for (int currentCount = 0; startRadius >= 0 && currentCount < 3; ++currentCount)
            {
                int x = startRadius + rand.nextInt(2);
                int y = startRadius + rand.nextInt(2);
                int z = startRadius + rand.nextInt(2);
                float calculatedDistance = (float)(x + y + z) * 0.333F + 0.5F;

                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(tempPos.add(-x, -y, -z), tempPos.add(x, y, z)))
                {
                    if (blockpos.distanceSq(tempPos) <= (double)(calculatedDistance * calculatedDistance))
                    {
                    	//adds the blocks for generation in this boulder
                    	//note, if user turns off an ore, that ore's chance is dumped into the below ore for generation
                    	int randomChance = rand.nextInt(1400);
                    	
                    	// 2/1400th chance for diamond ore
                    	if(ConfigUA.diamondOreSpawnrate != 0 && randomChance <= 1) {
                    		worldIn.setBlockState(blockpos, diamondOre, 4);
                    	}
                    	
                    	// 48/1400th chance for iron ore
                    	else if(ConfigUA.ironOreSpawnrate != 0 && randomChance <= 50){
                    		worldIn.setBlockState(blockpos, ironOre, 4);
                    	}
                    	
                    	// 82/1400th chance for coal ore
                    	else if(ConfigUA.coalOreSpawnrate != 0 && randomChance <= 130){
                    		worldIn.setBlockState(blockpos, coalOre, 4);
                    	}
                    	
                    	// 398/1400th chance for andesite
                    	else if(randomChance <= 480){
                    		worldIn.setBlockState(blockpos, andesite, 4);
                    	}
                    	
                    	// 352/1400th chance for cobblestone
                    	else if(randomChance <= 750){
                    		worldIn.setBlockState(blockpos, cobblestone, 4);
                    	}
                    	
                    	// 650/1400th chance for mossyCobblestone
                    	else {
                    		worldIn.setBlockState(blockpos, mossyCobblestone, 4);
                    	}
                    }
                }
                tempPos = tempPos.add(-(startRadius + 1) + rand.nextInt((int) (startRadius * 1.2)), 0 - rand.nextInt(2), -(startRadius + 1) + rand.nextInt((int) (startRadius * 1.2)));
           
                
            }
            boulderSpawned++;
        	tempPos = new BlockPos(position.getX()+rand.nextInt(3), 249, position.getZ()+rand.nextInt(3));
        }
        
        //finished generating the boulder
        return true;
    }
}
