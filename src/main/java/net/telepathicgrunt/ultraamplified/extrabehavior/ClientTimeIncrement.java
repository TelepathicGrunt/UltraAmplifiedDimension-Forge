package net.telepathicgrunt.ultraamplified.extrabehavior;

import net.minecraft.client.Minecraft;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.telepathicgrunt.ultraamplified.world.dimension.UADimension;
import net.telepathicgrunt.ultraamplified.world.dimension.UADimensionRegistration;


public class ClientTimeIncrement
{
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
			if(dimension instanceof UADimension && dimension.getType() == UADimensionRegistration.ultraamplified()) 
			{
				timeIncrementMod++;
				if(timeIncrementMod % 2 == 0)
					((UADimension)dimension).setWorldTimeClientSided(((UADimension)dimension).getWorldTime()+1L);
			}
		}
	}
}
