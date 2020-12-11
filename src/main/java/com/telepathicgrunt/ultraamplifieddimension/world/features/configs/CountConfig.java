package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CountConfig implements IFeatureConfig {
    public static final Codec<CountConfig> CODEC = RecordCodecBuilder.create((configInstance) ->
            configInstance.group(Codec.intRange(0, Integer.MAX_VALUE).fieldOf("count").forGetter((countConfig) -> countConfig.count))
            .apply(configInstance, CountConfig::new));

    public final int count;

    public CountConfig(int count) {
        this.count = count;
    }
}
