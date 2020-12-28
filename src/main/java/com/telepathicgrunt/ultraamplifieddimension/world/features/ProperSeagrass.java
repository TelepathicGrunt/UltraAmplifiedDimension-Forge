package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.ProbabilityAndCountConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class ProperSeagrass extends Feature<ProbabilityAndCountConfig> {
    public ProperSeagrass(Codec<ProbabilityAndCountConfig> codec) {
        super(codec);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ProbabilityAndCountConfig config) {
        boolean flag = false;

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for(int i = 0; i < config.count; i++){

            int x = rand.nextInt(8) - rand.nextInt(8);
            int z = rand.nextInt(8) - rand.nextInt(8);
            int y = rand.nextInt(8) - rand.nextInt(8);
            mutable.setPos(pos).move(x, y, z);

            if (reader.getBlockState(mutable).isIn(Blocks.WATER)) {

                boolean spawnTallGrass = rand.nextFloat() < config.probability;
                BlockState blockstate = spawnTallGrass ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();

                if (blockstate.isValidPosition(reader, mutable)) {
                    if (spawnTallGrass) {
                        BlockState blockstate1 = blockstate.with(TallSeaGrassBlock.HALF, DoubleBlockHalf.UPPER);
                        if (reader.getBlockState(mutable.move(Direction.UP)).isIn(Blocks.WATER)) {
                            reader.setBlockState(mutable.move(Direction.DOWN), blockstate, 2);
                            reader.setBlockState(mutable.move(Direction.UP), blockstate1, 2);
                        }
                    } else {
                        reader.setBlockState(mutable, blockstate, 2);
                    }

                    flag = true;
                }
            }
        }

        return flag;
    }
}
