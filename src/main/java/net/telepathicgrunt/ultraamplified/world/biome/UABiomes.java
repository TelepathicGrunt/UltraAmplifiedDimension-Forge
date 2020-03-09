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
import net.minecraftforge.registries.IForgeRegistry;
import net.telepathicgrunt.ultraamplified.RegUtil;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsDensedWoodedBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsDissectedPlateauBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsSandlessBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsSpikyBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BadlandsWoodedBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BambooJungleBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BambooJungleRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.BirchForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DarkForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DarkForestRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DesertBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DesertLakesBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.DesertRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.EndBarrenFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.EndFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.FlowerForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.FlowerJungleBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.FlowerJungleEdgeBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ForestBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ForestRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.FrozenDesertBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantSpruceTaigaPillarsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantSpruceTaigaRelicPillarsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantTreeTaigaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GiantTreeTaigaRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GravellyColumnsFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.GravellyFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.IceSpikesBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.IcedTerrainBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.JungleBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.JungleEdgeBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.JungleRelicBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.MushroomFieldsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.NetherlandBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanColdBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanDeepBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanDeepColdBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanDeepFrozenBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanDeepLukewarmBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanDeepWarmBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanFrozenBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanLukewarmBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.OceanWarmBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.PlainsBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.RockyFieldBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.RockyFieldWoodedBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SavannaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.SavannaTerraceBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ShatteredSavannaBiomeUA;
import net.telepathicgrunt.ultraamplified.world.biomes.ShatteredSavannaTerraceBiomeUA;
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


public class UABiomes
{

