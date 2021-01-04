package com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADSurfaceBuilders;
import com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder.configs.QuadrarySurfaceBuilderConfig;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import java.util.Random;


public class ShatteredSavannaSurfaceBuilder extends SurfaceBuilder<QuadrarySurfaceBuilderConfig> {
    public ShatteredSavannaSurfaceBuilder(Codec<QuadrarySurfaceBuilderConfig> codec) {
        super(codec);
    }


    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, QuadrarySurfaceBuilderConfig config) {
        if (noise > 1.75D) {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG);
        }
        else if (noise > -0.5D) {
            UADSurfaceBuilders.PLATEAU_SURFACE_BUILDER.get().buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, UADSurfaceBuilders.COARSE_DIRT_COARSE_DIRT_GRAVEL_DIRT_SURFACE);
        }
        else {
            UADSurfaceBuilders.PLATEAU_SURFACE_BUILDER.get().buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, UADSurfaceBuilders.GRASS_BLOCK_DIRT_GRAVEL_COARSE_DIRT_SURFACE);
        }
    }

    // Set the noise gen for the plateau builder at startup or else we get a null crash
    @Override
    public void setSeed(long seed) {
        UADSurfaceBuilders.PLATEAU_SURFACE_BUILDER.get().setSeed(seed);
    }
}