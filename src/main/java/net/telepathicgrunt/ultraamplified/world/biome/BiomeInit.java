package net.telepathicgrunt.ultraamplified.world.biome;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsSandlessBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BambooJungleBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BambooJungleRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BirchForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanColdBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DarkForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DarkForestRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanDeepColdBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanDeepFrozenBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanDeepLukewarmBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanDeepBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanDeepWarmBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DesertBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DesertRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DesertLakesBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.EndBarrenFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.EndFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsSpikyBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.FlowerForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanFrozenBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantSpruceTaigaPillarsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantSpruceTaigaRelicPillarsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantTreeTaigaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantTreeTaigaRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GravellyFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.IcedTerrainBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.IceSpikesBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.JungleBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.JungleEdgeBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.JungleRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanLukewarmBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsDissectedPlateauBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GravellyColumnsFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.FlowerJungleBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.FlowerJungleEdgeBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsDensedWoodedBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.RockyFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.MushroomFieldsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.NetherlandBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.PlainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SavannaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SavannaTerraceBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ShatteredSavannaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ShatteredSavannaTerraceBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.FrozenDesertBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SnowyTaigaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SnowyTaigaRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SnowyTaigaRockyBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SnowyTundraBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.StoneFieldsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SunflowerPlainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SwampBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SwampSpookyBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.TaigaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.TaigaRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.TaigaRockyBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.TallBirchForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.TallBirchForestRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanWarmBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsWoodedBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ForestRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.RockyFieldWoodedBiomeUA;

public class BiomeInit {

    
    private static ForgeRegistry<Biome> BiomeRegistry = ((ForgeRegistry<Biome>)ForgeRegistries.BIOMES);
	//static variable to hold all biomes and their properties

