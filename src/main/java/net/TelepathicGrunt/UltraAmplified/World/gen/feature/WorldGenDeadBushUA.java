package net.telepathicgrunt.ultraamplified.World.gen.feature;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDeadBushUA extends WorldGenerator
{
	//just normal deadbush class without the moving down to non air block as that causes issues with how the ultra amplified terrain is done 
	//(would cause deadbush to be heavily populated at layers with large gaps between them and barely populating close layers)
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

        if (worldIn.isAirBlock(blockpos) && Blocks.DEADBUSH.canBlockStay(worldIn, blockpos, Blocks.DEADBUSH.getDefaultState()))
        {
            worldIn.setBlockState(blockpos, Blocks.DEADBUSH.getDefaultState(), 2);
        }

        return true;
    }
}
