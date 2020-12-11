package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.Set;


public class NetherSurfaceLavaRare extends Feature<NoFeatureConfig> {

    public NetherSurfaceLavaRare(Codec<NoFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockState blockstate = world.getBlockState(pos);
        boolean generateLava = false;
        int solidSurrounding = 0;

        for(Direction side : Direction.Plane.HORIZONTAL){
            if (world.getBlockState(mutable.setPos(pos).move(Direction.DOWN).move(side)).isSolid()) {
                ++solidSurrounding;
            }
        }

        //not enough solid blocks surrounding area to generate lava
        if (solidSurrounding < 3) {
            return false;
        }

        //full chance to generate in gravel
        if (blockstate == Blocks.GRAVEL.getDefaultState()) {
            mutable.setPos(pos).move(Direction.DOWN);
            if (world.getBlockState(mutable).isSolid()) {
                //can only generate in gravel if below is also a solid block to prevent
                //lava spawning in 1 thick gravel which causes the gravel to fall,
                //leaving a pillar of lava floating in mid-air which looks bad.
                generateLava = true;
            }
        }
        //1/3rd chance to generate in soulsand
        else if (blockstate == Blocks.SOUL_SAND.getDefaultState()) {
            if (rand.nextFloat() < 0.33) {
                generateLava = true;
            }
        }

        //1/30th chance to generate in netherrack
        else if (blockstate == Blocks.NETHERRACK.getDefaultState()) {
            if (rand.nextFloat() < 0.033) {
                generateLava = true;
            }
        }

        //generates surface lava that can flow
        if (generateLava) {
            world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 2);
            world.getPendingFluidTicks().scheduleTick(pos, Fluids.LAVA, 0);
        }
        return true;
    }
}