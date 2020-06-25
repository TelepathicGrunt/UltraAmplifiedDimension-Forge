package net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.BadlandsSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class MesaBryceSurfaceBuilderUA extends BadlandsSurfaceBuilder
{
	public MesaBryceSurfaceBuilderUA(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51317_1_)
	{
		super(p_i51317_1_);
	}

	private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
	private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
	private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();


	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		double spikeHeight = 0.0D;
		double d1 = Math.min(Math.abs(noise), this.field_215435_c.noiseAt(x * 0.25D, z * 0.25D, false) * 15.0D);
		if (d1 > -1.5D)
		{
			double d3 = Math.abs(this.field_215437_d.noiseAt(x * 1500.001953125D, z * 1500.001953125D, false) * 15.0D);
			spikeHeight = d1 * d1 * 8.5D;
			double d4 = Math.ceil(d3 * 1200.0D) + 1000.0D;
			if (spikeHeight > d4)
			{
				spikeHeight = d4;
			}

			spikeHeight = spikeHeight + 95.0D;
		}

		//messy spiky spikes
		if (spikeHeight > 160D)
		{
			spikeHeight *= 1.2D;
		}
		else if (spikeHeight > 135D)
		{
			spikeHeight *= 1.5D;
		}
		else if (spikeHeight > 115D)
		{
			spikeHeight *= 1.9D;
		}

		int l = x & 15;
		int i = z & 15;
		BlockState iblockstate2 = WHITE_TERRACOTTA;
		BlockState iblockstate = biomeIn.getSurfaceBuilderConfig().getUnder();
		int i1 = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		boolean flag = Math.cos(noise / 3.0D * Math.PI) > 0.0D;
		int j = -1;
		boolean flag1 = false;
		boolean hitSolidUnderwaterBlock = false;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

		//might need to make k start at 255
		for (int k = Math.max(startHeight, (int) spikeHeight + 1); k >= 0; --k)
		{
			blockpos$Mutable.setPos(l, k, i);
			Material material = chunkIn.getBlockState(blockpos$Mutable).getMaterial();
			if ((material == Material.AIR || material == Material.WATER || material == Material.LAVA) && k < (int) spikeHeight && !hitSolidUnderwaterBlock)
			{
				chunkIn.setBlockState(blockpos$Mutable, defaultBlock, false);
			}
			else if (k < UltraAmplified.UATerrainConfig.seaLevel.get() + 1)
			{
				hitSolidUnderwaterBlock = true;
			}

			BlockState iblockstate1 = chunkIn.getBlockState(blockpos$Mutable);
			if (iblockstate1.getMaterial() == Material.AIR)
			{
				j = -1;
			}
			else if (iblockstate1.getBlock() == defaultBlock.getBlock())
			{
				if (j == -1)
				{
					flag1 = false;
					if (i1 <= 0)
					{
						iblockstate2 = Blocks.AIR.getDefaultState();
						iblockstate = defaultBlock;
					}
					else if (k >= seaLevel - 4 && k <= seaLevel + 1)
					{
						iblockstate2 = WHITE_TERRACOTTA;
						iblockstate = biomeIn.getSurfaceBuilderConfig().getUnder();
					}

					if (k < seaLevel - 5 && (iblockstate2 == null || iblockstate2.getMaterial() == Material.AIR))
					{
						iblockstate2 = defaultFluid;
					}

					j = i1 + Math.max(0, k - seaLevel);
					if (k >= seaLevel - 1)
					{
						if (k <= seaLevel + 25 + i1)
						{
							chunkIn.setBlockState(blockpos$Mutable, biomeIn.getSurfaceBuilderConfig().getTop(), false);
							flag1 = true;
						}
						else
						{
							BlockState iblockstate3;
							if (k >= 64 && k <= 127)
							{
								if (flag)
								{
									iblockstate3 = TERRACOTTA;
								}
								else
								{
									iblockstate3 = this.func_215431_a(x, k, z);
								}
							}
							else
							{
								iblockstate3 = ORANGE_TERRACOTTA;
							}

							chunkIn.setBlockState(blockpos$Mutable, iblockstate3, false);
						}
					}
					else
					{
						chunkIn.setBlockState(blockpos$Mutable, iblockstate, false);
						Block block = iblockstate.getBlock();
						if (block == Blocks.WHITE_TERRACOTTA || block == Blocks.ORANGE_TERRACOTTA || block == Blocks.MAGENTA_TERRACOTTA || block == Blocks.LIGHT_BLUE_TERRACOTTA || block == Blocks.YELLOW_TERRACOTTA || block == Blocks.LIME_TERRACOTTA || block == Blocks.PINK_TERRACOTTA || block == Blocks.GRAY_TERRACOTTA || block == Blocks.LIGHT_GRAY_TERRACOTTA || block == Blocks.CYAN_TERRACOTTA || block == Blocks.PURPLE_TERRACOTTA || block == Blocks.BLUE_TERRACOTTA
								|| block == Blocks.BROWN_TERRACOTTA || block == Blocks.GREEN_TERRACOTTA || block == Blocks.RED_TERRACOTTA || block == Blocks.BLACK_TERRACOTTA)
						{
							chunkIn.setBlockState(blockpos$Mutable, ORANGE_TERRACOTTA, false);
						}
					}
				}
				else if (j > 0)
				{
					--j;
					if (flag1)
					{
						chunkIn.setBlockState(blockpos$Mutable, ORANGE_TERRACOTTA, false);
					}
					else
					{
						chunkIn.setBlockState(blockpos$Mutable, this.func_215431_a(x, k, z), false);
					}
				}
			}
		}

	}
}