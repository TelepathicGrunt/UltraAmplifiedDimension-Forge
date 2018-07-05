package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBoulderUA extends WorldGenerator
{
    private final Block block;
    private final Block block2;
    private final IBlockState block3;
    private final Block block4;
    private final Block block5;
    private final Block block6;
    private final int startRadius;

    public WorldGenBoulderUA(int startRadiusIn)
    {
        super(false);
        this.block = Blocks.MOSSY_COBBLESTONE;
        this.block2 = Blocks.COBBLESTONE;
        this.block3 = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE);
        this.block4 = Blocks.COAL_ORE;
        this.block5 = Blocks.IRON_ORE;
        this.block6 = Blocks.DIAMOND_ORE;
        this.startRadius = startRadiusIn+3;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        while (true)
        {
            label0:
            {
                if (position.getY() > 3)
                {
                    if (worldIn.isAirBlock(position.down()))
                    {
                        break label0;
                    }

                    Block block = worldIn.getBlockState(position.down()).getBlock();

                    if (block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.STONE)
                    {
                        break label0;
                    }
                }

                if (position.getY() <= 3)
                {
                    return false;
                }

                int i1 = this.startRadius;

                for (int i = 0; i1 >= 0 && i < 3; ++i)
                {
                    int j = i1 + rand.nextInt(2);
                    int k = i1 + rand.nextInt(2);
                    int l = i1 + rand.nextInt(2);
                    float f = (float)(j + k + l) * 0.333F + 0.5F;

                    for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-j, -k, -l), position.add(j, k, l)))
                    {
                        if (blockpos.distanceSq(position) <= (double)(f * f))
                        {
                        	
                        	int randomChance = rand.nextInt(700);
                        	
                        	if(randomChance >= 350) {
                        		worldIn.setBlockState(blockpos, this.block.getDefaultState(), 4);
                        	}
                        	else if(randomChance >= 200){
                        		worldIn.setBlockState(blockpos, this.block2.getDefaultState(), 4);
                        	}
                        	else if(randomChance >= 100){
                        		worldIn.setBlockState(blockpos, this.block3, 4);
                        	}
                        	else if(randomChance >= 40){
                        		worldIn.setBlockState(blockpos, this.block4.getDefaultState(), 4);
                        	}
                        	else if(randomChance >= 1){
                        		worldIn.setBlockState(blockpos, this.block5.getDefaultState(), 4);
                        	}
                        	else {
                        		worldIn.setBlockState(blockpos, this.block6.getDefaultState(), 4);
                        	}
                        }
                    }

                    position = position.add(-(i1 + 1) + rand.nextInt(2 + i1 * 2), 0 - rand.nextInt(2), -(i1 + 1) + rand.nextInt(2 + i1 * 2));
                }

                return true;
            }
            position = position.down();
        }
    }
}
