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

		this.func_226711_a_(FeatureUA.MINESHAFT_UA.func_225566_b_(new MineshaftConfigUA(MineshaftStructureUA.Type.END)));
		this.func_226711_a_(FeatureUA.VILLAGE_UA.func_225566_b_(new VillageConfigUA(0, VillagePastStyledPiecesUA.Type.END)));
		this.func_226711_a_(FeatureUA.END_CITY_UA.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.HANGING_RUINS.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(LEDGE_UNDERSIDE_MINI_FEATURE.func_227446_a_(new ChanceAndTypeConfig(0.6f, ChanceAndTypeConfig.Type.HANGING_RUINS))));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, FeatureUA.CONTAIN_UNDERGROUND_LIQUID.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.COLUMN.func_225566_b_(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.RAMP.func_225566_b_(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.CONTAIN_LIQUID.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, FeatureUA.END_DUNGEONS.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(DUNGEON_PLACEMENT.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(HEIGHT_BIASED_END_ISLAND_RANGE_UA.func_227446_a_(new CountRangeConfig(6, 200, 0, 254))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(HEIGHT_BIASED_END_ISLAND_RANGE_UA.func_227446_a_(new CountRangeConfig(3, 140, 0, 199))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(HEIGHT_BIASED_END_ISLAND_RANGE_UA.func_227446_a_(new CountRangeConfig(1, 75, 0, 139))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Feature.END_ISLAND.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(HEIGHT_BIASED_END_ISLAND_RANGE_UA.func_227446_a_(new CountRangeConfig(2, 10, 0, 50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureUA.DISK_DRY.func_225566_b_(new SphereReplaceConfig(Blocks.END_STONE.getDefaultState(), 7, 3, Lists.newArrayList(Blocks.STONE.getDefaultState(), Blocks.DIRT.getDefaultState()))).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(15, 70, 10, 250))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureUA.DISK_DRY.func_225566_b_(new SphereReplaceConfig(Blocks.END_STONE_BRICKS.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.END_STONE.getDefaultState()))).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(14, 10, 10, 70))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureUA.DISK_DRY.func_225566_b_(new SphereReplaceConfig(Blocks.END_STONE_BRICKS.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.END_STONE.getDefaultState()))).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(27, 70, 10, 250))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureUA.DISK_DRY.func_225566_b_(new SphereReplaceConfig(Blocks.OBSIDIAN.getDefaultState(), 10, 3, Lists.newArrayList(Blocks.GRASS_BLOCK.getDefaultState()))).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(6, 10, 10, 250))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.CHORUS_PLANT.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(HEIGHT_BIASED_RANGE_UA.func_227446_a_(new CountRangeConfig(130, 75, 10, 255))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.CHORUS_PLANT.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(HEIGHT_BIASED_RANGE_UA.func_227446_a_(new CountRangeConfig(45, 10, 10, 75))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.END_TREE.func_225566_b_(FeatureUA.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.field_226809_d_)).func_227228_a_(AT_SURFACE_WITH_EXTRA_UA.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.2F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.END_TREE.func_225566_b_(FeatureUA.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.field_226809_d_)).func_227228_a_(AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA_UA.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.2F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.field_226827_v_)).func_227228_a_(CHANCE_ON_ALL_SURFACES_UA.func_227446_a_(new PercentageAndFrequencyConfig(0.7F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.field_226718_F_)).func_227228_a_(CHANCE_ON_ALL_SURFACES_UA.func_227446_a_(new PercentageAndFrequencyConfig(0.05F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.SINGLE_BLOCK.func_225566_b_(new BlockConfig(Blocks.DRAGON_HEAD)).func_227228_a_(HEIGHT_BIASED_RANGE_UA.func_227446_a_(new CountRangeConfig(2, 10, 1, 50))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.SINGLE_BLOCK.func_225566_b_(new BlockConfig(Blocks.SHULKER_BOX)).func_227228_a_(HEIGHT_BIASED_RANGE_UA.func_227446_a_(new CountRangeConfig(1, 25, 1, 70))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.SINGLE_BLOCK.func_225566_b_(new BlockConfig(Blocks.DRAGON_EGG)).func_227228_a_(HEIGHT_BIASED_RANGE_UA.func_227446_a_(new CountRangeConfig(1, 10, 1, 255))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.END_STONE, Blocks.END_STONE_BRICKS))).func_227228_a_(WATERFALL_RANGE.func_227446_a_(new CountRangeConfig(2, 70, 8, 256))));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, FeatureUA.SNOW_AND_ICE_LAYERER.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 4, 4));
	}


	/*
	 * Set sky color
	 */
	@OnlyIn(Dist.CLIENT)
	public int func_225529_c_()
	{
		return 0;
	}


	/*
	 * set grass color
	 */
	@OnlyIn(Dist.CLIENT)
	public int func_225528_a_(double p_225528_1_, double p_225528_3_)
	{
		return 7037043;
	}


	/*
	 * set foliage/plant color
	 */
	@OnlyIn(Dist.CLIENT)
	public int func_225527_a_()
	{
		return 6050154;
	}

}
