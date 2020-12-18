package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.stream.Stream;


public class OffsetPlacer extends Placement<YOffsetPlacerConfig> {
    public OffsetPlacer(Codec<YOffsetPlacerConfig> codec) {
        super(codec);
    }

    @Nonnull
    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper context, Random rand, YOffsetPlacerConfig config, BlockPos pos) {
        return Stream.of(pos.up(config.yoffset));
    }
}
