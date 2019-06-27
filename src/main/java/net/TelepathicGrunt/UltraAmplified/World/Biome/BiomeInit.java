package net.TelepathicGrunt.UltraAmplified.World.Biome;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.TelepathicGrunt.UltraAmplified.World.Biomes.BadlandsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BadlandsPlateauBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BirchForestBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.DarkForestBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.DarkForestHillsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.DesertBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.DesertHillsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.DesertLakesBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.EndHighlandsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.ErodedBadlandsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.FlowerForestBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.ForestBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.GiantSpruceTaigaBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.GiantSpruceTaigaHillsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.GiantTreeTaigaBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.GiantTreeTaigaHillsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.GravellyMountainsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.IceMountainsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.IceSpikesBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.JungleBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.JungleEdgeBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.JungleHillsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.ModifiedBadlandsPlateauBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.ModifiedGravellyMountainsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.ModifiedJungleBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.ModifiedJungleEdgeBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.ModifiedWoodedBadlandsPlateauBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.MountainsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.MushroomFieldsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.NetherBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.PlainsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.SavannaBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.SavannaPlateauBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.ShatteredSavannaBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.ShatteredSavannaPlateauBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.SnowyBeachBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.SnowyTaigaBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.SnowyTaigaHillsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.SnowyTaigaMountainsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.SnowyTundraBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.StoneShoreBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.SunflowerPlainsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.SwampBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.SwampHillsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.TaigaBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.TaigaHillsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.TaigaMountainsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.TallBirchForestBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.TallBirchForestHillsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.WoodedBadlandsPlateauBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.WoodedHillsBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.WoodedMountainsBiomeUA;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;

public class BiomeInit {
	
	//static variable to hold all biomes and their properties

	//public static final Biome BiomeBambooForest = new BiomeBambooForestUA();
	public static final Biome BiomePlains = new PlainsBiomeUA();
	public static final Biome BiomeDesert = new DesertBiomeUA();
	public static final Biome BiomeForest = new ForestBiomeUA();
	public static final Biome BiomeTaiga = new TaigaBiomeUA();
	public static final Biome BiomeExtremeHills = new MountainsBiomeUA();
	public static final Biome BiomeSwampland = new SwampBiomeUA();
	public static final Biome BiomeNether =  new NetherBiomeUA();
	public static final Biome BiomeEnd = new EndHighlandsBiomeUA();
	public static final Biome BiomeIceFlats = new SnowyTundraBiomeUA();
	public static final Biome BiomeIceMountain = new IceMountainsBiomeUA();
	public static final Biome BiomeMushroomIsland= new MushroomFieldsBiomeUA();
	public static final Biome BiomeDesertHills = new DesertHillsBiomeUA();
	public static final Biome BiomeWoodedHills = new WoodedHillsBiomeUA();
	public static final Biome BiomeTaigaHills = new TaigaHillsBiomeUA();
	public static final Biome BiomeJungle = new JungleBiomeUA();
	public static final Biome BiomeJungleHills = new JungleHillsBiomeUA();
	public static final Biome BiomeJungleEdge = new JungleEdgeBiomeUA();
	public static final Biome BiomeStoneBeach = new StoneShoreBiomeUA();
	public static final Biome BiomeColdBeach = new SnowyBeachBiomeUA();
	public static final Biome BiomeBirchForest = new BirchForestBiomeUA();
	public static final Biome BiomeBirchForestHills = new BirchForestBiomeUA();
	public static final Biome BiomeRoofedForest = new DarkForestBiomeUA();
	public static final Biome BiomeColdTaiga = new SnowyTaigaBiomeUA();
	public static final Biome BiomeColdTaigaHills = new SnowyTaigaHillsBiomeUA();
	public static final Biome BiomeRedwoodTaiga = new GiantTreeTaigaBiomeUA();
	public static final Biome BiomeRedwoodTaigaHills = new GiantTreeTaigaHillsBiomeUA();
	public static final Biome BiomeExtremeHillsPlus = new WoodedMountainsBiomeUA();
	public static final Biome BiomeSavanna = new SavannaBiomeUA();
	public static final Biome BiomeSavannaRock = new SavannaPlateauBiomeUA();
	public static final Biome BiomeMesa = new BadlandsBiomeUA();
	public static final Biome BiomeMesaRock = new WoodedBadlandsPlateauBiomeUA();
	public static final Biome BiomeMesaClearRock = new BadlandsPlateauBiomeUA();
	public static final Biome BiomePlainsM = new SunflowerPlainsBiomeUA();
	public static final Biome BiomeDesertM = new DesertLakesBiomeUA();
	public static final Biome BiomeExtremeHillsM = new GravellyMountainsBiomeUA();
	public static final Biome BiomeForestM = new FlowerForestBiomeUA();
	public static final Biome BiomeTaigaM = new TaigaMountainsBiomeUA();
	public static final Biome BiomeSwamplandM = new SwampHillsBiomeUA();
	public static final Biome BiomeIceSpike = new IceSpikesBiomeUA();
	public static final Biome BiomeJungleM = new ModifiedJungleBiomeUA();
	public static final Biome BiomeJungleEdgeM = new ModifiedJungleEdgeBiomeUA();
	public static final Biome BiomeBirchForestM = new TallBirchForestBiomeUA();
	public static final Biome BiomeBirchForestHillsM = new TallBirchForestHillsBiomeUA();
	public static final Biome BiomeRoofedForestM = new DarkForestHillsBiomeUA();
	public static final Biome BiomeColdTaigaM = new SnowyTaigaMountainsBiomeUA();
	public static final Biome BiomeRedwoodTaigaM = new GiantSpruceTaigaBiomeUA();
	public static final Biome BiomeRedwoodTaigaHillsM = new GiantSpruceTaigaHillsBiomeUA();
	public static final Biome BiomeExtremeHillsPlusM = new ModifiedGravellyMountainsBiomeUA();
	public static final Biome BiomeSavannaM = new ShatteredSavannaBiomeUA();
	public static final Biome BiomeSavannaRockM = new ShatteredSavannaPlateauBiomeUA();
	public static final Biome BiomeMesaBryce = new ErodedBadlandsBiomeUA();
	public static final Biome BiomeMesaRockM = new ModifiedWoodedBadlandsPlateauBiomeUA();
	public static final Biome BiomeMesaClearRockM = new ModifiedBadlandsPlateauBiomeUA();
	private static RegistryEvent.Register<Biome> eventIn = null;
     
