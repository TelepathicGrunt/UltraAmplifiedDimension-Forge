package com.telepathicgrunt.ultraamplifieddimension;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADSkyProperty;
import com.telepathicgrunt.ultraamplifieddimension.mixin.SkyPropertiesAccessor;
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
		});
	}
}
