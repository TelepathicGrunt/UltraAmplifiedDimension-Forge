package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;


public class ChanceOnAllSurfaces extends Placement<PercentageAndFrequencyConfig>
{
	public ChanceOnAllSurfaces(Function<Dynamic<?>, ? extends PercentageAndFrequencyConfig> configFactory)
	{
		super(configFactory);
	}


	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, PercentageAndFrequencyConfig pfConfig, BlockPos pos)
	{
		int lowestHeight = 40;
		ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(pos);

		for (int i = 0; i < pfConfig.frequency; i++)
		{
			blockpos$Mutable.setPos(0, 255, 0);

			while (blockpos$Mutable.getY() > lowestHeight)
			{
				int x = random.nextInt(16);
				int z = random.nextInt(16);
				blockpos$Mutable.setPos(pos.getX() + x, blockpos$Mutable.getY(), pos.getZ() + z);

				//will stop at first non-air block (could be water)
				int height = PlacingUtils.topOfSurfaceBelowHeight(world, blockpos$Mutable.getY(), lowestHeight, blockpos$Mutable);

				//gets difference and sets mutable height to yPosOfSurface
				blockpos$Mutable.move(Direction.UP, height - blockpos$Mutable.getY());

				if (blockpos$Mutable.getY() <= lowestHeight)
				{
					break; // Too low to generate. Break and try again with next attempt.
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