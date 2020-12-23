package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;

public class NbtFeatureConfig implements IFeatureConfig {
    public static final Codec<NbtFeatureConfig> CODEC = RecordCodecBuilder.create((configInstance) -> configInstance.group(
            Codec.intRange(0, 16).fieldOf("solid_land_radius").orElse(3).forGetter(nbtFeatureConfig -> nbtFeatureConfig.solidLandRadius),
            Codec.mapPair(ResourceLocation.CODEC.fieldOf("resourcelocation"), Codec.INT.fieldOf("weight").orElse(1)).codec().listOf().fieldOf("nbt_entries").forGetter(nbtFeatureConfig -> nbtFeatureConfig.nbtResourcelocationsAndWeights)
            ).apply(configInstance, NbtFeatureConfig::new));

    public final int solidLandRadius;
    public final List<Pair<ResourceLocation, Integer>> nbtResourcelocationsAndWeights;

    public NbtFeatureConfig(int solidLandRadius, List<Pair<ResourceLocation, Integer>> nbtResourcelocationsAndWeights) {
        this.solidLandRadius = solidLandRadius;
        this.nbtResourcelocationsAndWeights = nbtResourcelocationsAndWeights;
    }
}
