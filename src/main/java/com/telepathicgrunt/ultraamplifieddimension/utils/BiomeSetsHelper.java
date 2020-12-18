package com.telepathicgrunt.ultraamplifieddimension.utils;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.world.biome.Biome;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BiomeSetsHelper {

    public static Set<Biome> FROZEN_BIOMES = new HashSet<>();
    public static Set<Biome> COLD_OCEAN_BIOMES = new HashSet<>();

    public static void generateBiomeSets(MutableRegistry<Biome> biomeRegistry){
        FROZEN_BIOMES.clear();
        COLD_OCEAN_BIOMES.clear();

        for(Map.Entry<RegistryKey<Biome>, Biome> biomeEntry : biomeRegistry.getEntries()){
            String path = biomeEntry.getKey().getLocation().getPath();
            Biome biome = biomeEntry.getValue();
            if(biome.getTemperature() < 0.05f || path.contains("frozen")){
                FROZEN_BIOMES.add(biome);
            }

            if(biome.getCategory() == Biome.Category.OCEAN && path.contains("cold")){
                COLD_OCEAN_BIOMES.add(biome);
            }
        }
    }
}
