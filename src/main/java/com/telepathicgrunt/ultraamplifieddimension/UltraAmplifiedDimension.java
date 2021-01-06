package com.telepathicgrunt.ultraamplifieddimension;

import com.telepathicgrunt.ultraamplifieddimension.capabilities.CapabilityPlayerPosAndDim;
import com.telepathicgrunt.ultraamplifieddimension.config.UABiomesConfig.UABiomesConfigValues;
import com.telepathicgrunt.ultraamplifieddimension.config.UADimensionConfig.UADimensionConfigValues;
import com.telepathicgrunt.ultraamplifieddimension.config.UAFeaturesConfig.UAFeaturesConfigValues;
import com.telepathicgrunt.ultraamplifieddimension.config.UAModCompatConfig.UAModCompatConfigValues;
import com.telepathicgrunt.ultraamplifieddimension.config.UAStructuresConfig.UAStructuresConfigValues;
import com.telepathicgrunt.ultraamplifieddimension.dimension.AmplifiedPortalCreation;
import com.telepathicgrunt.ultraamplifieddimension.dimension.UADDimension;
import com.telepathicgrunt.ultraamplifieddimension.modInit.*;
import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
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

	public static UABiomesConfigValues UABiomesConfig = null;
	public static UADimensionConfigValues UADimensionConfig = null;
	public static UAFeaturesConfigValues UAFeaturesConfig = null;
	public static UAModCompatConfigValues UAModCompatConfig = null;
	public static UAStructuresConfigValues UAStructuresConfig = null;

	public UltraAmplifiedDimension() {

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		modEventBus.addListener(this::setup);
		UADBlocks.ITEMS.register(modEventBus);
		UADBlocks.BLOCKS.register(modEventBus);
		UADFeatures.FEATURES.register(modEventBus);
		UADStructures.STRUCTURES.register(modEventBus);
		UADPlacements.DECORATORS.register(modEventBus);
		UADCarvers.WORLD_CARVERS.register(modEventBus);
		UADSurfaceBuilders.SURFACE_BUILDERS.register(modEventBus);
		UADTreeDecoratorTypes.TREE_DECORATOR_TYPES.register(modEventBus);
		UADTags.tagInit();

		forgeBus.addListener(EventPriority.NORMAL, UADDimension::worldTick);
		forgeBus.addListener(EventPriority.NORMAL, AmplifiedPortalCreation::PortalCreationRightClick);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> UltraAmplifiedDimensionClient::subscribeClientEvents);

		//generates config
		UABiomesConfig = ConfigHelper.register(ModConfig.Type.SERVER, UABiomesConfigValues::new, "ultra_amplified_dimension-biomes.toml");
		UADimensionConfig = ConfigHelper.register(ModConfig.Type.SERVER, UADimensionConfigValues::new, "ultra_amplified_dimension-dimension.toml");
		UAFeaturesConfig = ConfigHelper.register(ModConfig.Type.SERVER, UAFeaturesConfigValues::new, "ultra_amplified_dimension-features.toml");
		UAModCompatConfig = ConfigHelper.register(ModConfig.Type.SERVER, UAModCompatConfigValues::new, "ultra_amplified_dimension-mod_compat.toml");
		UAStructuresConfig = ConfigHelper.register(ModConfig.Type.SERVER, UAStructuresConfigValues::new, "ultra_amplified_dimension-structures.toml");
	}

	public void setup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			UADDimension.setupDimension();
			CapabilityPlayerPosAndDim.register();
			UADStructures.setupStructures();
			UADConfiguredStructures.registerConfiguredStructures();
		});
	}
}
