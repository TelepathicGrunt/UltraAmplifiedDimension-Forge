package net.telepathicgrunt.ultraamplified.world.biomes;

import com.google.common.collect.Lists;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.telepathicgrunt.ultraamplified.world.biome.UABiome;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.UASurfaceBuilders;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;
import net.telepathicgrunt.ultraamplified.world.feature.carver.UACarvers;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.placement.UAPlacements;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillagePastStyledPiecesUA;


public class IcedTerrainBiomeUA extends UABiome
{
	public IcedTerrainBiomeUA()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(UASurfaceBuilders.ICED_TERRAIN_SURFACE_BUILDER, UASurfaceBuilders.SNOWBLOCK_ICE_ICE_SURFACE)).precipitation(Biome.RainType.SNOW).category(Biome.Category.ICY).depth(0.45F).scale(0.3F).temperature(0.0F).downfall(0.5F).waterColor(13172735).waterFogColor(13172735).parent((String) null));
		this.addStructure(UAFeatures.MINESHAFT.withConfiguration(new MineshaftConfigUA(MineshaftStructureUA.Type.ICEY)));
		this.addStructure(UAFeatures.STRONGHOLD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addStructure(UAFeatures.STONE_FORTRESS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addStructure(UAFeatures.IGLOO.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addStructure(UAFeatures.VILLAGE.withConfiguration(new VillageConfigUA(0, VillagePastStyledPiecesUA.Type.ICY)));
		this.addStructureFeaturesUA();

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, UAFeatures.NETHER_UNDERWATER_MAGMA.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_UNDERGROUND_LIQUID.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.COLUMN.withConfiguration(new ColumnBlocksConfig(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.ICE.getDefaultState(), Blocks.ICE.getDefaultState())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 70, 0, 220))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_OCEAN_LIQUID.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.STONELESS_LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.PACKED_ICE.getDefaultState())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 30, 0, 250))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.STONELESS_LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.PACKED_ICE.getDefaultState())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 17, 0, 100))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.STONELESS_LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.BLUE_ICE.getDefaultState())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 10, 0, 40))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, UAFeatures.SNOW_DUNGEONS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.DUNGEON_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		DefaultBiomeFeatures.addMushrooms(this);
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.DISK_DRY.withConfiguration(new SphereReplaceConfig(Blocks.LAPIS_BLOCK.getDefaultState(), 0, 0, Lists.newArrayList(Blocks.ICE.getDefaultState()))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(3, 75, 0, 220))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, UAFeatures.DISK_DRY.withConfiguration(new SphereReplaceConfig(Blocks.LAPIS_BLOCK.getDefaultState(), 0, 0, Lists.newArrayList(Blocks.ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), Blocks.BLUE_ICE.getDefaultState()))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(5, 0, 0, 75))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, UAFeatures.BLUE_ICE_WATERFALL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_BIASED_RANGE.configure(new CountRangeConfig(16, 8, 8, 75))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, UAFeatures.BLUE_ICE_WATERFALL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(UAPlacements.HEIGHT_BIASED_RANGE.configure(new CountRangeConfig(36, 75, 8, 225))));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, UAFeatures.SNOW_AND_ICE_LAYERER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.POLAR_BEAR, 1, 1, 2));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 2, 1, 4));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SALMON, 5, 1, 5));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 5));
		this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CAVE_SPIDER, 150, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 20, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.STRAY, 200, 4, 4));
	}


	/**
	 * returns the chance a creature has to spawn.
	 */
	@Override
	public float getSpawningChance()
	{
		return 0.04F;
	}
}
