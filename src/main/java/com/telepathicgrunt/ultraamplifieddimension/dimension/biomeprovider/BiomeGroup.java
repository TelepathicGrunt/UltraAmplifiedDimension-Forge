package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.world.biome.Biome;

import java.util.Optional;
import java.util.function.Supplier;

public class BiomeGroup implements Comparable<BiomeGroup> {
    public static final Codec<BiomeGroup> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            RegistryKeyCodec.create(Registry.BIOME_KEY, Biome.CODEC).fieldOf("main_biome").forGetter(biomeGroup -> biomeGroup.mainBiome),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("weight").forGetter(biomeGroup -> biomeGroup.weight),
            RegistryKeyCodec.create(Registry.BIOME_KEY, Biome.CODEC).optionalFieldOf("shore_biome").forGetter(biomeGroup -> biomeGroup.shoreBiome),
            RegistryKeyCodec.create(Registry.BIOME_KEY, Biome.CODEC).optionalFieldOf("border_biome").forGetter(biomeGroup -> biomeGroup.borderBiome),
            RegistryKeyCodec.create(Registry.BIOME_KEY, Biome.CODEC).optionalFieldOf("sub_biome").forGetter(biomeGroup -> biomeGroup.subBiome),
            RegistryKeyCodec.create(Registry.BIOME_KEY, Biome.CODEC).optionalFieldOf("mutated_biome").forGetter(biomeGroup -> biomeGroup.mutatedBiome),
            RegistryKeyCodec.create(Registry.BIOME_KEY, Biome.CODEC).optionalFieldOf("mutated_sub_biome").forGetter(biomeGroup -> biomeGroup.mutatedSubBiome),
            RegistryKeyCodec.create(Registry.BIOME_KEY, Biome.CODEC).optionalFieldOf("mutated_border_biome").forGetter(biomeGroup -> biomeGroup.mutatedBorderBiome)
    ).apply(instance, BiomeGroup::new));

    private final Supplier<Biome> mainBiome;
    private final int weight;
    private final Optional<Supplier<Biome>> shoreBiome;
    private final Optional<Supplier<Biome>> borderBiome;
    private final Optional<Supplier<Biome>> subBiome;
    private final Optional<Supplier<Biome>> mutatedBiome;
    private final Optional<Supplier<Biome>> mutatedSubBiome;
    private final Optional<Supplier<Biome>> mutatedBorderBiome;

    BiomeGroup(Supplier<Biome> mainBiome, int weight, Optional<Supplier<Biome>> shoreBiome, Optional<Supplier<Biome>> borderBiome,
               Optional<Supplier<Biome>> subBiome, Optional<Supplier<Biome>> mutatedBiome, Optional<Supplier<Biome>> mutatedSubBiome,
               Optional<Supplier<Biome>> mutatedBorderBiome)
    {
        this.mainBiome = mainBiome;
        this.weight = weight;
        this.shoreBiome = shoreBiome;
        this.borderBiome = borderBiome;
        this.subBiome = subBiome;
        this.mutatedBiome = mutatedBiome;
        this.mutatedSubBiome = mutatedSubBiome;
        this.mutatedBorderBiome = mutatedBorderBiome;
    }

    BiomeGroup(Supplier<Biome> mainBiome, int weight)
    {
        this.mainBiome = mainBiome;
        this.weight = weight;
        this.shoreBiome = Optional.empty();
        this.borderBiome = Optional.empty();
        this.subBiome = Optional.empty();
        this.mutatedBiome = Optional.empty();
        this.mutatedSubBiome = Optional.empty();
        this.mutatedBorderBiome = Optional.empty();
    }

    public Supplier<Biome> getMainBiome(){
        return mainBiome;
    }

    public int getWeight(){
        return weight;
    }

    public Optional<Supplier<Biome>> getShoreBiome() {
        return shoreBiome;
    }

    public Optional<Supplier<Biome>> getBorderBiome() {
        return borderBiome;
    }

    public Optional<Supplier<Biome>> getSubBiome() {
        return subBiome;
    }

    public Optional<Supplier<Biome>> getMutatedBiome() {
        return mutatedBiome;
    }

    public Optional<Supplier<Biome>> getMutatedSubBiome() {
        return mutatedSubBiome;
    }

    public Optional<Supplier<Biome>> getMutatedBorderBiome() {
        return mutatedBorderBiome;
    }


    /**
     * Make sorting lists of BiomeGroups now sort from hottest to coldest main biomes.
     */
    @Override
    public int compareTo(BiomeGroup incomingBiomeGroup) {
        return Float.compare(incomingBiomeGroup.getMainBiome().get().getTemperature(), this.mainBiome.get().getTemperature());
    }
}
