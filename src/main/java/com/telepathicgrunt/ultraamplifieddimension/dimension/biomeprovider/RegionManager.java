package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider;

import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final Map<Biome, Biome> subBiomeMap = new HashMap<>();
    private final Map<Biome, Biome> shoreMap = new HashMap<>();
    private final Map<Biome, Biome> borderMap = new HashMap<>();
    private final Map<Biome, Biome> mutatedMap = new HashMap<>();
    private final Map<Biome, Biome> mutatedSubBiomeMap = new HashMap<>();
    private final Map<Biome, Biome> mutatedBorderBiomeMap = new HashMap<>();

    RegionManager(
            Pair<List<BiomeGroup>, Integer> oceanList,
            Pair<List<BiomeGroup>, Integer> endList,
            Pair<List<BiomeGroup>, Integer> netherList,
            Pair<List<BiomeGroup>, Integer> hotList,
            Pair<List<BiomeGroup>, Integer> warmList,
            Pair<List<BiomeGroup>, Integer> coolList,
            Pair<List<BiomeGroup>, Integer> icyList
    ){
        this.oceanList = prepareListAndPopulateBiomeRelationMaps(oceanList);
        this.endList = prepareListAndPopulateBiomeRelationMaps(endList);
        this.netherList = prepareListAndPopulateBiomeRelationMaps(netherList);
        this.hotList = prepareListAndPopulateBiomeRelationMaps(hotList);
        this.warmList = prepareListAndPopulateBiomeRelationMaps(warmList);
        this.coolList = prepareListAndPopulateBiomeRelationMaps(coolList);
        this.icyList = prepareListAndPopulateBiomeRelationMaps(icyList);
    }

    /**
     * Turn immutable list of BiomeGroups into a mutable list and sort it from hottest biome to coldest.
     * Populates the maps of biome relationships for ShoreEdgeHillsAndMutationsBiomeLayer to work.
     * Will throw an exception if the biome list is empty as we cannot work with empty regions.
     * Will throw error if a biome appeared more than once in "main_biome" entries as biome layers can't handle a single biome having multiple sub biomes.
     */
    private Pair<List<BiomeGroup>, Integer> prepareListAndPopulateBiomeRelationMaps(Pair<List<BiomeGroup>, Integer> regionGroup){
        Pair<List<BiomeGroup>, Integer> nonImmutableListPair = new Pair<>(new ArrayList<>(regionGroup.getFirst()), regionGroup.getSecond());
        if (regionGroup.getFirst().size() == 0){
            throw new JsonSyntaxException("Empty RegionGroup found in Ultra Amplified Dimension's dimension json. Please make sure every temperature category as at least 1 biome.");
        }
        nonImmutableListPair.getFirst().sort(BiomeGroup::compareTo);

        for(BiomeGroup group : nonImmutableListPair.getFirst()){
            Biome mainBiome = group.getMainBiome().get();
            group.getShoreBiome().ifPresent(shore -> checkBeforeAddingBiome(shoreMap, mainBiome, shore.get()));
            group.getBorderBiome().ifPresent(border -> checkBeforeAddingBiome(borderMap, mainBiome, border.get()));
            group.getSubBiome().ifPresent(sub -> checkBeforeAddingBiome(subBiomeMap, mainBiome, sub.get()));
            group.getMutatedBiome().ifPresent(mutated -> checkBeforeAddingBiome(mutatedMap ,mainBiome, mutated.get()));
            group.getMutatedSubBiome().ifPresent(mutatedSub -> checkBeforeAddingBiome(mutatedSubBiomeMap, mainBiome, mutatedSub.get()));
            group.getMutatedBorderBiome().ifPresent(mutatedBorder -> checkBeforeAddingBiome(mutatedBorderBiomeMap, mainBiome, mutatedBorder.get()));
        }
        return nonImmutableListPair;
    }

    private void checkBeforeAddingBiome(Map<Biome, Biome> biomeMap, Biome biomeKey, Biome biomeToAdd){
        if(biomeMap.containsKey(biomeKey)){
            if(biomeMap.get(biomeKey) != biomeToAdd){
                throw new JsonSyntaxException("A single biome was found multiple times in the \"main_biome\" entry with different border/sub/mutated biomes for it in Ultra Amplified Dimension's dimension json. Please make sure every entry with the same \"main_biome\" also have the same alternative biomes (This is due to how biome layers are done and they cannot handle 1 main biome having multiple kinds of sub biomes and stuff.)");
            }
        }
        else{
            biomeMap.put(biomeKey, biomeToAdd);
        }
    }

    /**
     * Randomly picks a biome from given region with their weights impacting their chance.
     */
    public BiomeGroup getRandomBiomeGroup(Pair<List<BiomeGroup>, Integer> regionGroup, INoiseRandom context){
        // Now choose a random item.
        int index = 0;
        for (double randomWeightPicked = context.random(regionGroup.getSecond()); index < regionGroup.getFirst().size() - 1; ++index) {
            randomWeightPicked -= regionGroup.getFirst().get(index).getWeight();
            if (randomWeightPicked <= 0.0) break;
        }

        return regionGroup.getFirst().get(index);
    }

    /**
     * Takes a float and clamps it between 0 inclusive and 1 exclusive.
     * The biome group weight also take effect on what threshold values picks it.
     */
    public BiomeGroup getWeightedBiomeGroupByTemperature(Pair<List<BiomeGroup>, Integer> regionGroup, double threshold){
        // Now choose a random item.
        int index = 0;
        double clampedThreshold = Math.min(Math.max(threshold, 0), 0.999999999D);
        for (double thresholdWeight = clampedThreshold * regionGroup.getSecond(); index < regionGroup.getFirst().size() - 1; ++index) {
            thresholdWeight -= regionGroup.getFirst().get(index).getWeight();
            if (thresholdWeight <= 0.0) break;
        }

        return regionGroup.getFirst().get(index);
    }

    /**
     * Returns the biome with hottest temperature (list is already pre-sorted)
     */
    public Supplier<Biome> getHottestBiomeOfList(List<BiomeGroup> regionGroup){
        return regionGroup.get(0).getMainBiome();
    }

    /**
     * Returns the biome with coldest temperature (list is already pre-sorted)
     */
    public Supplier<Biome> getColdestBiomeOfList(List<BiomeGroup> regionGroup){
        return regionGroup.get(regionGroup.size() - 1).getMainBiome();
    }


    public Pair<List<BiomeGroup>, Integer> getOceanList() { return oceanList; }
    public Pair<List<BiomeGroup>, Integer> getEndList() { return endList; }
    public Pair<List<BiomeGroup>, Integer> getNetherList() { return netherList; }
    public Pair<List<BiomeGroup>, Integer> getHotList() { return hotList; }
    public Pair<List<BiomeGroup>, Integer> getWarmRegion(){ return warmList; }
    public Pair<List<BiomeGroup>, Integer> getCoolList() { return coolList; }
    public Pair<List<BiomeGroup>, Integer> getIcyList() { return icyList; }

    public Biome getSubBiome(Biome biome) {
        return subBiomeMap.get(biome);
    }

    public Biome getShore(Biome biome) {
        return shoreMap.get(biome);
    }

    public Biome getBorder(Biome biome) {
        return borderMap.get(biome);
    }

    public Biome getMutated(Biome biome) {
        return mutatedMap.get(biome);
    }

    public Biome getMutatedSubBiome(Biome biome) {
        return mutatedSubBiomeMap.get(biome);
    }

    public Biome getMutatedBorderBiome(Biome biome) {
        return mutatedBorderBiomeMap.get(biome);
    }
}
