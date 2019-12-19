package net.TelepathicGrunt.UltraAmplified.Utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


//shows loading progress on world loading screen
//All that is need is just the existence of this class in any mod lol
public class LoadingScreenProgressPercentage{

	//Very similar to MinecraftServer's initialWorldChunkLoad() method but here we display the progress on screen.
	//Runs at same time as MinecraftServer's initialWorldChunkLoad().
	public static void initialWorldChunkLoad()
    {
        int i1 = 0;
        int j1 = 0;
        WorldServer worldserver = net.minecraftforge.common.DimensionManager.getWorld(j1);
        BlockPos blockpos = worldserver.getSpawnPoint();
        MinecraftServer ms = FMLCommonHandler.instance().getMinecraftServerInstance();
        
        long k1 = MinecraftServer.getCurrentTimeMillis();

        for (int l1 = -192; l1 <= 192 && ms.isServerRunning(); l1 += 16)
        {
            for (int i2 = -192; i2 <= 192 && ms.isServerRunning(); i2 += 16)
            {
                long j2 = MinecraftServer.getCurrentTimeMillis();

                if (j2 - k1 > 1000L)
                {
                	//this is what gets displayed to the user
                	ms.setUserMessage("Preparing spawn area: " + Integer.valueOf(i1 * 100 / 625)+"%%");
                    k1 = j2;
                	
                	/**
                	 * For debugging purposes
                	 */
            		//UltraAmplified.logger.warn("Preparing spawn area " + Integer.valueOf(i1 * 100 / 625)+"%");
                }

                ++i1;
                worldserver.getChunkProvider().provideChunk(blockpos.getX() + l1 >> 4, blockpos.getZ() + i2 >> 4);
            }
        }
    }

	 
	//Runs this class' initialWorldChunkLoad() method just before MinecraftServer's.
	@Mod.EventBusSubscriber
	private static class EventHandler {

		@SubscribeEvent
		public static void Load(WorldEvent.Load event) {
			 initialWorldChunkLoad();
		}
	}
}
