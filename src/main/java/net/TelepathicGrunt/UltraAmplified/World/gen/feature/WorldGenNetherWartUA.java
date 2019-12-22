package net.telepathicgrunt.ultraamplified.World.gen.feature;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenNetherWartUA extends WorldGenerator
{
	//generates netherwart by using similar code for bush, cactus, etc
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int attempts = 0; attempts < 20; ++attempts)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos))
            {
                //generates netherwart if block below is soul sand
                if (worldIn.getBlockState(blockpos.down()).getBlock().getDefaultState() == Blocks.SOUL_SAND.getDefaultState())
                {
                    worldIn.setBlockState(blockpos, Blocks.NETHER_WART.getDefaultState(), 2);
                }
            }
        }

        return true;
    }
}
