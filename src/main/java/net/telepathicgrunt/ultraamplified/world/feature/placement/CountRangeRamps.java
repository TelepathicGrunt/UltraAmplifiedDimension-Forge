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


public class CountRangeRamps extends SimplePlacement<CountRangeConfig>
{
    public CountRangeRamps(Function<Dynamic<?>, ? extends CountRangeConfig> config) {
	super(config);
    }

    public Stream<BlockPos> getPositions(Random random, CountRangeConfig config, BlockPos pos) {
	return IntStream.range(0, ((int)((UltraAmplified.UAFeaturesConfig.rampSpawnrate.get()/2f)*config.count))).mapToObj((obj) -> {
	    int x = random.nextInt(16) + pos.getX();
	    int z = random.nextInt(16) + pos.getZ();
	    int y = random.nextInt(config.maximum) + config.bottomOffset;
	    return new BlockPos(x, y, z);
	});
    }
}