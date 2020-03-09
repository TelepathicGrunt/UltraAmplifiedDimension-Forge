package net.telepathicgrunt.ultraamplified.world.feature.placement;

import net.minecraft.world.gen.placement.AtSurfaceWithExtra;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.HeightBiasedRange;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TwiceSurfaceWithChance;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.telepathicgrunt.ultraamplified.RegUtil;
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.CountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LakeCountRangeAndTypeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.LapisCountRangeConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndHeightConfig;

public class UAPlacement
{
	public static final Placement<ChanceConfig> RANDOM_SURFACE_BELOW_TOP_LAYER = new AtSurfaceUnderTopLedgeWithChance(ChanceConfig::deserialize);
	public static final Placement<ChanceConfig> RANDOM_CHANCE_UNDER_SURFACE = new RandomChanceUnderSurface(ChanceConfig::deserialize);
	public static final Placement<CountRangeConfig> RANDOM_BOTTOM_LAYER = new AtBottomOfLedge(CountRangeConfig::deserialize);
	public static final Placement<ChanceConfig> TWICE_SURFACE_WITH_CHANCE = new TwiceSurfaceWithChance(ChanceConfig::deserialize);
	public static final Placement<CountRangeConfig> LAVAFALL_RANGE = new HeightBasedLavafallsRange(CountRangeConfig::deserialize);
	public static final Placement<CountRangeConfig> LAVAFALL_RANGE_2 = new HeightBasedLavafallsRange2(CountRangeConfig::deserialize);
	public static final Placement<CountRangeConfig> WATERFALL_RANGE = new HeightBasedWaterfallsRange(CountRangeConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> CHANCE_ON_ALL_SURFACES = new ChanceOnAllSurfaces(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> CHANCE_ON_ALL_WATER_SURFACES = new ChanceOnAllLiquidSurfaces(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> CHANCE_ON_ALL_WATER_BOTTOMS = new ChanceOnAllLiquidBottoms(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<PercentageAndFrequencyConfig> NETHERWART_SOUL_SAND_SURFACES = new AllSoulSandSurfaces(PercentageAndFrequencyConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> AT_SURFACE_WITH_EXTRA = new AtSurfaceWithExtra(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA = new AtSurfaceBelowTopLayerWithExtra(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> AT_SURFACE_THROUGH_WATER_WITH_EXTRA = new AtSurfaceThroughWaterWithExtra(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<AtSurfaceWithExtraConfig> ROOFED_TREE = new AtSurfaceRoofedForest(AtSurfaceWithExtraConfig::deserialize);
	public static final Placement<ChanceAndTypeConfig> AT_CENTER_SURFACE_MINI_FEATURE = new AtCenterSurfaceMiniFeature(ChanceAndTypeConfig::deserialize);
	public static final Placement<ChanceAndTypeConfig> LEDGE_UNDERSIDE_MINI_FEATURE = new LedgeUndersideMiniFeature(ChanceAndTypeConfig::deserialize);
	public static final Placement<ChanceConfig> GLOWSTONE_VARIANT_PATCH = new GlowstonePatchPlacement1(ChanceConfig::deserialize);
	public static final Placement<NoPlacementConfig> DUNGEON_PLACEMENT = new DungeonPlacementBands(NoPlacementConfig::deserialize);
	public static final Placement<NoPlacementConfig> GLOWSTONE_PLACEMENT = new GlowstonePlacement(NoPlacementConfig::deserialize);
	public static final Placement<CountRangeAndTypeConfig> GENERAL_PLACEMENT = new GeneralConfigHookupPlacement(CountRangeAndTypeConfig::deserialize);
	public static final Placement<LapisCountRangeConfig> LAPIS_PLACEMENT = new LapisPlacement(LapisCountRangeConfig::deserialize);
	public static final Placement<CountRangeConfig> HEIGHT_BIASED_RANGE = new HeightBiasedRange(CountRangeConfig::deserialize);
	public static final Placement<CountRangeConfig> HEIGHT_BIASED_END_ISLAND_RANGE = new HeightBiasedEndIslandRange(CountRangeConfig::deserialize);
	public static final Placement<PercentageAndHeightConfig> FIXED_HEIGHT_WITH_CHANCE = new FixedHeightWithChance(PercentageAndHeightConfig::deserialize);
	
	//needed so we can prevent vanilla Treasure Chest from spawning if config is off
	public static final Placement<NoPlacementConfig> PASSTHROUGH_CHEST = new PassthroughChest(NoPlacementConfig::deserialize);
	
	//needed so we can prevent vanilla Desert Well from spawning if config is off
	public static final Placement<ChanceConfig> AT_SURFACE_WITH_CHANCE_DESERT_WELL = new AtSurfaceWithChanceDesertWell(ChanceConfig::deserialize);
	
	//needed so we can prevent lava lakes and water lakes from spawning if config is off
	public static final Placement<LakeCountRangeAndTypeConfig> LAKE_PLACEMENT = new LakePlacement(LakeCountRangeAndTypeConfig::deserialize);

	
	
    public static void registerPlacements(RegistryEvent.Register<Placement<?>> event)
    {
    	IForgeRegistry<Placement<?>> registry = event.getRegistry();
    	RegUtil.register(registry, RANDOM_SURFACE_BELOW_TOP_LAYER, "random_surface_below_top_layer");
    	RegUtil.register(registry, RANDOM_CHANCE_UNDER_SURFACE, "random_chance_under_surface");
    	RegUtil.register(registry, RANDOM_BOTTOM_LAYER, "random_bottom_layer");
    	RegUtil.register(registry, TWICE_SURFACE_WITH_CHANCE, "twice_surface_with_chance");
    	RegUtil.register(registry, LAVAFALL_RANGE, "lavafall_range");
    	RegUtil.register(registry, LAVAFALL_RANGE_2, "lavafall_range_2");
    	RegUtil.register(registry, WATERFALL_RANGE, "waterfall_range");
    	RegUtil.register(registry, CHANCE_ON_ALL_SURFACES, "chance_on_all_surfaces");
    	RegUtil.register(registry, CHANCE_ON_ALL_WATER_SURFACES, "chance_on_all_water_surfaces");
    	RegUtil.register(registry, CHANCE_ON_ALL_WATER_BOTTOMS, "chance_on_all_water_bottoms");
    	RegUtil.register(registry, NETHERWART_SOUL_SAND_SURFACES, "netherwart_soul_sand_surfaces");
    	RegUtil.register(registry, AT_SURFACE_WITH_EXTRA, "at_surface_with_extra");
    	RegUtil.register(registry, AT_SURFACE_BELOW_TOP_LAYER_WITH_EXTRA, "at_surface_below_top_layer_with_extra");
    	RegUtil.register(registry, AT_SURFACE_THROUGH_WATER_WITH_EXTRA, "at_surface_through_water_with_extra");
    	RegUtil.register(registry, ROOFED_TREE, "roofed_tree");
    	RegUtil.register(registry, AT_CENTER_SURFACE_MINI_FEATURE, "at_center_surface_mini_feature");
    	RegUtil.register(registry, LEDGE_UNDERSIDE_MINI_FEATURE, "ledge_underside_mini_feature");
    	RegUtil.register(registry, GLOWSTONE_VARIANT_PATCH, "glowstone_variant_patch");
    	RegUtil.register(registry, DUNGEON_PLACEMENT, "dungeon_placement");
    	RegUtil.register(registry, GLOWSTONE_PLACEMENT, "glowstone_placement");
    	RegUtil.register(registry, GENERAL_PLACEMENT, "general_placement");
    	RegUtil.register(registry, LAPIS_PLACEMENT, "lapis_placement");
    	RegUtil.register(registry, HEIGHT_BIASED_RANGE, "height_biased_range");
    	RegUtil.register(registry, HEIGHT_BIASED_END_ISLAND_RANGE, "height_biased_end_island_range");
    	RegUtil.register(registry, FIXED_HEIGHT_WITH_CHANCE, "fixed_height_with_chance");
    	RegUtil.register(registry, PASSTHROUGH_CHEST, "passthrough_chest");
    	RegUtil.register(registry, AT_SURFACE_WITH_CHANCE_DESERT_WELL, "at_surface_with_chance_desert_well");
    	RegUtil.register(registry, LAKE_PLACEMENT, "lake_placement");
    }
}
