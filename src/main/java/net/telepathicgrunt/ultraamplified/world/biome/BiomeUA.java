package net.telepathicgrunt.ultraamplified.world.biome;

import java.util.Map.Entry;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.BuriedTreasureConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtra;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.HeightBiasedRange;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TwiceSurfaceWithChance;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.BadlandsSurfaceBuilderUA;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.DeepOceanSurfaceBuilder;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.DesertLakesSurfaceBuilder;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.EndSurfaceBuilderUA;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.ExtremeHillsMutatedSurfaceBuilderUA;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.GravelSurfaceBuilder;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.IceMountainSurfaceBuilder;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.MesaBryceSurfaceBuilderUA;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.ModifiedBadlandsSurfaceBuilderUA;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.NetherSurfaceBuilderUA;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.OceanSurfaceBuilder;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.PlateauSurfaceBuilder;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.SandSurfaceBuilder;
import net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder.ShatteredSavannaSurfaceBuilderUA;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.CountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LakeCountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LapisCountRangeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndHeightConfig;
import net.telepathicgrunt.ultraamplified.world.feature.placement.AllSoulSandSurfaces;
import net.telepathicgrunt.ultraamplified.world.feature.placement.AtBottomOfLedge;
import net.telepathicgrunt.ultraamplified.world.feature.placement.AtCenterSurfaceMiniFeature;
import net.telepathicgrunt.ultraamplified.world.feature.placement.AtSurfaceBelowTopLayerWithExtra;
import net.telepathicgrunt.ultraamplified.world.feature.placement.AtSurfaceRoofedForest;
import net.telepathicgrunt.ultraamplified.world.feature.placement.AtSurfaceThroughWaterWithExtra;
import net.telepathicgrunt.ultraamplified.world.feature.placement.AtSurfaceUnderTopLedgeWithChance;
import net.telepathicgrunt.ultraamplified.world.feature.placement.AtSurfaceWithChanceDesertWell;
import net.telepathicgrunt.ultraamplified.world.feature.placement.ChanceOnAllLiquidBottoms;
import net.telepathicgrunt.ultraamplified.world.feature.placement.ChanceOnAllLiquidSurfaces;
import net.telepathicgrunt.ultraamplified.world.feature.placement.ChanceOnAllSurfaces;
import net.telepathicgrunt.ultraamplified.world.feature.placement.DungeonPlacementBands;
import net.telepathicgrunt.ultraamplified.world.feature.placement.FixedHeightWithChance;
import net.telepathicgrunt.ultraamplified.world.feature.placement.GeneralConfigHookupPlacement;
import net.telepathicgrunt.ultraamplified.world.feature.placement.GlowstonePatchPlacement1;
import net.telepathicgrunt.ultraamplified.world.feature.placement.GlowstonePlacement;
import net.telepathicgrunt.ultraamplified.world.feature.placement.HeightBasedLavafallsRange;
import net.telepathicgrunt.ultraamplified.world.feature.placement.HeightBasedLavafallsRange2;
import net.telepathicgrunt.ultraamplified.world.feature.placement.HeightBasedWaterfallsRange;
import net.telepathicgrunt.ultraamplified.world.feature.placement.HeightBiasedEndIslandRange;
import net.telepathicgrunt.ultraamplified.world.feature.placement.LakePlacement;
import net.telepathicgrunt.ultraamplified.world.feature.placement.LapisPlacement;
import net.telepathicgrunt.ultraamplified.world.feature.placement.LedgeUndersideMiniFeature;
import net.telepathicgrunt.ultraamplified.world.feature.placement.PassthroughChest;
import net.telepathicgrunt.ultraamplified.world.feature.placement.RandomChanceUnderSurface;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillagePastStyledPiecesUA;


public class BiomeUA extends Biome
{

