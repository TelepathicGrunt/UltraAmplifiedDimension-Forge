package net.telepathicgrunt.ultraamplified.extrabehavior;

import net.minecraft.client.Minecraft;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.dimension.UltraAmplifiedDimension;
import net.telepathicgrunt.ultraamplified.world.dimension.UltraAmplifiedWorldProvider;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerSleepBehavior
{
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{
		@SubscribeEvent
		public static void ChangeTimeToDay(SleepFinishedTimeEvent event)
		{
			IWorld world = event.getWorld();
			Dimension dimension = world.getDimension();
			if (!world.isRemote() && dimension.getType() == UltraAmplifiedDimension.ultraamplified())
			{
				if(!dimension.isDaytime())
				{
					long time = dimension.getWorldTime();
					long nextDayTime = time + (24000 - time % 24000);
					((UltraAmplifiedWorldProvider)dimension).setWorldTime(nextDayTime);
					MessageHandler.UpdateTimePacket.sendToClient(nextDayTime);
				}
			}
		}

		static int timeIncrementMod = 0;
		@SubscribeEvent
		public static void IncrementTimeClientSided(ClientTickEvent event)
		{
			@SuppressWarnings("resource")
			World world = Minecraft.getInstance().world;
			if(world != null && !Minecraft.getInstance().isGamePaused()) {
				Dimension dimension = world.dimension;
				if(dimension.getType() == UltraAmplifiedDimension.ultraamplified()) {
					timeIncrementMod++;
					if(timeIncrementMod % 2 == 0)
						((UltraAmplifiedWorldProvider)dimension).setWorldTimeClientSided(((UltraAmplifiedWorldProvider)dimension).getWorldTime()+1L);
				}
			}
		}
	
		@SubscribeEvent
		public static void SleepInBed(SleepingTimeCheckEvent event)
		{
//			PlayerEntity player = event.getPlayer();
//			if (player.dimension == UltraAmplifiedDimension.ultraamplified())
//			{
//			    player.setSpawnPoint(event.getSleepingLocation().get(), false, true, player.dimension);
//				int time = UAWorldSavedData.get(player.world).getTimeUA();
//				int timeOfDay = (time % 24000);
//				if(timeOfDay - 13000 < 0 || timeOfDay - 13000 > 10000)
//				{
//					event.setResult(Result.DENY);
//				}
//				else 
//				{
//					event.setResult(Result.ALLOW);
//				}
//			}
		}
	}
}
