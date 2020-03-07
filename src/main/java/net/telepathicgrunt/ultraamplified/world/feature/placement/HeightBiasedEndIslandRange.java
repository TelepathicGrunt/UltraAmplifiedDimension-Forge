package net.telepathicgrunt.ultraamplified.world.feature.placement;

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
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class HeightBiasedEndIslandRange extends Placement<CountRangeConfig>
{
	public HeightBiasedEndIslandRange(Function<Dynamic<?>, ? extends CountRangeConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, CountRangeConfig placementConfig, BlockPos pos)
	{
		return IntStream.range(0, ConfigUA.endIslandSpawnrate / placementConfig.count).mapToObj((p_215051_3_) ->
		{
			int j = random.nextInt(16);
			int k = random.nextInt(placementConfig.maximum - placementConfig.bottomOffset - placementConfig.topOffset) + placementConfig.bottomOffset;
			int l = random.nextInt(16);
			return pos.add(j, k, l);
		});
	}
}