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
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.utils.OpenSimplexNoise;


public class NetherSurfaceBuilderUA extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public NetherSurfaceBuilderUA(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_)
	{
		super(p_i51310_1_);
	}

	private static final BlockState STONE = Blocks.STONE.getDefaultState();
	private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
	private static final BlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
	private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
	private static final BlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
	private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	private static final BlockState WATER = Blocks.WATER.getDefaultState();
	private static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
	protected long noiseSeed;
	protected OpenSimplexNoise noiseGen;


	@Override
	public void setSeed(long seed)
	{
		if (this.noiseSeed != seed || this.noiseGen == null)
		{
			this.noiseGen = new OpenSimplexNoise(seed);
		}

		this.noiseSeed = seed;
	}


	@SuppressWarnings("deprecation")
	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		int sealevel = seaLevel + 1;
		int xpos = x & 15;
		int zpos = z & 15;
		int noiseDepth = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		int depth = -1;
		BlockState topBlockstate = NETHERRACK;
		BlockState bottomBlockstates = NETHERRACK;

		for (int ypos = startHeight; ypos >= 0; --ypos)
		{
			blockpos$Mutable.setPos(xpos, ypos, zpos);
			BlockState currentBlockToReplace = chunkIn.getBlockState(blockpos$Mutable);

			if (currentBlockToReplace.getBlock() == null || currentBlockToReplace.getMaterial() == Material.AIR)
			{
				depth = -1;
			}
			else if (currentBlockToReplace.getMaterial() == Material.WATER)
			{

				if (ypos < ConfigUA.seaLevel - 7)
				{
					chunkIn.setBlockState(blockpos$Mutable, LAVA, false);
				}
				else
				{
					chunkIn.setBlockState(blockpos$Mutable, ConfigUA.lavaOcean ? LAVA : WATER, false);
				}

				depth = -1;
			}
			else
			{
				if (currentBlockToReplace == STONE)
				{
					boolean soulSandFlag = this.noiseGen.eval(x * 0.015D, ypos / 7, z * 0.015D) > 0.45D;
					boolean gravelFlag = this.noiseGen.eval(x * 0.02D + 100.0D, (ypos / 8), z * 0.02D - 100.0D) > 0.48D;

					if (depth == -1)
					{
						if (noiseDepth <= 0)
						{
							topBlockstate = CAVE_AIR;
							bottomBlockstates = NETHERRACK;
						}
						else if (ypos >= sealevel - 4)
						{
							topBlockstate = NETHERRACK;
							bottomBlockstates = NETHERRACK;

							if (soulSandFlag)
							{
								topBlockstate = SOUL_SAND;
								bottomBlockstates = SOUL_SAND;
							}
							else if (gravelFlag)
							{
								topBlockstate = GRAVEL;
							}
							else if ((noise > -3.85 && noise < -3.7) || (noise > -0.1 && noise < 0.05) || (noise > 3.7 && noise < 3.85))
							{
								topBlockstate = MAGMA;
							}
						}

						depth = noiseDepth;
						if (ypos >= sealevel - 1)
						{
							chunkIn.setBlockState(blockpos$Mutable, topBlockstate, false);
							
							if(gravelFlag && chunkIn.getBlockState(blockpos$Mutable.down()).isAir())
							{
								chunkIn.setBlockState(blockpos$Mutable.down(), NETHERRACK, false);
							}
						}
						else
						{
							chunkIn.setBlockState(blockpos$Mutable, bottomBlockstates, false);
						}
					}
					else if (depth > 0)
					{
						--depth;
						chunkIn.setBlockState(blockpos$Mutable, bottomBlockstates, false);
					}
					else
					{
						chunkIn.setBlockState(blockpos$Mutable, NETHERRACK, false);
					}
				}
			}
		}

	}
}