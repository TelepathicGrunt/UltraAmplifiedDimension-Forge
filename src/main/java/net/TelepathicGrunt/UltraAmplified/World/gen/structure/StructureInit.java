package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Locale;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.SwampHutPiece;

public class StructureInit {

	public static final IStructurePieceType TEDPUA = register(DesertTemplePiecesUA::new, "TeDPUA");
	public static final IStructurePieceType ECPUA = register(EndCityPiecesUA.CityTemplate::new, "ECPUA");
	public static final IStructurePieceType IGLUUA = register(IglooPiecesUA.Piece::new, "IgluUA");
	public static final IStructurePieceType TEJPUA = register(JungleTemplePiecesUA::new, "TeJPUA");
	public static final IStructurePieceType MSCORRIDORUA = register(MineshaftPiecesUA.Corridor::new, "MSCorridorUA");
	public static final IStructurePieceType MSCROSSINGUA = register(MineshaftPiecesUA.Cross::new, "MSCrossingUA");
	public static final IStructurePieceType MSROOMUA = register(MineshaftPiecesUA.Room::new, "MSRoomUA");
	public static final IStructurePieceType MSSTAIRSUA = register(MineshaftPiecesUA.Stairs::new, "MSStairsUA");
	public static final IStructurePieceType ORPUA = register(OceanRuinsPiecesUA.Piece::new, "ORPUA");
	public static final IStructurePieceType SHIPWRECKUA = register(ShipwreckPiecesUA.Piece::new, "ShipwreckUA");
	public static final IStructurePieceType TESHUA = register(WitchHutPiecesUA::new, "TeSHUA");
	public static final IStructurePieceType NEBCRUA = register(NetherBridgePiecesUA.Crossing3::new, "NeBCrUA");
	public static final IStructurePieceType NEBEFUA = register(NetherBridgePiecesUA.End::new, "NeBEFUA");
	public static final IStructurePieceType NEBSUA = register(NetherBridgePiecesUA.Straight::new, "NeBSUA");
	public static final IStructurePieceType NECCSUA = register(NetherBridgePiecesUA.Corridor3::new, "NeCCSUA");
	public static final IStructurePieceType NECTBUA = register(NetherBridgePiecesUA.Corridor4::new, "NeCTBUA");
	public static final IStructurePieceType NECEUA = register(NetherBridgePiecesUA.Entrance::new, "NeCEUA");
	public static final IStructurePieceType NESCSCUA = register(NetherBridgePiecesUA.Crossing2::new, "NeSCSCUA");
	public static final IStructurePieceType NESCLTUA = register(NetherBridgePiecesUA.Corridor::new, "NeSCLTUA");
	public static final IStructurePieceType NESCUA = register(NetherBridgePiecesUA.Corridor5::new, "NeSCUA");
	public static final IStructurePieceType NESCRTUA = register(NetherBridgePiecesUA.Corridor2::new, "NeSCRTUA");
	public static final IStructurePieceType NECSRUA = register(NetherBridgePiecesUA.NetherStalkRoom::new, "NeCSRUA");
	public static final IStructurePieceType NEMTUA = register(NetherBridgePiecesUA.Throne::new, "NeMTUA");
	public static final IStructurePieceType NERCUA = register(NetherBridgePiecesUA.Crossing::new, "NeRCUA");
	public static final IStructurePieceType NESRUA = register(NetherBridgePiecesUA.Stairs::new, "NeSRUA");
	public static final IStructurePieceType NESTARTUA = register(NetherBridgePiecesUA.Start::new, "NeStartUA");
	public static final IStructurePieceType OMBUA = register(OceanMonumentPiecesUA.MonumentBuilding::new, "OMBUA");
	public static final IStructurePieceType OMCRUA = register(OceanMonumentPiecesUA.MonumentCoreRoom::new, "OMCRUA");
	public static final IStructurePieceType OMDXRUA = register(OceanMonumentPiecesUA.DoubleXRoom::new, "OMDXRUA");
	public static final IStructurePieceType OMDXYRUA = register(OceanMonumentPiecesUA.DoubleXYRoom::new, "OMDXYRUA");
	public static final IStructurePieceType OMDYRUA = register(OceanMonumentPiecesUA.DoubleYRoom::new, "OMDYRUA");
	public static final IStructurePieceType OMDYZRUA = register(OceanMonumentPiecesUA.DoubleYZRoom::new, "OMDYZRUA");
	public static final IStructurePieceType OMDZRUA = register(OceanMonumentPiecesUA.DoubleZRoom::new, "OMDZRUA");
	public static final IStructurePieceType OMENTRYUA = register(OceanMonumentPiecesUA.EntryRoom::new, "OMEntryUA");
	public static final IStructurePieceType OMPENTHOUSEUA = register(OceanMonumentPiecesUA.Penthouse::new, "OMPenthouseUA");
	public static final IStructurePieceType OMSIMPLEUA = register(OceanMonumentPiecesUA.SimpleRoom::new, "OMSimpleUA");
	public static final IStructurePieceType OMSIMPLETUA = register(OceanMonumentPiecesUA.SimpleTopRoom::new, "OMSimpleTUA");
	public static final IStructurePieceType OMWRUA = register(OceanMonumentPiecesUA.WingRoom::new, "OMWRUA");
	public static final IStructurePieceType SHCCUA = register(StrongholdPiecesUA.ChestCorridor::new, "SHCCUA");
	public static final IStructurePieceType SHFCUA = register(StrongholdPiecesUA.Corridor::new, "SHFCUA");
	public static final IStructurePieceType SH5CUA = register(StrongholdPiecesUA.Crossing::new, "SH5CUA");
	public static final IStructurePieceType SHLTUA = register(StrongholdPiecesUA.LeftTurn::new, "SHLTUA");
	public static final IStructurePieceType SHLIUA = register(StrongholdPiecesUA.Library::new, "SHLiUA");
	public static final IStructurePieceType SHPRUA = register(StrongholdPiecesUA.PortalRoom::new, "SHPRUA");
	public static final IStructurePieceType SHPHUA = register(StrongholdPiecesUA.Prison::new, "SHPHUA");
	public static final IStructurePieceType SHRTUA = register(StrongholdPiecesUA.RightTurn::new, "SHRTUA");
	public static final IStructurePieceType SHRCUA = register(StrongholdPiecesUA.RoomCrossing::new, "SHRCUA");
	public static final IStructurePieceType SHSDUA = register(StrongholdPiecesUA.Stairs::new, "SHSDUA");
	public static final IStructurePieceType SHSTARTUA = register(StrongholdPiecesUA.Stairs2::new, "SHStartUA");
	public static final IStructurePieceType SHSUA = register(StrongholdPiecesUA.Straight::new, "SHSUA");
	public static final IStructurePieceType SHSSDUA = register(StrongholdPiecesUA.StairsStraight::new, "SHSSDUA");
	public static final IStructurePieceType VISTUA = register(VillagePiecesUA.Church::new, "ViSTUA");
	public static final IStructurePieceType VIDFUA = register(VillagePiecesUA.Field1::new, "ViDFUA");
	public static final IStructurePieceType VIFUA = register(VillagePiecesUA.Field2::new, "ViFUA");
	public static final IStructurePieceType VIPHUA = register(VillagePiecesUA.Hall::new, "ViPHUA");
	public static final IStructurePieceType VIBHUA = register(VillagePiecesUA.House1::new, "ViBHUA");
	public static final IStructurePieceType VISUA = register(VillagePiecesUA.House2::new, "ViSUA");
	public static final IStructurePieceType VITRHUA = register(VillagePiecesUA.House3::new, "ViTRHUA");
	public static final IStructurePieceType VISHUA = register(VillagePiecesUA.House4Garden::new, "ViSHUA");
	public static final IStructurePieceType VISRUA = register(VillagePiecesUA.Path::new, "ViSRUA");
	public static final IStructurePieceType VIWUA = register(VillagePiecesUA.Well::new, "ViWUA");
	public static final IStructurePieceType VISMHUA = register(VillagePiecesUA.WoodHut::new, "ViSmHUA");
	public static final IStructurePieceType VILUA = register(VillagePiecesUA.Torch::new, "ViLUA");
	public static final IStructurePieceType VISTARTUA = register(VillagePiecesUA.Start::new, "ViStartUA");
	
	static IStructurePieceType register(IStructurePieceType p_214750_0_, String key) {
      return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), p_214750_0_);
    } 
}
