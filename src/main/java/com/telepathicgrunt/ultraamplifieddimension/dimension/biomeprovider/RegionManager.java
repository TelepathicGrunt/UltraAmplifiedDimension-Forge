package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider;

import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.*;
import java.util.function.Supplier;

public class RegionManager {
    public static final Codec<RegionManager> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        BiomeGroup.CODEC.listOf().fieldOf("ocean_biomes").xmap(biomeGroupList -> new MutablePair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), MutablePair::getLeft)
                .forGetter(regionManager -> regionManager.oceanList),
        BiomeGroup.CODEC.listOf().fieldOf("end_biomes").xmap(biomeGroupList -> new MutablePair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), MutablePair::getLeft)
                .forGetter(regionManager -> regionManager.endList),
        BiomeGroup.CODEC.listOf().fieldOf("nether_biomes").xmap(biomeGroupList -> new MutablePair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), MutablePair::getLeft)
                .forGetter(regionManager -> regionManager.netherList),
        BiomeGroup.CODEC.listOf().fieldOf("hot_biomes").xmap(biomeGroupList -> new MutablePair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), MutablePair::getLeft)
                .forGetter(regionManager -> regionManager.hotList),
        BiomeGroup.CODEC.listOf().fieldOf("warm_biomes").xmap(biomeGroupList -> new MutablePair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), MutablePair::getLeft)
                .forGetter(regionManager -> regionManager.warmList),
        BiomeGroup.CODEC.listOf().fieldOf("cool_biomes").xmap(biomeGroupList -> new MutablePair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), MutablePair::getLeft)
                .forGetter(regionManager -> regionManager.coolList),
        BiomeGroup.CODEC.listOf().fieldOf("icy_biomes").xmap(biomeGroupList -> new MutablePair<>(biomeGroupList, biomeGroupList.stream().mapToInt(BiomeGroup::getWeight).sum()), MutablePair::getLeft)
                .forGetter(regionManager -> regionManager.icyList)
    ).apply(instance, RegionManager::new));

    private final MutablePair<List<BiomeGroup>, Integer> oceanList;
    private final MutablePair<List<BiomeGroup>, Integer> endList;
    private final MutablePair<List<BiomeGroup>, Integer> netherList;
    private final MutablePair<List<BiomeGroup>, Integer> hotList;
    private final MutablePair<List<BiomeGroup>, Integer> warmList;
    private final MutablePair<List<BiomeGroup>, Integer> coolList;
    private final MutablePair<List<BiomeGroup>, Integer> icyList;

    private final Map<Biome, Biome> subBiomeMap = new HashMap<>();
    private final Map<Biome, Biome> shoreMap = new HashMap<>();
    private final Map<Biome, Biome> borderMap = new HashMap<>();
    private final Map<Biome, Biome> mutatedMap = new HashMap<>();
    private final Map<Biome, Biome> mutatedSubBiomeMap = new HashMap<>();
    private final Map<Biome, Biome> mutatedBorderBiomeMap = new HashMap<>();

    RegionManager(
            MutablePair<List<BiomeGroup>, Integer> oceanList,
            MutablePair<List<BiomeGroup>, Integer> endList,
            MutablePair<List<BiomeGroup>, Integer> netherList,
            MutablePair<List<BiomeGroup>, Integer> hotList,
            MutablePair<List<BiomeGroup>, Integer> warmList,
            MutablePair<List<BiomeGroup>, Integer> coolList,
            MutablePair<List<BiomeGroup>, Integer> icyList
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
    private MutablePair<List<BiomeGroup>, Integer> prepareListAndPopulateBiomeRelationMaps(MutablePair<List<BiomeGroup>, Integer> regionGroup){
        MutablePair<List<BiomeGroup>, Integer> nonImmutableListPair = new MutablePair<>(new ArrayList<>(regionGroup.getLeft()), regionGroup.getRight());
        if (regionGroup.getLeft().size() == 0){
            throw new JsonSyntaxException("Empty RegionGroup found in Ultra Amplified Dimension's dimension json. Please make sure every temperature category as at least 1 biome.");
        }
        nonImmutableListPair.getLeft().sort(BiomeGroup::compareTo);

        for(BiomeGroup group : nonImmutableListPair.getLeft()){
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
    public BiomeGroup getRandomBiomeGroup(MutablePair<List<BiomeGroup>, Integer> regionGroup, INoiseRandom context){
        // Now choose a random item.
        int index = 0;
        for (double randomWeightPicked = context.random(regionGroup.getRight()); index < regionGroup.getLeft().size() - 1; ++index) {
            randomWeightPicked -= regionGroup.getLeft().get(index).getWeight();
            if (randomWeightPicked <= 0.0) break;
        }

        return regionGroup.getLeft().get(index);
    }

    /**
     * Takes a float and clamps it between 0 inclusive and 1 exclusive.
     * The biome group weight also take effect on what threshold values picks it.
     */
    public BiomeGroup getWeightedBiomeGroupByTemperature(MutablePair<List<BiomeGroup>, Integer> regionGroup, double threshold){
        // Now choose a random item.
        int index = 0;
        double clampedThreshold = Math.min(Math.max(threshold, 0), 0.999999999D);
        for (double thresholdWeight = clampedThreshold * regionGroup.getRight(); index < regionGroup.getLeft().size() - 1; ++index) {
            thresholdWeight -= regionGroup.getLeft().get(index).getWeight();
            if (thresholdWeight <= 0.0) break;
        }

        return regionGroup.getLeft().get(index);
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


    public MutablePair<List<BiomeGroup>, Integer> getOceanList() { return oceanList; }
    public MutablePair<List<BiomeGroup>, Integer> getEndList() { return endList; }
    public MutablePair<List<BiomeGroup>, Integer> getNetherList() { return netherList; }
    public MutablePair<List<BiomeGroup>, Integer> getHotList() { return hotList; }
    public MutablePair<List<BiomeGroup>, Integer> getWarmRegion(){ return warmList; }
    public MutablePair<List<BiomeGroup>, Integer> getCoolList() { return coolList; }
    public MutablePair<List<BiomeGroup>, Integer> getIcyList() { return icyList; }

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


    public List<Biome> importAllModdedBiomes(Registry<Biome> biomeRegistry, HashSet<ResourceLocation> blacklistedModdedBiomes){
        List<Biome> addedBiomes = new ArrayList<>();
        for(Map.Entry<RegistryKey<Biome>, Biome> entry : biomeRegistry.getEntries()){

            if(entry.getKey().getLocation().getNamespace().equals("minecraft") ||
                entry.getKey().getLocation().getNamespace().equals(UltraAmplifiedDimension.MODID) ||
                blacklistedModdedBiomes.contains(entry.getKey().getLocation()))
            {
                continue;
            }

            int biomeWeight = 6;
            Biome.Category biomeCategory = entry.getValue().getCategory();
            float temperature = entry.getValue().getTemperature();

            if(biomeCategory.equals(Biome.Category.NETHER)){
                addBiomeToList(entry, biomeWeight, netherList);
            }
            else if(biomeCategory.equals(Biome.Category.THEEND) || biomeCategory.equals(Biome.Category.NONE)){
                addBiomeToList(entry, biomeWeight, endList);
            }
            else if(biomeCategory.equals(Biome.Category.OCEAN)){
                addBiomeToList(entry, biomeWeight, oceanList);
            }
            else if(temperature > 1.0){
                addBiomeToList(entry, biomeWeight, hotList);
            }
            else if(temperature <= 0.0){
                addBiomeToList(entry, biomeWeight, coolList);
            }
            else if(temperature < 0.4){
                addBiomeToList(entry, biomeWeight, coolList);
            }
            else{
                addBiomeToList(entry, biomeWeight, warmList);
            }
            addedBiomes.add(entry.getValue());
        }
        return addedBiomes;
    }

    private void addBiomeToList(Map.Entry<RegistryKey<Biome>, Biome> entry, int biomeWeight, MutablePair<List<BiomeGroup>, Integer> netherList) {
        netherList.getLeft().add(new BiomeGroup(entry::getValue, biomeWeight));
        netherList.right += 6;
    }
}
