package net.telepathicgrunt.ultraamplified.capabilities;

import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=UltraAmplified.MODID, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEventHandler
{
	public static final ResourceLocation PLAYER_PAST_POS_AND_DIM = new ResourceLocation(UltraAmplified.MODID, "player_past_pos_and_dim");
	
	@SubscribeEvent
	public static void onAttachCapabilitiesToEntities(AttachCapabilitiesEvent<Entity> e)
	{
		Entity ent = e.getObject();
		if (ent instanceof PlayerEntity)
		{
			e.addCapability(PLAYER_PAST_POS_AND_DIM, new PastPosAndDimProvider());
		}
	}
}