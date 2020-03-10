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
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;


public class SwampSpookyBiomeUA extends UABiome
{
	public SwampSpookyBiomeUA()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.SWAMP, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)).precipitation(Biome.RainType.RAIN).category(Biome.Category.SWAMP).depth(-0.1F).scale(0.3F).temperature(0.8F).downfall(0.9F).waterColor(6388580).waterFogColor(2302743).parent("swamp"));

		this.addStructureFeature(UAFeatures.MINESHAFT.configure(new MineshaftConfigUA(MineshaftStructureUA.Type.SWAMPORDARKFOREST)));
		this.addStructureFeature(UAFeatures.WITCH_HUT.configure(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addStructureFeature(UAFeatures.STRONGHOLD.configure(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addStructureFeature(UAFeatures.FORTRESS.configure(new FortressConfigUA(false)));
		this.addStructureFeature(UAFeatures.WOODLAND_MANSION.configure(IFeatureConfig.NO_FEATURE_CONFIG));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.HANGING_RUINS.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(UAPlacements.LEDGE_UNDERSIDE_MINI_FEATURE.configure(new ChanceAndTypeConfig(0.6f, ChanceAndTypeConfig.Type.HANGING_RUINS))));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, UAFeatures.NETHER_UNDERWATER_MAGMA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_UNDERGROUND_LIQUID.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.COLUMN.configure(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.RAMP.configure(new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.GLOWPATCH.configure(new CountConfig(160)).createDecoratedFeature(UAPlacements.GLOWSTONE_VARIANT_PATCH.configure(new ChanceConfig(4))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.GLOWPATCH.configure(new CountConfig(100)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(0.005f, 45, 45, 70, false, CountRangeAndTypeConfig.Type.GLOWSTONE_VARIANT_PATCH))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_OCEAN_LIQUID.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.configure(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).createDecoratedFeature(UAPlacements.LAKE_PLACEMENT.configure(new LakeCountRangeAndTypeConfig(4, LakeCountRangeAndTypeConfig.Type.WATER_ALGORITHM))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.ROOTS.configure(new BlockConfig(Blocks.DARK_OAK_WOOD)).createDecoratedFeature(UAPlacements.RANDOM_BOTTOM_LAYER.configure(new CountRangeConfig(5, 70, 0, 250))));

		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.SHALLOW_LAKE.configure(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(40, 7))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.configure(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).createDecoratedFeature(UAPlacements.LAKE_PLACEMENT.configure(new LakeCountRangeAndTypeConfig(80, LakeCountRangeAndTypeConfig.Type.LAVA_ALGORITHM))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.STONELESS_LAKE.configure(new BlockStateFeatureConfig(Blocks.SLIME_BLOCK.getDefaultState())).createDecoratedFeature(UAPlacements.LAKE_PLACEMENT.configure(new LakeCountRangeAndTypeConfig(7, LakeCountRangeAndTypeConfig.Type.SLIME_ALGORITHM))));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, UAFeatures.FOSSILS.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(UAPlacements.RANDOM_SURFACE_BELOW_TOP_LAYER.configure(new ChanceConfig(50))));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.CROSS.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(5))));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.STONEHENGE.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(UAPlacements.AT_CENTER_SURFACE_MINI_FEATURE.configure(new ChanceAndTypeConfig(4f, ChanceAndTypeConfig.Type.STONEHENGE))));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, UAFeatures.SWAMP_DUNGEONS.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(UAPlacements.DUNGEON_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.UNDERGROUND_POCKET.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIRT.getDefaultState(), 13)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 175))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.UNDERGROUND_POCKET.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRAVEL.getDefaultState(), 13)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.UNDERGROUND_POCKET.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 13)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(17, 0, 0, 100))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.UNDERGROUND_POCKET.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 13)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(14, 0, 0, 150))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.UNDERGROUND_POCKET.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 13)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 200))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 240, false, CountRangeAndTypeConfig.Type.COAL))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 200, false, CountRangeAndTypeConfig.Type.IRON))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 50, false, CountRangeAndTypeConfig.Type.GOLD))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 8)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.REDSTONE))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 8)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.DIAMOND))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7)).createDecoratedFeature(UAPlacements.LAPIS_PLACEMENT.configure(new LapisCountRangeConfig(1f, 20, 20, false))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 7)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 18, 0, 15, true, CountRangeAndTypeConfig.Type.GOLD))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 7)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(0.5f, 15, 0, 10, true, CountRangeAndTypeConfig.Type.REDSTONE))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 6)).createDecoratedFeature(UAPlacements.LAPIS_PLACEMENT.configure(new LapisCountRangeConfig(0.5f, 10, 8, true))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(Blocks.COARSE_DIRT.getDefaultState(), 7, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()))).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_WATER_BOTTOMS.configure(new PercentageAndFrequencyConfig(0.9F, 3))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(Blocks.CLAY.getDefaultState(), 4, 1, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.STONE.getDefaultState()))).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_WATER_BOTTOMS.configure(new PercentageAndFrequencyConfig(1F, 4))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(Blocks.GRAVEL.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()))).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_WATER_BOTTOMS.configure(new PercentageAndFrequencyConfig(1F, 2))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, UAFeatures.HORNED_SWAMP_TREE.configure(DefaultBiomeFeatures.SWAMP_TREE_CONFIG).createDecoratedFeature(UAPlacements.AT_SURFACE_WITH_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.5F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, UAFeatures.HORNED_SWAMP_TREE.configure(DefaultBiomeFeatures.SWAMP_TREE_CONFIG).createDecoratedFeature(UAPlacements.AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.8F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.BLUE_ORCHID_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.15F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.GRASS_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.8F, 3))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.DEAD_BUSH_CONFIG).createDecoratedFeature(UAPlacements.AT_SURFACE_WITH_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.0F, 0))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.LILY_PAD_CONFIG).createDecoratedFeature(UAPlacements.AT_SURFACE_WITH_EXTRA.configure(new AtSurfaceWithExtraConfig(4, 0.0F, 0))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.04F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.04F, 1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).createDecoratedFeature(UAPlacements.RANDOM_CHANCE_UNDER_SURFACE.configure(new ChanceConfig(1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).createDecoratedFeature(UAPlacements.RANDOM_CHANCE_UNDER_SURFACE.configure(new ChanceConfig(2))));
		DefaultBiomeFeatures.addMushrooms(this);
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.SUGAR_CANE_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.70F, 5))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.PUMPKIN_PATCH_CONFIG).createDecoratedFeature(UAPlacements.TWICE_SURFACE_WITH_CHANCE.configure(new ChanceConfig(32))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).createDecoratedFeature(UAPlacements.WATERFALL_RANGE.configure(new CountRangeConfig(20, 8, 8, 256))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).createDecoratedFeature(UAPlacements.WATERFALL_RANGE.configure(new CountRangeConfig(1, 75, 8, 175))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(new LiquidsConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).createDecoratedFeature(UAPlacements.LAVAFALL_RANGE_2.configure(new CountRangeConfig(1, 75, 16, 175))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(new LiquidsConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).createDecoratedFeature(UAPlacements.LAVAFALL_RANGE_2.configure(new CountRangeConfig(1, 8, 16, 70))));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, UAFeatures.SNOW_AND_ICE_LAYERER.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.configure(new SeaGrassConfig(64, 0.6D)).createDecoratedFeature(Placement.TOP_SOLID_HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
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
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 3, 2, 4));
	}


	/*
	 * set grass color
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public int getGrassColorAt(double p_225528_1_, double p_225528_3_)
	{
		double d0 = INFO_NOISE.noiseAt(p_225528_1_ * 0.0225D, p_225528_3_ * 0.0225D, false);
		return d0 < -0.1D ? 5011004 : 6975545;
	}


	/*
	 * set foliage/plant color
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public int getFoliageColor()
	{
		return 6975545;
	}
}
