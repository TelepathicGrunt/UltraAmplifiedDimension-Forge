package com.telepathicgrunt.ultraamplifieddimension.mixin;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import com.telepathicgrunt.ultraamplifieddimension.utils.BiomeSetsHelper;
import com.telepathicgrunt.ultraamplifieddimension.world.carver.CaveCavityCarver;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
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
	protected DynamicRegistries.Impl field_240767_f_;

	/**
	 *
	 * @author TelepathicGrunt
	 */
	@Inject(method = "func_240787_a_(Lnet/minecraft/world/chunk/listener/IChunkStatusListener;)V",
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/biome/BiomeManager;getHashedSeed(J)J"),
		locals = LocalCapture.CAPTURE_FAILHARD)
	private void seedCarvers(IChunkStatusListener chunkStatusListener, CallbackInfo ci, IServerWorldInfo iserverworldinfo,
									DimensionGeneratorSettings dimensiongeneratorsettings, boolean isDebugWorld, long seed, long hashedSeed)
	{
		MutableRegistry<Biome> biomeRegistry = field_240767_f_.getRegistry(Registry.BIOME_KEY);
		CaveCavityCarver.setSeed(hashedSeed);
		BiomeSetsHelper.generateBiomeSets(biomeRegistry);

		// Grab our UA End Biomes as they have different behavior for the chunk generator.
		UADChunkGenerator.UA_END_BIOMES.clear();
		biomeRegistry.getEntries().forEach(entry -> {
			if(entry.getKey().getLocation().getNamespace().equals(UltraAmplifiedDimension.MODID)){
				if(entry.getValue().getCategory() == Biome.Category.THEEND){
					UADChunkGenerator.UA_END_BIOMES.add(entry.getValue());
				}
			}
		});

	}
}