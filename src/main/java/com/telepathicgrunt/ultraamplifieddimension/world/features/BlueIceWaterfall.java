package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
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

import java.util.Random;


public class BlueIceWaterfall extends Feature<NoFeatureConfig> {

    public BlueIceWaterfall(Codec<NoFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, NoFeatureConfig fluidConfig) {

        //creates a waterfall of blue ice that has a puddle at bottom
        BlockPos.Mutable blockposMutable = new BlockPos.Mutable().setPos(position);
        IChunk cachedChunk = world.getChunk(blockposMutable);

        if (!cachedChunk.getBlockState(blockposMutable.move(Direction.UP)).isSolid()) {
            return false;
        }
        else {
            //checks if we are in the side of a wall with air exposed on one side

            int numberOfSolidSides = 0;
            int neededNumberOfSides;

            if (!cachedChunk.getBlockState(blockposMutable.move(Direction.DOWN, 2)).isSolid()) {
                neededNumberOfSides = 4;
            }
            else {
                neededNumberOfSides = 3;
            }

            Direction emptySpot = Direction.NORTH;

            for (Direction face : Direction.Plane.HORIZONTAL) {

                blockposMutable.setPos(position).move(face);
                if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
                    cachedChunk = world.getChunk(blockposMutable);

                if (cachedChunk.getBlockState(blockposMutable).isSolid()) {
                    ++numberOfSolidSides;
                }
                else {
                    emptySpot = face;
                }
            }

            //position valid. begin making ice waterfall
            if (numberOfSolidSides == neededNumberOfSides) {

                //initial starting point of icefall
                cachedChunk.setBlockState(blockposMutable, Blocks.BLUE_ICE.getDefaultState(), false);

                //in wall, offset to out of wall
                if (numberOfSolidSides == 3) {
                    //set what direction the open side of the wall is
                    blockposMutable.move(emptySpot);

                    if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
                        cachedChunk = world.getChunk(blockposMutable);

                    cachedChunk.setBlockState(blockposMutable, Blocks.BLUE_ICE.getDefaultState(), false);
                }

                //places blue ice downward until it hit solid block
                while (true) {
                    if (blockposMutable.getY() <= 1) {
                        return false;
                    }

                    blockposMutable.move(Direction.DOWN); //move down to check below

                    BlockState blockState1 = cachedChunk.getBlockState(blockposMutable);
                    if (blockState1.getMaterial() == Material.AIR ||
                        blockState1.getBlock() == Blocks.BLUE_ICE ||
                        blockState1.getBlock() == Blocks.SNOW_BLOCK)
                    {
                        cachedChunk.setBlockState(blockposMutable, Blocks.BLUE_ICE.getDefaultState(), false);
                        continue; //restart loop to keep moving downward
                    }

                    blockposMutable.move(Direction.UP); //move back up as downward is blocked off
                    boolean spotFound = false;

                    //goes around ledge
                    for (Direction face : Direction.Plane.HORIZONTAL) {

                        blockposMutable.move(face);
                        if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
                            cachedChunk = world.getChunk(blockposMutable);

                        BlockState blockState2 = cachedChunk.getBlockState(blockposMutable);
                        if (blockState2.getMaterial() == Material.AIR || blockState2.getBlock() == Blocks.BLUE_ICE) {

                            blockposMutable.move(Direction.DOWN);
                            BlockState blockState3 = cachedChunk.getBlockState(blockposMutable);

                            if(blockState3.getMaterial() == Material.AIR || blockState3.getBlock() == Blocks.BLUE_ICE){
                                // undo the move down and face offset in the if statement above
                                blockposMutable.move(face.getOpposite()).move(Direction.UP);
                                blockposMutable.move(emptySpot);

                                if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
                                    cachedChunk = world.getChunk(blockposMutable);

                                cachedChunk.setBlockState(blockposMutable, Blocks.BLUE_ICE.getDefaultState(), false);
                                blockposMutable.move(Direction.DOWN);
                                cachedChunk.setBlockState(blockposMutable, Blocks.BLUE_ICE.getDefaultState(), false);
                                spotFound = true;

                                if (blockposMutable.getY() <= 1) {
                                    return false;
                                }
                                else {
                                    break;
                                }
                            }
                            else{
                                // undo the move down and face offset in the if statement above
                                blockposMutable.move(face.getOpposite()).move(Direction.UP);
                            }
                        }
                        else{
                            // undo the face offset in the if statement above
                            blockposMutable.move(face.getOpposite());
                        }
                    }

                    if (!spotFound) {
                        break;
                    }

                }

                //creates blue ice puddle at bottom
                position = blockposMutable.toImmutable();
                int width = rand.nextInt(2) + 2;
                for (int y = blockposMutable.getY() - 1; y < blockposMutable.getY() + 1; ++y) {
                    for (int x = -width; x <= width; ++x) {
                        for (int z = -width; z <= width; ++z) {
                            if (x * x + z * z <= width * width) {
                                if (y > 1 && y < chunkGenerator.getMaxBuildHeight()) {

                                    blockposMutable.setPos(position).move(x, y, z);
                                    if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
                                        cachedChunk = world.getChunk(blockposMutable);

                                    BlockState blockState4 = cachedChunk.getBlockState(blockposMutable);

                                    //replace solid and liquid blocks
                                    if (blockState4.isSolid() || !blockState4.getFluidState().isEmpty() || blockState4.getBlock() == Blocks.ICE) {
                                        cachedChunk.setBlockState(blockposMutable, Blocks.BLUE_ICE.getDefaultState(), false);
                                    }
                                }
                                else {
                                    break;
                                }

                            }
                        }
                    }

                    width++;
                }

                //remove thin snow above the highest blue ice puddle
                position = blockposMutable.move(Direction.UP).toImmutable();
                for (int x = -width; x <= width; ++x) {
                    for (int z = -width; z <= width; ++z) {
                        if (x * x + z * z <= width * width) {

                            blockposMutable.setPos(position).move(x, 0, z);
                            if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
                                cachedChunk = world.getChunk(blockposMutable);

                            BlockState block = cachedChunk.getBlockState(blockposMutable);

                            if (block.getBlock() == Blocks.SNOW) {
                                cachedChunk.setBlockState(blockposMutable, Blocks.AIR.getDefaultState(), false);
                            }
                        }
                    }
                }

            }
            return true;
        }
    }
}