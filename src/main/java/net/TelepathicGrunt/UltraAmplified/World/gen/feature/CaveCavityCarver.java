package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CaveCavityCarver extends WorldCarver<ProbabilityConfig> {
    private final float[] field_202536_i = new float[1024];
    protected IBlockState fillerBlock = Blocks.STONE.getDefaultState();
    protected static final IBlockState STONE = Blocks.STONE.getDefaultState();
    protected static final IBlockState LAVA = Blocks.LAVA.getDefaultState();
    protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
    protected static final IBlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
    protected static final IBlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();
    
    private static final Map<IBlockState, IBlockState> fillerMap = createMap();
	
	private static Map<IBlockState, IBlockState> createMap() 
	{
        Map<IBlockState, IBlockState> result = new HashMap<IBlockState, IBlockState>();
        
        result.put(Blocks.NETHERRACK.getDefaultState(), Blocks.NETHERRACK.getDefaultState()); 
        result.put(Blocks.ICE.getDefaultState(), Blocks.ICE.getDefaultState()); 
        result.put(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.ICE.getDefaultState()); 
        result.put(Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState()); 
        result.put(Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState()); 
        
        return Collections.unmodifiableMap(result);
    }
	

   protected long field_205552_a;
   protected NoiseGeneratorOctaves field_205553_b;

   public void setSeed(long seed) {
      if (this.field_205553_b == null) {
         this.field_205553_b = new NoiseGeneratorOctaves(new SharedSeedRandom(seed), 4);
      }

      this.field_205552_a = seed;
   }

    public boolean func_212246_a(IBlockReader p_212246_1_, Random p_212246_2_, int p_212246_3_, int p_212246_4_, ProbabilityConfig p_212246_5_) {
        return p_212246_2_.nextFloat() <= p_212246_5_.probability;
     }

     public boolean carve(IWorld region, Random random, int chunkX, int chunkZ, int originalX, int originalZ, BitSet mask, ProbabilityConfig config) {
       	 
    	 
    	int i = (this.func_202520_b() * 2 - 1) * 16;
        double xpos = (double)(chunkX * 16 + random.nextInt(16));
        double height = (double)(random.nextInt(random.nextInt(2) + 1) + 34);
        double zpos = (double)(chunkZ * 16 + random.nextInt(16));
        float xzNoise2 = random.nextFloat() * ((float)Math.PI * 1F);
        float xzCosNoise = (random.nextFloat() - 0.5F) / 16.0F;
        float widthHeightBase = (random.nextFloat() + random.nextFloat())/16; //width And Height Modifier
        int j = i - random.nextInt(i / 4);
        this.beginCarvingRoom(region, random.nextLong(), originalX, originalZ, xpos, height, zpos, widthHeightBase, xzNoise2, xzCosNoise, 0, j, random.nextDouble()+20D, mask);
        return true;
     }

     private void beginCarvingRoom(IWorld worldIn, long randomSeed, int mainChunkX, int mainChunkZ, double randomBlockX, double randomBlockY, double randomBlockZ, float widthHeightBase, float xzNoise2, float xzCosNoise, int startIteration, int maxIteration, double heightMultiplier, BitSet mask) {
    	 setSeed(randomSeed);
    	 Random random = new Random(randomSeed);
        float f = 1.0F;

        for(int i = 0; i < 256; ++i) {
           if (i == 0 || random.nextInt(3) == 0) {
              f = 1.0F+random.nextFloat()*0.5F; //CONTROLS THE LEDGES' WIDTH! FINALLY FOUND WHAT THIS JUNK DOES
           }

           this.field_202536_i[i] = f;
        }

        float f4 = 0.0F;
        float f1 = 0.0F;

        for(int currentRoom = startIteration; currentRoom < maxIteration; ++currentRoom) {
           double placementXZBound = 2D + (double)(MathHelper.sin((float)currentRoom * (float)Math.PI / (float)maxIteration) * widthHeightBase);
           double placementYBound = placementXZBound * heightMultiplier;
           placementXZBound = placementXZBound * ((double)random.nextFloat() + 25D); //thickness
           placementYBound = placementYBound * 2.2D;
           float f2 = MathHelper.cos(xzCosNoise);
           randomBlockX += (double)(MathHelper.cos(xzNoise2) * f2);
           randomBlockZ += (double)(MathHelper.sin(xzNoise2) * f2);
           xzCosNoise = xzCosNoise * 0.7F;
           xzCosNoise = xzCosNoise + f1 * 0.05F;
           xzNoise2 += f4 * 0.05F;
           f1 = f1 * 0.8F;
           f4 = f4 * 0.5F;
           f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 1.5F;
           f4 = f4 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 3.0F;
           if (random.nextInt(4) != 0) {
              if (!this.isWithinGenerationDepth(mainChunkX, mainChunkZ, randomBlockX, randomBlockZ, currentRoom, maxIteration, widthHeightBase)) {
                 return;
              }

              this.carveAtTarget(worldIn, random, randomSeed, mainChunkX, mainChunkZ, randomBlockX, randomBlockY, randomBlockZ, placementXZBound, placementYBound, mask);
           }
        }

     }

     protected boolean carveAtTarget(IWorld worldIn, Random random, long seed, int mainChunkX, int mainChunkZ, double xRange, double yRange, double zRange, double placementXZBound, double placementYBound, BitSet mask) {
        double xPos = (double)(mainChunkX * 16 + 8);
        double zPos = (double)(mainChunkZ * 16 + 8);
        IBlockState iblockstate;
        IBlockState iblockstate1;
        double targetedHeight = 14;
        
        if (!(xRange < xPos - 16.0D - placementXZBound * 2.0D) && !(zRange < zPos - 16.0D - placementXZBound * 2.0D) && !(xRange > xPos + 16.0D + placementXZBound * 2.0D) && !(zRange > zPos + 16.0D + placementXZBound * 2.0D)) {
           int xMin = Math.max(MathHelper.floor(xRange - placementXZBound) - mainChunkX * 16 - 1, 0);
           int xMax = Math.min(MathHelper.floor(xRange + placementXZBound) - mainChunkX * 16 + 1, 16);
           int yMin = Math.max(MathHelper.floor(yRange - placementYBound) - 1, 5);
           int yMax = Math.min(MathHelper.floor(yRange + placementYBound) + 1, 70);
           int zMin = Math.max(MathHelper.floor(zRange - placementXZBound) - mainChunkZ * 16 - 1, 0);
           int zMax = Math.min(MathHelper.floor(zRange + placementXZBound) - mainChunkZ * 16 + 1, 16);
           if (xMin <= xMax && yMin <= yMax && zMin <= zMax) {
              boolean flag = false;
              BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
              BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
              BlockPos.MutableBlockPos blockpos$mutableblockpos2 = new BlockPos.MutableBlockPos();
              

             for(int smallX = xMin; smallX < xMax; ++smallX) {
                int x = smallX + mainChunkX * 16;
                double d2 = ((double)x + 0.5D - xRange) / placementXZBound;

                for(int smallZ = zMin; smallZ < zMax; ++smallZ) {
                   int z = smallZ + mainChunkZ * 16;
                   double d3 = ((double)z + 0.5D - zRange) / placementXZBound;
                   if (d2 * d2 + d3 * d3 < 1.0D) {
                      boolean flag1 = false;
                      int yMaxSum = (int) (yMax - ((1+random.nextFloat())*random.nextFloat()*20));

                      if(yMaxSum<yMin) {
                    	  continue;
                      }

                     blockpos$mutableblockpos.setPos(x, 60, z);
                     fillerBlock = fillerMap.get(worldIn.getBiome(blockpos$mutableblockpos).getSurfaceBuilderConfig().getTop());
                   	 if (fillerBlock == null){
                   		fillerBlock = STONE; 
                   	 }
                      
                   	 for(int y = yMaxSum; y > yMin; --y) {
                         double d4 = ((double)(y - 1) + 0.5D - yRange) / placementYBound;
                         
                         double yModified = y-6;
                         if(yModified <= 0) {
                        	 yModified = 0.000001D;
                         }
                         boolean flagPillars = this.field_205553_b.func_205563_a((double)x * 0.2D, (double)z * 0.2D, y*0.035D) - (targetedHeight/yModified) + random.nextDouble() * 0.1D > -3.5D;
                         boolean flagStalagmites = this.field_205553_b.func_205563_a((double)x * 0.63125D, (double)z * 0.63125D, y*0.04D) +(400/(y)) + random.nextDouble() * 0.1D > 2.3D;
                         
                         
                         if ((flagPillars && flagStalagmites) && (d2 * d2 + d3 * d3) * (double)this.field_202536_i[y - 1] + d4 * d4 / 6.0D < 1.0D) {
                            int l2 = smallX | smallZ << 4 | y << 8;
                            if (!mask.get(l2)) {
                               mask.set(l2);
                               blockpos$mutableblockpos.setPos(x, y, z);

                               
                               iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);
                               blockpos$mutableblockpos1.setPos(blockpos$mutableblockpos).move(EnumFacing.UP);
                               blockpos$mutableblockpos2.setPos(blockpos$mutableblockpos).move(EnumFacing.DOWN);
                               iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);
                               if (iblockstate.getBlock() == Blocks.GRASS_BLOCK || iblockstate.getBlock() == Blocks.MYCELIUM) {
                                  flag1 = true;
                               }
                               
                               if(iblockstate1.getBlock() == Blocks.WATER) {
                        		   worldIn.setBlockState(blockpos$mutableblockpos, fillerBlock, 2);
                            	   worldIn.setBlockState(blockpos$mutableblockpos1, fillerBlock, 2);
                            	   worldIn.setBlockState(blockpos$mutableblockpos2, fillerBlock, 2);
                                   flag = true;
                               }
                               else if (this.isTargetSafeFromFalling(iblockstate, iblockstate1) || fillerMap.containsKey(iblockstate)) {
                                  if (y < 11) {
                                     worldIn.setBlockState(blockpos$mutableblockpos, LAVA, 2);
                                  } else {
                                     worldIn.setBlockState(blockpos$mutableblockpos, DEFAULT_CAVE_AIR, 2);
                                     if (flag1 && worldIn.getBlockState(blockpos$mutableblockpos2).getBlock() == Blocks.DIRT) {
                                        worldIn.setBlockState(blockpos$mutableblockpos2, worldIn.getBiome(blockpos$mutableblockpos).getSurfaceBuilderConfig().getTop(), 2);
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

	@Override
	protected boolean carveAtTarget(IWorld worldIn, long seed, int mainChunkX, int mainChunkZ, double xRange,
			double yRange, double zRange, double placementXZBound, double placementYBound, BitSet mask) {
		// TODO Auto-generated method stub
		return false;
	}
}
