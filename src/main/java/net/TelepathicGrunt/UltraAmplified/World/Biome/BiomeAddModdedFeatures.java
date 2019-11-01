package net.telepathicgrunt.ultraamplified.world.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.BlockWithContextFeature;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.BushFeature;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.DoublePlantConfig;
import net.minecraft.world.gen.feature.DoublePlantFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GrassFeature;
import net.minecraft.world.gen.feature.GrassFeatureConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.LakesFeature;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ScatteredPlantFeature;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.SphereReplaceFeature;
import net.minecraft.world.gen.feature.SpringFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.Structures;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeAddModdedFeatures {

	private static ForgeRegistry<Feature<?>> FeatureRegistry = ((ForgeRegistry<Feature<?>>) ForgeRegistries.FEATURES);
	private static ForgeRegistry<EntityType<?>> EntityRegistry = ((ForgeRegistry<EntityType<?>>) ForgeRegistries.ENTITIES);
	private static ForgeRegistry<Block> BlockRegistry = ((ForgeRegistry<Block>) ForgeRegistries.BLOCKS);

	// workaround due to some people registering structures under minecraft namespace
	private static ArrayList<Structure<?>> listOfVanillaStructures = new ArrayList<Structure<?>>() {
		private static final long serialVersionUID = 1L;
		{
			add(Structures.MINESHAFT);
			add(Structures.PILLAGER_OUTPOST);
			add(Structures.FORTRESS);
			add(Structures.STRONGHOLD);
			add(Structures.JUNGLE_PYRAMID);
			add(Structures.OCEAN_RUIN);
			add(Structures.DESERT_PYRAMID);
			add(Structures.IGLOO);
			add(Structures.SWAMP_HUT);
			add(Structures.MONUMENT);
			add(Structures.ENDCITY);
			add(Structures.MANSION);
			add(Structures.BURIED_TREASURE);
			add(Structures.SHIPWRECK);
			add(Structures.VILLAGE);
		}
	};

	// workaround due to some people registering structures under minecraft namespace
	private static ArrayList<Feature<?>> listOfVanillaStructureFeatures = new ArrayList<Feature<?>>() {
		private static final long serialVersionUID = 11L;
		{
			add(Feature.MINESHAFT);
			add(Feature.PILLAGER_OUTPOST);
			add(Feature.NETHER_BRIDGE);
			add(Feature.STRONGHOLD);
			add(Feature.JUNGLE_TEMPLE);
			add(Feature.OCEAN_RUIN);
			add(Feature.DESERT_PYRAMID);
			add(Feature.IGLOO);
			add(Feature.SWAMP_HUT);
			add(Feature.OCEAN_MONUMENT);
			add(Feature.END_CITY);
			add(Feature.WOODLAND_MANSION);
			add(Feature.BURIED_TREASURE);
			add(Feature.SHIPWRECK);
			add(Feature.VILLAGE);
		}
	};

	// workaround due to some people registering mobs under minecraft namespace
	private static ArrayList<EntityType<?>> listOfVanillaMobs = new ArrayList<EntityType<?>>() {
		private static final long serialVersionUID = 12L;
		{
			add(EntityType.BAT);
			add(EntityType.BLAZE);
			add(EntityType.CAT);
			add(EntityType.CHICKEN);
			add(EntityType.COD);
			add(EntityType.COW);
			add(EntityType.CREEPER);
			add(EntityType.DONKEY);
			add(EntityType.DOLPHIN);
			add(EntityType.DROWNED);
			add(EntityType.ENDERMAN);
			add(EntityType.ENDERMITE);
			add(EntityType.FOX);
			add(EntityType.GHAST);
			add(EntityType.HORSE);
			add(EntityType.HUSK);
			add(EntityType.LLAMA);
			add(EntityType.MAGMA_CUBE);
			add(EntityType.MULE);
			add(EntityType.MOOSHROOM);
			add(EntityType.OCELOT);
			add(EntityType.PANDA);
			add(EntityType.PARROT);
			add(EntityType.PIG);
			add(EntityType.PUFFERFISH);
			add(EntityType.ZOMBIE_PIGMAN);
			add(EntityType.POLAR_BEAR);
			add(EntityType.RABBIT);
			add(EntityType.SALMON);
			add(EntityType.SHEEP);
			add(EntityType.SKELETON);
			add(EntityType.SLIME);
			add(EntityType.SPIDER);
			add(EntityType.SQUID);
			add(EntityType.STRAY);
			add(EntityType.TROPICAL_FISH);
			add(EntityType.TURTLE);
			add(EntityType.WITCH);
			add(EntityType.WITHER_SKELETON);
			add(EntityType.WOLF);
			add(EntityType.ZOMBIE);
			add(EntityType.ZOMBIE_VILLAGER);
		}
	};


	// we have a list of vanilla biomes in the order to match the UA biome list in BiomeInit.
	// Thus we only need to iterate through the vanilla list to add modded features and mobs to UA biomes
	private static Biome[] vanillaBiomesToCheck = new Biome[] { 
			Biomes.PLAINS, Biomes.DESERT, Biomes.MOUNTAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.SWAMP, 
			Biomes.NETHER, Biomes.THE_END, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_MOUNTAINS, Biomes.MUSHROOM_FIELDS, 
			Biomes.DESERT_HILLS, Biomes.WOODED_HILLS, Biomes.TAIGA_HILLS, Biomes.BAMBOO_JUNGLE, 
			Biomes.BAMBOO_JUNGLE_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, 
			Biomes.STONE_SHORE, Biomes.SNOWY_BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS,
			Biomes.DARK_FOREST, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA,
			Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.WOODED_MOUNTAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU,
			Biomes.BADLANDS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.BADLANDS_PLATEAU, Biomes.SUNFLOWER_PLAINS,
			Biomes.DESERT_LAKES, Biomes.GRAVELLY_MOUNTAINS, Biomes.FLOWER_FOREST, Biomes.TAIGA_MOUNTAINS,
			Biomes.SWAMP_HILLS, Biomes.ICE_SPIKES, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE,
			Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.DARK_FOREST_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS,
			Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS,
			Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.ERODED_BADLANDS,
			Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.FROZEN_OCEAN,
			Biomes.COLD_OCEAN, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_FROZEN_OCEAN,
			Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_WARM_OCEAN 
	};

	private static List<String> blacklistedFeatureMods;
    private static List<String> blacklistedFeatureResourceLocations;
    
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents {

		@SubscribeEvent
		public static void Load(WorldEvent.Load event) {
			// makes sure the registry exists
			if (FeatureRegistry == null)
				throw new NullPointerException("Feature Registry not set");
			if (EntityRegistry == null)
				throw new NullPointerException("Entity Registry not set");
			if (BlockRegistry == null)
				throw new NullPointerException("Block Registry not set");

			// remove past modded feature/spawn additions by going through all UA biomes
			// helps keep what gets imported into the UA biomes on a per world basis.
			resetBiomesBackToDefault();

			//SET UP FEATURE/STRUCTURE BLACKLIST
			    //grabs what the user entered.
			    //is done as linked list so we can remove entries later if needed since Arrays.asList creates a fixed size list.
			    blacklistedFeatureMods = new ArrayList<String>();
			    blacklistedFeatureResourceLocations = new LinkedList<String>(Arrays.asList(ConfigUA.blacklistedStructureList.split(",")));
			    for(int i = 0; i < blacklistedFeatureResourceLocations.size(); i++) {
			    	blacklistedFeatureResourceLocations.set(i, blacklistedFeatureResourceLocations.get(i).trim());
			    }
			    
			    
			    //finds all strings that doesn't have a path and adds it to blacklistedFeatureMods as it's a mod's namespace
			    for(String entry : blacklistedFeatureResourceLocations) {
			    	if(entry.contains(":*")) {
			    		blacklistedFeatureMods.add(entry.split(":")[0]);
			    	}
			    }
			
			// we have a list of vanilla biomes in the order to match the UA biome list in BiomeInit.
			// Thus we only need to iterate through the vanilla list to add modded features and mobs to UA biomes
			for (int biomeIndex = 0; biomeIndex < vanillaBiomesToCheck.length; biomeIndex++) {
				addModdedFeatureAndSpawns(vanillaBiomesToCheck[biomeIndex], (BiomeUA) BiomeInit.getBiomeArray()[biomeIndex]);
			}
			
			//edge case as UA End biome will grab features from multiple end biomes
			addModdedFeatureAndSpawns(Biomes.SMALL_END_ISLANDS, (BiomeUA) BiomeInit.END);
			addModdedFeatureAndSpawns(Biomes.END_MIDLANDS, (BiomeUA) BiomeInit.END);
			addModdedFeatureAndSpawns(Biomes.END_HIGHLANDS, (BiomeUA) BiomeInit.END);
			addModdedFeatureAndSpawns(Biomes.END_BARRENS, (BiomeUA) BiomeInit.END);
			addModdedFeatureAndSpawns(Biomes.END_BARRENS, (BiomeUA) BiomeInit.BARREN_END_FIELD);
		}
	}

	/**
	 * Checks vanilla biomes for modded features and mobs and adds it to the
	 * corrisponding UA biome
	 * 
	 * @param vanillaBiome - Vanilla biome to check for modded features/mobs
	 * @param uaBiome      - UA biome to get modded features/mobs
	 */
	@SuppressWarnings("unlikely-arg-type")
	private static void addModdedFeatureAndSpawns(Biome vanillaBiome, BiomeUA uaBiome) {

		// FEATURES
		// loop through all decorations and checks to see if they have the minecraft namespace.
		// If not, it is a modded feature and we should add it to the UA biome
		for (Decoration decorationType : Decoration.values()) {
			for (ConfiguredFeature<?> feature : vanillaBiome.getFeatures(decorationType)) {
				//if feature is already registered (Same feature, placement, config, etc),
				//then skip this feature and move on to next one
				if(uaBiome.getFeatures(decorationType).contains(feature)) {
					break;
				}
				
				//bug fix. If config is not decorated feature config somehow, ignore the feature.
				if(!(feature.config instanceof DecoratedFeatureConfig)) {
					break;
				}
				
				DecoratedFeatureConfig insideConfig = (DecoratedFeatureConfig) feature.config;

				Feature<?> insideFeature = insideConfig.feature.feature;
				ResourceLocation rl = FeatureRegistry.getKey(insideFeature);
				if(rl == null) {
					continue;
				}
				String namespace = rl.getNamespace();
				
				
				// add feature form of structures
				// workaround due to some people registering structures under minecraft namespace
				if (ConfigUA.importModdedStructure && insideFeature instanceof Structure) {

					//check blacklist
					if (blacklistedFeatureMods.contains(namespace) || ConfigUA.blacklistedStructureList.contains(rl.toString())) {
						continue;
					}
					
					if (!listOfVanillaStructureFeatures.contains(insideFeature)) {
						uaBiome.addFeature(decorationType, feature);
					}
				}
				else if(ConfigUA.importModdedFeatures) {
					
					// modded ores are buried in the state variable within the 
					// OreFeature so we have to dig for it
					if (insideFeature instanceof OreFeature) {
						OreFeatureConfig oreConfig = (OreFeatureConfig) insideConfig.feature.config;
						if (checkIfBlockIsAllowed(oreConfig.state.getBlock())) {
							uaBiome.addFeature(decorationType, feature);
						}
					}
					// modded bushes are buried in the state variable within the 
					// BushFeature so we have to dig for it
					else if (insideFeature instanceof BushFeature) {
						BushConfig bushConfig = (BushConfig) insideConfig.feature.config;
						if (checkIfBlockIsAllowed(bushConfig.state.getBlock())) {
							uaBiome.addFeature(decorationType, feature);
						}
					}
					// modded double plants are buried in the state variable within the
					// DoublePlantFeature so we have to dig for it
					else if (insideFeature instanceof DoublePlantFeature) {
						DoublePlantConfig doublePlantConfig = (DoublePlantConfig) insideConfig.feature.config;
						if (checkIfBlockIsAllowed(doublePlantConfig.state.getBlock())) {
							uaBiome.addFeature(decorationType, feature);
						}
					}
					// modded scattered plants are buried in the state variable within the
					// ScatteredPlantFeature so we have to dig for it
					else if (insideFeature instanceof ScatteredPlantFeature) {
						if (checkIfBlockIsAllowed(((ScatteredPlantFeature)insideFeature).plant.getBlock())) {
							uaBiome.addFeature(decorationType, feature);
						}
					}
					// modded grass are buried in the state variable within the
					// GrassFeature so we have to dig for it
					else if (insideFeature instanceof GrassFeature) {
						GrassFeatureConfig bushConfig = (GrassFeatureConfig) insideConfig.feature.config;
						if (checkIfBlockIsAllowed(bushConfig.state.getBlock())) {
							uaBiome.addFeature(decorationType, feature);
						}
					}
					// modded lakes are buried in the state variable within the
					// LakeFeature so we have to dig for it
					else if (insideFeature instanceof LakesFeature) {
						LakesConfig lakeConfig = (LakesConfig) insideConfig.feature.config;

						if (checkIfBlockIsAllowed(lakeConfig.state.getBlock())) {
							uaBiome.addFeature(decorationType, feature);
						}
					}
					// modded springs are buried in the state variable within the 
					// SpringFeature so we have to dig for it
					else if (insideFeature instanceof SpringFeature) {
						LiquidsConfig springConfig = (LiquidsConfig) insideConfig.feature.config;

						if (checkIfBlockIsAllowed(springConfig.state.getBlockState().getBlock())) {
							uaBiome.addFeature(decorationType, feature);
						}
					}
					// modded single block are buried in the state variable within the
					// BlockWithContextFeature so we have to dig for it
					else if (insideFeature instanceof BlockWithContextFeature) {
						BlockWithContextConfig sphereReplaceConfig = (BlockWithContextConfig) insideConfig.feature.config;

						if (checkIfBlockIsAllowed(sphereReplaceConfig.toPlace.getBlock())) {
							uaBiome.addFeature(decorationType, feature);
						}
					}
					// modded trees are typically buried in the state variable within the
					// MultipleWithChanceRandomFeature so we have to dig for it
					else if (insideFeature instanceof MultipleWithChanceRandomFeature) {
						MultipleRandomFeatureConfig randConfig = (MultipleRandomFeatureConfig) insideConfig.feature.config;

						// check first feature in random feature as I assume people will always have
						// entire feature list be full of modded trees/features
						insideFeature = randConfig.features.get(0).feature;
						rl = FeatureRegistry.getKey(insideFeature);
						if (rl == null) {
							continue;
						}

						namespace = rl.getNamespace();
						if (!namespace.equals("minecraft")) {
							uaBiome.addFeature(decorationType, feature);
						}
					}
					
					// add all regular features but only if it doesn't have minecraft namespace
					// Everything above was edge cases.
					else if (!namespace.equals("minecraft")) {
						uaBiome.addFeature(decorationType, feature);
					}
				}
			}
		}

		// STRUCTURES
		// add structure to biome's structure list to allow generation and locate command to work
		for (Entry<Structure<?>, IFeatureConfig> structureEntry : vanillaBiome.structures.entrySet()) {
			//if structure is already registered,
			//then skip this structure and move on to next one
			if(uaBiome.structures.containsKey(structureEntry)) {
				break;
			}

			ResourceLocation rl = structureEntry.getKey().getRegistryName();
			String namespace = rl.getNamespace();
			if (blacklistedFeatureMods.contains(namespace) || ConfigUA.blacklistedStructureList.contains(rl.toString())) {
				continue;
			}
			
			// workaround due to some people registering structures under minecraft namespace
			if (ConfigUA.importModdedStructure && !listOfVanillaStructures.contains(structureEntry.getKey())) {
				uaBiome.addStructure(structureEntry);
			}
		}

		// MOBS
		// loop through all mobs and checks to see if they have the minecraft namespace.
		// If not, it is a modded mob and we should add it to the UA biome
		for (EntityClassification entityType : EntityClassification.values()) {
			for (SpawnListEntry spawnEntry : vanillaBiome.getSpawns(entityType)) {
				//if mob is already registered,
				//then skip this mob and move on to next one
				if(uaBiome.getSpawns(entityType).contains(spawnEntry)) {
					break;
				}

//				ResourceLocation rl = EntityRegistry.getKey(spawnEntry.entityType);
//				if(rl == null) {
//					continue;
//				}
//				String namespace = rl.getNamespace();

				// workaround due to some people registering mobs under minecraft namespace
				if (ConfigUA.importModdedMobs && !listOfVanillaMobs.contains(spawnEntry.entityType)) {
					uaBiome.addSpawn(entityType, spawnEntry);
				}
			}
		}
	}

	/**
	 * Removes modded features and mobs from the UA biomes so it only has minecraft
	 * and UA features/mobs
	 */
	private static void resetBiomesBackToDefault() {

		// loop through all features and mobs in UA Biomes and checks to see if they
		// have a minecraft or UA namespace.
		// If not, it is a modded feature and we should remove it from the UA biome
		for (Biome uaBiome : BiomeInit.getBiomeArray()) {
			
			// FEATURES
			// removes feature from UA biome
			for (Decoration decorationType : Decoration.values()) {
				for (int featureIndex = uaBiome.getFeatures(decorationType).size() - 1; featureIndex >= 0; featureIndex--) {
					
					//bug fix. If config is not decorated feature config somehow, ignore the feature.
					if(!(uaBiome.getFeatures(decorationType).get(featureIndex).config instanceof DecoratedFeatureConfig)) {
						break;
					}
					
					DecoratedFeatureConfig insideConfig = 
							(DecoratedFeatureConfig) uaBiome.getFeatures(decorationType).get(featureIndex).config;

					Feature<?> insideFeature = insideConfig.feature.feature;
					ResourceLocation rl = FeatureRegistry.getKey(insideFeature);
					if (rl == null) {
						continue;
					}
					String namespace = rl.getNamespace();

					
					// workaround due to some people registering structures under minecraft namespace
					if (insideFeature instanceof Structure) {
						if (!listOfVanillaStructureFeatures.contains(insideFeature)
								&& !namespace.equals(UltraAmplified.MODID)) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					// modded ores are buried in the state variable within the
					// OreFeature so we have to dig for it
					else if (insideFeature instanceof OreFeature) {
						OreFeatureConfig oreConfig = (OreFeatureConfig) insideConfig.feature.config;
						if (checkIfBlockIsAllowed(oreConfig.state.getBlock())) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					// modded flowers are buried in the state variable within the
					// BushFeature so we have to dig for it
					else if (insideFeature instanceof BushFeature) {
						BushConfig bushConfig = (BushConfig) insideConfig.feature.config;
						if (checkIfBlockIsAllowed(bushConfig.state.getBlock())) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					// modded double plants are buried in the state variable within the
					// DoublePlantFeature so we have to dig for it
					else if (insideFeature instanceof DoublePlantFeature) {
						DoublePlantConfig doublePlantConfig = (DoublePlantConfig) insideConfig.feature.config;
						if (checkIfBlockIsAllowed(doublePlantConfig.state.getBlock())) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					// modded scattered plants are buried in the state variable within the
					// ScatteredPlantFeature so we have to dig for it
					else if (insideFeature instanceof ScatteredPlantFeature) {
						if (checkIfBlockIsAllowed(((ScatteredPlantFeature)insideFeature).plant.getBlock())) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					// modded grass are buried in the state variable within the
					// GrassFeature so we have to dig for it
					else if (insideFeature instanceof GrassFeature) {
						GrassFeatureConfig grassConfig = (GrassFeatureConfig) insideConfig.feature.config;

						if (checkIfBlockIsAllowed(grassConfig.state.getBlock())) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					// modded disk are buried in the state variable within the
					// SphereReplaceFeature so we have to dig for it
					else if (insideFeature instanceof SphereReplaceFeature) {
						SphereReplaceConfig sphereReplaceConfig = (SphereReplaceConfig) insideConfig.feature.config;

						if (checkIfBlockIsAllowed(sphereReplaceConfig.state.getBlock())) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					// modded single block are buried in the state variable within the
					// BlockWithContextFeature so we have to dig for it
					else if (insideFeature instanceof BlockWithContextFeature) {
						BlockWithContextConfig sphereReplaceConfig = (BlockWithContextConfig) insideConfig.feature.config;

						if (checkIfBlockIsAllowed(sphereReplaceConfig.toPlace.getBlock())) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					// modded lakes are buried in the state variable within the
					// LakeFeature so we have to dig for it
					else if (insideFeature instanceof LakesFeature) {
						LakesConfig lakeConfig = (LakesConfig) insideConfig.feature.config;

						if (checkIfBlockIsAllowed(lakeConfig.state.getBlock())) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					// modded springs are buried in the state variable within the
					// SpringFeature so we have to dig for it
					else if (insideFeature instanceof SpringFeature) {
						LiquidsConfig springConfig = (LiquidsConfig) insideConfig.feature.config;

						if (checkIfBlockIsAllowed(springConfig.state.getBlockState().getBlock())) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					// modded trees are typically buried in the state variable within the
					// MultipleWithChanceRandomFeature so we have to dig for it
					else if (insideFeature instanceof MultipleWithChanceRandomFeature) {
						MultipleRandomFeatureConfig randConfig = (MultipleRandomFeatureConfig) insideConfig.feature.config;

						// check first feature in random feature as I assume people will always have
						// entire feature list be full of modded trees/features
						insideFeature = randConfig.features.get(0).feature;
						rl = FeatureRegistry.getKey(insideFeature);
						if (rl == null) {
							continue;
						}

						namespace = rl.getNamespace();
						if (ConfigUA.importModdedFeatures && !namespace.equals("minecraft")) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					} 

					// removes all regular features but only if it doesn't have minecraft or UA namespace
					// Everything above was edge cases.
					else if (!namespace.equals("minecraft") && !namespace.equals(UltraAmplified.MODID)) {
						uaBiome.getFeatures(decorationType).remove(featureIndex);
					}
				}
			}

			// STRUCTURES
			// have to add it to a list first since we cannot remove from
			// structure map while iterating through it
			ArrayList<Structure<?>> listOfStructureToRemove = new ArrayList<Structure<?>>();
			for (Entry<Structure<?>, IFeatureConfig> structureEntry : uaBiome.structures.entrySet()) {
				String namespace = structureEntry.getKey().getRegistryName().getNamespace();

				if (!listOfVanillaStructures.contains(structureEntry.getKey())
						&& !namespace.equals(UltraAmplified.MODID)) {
					listOfStructureToRemove.add(structureEntry.getKey());
				}
			}
			for (Structure<?> structToRemove : listOfStructureToRemove) {
				uaBiome.structures.remove(structToRemove);
			}

			// MOBS
			for (EntityClassification entityType : EntityClassification.values()) {
				for (int spawnListIndex = uaBiome.getSpawns(entityType).size() - 1; spawnListIndex >= 0; spawnListIndex--) {
					
					EntityType<?> entity = uaBiome.getSpawns(entityType).get(spawnListIndex).entityType;
					ResourceLocation rl = EntityRegistry.getKey(entity);
					if (rl == null) {
						continue;
					}
					String namespace = rl.getNamespace();

					if (!listOfVanillaMobs.contains(entity) && !namespace.equals(UltraAmplified.MODID)) {
						uaBiome.getSpawns(entityType).remove(spawnListIndex);
					}
				}
			}
		}
	}

	/**
	 * Helper method to check if block is a modded block not from UA mod.
	 * 
	 * @param blockToCheck - Block to check if it is from a mod that isn't UA.
	 * @return             - Whether block has a non-UA and non-Minecraft namespace.
	 */
	private static boolean checkIfBlockIsAllowed(Block blockToCheck) {
		ResourceLocation rl = BlockRegistry.getKey(blockToCheck);

		if (rl == null) {
			return false;
		}
		String namespace = rl.getNamespace();

		if (!namespace.equals("minecraft") && !namespace.equals(UltraAmplified.MODID)) {
			return true;
		}

		return false;
	}
}
