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


public class ChanceOnAllLiquidSurfaces extends Placement<PercentageAndFrequencyConfig>
{

	public ChanceOnAllLiquidSurfaces(Function<Dynamic<?>, ? extends PercentageAndFrequencyConfig> configFactory)
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
				int xOffset = random.nextInt(16);
				int zOffset = random.nextInt(16);
				blockpos$Mutable.setPos(pos.getX() + xOffset, blockpos$Mutable.getY(), pos.getZ() + zOffset);

				//will stop at first non-air block (could be water)
				int height = PlacingUtils.topOfSurfaceBelowHeight(world, blockpos$Mutable.getY(), lowestHeight, blockpos$Mutable);

				//gets difference and sets mutable height to yPosOfSurface
				blockpos$Mutable.move(Direction.UP, height - blockpos$Mutable.getY());

				//if we are in a non-liquid block (not water), don't add this blockpos as it isn't valid. Retry for a new surface.
				if (!world.getBlockState(blockpos$Mutable).getFluidState().isEmpty())
				{
					continue;
				}

				//valid surface. Now to check if height and chance allows for the position. Won't generate at 40 itself.
				if (blockpos$Mutable.getY() <= lowestHeight)
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