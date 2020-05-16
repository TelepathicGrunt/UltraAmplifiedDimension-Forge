package net.telepathicgrunt.ultraamplified;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.telepathicgrunt.ultraamplified.blocks.UABlocks;
import net.telepathicgrunt.ultraamplified.extrabehavior.ClientTimeIncrement;


public class ClientEvents
{
	public static void subscribeClientEvents(IEventBus modBus, IEventBus forgeBus)
	{
		modBus.addListener(ClientEvents::onClientSetup);
		forgeBus.addListener(ClientTimeIncrement::IncrementTimeClientSided);
	}


	public static void onClientSetup(FMLClientSetupEvent event)
	{
		RenderTypeLookup.setRenderLayer(UABlocks.GLOWSTONE_ORE.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(UABlocks.GLOWGRASS_BLOCK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(UABlocks.GLOWMYCELIUM.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(UABlocks.GLOWPODZOL.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(UABlocks.GLOWDIRT.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(UABlocks.COARSE_GLOWDIRT.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(UABlocks.GLOWSAND.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(UABlocks.REDGLOWSAND.get(), RenderType.getTranslucent());
	}
}