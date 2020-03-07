package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;


public class AllSoulSandSurfaces extends Placement<PercentageAndFrequencyConfig>
{
	public AllSoulSandSurfaces(Function<Dynamic<?>, ? extends PercentageAndFrequencyConfig> configFactory)
	{
		super(configFactory);
	}

	private final BlockState SOULSAND = Blocks.SOUL_SAND.getDefaultState();


	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, PercentageAndFrequencyConfig pfConfig, BlockPos position)
	{

		ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

		for (int i = 0; i < pfConfig.frequency; i++)
		{
			blockpos$Mutable.setPos(0, 255, 0);

			while (blockpos$Mutable.getY() > 74)
			{
				int x = random.nextInt(16);
				int z = random.nextInt(16);
				blockpos$Mutable.setPos(position.getX() + x, blockpos$Mutable.getY(), position.getZ() + z);

				//if height is inside a non-air block, move down until we reached a soulsand block
				while (blockpos$Mutable.getY() > 74)
				{
					if (world.isAirBlock(blockpos$Mutable))
					{
						break;
					}

					blockpos$Mutable.move(Direction.DOWN);
				}

				//if height is an air block, move down until we reached a soulsand block. We are now on the surface of a piece of valid land
				while (blockpos$Mutable.getY() > 74)
				{
					if (world.getBlockState(blockpos$Mutable) == SOULSAND)
					{
						break;
					}

					blockpos$Mutable.move(Direction.DOWN);
				}

				if (blockpos$Mutable.getY() <= 74)
				{
					break;
				}
				else if (random.nextFloat() < pfConfig.percentage)
				{
					blockPosList.add(blockpos$Mutable.up());
				}
			}
		}

		return IntStream.range(0, blockPosList.size()).mapToObj((p_215051_3_) ->
		{
			return blockPosList.remove(0);
		}).filter(Objects::nonNull);
	}
}