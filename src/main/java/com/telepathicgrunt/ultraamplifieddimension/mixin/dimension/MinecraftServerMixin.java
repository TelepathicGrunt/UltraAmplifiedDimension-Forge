package com.telepathicgrunt.ultraamplifieddimension.mixin.dimension;

import com.telepathicgrunt.ultraamplifieddimension.utils.BiomeSetsHelper;
import com.telepathicgrunt.ultraamplifieddimension.world.carver.CaveCavityCarver;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.IServerWorldInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	@Final
	@Shadow
	protected DynamicRegistries.Impl dynamicRegistries;

	/**
	 * Used for setting up everything that needs to be initialized for UA worldgen.
	 * Also, to get the seed that the world is using.
	 * @author TelepathicGrunt
	 */
	@Inject(method = "func_240787_a_(Lnet/minecraft/world/chunk/listener/IChunkStatusListener;)V",
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/biome/BiomeManager;getHashedSeed(J)J"),
		locals = LocalCapture.CAPTURE_FAILHARD)
	private void uad_seedCarvers(IChunkStatusListener chunkStatusListener, CallbackInfo ci, IServerWorldInfo iserverworldinfo,
								 DimensionGeneratorSettings dimensiongeneratorsettings, boolean isDebugWorld, long seed, long hashedSeed)
	{
		CaveCavityCarver.setSeed(hashedSeed);
		BiomeSetsHelper.generateBiomeSets(dynamicRegistries.getRegistry(Registry.BIOME_KEY));
	}
}