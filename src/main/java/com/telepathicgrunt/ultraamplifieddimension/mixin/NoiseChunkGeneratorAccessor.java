package com.telepathicgrunt.ultraamplifieddimension.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Supplier;

@Mixin(NoiseChunkGenerator.class)
public interface NoiseChunkGeneratorAccessor {

    @Accessor
    int getVerticalNoiseGranularity();

    @Accessor
    int getHorizontalNoiseGranularity();

    @Accessor
    int getNoiseSizeX();

    @Accessor
    int getNoiseSizeY();

    @Accessor
    int getNoiseSizeZ();

    @Accessor
    OctavesNoiseGenerator getField_222568_o();

    @Accessor
    OctavesNoiseGenerator getField_222569_p();

    @Accessor
    OctavesNoiseGenerator getField_222570_q();

    @Accessor
    OctavesNoiseGenerator getField_236082_u_();

    @Accessor("field_236084_w_")
    long getfield_236084_w_();

    @Accessor("field_236080_h_")
    Supplier<DimensionSettings> getfield_236080_h_();

}