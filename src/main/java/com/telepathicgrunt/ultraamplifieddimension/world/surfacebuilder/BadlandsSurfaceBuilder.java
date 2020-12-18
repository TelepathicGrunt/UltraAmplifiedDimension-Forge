package com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;


public class BadlandsSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    protected BlockState[] field_215432_a;
    protected long field_215433_b;
    protected PerlinNoiseGenerator field_215435_c;
    protected PerlinNoiseGenerator field_215437_d;
    protected PerlinNoiseGenerator field_215439_e;


    public BadlandsSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }


    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        int xInChunk = x & 15;
        int zInChunk = z & 15;
        BlockState blockstate = Blocks.WHITE_TERRACOTTA.getDefaultState();
        BlockState blockstate1 = config.getUnder();
        int k = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        boolean flag = Math.cos(noise / 3.0D * Math.PI) > 0.0D;
        int depth = -1;
        boolean flag1 = false;
        int i1 = 0;
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

        for (int y = startHeight; y >= 0; --y) {
            if (i1 < 15) {
                blockpos$Mutable.setPos(xInChunk, y, zInChunk);
                BlockState blockstate2 = chunkIn.getBlockState(blockpos$Mutable);
                if (blockstate2.getMaterial() == Material.AIR) {
                    depth = -1;
                }
                else if (blockstate2.getBlock() == defaultBlock.getBlock()) {
                    if (depth == -1) {
                        flag1 = false;
                        if (k <= 0) {
                            blockstate = Blocks.AIR.getDefaultState();
                            blockstate1 = defaultBlock;
                        }
                        else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
                            blockstate = config.getUnderWaterMaterial();
                            blockstate1 = config.getUnder();
                        }

                        if (y < seaLevel && blockstate.getMaterial() == Material.AIR) {
                            blockstate = defaultFluid;
                        }

                        depth = k + Math.max(0, y - seaLevel);
                        if (y >= seaLevel - 1) {
                            if (y > seaLevel + 3 + k && chunkIn.getBlockState(blockpos$Mutable.down()).getMaterial() != Material.AIR) {
                                chunkIn.setBlockState(blockpos$Mutable, config.getTop(), false);
                                flag1 = true;
                            }
                            else {
                                BlockState blockstate3;
                                if (y >= 64 && y <= 127) {
                                    if (flag) {
                                        blockstate3 = Blocks.TERRACOTTA.getDefaultState();
                                    }
                                    else {
                                        blockstate3 = this.func_215431_a(x, y, z);
                                    }
                                }
                                else {
                                    blockstate3 = Blocks.ORANGE_TERRACOTTA.getDefaultState();
                                }

                                chunkIn.setBlockState(blockpos$Mutable, blockstate3, false);
                            }
                        }
                        else {
                            chunkIn.setBlockState(blockpos$Mutable, blockstate1, false);
                            Block block = blockstate1.getBlock();
                            if (UADTags.TERRACOTTA_BLOCKS.contains(block)) {
                                chunkIn.setBlockState(blockpos$Mutable, Blocks.ORANGE_TERRACOTTA.getDefaultState(), false);
                            }
                        }
                    }
                    else if (depth > 0) {
                        --depth;
                        if (flag1) {
                            chunkIn.setBlockState(blockpos$Mutable, Blocks.ORANGE_TERRACOTTA.getDefaultState(), false);
                        }
                        else {
                            chunkIn.setBlockState(blockpos$Mutable, this.func_215431_a(x, y, z), false);
                        }
                    }

                    ++i1;
                }
            }
        }

    }


    @Override
    public void setSeed(long seed) {
        if (this.field_215433_b != seed || this.field_215432_a == null) {
            this.func_215430_b(seed);
        }

        if (this.field_215433_b != seed || this.field_215435_c == null || this.field_215437_d == null) {
            SharedSeedRandom random = new SharedSeedRandom(seed);
            this.field_215435_c = new PerlinNoiseGenerator(random, IntStream.rangeClosed(-3, 0));
            this.field_215437_d = new PerlinNoiseGenerator(random, ImmutableList.of(0));
        }

        this.field_215433_b = seed;
    }


    protected void func_215430_b(long p_215430_1_) {
        this.field_215432_a = new BlockState[64];
        Arrays.fill(this.field_215432_a, Blocks.TERRACOTTA.getDefaultState());
        SharedSeedRandom random = new SharedSeedRandom(p_215430_1_);
        this.field_215439_e = new PerlinNoiseGenerator(random, ImmutableList.of(0));

        for (int l1 = 0; l1 < 64; ++l1) {
            l1 += random.nextInt(5) + 1;
            if (l1 < 64) {
                this.field_215432_a[l1] = Blocks.ORANGE_TERRACOTTA.getDefaultState();
            }
        }

        int i2 = random.nextInt(4) + 2;

        for (int i = 0; i < i2; ++i) {
            int j = random.nextInt(3) + 1;
            int k = random.nextInt(64);

            for (int l = 0; k + l < 64 && l < j; ++l) {
                this.field_215432_a[k + l] = Blocks.YELLOW_TERRACOTTA.getDefaultState();
            }
        }

        int j2 = random.nextInt(4) + 2;

        for (int k2 = 0; k2 < j2; ++k2) {
            int i3 = random.nextInt(3) + 2;
            int l3 = random.nextInt(64);

            for (int i1 = 0; l3 + i1 < 64 && i1 < i3; ++i1) {
                this.field_215432_a[l3 + i1] = Blocks.BROWN_TERRACOTTA.getDefaultState();
            }
        }

        int l2 = random.nextInt(4) + 2;

        for (int j3 = 0; j3 < l2; ++j3) {
            int i4 = random.nextInt(3) + 1;
            int k4 = random.nextInt(64);

            for (int j1 = 0; k4 + j1 < 64 && j1 < i4; ++j1) {
                this.field_215432_a[k4 + j1] = Blocks.RED_TERRACOTTA.getDefaultState();
            }
        }

        int k3 = random.nextInt(3) + 3;
        int j4 = 0;

        for (int l4 = 0; l4 < k3; ++l4) {
            j4 += random.nextInt(16) + 4;

            for (int k1 = 0; j4 + k1 < 64 && k1 < 1; ++k1) {
                this.field_215432_a[j4 + k1] = Blocks.WHITE_TERRACOTTA.getDefaultState();
                if (j4 + k1 > 1 && random.nextBoolean()) {
                    this.field_215432_a[j4 + k1 - 1] = Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
                }

                if (j4 + k1 < 63 && random.nextBoolean()) {
                    this.field_215432_a[j4 + k1 + 1] = Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
                }
            }
        }

    }


    protected BlockState func_215431_a(int p_215431_1_, int p_215431_2_, int p_215431_3_) {
        int i = (int) Math.round(this.field_215439_e.noiseAt(p_215431_1_ / 512.0D, p_215431_3_ / 512.0D, false) * 2.0D);
        return this.field_215432_a[(p_215431_2_ + i + 64) % 64];
    }
}