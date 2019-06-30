package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.lang.reflect.Method;

import net.minecraft.world.gen.feature.structure.StructureIO;

public class StructureInit {

	static Method registerStructure;
	
	public static void initializeStructures() {
			StructureIO.registerStructure(MineshaftUA.Start.class, "Mineshaft UA");
			StructureIO.registerStructure(EndCityUA.Start.class, "End City UA");
			StructureIO.registerStructure(NetherBridgeUA.Start.class, "Nether Fortress UA");
			StructureIO.registerStructure(WoodlandMansionUA.Start.class, "Woodland Mansion UA"); // Calls vanilla pieces so we don't need to register pieces here
			StructureIO.registerStructure(StrongholdUA.Start.class, "Stronghold UA");
			StructureIO.registerStructure(IglooUA.Start.class, "Igloo UA");
			StructureIO.registerStructure(DesertTempleUA.Start.class, "Desert Temple UA");
			StructureIO.registerStructure(JungleTempleUA.Start.class, "Jungle Temple UA");
			StructureIO.registerStructure(WitchHutUA.Start.class, "Witch Hut UA");
			StructureIO.registerStructure(VillageUA.Start.class, "Village UA");
			MineshaftPiecesUA.registerStructurePieces();
			EndCityPiecesUA.registerPieces();
			NetherBridgePiecesUA.registerStructurePieces();
			StrongholdPiecesUA.registerStrongholdPieces();
			IglooPiecesUA.registerPieces();
			DesertTemplePiecesUA.registerPieces();
			JungleTemplePiecesUA.registerJunglePyramidPieces();
			WitchHutPiecesUA.registerPieces();
			VillagePiecesUA.registerVillagePieces();
	}
}
