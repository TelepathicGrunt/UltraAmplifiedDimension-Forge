package net.telepathicgrunt.ultraamplified.world.feature.carver;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;

public class UnderwaterCaveCarver extends CaveWorldCarver {
	  protected static final Set<BlockState> CARVABLE_BLOCKS = ImmutableSet.of(Blocks.STONE.getDefaultState(), Blocks.GRANITE.getDefaultState(), Blocks.DIORITE.getDefaultState(), Blocks.ANDESITE.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), Blocks.PODZOL.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), Blocks.TERRACOTTA.getDefaultState(), Blocks.WHITE_TERRACOTTA.getDefaultState(), Blocks.ORANGE_TERRACOTTA.getDefaultState(), Blocks.MAGENTA_TERRACOTTA.getDefaultState(), Blocks.LIGHT_BLUE_TERRACOTTA.getDefaultState(), Blocks.YELLOW_TERRACOTTA.getDefaultState(), Blocks.LIME_TERRACOTTA.getDefaultState(), Blocks.PINK_TERRACOTTA.getDefaultState(), Blocks.GRAY_TERRACOTTA.getDefaultState(), Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState(), Blocks.CYAN_TERRACOTTA.getDefaultState(), Blocks.PURPLE_TERRACOTTA.getDefaultState(), Blocks.BLUE_TERRACOTTA.getDefaultState(), Blocks.BROWN_TERRACOTTA.getDefaultState(), Blocks.GREEN_TERRACOTTA.getDefaultState(), Blocks.RED_TERRACOTTA.getDefaultState(), Blocks.BLACK_TERRACOTTA.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.RED_SANDSTONE.getDefaultState(), Blocks.MYCELIUM.getDefaultState(), Blocks.SNOW.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.WATER.getDefaultState(), Blocks.LAVA.getDefaultState(), Blocks.OBSIDIAN.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.CAVE_AIR.getDefaultState(), Blocks.PACKED_ICE.getDefaultState());
	  private static final BlockState STONE = Blocks.STONE.getDefaultState(); 

	    private static Map<Biome, BlockState> fillerBiomeMap;

		/**
		 * Have to make this map much later since the biomes needs to be initialized first and that's delayed a bit
		 */
		public void setFillerMap() {
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
	  
	  public UnderwaterCaveCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> probabilityConfig) {
	      super(probabilityConfig, 73);
	   }


       public boolean shouldCarve(Random randomIn, int chunkX, int chunkZ, ProbabilityConfig config) {
          return randomIn.nextFloat() <= (float) (ConfigUA.oceanCaveSpawnrate) / 100f;
       }

	  
	   protected boolean func_222700_a(IChunk chunkIn, int chunkX, int chunkZ, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
	      return false;
	   }

	   protected boolean carveBlock(IChunk chunkIn, BitSet carvingMask, Random randomIn, BlockPos.MutableBlockPos mutableBlockPosIn, BlockPos.MutableBlockPos p_222703_5_, BlockPos.MutableBlockPos p_222703_6_, int minHeight, int chunkX, int chunkZ, int x, int z, int maskY, int y, int atomicBoolean, AtomicBoolean p_222703_15_) {
		   setFillerMap();
		   return carvingBlock(this, chunkIn, carvingMask, randomIn, mutableBlockPosIn, minHeight, chunkX, chunkZ, x, z, maskY, y, atomicBoolean);
	   }

	   protected static boolean carvingBlock(WorldCarver<?> worldCarver, IChunk chunkIn, BitSet carvingMask, Random randomIn, BlockPos.MutableBlockPos mutableBlockPosIn, int minHeight, int chunkX, int chunkZ, int xStart, int zStart, int maskY, int y, int atomicBoolean) {
	      if (y >= minHeight) {
	         return false;
	      } else {
	         int maskFlag = maskY | atomicBoolean << 4 | y << 8;
	         if (carvingMask.get(maskFlag)) {
	            return false;
	         } else {
	            carvingMask.set(maskFlag);
	            mutableBlockPosIn.setPos(xStart, y, zStart);

	            BlockState fillerBlock = fillerBiomeMap.get(chunkIn.getBiome(mutableBlockPosIn));
	         	  if (fillerBlock == null){
	         	 	fillerBlock = STONE; 
	         	  }
                
	            BlockState blockstate = chunkIn.getBlockState(mutableBlockPosIn);
	            if (!CARVABLE_BLOCKS.contains(blockstate)) {
	               return false;
	            } else if (y == 10) {
	               float f = randomIn.nextFloat();
	               if ((double)f < 0.25D) {
	                  chunkIn.setBlockState(mutableBlockPosIn, Blocks.MAGMA_BLOCK.getDefaultState(), false);
	                  chunkIn.getBlocksToBeTicked().scheduleTick(mutableBlockPosIn, Blocks.MAGMA_BLOCK, 0);
	               } else {
	                  chunkIn.setBlockState(mutableBlockPosIn, Blocks.OBSIDIAN.getDefaultState(), false);
	               }

	               return true;
	            } else if (y < 10) {
	               chunkIn.setBlockState(mutableBlockPosIn, Blocks.LAVA.getDefaultState(), false);
	               return false;
	            } else {

	              mutableBlockPosIn.setPos(xStart, y, zStart);
                  chunkIn.setBlockState(mutableBlockPosIn, WATER.getBlockState(), false);
                   
                  return true;
	            }
	         }
	      }
	   }
	}