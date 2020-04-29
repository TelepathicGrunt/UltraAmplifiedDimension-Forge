package net.telepathicgrunt.ultraamplified.extrabehavior;

import net.minecraft.client.Minecraft;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.dimension.UADimensionRegistration;
import net.telepathicgrunt.ultraamplified.world.dimension.TimeSyncNetworkPacket;
import net.telepathicgrunt.ultraamplified.world.dimension.UADimension;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerSleepBehavior
{
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{
		
		@SuppressWarnings("resource")
		@SubscribeEvent
		/**
		 * When all players slept and finished sleeping in UA dimension, 
		 * change the time in UA dimension to daytime and
		 *  remove weather for both UA and Overworld.
		 */
		public static void ChangeTimeToDay(SleepFinishedTimeEvent event)
		{
			IWorld world = event.getWorld();
			Dimension dimension = world.getDimension();
			if (!world.isRemote() && dimension.getType() == UADimensionRegistration.ultraamplified())
			{
				if(!dimension.isDaytime() && ((World) world).getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE))
				{
					long time = dimension.getWorldTime();
					long nextDayTime = time + (24000 - time % 24000);
					((UADimension)dimension).setWorldTime(nextDayTime);
					TimeSyncNetworkPacket.UpdateTimePacket.sendToClient(nextDayTime);
					
					//removes rain and thunder from overworld so it syncs to ultra amplified dimension too
					if (((World) world).getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE))
					{
						((ServerWorld)world).getServer().getWorld(DimensionType.OVERWORLD).dimension.resetRainAndThunder();
					}
				}
			}
		}

		
		static int timeIncrementMod = 0;
		
		@SubscribeEvent
		/**
		 * Increments the time used for the daytime on client side.
		 * Thus the day cycle appears smoother on the client.
		 */
		public static void IncrementTimeClientSided(ClientTickEvent event)
		{
			@SuppressWarnings("resource")
			World world = Minecraft.getInstance().world;
			if(world != null && !Minecraft.getInstance().isGamePaused() && world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) 
			{
				Dimension dimension = world.dimension;
				if(dimension.getType() == UADimensionRegistration.ultraamplified()) 
				{
					timeIncrementMod++;
					if(timeIncrementMod % 2 == 0)
						((UADimension)dimension).setWorldTimeClientSided(((UADimension)dimension).getWorldTime()+1L);
				}
			}
		}
	}
}
