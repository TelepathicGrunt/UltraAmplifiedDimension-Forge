package net.telepathicgrunt.ultraamplified.world.biomes;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.ultraamplified.world.biome.UABiome;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.UASurfaceBuilders;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;
import net.telepathicgrunt.ultraamplified.world.feature.carver.UACarvers;
import net.telepathicgrunt.ultraamplified.world.feature.config.CountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LapisCountRangeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndHeightConfig;
import net.telepathicgrunt.ultraamplified.world.feature.placement.UAPlacements;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;


public class OceanDeepFrozenBiomeUA extends UABiome
{
	protected static final PerlinNoiseGenerator NOISE_GENERATOR = new PerlinNoiseGenerator(new SharedSeedRandom(3456L), 2, 0);


	public OceanDeepFrozenBiomeUA()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(UASurfaceBuilders.DEEP_OCEAN_SURFACE_BUILDER_UA, UASurfaceBuilders.ICE_GRAVEL_STONE_SURFACE)).precipitation(Biome.RainType.SNOW).category(Biome.Category.OCEAN).depth(-1.8F).scale(0.1F).temperature(0.0F).downfall(0.5F).waterColor(3750089).waterFogColor(329011).parent((String) null));

		this.addStructureFeature(UAFeatures.MINESHAFT_UA.configure(new MineshaftConfigUA(MineshaftStructureUA.Type.OCEAN)));
		this.addStructureFeature(UAFeatures.STRONGHOLD_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addStructureFeature(UAFeatures.FORTRESS_UA.configure(new FortressConfigUA(false)));
		this.addStructureFeature(UAFeatures.SHIPWRECK_UA.configure(new ShipwreckConfig(false)));
		this.addStructureFeature(UAFeatures.OCEAN_MONUMENT_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, UAFeatures.MARKED_TREASURE_CHEST_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(UAPlacements.FIXED_HEIGHT_WITH_CHANCE.configure(new PercentageAndHeightConfig(0.025f, 64))));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(UACarvers.UNDERWATER_CAVE_CARVER, new ProbabilityConfig(0f)));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, UAFeatures.NETHER_UNDERWATER_MAGMA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_UNDERGROUND_LIQUID.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.GLOWPATCH.configure(new CountConfig(100)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(0.005f, 45, 45, 60, false, CountRangeAndTypeConfig.Type.GLOWSTONE_VARIANT_PATCH))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.CONTAIN_OCEAN_LIQUID.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.ICEBERG_UA.configure(new BlockStateFeatureConfig(Blocks.PACKED_ICE.getDefaultState())).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_WATER_SURFACES.configure(new PercentageAndFrequencyConfig(0.35F, 6))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UAFeatures.ICEBERG_UA.configure(new BlockStateFeatureConfig(Blocks.BLUE_ICE.getDefaultState())).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_WATER_SURFACES.configure(new PercentageAndFrequencyConfig(0.09F, 2))));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, UAFeatures.SNOW_DUNGEONS.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(UAPlacements.DUNGEON_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.BLUE_ICE.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.RANDOM_COUNT_RANGE.configure(new CountRangeConfig(20, 40, 32, 160))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ICE.getDefaultState(), 80)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(9, 0, 0, 100))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIRT.getDefaultState(), 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(6, 0, 0, 175))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRAVEL.getDefaultState(), 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 100))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(6, 0, 0, 150))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(13, 0, 0, 200))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 240, false, CountRangeAndTypeConfig.Type.COAL))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 200, false, CountRangeAndTypeConfig.Type.IRON))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 50, false, CountRangeAndTypeConfig.Type.GOLD))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 8)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.REDSTONE))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 8)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 0, 0, 25, false, CountRangeAndTypeConfig.Type.DIAMOND))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7)).createDecoratedFeature(UAPlacements.LAPIS_PLACEMENT.configure(new LapisCountRangeConfig(1.5f, 20, 20, false))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 7)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(1f, 18, 0, 15, true, CountRangeAndTypeConfig.Type.GOLD))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 7)).createDecoratedFeature(UAPlacements.GENERAL_PLACEMENT.configure(new CountRangeAndTypeConfig(0.5f, 15, 0, 10, true, CountRangeAndTypeConfig.Type.REDSTONE))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 6)).createDecoratedFeature(UAPlacements.LAPIS_PLACEMENT.configure(new LapisCountRangeConfig(1f, 10, 8, true))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.DEFAULT_FLOWER_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.2F, 2))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.GRASS_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.3F, 2))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).createDecoratedFeature(UAPlacements.RANDOM_CHANCE_UNDER_SURFACE.configure(new ChanceConfig(1))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).createDecoratedFeature(UAPlacements.RANDOM_CHANCE_UNDER_SURFACE.configure(new ChanceConfig(2))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(UAFeatures.getConfiguredClusterConfig(DefaultBiomeFeatures.SUGAR_CANE_CONFIG)).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_SURFACES.configure(new PercentageAndFrequencyConfig(0.2F, 4))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.PUMPKIN_PATCH_CONFIG).createDecoratedFeature(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(32))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, UAFeatures.KELP_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(UAPlacements.CHANCE_ON_ALL_WATER_BOTTOMS.configure(new PercentageAndFrequencyConfig(0.1F, 2))));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).createDecoratedFeature(UAPlacements.WATERFALL_RANGE.configure(new CountRangeConfig(30, 8, 8, 256))));

		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, UAFeatures.SNOW_AND_ICE_LAYERER.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 3, 1, 4));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SALMON, 40, 1, 5));
		this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.POLAR_BEAR, 8, 1, 2));
		this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.TURTLE, 5, 2, 5));
		this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 1));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
	}


	/**
	 * Gets the current temperature at the given location, based off of the default for this biome, the elevation of the
	 * position, and {@linkplain #TEMPERATURE_NOISE} some random perlin noise.
	 */
	@Override
	public float getTemperature(BlockPos pos)
	{
		float f = this.getDefaultTemperature();
		double d0 = NOISE_GENERATOR.noiseAt(pos.getX() * 0.05D, pos.getZ() * 0.05D, false) * 7.0D;
		double d1 = INFO_NOISE.noiseAt(pos.getX() * 0.2D, pos.getZ() * 0.2D, false);
		double d2 = d0 + d1;
		if (d2 < 0.3D)
		{
			double d3 = INFO_NOISE.noiseAt(pos.getX() * 0.09D, pos.getZ() * 0.09D, false);
			if (d3 < 0.8D)
			{
				f = 0.2F;
			}
		}

		if (pos.getY() > 64)
		{
			float f1 = (float) (TEMPERATURE_NOISE.noiseAt(pos.getX() / 8.0F, pos.getZ() / 8.0F, false) * 4.0D);
			return f - (f1 + pos.getY() - 64.0F) * 0.05F / 30.0F;
		}
		else
		{
			return f;
		}
	}


	/*
	 * set grass color
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public int getGrassColorAt(double p_225528_1_, double p_225528_3_)
	{
		return 3838576;
	}


	/*
	 * set foliage/plant color
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public int getFoliageColor()
	{
		return 4099688;
	}
}
