package net.TelepathicGrunt.UltraAmplified.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Proxy;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import jline.internal.Log;
import net.TelepathicGrunt.UltraAmplified.UltraAmplified;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


//shows loading progress on world loading screen
//All that is need is just the existence of this class in any mod lol
public class LoadingScreenProgressPercentage{

	//Very similar to MinecraftServer's initialWorldChunkLoad() method but here we display the progress on screen.
	//Runs at same time as MinecraftServer's initialWorldChunkLoad().
	public static void initialWorldChunkLoad()
    {
        int i = 16;
        int j = 4;
        int k = 192;
        int l = 625;
        int i1 = 0;
        int j1 = 0;
        WorldServer worldserver = net.minecraftforge.common.DimensionManager.getWorld(j1);
        BlockPos blockpos = worldserver.getSpawnPoint();
        MinecraftServer ms = FMLCommonHandler.instance().getMinecraftServerInstance();
        
        long k1 = ms.getCurrentTimeMillis();

        for (int l1 = -192; l1 <= 192 && ms.isServerRunning(); l1 += 16)
        {
            for (int i2 = -192; i2 <= 192 && ms.isServerRunning(); i2 += 16)
            {
                long j2 = ms.getCurrentTimeMillis();

                if (j2 - k1 > 1000L)
                {
                	//this is what gets displayed to the user
                	ms.setUserMessage("Preparing spawn area: " + Integer.valueOf(i1 * 100 / 625)+"%%");
                    k1 = j2;
                	
                	/**
                	 * For debugging purposes
                	 */
            		//Log.warn("Preparing spawn area " + Integer.valueOf(i1 * 100 / 625)+"%");
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
