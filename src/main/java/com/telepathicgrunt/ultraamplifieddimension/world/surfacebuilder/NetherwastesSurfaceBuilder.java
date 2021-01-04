package com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.utils.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;


public class NetherwastesSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public NetherwastesSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }
    
    protected long noiseSeed;
    protected OpenSimplexNoise noiseGen;

    @Override
    public void setSeed(long seed) {
        if (this.noiseSeed != seed || this.noiseGen == null) {
            this.noiseGen = new OpenSimplexNoise(seed);
        }

        this.noiseSeed = seed;
    }


    @SuppressWarnings("deprecation")
    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        int sealevel = seaLevel + 1;
        int xInChunk = x & 15;
        int zInChunk = z & 15;
        int noiseDepth = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
        BlockPos.Mutable blockpos$MutableUnder = new BlockPos.Mutable();
        int depth = -1;
        BlockState topBlockstate = config.getTop();
        BlockState bottomBlockstates = config.getTop();

        for (int ypos = startHeight; ypos >= 0; --ypos) {
            blockpos$Mutable.setPos(xInChunk, ypos, zInChunk);
            blockpos$MutableUnder.setPos(blockpos$Mutable).move(Direction.DOWN);
            BlockState currentBlockToReplace = chunkIn.getBlockState(blockpos$Mutable);

            if (currentBlockToReplace.isAir() || !currentBlockToReplace.getFluidState().isEmpty()) {
                depth = -1;
            }
            else if (currentBlockToReplace != Blocks.MAGMA_BLOCK.getDefaultState()){
                if (depth == -1) {
                    boolean bottomBlockFlag = this.noiseGen.eval(x * 0.02D + 100.0D, (ypos / 8f), z * 0.02D - 100.0D) > 0.45D;

                    if (noiseDepth <= 0) {
                        topBlockstate = Blocks.CAVE_AIR.getDefaultState();
                        bottomBlockstates = config.getTop();
                    }
                    else if (ypos >= sealevel - 4) {
                        boolean middleBlockFlag = this.noiseGen.eval(x * 0.015D, ypos / 7f, z * 0.015D) > 0.5D;

                        topBlockstate = config.getTop();
                        bottomBlockstates = config.getTop();

                        if (middleBlockFlag) {
                            topBlockstate = config.getUnder();
                            bottomBlockstates = config.getUnder();
                        }
                        else if (bottomBlockFlag) {
                            topBlockstate = config.getUnderWaterMaterial();
                        }
                        else if ((noise > -3.85 && noise < -3.7) ||
                                (noise > -0.1 && noise < 0.05) ||
                                (noise > 3.7 && noise < 3.85))
                        {
                            topBlockstate = Blocks.MAGMA_BLOCK.getDefaultState();
                        }
                    }

                    depth = noiseDepth;
                    if (ypos >= sealevel - 1) {
                        chunkIn.setBlockState(blockpos$Mutable, topBlockstate, false);

                        if (bottomBlockFlag && chunkIn.getBlockState(blockpos$MutableUnder).isAir()) {
                            chunkIn.setBlockState(blockpos$MutableUnder, config.getTop(), false);
                        }
                    }
                    else {
                        chunkIn.setBlockState(blockpos$Mutable, bottomBlockstates, false);
                    }
                }
                else if (depth > 0) {
                    --depth;
                    chunkIn.setBlockState(blockpos$Mutable, bottomBlockstates, false);
                }
                else {
                    chunkIn.setBlockState(blockpos$Mutable, config.getTop(), false);
                }
            }
        }
    }
}