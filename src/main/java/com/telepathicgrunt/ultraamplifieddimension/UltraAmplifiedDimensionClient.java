package com.telepathicgrunt.ultraamplifieddimension;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADSkyProperty;
import com.telepathicgrunt.ultraamplifieddimension.mixin.SkyPropertiesAccessor;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class UltraAmplifiedDimensionClient {
	public static void subscribeClientEvents()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(UltraAmplifiedDimensionClient::onClientSetup);
	}


	public static void onClientSetup(FMLClientSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			SkyPropertiesAccessor.getfield_239208_a_().put(new ResourceLocation(UltraAmplifiedDimension.MODID, "sky_property"), new UADSkyProperty());

			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWSTONE_ORE.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWGRASS_BLOCK.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWMYCELIUM.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWPODZOL.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWDIRT.get(), RenderType.getCutout());
			RenderTypeLookup.setRenderLayer(UADBlocks.COARSE_GLOWDIRT.get(), RenderType.getCutout());
			RenderTypeLookup.setRenderLayer(UADBlocks.GLOWSAND.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.REDGLOWSAND.get(), RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(UADBlocks.AMPLIFIEDPORTAL.get(), RenderType.getTranslucent());
		});
	}
}
