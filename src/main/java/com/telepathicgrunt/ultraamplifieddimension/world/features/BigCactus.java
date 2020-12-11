package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.blocks.BigCactusBodyBlock;
import com.telepathicgrunt.ultraamplifieddimension.blocks.BigCactusCornerBlock;
import com.telepathicgrunt.ultraamplifieddimension.blocks.BigCactusMainBlock;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.HeightConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


public class BigCactus extends Feature<HeightConfig> {

    public BigCactus(Codec<HeightConfig> configFactory) {
        super(configFactory);
    }

    //private final int height = 9;

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, HeightConfig betterCactusConfig) {
        //randomly set this cactus to a random spot. (thus passed in position must be the corner of the 4 loaded chunks)
        BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

        if (world.isAirBlock(blockpos) && world.getBlockState(blockpos.down()).isIn(BlockTags.SAND)) {

            //gets height with some variations
            //then gets left and right side between 2 and maximum height - 3
            //then finds the direction that the cactus will be facing
            int maxHeight = betterCactusConfig.height + rand.nextInt(2);
            int frontSideHeight = 2 + rand.nextInt(maxHeight - 4);
            int backSideHeight = 2 + rand.nextInt(maxHeight - 4);
            Direction cactusFacing = Direction.byHorizontalIndex(rand.nextInt(4));

            //create cactus from ground up
            for (int currentHeight = 0; currentHeight < maxHeight && world.isAirBlock(blockpos.up(currentHeight)); currentHeight++) {
                if (blockpos.up(currentHeight).getY() <= 254 && (currentHeight == frontSideHeight || currentHeight == backSideHeight)) {
                    //will make at least one branch

                    //finds what the center should be
                    if (frontSideHeight == backSideHeight) {
                        world.setBlockState(blockpos.up(currentHeight), UADBlocks.BIG_CACTUS_BODY_BLOCK.get().getDefaultState().with(BigCactusBodyBlock.FACING, cactusFacing), 18);
                    }
                    else {
                        world.setBlockState(blockpos.up(currentHeight), UADBlocks.BIG_CACTUS_CORNER_BLOCK.get().getDefaultState().with(BigCactusCornerBlock.FACING, currentHeight == frontSideHeight ? cactusFacing.getOpposite() : cactusFacing), 18);
                    }

                    //create the branches off of cactus
                    if (currentHeight == frontSideHeight) {
                        createBranch(world, blockpos.up(currentHeight), cactusFacing, rand.nextInt(maxHeight - frontSideHeight - 2) + 2);
                    }
                    if (currentHeight == backSideHeight) {
                        createBranch(world, blockpos.up(currentHeight), cactusFacing.getOpposite(), rand.nextInt(maxHeight - backSideHeight - 2) + 2);
                    }

                }
                else {
                    //places normal vertical cactus
                    world.setBlockState(blockpos.up(currentHeight), UADBlocks.BIG_CACTUS_MAIN_BLOCK.get().getDefaultState().with(BigCactusMainBlock.FACING, Direction.UP), 18);
                }
            }

        }

        //cactus finished generating
        return true;
    }


    /**
     * Will create branch but will stop early if any spot along the way is not an air block
     *
     * @param world           - world to check and place blocks in
     * @param position        - position of center of cactus that branch is coming off of
     * @param branchDirection - direction that the branch will go
     */
    private void createBranch(ISeedReader world, BlockPos position, Direction branchDirection, int maxHeightUp) {

        //horizontal part of branch first
        position = position.offset(branchDirection);
        if (world.isAirBlock(position)) {
            world.setBlockState(position, UADBlocks.BIG_CACTUS_MAIN_BLOCK.get().getDefaultState().with(BigCactusMainBlock.FACING, branchDirection), 18);
        }
        else {
            return;
        }

        //corner
        position = position.offset(branchDirection);
        if (world.isAirBlock(position)) {
            world.setBlockState(position, UADBlocks.BIG_CACTUS_CORNER_BLOCK.get().getDefaultState().with(BigCactusCornerBlock.FACING, branchDirection), 18);
        }
        else {
            return;
        }

        //upward part of branch
        for (int currentHeight = 1; currentHeight < maxHeightUp && position.up(currentHeight).getY() <= world.getWorld().func_234938_ad_(); currentHeight++) {
            if (world.isAirBlock(position.up(currentHeight))) {
                world.setBlockState(position.up(currentHeight), UADBlocks.BIG_CACTUS_MAIN_BLOCK.get().getDefaultState().with(BigCactusMainBlock.FACING, Direction.UP), 18);

            }
            else {
                return;
            }
        }

    }
}
