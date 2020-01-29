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


public class SandSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public SandSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_)
	{
		super(p_i51310_1_);
	}

	private static final BlockState AIR = Blocks.AIR.getDefaultState();


	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		this.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
	}


	protected void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int xStart, int zStart, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState topBlock, BlockState middleBlock, BlockState bottomBlock, int seaLevel)
	{

		BlockState iblockstate = topBlock;
		BlockState iblockstate1 = middleBlock;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		int bottomLayerNoise = -1;
		int noiseThing = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int x = xStart & 15;
		int z = zStart & 15;

		for (int y = startHeight; y >= 0; --y)
		{
			blockpos$Mutable.setPos(x, y, z);
			BlockState iblockstate2 = chunkIn.getBlockState(blockpos$Mutable);
			if (iblockstate2.getMaterial() == Material.AIR)
			{
				bottomLayerNoise = -1;
			}
			else if (iblockstate2.getBlock() == defaultBlock.getBlock())
			{
				if (bottomLayerNoise == -1)
				{
					if (noiseThing <= 0)
					{
						iblockstate = Blocks.AIR.getDefaultState();
						iblockstate1 = defaultBlock;
					}
					else if (y >= seaLevel - 4 && y <= seaLevel + 1)
					{
						iblockstate = topBlock;
						iblockstate1 = middleBlock;
					}

					if (y < seaLevel && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
					{
						if (biomeIn.getTemperature(blockpos$Mutable.setPos(xStart, y, zStart)) < 0.15F)
						{
							iblockstate = Blocks.ICE.getDefaultState();
						}
						else
						{
							iblockstate = defaultFluid;
						}

						blockpos$Mutable.setPos(x, y, z);
					}

					bottomLayerNoise = noiseThing;
					if (y >= seaLevel - 1)
					{
						chunkIn.setBlockState(blockpos$Mutable, iblockstate, false);
					}
					else if (y < seaLevel - 7 - noiseThing)
					{
						iblockstate = Blocks.AIR.getDefaultState();
						iblockstate1 = defaultBlock;
						chunkIn.setBlockState(blockpos$Mutable, bottomBlock, false);
					}
					else
					{
						chunkIn.setBlockState(blockpos$Mutable, iblockstate1, false);
					}
				}
				else if (bottomLayerNoise > 0)
				{
					--bottomLayerNoise;
					chunkIn.setBlockState(blockpos$Mutable, iblockstate1, false);
					if (bottomLayerNoise == 0 && iblockstate1.getBlock() == Blocks.SAND && noiseThing > 1)
					{
						bottomLayerNoise = random.nextInt(4) + Math.max(0, y - seaLevel);
						iblockstate1 = iblockstate1.getBlock() == Blocks.RED_SAND ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
					}
				}
			}

			//needed to contain fallable blocks
			if (y < 256 && y > 0)
			{
				Material materialAbove = chunkIn.getBlockState(blockpos$Mutable.up()).getMaterial();
				Material materialBelow = chunkIn.getBlockState(blockpos$Mutable.down()).getMaterial();
				if (materialBelow == Material.AIR)
				{
					if (materialAbove == Material.SAND)
					{
						// sets bottom block so block above cannot fall
						chunkIn.setBlockState(blockpos$Mutable, bottomBlock, false);
					}
					else if (materialAbove == Material.AIR)
					{
						// one block thick ledges gets removed
						chunkIn.setBlockState(blockpos$Mutable.up(), AIR, false);
					}
				}

			}
		}
	}
}