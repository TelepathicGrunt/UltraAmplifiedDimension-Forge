package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.NetherBridgeConfigUA;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.DoublePlantConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.RandomFeatureListConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TallGrassConfig;
import net.minecraft.world.gen.feature.structure.StrongholdConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
import net.minecraft.world.gen.surfacebuilders.CompositeSurfaceBuilder;

public class BirchForestHillsBiomeUA extends BiomeUA {
	   public BirchForestHillsBiomeUA() {
		      super((new Biome.BiomeBuilder()).surfaceBuilder(new CompositeSurfaceBuilder<>(DEFAULT_SURFACE_BUILDER, GRASS_DIRT_GRAVEL_SURFACE)).precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(0.45F).scale(0.3F).temperature(0.6F).downfall(0.6F).waterColor(4159204).waterFogColor(329011).parent((String)null));
		      if(Config.mineshaftAbovegroundAllowed || Config.mineshaftUndergroundAllowed)
		      this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA((double)Config.mineshaftSpawnrate, MineshaftUA.Type.COLDORBIRCH));
		      
		      if(Config.strongholdGeneration)
		    	  this.addStructure(FeatureUA.STRONGHOLD_UA, new StrongholdConfig()); 

		      if(Config.netherFortressGeneration)
		    	  this.addStructure(FeatureUA.FORTRESS_UA, new NetherBridgeConfigUA(false));

		      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(CAVE_CAVITY_CARVER, new ProbabilityConfig((float)(Config.caveCavitySpawnrate)/1000)));
		      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(RAVINE_CARVER, new ProbabilityConfig((float)(Config.ravineSpawnrate)/100)));
		      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(LONG_RAVINE_CARVER, new ProbabilityConfig((float)(Config.ravineSpawnrate)/850)));
		      this.addStructureFeaturesUA();
		       
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.DEFAULT_RANDOM_FEATURE_LIST, new RandomFeatureListConfig(new Feature[]{Feature.DOUBLE_PLANT, Feature.DOUBLE_PLANT, Feature.DOUBLE_PLANT}, new IFeatureConfig[]{new DoublePlantConfig(Blocks.LILAC.getDefaultState()), new DoublePlantConfig(Blocks.ROSE_BUSH.getDefaultState()), new DoublePlantConfig(Blocks.PEONY.getDefaultState())}, 0), RANDOM_POSITION_EVERY_5_HEIGHT, new FrequencyConfig(2)));
		      
		      if(Config.waterLakeGen)
		    	  this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(Feature.LAKES, new LakesConfig(Blocks.WATER), LAKE_WATER, new LakeChanceConfig(4)));

		      if(Config.lavaLakeGen)
		    	  this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(Feature.LAKES, new LakesConfig(Blocks.LAVA), LAVA_LAKE, new LakeChanceConfig(80)));

		      if(Config.slimeLakeGen)
		    	  this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(FeatureUA.SLIME_LAKE, new LakesConfig(Blocks.SLIME_BLOCK), SLIME_LAKE_PLACEMENT, new LakeChanceConfig(7)));

		      if(Config.miniStructureGeneration) {
		    	  this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(FeatureUA.SUN_SHRINE, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_CHANCE, new ChanceConfig(130))); 
		    	  this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(FeatureUA.STONEHENGE, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_CHANCE, new ChanceConfig(15))); 
		      }
		      
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, createCompositeFeature(FeatureUA.GENERIC_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, new DungeonRoomConfig(Config.dungeonSpawnrate)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIRT.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(10, 0, 0, 175)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GRAVEL.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(8, 0, 0, 256)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GRANITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(17, 0, 0, 100)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIORITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(14, 0, 0, 150)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.ANDESITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(20, 0, 0, 200)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.COAL_ORE.getDefaultState(), 17), COUNT_RANGE, new CountRangeConfig(Config.coalOreSpawnrate, 0, 0, 240)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.IRON_ORE.getDefaultState(), 9), COUNT_RANGE, new CountRangeConfig(Config.ironOreSpawnrate, 0, 0, 200)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GOLD_ORE.getDefaultState(), 9), COUNT_RANGE, new CountRangeConfig(Config.goldOreSpawnrate, 0, 0, 50)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.REDSTONE_ORE.getDefaultState(), 8), COUNT_RANGE, new CountRangeConfig(Config.redstoneOreSpawnrate, 0, 0, 25)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIAMOND_ORE.getDefaultState(), 8), COUNT_RANGE, new CountRangeConfig(Config.diamondOreSpawnrate, 0, 0, 25)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.LAPIS_ORE.getDefaultState(), 7), DEPTH_AVERAGE, new DepthAverageConfig(Config.lapisOreSpawnrate, 20, 20))); 
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GOLD_ORE.getDefaultState(), 7), COUNT_RANGE, new CountRangeConfig(Config.goldOreSpawnrate, Config.seaLevel-18, 0, 15))); 
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.REDSTONE_ORE.getDefaultState(), 7), COUNT_RANGE, new CountRangeConfig(Config.redstoneOreSpawnrate/2, Config.seaLevel-15, 0, 10))); 
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.LAPIS_ORE.getDefaultState(), 6), DEPTH_AVERAGE, new DepthAverageConfig(Config.lapisOreSpawnrate/2, Config.seaLevel-10, 8)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.SAND, 7, 2, Lists.newArrayList(Blocks.DIRT, Blocks.GRASS_BLOCK)), TOP_SOLID, new FrequencyConfig(3)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.CLAY, 4, 1, Lists.newArrayList(Blocks.DIRT, Blocks.CLAY)), TOP_SOLID, new FrequencyConfig(1)));
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.GRAVEL, 6, 2, Lists.newArrayList(Blocks.DIRT, Blocks.GRASS_BLOCK)), TOP_SOLID, new FrequencyConfig(1)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.TALL_BIRCH_TREE, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
		      
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFlowerFeature(Feature.DEFAULT_FLOWERS, RANDOM_POSITION_EVERY_5_HEIGHT, new FrequencyConfig(2)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.TALL_GRASS, new TallGrassConfig(Blocks.GRASS.getDefaultState()), RANDOM_POSITION_EVERY_5_HEIGHT, new FrequencyConfig(1)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(2)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(4)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), RANDOM_CHANCE_UNDER_SURFACE, new ChanceConfig(2)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), RANDOM_CHANCE_UNDER_SURFACE, new ChanceConfig(4)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, RANDOM_POSITION_EVERY_5_HEIGHT, new FrequencyConfig(6)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, TWICE_SURFACE_WITH_CHANCE_UA, new ChanceConfig(32)));

		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.WATER), HEIGHT_BIASED_RANGE, new CountRangeConfig(Config.waterfallSpawnrate/34, 8, 8, 256)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.WATER), HEIGHT_BIASED_RANGE, new CountRangeConfig(Config.waterfallSpawnrate, 75, 8, 175)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.LAVA), HEIGHT_VERY_BIASED_RANGE, new CountRangeConfig(Config.lavafallSpawnrate, 75, 16, 175)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.LAVA), HEIGHT_VERY_BIASED_RANGE, new CountRangeConfig(Config.lavafallSpawnrate, 8, 16, 70)));

		      this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
		      this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.PIG, 10, 4, 4));
		      this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
		      this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.COW, 8, 4, 4));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 1, 1, 4));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.COD, 10, 3, 6));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DOLPHIN, 1, 1, 2));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 5));
		      this.addSpawn(EnumCreatureType.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
		   }
		}