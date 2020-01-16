package net.telepathicgrunt.ultraamplified.world.feature.placement;

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
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;


public class AtBottomOfLedge extends Placement<CountRangeConfig>
{
	public AtBottomOfLedge(Function<Dynamic<?>, ? extends CountRangeConfig> configFactory)
	{
		super(configFactory);
	}


	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, CountRangeConfig placementConfig, BlockPos pos)
	{

		return IntStream.range(0, placementConfig.count).mapToObj((p_215051_3_) ->
		{
			int x = random.nextInt(16);
			int z = random.nextInt(16);

			int height = random.nextInt(placementConfig.maximum - placementConfig.bottomOffset) + placementConfig.bottomOffset;

			// gets y value of a layer below top layer
			int bottomYLayer = YPositionOfBottomOfLayer(world, height, random, pos.add(x, 0, z));

			if (bottomYLayer > placementConfig.maximum)
			{
				return null;
			}
			return pos.add(x, bottomYLayer, z);

		}).filter(Objects::nonNull);
	}


	private int YPositionOfBottomOfLayer(IWorld world, int height, Random random, BlockPos position)
	{
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

		// if height is inside a non-air block, move up until we reached an air block
		while (blockpos$Mutable.getY() < 255)
		{
			if (world.isAirBlock(blockpos$Mutable))
			{
				break;
			}

			blockpos$Mutable.move(Direction.UP);
		}

		// if height is an air block, move up until we reached a solid block. We are now
		// on the bottom of a piece of land
		while (blockpos$Mutable.getY() > 255)
		{
			if (!world.isAirBlock(blockpos$Mutable))
			{
				break;
			}

			blockpos$Mutable.move(Direction.UP);
		}

		return blockpos$Mutable.getY() > 255 ? 255 : blockpos$Mutable.getY();
	}
}