	private static ForgeRegistry<Biome> BiomeRegistry = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES);
	//static variable to hold all biomes and their properties

	private static Set<Biome> biomes;
	public static Biome PLAINS;
	public static Biome DESERT;
	public static Biome FOREST;
	public static Biome TAIGA;
	public static Biome ROCKY_FIELD;
	public static Biome SWAMP;
	public static Biome NETHERLAND;
	public static Biome END_FIELD;
	public static Biome SNOWY_TUNDRA;
	public static Biome ICED_TERRAIN;
	public static Biome MUSHROOM_FIELDS;
	public static Biome RELIC_DESERT;
	public static Biome RELIC_FOREST;
	public static Biome RELIC_TAIGA;
	public static Biome BAMBOO_JUNGLE;
	public static Biome RELIC_BAMBOO_JUNGLE;
	public static Biome JUNGLE;
	public static Biome RELIC_JUNGLE;
	public static Biome JUNGLE_EDGE;
	public static Biome STONE_PLAINS;
	public static Biome FROZEN_DESERT;
	public static Biome BIRCH_FOREST;
	public static Biome RELIC_BIRCH_FOREST;
	public static Biome DARK_FOREST;
	public static Biome SNOWY_TAIGA;
	public static Biome RELIC_SNOWY_TAIGA;
	public static Biome GIANT_TREE_TAIGA;
	public static Biome RELIC_GIANT_TREE_TAIGA;
	public static Biome WOODED_ROCKY_FIELD;
	public static Biome SAVANNA;
	public static Biome SAVANNA_TERRACE;
	public static Biome BADLANDS;
	public static Biome WOODED_BADLANDS;
	public static Biome SANDLESS_BADLANDS;
	public static Biome SUNFLOWER_PLAINS;
	public static Biome DESERT_LAKES;
	public static Biome GRAVELLY_FIELD;
	public static Biome FLOWER_FOREST;
	public static Biome ROCKY_TAIGA;
	public static Biome SPOOKY_SWAMP;
	public static Biome ICE_SPIKES;
	public static Biome FLOWER_JUNGLE;
	public static Biome FLOWER_JUNGLE_EDGE;
	public static Biome TALL_BIRCH_FOREST;
	public static Biome RELIC_TALL_BIRCH_FOREST;
	public static Biome RELIC_DARK_FOREST;
	public static Biome SNOWY_ROCKY_TAIGA;
	public static Biome GIANT_SPRUCE_TAIGA_PILLARS;
	public static Biome RELIC_GIANT_SPRUCE_TAIGA_PILLARS;
	public static Biome GRAVELLY_COLUMNS_FIELD;
	public static Biome SHATTERED_SAVANNA;
	public static Biome SHATTERED_SAVANNA_TERRACE;
	public static Biome SPIKY_BADLANDS;
	public static Biome DENSED_WOODED_BADLANDS;
	public static Biome DISSECTED_PLATEAU_BADLANDS;
	public static Biome COLD_OCEAN;
	public static Biome DEEP_COLD_OCEAN;
	public static Biome DEEP_FROZEN_OCEAN;
	public static Biome DEEP_LUKEWARM_OCEAN;
	public static Biome DEEP_OCEAN;
	public static Biome DEEP_WARM_OCEAN;
	public static Biome FROZEN_OCEAN;
	public static Biome LUKEWARM_OCEAN;
	public static Biome OCEAN;
	public static Biome WARM_OCEAN;
	public static Biome BARREN_END_FIELD;


	//registers the biomes so they now exist in the registry along with their types
	public static void registerBiomes(RegistryEvent.Register<Biome> event)
	{

		IForgeRegistry<Biome> registry = event.getRegistry();

		PLAINS = initBiome(registry, new PlainsBiomeUA(), "Plains", BiomeType.WARM, Type.PLAINS);
		DESERT = initBiome(registry, new DesertBiomeUA(), "Desert", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY);
		FOREST = initBiome(registry, new ForestBiomeUA(), "Forest", BiomeType.WARM, Type.FOREST);
		TAIGA = initBiome(registry, new TaigaBiomeUA(), "Taiga", BiomeType.COOL, Type.CONIFEROUS, Type.FOREST);
		ROCKY_FIELD = initBiome(registry, new RockyFieldBiomeUA(), "Rocky Field", BiomeType.COOL, Type.MOUNTAIN, Type.HILLS);
		SWAMP = initBiome(registry, new SwampBiomeUA(), "Swamp", BiomeType.WARM, Type.WET, Type.SWAMP);
		NETHERLAND = initBiome(registry, new NetherlandBiomeUA(), "Netherland", BiomeType.DESERT, Type.NETHER, Type.HOT, Type.DRY);
		END_FIELD = initBiome(registry, new EndFieldBiomeUA(), "End Field", BiomeType.COOL, Type.END, Type.SPOOKY, Type.COLD, Type.DRY);
		SNOWY_TUNDRA = initBiome(registry, new SnowyTundraBiomeUA(), "Snowy Tundra", BiomeType.ICY, Type.COLD, Type.WASTELAND, Type.SNOWY);
		ICED_TERRAIN = initBiome(registry, new IcedTerrainBiomeUA(), "Iced Terrain", BiomeType.ICY, Type.COLD, Type.MOUNTAIN, Type.SNOWY);
		MUSHROOM_FIELDS = initBiome(registry, new MushroomFieldsBiomeUA(), "Mushroom Field", BiomeType.WARM, Type.MAGICAL, Type.MUSHROOM, Type.RARE);
		RELIC_DESERT = initBiome(registry, new DesertRelicBiomeUA(), "Relic Desert", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY, Type.HILLS);
		RELIC_FOREST = initBiome(registry, new ForestRelicBiomeUA(), "Relic Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS);
		RELIC_TAIGA = initBiome(registry, new TaigaRelicBiomeUA(), "Relic Taiga", BiomeType.COOL, Type.CONIFEROUS);
		BAMBOO_JUNGLE = initBiome(registry, new BambooJungleBiomeUA(), "Bamboo Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
		RELIC_BAMBOO_JUNGLE = initBiome(registry, new BambooJungleRelicBiomeUA(), "Relic Bamboo Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
		JUNGLE = initBiome(registry, new JungleBiomeUA(), "Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
		RELIC_JUNGLE = initBiome(registry, new JungleRelicBiomeUA(), "Relic Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.HILLS);
		JUNGLE_EDGE = initBiome(registry, new JungleEdgeBiomeUA(), "Jungle Edge", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.RARE);
		STONE_PLAINS = initBiome(registry, new StoneFieldsBiomeUA(), "Stone Plains", BiomeType.COOL, Type.BEACH);
		FROZEN_DESERT = initBiome(registry, new FrozenDesertBiomeUA(), "Frozen Desert", BiomeType.ICY, Type.BEACH, Type.SNOWY, Type.COLD);
		BIRCH_FOREST = initBiome(registry, new BirchForestBiomeUA(), "Birch Forest", BiomeType.WARM, Type.FOREST);
		RELIC_BIRCH_FOREST = initBiome(registry, new BirchForestBiomeUA(), "Relic Birch Forest", BiomeType.WARM, Type.FOREST, Type.HILLS);
		DARK_FOREST = initBiome(registry, new DarkForestBiomeUA(), "Dark Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.SPOOKY);
		SNOWY_TAIGA = initBiome(registry, new SnowyTaigaBiomeUA(), "Snowy Taiga", BiomeType.ICY, Type.FOREST, Type.CONIFEROUS, Type.SNOWY, Type.COLD);
		RELIC_SNOWY_TAIGA = initBiome(registry, new SnowyTaigaRelicBiomeUA(), "Relic Snowy Taiga", BiomeType.ICY, Type.FOREST, Type.CONIFEROUS, Type.SNOWY, Type.COLD, Type.HILLS);
		GIANT_TREE_TAIGA = initBiome(registry, new GiantTreeTaigaBiomeUA(), "Giant Tree Taiga", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST);
		RELIC_GIANT_TREE_TAIGA = initBiome(registry, new GiantTreeTaigaRelicBiomeUA(), "Relic Giant Tree Taiga", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.HILLS);
		WOODED_ROCKY_FIELD = initBiome(registry, new RockyFieldWoodedBiomeUA(), "Wooden Rocky Field", BiomeType.COOL, Type.MOUNTAIN, Type.FOREST, Type.SPARSE);
		SAVANNA = initBiome(registry, new SavannaBiomeUA(), "Savanna", BiomeType.DESERT, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE);
		SAVANNA_TERRACE = initBiome(registry, new SavannaTerraceBiomeUA(), "Savanna Terrace", BiomeType.DESERT, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE, Type.RARE);
		BADLANDS = initBiome(registry, new BadlandsBiomeUA(), "Badlands", BiomeType.DESERT, Type.MESA, Type.SANDY);
		WOODED_BADLANDS = initBiome(registry, new BadlandsWoodedBiomeUA(), "Wooded Badlands", BiomeType.DESERT, Type.MESA, Type.SANDY, Type.SPARSE);
		SANDLESS_BADLANDS = initBiome(registry, new BadlandsSandlessBiomeUA(), "Sandless_Badlands", BiomeType.DESERT, Type.MESA, Type.SANDY);
		SUNFLOWER_PLAINS = initBiome(registry, new SunflowerPlainsBiomeUA(), "Sunflower Plains", BiomeType.WARM, Type.PLAINS, Type.RARE);
		DESERT_LAKES = initBiome(registry, new DesertLakesBiomeUA(), "Desert Lakes", BiomeType.DESERT, Type.HOT, Type.SANDY, Type.RARE);
		GRAVELLY_FIELD = initBiome(registry, new GravellyFieldBiomeUA(), "Gravelly Field", BiomeType.COOL, Type.MOUNTAIN, Type.SPARSE, Type.RARE);
		FLOWER_FOREST = initBiome(registry, new FlowerForestBiomeUA(), "Flower Forest", BiomeType.WARM, Type.FOREST, Type.HILLS, Type.RARE);
		ROCKY_TAIGA = initBiome(registry, new TaigaRockyBiomeUA(), "Rocky Taiga", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.MOUNTAIN, Type.RARE);
		SPOOKY_SWAMP = initBiome(registry, new SwampSpookyBiomeUA(), "Spooky Swampland", BiomeType.WARM, Type.SWAMP, Type.WET, Type.HILLS, Type.RARE);
		ICE_SPIKES = initBiome(registry, new IceSpikesBiomeUA(), "Ice Spikes", BiomeType.ICY, Type.COLD, Type.SNOWY, Type.HILLS, Type.RARE);
		FLOWER_JUNGLE = initBiome(registry, new FlowerJungleBiomeUA(), "Flower Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.JUNGLE, Type.MOUNTAIN, Type.RARE, Type.DENSE);
		FLOWER_JUNGLE_EDGE = initBiome(registry, new FlowerJungleEdgeBiomeUA(), "Flower Jungle Edge", BiomeType.WARM, Type.HOT, Type.SPARSE, Type.HILLS, Type.JUNGLE, Type.RARE);
		TALL_BIRCH_FOREST = initBiome(registry, new TallBirchForestBiomeUA(), "Tall Birch Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS, Type.RARE);
		RELIC_TALL_BIRCH_FOREST = initBiome(registry, new TallBirchForestRelicBiomeUA(), "Relic Tall Birch Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.MOUNTAIN, Type.RARE);
		RELIC_DARK_FOREST = initBiome(registry, new DarkForestRelicBiomeUA(), "Relic Dark Forest", BiomeType.WARM, Type.SPOOKY, Type.DENSE, Type.FOREST, Type.MOUNTAIN, Type.RARE);
		SNOWY_ROCKY_TAIGA = initBiome(registry, new SnowyTaigaRockyBiomeUA(), "Snowy Rocky Taiga", BiomeType.ICY, Type.COLD, Type.SNOWY, Type.CONIFEROUS, Type.MOUNTAIN, Type.FOREST, Type.RARE);
		GIANT_SPRUCE_TAIGA_PILLARS = initBiome(registry, new GiantSpruceTaigaPillarsBiomeUA(), "Giant Spruce Taiga Pillars", BiomeType.COOL, Type.DENSE, Type.FOREST, Type.RARE);
		RELIC_GIANT_SPRUCE_TAIGA_PILLARS = initBiome(registry, new GiantSpruceTaigaRelicPillarsBiomeUA(), "Relic Giant Spruce Taiga Pillars", BiomeType.COOL, Type.DENSE, Type.FOREST, Type.HILLS, Type.RARE);
		GRAVELLY_COLUMNS_FIELD = initBiome(registry, new GravellyColumnsFieldBiomeUA(), "Gravelly Columns Field", BiomeType.COOL, Type.MOUNTAIN, Type.SPARSE, Type.RARE);
		SHATTERED_SAVANNA = initBiome(registry, new ShatteredSavannaBiomeUA(), "Shattered Savanna", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.MOUNTAIN, Type.RARE);
		SHATTERED_SAVANNA_TERRACE = initBiome(registry, new ShatteredSavannaTerraceBiomeUA(), "Shattered Savanna Terrace", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.HILLS, Type.RARE);
		SPIKY_BADLANDS = initBiome(registry, new BadlandsSpikyBiomeUA(), "Spiky Badlands", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
		DENSED_WOODED_BADLANDS = initBiome(registry, new BadlandsDensedWoodedBiomeUA(), "Densed Wooded Badlands", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.HILLS, Type.RARE);
		DISSECTED_PLATEAU_BADLANDS = initBiome(registry, new BadlandsDissectedPlateauBiomeUA(), "Dissected Plateau Badlands", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
		COLD_OCEAN = initBiome(registry, new OceanColdBiomeUA(), "Cold Ocean", BiomeType.COOL, Type.OCEAN);
		DEEP_COLD_OCEAN = initBiome(registry, new OceanDeepColdBiomeUA(), "Deep Cold Ocean", BiomeType.COOL, Type.OCEAN, Type.COLD);
		DEEP_FROZEN_OCEAN = initBiome(registry, new OceanDeepFrozenBiomeUA(), "Deep Frozen Ocean", BiomeType.ICY, Type.OCEAN, Type.COLD);
		DEEP_LUKEWARM_OCEAN = initBiome(registry, new OceanDeepLukewarmBiomeUA(), "Deep Lukewarm Ocean", BiomeType.WARM, Type.OCEAN);
		DEEP_OCEAN = initBiome(registry, new OceanDeepBiomeUA(), "Deep Ocean", BiomeType.COOL, Type.OCEAN);
		DEEP_WARM_OCEAN = initBiome(registry, new OceanDeepWarmBiomeUA(), "Deep Warm Ocean", BiomeType.WARM, Type.OCEAN, Type.HOT);
		FROZEN_OCEAN = initBiome(registry, new OceanFrozenBiomeUA(), "Frozen Ocean", BiomeType.ICY, Type.OCEAN, Type.COLD);
		LUKEWARM_OCEAN = initBiome(registry, new OceanLukewarmBiomeUA(), "Lukewarm Ocean", BiomeType.WARM, Type.OCEAN);
		OCEAN = initBiome(registry, new OceanBiomeUA(), "Ocean", BiomeType.COOL, Type.OCEAN);
		WARM_OCEAN = initBiome(registry, new OceanWarmBiomeUA(), "Warm Ocean", BiomeType.WARM, Type.OCEAN, Type.HOT);
		BARREN_END_FIELD = initBiome(registry, new EndBarrenFieldBiomeUA(), "Barren End Field", BiomeType.COOL, Type.END, Type.SPOOKY, Type.COLD, Type.DRY);

		mapHillsBiomes();
		mapMBiomes();

		//adds to a list which we then need in biomeProviderUA
		biomes = ImmutableSet.of(PLAINS, DESERT, ROCKY_FIELD, FOREST, TAIGA, SWAMP, NETHERLAND, END_FIELD, SNOWY_TUNDRA, ICED_TERRAIN, MUSHROOM_FIELDS, RELIC_DESERT, RELIC_FOREST, RELIC_TAIGA, BAMBOO_JUNGLE, RELIC_BAMBOO_JUNGLE, JUNGLE, RELIC_JUNGLE, JUNGLE_EDGE, STONE_PLAINS, FROZEN_DESERT, BIRCH_FOREST, RELIC_BIRCH_FOREST, DARK_FOREST, SNOWY_TAIGA, RELIC_SNOWY_TAIGA, GIANT_TREE_TAIGA, RELIC_GIANT_TREE_TAIGA, WOODED_ROCKY_FIELD, SAVANNA, SAVANNA_TERRACE, BADLANDS, WOODED_BADLANDS,
				SANDLESS_BADLANDS, SUNFLOWER_PLAINS, DESERT_LAKES, GRAVELLY_FIELD, FLOWER_FOREST, ROCKY_TAIGA, SPOOKY_SWAMP, ICE_SPIKES, FLOWER_JUNGLE, FLOWER_JUNGLE_EDGE, TALL_BIRCH_FOREST, RELIC_TALL_BIRCH_FOREST, RELIC_DARK_FOREST, SNOWY_ROCKY_TAIGA, GIANT_SPRUCE_TAIGA_PILLARS, RELIC_GIANT_SPRUCE_TAIGA_PILLARS, GRAVELLY_COLUMNS_FIELD, SHATTERED_SAVANNA, SHATTERED_SAVANNA_TERRACE, SPIKY_BADLANDS, DENSED_WOODED_BADLANDS, DISSECTED_PLATEAU_BADLANDS, FROZEN_OCEAN, COLD_OCEAN, OCEAN,
				LUKEWARM_OCEAN, WARM_OCEAN, DEEP_FROZEN_OCEAN, DEEP_COLD_OCEAN, DEEP_OCEAN, DEEP_LUKEWARM_OCEAN, DEEP_WARM_OCEAN, BARREN_END_FIELD);
	}


	//adds biome to registry with their type to the registry and to the biome dictionary
	private static Biome initBiome(IForgeRegistry<Biome> registry, Biome biome, String name, BiomeType biomeType, Type... types)
	{
		RegUtil.register(registry, biome, name);
		BiomeDictionary.addTypes(biome, types);
		return biome;
	}


	//needed by BiomeProviderUA to check if a biome can generate a structure and grabs a biome's surface blocks
	public static Set<Biome> getBiomeArray()
	{
		return biomes;
	}

	//Handles conversion between M form and non-M form biomes. 
	//Necessary since forge does not have a way of doing this and vanilla uses a map that is initialized at a bad time/place in reference to forge mods
	//Also better as I can specify new M variant biomes or make any biome an M variant of another unrelated biome.
	public static final Map<Biome, Biome> BASE_TO_MUTATION_MAP = new HashMap<Biome, Biome>();


	public static void mapMBiomes()
	{
		//clear out old list
		BASE_TO_MUTATION_MAP.clear();

		//registers who is an M variant of another biome
		BASE_TO_MUTATION_MAP.put(PLAINS, SUNFLOWER_PLAINS);
		BASE_TO_MUTATION_MAP.put(DESERT, DESERT_LAKES);
		BASE_TO_MUTATION_MAP.put(BIRCH_FOREST, TALL_BIRCH_FOREST);
		BASE_TO_MUTATION_MAP.put(RELIC_BIRCH_FOREST, RELIC_TALL_BIRCH_FOREST);
		BASE_TO_MUTATION_MAP.put(SNOWY_TAIGA, SNOWY_ROCKY_TAIGA);
		BASE_TO_MUTATION_MAP.put(ROCKY_FIELD, GRAVELLY_FIELD);
		BASE_TO_MUTATION_MAP.put(WOODED_ROCKY_FIELD, GRAVELLY_COLUMNS_FIELD);
		BASE_TO_MUTATION_MAP.put(FOREST, FLOWER_FOREST);
		BASE_TO_MUTATION_MAP.put(JUNGLE, FLOWER_JUNGLE);
		BASE_TO_MUTATION_MAP.put(JUNGLE_EDGE, FLOWER_JUNGLE_EDGE);
		BASE_TO_MUTATION_MAP.put(WOODED_BADLANDS, DENSED_WOODED_BADLANDS);
		BASE_TO_MUTATION_MAP.put(GIANT_TREE_TAIGA, GIANT_SPRUCE_TAIGA_PILLARS);
		BASE_TO_MUTATION_MAP.put(RELIC_GIANT_TREE_TAIGA, RELIC_GIANT_SPRUCE_TAIGA_PILLARS);
		BASE_TO_MUTATION_MAP.put(DARK_FOREST, RELIC_DARK_FOREST);
		BASE_TO_MUTATION_MAP.put(SAVANNA, SHATTERED_SAVANNA);
		BASE_TO_MUTATION_MAP.put(SAVANNA_TERRACE, SHATTERED_SAVANNA_TERRACE);
		BASE_TO_MUTATION_MAP.put(SWAMP, SPOOKY_SWAMP);
		BASE_TO_MUTATION_MAP.put(TAIGA, ROCKY_TAIGA);

		if (ConfigUA.iceSpike)
		{
			BASE_TO_MUTATION_MAP.put(SNOWY_TUNDRA, ICE_SPIKES);
		}

		if (ConfigUA.spikyBadlands)
		{
			BASE_TO_MUTATION_MAP.put(SANDLESS_BADLANDS, DISSECTED_PLATEAU_BADLANDS);
			BASE_TO_MUTATION_MAP.put(BADLANDS, SPIKY_BADLANDS);
		}
	}

	//Handles conversion between Hills form and non-M form biomes in GenLayerHillsUA. 
	//Necessary since forge does not have a way of doing this and vanilla uses a map that is initialized at a bad time/place in reference to forge mods
	//Also better as I can specify new Hills variant biomes or make any biome an Hills variant of another unrelated biome.
	public static Map<Integer, Integer> BASE_TO_HILLS_MAP = new HashMap<Integer, Integer>();


	private static void mapHillsBiomes()
	{
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
