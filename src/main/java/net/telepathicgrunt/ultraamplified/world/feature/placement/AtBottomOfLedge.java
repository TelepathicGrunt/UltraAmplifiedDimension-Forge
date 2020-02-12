package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

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
			int bottomYLayer = PlacingUtils.topOfCeilingAboveHeight(world, height, 255, pos.add(x, 0, z));

			if (bottomYLayer > placementConfig.maximum)
			{
				return null;
			}
			return pos.add(x, bottomYLayer, z);

		}).filter(Objects::nonNull);
	}
}
