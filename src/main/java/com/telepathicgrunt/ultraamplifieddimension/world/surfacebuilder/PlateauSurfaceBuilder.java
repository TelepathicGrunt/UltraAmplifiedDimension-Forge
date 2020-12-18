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


public class PlateauSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public PlateauSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        this.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
    }

    protected void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int xStart, int zStart, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState topBlock, BlockState middleBlock, BlockState bottomBlock, int seaLevel) {

        BlockState iblockstate = topBlock;
        BlockState iblockstate1 = middleBlock;
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
        int bottomLayerNoise = -1;
        int noiseThing = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int x = xStart & 15;
        int z = zStart & 15;
        BlockState aboveBlock = Blocks.AIR.getDefaultState();
        BlockState above2Block = Blocks.AIR.getDefaultState();

        for (int y = startHeight; y >= 0; --y) {
            blockpos$Mutable.setPos(x, y, z);
            BlockState currentBlock = chunkIn.getBlockState(blockpos$Mutable);
            if (currentBlock.getMaterial() == Material.AIR) {
                bottomLayerNoise = -1;
            }
            else if (currentBlock.getBlock() == defaultBlock.getBlock()) {
                if (bottomLayerNoise == -1) {
                    if (noiseThing <= 0) {
                        iblockstate = Blocks.AIR.getDefaultState();
                        iblockstate1 = defaultBlock;
                    }
                    else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
                        iblockstate = topBlock;
                        iblockstate1 = middleBlock;
                    }

                    if (y < seaLevel && iblockstate.getMaterial() == Material.AIR) {
                        if (biomeIn.getTemperature(blockpos$Mutable.setPos(xStart, y, zStart)) < 0.15F) {
                            iblockstate = Blocks.ICE.getDefaultState();
                        }
                        else {
                            iblockstate = defaultFluid;
                        }

                        blockpos$Mutable.setPos(x, y, z);
                    }

                    bottomLayerNoise = noiseThing;
                    if (y >= seaLevel - 1) {
                        chunkIn.setBlockState(blockpos$Mutable, iblockstate, false);
                    }
                    else if (y < seaLevel - 7 - noiseThing) {
                        iblockstate = Blocks.AIR.getDefaultState();
                        iblockstate1 = defaultBlock;
                        chunkIn.setBlockState(blockpos$Mutable, bottomBlock, false);
                    }
                    else {
                        chunkIn.setBlockState(blockpos$Mutable, iblockstate1, false);
                    }
                }
                else if (bottomLayerNoise > 0) {
                    --bottomLayerNoise;
                    chunkIn.setBlockState(blockpos$Mutable, iblockstate1, false);
                }
            }

            //creates pillars under ledges
            if (y < chunkIn.getHeight() && y > 0) {

                //at bottom of ledge
                if (!currentBlock.isSolid() && !aboveBlock.isAir() && !above2Block.isAir()) {
                    // Sets middle block to start making pillar.
                    // Move down 2 back to original pos.
                    chunkIn.setBlockState(blockpos$Mutable.move(Direction.DOWN, 2), middleBlock, false);
                }
            }

            above2Block = aboveBlock;
            aboveBlock = currentBlock;
        }
    }
}