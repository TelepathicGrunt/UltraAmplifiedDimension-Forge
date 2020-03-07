package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;


public class LakeStoneless extends Feature<BlockStateFeatureConfig>
{
	public LakeStoneless(Function<Dynamic<?>, ? extends BlockStateFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, BlockStateFeatureConfig configBlock)
	{

		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);
		boolean[] aboolean = new boolean[2048];
		int i = random.nextInt(4) + 4;

		for (int j = 0; j < i; ++j)
		{
			double d0 = random.nextDouble() * 6.0D + 3.0D;
			double d1 = random.nextDouble() * 4.0D + 2.0D;
			double d2 = random.nextDouble() * 6.0D + 3.0D;
			double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
			double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
			double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

			for (int l = 1; l < 15; ++l)
			{
				for (int i1 = 1; i1 < 15; ++i1)
				{
					for (int j1 = 1; j1 < 7; ++j1)
					{
						double d6 = (l - d3) / (d0 / 2.0D);
						double d7 = (j1 - d4) / (d1 / 2.0D);
						double d8 = (i1 - d5) / (d2 / 2.0D);
						double d9 = d6 * d6 + d7 * d7 + d8 * d8;
						if (d9 < 1.0D)
						{
							aboolean[(l * 16 + i1) * 8 + j1] = true;
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
						world.setBlockState(blockpos$Mutable, y >= 4 ? CAVE_AIR : configBlock.state, 2);
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
					boolean flag1 = !aboolean[(x * 16 + z) * 8 + y] && (x < 15 && aboolean[((x + 1) * 16 + z) * 8 + y] || x > 0 && aboolean[((x - 1) * 16 + z) * 8 + y] || z < 15 && aboolean[(x * 16 + z + 1) * 8 + y] || z > 0 && aboolean[(x * 16 + (z - 1)) * 8 + y] || y < 7 && aboolean[(x * 16 + z) * 8 + y + 1] || y > 0 && aboolean[(x * 16 + z) * 8 + (y - 1)]);

					blockpos$Mutable.setPos(position).move(x - 8, y - 4, z - 8);
					Material blockMaterial = world.getBlockState(blockpos$Mutable).getMaterial();
					if (flag1 && (y < 4 || random.nextInt(2) != 0) && blockMaterial.isSolid() && blockMaterial != Material.LEAVES)
					{
						world.setBlockState(blockpos$Mutable, configBlock.state, 2);
					}
				}
			}
		}

		return true;

	}
}