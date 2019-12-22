package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;

public class WorldGenMegaPineTreeUA extends WorldGenHugeTrees
{
    private static final IBlockState TRUNK = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
    private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private static final IBlockState PODZOL = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
    private final boolean useBaseHeight;

    //only change is significant increase in possible height and thicker/bigger leaves so trees fit the terrain more.
    public WorldGenMegaPineTreeUA(boolean notify, boolean useBaseHeight)
    {
        super(notify, 13, 50, TRUNK, LEAF);
        this.useBaseHeight = useBaseHeight;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int height = this.getHeight(rand);

        if (!this.ensureGrowable(worldIn, rand, position, height))
        {
            return false;
        }
        else
        {
            this.createCrown(worldIn, position.getX(), position.getZ(), position.getY() + height, 0, rand);

            for (int currentHeight = 0; currentHeight < height; ++currentHeight)
            {
                IBlockState iblockstate = worldIn.getBlockState(position.up(currentHeight));

                if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                {
                    this.setBlockAndNotifyAdequately(worldIn, position.up(currentHeight), this.woodMetadata);
                }

                if (currentHeight < height - 1)
                {
                    iblockstate = worldIn.getBlockState(position.add(1, currentHeight, 0));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, position.add(1, currentHeight, 0), this.woodMetadata);
                    }

                    iblockstate = worldIn.getBlockState(position.add(1, currentHeight, 1));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, position.add(1, currentHeight, 1), this.woodMetadata);
                    }

                    iblockstate = worldIn.getBlockState(position.add(0, currentHeight, 1));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, position.add(0, currentHeight, 1), this.woodMetadata);
                    }
                }
            }

            return true;
        }
    }

    private void createCrown(World worldIn, int x, int z, int y, int extraRadius, Random rand)
    {
        int height = rand.nextInt(5) + (this.useBaseHeight ? this.baseHeight : 3);
        int prevRadius = 0;

        for (int currentHeight = y - height; currentHeight <= y+20; ++currentHeight)
        {
            int heightDiff = y - currentHeight;
            int radius = extraRadius + MathHelper.floor((float)heightDiff / (float)height * 3.5F);
            this.growLeavesLayerStrict(worldIn, new BlockPos(x, currentHeight, z), radius + (int)((heightDiff > 0 && radius == prevRadius && (currentHeight & 1) == 0 ? 1 : 0)*2));
            prevRadius = radius;
        }
    }

    public void generateSaplings(World worldIn, Random random, BlockPos pos)
    {
        this.placePodzolCircle(worldIn, pos.west().north());
        this.placePodzolCircle(worldIn, pos.east(2).north());
        this.placePodzolCircle(worldIn, pos.west().south(2));
        this.placePodzolCircle(worldIn, pos.east(2).south(2));

        for (int currentCount = 0; currentCount < 5; ++currentCount)
        {
            int randNum = random.nextInt(64);
            int x = randNum % 8;
            int z = randNum / 8;

            if (x == 0 || x == 7 || z == 0 || z == 7)
            {
                this.placePodzolCircle(worldIn, pos.add(-3 + x, 0, -3 + z));
            }
        }
    }

    private void placePodzolCircle(World worldIn, BlockPos center)
    {
        for (int i = -2; i <= 2; ++i)
        {
            for (int j = -2; j <= 2; ++j)
            {
                if (Math.abs(i) != 2 || Math.abs(j) != 2)
                {
                    this.placePodzolAt(worldIn, center.add(i, 0, j));
                }
            }
        }
    }

    private void placePodzolAt(World worldIn, BlockPos pos)
    {
        for (int i = 2; i >= -3; --i)
        {
            BlockPos blockpos = pos.up(i);
            IBlockState iblockstate = worldIn.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (block == Blocks.GRASS || block == Blocks.DIRT)
            {
                this.setBlockAndNotifyAdequately(worldIn, blockpos, PODZOL);
                break;
            }

            if (iblockstate.getMaterial() != Material.AIR && i < 0)
            {
                break;
            }
        }
    }
}
