package net.telepathicgrunt.ultraamplified;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.telepathicgrunt.ultraamplified.Blocks.BlocksAndItemsInit;
import net.telepathicgrunt.ultraamplified.Config.UAConfig;
import net.telepathicgrunt.ultraamplified.World.Biome.BiomeInit;
import net.telepathicgrunt.ultraamplified.World.WorldTypes.WorldTypeUA;
import net.telepathicgrunt.ultraamplified.World.dimension.UltraAmplifiedWorldProvider;
import net.telepathicgrunt.ultraamplified.World.gen.structure.ComponentScatteredFeaturePiecesUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.MapGenEndCityUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.MapGenNetherBridgeUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.MapGenScatteredFeatureUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.MapGenStrongholdUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.MapGenVillageUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.StructureEndCityPiecesUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.StructureMineshaftPiecesUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.StructureMineshaftStartUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.StructureNetherBridgePiecesUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.StructureOceanMonumentPiecesUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.StructureOceanMonumentUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.StructureStrongholdPiecesUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.StructureVillagePiecesUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.WoodlandMansionPiecesUA;
import net.telepathicgrunt.ultraamplified.World.gen.structure.WoodlandMansionUA;
import net.telepathicgrunt.ultraamplified.capabilities.CapabilityPlayerPosAndDim;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = UltraAmplified.MOD_ID, name = UltraAmplified.MOD_NAME, version = UltraAmplified.VERSION)
public class UltraAmplified {

	//constants
	public static final String MOD_ID = "ultra_amplified_mod";
	public static final String MOD_NAME = "Ultra Amplified Mod";
	
	//Change mod version here, in mcmod.info, in UAConfig, and in build.gradlew. I probably should automate this lol.
	public static final String VERSION = "4.6.1";
	
	public static WorldType UltraAmplifiedWorldType;
	
	@Instance(MOD_ID)
	public static UltraAmplified instance;
	
	public static final Logger logger = LogManager.getLogger(MOD_ID);
	
	public static DimensionType UADimType;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//registers all the biomes so forge knows about them
		 BiomeInit.registerBiomes();
		 
		 //create dimension and register it
		 UADimType = DimensionType.register(MOD_ID, "_ultraamplified", UAConfig.dimensionOptions.dimensionID, UltraAmplifiedWorldProvider.class, false);
	     DimensionManager.registerDimension(UAConfig.dimensionOptions.dimensionID, UADimType);
	}

	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		
		//registers player cap
		CapabilityPlayerPosAndDim.register();
		
		
		//registers all the large structures that this mod will need
		
		MapGenStructureIO.registerStructure(MapGenVillageUA.Start.class, MOD_ID+":village");
		StructureVillagePiecesUA.registerVillagePieces();
		
		MapGenStructureIO.registerStructure(MapGenEndCityUA.Start.class, MOD_ID+":end_city");
		StructureEndCityPiecesUA.registerPieces();
		
		MapGenStructureIO.registerStructure(StructureMineshaftStartUA.class, MOD_ID+":mineshaft");
		StructureMineshaftPiecesUA.registerStructurePieces();
		
		MapGenStructureIO.registerStructure(MapGenNetherBridgeUA.Start.class, MOD_ID+":fortress");
		StructureNetherBridgePiecesUA.registerNetherFortressPieces();
		
		MapGenStructureIO.registerStructure(MapGenScatteredFeatureUA.Start.class, MOD_ID+":scattered_feature");
		ComponentScatteredFeaturePiecesUA.registerScatteredFeaturePieces();
		
		MapGenStructureIO.registerStructure(MapGenStrongholdUA.Start.class, MOD_ID+":stronghold");
		StructureStrongholdPiecesUA.registerStrongholdPieces();
		
		MapGenStructureIO.registerStructure(StructureOceanMonumentUA.StartMonument.class, MOD_ID+":monument");
		StructureOceanMonumentPiecesUA.registerOceanMonumentPieces();

		MapGenStructureIO.registerStructure(WoodlandMansionUA.Start.class, MOD_ID+":mansion");
		WoodlandMansionPiecesUA.registerMansionPieces();
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//nothing needed here
	}
	
	
	@EventHandler
    public void post(FMLPostInitializationEvent event)
    {
		//registers the worldtype used for this mod so we can select that worldtype
		UltraAmplifiedWorldType = new WorldTypeUA();
    }

	
	@Mod.EventBusSubscriber(modid = MOD_ID)
	public static class RegistryEvents
	{
		
		/**
		 * This method will be called by Forge when it is time for the mod to register its Blocks.
		 * This method will always be called before the Item registry method.
		 */
		@SubscribeEvent
		public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
			BlocksAndItemsInit.registerBlocks(event);
			logger.log(Level.INFO, "Blocks registered.");
		}
		
		/**
		 * This method will be called by Forge when it is time for the mod to register its Items.
		 * This method will always be called after the Block registry method.
		 */
		@SubscribeEvent
		public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
			BlocksAndItemsInit.registerItems(event);
			logger.log(Level.INFO, "Items registered.");
		}
		
		
		@SubscribeEvent
		public static void registerRenders(ModelRegistryEvent event) {
		  registerRender(Item.getItemFromBlock(BlocksAndItemsInit.AMPLIFIEDPORTAL));
		}
	}
	
    public static void registerRender(Item item) {
	  ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
	}
}
