package net.telepathicgrunt.ultraamplified.world.feature.carver;

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
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;

public class SuperLongRavineCarver extends WorldCarver<ProbabilityConfig> {
    public SuperLongRavineCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> p_i49921_1_, int p_i49921_2_) {
		super(p_i49921_1_, p_i49921_2_);
	}

	private final float[] field_202536_i = new float[1024];
    protected static final BlockState STONE = Blocks.STONE.getDefaultState();
    protected static final BlockState WATER = Blocks.WATER.getDefaultState();
    protected static final BlockState LAVA = Blocks.LAVA.getDefaultState();
    protected BlockState fillerBlock = Blocks.STONE.getDefaultState();
    
    private static Map<BlockState, BlockState> canReplaceMap = createMap();
	
	private static final Map<BlockState, BlockState> createMap() 
	{
        Map<BlockState, BlockState> result = new HashMap<BlockState, BlockState>();
        
        result.put(Blocks.NETHERRACK.getDefaultState(), Blocks.NETHERRACK.getDefaultState()); 
        result.put(Blocks.ICE.getDefaultState(), Blocks.ICE.getDefaultState()); 
        result.put(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.ICE.getDefaultState()); 
        result.put(Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState()); 
        
        return Collections.unmodifiableMap(result);
    }
	
    private static Map<Biome, BlockState> fillerBiomeMap;

	/**
	 * Have to make this map much later since the biomes needs to be initialized first and that's delayed a bit
	 */
	public static void setFillerMap() {
		if (fillerBiomeMap == null) {
			fillerBiomeMap = new HashMap<Biome, BlockState>();

			fillerBiomeMap.put(BiomeInit.NETHER, Blocks.NETHERRACK.getDefaultState()); 
			fillerBiomeMap.put(BiomeInit.ICE_MOUNTAIN, Blocks.ICE.getDefaultState()); 
			fillerBiomeMap.put(BiomeInit.ICE_SPIKES, Blocks.ICE.getDefaultState()); 
			fillerBiomeMap.put(BiomeInit.DEEP_FROZEN_OCEAN, Blocks.ICE.getDefaultState()); 
			fillerBiomeMap.put(BiomeInit.FROZEN_OCEAN, Blocks.ICE.getDefaultState()); 
	        fillerBiomeMap.put(BiomeInit.BARREN_END_FIELD, Blocks.END_STONE.getDefaultState()); 
	        fillerBiomeMap.put(BiomeInit.END, Blocks.END_STONE.getDefaultState()); 
		}
	}

    public boolean shouldCarve(Random p_212246_2_, int chunkX, int chunkZ, ProbabilityConfig config) {
    	return p_212246_2_.nextFloat() <= (float) (ConfigUA.ravineSpawnrate) / 850f;
     }

     public boolean carve(IChunk region, Random random, int seaLevel, int chunkX, int chunkZ, int originalX, int originalZ, BitSet mask, ProbabilityConfig config) {
       	 
    	
    	int i = (this.func_222704_c() * 2 - 1) * 16;
        double xpos = (double)(chunkX * 16 + random.nextInt(16));
        double height = (double)(random.nextInt(random.nextInt(4) + 3) + 17);
        double zpos = (double)(chunkZ * 16 + random.nextInt(16));
        float f = random.nextFloat() * ((float)Math.PI * 2F);
        float f1 = (random.nextFloat() - 0.5F) / 8.0F;
        float f2 = (random.nextFloat() * 1.3F + random.nextFloat()) * 1.3F;
        int j = i+12; //length of ravine. probably in chunks
        this.func_202535_a(region, random.nextLong(), originalX, originalZ, xpos, height, zpos, f2, f, f1, 0, j, random.nextDouble()/2+0.9D, mask);
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
           double placementXZBound = 6D + (double)(MathHelper.sin((float)j * (float)Math.PI / (float)p_202535_16_) * p_202535_12_);
           double placementYBound = placementXZBound * heightMultiplier;
           placementXZBound = placementXZBound * ((double)random.nextFloat() * 0.10D + 0.4D); //thickness
           placementYBound = placementYBound * 0.8D;
           float f2 = MathHelper.cos(p_202535_14_); //multiply by 0.1f to make cylinders
           randomBlockX += (double)(MathHelper.cos(p_202535_13_) * f2);
           randomBlockZ += (double)(MathHelper.sin(p_202535_13_) * f2);
           p_202535_14_ = p_202535_14_ * 0.5F;
           p_202535_14_ = p_202535_14_ + f1 * 0.03F;
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
           int k = Math.max(MathHelper.floor(yRange - placementYBound) - 1, 6);
           int l = Math.min(MathHelper.floor(yRange + placementYBound) + 1, this.maxHeight);
           int i1 = Math.max(MathHelper.floor(zRange - placementXZBound) - mainChunkZ * 16 - 1, 0);
           int j1 = Math.min(MathHelper.floor(zRange + placementXZBound) - mainChunkZ * 16 + 1, 16);
           if (i <= j && k <= l && i1 <= j1) {
              boolean flag = false;
              BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
              BlockPos.MutableBlockPos blockpos$mutableblockposup = new BlockPos.MutableBlockPos();
              BlockPos.MutableBlockPos blockpos$mutableblockposdown = new BlockPos.MutableBlockPos();
              

             for(int k1 = i; k1 < j; ++k1) {
                int x = k1 + mainChunkX * 16;
                double xSquaringModified = ((double)x + 0.5D - xRange) / placementXZBound;

                for(int i2 = i1; i2 < j1; ++i2) {
                   int z = i2 + mainChunkZ * 16;
                   double zSquaringModified = ((double)z + 0.5D - zRange) / placementXZBound;
				   double xzSquaredModified = xSquaringModified * xSquaringModified + zSquaringModified * zSquaringModified;
				   
                   if (xzSquaredModified < 1.0D) {

                      blockpos$mutableblockpos.setPos(x, 60, z);
                      fillerBlock = fillerBiomeMap.get(worldIn.getBiome(blockpos$mutableblockpos));
                   	  if (fillerBlock == null){
                   	 	fillerBlock = STONE; 
                   	  }
                      
                      for(int y = l; y > k; --y) {
                    	  
                         double d4 = ((double)(y - 1) + 0.5D - yRange) / placementYBound;
                         
                         if (xzSquaredModified * (double)this.field_202536_i[y - 1] + d4 * d4 / 6.0D < 1.0D) {
                          
                           blockpos$mutableblockpos.setPos(x, y, z);

                           BlockState currentBlockstate = worldIn.getBlockState(blockpos$mutableblockpos);
                           blockpos$mutableblockposup.setPos(blockpos$mutableblockpos).move(Direction.UP);
                           blockpos$mutableblockposdown.setPos(blockpos$mutableblockpos).move(Direction.DOWN);
                           BlockState aboveBlockstate = worldIn.getBlockState(blockpos$mutableblockposup);

                           if (this.canCarveBlock(currentBlockstate, aboveBlockstate) || 
                        		   canReplaceMap.containsKey(currentBlockstate)) {
                        	   
                              if (y - 1 < 10) {
                                 worldIn.setBlockState(blockpos$mutableblockpos, LAVA.getBlockState(), false);
                              } else {
           	                	   //carves the ravine
           	                	   worldIn.setBlockState(blockpos$mutableblockpos, AIR.getBlockState(), false);
                              }

                              flag = true;
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


 	/**
 	 * MC doesn't seem to do anything with the returned value in the end. Strange.
 	 * I wonder why.
 	 */
	protected boolean func_222708_a(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_) {
	    return true;
		//return (p_222708_1_ * p_222708_1_ + p_222708_5_ * p_222708_5_) * (double)this.field_202536_i[p_222708_7_ - 1] + p_222708_3_ * p_222708_3_ / 6.0D >= 1.0D;
	}
}
