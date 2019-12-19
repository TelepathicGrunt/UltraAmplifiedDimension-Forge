package net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class NetherSurfaceBuilderUA extends SurfaceBuilder<SurfaceBuilderConfig> 
{
	public NetherSurfaceBuilderUA(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_) {
	      super(p_i51310_1_);
	}

   private static final BlockState STONE = Blocks.STONE.getDefaultState();
   private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
   private static final BlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
   private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
   private static final BlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
   private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
   private static final BlockState WATER = Blocks.WATER.getDefaultState();
   private static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
   protected long field_205552_a;
   protected OctavesNoiseGenerator field_205553_b;

   public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
	  int sealevel = seaLevel + 1;
      int xpos = x & 15;
      int zpos = z & 15;
      int l = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i1 = -1;
      BlockState iblockstate = NETHERRACK;
      BlockState iblockstate1 = NETHERRACK;

      for(int ypos = 255; ypos >= 0; --ypos) {
         blockpos$mutableblockpos.setPos(xpos, ypos, zpos);
         BlockState iblockstate2 = chunkIn.getBlockState(blockpos$mutableblockpos);
         
         if (iblockstate2.getBlock() == null || iblockstate2.getMaterial() == Material.AIR) {
             i1 = -1;
         }
         else if(iblockstate2.getMaterial() == Material.WATER) {

        	 if(ypos < ConfigUA.seaLevel - 7) {
        		 chunkIn.setBlockState(blockpos$mutableblockpos, LAVA, false);
        	 }
        	 else if(ypos == ConfigUA.seaLevel - 7) {
        		 chunkIn.setBlockState(blockpos$mutableblockpos, MAGMA, false);
        	 }
        	 else{
        		 chunkIn.setBlockState(blockpos$mutableblockpos, ConfigUA.lavaOcean ? LAVA : WATER, false);
        	 }
        	 
             i1 = -1;
    	 }else {
    		 if (iblockstate2 == STONE) {
        		 
       	      boolean flag = this.field_205553_b.func_205563_a((double)x * 0.13125D, (double)z * 0.13125D, ypos/5) + random.nextDouble() * 0.2D > 4.5D;
       	      boolean flag1 = this.field_205553_b.func_205563_a((double)x * 0.13125D, (ypos/5)+109.0D, (double)z * 0.13125D) + random.nextDouble() * 0.2D > 5.5D;
       	     
       	       if (i1 == -1) {
                     if (l <= 0) {
                        iblockstate = CAVE_AIR;
                        iblockstate1 = NETHERRACK;
                     } else if (ypos >= sealevel - 4) {
                        iblockstate = NETHERRACK;
                        iblockstate1 = NETHERRACK;

                        
                        if((noise > -3.85 && noise < -3.7) || (noise > -0.1 && noise < 0.05) || (noise > 3.7 && noise < 3.85)) {
                       	 iblockstate = MAGMA;
                        }
                        
                        
                        if (flag1) {
                       	 if(random.nextFloat() < 0.015F) {
                                iblockstate = LAVA;
                       	 }
                       	 else {
                            	iblockstate = GRAVEL;
                       	 }
                        }

                        if (flag) {
                       	 if(random.nextFloat() < 0.005F) {
                                iblockstate = LAVA;
                       	 }
                       	 else {
                       		 iblockstate = SOUL_SAND;
                       	 }
                       	 
                            iblockstate1 = SOUL_SAND;
                        }
                        
                        
                        
                        
                        
                        //rare lava spot
                   	 if(random.nextFloat() < 0.0005F) {
                            iblockstate = LAVA;
                   	 }
                   	 
                     }
                     
                     i1 = l;
                     if (ypos >= sealevel - 1) {
                        chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate, false);
                     } else {
                        chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate1, false);
                     }
                  } else if (i1 > 0) {
                     --i1;
                     chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate1, false);
                  }
                  else {
                	  chunkIn.setBlockState(blockpos$mutableblockpos, NETHERRACK, false);
                  }
           	 }
         }
      }

   }

   public void setSeed(long seed) {
      if (this.field_205552_a != seed || this.field_205553_b == null) {
         this.field_205553_b = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 4);
      }

      this.field_205552_a = seed;
   }
}