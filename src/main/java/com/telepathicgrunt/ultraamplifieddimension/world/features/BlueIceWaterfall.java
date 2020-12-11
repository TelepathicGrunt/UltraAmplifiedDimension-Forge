package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.Set;


public class BlueIceWaterfall extends Feature<NoFeatureConfig> {

    public BlueIceWaterfall(Codec<NoFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, NoFeatureConfig fluidConfig) {

        //creates a waterfall of blue ice that has a puddle at bottom
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position);

        if (!world.getBlockState(blockpos$Mutable.move(Direction.UP)).isSolid()) {
            return false;
        }
        else {
            //checks if we are in the side of a wall with air exposed on one side

            int numberOfSolidSides = 0;
            int neededNumberOfSides;

            if (!world.getBlockState(blockpos$Mutable.move(Direction.DOWN, 2)).isSolid()) {
                neededNumberOfSides = 4;
            }
            else {
                neededNumberOfSides = 3;
            }

            Direction emptySpot = Direction.NORTH;

            for (Direction face : Direction.Plane.HORIZONTAL) {

                if (world.getBlockState(blockpos$Mutable.setPos(position).move(face)).isSolid()) {
                    ++numberOfSolidSides;
                }
                else {
                    emptySpot = face;
                }
            }

            //position valid. begin making ice waterfall
            if (numberOfSolidSides == neededNumberOfSides) {

                //initial starting point of icefall
                world.setBlockState(blockpos$Mutable, Blocks.BLUE_ICE.getDefaultState(), 2);

                //in wall, offset to out of wall
                if (numberOfSolidSides == 3) {
                    //set what direction the open side of the wall is
                    blockpos$Mutable.move(emptySpot);
                    world.setBlockState(blockpos$Mutable, Blocks.BLUE_ICE.getDefaultState(), 2);
                }

                //places blue ice downward until it hit solid block
                while (true) {
                    if (blockpos$Mutable.getY() <= 1) {
                        return false;
                    }

                    blockpos$Mutable.move(Direction.DOWN); //move down to check below
                    if (world.getBlockState(blockpos$Mutable).getMaterial() == Material.AIR ||
                            world.getBlockState(blockpos$Mutable) == Blocks.BLUE_ICE.getDefaultState() ||
                            world.getBlockState(blockpos$Mutable) == Blocks.SNOW_BLOCK.getDefaultState())
                    {
                        world.setBlockState(blockpos$Mutable, Blocks.BLUE_ICE.getDefaultState(), 2);
                        continue; //restart loop to keep moving downward
                    }

                    blockpos$Mutable.move(Direction.UP); //move back up as downward is blocked off
                    boolean spotFound = false;

                    //goes around ledge
                    for (Direction face : Direction.Plane.HORIZONTAL) {

                        if ((world.getBlockState(blockpos$Mutable.move(face)).getMaterial() == Material.AIR || world.getBlockState(blockpos$Mutable) == Blocks.BLUE_ICE.getDefaultState()))
                        {
                            if((world.getBlockState(blockpos$Mutable.move(Direction.DOWN)).getMaterial() == Material.AIR || world.getBlockState(blockpos$Mutable) == Blocks.BLUE_ICE.getDefaultState())){
                                // undo the move down and face offset in the if statement above
                                blockpos$Mutable.move(face.getOpposite()).move(Direction.UP);
                                blockpos$Mutable.move(emptySpot);

                                world.setBlockState(blockpos$Mutable, Blocks.BLUE_ICE.getDefaultState(), 2);
                                blockpos$Mutable.move(Direction.DOWN);
                                world.setBlockState(blockpos$Mutable, Blocks.BLUE_ICE.getDefaultState(), 2);
                                spotFound = true;

                                if (blockpos$Mutable.getY() <= 1) {
                                    return false;
                                }
                                else {
                                    break;
                                }
                            }
                            else{
                                // undo the move down and face offset in the if statement above
                                blockpos$Mutable.move(face.getOpposite()).move(Direction.UP);
                            }
                        }
                        else{
                            // undo the face offset in the if statement above
                            blockpos$Mutable.move(face.getOpposite());
                        }
                    }

                    if (!spotFound) {
                        break;
                    }

                }

                //creates blue ice puddle at bottom
                int width = rand.nextInt(2) + 2;
                for (int y = blockpos$Mutable.getY() - 1; y < blockpos$Mutable.getY() + 1; ++y) {
                    for (int x = -width; x <= width; ++x) {
                        for (int z = -width; z <= width; ++z) {
                            if (x * x + z * z <= width * width) {
                                if (y > 1 && y < world.getWorld().func_234938_ad_()) {

                                    BlockPos blockpos = new BlockPos(x + blockpos$Mutable.getX(), y, z + blockpos$Mutable.getZ());
                                    BlockState block = world.getBlockState(blockpos);

                                    //replace solid and liquid blocks
                                    if (block.isSolid() || !block.getFluidState().isEmpty() || block == Blocks.ICE.getDefaultState()) {
                                        world.setBlockState(blockpos, Blocks.BLUE_ICE.getDefaultState(), 2);
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
                for (int x = -width; x <= width; ++x) {
                    for (int z = -width; z <= width; ++z) {
                        if (x * x + z * z <= width * width) {
                            BlockPos blockpos = new BlockPos(x + blockpos$Mutable.getX(), blockpos$Mutable.getY() + 1, z + blockpos$Mutable.getZ());
                            BlockState block = world.getBlockState(blockpos);

                            if (block == Blocks.SNOW.getDefaultState()) {
                                world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
                            }
                        }
                    }
                }

            }
            return true;
        }
    }
}