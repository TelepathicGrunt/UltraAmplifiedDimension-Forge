package net.TelepathicGrunt.UltraAmplified.World.gen.surfacebuilder;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class DeepOceanSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig> {
   private static final IBlockState AIR = Blocks.AIR.getDefaultState();
   private static final IBlockState WATER = Blocks.WATER.getDefaultState();
   private final static IBlockState[] DEAD_CORAL_ARRAY = { 
			  Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState(),
			  Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultState(), 
			  Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultState(), 
			  Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultState(),
			  Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultState()
			};
   
   public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
      this.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getMiddle(), config.getBottom(), seaLevel);
   }

   protected void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int xStart, int zStart, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, IBlockState topBlock, IBlockState middleBlock, IBlockState bottomBlock, int seaLevel) {
	  
      int x = xStart & 15;
      int z = zStart & 15;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      IBlockState bottom = topBlock;
      IBlockState middle = middleBlock;
      IBlockState aboveBlock = middleBlock;
      IBlockState above2Block = middleBlock;
      IBlockState above3Block = middleBlock;
      IBlockState currentBlock;
      boolean useCoral = bottomBlock.getBlockState() == DEAD_CORAL_ARRAY[0];
      
      for(int y = 255; y >= seaLevel-10; --y) {
         blockpos$mutableblockpos.setPos(x, y, z);
         currentBlock = chunkIn.getBlockState(blockpos$mutableblockpos);
         
         
         if (currentBlock.getBlock() != null) {
        	 if(currentBlock == defaultBlock) {
     		 		//turns terrain into water to manipulate later
                     bottom = WATER;
                	 chunkIn.setBlockState(blockpos$mutableblockpos, bottom, false);
                     currentBlock = bottom;
	         }
        	 else if(aboveBlock.getMaterial() == Material.WATER && y < 256){
        		 
        		 if(above2Block.getMaterial() == Material.AIR) {
         			//sets very bottom of terrain to bottom block
                     bottom = topBlock;
                	 chunkIn.setBlockState(blockpos$mutableblockpos.up(), bottom, false);
        		 }
        		 else {
        			 
        			 //removes two block high land
        			 if(above3Block.getMaterial() == Material.AIR) {
                         bottom = AIR;
                         middle = AIR;
        			 }
        			 //Chooses blocks for bottom of terrain
        			 else {
        				 if(useCoral) {
	                    	 //if coral is passed in, randomly chooses a coral block
        					 bottom = DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)];
	                     }else {
	                         bottom = bottomBlock;
	                     }
        				 
        				 //prevent ravines from making huge amounts of sand and gravel that can fall which causes an insane spike in lag 
        				 if(y > 65) {
                             middle = middleBlock;
        				 }else {
                             middle = bottomBlock;
        				 }
        			 }
                     
                	 chunkIn.setBlockState(blockpos$mutableblockpos.up(), bottom, false);
                     currentBlock = bottom;
                     
                     if(above2Block.getMaterial() == Material.WATER)
                    	 chunkIn.setBlockState(blockpos$mutableblockpos.up(2), middle, false);
        		 }
        		 
        	 }
	      } 

         above3Block = above2Block;
         above2Block = aboveBlock;
         aboveBlock = currentBlock;
      }
   }
}