package net.telepathicgrunt.ultraamplified.world.biomes;

import com.google.common.collect.Lists;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
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

public class EndBarrenFieldBiomeUA extends BiomeUA {
	public EndBarrenFieldBiomeUA() {
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(END_SURFACE_BUILDER_UA, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1F).scale(0.2F).temperature(0.7F).downfall(0.5F).waterColor(7299198).waterFogColor(8542866).parent((String) null));

		this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA(MineshaftUA.Type.END));
		this.addStructure(FeatureUA.VILLAGE_UA, new VillageConfigUA(0, VillagePiecesUA.Type.END));
		this.addStructure(FeatureUA.END_CITY_UA, IFeatureConfig.NO_FEATURE_CONFIG);

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE_CAVITY_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(LONG_RAVINE_CARVER, new ProbabilityConfig(0f)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.HANGING_RUINS, IFeatureConfig.NO_FEATURE_CONFIG, LEDGE_UNDERSIDE_MINI_FEATURE, new ChanceAndTypeConfig(1f, ChanceAndTypeConfig.Type.HANGING_RUINS)));

		this.addFeature(GenerationStage.Decoration.RAW_GENERATION, Biome.createDecoratedFeature(FeatureUA.CONTAIN_UNDERGROUND_LIQUID, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.COLUMN, new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()), Placement.COUNT_RANGE, new CountRangeConfig(2, 70, 0, 220)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.RAMP, new ColumnBlocksConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()), Placement.COUNT_RANGE, new CountRangeConfig(2, 70, 0, 220)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.GLOWPATCH, new CountConfig(160), GLOWSTONE_VARIANT_PATCH, new ChanceConfig(2)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.CONTAIN_LIQUID, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.END_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(FeatureUA.DISK_DRY, new SphereReplaceConfig(Blocks.END_STONE.getDefaultState(), 5, 3, Lists.newArrayList(Blocks.STONE.getDefaultState(), Blocks.DIRT.getDefaultState())), Placement.COUNT_RANGE, new CountRangeConfig(4, 70, 10, 250)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(FeatureUA.DISK_DRY, new SphereReplaceConfig(Blocks.OBSIDIAN.getDefaultState(), 5, 2, Lists.newArrayList( Blocks.GRASS_BLOCK.getDefaultState())), Placement.COUNT_RANGE, new CountRangeConfig(3, 10, 10, 250)));
		
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.END_TREE, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.END_TREE, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.TAIGA_GRASS, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.4F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SWEET_BERRY_BUSH, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.1F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.WATER.getDefaultState()), WATERFALL_RANGE, new CountRangeConfig(2, 70, 8, 256)));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Biome.createDecoratedFeature(Feature.FREEZE_TOP_LAYER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMITE, 10, 4, 12));
	}

	/**
	 * takes temperature, returns color
	 */
	@OnlyIn(Dist.CLIENT)
	public int getSkyColorByTemp(float currentTemperature) {
		return 2369577;
	}

	@OnlyIn(Dist.CLIENT)
	public int getGrassColor(BlockPos pos) {
		return 7045758;
	}

	@OnlyIn(Dist.CLIENT)
	public int getFoliageColor(BlockPos pos) {
		return 6060912;
	}

}