package com.telepathicgrunt.ultraamplifieddimension.mixin.dimension;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DimensionSettings.class)
public interface DimensionSettingsInvoker {

    @Invoker("<init>")
    static DimensionSettings uad_invokeinit(DimensionStructuresSettings structures, NoiseSettings noise, BlockState defaultBlock, BlockState defaultFluid, int p_i231905_5_, int p_i231905_6_, int p_i231905_7_, boolean p_i231905_8_) {
        throw new UnsupportedOperationException();
    }

    @Invoker("isMobGenerationDisabled")
    boolean uad_invokefunc_236120_h_();
}