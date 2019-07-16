package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
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

public class ContainWaterForOceans extends Feature<ContainWaterConfig> {
	  public ContainWaterForOceans(Function<Dynamic<?>, ? extends ContainWaterConfig> configFactoryIn) {
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
	     
         //needs to take up all 4 chunks so biome borders betwen ocean and non-oceans are cleaner
    	 for(int x = -8; x < 24; ++x) {
             for(int z = -8; z < 24; ++z) {
                 
            	 for(int y = 256; y > ConfigUA.seaLevel-10; y--) {

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
    	                 for (Direction face : Direction.Plane.HORIZONTAL) {
    	
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
                	        	   worldIn.setBlockState(pos.add(x, y, z), configBlock.middleBlock, 2);
            	        	   }
            	        	   
            	        	   
            	        	   //place first config block if no solid block above and below
            	        	   else{
            	        		   //if config top block is dead coral, randomly chooses any dead coral block to place
            	        		   if(useCoralTop) {
            	        			   worldIn.setBlockState(pos.add(x, y, z), DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
            	        		   }else {
            	        			   worldIn.setBlockState(pos.add(x, y, z), configBlock.topBlock, 2);
            	        		   }
            	        	   }
         	        	   }
         	        	   
        	        	   //place first config block if too high
         	        	   //if config top block is dead coral, randomly chooses any dead coral block to place
         	        	   else if(useCoralTop) {
    	        			   worldIn.setBlockState(pos.add(x, y, z), DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
    	        		   }else {
    	        			   worldIn.setBlockState(pos.add(x, y, z), configBlock.topBlock, 2);
    	        		   }
     	               }
         	           else {
         	        	   //water block is contained 
         	        	   
     	        		   blockAbove = worldIn.getBlockState(pos.add(x, y+1, z));
      	        		   
	      	        	   //if above is middle block, replace above block with third config block so middle block (sand/gravel) cannot fall.
     	        		   if(blockAbove == configBlock.middleBlock) {
     	        			   if(useCoralBottom) {
         	        			  worldIn.setBlockState(pos.add(x, y+1, z), DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
         	        		   }
         	        		   else{
    	      	        		   worldIn.setBlockState(pos.add(x, y+1, z), configBlock.bottomBlock, 2);
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