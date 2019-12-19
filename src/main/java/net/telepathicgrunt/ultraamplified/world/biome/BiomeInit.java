package net.telepathicgrunt.ultraamplified.world.biome;


import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsPlateauBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BambooJungleBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BambooJungleHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BirchForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ColdOceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DarkForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DarkForestHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DeepColdOceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DeepFrozenOceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DeepLukewarmOceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DeepOceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DeepWarmOceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DesertBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DesertHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DesertLakesBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.EndBarrenFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.EndHighlandsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ErodedBadlandsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.FlowerForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.FrozenOceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantSpruceTaigaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantSpruceTaigaHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantTreeTaigaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantTreeTaigaHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GravellyMountainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.IceMountainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.IceSpikesBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.JungleBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.JungleEdgeBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.JungleHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.LukewarmOceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ModifiedBadlandsPlateauBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ModifiedGravellyMountainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ModifiedJungleBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ModifiedJungleEdgeBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ModifiedWoodedBadlandsPlateauBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.MountainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.MushroomFieldsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.NetherBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.PlainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SavannaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SavannaPlateauBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ShatteredSavannaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ShatteredSavannaPlateauBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SnowyBeachBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SnowyTaigaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SnowyTaigaHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SnowyTaigaMountainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SnowyTundraBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.StoneShoreBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SunflowerPlainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SwampBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SwampHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.TaigaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.TaigaHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.TaigaMountainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.TallBirchForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.TallBirchForestHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.WarmOceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.WoodedBadlandsPlateauBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.WoodedHillsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.WoodedMountainsBiomeUA;

public class BiomeInit {

    
    private static ForgeRegistry<Biome> BiomeRegistry = ((ForgeRegistry<Biome>)ForgeRegistries.BIOMES);
	//static variable to hold all biomes and their properties

