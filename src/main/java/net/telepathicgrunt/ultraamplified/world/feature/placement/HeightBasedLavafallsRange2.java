package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.SimplePlacement;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class HeightBasedLavafallsRange2 extends SimplePlacement<CountRangeConfig>
{
	public HeightBasedLavafallsRange2(Function<Dynamic<?>, ? extends CountRangeConfig> config)
	{
		super(config);
	}


	@Override
	public Stream<BlockPos> getPositions(Random rand, CountRangeConfig config, BlockPos pos)
	{
		//we do * instead of / as no biome reduces number of lavafalls. only 2 increases the number of lavafalls
		return IntStream.range(0, UltraAmplified.UAConfig.lavafallSpawnrate.get() * config.count).mapToObj((p_215057_3_) ->
		{
			int i = rand.nextInt(16);
			int j = rand.nextInt(rand.nextInt(rand.nextInt(config.maximum - config.topOffset) + config.bottomOffset) + config.bottomOffset);
			int k = rand.nextInt(16);
			return pos.add(i, j, k);
		});
	}
}