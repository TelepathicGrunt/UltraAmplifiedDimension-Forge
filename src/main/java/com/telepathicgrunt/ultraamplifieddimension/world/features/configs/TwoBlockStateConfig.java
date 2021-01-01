package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TwoBlockStateConfig implements IFeatureConfig {
    public static final Codec<TwoBlockStateConfig> CODEC = RecordCodecBuilder.create((columnConfigInstance) -> columnConfigInstance.group(
            BlockState.CODEC.fieldOf("state_1").forGetter((config) -> config.state1),
            BlockState.CODEC.fieldOf("state_2").forGetter((config) -> config.state2)
        ).apply(columnConfigInstance, TwoBlockStateConfig::new));

    public final BlockState state1;
    public final BlockState state2;

    public TwoBlockStateConfig(BlockState state1, BlockState state2) {
        this.state1 = state1;
        this.state2 = state2;
    }
}
