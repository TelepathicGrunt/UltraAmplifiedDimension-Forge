package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;


public class CountPlacerConfig implements IPlacementConfig {
    public static final Codec<CountPlacerConfig> CODEC = FeatureSpread.func_242254_a(-10, Integer.MAX_VALUE, Integer.MAX_VALUE).fieldOf("count").xmap(CountPlacerConfig::new, CountPlacerConfig::func_242799_a).codec();
    private final FeatureSpread featureSpread;

    public CountPlacerConfig(int base) {
        this.featureSpread = FeatureSpread.func_242252_a(base);
    }

    public CountPlacerConfig(FeatureSpread featureSpread) {
        this.featureSpread = featureSpread;
    }

    public FeatureSpread func_242799_a() {
        return this.featureSpread;
    }
}