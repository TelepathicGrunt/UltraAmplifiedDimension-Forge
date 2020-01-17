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
import net.telepathicgrunt.ultraamplified.world.biome.BiomeUA;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillagePastStyledPiecesUA;


public class EndFieldBiomeUA extends BiomeUA
{
	public EndFieldBiomeUA()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(END_SURFACE_BUILDER_UA, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1F).scale(0.2F).temperature(0.7F).downfall(0.5F).waterColor(9844124).waterFogColor(8205710).parent((String) null));

		this.addStructureFeature(FeatureUA.MINESHAFT_UA.configure(new MineshaftConfigUA(MineshaftStructureUA.Type.END)));
		this.addStructureFeature(FeatureUA.VILLAGE_UA.configure(new VillageConfigUA(0, VillagePastStyledPiecesUA.Type.END)));
		this.addStructureFeature(FeatureUA.END_CITY_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.HANGING_RUINS.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(LEDGE_UNDERSIDE_MINI_FEATURE.configure(new ChanceAndTypeConfig(0.6f, ChanceAndTypeConfig.Type.HANGING_RUINS))));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, FeatureUA.NETHER_UNDERWATER_MAGMA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.CONTAIN_UNDERGROUND_LIQUID.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.COLUMN.configure(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.RAMP.configure(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.CONTAIN_LIQUID.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, FeatureUA.END_DUNGEONS.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(DUNGEON_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(HEIGHT_BIASED_END_ISLAND_RANGE_UA.configure(new CountRangeConfig(6, 200, 0, 254))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(HEIGHT_BIASED_END_ISLAND_RANGE_UA.configure(new CountRangeConfig(3, 140, 0, 199))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(HEIGHT_BIASED_END_ISLAND_RANGE_UA.configure(new CountRangeConfig(1, 75, 0, 139))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(HEIGHT_BIASED_END_ISLAND_RANGE_UA.configure(new CountRangeConfig(2, 10, 0, 50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureUA.DISK_DRY.configure(new SphereReplaceConfig(Blocks.END_STONE.getDefaultState(), 7, 3, Lists.newArrayList(Blocks.STONE.getDefaultState(), Blocks.DIRT.getDefaultState()))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 70, 10, 250))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureUA.DISK_DRY.configure(new SphereReplaceConfig(Blocks.END_STONE_BRICKS.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.END_STONE.getDefaultState()))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(14, 10, 10, 70))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureUA.DISK_DRY.configure(new SphereReplaceConfig(Blocks.END_STONE_BRICKS.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.END_STONE.getDefaultState()))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(27, 70, 10, 250))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureUA.DISK_DRY.configure(new SphereReplaceConfig(Blocks.OBSIDIAN.getDefaultState(), 10, 3, Lists.newArrayList(Blocks.GRASS_BLOCK.getDefaultState()))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(6, 10, 10, 250))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.CHORUS_PLANT.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(HEIGHT_BIASED_RANGE_UA.configure(new CountRangeConfig(130, 75, 10, 255))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.CHORUS_PLANT.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(HEIGHT_BIASED_RANGE_UA.configure(new CountRangeConfig(45, 10, 10, 75))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.END_TREE.configure(FeatureUA.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.PINE_TREE_CONFIG)).createDecoratedFeature(AT_SURFACE_WITH_EXTRA_UA.configure(new AtSurfaceWithExtraConfig(0, 0.2F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.END_TREE.configure(FeatureUA.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.PINE_TREE_CONFIG)).createDecoratedFeature(AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA_UA.configure(new AtSurfaceWithExtraConfig(0, 0.2F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.TAIGA_GRASS_CONFIG)).createDecoratedFeature(CHANCE_ON_ALL_SURFACES_UA.configure(new PercentageAndFrequencyConfig(0.7F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.SWEET_BERRY_BUSH_CONFIG)).createDecoratedFeature(CHANCE_ON_ALL_SURFACES_UA.configure(new PercentageAndFrequencyConfig(0.05F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.SINGLE_BLOCK.configure(new BlockConfig(Blocks.DRAGON_HEAD)).createDecoratedFeature(HEIGHT_BIASED_RANGE_UA.configure(new CountRangeConfig(2, 10, 1, 50))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.SINGLE_BLOCK.configure(new BlockConfig(Blocks.SHULKER_BOX)).createDecoratedFeature(HEIGHT_BIASED_RANGE_UA.configure(new CountRangeConfig(1, 25, 1, 70))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.SINGLE_BLOCK.configure(new BlockConfig(Blocks.DRAGON_EGG)).createDecoratedFeature(HEIGHT_BIASED_RANGE_UA.configure(new CountRangeConfig(1, 10, 1, 255))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.END_STONE, Blocks.END_STONE_BRICKS))).createDecoratedFeature(WATERFALL_RANGE.configure(new CountRangeConfig(2, 70, 8, 256))));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, FeatureUA.SNOW_AND_ICE_LAYERER.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 4, 4));
	}


	/*
	 * Set sky color
	 */
	@OnlyIn(Dist.CLIENT)
	public int getSkyColor()
	{
		return 0;
	}


	/*
	 * set grass color
	 */
	@OnlyIn(Dist.CLIENT)
	public int getGrassColorAt(double p_225528_1_, double p_225528_3_)
	{
		return 7037043;
	}


	/*
	 * set foliage/plant color
	 */
	@OnlyIn(Dist.CLIENT)
	public int getFoliageColor()
	{
		return 6050154;
	}

}
