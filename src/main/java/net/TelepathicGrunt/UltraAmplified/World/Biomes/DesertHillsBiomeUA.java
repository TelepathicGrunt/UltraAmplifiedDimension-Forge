package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.PercentageAndFrequencyConfig;
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
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TallGrassConfig;
import net.minecraft.world.gen.feature.structure.DesertPyramidConfig;
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

public class DesertHillsBiomeUA extends BiomeUA {
	   public DesertHillsBiomeUA() {
		      super((new Biome.BiomeBuilder()).surfaceBuilder(new CompositeSurfaceBuilder<>(DEFAULT_SURFACE_BUILDER, SAND_SAND_GRAVEL_SURFACE)).precipitation(Biome.RainType.NONE).category(Biome.Category.DESERT).depth(0.45F).scale(0.3F).temperature(2.0F).downfall(0.0F).waterColor(11260362).waterFogColor(9812925).parent((String)null));

		      if(Config.mineshaftAbovegroundAllowed || Config.mineshaftUndergroundAllowed)
		    	  this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA((double)Config.mineshaftSpawnrate, MineshaftUA.Type.DESERT));
		      
		      if(Config.strongholdGeneration)
		    	  this.addStructure(FeatureUA.STRONGHOLD_UA, new StrongholdConfig());
		      
		      if(Config.netherFortressGeneration)
		    	  this.addStructure(FeatureUA.FORTRESS_UA, new NetherBridgeConfigUA(false));
		      
		      if(Config.scatteredGeneration) {
		          this.addStructure(FeatureUA.DESERT_TEMPLE_UA, new DesertPyramidConfig());
		    	  this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(FeatureUA.DESERT_TEMPLE_UA, new DesertPyramidConfig(), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
		      }
		      
		      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(CAVE_CAVITY_CARVER, new ProbabilityConfig((float)(Config.caveCavitySpawnrate)/1000)));
		      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(RAVINE_CARVER, new ProbabilityConfig((float)(Config.ravineSpawnrate)/100)));
		      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(LONG_RAVINE_CARVER, new ProbabilityConfig((float)(Config.ravineSpawnrate)/850)));
		      this.addStructureFeaturesUA();
		      
		      if(Config.lavaLakeGen)
		    	  this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(Feature.LAKES, new LakesConfig(Blocks.LAVA), LAVA_LAKE, new LakeChanceConfig(80)));

		      if(Config.slimeLakeGen)
		    	  this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(FeatureUA.SLIME_LAKE, new LakesConfig(Blocks.SLIME_BLOCK), SLIME_LAKE_PLACEMENT, new LakeChanceConfig(7)));
		    
		      if(Config.miniStructureGeneration) {
		    	  this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(Feature.DESERT_WELLS, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_CHANCE, new ChanceConfig(100)));
		    	  this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(FeatureUA.FOSSILS_UA, IFeatureConfig.NO_FEATURE_CONFIG, RANDOM_SURFACE_BELOW_TOP_LAYER, new ChanceConfig(60)));
		      }

		      if(Config.miniStructureGeneration) {
		    	  this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(FeatureUA.SUN_SHRINE, IFeatureConfig.NO_FEATURE_CONFIG, AT_CENTER_SURFACE_WITH_CHANCE, new ChanceConfig(150))); 
		    	  this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(FeatureUA.STONEHENGE, IFeatureConfig.NO_FEATURE_CONFIG, AT_CENTER_SURFACE_WITH_CHANCE, new ChanceConfig(35))); 
		      }
		      
		      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, createCompositeFeature(FeatureUA.DESERT_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, new DungeonRoomConfig(Config.dungeonSpawnrate)));
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
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFlowerFeature(Feature.DEFAULT_FLOWERS, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.2F, 1)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.TALL_GRASS, new TallGrassConfig(Blocks.GRASS.getDefaultState()), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(1)));
			  this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.DEAD_BUSH,IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(2, 0.5f, 1)));
			  this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(FeatureUA.BETTER_CACTUS,IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(2, 0.8f, 1)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(2)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(4)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), RANDOM_CHANCE_UNDER_SURFACE, new ChanceConfig(2)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), RANDOM_CHANCE_UNDER_SURFACE, new ChanceConfig(4)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, RANDOM_POSITION_EVERY_5_HEIGHT, new FrequencyConfig(50)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, TWICE_SURFACE_WITH_CHANCE_UA, new ChanceConfig(32)));

		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.WATER), HEIGHT_BIASED_RANGE, new CountRangeConfig(Config.waterfallSpawnrate/34, 8, 8, 256)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.LAVA), HEIGHT_VERY_BIASED_RANGE, new CountRangeConfig(Config.lavafallSpawnrate, 75, 16, 175)));
		      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.LAVA), HEIGHT_VERY_BIASED_RANGE, new CountRangeConfig(Config.lavafallSpawnrate, 8, 16, 70)));

		      this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.RABBIT, 4, 2, 3));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 10, 4, 4));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.PUFFERFISH, 15, 1, 3));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DOLPHIN, 2, 1, 2));
		      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 5));
		      this.addSpawn(EnumCreatureType.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 19, 4, 4));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 1, 1, 1));
		      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.HUSK, 80, 4, 4));
		   }
		}