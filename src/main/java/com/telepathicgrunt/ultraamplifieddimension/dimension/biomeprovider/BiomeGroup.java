package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.NbtDungeonConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.function.Supplier;

public class BiomeGroup {
    public static final Codec<BiomeGroup> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("main_biome").forGetter(biomeGroup -> biomeGroup.mainBiome),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("weight").forGetter(biomeGroup -> biomeGroup.weight)
    ).apply(instance, BiomeGroup::new));

    //Biome.BIOME_CODEC.fieldOf("biome")
    private final ResourceLocation mainBiome;
    private final int weight;

    BiomeGroup(ResourceLocation mainBiome, int weight){
        this.mainBiome = mainBiome;
        this.weight = weight;
    }

    public ResourceLocation getMainBiome(){
        return mainBiome;
    }

    public int getWeight(){
        return weight;
    }
}
