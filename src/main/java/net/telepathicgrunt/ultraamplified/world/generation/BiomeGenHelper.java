package net.telepathicgrunt.ultraamplified.world.generation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.biome.UABiomes;


public enum BiomeGenHelper
{
	instance;
	public static ForgeRegistry<Biome> BiomeRegistry = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES);

	public static final int PLAINS = BiomeRegistry.getID(UABiomes.PLAINS);
	public static final int SWAMP = BiomeRegistry.getID(UABiomes.SWAMP);
	public static final int FOREST = BiomeRegistry.getID(UABiomes.FOREST);
	public static final int DESERT = BiomeRegistry.getID(UABiomes.DESERT);
	public static final int WOODED_ROCKY_FIELD = BiomeRegistry.getID(UABiomes.WOODED_ROCKY_FIELD);
	public static final int RELIC_FOREST = BiomeRegistry.getID(UABiomes.RELIC_FOREST);
	public static final int SNOWY_TUNDRA = BiomeRegistry.getID(UABiomes.SNOWY_TUNDRA);
	public static final int ICED_TERRAIN = BiomeRegistry.getID(UABiomes.ICED_TERRAIN);
	public static final int BAMBOO_JUNGLE = BiomeRegistry.getID(UABiomes.BAMBOO_JUNGLE);
	public static final int JUNGLE = BiomeRegistry.getID(UABiomes.JUNGLE);
	public static final int JUNGLE_EDGE = BiomeRegistry.getID(UABiomes.JUNGLE_EDGE);
	public static final int BADLANDS = BiomeRegistry.getID(UABiomes.BADLANDS);
	public static final int WOODED_BADLANDS = BiomeRegistry.getID(UABiomes.WOODED_BADLANDS);
	public static final int SANDLESS_BADLANDS = BiomeRegistry.getID(UABiomes.SANDLESS_BADLANDS);
	public static final int MUSHROOM_FIELDS = BiomeRegistry.getID(UABiomes.MUSHROOM_FIELDS);
	public static final int GIANT_TREE_TAIGA = BiomeRegistry.getID(UABiomes.GIANT_TREE_TAIGA);
	public static final int DARK_FOREST = BiomeRegistry.getID(UABiomes.DARK_FOREST);
	public static final int SAVANNA = BiomeRegistry.getID(UABiomes.SAVANNA);
	public static final int TAIGA = BiomeRegistry.getID(UABiomes.TAIGA);
	public static final int SNOWY_TAIGA = BiomeRegistry.getID(UABiomes.SNOWY_TAIGA);
	public static final int WARM_OCEAN = BiomeRegistry.getID(UABiomes.WARM_OCEAN);
	public static final int LUKEWARM_OCEAN = BiomeRegistry.getID(UABiomes.LUKEWARM_OCEAN);
	public static final int OCEAN = BiomeRegistry.getID(UABiomes.OCEAN);
	public static final int COLD_OCEAN = BiomeRegistry.getID(UABiomes.COLD_OCEAN);
	public static final int FROZEN_OCEAN = BiomeRegistry.getID(UABiomes.FROZEN_OCEAN);
	public static final int DEEP_WARM_OCEAN = BiomeRegistry.getID(UABiomes.DEEP_WARM_OCEAN);
	public static final int DEEP_LUKEWARM_OCEAN = BiomeRegistry.getID(UABiomes.DEEP_LUKEWARM_OCEAN);
	public static final int DEEP_OCEAN = BiomeRegistry.getID(UABiomes.DEEP_OCEAN);
	public static final int DEEP_COLD_OCEAN = BiomeRegistry.getID(UABiomes.DEEP_COLD_OCEAN);
	public static final int DEEP_FROZEN_OCEAN = BiomeRegistry.getID(UABiomes.DEEP_FROZEN_OCEAN);
	public static final int SUNFLOWER_PLAINS = BiomeRegistry.getID(UABiomes.SUNFLOWER_PLAINS);
	public static final int NETHERLAND = BiomeRegistry.getID(UABiomes.NETHERLAND);
	public static final int END_FIELD = BiomeRegistry.getID(UABiomes.END_FIELD);
	public static final int BARREN_END_FIELD = BiomeRegistry.getID(UABiomes.BARREN_END_FIELD);
	public static final int FROZEN_DESERT = BiomeRegistry.getID(UABiomes.FROZEN_DESERT);
	public static final int STONE_PLAINS = BiomeRegistry.getID(UABiomes.STONE_PLAINS);

	public static final int VANILLA_OCEAN = BiomeRegistry.getID(Biomes.OCEAN);
	public static final int VANILLA_DEEP_OCEAN = BiomeRegistry.getID(Biomes.DEEP_OCEAN);

	public static Set<Biome> frozenBiomes = ImmutableSet.of(UABiomes.DEEP_FROZEN_OCEAN, UABiomes.FROZEN_OCEAN, UABiomes.ICE_SPIKES, UABiomes.FROZEN_DESERT, UABiomes.SNOWY_TAIGA, UABiomes.RELIC_SNOWY_TAIGA, UABiomes.SNOWY_ROCKY_TAIGA, UABiomes.SNOWY_TUNDRA);

	public static Set<Biome> coldOceanBiomes = ImmutableSet.of(UABiomes.DEEP_COLD_OCEAN, UABiomes.COLD_OCEAN);

	private static final Set<Biome> OCEAN_LIST = ImmutableSet.of(UABiomes.WARM_OCEAN, UABiomes.LUKEWARM_OCEAN, UABiomes.OCEAN, UABiomes.COLD_OCEAN, UABiomes.FROZEN_OCEAN, UABiomes.DEEP_WARM_OCEAN, UABiomes.DEEP_LUKEWARM_OCEAN, UABiomes.DEEP_OCEAN, UABiomes.DEEP_COLD_OCEAN, UABiomes.DEEP_FROZEN_OCEAN);

	public static final Map<Pair<Integer, Integer>, Integer> biomesComboToEdge = new HashMap<Pair<Integer, Integer>, Integer>();


	public static void setBiomeEdgeMap()
	{
		biomesComboToEdge.clear();

		if (UltraAmplified.UAConfig.rockyField.get())
		{
			biomesComboToEdge.put(new Pair<Integer, Integer>(DESERT, SNOWY_TUNDRA), WOODED_ROCKY_FIELD);
		}

		if (UltraAmplified.UAConfig.plains.get())
		{
			biomesComboToEdge.put(new Pair<Integer, Integer>(SWAMP, DESERT), PLAINS);
			biomesComboToEdge.put(new Pair<Integer, Integer>(SWAMP, SNOWY_TUNDRA), PLAINS);
			biomesComboToEdge.put(new Pair<Integer, Integer>(SWAMP, SNOWY_TAIGA), PLAINS);
		}

		//adds a lot of potential biomes to certain biomes requirements for borders
		for (Biome biome : BiomeRegistry)
		{
			int biomeID = BiomeRegistry.getID(biome);

			if (UltraAmplified.UAConfig.savanna.get())
			{
				if (biomeID != NETHERLAND && biomeID != SAVANNA)
				{
					biomesComboToEdge.put(new Pair<Integer, Integer>(NETHERLAND, biomeID), SAVANNA);
				}
			}

			if (biomeID != END_FIELD && biomeID != BARREN_END_FIELD)
			{
				biomesComboToEdge.put(new Pair<Integer, Integer>(biomeID, END_FIELD), BARREN_END_FIELD);
			}

			if (biomeID != JUNGLE && biomeID != BAMBOO_JUNGLE)
			{
				biomesComboToEdge.put(new Pair<Integer, Integer>(JUNGLE, biomeID), JUNGLE_EDGE);
			}

			if (UltraAmplified.UAConfig.frozenDesert.get())
			{
				if (BiomeRegistry.getValue(biomeID).getCategory() == Biome.Category.ICY && !BiomeGenHelper.isOcean(biomeID))
				{
					biomesComboToEdge.put(new Pair<Integer, Integer>(VANILLA_OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(VANILLA_DEEP_OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(WARM_OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(LUKEWARM_OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(COLD_OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(FROZEN_OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(DEEP_WARM_OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(DEEP_LUKEWARM_OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(DEEP_OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(DEEP_COLD_OCEAN, biomeID), FROZEN_DESERT);
					biomesComboToEdge.put(new Pair<Integer, Integer>(DEEP_FROZEN_OCEAN, biomeID), FROZEN_DESERT);
				}
			}

			if (UltraAmplified.UAConfig.stonePlains.get())
			{
				if (BiomeRegistry.getValue(biomeID).getCategory() == Biome.Category.EXTREME_HILLS)
				{
					biomesComboToEdge.put(new Pair<Integer, Integer>(VANILLA_OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(VANILLA_DEEP_OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(WARM_OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(LUKEWARM_OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(COLD_OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(FROZEN_OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(DEEP_WARM_OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(DEEP_LUKEWARM_OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(DEEP_OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(DEEP_COLD_OCEAN, biomeID), STONE_PLAINS);
					biomesComboToEdge.put(new Pair<Integer, Integer>(DEEP_FROZEN_OCEAN, biomeID), STONE_PLAINS);
				}
			}

			if (biomeID != WOODED_BADLANDS && biomeID != SANDLESS_BADLANDS)
			{
				biomesComboToEdge.put(new Pair<Integer, Integer>(WOODED_BADLANDS, biomeID), BADLANDS);
				biomesComboToEdge.put(new Pair<Integer, Integer>(SANDLESS_BADLANDS, biomeID), BADLANDS);
			}

			if (BiomeRegistry.getValue(biomeID).getCategory() != Biome.Category.TAIGA)
			{
				biomesComboToEdge.put(new Pair<Integer, Integer>(GIANT_TREE_TAIGA, biomeID), TAIGA);
			}
		}
	}


	public static boolean isOcean(Biome biomeIn)
	{
		return OCEAN_LIST.contains(biomeIn);
	}


	public static boolean isOcean(int biomeIn)
	{
		return biomeIn == VANILLA_OCEAN || biomeIn == VANILLA_DEEP_OCEAN || biomeIn == WARM_OCEAN || biomeIn == LUKEWARM_OCEAN || biomeIn == OCEAN || biomeIn == COLD_OCEAN || biomeIn == FROZEN_OCEAN || biomeIn == DEEP_WARM_OCEAN || biomeIn == DEEP_LUKEWARM_OCEAN || biomeIn == DEEP_OCEAN || biomeIn == DEEP_COLD_OCEAN || biomeIn == DEEP_FROZEN_OCEAN;
	}


	public static boolean isShallowOcean(int biomeIn)
	{
		return biomeIn == VANILLA_OCEAN || biomeIn == WARM_OCEAN || biomeIn == LUKEWARM_OCEAN || biomeIn == OCEAN || biomeIn == COLD_OCEAN || biomeIn == FROZEN_OCEAN;
	}


	public static boolean areUABiomesSimilar(int biome1ID, int biome2ID)
	{
		if (biome1ID == biome2ID)
		{
			return true;
		}
		else
		{
			Biome biome1 = BiomeRegistry.getValue(biome1ID);
			Biome biome2 = BiomeRegistry.getValue(biome2ID);
			if (biome1 != null && biome2 != null)
			{
				return biome1.getCategory() == biome2.getCategory();
			}
			else
			{
				return false;
			}
		}
	}
}
