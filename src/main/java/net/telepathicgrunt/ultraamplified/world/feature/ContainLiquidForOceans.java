package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;

public class ContainLiquidForOceans extends Feature<NoFeatureConfig> {
	  public ContainLiquidForOceans(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
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
	  

	   public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos pos, NoFeatureConfig configBlock) {
	     
		 //set y to 0
		 pos.down(pos.getY());
		 
  		 boolean notContainedFlag;
       	 BlockState currentblock;
       	 BlockState blockAbove;
         Biome oceanBiome = null;
         boolean bordersOcean = false;
         BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(pos);
         

         //checks to see if there is an ocean biome in this chunk
         //breaks out of nested loop if ocean if found so oceanBiome holds the ocean
    	 for(int x = 0; x < 16; ++x) {
             for(int z = 0; z < 16; ++z) {
            	 oceanBiome = worldIn.func_226691_t_(blockpos$Mutable.add(x, 0, z));
            	 if(BiomeGenHelper.isOcean(oceanBiome)) {
            		 bordersOcean = true;
            		 x = 16;
            		 break;
                 }
             }
         }
    	 
    	 //does not do anything if there is no ocean biome
    	 if(!bordersOcean) {
    		 return false;
    	 }
         
    	 //ocean biome was found and thus, is not null. Can safely contain all water in this chunk
    	 for(int x = 0; x < 16; ++x) {
             for(int z = 0; z < 16; ++z) {
                 boolean useCoralTop = oceanBiome.getSurfaceBuilderConfig().getTop() == DEAD_CORAL_ARRAY[0];
                 boolean useCoralBottom = oceanBiome.getSurfaceBuilderConfig().getTop() == DEAD_CORAL_ARRAY[0];
            	 
            	 for(int y = 256; y > ConfigUA.seaLevel-10; y--) {
            		 
            		blockpos$Mutable.setPos(pos.getX() + x, 0, pos.getZ() + z);
                  	currentblock = worldIn.getBlockState(blockpos$Mutable.up(y));
                  	
                  	//move down until we hit a liquid block
            		 while(currentblock.getFluidState().isEmpty() && y > 10) 
            		 {
                 		y--;
                 		currentblock = worldIn.getBlockState(blockpos$Mutable.up(y));
                 	 }
            		 if(y == 10) {
            			 continue;
            		 }
            		 
            		 //y value is now fully set for rest of code
             		 blockpos$Mutable.setPos(pos.getX() + x, y, pos.getZ() + z);
            		 
                 	
             		notContainedFlag = false;
     	            
     	            /*
     	            //must be solid all around even diagonally
     	            for(int x2 = -1; x2 < 2; x2++) {
     	            	for(int z2 = -1; z2 < 2; z2++) {
     	                	
     	            		material = worldIn.getBlockState(blockpos$Mutable.west(x2).north(z2)).getMaterial();
     	            		
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
	
	                 	currentblock = worldIn.getBlockState(blockpos$Mutable.offset(face));
	                 	
	                 	//detects air, snow, etc and ignores Ice as ice is not solid and has a fluid state
	                 	if(currentblock != ICE && !currentblock.isSolid() && currentblock.getFluidState().isEmpty()) 
	                 	{
	                 		notContainedFlag = true;
	                 	}
	                 }
	                 
     	           if (notContainedFlag)
 	               {
     	        	   if(y < 256) {
     	        		   
     	        		   blockAbove = worldIn.getBlockState(blockpos$Mutable.up());
     	        		   
        	        	   
        	        	   if(blockAbove.isSolid() || !blockAbove.getFluidState().isEmpty()) {
        	        		   
        	        		   //if above is solid or water, place second config block
            	        	   worldIn.setBlockState(blockpos$Mutable, oceanBiome.getSurfaceBuilderConfig().getUnder(), 2);
        	        	   }
        	        	   
        	        	   
        	        	   //place first config block if no solid block above and below
        	        	   else{
        	        		   //if config top block is dead coral, randomly chooses any dead coral block to place
        	        		   if(useCoralTop) {
        	        			   worldIn.setBlockState(blockpos$Mutable, DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
        	        		   }else {
        	        			   worldIn.setBlockState(blockpos$Mutable, oceanBiome.getSurfaceBuilderConfig().getTop(), 2);
        	        		   }
        	        	   }
     	        	   }
     	        	   
    	        	   //place first config block if too high
     	        	   //if config top block is dead coral, randomly chooses any dead coral block to place
     	        	   else if(useCoralTop) {
	        			   worldIn.setBlockState(blockpos$Mutable, DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
	        		   }else {
	        			   worldIn.setBlockState(blockpos$Mutable, oceanBiome.getSurfaceBuilderConfig().getTop(), 2);
	        		   }
 	               }
     	           else {
     	        	   //water block is contained 
     	        	   
 	        		   blockAbove = worldIn.getBlockState(blockpos$Mutable.up());
  	        		   
      	        	   //if above is middle block, replace above block with third config block so middle block (sand/gravel) cannot fall.
 	        		   if(blockAbove == oceanBiome.getSurfaceBuilderConfig().getUnder()) {
 	        			   if(useCoralBottom) {
     	        			  worldIn.setBlockState(blockpos$Mutable.up(), DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
     	        		   }
     	        		   else{
	      	        		   worldIn.setBlockState(blockpos$Mutable.up(), ((SurfaceBuilderConfig) oceanBiome.getSurfaceBuilderConfig()).getUnderWaterMaterial(), 2);
	      	        	   }
      	        	   }
     	           }
                 	
                 }
              }
         }
         return true;
	      
	   }
	}