package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.mixin.NoiseChunkGeneratorAccessor;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.ColumnConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.Set;


public class ColumnRamp extends Feature<ColumnConfig> {
    public final Set<Block> irreplacableBlocks;

    public ColumnRamp(Codec<ColumnConfig> configFactory) {
        super(configFactory);

        irreplacableBlocks = ImmutableSet.of(Blocks.BEEHIVE, Blocks.AIR, Blocks.CAVE_AIR, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK, Blocks.MUSHROOM_STEM, Blocks.CACTUS, UADBlocks.BIG_CACTUS_BODY_BLOCK.get(), UADBlocks.BIG_CACTUS_CORNER_BLOCK.get(), UADBlocks.BIG_CACTUS_MAIN_BLOCK.get());
    }


    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, ColumnConfig blocksConfig) {

        BlockPos.Mutable blockposMutable = new BlockPos.Mutable().setPos(position);
        int minWidth = 4;
        int currentHeight = 0;
        int ceilingHeight;
        int bottomFloorHeight;
        int topFloorHeight;
        int heightDiff;
        IChunk cachedChunk = world.getChunk(blockposMutable);

        //finds ceiling
        while (!cachedChunk.getBlockState(blockposMutable.up(currentHeight)).isSolid()) {
            //too high for ramp to generate
            if (blockposMutable.getY() > chunkGenerator.getMaxBuildHeight() - 1) {
                return false;
            }
            blockposMutable.move(Direction.UP, 2);
        }
        ceilingHeight = blockposMutable.getY();

        //finds floor above ceiling
        while (cachedChunk.getBlockState(blockposMutable.up(currentHeight)).isSolid()) {
            //too high for ramp to generate
            if (blockposMutable.getY() > chunkGenerator.getMaxBuildHeight() - 1) {
                return false;
            }
            blockposMutable.move(Direction.UP);
        }
        topFloorHeight = blockposMutable.up(currentHeight).getY();

        //too thick or thin for ramp to generate
        if (topFloorHeight - ceilingHeight > 7 || topFloorHeight - ceilingHeight < 2) {
            return false;
        }

        //find floor
        blockposMutable.setPos(position); // reset back to normal height
        while (!cachedChunk.getBlockState(blockposMutable).isSolid()) {
            //too low/tall for column to generate
            if (blockposMutable.getY() < 70) {
                return false;
            }
            blockposMutable.move(Direction.DOWN, 2);
        }
        bottomFloorHeight = blockposMutable.getY();

        heightDiff = ceilingHeight - bottomFloorHeight;
        if (heightDiff > 27 || heightDiff < 8) {
            //too tall or short for a column ramp to spawn
            return false;
        }

        //how much to turn on a range of -1 to 1. -1 for north, 0 for south
        float randFloat = rand.nextFloat();
        float xTurningValue = (float) Math.sin(randFloat * Math.PI * 2);
        float zTurningValue = (float) Math.cos(randFloat * Math.PI * 2);

        //min thickness   where we are in height  /  controls thickening rate
        int widthAtHeight = getWidthAtHeight(0, heightDiff + 5, minWidth);

        //gets center of the ceiling position and floor position
        int xPosCeiling = position.getX() + getOffsetAtHeight(heightDiff + 1, heightDiff, xTurningValue);
        int zPosCeiling = position.getZ() + getOffsetAtHeight(0, heightDiff, zTurningValue);
        int xPosFloor = position.getX() - getOffsetAtHeight(heightDiff - 1, heightDiff, xTurningValue);
        int zPosFloor = position.getZ() + getOffsetAtHeight(0, heightDiff, zTurningValue);

        //checks to see if there is enough land above and below to hold pillar
        for (int x = -widthAtHeight; x <= widthAtHeight; x++) {
            for (int z = -widthAtHeight; z <= widthAtHeight; z++) {
                if (x * x + z * z > widthAtHeight * widthAtHeight * 0.85 && x * x + z * z < widthAtHeight * widthAtHeight) {

                    if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
                        cachedChunk = world.getChunk(blockposMutable);

                    BlockState block1 = cachedChunk.getBlockState(blockposMutable.setPos(xPosCeiling + x, ceilingHeight + 2, zPosCeiling + z));
                    BlockState block2 = cachedChunk.getBlockState(blockposMutable.setPos(xPosFloor + x, bottomFloorHeight - 2, zPosFloor + z));

                    //debugging
                    //world.setBlockState(blockpos$Mutable.setPos(position.getX() + x + getOffsetAtHeight(heightDiff + 1, heightDiff, xTurningValue), ceilingHeight + 2, position.getZ() + z + getOffsetAtHeight(0, heightDiff, zTurningValue)), Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
                    //world.setBlockState(blockpos$Mutable.setPos(position.getX() + x - getOffsetAtHeight(-1, heightDiff, xTurningValue), bottomFloorHeight - 2, position.getZ() + z - getOffsetAtHeight(0, heightDiff, zTurningValue)), Blocks.LAPIS_BLOCK.getDefaultState(), 2);

                    //there is not enough land to contain bases of ramp
                    if (!block1.isSolid() || !block2.isSolid()) {
                        return false;
                    }
                }
            }
        }

        //If this is reached, position is valid for ramp gen.

        //debugging
        //        if(heightDiff > 18) {
        //        	UltraAmplified.LOGGER.info("Large Ramp: "+position.getX()+" "+position.getY()+" "+position.getZ());
        //        }

        int xOffset;
        int zOffset;
        int xDiff;
        int zDiff;

        //clears hole for ramp
        for (int y = -2; y <= heightDiff + 3; y++) {
            // Method interprets input as:  min thickness  ,  where we are in height  ,  controls thickening rate
            widthAtHeight = getWidthAtHeight(y, heightDiff + 2, minWidth);

            if (heightDiff < 16) {
                xOffset = (int) (getOffsetAtHeight(y, heightDiff, xTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, xTurningValue) / 2f) * 2);
                zOffset = (int) (getOffsetAtHeight(y, heightDiff, zTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, zTurningValue) / 2f) * 2);
            }
            else if (heightDiff < 21) {
                xOffset = (int) (getOffsetAtHeight(y, heightDiff, xTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, xTurningValue) / 3f) * 4);
                zOffset = (int) (getOffsetAtHeight(y, heightDiff, zTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, zTurningValue) / 3f) * 4);
            }
            else {
                xOffset = (int) (getOffsetAtHeight(y, heightDiff, xTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, xTurningValue) / 3f) * 6);
                zOffset = (int) (getOffsetAtHeight(y, heightDiff, zTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, zTurningValue) / 3f) * 6);
            }

            //Begin clearing gen
            for (int x = position.getX() - widthAtHeight - 1; x <= position.getX() + widthAtHeight + 1; ++x) {
                for (int z = position.getZ() - widthAtHeight - 1; z <= position.getZ() + widthAtHeight + 1; ++z) {
                    xDiff = x - position.getX();
                    zDiff = z - position.getZ();
                    blockposMutable.setPos(x + xOffset, y + bottomFloorHeight + 3, z + zOffset);

                    //creates pillar with inside block
                    int xzDiffSquaredStretched = (xDiff * xDiff) + (zDiff * zDiff);
                    int circleBounds = (int) ((widthAtHeight - 1) * (widthAtHeight - 1) - 0.5F);

                    if (y > heightDiff) {
                        circleBounds *= (0.6f / (y - heightDiff));
                    }

                    if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
                        cachedChunk = world.getChunk(blockposMutable);

                    BlockState block = cachedChunk.getBlockState(blockposMutable);
                    if (!block.isIn(BlockTags.LEAVES) && !block.isIn(BlockTags.LOGS) && !irreplacableBlocks.contains(block.getBlock()) && xzDiffSquaredStretched <= circleBounds) {
                        if (blockposMutable.getY() < chunkGenerator.getSeaLevel() && chunkGenerator instanceof NoiseChunkGenerator)
                            cachedChunk.setBlockState(blockposMutable, ((NoiseChunkGeneratorAccessor)chunkGenerator).getDefaultFluid(), false);
                        else
                            cachedChunk.setBlockState(blockposMutable, Blocks.AIR.getDefaultState(), false);

                        //adds top block to exposed middle block after air was set
                        BlockState blockBelowAir = cachedChunk.getBlockState(blockposMutable.move(Direction.DOWN));
                        BlockState blockBelowBelowAir = cachedChunk.getBlockState(blockposMutable.move(Direction.DOWN));
                        blockposMutable.move(Direction.UP); // Move back to blockBelowAir

                        if (blockBelowAir.isSolid()) {
                            if (blocksConfig.topBlock.getMaterial() == Material.SAND && blockBelowBelowAir.getMaterial() == Material.AIR) {
                                cachedChunk.setBlockState(blockposMutable, blocksConfig.middleBlock, false);
                            }
                            else {
                                cachedChunk.setBlockState(blockposMutable, blocksConfig.topBlock, false);
                            }
                        }

                        blockposMutable.move(Direction.UP); // Move back to air spot
                    }
                }
            }
        }

        //makes ramp
        for (int y = -2; y <= heightDiff + 4; y++) {
            // Method interprets input as:  min thickness  ,  where we are in height  ,  controls thickening rate
            widthAtHeight = getWidthAtHeight(y, heightDiff + 5, minWidth);
            xOffset = getOffsetAtHeight(y, heightDiff, xTurningValue);
            zOffset = getOffsetAtHeight(y, heightDiff, zTurningValue);

            //Begin column gen
            for (int x = position.getX() - widthAtHeight - 1; x <= position.getX() + widthAtHeight + 1; ++x) {
                for (int z = position.getZ() - widthAtHeight - 1; z <= position.getZ() + widthAtHeight + 1; ++z) {
                    xDiff = x - position.getX();
                    zDiff = z - position.getZ();
                    blockposMutable.setPos(x + xOffset, y + bottomFloorHeight, z + zOffset);

                    //creates pillar with inside block
                    int xzDiffSquaredStretched = (xDiff * xDiff) + (zDiff * zDiff);
                    int circleBounds = (int) ((widthAtHeight - 1) * (widthAtHeight - 1) - 0.5F);

                    if (y > heightDiff - 3) {
                        circleBounds *= (0.8f / (y - (heightDiff - 3)));
                    }

                    if (y <= heightDiff && xzDiffSquaredStretched <= circleBounds) {
                        if (!world.getBlockState(blockposMutable).isSolid()) {
                            world.setBlockState(blockposMutable, blocksConfig.insideBlock, 2);
                        }
                    }
                    //We are at non-pillar space
                    //adds top and middle block to pillar part exposed in the below half of pillar
                    else if (y > heightDiff || xzDiffSquaredStretched <= (widthAtHeight + 3) * (widthAtHeight + 3)) {
                        //top block followed by 4 middle blocks below that
                        for (int downward = 0; downward < 6 && y - downward >= -3; downward++) {
                            BlockState block = world.getBlockState(blockposMutable.down(downward));
                            BlockState blockBelow = world.getBlockState(blockposMutable.down(downward + 1));
                            if (block == blocksConfig.insideBlock) {
                                if (downward == 1 && !(blocksConfig.topBlock.getMaterial() == Material.SAND && blockBelow.getMaterial() == Material.AIR)) {
                                    world.setBlockState(blockposMutable.down(downward), blocksConfig.topBlock, 2);
                                }
                                else {
                                    world.setBlockState(blockposMutable.down(downward), blocksConfig.middleBlock, 2);
                                }

                            }
                        }
                    }
                }
            }
        }

        return true;
    }


    private int getWidthAtHeight(int y, int heightDiff, int thinnestWidth) {
        float yFromCenter = y - heightDiff * 0.5F;
        yFromCenter = Math.abs(yFromCenter * 0.4F) + 3;

        return thinnestWidth + (int) ((yFromCenter * yFromCenter) / 8);
    }


    private int getOffsetAtHeight(int y, int heightDiff, float turningValue) {
        float yFromCenter = y - heightDiff / 2F;
        return (int) (turningValue * yFromCenter);
    }
}
