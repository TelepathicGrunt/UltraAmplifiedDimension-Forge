package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.SimplePlacement;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.LapisCountRangeConfig;


public class LapisPlacement extends SimplePlacement<LapisCountRangeConfig>
{
	public LapisPlacement(Function<Dynamic<?>, ? extends LapisCountRangeConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public Stream<BlockPos> getPositions(Random random, LapisCountRangeConfig placementConfig, BlockPos pos)
	{
		int count = (int) (ConfigUA.lapisOreSpawnrate * placementConfig.countModifier);
		int baseline = placementConfig.sealevelBased ? ConfigUA.seaLevel - placementConfig.baseline : placementConfig.baseline;
		int spread = placementConfig.spread;
		return IntStream.range(0, count).mapToObj((p_215058_4_) ->
		{
			int x = random.nextInt(16);
			int y = random.nextInt(spread) + random.nextInt(spread) - spread + baseline;
			int z = random.nextInt(16);
			return pos.add(x, y, z);
		});
	}
}