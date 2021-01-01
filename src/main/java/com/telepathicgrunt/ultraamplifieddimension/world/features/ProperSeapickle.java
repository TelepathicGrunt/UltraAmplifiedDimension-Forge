package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.SeaPickleConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class ProperSeapickle extends Feature<SeaPickleConfig> {
    public ProperSeapickle(Codec<SeaPickleConfig> codec) {
        super(codec);
    }

    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, SeaPickleConfig config) {
        int picklesPlaced = 0;

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for(int i = 0; i < config.count; ++i) {
            int x = rand.nextInt(8) - rand.nextInt(8);
            int z = rand.nextInt(8) - rand.nextInt(8);
            int y = rand.nextInt(8) - rand.nextInt(8);
            mutable.setPos(pos).move(x, y, z);
            BlockState blockstate = Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, rand.nextInt(config.maxPickles - (config.minPickles - 1)) + config.minPickles);
            if (world.getBlockState(mutable).isIn(Blocks.WATER) && blockstate.isValidPosition(world, mutable)) {
                world.setBlockState(mutable, blockstate, 2);
                ++picklesPlaced;
            }
        }

        return picklesPlaced > 0;
    }
}