	private static Set<Biome> biomes;
	public static Biome PLAINS = new PlainsBiomeUA();
	public static Biome DESERT = new DesertBiomeUA();
	public static Biome FOREST = new ForestBiomeUA();
	public static Biome TAIGA = new TaigaBiomeUA();
	public static Biome ROCKY_FIELD = new RockyFieldBiomeUA();
	public static Biome SWAMP = new SwampBiomeUA();
	public static Biome NETHERLAND =  new NetherlandBiomeUA();
	public static Biome END_FIELD = new EndFieldBiomeUA();
	public static Biome SNOWY_TUNDRA = new SnowyTundraBiomeUA();
	public static Biome ICED_TERRAIN = new IcedTerrainBiomeUA();
	public static Biome MUSHROOM_FIELDS= new MushroomFieldsBiomeUA();
	public static Biome RELIC_DESERT = new DesertRelicBiomeUA();
	public static Biome RELIC_FOREST = new ForestRelicBiomeUA();
	public static Biome RELIC_TAIGA = new TaigaRelicBiomeUA();
	public static Biome BAMBOO_JUNGLE = new BambooJungleBiomeUA();
	public static Biome RELIC_BAMBOO_JUNGLE = new BambooJungleRelicBiomeUA();
	public static Biome JUNGLE = new JungleBiomeUA();
	public static Biome RELIC_JUNGLE = new JungleRelicBiomeUA();
	public static Biome JUNGLE_EDGE = new JungleEdgeBiomeUA();
	public static Biome STONE_PLAINS = new StoneFieldsBiomeUA();
	public static Biome FROZEN_DESERT = new FrozenDesertBiomeUA();
	public static Biome BIRCH_FOREST = new BirchForestBiomeUA();
	public static Biome RELIC_BIRCH_FOREST = new BirchForestBiomeUA();
	public static Biome DARK_FOREST = new DarkForestBiomeUA();
	public static Biome SNOWY_TAIGA = new SnowyTaigaBiomeUA();
	public static Biome RELIC_SNOWY_TAIGA = new SnowyTaigaRelicBiomeUA();
	public static Biome GIANT_TREE_TAIGA = new GiantTreeTaigaBiomeUA();
	public static Biome RELIC_GIANT_TREE_TAIGA = new GiantTreeTaigaRelicBiomeUA();
	public static Biome WOODED_ROCKY_FIELD = new RockyFieldWoodedBiomeUA();
	public static Biome SAVANNA = new SavannaBiomeUA();
	public static Biome SAVANNA_TERRACE = new SavannaTerraceBiomeUA();
	public static Biome BADLANDS = new BadlandsBiomeUA();
	public static Biome WOODED_BADLANDS = new BadlandsWoodedBiomeUA();
	public static Biome SANDLESS_BADLANDS = new BadlandsSandlessBiomeUA();
	public static Biome SUNFLOWER_PLAINS = new SunflowerPlainsBiomeUA();
	public static Biome DESERT_LAKES = new DesertLakesBiomeUA();
	public static Biome GRAVELLY_FIELD = new GravellyFieldBiomeUA();
	public static Biome FLOWER_FOREST = new FlowerForestBiomeUA();
	public static Biome ROCKY_TAIGA = new TaigaRockyBiomeUA();
	public static Biome SPOOKY_SWAMP = new SwampSpookyBiomeUA();
	public static Biome ICE_SPIKES = new IceSpikesBiomeUA();
	public static Biome FLOWER_JUNGLE = new FlowerJungleBiomeUA();
	public static Biome FLOWER_JUNGLE_EDGE = new FlowerJungleEdgeBiomeUA();
	public static Biome TALL_BIRCH_FOREST = new TallBirchForestBiomeUA();
	public static Biome RELIC_TALL_BIRCH_FOREST = new TallBirchForestRelicBiomeUA();
	public static Biome RELIC_DARK_FOREST = new DarkForestRelicBiomeUA();
	public static Biome SNOWY_ROCKY_TAIGA = new SnowyTaigaRockyBiomeUA();
	public static Biome GIANT_SPRUCE_TAIGA_PILLARS = new GiantSpruceTaigaPillarsBiomeUA();
	public static Biome RELIC_GIANT_SPRUCE_TAIGA_PILLARS = new GiantSpruceTaigaRelicPillarsBiomeUA();
	public static Biome GRAVELLY_COLUMNS_FIELD = new GravellyColumnsFieldBiomeUA();
	public static Biome SHATTERED_SAVANNA = new ShatteredSavannaBiomeUA();
	public static Biome SHATTERED_SAVANNA_TERRACE = new ShatteredSavannaTerraceBiomeUA();
	public static Biome SPIKY_BADLANDS = new BadlandsSpikyBiomeUA();
	public static Biome DENSED_WOODED_BADLANDS = new BadlandsDensedWoodedBiomeUA();
	public static Biome BADLANDS_DISSECTED_PLATEAU = new BadlandsDissectedPlateauBiomeUA();
	public static Biome COLD_OCEAN = new OceanColdBiomeUA();
	public static Biome DEEP_COLD_OCEAN = new OceanDeepColdBiomeUA();
	public static Biome DEEP_FROZEN_OCEAN = new OceanDeepFrozenBiomeUA();
	public static Biome DEEP_LUKEWARM_OCEAN = new OceanDeepLukewarmBiomeUA();
	public static Biome DEEP_OCEAN = new OceanDeepBiomeUA();
	public static Biome DEEP_WARM_OCEAN = new OceanDeepWarmBiomeUA();
	public static Biome FROZEN_OCEAN = new OceanFrozenBiomeUA();
	public static Biome LUKEWARM_OCEAN = new OceanLukewarmBiomeUA();
	public static Biome OCEAN = new OceanBiomeUA();
	public static Biome WARM_OCEAN = new OceanWarmBiomeUA();
	public static Biome BARREN_END_FIELD = new EndBarrenFieldBiomeUA();
	
	
	private static RegistryEvent.Register<Biome> eventIn = null;
     
	
	//registers the biomes so they now exist in the registry along with their types
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		
		eventIn = event;
		
