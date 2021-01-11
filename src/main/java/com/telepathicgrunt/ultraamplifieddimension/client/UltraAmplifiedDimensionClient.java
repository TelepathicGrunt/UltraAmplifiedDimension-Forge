package com.telepathicgrunt.ultraamplifieddimension.client;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.dimension.UADSkyProperty;
import com.telepathicgrunt.ultraamplifieddimension.mixin.dimension.SkyPropertiesAccessor;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class UltraAmplifiedDimensionClient {
	public static void subscribeClientEvents()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(UltraAmplifiedDimensionClient::onClientSetup);
		modEventBus.addListener(BlockColorManager::onBlockColorsInit);
		modEventBus.addListener(BlockColorManager::onItemColorsInit);
	}


	public static void onClientSetup(FMLClientSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			SkyPropertiesAccessor.uad_getfield_239208_a_().put(new ResourceLocation(UltraAmplifiedDimension.MODID, "sky_property"), new UADSkyProperty());

			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWSTONE_ORE.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWGRASS_BLOCK.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWMYCELIUM.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWPODZOL.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWDIRT.get(), RenderType.getCutout());
			RenderTypeLookup.setRenderLayer(UADBlocks.COARSE_GLOWDIRT.get(), RenderType.getCutout());
			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWSAND.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.RED_GLOWSAND.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.AMPLIFIED_PORTAL.get(), RenderType.getTranslucent());
		});
	}
}
