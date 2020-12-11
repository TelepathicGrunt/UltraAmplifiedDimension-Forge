package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ColumnConfig implements IFeatureConfig {
    public static final Codec<ColumnConfig> CODEC = RecordCodecBuilder.create((columnConfigInstance) -> columnConfigInstance.group(
            Codec.intRange(1, 256).fieldOf("max_height").forGetter(columnConfig -> columnConfig.maxHeight),
            Codec.intRange(1, 256).fieldOf("min_height").forGetter(columnConfig -> columnConfig.minHeight),
            BlockState.CODEC.fieldOf("top_block").forGetter(columnConfig -> columnConfig.topBlock),
            BlockState.CODEC.fieldOf("top_block").forGetter(columnConfig -> columnConfig.middleBlock),
            BlockState.CODEC.fieldOf("top_block").forGetter(columnConfig -> columnConfig.insideBlock)
            ).apply(columnConfigInstance, ColumnConfig::new));

    public final int maxHeight;
    public final int minHeight;
    public final BlockState topBlock;
    public final BlockState middleBlock;
    public final BlockState insideBlock;

    public ColumnConfig(int maxHeight, int minHeight, BlockState topBlock, BlockState middleBlock, BlockState insideBlock) {
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.topBlock = topBlock;
        this.middleBlock = middleBlock;
        this.insideBlock = insideBlock;
    }
}
