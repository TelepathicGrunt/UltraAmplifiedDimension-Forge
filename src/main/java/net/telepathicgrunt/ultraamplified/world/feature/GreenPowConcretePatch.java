package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class GreenPowConcretePatch extends Feature<NoFeatureConfig> {
	  
    public GreenPowConcretePatch(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory) {
		super(configFactory);
	}


	private final BlockState greenConcretePowder = Blocks.GREEN_CONCRETE_POWDER.getDefaultState();
    private final int maximumRadius = 6;


    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) 
    {
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);
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
                    	blockpos$Mutable.setPos(x, y, z);
                        Block block = world.getBlockState(blockpos$Mutable).getBlock();

                        if (block == Blocks.DIRT || block == Blocks.GRASS_BLOCK)
                        {
                            world.setBlockState(blockpos$Mutable, this.greenConcretePowder, 2);
                        }
                    }
                }
            }
        }

        return true;
    
    }
}