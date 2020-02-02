package net.telepathicgrunt.ultraamplified;


import java.nio.file.Paths;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.server.dedicated.ServerProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.telepathicgrunt.ultraamplified.blocks.BlockColorManager;
import net.telepathicgrunt.ultraamplified.blocks.BlocksInit;
import net.telepathicgrunt.ultraamplified.capabilities.CapabilityPlayerPosAndDim;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;
import net.telepathicgrunt.ultraamplified.world.feature.ContainUndergroundLiquids;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;
import net.telepathicgrunt.ultraamplified.world.feature.GlowPatch;
import net.telepathicgrunt.ultraamplified.world.feature.carver.CaveCavityCarver;
import net.telepathicgrunt.ultraamplified.world.feature.carver.RavineCarver;
import net.telepathicgrunt.ultraamplified.world.feature.carver.SuperLongRavineCarver;
import net.telepathicgrunt.ultraamplified.world.feature.carver.UnderwaterCaveCarver;
import net.telepathicgrunt.ultraamplified.world.worldtypes.WorldTypeUA;

/*
 * Mod for making insane landscape and a more challenging world to survive on!
 * If you have any questions, advice, or suggestions for this mod, reach out to me on Reddit! (my name is TelepathicGrunt on there as well) 
 * 
 * @author TelepathicGrunt
 */
@Mod(UltraAmplified.MODID)
public class UltraAmplified {
	
	public static final String MODID = "ultra_amplified_dimension";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	//worldTypes
	public static WorldType UltraAmplifiedWorldType;
	
	public UltraAmplified() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::modConfig);
        modEventBus.addListener(this::dedicatedServerSetup);
        modEventBus.register(new BlockColorManager());
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ClientEvents.subscribeClientEvents(modEventBus, forgeBus)); //client side only for glowgrass color

		//generates config
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigUA.SERVER_SPEC);
        
	}
	

	/*
	 * Hacky workaround similar to what Biome O' Plenty did to get around server.properties being read and set too early before
	 * the mod even loads at all. It's so early that the mod's worldtypes aren't added to WORLD_TYPES array and so it'll
	 * change level-type to default regardless of what the user added.
	 * 
	 * My solution is to tell users to add a new entry called ultra-amplifed-overworld=true and then read that property instead. 
	 */
    public void dedicatedServerSetup(FMLDedicatedServerSetupEvent event)
    {
        if(PropertyManager.load(Paths.get("server.properties")).getProperty("ultra-amplifed-overworld").equals("true")) 
        {
            ServerProperties serverProperties = event.getServerSupplier().get().getServerProperties();
            serverProperties.worldType = UltraAmplifiedWorldType;
        }
    }
	
	public void setup(final FMLCommonSetupEvent event) 
	{
		//registers the worldtype used for this mod so we can select that worldtype
		UltraAmplifiedWorldType = new WorldTypeUA();
		
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
	
	public void modConfig(final ModConfig.ModConfigEvent event)
    {
        ModConfig config = event.getConfig();
        if (config.getSpec() == ConfigUA.SERVER_SPEC)
            ConfigUA.refreshServer();
    }
	
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void registerBiomes(final RegistryEvent.Register<Biome> event){
			//registers all my modified biomes
			BiomeInit.registerBiomes(event);
			LOGGER.log(Level.INFO, "Biomes registered.");
		}
		
		/**
		 * This method will be called by Forge when it is time for the mod to register its Blocks.
		 * This method will always be called before the Item registry method.
		 */
		@SubscribeEvent
		public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
			BlocksInit.registerBlocks(event);
			LOGGER.log(Level.INFO, "Blocks registered.");
		}
		
		/**
		 * This method will be called by Forge when it is time for the mod to register its Items.
		 * This method will always be called after the Block registry method.
		 */
		@SubscribeEvent
		public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
			BlocksInit.registerItems(event);
			LOGGER.log(Level.INFO, "Items registered.");
		}

		/**
		 * This method will be called by Forge when it is time for the mod to register features.
		 */
		@SubscribeEvent
		public static void onRegisterFeatures(final RegistryEvent.Register<Feature<?>> event) {
			FeatureUA.registerFeatures(event);
			LOGGER.log(Level.INFO, "features registered.");
		}

	}
	
	/*
	 * Helper method to quickly register features, blocks, items, structures, biomes, anything that can be registered.
	 */
	public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey)
	{
		entry.setRegistryName(new ResourceLocation(UltraAmplified.MODID, registryKey.toLowerCase().replace(' ', '_')));
		registry.register(entry);
		return entry;
	}
}