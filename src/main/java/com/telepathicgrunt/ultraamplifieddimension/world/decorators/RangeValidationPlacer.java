package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.stream.Stream;


public class RangeValidationPlacer extends Placement<RangeValidationPlacerConfig> {
    public RangeValidationPlacer(Codec<RangeValidationPlacerConfig> codec) {
        super(codec);
    }

    @Nonnull
    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper context, Random rand, RangeValidationPlacerConfig config, BlockPos pos) {
        if(pos.getY() <= config.maxY && pos.getY() > config.minY){
            return Stream.of(pos);
        }
        else{
            return Stream.empty();
        }
    }
}
