package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;


public class WaterIceSurfaceConfig implements IPlacementConfig {
    public static final Codec<WaterIceSurfaceConfig> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("column_passes").orElse(0).forGetter((config) -> config.columnCount),
            Codec.floatRange(0, 1).fieldOf("valid_spot_chance").orElse(1F).forGetter((config) -> config.validSpotChance),
            Codec.BOOL.fieldOf("skip_top_ledge").orElse(false).forGetter((config) -> config.skipTopLedge),
            Codec.BOOL.fieldOf("include_ice_placement").orElse(false).forGetter((config) -> config.includeIcePlacement))
                .apply(builder, WaterIceSurfaceConfig::new));

    public final int columnCount;
    public final float validSpotChance;
    public final boolean skipTopLedge;
    public final boolean includeIcePlacement;

    public WaterIceSurfaceConfig(int columnCount, float validSpotChance, boolean skipTopLedge, boolean includeIcePlacement) {
        this.columnCount = columnCount;
        this.validSpotChance = validSpotChance;
        this.skipTopLedge = skipTopLedge;
        this.includeIcePlacement = includeIcePlacement;
    }
}