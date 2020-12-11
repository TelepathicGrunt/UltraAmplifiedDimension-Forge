package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class NbtFeatureConfig implements IFeatureConfig {
    public static final Codec<NbtFeatureConfig> CODEC = RecordCodecBuilder.create((configInstance) -> configInstance.group(
            Codec.intRange(0, 16).fieldOf("solid_land_radius").orElse(3).forGetter(nbtFeatureConfig -> nbtFeatureConfig.solidLandRadius),
            ResourceLocation.CODEC.fieldOf("nbt_resourcelocation").forGetter(nbtFeatureConfig -> nbtFeatureConfig.nbtResourcelocation)
            ).apply(configInstance, NbtFeatureConfig::new));

    public final int solidLandRadius;
    public final ResourceLocation nbtResourcelocation;

    public NbtFeatureConfig(int solidLandRadius, ResourceLocation nbtResourcelocation) {
        this.solidLandRadius = solidLandRadius;
        this.nbtResourcelocation = nbtResourcelocation;
    }
}
