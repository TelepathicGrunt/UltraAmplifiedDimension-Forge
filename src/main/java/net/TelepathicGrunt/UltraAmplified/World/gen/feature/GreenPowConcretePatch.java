package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class GreenPowConcretePatch extends Feature<NoFeatureConfig> {
	  
    private final IBlockState greenConcretePowder = Blocks.GREEN_CONCRETE_POWDER.getDefaultState();
    private final int maximumRadius = 6;


    public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) 
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

                        if (block == Blocks.DIRT || block == Blocks.GRASS_BLOCK)
                        {
                            worldIn.setBlockState(blockpos, this.greenConcretePowder, 2);
                        }
                    }
                }
            }
        }

        return true;
    
    }
}