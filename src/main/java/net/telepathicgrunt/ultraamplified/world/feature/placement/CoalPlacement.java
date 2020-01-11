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


public class CoalPlacement extends SimplePlacement<CountRangeConfig>
{
	public CoalPlacement(Function<Dynamic<?>, ? extends CountRangeConfig> config)
	{
		super(config);
	}


	public Stream<BlockPos> getPositions(Random rand, CountRangeConfig config, BlockPos pos)
	{

		//We do ConfigUA. ...Spawnrate * (config.count/100f) as the config can only be an int but 
		//number of ore can be multipled or divided. So 100 count is default and lower/higher numbers act
		//as if multiplying percentages. I know. It's a really hacky and probably dumb solution around
		//biomes registering at MC startup before world's config files load.
		return IntStream.range(0, (int) (ConfigUA.coalOreSpawnrate * (config.count / 100f))).mapToObj((p_215061_3_) ->
		{
			int i = rand.nextInt(16);
			int j = rand.nextInt(config.maximum - config.topOffset) + config.bottomOffset;
			int k = rand.nextInt(16);
			return pos.add(i, j, k);
		});
	}
}