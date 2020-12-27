package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.BlockWithRuleReplaceConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;

import java.util.Random;


public class OnSolidBlockPlacer extends Feature<BlockWithRuleReplaceConfig> {

    public OnSolidBlockPlacer(Codec<BlockWithRuleReplaceConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, BlockWithRuleReplaceConfig replaceBlockConfig) {
        if (replaceBlockConfig.target.test(world.getBlockState(position), rand) && world.getBlockState(position.down()).isSolidSide(world, position, Direction.DOWN)) {
            world.setBlockState(position, replaceBlockConfig.state, 2);
            return true;
        }

        return false;
    }
}