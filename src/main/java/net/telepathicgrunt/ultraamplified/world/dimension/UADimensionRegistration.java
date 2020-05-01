package net.telepathicgrunt.ultraamplified.world.dimension;

import java.util.function.BiFunction;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerPropertiesProvider;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.utils.RegUtil;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UADimensionRegistration
{
	private static final ResourceLocation ULTRAAMPLIFIED_ID = new ResourceLocation(UltraAmplified.MODID, "ultraamplified");

	public static DimensionType ultraamplified()
	{
		return DimensionType.byName(ULTRAAMPLIFIED_ID);
	}
	
	/**
	 * Default UA dimension for all non UA worldtypes
	 */
	public static final ModDimension ULTRAAMPLIFIED = new ModDimension()
	{
		@Override
		public BiFunction<World, DimensionType, ? extends Dimension> getFactory()
		{
			return UADimension::new;
		}
	};
	
	/**
	 * Make UA dimension be a copy of the Overworld instead in UA worldtype.
	 */
	public static final ModDimension OVERWORLD_ULTRAAMPLIFIED = new ModDimension()
	{
		@Override
		public BiFunction<World, DimensionType, ? extends Dimension> getFactory()
		{
			return UAOverworldDimension::new;
		}
	};

	//registers the dimension
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{
		private static MinecraftServer server;

		@SubscribeEvent
		public static void registerDimensions(FMLServerAboutToStartEvent event)
		{
			server = event.getServer();
		}


		@SuppressWarnings("deprecation")
		@SubscribeEvent
		public static void registerDimensions(RegisterDimensionsEvent event)
		{
			ModDimension dimension = ULTRAAMPLIFIED;
			
			if (server != null)
			{
				WorldType worldtype = null; 
				if(server instanceof DedicatedServer) 
				{
					ServerPropertiesProvider serverPropertiesProvider = ObfuscationReflectionHelper.getPrivateValue(DedicatedServer.class, (DedicatedServer)server, "field_71340_o");
					worldtype = serverPropertiesProvider.getProperties().worldType;
				}
				else if(server instanceof IntegratedServer) 
				{
					WorldSettings worldSettings = ObfuscationReflectionHelper.getPrivateValue(IntegratedServer.class, (IntegratedServer)server, "field_71350_m");
					worldtype = worldSettings.getTerrainType();
				}
				
				
				if(worldtype == UltraAmplified.UltraAmplifiedWorldType) 
				{
					dimension = OVERWORLD_ULTRAAMPLIFIED;
				}
			}

			if(DimensionManager.getRegistry().getValue(ULTRAAMPLIFIED_ID).isPresent()) 
			{
				int id = DimensionManager.getRegistry().getId(DimensionType.byName(ULTRAAMPLIFIED_ID));
				
				DimensionManager.getRegistry().register(id, ULTRAAMPLIFIED_ID, 
						new DimensionType(id, "", ULTRAAMPLIFIED_ID.getNamespace() + "/" + ULTRAAMPLIFIED_ID.getPath(), 
								dimension.getFactory(), true, dimension.getMagnifier(), dimension, null));
			}
			else 
			{
				DimensionManager.registerDimension(ULTRAAMPLIFIED_ID, dimension, null, true);
			}
		}
	}

	@SubscribeEvent
	public static void registerModDimensions(RegistryEvent.Register<ModDimension> event)
	{
		RegUtil.generic(event.getRegistry()).add("ultraamplified", ULTRAAMPLIFIED);
		RegUtil.generic(event.getRegistry()).add("overworld_ultraamplified", OVERWORLD_ULTRAAMPLIFIED);
	}

}
