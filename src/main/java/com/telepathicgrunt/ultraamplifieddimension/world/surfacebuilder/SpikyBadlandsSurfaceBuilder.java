package com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.BadlandsSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;


public class SpikyBadlandsSurfaceBuilder extends BadlandsSurfaceBuilder {
    public SpikyBadlandsSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    double max = 0;
    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        double spikeHeight = 0.0D;
        double spikeNoise = Math.min(Math.abs(noise), this.field_215435_c.noiseAt(x * 0.20D, z * 0.20D, false) * 15.0D);
        if (spikeNoise > -2D) {
            spikeHeight = spikeNoise * spikeNoise * 8.5D;

            // Result spikeHeight is between 0 and 330 it seems
            // This makes the massive spikes
            if (spikeHeight > 30D - (random.nextFloat() * 5D)) {
                spikeHeight = (spikeNoise + 3D); // 1 - 18

                // Scale it higher with spiky tops
                spikeHeight *=
                        (19D + spikeHeight) +
                        (random.nextFloat() * random.nextFloat() * 3) -
                        (random.nextFloat() * random.nextFloat() * 3);

            }

            double overallHeightRaised = 65D;
            spikeHeight += overallHeightRaised;

            // Rough up spikes that are too high
            if(spikeHeight > 250){
                spikeHeight -= (spikeHeight - (249D + (random.nextFloat() * 5D))) * 2D;
            }
            else if (spikeHeight < 150) {
                spikeHeight *= 1.25D;
            }
        }

        int xInChunk = x & 15;
        int zInChunk = z & 15;
        BlockState iblockstate2 = config.getUnderWaterMaterial();
        BlockState iblockstate = config.getUnder();
        int i1 = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        boolean flag = Math.cos(noise / 3.0D * Math.PI) > 0.0D;
        int j = -1;
        boolean flag1 = false;
        boolean hitSolidUnderwaterBlock = false;
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

        //might need to make k start at 255
        for (int y = Math.max(startHeight, (int) spikeHeight + 1); y >= 0; --y) {
            blockpos$Mutable.setPos(xInChunk, y, zInChunk);
            Material material = chunkIn.getBlockState(blockpos$Mutable).getMaterial();
            if ((material == Material.AIR || material == Material.WATER || material == Material.LAVA) && y < (int) spikeHeight && !hitSolidUnderwaterBlock) {
                chunkIn.setBlockState(blockpos$Mutable, defaultBlock, false);
            }
            else if (y < seaLevel) {
                hitSolidUnderwaterBlock = true;
            }

            BlockState iblockstate1 = chunkIn.getBlockState(blockpos$Mutable);
            if (iblockstate1.getMaterial() == Material.AIR) {
                j = -1;
            }
            else if (iblockstate1.getBlock() == defaultBlock.getBlock()) {
                if (j == -1) {
                    flag1 = false;
                    if (i1 <= 0) {
                        iblockstate2 = Blocks.AIR.getDefaultState();
                        iblockstate = defaultBlock;
                    }
                    else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
                        iblockstate2 = config.getUnderWaterMaterial();
                        iblockstate = config.getUnder();
                    }

                    if (y < seaLevel - 5 && iblockstate2.getMaterial() == Material.AIR) {
                        iblockstate2 = defaultFluid;
                    }

                    j = i1 + Math.max(0, y - seaLevel);
                    if (y >= seaLevel - 1) {
                        if (y <= seaLevel + 25 + i1) {
                            chunkIn.setBlockState(blockpos$Mutable, config.getTop(), false);
                            flag1 = true;
                        }
                        else {
                            BlockState iblockstate3;
                            if (y >= 64 && y <= 127) {
                                if (flag) {
                                    iblockstate3 = Blocks.TERRACOTTA.getDefaultState();
                                }
                                else {
                                    iblockstate3 = this.func_215431_a(x, y, z);
                                }
                            }
                            else {
                                iblockstate3 = Blocks.ORANGE_TERRACOTTA.getDefaultState();
                            }

                            chunkIn.setBlockState(blockpos$Mutable, iblockstate3, false);
                        }
                    }
                    else {
                        chunkIn.setBlockState(blockpos$Mutable, iblockstate, false);
                        Block block = iblockstate.getBlock();
                        if (UADTags.TERRACOTTA_BLOCKS.contains(block)) {
                            chunkIn.setBlockState(blockpos$Mutable, Blocks.ORANGE_TERRACOTTA.getDefaultState(), false);
                        }
                    }
                }
                else if (j > 0) {
                    --j;
                    if (flag1) {
                        chunkIn.setBlockState(blockpos$Mutable, Blocks.ORANGE_TERRACOTTA.getDefaultState(), false);
                    }
                    else {
                        chunkIn.setBlockState(blockpos$Mutable, this.func_215431_a(x, y, z), false);
                    }
                }
            }
        }

    }
}