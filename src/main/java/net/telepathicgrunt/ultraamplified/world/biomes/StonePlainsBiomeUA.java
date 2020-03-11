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
import net.minecraft.world.gen.feature.SphereReplaceConfig;
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
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.CountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LakeCountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LapisCountRangeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.placement.UAPlacements;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillagePastStyledPiecesUA;


public final class StonePlainsBiomeUA extends UABiome
{
	public StonePlainsBiomeUA()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG)).precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.1F).scale(0.8F).temperature(0.43F).downfall(0.3F).waterColor(4159204).waterFogColor(329011).parent((String) null));


		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.AIR.getDefaultState(), 9)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 200, false, CountRangeAndTypeConfig.Type.IRON))));
		
//		this.addStructureFeature(UAFeatures.MINESHAFT.configure(new MineshaftConfigUA(MineshaftStructureUA.Type.STONE)));
//
//		this.addStructureFeature(UAFeatures.STRONGHOLD.configure(IFeatureConfig.NO_FEATURE_CONFIG));
//
//		this.addStructureFeature(UAFeatures.FORTRESS.configure(new FortressConfigUA(false)));
//
//		this.addStructureFeature(UAFeatures.VILLAGE.configure(new VillageConfigUA(0, VillagePastStyledPiecesUA.Type.STONE)));
//
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
//		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.RAVINE_CARVER, new ProbabilityConfig(0f)));
//		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
//		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.HANGING_RUINS.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(UAPlacements.LEDGE_UNDERSIDE_MINI_FEATURE.configure(new ChanceAndTypeConfig(0.9f, ChanceAndTypeConfig.Type.HANGING_RUINS))));
//
//		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, UAFeatures.NETHER_UNDERWATER_MAGMA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_UNDERGROUND_LIQUID.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//		this.addStructureFeaturesUA();
//		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.COLUMN.configure(new ColumnBlocksConfig(Blocks.ANDESITE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState())).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(5, 70, 0, 220))));
//		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.RAMP.configure(new ColumnBlocksConfig(Blocks.ANDESITE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState())).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(5, 70, 0, 220))));
//		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.GLOWPATCH.configure(new CountConfig(100)).createDecoratedFeature(UAPlacements.GLOWSTONE_VARIANT_PATCH.configure(new ChanceConfig(4))));
//		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.GLOWPATCH.configure(new CountConfig(100)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(0.005f, 45, 45, 70, false, CountRangeAndTypeConfig.Type.GLOWSTONE_VARIANT_PATCH))));
//		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_OCEAN_LIQUID.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//
//		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.STONED_LAKE.configure(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).createDecoratedFeature(UAPlacements.LAKE_PLACEMENT.configure(new LakeCountRangeAndTypeConfig(4, LakeCountRangeAndTypeConfig.Type.WATER_ALGORITHM))));
//
//		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.STONED_LAKE.configure(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).createDecoratedFeature(UAPlacements.LAKE_PLACEMENT.configure(new LakeCountRangeAndTypeConfig(80, LakeCountRangeAndTypeConfig.Type.LAVA_ALGORITHM))));
//
//		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.STONEHENGE.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(UAPlacements.AT_CENTER_SURFACE_MINI_FEATURE.configure(new ChanceAndTypeConfig(2.66f, ChanceAndTypeConfig.Type.STONEHENGE))));
//
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, UAFeatures.GENERIC_DUNGEONS.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(UAPlacements.DUNGEON_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIRT.getDefaultState(), 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 175))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRAVEL.getDefaultState(), 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(17, 0, 0, 100))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(14, 0, 0, 150))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 200))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 240, false, CountRangeAndTypeConfig.Type.COAL))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 200, false, CountRangeAndTypeConfig.Type.IRON))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 50, false, CountRangeAndTypeConfig.Type.GOLD))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 8)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.REDSTONE))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 8)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.DIAMOND))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7)).createDecoratedFeature(UAPlacements.LAPIS_PLACEMENT.configure(new LapisCountRangeConfig(1f, 20, 20, false))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 12)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(0.1f, 10, 0, 15, true, CountRangeAndTypeConfig.Type.COAL))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 7)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(0.067f, 15, 0, 10, true, CountRangeAndTypeConfig.Type.IRON))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 7)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 18, 0, 15, true, CountRangeAndTypeConfig.Type.GOLD))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 7)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(0.5f, 15, 0, 10, true, CountRangeAndTypeConfig.Type.REDSTONE))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 6)).createDecoratedFeature(UAPlacements.LAPIS_PLACEMENT.configure(new LapisCountRangeConfig(0.5f, 10, 8, true))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 12)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(2f, 15, 0, 12, true, CountRangeAndTypeConfig.Type.NOCONFIG))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 12)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(3f, 18, 0, 15, true, CountRangeAndTypeConfig.Type.NOCONFIG))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 10)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 21, 0, 18, true, CountRangeAndTypeConfig.Type.NOCONFIG))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(Blocks.COARSE_DIRT.getDefaultState(), 7, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()))).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_WATER_BOTTOMS.configure(new PercentageAndFrequencyConfig(0.9F, 3))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(Blocks.CLAY.getDefaultState(), 4, 1, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.STONE.getDefaultState()))).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_WATER_BOTTOMS.configure(new PercentageAndFrequencyConfig(1F, 4))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(Blocks.GRAVEL.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()))).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_WATER_BOTTOMS.configure(new PercentageAndFrequencyConfig(1F, 2))));
//		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.DEFAULT_FLOWER_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.1F, 3))));
//		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.GRASS_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.4F, 5))));
//		DefaultBiomeFeatures.addMushrooms(this);
//		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.SUGAR_CANE_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.70F, 5))));
//		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.PUMPKIN_PATCH_CONFIG).createDecoratedFeature(UAPlacements.TWICE_SURFACE_WITH_CHANCE.configure(new ChanceConfig(32))));
//
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).createDecoratedFeature(UAPlacements.WATERFALL_RANGE.configure(new CountRangeConfig(20, 8, 8, 256))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).createDecoratedFeature(UAPlacements.WATERFALL_RANGE.configure(new CountRangeConfig(1, 75, 8, 175))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(new LiquidsConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).createDecoratedFeature(UAPlacements.LAVAFALL_RANGE_2.configure(new CountRangeConfig(3, 75, 16, 200))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(new LiquidsConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).createDecoratedFeature(UAPlacements.LAVAFALL_RANGE_2.configure(new CountRangeConfig(2, 8, 16, 70))));
//
//		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, UAFeatures.SNOW_AND_ICE_LAYERER.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 1, 1, 4));
//		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.COD, 10, 3, 6));
//		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DOLPHIN, 1, 1, 2));
//		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 5));
//		this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
//		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
//		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
//		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
//		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
//		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
//		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
//		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
//		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));

	}
}
