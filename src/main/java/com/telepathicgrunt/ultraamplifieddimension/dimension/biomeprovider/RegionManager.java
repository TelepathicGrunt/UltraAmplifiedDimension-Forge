package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider;

import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RegionManager {
    public static final Codec<RegionManager> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        BiomeGroup.CODEC.listOf().fieldOf("ocean_biomes").xmap(biomeGroupList -> new Pair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), Pair::getFirst)
                .forGetter(regionManager -> regionManager.oceanList),
        BiomeGroup.CODEC.listOf().fieldOf("end_biomes").xmap(biomeGroupList -> new Pair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), Pair::getFirst)
                .forGetter(regionManager -> regionManager.endList),
        BiomeGroup.CODEC.listOf().fieldOf("nether_biomes").xmap(biomeGroupList -> new Pair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), Pair::getFirst)
                .forGetter(regionManager -> regionManager.netherList),
        BiomeGroup.CODEC.listOf().fieldOf("hot_biomes").xmap(biomeGroupList -> new Pair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), Pair::getFirst)
                .forGetter(regionManager -> regionManager.hotList),
        BiomeGroup.CODEC.listOf().fieldOf("warm_biomes").xmap(biomeGroupList -> new Pair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), Pair::getFirst)
                .forGetter(regionManager -> regionManager.warmList),
        BiomeGroup.CODEC.listOf().fieldOf("cool_biomes").xmap(biomeGroupList -> new Pair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), Pair::getFirst)
                .forGetter(regionManager -> regionManager.coolList),
        BiomeGroup.CODEC.listOf().fieldOf("icy_biomes").xmap(biomeGroupList -> new Pair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), Pair::getFirst)
                .forGetter(regionManager -> regionManager.icyList)
    ).apply(instance, RegionManager::new));

    private final Pair<List<BiomeGroup>, Integer> oceanList;
    private final Pair<List<BiomeGroup>, Integer> endList;
    private final Pair<List<BiomeGroup>, Integer> netherList;
    private final Pair<List<BiomeGroup>, Integer> hotList;
    private final Pair<List<BiomeGroup>, Integer> warmList;
    private final Pair<List<BiomeGroup>, Integer> coolList;
    private final Pair<List<BiomeGroup>, Integer> icyList;

    RegionManager(
            Pair<List<BiomeGroup>, Integer> oceanList,
            Pair<List<BiomeGroup>, Integer> endList,
            Pair<List<BiomeGroup>, Integer> netherList,
            Pair<List<BiomeGroup>, Integer> hotList,
            Pair<List<BiomeGroup>, Integer> warmList,
            Pair<List<BiomeGroup>, Integer> coolList,
            Pair<List<BiomeGroup>, Integer> icyList
    ){
        this.oceanList = sortBiomesAndIsNotEmpty(oceanList);
        this.endList = sortBiomesAndIsNotEmpty(endList);
        this.netherList = sortBiomesAndIsNotEmpty(netherList);
        this.hotList = sortBiomesAndIsNotEmpty(hotList);
        this.warmList = sortBiomesAndIsNotEmpty(warmList);
        this.coolList = sortBiomesAndIsNotEmpty(coolList);
        this.icyList = sortBiomesAndIsNotEmpty(icyList);
    }

    /**
     * Turn immutable list of BiomeGroups into a mutable list and sort it from hottest biome to coldest.
     * WIll throw an exception if the biome list is empty as we cannot work with empty regions.
     */
    private Pair<List<BiomeGroup>, Integer> sortBiomesAndIsNotEmpty(Pair<List<BiomeGroup>, Integer> regionGroup){
        Pair<List<BiomeGroup>, Integer> nonImmutableListPair = new Pair<>(new ArrayList<>(regionGroup.getFirst()), regionGroup.getSecond());
        if (regionGroup.getFirst().size() == 0){
            throw new JsonSyntaxException("Empty RegionGroup found in Ultra Amplified Dimension's dimension json. Please make sure every temperature category as at least 1 biome.");
        }
        nonImmutableListPair.getFirst().sort(BiomeGroup::compareTo);
        return nonImmutableListPair;
    }

    public Supplier<Biome> getRandomMainBiome(Pair<List<BiomeGroup>, Integer> regionGroup, INoiseRandom context){
        // Now choose a random item.
        int index = 0;
        for (double randomWeightPicked = context.random(regionGroup.getSecond()); index < regionGroup.getFirst().size() - 1; ++index) {
            randomWeightPicked -= regionGroup.getFirst().get(index).getWeight();
            if (randomWeightPicked <= 0.0) break;
        }

        return regionGroup.getFirst().get(index).getMainBiome();
    }

    public Supplier<Biome> getHottestBiomeOfList(List<BiomeGroup> regionGroup){
        return regionGroup.get(0).getMainBiome();
    }

    public Supplier<Biome> getColdestBiomeOfList(List<BiomeGroup> regionGroup){
        return regionGroup.get(regionGroup.size() - 1).getMainBiome();
    }

    /**
     * takes a float and clamps it between 0 inclusive and 1 exclusive
     */
    public Supplier<Biome> getBiomeByTemperature(List<BiomeGroup> regionGroup, double threshold){
        return regionGroup.get((int) (regionGroup.size() * Math.min(Math.max(threshold, 0), 0.999999999D))).getMainBiome();
    }

    public Supplier<Biome> getNonHottestOrColdestBiomeOfList(List<BiomeGroup> regionGroup, INoiseRandom context){
        if(regionGroup.size() > 2){
            return regionGroup.get(context.random(regionGroup.size() - 2) + 1).getMainBiome();
        }
        return regionGroup.get(context.random(regionGroup.size())).getMainBiome();
    }

    public Pair<List<BiomeGroup>, Integer> getOceanList() { return oceanList; }
    public Pair<List<BiomeGroup>, Integer> getEndList() { return endList; }
    public Pair<List<BiomeGroup>, Integer> getNetherList() { return netherList; }
    public Pair<List<BiomeGroup>, Integer> getHotList() { return hotList; }
    public Pair<List<BiomeGroup>, Integer> getWarmRegion(){ return warmList; }
    public Pair<List<BiomeGroup>, Integer> getCoolList() { return coolList; }
    public Pair<List<BiomeGroup>, Integer> getIcyList() { return icyList; }
}
