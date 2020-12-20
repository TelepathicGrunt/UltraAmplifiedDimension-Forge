package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class UADConfiguredStructures {
    /**
     * Static instance of our structure so we can reference it and add it to biomes easily.
     */
    public static StructureFeature<?, ?> CONFIGURED_SUN_SHRINE;
    public static StructureFeature<?, ?> CONFIGURED_STONEHENGE;
    public static StructureFeature<?, ?> CONFIGURED_ICE_SPIKE_TEMPLE;
    public static StructureFeature<?, ?> CONFIGURED_MUSHROOM_TEMPLE;

    public static Map<Structure<?>, StructureFeature<?, ?>> REGISTERED_UAD_STRUCTURES = new HashMap<>();

    public static void registerConfiguredStructures() {
        CONFIGURED_SUN_SHRINE = setupAndRegisterConfiguredForm(UADStructures.SUN_SHRINE);
        CONFIGURED_STONEHENGE = setupAndRegisterConfiguredForm(UADStructures.STONEHENGE);
        CONFIGURED_ICE_SPIKE_TEMPLE = setupAndRegisterConfiguredForm(UADStructures.ICE_SPIKE_TEMPLE);
        CONFIGURED_MUSHROOM_TEMPLE = setupAndRegisterConfiguredForm(UADStructures.MUSHROOM_TEMPLE);
    }

    /**
     * Registers the configured structure which is what gets added to the biomes.
     * Noticed we are not using a forge registry because there is none for configured structures.
     *
     * We can register configured structures at any time before a world is clicked on and made.
     * But the best time to register configured features by code is honestly to do it in FMLCommonSetupEvent.
     */
    private static StructureFeature<?, ?> setupAndRegisterConfiguredForm(RegistryObject<Structure<NoFeatureConfig>> structure){
        StructureFeature<?, ?> configuredStructure = structure.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

        Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE,
                new ResourceLocation(UltraAmplifiedDimension.MODID, "configured_" + structure.getId().getPath()),
                configuredStructure);

        FlatGenerationSettings.STRUCTURES.put(structure.get(), configuredStructure);
        structure.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

        REGISTERED_UAD_STRUCTURES.put(structure.get(), configuredStructure);
        return configuredStructure;
    }
}
