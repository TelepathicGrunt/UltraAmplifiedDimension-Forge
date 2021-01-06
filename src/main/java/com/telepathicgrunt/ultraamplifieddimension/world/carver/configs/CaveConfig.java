package com.telepathicgrunt.ultraamplifieddimension.world.carver.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CaveConfig implements ICarverConfig, IFeatureConfig {

    public static final Codec<CaveConfig> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((config) -> config.probability),
            Codec.INT.fieldOf("cutoff_height").forGetter((config) -> config.cutoffHeight)
    ).apply(builder, CaveConfig::new));

    public final float probability;
    public final int cutoffHeight;

    public CaveConfig(float probability, int cutoffHeight) {
        this.probability = probability;
        this.cutoffHeight = cutoffHeight;
    }
}