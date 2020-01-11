package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.SimplePlacement;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class HeightBasedLavafallsRange extends SimplePlacement<CountRangeConfig>
{
	public HeightBasedLavafallsRange(Function<Dynamic<?>, ? extends CountRangeConfig> config)
	{
		super(config);
	}


	public Stream<BlockPos> getPositions(Random rand, CountRangeConfig config, BlockPos pos)
	{
		return IntStream.range(0, ConfigUA.lavafallSpawnrate / config.count).mapToObj((p_215057_3_) ->
		{
			int i = rand.nextInt(16);
			int j = rand.nextInt(rand.nextInt(config.maximum - config.topOffset) + config.bottomOffset);
			int k = rand.nextInt(16);
			return pos.add(i, j, k);
		});
	}
}