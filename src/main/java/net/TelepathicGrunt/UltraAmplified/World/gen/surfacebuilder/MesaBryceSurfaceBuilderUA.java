package net.TelepathicGrunt.UltraAmplified.World.gen.surfacebuilder;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.MesaSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class MesaBryceSurfaceBuilderUA extends MesaSurfaceBuilder {
	   private static final IBlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
	   private static final IBlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
	   private static final IBlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();

	   public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
	      double d0 = 0.0D;
	      double d1 = Math.min(Math.abs(noise), this.field_202617_c.getValue((double)x * 0.25D, (double)z * 0.25D));
	      if (d1 > -1.5D) {
	         double d3 = Math.abs(this.field_202618_d.getValue((double)x * 1500.001953125D, (double)z * 1500.001953125D));
	         d0 = d1 * d1 * 8.5D;
	         double d4 = Math.ceil(d3 * 1200.0D) + 1000.0D;
	         if (d0 > d4) {
	            d0 = d4;
	         }

	         d0 = d0 + 95.0D;
	      }

	      int l = x & 15;
	      int i = z & 15;
	      IBlockState iblockstate2 = WHITE_TERRACOTTA;
	      IBlockState iblockstate = biomeIn.getSurfaceBuilderConfig().getMiddle();
	      int i1 = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
	      boolean flag = Math.cos(noise / 3.0D * Math.PI) > 0.0D;
	      int j = -1;
	      boolean flag1 = false;
	      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

	      //might need to make k start at 255
	      for(int k = Math.max(startHeight, (int)d0 + 1); k >= 0; --k) {
	         blockpos$mutableblockpos.setPos(l, k, i);
	         if (chunkIn.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.AIR && k < (int)d0) {
	            chunkIn.setBlockState(blockpos$mutableblockpos, defaultBlock, false);
	         }

	         IBlockState iblockstate1 = chunkIn.getBlockState(blockpos$mutableblockpos);
	         if (iblockstate1.getMaterial() == Material.AIR) {
	            j = -1;
	         } else if (iblockstate1.getBlock() == defaultBlock.getBlock() || iblockstate1.getBlock() == Blocks.NETHERRACK || iblockstate1.getBlock() == Blocks.END_STONE) {
	            if (j == -1) {
	               flag1 = false;
	               if (i1 <= 0) {
	                  iblockstate2 = Blocks.AIR.getDefaultState();
	                  iblockstate = defaultBlock;
	               } else if (k >= seaLevel - 4 && k <= seaLevel + 1) {
	                  iblockstate2 = WHITE_TERRACOTTA;
	                  iblockstate = biomeIn.getSurfaceBuilderConfig().getMiddle();
	               }

	               if (k < seaLevel-5 && (iblockstate2 == null || iblockstate2.getMaterial() == Material.AIR)) {
	                  iblockstate2 = defaultFluid;
	               }

	               j = i1 + Math.max(0, k - seaLevel);
	               if (k >= seaLevel - 1) {
	                  if (k <= seaLevel + 25 + i1) {
	                     chunkIn.setBlockState(blockpos$mutableblockpos, biomeIn.getSurfaceBuilderConfig().getTop(), false);
	                     flag1 = true;
	                  } else {
	                     IBlockState iblockstate3;
	                     if (k >= 64 && k <= 127) {
	                        if (flag) {
	                           iblockstate3 = TERRACOTTA;
	                        } else {
	                           iblockstate3 = this.func_202614_a(x, k, z);
	                        }
	                     } else {
	                        iblockstate3 = ORANGE_TERRACOTTA;
	                     }

	                     chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate3, false);
	                  }
	               } else {
	                  chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate, false);
	                  Block block = iblockstate.getBlock();
	                  if (block == Blocks.WHITE_TERRACOTTA || block == Blocks.ORANGE_TERRACOTTA || block == Blocks.MAGENTA_TERRACOTTA || block == Blocks.LIGHT_BLUE_TERRACOTTA || block == Blocks.YELLOW_TERRACOTTA || block == Blocks.LIME_TERRACOTTA || block == Blocks.PINK_TERRACOTTA || block == Blocks.GRAY_TERRACOTTA || block == Blocks.LIGHT_GRAY_TERRACOTTA || block == Blocks.CYAN_TERRACOTTA || block == Blocks.PURPLE_TERRACOTTA || block == Blocks.BLUE_TERRACOTTA || block == Blocks.BROWN_TERRACOTTA || block == Blocks.GREEN_TERRACOTTA || block == Blocks.RED_TERRACOTTA || block == Blocks.BLACK_TERRACOTTA) {
	                     chunkIn.setBlockState(blockpos$mutableblockpos, ORANGE_TERRACOTTA, false);
	                  }
	               }
	            } else if (j > 0) {
	               --j;
	               if (flag1) {
	                  chunkIn.setBlockState(blockpos$mutableblockpos, ORANGE_TERRACOTTA, false);
	               } else {
	                  chunkIn.setBlockState(blockpos$mutableblockpos, this.func_202614_a(x, k, z), false);
	               }
	            }
	         }
	      }

	   }
	}