package net.telepathicgrunt.ultraamplified.world.biome;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.Structures;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeAddModdedFeatures {

	private static ForgeRegistry<Feature<?>> FeatureRegistry = ((ForgeRegistry<Feature<?>>)ForgeRegistries.FEATURES);
	private static ForgeRegistry<EntityType<?>> EntityRegistry = ((ForgeRegistry<EntityType<?>>)ForgeRegistries.ENTITIES);

	//workaround due to some people registering structures under minecraft namespace
	private static ArrayList<Structure<?>> listOfVanillaStructures =  new ArrayList<Structure<?>>() { 
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

	//workaround due to some people registering structures under minecraft namespace
    private static ArrayList<Feature<?>> listOfVanillaStructureFeatures =  new ArrayList<Feature<?>>() { 
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
    
    //workaround due to some people registering mobs under minecraft namespace
    private static ArrayList<EntityType<?>> listOfVanillaMobs =  new ArrayList<EntityType<?>>() { 
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
	
	
    private static Biome[] vanillaBiomesToCheck = new Biome[]
	{
		Biomes.PLAINS,
		Biomes.DESERT,
		Biomes.MOUNTAINS,
		Biomes.FOREST,
		Biomes.TAIGA,
		Biomes.SWAMP,
		Biomes.NETHER,
		Biomes.THE_END,
		Biomes.SNOWY_TUNDRA,
		Biomes.SNOWY_MOUNTAINS,
		Biomes.MUSHROOM_FIELDS,
		Biomes.DESERT_HILLS,
		Biomes.WOODED_HILLS,
		Biomes.TAIGA_HILLS,
		Biomes.BAMBOO_JUNGLE,
		Biomes.BAMBOO_JUNGLE_HILLS,
		Biomes.JUNGLE, 
		Biomes.JUNGLE_HILLS,
		Biomes.JUNGLE_EDGE,
		Biomes.STONE_SHORE,
		Biomes.SNOWY_BEACH,
		Biomes.BIRCH_FOREST,
		Biomes.BIRCH_FOREST_HILLS,
		Biomes.DARK_FOREST,
		Biomes.SNOWY_TAIGA,
		Biomes.SNOWY_TAIGA_HILLS,
		Biomes.GIANT_TREE_TAIGA,
		Biomes.GIANT_TREE_TAIGA_HILLS,
		Biomes.WOODED_MOUNTAINS,
		Biomes.SAVANNA,
		Biomes.SAVANNA_PLATEAU,
		Biomes.BADLANDS,
		Biomes.WOODED_BADLANDS_PLATEAU,
		Biomes.BADLANDS_PLATEAU,
		Biomes.SUNFLOWER_PLAINS,
		Biomes.DESERT_LAKES,
		Biomes.GRAVELLY_MOUNTAINS,
		Biomes.FLOWER_FOREST,
		Biomes.TAIGA_MOUNTAINS,
		Biomes.SWAMP_HILLS,
		Biomes.ICE_SPIKES,
		Biomes.MODIFIED_JUNGLE,
		Biomes.MODIFIED_JUNGLE_EDGE,
		Biomes.TALL_BIRCH_FOREST,
		Biomes.TALL_BIRCH_HILLS,
		Biomes.DARK_FOREST_HILLS,
		Biomes.SNOWY_TAIGA_MOUNTAINS,
		Biomes.GIANT_SPRUCE_TAIGA,
		Biomes.GIANT_SPRUCE_TAIGA_HILLS,
		Biomes.MODIFIED_GRAVELLY_MOUNTAINS,
		Biomes.SHATTERED_SAVANNA,
		Biomes.SHATTERED_SAVANNA_PLATEAU,
		Biomes.ERODED_BADLANDS,
		Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU,
		Biomes.MODIFIED_BADLANDS_PLATEAU,
		Biomes.FROZEN_OCEAN,
		Biomes.COLD_OCEAN,
		Biomes.OCEAN,
		Biomes.LUKEWARM_OCEAN,
		Biomes.WARM_OCEAN,
		Biomes.DEEP_FROZEN_OCEAN,
		Biomes.DEEP_COLD_OCEAN,
		Biomes.DEEP_OCEAN,
		Biomes.DEEP_LUKEWARM_OCEAN,
		Biomes.DEEP_WARM_OCEAN
	};
	
	
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents {
		
		@SubscribeEvent
		public static void Load(WorldEvent.Load event) {
			//remove past modded feature/spawn additions by going through all UA biomes
			resetBiomesBackToDefault();
			
			//we have a list of vanilla biomes in the order to match the UA biome list in BiomeInit.
			//Thus we only need to iterate through the vanilla list to add modded feature and mobs to UA biomes
			for(int biomeIndex = 0; biomeIndex < vanillaBiomesToCheck.length; biomeIndex++) {
				addModdedFeatureAndSpawns(vanillaBiomesToCheck[biomeIndex], (BiomeUA) BiomeInit.getBiomeArray()[biomeIndex]);
			}
		}
	}
	
	
	/**
	 * Checks vanilla biomes for modded features and mobs and adds it to the corrisponding UA biome
	 * @param vanillaBiome - Vanilla biome to check for modded features/mobs
	 * @param uaBiome - UA biome to get modded features/mobs
	 */
	private static void addModdedFeatureAndSpawns(Biome vanillaBiome, BiomeUA uaBiome) {

		//makes sure the registry exists
        if(FeatureRegistry == null)
            throw new NullPointerException("Feature Registry not set");
        if(EntityRegistry == null)
            throw new NullPointerException("Entity Registry not set");
        
		
		//loop through all decorations and checks to see if they have the minecraft namespace.
		//If not, it is a modded feature and we should add it to the UA biome
		for (Decoration decorationType : Decoration.values()) { 
			for (ConfiguredFeature<?> feature : vanillaBiome.getFeatures(decorationType)){
				DecoratedFeatureConfig insideConfig = (DecoratedFeatureConfig) feature.config;

				Feature<?> insideFeature = insideConfig.feature.feature;
				ResourceLocation rl = FeatureRegistry.getKey(insideFeature);
				if(rl == null) {
					continue;
				}
				
				String namespace = rl.getNamespace();
				
				//workaround due to some people registering structures under minecraft namespace
				if(insideFeature instanceof Structure) {
					if(!listOfVanillaStructureFeatures.contains(insideFeature)) {
						uaBiome.addFeature(decorationType, feature);
					}
				}
				else if(!namespace.equals("minecraft")) {
					uaBiome.addFeature(decorationType, feature);
				}
			}
        }
		
		//structures
		for (Entry<Structure<?>, IFeatureConfig> structureEntry : vanillaBiome.structures.entrySet()) {
			//String namespace = structureEntry.getKey().getRegistryName().getNamespace();
			
			if(!listOfVanillaStructures.contains(structureEntry.getKey())) {
				uaBiome.addStructure(structureEntry);
			}
		}

		//loop through all mobs and checks to see if they have the minecraft namespace.
		//If not, it is a modded mob and we should add it to the UA biome
		for (EntityClassification entityType : EntityClassification.values()) { 
			for (SpawnListEntry spawnEntry : vanillaBiome.getSpawns(entityType)){
				
				ResourceLocation rl = EntityRegistry.getKey(spawnEntry.entityType);
				if(rl == null) {
					continue;
				}
				
				//String namespace = rl.getNamespace();
				
				if(!listOfVanillaMobs.contains(spawnEntry.entityType)) {
					uaBiome.addSpawn(entityType, spawnEntry);
				}
			}
		}
	}
	
	/**
	 * Removes modded features and mobs from the UA biomes so it only has minecraft and UA features/mobs 
	 */
	private static void resetBiomesBackToDefault() {
		//makes sure the registry exists
        if(FeatureRegistry == null)
            throw new NullPointerException("Feature Registry not set");
        
		
		//loop through all features and mobs in UA Biomes and checks to see if they have a minecraft or UA namespace.
		//If not, it is a modded feature and we should remove it from the UA biome
        for(Biome uaBiome : BiomeInit.getBiomeArray()) 
        {
        	//features
			for (Decoration decorationType : Decoration.values()) 
			{ 
				for(int featureIndex = uaBiome.getFeatures(decorationType).size()-1; featureIndex >= 0; featureIndex--) 
				{
					DecoratedFeatureConfig insideConfig = 
							(DecoratedFeatureConfig) uaBiome.getFeatures(decorationType).get(featureIndex).config;
					
					Feature<?> insideFeature = insideConfig.feature.feature;
					ResourceLocation rl = FeatureRegistry.getKey(insideFeature);
					if(rl == null) {
						continue;
					}
					String namespace = rl.getNamespace();

					//workaround due to some people registering structures under minecraft namespace
					if(insideFeature instanceof Structure) {
						if(!listOfVanillaStructureFeatures.contains(insideFeature)) {
							uaBiome.getFeatures(decorationType).remove(featureIndex);
						}
					}
					else if(!namespace.equals("minecraft") && !namespace.equals(UltraAmplified.MODID)) 
					{
						uaBiome.getFeatures(decorationType).remove(featureIndex);
					}
				}
	        }
			

			//structures
			//have to add it to a list first since we cannot remove from structure map while iterating through it
			ArrayList<Structure<?>> listOfStructureToRemove =  new ArrayList<Structure<?>>();
			for (Entry<Structure<?>, IFeatureConfig> structureEntry : uaBiome.structures.entrySet()) {
				String namespace = structureEntry.getKey().getRegistryName().getNamespace();
				
				if(!listOfVanillaStructures.contains(structureEntry.getKey()) && !namespace.equals(UltraAmplified.MODID)) {
					listOfStructureToRemove.add(structureEntry.getKey());
				}
			}
			for(Structure<?> structToRemove : listOfStructureToRemove) {
				uaBiome.structures.remove(structToRemove);
			}
			
			
			//mobs
			for (EntityClassification entityType : EntityClassification.values()) 
			{ 
				for(int spawnListIndex = uaBiome.getSpawns(entityType).size()-1; spawnListIndex >= 0; spawnListIndex--) 
				{
					EntityType<?> entity = uaBiome.getSpawns(entityType).get(spawnListIndex).entityType;
					ResourceLocation rl = EntityRegistry.getKey(entity);
					if(rl == null) {
						continue;
					}
					
					String namespace = rl.getNamespace();
					
					if(!listOfVanillaMobs.contains(entity) && !namespace.equals(UltraAmplified.MODID)) 
					{
						uaBiome.getSpawns(entityType).remove(spawnListIndex);
					}
				}
			}
        }
	}
	
}
