package com.telepathicgrunt.ultraamplifieddimension.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;

public class BlockWithRuleReplaceConfig implements IFeatureConfig {
    public static final Codec<BlockWithRuleReplaceConfig> CODEC = RecordCodecBuilder.create((columnConfigInstance) -> columnConfigInstance.group(
            RuleTest.field_237127_c_.fieldOf("target").forGetter((config) -> config.target),
            BlockState.CODEC.fieldOf("state").forGetter((config) -> config.state)
        ).apply(columnConfigInstance, BlockWithRuleReplaceConfig::new));

    public final RuleTest target;
    public final BlockState state;

    public BlockWithRuleReplaceConfig(RuleTest target, BlockState state) {
        this.state = state;
        this.target = target;
    }
}
