package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;

import java.util.function.Function;


public class RangeValidationPlacerConfig implements IPlacementConfig {
    public static final Codec<RangeValidationPlacerConfig> CODEC = RecordCodecBuilder.<RangeValidationPlacerConfig>create((builder) -> builder.group(
            Codec.INT.fieldOf("max_Y").forGetter((config) -> config.maxY),
            Codec.INT.fieldOf("min_Y").forGetter((config) -> config.minY))
        .apply(builder, RangeValidationPlacerConfig::new))
            .comapFlatMap((rangeValidationPlacerConfig) -> rangeValidationPlacerConfig.maxY <= rangeValidationPlacerConfig.minY ?
                    DataResult.error("min_Y has to be smaller than max_Y") : DataResult.success(rangeValidationPlacerConfig), Function.identity());

    public final int maxY;
    public final int minY;

    public RangeValidationPlacerConfig(int maxY, int minY) {
        this.maxY = maxY;
        this.minY = minY;
    }
}