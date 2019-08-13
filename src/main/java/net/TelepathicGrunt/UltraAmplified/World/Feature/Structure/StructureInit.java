package net.TelepathicGrunt.UltraAmplified.World.Feature.Structure;

import java.util.Locale;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

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
	public static final IStructurePieceType NEBCRUA = register(FortressPiecesUA.Crossing3::new, "NeBCrUA");
	public static final IStructurePieceType NEBEFUA = register(FortressPiecesUA.End::new, "NeBEFUA");
	public static final IStructurePieceType NEBSUA = register(FortressPiecesUA.Straight::new, "NeBSUA");
	public static final IStructurePieceType NECCSUA = register(FortressPiecesUA.Corridor3::new, "NeCCSUA");
	public static final IStructurePieceType NECTBUA = register(FortressPiecesUA.Corridor4::new, "NeCTBUA");
	public static final IStructurePieceType NECEUA = register(FortressPiecesUA.Entrance::new, "NeCEUA");
	public static final IStructurePieceType NESCSCUA = register(FortressPiecesUA.Crossing2::new, "NeSCSCUA");
	public static final IStructurePieceType NESCLTUA = register(FortressPiecesUA.Corridor::new, "NeSCLTUA");
	public static final IStructurePieceType NESCUA = register(FortressPiecesUA.Corridor5::new, "NeSCUA");
	public static final IStructurePieceType NESCRTUA = register(FortressPiecesUA.Corridor2::new, "NeSCRTUA");
	public static final IStructurePieceType NECSRUA = register(FortressPiecesUA.NetherStalkRoom::new, "NeCSRUA");
	public static final IStructurePieceType NEMTUA = register(FortressPiecesUA.Throne::new, "NeMTUA");
	public static final IStructurePieceType NERCUA = register(FortressPiecesUA.Crossing::new, "NeRCUA");
	public static final IStructurePieceType NESRUA = register(FortressPiecesUA.Stairs::new, "NeSRUA");
	public static final IStructurePieceType NESTARTUA = register(FortressPiecesUA.Start::new, "NeStartUA");
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
	public static final IStructurePieceType VIPHUA = register(VillagePiecesUA.WorkHouse::new, "ViPHUA");
	public static final IStructurePieceType VIBHUA = register(VillagePiecesUA.LibraryHouse::new, "ViBHUA");
	public static final IStructurePieceType VISUA = register(VillagePiecesUA.Blacksmith::new, "ViSUA");
	public static final IStructurePieceType VITRHUA = register(VillagePiecesUA.LargeHouse::new, "ViTRHUA");
	public static final IStructurePieceType VISHUA = register(VillagePiecesUA.FlatTopTinyHouse::new, "ViSHUA");
	public static final IStructurePieceType VISRUA = register(VillagePiecesUA.Path::new, "ViSRUA");
	public static final IStructurePieceType VIWUA = register(VillagePiecesUA.Well::new, "ViWUA");
	public static final IStructurePieceType VISMHUA = register(VillagePiecesUA.TinyHouse::new, "ViSmHUA");
	public static final IStructurePieceType VILUA = register(VillagePiecesUA.Torch::new, "ViLUA");
	public static final IStructurePieceType VISTARTUA = register(VillagePiecesUA.Start::new, "ViStartUA");
	
	static IStructurePieceType register(IStructurePieceType p_214750_0_, String key) {
      return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), p_214750_0_);
    } 
}
