package com.TelepathicGrunt.UltraAmplified;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.TelepathicGrunt.UltraAmplified.World.WorldTypes.WorldTypeUA;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UltraAmplified.modid)
public class UltraAmplified {
	
	public static UltraAmplified instance;
	public static final String modid = "ultra_amplified_mod";
	public static final Logger Logger = LogManager.getLogger(modid);
	
	//worldTypes
	public static WorldType UltraAmplified;
	
	public UltraAmplified() {
		instance = this;
		
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        modEventBus.addListener(this::setup);

		//generates config
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void registerBiomes(RegistryEvent.Register<Biome> event){
			//registers all my modified biomes
			BiomeInit.registerBiomes(event);
			
			Logger.info("Biomes registered.");
		}
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		
		//registers the worldtype used for this mod so we can select that worldtype
		UltraAmplified = new WorldTypeUA();
		
	}
	
}
