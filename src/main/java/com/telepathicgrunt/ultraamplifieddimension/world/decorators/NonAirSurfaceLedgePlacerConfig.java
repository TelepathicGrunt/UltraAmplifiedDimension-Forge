package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;


public class NonAirSurfaceLedgePlacerConfig implements IPlacementConfig {
    public static final Codec<NonAirSurfaceLedgePlacerConfig> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("column_passes").orElse(0).forGetter((config) -> config.columnCount),
            Codec.floatRange(0, 1).fieldOf("valid_spot_chance").orElse(1F).forGetter((config) -> config.validSpotChance))
                .apply(builder, NonAirSurfaceLedgePlacerConfig::new));

    public final int columnCount;
    public final float validSpotChance;

    public NonAirSurfaceLedgePlacerConfig(int columnCount, float validSpotChance) {
        this.columnCount = columnCount;
        this.validSpotChance = validSpotChance;
    }
}