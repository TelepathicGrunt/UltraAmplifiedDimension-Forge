package com.telepathicgrunt.ultraamplifieddimension.utils;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class GeneralUtils {

    public static String biomeIDString(String biomeName){
        return UltraAmplifiedDimension.MODID + ":" + biomeName;
    }

}
