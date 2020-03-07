package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;


public class IcePatchUA extends Feature<NoFeatureConfig>
{
	public IcePatchUA(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private final BlockState packedIce = Blocks.PACKED_ICE.getDefaultState();
	private final int basePathWidth = 3;


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random random, BlockPos position, NoFeatureConfig p_212245_5_)
	{
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);
		BlockPos.Mutable blockpos$Mutable2 = new BlockPos.Mutable(position);
		while (world.isAirBlock(blockpos$Mutable) && blockpos$Mutable.getY() > 2)
		{
			blockpos$Mutable.move(Direction.DOWN);
		}

		if (world.getBlockState(blockpos$Mutable).getBlock() != Blocks.SAND)
		{
			return false;
		}
		else
		{
			int width = random.nextInt(this.basePathWidth - 2) + 2;

			for (int x = blockpos$Mutable.getX() - width; x <= blockpos$Mutable.getX() + width; ++x)
			{
				for (int z = blockpos$Mutable.getZ() - width; z <= blockpos$Mutable.getZ() + width; ++z)
				{
					int xDiff = x - blockpos$Mutable.getX();
					int zDiff = z - blockpos$Mutable.getZ();

					if (xDiff * xDiff + zDiff * zDiff <= width * width)
					{
						for (int y = blockpos$Mutable.getY() - 1; y <= blockpos$Mutable.getY() + 1; ++y)
						{
							blockpos$Mutable2.setPos(x, y, z);
							Block block = world.getBlockState(blockpos$Mutable2).getBlock();

							if (block == Blocks.SAND || block == Blocks.SANDSTONE)
							{
								world.setBlockState(blockpos$Mutable2, this.packedIce, 2);
							}
						}
					}
				}
			}

			return true;
		}
	}
}
