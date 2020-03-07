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


public class IceMountainSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public IceMountainSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_)
	{
		super(p_i51310_1_);
	}

	private static final BlockState STONE = Blocks.STONE.getDefaultState();
	private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	private static final BlockState ICE = Blocks.ICE.getDefaultState();
	private static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();


	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		BlockState iblockstate = SNOW_BLOCK;
		BlockState iblockstate1 = ICE;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		int i = -1;
		int j = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int xpos = x & 15;
		int zpos = z & 15;

		for (int ypos = 255; ypos >= 0; --ypos)
		{
			blockpos$Mutable.setPos(xpos, ypos, zpos);
			BlockState iblockstate2 = chunkIn.getBlockState(blockpos$Mutable);

			if (iblockstate2.getBlock() == null || iblockstate2.getMaterial() == Material.AIR)
			{
				i = -1;
			}
			else if (iblockstate2.getMaterial() == Material.WATER)
			{

				if (ypos < ConfigUA.seaLevel)
				{
					chunkIn.setBlockState(blockpos$Mutable, ConfigUA.lavaOcean ? LAVA : SNOW_BLOCK, false);
				}

				i = -1;
			}
			else
			{
				if (iblockstate2 == STONE)
				{
					if (i == -1)
					{
						if (j <= 0)
						{
							iblockstate = Blocks.AIR.getDefaultState();
							iblockstate1 = ICE;
						}
						else if (ypos >= seaLevel - 4 && ypos <= seaLevel + 1)
						{
							iblockstate = SNOW_BLOCK;
							iblockstate1 = ICE;
						}

						i = j;
						if (ypos >= seaLevel - 1)
						{
							chunkIn.setBlockState(blockpos$Mutable, iblockstate, false);
						}
						else
						{
							chunkIn.setBlockState(blockpos$Mutable, iblockstate1, false);
						}
					}
					else if (i > 0)
					{
						--i;
						chunkIn.setBlockState(blockpos$Mutable, iblockstate1, false);
					}
					else
					{
						chunkIn.setBlockState(blockpos$Mutable, ICE, false);
					}
				}
			}
		}

	}
}