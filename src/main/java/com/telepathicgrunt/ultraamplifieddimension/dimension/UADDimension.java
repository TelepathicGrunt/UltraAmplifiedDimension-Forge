package com.telepathicgrunt.ultraamplifieddimension.dimension;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.UADBiomeProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class UADDimension {
    public static final RegistryKey<World> UAD_WORLD_KEY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(UltraAmplifiedDimension.MODID, UltraAmplifiedDimension.MODID));

    public static void setupDimension() {
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, new ResourceLocation(UltraAmplifiedDimension.MODID, "terrain"), UADChunkGenerator.UAD_CHUNK_GENERATOR_CODEC);
        Registry.register(Registry.BIOME_PROVIDER_CODEC, new ResourceLocation(UltraAmplifiedDimension.MODID, "biome_source"), UADBiomeProvider.CODEC);
    }
}
