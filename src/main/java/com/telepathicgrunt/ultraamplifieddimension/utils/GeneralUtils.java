package com.telepathicgrunt.ultraamplifieddimension.utils;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class GeneralUtils {

    public static RegistryKey<Biome> biomeRegistryKey(String biomeName){
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(UltraAmplifiedDimension.MODID, biomeName));
    }

}
