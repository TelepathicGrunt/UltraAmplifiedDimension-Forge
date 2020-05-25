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
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class HeightBiasedEndIslandRange extends Placement<CountRangeConfig>
{
    public HeightBiasedEndIslandRange(Function<Dynamic<?>, ? extends CountRangeConfig> configFactory) {
	super(configFactory);
    }


    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, CountRangeConfig placementConfig, BlockPos pos) 
    {
	if(UltraAmplified.UAConfig.endIslandMaxHeight.get() <= placementConfig.bottomOffset)
	    return Stream.empty();

	return IntStream.range(0, UltraAmplified.UAConfig.endIslandSpawnrate.get() / placementConfig.count).mapToObj((p_215051_3_) -> {
	    int x = random.nextInt(16);
	    int range = Math.min(placementConfig.maximum, UltraAmplified.UAConfig.endIslandMaxHeight.get()-placementConfig.bottomOffset);
	    int y = random.nextInt(range) + placementConfig.bottomOffset;
	    int z = random.nextInt(16);
	    return pos.add(x, y, z);
	});
    }
}