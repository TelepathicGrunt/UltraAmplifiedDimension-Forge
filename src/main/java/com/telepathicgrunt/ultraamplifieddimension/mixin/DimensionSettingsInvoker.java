package com.telepathicgrunt.ultraamplifieddimension.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DimensionSettings.class)
public interface DimensionSettingsInvoker {

    @Invoker("<init>")
    static DimensionSettings invokeinit(DimensionStructuresSettings structures, NoiseSettings noise, BlockState defaultBlock, BlockState defaultFluid, int p_i231905_5_, int p_i231905_6_, int p_i231905_7_, boolean p_i231905_8_) {
        throw new UnsupportedOperationException();
    }

    @Invoker("func_236120_h_")
    boolean invokefunc_236120_h_();
}