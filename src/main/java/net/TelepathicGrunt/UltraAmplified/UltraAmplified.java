package net.TelepathicGrunt.UltraAmplified;

import java.lang.reflect.Method;

import jline.internal.Log;
import net.TelepathicGrunt.UltraAmplified.Utils.LoadingScreenProgressPercentage;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.TelepathicGrunt.UltraAmplified.World.WorldTypes.WorldTypeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.ComponentScatteredFeaturePiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MapGenEndCityUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MapGenNetherBridgeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MapGenScatteredFeatureUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MapGenStrongholdUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MapGenVillageUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.StructureEndCityPiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.StructureMineshaftPiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.StructureMineshaftStartUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.StructureNetherBridgePiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.StructureOceanMonumentPiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.StructureOceanMonumentUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.StructureStrongholdPiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.StructureVillagePiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.WoodlandMansionPiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.WoodlandMansionUA;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod(modid = UltraAmplified.MOD_ID, name = UltraAmplified.MOD_NAME, version = UltraAmplified.VERSION)
public class UltraAmplified {

	//constants
	public static final String MOD_ID = "ultra_amplified_mod";
	public static final String MOD_NAME = "Ultra Amplified Mod";
	
	//Change mod version here, in mcmod.info, in UAConfig, and in build.gradlew. I probably should automate this lol.
	public static final String VERSION = "0.4.4";
	
	public static WorldType UltraAmplified;
	public static WorldType UltraAmplifiedLargeBiome;
	
	@Instance(MOD_ID)
	public static UltraAmplified instance;
	
	@SidedProxy(clientSide = "net.TelepathicGrunt.UltraAmplified.ClientProxy", serverSide = "net.TelepathicGrunt.UltraAmplified.CommonProxy")
	public static CommonProxy proxy;
	
	
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//registers all the biomes so forge knows about them
		 BiomeInit.registerBiomes();
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		
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
		UltraAmplified = new WorldTypeUA();
    }
	
	
}
