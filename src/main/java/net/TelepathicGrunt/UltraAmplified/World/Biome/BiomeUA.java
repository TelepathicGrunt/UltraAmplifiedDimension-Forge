package net.TelepathicGrunt.UltraAmplified.World.Biome;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.CaveCavityCarver;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.RavineCarver;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.SuperLongRavineCarver;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.AllSoulSandSurfacesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.AtBottomOfRandomLayerUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.AtCenterSurfaceWithChanceUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.AtSurfaceRoofedForestUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.AtSurfaceThroughWaterWithExtraUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.AtSurfaceUnderTopLayerWithChanceUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.AtSurfaceWithChanceUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.AtSurfaceWithExtraUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.ChanceOnAllSurfacesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.DungeonPlacementBands;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.EmeraldPlacement;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.GlowstonePlacementUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.HeightBiasedRangeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.NetherFirePlacementUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.PercentageAndFrequencyConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.RandomChanceUnderSurface;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.RandomPlacementUnderSurface;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.RandomPositionEvery3Height;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.RandomPositionEvery5Height;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.SlimeLakePlacementUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.TwiceSurfaceWithChanceUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.NetherBridgeConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillagePiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillageUAConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.surfacebuilder.DesertLakesSurfaceBuilder;
import net.TelepathicGrunt.UltraAmplified.World.gen.surfacebuilder.EndSurfaceBuilderUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.surfacebuilder.IceMountainSurfaceBuilder;
import net.TelepathicGrunt.UltraAmplified.World.gen.surfacebuilder.MesaBryceSurfaceBuilderUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.surfacebuilder.NetherSurfaceBuilderUA;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.structure.StrongholdConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.structure.VillagePieces;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.BasePlacement;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BiomeUA extends Biome {

	//set up custom position placements
	public static final BasePlacement<FrequencyConfig> RANDOM_POSITION_EVERY_5_HEIGHT = new RandomPositionEvery5Height();
	public static final BasePlacement<FrequencyConfig> RANDOM_POSITION_EVERY_3_HEIGHT = new RandomPositionEvery3Height();
	public static final BasePlacement<FrequencyConfig> RANDOM_PLACEMENT_UNDER_SURFACE = new RandomPlacementUnderSurface();
	public static final BasePlacement<ChanceConfig> RANDOM_SURFACE_BELOW_TOP_LAYER = new AtSurfaceUnderTopLayerWithChanceUA();
	public static final BasePlacement<ChanceConfig> RANDOM_CHANCE_UNDER_SURFACE = new RandomChanceUnderSurface();
	public static final BasePlacement<CountRangeConfig> RANDOM_BOTTOM_LAYER = new AtBottomOfRandomLayerUA();
	public static final BasePlacement<ChanceConfig> TWICE_SURFACE_WITH_CHANCE_UA = new TwiceSurfaceWithChanceUA();
	public static final BasePlacement<PercentageAndFrequencyConfig> CHANCE_ON_ALL_SURFACES_UA = new ChanceOnAllSurfacesUA();
	public static final BasePlacement<PercentageAndFrequencyConfig> NETHERWART_SOUL_SAND_SURFACES_UA = new AllSoulSandSurfacesUA();
	public static final BasePlacement<AtSurfaceWithExtraConfig> AT_SURFACE_WITH_EXTRA_UA = new AtSurfaceWithExtraUA();
	public static final BasePlacement<AtSurfaceWithExtraConfig> AT_SURFACE_THROUGH_WATER_WITH_EXTRA_UA = new AtSurfaceThroughWaterWithExtraUA();
	public static final BasePlacement<AtSurfaceWithExtraConfig> ROOFED_TREE_UA = new AtSurfaceRoofedForestUA();
	public static final BasePlacement<ChanceConfig> AT_SURFACE_WITH_CHANCE_UA = new AtSurfaceWithChanceUA();
	   public static final BasePlacement<ChanceConfig> AT_CENTER_SURFACE_WITH_CHANCE = new AtCenterSurfaceWithChanceUA();
    public static final WorldCarver<ProbabilityConfig> RAVINE_CARVER = new RavineCarver();
    public static final WorldCarver<ProbabilityConfig> LONG_RAVINE_CARVER = new SuperLongRavineCarver();
    public static final WorldCarver<ProbabilityConfig> CAVE_CAVITY_CARVER = new CaveCavityCarver();
    public static final BasePlacement<DungeonRoomConfig> DUNGEON_PLACEMENT = new DungeonPlacementBands();
    public static final BasePlacement<CountRangeConfig> EMERALD_PLACEMENT = new EmeraldPlacement();
    public static final BasePlacement<FrequencyConfig> GLOWSTONE_PLACEMENT = new GlowstonePlacementUA();
    public static final BasePlacement<FrequencyConfig> FIRE_PLACEMENT = new NetherFirePlacementUA();
    public static final BasePlacement<LakeChanceConfig> SLIME_LAKE_PLACEMENT = new SlimeLakePlacementUA();
    public static final BasePlacement<CountRangeConfig> HEIGHT_BIASED_RANGE_UA = new HeightBiasedRangeUA();
    
    

    protected static final IBlockState SANDSTONE = Blocks.SANDSTONE.getDefaultState();
    protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
    protected static final IBlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
    protected static final IBlockState ICE = Blocks.ICE.getDefaultState();
    
    public static final SurfaceBuilderConfig SAND_SANDSTONE_SANDSTONE_SURFACE = new SurfaceBuilderConfig(SAND, SANDSTONE, SANDSTONE);
    public static final SurfaceBuilderConfig SANDSTONE_SURFACE = new SurfaceBuilderConfig(SANDSTONE, SANDSTONE, SANDSTONE);
    public static final SurfaceBuilderConfig THIN_WATER_SURFACE = new SurfaceBuilderConfig(WATER, DIRT, GRAVEL);
    public static final SurfaceBuilderConfig SNOWBLOCK_ICE_ICE_SURFACE = new SurfaceBuilderConfig(SNOW_BLOCK, ICE, ICE);

    public static final ISurfaceBuilder<SurfaceBuilderConfig> DESERT_LAKE_SURFACE_BUILDER = new DesertLakesSurfaceBuilder();
    public static final ISurfaceBuilder<SurfaceBuilderConfig> UA_MESA_BRYCE_SURACE_BUILDER = new MesaBryceSurfaceBuilderUA();
    public static final ISurfaceBuilder<SurfaceBuilderConfig> ICE_MOUNTAIN_SURFACE_BUILDER = new IceMountainSurfaceBuilder();
    public static final ISurfaceBuilder<SurfaceBuilderConfig> NETHER_SURFACE_BUILDER_UA = new NetherSurfaceBuilderUA();
    public static final ISurfaceBuilder<SurfaceBuilderConfig> END_SURFACE_BUILDER_UA = new EndSurfaceBuilderUA();
	
	protected BiomeUA(BiomeBuilder biomeBuilder) {
		super(biomeBuilder);
	}
	
	
	protected void addStructureFeaturesUA() {
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, createCompositeFeature(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA((double)Config.mineshaftSpawnrate, MineshaftUA.Type.NORMAL), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, createCompositeFeature(FeatureUA.STRONGHOLD_UA, new StrongholdConfig(), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(Feature.VILLAGE, new VillageConfig(0, VillagePieces.Type.OAK), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(FeatureUA.VILLAGE_UA, new VillageUAConfig(0, VillagePiecesUA.Type.DARK), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(FeatureUA.FORTRESS_UA, new NetherBridgeConfigUA(false), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
      
      /*
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(Feature.SHIPWRECK, new ShipwreckConfig(false), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(Feature.OCEAN_MONUMENT, new OceanMonumentConfig(), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(Feature.OCEAN_RUIN, new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.9F), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, createCompositeFeature(Feature.BURIED_TREASURE, new BuriedTreasureConfig(0.01F), PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));*/
   }

}
