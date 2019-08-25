package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;

public class RavineCarver extends WorldCarver<ProbabilityConfig> {
    public RavineCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> p_i49921_1_, int p_i49921_2_) {
		super(p_i49921_1_, p_i49921_2_);
	}

	private final float[] field_202536_i = new float[1024];
    protected static final BlockState STONE = Blocks.STONE.getDefaultState();
    protected BlockState fillerBlock = Blocks.STONE.getDefaultState();
    protected static final BlockState WATER = Blocks.WATER.getDefaultState();
    protected static final BlockState LAVA = Blocks.LAVA.getDefaultState();
    
    private static final Map<BlockState, BlockState> fillerMap = createMap();
	
	private static Map<BlockState, BlockState> createMap() 
	{
        Map<BlockState, BlockState> result = new HashMap<BlockState, BlockState>();
        
        result.put(Blocks.NETHERRACK.getDefaultState(), Blocks.NETHERRACK.getDefaultState()); 
        result.put(Blocks.ICE.getDefaultState(), Blocks.ICE.getDefaultState()); 
        result.put(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.ICE.getDefaultState()); 
        result.put(Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState()); 
        
        return Collections.unmodifiableMap(result);
    }

    public boolean shouldCarve(Random p_212246_2_, int chunkX, int chunkZ, ProbabilityConfig config) {
        return p_212246_2_.nextFloat() <= config.probability;
     }

     public boolean carve(IChunk region, Random random, int seaLevel, int chunkX, int chunkZ, int originalX, int originalZ, BitSet mask, ProbabilityConfig config) {
       	 
    	Biome biome = region.getBiome(new BlockPos(originalX * 16, 100, originalZ * 16));
    	int i = (this.func_222704_c() * 2 - 1) * 16;
        double xpos = (double)(chunkX * 16 + random.nextInt(16));
        double height = biome == BiomeInit.NETHER ? 25 : (double)(random.nextInt(random.nextInt(2) + 1) + 42);
        double zpos = (double)(chunkZ * 16 + random.nextInt(16));
        float f = random.nextFloat() * ((float)Math.PI * 2F);
        float f1 = (random.nextFloat() - 0.5F) / 8.0F;
        float f2 = (random.nextFloat() * 2.0F + random.nextFloat()) * 2.0F;
        int j = i - random.nextInt(i / 4);
        this.func_202535_a(region, random.nextLong(), originalX, originalZ, xpos, height, zpos, f2, f, f1, 0, j, biome == BiomeInit.NETHER ? random.nextDouble()+2.5D : random.nextDouble()/3+1.9D, mask);
        return true;
     }

     private void func_202535_a(IChunk worldIn, long randomSeed, int mainChunkX, int mainChunkZ, double randomBlockX, double randomBlockY, double randomBlockZ, float p_202535_12_, float p_202535_13_, float p_202535_14_, int p_202535_15_, int p_202535_16_, double heightMultiplier, BitSet mask) {
        Random random = new Random(randomSeed);
        float f = 1.0F;

        for(int i = 0; i < 256; ++i) {
           if (i == 0 || random.nextInt(3) == 0) {
              f = 1.0F + random.nextFloat() * random.nextFloat();
           }

           this.field_202536_i[i] = f * f;
        }

        float f4 = 0.0F;
        float f1 = 0.0F;

        for(int j = p_202535_15_; j < p_202535_16_; ++j) {
           double placementXZBound = 2D + (double)(MathHelper.sin((float)j * (float)Math.PI / (float)p_202535_16_) * p_202535_12_);
           double placementYBound = placementXZBound * heightMultiplier;
           placementXZBound = placementXZBound * ((double)random.nextFloat() * 0.15D + 0.65D); //thickness
           placementYBound = placementYBound * 0.8D;
           float f2 = MathHelper.cos(p_202535_14_); //multiply by 0.1f to make cylinders
           randomBlockX += (double)(MathHelper.cos(p_202535_13_) * f2);
           randomBlockZ += (double)(MathHelper.sin(p_202535_13_) * f2);
           p_202535_14_ = p_202535_14_ * 0.8F;
           p_202535_14_ = p_202535_14_ + f1 * 0.08F;
           p_202535_13_ += f4 * 0.1F;
           f1 = f1 * 0.8F;
           f4 = f4 * 0.5F;
           f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 1.5F;
           f4 = f4 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 3.0F;
           if (random.nextInt(4) != 0) {
              if (!this.func_222702_a(mainChunkX, mainChunkZ, randomBlockX, randomBlockZ, j, p_202535_16_, p_202535_12_)) {
                 return;
              }

              this.carveAtTarget(worldIn, randomSeed, mainChunkX, mainChunkZ, randomBlockX, randomBlockY, randomBlockZ, placementXZBound, placementYBound, mask);
           }
        }

     }

     protected boolean carveAtTarget(IChunk worldIn, long seed, int mainChunkX, int mainChunkZ, double xRange, double yRange, double zRange, double placementXZBound, double placementYBound, BitSet mask) {
        double d0 = (double)(mainChunkX * 16 + 8);
        double d1 = (double)(mainChunkZ * 16 + 8);
        if (!(xRange < d0 - 16.0D - placementXZBound * 2.0D) && !(zRange < d1 - 16.0D - placementXZBound * 2.0D) && !(xRange > d0 + 16.0D + placementXZBound * 2.0D) && !(zRange > d1 + 16.0D + placementXZBound * 2.0D)) {
           int i = Math.max(MathHelper.floor(xRange - placementXZBound) - mainChunkX * 16 - 1, 0);
           int j = Math.min(MathHelper.floor(xRange + placementXZBound) - mainChunkX * 16 + 1, 16);
           int k = Math.max(MathHelper.floor(yRange - placementYBound) - 1, 9);
           int l = Math.min(MathHelper.floor(yRange + placementYBound) + 1, this.maxHeight);
           int i1 = Math.max(MathHelper.floor(zRange - placementXZBound) - mainChunkZ * 16 - 1, 0);
           int j1 = Math.min(MathHelper.floor(zRange + placementXZBound) - mainChunkZ * 16 + 1, 16);
           if (i <= j && k <= l && i1 <= j1) {
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
                   if (d2 * d2 + d3 * d3 < 1.0D) {

                      blockpos$mutableblockpos.setPos(l1, 60, j2);
                      fillerBlock = fillerMap.get(worldIn.getBiome(blockpos$mutableblockpos).getSurfaceBuilderConfig().getTop());
                   	  if (fillerBlock == null){
                   	 	fillerBlock = STONE; 
                   	  }
                      
                      for(int k2 = l; k2 > k; --k2) {
                         double d4 = ((double)(k2 - 1) + 0.5D - yRange) / placementYBound;
                         if ((d2 * d2 + d3 * d3) * (double)this.field_202536_i[k2 - 1] + d4 * d4 / 6.0D < 1.0D) {
                            int l2 = k1 | i2 << 4 | k2 << 8;
                            if (!mask.get(l2)) {
                               mask.set(l2);
                               blockpos$mutableblockpos.setPos(l1, k2, j2);

                               BlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);
                               blockpos$mutableblockpos1.setPos(blockpos$mutableblockpos).move(Direction.UP);
                               blockpos$mutableblockpos2.setPos(blockpos$mutableblockpos).move(Direction.DOWN);
                               BlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);
                               
                               if(!iblockstate1.getFluidState().isEmpty()) {
                            	   worldIn.setBlockState(blockpos$mutableblockpos, fillerBlock, false);
                            	   worldIn.setBlockState(blockpos$mutableblockpos1, fillerBlock, false);
                            	   worldIn.setBlockState(blockpos$mutableblockpos2, fillerBlock, false);
                                   flag = true;
                               }
                               else if (this.canCarveBlock(iblockstate, iblockstate1) || fillerMap.containsKey(iblockstate)) {
                                  if (k2 - 1 < 10) {
                                     worldIn.setBlockState(blockpos$mutableblockpos, LAVA.getBlockState(), false);
                                  } else {
	                                   boolean bordersFluid = false;
	               	            	   
	               	                   for(Direction direction : Direction.Plane.HORIZONTAL) {
	               	                	    if(!worldIn.getBlockState(blockpos$mutableblockpos.offset(direction)).getFluidState().isEmpty()) {
	               	                	    	bordersFluid = true;
	               	                	    }
	               	                   }
	               	                   
	               	                   if(!worldIn.getBlockState(blockpos$mutableblockpos.up()).getFluidState().isEmpty()) {
	               	                	   bordersFluid = true;
	               	                   }
	               	            	   
	               	                   if(bordersFluid) {
	               	                	   worldIn.setBlockState(blockpos$mutableblockpos, fillerBlock, false);
	               	                   }else {
	               	                	   worldIn.setBlockState(blockpos$mutableblockpos, CAVE_AIR.getBlockState(), false);
	               	                   }
                                  }

                                  flag = true;
                               }

                               
                            }
                         }
                      }
                   }
                }
             }

             return flag;
          } else {
             return false;
          }
       } else {
          return false;
       }
    }
     

     //not used i believe for our class
	protected boolean func_222708_a(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_) {
	    return (p_222708_1_ * p_222708_1_ + p_222708_5_ * p_222708_5_) * (double)this.field_202536_i[p_222708_7_ - 1] + p_222708_3_ * p_222708_3_ / 6.0D >= 1.0D;
	}
}
