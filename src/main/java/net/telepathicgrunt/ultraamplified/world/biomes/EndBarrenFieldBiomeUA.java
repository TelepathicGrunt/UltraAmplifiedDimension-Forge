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
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeUA;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillagePiecesUA;


public class EndBarrenFieldBiomeUA extends BiomeUA
{
	public EndBarrenFieldBiomeUA()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(END_SURFACE_BUILDER_UA, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1F).scale(0.2F).temperature(0.7F).downfall(0.5F).waterColor(7299198).waterFogColor(8542866).parent((String) null));

		this.func_226711_a_(FeatureUA.MINESHAFT_UA.func_225566_b_(new MineshaftConfigUA(MineshaftUA.Type.END)));
		this.func_226711_a_(FeatureUA.VILLAGE_UA.func_225566_b_(new VillageConfigUA(0, VillagePiecesUA.Type.END)));
		this.func_226711_a_(FeatureUA.END_CITY_UA.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.HANGING_RUINS.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(LEDGE_UNDERSIDE_MINI_FEATURE.func_227446_a_(new ChanceAndTypeConfig(0.6f, ChanceAndTypeConfig.Type.HANGING_RUINS))));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, FeatureUA.CONTAIN_UNDERGROUND_LIQUID.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.COLUMN.func_225566_b_(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.RAMP.func_225566_b_(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.GLOWPATCH.func_225566_b_(new CountConfig(160)).func_227228_a_(GLOWSTONE_VARIANT_PATCH.func_227446_a_(new ChanceConfig(2))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.CONTAIN_LIQUID.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, FeatureUA.END_DUNGEONS.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(DUNGEON_PLACEMENT.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureUA.DISK_DRY.func_225566_b_(new SphereReplaceConfig(Blocks.END_STONE.getDefaultState(), 5, 3, Lists.newArrayList(Blocks.STONE.getDefaultState(), Blocks.DIRT.getDefaultState()))).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(4, 70, 10, 250))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureUA.DISK_DRY.func_225566_b_(new SphereReplaceConfig(Blocks.OBSIDIAN.getDefaultState(), 5, 2, Lists.newArrayList(Blocks.GRASS_BLOCK.getDefaultState()))).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(3, 10, 10, 250))));

		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.END_TREE.func_225566_b_(FeatureUA.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.field_226809_d_)).func_227228_a_(AT_SURFACE_WITH_EXTRA_UA.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureUA.END_TREE.func_225566_b_(FeatureUA.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.field_226809_d_)).func_227228_a_(AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA_UA.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.field_226827_v_)).func_227228_a_(CHANCE_ON_ALL_SURFACES_UA.func_227446_a_(new PercentageAndFrequencyConfig(0.4F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.field_226718_F_)).func_227228_a_(CHANCE_ON_ALL_SURFACES_UA.func_227446_a_(new PercentageAndFrequencyConfig(0.1F, 1))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.END_STONE, Blocks.END_STONE_BRICKS))).func_227228_a_(WATERFALL_RANGE.func_227446_a_(new CountRangeConfig(2, 70, 8, 256))));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, FeatureUA.SNOW_AND_ICE_LAYERER.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMITE, 10, 4, 12));
	}


	/*
	 * Set sky color
	 */
	@OnlyIn(Dist.CLIENT)
	public int func_225529_c_()
	{
		return 2369577;
	}


	/*
	 * set grass color
	 */
	@OnlyIn(Dist.CLIENT)
	public int func_225528_a_(double p_225528_1_, double p_225528_3_)
	{
		return 7045758;
	}


	/*
	 * set foliage/plant color
	 */
	@OnlyIn(Dist.CLIENT)
	public int func_225527_a_()
	{
		return 6060912;
	}

}
