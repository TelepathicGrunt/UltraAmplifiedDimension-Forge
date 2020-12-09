package com.telepathicgrunt.ultraamplifieddimension.mixin;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.dimension.UADDimension;
import com.telepathicgrunt.ultraamplifieddimension.world.carver.CaveCavityCarver;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.World;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.chunk.listener.IChunkStatusListenerFactory;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraft.world.storage.SaveFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.net.Proxy;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	/**
	 * Make it so that Nether Portals can be created and activated in Ultra Amplified Dimension
	 * @author TelepathicGrunt
	 */
	@Inject(method = "func_240787_a_(Lnet/minecraft/world/chunk/listener/IChunkStatusListener;)V",
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/biome/BiomeManager;getHashedSeed(J)J"),
		locals = LocalCapture.CAPTURE_FAILHARD)
	private void seedCarvers(IChunkStatusListener chunkStatusListener, CallbackInfo ci, IServerWorldInfo iserverworldinfo,
									DimensionGeneratorSettings dimensiongeneratorsettings, boolean isDebugWorld, long seed, long hashedSeed)
	{
		CaveCavityCarver.setSeed(hashedSeed);
	}
}