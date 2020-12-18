package com.telepathicgrunt.ultraamplifieddimension.blocks;

import com.telepathicgrunt.ultraamplifieddimension.mixin.SpreadableSnowyDirtBlockAccessor;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;


public class GlowgrassBlock extends GrassBlock {

    public GlowgrassBlock() {
        super(Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.5F).sound(SoundType.PLANT).setLightLevel((blockState) -> 15));
    }


    /*
     * every tick, it'll attempt to spread normal mycelium instead of itself. If covered, will turn into glowdirt.
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isRemote) {
            if (!world.isAreaLoaded(pos, 3))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (!SpreadableSnowyDirtBlockAccessor.callIsSnowyConditions(state, world, pos)) {
                //block is covered and so will turn into glowdirt
                world.setBlockState(pos, UADBlocks.GLOWDIRT.get().getDefaultState());
            }
            else if (world.getLight(pos.up()) >= 4) {
                if (world.getLight(pos.up()) >= 9) {
                    //attempt to spread grass onto neighboring dirt (glowdirt handles its own conversion)
                    BlockState replacementBlock = Blocks.GRASS_BLOCK.getDefaultState();

                    for (int i = 0; i < 4; ++i) {
                        BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                        if (world.getBlockState(blockpos).getBlock() == Blocks.DIRT && SpreadableSnowyDirtBlockAccessor.callIsSnowyAndNotUnderwater(replacementBlock, world, blockpos)) {
                            world.setBlockState(blockpos, replacementBlock.with(SNOWY, world.getBlockState(blockpos.up()).getBlock() == Blocks.SNOW));
                        }
                    }
                }
            }
        }
    }
}
