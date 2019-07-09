package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.ContainWaterConfig;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class ContainWaterForOceans extends Feature<ContainWaterConfig> {
	  private final static IBlockState ICE = Blocks.ICE.getDefaultState();
	  private final static IBlockState[] DEAD_CORAL_ARRAY = { 
			  Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState(),
			  Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultState(), 
			  Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultState(), 
			  Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultState(),
			  Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultState()
			};
	
	   public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkSettings, Random random, BlockPos pos, ContainWaterConfig configBlock) {
	     
		 //set y to 0
		 pos.down(pos.getY());
		 
  		 boolean notContainedFlag;
       	 IBlockState currentblock;
       	 IBlockState blockAbove;
         boolean useCoralTop = configBlock.topBlock.getDefaultState() == DEAD_CORAL_ARRAY[0];
         boolean useCoralBottom = configBlock.topBlock.getDefaultState() == DEAD_CORAL_ARRAY[0];
	     
         //needs to take up all 4 chunks so biome borders betwen ocean and non-oceans are cleaner
    	 for(int x = -8; x < 24; ++x) {
             for(int z = -8; z < 24; ++z) {
                 
            	 for(int y = 256; y > worldIn.getSeaLevel(); y--) {

                  	currentblock = worldIn.getBlockState(pos.add(x, y, z));
                  	
                  	//move down until we hit a non-air block
            		 while(currentblock.getMaterial() == Material.AIR && y > 0) 
            		 {
                 		y--;
                 		currentblock = worldIn.getBlockState(pos.add(x, y, z));
                 	 }
            		 
                 	
            		 //checks to see if we are at a water block
                 	if(!currentblock.getFluidState().isEmpty()) {
                 		notContainedFlag = false;
         	            
         	            /*
         	            //must be solid all around even diagonally
         	            for(int x2 = -1; x2 < 2; x2++) {
         	            	for(int z2 = -1; z2 < 2; z2++) {
         	                	
         	            		material = worldIn.getBlockState(pos.add(x, y, z).west(x2).north(z2)).getMaterial();
         	            		
         	            		if(!material.isSolid() &&
         	                        material != Material.WATER ) 
         	                   	{
         	                   		notContainedFlag = true;
         	                   	}
         	            	}
         	            }
         	            */
         	            
                     
    	     	        //Adjacent blocks must be solid    
    	                 for (EnumFacing face : EnumFacing.Plane.HORIZONTAL) {
    	
    	                 	currentblock = worldIn.getBlockState(pos.add(x, y, z).offset(face));
    	                 	
    	                 	//detects air, snow, etc and ignores Ice as ice is not solid and has a fluid state
    	                 	if(currentblock != ICE && !currentblock.isSolid() && currentblock.getFluidState().isEmpty()) 
    	                 	{
    	                 		notContainedFlag = true;
    	                 	}
    	                 }
    	                 
         	           if (notContainedFlag)
     	               {
         	        	   if(y < 256) {
         	        		   
         	        		   blockAbove = worldIn.getBlockState(pos.add(x, y+1, z));
         	        		   
            	        	   
            	        	   if(blockAbove.isSolid() || !blockAbove.getFluidState().isEmpty()) {
            	        		   
            	        		   //if above is solid or water, place second config block
                	        	   worldIn.setBlockState(pos.add(x, y, z), configBlock.middleBlock.getDefaultState(), 2);
            	        	   }
            	        	   
            	        	   
            	        	   //place first config block if no solid block above and below
            	        	   else{
            	        		   //if config top block is dead coral, randomly chooses any dead coral block to place
            	        		   if(useCoralTop) {
            	        			   worldIn.setBlockState(pos.add(x, y, z), DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
            	        		   }else {
            	        			   worldIn.setBlockState(pos.add(x, y, z), configBlock.topBlock.getDefaultState(), 2);
            	        		   }
            	        	   }
         	        	   }
         	        	   
        	        	   //place first config block if too high
         	        	   //if config top block is dead coral, randomly chooses any dead coral block to place
         	        	   else if(useCoralTop) {
    	        			   worldIn.setBlockState(pos.add(x, y, z), DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
    	        		   }else {
    	        			   worldIn.setBlockState(pos.add(x, y, z), configBlock.topBlock.getDefaultState(), 2);
    	        		   }
     	               }
         	           else {
         	        	   //water block is contained 
         	        	   
     	        		   blockAbove = worldIn.getBlockState(pos.add(x, y+1, z));
      	        		   
	      	        	   //if above is middle block, replace above block with third config block so middle block (sand/gravel) cannot fall.
     	        		   if(blockAbove == configBlock.middleBlock.getDefaultState()) {
     	        			   if(useCoralBottom) {
         	        			  worldIn.setBlockState(pos.add(x, y+1, z), DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
         	        		   }
         	        		   else{
    	      	        		   worldIn.setBlockState(pos.add(x, y+1, z), configBlock.bottomBlock.getDefaultState(), 2);
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