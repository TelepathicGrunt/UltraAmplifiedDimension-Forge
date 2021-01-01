package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;
import java.util.function.Function;

public class BoulderFeatureConfig implements IFeatureConfig {
    public static final Codec<BoulderFeatureConfig> CODEC = RecordCodecBuilder.<BoulderFeatureConfig>create((configInstance) -> configInstance.group(
            Codec.intRange(1, 16).fieldOf("max_radius").orElse(4).forGetter(config -> config.maxRadius),
            Codec.intRange(1, 16).fieldOf("min_radius").orElse(1).forGetter(config -> config.minRadius),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("boulder_stack_count").orElse(1).forGetter(config -> config.boulderStackCount),
            Codec.mapPair(BlockState.CODEC.fieldOf("state"), Codec.INT.fieldOf("weight").orElse(1)).codec().listOf().fieldOf("boulder_blocks").forGetter(config -> config.blockAndWeights))
        .apply(configInstance, BoulderFeatureConfig::new))
            .comapFlatMap((rangeValidationPlacerConfig) -> rangeValidationPlacerConfig.maxRadius < rangeValidationPlacerConfig.minRadius ?
                DataResult.error("minRadius has to be smaller than or equal to maxRadius") : DataResult.success(rangeValidationPlacerConfig), Function.identity());


    public final int maxRadius;
    public final int minRadius;
    public final int boulderStackCount;
    public final List<Pair<BlockState, Integer>> blockAndWeights;

    public BoulderFeatureConfig(int maxRadius, int minRadius, int boulderStackCount, List<Pair<BlockState, Integer>> blockAndWeights) {
        this.maxRadius = maxRadius;
        this.minRadius = minRadius;
        this.boulderStackCount = boulderStackCount;
        this.blockAndWeights = blockAndWeights;
    }
}