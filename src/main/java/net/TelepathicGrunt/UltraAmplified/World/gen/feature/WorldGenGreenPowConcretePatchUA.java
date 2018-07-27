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
        
        int radius = rand.nextInt(this.maximumRadius - 2) + 2;

        for (int x = position.getX() - radius; x <= position.getX() + radius; ++x)
        {
            for (int z = position.getZ() - radius; z <= position.getZ() + radius; ++z)
            {
                int xDiff = x - position.getX();
                int zDiff = z - position.getZ();

                if (xDiff * xDiff + zDiff * zDiff <= radius * radius)
                {
                    for (int y = position.getY() - 2; y <= position.getY() + 2; ++y)
                    {
                        BlockPos blockpos = new BlockPos(x, y, z);
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