package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.world.features.*;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.ColumnConfig;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class UADFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, UltraAmplifiedDimension.MODID);

    public static final RegistryObject<Feature<NoFeatureConfig>> CONTAIN_LIQUID_FOR_OCEANS = FEATURES.register("contain_liquid_for_oceans", () -> new ContainLiquidForOceans(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> CONTAIN_UNDERGROUND_LIQUIDS = FEATURES.register("contain_underground_liquids", () -> new ContainUndergroundLiquids(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<HeightConfig>> BIG_CACTUS = FEATURES.register("big_cactus", () -> new BigCactus(HeightConfig.CODEC));
    public static final RegistryObject<Feature<TwoBlockStateConfig>> NON_LIQUID_WATERFALL = FEATURES.register("non_liquid_waterfall", () -> new NonLiquidWaterfall(TwoBlockStateConfig.CODEC));
    public static final RegistryObject<Feature<ColumnConfig>> COLUMN_RAMP = FEATURES.register("column_ramp", () -> new ColumnRamp(ColumnConfig.CODEC));
    public static final RegistryObject<Feature<ColumnConfig>> COLUMN_VERTICAL = FEATURES.register("column_vertical", () -> new ColumnVertical(ColumnConfig.CODEC));
    public static final RegistryObject<Feature<CountConfig>> GLOW_PATCH = FEATURES.register("glow_patch", () -> new GlowPatch(CountConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateFeatureConfig>> LAKE_WIDE_SHALLOW = FEATURES.register("lake_wide_shallow", () -> new LakeWideShallow(BlockStateFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> NBT_FEATURE = FEATURES.register("nbt_feature", () -> new NbtFeature(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtDungeonConfig>> NBT_DUNGEON = FEATURES.register("nbt_dungeon", () -> new NbtDungeon(NbtDungeonConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> NETHER_SEA_ADJUSTER = FEATURES.register("nether_sea_adjuster", () -> new NetherSeaAdjuster(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> NETHER_LAVA_SPOT = FEATURES.register("nether_lava_spot", () -> new NetherLavaSpot(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<RootConfig>> ROOTS = FEATURES.register("roots", () -> new Roots(RootConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SNOW_ICE_LAYER_HANDLER_FEATURE = FEATURES.register("snow_ice_layer_handler_feature", () -> new SnowIceLayerHandlerFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SNOW_ICE_ALL_LAYERS = FEATURES.register("snow_ice_all_layers", () -> new SnowIceAllLayers(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SNOW_ICE_TOP_LAYER = FEATURES.register("snow_ice_top_layer", () -> new SnowIceTopLayer(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SNOW_LAYER_WITHOUT_ICE = FEATURES.register("snow_layer_without_ice", () -> new SnowLayerWithoutIceFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<DiskDryConfig>> DISK_DRY = FEATURES.register("disk_dry", () -> new DiskDry(DiskDryConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SWAMP_CROSS = FEATURES.register("swamp_cross", () -> new SwampCross(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> TREE_SWAMP_HORNED = FEATURES.register("tree_swamp_horned", () -> new TreeSwampHorned(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<OreFeatureConfig>> ELLIPSOID_POCKET = FEATURES.register("ellipsoid_pocket", () -> new EllipsoidPocket(OreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> VINES_LONG = FEATURES.register("vines_long", () -> new VinesLong(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<HeightConfig>> VINES_SHORT = FEATURES.register("vines_short", () -> new VinesShort(HeightConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> HANGING_RUINS = FEATURES.register("hanging_ruins", () -> new HangingRuins(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<PondConfig>> POND = FEATURES.register("pond", () -> new Pond(PondConfig.CODEC));
    public static final RegistryObject<Feature<BambooConfig>> SAFE_BAMBOO = FEATURES.register("safe_bamboo", () -> new SafeBamboo(BambooConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> TREE_GIANT_DARK_OAK = FEATURES.register("tree_giant_dark_oak", () -> new TreeGiantDarkOak(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockWithRuleReplaceConfig>> ON_SOLID_BLOCK_PLACER = FEATURES.register("on_solid_block_placer", () -> new OnSolidBlockPlacer(BlockWithRuleReplaceConfig.CODEC));
    public static final RegistryObject<Feature<ProbabilityAndCountConfig>> PROPER_SEAGRASS = FEATURES.register("proper_seagrass", () -> new ProperSeagrass(ProbabilityAndCountConfig.CODEC));
    public static final RegistryObject<Feature<SeaPickleConfig>> PROPER_SEAPICKLES = FEATURES.register("proper_sea_pickles", () -> new ProperSeapickle(SeaPickleConfig.CODEC));
    public static final RegistryObject<Feature<ProbabilityAndCountConfig>> PROPER_KELP = FEATURES.register("proper_kelp", () -> new ProperKelp(ProbabilityAndCountConfig.CODEC));
    public static final RegistryObject<Feature<BoulderFeatureConfig>> BOULDERS = FEATURES.register("boulders", () -> new Boulders(BoulderFeatureConfig.CODEC));
    public static final RegistryObject<Feature<GiantSpikeConfig>> GIANT_SPIKE = FEATURES.register("giant_spike", () -> new GiantSpike(GiantSpikeConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateFeatureConfig>> ICEBERG_WITHOUT_AIR = FEATURES.register("iceberg_without_air", () -> new IcebergWithoutAir(BlockStateFeatureConfig.CODEC));
    public static final RegistryObject<Feature<LootTableConfig>> MARKED_TREASURE_CHEST = FEATURES.register("marked_treasure_chest", () -> new MarkedTreasureChest(LootTableConfig.CODEC));
}