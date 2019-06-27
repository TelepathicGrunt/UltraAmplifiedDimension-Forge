package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CaveCavityCarver extends WorldCarver<ProbabilityConfig> 
{
    protected static final IBlockState STONE = Blocks.STONE.getDefaultState();
    protected static final IBlockState LAVA = Blocks.LAVA.getDefaultState();
    protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
    protected static final IBlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
    protected static final IBlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();
    
    protected static final Set<IBlockState> ALLOWED_BLOCKS_TO_REPLACE = 
    		Stream.of(
				Blocks.NETHERRACK.getDefaultState(),
	    		Blocks.END_STONE.getDefaultState(),
	    		Blocks.ICE.getDefaultState(),
	    		Blocks.LAVA.getDefaultState(),
	    		Blocks.SNOW.getDefaultState()
    		).collect(Collectors.toCollection(HashSet::new));
    
    
    
    public boolean func_212246_a(IBlockReader p_212246_1_, Random p_212246_2_, int p_212246_3_, int p_212246_4_, ProbabilityConfig p_212246_5_) {
        return p_212246_2_.nextFloat() <= p_212246_5_.probability;
     }

     public boolean carve(IWorld region, Random random, int chunkX, int chunkZ, int originalX, int originalZ, BitSet mask, ProbabilityConfig config) {
        int i = (this.func_202520_b() * 2 - 1) * 16;
        int numberOfRooms = random.nextInt(5) + 5;

        for(int k = 0; k < numberOfRooms; ++k) {
           double x = (double)(chunkX * 16 + random.nextInt(16));
           double y = (double)random.nextInt(random.nextInt(30) + 15);
           double z = (double)(chunkZ * 16 + random.nextInt(16));
           if (random.nextInt(4) == 0) {
              float roomRadius = 36.0F + random.nextFloat() * 6.0F;
              this.addRoom(region, random.nextLong(), originalX, originalZ, x, y, z, roomRadius, 1.3D, mask);
           }
           
        }

        return true;
     }

     protected void addRoom(IWorld worldIn, long seed, int mainChunkX, int mainChunkZ, double xRange, double yRange, double zRange, float roomRadius, double roomHeight, BitSet mask) {
        double d0 = 1D + (double)(MathHelper.sin(((float)Math.PI / 2F)) * roomRadius);
        double d1 = d0 * roomHeight;
        this.carveAtTarget(worldIn, seed, mainChunkX, mainChunkZ, xRange + 1.0D, yRange, zRange, d0, d1, mask);
     }


     protected boolean carveAtTarget(IWorld worldIn, long seed, int mainChunkX, int mainChunkZ, double xRange, double yRange, double zRange, double placementXZBound, double placementYBound, BitSet mask) {
        double d0 = (double)(mainChunkX * 16 + 8);
        double d1 = (double)(mainChunkZ * 16 + 8);
        if (!(xRange < d0 - 16.0D - placementXZBound * 2.0D) && !(zRange < d1 - 16.0D - placementXZBound * 2.0D) && !(xRange > d0 + 16.0D + placementXZBound * 2.0D) && !(zRange > d1 + 16.0D + placementXZBound * 2.0D)) {
           int i = Math.max(MathHelper.floor(xRange - placementXZBound) - mainChunkX * 16 - 1, 0);
           int j = Math.min(MathHelper.floor(xRange + placementXZBound) - mainChunkX * 16 + 1, 16);
           int k = Math.max(MathHelper.floor(yRange - placementYBound) - 1, 1);
           int l = Math.min(MathHelper.floor(yRange + placementYBound) + 1, 248);
           int i1 = Math.max(MathHelper.floor(zRange - placementXZBound) - mainChunkZ * 16 - 1, 0);
           int j1 = Math.min(MathHelper.floor(zRange + placementXZBound) - mainChunkZ * 16 + 1, 16);
           if (this.doesAreaHaveFluids(worldIn, mainChunkX, mainChunkZ, i, j, k, l, i1, j1)) {
              return false;
           } else {
              boolean flag = false;
              BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
              BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
              BlockPos.MutableBlockPos blockpos$mutableblockpos2 = new BlockPos.MutableBlockPos();

              for(int k1 = i; k1 < j; ++k1) {
                 int l1 = k1 + mainChunkX * 16;
                 double d2 = ((double)l1 + 0.5D - xRange) / placementXZBound;

                 for(int i2 = i1; i2 < j1; ++i2) {
                    int j2 = i2 + mainChunkZ * 16;
                    double d3 = ((double)j2 + 0.5D - zRange) / placementXZBound;
                    if (!(d2 * d2 + d3 * d3 >= 1.0D)) {
                       boolean flag1 = false;

                       for(int k2 = l; k2 > k; --k2) {
                          double d4 = ((double)k2 - 0.5D - yRange) / placementYBound;
                          if (!(d4 <= -0.7D) && !(d2 * d2 + d4 * d4 + d3 * d3 >= 1.0D)) {
                             int l2 = k1 | i2 << 4 | k2 << 8;
                             if (!mask.get(l2)) {
                                mask.set(l2);
                                blockpos$mutableblockpos.setPos(l1, k2, j2);
                                IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);
                                IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1.setPos(blockpos$mutableblockpos).move(EnumFacing.UP));
                                if (iblockstate.getBlock() == Blocks.GRASS_BLOCK || iblockstate.getBlock() == Blocks.MYCELIUM) {
                                   flag1 = true;
                                }

                                if (this.isTargetSafeFromFalling(iblockstate, iblockstate1) || ALLOWED_BLOCKS_TO_REPLACE.contains(iblockstate)) {
                                   if (k2 < 11) {
                                      worldIn.setBlockState(blockpos$mutableblockpos, LAVA, 2);
                                   } else {
                                      worldIn.setBlockState(blockpos$mutableblockpos, DEFAULT_CAVE_AIR, 2);
                                      if (flag1) {
                                         blockpos$mutableblockpos2.setPos(blockpos$mutableblockpos).move(EnumFacing.DOWN);
                                         if (worldIn.getBlockState(blockpos$mutableblockpos2).getBlock() == Blocks.DIRT) {
                                            IBlockState iblockstate2 = worldIn.getBiome(blockpos$mutableblockpos).getSurfaceBuilderConfig().getTop();
                                            worldIn.setBlockState(blockpos$mutableblockpos2, iblockstate2, 2);
                                         }
                                      }
                                   }

                                   flag = true;
                                }
                                
                                if (iblockstate.getBlock() == Blocks.WATER) {
                                	worldIn.setBlockState(blockpos$mutableblockpos, DEFAULT_CAVE_AIR, 2);
                                	worldIn.setBlockState(blockpos$mutableblockpos1, DEFAULT_CAVE_AIR, 2);
                                	worldIn.setBlockState(blockpos$mutableblockpos2, DEFAULT_CAVE_AIR, 2);
                                    flag1 = true;
                                 }
                             }
                          }
                       }
                    }
                 }
              }

              return flag;
           }
        } else {
           return false;
        }
     }
     
     protected boolean doesAreaHaveFluids(IWorldReaderBase worldIn, int mainChunkX, int mainChunkZ, int minXPos, int maxXPos, int minYPos, int maxYPos, int minZPos, int maxZPos) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int i = minXPos; i < maxXPos; ++i) {
            for(int j = minZPos; j < maxZPos; ++j) {
               for(int k = minYPos - 1; k <= maxYPos + 1; ++k) {
                  if (this.terrainFluids.contains(worldIn.getFluidState(blockpos$mutableblockpos.setPos(i + mainChunkX * 16, k, j + mainChunkZ * 16)).getFluid())) {
                     return true;
                  }

                  if (k != maxYPos + 1 && !this.isInBounds(minXPos, maxXPos, minZPos, maxZPos, i, j)) {
                     k = maxYPos;
                  }
               }
            }
         }

         return false;
      }
 }