	private static Biome[] biomes;
	public static Biome PLAINS = new PlainsBiomeUA();
	public static Biome DESERT = new DesertBiomeUA();
	public static Biome FOREST = new ForestBiomeUA();
	public static Biome TAIGA = new TaigaBiomeUA();
	public static Biome MOUNTAINS = new MountainsBiomeUA();
	public static Biome SWAMP = new SwampBiomeUA();
	public static Biome NETHER =  new NetherBiomeUA();
	public static Biome END = new EndHighlandsBiomeUA();
	public static Biome SNOWY_TUNDRA = new SnowyTundraBiomeUA();
	public static Biome ICE_MOUNTAIN = new IceMountainsBiomeUA();
	public static Biome MUSHROOM_FIELDS= new MushroomFieldsBiomeUA();
	public static Biome DESERT_HILLS = new DesertHillsBiomeUA();
	public static Biome WOODED_HILLS = new WoodedHillsBiomeUA();
	public static Biome TAIGA_HILLS = new TaigaHillsBiomeUA();
	public static Biome BAMBOO_JUNGLE = new BambooJungleBiomeUA();
	public static Biome BAMBOO_JUNGLE_HILLS = new BambooJungleHillsBiomeUA();
	public static Biome JUNGLE = new JungleBiomeUA();
	public static Biome JUNGLE_HILLS = new JungleHillsBiomeUA();
	public static Biome JUNGLE_EDGE = new JungleEdgeBiomeUA();
	public static Biome STONE_SHORE = new StoneShoreBiomeUA();
	public static Biome SNOWY_BEACH = new SnowyBeachBiomeUA();
	public static Biome BIRCH_FOREST = new BirchForestBiomeUA();
	public static Biome BIRCH_FOREST_HILLS = new BirchForestBiomeUA();
	public static Biome DARK_FOREST = new DarkForestBiomeUA();
	public static Biome SNOWY_TAIGA = new SnowyTaigaBiomeUA();
	public static Biome SNOWY_TAIGA_HILLS = new SnowyTaigaHillsBiomeUA();
	public static Biome GIANT_TREE_TAIGA = new GiantTreeTaigaBiomeUA();
	public static Biome GIANT_TREE_TAIGA_HILLS = new GiantTreeTaigaHillsBiomeUA();
	public static Biome WOODED_MOUNTAINS = new WoodedMountainsBiomeUA();
	public static Biome SAVANNA = new SavannaBiomeUA();
	public static Biome SAVANNA_PLATEAU = new SavannaPlateauBiomeUA();
	public static Biome BADLANDS = new BadlandsBiomeUA();
	public static Biome WOODED_BADLANDS_PLATEAU = new WoodedBadlandsPlateauBiomeUA();
	public static Biome BADLANDS_PLATEAU = new BadlandsPlateauBiomeUA();
	public static Biome SUNFLOWER_PLAINS = new SunflowerPlainsBiomeUA();
	public static Biome DESERT_LAKES = new DesertLakesBiomeUA();
	public static Biome GRAVELLY_MOUNTAINS = new GravellyMountainsBiomeUA();
	public static Biome FLOWER_FOREST = new FlowerForestBiomeUA();
	public static Biome TAIGA_MOUNTAINS = new TaigaMountainsBiomeUA();
	public static Biome SWAMP_HILLS = new SwampHillsBiomeUA();
	public static Biome ICE_SPIKES = new IceSpikesBiomeUA();
	public static Biome MODIFIED_JUNGLE = new ModifiedJungleBiomeUA();
	public static Biome MODIFIED_JUNGLE_EDGE = new ModifiedJungleEdgeBiomeUA();
	public static Biome TALL_BIRCH_FOREST = new TallBirchForestBiomeUA();
	public static Biome TALL_BIRCH_FOREST_HILLS = new TallBirchForestHillsBiomeUA();
	public static Biome DARK_FOREST_HILLS = new DarkForestHillsBiomeUA();
	public static Biome SNOWY_TAIGA_MOUNTAINS = new SnowyTaigaMountainsBiomeUA();
	public static Biome GIANT_SPRUCE_TAIGA = new GiantSpruceTaigaBiomeUA();
	public static Biome GIANT_SPRUCE_TAIGA_HILLS = new GiantSpruceTaigaHillsBiomeUA();
	public static Biome MODIFIED_GRAVELLY_MOUNTAINS = new ModifiedGravellyMountainsBiomeUA();
	public static Biome SHATTERED_SAVANNA = new ShatteredSavannaBiomeUA();
	public static Biome SHATTERED_SAVANNA_PLATEAU = new ShatteredSavannaPlateauBiomeUA();
	public static Biome ERODED_BADLANDS = new ErodedBadlandsBiomeUA();
	public static Biome MODIFIED_WOODED_BADLANDS_PLATEAU = new ModifiedWoodedBadlandsPlateauBiomeUA();
	public static Biome MODIFIED_BADLANDS_PLATEAU = new ModifiedBadlandsPlateauBiomeUA();
	public static Biome COLD_OCEAN = new ColdOceanBiomeUA();
	public static Biome DEEP_COLD_OCEAN = new DeepColdOceanBiomeUA();
	public static Biome DEEP_FROZEN_OCEAN = new DeepFrozenOceanBiomeUA();
	public static Biome DEEP_LUKEWARM_OCEAN = new DeepLukewarmOceanBiomeUA();
	public static Biome DEEP_OCEAN = new DeepOceanBiomeUA();
	public static Biome DEEP_WARM_OCEAN = new DeepWarmOceanBiomeUA();
	public static Biome FROZEN_OCEAN = new FrozenOceanBiomeUA();
	public static Biome LUKEWARM_OCEAN = new LukewarmOceanBiomeUA();
	public static Biome OCEAN = new OceanBiomeUA();
	public static Biome WARM_OCEAN = new WarmOceanBiomeUA();
	public static Biome BARREN_END_FIELD = new EndBarrenFieldBiomeUA();
	
	
	private static RegistryEvent.Register<Biome> eventIn = null;
     
	
	//registers the biomes so they now exist in the registry along with their types
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		
		eventIn = event;
		
