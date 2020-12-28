package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ProbabilityAndCountConfig implements IFeatureConfig {
    public static final Codec<ProbabilityAndCountConfig> CODEC = RecordCodecBuilder.create((configInstance) ->
            configInstance.group(Codec.intRange(0, Integer.MAX_VALUE).fieldOf("count").forGetter((config) -> config.count),
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((config) -> config.probability)
        ).apply(configInstance, ProbabilityAndCountConfig::new));

    public final int count;
    public final float probability;

    public ProbabilityAndCountConfig(int count, float probability) {
        this.count = count;
        this.probability = probability;
    }
}
