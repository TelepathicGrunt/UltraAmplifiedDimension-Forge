package com.telepathicgrunt.ultraamplifieddimension.mixin.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpreadableSnowyDirtBlock.class)
public interface SpreadableSnowyDirtBlockAccessor {
    @Invoker("isSnowyAndNotUnderwater")
    static boolean uad_callIsSnowyAndNotUnderwater(BlockState state, IWorldReader worldReader, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    @Invoker("isSnowyConditions")
    static boolean uad_callIsSnowyConditions(BlockState state, IWorldReader worldReader, BlockPos pos) {
        throw new UnsupportedOperationException();
    }
}
