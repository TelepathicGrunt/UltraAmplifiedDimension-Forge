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

    @Accessor("verticalNoiseGranularity")
    int uad_getVerticalNoiseGranularity();

    @Accessor("horizontalNoiseGranularity")
    int uad_getHorizontalNoiseGranularity();

    @Accessor("noiseSizeX")
    int uad_getNoiseSizeX();

    @Accessor("noiseSizeY")
    int uad_getNoiseSizeY();

    @Accessor("noiseSizeZ")
    int uad_getNoiseSizeZ();

    @Accessor("field_222568_o")
    OctavesNoiseGenerator uad_getField_222568_o();

    @Accessor("field_222569_p")
    OctavesNoiseGenerator uad_getField_222569_p();

    @Accessor("field_222570_q")
    OctavesNoiseGenerator uad_getField_222570_q();

    @Accessor("field_236082_u_")
    OctavesNoiseGenerator uad_getField_236082_u_();

    @Accessor("field_236084_w_")
    long uad_getfield_236084_w_();

    @Accessor("field_236080_h_")
    Supplier<DimensionSettings> uad_getfield_236080_h_();

    @Accessor("defaultFluid")
    BlockState uad_getDefaultFluid();

    @Accessor("surfaceDepthNoise")
    INoiseGenerator uad_getSurfaceDepthNoise();

    @Invoker("makeBedrock")
    void uad_callMakeBedrock(IChunk chunkIn, Random rand);
}