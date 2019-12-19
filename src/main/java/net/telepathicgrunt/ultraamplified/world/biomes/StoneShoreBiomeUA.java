package net.telepathicgrunt.ultraamplified.world.biomes;

import com.google.common.collect.Lists;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GrassFeatureConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LakesConfig;
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
import net.telepathicgrunt.ultraamplified.world.biome.BiomeUA;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.CountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LakeCountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LapisCountRangeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillagePiecesUA;

public final class StoneShoreBiomeUA extends BiomeUA {
	public StoneShoreBiomeUA() {
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG)).precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.1F).scale(0.8F).temperature(0.44F).downfall(0.3F).waterColor(4159204).waterFogColor(329011).parent((String) null));

		this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA(MineshaftUA.Type.STONE));

		this.addStructure(FeatureUA.STRONGHOLD_UA, IFeatureConfig.NO_FEATURE_CONFIG);

		this.addStructure(FeatureUA.FORTRESS_UA, new FortressConfigUA(false));

		this.addStructure(FeatureUA.VILLAGE_UA, new VillageConfigUA(0, VillagePiecesUA.Type.STONE));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.HANGING_RUINS, IFeatureConfig.NO_FEATURE_CONFIG, LEDGE_UNDERSIDE_MINI_FEATURE, new ChanceAndTypeConfig(1f, ChanceAndTypeConfig.Type.HANGING_RUINS)));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(FeatureUA.CONTAIN_UNDERGROUND_LIQUID, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.COLUMN, new ColumnBlocksConfig(Blocks.ANDESITE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState()), Placement.COUNT_RANGE, new CountRangeConfig(2, 70, 0, 220)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.RAMP, new ColumnBlocksConfig(Blocks.ANDESITE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState()), Placement.COUNT_RANGE, new CountRangeConfig(2, 70, 0, 220)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.GLOWPATCH, new CountConfig(100), GLOWSTONE_VARIANT_PATCH, new ChanceConfig(4)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.GLOWPATCH, new CountConfig(100), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(0.005f, 45, 45, 70, false, CountRangeAndTypeConfig.Type.GLOWSTONE_VARIANT_PATCH)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.CONTAIN_LIQUID, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(Feature.LAKE, new LakesConfig(Blocks.WATER.getDefaultState()), LAKE_PLACEMENT, new LakeCountRangeAndTypeConfig(4, LakeCountRangeAndTypeConfig.Type.WATER)));

		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(Feature.LAKE, new LakesConfig(Blocks.LAVA.getDefaultState()), LAKE_PLACEMENT, new LakeCountRangeAndTypeConfig(80, LakeCountRangeAndTypeConfig.Type.LAVA)));

		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.STONEHENGE, IFeatureConfig.NO_FEATURE_CONFIG, AT_CENTER_SURFACE_MINI_FEATURE, new ChanceAndTypeConfig(2.66f, ChanceAndTypeConfig.Type.STONEHENGE)));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.GENERIC_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, IPlacementConfig.NO_PLACEMENT_CONFIG));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIRT.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(10, 0, 0, 175)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRAVEL.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(8, 0, 0, 256)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(17, 0, 0, 100)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(14, 0, 0, 150)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 200)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(1f, 0, 0, 240, false, CountRangeAndTypeConfig.Type.COAL)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(1f, 0, 0, 200, false, CountRangeAndTypeConfig.Type.IRON)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(1f, 0, 0, 50, false, CountRangeAndTypeConfig.Type.GOLD)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 8), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.REDSTONE)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 8), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.DIAMOND)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7), LAPIS_PLACEMENT, new LapisCountRangeConfig(1f, 20, 20, false)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 12), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(0.1f, 10, 0, 15, true, CountRangeAndTypeConfig.Type.COAL)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 7), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(0.067f, 15, 0, 10, true, CountRangeAndTypeConfig.Type.IRON)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 7), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(1f, 18, 0, 15, true, CountRangeAndTypeConfig.Type.GOLD)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 7), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(0.5f, 15, 0, 10, true, CountRangeAndTypeConfig.Type.REDSTONE)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 6), LAPIS_PLACEMENT, new LapisCountRangeConfig(0.5f, 10, 8, true)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 12), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(2f, 15, 0, 12, true, CountRangeAndTypeConfig.Type.NOCONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 12), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(3f, 18, 0, 15, true, CountRangeAndTypeConfig.Type.NOCONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 10), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(1f, 21, 0, 18, true, CountRangeAndTypeConfig.Type.NOCONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, new SphereReplaceConfig(Blocks.COARSE_DIRT.getDefaultState(), 7, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())), CHANCE_ON_ALL_WATER_BOTTOMS_UA, new PercentageAndFrequencyConfig(0.9F, 3)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, new SphereReplaceConfig(Blocks.CLAY.getDefaultState(), 4, 1, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.STONE.getDefaultState())), CHANCE_ON_ALL_WATER_BOTTOMS_UA, new PercentageAndFrequencyConfig(1F, 4)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, new SphereReplaceConfig(Blocks.GRAVEL.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())), CHANCE_ON_ALL_WATER_BOTTOMS_UA, new PercentageAndFrequencyConfig(1F, 2)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.DEFAULT_FLOWER, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.1F, 3)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.GRASS, new GrassFeatureConfig(Blocks.GRASS.getDefaultState()), CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.25F, 3)));
		DefaultBiomeFeatures.addMushrooms(this);
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.70F, 5)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, TWICE_SURFACE_WITH_CHANCE_UA, new ChanceConfig(32)));

		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.WATER.getDefaultState()), WATERFALL_RANGE, new CountRangeConfig(34, 8, 8, 256)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.WATER.getDefaultState()), WATERFALL_RANGE, new CountRangeConfig(1, 75, 8, 175)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.LAVA.getDefaultState()), LAVAFALL_RANGE_2, new CountRangeConfig(1, 75, 16, 175)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.LAVA.getDefaultState()), LAVAFALL_RANGE_2, new CountRangeConfig(1, 8, 16, 70)));

		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Biome.createDecoratedFeature(Feature.FREEZE_TOP_LAYER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
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