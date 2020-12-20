package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.structures.GenericJigsawStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class UADStructures {
    // Random seed
    // https://www.google.com/search?q=random+number
    // 2147483647

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, UltraAmplifiedDimension.MODID);

    public static final RegistryObject<Structure<NoFeatureConfig>> SUN_SHRINE = registerStructure("sun_shrine", () -> (
            new GenericJigsawStructure(
                    NoFeatureConfig.field_236558_a_,
                    new ResourceLocation(UltraAmplifiedDimension.MODID, "sun_shrine_start"),
                    10,
                    -1,
                    0,
                    0
            )
    ));

    public static final RegistryObject<Structure<NoFeatureConfig>> STONEHENGE = registerStructure("stonehenge", () -> (
            new GenericJigsawStructure(
                    NoFeatureConfig.field_236558_a_,
                    new ResourceLocation(UltraAmplifiedDimension.MODID, "stonehenge/center_start"),
                    10,
                    -1,
                    0,
                    0
            )
    ));

    public static final RegistryObject<Structure<NoFeatureConfig>> ICE_SPIKE_TEMPLE = registerStructure("ice_spike_temple", () -> (
            new GenericJigsawStructure(
                    NoFeatureConfig.field_236558_a_,
                    new ResourceLocation(UltraAmplifiedDimension.MODID, "ice_spike_temple/body_start"),
                    10,
                    -7,
                    7,
                    1
            )
    ));

    public static final RegistryObject<Structure<NoFeatureConfig>> MUSHROOM_TEMPLE = registerStructure("mushroom_temple", () -> (
            new GenericJigsawStructure(
                    NoFeatureConfig.field_236558_a_,
                    new ResourceLocation(UltraAmplifiedDimension.MODID, "mushroom_temple/body_start"),
                    10,
                    -2,
                    0,
                    1
            )
    ));

    private static <T extends Structure<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
        return STRUCTURES.register(name, structure);
    }

    /**
     * This is where we set the rarity of your structures and determine if land conforms to it.
     * See the comments in below for more details.
     */
    public static void setupStructures() {
        setupMapSpacingAndLand(SUN_SHRINE.get(), true);
        setupMapSpacingAndLand(STONEHENGE.get(), true);
        setupMapSpacingAndLand(ICE_SPIKE_TEMPLE.get(), true);
        setupMapSpacingAndLand(MUSHROOM_TEMPLE.get(), false);
    }

    /**
     * Adds the provided structure to the registry, and adds the separation settings.
     * The rarity of the structure is determined based on the values passed into
     * this method in the structureSeparationSettings argument. Called by registerFeatures.
     */
    public static <F extends Structure<?>> void setupMapSpacingAndLand(
            F structure,
            boolean transformSurroundingLand)
    {
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        if(transformSurroundingLand){
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(structure)
                            .build();
        }
    }
}
