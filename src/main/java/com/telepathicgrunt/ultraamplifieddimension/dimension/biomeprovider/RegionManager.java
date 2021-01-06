package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;

import java.util.List;

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
        this.oceanList = oceanList;
        this.endList = endList;
        this.netherList = netherList;
        this.hotList = hotList;
        this.warmList = warmList;
        this.coolList = coolList;
        this.icyList = icyList;
    }

    /**
     * Will validate and make sure all biome identifiers do exist with the given dynamic registry.
     */
    public void validateAllBiomeIDs(Registry<Biome> dynamicRegistry){
        validateBiomeIDs(this.oceanList, "ocean list", dynamicRegistry);
        validateBiomeIDs(this.endList, "end list", dynamicRegistry);
        validateBiomeIDs(this.netherList, "nether list", dynamicRegistry);
        validateBiomeIDs(this.hotList, "hot list", dynamicRegistry);
        validateBiomeIDs(this.warmList, "warm list", dynamicRegistry);
        validateBiomeIDs(this.coolList, "cool list", dynamicRegistry);
        validateBiomeIDs(this.icyList, "icy list", dynamicRegistry);
    }

    /**
     * WIll print out any invalid biome within the regionGroup.
     */
    private void validateBiomeIDs(Pair<List<BiomeGroup>, Integer> regionGroup, String jsonEntry, Registry<Biome> dynamicRegistry){
        for(BiomeGroup biomeGroup : regionGroup.getFirst()){
            if(!dynamicRegistry.getOptional(biomeGroup.getMainBiome()).isPresent()){
                UltraAmplifiedDimension.LOGGER.warn("Unknown Main Biome in Ultra Amplified Dimension's dimenion json file at " + jsonEntry + " : " + biomeGroup.getMainBiome());
            }
        }
    }

    public ResourceLocation getRandomMainBiome(Pair<List<BiomeGroup>, Integer> regionGroup, INoiseRandom context){
        // Now choose a random item.
        int index = 0;
        for (double randomWeightPicked = context.random(regionGroup.getSecond()); index < regionGroup.getFirst().size() - 1; ++index) {
            randomWeightPicked -= regionGroup.getFirst().get(index).getWeight();
            if (randomWeightPicked <= 0.0) break;
        }

        return regionGroup.getFirst().get(index).getMainBiome();
    }

    public Pair<List<BiomeGroup>, Integer> getOceanList() { return oceanList; }
    public Pair<List<BiomeGroup>, Integer> getEndList() { return endList; }
    public Pair<List<BiomeGroup>, Integer> getNetherList() { return netherList; }
    public Pair<List<BiomeGroup>, Integer> getHotList() { return hotList; }
    public Pair<List<BiomeGroup>, Integer> getWarmRegion(){ return warmList; }
    public Pair<List<BiomeGroup>, Integer> getCoolList() { return coolList; }
    public Pair<List<BiomeGroup>, Integer> getIcyList() { return icyList; }
}
