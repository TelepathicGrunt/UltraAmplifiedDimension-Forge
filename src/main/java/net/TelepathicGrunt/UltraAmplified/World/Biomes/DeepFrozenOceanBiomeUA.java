package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.ContainWaterConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.PercentageAndFrequencyConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.PercentageAndHeightConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.NetherBridgeConfigUA;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.IcebergConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TallGrassConfig;
import net.minecraft.world.gen.feature.structure.OceanMonumentConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.StrongholdConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.surfacebuilders.CompositeSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DeepFrozenOceanBiomeUA  extends BiomeUA {
	   protected static final NoiseGeneratorPerlin field_206856_bb = new NoiseGeneratorPerlin(new Random(3456L), 3);

	   public DeepFrozenOceanBiomeUA() {
	      super((new Biome.BiomeBuilder()).surfaceBuilder(new CompositeSurfaceBuilder<>(DEEP_OCEAN_SURFACE_BUILDER_UA, ICE_GRAVEL_STONE_SURFACE)).precipitation(Biome.RainType.SNOW).category(Biome.Category.OCEAN).depth(-1.8F).scale(0.1F).temperature(0.0F).downfall(0.5F).waterColor(3750089).waterFogColor(329011).parent((String)null));
	      if(Config.mineshaftAbovegroundAllowed || Config.mineshaftUndergroundAllowed)
	    	  this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA((double)Config.mineshaftSpawnrate, MineshaftUA.Type.OCEAN));
	      
	      if(Config.strongholdGeneration)
	    	  this.addStructure(FeatureUA.STRONGHOLD_UA, new StrongholdConfig());

	      if(Config.netherFortressAboveground || Config.netherFortressUnderground)
	    	  this.addStructure(FeatureUA.FORTRESS_UA, new NetherBridgeConfigUA(false));

	      if(Config.shipwreckGeneration)
	    	  this.addStructure(FeatureUA.SHIPWRECK_UA, new ShipwreckConfig(false));
	      
	      if(Config.oceanMonumentGeneration)
	    	  this.addStructure(FeatureUA.OCEAN_MONUMENT_UA, new OceanMonumentConfig());

		  if(Config.chestGeneration) {
			  this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(FeatureUA.MARKED_TREASURE_CHEST_UA, IFeatureConfig.NO_FEATURE_CONFIG, FIXED_HEIGHT_WITH_CHANCE, new PercentageAndHeightConfig(0.015f, 52)));
		  }
	      
	      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(CAVE_CAVITY_CARVER, new ProbabilityConfig((float)(Config.caveCavitySpawnrate)/1000)));
	      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(RAVINE_CARVER, new ProbabilityConfig((float)(Config.ravineSpawnrate)/100)));
		  this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(LONG_RAVINE_CARVER, new ProbabilityConfig((float)(Config.ravineSpawnrate)/850)));
	      
	      this.addStructureFeaturesUA();
	      
	      this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(FeatureUA.CONTAIN_WATER, new ContainWaterConfig(Blocks.STONE, Blocks.ANDESITE, Blocks.ANDESITE), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
	      
	      this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(FeatureUA.ICEBERG_UA, new IcebergConfig(Blocks.PACKED_ICE.getDefaultState()), CHANCE_ON_ALL_WATER_SURFACES_UA, new PercentageAndFrequencyConfig(0.35F, 6)));
	      this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(FeatureUA.ICEBERG_UA, new IcebergConfig(Blocks.BLUE_ICE.getDefaultState()), CHANCE_ON_ALL_WATER_SURFACES_UA, new PercentageAndFrequencyConfig(0.09F, 2)));
	      
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, createCompositeFeature(FeatureUA.SNOW_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, new DungeonRoomConfig(Config.dungeonSpawnrate)));

          this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(Feature.BLUE_ICE, IFeatureConfig.NO_FEATURE_CONFIG, RANDOM_COUNT_WITH_RANGE, new CountRangeConfig(20, 40, 32, 160)));
          this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIRT.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(6, 0, 0, 175)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GRAVEL.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(8, 0, 0, 256)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GRANITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(10, 0, 0, 100)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIORITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(6, 0, 0, 150)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.ANDESITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(13, 0, 0, 200)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.COAL_ORE.getDefaultState(), 17), COUNT_RANGE, new CountRangeConfig(Config.coalOreSpawnrate, 0, 0, 240)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.IRON_ORE.getDefaultState(), 9), COUNT_RANGE, new CountRangeConfig(Config.ironOreSpawnrate, 0, 0, 200)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GOLD_ORE.getDefaultState(), 9), COUNT_RANGE, new CountRangeConfig(Config.goldOreSpawnrate, 0, 0, 50)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.REDSTONE_ORE.getDefaultState(), 8), COUNT_RANGE, new CountRangeConfig(Config.redstoneOreSpawnrate, 0, 0, 25)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIAMOND_ORE.getDefaultState(), 8), COUNT_RANGE, new CountRangeConfig(Config.diamondOreSpawnrate, 0, 0, 25)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.LAPIS_ORE.getDefaultState(), 7), DEPTH_AVERAGE, new DepthAverageConfig((int) (Config.lapisOreSpawnrate*1.5), 20, 20))); 
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GOLD_ORE.getDefaultState(), 7), COUNT_RANGE, new CountRangeConfig(Config.goldOreSpawnrate, Config.seaLevel-18, 0, 15))); 
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.REDSTONE_ORE.getDefaultState(), 7), COUNT_RANGE, new CountRangeConfig(Config.redstoneOreSpawnrate/2, Config.seaLevel-15, 0, 10))); 
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.LAPIS_ORE.getDefaultState(), 6), DEPTH_AVERAGE, new DepthAverageConfig(Config.lapisOreSpawnrate, Config.seaLevel-10, 8)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.SAND, 7, 2, Lists.newArrayList(Blocks.DIRT, Blocks.GRASS_BLOCK)), TOP_SOLID, new FrequencyConfig(3)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.CLAY, 4, 1, Lists.newArrayList(Blocks.DIRT, Blocks.CLAY)), TOP_SOLID, new FrequencyConfig(1)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.GRAVEL, 6, 2, Lists.newArrayList(Blocks.DIRT, Blocks.GRASS_BLOCK)), TOP_SOLID, new FrequencyConfig(1)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFlowerFeature(Feature.DEFAULT_FLOWERS, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.2F, 2)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.TALL_GRASS, new TallGrassConfig(Blocks.GRASS.getDefaultState()), CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.3F, 2)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), RANDOM_CHANCE_UNDER_SURFACE, new ChanceConfig(1)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), RANDOM_CHANCE_UNDER_SURFACE, new ChanceConfig(2)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.2F, 4)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(32)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(FeatureUA.KELP_UA, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_WATER_BOTTOMS_UA, new PercentageAndFrequencyConfig(0.1F, 2)));
	      
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.WATER), HEIGHT_BIASED_RANGE, new CountRangeConfig(Config.waterfallSpawnrate/35, 8, 8, 256)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(FeatureUA.CEILING_FLUID, new LiquidsConfig(Fluids.WATER), RANDOM_BOTTOM_LAYER, new CountRangeConfig(Config.waterfallSpawnrate/18, 75, 8, 230)));

	      this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, createCompositeFeature(FeatureUA.ICE_AND_SNOW_UNDER_LEDGES, IFeatureConfig.NO_FEATURE_CONFIG, PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
	      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 3, 1, 4));
	      this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SALMON, 40, 1, 5));
	      this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.POLAR_BEAR, 8, 1, 2));
	      this.addSpawn(EnumCreatureType.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 1));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
	   }

	   /**
	    * Gets the current temperature at the given location, based off of the default for this biome, the elevation of the
	    * position, and {@linkplain #TEMPERATURE_NOISE} some random perlin noise.
	    */
	   public float getTemperature(BlockPos pos) {
	      float f = this.getDefaultTemperature();
	      double d0 = field_206856_bb.getValue((double)pos.getX() * 0.05D, (double)pos.getZ() * 0.05D);
	      double d1 = INFO_NOISE.getValue((double)pos.getX() * 0.2D, (double)pos.getZ() * 0.2D);
	      double d2 = d0 + d1;
	      if (d2 < 0.3D) {
	         double d3 = INFO_NOISE.getValue((double)pos.getX() * 0.09D, (double)pos.getZ() * 0.09D);
	         if (d3 < 0.8D) {
	            f = 0.2F;
	         }
	      }

	      if (pos.getY() > 64) {
	         float f1 = (float)(TEMPERATURE_NOISE.getValue((double)((float)pos.getX() / 8.0F), (double)((float)pos.getZ() / 8.0F)) * 4.0D);
	         return f - (f1 + (float)pos.getY() - 64.0F) * 0.05F / 30.0F;
	      } else {
	         return f;
	      }
	   }
	   

	   @OnlyIn(Dist.CLIENT)
	   public int getGrassColor(BlockPos pos) {
	      return 3838576;
	   }

	   @OnlyIn(Dist.CLIENT)
	   public int getFoliageColor(BlockPos pos) {
	      return 4099688;
	   }
}