package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;


public class YOffsetPlacerConfig implements IPlacementConfig {
    public static final Codec<YOffsetPlacerConfig> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Codec.INT.fieldOf("yoffset").orElse(0).forGetter((config) -> config.yOffset),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("yspread").orElse(0).forGetter((config) -> config.ySpread))
                .apply(builder, YOffsetPlacerConfig::new));

    public final int yOffset;
    public final int ySpread;

    public YOffsetPlacerConfig(int yOffset, int ySpread) {
        this.yOffset = yOffset;
        this.ySpread = ySpread;
    }
}