package net.TelepathicGrunt.UltraAmplified.World.Biome;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.BadlandsSurfaceBuilderUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.DeepOceanSurfaceBuilder;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.DesertLakesSurfaceBuilder;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.EndSurfaceBuilderUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.ExtremeHillsMutatedSurfaceBuilderUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.GravelSurfaceBuilder;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.IceMountainSurfaceBuilder;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.MesaBryceSurfaceBuilderUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.NetherSurfaceBuilderUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.OceanSurfaceBuilder;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.PlateauSurfaceBuilder;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.SandSurfaceBuilder;
import net.TelepathicGrunt.UltraAmplified.World.Biome.Surfacebuilder.ShatteredSavannaSurfaceBuilderUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.CaveCavityCarver;
import net.TelepathicGrunt.UltraAmplified.World.Feature.FeatureUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.RavineCarver;
import net.TelepathicGrunt.UltraAmplified.World.Feature.SuperLongRavineCarver;
import net.TelepathicGrunt.UltraAmplified.World.Feature.UnderwaterCaveCarver;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Config.CountRangeAndTypeConfig;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Config.PercentageAndFrequencyConfig;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Config.PercentageAndHeightConfig;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.AllSoulSandSurfacesUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.AtBottomOfRandomLayerUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.AtCenterSurfaceWithChanceUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.AtSurfaceRoofedForestUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.AtSurfaceThroughWaterWithExtraUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.AtSurfaceUnderTopLayerWithChanceUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.AtSurfaceWithChanceDesertWell;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.AtSurfaceWithExtraUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.ChanceOnAllLiquidBottomsUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.ChanceOnAllLiquidSurfacesUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.ChanceOnAllSurfacesUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.DungeonPlacementBands;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.EmeraldPlacement;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.FixedHeightWithChance;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.GlowstonePlacementUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.HeightBiasedRangeUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.LakePlacementUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.PassthroughChest;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.RandomChanceUnderSurface;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.RandomPositionEvery5Height;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Placement.TwiceSurfaceWithChanceUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.MineshaftConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.MineshaftUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.FortressConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.VillagePiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.VillageUAConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.structure.BuriedTreasureConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BiomeUA extends Biome {

    //set up custom position placements
	public static final Placement<FrequencyConfig> RANDOM_POSITION_EVERY_5_HEIGHT = new RandomPositionEvery5Height(FrequencyConfig::deserialize);
	public static final Placement<ChanceConfig> RANDOM_SURFACE_BELOW_TOP_LAYER = new AtSurfaceUnderTopLayerWithChanceUA(ChanceConfig::deserialize);
	public static final Placement<ChanceConfig> RANDOM_CHANCE_UNDER_SURFACE = new RandomChanceUnderSurface(ChanceConfig::deserialize);
	public static final Placement<CountRangeConfig> RANDOM_BOTTOM_LAYER = new AtBottomOfRandomLayerUA(CountRangeConfig::deserialize);
	public static final Placement<ChanceConfig> TWICE_SURFACE_WITH_CHANCE_UA = new TwiceSurfaceWithChanceUA(ChanceConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> CHANCE_ON_ALL_SURFACES_UA = new ChanceOnAllSurfacesUA(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> CHANCE_ON_ALL_WATER_SURFACES_UA = new ChanceOnAllLiquidSurfacesUA(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> CHANCE_ON_ALL_WATER_BOTTOMS_UA = new ChanceOnAllLiquidBottomsUA(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> NETHERWART_SOUL_SAND_SURFACES_UA = new AllSoulSandSurfacesUA(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> AT_SURFACE_WITH_EXTRA_UA = new AtSurfaceWithExtraUA(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> AT_SURFACE_THROUGH_WATER_WITH_EXTRA_UA = new AtSurfaceThroughWaterWithExtraUA(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> ROOFED_TREE_UA = new AtSurfaceRoofedForestUA(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<ChanceConfig> AT_CENTER_SURFACE_WITH_CHANCE = new AtCenterSurfaceWithChanceUA(ChanceConfig::deserialize);
    public static final WorldCarver<ProbabilityConfig> RAVINE_CARVER = new RavineCarver(ProbabilityConfig::deserialize, 70);
    public static final WorldCarver<ProbabilityConfig> LONG_RAVINE_CARVER = new SuperLongRavineCarver(ProbabilityConfig::deserialize, 50);
    public static final WorldCarver<ProbabilityConfig> CAVE_CAVITY_CARVER = new CaveCavityCarver(ProbabilityConfig::deserialize, 70);
    public static final WorldCarver<ProbabilityConfig> UNDERWATER_CAVE_CARVER = new UnderwaterCaveCarver(ProbabilityConfig::deserialize);
    public static final Placement<DungeonRoomConfig> DUNGEON_PLACEMENT = new DungeonPlacementBands(DungeonRoomConfig::deserialize);
    public static final Placement<CountRangeConfig> EMERALD_PLACEMENT = new EmeraldPlacement(CountRangeConfig::deserialize);
    public static final Placement<FrequencyConfig> GLOWSTONE_PLACEMENT = new GlowstonePlacementUA(FrequencyConfig::deserialize);
    public static final Placement<CountRangeConfig> HEIGHT_BIASED_RANGE_UA = new HeightBiasedRangeUA(CountRangeConfig::deserialize);
    public static final Placement<PercentageAndHeightConfig> FIXED_HEIGHT_WITH_CHANCE = new FixedHeightWithChance(PercentageAndHeightConfig::deserialize);
    //needed so we can prevent vanilla Treasure Chest from spawning if config is off
    public static final Placement<NoPlacementConfig> PASSTHROUGH_CHEST = new PassthroughChest(NoPlacementConfig::deserialize);
    //needed so we can prevent vanilla Desert Well from spawning if config is off
    public static final Placement<ChanceConfig> AT_SURFACE_WITH_CHANCE_DESERT_WELL = new AtSurfaceWithChanceDesertWell(ChanceConfig::deserialize);
    //needed so we can prevent lava lakes and water lakes from spawning if config is off
    public static final Placement<CountRangeAndTypeConfig> LAKE_PLACEMENT = new LakePlacementUA(CountRangeAndTypeConfig::deserialize);
    
    

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

    public static final SurfaceBuilder<SurfaceBuilderConfig> DESERT_LAKE_SURFACE_BUILDER = new DesertLakesSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    public static final SurfaceBuilder<SurfaceBuilderConfig> BADLANDS_SURFACE_BUILDER_UA = new BadlandsSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
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
	
	protected BiomeUA(Biome.Builder biomeBuilder) {
		super(biomeBuilder);
	}
	
	
	protected void addStructureFeaturesUA() {
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA((double)ConfigUA.mineshaftSpawnrate, MineshaftUA.Type.NORMAL), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.STRONGHOLD_UA, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(Feature.VILLAGE, new VillageConfig("village/plains/town_centers", 6), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.VILLAGE_UA, new VillageUAConfig(0, VillagePiecesUA.Type.OAK), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(FeatureUA.FORTRESS_UA, new FortressConfigUA(false), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.DESERT_TEMPLE_UA, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.OCEAN_RUIN_UA, new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.9F), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.OCEAN_MONUMENT_UA, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.SHIPWRECK_UA, new ShipwreckConfig(false), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.END_CITY_UA, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.IGLOO_UA, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.JUNGLE_TEMPLE_UA, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.WITCH_HUT_UA, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.WOODLAND_MANSION_UA, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.createDecoratedFeature(Feature.BURIED_TREASURE, new BuriedTreasureConfig(0.01F), PASSTHROUGH_CHEST, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.PILLAGER_OUTPOST_UA, new PillagerOutpostConfig(0.05D), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
    }

}