		initBiome(PLAINS, "Plains", BiomeType.WARM, Type.PLAINS);
		initBiome(DESERT, "Desert", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY);
		initBiome(FOREST, "Forest", BiomeType.WARM, Type.FOREST);
		initBiome(TAIGA, "Taiga", BiomeType.COOL, Type.CONIFEROUS, Type.FOREST);
		initBiome(MOUNTAINS, "Mountains", BiomeType.COOL, Type.MOUNTAIN, Type.HILLS);
		initBiome(SWAMP, "Swamp", BiomeType.WARM, Type.WET, Type.SWAMP);
		initBiome(NETHER, "Nether", BiomeType.DESERT, Type.NETHER, Type.HOT, Type.DRY);
		initBiome(END, "The End", BiomeType.COOL, Type.END, Type.SPOOKY, Type.COLD, Type.DRY);
		initBiome(SNOWY_TUNDRA, "Snowy Tundra", BiomeType.ICY, Type.COLD, Type.WASTELAND, Type.SNOWY);
		initBiome(ICE_MOUNTAIN, "Ice Mountain", BiomeType.ICY, Type.COLD, Type.MOUNTAIN, Type.SNOWY);
		initBiome(MUSHROOM_FIELDS, "Mushroom Field", BiomeType.WARM, Type.MAGICAL, Type.MUSHROOM, Type.RARE);
		initBiome(DESERT_HILLS, "Desert Hills", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY, Type.HILLS);
		initBiome(WOODED_HILLS, "Wooden Hills", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS);
		initBiome(TAIGA_HILLS, "Taiga Hills", BiomeType.COOL, Type.CONIFEROUS);
		initBiome(BAMBOO_JUNGLE, "Bamboo Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
		initBiome(BAMBOO_JUNGLE_HILLS, "Bamboo Jungle Hills", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
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
		initBiome(WOODED_BADLANDS_PLATEAU, "Wooded Badlands Plateau", BiomeType.DESERT, Type.MESA, Type.SANDY, Type.SPARSE);
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
		initBiome(MODIFIED_WOODED_BADLANDS_PLATEAU, "Modified Wooded Badlands Plateau", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.HILLS, Type.RARE);
		initBiome(MODIFIED_BADLANDS_PLATEAU, "Modified Badlands Plateau", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
		initBiome(COLD_OCEAN, "Cold Ocean", BiomeType.COOL, Type.OCEAN);
		initBiome(DEEP_COLD_OCEAN, "Deep Cold Ocean", BiomeType.COOL, Type.OCEAN, Type.COLD);
		initBiome(DEEP_FROZEN_OCEAN, "Deep Frozen Ocean", BiomeType.ICY, Type.OCEAN, Type.COLD);
		initBiome(DEEP_LUKEWARM_OCEAN, "Deep Lukewarm Ocean", BiomeType.WARM, Type.OCEAN);
		initBiome(DEEP_OCEAN, "Deep Ocean", BiomeType.COOL, Type.OCEAN);
		initBiome(DEEP_WARM_OCEAN, "Deep Warm Ocean", BiomeType.WARM, Type.OCEAN, Type.HOT);
		initBiome(FROZEN_OCEAN, "Frozen Ocean", BiomeType.ICY, Type.OCEAN, Type.COLD);
		initBiome(LUKEWARM_OCEAN, "Lukewarm Ocean", BiomeType.WARM, Type.OCEAN);
		initBiome(OCEAN, "Ocean", BiomeType.COOL, Type.OCEAN);
		initBiome(WARM_OCEAN, "Warm Ocean", BiomeType.WARM, Type.OCEAN, Type.HOT);
		initBiome(BARREN_END_FIELD, "Barren End Field", BiomeType.COOL, Type.END, Type.SPOOKY, Type.COLD, Type.DRY);
		
		
		mapMBiomes();
		mapHillsBiomes();
		
		
		//adds to a list which we then need in biomeProviderUA
		biomes = new Biome[]
		{
				PLAINS, 
				DESERT, 
				MOUNTAINS, 
				FOREST, 
				TAIGA, 
				SWAMP, 
				NETHER, 
				END, 
				SNOWY_TUNDRA, 
				ICE_MOUNTAIN, 
				MUSHROOM_FIELDS, 
				DESERT_HILLS, 
				WOODED_HILLS, 
				TAIGA_HILLS, 
				BAMBOO_JUNGLE,
				BAMBOO_JUNGLE_HILLS,
				JUNGLE, 
				JUNGLE_HILLS, 
				JUNGLE_EDGE, 
				STONE_SHORE, 
				SNOWY_BEACH, 
				BIRCH_FOREST, 
				BIRCH_FOREST_HILLS, 
				DARK_FOREST, 
				SNOWY_TAIGA, 
				SNOWY_TAIGA_HILLS, 
				GIANT_TREE_TAIGA, 
				GIANT_TREE_TAIGA_HILLS, 
				WOODED_MOUNTAINS, 
				SAVANNA, 
				SAVANNA_PLATEAU, 
				BADLANDS, 
				WOODED_BADLANDS_PLATEAU, 
				BADLANDS_PLATEAU, 
				SUNFLOWER_PLAINS, 
				DESERT_LAKES, 
				GRAVELLY_MOUNTAINS, 
				FLOWER_FOREST, 
				TAIGA_MOUNTAINS, 
				SWAMP_HILLS, 
				ICE_SPIKES, 
				MODIFIED_JUNGLE, 
				MODIFIED_JUNGLE_EDGE, 
				TALL_BIRCH_FOREST, 
				TALL_BIRCH_FOREST_HILLS, 
				DARK_FOREST_HILLS, 
				SNOWY_TAIGA_MOUNTAINS, 
				GIANT_SPRUCE_TAIGA, 
				GIANT_SPRUCE_TAIGA_HILLS,
				MODIFIED_GRAVELLY_MOUNTAINS,
				SHATTERED_SAVANNA, 
				SHATTERED_SAVANNA_PLATEAU, 
				ERODED_BADLANDS, 
				MODIFIED_WOODED_BADLANDS_PLATEAU,
				MODIFIED_BADLANDS_PLATEAU,
				FROZEN_OCEAN,
				COLD_OCEAN,
				OCEAN,
				LUKEWARM_OCEAN,
				WARM_OCEAN,
				DEEP_FROZEN_OCEAN,
				DEEP_COLD_OCEAN,
				DEEP_OCEAN,
				DEEP_LUKEWARM_OCEAN,
				DEEP_WARM_OCEAN,
				BARREN_END_FIELD
			};
	}



	//adds biome to registry with their type
	private static Biome initBiome(Biome biome, String name, BiomeType biomeType, Type... types) {
		biome.setRegistryName(name.toLowerCase().replace(' ', '_'));
		eventIn.getRegistry().register(biome);
		BiomeDictionary.addTypes(biome, types);
		return biome;
	}
	
	
	
	//needed by BiomeProviderUA to check if a biome can generate a structure and grabs a biome's surface blocks
	public static Biome[] getBiomeArray() {
		return biomes;
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

    
	//Handles conversion between Hills form and non-M form biomes in GenLayerHillsUA. 
	//Necessary since forge does not have a way of doing this and vanilla uses a map that is initialized at a bad time/place in reference to forge mods
	//Also better as I can specify new Hills variant biomes or make any biome an Hills variant of another unrelated biome.
    public static final Map<Integer, Integer> BASE_TO_HILLS_MAP = new HashMap<>();
    
    private static void mapHillsBiomes() {
    	//registers who is an Hills variant of another biome
    	
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(DESERT), BiomeRegistry.getID(DESERT_HILLS));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(FOREST), BiomeRegistry.getID(WOODED_HILLS));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(TAIGA), BiomeRegistry.getID(TAIGA_HILLS));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(BAMBOO_JUNGLE), BiomeRegistry.getID(BAMBOO_JUNGLE_HILLS));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(JUNGLE), BiomeRegistry.getID(JUNGLE_HILLS));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(BIRCH_FOREST), BiomeRegistry.getID(BIRCH_FOREST_HILLS));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(SNOWY_TAIGA), BiomeRegistry.getID(SNOWY_TAIGA_HILLS));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(GIANT_TREE_TAIGA), BiomeRegistry.getID(GIANT_TREE_TAIGA_HILLS));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(MOUNTAINS), BiomeRegistry.getID(WOODED_MOUNTAINS));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(SAVANNA), BiomeRegistry.getID(SAVANNA_PLATEAU));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(OCEAN), BiomeRegistry.getID(DEEP_OCEAN));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(COLD_OCEAN), BiomeRegistry.getID(DEEP_COLD_OCEAN));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(FROZEN_OCEAN), BiomeRegistry.getID(DEEP_FROZEN_OCEAN));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(LUKEWARM_OCEAN), BiomeRegistry.getID(DEEP_LUKEWARM_OCEAN));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(WARM_OCEAN), BiomeRegistry.getID(DEEP_WARM_OCEAN));
    }
    
	
	
}
