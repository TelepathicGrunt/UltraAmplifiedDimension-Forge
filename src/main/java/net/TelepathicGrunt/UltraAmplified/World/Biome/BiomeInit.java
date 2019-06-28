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
	public static final Biome PLAINS = new PlainsBiomeUA();
	public static final Biome DESERT = new DesertBiomeUA();
	public static final Biome FOREST = new ForestBiomeUA();
	public static final Biome TAIGA = new TaigaBiomeUA();
	public static final Biome MOUNTAINS = new MountainsBiomeUA();
	public static final Biome SWAMP = new SwampBiomeUA();
	public static final Biome NETHER =  new NetherBiomeUA();
	public static final Biome END = new EndHighlandsBiomeUA();
	public static final Biome SNOWY_TUNDRA = new SnowyTundraBiomeUA();
	public static final Biome ICE_MOUNTAIN = new IceMountainsBiomeUA();
	public static final Biome MUSHROOM_FIELDS= new MushroomFieldsBiomeUA();
	public static final Biome DESERT_HILLS = new DesertHillsBiomeUA();
	public static final Biome WOODED_HILLS = new WoodedHillsBiomeUA();
	public static final Biome TAIGA_HILLS = new TaigaHillsBiomeUA();
	public static final Biome JUNGLE = new JungleBiomeUA();
	public static final Biome JUNGLE_HILLS = new JungleHillsBiomeUA();
	public static final Biome JUNGLE_EDGE = new JungleEdgeBiomeUA();
	public static final Biome STONE_SHORE = new StoneShoreBiomeUA();
	public static final Biome SNOWY_BEACH = new SnowyBeachBiomeUA();
	public static final Biome BIRCH_FOREST = new BirchForestBiomeUA();
	public static final Biome BIRCH_FOREST_HILLS = new BirchForestBiomeUA();
	public static final Biome DARK_FOREST = new DarkForestBiomeUA();
	public static final Biome SNOWY_TAIGA = new SnowyTaigaBiomeUA();
	public static final Biome SNOWY_TAIGA_HILLS = new SnowyTaigaHillsBiomeUA();
	public static final Biome GIANT_TREE_TAIGA = new GiantTreeTaigaBiomeUA();
	public static final Biome GIANT_TREE_TAIGA_HILLS = new GiantTreeTaigaHillsBiomeUA();
	public static final Biome WOODED_MOUNTAINS = new WoodedMountainsBiomeUA();
	public static final Biome SAVANNA = new SavannaBiomeUA();
	public static final Biome SAVANNA_PLATEAU = new SavannaPlateauBiomeUA();
	public static final Biome BADLANDS = new BadlandsBiomeUA();
	public static final Biome WOODED_BADLANDS_PLATEAU = new WoodedBadlandsPlateauBiomeUA();
	public static final Biome BADLANDS_PLATEAU = new BadlandsPlateauBiomeUA();
	public static final Biome SUNFLOWER_PLAINS = new SunflowerPlainsBiomeUA();
	public static final Biome DESERT_LAKES = new DesertLakesBiomeUA();
	public static final Biome GRAVELLY_MOUNTAINS = new GravellyMountainsBiomeUA();
	public static final Biome FLOWER_FOREST = new FlowerForestBiomeUA();
	public static final Biome TAIGA_MOUNTAINS = new TaigaMountainsBiomeUA();
	public static final Biome SWAMP_HILLS = new SwampHillsBiomeUA();
	public static final Biome ICE_SPIKES = new IceSpikesBiomeUA();
	public static final Biome MODIFIED_JUNGLE = new ModifiedJungleBiomeUA();
	public static final Biome MODIFIED_JUNGLE_EDGE = new ModifiedJungleEdgeBiomeUA();
	public static final Biome TALL_BIRCH_FOREST = new TallBirchForestBiomeUA();
	public static final Biome TALL_BIRCH_FOREST_HILLS = new TallBirchForestHillsBiomeUA();
	public static final Biome DARK_FOREST_HILLS = new DarkForestHillsBiomeUA();
	public static final Biome SNOWY_TAIGA_MOUNTAINS = new SnowyTaigaMountainsBiomeUA();
	public static final Biome GIANT_SPRUCE_TAIGA = new GiantSpruceTaigaBiomeUA();
	public static final Biome GIANT_SPRUCE_TAIGA_HILLS = new GiantSpruceTaigaHillsBiomeUA();
	public static final Biome MODIFIED_GRAVELLY_MOUNTAINS = new ModifiedGravellyMountainsBiomeUA();
	public static final Biome SHATTERED_SAVANNA = new ShatteredSavannaBiomeUA();
	public static final Biome SHATTERED_SAVANNA_PLATEAU = new ShatteredSavannaPlateauBiomeUA();
	public static final Biome ERODED_BADLANDS = new ErodedBadlandsBiomeUA();
	public static final Biome MODIFIED_WOODED_BADLANDS_PLATEAU = new ModifiedWoodedBadlandsPlateauBiomeUA();
	public static final Biome MODIFIED_BADLANDS_PLATEAU = new ModifiedBadlandsPlateauBiomeUA();
	private static RegistryEvent.Register<Biome> eventIn = null;
     
	//registers the biomes so they now exist in the registry along with their types
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		
		eventIn = event;
		
		//initBiome(BiomeBambooForest, "Bamboo Forest", BiomeType.WARM, Type.DENSE, Type.WET, Type.FOREST);
		initBiome(PLAINS, "Plains", BiomeType.WARM, Type.PLAINS);
		initBiome(DESERT, "Desert", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY);
		initBiome(FOREST, "Forest", BiomeType.WARM, Type.FOREST);
		initBiome(TAIGA, "Taiga", BiomeType.COOL, Type.CONIFEROUS, Type.FOREST);
		initBiome(MOUNTAINS, "Mountains", BiomeType.COOL, Type.MOUNTAIN, Type.HILLS);
		initBiome(SWAMP, "Swamp", BiomeType.WARM, Type.WET, Type.SWAMP);
		initBiome(NETHER, "Nether", BiomeType.DESERT, Type.NETHER, Type.HOT, Type.DRY);
		initBiome(END, "The End", BiomeType.COOL, Type.END, Type.SPOOKY, Type.COLD, Type.DRY);
		initBiome(SNOWY_TUNDRA, "Snowy Tundra", BiomeType.ICY, Type.COLD, Type.WASTELAND, Type.SNOWY);
		initBiome(ICE_MOUNTAIN, "Snowy Mountain", BiomeType.ICY, Type.COLD, Type.MOUNTAIN, Type.SNOWY);
		initBiome(MUSHROOM_FIELDS, "Mushroom Field", BiomeType.WARM, Type.MAGICAL, Type.MUSHROOM, Type.RARE);
		initBiome(DESERT_HILLS, "Desert Hills", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY, Type.HILLS);
		initBiome(WOODED_HILLS, "Wooden Hills", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS);
		initBiome(TAIGA_HILLS, "Taiga Hills", BiomeType.COOL, Type.CONIFEROUS);
		initBiome(JUNGLE, "Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
		initBiome(JUNGLE_HILLS, "Jungle Hills", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.HILLS);
		initBiome(JUNGLE_EDGE, "Jungle Edge", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.RARE);
		initBiome(STONE_SHORE, "Stone Shore", BiomeType.COOL, Type.BEACH);
		initBiome(SNOWY_BEACH, "Snowy Beach", BiomeType.ICY, Type.BEACH, Type.SNOWY, Type.COLD);
		initBiome(BIRCH_FOREST, "Birch Forest", BiomeType.WARM, Type.FOREST);
		initBiome(BIRCH_FOREST_HILLS, "Birch Forest Hills", BiomeType.WARM, Type.FOREST, Type.HILLS);
		initBiome(DARK_FOREST, "Dark Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.SPOOKY);
		initBiome(SNOWY_TAIGA, "Snowy Taiga", BiomeType.ICY, Type.FOREST,Type.CONIFEROUS, Type.SNOWY, Type.COLD);
		initBiome(SNOWY_TAIGA_HILLS, "Snowy Taiga Hills", BiomeType.ICY, Type.FOREST, Type.CONIFEROUS, Type.SNOWY, Type.COLD, Type.HILLS);
		initBiome(GIANT_TREE_TAIGA, "Giant Tree Taiga", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST);
		initBiome(GIANT_TREE_TAIGA_HILLS, "Giant Tree Taiga Hills", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.HILLS);
		initBiome(WOODED_MOUNTAINS, "Wooden Mountains", BiomeType.COOL, Type.MOUNTAIN, Type.FOREST, Type.SPARSE);
		initBiome(SAVANNA, "Savanna", BiomeType.DESERT, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE);
		initBiome(SAVANNA_PLATEAU, "Savanna Plateau", BiomeType.DESERT, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE, Type.RARE);
		initBiome(BADLANDS, "Badlands", BiomeType.DESERT, Type.MESA, Type.SANDY);
		initBiome(WOODED_BADLANDS_PLATEAU, "Wooden Badlands Plateau", BiomeType.DESERT, Type.MESA, Type.SANDY, Type.SPARSE);
		initBiome(BADLANDS_PLATEAU, "Badlands Plateau", BiomeType.DESERT, Type.MESA, Type.SANDY);
		initBiome(SUNFLOWER_PLAINS, "Sunflower Plains", BiomeType.WARM, Type.PLAINS, Type.RARE);
		initBiome(DESERT_LAKES, "Desert Lakes", BiomeType.DESERT, Type.HOT, Type.SANDY, Type.RARE);
		initBiome(GRAVELLY_MOUNTAINS, "Gravelly Mountains", BiomeType.COOL, Type.MOUNTAIN, Type.SPARSE, Type.RARE);
		initBiome(FLOWER_FOREST, "Flower Forest", BiomeType.WARM, Type.FOREST, Type.HILLS, Type.RARE);
		initBiome(TAIGA_MOUNTAINS, "Taiga Mountains", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.MOUNTAIN, Type.RARE);
		initBiome(SWAMP_HILLS, "Swampland Hills", BiomeType.WARM, Type.SWAMP, Type.WET, Type.HILLS, Type.RARE);
		initBiome(ICE_SPIKES, "Ice Spikes", BiomeType.ICY, Type.COLD, Type.SNOWY, Type.HILLS, Type.RARE);
		initBiome(MODIFIED_JUNGLE, "Modified Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.JUNGLE, Type.MOUNTAIN, Type.RARE, Type.DENSE);
		initBiome(MODIFIED_JUNGLE_EDGE, "Modified Jungle Edge", BiomeType.WARM, Type.HOT, Type.SPARSE, Type.HILLS, Type.JUNGLE, Type.RARE);
		initBiome(TALL_BIRCH_FOREST, "Tall Birch Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS, Type.RARE);
		initBiome(TALL_BIRCH_FOREST_HILLS, "Tall Birch Forest Hills", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.MOUNTAIN, Type.RARE);
		initBiome(DARK_FOREST_HILLS, "Dark Forest Hills", BiomeType.WARM, Type.SPOOKY, Type.DENSE, Type.FOREST, Type.MOUNTAIN, Type.RARE);
		initBiome(SNOWY_TAIGA_MOUNTAINS, "Snowy Taiga Mountains", BiomeType.ICY, Type.COLD, Type.SNOWY, Type.CONIFEROUS, Type.MOUNTAIN, Type.FOREST, Type.RARE);
		initBiome(GIANT_SPRUCE_TAIGA, "Giant Spruce Taiga", BiomeType.COOL, Type.DENSE, Type.FOREST, Type.RARE);
		initBiome(GIANT_SPRUCE_TAIGA_HILLS, "Giant Spruce Taiga Hills", BiomeType.COOL, Type.DENSE, Type.FOREST, Type.HILLS, Type.RARE);
		initBiome(MODIFIED_GRAVELLY_MOUNTAINS, "Modified Gravelly Mountains", BiomeType.COOL, Type.MOUNTAIN, Type.SPARSE, Type.RARE);
		initBiome(SHATTERED_SAVANNA, "Shattered Savanna", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.MOUNTAIN, Type.RARE);
		initBiome(SHATTERED_SAVANNA_PLATEAU, "Shattered Savanna Plateau", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.HILLS, Type.RARE);
		initBiome(ERODED_BADLANDS, "Eroded Badlands", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
		initBiome(MODIFIED_WOODED_BADLANDS_PLATEAU, "Modified Wooden Badlands Plateau", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.HILLS, Type.RARE);
		initBiome(MODIFIED_BADLANDS_PLATEAU, "Modified Badlands Plateau", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
		
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
    	
    	BASE_TO_MUTATION_MAP.put(PLAINS, SUNFLOWER_PLAINS);
    	BASE_TO_MUTATION_MAP.put(DESERT, DESERT_LAKES);
    	BASE_TO_MUTATION_MAP.put(BIRCH_FOREST, TALL_BIRCH_FOREST);
    	BASE_TO_MUTATION_MAP.put(BIRCH_FOREST_HILLS, TALL_BIRCH_FOREST_HILLS);
    	BASE_TO_MUTATION_MAP.put(SNOWY_TAIGA, SNOWY_TAIGA_MOUNTAINS);
    	BASE_TO_MUTATION_MAP.put(MOUNTAINS, GRAVELLY_MOUNTAINS);
    	BASE_TO_MUTATION_MAP.put(WOODED_MOUNTAINS, MODIFIED_GRAVELLY_MOUNTAINS);
    	BASE_TO_MUTATION_MAP.put(FOREST, FLOWER_FOREST);
    	BASE_TO_MUTATION_MAP.put(SNOWY_TUNDRA, ICE_SPIKES);
    	BASE_TO_MUTATION_MAP.put(JUNGLE, MODIFIED_JUNGLE);
    	BASE_TO_MUTATION_MAP.put(JUNGLE_EDGE, MODIFIED_JUNGLE_EDGE);
    	BASE_TO_MUTATION_MAP.put(BADLANDS, ERODED_BADLANDS);
    	BASE_TO_MUTATION_MAP.put(BADLANDS_PLATEAU, MODIFIED_BADLANDS_PLATEAU);
    	BASE_TO_MUTATION_MAP.put(WOODED_BADLANDS_PLATEAU, MODIFIED_WOODED_BADLANDS_PLATEAU);
    	BASE_TO_MUTATION_MAP.put(GIANT_TREE_TAIGA, GIANT_SPRUCE_TAIGA);
    	BASE_TO_MUTATION_MAP.put(GIANT_TREE_TAIGA_HILLS, GIANT_SPRUCE_TAIGA_HILLS);
    	BASE_TO_MUTATION_MAP.put(DARK_FOREST, DARK_FOREST_HILLS);
    	BASE_TO_MUTATION_MAP.put(SAVANNA, SHATTERED_SAVANNA);
    	BASE_TO_MUTATION_MAP.put(SAVANNA_PLATEAU, SHATTERED_SAVANNA_PLATEAU);
    	BASE_TO_MUTATION_MAP.put(SWAMP, SWAMP_HILLS);
    	BASE_TO_MUTATION_MAP.put(TAIGA, TAIGA_MOUNTAINS);
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
    	
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(DESERT), IRegistry.field_212624_m.getId(DESERT_HILLS));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(FOREST), IRegistry.field_212624_m.getId(WOODED_HILLS));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(TAIGA), IRegistry.field_212624_m.getId(TAIGA_HILLS));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(JUNGLE), IRegistry.field_212624_m.getId(JUNGLE_HILLS));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(BIRCH_FOREST), IRegistry.field_212624_m.getId(BIRCH_FOREST_HILLS));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(SNOWY_TAIGA), IRegistry.field_212624_m.getId(SNOWY_TAIGA_HILLS));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(GIANT_TREE_TAIGA), IRegistry.field_212624_m.getId(GIANT_TREE_TAIGA_HILLS));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(MOUNTAINS), IRegistry.field_212624_m.getId(WOODED_MOUNTAINS));
    	BASE_TO_HILLS_MAP.put(IRegistry.field_212624_m.getId(SAVANNA), IRegistry.field_212624_m.getId(SAVANNA_PLATEAU));
    }
    
	
	
}
