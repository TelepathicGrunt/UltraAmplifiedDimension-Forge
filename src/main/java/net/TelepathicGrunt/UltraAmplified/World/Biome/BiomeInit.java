package net.TelepathicGrunt.UltraAmplified.World.Biome;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import jline.internal.Log;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeBambooForestUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeColdBeachUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeDesertUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeEndUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeForestUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeBirchForestMutatedUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeHellUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeHillsUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeJungleUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeMesaUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeMushroomIslandUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomePlainsUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeRoofedForestMutatedUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeSavannaUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeSavannaMutatedUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeSnowUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeStoneBeachUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeSwampUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeTaigaUA;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class BiomeInit {
	
	//static variable to hold all biomes and their properties
	
	public static final Biome BiomeBambooForest = new BiomeBambooForestUA();
	public static final Biome BiomePlains = new BiomePlainsUA(false, new Biome.BiomeProperties("Plains").setBaseHeight(0.1F).setHeightVariation(0.2F).setTemperature(0.8F).setRainfall(0.4F));
	public static final Biome BiomeDesert = new BiomeDesertUA(new Biome.BiomeProperties("Desert").setBaseHeight(0.2F).setHeightVariation(0.3F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeForest = new BiomeForestUA(BiomeForestUA.Type.NORMAL, (new Biome.BiomeProperties("Forest")).setBaseHeight(0.2F).setHeightVariation(0.3F).setTemperature(0.7F).setRainfall(0.8F));
	public static final Biome BiomeTaiga = new BiomeTaigaUA(BiomeTaigaUA.Type.NORMAL, (new Biome.BiomeProperties("Taiga")).setBaseHeight(0.2F).setHeightVariation(3.0F).setTemperature(0.44F).setRainfall(0.8F));
	public static final Biome BiomeExtremeHills = new BiomeHillsUA(BiomeHillsUA.Type.NORMAL, (new Biome.BiomeProperties("Extreme Hills")).setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.35F).setRainfall(0.3F));
	public static final Biome BiomeSwampland = new BiomeSwampUA((new Biome.BiomeProperties("Swampland")).setBaseHeight(-0.4F).setHeightVariation(0.2F).setTemperature(0.8F).setRainfall(0.9F).setWaterColor(14745518));
	public static final Biome BiomeNether =  new BiomeHellUA((new Biome.BiomeProperties("Nether")).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled().setWaterColor(14090240));
	public static final Biome BiomeEnd = new BiomeEndUA((new Biome.BiomeProperties("The End")).setRainDisabled().setWaterColor(16711840));
	public static final Biome BiomeIceFlats = new BiomeSnowUA(false, (new Biome.BiomeProperties("Ice Plains")).setBaseHeight(0.125F).setHeightVariation(0.2F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled());
	public static final Biome BiomeIceMountain = new BiomeSnowUA(false, (new Biome.BiomeProperties("Ice Mountains")).setBaseHeight(0.55F).setHeightVariation(0.3F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled());
	public static final Biome BiomeMushroomIsland= new BiomeMushroomIslandUA((new Biome.BiomeProperties("Mushroom")).setBaseHeight(0.2F).setHeightVariation(0.3F).setTemperature(0.9F).setRainfall(1.0F));
	public static final Biome BiomeDesertHills = new BiomeDesertUA((new Biome.BiomeProperties("Desert Hills")).setBaseHeight(0.6F).setHeightVariation(0.3F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeForestHills = new BiomeForestUA(BiomeForestUA.Type.NORMAL, (new Biome.BiomeProperties("Forest Hills")).setBaseHeight(0.6F).setHeightVariation(0.3F).setTemperature(0.7F).setRainfall(0.8F));
	public static final Biome BiomeTaigaHills = new BiomeTaigaUA(BiomeTaigaUA.Type.NORMAL, (new Biome.BiomeProperties("Taiga Hills")).setTemperature(0.44F).setRainfall(0.8F).setBaseHeight(0.6F).setHeightVariation(0.3F));
	public static final Biome BiomeJungle = new BiomeJungleUA(false, (new Biome.BiomeProperties("Jungle")).setTemperature(0.95F).setRainfall(0.9F));
	public static final Biome BiomeJungleHills = new BiomeJungleUA(false, (new Biome.BiomeProperties("Jungle Hills")).setBaseHeight(0.6F).setHeightVariation(0.3F).setTemperature(0.95F).setRainfall(0.9F));
	public static final Biome BiomeJungleEdge = new BiomeJungleUA(true, (new Biome.BiomeProperties("Jungle Edge")).setTemperature(0.95F).setRainfall(0.8F));
	public static final Biome BiomeStoneBeach = new BiomeStoneBeachUA((new Biome.BiomeProperties("Stone Beach")).setBaseHeight(0.1F).setHeightVariation(0.8F).setTemperature(0.44F).setRainfall(0.3F));
	public static final Biome BiomeColdBeach = new BiomeColdBeachUA(new Biome.BiomeProperties("Cold Beach").setBaseHeight(0.0F).setHeightVariation(0.025F).setTemperature(0.0F).setRainfall(0.3F).setSnowEnabled());
	public static final Biome BiomeBirchForest = new BiomeForestUA(BiomeForestUA.Type.BIRCH, (new Biome.BiomeProperties("Birch Forest")).setTemperature(0.6F).setRainfall(0.6F));
	public static final Biome BiomeBirchForestHills = new BiomeForestUA(BiomeForestUA.Type.BIRCH, (new Biome.BiomeProperties("Birch Forest Hills")).setBaseHeight(0.6F).setHeightVariation(0.3F).setTemperature(0.6F).setRainfall(0.6F));
	public static final Biome BiomeRoofedForest = new BiomeForestUA(BiomeForestUA.Type.ROOFED, (new Biome.BiomeProperties("Roofed Forest")).setTemperature(0.7F).setRainfall(0.8F));
	public static final Biome BiomeColdTaiga = new BiomeTaigaUA(BiomeTaigaUA.Type.NORMAL, (new Biome.BiomeProperties("Cold Taiga")).setBaseHeight(0.2F).setHeightVariation(0.2F).setTemperature(-0.5F).setRainfall(0.4F).setSnowEnabled());
	public static final Biome BiomeColdTaigaHills = new BiomeTaigaUA(BiomeTaigaUA.Type.NORMAL, (new Biome.BiomeProperties("Cold Taiga Hills")).setBaseHeight(0.6F).setHeightVariation(0.3F).setTemperature(-0.5F).setRainfall(0.4F).setSnowEnabled());
	public static final Biome BiomeRedwoodTaiga = new BiomeTaigaUA(BiomeTaigaUA.Type.MEGA, (new Biome.BiomeProperties("Mega Taiga")).setTemperature(0.5F).setRainfall(0.8F).setBaseHeight(0.2F).setHeightVariation(0.2F));
	public static final Biome BiomeRedwoodTaigaHills = new BiomeTaigaUA(BiomeTaigaUA.Type.MEGA, (new Biome.BiomeProperties("Mega Taiga Hills")).setBaseHeight(0.6F).setHeightVariation(0.3F).setTemperature(0.5F).setRainfall(0.8F));
	public static final Biome BiomeExtremeHillsPlus = new BiomeHillsUA(BiomeHillsUA.Type.EXTRA_TREES, (new Biome.BiomeProperties("Extreme Hills+")).setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.35F).setRainfall(0.3F));
	public static final Biome BiomeSavanna = new BiomeSavannaUA((new Biome.BiomeProperties("Savanna")).setBaseHeight(0.125F).setHeightVariation(0.2F).setTemperature(1.2F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeSavannaRock = new BiomeSavannaUA((new Biome.BiomeProperties("Savanna Plateau")).setBaseHeight(1.5F).setHeightVariation(0.2F).setTemperature(1.0F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeMesa = new BiomeMesaUA(false, false, (new Biome.BiomeProperties("Mesa")).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeMesaRock = new BiomeMesaUA(false, true, (new Biome.BiomeProperties("Mesa Plateau F")).setBaseHeight(1.5F).setHeightVariation(0.2F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeMesaClearRock = new BiomeMesaUA(false, false, (new Biome.BiomeProperties("Mesa Plateau")).setBaseHeight(1.5F).setHeightVariation(0.2F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomePlainsM = new BiomePlainsUA(true, (new Biome.BiomeProperties("Sunflower Plains")).setBaseBiome("Plains").setBaseHeight(0.125F).setHeightVariation(0.2F).setTemperature(0.8F).setRainfall(0.4F));
	public static final Biome BiomeDesertM = new BiomeDesertUA((new Biome.BiomeProperties("Desert M")).setBaseBiome("Desert").setBaseHeight(0.225F).setHeightVariation(0.25F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeExtremeHillsM = new BiomeHillsUA(BiomeHillsUA.Type.MUTATED, (new Biome.BiomeProperties("Extreme Hills M")).setBaseBiome("Extreme Hills").setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.5F).setRainfall(0.3F));
	public static final Biome BiomeForestM = new BiomeForestUA(BiomeForestUA.Type.FLOWER, (new Biome.BiomeProperties("Flower Forest")).setBaseBiome("Forest").setHeightVariation(0.4F).setTemperature(0.7F).setRainfall(0.8F));
	public static final Biome BiomeTaigaM = new BiomeTaigaUA(BiomeTaigaUA.Type.NORMAL, (new Biome.BiomeProperties("Taiga M")).setBaseBiome("Taiga").setBaseHeight(0.3F).setHeightVariation(0.4F).setTemperature(0.44F).setRainfall(0.8F));
	public static final Biome BiomeSwamplandM = new BiomeSwampUA((new Biome.BiomeProperties("Swampland M")).setBaseBiome("Swampland").setBaseHeight(-0.1F).setHeightVariation(0.3F).setTemperature(0.8F).setRainfall(0.9F).setWaterColor(14745518));
	public static final Biome BiomeIceSpike = new BiomeSnowUA(true, (new Biome.BiomeProperties("Ice Plains Spikes")).setBaseBiome("Ice Plains").setBaseHeight(0.425F).setHeightVariation(0.45000002F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled());
	public static final Biome BiomeJungleM = new BiomeJungleUA(false, (new Biome.BiomeProperties("Jungle M")).setBaseBiome("Jungle").setBaseHeight(0.2F).setHeightVariation(0.4F).setTemperature(0.95F).setRainfall(0.9F));
	public static final Biome BiomeJungleEdgeM = new BiomeJungleUA(true, (new Biome.BiomeProperties("JungleEdge M")).setBaseBiome("Jungle Edge").setBaseHeight(0.2F).setHeightVariation(0.4F).setTemperature(0.95F).setRainfall(0.8F));
	public static final Biome BiomeBirchForestM = new BiomeBirchForestMutatedUA((new Biome.BiomeProperties("Birch Forest M")).setBaseBiome("Birch Forest").setBaseHeight(0.2F).setHeightVariation(0.4F).setTemperature(0.6F).setRainfall(0.6F));
	public static final Biome BiomeBirchForestHillsM = new BiomeBirchForestMutatedUA((new Biome.BiomeProperties("Birch Forest Hills M")).setBaseBiome("Birch Forest Hills").setBaseHeight(0.6F).setHeightVariation(0.5F).setTemperature(0.6F).setRainfall(0.6F));
	public static final Biome BiomeRoofedForestM = new BiomeRoofedForestMutatedUA((new Biome.BiomeProperties("Roofed Forest M")).setBaseBiome("Roofed Forest").setBaseHeight(0.2F).setHeightVariation(0.4F).setTemperature(0.7F).setRainfall(0.8F));
	public static final Biome BiomeColdTaigaM = new BiomeTaigaUA(BiomeTaigaUA.Type.NORMAL, (new Biome.BiomeProperties("Cold Taiga M")).setBaseBiome("Taiga Cold").setBaseHeight(0.3F).setHeightVariation(0.4F).setTemperature(-0.5F).setRainfall(0.4F).setSnowEnabled());
	public static final Biome BiomeRedwoodTaigaM = new BiomeTaigaUA(BiomeTaigaUA.Type.MEGA_SPRUCE, (new Biome.BiomeProperties("Mega Spruce Taiga")).setBaseBiome("Redwood Taiga").setBaseHeight(0.2F).setHeightVariation(0.2F).setTemperature(0.5F).setRainfall(0.8F));
	public static final Biome BiomeRedwoodTaigaHillsM = new BiomeTaigaUA(BiomeTaigaUA.Type.MEGA_SPRUCE, (new Biome.BiomeProperties("Redwood Taiga Hills M")).setBaseBiome("Redwood Taiga Hills").setBaseHeight(0.6F).setHeightVariation(0.2F).setTemperature(0.5F).setRainfall(0.8F));
	public static final Biome BiomeExtremeHillsPlusM = new BiomeHillsUA(BiomeHillsUA.Type.MUTATED, (new Biome.BiomeProperties("Extreme Hills+ M")).setBaseBiome("Extreme Hills+").setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.5F).setRainfall(0.3F));
	public static final Biome BiomeSavannaM = new BiomeSavannaMutatedUA((new Biome.BiomeProperties("Savanna M")).setBaseBiome("Savanna").setBaseHeight(0.3625F).setHeightVariation(1.225F).setTemperature(1.1F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeSavannaRockM = new BiomeSavannaMutatedUA((new Biome.BiomeProperties("Savanna Plateau M")).setBaseBiome("Savanna Rock").setBaseHeight(1.05F).setHeightVariation(1.2125001F).setTemperature(1.0F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeMesaBryce = new BiomeMesaUA(true, false, (new Biome.BiomeProperties("Mesa Bryce")).setBaseBiome("Mesa").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeMesaRockM = new BiomeMesaUA(false, true, (new Biome.BiomeProperties("Mesa Plateau F M")).setBaseBiome("Mesa Rock").setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
	public static final Biome BiomeMesaClearRockM = new BiomeMesaUA(false, false, (new Biome.BiomeProperties("Mesa Plateau M")).setBaseBiome("Mesa Clear Rock").setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
     
	//registers the biomes so they now exist in the registry along with their types
	public static void registerBiomes() {
		
		initBiome(BiomeBambooForest, "Bamboo Forest", BiomeType.WARM, Type.DENSE, Type.WET, Type.FOREST);
		initBiome(BiomePlains, "Plains", BiomeType.WARM, Type.PLAINS);
		initBiome(BiomeDesert, "Desert", BiomeType.DESERT, Type.DRY, Type.HOT, Type.SANDY);
		initBiome(BiomeForest, "Forest", BiomeType.WARM, Type.FOREST);
		initBiome(BiomeTaiga, "Taiga", BiomeType.COOL, Type.CONIFEROUS, Type.FOREST);
		initBiome(BiomeExtremeHills, "Extreme Hills", BiomeType.COOL, Type.MOUNTAIN, Type.HILLS);
		initBiome(BiomeSwampland, "Swampland", BiomeType.WARM, Type.WET, Type.SWAMP);
		initBiome(BiomeNether, "Nether", BiomeType.DESERT, Type.NETHER, Type.HOT, Type.DRY);
		initBiome(BiomeEnd, "The End", BiomeType.COOL, Type.END, Type.SPOOKY, Type.COLD, Type.DRY);
		initBiome(BiomeIceFlats, "Ice Plains", BiomeType.ICY, Type.COLD, Type.WASTELAND, Type.SNOWY);
		initBiome(BiomeIceMountain, "Ice Mountain", BiomeType.ICY, Type.COLD, Type.MOUNTAIN, Type.SNOWY);
		initBiome(BiomeMushroomIsland, "Mushroom", BiomeType.WARM, Type.MAGICAL, Type.MUSHROOM, Type.RARE);
		initBiome(BiomeDesertHills, "Desert Hills", BiomeType.DESERT, Type.HOT, Type.DRY, Type.HILLS, Type.SANDY);
		initBiome(BiomeForestHills, "Forest Hills", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS);
		initBiome(BiomeTaigaHills, "Taiga Hills", BiomeType.COOL, Type.CONIFEROUS);
		initBiome(BiomeJungle, "Jungle", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE);
		initBiome(BiomeJungleHills, "Jungle Hills", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.HILLS);
		initBiome(BiomeJungleEdge, "Jungle Edge", BiomeType.WARM, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.RARE);
		initBiome(BiomeStoneBeach, "Stone Beach", BiomeType.COOL, Type.BEACH);
		initBiome(BiomeColdBeach, "Cold Beach", BiomeType.ICY, Type.BEACH, Type.SNOWY, Type.COLD);
		initBiome(BiomeBirchForest, "Birch Forest", BiomeType.WARM, Type.FOREST);
		initBiome(BiomeBirchForestHills, "Birch Forest Hills", BiomeType.WARM, Type.FOREST, Type.HILLS);
		initBiome(BiomeRoofedForest, "Roofed Forest", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.SPOOKY);
		initBiome(BiomeColdTaiga, "Cold Taiga", BiomeType.ICY, Type.FOREST,Type.CONIFEROUS, Type.SNOWY, Type.COLD);
		initBiome(BiomeColdTaigaHills, "Cold Taiga Hills", BiomeType.ICY, Type.FOREST, Type.CONIFEROUS, Type.SNOWY, Type.COLD, Type.HILLS);
		initBiome(BiomeRedwoodTaiga, "Mega Taiga", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST);
		initBiome(BiomeRedwoodTaigaHills, "Mega Taiga Hills", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.HILLS);
		initBiome(BiomeExtremeHillsPlus, "Extreme Hills+", BiomeType.COOL, Type.MOUNTAIN, Type.FOREST, Type.SPARSE);
		initBiome(BiomeSavanna, "Savanna", BiomeType.DESERT, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE);
		initBiome(BiomeSavannaRock, "Savanna Plateau", BiomeType.DESERT, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE, Type.RARE);
		initBiome(BiomeMesa, "Mesa", BiomeType.DESERT, Type.MESA, Type.SANDY);
		initBiome(BiomeMesaRock, "Mesa Plateau F", BiomeType.DESERT, Type.MESA, Type.SANDY, Type.SPARSE);
		initBiome(BiomeMesaClearRock, "Mesa Plateau", BiomeType.DESERT, Type.MESA, Type.SANDY);
		initBiome(BiomePlainsM, "Sunflower Plains", BiomeType.WARM, Type.PLAINS, Type.RARE);
		initBiome(BiomeDesertM, "Desert M", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SANDY, Type.RARE);
		initBiome(BiomeExtremeHillsM, "Extreme Hills M", BiomeType.COOL, Type.MOUNTAIN, Type.SPARSE, Type.RARE);
		initBiome(BiomeForestM, "Flower Forest", BiomeType.WARM, Type.FOREST, Type.HILLS, Type.RARE);
		initBiome(BiomeTaigaM, "Taiga M", BiomeType.COOL, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.MOUNTAIN, Type.RARE);
		initBiome(BiomeSwamplandM, "Swampland M", BiomeType.WARM, Type.SWAMP, Type.WET, Type.HILLS, Type.RARE);
		initBiome(BiomeIceSpike, "Ice Spikes", BiomeType.ICY, Type.COLD, Type.SNOWY, Type.HILLS, Type.RARE);
		initBiome(BiomeJungleM, "Jungle M", BiomeType.WARM, Type.HOT, Type.WET, Type.JUNGLE, Type.MOUNTAIN, Type.RARE, Type.DENSE);
		initBiome(BiomeJungleEdgeM, "Jungle Edge M", BiomeType.WARM, Type.HOT, Type.SPARSE, Type.HILLS, Type.JUNGLE, Type.RARE);
		initBiome(BiomeBirchForestM, "Birch Forest M", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.HILLS, Type.RARE);
		initBiome(BiomeBirchForestHillsM, "Birch Forest Hills M", BiomeType.WARM, Type.FOREST, Type.DENSE, Type.MOUNTAIN, Type.RARE);
		initBiome(BiomeRoofedForestM, "Roofed Forest M", BiomeType.WARM, Type.SPOOKY, Type.DENSE, Type.FOREST, Type.MOUNTAIN, Type.RARE);
		initBiome(BiomeColdTaigaM, "Cold Taiga M", BiomeType.ICY, Type.COLD, Type.SNOWY, Type.CONIFEROUS, Type.MOUNTAIN, Type.FOREST, Type.RARE);
		initBiome(BiomeRedwoodTaigaM, "Mega Spruce Taiga", BiomeType.COOL, Type.DENSE, Type.FOREST, Type.RARE);
		initBiome(BiomeRedwoodTaigaHillsM, "Redwood Taiga Hills M", BiomeType.COOL, Type.DENSE, Type.FOREST, Type.HILLS, Type.RARE);
		initBiome(BiomeExtremeHillsPlusM, "Extreme Hill+ M", BiomeType.COOL, Type.MOUNTAIN, Type.SPARSE, Type.RARE);
		initBiome(BiomeSavannaM, "Savanna M", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.MOUNTAIN, Type.RARE);
		initBiome(BiomeSavannaRockM, "Savanna Plateau M", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.HILLS, Type.RARE);
		initBiome(BiomeMesaBryce, "Mesa Bryce", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
		initBiome(BiomeMesaRockM, "Mesa Plateau F M", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.HILLS, Type.RARE);
		initBiome(BiomeMesaClearRockM, "Mesa Plateau M", BiomeType.DESERT, Type.HOT, Type.DRY, Type.SPARSE, Type.MESA, Type.MOUNTAIN, Type.RARE);
		
		mapBiomes();
	}
	
	//adds biome to registry with their type
	private static Biome initBiome(Biome biome, String name, BiomeType biomeType, Type... types) {
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		ForgeRegistries.BIOMES.getKey(biome);
		BiomeDictionary.addTypes(biome, types);
		
		return biome;
	}
	
	
	//Handles conversion between M form and non-M form biomes. 
	//Necessary since forge does not have a way of doing this and vanilla uses a map that is initialized at a bad time/place in reference to forge mods
	//Also better as I can specify new M variant biomes or make any biome an M variant of another unrelated biome.
    public static final Map<Biome,Biome> BASE_TO_MUTATION_MAP = new HashMap<>();
    
    private static void mapBiomes() {
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
	
	
}
