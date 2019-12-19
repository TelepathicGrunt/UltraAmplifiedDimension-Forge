package net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeUA;

public class ExtremeHillsMutatedSurfaceBuilderUA extends SurfaceBuilder<SurfaceBuilderConfig> 
{
	public ExtremeHillsMutatedSurfaceBuilderUA(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_) {
	      super(p_i51310_1_);
	}
	
   public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
	      if (!(noise < -1.0D) && !(noise > 2.0D)) {
	         if (noise > 1.0D) {
	            BiomeUA.GRAVEL_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG);
	         } else {
	            BiomeUA.GRAVEL_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
	         }
	      } else {
	         BiomeUA.GRAVEL_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.GRAVEL_CONFIG);
	      }

	   }
	}