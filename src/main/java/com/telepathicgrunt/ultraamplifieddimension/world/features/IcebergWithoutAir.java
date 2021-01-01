package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


public class IcebergWithoutAir extends Feature<BlockStateFeatureConfig> {

    public IcebergWithoutAir(Codec<BlockStateFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, BlockStateFeatureConfig icebergConfig) {

        boolean flag = random.nextDouble() > 0.7D;
        BlockState iblockstate = icebergConfig.state;
        double d0 = random.nextDouble() * 2.0D * Math.PI;
        int i = 11 - random.nextInt(5);
        int j = 3 + random.nextInt(3);
        boolean flag1 = random.nextDouble() > 0.7D;
        int upperHeight = flag1 ? random.nextInt(6) + 6 : random.nextInt(15) + 3;
        if (!flag1 && random.nextDouble() > 0.9D) {
            upperHeight += random.nextInt(19) + 7;
        }

        int downHeight = Math.min(upperHeight + random.nextInt(11), 18);
        int maxheight = Math.min(upperHeight + random.nextInt(7) - random.nextInt(5), 11);
        int radius = flag1 ? i : 11;

        for (int x = -radius; x < radius; ++x) {
            for (int z = -radius; z < radius; ++z) {
                for (int y = 0; y < upperHeight; ++y) {
                    int k2 = flag1 ? this.func_205178_b(y, upperHeight, maxheight) : this.func_205183_a(random, y, upperHeight, maxheight);
                    if (flag1 || x < k2) {
                        this.func_205181_a(world, random, position, upperHeight, x, y, z, k2, radius, flag1, j, d0, flag, iblockstate);
                    }
                }
            }
        }

        this.func_205186_a(world, position, maxheight, upperHeight, flag1, i);

        for (int x = -radius; x < radius; ++x) {
            for (int z = -radius; z < radius; ++z) {
                for (int y = -1; y > -downHeight; --y) {
                    int l3 = flag1 ? MathHelper.ceil(radius * (1.0F - (float) Math.pow(y, 2.0D) / (downHeight * 8.0F))) : radius;
                    int l2 = this.func_205187_b(random, -y, downHeight, maxheight);
                    if (x < l2) {
                        this.func_205181_a(world, random, position, downHeight, x, y, z, l2, l3, flag1, j, d0, flag, iblockstate);
                    }
                }
            }
        }

        boolean flag2 = flag1 ? random.nextDouble() > 0.1D : random.nextDouble() > 0.7D;
        if (flag2) {
            this.func_205184_a(random, world, maxheight, upperHeight, position, flag1, i, d0, j);
        }

        return true;
    }


    private void func_205184_a(Random random, IWorld world,int maxheight, int height, BlockPos position,boolean flag, int int1, double double1, int int2)
    {
        int x = random.nextBoolean() ? -1 : 1;
        int z = random.nextBoolean() ? -1 : 1;
        int randomHeightBasedMultiplier = random.nextInt(Math.max(maxheight / 2 - 2, 1));
        if (random.nextBoolean()) {
            randomHeightBasedMultiplier = maxheight / 2 + 1 - random.nextInt(Math.max(maxheight - maxheight / 2 - 1, 1));
        }

        int l = random.nextInt(Math.max(maxheight / 2 - 2, 1));
        if (random.nextBoolean()) {
            l = maxheight / 2 + 1 - random.nextInt(Math.max(maxheight - maxheight / 2 - 1, 1));
        }

        if (flag) {
            randomHeightBasedMultiplier = l = random.nextInt(Math.max(int1 - 5, 1));
        }

        BlockPos blockpos = new BlockPos(x * randomHeightBasedMultiplier, 0, z * l);
        double double2 = flag ? double1 + (Math.PI / 2D) : random.nextDouble() * 2.0D * Math.PI;

        for (int currentHeight = 0; currentHeight < height - 3; ++currentHeight) {
            int heightThickness = this.func_205183_a(random, currentHeight, height, maxheight);
            this.func_205174_a(heightThickness, currentHeight, position, world, false, double2, blockpos, int1, int2);
        }

        for (int belowCenterY = -1; belowCenterY > -height + random.nextInt(5); --belowCenterY) {
            int heightThickness = this.func_205187_b(random, -belowCenterY, height, maxheight);
            this.func_205174_a(heightThickness, belowCenterY, position, world, true, double2, blockpos, int1, int2);
        }

    }


