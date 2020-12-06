package com.telepathicgrunt.ultraamplifieddimension.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpreadableSnowyDirtBlock.class)
public interface SpreadableSnowyDirtBlockAccessor {
    @Invoker
    static boolean callIsSnowyAndNotUnderwater(BlockState state, IWorldReader worldReader, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static boolean callIsSnowyConditions(BlockState state, IWorldReader worldReader, BlockPos pos) {
        throw new UnsupportedOperationException();
    }
}
