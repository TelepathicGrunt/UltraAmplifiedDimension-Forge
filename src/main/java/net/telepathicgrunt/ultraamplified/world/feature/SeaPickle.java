package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.CountConfig;


public class SeaPickle extends Feature<CountConfig>
{
	public SeaPickle(Function<Dynamic<?>, ? extends CountConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public boolean place(IWorld world, ChunkGenerator<?> generator, Random random, BlockPos position, CountConfig configCount)
	{
		int howManyPickleGenerated = 0;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

		for (int count = 0; count < configCount.count; ++count)
		{
			int x = random.nextInt(8) - random.nextInt(8);
			int z = random.nextInt(8) - random.nextInt(8);

			//same as vanilla SeaPickle class but now generates at position we passed in instead of finding the top y value. 
			//We have placement classes for a reason. Features should not be finding their own positions.

			blockpos$Mutable.setPos(position).move(x, 0, z);
			BlockState pickle = Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, Integer.valueOf(random.nextInt(4) + 1));

			if (world.getBlockState(blockpos$Mutable).getBlock() == Blocks.WATER && pickle.isValidPosition(world, blockpos$Mutable))
			{
				world.setBlockState(blockpos$Mutable, pickle, 2);
				++howManyPickleGenerated;
			}
		}

		return howManyPickleGenerated > 0;
	}
}