package net.telepathicgrunt.ultraamplified.world.biomes;

import com.google.common.collect.ImmutableList;
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
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
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
import net.telepathicgrunt.ultraamplified.world.biome.UABiome;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;
import net.telepathicgrunt.ultraamplified.world.feature.carver.UACarvers;
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.CountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LakeCountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LapisCountRangeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.placement.UAPlacements;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;


public class BirchForestRelicBiomeUA extends UABiome
{
	public BirchForestRelicBiomeUA()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)).precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(0.45F).scale(0.3F).temperature(0.9F).downfall(0.8F).waterColor(4159204).waterFogColor(329011).parent((String) null));
		this.addStructure(UAFeatures.MINESHAFT.withConfiguration(new MineshaftConfigUA(MineshaftStructureUA.Type.COLDORBIRCH)));
		this.addStructure(UAFeatures.STRONGHOLD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addStructure(UAFeatures.STONE_FORTRESS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.HANGING_RUINS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.LEDGE_UNDERSIDE_MINI_FEATURE.configure(new ChanceAndTypeConfig(1.2f, ChanceAndTypeConfig.Type.HANGING_RUINS))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, UAFeatures.NETHER_UNDERWATER_MAGMA.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_UNDERGROUND_LIQUID.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.COLUMN.withConfiguration(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.RAMP.withConfiguration(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.GLOWPATCH.withConfiguration(new CountConfig(160)).withPlacement(UAPlacements.GLOWSTONE_VARIANT_PATCH.configure(new ChanceConfig(6))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.GLOWPATCH.withConfiguration(new CountConfig(100)).withPlacement(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(0.005f, 45, 45, 70, false, CountRangeAndTypeConfig.Type.GLOWSTONE_VARIANT_PATCH))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_OCEAN_LIQUID.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.STONED_LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(UAPlacements.LAKE_PLACEMENT.configure(new LakeCountRangeAndTypeConfig(4, LakeCountRangeAndTypeConfig.Type.WATER_ALGORITHM))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.STONED_LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).withPlacement(UAPlacements.LAKE_PLACEMENT.configure(new LakeCountRangeAndTypeConfig(80, LakeCountRangeAndTypeConfig.Type.LAVA_ALGORITHM))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.STONELESS_LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.SLIME_BLOCK.getDefaultState())).withPlacement(UAPlacements.LAKE_PLACEMENT.configure(new LakeCountRangeAndTypeConfig(7, LakeCountRangeAndTypeConfig.Type.SLIME_ALGORITHM))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.STONELESS_LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.HONEY_BLOCK.getDefaultState())).withPlacement(UAPlacements.LAKE_PLACEMENT.configure(new LakeCountRangeAndTypeConfig(16, LakeCountRangeAndTypeConfig.Type.HONEY_ALGORITHM))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.ROOTS.withConfiguration(new BlockConfig(Blocks.STRIPPED_BIRCH_WOOD)).withPlacement(UAPlacements.RANDOM_BOTTOM_LAYER.configure(new CountRangeConfig(20, 70, 0, 250))));

		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.SUN_SHRINE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.AT_CENTER_SURFACE_MINI_FEATURE.configure(new ChanceAndTypeConfig(1f, ChanceAndTypeConfig.Type.SUNSHRINE))));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.STONEHENGE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.AT_CENTER_SURFACE_MINI_FEATURE.configure(new ChanceAndTypeConfig(1f, ChanceAndTypeConfig.Type.STONEHENGE))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, UAFeatures.GENERIC_DUNGEONS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.DUNGEON_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIRT.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 175))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRAVEL.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(17, 0, 0, 100))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(14, 0, 0, 150))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 200))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17)).withPlacement(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 240, false, CountRangeAndTypeConfig.Type.COAL))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9)).withPlacement(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 200, false, CountRangeAndTypeConfig.Type.IRON))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9)).withPlacement(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 50, false, CountRangeAndTypeConfig.Type.GOLD))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 8)).withPlacement(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.REDSTONE))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 8)).withPlacement(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.DIAMOND))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7)).withPlacement(UAPlacements.LAPIS_PLACEMENT.configure(new LapisCountRangeConfig(1f, 20, 20, false))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 7)).withPlacement(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 18, 0, 15, true, CountRangeAndTypeConfig.Type.GOLD))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 7)).withPlacement(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(0.5f, 15, 0, 10, true, CountRangeAndTypeConfig.Type.REDSTONE))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 6)).withPlacement(UAPlacements.LAPIS_PLACEMENT.configure(new LapisCountRangeConfig(0.5f, 10, 8, true))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(Blocks.COARSE_DIRT.getDefaultState(), 7, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()))).withPlacement(UAPlacements.CHANCE_ON_ALL_WATER_BOTTOMS.configure(new PercentageAndFrequencyConfig(0.9F, 3))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(Blocks.CLAY.getDefaultState(), 4, 1, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.STONE.getDefaultState()))).withPlacement(UAPlacements.CHANCE_ON_ALL_WATER_BOTTOMS.configure(new PercentageAndFrequencyConfig(1F, 4))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(Blocks.GRAVEL.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()))).withPlacement(UAPlacements.CHANCE_ON_ALL_WATER_BOTTOMS.configure(new PercentageAndFrequencyConfig(1F, 2))));

		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(UAFeatures.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.field_230130_i_)).withPlacement(UAPlacements.AT_SURFACE_WITH_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(UAFeatures.getConfiguredForUndergroundTreeConfig(DefaultBiomeFeatures.field_230130_i_)).withPlacement(UAPlacements.AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA.configure(new AtSurfaceWithExtraConfig(8, 0.1F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
				Feature.RANDOM_RANDOM_SELECTOR
						.withConfiguration(new MultipleWithChanceRandomFeatureConfig(
								ImmutableList.of(Feature.RANDOM_PATCH.withConfiguration(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.LILAC_CONFIG)), Feature.RANDOM_PATCH.withConfiguration(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.ROSE_BUSH_CONFIG)), Feature.RANDOM_PATCH.withConfiguration(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.PEONY_CONFIG)), Feature.FLOWER.withConfiguration(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.LILY_OF_THE_VALLEY_CONFIG))), 0))
						.withPlacement(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.20F, 2))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.DEFAULT_FLOWER_CONFIG)).withPlacement(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.20F, 2))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.GRASS_CONFIG)).withPlacement(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.4F, 5))));

		DefaultBiomeFeatures.addMushrooms(this);

		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.SUGAR_CANE_CONFIG)).withPlacement(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.80F, 6))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.PUMPKIN_PATCH_CONFIG).withPlacement(UAPlacements.TWICE_SURFACE_WITH_CHANCE.configure(new ChanceConfig(32))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).withPlacement(UAPlacements.WATERFALL_RANGE.configure(new CountRangeConfig(20, 8, 8, 256))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).withPlacement(UAPlacements.WATERFALL_RANGE.configure(new CountRangeConfig(1, 75, 8, 175))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).withPlacement(UAPlacements.LAVAFALL_RANGE_2.configure(new CountRangeConfig(1, 75, 16, 175))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).withPlacement(UAPlacements.LAVAFALL_RANGE_2.configure(new CountRangeConfig(1, 8, 16, 70))));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, UAFeatures.SNOW_AND_ICE_LAYERER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
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
	}
}
