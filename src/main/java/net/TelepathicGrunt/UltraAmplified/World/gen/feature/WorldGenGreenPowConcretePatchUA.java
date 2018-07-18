package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockConcretePowder;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGreenPowConcretePatchUA extends WorldGenerator
{
    private final IBlockState concretePowder;
    private final int maximumRadius;

    public WorldGenGreenPowConcretePatchUA()
    {
        this.concretePowder = Blocks.CONCRETE_POWDER.getDefaultState().withProperty(BlockConcretePowder.COLOR, EnumDyeColor.GREEN);
        this.maximumRadius = 6;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        
        int i = rand.nextInt(this.maximumRadius - 2) + 2;
        int j = 2;

        for (int k = position.getX() - i; k <= position.getX() + i; ++k)
        {
            for (int l = position.getZ() - i; l <= position.getZ() + i; ++l)
            {
                int i1 = k - position.getX();
                int j1 = l - position.getZ();

                if (i1 * i1 + j1 * j1 <= i * i)
                {
                    for (int k1 = position.getY() - 2; k1 <= position.getY() + 2; ++k1)
                    {
                        BlockPos blockpos = new BlockPos(k, k1, l);
                        Block block = worldIn.getBlockState(blockpos).getBlock();

                        if (block == Blocks.DIRT || block == Blocks.GRASS)
                        {
                            worldIn.setBlockState(blockpos, this.concretePowder, 2);
                        }
                    }
                }
            }
        }

        return true;
    
    }
}