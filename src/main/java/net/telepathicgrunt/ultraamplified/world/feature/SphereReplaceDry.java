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
		int placedBlocks = 0;
		int radius;
		if(config.radius > 2) 
		{
			radius = random.nextInt(config.radius - 2) + 2;
		}
		else 
		{
			radius = config.radius;
		}
		
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

		for (int x = position.getX() - radius; x <= position.getX() + radius; ++x)
		{
			for (int z = position.getZ() - radius; z <= position.getZ() + radius; ++z)
			{
				int trueX = x - position.getX();
				int trueZ = z - position.getZ();
				if (trueX * trueX + trueZ * trueZ <= radius * radius)
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