package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCactusUA extends WorldGenerator
{
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int i = 0; i < 7; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos))
            {
                int j = 6 + rand.nextInt(rand.nextInt(7) + 2);

                for (int k = 0; k < j; ++k)
                {
                    if (Blocks.CACTUS.canBlockStay(worldIn, blockpos))
                    {
                        worldIn.setBlockState(blockpos.up(k), Blocks.CACTUS.getDefaultState(), 2);
                    }
                }
            }
        }

        return true;
    }
}
