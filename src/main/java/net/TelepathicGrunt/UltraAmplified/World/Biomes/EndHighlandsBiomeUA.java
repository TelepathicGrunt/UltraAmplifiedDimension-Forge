package net.telepathicgrunt.ultraamplified.world.biomes;

import com.google.common.collect.Lists;

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
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeUA;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillagePiecesUA;

public class EndHighlandsBiomeUA extends BiomeUA {
	public EndHighlandsBiomeUA() {
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(END_SURFACE_BUILDER_UA, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1F).scale(0.2F).temperature(0.7F).downfall(0.5F).waterColor(9844124).waterFogColor(8205710).parent((String) null));

		this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA(MineshaftUA.Type.END));
		this.addStructure(FeatureUA.VILLAGE_UA, new VillageConfigUA(0, VillagePiecesUA.Type.END));
		this.addStructure(FeatureUA.END_CITY_UA, IFeatureConfig.NO_FEATURE_CONFIG);

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));

		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.COLUMN, new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()), Placement.COUNT_RANGE, new CountRangeConfig(2, 70, 0, 220)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.RAMP, new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()), Placement.COUNT_RANGE, new CountRangeConfig(2, 70, 0, 220)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.CONTAIN_LIQUID, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.END_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(Feature.END_ISLAND, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_END_ISLAND_RANGE_UA, new CountRangeConfig(6, 200, 0, 254)));// low density
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(Feature.END_ISLAND, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_END_ISLAND_RANGE_UA, new CountRangeConfig(3, 140, 0, 199)));// low-medium density
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(Feature.END_ISLAND, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_END_ISLAND_RANGE_UA, new CountRangeConfig(1, 75, 0, 139)));// high density
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(Feature.END_ISLAND, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_END_ISLAND_RANGE_UA, new CountRangeConfig(2, 10, 0, 50)));// medium density
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(FeatureUA.DISK_DRY, new SphereReplaceConfig(Blocks.END_STONE.getDefaultState(), 7, 3, Lists.newArrayList(Blocks.STONE.getDefaultState(), Blocks.DIRT.getDefaultState())), Placement.COUNT_RANGE, new CountRangeConfig(15, 70, 10, 250)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(FeatureUA.DISK_DRY, new SphereReplaceConfig(Blocks.END_STONE_BRICKS.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.END_STONE.getDefaultState())), Placement.COUNT_RANGE, new CountRangeConfig(14, 10, 10, 70)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(FeatureUA.DISK_DRY, new SphereReplaceConfig(Blocks.END_STONE_BRICKS.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.END_STONE.getDefaultState())), Placement.COUNT_RANGE, new CountRangeConfig(27, 70, 10, 250)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(FeatureUA.DISK_DRY, new SphereReplaceConfig(Blocks.OBSIDIAN.getDefaultState(), 10, 3, Lists.newArrayList(Blocks.GRASS_BLOCK.getDefaultState())), Placement.COUNT_RANGE, new CountRangeConfig(6, 10, 10, 250)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.CHORUS_PLANT, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(130, 75, 10, 255)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.CHORUS_PLANT, IFeatureConfig.NO_FEATURE_CONFIG, HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(45, 10, 10, 75)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.END_TREE, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(0, 0.2F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.END_TREE, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(0, 0.2F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.TAIGA_GRASS, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.7F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SWEET_BERRY_BUSH, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.05F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.SINGLE_BLOCK, new BlockConfig(Blocks.DRAGON_HEAD), HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(2, 10, 1, 50)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.SINGLE_BLOCK, new BlockConfig(Blocks.SHULKER_BOX), HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(1, 25, 1, 70)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.SINGLE_BLOCK, new BlockConfig(Blocks.DRAGON_EGG), HEIGHT_BIASED_RANGE_UA, new CountRangeConfig(1, 10, 1, 255)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.WATER.getDefaultState()), WATERFALL_RANGE, new CountRangeConfig(1, 70, 8, 256)));
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