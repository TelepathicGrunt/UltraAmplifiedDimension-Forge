package com.telepathicgrunt.ultraamplifieddimension;

import com.telepathicgrunt.ultraamplifieddimension.capabilities.CapabilityPlayerPosAndDim;
import com.telepathicgrunt.ultraamplifieddimension.dimension.AmplifiedPortalCreation;
import com.telepathicgrunt.ultraamplifieddimension.dimension.UADDimension;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
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
		UADBlocks.BLOCKS.register(modEventBus);
		UADBlocks.ITEMS.register(modEventBus);

		forgeBus.addListener(EventPriority.NORMAL, AmplifiedPortalCreation::PortalCreationRightClick);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> UltraAmplifiedDimensionClient::subscribeClientEvents);
	}

	public void setup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			UADDimension.setupDimension();
			CapabilityPlayerPosAndDim.register();
		});
	}
}
