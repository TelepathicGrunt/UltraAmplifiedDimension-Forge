package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.UAConfig;
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
    private final boolean stackable;

    public WorldGenBoulderUA(int startRadiusIn, boolean stackable)
    {
        super(false);
        this.block = Blocks.MOSSY_COBBLESTONE;
        this.block2 = Blocks.COBBLESTONE;
        this.block3 = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE);
        this.block4 = Blocks.COAL_ORE;
        this.block5 = Blocks.IRON_ORE;
        this.block6 = Blocks.DIAMOND_ORE;
        this.startRadius = startRadiusIn;
        this.stackable = stackable;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        while (true)
        {
            label0:
            { 
        		if (position.getY() <= 6 || position.getY() >= 250)
                {
                    return false;
                }
            
            
                if (worldIn.isAirBlock(position.down()))
                {
                    break label0;
                }

                Block block = worldIn.getBlockState(position.down()).getBlock();

                if(this.stackable) 
                {
	                if (block != Blocks.STONE && block != Blocks.COBBLESTONE && block != Blocks.MOSSY_COBBLESTONE && block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.COAL_ORE && block != Blocks.IRON_ORE && block != Blocks.DIAMOND_ORE)
	                {
	                    break label0;
	                }
                }
                else 
                {
                	if (block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.COAL_ORE)
	                {
	                    break label0;
	                }
                }
                

                int i1 = this.startRadius;

                for (int i = 0; i1 >= 0 && i < 3; ++i)
                {
                    int j = i1 + rand.nextInt(2);
                    int k = i1 + rand.nextInt(2);
                    int l = i1 + rand.nextInt(2);
                    float f = (float)(j + k + l) * 0.333F + 0.5F;

                    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(position.add(-j, -k, -l), position.add(j, k, l)))
                    {
                        if (blockpos.distanceSq(position) <= (double)(f * f))
                        {
                        	
                        	int randomChance = rand.nextInt(1400);
                        	
                        	if(UAConfig.oreAndFeatures.mainOresOptions.diamondOreSpawnrate != 0 && randomChance == 0) {
                        		worldIn.setBlockState(blockpos, this.block6.getDefaultState(), 4);
                        	}
                        	else if(UAConfig.oreAndFeatures.mainOresOptions.ironOreSpawnrate != 0 && randomChance <= 40){
                        		worldIn.setBlockState(blockpos, this.block5.getDefaultState(), 4);
                        	}
                        	else if(UAConfig.oreAndFeatures.mainOresOptions.coalOreSpawnrate != 0 && randomChance <= 100){
                        		worldIn.setBlockState(blockpos, this.block4.getDefaultState(), 4);
                        	}
                        	else if(randomChance <= 400){
                        		worldIn.setBlockState(blockpos, this.block3, 4);
                        	}
                        	else if(randomChance <= 700){
                        		worldIn.setBlockState(blockpos, this.block2.getDefaultState(), 4);
                        	}
                        	else {
                        		worldIn.setBlockState(blockpos, this.block.getDefaultState(), 4);
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
