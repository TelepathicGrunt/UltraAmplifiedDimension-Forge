package net.telepathicgrunt.ultraamplified.extrabehavior;

import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.dimension.UADimensionRegistration;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherPortalSpawning
{
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{
		
		@SubscribeEvent
		/**
		 * Allow Nether Portal to spawn in UA dimension
		 */
		public static void spawnNetherPortal(BlockEvent.EntityPlaceEvent event)
		{
			if(UltraAmplified.UADimensionConfig.allowNetherPortal.get()) 
			{
				if (!event.getWorld().isRemote() && event.getWorld().getDimension().getType() == UADimensionRegistration.ultraamplified() && event.getPlacedBlock().getBlock() == Blocks.FIRE)
				{
					if(((NetherPortalBlock)Blocks.NETHER_PORTAL).trySpawnPortal(event.getWorld(), event.getPos())) {
						event.setResult(Result.DENY);
					}
				}
			}
		}
	}
}
