package com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;


public class SandSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public SandSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random, IChunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        this.buildSurface(random, chunk, biome, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
    }

    protected void buildSurface(Random random, IChunk chunk, Biome biome, int xStart, int zStart, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState topBlock, BlockState middleBlock, BlockState bottomBlock, int seaLevel) {

        BlockState topBlockstate = topBlock;
        BlockState bottomBlockstate = middleBlock;
        int bottomLayerNoise = -1;
        int noiseModified = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int x = xStart & 15;
        int z = zStart & 15;
        BlockPos.Mutable blockPosMutable = new BlockPos.Mutable(x, startHeight, z);

        for (; blockPosMutable.getY() >= 0; blockPosMutable.move(Direction.DOWN)) {
            BlockState currentBlock = chunk.getBlockState(blockPosMutable);
            if (bottomLayerNoise != -1 && currentBlock.getMaterial() == Material.AIR) {
                bottomLayerNoise = -1;
            }
            else if (currentBlock.getBlock() == defaultBlock.getBlock()) {
                if (bottomLayerNoise == -1) {
                    if (noiseModified <= 0) {
                        topBlockstate = Blocks.AIR.getDefaultState();
                        bottomBlockstate = defaultBlock;
                    }
                    else if (blockPosMutable.getY() >= seaLevel - 4 && blockPosMutable.getY() <= seaLevel + 1) {
                        topBlockstate = topBlock;
                        bottomBlockstate = middleBlock;
                    }

                    if (blockPosMutable.getY() < seaLevel && topBlockstate.getMaterial() == Material.AIR) {
                        if (biome.getTemperature(blockPosMutable.setPos(xStart, blockPosMutable.getY(), zStart)) < 0.15F) {
                            topBlockstate = Blocks.ICE.getDefaultState();
                        }
                        else {
                            topBlockstate = defaultFluid;
                        }

                        blockPosMutable.setPos(x, blockPosMutable.getY(), z);
                    }

                    bottomLayerNoise = noiseModified;
                    if (blockPosMutable.getY() >= seaLevel - 1) {
                        chunk.setBlockState(blockPosMutable, topBlockstate, false);
                        reinforceLedges(chunk, blockPosMutable, bottomBlock);
                    }
                    else if (blockPosMutable.getY() < seaLevel - 7 - noiseModified) {
                        topBlockstate = Blocks.AIR.getDefaultState();
                        bottomBlockstate = defaultBlock;
                        chunk.setBlockState(blockPosMutable, bottomBlock, false);
                    }
                    else {
                        chunk.setBlockState(blockPosMutable, bottomBlockstate, false);
                        reinforceLedges(chunk, blockPosMutable, bottomBlock);
                    }
                }
                else if (bottomLayerNoise > 0) {
                    --bottomLayerNoise;
                    chunk.setBlockState(blockPosMutable, bottomBlockstate, false);
                    reinforceLedges(chunk, blockPosMutable, bottomBlock);

                    if (bottomLayerNoise == 0 && bottomBlockstate.getBlock() == Blocks.SAND && noiseModified > 1) {
                        bottomLayerNoise = random.nextInt(4) + Math.max(0, blockPosMutable.getY() - seaLevel);
                        bottomBlockstate = bottomBlockstate.getBlock() == Blocks.RED_SAND ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
                    }
                }
            }

        }
    }

    private static void reinforceLedges(IChunk chunk, BlockPos.Mutable blockPosMutable, BlockState bottomBlock) {
        //needed to contain fallable blocks
        if (blockPosMutable.getY() < chunk.getHeight() && blockPosMutable.getY() > 0) {
            Material materialAbove = chunk.getBlockState(blockPosMutable.move(Direction.UP)).getMaterial();
            Material materialBelow = chunk.getBlockState(blockPosMutable.move(Direction.DOWN, 2)).getMaterial();
            blockPosMutable.move(Direction.UP); // Move it back to center

            if (materialBelow == Material.AIR) {
                if (materialAbove == Material.SAND) {
                    // sets bottom block so block above cannot fall
                    chunk.setBlockState(blockPosMutable, bottomBlock, false);
                }
                else if (materialAbove == Material.AIR) {
                    // one block thick ledges gets removed
                    chunk.setBlockState(blockPosMutable, Blocks.AIR.getDefaultState(), false);
                }
            }
        }
    }
}