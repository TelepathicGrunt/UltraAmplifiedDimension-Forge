package com.telepathicgrunt.ultraamplifieddimension.mixin.surfacebuilders;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ValleySurfaceBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Random;

@Mixin(ValleySurfaceBuilder.class)
public class ValleySurfaceBuilderMixin {

	/**
	 * Allow vanilla nether surfacebuilders to work at any height for any dimension including my own.
	 *
	 * @author TelepathicGrunt
	 * @reason We do this to maximize mod compat with vanilla and modded nether biomes as best as we can.
 	 */
	@ModifyConstant(method = "buildSurface(Ljava/util/Random;Lnet/minecraft/world/chunk/IChunk;Lnet/minecraft/world/biome/Biome;IIIDLnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;IJLnet/minecraft/world/gen/surfacebuilders/SurfaceBuilderConfig;)V",
			constant = @Constant(intValue = 127))
	private int buildSurfaceAnyHeight(int constant, Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight)
	{
		return startHeight;
	}
}