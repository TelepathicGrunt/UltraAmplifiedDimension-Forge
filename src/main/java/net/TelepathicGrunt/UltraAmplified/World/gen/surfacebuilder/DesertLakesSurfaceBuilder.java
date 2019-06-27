package net.TelepathicGrunt.UltraAmplified.World.gen.surfacebuilder;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeUA;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class DesertLakesSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig> 
{
	
public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
		IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {

		if (noise > 1.0D) {
			Biome.DEFAULT_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock,
					defaultFluid, seaLevel, seed, BiomeUA.SAND_SANDSTONE_SANDSTONE_SURFACE);
		} else {
			Biome.DEFAULT_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock,
					defaultFluid, seaLevel, seed, BiomeUA.SANDSTONE_SURFACE);
		}

	}

}