package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SeaGrassConfig;


public class SeaGrass extends Feature<SeaGrassConfig>
{
	public SeaGrass(Function<Dynamic<?>, ? extends SeaGrassConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, SeaGrassConfig config)
	{
		int i = 0;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);
		BlockPos.Mutable blockpos$Mutable2 = new BlockPos.Mutable(position);

		for (int j = 0; j < config.count; ++j)
		{
			int x = random.nextInt(8) - random.nextInt(8);
			int z = random.nextInt(8) - random.nextInt(8);

			//same as vanilla SeaGrass class but now generates at position we passed in instead of finding the top y value. 
			//We have placement classes for a reason. Features should not be finding their own positions.

			blockpos$Mutable.setPos(position).move(x, 0, z);

			if (world.getBlockState(blockpos$Mutable).getBlock() == Blocks.WATER)
			{
				boolean flag = random.nextDouble() < config.tallProbability;
				BlockState iblockstate = flag ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();
				if (iblockstate.isValidPosition(world, blockpos$Mutable))
				{
					if (flag)
					{
						BlockState iblockstate1 = iblockstate.with(TallSeaGrassBlock.field_208065_c, DoubleBlockHalf.UPPER);
						blockpos$Mutable2.setPos(blockpos$Mutable.up());
						if (world.getBlockState(blockpos$Mutable2).getBlock() == Blocks.WATER)
						{
							world.setBlockState(blockpos$Mutable, iblockstate, 2);
							world.setBlockState(blockpos$Mutable2, iblockstate1, 2);
						}
					}
					else
					{
						world.setBlockState(blockpos$Mutable, iblockstate, 2);
					}

					++i;
				}
			}
		}

		return i > 0;
	}
}