    private void func_205174_a(int heightThickness, int belowCenterY, BlockPos position, IWorld world, boolean placeWater, double double1, BlockPos pos2,int int1, int int2)
    {
        int radius = heightThickness + 1 + int1 / 3;
        int int3 = Math.min(heightThickness - 3, 3) + int2 / 2 - 1;
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position);
        BlockState blockState;

        for (int x = -radius; x < radius; ++x) {
            for (int z = -radius; z < radius; ++z) {
                double d0 = this.func_205180_a(x, z, pos2, radius, int3, double1);
                if (d0 < 0.0D) {
                    blockpos$Mutable.setPos(position).move(x, belowCenterY, z);
                    blockState = world.getBlockState(blockpos$Mutable);
                    if (blockState.isIn(BlockTags.ICE) || blockState.isIn(Blocks.SNOW_BLOCK)) {

                        world.setBlockState(blockpos$Mutable, appropriateBlockForNeighbors(world, position), 3);
                        if (!placeWater) {
                            this.removeSnowLayer(world, blockpos$Mutable);
                        }
                    }
                }
            }
        }

    }


    private void removeSnowLayer(IWorld world, BlockPos.Mutable mutableBlockPos)
    {
        if (world.getBlockState(mutableBlockPos.move(Direction.UP)).isIn(Blocks.SNOW)) {
            this.setBlockState(world, mutableBlockPos, appropriateBlockForNeighbors(world, mutableBlockPos.down()));
        }
        mutableBlockPos.move(Direction.DOWN);
    }


    private void func_205181_a(IWorld world, Random random, BlockPos position,int maxHeight, int xPos, int yPos, int zPos, int int1, int int2, boolean flag1, int int3, double double1, boolean flag2, BlockState defaultState)
    {
        double noise = flag1 ? this.func_205180_a(xPos, zPos, BlockPos.ZERO, int2, this.func_205176_a(yPos, maxHeight, int3), double1) : this.func_205177_a(xPos, zPos, BlockPos.ZERO, int1, random);
        if (noise < 0.0D) {
            BlockPos blockpos1 = position.add(xPos, yPos, zPos);
            double randomizer = flag1 ? -0.5D : (double)(-6 - random.nextInt(3));
            if (noise > randomizer && random.nextDouble() > 0.9D) {
                return;
            }

            this.func_205175_a(blockpos1, world, random, maxHeight - yPos, maxHeight, flag1, flag2, defaultState);
        }

    }


    private void func_205175_a(BlockPos position, IWorld world, Random random,int minHeight, int maxHeight, boolean flag1, boolean flag2, BlockState defaultState)
    {
        BlockState currentBlockState = world.getBlockState(position);

        if (currentBlockState.isAir() || currentBlockState.isIn(Blocks.SNOW_BLOCK) || currentBlockState.isIn(BlockTags.ICE) || !currentBlockState.getFluidState().isEmpty()) {
            boolean flag = !flag1 || random.nextDouble() > 0.05D;
            int i = flag1 ? 3 : 2;
            if (flag2 && currentBlockState.getFluidState().isEmpty() && minHeight <= random.nextInt(Math.max(1, maxHeight / i)) + maxHeight * 0.6D && flag) {
                this.setBlockState(world, position, Blocks.SNOW_BLOCK.getDefaultState());
            }
            else {
                this.setBlockState(world, position, defaultState);
            }
        }

    }


    private BlockState appropriateBlockForNeighbors(IWorld world, BlockPos position)
    {
        boolean bordersWater = false;
        boolean bordersAir = false;
        BlockState blockState;

        //detects what blocks are adjacent
        for (Direction face : Direction.values()) {

            if (face == Direction.UP) {
                continue;
            }

            blockState = world.getBlockState(position.offset(face));
            if (!blockState.isIn(BlockTags.ICE)) {
                if (blockState.isAir()) {
                    bordersAir = true;
                }
                else if (!blockState.getFluidState().isEmpty()) {
                    bordersWater = true;
                }
            }
        }

        if (bordersWater && bordersAir) {
            return Blocks.STONE.getDefaultState();
        }
        else if (bordersWater) {
            return Blocks.WATER.getDefaultState();
        }
        else if (bordersAir) {
            return Blocks.AIR.getDefaultState();
        }
        else {
            return Blocks.PACKED_ICE.getDefaultState();
        }
    }


    private void func_205186_a(IWorld world, BlockPos position,int smallRadiusIn, int height, boolean flag, int largeRadiusIn)
    {
        int radius = flag ? largeRadiusIn : smallRadiusIn / 2;
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position);

        for (int x = -radius; x <= radius; ++x) {
            for (int z = -radius; z <= radius; ++z) {
                for (int y = 0; y <= height; ++y) {
                    blockpos$Mutable.setPos(position).move(x, y, z);
                    BlockState blockState = world.getBlockState(blockpos$Mutable);
                    if (blockState.isIn(BlockTags.ICE) || blockState.isIn(Blocks.SNOW)) {
                        if (isAirBelow(world, blockpos$Mutable)) {
                            this.setBlockState(world, blockpos$Mutable, appropriateBlockForNeighbors(world, blockpos$Mutable));
                            this.setBlockState(world, blockpos$Mutable.move(Direction.UP), appropriateBlockForNeighbors(world, blockpos$Mutable));
                        }
                        else if (blockState.isIn(BlockTags.ICE)) {
                            int notIceCounter = 0;

                            for(Direction direction : Direction.Plane.HORIZONTAL){
                                BlockState neighboringState = world.getBlockState(blockpos$Mutable.move(direction));
                                if (!neighboringState.isIn(BlockTags.ICE)) {
                                    ++notIceCounter;
                                }
                                blockpos$Mutable.move(direction.getOpposite());
                            }

                            if (notIceCounter >= 3) {
                                this.setBlockState(world, blockpos$Mutable, appropriateBlockForNeighbors(world, blockpos$Mutable));
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isAirBelow(IBlockReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).isAir();
    }

    public int func_205178_b(int p_205178_1_, int p_205178_2_, int p_205178_3_) {
        float f1 = (1.0F - (float)Math.pow(p_205178_1_, 2.0D) / ((float)p_205178_2_ * 1.0F)) * (float)p_205178_3_;
        return MathHelper.ceil(f1 / 2.0F);
    }

    public int func_205183_a(Random rand, int int1, int int2, int int3) {
        float f = 3.5F - (rand.nextFloat() * 0.1f);
        float f1 = (1.0F - (float)Math.pow(int1, 2.0D) / ((float)int2 * f)) * (float)int3;
        if (int2 > 15) {
            int i = int1 < 3 ? int1 / 2 : int1;
            f1 = (1.0F - (float)i / ((float)int2 * f * 0.4F)) * (float)int3;
        }

        return MathHelper.ceil(f1 / 2.0F);
    }

    public int func_205187_b(Random rand, int p_205187_2_, int p_205187_3_, int p_205187_4_) {
        float f = 1.0F + rand.nextFloat() / 2.0F;
        float f1 = (1.0F - (float)p_205187_2_ / ((float)p_205187_3_ * f)) * (float)p_205187_4_;
        return MathHelper.ceil(f1 / 2.0F);
    }

    public double func_205180_a(int xIn, int zIn, BlockPos pos, int p_205180_4_, int p_205180_5_, double p_205180_6_) {
        return Math.pow(((double)(xIn - pos.getX()) * Math.cos(p_205180_6_) - (double)(zIn - pos.getZ()) * Math.sin(p_205180_6_)) / (double)p_205180_4_, 2.0D) + Math.pow(((double)(xIn - pos.getX()) * Math.sin(p_205180_6_) + (double)(zIn - pos.getZ()) * Math.cos(p_205180_6_)) / (double)p_205180_5_, 2.0D) - 1.0D;
    }

    public int func_205176_a(int p_205176_1_, int p_205176_2_, int p_205176_3_) {
        int i = p_205176_3_;
        if (p_205176_1_ > 0 && p_205176_2_ - p_205176_1_ <= 3) {
            i = p_205176_3_ - (4 - (p_205176_2_ - p_205176_1_));
        }

        return i;
    }

    public double func_205177_a(int p_205177_1_, int p_205177_2_, BlockPos pos, int p_205177_4_, Random rand) {
        float f = 10.0F * MathHelper.clamp(rand.nextFloat(), 0.2F, 0.8F) / (float)p_205177_4_;
        return (double)f + Math.pow(p_205177_1_ - pos.getX(), 2.0D) + Math.pow(p_205177_2_ - pos.getZ(), 2.0D) - Math.pow(p_205177_4_, 2.0D);
    }
}