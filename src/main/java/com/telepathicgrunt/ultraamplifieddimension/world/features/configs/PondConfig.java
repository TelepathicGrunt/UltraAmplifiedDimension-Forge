package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class PondConfig implements IFeatureConfig {
    public static final Codec<PondConfig> CODEC = RecordCodecBuilder.create((cactusConfigInstance) -> cactusConfigInstance.group(
            BlockState.CODEC.fieldOf("top_state").forGetter((pondConfig) -> pondConfig.insideState),
            BlockState.CODEC.fieldOf("inside_state").forGetter((pondConfig) -> pondConfig.insideState),
            BlockState.CODEC.fieldOf("outside_state").forGetter((pondConfig) -> pondConfig.outsideState),
            Codec.BOOL.fieldOf("place_outside_state_often").forGetter((pondConfig) -> pondConfig.placeOutsideStateOften))
            .apply(cactusConfigInstance, PondConfig::new));

    public final BlockState topState;
    public final BlockState insideState;
    public final BlockState outsideState;
    public final boolean placeOutsideStateOften;

    public PondConfig(BlockState topState, BlockState insideState, BlockState outsideState, boolean placeOutsideStateOften) {
        this.topState = topState;
        this.insideState = insideState;
        this.outsideState = outsideState;
        this.placeOutsideStateOften = placeOutsideStateOften;
    }
}
