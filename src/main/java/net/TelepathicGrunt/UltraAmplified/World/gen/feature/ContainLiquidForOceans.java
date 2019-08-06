package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.BiomeGenHelper;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.ContainWaterConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;

public class ContainLiquidForOceans extends Feature<ContainWaterConfig> {
	  public ContainLiquidForOceans(Function<Dynamic<?>, ? extends ContainWaterConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	  private final static BlockState ICE = Blocks.ICE.getDefaultState();
	  
	  private final static BlockState[] DEAD_CORAL_ARRAY = { 
			  Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState(),
			  Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultState(), 
			  Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultState(), 
			  Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultState(),
			  Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultState()
			};
	  

	   public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos pos, ContainWaterConfig configBlock) {
	     
		 //set y to 0
		 pos.down(pos.getY());
		 
  		 boolean notContainedFlag;
       	 BlockState currentblock;
       	 BlockState blockAbove;
         boolean useCoralTop = configBlock.topBlock == DEAD_CORAL_ARRAY[0];
         boolean useCoralBottom = configBlock.topBlock == DEAD_CORAL_ARRAY[0];
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
	     int extraRange = 0;
         
         //needs to take up all 4 chunks when non-ocean is present so biome borders between ocean and non-oceans are cleaner
         for(int xx = 0; xx < 16; xx+=15) {
        	 for(int zz = 0; zz < 16; zz+=15) {
        		 blockpos$mutableblockpos.setPos(pos.getX() + xx, 0, pos.getZ()+zz);
                 if(!BiomeGenHelper.isOcean(worldIn.getBiome(blockpos$mutableblockpos))) {
                	 extraRange = 6;
                	 xx = 17;
                	 zz = 17;
                 }
             }
         }
    	 for(int x = 0-extraRange; x < 16+extraRange; ++x) {
             for(int z = 0-extraRange; z < 16+extraRange; ++z) {
            	 for(int y = 256; y > ConfigUA.seaLevel-10; y--) {
            		 
            		blockpos$mutableblockpos.setPos(pos.getX() + x, 0, pos.getZ() + z);
                  	currentblock = worldIn.getBlockState(blockpos$mutableblockpos.up(y));
                  	
                  	//move down until we hit a non-air block
            		 while(currentblock.getMaterial() == Material.AIR && y > 0) 
            		 {
                 		y--;
                 		currentblock = worldIn.getBlockState(blockpos$mutableblockpos.up(y));
                 	 }
            		 
            		 //y value is now fully set for rest of code
             		 blockpos$mutableblockpos.setPos(pos.getX() + x, y, pos.getZ() + z);
            		 
                 	
            		 //checks to see if we are at a water block
                 	if(!currentblock.getFluidState().isEmpty()) {
                 		notContainedFlag = false;
         	            
         	            /*
         	            //must be solid all around even diagonally
         	            for(int x2 = -1; x2 < 2; x2++) {
         	            	for(int z2 = -1; z2 < 2; z2++) {
         	                	
         	            		material = worldIn.getBlockState(blockpos$mutableblockpos.west(x2).north(z2)).getMaterial();
         	            		
         	            		if(!material.isSolid() &&
         	                        material != Material.WATER ) 
         	                   	{
         	                   		notContainedFlag = true;
         	                   	}
         	            	}
         	            }
         	            */
         	            
                     
    	     	        //Adjacent blocks must be solid    
    	                 for (Direction face : Direction.Plane.HORIZONTAL) {
    	
    	                 	currentblock = worldIn.getBlockState(blockpos$mutableblockpos.offset(face));
    	                 	
    	                 	//detects air, snow, etc and ignores Ice as ice is not solid and has a fluid state
    	                 	if(currentblock != ICE && !currentblock.isSolid() && currentblock.getFluidState().isEmpty()) 
    	                 	{
    	                 		notContainedFlag = true;
    	                 	}
    	                 }
    	                 
         	           if (notContainedFlag)
     	               {
         	        	   if(y < 256) {
         	        		   
         	        		   blockAbove = worldIn.getBlockState(blockpos$mutableblockpos.up());
         	        		   
            	        	   
            	        	   if(blockAbove.isSolid() || !blockAbove.getFluidState().isEmpty()) {
            	        		   
            	        		   //if above is solid or water, place second config block
                	        	   worldIn.setBlockState(blockpos$mutableblockpos, configBlock.middleBlock, 2);
            	        	   }
            	        	   
            	        	   
            	        	   //place first config block if no solid block above and below
            	        	   else{
            	        		   //if config top block is dead coral, randomly chooses any dead coral block to place
            	        		   if(useCoralTop) {
            	        			   worldIn.setBlockState(blockpos$mutableblockpos, DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
            	        		   }else {
            	        			   worldIn.setBlockState(blockpos$mutableblockpos, configBlock.topBlock, 2);
            	        		   }
            	        	   }
         	        	   }
         	        	   
        	        	   //place first config block if too high
         	        	   //if config top block is dead coral, randomly chooses any dead coral block to place
         	        	   else if(useCoralTop) {
    	        			   worldIn.setBlockState(blockpos$mutableblockpos, DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
    	        		   }else {
    	        			   worldIn.setBlockState(blockpos$mutableblockpos, configBlock.topBlock, 2);
    	        		   }
     	               }
         	           else {
         	        	   //water block is contained 
         	        	   
     	        		   blockAbove = worldIn.getBlockState(blockpos$mutableblockpos.up());
      	        		   
	      	        	   //if above is middle block, replace above block with third config block so middle block (sand/gravel) cannot fall.
     	        		   if(blockAbove == configBlock.middleBlock) {
     	        			   if(useCoralBottom) {
         	        			  worldIn.setBlockState(blockpos$mutableblockpos.up(), DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
         	        		   }
         	        		   else{
    	      	        		   worldIn.setBlockState(blockpos$mutableblockpos.up(), configBlock.bottomBlock, 2);
    	      	        	   }
	      	        	   }
         	           }
                 	}
                 }
              }
         }
         return true;
	      
	   }
	}