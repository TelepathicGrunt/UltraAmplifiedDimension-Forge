package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SphereReplaceConfig;


public class SphereReplaceDry extends Feature<SphereReplaceConfig>
{
	public SphereReplaceDry(Function<Dynamic<?>, ? extends SphereReplaceConfig> p_i49885_1_)
	{
		super(p_i49885_1_);
	}


	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos position, SphereReplaceConfig config)
	{
		if (!world.getFluidState(position).isEmpty())
		{
			return false;
		}
		else
		{
			int placedBlocks = 0;
			int radius = random.nextInt(config.radius - 2) + 2;
			BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

			for (int x = position.getX() - radius; x <= position.getX() + radius; ++x)
			{
				for (int z = position.getZ() - radius; z <= position.getZ() + radius; ++z)
				{
					int i1 = x - position.getX();
					int j1 = z - position.getZ();
					if (i1 * i1 + j1 * j1 <= radius * radius)
					{
						for (int y = position.getY() - config.ySize; y <= position.getY() + config.ySize; ++y)
						{
		            		blockpos$Mutable.setPos(x, y, z);
							BlockState blockstate = world.getBlockState(blockpos$Mutable);

							for (BlockState blockstate1 : config.targets)
							{
								if (blockstate1.getBlock() == blockstate.getBlock())
								{
									world.setBlockState(blockpos$Mutable, config.state, 2);
									++placedBlocks;
									break;
								}
							}
						}
					}
				}
			}

			return placedBlocks > 0;
		}
	}
}