package com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;


public class IcedTerrainSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public IcedTerrainSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        BlockState iblockstate = config.getTop();
        BlockState iblockstate1 = config.getUnder();
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
        int i = -1;
        int j = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int xpos = x & 15;
        int zpos = z & 15;

        for (int ypos = startHeight; ypos >= 0; --ypos) {
            blockpos$Mutable.setPos(xpos, ypos, zpos);
            BlockState iblockstate2 = chunkIn.getBlockState(blockpos$Mutable);

            iblockstate2.getBlock();
            if (iblockstate2.getMaterial() == Material.AIR) {
                i = -1;
            }
            else if (iblockstate2.getMaterial() == Material.WATER) {

                if (ypos < seaLevel) {
                    chunkIn.setBlockState(blockpos$Mutable, config.getTop(), false);
                }

                i = -1;
            }
            else {
                if (iblockstate2.getBlock() == defaultBlock.getBlock()) {
                    if (i == -1) {
                        if (j <= 0) {
                            iblockstate = Blocks.AIR.getDefaultState();
                            iblockstate1 = config.getTop();
                        }
                        else if (ypos >= seaLevel - 4 && ypos <= seaLevel + 1) {
                            iblockstate = config.getUnderWaterMaterial();
                            iblockstate1 = config.getUnder();
                        }

                        i = j;
                        if (ypos >= seaLevel - 1) {
                            chunkIn.setBlockState(blockpos$Mutable, iblockstate, false);
                        }
                        else {
                            chunkIn.setBlockState(blockpos$Mutable, iblockstate1, false);
                        }
                    }
                    else if (i > 0) {
                        --i;
                        chunkIn.setBlockState(blockpos$Mutable, iblockstate1, false);
                    }
                    else {
                        chunkIn.setBlockState(blockpos$Mutable, config.getUnder(), false);
                    }
                }
            }
        }

    }
}