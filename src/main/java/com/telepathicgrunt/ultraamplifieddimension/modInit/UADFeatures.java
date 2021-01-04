package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.world.features.*;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.ColumnConfig;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;


public class UADFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, UltraAmplifiedDimension.MODID);

    public static final RegistryObject<Feature<NoFeatureConfig>> CONTAIN_LIQUID_FOR_OCEANS = createFeature("contain_liquid_for_oceans", () -> new ContainLiquidForOceans(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> CONTAIN_UNDERGROUND_LIQUIDS = createFeature("contain_underground_liquids", () -> new ContainUndergroundLiquids(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<HeightConfig>> BIG_CACTUS = createFeature("big_cactus", () -> new BigCactus(HeightConfig.CODEC));
    public static final RegistryObject<Feature<TwoBlockStateConfig>> NON_LIQUID_WATERFALL = createFeature("non_liquid_waterfall", () -> new NonLiquidWaterfall(TwoBlockStateConfig.CODEC));
    public static final RegistryObject<Feature<ColumnConfig>> COLUMN_RAMP = createFeature("column_ramp", () -> new ColumnRamp(ColumnConfig.CODEC));
    public static final RegistryObject<Feature<ColumnConfig>> COLUMN_VERTICAL = createFeature("column_vertical", () -> new ColumnVertical(ColumnConfig.CODEC));
    public static final RegistryObject<Feature<CountConfig>> GLOW_PATCH = createFeature("glow_patch", () -> new GlowPatch(CountConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateFeatureConfig>> LAKE_WIDE_SHALLOW = createFeature("lake_wide_shallow", () -> new LakeWideShallow(BlockStateFeatureConfig.field_236455_a_));
    public static final RegistryObject<Feature<NbtFeatureConfig>> NBT_FEATURE = createFeature("nbt_feature", () -> new NbtFeature(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtDungeonConfig>> NBT_DUNGEON = createFeature("nbt_dungeon", () -> new NbtDungeon(NbtDungeonConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> NETHER_SEA_ADJUSTER = createFeature("nether_sea_adjuster", () -> new NetherSeaAdjuster(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> NETHER_LAVA_SPOT = createFeature("nether_lava_spot", () -> new NetherLavaSpot(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<RootConfig>> ROOTS = createFeature("roots", () -> new Roots(RootConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SNOW_ICE_LAYER_HANDLER_FEATURE = createFeature("snow_ice_layer_handler_feature", () -> new SnowIceLayerHandlerFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> SNOW_ICE_ALL_LAYERS = createFeature("snow_ice_all_layers", () -> new SnowIceAllLayers(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> SNOW_ICE_TOP_LAYER = createFeature("snow_ice_top_layer", () -> new SnowIceTopLayer(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> SNOW_LAYER_WITHOUT_ICE = createFeature("snow_layer_without_ice", () -> new SnowLayerWithoutIceFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<DiskDryConfig>> DISK_DRY = createFeature("disk_dry", () -> new DiskDry(DiskDryConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SWAMP_CROSS = createFeature("swamp_cross", () -> new SwampCross(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> TREE_SWAMP_HORNED = createFeature("tree_swamp_horned", () -> new TreeSwampHorned(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<OreFeatureConfig>> ELLIPSOID_POCKET = createFeature("ellipsoid_pocket", () -> new EllipsoidPocket(OreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> VINES_LONG = createFeature("vines_long", () -> new VinesLong(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<HeightConfig>> VINES_SHORT = createFeature("vines_short", () -> new VinesShort(HeightConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> HANGING_RUINS = createFeature("hanging_ruins", () -> new HangingRuins(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<PondConfig>> POND = createFeature("pond", () -> new Pond(PondConfig.CODEC));
    public static final RegistryObject<Feature<BambooConfig>> SAFE_BAMBOO = createFeature("safe_bamboo", () -> new SafeBamboo(BambooConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> TREE_GIANT_DARK_OAK = createFeature("tree_giant_dark_oak", () -> new TreeGiantDarkOak(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockWithRuleReplaceConfig>> ON_SOLID_BLOCK_PLACER = createFeature("on_solid_block_placer", () -> new OnSolidBlockPlacer(BlockWithRuleReplaceConfig.CODEC));
    public static final RegistryObject<Feature<ProbabilityAndCountConfig>> PROPER_SEAGRASS = createFeature("proper_seagrass", () -> new ProperSeagrass(ProbabilityAndCountConfig.CODEC));
    public static final RegistryObject<Feature<SeaPickleConfig>> PROPER_SEAPICKLES = createFeature("proper_sea_pickles", () -> new ProperSeapickle(SeaPickleConfig.CODEC));
    public static final RegistryObject<Feature<ProbabilityAndCountConfig>> PROPER_KELP = createFeature("proper_kelp", () -> new ProperKelp(ProbabilityAndCountConfig.CODEC));
    public static final RegistryObject<Feature<BoulderFeatureConfig>> BOULDERS = createFeature("boulders", () -> new Boulders(BoulderFeatureConfig.CODEC));
    public static final RegistryObject<Feature<GiantSpikeConfig>> GIANT_SPIKE = createFeature("giant_spike", () -> new GiantSpike(GiantSpikeConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateFeatureConfig>> ICEBERG_WITHOUT_AIR = createFeature("iceberg_without_air", () -> new IcebergWithoutAir(BlockStateFeatureConfig.field_236455_a_));

    public static <B extends Feature<?>> RegistryObject<B> createFeature(String name, Supplier<B> feature) {
        return FEATURES.register(name, feature);
    }
}