package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;


public class YOffsetPlacerConfig implements IPlacementConfig {
    public static final Codec<YOffsetPlacerConfig> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Codec.INT.fieldOf("yoffset").orElse(0).forGetter((config) -> config.yoffset))
                .apply(builder, YOffsetPlacerConfig::new));

    public final int yoffset;

    public YOffsetPlacerConfig(int yoffset) {
        this.yoffset = yoffset;
    }
}