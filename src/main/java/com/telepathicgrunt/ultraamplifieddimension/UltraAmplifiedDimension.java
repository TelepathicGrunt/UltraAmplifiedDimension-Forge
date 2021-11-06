package com.telepathicgrunt.ultraamplifieddimension;

import com.telepathicgrunt.ultraamplifieddimension.capabilities.CapabilityPlayerPosAndDim;
import com.telepathicgrunt.ultraamplifieddimension.client.UltraAmplifiedDimensionClient;
import com.telepathicgrunt.ultraamplifieddimension.config.UADimensionConfig;
import com.telepathicgrunt.ultraamplifieddimension.dimension.AmplifiedPortalCreation;
import com.telepathicgrunt.ultraamplifieddimension.dimension.UADDimension;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADCarvers;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADFeatures;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADPlacements;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADProcessors;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADStructures;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADSurfaceBuilders;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADTags;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADTreeDecoratorTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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

		UADTags.tagInit();
		UADBlocks.ITEMS.register(modEventBus);
		UADBlocks.BLOCKS.register(modEventBus);
		UADFeatures.FEATURES.register(modEventBus);
		UADStructures.STRUCTURES.register(modEventBus);
		UADPlacements.DECORATORS.register(modEventBus);
		UADCarvers.WORLD_CARVERS.register(modEventBus);
		UADSurfaceBuilders.SURFACE_BUILDERS.register(modEventBus);
		UADTreeDecoratorTypes.TREE_DECORATOR_TYPES.register(modEventBus);

		forgeBus.addListener(EventPriority.NORMAL, UADDimension::worldTick);
		forgeBus.addListener(EventPriority.NORMAL, AmplifiedPortalCreation::PortalCreationRightClick);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> UltraAmplifiedDimensionClient::subscribeClientEvents);

		//generates config
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UADimensionConfig.GENERAL_SPEC, "ultra_amplified_dimension.toml");
	}

	public void setup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			UADDimension.setupDimension();
			CapabilityPlayerPosAndDim.register();
			UADStructures.setupStructures();
			UADProcessors.registerProcessors();
		});
	}
}
