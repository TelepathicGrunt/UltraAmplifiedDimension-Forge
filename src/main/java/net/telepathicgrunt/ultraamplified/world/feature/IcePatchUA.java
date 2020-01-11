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

public class IcePatchUA extends Feature<NoFeatureConfig> 
{
    public IcePatchUA(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory) {
		super(configFactory);
	}

	private final BlockState packedIce = Blocks.PACKED_ICE.getDefaultState();
    private final int basePathWidth = 3;

    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) 
    {
        while (world.isAirBlock(position) && position.getY() > 2)
        {
            position = position.down();
        }

        if (world.getBlockState(position).getBlock() != Blocks.SAND)
        {
            return false;
        }
        else
        {
            int width = rand.nextInt(this.basePathWidth - 2) + 2;

            for (int x = position.getX() - width; x <= position.getX() + width; ++x)
            {
                for (int z = position.getZ() - width; z <= position.getZ() + width; ++z)
                {
                    int xDiff = x - position.getX();
                    int zDiff = z - position.getZ();

                    if (xDiff * xDiff + zDiff * zDiff <= width * width)
                    {
                        for (int y = position.getY() - 1; y <= position.getY() + 1; ++y)
                        {
                            BlockPos blockpos = new BlockPos(x, y, z);
                            Block block = world.getBlockState(blockpos).getBlock();

                            if (block == Blocks.SAND || block == Blocks.SANDSTONE)
                            {
                                world.setBlockState(blockpos, this.packedIce, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