		initBiome(PLAINS, "Plains", BiomeType.WARM, Type.PLAINS);
		initBiome(DESERT, "Desert", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY);
		initBiome(FOREST, "Forest", BiomeType.WARM, Type.FOREST);
		initBiome(TAIGA, "Taiga", BiomeType.COOL, Type.CONIFEROUS, Type.FOREST);
		initBiome(ROCKY_FIELD, "Rocky Field", BiomeType.COOL, Type.MOUNTAIN, Type.HILLS);
		initBiome(SWAMP, "Swamp", BiomeType.WARM, Type.WET, Type.SWAMP);
		initBiome(NETHERLAND, "Netherland", BiomeType.DESERT, Type.NETHER, Type.HOT, Type.DRY);
		initBiome(END_FIELD, "End Field", BiomeType.COOL, Type.END, Type.SPOOKY, Type.COLD, Type.DRY);
		initBiome(SNOWY_TUNDRA, "Snowy Tundra", BiomeType.ICY, Type.COLD, Type.WASTELAND, Type.SNOWY);
		initBiome(ICED_TERRAIN, "Iced Terrain", BiomeType.ICY, Type.COLD, Type.MOUNTAIN, Type.SNOWY);
		initBiome(MUSHROOM_FIELDS, "Mushroom Field", BiomeType.WARM, Type.MAGICAL, Type.MUSHROOM, Type.RARE);
		initBiome(RELIC_DESERT, "Relic Desert", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY, Type.HILLS);
		initBiome(RELIC_FOREST, "Relic Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS);
		initBiome(RELIC_TAIGA, "Relic Taiga", BiomeType.COOL, Type.CONIFEROUS);
		initBiome(BAMBOO_JUNGLE, "Bamboo Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
		initBiome(RELIC_BAMBOO_JUNGLE, "Relic Bamboo Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
		initBiome(JUNGLE, "Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
		initBiome(RELIC_JUNGLE, "Relic Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.HILLS);
		initBiome(JUNGLE_EDGE, "Jungle Edge", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.RARE);
		initBiome(STONE_PLAINS, "Stone Plains", BiomeType.COOL, Type.BEACH);
		initBiome(FROZEN_DESERT, "Frozen Desert", BiomeType.ICY, Type.BEACH, Type.SNOWY, Type.COLD);
		initBiome(BIRCH_FOREST, "Birch Forest", BiomeType.WARM, Type.FOREST);
		initBiome(RELIC_BIRCH_FOREST, "Relic Birch Forest", BiomeType.WARM, Type.FOREST, Type.HILLS);
		initBiome(DARK_FOREST, "Dark Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.SPOOKY);
		initBiome(SNOWY_TAIGA, "Snowy Taiga", BiomeType.ICY, Type.FOREST,Type.CONIFEROUS, Type.SNOWY, Type.COLD);
		initBiome(RELIC_SNOWY_TAIGA, "Relic Snowy Taiga", BiomeType.ICY, Type.FOREST, Type.CONIFEROUS, Type.SNOWY, Type.COLD, Type.HILLS);
		initBiome(GIANT_TREE_TAIGA, "Giant Tree Taiga", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST);
		initBiome(RELIC_GIANT_TREE_TAIGA, "Relic Giant Tree Taiga", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.HILLS);
		initBiome(WOODED_ROCKY_FIELD, "Wooden Rocky Field", BiomeType.COOL, Type.MOUNTAIN, Type.FOREST, Type.SPARSE);
		initBiome(SAVANNA, "Savanna", BiomeType.DESERT, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE);
		initBiome(SAVANNA_TERRACE, "Savanna Terrace", BiomeType.DESERT, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE, Type.RARE);
		initBiome(BADLANDS, "Badlands", BiomeType.DESERT, Type.MESA, Type.SANDY);
		initBiome(WOODED_BADLANDS, "Wooded Badlands", BiomeType.DESERT, Type.MESA, Type.SANDY, Type.SPARSE);
		initBiome(SANDLESS_BADLANDS, "Sandless_Badlands", BiomeType.DESERT, Type.MESA, Type.SANDY);
		initBiome(SUNFLOWER_PLAINS, "Sunflower Plains", BiomeType.WARM, Type.PLAINS, Type.RARE);
		initBiome(DESERT_LAKES, "Desert Lakes", BiomeType.DESERT, Type.HOT, Type.SANDY, Type.RARE);
		initBiome(GRAVELLY_FIELD, "Gravelly Field", BiomeType.COOL, Type.MOUNTAIN, Type.SPARSE, Type.RARE);
		initBiome(FLOWER_FOREST, "Flower Forest", BiomeType.WARM, Type.FOREST, Type.HILLS, Type.RARE);
		initBiome(ROCKY_TAIGA, "Rocky Taiga", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.MOUNTAIN, Type.RARE);
		initBiome(SPOOKY_SWAMP, "Spooky Swampland", BiomeType.WARM, Type.SWAMP, Type.WET, Type.HILLS, Type.RARE);
		initBiome(ICE_SPIKES, "Ice Spikes", BiomeType.ICY, Type.COLD, Type.SNOWY, Type.HILLS, Type.RARE);
		initBiome(FLOWER_JUNGLE, "Flower Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.JUNGLE, Type.MOUNTAIN, Type.RARE, Type.DENSE);
		initBiome(FLOWER_JUNGLE_EDGE, "Flower Jungle Edge", BiomeType.WARM, Type.HOT, Type.SPARSE, Type.HILLS, Type.JUNGLE, Type.RARE);
		initBiome(TALL_BIRCH_FOREST, "Tall Birch Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS, Type.RARE);
		initBiome(RELIC_TALL_BIRCH_FOREST, "Relic Tall Birch Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.MOUNTAIN, Type.RARE);
		initBiome(RELIC_DARK_FOREST, "Relic Dark Forest", BiomeType.WARM, Type.SPOOKY, Type.DENSE, Type.FOREST, Type.MOUNTAIN, Type.RARE);
		initBiome(SNOWY_ROCKY_TAIGA, "Snowy Rocky Taiga", BiomeType.ICY, Type.COLD, Type.SNOWY, Type.CONIFEROUS, Type.MOUNTAIN, Type.FOREST, Type.RARE);
		initBiome(GIANT_SPRUCE_TAIGA_PILLARS, "Giant Spruce Taiga Pillars", BiomeType.COOL, Type.DENSE, Type.FOREST, Type.RARE);
		initBiome(RELIC_GIANT_SPRUCE_TAIGA_PILLARS, "Relic Giant Spruce Taiga Pillars", BiomeType.COOL, Type.DENSE, Type.FOREST, Type.HILLS, Type.RARE);
		initBiome(GRAVELLY_COLUMNS_FIELD, "Gravelly Columns Field", BiomeType.COOL, Type.MOUNTAIN, Type.SPARSE, Type.RARE);
		initBiome(SHATTERED_SAVANNA, "Shattered Savanna", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.MOUNTAIN, Type.RARE);
		initBiome(SHATTERED_SAVANNA_TERRACE, "Shattered Savanna Terrace", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.HILLS, Type.RARE);
		initBiome(SPIKY_BADLANDS, "Spiky Badlands", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
		initBiome(DENSED_WOODED_BADLANDS, "Densed Wooded Badlands", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.HILLS, Type.RARE);
		initBiome(BADLANDS_DISSECTED_PLATEAU, "Badlands Dissected Plateau", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
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
		biomes = ImmutableSet.of(
				PLAINS, 
				DESERT, 
				ROCKY_FIELD, 
				FOREST, 
				TAIGA, 
				SWAMP, 
				NETHERLAND, 
				END_FIELD, 
				SNOWY_TUNDRA, 
				ICED_TERRAIN, 
				MUSHROOM_FIELDS, 
				RELIC_DESERT, 
				RELIC_FOREST, 
				RELIC_TAIGA, 
				BAMBOO_JUNGLE,
				RELIC_BAMBOO_JUNGLE,
				JUNGLE, 
				RELIC_JUNGLE, 
				JUNGLE_EDGE, 
				STONE_PLAINS, 
				FROZEN_DESERT, 
				BIRCH_FOREST, 
				RELIC_BIRCH_FOREST, 
				DARK_FOREST, 
				SNOWY_TAIGA, 
				RELIC_SNOWY_TAIGA, 
				GIANT_TREE_TAIGA, 
				RELIC_GIANT_TREE_TAIGA, 
				WOODED_ROCKY_FIELD, 
				SAVANNA, 
				SAVANNA_TERRACE, 
				BADLANDS, 
				WOODED_BADLANDS, 
				SANDLESS_BADLANDS, 
				SUNFLOWER_PLAINS, 
				DESERT_LAKES, 
				GRAVELLY_FIELD, 
				FLOWER_FOREST, 
				ROCKY_TAIGA, 
				SPOOKY_SWAMP, 
				ICE_SPIKES, 
				FLOWER_JUNGLE, 
				FLOWER_JUNGLE_EDGE, 
				TALL_BIRCH_FOREST, 
				RELIC_TALL_BIRCH_FOREST, 
				RELIC_DARK_FOREST, 
				SNOWY_ROCKY_TAIGA, 
				GIANT_SPRUCE_TAIGA_PILLARS, 
				RELIC_GIANT_SPRUCE_TAIGA_PILLARS,
				GRAVELLY_COLUMNS_FIELD,
				SHATTERED_SAVANNA, 
				SHATTERED_SAVANNA_TERRACE, 
				SPIKY_BADLANDS, 
				DENSED_WOODED_BADLANDS,
				BADLANDS_DISSECTED_PLATEAU,
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
			);
	}



	//adds biome to registry with their type
	private static Biome initBiome(Biome biome, String name, BiomeType biomeType, Type... types) {
		biome.setRegistryName(name.toLowerCase().replace(' ', '_'));
		eventIn.getRegistry().register(biome);
		BiomeDictionary.addTypes(biome, types);
		return biome;
	}
	
	
	
	//needed by BiomeProviderUA to check if a biome can generate a structure and grabs a biome's surface blocks
	public static Set<Biome> getBiomeArray() {
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
    	BASE_TO_MUTATION_MAP.put(RELIC_BIRCH_FOREST, RELIC_TALL_BIRCH_FOREST);
    	BASE_TO_MUTATION_MAP.put(SNOWY_TAIGA, SNOWY_ROCKY_TAIGA);
    	BASE_TO_MUTATION_MAP.put(ROCKY_FIELD, GRAVELLY_FIELD);
    	BASE_TO_MUTATION_MAP.put(WOODED_ROCKY_FIELD, GRAVELLY_COLUMNS_FIELD);
    	BASE_TO_MUTATION_MAP.put(FOREST, FLOWER_FOREST);
    	BASE_TO_MUTATION_MAP.put(SNOWY_TUNDRA, ICE_SPIKES);
    	BASE_TO_MUTATION_MAP.put(JUNGLE, FLOWER_JUNGLE);
    	BASE_TO_MUTATION_MAP.put(JUNGLE_EDGE, FLOWER_JUNGLE_EDGE);
    	BASE_TO_MUTATION_MAP.put(BADLANDS, SPIKY_BADLANDS);
    	BASE_TO_MUTATION_MAP.put(SANDLESS_BADLANDS, BADLANDS_DISSECTED_PLATEAU);
    	BASE_TO_MUTATION_MAP.put(WOODED_BADLANDS, DENSED_WOODED_BADLANDS);
    	BASE_TO_MUTATION_MAP.put(GIANT_TREE_TAIGA, GIANT_SPRUCE_TAIGA_PILLARS);
    	BASE_TO_MUTATION_MAP.put(RELIC_GIANT_TREE_TAIGA, RELIC_GIANT_SPRUCE_TAIGA_PILLARS);
    	BASE_TO_MUTATION_MAP.put(DARK_FOREST, RELIC_DARK_FOREST);
    	BASE_TO_MUTATION_MAP.put(SAVANNA, SHATTERED_SAVANNA);
    	BASE_TO_MUTATION_MAP.put(SAVANNA_TERRACE, SHATTERED_SAVANNA_TERRACE);
    	BASE_TO_MUTATION_MAP.put(SWAMP, SPOOKY_SWAMP);
    	BASE_TO_MUTATION_MAP.put(TAIGA, ROCKY_TAIGA);
    }

    
	//Handles conversion between Hills form and non-M form biomes in GenLayerHillsUA. 
	//Necessary since forge does not have a way of doing this and vanilla uses a map that is initialized at a bad time/place in reference to forge mods
	//Also better as I can specify new Hills variant biomes or make any biome an Hills variant of another unrelated biome.
    public static final Map<Integer, Integer> BASE_TO_HILLS_MAP = new HashMap<>();
    
    private static void mapHillsBiomes() {
    	//registers who is an Hills variant of another biome
    	
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(DESERT), BiomeRegistry.getID(RELIC_DESERT));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(FOREST), BiomeRegistry.getID(RELIC_FOREST));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(TAIGA), BiomeRegistry.getID(RELIC_TAIGA));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(BAMBOO_JUNGLE), BiomeRegistry.getID(RELIC_BAMBOO_JUNGLE));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(JUNGLE), BiomeRegistry.getID(RELIC_JUNGLE));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(BIRCH_FOREST), BiomeRegistry.getID(RELIC_BIRCH_FOREST));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(SNOWY_TAIGA), BiomeRegistry.getID(RELIC_SNOWY_TAIGA));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(GIANT_TREE_TAIGA), BiomeRegistry.getID(RELIC_GIANT_TREE_TAIGA));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(ROCKY_FIELD), BiomeRegistry.getID(WOODED_ROCKY_FIELD));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(SAVANNA), BiomeRegistry.getID(SAVANNA_TERRACE));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(OCEAN), BiomeRegistry.getID(DEEP_OCEAN));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(COLD_OCEAN), BiomeRegistry.getID(DEEP_COLD_OCEAN));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(FROZEN_OCEAN), BiomeRegistry.getID(DEEP_FROZEN_OCEAN));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(LUKEWARM_OCEAN), BiomeRegistry.getID(DEEP_LUKEWARM_OCEAN));
    	BASE_TO_HILLS_MAP.put(BiomeRegistry.getID(WARM_OCEAN), BiomeRegistry.getID(DEEP_WARM_OCEAN));
    }
    
	
	
}
