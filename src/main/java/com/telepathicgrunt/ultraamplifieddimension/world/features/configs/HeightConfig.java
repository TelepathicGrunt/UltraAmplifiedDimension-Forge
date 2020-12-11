package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class HeightConfig implements IFeatureConfig {
    public static final Codec<HeightConfig> CODEC = RecordCodecBuilder.create((cactusConfigInstance) -> cactusConfigInstance.group(
            Codec.intRange(1, 256).fieldOf("height").orElse(9).forGetter((bigCactusConfig) -> bigCactusConfig.height))
            .apply(cactusConfigInstance, HeightConfig::new));

    public final int height;

    public HeightConfig(int height) {
        this.height = height;
    }
}
