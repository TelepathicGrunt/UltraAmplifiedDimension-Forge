package com.telepathicgrunt.ultraamplifieddimension.blocks;

import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;


public class GlowdirtBlock extends Block {

    public GlowdirtBlock() {
        super(Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().hardnessAndResistance(0.4F).sound(SoundType.GROUND).setLightLevel((blockState) -> 15));
    }


    public static boolean validLightAndSpacing(BlockState blockStateIn, IWorldReader world, BlockPos blockPosIn) {
        BlockPos blockpos = blockPosIn.up();
        BlockState blockstate = world.getBlockState(blockpos);

        if (blockstate.getBlock() == Blocks.SNOW && blockstate.get(SnowBlock.LAYERS) == 1) {
            return true;
        }
        else if (blockstate.getMaterial() != Material.AIR) {
            return false;
        }
        else {
            int i = LightEngine.func_215613_a(world, blockStateIn, blockPosIn, blockstate, blockpos, Direction.UP, blockstate.getOpacity(world, blockpos));
            return i < world.getMaxLightLevel();
        }
    }


    public static boolean validNeighboringBlockSpace(BlockState blockStateIn, IWorldReader world, BlockPos blockPosIn) {
        BlockPos blockpos = blockPosIn.up();
        return validLightAndSpacing(blockStateIn, world, blockPosIn) && !world.getFluidState(blockpos).isTagged(FluidTags.WATER);
    }


    // checks to see if there is a nearby grass block, glowgrass block, mycelium, or
    // glow mycelium and will transform into glowgrass block or mycelium block
    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isAreaLoaded(pos, 3))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading

        //if block is already in an invalid light level or has water/non-air/non-snow layer block above, exits method
        //as this block cannot convert now.
        if (!validNeighboringBlockSpace(this.getDefaultState(), world, pos)) {
            return;
        }

        if (world.getLight(pos.up()) >= 9) {
            BlockState replacementBlock;

            for (int i = 0; i < 4; ++i) {
                BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                Block neighborBlock = world.getBlockState(blockpos).getBlock();

                if (neighborBlock == Blocks.GRASS_BLOCK || neighborBlock == UADBlocks.GLOWGRASS_BLOCK.get()) {
                    replacementBlock = UADBlocks.GLOWGRASS_BLOCK.get().getDefaultState();
                    world.setBlockState(pos, replacementBlock.with(SnowyDirtBlock.SNOWY, world.getBlockState(pos.up()).getBlock() == Blocks.SNOW));
                }
                else if (neighborBlock == Blocks.MYCELIUM || neighborBlock == UADBlocks.GLOWMYCELIUM.get()) {
                    replacementBlock = UADBlocks.GLOWMYCELIUM.get().getDefaultState();
                    world.setBlockState(pos, replacementBlock.with(SnowyDirtBlock.SNOWY, world.getBlockState(pos.up()).getBlock() == Blocks.SNOW));
                }
            }
        }
    }
}
