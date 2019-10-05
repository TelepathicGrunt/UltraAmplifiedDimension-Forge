package net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder;

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

public class GravelSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> 
{
	public GravelSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_) {
	      super(p_i51310_1_);
	}
	
   private static final BlockState AIR = Blocks.AIR.getDefaultState();
   private static final BlockState STONE = Blocks.STONE.getDefaultState();
   
   public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
      this.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
   }

   protected void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int xStart, int zStart, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState topBlock, BlockState middleBlock, BlockState bottomBlock, int seaLevel) {

		BlockState iblockstate = topBlock;
		BlockState iblockstate1 = middleBlock;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		int bottomLayerNoise = -1;
		int noiseThing = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int x = xStart & 15;
		int z = zStart & 15;

		for (int y = startHeight; y >= 0; --y) {
			blockpos$mutableblockpos.setPos(x, y, z);
			BlockState iblockstate2 = chunkIn.getBlockState(blockpos$mutableblockpos);
			if (iblockstate2.getMaterial() == Material.AIR) {
				bottomLayerNoise = -1;
			} else if (iblockstate2.getBlock() == defaultBlock.getBlock()) {
				if (bottomLayerNoise == -1) {
					if (noiseThing <= 0) {
						iblockstate = Blocks.AIR.getDefaultState();
						iblockstate1 = defaultBlock;
					} else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
						iblockstate = topBlock;
						iblockstate1 = middleBlock;
					}

					if (y < seaLevel && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
						if (biomeIn.getTemperature(blockpos$mutableblockpos.setPos(xStart, y, zStart)) < 0.15F) {
							iblockstate = Blocks.ICE.getDefaultState();
						} else {
							iblockstate = defaultFluid;
						}

						blockpos$mutableblockpos.setPos(x, y, z);
					}

					bottomLayerNoise = noiseThing;
					if (y >= seaLevel - 1) {
						chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate, false);
					} else if (y < seaLevel - 7 - noiseThing) {
						iblockstate = Blocks.AIR.getDefaultState();
						iblockstate1 = defaultBlock;
						chunkIn.setBlockState(blockpos$mutableblockpos, bottomBlock, false);
					} else {
						chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate1, false);
					}
				} else if (bottomLayerNoise > 0) {
					--bottomLayerNoise;
					chunkIn.setBlockState(blockpos$mutableblockpos, iblockstate1, false);
				}
			}
			
			//needed to contain fallable blocks
			if(y < 256 && y > 0) {
				Material materialAbove = chunkIn.getBlockState(blockpos$mutableblockpos.up()).getMaterial();
				Material materialBelow = chunkIn.getBlockState(blockpos$mutableblockpos.down()).getMaterial();
				
				//at bottom of ledge
				if(materialBelow == Material.AIR) {
					if (materialAbove == Material.SAND) {
						// sets bottom block so block above cannot fall
						chunkIn.setBlockState(blockpos$mutableblockpos, STONE, false);
					} else if (materialAbove == Material.AIR) {
						// one block thick ledges gets removed
						chunkIn.setBlockState(blockpos$mutableblockpos.up(), AIR, false);
					}
				}
				
			}
		}
	}
}