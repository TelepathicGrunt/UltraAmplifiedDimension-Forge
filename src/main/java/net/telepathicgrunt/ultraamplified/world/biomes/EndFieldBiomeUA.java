package net.telepathicgrunt.ultraamplified.world.biomes;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
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
import net.telepathicgrunt.ultraamplified.world.biome.UABiome;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.UASurfaceBuilders;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;
import net.telepathicgrunt.ultraamplified.world.feature.carver.UACarvers;
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.placement.UAPlacements;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillagePastStyledPiecesUA;


public class EndFieldBiomeUA extends UABiome
{
	public EndFieldBiomeUA()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(UASurfaceBuilders.END_SURFACE_BUILDER_UA, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1F).scale(0.2F).temperature(0.7F).downfall(0.5F).waterColor(9844124).waterFogColor(8205710).parent((String) null));

		this.addStructure(UAFeatures.MINESHAFT.withConfiguration(new MineshaftConfigUA(MineshaftStructureUA.Type.END)));
		this.addStructure(UAFeatures.VILLAGE.withConfiguration(new VillageConfigUA(0, VillagePastStyledPiecesUA.Type.END)));
		this.addStructure(UAFeatures.END_CITY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.HANGING_RUINS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.LEDGE_UNDERSIDE_MINI_FEATURE.configure(new ChanceAndTypeConfig(0.6f, ChanceAndTypeConfig.Type.HANGING_RUINS))));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, UAFeatures.NETHER_UNDERWATER_MAGMA.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_UNDERGROUND_LIQUID.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.COLUMN.withConfiguration(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.RAMP.withConfiguration(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_OCEAN_LIQUID.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, UAFeatures.END_DUNGEONS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.DUNGEON_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.HEIGHT_BIASED_END_ISLAND_RANGE.configure(new CountRangeConfig(6, 200, 0, 254))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.HEIGHT_BIASED_END_ISLAND_RANGE.configure(new CountRangeConfig(3, 140, 0, 199))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.HEIGHT_BIASED_END_ISLAND_RANGE.configure(new CountRangeConfig(1, 75, 0, 139))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.HEIGHT_BIASED_END_ISLAND_RANGE.configure(new CountRangeConfig(2, 10, 0, 50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.DISK_DRY.withConfiguration(new SphereReplaceConfig(Blocks.END_STONE.getDefaultState(), 7, 3, Lists.newArrayList(Blocks.STONE.getDefaultState(), Blocks.DIRT.getDefaultState()))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 70, 10, 250))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.DISK_DRY.withConfiguration(new SphereReplaceConfig(Blocks.END_STONE_BRICKS.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.END_STONE.getDefaultState()))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(14, 10, 10, 70))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.DISK_DRY.withConfiguration(new SphereReplaceConfig(Blocks.END_STONE_BRICKS.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.END_STONE.getDefaultState()))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(27, 70, 10, 250))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.DISK_DRY.withConfiguration(new SphereReplaceConfig(Blocks.OBSIDIAN.getDefaultState(), 10, 3, Lists.newArrayList(Blocks.GRASS_BLOCK.getDefaultState()))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(6, 10, 10, 250))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.CHORUS_PLANT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.HEIGHT_BIASED_RANGE.configure(new CountRangeConfig(130, 75, 10, 255))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.CHORUS_PLANT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.HEIGHT_BIASED_RANGE.configure(new CountRangeConfig(45, 10, 10, 75))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, UAFeatures.END_TREE.withConfiguration(UAFeatures.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.PINE_TREE_CONFIG)).withPlacement(UAPlacements.AT_SURFACE_WITH_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.2F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, UAFeatures.END_TREE.withConfiguration(UAFeatures.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.PINE_TREE_CONFIG)).withPlacement(UAPlacements.AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.2F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.TAIGA_GRASS_CONFIG)).withPlacement(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.7F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.SWEET_BERRY_BUSH_CONFIG)).withPlacement(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.05F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, UAFeatures.SINGLE_BLOCK.withConfiguration(new BlockConfig(Blocks.DRAGON_HEAD)).withPlacement(UAPlacements.HEIGHT_BIASED_RANGE.configure(new CountRangeConfig(2, 10, 1, 50))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, UAFeatures.SINGLE_BLOCK.withConfiguration(new BlockConfig(Blocks.SHULKER_BOX)).withPlacement(UAPlacements.HEIGHT_BIASED_RANGE.configure(new CountRangeConfig(1, 25, 1, 70))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, UAFeatures.SINGLE_BLOCK.withConfiguration(new BlockConfig(Blocks.DRAGON_EGG)).withPlacement(UAPlacements.HEIGHT_BIASED_RANGE.configure(new CountRangeConfig(1, 10, 1, 255))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.END_STONE, Blocks.END_STONE_BRICKS))).withPlacement(UAPlacements.WATERFALL_RANGE.configure(new CountRangeConfig(2, 70, 8, 256))));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, UAFeatures.SNOW_AND_ICE_LAYERER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 4, 4));
	}


	/*
	 * Set sky color
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public int getSkyColor()
	{
		return 0;
	}


	/*
	 * set grass color
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public int getGrassColor(double p_225528_1_, double p_225528_3_)
	{
		return 7037043;
	}


	/*
	 * set foliage/plant color
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public int getFoliageColor()
	{
		return 6050154;
	}

}
