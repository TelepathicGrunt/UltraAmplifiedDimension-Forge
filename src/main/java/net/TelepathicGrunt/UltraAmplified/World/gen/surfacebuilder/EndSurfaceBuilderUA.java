package net.TelepathicGrunt.UltraAmplified.World.gen.surfacebuilder;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class EndSurfaceBuilderUA extends SurfaceBuilder<SurfaceBuilderConfig> 
{
	public EndSurfaceBuilderUA(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_) {
	      super(p_i51310_1_);
	}
	
   private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
   private static final BlockState STONE = Blocks.STONE.getDefaultState();
   private static final BlockState ENDSTONE = Blocks.END_STONE.getDefaultState();

   public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
	  int i = seaLevel + 1;
      int xpos = x & 15;
      int zpos = z & 15;
      int l = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i1 = -1;
      BlockState iblockstate = ENDSTONE;
      BlockState iblockstate1 = ENDSTONE;

      for(int ypos = 255; ypos >= 0; --ypos) {
         blockpos$mutableblockpos.setPos(xpos, ypos, zpos);
         BlockState iblockstate2 = chunkIn.getBlockState(blockpos$mutableblockpos);
         
         if (iblockstate2.getBlock() != null && iblockstate2.getMaterial() != Material.AIR) {
        	 if (iblockstate2 == STONE) {
        		
    	       if (i1 == -1) {
                  if (l <= 0) {
                     iblockstate = CAVE_AIR;
                     iblockstate1 = ENDSTONE;
                  } else if (ypos >= i - 4) {
                     iblockstate = ENDSTONE;
                     iblockstate1 = ENDSTONE;
                     
                  }
                  
                  i1 = l;
                  if (ypos >= i - 1) {
                     chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate, false);
                  } else {
                     chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate1, false);
                  }
               } else if (i1 > 0) {
                  --i1;
                  chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate1, false);
               }
               else {
             	  chunkIn.setBlockState(blockpos$mutableblockpos, ENDSTONE, false);
               }
        	 }
            
         } else {
            i1 = -1;
         }
      }

   }
}