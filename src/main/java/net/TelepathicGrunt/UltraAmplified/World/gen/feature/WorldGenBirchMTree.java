package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

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
        int height = this.getHeight(rand);

        if (!this.ensureGrowable(worldIn, rand, position, height+9))
        {
            return false;
        }
        else
        {
        	//adds the leaves on crown
            this.createCrown(worldIn, position.getX(), position.getZ(), position.getY() + height, 0, rand);

            //adds the 2 by 2 wood trunk
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

    //this is set so that the crown is leaves in a cone shape 
    private void createCrown(World worldIn, int x, int z, int y, int extraRadiusSize, Random rand)
    {
        int i = this.baseHeight - (rand.nextInt(5) + 6);
        int j = 0;

        for (int currentHeight = y - i; currentHeight <= y+10; ++currentHeight)
        {
            int l = y - currentHeight;
            int radius = extraRadiusSize + MathHelper.floor((float)l / (float)i * 2F);
            this.growLeavesLayerStrict(worldIn, new BlockPos(x, currentHeight, z), radius + (int)((l > 0 && radius == j && (currentHeight & 1) == 0 ? 0.7 : 1)*4));
            j = radius;
        }
    }

}
