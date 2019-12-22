package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockChorusFlower;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenChorusPlantUA extends WorldGenerator
{
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
    	//attempts to spawn chorus plant 18 times near given position
        for (int currentCount = 0; currentCount < 18; ++currentCount)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos))
            {
                
                if (worldIn.getBlockState(blockpos.down()).getBlock().getDefaultState() == Blocks.END_STONE.getDefaultState())
                {
                	BlockChorusFlower.generatePlant(worldIn, blockpos, rand, 8);
                }
            }
        }

        return true;
    }
}
