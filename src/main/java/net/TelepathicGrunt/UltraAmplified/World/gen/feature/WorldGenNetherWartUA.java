package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenNetherWartUA extends WorldGenerator
{
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int i = 0; i < 20; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos))
            {
                
                if (worldIn.getBlockState(new BlockPos(blockpos.getX(), blockpos.getY()-1, blockpos.getZ())).getBlock().getDefaultState() == Blocks.SOUL_SAND.getDefaultState())
                {
                    worldIn.setBlockState(blockpos, Blocks.NETHER_WART.getDefaultState(), 2);
                }
            }
        }

        return true;
    }
}
