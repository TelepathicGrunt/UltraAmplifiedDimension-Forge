package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;

public class WorldGenGiantStackableBoulderUA extends Feature<BlockBlobConfig> 
{
    private final static Block mossyCobblestone = Blocks.MOSSY_COBBLESTONE;
    private final static Block cobblestone = Blocks.COBBLESTONE;
    private final static Block andesite = Blocks.ANDESITE;
    private final static Block coalOre = Blocks.COAL_ORE;
    private final static Block ironOre = Blocks.IRON_ORE;
    private final static Block diamondOre = Blocks.DIAMOND_ORE;
    private final static int startRadius = 4;

    protected static final Set<Block> acceptableBlocks = 
    		Stream.of(
	    		Blocks.DIRT,
	    		Blocks.GRASS_BLOCK,
				Blocks.GRASS_PATH,
	    		Blocks.PODZOL,
    			mossyCobblestone,
	    		cobblestone,
	    		andesite,
	    		coalOre,
	    		ironOre,
	    		diamondOre
    		).collect(Collectors.toCollection(HashSet::new));
	
    
    
    public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> p_212245_2_, Random rand, BlockPos position, BlockBlobConfig p_212245_5_) {
        
    	int boulderSpawned = 0;
    	BlockPos tempPos = new BlockPos(position.getX()+rand.nextInt(3), 249, position.getZ()+rand.nextInt(3));
    	
        while (boulderSpawned < 15)
        {
        	//exit if there is a boulder already at 249
        	Block block = worldIn.getBlockState(tempPos).getBlock();
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
            	

                block = worldIn.getBlockState(tempPos.down()).getBlock();
                
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
                    	if(Config.diamondOreSpawnrate != 0 && randomChance <= 1) {
                    		worldIn.setBlockState(blockpos, diamondOre.getDefaultState(), 4);
                    	}
                    	
                    	// 48/1400th chance for iron ore
                    	else if(Config.ironOreSpawnrate != 0 && randomChance <= 50){
                    		worldIn.setBlockState(blockpos, ironOre.getDefaultState(), 4);
                    	}
                    	
                    	// 82/1400th chance for coal ore
                    	else if(Config.coalOreSpawnrate != 0 && randomChance <= 130){
                    		worldIn.setBlockState(blockpos, coalOre.getDefaultState(), 4);
                    	}
                    	
                    	// 398/1400th chance for andesite
                    	else if(randomChance <= 480){
                    		worldIn.setBlockState(blockpos, andesite.getDefaultState(), 4);
                    	}
                    	
                    	// 352/1400th chance for cobblestone
                    	else if(randomChance <= 750){
                    		worldIn.setBlockState(blockpos, cobblestone.getDefaultState(), 4);
                    	}
                    	
                    	// 650/1400th chance for mossyCobblestone
                    	else {
                    		worldIn.setBlockState(blockpos, mossyCobblestone.getDefaultState(), 4);
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
