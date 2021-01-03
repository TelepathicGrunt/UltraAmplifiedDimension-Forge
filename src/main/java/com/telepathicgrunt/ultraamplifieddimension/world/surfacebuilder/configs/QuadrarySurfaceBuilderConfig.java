package com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class QuadrarySurfaceBuilderConfig extends SurfaceBuilderConfig {
    public static final Codec<QuadrarySurfaceBuilderConfig> CODEC = RecordCodecBuilder.create((configInstance) -> configInstance.group(
            BlockState.CODEC.fieldOf("top_material").forGetter((surfaceBuilderConfig) -> surfaceBuilderConfig.topMaterial),
            BlockState.CODEC.fieldOf("under_material").forGetter((surfaceBuilderConfig) -> surfaceBuilderConfig.underMaterial),
            BlockState.CODEC.fieldOf("underwater_material").forGetter((surfaceBuilderConfig) -> surfaceBuilderConfig.underWaterMaterial),
            BlockState.CODEC.fieldOf("extra_material").forGetter((surfaceBuilderConfig) -> surfaceBuilderConfig.extraMaterial))
        .apply(configInstance, QuadrarySurfaceBuilderConfig::new));

    private final BlockState topMaterial;
    private final BlockState underMaterial;
    private final BlockState underWaterMaterial;
    private final BlockState extraMaterial;

    public QuadrarySurfaceBuilderConfig(BlockState topMaterial, BlockState underMaterial, BlockState underWaterMaterial, BlockState extraMaterial) {
        super(topMaterial, underMaterial, underWaterMaterial);
        this.topMaterial = topMaterial;
        this.underMaterial = underMaterial;
        this.underWaterMaterial = underWaterMaterial;
        this.extraMaterial = extraMaterial;
    }

    public BlockState getExtraMaterial() {
        return this.extraMaterial;
    }
}
