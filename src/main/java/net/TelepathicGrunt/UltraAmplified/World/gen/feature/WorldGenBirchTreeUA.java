package net.telepathicgrunt.ultraamplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenBirchTreeUA extends WorldGenAbstractTree
{
    private static final IBlockState LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH);
    private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));

    //this class is just a slightly modified WorldGenBirchTree class so we could generate slightly taller birch tree than normal birch tree
    public WorldGenBirchTreeUA(boolean notify)
    {
        super(notify);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int height = rand.nextInt(3) + 6 + rand.nextInt(13);
        
        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
        {
            for (int currentHeight = position.getY(); currentHeight <= position.getY() + 1 + height; ++currentHeight)
            {
                int k = 1;

                if (currentHeight == position.getY())
                {
                    k = 0;
                }

                if (currentHeight >= position.getY() + 1 + height - 2)
                {
                    k = 2;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int x = position.getX() - k; x <= position.getX() + k && flag; ++x)
                {
                    for (int z = position.getZ() - k; z <= position.getZ() + k && flag; ++z)
                    {
                        if (currentHeight >= 0 && currentHeight < 256)
                        {
                            if (!this.canGrowInto(worldIn.getBlockState(blockpos$mutableblockpos.setPos(x, currentHeight, z)).getBlock()))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                Block block = worldIn.getBlockState(position.down()).getBlock();

                if ((block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.FARMLAND) && position.getY() < 256 - height - 1)
                {
                    this.setDirtAt(worldIn, position.down());

                    for (int i2 = position.getY() - 3 + height; i2 <= position.getY() + height; ++i2)
                    {
                        int k2 = i2 - (position.getY() + height);
                        int l2 = 1 - k2 / 2;

                        for (int i3 = position.getX() - l2; i3 <= position.getX() + l2; ++i3)
                        {
                            int j1 = i3 - position.getX();

                            for (int k1 = position.getZ() - l2; k1 <= position.getZ() + l2; ++k1)
                            {
                                int l1 = k1 - position.getZ();

                                if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || rand.nextInt(2) != 0 && k2 != 0)
                                {
                                    BlockPos blockpos = new BlockPos(i3, i2, k1);
                                    Material material = worldIn.getBlockState(blockpos).getMaterial();

                                    if (material == Material.AIR || material == Material.LEAVES)
                                    {
                                        this.setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
                                    }
                                }
                            }
                        }
                    }

                    for (int j2 = 0; j2 < height; ++j2)
                    {
                        Material material1 = worldIn.getBlockState(position.up(j2)).getMaterial();

                        if (material1 == Material.AIR || material1 == Material.LEAVES)
                        {
                            this.setBlockAndNotifyAdequately(worldIn, position.up(j2), LOG);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
