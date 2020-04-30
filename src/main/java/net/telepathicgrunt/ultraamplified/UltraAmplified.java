package net.telepathicgrunt.ultraamplified;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.telepathicgrunt.ultraamplified.blocks.BlockColorManager;
import net.telepathicgrunt.ultraamplified.blocks.UABlocks;
import net.telepathicgrunt.ultraamplified.capabilities.CapabilityPlayerPosAndDim;
import net.telepathicgrunt.ultraamplified.config.UAConfig;
import net.telepathicgrunt.ultraamplified.config.UAConfig.UAConfigValues;
import net.telepathicgrunt.ultraamplified.utils.ConfigHelper;
import net.telepathicgrunt.ultraamplified.world.biome.UABiomes;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.UASurfaceBuilders;
import net.telepathicgrunt.ultraamplified.world.dimension.TimeSyncNetworkPacket;
import net.telepathicgrunt.ultraamplified.world.feature.ContainUndergroundLiquids;
import net.telepathicgrunt.ultraamplified.world.feature.GlowPatch;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;
import net.telepathicgrunt.ultraamplified.world.feature.carver.CaveCavityCarver;
import net.telepathicgrunt.ultraamplified.world.feature.carver.RavineCarver;
import net.telepathicgrunt.ultraamplified.world.feature.carver.SuperLongRavineCarver;
import net.telepathicgrunt.ultraamplified.world.feature.carver.UACarvers;
import net.telepathicgrunt.ultraamplified.world.feature.carver.UnderwaterCaveCarver;
import net.telepathicgrunt.ultraamplified.world.feature.placement.UAPlacements;
import net.telepathicgrunt.ultraamplified.world.worldtypes.UAWorldType;


/*
 * Mod for making insane landscape and a more challenging world to survive on! If you have any questions, advice, or
 * suggestions for this mod, reach out to me on Reddit or CurseForge! (my name is TelepathicGrunt on there as well)
 * 
 * @author TelepathicGrunt
 */
@Mod(UltraAmplified.MODID)
public class UltraAmplified
{

	public static final String MODID = "ultra_amplified_dimension";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static UAConfigValues UAConfig = null;

	//worldTypes
	public static WorldType UltraAmplifiedWorldType;

	//TODO:: fix spawn being made with wrong biomes before reading config
	//TODO:: redo biome picking lists
	public UltraAmplified()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		modEventBus.addListener(this::setup);
		modEventBus.register(new BlockColorManager());
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ClientEvents.subscribeClientEvents(modEventBus, forgeBus)); //client side only for glowgrass color

		//generates config
		UAConfig = ConfigHelper.register(ModConfig.Type.SERVER, (builder, subscriber) -> new UAConfig.UAConfigValues(builder, subscriber));
	}


	public void setup(final FMLCommonSetupEvent event)
	{
		TimeSyncNetworkPacket.init();
		
		//registers the worldtype used for this mod so we can select that worldtype
		UltraAmplifiedWorldType = new UAWorldType();

		CapabilityPlayerPosAndDim.register();
		RavineCarver.setFillerMap();
		SuperLongRavineCarver.setFillerMap();
		SuperLongRavineCarver.setLavaFloorMap();
		UnderwaterCaveCarver.setFillerMap();
		CaveCavityCarver.setFillerMap();
		CaveCavityCarver.setLavaFloorMap();
		GlowPatch.setFillerMap();
		ContainUndergroundLiquids.setFillerMap();
	}


	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void registerBiomes(final RegistryEvent.Register<Biome> event)
		{
			UABiomes.registerBiomes(event);
		}


		/**
		 * This method will be called by Forge when it is time for the mod to register its Blocks. This method will always be
		 * called before the Item registry method.
		 */
		@SubscribeEvent
		public static void onRegisterBlocks(final RegistryEvent.Register<Block> event)
		{
			UABlocks.registerBlocks(event);
		}


		/**
		 * This method will be called by Forge when it is time for the mod to register its Items. This method will always be
		 * called after the Block registry method.
		 */
		@SubscribeEvent
		public static void onRegisterItems(final RegistryEvent.Register<Item> event)
		{
			UABlocks.registerBlockItems(event);
		}


		/**
		 * This method will be called by Forge when it is time for the mod to register features.
		 */
		@SubscribeEvent
		public static void onRegisterFeatures(final RegistryEvent.Register<Feature<?>> event)
		{
			UAFeatures.registerFeatures(event);
		}

		/**
		 * This method will be called by Forge when it is time for the mod to register placement.
		 */
		@SubscribeEvent
		public static void onRegisterPlacements(final RegistryEvent.Register<Placement<?>> event)
		{
			UAPlacements.registerPlacements(event);
		}


		/**
		 * This method will be called by Forge when it is time for the mod to register surface builders.
		 */
		@SubscribeEvent
		public static void onRegisterSurfacebuilders(final RegistryEvent.Register<SurfaceBuilder<?>> event)
		{
			UASurfaceBuilders.registerSurfaceBuilders(event);
		}

		
		/**
		 * This method will be called by Forge when it is time for the mod to register carvers.
		 */
		@SubscribeEvent
		public static void onRegisterCarvers(final RegistryEvent.Register<WorldCarver<?>> event)
		{
			UACarvers.registerCarvers(event);
		}
	}
}