package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class LootTableConfig implements IFeatureConfig {
    public static final Codec<LootTableConfig> CODEC = RecordCodecBuilder.create((cactusConfigInstance) -> cactusConfigInstance.group(
            ResourceLocation.CODEC.fieldOf("loot_table").forGetter((config) -> config.lootTable)
    ).apply(cactusConfigInstance, LootTableConfig::new));

    public final ResourceLocation lootTable;

    public LootTableConfig(ResourceLocation lootTable) {
        this.lootTable = lootTable;
    }
}
