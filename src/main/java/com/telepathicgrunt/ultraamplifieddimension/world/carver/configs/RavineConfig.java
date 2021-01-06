package com.telepathicgrunt.ultraamplifieddimension.world.carver.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class RavineConfig implements ICarverConfig, IFeatureConfig {

    public static final Codec<RavineConfig> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((config) -> config.probability),
            FeatureSpread.func_242254_a(0, 255, 255).fieldOf("height_placement").forGetter((config) -> config.heightPlacement),
            Codec.INT.fieldOf("cutoff_height").forGetter((config) -> config.cutoffHeight),
            FeatureSpread.func_242254_a(0, 255, 255).fieldOf("tallness").forGetter((config) -> config.tallness)
    ).apply(builder, RavineConfig::new));

    public final float probability;
    public final FeatureSpread heightPlacement;
    public final int cutoffHeight;
    public final FeatureSpread tallness;

    public RavineConfig(float probability, FeatureSpread heightPlacement, int cutoffHeight, FeatureSpread tallness) {
        this.probability = probability;
        this.heightPlacement = heightPlacement;
        this.cutoffHeight = cutoffHeight;
        this.tallness = tallness;
    }
}