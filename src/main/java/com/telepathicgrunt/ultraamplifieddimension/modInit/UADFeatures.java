package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;


public class UADFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, UltraAmplifiedDimension.MODID);

    //public static final RegistryObject<Feature<?>> TREE = createFeature("tree", () -> new TreeFeature(UADBaseTreeFeatureConfig.CODEC));

    public static <B extends Feature<?>> RegistryObject<B> createFeature(String name, Supplier<B> feature) {
        return FEATURES.register(name, feature);
    }
}