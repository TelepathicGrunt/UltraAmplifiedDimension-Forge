package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.SimplePlacement;


public class GlowstonePlacement2 extends SimplePlacement<CountRangeConfig>
{
	public GlowstonePlacement2(Function<Dynamic<?>, ? extends CountRangeConfig> config)
	{
		super(config);
	}


	public Stream<BlockPos> getPositions(Random rand, CountRangeConfig config, BlockPos pos)
	{
		return IntStream.range(0, config.count).mapToObj((p_215061_3_) ->
		{
			int x = rand.nextInt(16);
			int y = rand.nextInt(config.maximum - config.topOffset) + config.bottomOffset;
			int z = rand.nextInt(16);
			return pos.add(x, y, z);
		});
	}
}