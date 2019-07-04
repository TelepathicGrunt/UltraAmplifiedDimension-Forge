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

public class OceanSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig> {
	   private static final IBlockState AIR = Blocks.AIR.getDefaultState();
   private static final IBlockState WATER = Blocks.WATER.getDefaultState();
   
   public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
      this.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getMiddle(), config.getBottom(), seaLevel);
   }

   protected void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int xStart, int zStart, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, IBlockState top, IBlockState middle, IBlockState bottom, int seaLevel) {
	  
      int x = xStart & 15;
      int z = zStart & 15;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      IBlockState iblockstate = top;
      IBlockState iblockstate1 = middle;
      IBlockState aboveBlock = middle;
      IBlockState above2Block = middle;
      IBlockState above3Block = middle;
      IBlockState currentBlock;
      
      for(int y = 255; y >= seaLevel-10; --y) {
         blockpos$mutableblockpos.setPos(x, y, z);
         currentBlock = chunkIn.getBlockState(blockpos$mutableblockpos);
         
         
         if (currentBlock.getBlock() != null) {
        	 if(currentBlock == defaultBlock) {
                     iblockstate = WATER;
	                  
                     chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate, false);
                     currentBlock = iblockstate.getBlockState();
	         }
        	 else if(aboveBlock.getMaterial() == Material.WATER){
        		 
        		 if(above2Block.getMaterial() == Material.AIR) {
        			//makes bottom of terrain now solid
                     iblockstate = top;
                     if(y<256)
                    	 chunkIn.setBlockState(blockpos$mutableblockpos.up(), iblockstate, false);
        		 }else {
        			//makes bottom of terrain now solid
        			 
        			 if(above3Block.getMaterial() == Material.AIR) {
                         iblockstate = AIR;
                         iblockstate1 = AIR;
        			 }else {
                         iblockstate = bottom;
                         iblockstate1 = middle;
        			 }
                     
                     if(y<256)
                    	 chunkIn.setBlockState(blockpos$mutableblockpos.up(), iblockstate, false);
                     if(y<255 && above2Block.getMaterial() == Material.WATER)
                    	 chunkIn.setBlockState(blockpos$mutableblockpos.up(2), iblockstate1, false);
        		 }
        		 
        	 }
	      } 

         above3Block = above2Block;
         above2Block = aboveBlock;
         aboveBlock = currentBlock;
      }
   }
}