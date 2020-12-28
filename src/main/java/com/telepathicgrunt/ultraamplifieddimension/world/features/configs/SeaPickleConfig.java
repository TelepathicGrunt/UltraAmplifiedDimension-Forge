package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.function.Function;

public class SeaPickleConfig implements IFeatureConfig {
    public static final Codec<SeaPickleConfig> CODEC = RecordCodecBuilder.<SeaPickleConfig>create((configInstance) -> configInstance.group(
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("count").forGetter((config) -> config.count),
            Codec.intRange(1, 4).fieldOf("min_pickles").forGetter((config) -> config.minPickles),
            Codec.intRange(1, 4).fieldOf("max_pickles").forGetter((config) -> config.maxPickles)
        ).apply(configInstance, SeaPickleConfig::new))
            .comapFlatMap((rangeValidationPlacerConfig) -> rangeValidationPlacerConfig.maxPickles < rangeValidationPlacerConfig.minPickles ?
                DataResult.error("min_pickles has to be smaller than or equal to max_pickles") : DataResult.success(rangeValidationPlacerConfig), Function.identity());

    public final int count;
    public final int minPickles;
    public final int maxPickles;

    public SeaPickleConfig(int count, int minPickles, int maxPickles) {
        this.count = count;
        this.minPickles = minPickles;
        this.maxPickles = maxPickles;
    }
}