	//registers the biomes so they now exist in the registry along with their types
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		
		eventIn = event;
		
		//initBiome(BiomeBambooForest, "Bamboo Forest", BiomeType.WARM, Type.DENSE, Type.WET, Type.FOREST);
		initBiome(BiomePlains, "Plains", BiomeType.WARM, Type.PLAINS);
		initBiome(BiomeDesert, "Desert", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY);
		initBiome(BiomeForest, "Forest", BiomeType.WARM, Type.FOREST);
		initBiome(BiomeTaiga, "Taiga", BiomeType.COOL, Type.CONIFEROUS, Type.FOREST);
		initBiome(BiomeExtremeHills, "Mountains", BiomeType.COOL, Type.MOUNTAIN, Type.HILLS);
		initBiome(BiomeSwampland, "Swamp", BiomeType.WARM, Type.WET, Type.SWAMP);
		initBiome(BiomeNether, "Nether", BiomeType.DESERT, Type.NETHER, Type.HOT, Type.DRY);
		initBiome(BiomeEnd, "The End", BiomeType.COOL, Type.END, Type.SPOOKY, Type.COLD, Type.DRY);
		initBiome(BiomeIceFlats, "Snowy Tundra", BiomeType.ICY, Type.COLD, Type.WASTELAND, Type.SNOWY);
		initBiome(BiomeIceMountain, "Snowy Mountain", BiomeType.ICY, Type.COLD, Type.MOUNTAIN, Type.SNOWY);
		initBiome(BiomeMushroomIsland, "Mushroom Field", BiomeType.WARM, Type.MAGICAL, Type.MUSHROOM, Type.RARE);
		initBiome(BiomeDesertHills, "Desert Hills", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY, Type.HILLS);
		initBiome(BiomeWoodedHills, "Wooden Hills", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS);
		initBiome(BiomeTaigaHills, "Taiga Hills", BiomeType.COOL, Type.CONIFEROUS);
		initBiome(BiomeJungle, "Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
		initBiome(BiomeJungleHills, "Jungle Hills", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.HILLS);
		initBiome(BiomeJungleEdge, "Jungle Edge", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.RARE);
		initBiome(BiomeStoneBeach, "Stone Shore", BiomeType.COOL, Type.BEACH);
		initBiome(BiomeColdBeach, "Snowy Beach", BiomeType.ICY, Type.BEACH, Type.SNOWY, Type.COLD);
		initBiome(BiomeBirchForest, "Birch Forest", BiomeType.WARM, Type.FOREST);
		initBiome(BiomeBirchForestHills, "Birch Forest Hills", BiomeType.WARM, Type.FOREST, Type.HILLS);
		initBiome(BiomeRoofedForest, "Dark Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.SPOOKY);
		initBiome(BiomeColdTaiga, "Snowy Taiga", BiomeType.ICY, Type.FOREST,Type.CONIFEROUS, Type.SNOWY, Type.COLD);
		initBiome(BiomeColdTaigaHills, "Snowy Taiga Hills", BiomeType.ICY, Type.FOREST, Type.CONIFEROUS, Type.SNOWY, Type.COLD, Type.HILLS);
		initBiome(BiomeRedwoodTaiga, "Giant Tree Taiga", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST);
		initBiome(BiomeRedwoodTaigaHills, "Giant Tree Taiga Hills", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.HILLS);
		initBiome(BiomeExtremeHillsPlus, "Wooden Mountains", BiomeType.COOL, Type.MOUNTAIN, Type.FOREST, Type.SPARSE);
		initBiome(BiomeSavanna, "Savanna", BiomeType.DESERT, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE);
		initBiome(BiomeSavannaRock, "Savanna Plateau", BiomeType.DESERT, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE, Type.RARE);
		initBiome(BiomeMesa, "Badlands", BiomeType.DESERT, Type.MESA, Type.SANDY);
		initBiome(BiomeMesaRock, "Wooden Badlands Plateau", BiomeType.DESERT, Type.MESA, Type.SANDY, Type.SPARSE);
		initBiome(BiomeMesaClearRock, "Badlands Plateau", BiomeType.DESERT, Type.MESA, Type.SANDY);
		initBiome(BiomePlainsM, "Sunflower Plains", BiomeType.WARM, Type.PLAINS, Type.RARE);
		initBiome(BiomeDesertM, "Desert Lakes", BiomeType.DESERT, Type.HOT, Type.SANDY, Type.RARE);
		initBiome(BiomeExtremeHillsM, "Gravelly Mountains", BiomeType.COOL, Type.MOUNTAIN, Type.SPARSE, Type.RARE);
		initBiome(BiomeForestM, "Flower Forest", BiomeType.WARM, Type.FOREST, Type.HILLS, Type.RARE);
		initBiome(BiomeTaigaM, "Taiga Mountains", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.MOUNTAIN, Type.RARE);
		initBiome(BiomeSwamplandM, "Swampland Hills", BiomeType.WARM, Type.SWAMP, Type.WET, Type.HILLS, Type.RARE);
		initBiome(BiomeIceSpike, "Ice Spikes", BiomeType.ICY, Type.COLD, Type.SNOWY, Type.HILLS, Type.RARE);
		initBiome(BiomeJungleM, "Modified Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.JUNGLE, Type.MOUNTAIN, Type.RARE, Type.DENSE);
		initBiome(BiomeJungleEdgeM, "Modified Jungle Edge", BiomeType.WARM, Type.HOT, Type.SPARSE, Type.HILLS, Type.JUNGLE, Type.RARE);
		initBiome(BiomeBirchForestM, "Tall Birch Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS, Type.RARE);
		initBiome(BiomeBirchForestHillsM, "Tall Birch Forest Hills", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.MOUNTAIN, Type.RARE);
		initBiome(BiomeRoofedForestM, "Dark Forest Hills", BiomeType.WARM, Type.SPOOKY, Type.DENSE, Type.FOREST, Type.MOUNTAIN, Type.RARE);
		initBiome(BiomeColdTaigaM, "Snowy Taiga Mountains", BiomeType.ICY, Type.COLD, Type.SNOWY, Type.CONIFEROUS, Type.MOUNTAIN, Type.FOREST, Type.RARE);
		initBiome(BiomeRedwoodTaigaM, "Giant Spruce Taiga", BiomeType.COOL, Type.DENSE, Type.FOREST, Type.RARE);
		initBiome(BiomeRedwoodTaigaHillsM, "Giant Spruce Taiga Hills", BiomeType.COOL, Type.DENSE, Type.FOREST, Type.HILLS, Type.RARE);
		initBiome(BiomeExtremeHillsPlusM, "Modified Gravelly Mountains", BiomeType.COOL, Type.MOUNTAIN, Type.SPARSE, Type.RARE);
		initBiome(BiomeSavannaM, "Shattered Savanna", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.MOUNTAIN, Type.RARE);
		initBiome(BiomeSavannaRockM, "Shattered Savanna Plateau", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.HILLS, Type.RARE);
		initBiome(BiomeMesaBryce, "Eroded Badlands", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
		initBiome(BiomeMesaRockM, "Modified Wooden Badlands Plateau", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.HILLS, Type.RARE);
		initBiome(BiomeMesaClearRockM, "Modified Badlands Plateau", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
		
		mapMBiomes();
		mapHillsBiomes();
	}
	
	//adds biome to registry with their type
	private static Biome initBiome(Biome biome, String name, BiomeType biomeType, Type... types) {
		biome.setRegistryName(name.toLowerCase().replace(' ', '_'));
		eventIn.getRegistry().register(biome);
		BiomeDictionary.addTypes(biome, types);
		return biome;
	}
	
	
	//Handles conversion between M form and non-M form biomes. 
	//Necessary since forge does not have a way of doing this and vanilla uses a map that is initialized at a bad time/place in reference to forge mods
	//Also better as I can specify new M variant biomes or make any biome an M variant of another unrelated biome.
    public static final Map<Biome,Biome> BASE_TO_MUTATION_MAP = new HashMap<>();
    
    private static void mapMBiomes() {
    	//registers who is an M variant of another biome
    	
    	BASE_TO_MUTATION_MAP.put(BiomePlains, BiomePlainsM);
    	BASE_TO_MUTATION_MAP.put(BiomeDesert, BiomeDesertM);
    	BASE_TO_MUTATION_MAP.put(BiomeBirchForest, BiomeBirchForestM);
    	BASE_TO_MUTATION_MAP.put(BiomeBirchForestHills, BiomeBirchForestHillsM);
    	BASE_TO_MUTATION_MAP.put(BiomeColdTaiga, BiomeColdTaigaM);
    	BASE_TO_MUTATION_MAP.put(BiomeExtremeHills, BiomeExtremeHillsM);
    	BASE_TO_MUTATION_MAP.put(BiomeExtremeHillsPlus, BiomeExtremeHillsPlusM);
    	BASE_TO_MUTATION_MAP.put(BiomeForest, BiomeForestM);
    	BASE_TO_MUTATION_MAP.put(BiomeIceFlats, BiomeIceSpike);
    	BASE_TO_MUTATION_MAP.put(BiomeJungle, BiomeJungleM);
    	BASE_TO_MUTATION_MAP.put(BiomeJungleEdge, BiomeJungleEdgeM);
    	BASE_TO_MUTATION_MAP.put(BiomeMesa, BiomeMesaBryce);
    	BASE_TO_MUTATION_MAP.put(BiomeMesaClearRock, BiomeMesaClearRockM);
    	BASE_TO_MUTATION_MAP.put(BiomeMesaRock, BiomeMesaRockM);
    	BASE_TO_MUTATION_MAP.put(BiomeRedwoodTaiga, BiomeRedwoodTaigaM);
    	BASE_TO_MUTATION_MAP.put(BiomeRedwoodTaigaHills, BiomeRedwoodTaigaHillsM);
    	BASE_TO_MUTATION_MAP.put(BiomeRoofedForest, BiomeRoofedForestM);
    	BASE_TO_MUTATION_MAP.put(BiomeSavanna, BiomeSavannaM);
    	BASE_TO_MUTATION_MAP.put(BiomeSavannaRock, BiomeSavannaRockM);
    	BASE_TO_MUTATION_MAP.put(BiomeSwampland, BiomeSwamplandM);
    	BASE_TO_MUTATION_MAP.put(BiomeTaiga, BiomeTaigaM);
    }
    
	@Nullable
    public static Biome getMutationForBiome(Biome biome)
    {
		//returns the M variant of the given biome
		
		return BASE_TO_MUTATION_MAP.get(biome);
    }
    
    
	//Handles conversion between Hills form and non-M form biomes in GenLayerHillsUA. 
	//Necessary since forge does not have a way of doing this and vanilla uses a map that is initialized at a bad time/place in reference to forge mods
	//Also better as I can specify new Hills variant biomes or make any biome an Hills variant of another unrelated biome.
    public static final Map<Integer, Integer> BASE_TO_HILLS_MAP = new HashMap<>();
    
    private static void mapHillsBiomes() {
    	//registers who is an Hills variant of another biome
    	
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(BiomeDesert), IRegistry.field_212624_m.getId(BiomeDesertHills));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(BiomeForest), IRegistry.field_212624_m.getId(BiomeWoodedHills));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(BiomeTaiga), IRegistry.field_212624_m.getId(BiomeTaigaHills));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(BiomeJungle), IRegistry.field_212624_m.getId(BiomeJungleHills));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(BiomeBirchForest), IRegistry.field_212624_m.getId(BiomeBirchForestHills));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(BiomeColdTaiga), IRegistry.field_212624_m.getId(BiomeColdTaigaHills));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(BiomeRedwoodTaiga), IRegistry.field_212624_m.getId(BiomeRedwoodTaigaHills));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(BiomeExtremeHills), IRegistry.field_212624_m.getId(BiomeExtremeHillsPlus));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(BiomeSavanna), IRegistry.field_212624_m.getId(BiomeSavannaRock));
    }
    
	
	
}
