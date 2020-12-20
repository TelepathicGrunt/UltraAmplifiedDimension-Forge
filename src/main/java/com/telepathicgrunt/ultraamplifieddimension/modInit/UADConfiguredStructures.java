package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class UADConfiguredStructures {
    /**
     * Static instance of our structure so we can reference it and add it to biomes easily.
     */
    public static StructureFeature<?, ?> CONFIGURED_SUN_SHRINE = UADStructures.SUN_SHRINE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> CONFIGURED_STONEHENGE = UADStructures.STONEHENGE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> CONFIGURED_ICE_SPIKE_TEMPLE = UADStructures.ICE_SPIKE_TEMPLE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> CONFIGURED_MUSHROOM_TEMPLE = UADStructures.MUSHROOM_TEMPLE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    /**
     * Registers the configured structure which is what gets added to the biomes.
     * Noticed we are not using a forge registry because there is none for configured structures.
     *
     * We can register configured structures at any time before a world is clicked on and made.
     * But the best time to register configured features by code is honestly to do it in FMLCommonSetupEvent.
     */
    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        Registry.register(registry, new ResourceLocation(UltraAmplifiedDimension.MODID, "configured_sun_shrine"), CONFIGURED_SUN_SHRINE);
        Registry.register(registry, new ResourceLocation(UltraAmplifiedDimension.MODID, "configured_stonehenge"), CONFIGURED_STONEHENGE);
        Registry.register(registry, new ResourceLocation(UltraAmplifiedDimension.MODID, "configured_ice_spike_temple"), CONFIGURED_ICE_SPIKE_TEMPLE);
        Registry.register(registry, new ResourceLocation(UltraAmplifiedDimension.MODID, "configured_mushroom_temple"), CONFIGURED_MUSHROOM_TEMPLE);

        FlatGenerationSettings.STRUCTURES.put(UADStructures.SUN_SHRINE.get(), CONFIGURED_SUN_SHRINE);
        FlatGenerationSettings.STRUCTURES.put(UADStructures.STONEHENGE.get(), CONFIGURED_STONEHENGE);
        FlatGenerationSettings.STRUCTURES.put(UADStructures.ICE_SPIKE_TEMPLE.get(), CONFIGURED_ICE_SPIKE_TEMPLE);
        FlatGenerationSettings.STRUCTURES.put(UADStructures.MUSHROOM_TEMPLE.get(), CONFIGURED_MUSHROOM_TEMPLE);
    }
}
