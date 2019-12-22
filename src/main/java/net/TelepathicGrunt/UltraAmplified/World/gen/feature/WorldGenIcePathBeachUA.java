package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenIcePathBeachUA extends WorldGenerator
{
    private final Block block = Blocks.PACKED_ICE;
    private final int basePathWidth;

    //creates a patch of packed ice for cold beach biome only
    public WorldGenIcePathBeachUA(int basePathWidthIn)
    {
        this.basePathWidth = basePathWidthIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
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
                                worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