	//set up custom position placements
	public static final Placement<ChanceConfig> RANDOM_SURFACE_BELOW_TOP_LAYER = new AtSurfaceUnderTopLedgeWithChance(ChanceConfig::deserialize);
	public static final Placement<ChanceConfig> RANDOM_CHANCE_UNDER_SURFACE = new RandomChanceUnderSurface(ChanceConfig::deserialize);
	public static final Placement<CountRangeConfig> RANDOM_BOTTOM_LAYER = new AtBottomOfLedge(CountRangeConfig::deserialize);
	public static final Placement<ChanceConfig> TWICE_SURFACE_WITH_CHANCE_UA = new TwiceSurfaceWithChance(ChanceConfig::deserialize);
	public static final Placement<CountRangeConfig> LAVAFALL_RANGE = new HeightBasedLavafallsRange(CountRangeConfig::deserialize);
	public static final Placement<CountRangeConfig> LAVAFALL_RANGE_2 = new HeightBasedLavafallsRange2(CountRangeConfig::deserialize);
	public static final Placement<CountRangeConfig> WATERFALL_RANGE = new HeightBasedWaterfallsRange(CountRangeConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> CHANCE_ON_ALL_SURFACES_UA = new ChanceOnAllSurfaces(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> CHANCE_ON_ALL_WATER_SURFACES_UA = new ChanceOnAllLiquidSurfaces(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> CHANCE_ON_ALL_WATER_BOTTOMS_UA = new ChanceOnAllLiquidBottoms(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> NETHERWART_SOUL_SAND_SURFACES_UA = new AllSoulSandSurfaces(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> AT_SURFACE_WITH_EXTRA_UA = new AtSurfaceWithExtra(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA_UA = new AtSurfaceBelowTopLayerWithExtra(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> AT_SURFACE_THROUGH_WATER_WITH_EXTRA_UA = new AtSurfaceThroughWaterWithExtra(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> ROOFED_TREE_UA = new AtSurfaceRoofedForest(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<ChanceAndTypeConfig> AT_CENTER_SURFACE_MINI_FEATURE = new AtCenterSurfaceMiniFeature(ChanceAndTypeConfig::deserialize);
	public static final Placement<ChanceAndTypeConfig> LEDGE_UNDERSIDE_MINI_FEATURE = new LedgeUndersideMiniFeature(ChanceAndTypeConfig::deserialize);
	public static final Placement<ChanceConfig> GLOWSTONE_VARIANT_PATCH = new GlowstonePatchPlacement1(ChanceConfig::deserialize);
	public static final Placement<NoPlacementConfig> DUNGEON_PLACEMENT = new DungeonPlacementBands(NoPlacementConfig::deserialize);
	public static final Placement<NoPlacementConfig> GLOWSTONE_PLACEMENT = new GlowstonePlacement(NoPlacementConfig::deserialize);
	public static final Placement<CountRangeAndTypeConfig> GENERAL_PLACEMENT = new GeneralConfigHookupPlacement(CountRangeAndTypeConfig::deserialize);
	public static final Placement<LapisCountRangeConfig> LAPIS_PLACEMENT = new LapisPlacement(LapisCountRangeConfig::deserialize);
	public static final Placement<CountRangeConfig> HEIGHT_BIASED_RANGE_UA = new HeightBiasedRange(CountRangeConfig::deserialize);
	public static final Placement<CountRangeConfig> HEIGHT_BIASED_END_ISLAND_RANGE_UA = new HeightBiasedEndIslandRange(CountRangeConfig::deserialize);
	public static final Placement<PercentageAndHeightConfig> FIXED_HEIGHT_WITH_CHANCE = new FixedHeightWithChance(PercentageAndHeightConfig::deserialize);
	//needed so we can prevent vanilla Treasure Chest from spawning if config is off
	public static final Placement<NoPlacementConfig> PASSTHROUGH_CHEST = new PassthroughChest(NoPlacementConfig::deserialize);
	//needed so we can prevent vanilla Desert Well from spawning if config is off
	public static final Placement<ChanceConfig> AT_SURFACE_WITH_CHANCE_DESERT_WELL = new AtSurfaceWithChanceDesertWell(ChanceConfig::deserialize);
	//needed so we can prevent lava lakes and water lakes from spawning if config is off
	public static final Placement<LakeCountRangeAndTypeConfig> LAKE_PLACEMENT = new LakePlacement(LakeCountRangeAndTypeConfig::deserialize);

	protected static final BlockState SANDSTONE = Blocks.SANDSTONE.getDefaultState();
	protected static final BlockState WATER = Blocks.WATER.getDefaultState();
	protected static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
	protected static final BlockState ICE = Blocks.ICE.getDefaultState();
	protected static final BlockState SAND = Blocks.SAND.getDefaultState();
	protected static final BlockState DIRT = Blocks.DIRT.getDefaultState();
	protected static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
	protected static final BlockState STONE = Blocks.STONE.getDefaultState();
	protected static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.getDefaultState();
	protected static final BlockState COARSE_DIRT = Blocks.COARSE_DIRT.getDefaultState();
	protected static final BlockState RED_TERRACOTTA = Blocks.RED_TERRACOTTA.getDefaultState();
	protected static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();

	public static final SurfaceBuilderConfig SAND_SAND_SANDSTONE_SURFACE = new SurfaceBuilderConfig(SAND, SAND, SANDSTONE);
	public static final SurfaceBuilderConfig SAND_SANDSTONE_SANDSTONE_SURFACE = new SurfaceBuilderConfig(SAND, SANDSTONE, SANDSTONE);
	public static final SurfaceBuilderConfig SANDSTONE_SURFACE = new SurfaceBuilderConfig(SANDSTONE, SANDSTONE, SANDSTONE);
	public static final SurfaceBuilderConfig THIN_WATER_SURFACE = new SurfaceBuilderConfig(WATER, DIRT, GRAVEL);
	public static final SurfaceBuilderConfig SNOWBLOCK_ICE_ICE_SURFACE = new SurfaceBuilderConfig(SNOW_BLOCK, ICE, ICE);
	public static final SurfaceBuilderConfig GRASS_GRAVEL_STONE_SURFACE = new SurfaceBuilderConfig(GRASS_BLOCK, GRAVEL, STONE);
	public static final SurfaceBuilderConfig ICE_GRAVEL_STONE_SURFACE = new SurfaceBuilderConfig(ICE, GRAVEL, STONE);
	public static final SurfaceBuilderConfig GRASS_GRAVEL_DEAD_CORAL_SURFACE = new SurfaceBuilderConfig(GRASS_BLOCK, GRAVEL, Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState());
	public static final SurfaceBuilderConfig GRASS_SAND_SANDSTONE_SURFACE = new SurfaceBuilderConfig(GRASS_BLOCK, SAND, SANDSTONE);
	public static final SurfaceBuilderConfig GRASS_SAND_DEAD_CORAL_SURFACE = new SurfaceBuilderConfig(GRASS_BLOCK, SAND, Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState());
	public static final SurfaceBuilderConfig COARSE_DIRT_COARSE_DIRT_GRAVEL_SURFACE = new SurfaceBuilderConfig(COARSE_DIRT, COARSE_DIRT, GRAVEL);
	public static final SurfaceBuilderConfig RED_TERRACOTTA_WHITE_TERRACOTTA_GRAVEL_SURFACE = new SurfaceBuilderConfig(RED_TERRACOTTA, WHITE_TERRACOTTA, GRAVEL);

	public static final SurfaceBuilder<SurfaceBuilderConfig> DESERT_LAKE_SURFACE_BUILDER = new DesertLakesSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> BADLANDS_SURFACE_BUILDER_UA = new BadlandsSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> MODIFIED_BADLANDS_SURFACE_BUILDER_UA = new ModifiedBadlandsSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> ERODED_BADLANDS = new MesaBryceSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> ICE_MOUNTAIN_SURFACE_BUILDER = new IceMountainSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> NETHER_SURFACE_BUILDER_UA = new NetherSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> END_SURFACE_BUILDER_UA = new EndSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> OCEAN_SURFACE_BUILDER_UA = new OceanSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> DEEP_OCEAN_SURFACE_BUILDER_UA = new DeepOceanSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> SAND_SURFACE_BUILDER = new SandSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> GRAVEL_SURFACE_BUILDER = new GravelSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> EXTREME_HILLS_MUTATED_SURFACE_BUILDER_UA = new ExtremeHillsMutatedSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> PLATEAU_SURFACE_BUILDER = new PlateauSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig> SHATTERED_SAVANNA_SURFACE_BUILDER_UA = new ShatteredSavannaSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);


	protected BiomeUA(Biome.Builder biomeBuilder)
	{
		super(biomeBuilder);
	}


	protected void addStructureFeaturesUA()
	{
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, FeatureUA.MINESHAFT_UA.configure(new MineshaftConfigUA(MineshaftStructureUA.Type.NORMAL)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, FeatureUA.STRONGHOLD_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.VILLAGE.configure(new VillageConfig("village/plains/town_centers", 6)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.VILLAGE_UA.configure(new VillageConfigUA(0, VillagePastStyledPiecesUA.Type.OAK)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, FeatureUA.FORTRESS_UA.configure(new FortressConfigUA(false)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.DESERT_TEMPLE_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.OCEAN_RUIN_UA.configure(new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.9F)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.OCEAN_MONUMENT_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.SHIPWRECK_UA.configure(new ShipwreckConfig(false)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.END_CITY_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.IGLOO_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.JUNGLE_TEMPLE_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.WITCH_HUT_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.WOODLAND_MANSION_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Feature.BURIED_TREASURE.configure(new BuriedTreasureConfig(0.01F)).createDecoratedFeature(PASSTHROUGH_CHEST.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.PILLAGER_OUTPOST_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.MUSHROOM_TEMPLE_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureUA.ICE_SPIKE_TEMPLE_UA.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
	}


	public <C extends IFeatureConfig> void addStructure(Entry<Structure<?>, IFeatureConfig> structureEntry)
	{
		this.structures.put(structureEntry.getKey(), structureEntry.getValue());
	}
}
