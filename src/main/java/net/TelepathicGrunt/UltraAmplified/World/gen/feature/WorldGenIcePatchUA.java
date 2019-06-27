package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WorldGenIcePatchUA extends Feature<NoFeatureConfig> 
{
    private final IBlockState packedIce = Blocks.PACKED_ICE.getDefaultState();
    private final int basePathWidth = 3;

    public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) 
    {
        while (worldIn.isAirBlock(position) && position.getY() > 2)
        {
            position = position.down();
        }

        if (worldIn.getBlockState(position).getBlock() != Blocks.SAND)
        {
            return false;
        }
        else
        {
            int width = rand.nextInt(this.basePathWidth - 2) + 2;

            for (int x = position.getX() - width; x <= position.getX() + width; ++x)
            {
                for (int z = position.getZ() - width; z <= position.getZ() + width; ++z)
                {
                    int xDiff = x - position.getX();
                    int zDiff = z - position.getZ();

                    if (xDiff * xDiff + zDiff * zDiff <= width * width)
                    {
                        for (int y = position.getY() - 1; y <= position.getY() + 1; ++y)
                        {
                            BlockPos blockpos = new BlockPos(x, y, z);
                            Block block = worldIn.getBlockState(blockpos).getBlock();

                            if (block == Blocks.SAND || block == Blocks.SANDSTONE)
                            {
                                worldIn.setBlockState(blockpos, this.packedIce, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
