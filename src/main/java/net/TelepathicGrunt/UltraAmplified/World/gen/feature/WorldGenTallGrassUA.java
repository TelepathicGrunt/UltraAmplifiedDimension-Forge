package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTallGrassUA extends WorldGenerator
{
    private final IBlockState tallGrassState;

    public WorldGenTallGrassUA(BlockTallGrass.EnumType p_i45629_1_)
    {
        this.tallGrassState = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, p_i45629_1_);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
        {
            position = position.down();
        }

        BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

        if (worldIn.isAirBlock(blockpos) && Blocks.TALLGRASS.canBlockStay(worldIn, blockpos, this.tallGrassState))
        {
            worldIn.setBlockState(blockpos, this.tallGrassState, 2);
        }

        return true;
    }
}
