package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;

public class DiskDryConfig implements IFeatureConfig {

    public static final Codec<DiskDryConfig> CODEC = RecordCodecBuilder.create((diskDryConfig) -> diskDryConfig.group(
            BlockState.CODEC.fieldOf("state").forGetter((config) -> config.state),
            FeatureSpread.func_242254_a(1, 36, 36).fieldOf("radius").forGetter((config) -> config.radius),
            Codec.intRange(0, 36).fieldOf("half_height").forGetter((config) -> config.half_height),
            BlockState.CODEC.listOf().fieldOf("targets").forGetter((config) -> config.targets)
    ).apply(diskDryConfig, DiskDryConfig::new));

    public final BlockState state;
    public final FeatureSpread radius;
    public final int half_height;
    public final List<BlockState> targets;

    public DiskDryConfig(BlockState blockState, FeatureSpread radius, int half_height, List<BlockState> targets) {
        this.state = blockState;
        this.radius = radius;
        this.half_height = half_height;
        this.targets = targets;
    }
}
