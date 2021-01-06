package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.NbtDungeonConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.world.biome.Biome;

import java.util.function.Supplier;

public class BiomeGroup implements Comparable<BiomeGroup> {
    public static final Codec<BiomeGroup> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            RegistryKeyCodec.create(Registry.BIOME_KEY, Biome.CODEC).fieldOf("main_biome").forGetter(biomeGroup -> biomeGroup.mainBiome),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("weight").forGetter(biomeGroup -> biomeGroup.weight)
    ).apply(instance, BiomeGroup::new));

    private final Supplier<Biome> mainBiome;
    private final int weight;

    BiomeGroup(Supplier<Biome> mainBiome, int weight){
        this.mainBiome = mainBiome;
        this.weight = weight;
    }

    public Supplier<Biome> getMainBiome(){
        return mainBiome;
    }

    public int getWeight(){
        return weight;
    }

    /**
     * Make sorting lists now sort from hottest to coldest biomes.
     */
    @Override
    public int compareTo(BiomeGroup incomingBiomeGroup) {
        return Float.compare(incomingBiomeGroup.getMainBiome().get().getTemperature(), this.mainBiome.get().getTemperature());
    }
}
