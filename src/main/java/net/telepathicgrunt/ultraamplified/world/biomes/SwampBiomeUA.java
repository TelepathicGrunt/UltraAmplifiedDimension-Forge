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
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
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
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.CountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LakeCountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LapisCountRangeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftUA;


public class SwampBiomeUA extends BiomeUA
{
	public SwampBiomeUA()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.SWAMP, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)).precipitation(Biome.RainType.RAIN).category(Biome.Category.SWAMP).depth(-0.2F).scale(0.1F).temperature(0.8F).downfall(0.9F).waterColor(6388580).waterFogColor(2302743).parent((String) null));

		this.func_226711_a_(FeatureUA.MINESHAFT_UA.func_225566_b_(new MineshaftConfigUA(MineshaftUA.Type.SWAMPORDARKFOREST)));
		this.func_226711_a_(FeatureUA.WITCH_HUT_UA.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG));
		this.func_226711_a_(FeatureUA.STRONGHOLD_UA.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG));
		this.func_226711_a_(FeatureUA.FORTRESS_UA.func_225566_b_(new FortressConfigUA(false)));
		this.func_226711_a_(FeatureUA.WOODLAND_MANSION_UA.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.HANGING_RUINS.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(LEDGE_UNDERSIDE_MINI_FEATURE.func_227446_a_(new ChanceAndTypeConfig(0.6f, ChanceAndTypeConfig.Type.HANGING_RUINS))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, FeatureUA.CONTAIN_UNDERGROUND_LIQUID.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.COLUMN.func_225566_b_(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.RAMP.func_225566_b_(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.GLOWPATCH.func_225566_b_(new CountConfig(160)).func_227228_a_(GLOWSTONE_VARIANT_PATCH.func_227446_a_(new ChanceConfig(4))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.GLOWPATCH.func_225566_b_(new CountConfig(100)).func_227228_a_(GENERAL_PLACEMENT.func_227446_a_(new CountRangeAndTypeConfig(0.005f, 45, 45, 70, false, CountRangeAndTypeConfig.Type.GLOWSTONE_VARIANT_PATCH))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.CONTAIN_LIQUID.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.func_225566_b_(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).func_227228_a_(LAKE_PLACEMENT.func_227446_a_(new LakeCountRangeAndTypeConfig(4, LakeCountRangeAndTypeConfig.Type.WATER))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.ROOTS.func_225566_b_(new BlockConfig(Blocks.DARK_OAK_WOOD)).func_227228_a_(RANDOM_BOTTOM_LAYER.func_227446_a_(new CountRangeConfig(3, 70, 0, 250))));

		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.SHALLOW_LAKE.func_225566_b_(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).func_227228_a_(CHANCE_ON_ALL_SURFACES_UA.func_227446_a_(new PercentageAndFrequencyConfig(100, 200))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.func_225566_b_(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).func_227228_a_(LAKE_PLACEMENT.func_227446_a_(new LakeCountRangeAndTypeConfig(80, LakeCountRangeAndTypeConfig.Type.LAVA))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeatureUA.STONELESS_LAKE.func_225566_b_(new BlockStateFeatureConfig(Blocks.SLIME_BLOCK.getDefaultState())).func_227228_a_(LAKE_PLACEMENT.func_227446_a_(new LakeCountRangeAndTypeConfig(7, LakeCountRangeAndTypeConfig.Type.SLIME))));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, FeatureUA.FOSSILS_UA.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(RANDOM_SURFACE_BELOW_TOP_LAYER.func_227446_a_(new ChanceConfig(50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, FeatureUA.SWAMP_DUNGEONS.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(DUNGEON_PLACEMENT.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIRT.getDefaultState(), 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 175))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRAVEL.getDefaultState(), 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(8, 0, 0, 256))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(17, 0, 0, 100))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(14, 0, 0, 150))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(20, 0, 0, 200))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17)).func_227228_a_(GENERAL_PLACEMENT.func_227446_a_(new CountRangeAndTypeConfig(1f, 0, 0, 240, false, CountRangeAndTypeConfig.Type.COAL))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9)).func_227228_a_(GENERAL_PLACEMENT.func_227446_a_(new CountRangeAndTypeConfig(1f, 0, 0, 200, false, CountRangeAndTypeConfig.Type.IRON))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9)).func_227228_a_(GENERAL_PLACEMENT.func_227446_a_(new CountRangeAndTypeConfig(1f, 0, 0, 50, false, CountRangeAndTypeConfig.Type.GOLD))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 8)).func_227228_a_(GENERAL_PLACEMENT.func_227446_a_(new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.REDSTONE))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 8)).func_227228_a_(GENERAL_PLACEMENT.func_227446_a_(new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.DIAMOND))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7)).func_227228_a_(LAPIS_PLACEMENT.func_227446_a_(new LapisCountRangeConfig(1f, 20, 20, false))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 7)).func_227228_a_(GENERAL_PLACEMENT.func_227446_a_(new CountRangeAndTypeConfig(1f, 18, 0, 15, true, CountRangeAndTypeConfig.Type.GOLD))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 7)).func_227228_a_(GENERAL_PLACEMENT.func_227446_a_(new CountRangeAndTypeConfig(0.5f, 15, 0, 10, true, CountRangeAndTypeConfig.Type.REDSTONE))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 6)).func_227228_a_(LAPIS_PLACEMENT.func_227446_a_(new LapisCountRangeConfig(0.5f, 10, 8, true))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(Blocks.COARSE_DIRT.getDefaultState(), 7, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()))).func_227228_a_(CHANCE_ON_ALL_WATER_BOTTOMS_UA.func_227446_a_(new PercentageAndFrequencyConfig(0.9F, 3))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(Blocks.CLAY.getDefaultState(), 4, 1, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.STONE.getDefaultState()))).func_227228_a_(CHANCE_ON_ALL_WATER_BOTTOMS_UA.func_227446_a_(new PercentageAndFrequencyConfig(1F, 4))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(Blocks.GRAVEL.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()))).func_227228_a_(CHANCE_ON_ALL_WATER_BOTTOMS_UA.func_227446_a_(new PercentageAndFrequencyConfig(1F, 2))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.func_225566_b_(FeatureUA.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.field_226814_i_)).func_227228_a_(AT_SURFACE_WITH_EXTRA_UA.func_227446_a_(new AtSurfaceWithExtraConfig(1, 0.1F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.func_225566_b_(FeatureUA.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.field_226814_i_)).func_227228_a_(AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA_UA.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.9F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.field_226830_y_)).func_227228_a_(CHANCE_ON_ALL_SURFACES_UA.func_227446_a_(new PercentageAndFrequencyConfig(0.15F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.field_226826_u_)).func_227228_a_(CHANCE_ON_ALL_SURFACES_UA.func_227446_a_(new PercentageAndFrequencyConfig(0.8F, 3))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226715_C_).func_227228_a_(AT_SURFACE_WITH_EXTRA_UA.func_227446_a_(new AtSurfaceWithExtraConfig(1, 0.0F, 0))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226720_H_).func_227228_a_(AT_SURFACE_WITH_EXTRA_UA.func_227446_a_(new AtSurfaceWithExtraConfig(4, 0.0F, 0))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.field_226722_J_)).func_227228_a_(CHANCE_ON_ALL_SURFACES_UA.func_227446_a_(new PercentageAndFrequencyConfig(0.04F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.field_226721_I_)).func_227228_a_(CHANCE_ON_ALL_SURFACES_UA.func_227446_a_(new PercentageAndFrequencyConfig(0.04F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226722_J_).func_227228_a_(RANDOM_CHANCE_UNDER_SURFACE.func_227446_a_(new ChanceConfig(1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226721_I_).func_227228_a_(RANDOM_CHANCE_UNDER_SURFACE.func_227446_a_(new ChanceConfig(2))));
		DefaultBiomeFeatures.addMushrooms(this);
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(FeatureUA.getConfiguredClusterConfig(DefaultBiomeFeatures.field_226730_R_)).func_227228_a_(CHANCE_ON_ALL_SURFACES_UA.func_227446_a_(new PercentageAndFrequencyConfig(0.70F, 5))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226717_E_).func_227228_a_(TWICE_SURFACE_WITH_CHANCE_UA.func_227446_a_(new ChanceConfig(32))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).func_227228_a_(WATERFALL_RANGE.func_227446_a_(new CountRangeConfig(20, 8, 8, 256))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).func_227228_a_(WATERFALL_RANGE.func_227446_a_(new CountRangeConfig(1, 75, 8, 175))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(new LiquidsConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).func_227228_a_(LAVAFALL_RANGE_2.func_227446_a_(new CountRangeConfig(1, 75, 16, 175))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(new LiquidsConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).func_227228_a_(LAVAFALL_RANGE_2.func_227446_a_(new CountRangeConfig(1, 8, 16, 70))));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, FeatureUA.SNOW_AND_ICE_LAYERER.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.func_225566_b_(new SeaGrassConfig(64, 0.6D)).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
		this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.PIG, 10, 4, 4));
		this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
		this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.COW, 8, 4, 4));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 1, 1, 4));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.COD, 10, 3, 6));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DOLPHIN, 1, 1, 2));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 5));
		this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 2, 1, 2));
	}


	/*
	 * set grass color
	 */
	@OnlyIn(Dist.CLIENT)
	public int func_225528_a_(double p_225528_1_, double p_225528_3_)
	{
		double d0 = INFO_NOISE.noiseAt(p_225528_1_ * 0.0225D, p_225528_3_ * 0.0225D, false);
		return d0 < -0.1D ? 5011004 : 6975545;
	}


	/*
	 * set foliage/plant color
	 */
	@OnlyIn(Dist.CLIENT)
	public int func_225527_a_()
	{
		return 6975545;
	}
}
