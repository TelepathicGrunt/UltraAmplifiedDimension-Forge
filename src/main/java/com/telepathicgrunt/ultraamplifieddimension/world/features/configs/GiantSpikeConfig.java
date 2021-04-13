package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;

import java.util.function.Function;

public class GiantSpikeConfig implements IFeatureConfig {
    public static final Codec<GiantSpikeConfig> CODEC = RecordCodecBuilder.<GiantSpikeConfig>create((configInstance) -> configInstance.group(
            RuleTest.CODEC.fieldOf("target").forGetter((config) -> config.target),
            BlockState.CODEC.fieldOf("above_sea_state").forGetter((config) -> config.aboveSeaState),
            BlockState.CODEC.fieldOf("below_sea_state").forGetter((config) -> config.belowSeaState),
            Codec.intRange(1, 256).fieldOf("giant_spike_max_height").forGetter(columnConfig -> columnConfig.giantSpikeMaxHeight),
            Codec.intRange(1, 256).fieldOf("giant_spike_min_height").forGetter(columnConfig -> columnConfig.giantSpikeMinHeight),
            Codec.floatRange(0, 1).fieldOf("giant_spike_chance").forGetter(columnConfig -> columnConfig.giantSpikeChance)
        ).apply(configInstance, GiantSpikeConfig::new))
            .comapFlatMap((rangeValidationPlacerConfig) -> rangeValidationPlacerConfig.giantSpikeMaxHeight < rangeValidationPlacerConfig.giantSpikeMinHeight ?
                    DataResult.error("min_height has to be smaller than max_height") : DataResult.success(rangeValidationPlacerConfig), Function.identity());

    public final RuleTest target;
    public final BlockState aboveSeaState;
    public final BlockState belowSeaState;
    public final int giantSpikeMaxHeight;
    public final int giantSpikeMinHeight;
    public final float giantSpikeChance;

    public GiantSpikeConfig(RuleTest target, BlockState state, BlockState state2, int giantSpikeMaxHeight, int giantSpikeMinHeight, float giantSpikeChance) {
        this.giantSpikeMaxHeight = giantSpikeMaxHeight;
        this.giantSpikeMinHeight = giantSpikeMinHeight;
        this.aboveSeaState = state;
        this.belowSeaState = state2;
        this.target = target;
        this.giantSpikeChance = giantSpikeChance;
    }
}
