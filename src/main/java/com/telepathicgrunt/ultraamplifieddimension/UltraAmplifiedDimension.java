package com.telepathicgrunt.ultraamplifieddimension;

import com.telepathicgrunt.ultraamplifieddimension.generation.UADChunkGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(UltraAmplifiedDimension.MODID)
public class UltraAmplifiedDimension {
	public static final String MODID = "ultra_amplified_dimension";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public UltraAmplifiedDimension() {

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		modEventBus.addListener(this::setup);
//		WBBiomes.BIOMES.register(modEventBus);
//		WBFeatures.FEATURES.register(modEventBus);
//		WBBlocks.BLOCKS.register(modEventBus);
//		WBBlocks.TILE_ENTITY_TYPES.register(modEventBus);
//		WBSurfaceBuilders.SURFACE_BUILDERS.register(modEventBus);
//
//		forgeBus.addListener(EventPriority.NORMAL, this::setupChestList);
//		forgeBus.addListener(EventPriority.LOWEST, TheBlender::addDimensionalSpacing);
//		forgeBus.addListener(EventPriority.NORMAL, WBPortalSpawning::BlockRightClickEvent);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> UltraAmplifiedDimensionClient::subscribeClientEvents);
	}

	public void setup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			Registry.register(Registry.CHUNK_GENERATOR_CODEC, new ResourceLocation(MODID, "terrain"), UADChunkGenerator.UAD_CHUNK_GENERATOR_CODEC);
			//WBConfiguredFeatures.registerConfiguredFeatures();
			//WBBiomeProvider.registerBiomeProvider();
		});
	}

	/*
	 * Helper method to quickly register features, blocks, items, structures, biomes, anything that can be registered.
	 */
	public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey)
	{
		entry.setRegistryName(new ResourceLocation(MODID, registryKey));
		registry.register(entry);
		return entry;
	}
}
