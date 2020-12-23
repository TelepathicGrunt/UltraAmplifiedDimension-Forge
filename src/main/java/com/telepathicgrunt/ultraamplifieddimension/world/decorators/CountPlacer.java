package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class CountPlacer extends SimplePlacement<CountPlacerConfig> {
    public CountPlacer(Codec<CountPlacerConfig> codec) {
        super(codec);
    }

    public Stream<BlockPos> getPositions(Random random, CountPlacerConfig config, BlockPos pos) {
        return IntStream.range(0, config.func_242799_a().func_242259_a(random)).mapToObj((count) -> pos);
    }
}
