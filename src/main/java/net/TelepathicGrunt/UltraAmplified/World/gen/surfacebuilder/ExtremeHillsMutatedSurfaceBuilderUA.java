package net.TelepathicGrunt.UltraAmplified.World.gen.surfacebuilder;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeUA;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ExtremeHillsMutatedSurfaceBuilderUA implements ISurfaceBuilder<SurfaceBuilderConfig> {
   public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
	      if (!(noise < -1.0D) && !(noise > 2.0D)) {
	         if (noise > 1.0D) {
	            BiomeUA.GRAVEL_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, Biome.STONE_STONE_GRAVEL_SURFACE);
	         } else {
	            BiomeUA.GRAVEL_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, Biome.GRASS_DIRT_GRAVEL_SURFACE);
	         }
	      } else {
	         BiomeUA.GRAVEL_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, Biome.GRAVEL_SURFACE);
	      }

	   }
	}