package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.StructureProcessorList;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class NbtDungeonConfig implements IFeatureConfig {
    public static final Codec<NbtDungeonConfig> CODEC = RecordCodecBuilder.<NbtDungeonConfig>create((configInstance) -> configInstance.group(
            Codec.BOOL.fieldOf("replace_air").orElse(false).forGetter(nbtDungeonConfig -> nbtDungeonConfig.replaceAir),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("min_air_space").forGetter(nbtFeatureConfig -> nbtFeatureConfig.minAirSpace),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("max_air_space").forGetter(nbtFeatureConfig -> nbtFeatureConfig.maxAirSpace),
            Codec.intRange(0, 100).fieldOf("max_num_of_chests").forGetter(nbtFeatureConfig -> nbtFeatureConfig.maxNumOfChests),
            ResourceLocation.CODEC.fieldOf("chest_loottable_resourcelocation").forGetter(nbtDungeonConfig -> nbtDungeonConfig.chestResourceLocation),
            Codec.mapPair(ResourceLocation.CODEC.fieldOf("resourcelocation"), Codec.intRange(1, Integer.MAX_VALUE).fieldOf("weight")).codec().listOf().fieldOf("dungeon_nbt_entries").forGetter(nbtFeatureConfig -> nbtFeatureConfig.nbtResourcelocationsAndWeights),
            Codec.mapPair(Registry.ENTITY_TYPE.fieldOf("resourcelocation"), Codec.intRange(1, Integer.MAX_VALUE).fieldOf("weight")).codec().listOf().fieldOf("spawner_mob_entries").forGetter(nbtFeatureConfig -> nbtFeatureConfig.spawnerResourcelocationsAndWeights),
            IStructureProcessorType.field_242922_m.fieldOf("processors").forGetter(nbtDungeonConfig -> nbtDungeonConfig.processor),
            Codec.BOOL.fieldOf("air_requirement_is_now_water").orElse(false).forGetter(nbtDungeonConfig -> nbtDungeonConfig.airRequirementIsNowWater),
            BlockState.CODEC.listOf().optionalFieldOf("blocks_to_always_place").forGetter((config) -> config.blocksToAlwaysPlace)
    ).apply(configInstance, NbtDungeonConfig::new))
            .comapFlatMap((nbtDungeonConfig) -> nbtDungeonConfig.maxAirSpace <= nbtDungeonConfig.minAirSpace ?
                    DataResult.error("min_air_space has to be smaller than max_air_space") : DataResult.success(nbtDungeonConfig), Function.identity());

    public final boolean replaceAir;
    public final int minAirSpace;
    public final int maxAirSpace;
    public final int maxNumOfChests;
    public final ResourceLocation chestResourceLocation;
    public final List<Pair<ResourceLocation, Integer>> nbtResourcelocationsAndWeights;
    public final List<Pair<EntityType<?>, Integer>> spawnerResourcelocationsAndWeights;
    public final Supplier<StructureProcessorList> processor;
    public final boolean airRequirementIsNowWater;
    public final Optional<List<BlockState>> blocksToAlwaysPlace;

    public NbtDungeonConfig(boolean replaceAir, int minAirSpace, int maxAirSpace,
                            int maxNumOfChests, ResourceLocation chestResourceLocation,
                            List<Pair<ResourceLocation, Integer>> nbtResourcelocationsAndWeights,
                            List<Pair<EntityType<?>, Integer>> spawnerResourcelocationsAndWeights,
                            Supplier<StructureProcessorList> processor, boolean airRequirementIsNowWater,
                            Optional<List<BlockState>> blocksToAlwaysPlace)
    {
        this.replaceAir = replaceAir;
        this.minAirSpace = minAirSpace;
        this.maxAirSpace = maxAirSpace;
        this.maxNumOfChests = maxNumOfChests;
        this.chestResourceLocation = chestResourceLocation;
        this.nbtResourcelocationsAndWeights = nbtResourcelocationsAndWeights;
        this.spawnerResourcelocationsAndWeights = spawnerResourcelocationsAndWeights;
        this.processor = processor;
        this.airRequirementIsNowWater = airRequirementIsNowWater;
        this.blocksToAlwaysPlace = blocksToAlwaysPlace;
    }
}
