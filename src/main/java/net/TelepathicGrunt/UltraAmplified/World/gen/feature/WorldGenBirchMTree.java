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

public class WorldGenBirchMTree extends WorldGenHugeTrees{
	private static final IBlockState TRUNK = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH);
    private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    public WorldGenBirchMTree(boolean notify)
    {
        super(notify, 13, 50, TRUNK, LEAF);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int i = this.getHeight(rand);

        if (!this.ensureGrowable(worldIn, rand, position, i))
        {
            return false;
        }
        else
        {
            this.createCrown(worldIn, position.getX(), position.getZ(), position.getY() + i, 0, rand);

            for (int j = 0; j < i; ++j)
            {
                IBlockState iblockstate = worldIn.getBlockState(position.up(j));

                if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                {
                    this.setBlockAndNotifyAdequately(worldIn, position.up(j), this.woodMetadata);
                }

                if (j < i - 1)
                {
                    iblockstate = worldIn.getBlockState(position.add(1, j, 0));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, position.add(1, j, 0), this.woodMetadata);
                    }

                    iblockstate = worldIn.getBlockState(position.add(1, j, 1));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, position.add(1, j, 1), this.woodMetadata);
                    }

                    iblockstate = worldIn.getBlockState(position.add(0, j, 1));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, position.add(0, j, 1), this.woodMetadata);
                    }
                }
            }

            return true;
        }
    }

    private void createCrown(World worldIn, int x, int z, int y, int p_150541_5_, Random rand)
    {
        int i = this.baseHeight - (rand.nextInt(5) + 6);
        int j = 0;

        for (int k = y - i; k <= y+10; ++k)
        {
            int l = y - k;
            int i1 = p_150541_5_ + MathHelper.floor((float)l / (float)i * 2F);
            this.growLeavesLayerStrict(worldIn, new BlockPos(x, k, z), i1 + (int)((l > 0 && i1 == j && (k & 1) == 0 ? 0.7 : 1)*4));
            j = i1;
        }
    }

}
