package net.TelepathicGrunt.UltraAmplified.capabilities;

import net.TelepathicGrunt.UltraAmplified.UltraAmplified;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid=UltraAmplified.MOD_ID)
public class CapabilityEventHandler
{
	public static final ResourceLocation PLAYER_PAST_POS_AND_DIM = new ResourceLocation(UltraAmplified.MOD_ID, "player_past_pos_and_dim");
	
	@SubscribeEvent
	public static void onAttachCapabilitiesToEntities(AttachCapabilitiesEvent<Entity> e)
	{
		Entity ent = e.getObject();
		if (ent instanceof EntityPlayer)
		{
			e.addCapability(PLAYER_PAST_POS_AND_DIM, new PastPosAndDimProvider());
		}
	}
}