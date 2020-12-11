package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.world.features.*;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.ColumnConfig;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.CountConfig;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.HeightConfig;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.NbtFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;


public class UADFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, UltraAmplifiedDimension.MODID);

    public static final RegistryObject<Feature<?>> CONTAIN_LIQUID_FOR_OCEANS = createFeature("contain_liquid_for_oceans", () -> new ContainLiquidForOceans(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<?>> CONTAIN_UNDERGROUND_LIQUIDS = createFeature("contain_underground_liquids", () -> new ContainUndergroundLiquids(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<?>> BIG_CACTUS = createFeature("big_cactus", () -> new BigCactus(HeightConfig.CODEC));
    public static final RegistryObject<Feature<?>> BLUE_ICE_WATERFALL = createFeature("blue_ice_waterfall", () -> new BlueIceWaterfall(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<?>> COLUMN_RAMP = createFeature("column_ramp", () -> new ColumnRamp(ColumnConfig.CODEC));
    public static final RegistryObject<Feature<?>> COLUMN_VERTICAL = createFeature("column_vertical", () -> new ColumnVertical(ColumnConfig.CODEC));
    public static final RegistryObject<Feature<?>> GLOW_PATCH = createFeature("glow_patch", () -> new GlowPatch(CountConfig.CODEC));
    public static final RegistryObject<Feature<?>> LAKE_WIDE_SHALLOW = createFeature("lake_wide_shallow", () -> new LakeWideShallow(BlockStateFeatureConfig.field_236455_a_));
    public static final RegistryObject<Feature<?>> NBT_FEATURE = createFeature("nbt_feature", () -> new NbtFeature(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<?>> NETHER_UNDERWATER_MAGMA = createFeature("nether_underwater_magma", () -> new NetherUnderwaterMagma(NoFeatureConfig.field_236558_a_));

    public static <B extends Feature<?>> RegistryObject<B> createFeature(String name, Supplier<B> feature) {
        return FEATURES.register(name, feature);
    }
}