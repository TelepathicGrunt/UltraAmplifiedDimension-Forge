package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.PercentageAndFrequencyConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.NetherBridgeConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillagePiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillageUAConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureRadiusConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TallGrassConfig;
import net.minecraft.world.gen.feature.structure.IglooConfig;
import net.minecraft.world.gen.feature.structure.StrongholdConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
import net.minecraft.world.gen.surfacebuilders.CompositeSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class IceSpikesBiomeUA extends BiomeUA {
	   public IceSpikesBiomeUA() {
		      super((new Biome.BiomeBuilder()).surfaceBuilder(new CompositeSurfaceBuilder<>(DEFAULT_SURFACE_BUILDER, new SurfaceBuilderConfig(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState()))).precipitation(Biome.RainType.SNOW).category(Biome.Category.ICY).depth(0.425F).scale(0.45F).temperature(0.0F).downfall(0.5F).waterColor(13172735).waterFogColor(13172735).parent("snowy_tundra"));
		      if(ConfigUA.mineshaftAbovegroundAllowed || ConfigUA.mineshaftUndergroundAllowed)
		      this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA((double)ConfigUA.mineshaftSpawnrate, MineshaftUA.Type.ICEY));
		      
		      if(ConfigUA.strongholdGeneration)
		    	  this.addStructure(FeatureUA.STRONGHOLD_UA, new StrongholdConfig());
		      
		      if(ConfigUA.netherFortressAboveground || ConfigUA.netherFortressUnderground)
		    	  this.addStructure(FeatureUA.FORTRESS_UA, new NetherBridgeConfigUA(false));
		      
		      if(ConfigUA.iglooGeneration)
		    	  this.addStructure(FeatureUA.IGLOO_UA, new IglooConfig());

	    	  if(ConfigUA.villageGeneration)
	    		  this.addStructure(FeatureUA.VILLAGE_UA, new VillageUAConfig(0, VillagePiecesUA.Type.ICY));
	    	  
		      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(CAVE_CAVITY_CARVER, new ProbabilityConfig((float)(ConfigUA.caveCavitySpawnrate)/1000)));
		      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(RAVINE_CARVER, new ProbabilityConfig((float)(ConfigUA.ravineSpawnrate)/100)));
		      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(LONG_RAVINE_CARVER, new ProbabilityConfig((float)(ConfigUA.ravineSpawnrate)/850)));
		      this.addStructureFeaturesUA();
		      
		      if(ConfigUA.waterLakeGen)
		    	  this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(Feature.LAKES, new LakesConfig(Blocks.WATER), LAKE_WATER, new LakeChanceConfig(4)));

		      if(ConfigUA.lavaLakeGen)
		    	  this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(Feature.LAKES, new LakesConfig(Blocks.LAVA), LAVA_LAKE, new LakeChanceConfig(80)));

		      if(ConfigUA.slimeLakeGen)
		    	  this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(FeatureUA.SLIME_LAKE, new LakesConfig(Blocks.SLIME_BLOCK), SLIME_LAKE_PLACEMENT, new LakeChanceConfig(7)));

		      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(FeatureUA.GIANT_ICE_SPIKE, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_THROUGH_WATER_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(3, 0.2F, 1)));
		      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(Feature.ICE_PATH, new FeatureRadiusConfig(2), AT_SURFACE, new FrequencyConfig(2)));

		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, createCompositeFeature(FeatureUA.SNOW_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, new DungeonRoomConfig(ConfigUA.dungeonSpawnrate)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIRT.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(10, 0, 0, 175)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GRAVEL.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(8, 0, 0, 256)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GRANITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(17, 0, 0, 100)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIORITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(14, 0, 0, 150)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.ANDESITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(20, 0, 0, 200)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.COAL_ORE.getDefaultState(), 17), COUNT_RANGE, new CountRangeConfig(ConfigUA.coalOreSpawnrate, 0, 0, 240)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.IRON_ORE.getDefaultState(), 9), COUNT_RANGE, new CountRangeConfig(ConfigUA.ironOreSpawnrate, 0, 0, 200)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GOLD_ORE.getDefaultState(), 9), COUNT_RANGE, new CountRangeConfig(ConfigUA.goldOreSpawnrate, 0, 0, 50)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.REDSTONE_ORE.getDefaultState(), 8), COUNT_RANGE, new CountRangeConfig(ConfigUA.redstoneOreSpawnrate, 0, 0, 25)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIAMOND_ORE.getDefaultState(), 8), COUNT_RANGE, new CountRangeConfig(ConfigUA.diamondOreSpawnrate, 0, 0, 25)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.LAPIS_ORE.getDefaultState(), 7), DEPTH_AVERAGE, new DepthAverageConfig(ConfigUA.lapisOreSpawnrate, 20, 20))); 
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GOLD_ORE.getDefaultState(), 7), COUNT_RANGE, new CountRangeConfig(ConfigUA.goldOreSpawnrate, ConfigUA.seaLevel-18, 0, 15))); 
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.REDSTONE_ORE.getDefaultState(), 7), COUNT_RANGE, new CountRangeConfig(ConfigUA.redstoneOreSpawnrate/2, ConfigUA.seaLevel-15, 0, 10))); 
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.LAPIS_ORE.getDefaultState(), 6), DEPTH_AVERAGE, new DepthAverageConfig(ConfigUA.lapisOreSpawnrate/2, ConfigUA.seaLevel-10, 8)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.SAND, 7, 2, Lists.newArrayList(Blocks.DIRT, Blocks.GRASS_BLOCK)), TOP_SOLID, new FrequencyConfig(3)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.CLAY, 4, 1, Lists.newArrayList(Blocks.DIRT, Blocks.CLAY)), TOP_SOLID, new FrequencyConfig(1)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.GRAVEL, 6, 2, Lists.newArrayList(Blocks.DIRT, Blocks.GRASS_BLOCK)), TOP_SOLID, new FrequencyConfig(1)));

		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.TALL_TAIGA_TREE, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFlowerFeature(Feature.DEFAULT_FLOWERS, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.20F, 2)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.TALL_GRASS, new TallGrassConfig(Blocks.GRASS.getDefaultState()), CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.25F, 3)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(2)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(4)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), RANDOM_CHANCE_UNDER_SURFACE, new ChanceConfig(2)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), RANDOM_CHANCE_UNDER_SURFACE, new ChanceConfig(4)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.80F, 6)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, TWICE_SURFACE_WITH_CHANCE_UA, new ChanceConfig(32)));

		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.WATER), HEIGHT_BIASED_RANGE, new CountRangeConfig(ConfigUA.waterfallSpawnrate/34, 8, 8, 256)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.WATER), HEIGHT_BIASED_RANGE, new CountRangeConfig(ConfigUA.waterfallSpawnrate, 75, 8, 175)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.LAVA), HEIGHT_VERY_BIASED_RANGE, new CountRangeConfig(ConfigUA.lavafallSpawnrate, 75, 16, 175)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.LAVA), HEIGHT_VERY_BIASED_RANGE, new CountRangeConfig(ConfigUA.lavafallSpawnrate, 8, 16, 70)));
		      
		      this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, createCompositeFeature(FeatureUA.ICE_AND_SNOW_UNDER_LEDGES, IFeatureConfig.NO_FEATURE_CONFIG, PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
		      this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.RABBIT, 10, 2, 3));
		      this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.POLAR_BEAR, 1, 1, 2));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 2, 1, 4));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SALMON, 5, 1, 5));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 5));
		      this.addSpawn(EnumCreatureType.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 20, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.STRAY, 80, 4, 4));
		   }

		   /**
		    * returns the chance a creature has to spawn.
		    */
		   public float getSpawningChance() {
		      return 0.07F;
		   }
		}