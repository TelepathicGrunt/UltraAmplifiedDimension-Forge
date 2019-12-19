package net.telepathicgrunt.ultraamplified.world.biomes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HellLavaConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeUA;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.CountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillagePiecesUA;

public class NetherBiomeUA extends BiomeUA {
	public NetherBiomeUA() {
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(NETHER_SURFACE_BUILDER_UA, SurfaceBuilder.NETHERRACK_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).waterColor(12411469).waterFogColor(12076846).parent((String) null));

		this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA(MineshaftUA.Type.HELL));
		this.addStructure(FeatureUA.VILLAGE_UA, new VillageConfigUA(0, VillagePiecesUA.Type.HELL));
		this.addStructure(FeatureUA.STRONGHOLD_UA, IFeatureConfig.NO_FEATURE_CONFIG);
		this.addStructure(FeatureUA.FORTRESS_UA, new FortressConfigUA(true));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(FeatureUA.CONTAIN_UNDERGROUND_LIQUID, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.CONTAIN_LIQUID, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.NETHER_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.LAVA.getDefaultState()), Placement.COUNT_VERY_BIASED_RANGE, new CountRangeConfig(40, 8, 16, 256)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM.getDefaultState()), Placement.CHANCE_RANGE, new ChanceRangeConfig(0.9F, 10, 0, 255)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM.getDefaultState()),  Placement.CHANCE_RANGE, new ChanceRangeConfig(0.9F, 10, 0, 255)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.NETHER_SPRING, new HellLavaConfig(false), Placement.COUNT_RANGE, new CountRangeConfig(23, 4, 8, 256)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.HELL_FIRE, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.18F, 1)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.GLOWSTONE_BLOB, IFeatureConfig.NO_FEATURE_CONFIG, GLOWSTONE_PLACEMENT, IPlacementConfig.NO_PLACEMENT_CONFIG));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.GLOWSTONE_BLOB, IFeatureConfig.NO_FEATURE_CONFIG, GENERAL_PLACEMENT, new CountRangeAndTypeConfig(1f, 0, 0, 256, false, CountRangeAndTypeConfig.Type.GLOWSTONE)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.BUSH, new BushConfig(Blocks.NETHER_WART.getDefaultState()), NETHERWART_SOUL_SAND_SURFACES_UA, new PercentageAndFrequencyConfig(0.7F, 2)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM.getDefaultState()), Placement.CHANCE_RANGE, new ChanceRangeConfig(1F, 0, 0, 75)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM.getDefaultState()), Placement.CHANCE_RANGE, new ChanceRangeConfig(1F, 0, 0, 75)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM.getDefaultState()), CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.05F, 1)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM.getDefaultState()), CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.05F, 1)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.NETHER_QUARTZ_ORE.getDefaultState(), 25), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(1f, 18, 0, 256, true, CountRangeAndTypeConfig.Type.QUARTZ)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.NETHER_QUARTZ_ORE.getDefaultState(), 25), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(0.5f, 10, 20, 120, false, CountRangeAndTypeConfig.Type.QUARTZ)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.NETHER_QUARTZ_ORE.getDefaultState(), 25), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(0.1f, 10, 120, 240, false, CountRangeAndTypeConfig.Type.QUARTZ)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.MAGMA_BLOCK.getDefaultState(), 55), GENERAL_PLACEMENT, new CountRangeAndTypeConfig(1f, 10, 20, 100, false, CountRangeAndTypeConfig.Type.MAGMA)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(Feature.NETHER_SPRING, new HellLavaConfig(true), LAVAFALL_RANGE, new CountRangeConfig(1, 10, 20, 240)));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Biome.createDecoratedFeature(Feature.FREEZE_TOP_LAYER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.GHAST, 50, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_PIGMAN, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.MAGMA_CUBE, 2, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 1, 4, 4));
	}

	/**
	 * takes temperature, returns colors
	 */
	@OnlyIn(Dist.CLIENT)
	public int getSkyColorByTemp(float currentTemperature) {
		return 2621440;
	}

	@OnlyIn(Dist.CLIENT)
	public int getGrassColor(BlockPos pos) {
		return 9259264;
	}

	@OnlyIn(Dist.CLIENT)
	public int getFoliageColor(BlockPos pos) {
		return 8075008;
	}

}