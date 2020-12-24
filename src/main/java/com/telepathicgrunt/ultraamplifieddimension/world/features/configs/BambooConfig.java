package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.function.Function;

public class BambooConfig implements IFeatureConfig {
    public static final Codec<BambooConfig> CODEC = RecordCodecBuilder.<BambooConfig>create((bambooConfig) -> bambooConfig.group(
            Codec.intRange(1, 256).fieldOf("max_height").orElse(17).forGetter((bambooConfigInstance) -> bambooConfigInstance.maxHeight),
            Codec.intRange(0, 256).fieldOf("min_height").orElse(5).forGetter((bambooConfigInstance) -> bambooConfigInstance.minHeight),
            Codec.intRange(0, 36).fieldOf("podzol_max_radius").orElse(2).forGetter((bambooConfigInstance) -> bambooConfigInstance.podzolMaxRadius),
            Codec.intRange(0, 36).fieldOf("podzol_min_radius").orElse(1).forGetter((bambooConfigInstance) -> bambooConfigInstance.podzolMinRadius))
        .apply(bambooConfig, BambooConfig::new))
            .comapFlatMap((rangeValidationPlacerConfig) -> rangeValidationPlacerConfig.maxHeight <= rangeValidationPlacerConfig.minHeight ?
                    DataResult.error("min_height has to be smaller than max_height") : DataResult.success(rangeValidationPlacerConfig), Function.identity())
            .comapFlatMap((rangeValidationPlacerConfig) -> rangeValidationPlacerConfig.podzolMaxRadius <= rangeValidationPlacerConfig.podzolMinRadius ?
                    DataResult.error("podzol_min_radius has to be smaller than podzol_max_radius") : DataResult.success(rangeValidationPlacerConfig), Function.identity());

    public final int maxHeight;
    public final int minHeight;
    public final int podzolMaxRadius;
    public final int podzolMinRadius;

    public BambooConfig(int height, int minHeight, int podzolMaxRadius, int podzolMinRadius) {
        this.maxHeight = height;
        this.minHeight = minHeight;
        this.podzolMaxRadius = podzolMaxRadius;
        this.podzolMinRadius = podzolMinRadius;
    }
}
