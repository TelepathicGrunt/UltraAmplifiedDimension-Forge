package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.BlockConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.PercentageAndFrequencyConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillagePiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillageUAConfig;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EndHighlandsBiomeUA extends BiomeUA {
	public EndHighlandsBiomeUA() {
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(END_SURFACE_BUILDER_UA, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1F).scale(0.2F).temperature(0.7F).downfall(0.5F).waterColor(16711840).waterFogColor(16711840).parent((String) null));

		this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA((double) ConfigUA.mineshaftSpawnrate, MineshaftUA.Type.END));
		this.addStructure(FeatureUA.VILLAGE_UA, new VillageUAConfig(0, VillagePiecesUA.Type.END));
		this.addStructure(FeatureUA.END_CITY_UA, IFeatureConfig.NO_FEATURE_CONFIG);

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE_CAVITY_CARVER, new ProbabilityConfig((float) (ConfigUA.caveCavitySpawnrate) / 1000)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(RAVINE_CARVER, new ProbabilityConfig((float) (ConfigUA.ravineSpawnrate) / 100)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(LONG_RAVINE_CARVER, new ProbabilityConfig((float) (ConfigUA.ravineSpawnrate) / 850)));

		this.addStructureFeaturesUA();

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.END_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, new DungeonRoomConfig(ConfigUA.dungeonSpawnrate)));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(Feature.END_ISLAND, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(ConfigUA.endIslandSpawnrate / 6, 200, 0, 254)));// low density
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(Feature.END_ISLAND, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(ConfigUA.endIslandSpawnrate / 3, 140, 0, 199)));// low-medium density
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(Feature.END_ISLAND, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(ConfigUA.endIslandSpawnrate, 75, 0, 139)));// high density
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(Feature.END_ISLAND, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(ConfigUA.endIslandSpawnrate / 2, 10, 0, 50)));// medium density
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, new SphereReplaceConfig(Blocks.END_STONE.getDefaultState(), 7, 3, Lists.newArrayList(Blocks.STONE.getDefaultState())), Placement.COUNT_RANGE, new CountRangeConfig(5, 10, 10, 250)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, new SphereReplaceConfig(Blocks.END_STONE_BRICKS.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.END_STONE.getDefaultState())), Placement.COUNT_RANGE, new CountRangeConfig(15, 10, 10, 250)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, new SphereReplaceConfig(Blocks.OBSIDIAN.getDefaultState(), 8, 2, Lists.newArrayList(Blocks.END_STONE.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState())), Placement.COUNT_RANGE, new CountRangeConfig(6, 10, 10, 250)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.CHORUS_PLANT, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(100, 75, 10, 255)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.CHORUS_PLANT, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(30, 10, 10, 75)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.BIRCH_TREE, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(0, 0.2F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.TAIGA_GRASS, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.7F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SWEET_BERRY_BUSH, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.05F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.SINGLE_BLOCK, new BlockConfig(Blocks.DRAGON_HEAD), HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(2, 4, 1, 50)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.SINGLE_BLOCK, new BlockConfig(Blocks.SHULKER_BOX), HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(1, 25, 1, 70)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.SINGLE_BLOCK, new BlockConfig(Blocks.DRAGON_EGG), HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(1, 4, 1, 255)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.WATER.getDefaultState()), Placement.COUNT_BIASED_RANGE, new CountRangeConfig(ConfigUA.waterfallSpawnrate, 70, 8, 256)));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Biome.createDecoratedFeature(Feature.FREEZE_TOP_LAYER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 4, 4));
	}

	/**
	 * takes temperature, returns color
	 */
	@OnlyIn(Dist.CLIENT)
	public int getSkyColorByTemp(float currentTemperature) {
		return 0;
	}

	@OnlyIn(Dist.CLIENT)
	public int getGrassColor(BlockPos pos) {
		return 7037043;
	}

	@OnlyIn(Dist.CLIENT)
	public int getFoliageColor(BlockPos pos) {
		return 6050154;
	}

}