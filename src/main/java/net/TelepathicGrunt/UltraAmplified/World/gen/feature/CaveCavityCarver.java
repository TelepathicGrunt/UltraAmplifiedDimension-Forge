package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

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
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CaveCavityCarver extends WorldCarver<ProbabilityConfig> {
    public CaveCavityCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> p_i49921_1_, int p_i49921_2_) {
		super(p_i49921_1_, p_i49921_2_);
	}


	private final float[] field_202536_i = new float[1024];
    protected BlockState fillerBlock = Blocks.STONE.getDefaultState();
    protected static final BlockState STONE = Blocks.STONE.getDefaultState();
    protected static final BlockState LAVA = Blocks.LAVA.getDefaultState();
    protected static final BlockState WATER = Blocks.WATER.getDefaultState();
    protected static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
    protected static final BlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();
    
    private static final Map<BlockState, BlockState> fillerMap = createMap();
	
	private static Map<BlockState, BlockState> createMap() 
	{
        Map<BlockState, BlockState> result = new HashMap<BlockState, BlockState>();
        
        result.put(Blocks.NETHERRACK.getDefaultState(), Blocks.NETHERRACK.getDefaultState()); 
        result.put(Blocks.ICE.getDefaultState(), Blocks.ICE.getDefaultState()); 
        result.put(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.ICE.getDefaultState()); 
        result.put(Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState()); 
        result.put(Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState()); 
        
        return Collections.unmodifiableMap(result);
    }
	

   protected long seed;
   protected OctavesNoiseGenerator noiseGen;
   

   public void setSeed(long seed) {
      if (this.noiseGen == null) {
         this.noiseGen = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 4);
      }

      this.seed = seed;
   }

    public boolean shouldCarve(Random p_212246_2_, int chunkX, int chunkZ, ProbabilityConfig config) {
        return p_212246_2_.nextFloat() <= config.probability;
     }

     public boolean carve(IChunk region, Random random, int seaLevel, int chunkX, int chunkZ, int originalX, int originalZ, BitSet mask, ProbabilityConfig config) {
       	 
    	 
    	int i = (this.func_222704_c() * 2 - 1) * 16;
        double xpos = (double)(chunkX * 16 + random.nextInt(16));
        double height = (double)(random.nextInt(random.nextInt(2) + 1) + 34);
        double zpos = (double)(chunkZ * 16 + random.nextInt(16));
        float xzNoise2 = random.nextFloat() * ((float)Math.PI * 1F);
        float xzCosNoise = (random.nextFloat() - 0.5F) / 16.0F;
        float widthHeightBase = (random.nextFloat() + random.nextFloat())/16; //width And Height Modifier
        this.func_222729_a(region, random.nextLong(), seaLevel, originalX, originalZ, xpos, height, zpos, widthHeightBase, xzNoise2, xzCosNoise, 0,i, random.nextDouble()+20D, mask);
        return true;
     }

     private void func_222729_a(IChunk worldIn, long randomSeed, int seaLevel, int mainChunkX, int mainChunkZ, double randomBlockX, double randomBlockY, double randomBlockZ, float widthHeightBase, float xzNoise2, float xzCosNoise, int startIteration, int maxIteration, double heightMultiplier, BitSet mask) {
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
           placementXZBound = placementXZBound * 23D; //thickness
           placementYBound = placementYBound * 2.2D;
           float f2 = MathHelper.cos(xzCosNoise);
           randomBlockX += (double)(MathHelper.cos(xzNoise2) * f2);
           randomBlockZ += (double)(MathHelper.sin(xzNoise2) * f2);
           xzCosNoise = xzCosNoise * 0.5F;
           xzCosNoise = xzCosNoise + f1 * 0.04F;
           xzNoise2 += f4 * 0.05F;
           f1 = f1 * 0.8F;
           f4 = f4 * 0.5F;
           f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 1.5F;
           f4 = f4 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 3.0F;
          if (!this.func_222702_a(mainChunkX, mainChunkZ, randomBlockX, randomBlockZ, currentRoom, maxIteration, widthHeightBase)) {
             return;
          }

          this.carveAtTarget(worldIn, random, randomSeed, mainChunkX, mainChunkZ, randomBlockX, randomBlockY, randomBlockZ, placementXZBound, placementYBound, mask);
       
        }

     }

     protected boolean carveAtTarget(IChunk worldIn, Random random, long seed, int mainChunkX, int mainChunkZ, double xRange, double yRange, double zRange, double placementXZBound, double placementYBound, BitSet mask) {
        double xPos = (double)(mainChunkX * 16 + 8);
        double zPos = (double)(mainChunkZ * 16 + 8);
        BlockState iblockstate;
        BlockState iblockstate1;
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
                         
                         //sets a trial and error value that widens base of pillar and makes paths through lava that look good
                         double yModified = y-6.4;
                         
                         if(y < 10) {
                        	 //creates a deep lava pool that starts 2 blocks deep automatically at edges.
                        	 yModified++;
                         }else if(yModified <= 0) {
                        	 //prevents divide by 0 or by negative numbers (decreasing negative would make more terrain be carved instead of not carve)
                        	 yModified = 0.00001D;
                         }
                         
                         //creates pillars that are widen at bottom. 
                         //Perlin field creates the main body for pillar by stepping slowly through x and z and extremely slowly through y.
                         //then subtracted out target height by yModified to flatten bottom of pillar to make a path through lava.
                         //add a random value to add some noise to the pillar.
                         //and set the greater than value to be very low so most of the cave gets carved out.
                         boolean flagPillars = this.noiseGen.func_205563_a((double)x * 0.2D, (double)z * 0.2D, y*0.035D) - (targetedHeight/yModified) + random.nextDouble() * 0.1D > -3.5D;
                         
                         //creates large stalagmites that cannot reach floor of cavern
                         //perlin field creates the main stalagmite shape and placement by stepping though x and z pretty fast and through y very slowly.
                         //Then adds 400/y so that as the y value gets lower, the more area gets carved which sets the limit on how far down the stalagmites can go.
                         //add a random value to add some noise to the pillar.
                         //and set the greater than value to be high so more stalagmites can be made while the 400/y has already carved out the rest of the cave.
                         boolean flagStalagmites = this.noiseGen.func_205563_a((double)x * 0.63125D, (double)z * 0.63125D, y*0.04D) +(360/(y)) + random.nextDouble() * 0.1D > 2.8D;
                         
                         //where the pillar flag and stalagmite flag both flagged this block to be carved, begin carving. 
                         //Thus the pillar and stalagmite is what is left after carving.
                         if ((flagPillars && flagStalagmites) && (d2 * d2 + d3 * d3) * (double)this.field_202536_i[y - 1] + d4 * d4 / 6.0D < 1.0D) {
                            int l2 = smallX | smallZ << 4 | y << 8;
                            if (!mask.get(l2)) {
                               mask.set(l2);
                               blockpos$mutableblockpos.setPos(x, y, z);

                               
                               iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);
                               blockpos$mutableblockpos1.setPos(blockpos$mutableblockpos).move(Direction.UP);
                               blockpos$mutableblockpos2.setPos(blockpos$mutableblockpos).move(Direction.DOWN);
                               iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);
                               if (iblockstate.getBlock() == Blocks.GRASS_BLOCK || iblockstate.getBlock() == Blocks.MYCELIUM) {
                                  flag1 = true;
                               }
                               
                               if(iblockstate1.getBlock() == Blocks.WATER) {
                        		   worldIn.setBlockState(blockpos$mutableblockpos, fillerBlock, false);
                            	   worldIn.setBlockState(blockpos$mutableblockpos1, fillerBlock, false);
                            	   worldIn.setBlockState(blockpos$mutableblockpos2, fillerBlock, false);
                                   flag = true;
                               }
                               else if (this.canCarveBlock(iblockstate, iblockstate1) || fillerMap.containsKey(iblockstate)) {
                                  if (y < 11) {
                                     worldIn.setBlockState(blockpos$mutableblockpos, LAVA, false);
                                  } else {
                                     worldIn.setBlockState(blockpos$mutableblockpos, CAVE_AIR, false);
                                     if (flag1 && worldIn.getBlockState(blockpos$mutableblockpos2).getBlock() == Blocks.DIRT) {
                                        worldIn.setBlockState(blockpos$mutableblockpos2, worldIn.getBiome(blockpos$mutableblockpos).getSurfaceBuilderConfig().getTop(), false);
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
