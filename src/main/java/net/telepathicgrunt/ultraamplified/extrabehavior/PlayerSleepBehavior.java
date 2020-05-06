package net.telepathicgrunt.ultraamplified.extrabehavior;

import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.dimension.TimeSyncNetworkPacket;
import net.telepathicgrunt.ultraamplified.world.dimension.UADimension;
import net.telepathicgrunt.ultraamplified.world.dimension.UADimensionRegistration;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerSleepBehavior
{
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{

		@SubscribeEvent
		/**
		 * When all players slept and finished sleeping in UA dimension, change the time in UA dimension to daytime and remove
		 * weather for both UA and Overworld.
		 */
		public static void ChangeTimeToDay(SleepFinishedTimeEvent event)
		{
			IWorld world = event.getWorld();
			Dimension dimension = world.getDimension();
			if (!world.isRemote() && dimension.getType() == UADimensionRegistration.ultraamplified())
			{
				if (dimension instanceof UADimension)
				{
					if (!dimension.isDaytime() && ((World) world).getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE))
					{
						long time = dimension.getWorldTime();
						long nextDayTime = time + (24000 - time % 24000);
						((UADimension) dimension).setWorldTime(nextDayTime);
						TimeSyncNetworkPacket.UpdateTimePacket.sendToClient(nextDayTime);
					}
					
					//removes rain and thunder from overworld so it syncs to ultra amplified dimension too
					if (((World) world).getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE))
					{
						((ServerWorld) world).getServer().getWorld(DimensionType.OVERWORLD).getDimension().resetRainAndThunder();

					}
				}
				
				//The UA dimension is using the OverworldDimension class which reads time and weather only
				//from the real Overworld Dimension. So we need to get that dimension to change the time/weather in UA dim.
				else
				{
					ServerWorld overworld = world.getWorld().getServer().getWorld(DimensionType.OVERWORLD);
					
					if (!overworld.getDimension().isDaytime() && overworld.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE))
					{
						long timeOfDay = overworld.getDayTime() + 24000L;
						overworld.setDayTime(net.minecraftforge.event.ForgeEventFactory.onSleepFinished(overworld, timeOfDay - timeOfDay % 24000L, overworld.getDayTime()));
					}
					
					if (overworld.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE))
					{
						overworld.getDimension().resetRainAndThunder();
					}
				}
			}
		}
	}
}
