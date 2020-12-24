package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.BambooConfig;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BambooLeaves;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


public class SafeBamboo extends Feature<BambooConfig> {

    private static final BlockState BAMBOO_DEFAULT = Blocks.BAMBOO.getDefaultState().with(BambooBlock.PROPERTY_AGE, 1).with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.NONE).with(BambooBlock.PROPERTY_STAGE, 0);
    private static final BlockState BAMBOO_LEAVES_LARGE_TOP = BAMBOO_DEFAULT.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.LARGE).with(BambooBlock.PROPERTY_STAGE, 1);
    private static final BlockState BAMBOO_LEAVES_LARGE = BAMBOO_DEFAULT.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.LARGE);
    private static final BlockState BAMBOO_LEAVES_SMALL = BAMBOO_DEFAULT.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.SMALL);

    public SafeBamboo(Codec<BambooConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, BambooConfig bambooConfig) {

        int i = 0;
        int maxHeight = rand.nextInt((bambooConfig.maxHeight + 1) - bambooConfig.minHeight) + bambooConfig.minHeight;
        int podzolRange = rand.nextInt((bambooConfig.podzolMaxRadius + 1) - bambooConfig.podzolMinRadius) + bambooConfig.podzolMinRadius;
        BlockPos.Mutable blockposMutable = new BlockPos.Mutable().setPos(position);
        IChunk cachedChunk = world.getChunk(position);

        if (cachedChunk.getBlockState(blockposMutable).isAir()) {
            if (Blocks.BAMBOO.getDefaultState().isValidPosition(world, blockposMutable)) {
                for (int x = position.getX() - podzolRange; x <= position.getX() + podzolRange; ++x) {
                    for (int z = position.getZ() - podzolRange; z <= position.getZ() + podzolRange; ++z) {
                        for (int y = position.getY() - 2; y <= position.getY() + 2; ++y) {
                            int xDiff = x - position.getX();
                            int zDiff = z - position.getZ();
                            if (xDiff * xDiff + zDiff * zDiff <= podzolRange * podzolRange) {
                                blockposMutable.setPos(x, y, z);
                                if (rand.nextFloat() < 0.4F && world.getBlockState(blockposMutable).getBlock() == Blocks.GRASS_BLOCK) {
                                    world.setBlockState(blockposMutable, Blocks.PODZOL.getDefaultState(), 3);
                                }
                            }
                        }
                    }
                }

                blockposMutable.setPos(position);
                for (int height = 0; height < maxHeight && height <= chunkGenerator.getMaxBuildHeight() && cachedChunk.getBlockState(blockposMutable).isAir(); ++height) {
                    cachedChunk.setBlockState(blockposMutable, BAMBOO_DEFAULT, false);
                    blockposMutable.move(Direction.UP, 1);
                }

                // Set the top of bamboo. We moved down one as the block above broke the previous loop.
                if(cachedChunk.getBlockState(blockposMutable.move(Direction.DOWN)).isIn(Blocks.BAMBOO))
                    cachedChunk.setBlockState(blockposMutable, BAMBOO_LEAVES_LARGE_TOP, false);
                if(cachedChunk.getBlockState(blockposMutable.move(Direction.DOWN)).isIn(Blocks.BAMBOO))
                    cachedChunk.setBlockState(blockposMutable, BAMBOO_LEAVES_LARGE, false);
                if(cachedChunk.getBlockState(blockposMutable.move(Direction.DOWN)).isIn(Blocks.BAMBOO))
                    cachedChunk.setBlockState(blockposMutable, BAMBOO_LEAVES_SMALL, false);
            }
            ++i;
        }
        return i > 0;
    }
}
