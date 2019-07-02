package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.PercentageAndFrequencyConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.NetherBridgeConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillagePiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillageUAConfig;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HellLavaConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.structure.StrongholdConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.surfacebuilders.CompositeSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NetherBiomeUA extends BiomeUA {
   public NetherBiomeUA() {
	      super((new Biome.BiomeBuilder()).surfaceBuilder(new CompositeSurfaceBuilder<>(NETHER_SURFACE_BUILDER_UA, NETHERRACK_SURFACE)).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).waterColor(12411469).waterFogColor(12076846).parent((String)null));
	      
	      if(Config.mineshaftAbovegroundAllowed || Config.mineshaftUndergroundAllowed)
		      this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA((double)Config.mineshaftSpawnrate, MineshaftUA.Type.HELL));

    	  if(Config.villageGeneration) {
    		  this.addStructure(FeatureUA.VILLAGE_UA, new VillageUAConfig(0, VillagePiecesUA.Type.HELL));
    	  }

	      if(Config.strongholdGeneration)
	    	  this.addStructure(FeatureUA.STRONGHOLD_UA, new StrongholdConfig());

	      if(Config.netherFortressGeneration) {
	    	  this.addStructure(FeatureUA.FORTRESS_UA, new NetherBridgeConfigUA(true));
	      }
	      
	      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(CAVE_CAVITY_CARVER, new ProbabilityConfig((float)(Config.caveCavitySpawnrate)/1000)));
	      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(RAVINE_CARVER, new ProbabilityConfig((float)(Config.ravineSpawnrate)/100)));
		      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(LONG_RAVINE_CARVER, new ProbabilityConfig((float)(Config.ravineSpawnrate)/850)));

	      this.addStructureFeaturesUA();
	      
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, createCompositeFeature(FeatureUA.NETHER_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, new DungeonRoomConfig(Config.dungeonSpawnrate)));
	      
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.LAVA), HEIGHT_VERY_BIASED_RANGE, new CountRangeConfig(40, 8, 16, 256)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(4)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(8)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.HELL_LAVA, new HellLavaConfig(false), COUNT_RANGE, new CountRangeConfig(23, 4, 8, 256)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.FIRE, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.18F, 1)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.GLOWSTONE, IFeatureConfig.NO_FEATURE_CONFIG, GLOWSTONE_PLACEMENT, new FrequencyConfig(Config.glowstoneSpawnrate)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.GLOWSTONE, IFeatureConfig.NO_FEATURE_CONFIG, COUNT_RANGE, new CountRangeConfig(Config.glowstoneSpawnrate, 0, 0, 256)));
	      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.NETHER_WART), NETHERWART_SOUL_SAND_SURFACES_UA, new PercentageAndFrequencyConfig(0.7F, 2)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), CHANCE_RANGE, new ChanceRangeConfig(1F, 0, 0, 75)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), CHANCE_RANGE, new ChanceRangeConfig(1F, 0, 0, 75)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.05F, 1)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.05F, 1)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.MINABLE, new MinableConfig(BlockMatcher.forBlock(Blocks.NETHERRACK), Blocks.NETHER_QUARTZ_ORE.getDefaultState(), 25), COUNT_RANGE, new CountRangeConfig(Config.quartzOreSpawnrate, Config.seaLevel-18, 0, 15))); 
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.MINABLE, new MinableConfig(BlockMatcher.forBlock(Blocks.NETHERRACK), Blocks.NETHER_QUARTZ_ORE.getDefaultState(), 25), COUNT_RANGE, new CountRangeConfig(Config.quartzOreSpawnrate/2, 10, 20, 120)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.MINABLE, new MinableConfig(BlockMatcher.forBlock(Blocks.NETHERRACK), Blocks.NETHER_QUARTZ_ORE.getDefaultState(), 25), COUNT_RANGE, new CountRangeConfig(Config.quartzOreSpawnrate/10, 10, 120, 240)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.MINABLE, new MinableConfig(BlockMatcher.forBlock(Blocks.NETHERRACK), Blocks.MAGMA_BLOCK.getDefaultState(), 55), COUNT_RANGE, new CountRangeConfig(Config.magmaSpawnrate, 10, 20, 100)));
	      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.HELL_LAVA, new HellLavaConfig(true), COUNT_RANGE, new CountRangeConfig(Config.lavaSpawnrate, 10, 20, 240)));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.GHAST, 50, 4, 4));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_PIGMAN, 100, 4, 4));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.MAGMA_CUBE, 2, 4, 4));
	      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 1, 4, 4));
	   }
   
	/**
   * takes temperature, returns colors
   */
   @OnlyIn(Dist.CLIENT)
   public int getSkyColorByTemp(float currentTemperature) {
      return 2621440;
   }
   
   @OnlyIn(Dist.CLIENT)
   public int getGrassColor(BlockPos pos) {
      return 9259264;
   }

   @OnlyIn(Dist.CLIENT)
   public int getFoliageColor(BlockPos pos) {
      return 8075008;
   }
	   
}