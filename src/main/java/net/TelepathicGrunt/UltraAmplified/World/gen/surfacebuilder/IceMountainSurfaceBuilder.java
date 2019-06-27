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

public class IceMountainSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig> 
{
	   private static final IBlockState ICE = Blocks.ICE.getDefaultState();
	   private static final IBlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
	
public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
	  IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
	  IBlockState iblockstate = SNOW_BLOCK;
      IBlockState iblockstate1 = ICE;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i = -1;
      int j = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
      int k = x & 15;
      int l = z & 15;

      for(int i1 = startHeight; i1 >= 0; --i1) {
         blockpos$mutableblockpos.setPos(k, i1, l);
         IBlockState iblockstate2 = chunkIn.getBlockState(blockpos$mutableblockpos);
         if (iblockstate2.getMaterial() == Material.AIR) {
            i = -1;
         } else if (iblockstate2.getBlock() == ICE.getBlock()) {
            if (i == -1) {
               if (j <= 0) {
                  iblockstate = Blocks.AIR.getDefaultState();
                  iblockstate1 = defaultBlock;
               } else if (i1 >= seaLevel - 4 && i1 <= seaLevel + 1) {
                  iblockstate = SNOW_BLOCK;
                  iblockstate1 = ICE;
               }

               
               i = j;
               if (i1 >= seaLevel - 1) {
                  chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate, false);
               } else if (i1 < seaLevel - 7 - j) {
                  iblockstate = Blocks.AIR.getDefaultState();
                  iblockstate1 = defaultBlock;
                  chunkIn.setBlockState(blockpos$mutableblockpos, ICE, false);
               } else {
                  chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate1, false);
               }
            } else if (i > 0) {
               --i;
               chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate1, false);
            }
         }
      }

   }
}