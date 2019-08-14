package net.TelepathicGrunt.UltraAmplified.World.Feature.Structure;

import java.util.Locale;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class StructureInit {

	public static IStructurePieceType TEDPUA;
	public static IStructurePieceType ECPUA;
	public static IStructurePieceType IGLUUA;
	public static IStructurePieceType TEJPUA;
	public static IStructurePieceType MSCORRIDORUA;
	public static IStructurePieceType MSCROSSINGUA;
	public static IStructurePieceType MSROOMUA;
	public static IStructurePieceType MSSTAIRSUA;
	public static IStructurePieceType ORPUA;
	public static IStructurePieceType SHIPWRECKUA;
	public static IStructurePieceType TESHUA;
	public static IStructurePieceType NEBCRUA;
	public static IStructurePieceType NEBEFUA;
	public static IStructurePieceType NEBSUA;
	public static IStructurePieceType NECCSUA;
	public static IStructurePieceType NECTBUA;
	public static IStructurePieceType NECEUA;
	public static IStructurePieceType NESCSCUA;
	public static IStructurePieceType NESCLTUA;
	public static IStructurePieceType NESCUA;
	public static IStructurePieceType NESCRTUA;
	public static IStructurePieceType NECSRUA;
	public static IStructurePieceType NEMTUA;
	public static IStructurePieceType NERCUA;
	public static IStructurePieceType NESRUA;
	public static IStructurePieceType NESTARTUA;
	public static IStructurePieceType OMBUA;
	public static IStructurePieceType OMCRUA;
	public static IStructurePieceType OMDXRUA;
	public static IStructurePieceType OMDXYRUA;
	public static IStructurePieceType OMDYRUA;
	public static IStructurePieceType OMDYZRUA;
	public static IStructurePieceType OMDZRUA;
	public static IStructurePieceType OMENTRYUA;
	public static IStructurePieceType OMPENTHOUSEUA;
	public static IStructurePieceType OMSIMPLEUA;
	public static IStructurePieceType OMSIMPLETUA;
	public static IStructurePieceType OMWRUA;
	public static IStructurePieceType SHCCUA;
	public static IStructurePieceType SHFCUA;
	public static IStructurePieceType SH5CUA;
	public static IStructurePieceType SHLTUA;
	public static IStructurePieceType SHLIUA;
	public static IStructurePieceType SHPRUA;
	public static IStructurePieceType SHPHUA;
	public static IStructurePieceType SHRTUA;
	public static IStructurePieceType SHRCUA;
	public static IStructurePieceType SHSDUA;
	public static IStructurePieceType SHSTARTUA;
	public static IStructurePieceType SHSUA;
	public static IStructurePieceType SHSSDUA;
	public static IStructurePieceType VISTUA;
	public static IStructurePieceType VIDFUA;
	public static IStructurePieceType VIFUA;
	public static IStructurePieceType VIPHUA;
	public static IStructurePieceType VIBHUA;
	public static IStructurePieceType VISUA;
	public static IStructurePieceType VITRHUA;
	public static IStructurePieceType VISHUA;
	public static IStructurePieceType VISRUA;
	public static IStructurePieceType VIWUA;
	public static IStructurePieceType VISMHUA;
	public static IStructurePieceType VILUA;
	public static IStructurePieceType VISTARTUA;
	
	public static void registerStructurePieces() {
		TEDPUA = register(DesertTemplePiecesUA::new, "TeDPUA");                                 
		ECPUA = register(EndCityPiecesUA.CityTemplate::new, "ECPUA");                           
		IGLUUA = register(IglooPiecesUA.Piece::new, "IgluUA");                                  
		TEJPUA = register(JungleTemplePiecesUA::new, "TeJPUA");                                 
		MSCORRIDORUA = register(MineshaftPiecesUA.Corridor::new, "MSCorridorUA");               
		MSCROSSINGUA = register(MineshaftPiecesUA.Cross::new, "MSCrossingUA");                  
		MSROOMUA = register(MineshaftPiecesUA.Room::new, "MSRoomUA");                           
		MSSTAIRSUA = register(MineshaftPiecesUA.Stairs::new, "MSStairsUA");                     
		ORPUA = register(OceanRuinsPiecesUA.Piece::new, "ORPUA");                               
		SHIPWRECKUA = register(ShipwreckPiecesUA.Piece::new, "ShipwreckUA");                    
		TESHUA = register(WitchHutPiecesUA::new, "TeSHUA");                                     
		NEBCRUA = register(FortressPiecesUA.Crossing3::new, "NeBCrUA");                         
		NEBEFUA = register(FortressPiecesUA.End::new, "NeBEFUA");                               
		NEBSUA = register(FortressPiecesUA.Straight::new, "NeBSUA");                            
		NECCSUA = register(FortressPiecesUA.Corridor3::new, "NeCCSUA");                         
		NECTBUA = register(FortressPiecesUA.Corridor4::new, "NeCTBUA");                         
		NECEUA = register(FortressPiecesUA.Entrance::new, "NeCEUA");                            
		NESCSCUA = register(FortressPiecesUA.Crossing2::new, "NeSCSCUA");                       
		NESCLTUA = register(FortressPiecesUA.Corridor::new, "NeSCLTUA");                        
		NESCUA = register(FortressPiecesUA.Corridor5::new, "NeSCUA");                           
		NESCRTUA = register(FortressPiecesUA.Corridor2::new, "NeSCRTUA");                       
		NECSRUA = register(FortressPiecesUA.NetherStalkRoom::new, "NeCSRUA");                   
		NEMTUA = register(FortressPiecesUA.Throne::new, "NeMTUA");                              
		NERCUA = register(FortressPiecesUA.Crossing::new, "NeRCUA");                            
		NESRUA = register(FortressPiecesUA.Stairs::new, "NeSRUA");                              
		NESTARTUA = register(FortressPiecesUA.Start::new, "NeStartUA");                         
		OMBUA = register(OceanMonumentPiecesUA.MonumentBuilding::new, "OMBUA");                 
		OMCRUA = register(OceanMonumentPiecesUA.MonumentCoreRoom::new, "OMCRUA");               
		OMDXRUA = register(OceanMonumentPiecesUA.DoubleXRoom::new, "OMDXRUA");                  
		OMDXYRUA = register(OceanMonumentPiecesUA.DoubleXYRoom::new, "OMDXYRUA");               
		OMDYRUA = register(OceanMonumentPiecesUA.DoubleYRoom::new, "OMDYRUA");                  
		OMDYZRUA = register(OceanMonumentPiecesUA.DoubleYZRoom::new, "OMDYZRUA");               
		OMDZRUA = register(OceanMonumentPiecesUA.DoubleZRoom::new, "OMDZRUA");                  
		OMENTRYUA = register(OceanMonumentPiecesUA.EntryRoom::new, "OMEntryUA");                
		OMPENTHOUSEUA = register(OceanMonumentPiecesUA.Penthouse::new, "OMPenthouseUA");        
		OMSIMPLEUA = register(OceanMonumentPiecesUA.SimpleRoom::new, "OMSimpleUA");             
		OMSIMPLETUA = register(OceanMonumentPiecesUA.SimpleTopRoom::new, "OMSimpleTUA");        
		OMWRUA = register(OceanMonumentPiecesUA.WingRoom::new, "OMWRUA");                       
		SHCCUA = register(StrongholdPiecesUA.ChestCorridor::new, "SHCCUA");                     
		SHFCUA = register(StrongholdPiecesUA.Corridor::new, "SHFCUA");                          
		SH5CUA = register(StrongholdPiecesUA.Crossing::new, "SH5CUA");                          
		SHLTUA = register(StrongholdPiecesUA.LeftTurn::new, "SHLTUA");                          
		SHLIUA = register(StrongholdPiecesUA.Library::new, "SHLiUA");                           
		SHPRUA = register(StrongholdPiecesUA.PortalRoom::new, "SHPRUA");                        
		SHPHUA = register(StrongholdPiecesUA.Prison::new, "SHPHUA");                            
		SHRTUA = register(StrongholdPiecesUA.RightTurn::new, "SHRTUA");                         
		SHRCUA = register(StrongholdPiecesUA.RoomCrossing::new, "SHRCUA");                      
		SHSDUA = register(StrongholdPiecesUA.Stairs::new, "SHSDUA");                            
		SHSTARTUA = register(StrongholdPiecesUA.Stairs2::new, "SHStartUA");                     
		SHSUA = register(StrongholdPiecesUA.Straight::new, "SHSUA");                            
		SHSSDUA = register(StrongholdPiecesUA.StairsStraight::new, "SHSSDUA");                  
		VISTUA = register(VillagePiecesUA.Church::new, "ViSTUA");                               
		VIDFUA = register(VillagePiecesUA.Field1::new, "ViDFUA");                               
		VIFUA = register(VillagePiecesUA.Field2::new, "ViFUA");                                 
		VIPHUA = register(VillagePiecesUA.WorkHouse::new, "ViPHUA");                            
		VIBHUA = register(VillagePiecesUA.LibraryHouse::new, "ViBHUA");                         
		VISUA = register(VillagePiecesUA.Blacksmith::new, "ViSUA");                             
		VITRHUA = register(VillagePiecesUA.LargeHouse::new, "ViTRHUA");                         
		VISHUA = register(VillagePiecesUA.FlatTopTinyHouse::new, "ViSHUA");                     
		VISRUA = register(VillagePiecesUA.Path::new, "ViSRUA");                                 
		VIWUA = register(VillagePiecesUA.Well::new, "ViWUA");                                   
		VISMHUA = register(VillagePiecesUA.TinyHouse::new, "ViSmHUA");                          
		VILUA = register(VillagePiecesUA.Torch::new, "ViLUA");                                  
		VISTARTUA = register(VillagePiecesUA.Start::new, "ViStartUA");                          
	}
	
	static IStructurePieceType register(IStructurePieceType p_214750_0_, String key) {
      return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), p_214750_0_);
    } 
}
