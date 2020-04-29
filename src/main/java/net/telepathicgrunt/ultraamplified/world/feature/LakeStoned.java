package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;


public class LakeStoned extends Feature<BlockStateFeatureConfig>
{
	public LakeStoned(Function<Dynamic<?>, ? extends BlockStateFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private static final BlockState AIR = Blocks.AIR.getDefaultState();


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, BlockStateFeatureConfig configBlock)
	{
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);
		while (blockpos$Mutable.getY() > 5 && world.isAirBlock(blockpos$Mutable))
		{
			blockpos$Mutable.move(Direction.DOWN);
		}

		if (blockpos$Mutable.getY() <= 4)
		{
			return false;
		}

		
		boolean[] aboolean = new boolean[2048];
		int iteration = random.nextInt(4) + 4;

		for (int currentBlob = 0; currentBlob < iteration; ++currentBlob)
		{
			double d0 = random.nextDouble() * 6.0D + 3.0D;
			double d1 = random.nextDouble() * 4.0D + 2.0D;
			double d2 = random.nextDouble() * 6.0D + 3.0D;
			double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
			double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
			double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

			for (int x = 1; x < 15; ++x)
			{
				for (int z = 1; z < 15; ++z)
				{
					for (int y = 1; y < 7; ++y)
					{
						double d6 = (x - d3) / (d0 / 2.0D);
						double d7 = (y - d4) / (d1 / 2.0D);
						double d8 = (z - d5) / (d2 / 2.0D);
						double d9 = d6 * d6 + d7 * d7 + d8 * d8;
						if (d9 < 1.0D)
						{
							aboolean[(x * 16 + z) * 8 + y] = true;
						}
					}
				}
			}
		}

		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				for (int y = 0; y < 8; ++y)
				{
					boolean flag = !aboolean[(x * 16 + z) * 8 + y] && (x < 15 && aboolean[((x + 1) * 16 + z) * 8 + y] || x > 0 && aboolean[((x - 1) * 16 + z) * 8 + y] || z < 15 && aboolean[(x * 16 + z + 1) * 8 + y] || z > 0 && aboolean[(x * 16 + (z - 1)) * 8 + y] || y < 7 && aboolean[(x * 16 + z) * 8 + y + 1] || y > 0 && aboolean[(x * 16 + z) * 8 + (y - 1)]);
					if (flag)
					{
						blockpos$Mutable.setPos(position).move(x - 8, y - 4, z - 8);
						Material material = world.getBlockState(blockpos$Mutable).getMaterial();
						if (y >= 4 && material.isLiquid())
						{
							return false;
						}

						if (y < 4 && !material.isSolid() && world.getBlockState(blockpos$Mutable) != configBlock.state)
						{
							return false;
						}
					}
				}
			}
		}

		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				for (int y = 0; y < 8; ++y)
				{
					if (aboolean[(x * 16 + z) * 8 + y])
					{
						blockpos$Mutable.setPos(position).move(x - 8, y - 4, z - 8);
						world.setBlockState(blockpos$Mutable, y >= 4 ? AIR : configBlock.state, 2);
					}
				}
			}
		}

		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				for (int y = 4; y < 8; ++y)
				{
					if (aboolean[(x * 16 + z) * 8 + y])
					{
						blockpos$Mutable.setPos(position).move(x - 8, y - 4, z - 8);
						if (isDirt(world.getBlockState(blockpos$Mutable).getBlock()) && world.getLightFor(LightType.SKY, blockpos$Mutable.up()) > 0)
						{
							Biome biome = world.getBiome(blockpos$Mutable);
							if (biome.getSurfaceBuilderConfig().getTop().getBlock() == Blocks.MYCELIUM)
							{
								world.setBlockState(blockpos$Mutable, Blocks.MYCELIUM.getDefaultState(), 2);
							}
							else
							{
								world.setBlockState(blockpos$Mutable, Blocks.GRASS_BLOCK.getDefaultState(), 2);
							}
						}
					}
				}
			}
		}

		if (configBlock.state.getMaterial() == Material.WATER)
		{
			for (int x = 0; x < 16; ++x)
			{
				for (int z = 0; z < 16; ++z)
				{
					blockpos$Mutable.setPos(position).move(x - 8, 4, z - 8);
					if (world.getBiome(blockpos$Mutable).doesWaterFreeze(world, blockpos$Mutable, false))
					{
						world.setBlockState(blockpos$Mutable, Blocks.ICE.getDefaultState(), 2);
					}
				}
			}
		}
		else if (configBlock.state.getMaterial() == Material.LAVA)
		{
			for (int x = 0; x < 16; ++x)
			{
				for (int z = 0; z < 16; ++z)
				{
					for (int y = 0; y < 8; ++y)
					{
						boolean flag1 = !aboolean[(x * 16 + z) * 8 + y] && (x < 15 && aboolean[((x + 1) * 16 + z) * 8 + y] || x > 0 && aboolean[((x - 1) * 16 + z) * 8 + y] || z < 15 && aboolean[(x * 16 + z + 1) * 8 + y] || z > 0 && aboolean[(x * 16 + (z - 1)) * 8 + y] || y < 7 && aboolean[(x * 16 + z) * 8 + y + 1] || y > 0 && aboolean[(x * 16 + z) * 8 + (y - 1)]);

						blockpos$Mutable.setPos(position).move(x - 8, y - 4, z - 8);
						Material blockMaterial = world.getBlockState(blockpos$Mutable).getMaterial();
						if (flag1 && (y < 4 || random.nextInt(2) != 0) && blockMaterial.isSolid() && blockMaterial != Material.LEAVES)
						{
							world.setBlockState(blockpos$Mutable, Blocks.STONE.getDefaultState(), 2);
						}
					}
				}
			}
		}

		return true;
	}
}