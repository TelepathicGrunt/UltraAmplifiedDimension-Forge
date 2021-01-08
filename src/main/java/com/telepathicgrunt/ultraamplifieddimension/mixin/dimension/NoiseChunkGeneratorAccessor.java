package com.telepathicgrunt.ultraamplifieddimension.mixin.dimension;

import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.INoiseGenerator;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;
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

    @Accessor
    BlockState getDefaultFluid();

    @Accessor
    INoiseGenerator getSurfaceDepthNoise();

    @Invoker
    void callMakeBedrock(IChunk chunkIn, Random rand);
}