package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.BlockWithRuleReplaceConfig;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


public class OnSolidBlockPlacer extends Feature<BlockWithRuleReplaceConfig> {

    public OnSolidBlockPlacer(Codec<BlockWithRuleReplaceConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, BlockWithRuleReplaceConfig replaceBlockConfig) {
        BlockPos.Mutable mutable = new BlockPos.Mutable().setPos(position);
        if (replaceBlockConfig.target.test(world.getBlockState(mutable), rand) && world.getBlockState(mutable.move(Direction.DOWN)).isSolidSide(world, mutable, Direction.UP)) {
            world.setBlockState(mutable.move(Direction.UP), replaceBlockConfig.state, 2);
            return true;
        }

        return false;
    }
}