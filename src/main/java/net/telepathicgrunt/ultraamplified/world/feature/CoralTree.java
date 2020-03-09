package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.CoralTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;


public class CoralTree extends CoralTreeFeature
{
	public CoralTree(Function<Dynamic<?>, ? extends NoFeatureConfig> config)
	{
		super(config);
	}


	@Override
	protected boolean func_204624_b(IWorld world, Random random, BlockPos position, BlockState blockState)
	{
		BlockPos positionUp = position.up();
		BlockState blockstate = world.getBlockState(position);
		if ((blockstate.getBlock() == Blocks.WATER || blockstate.getBlock() == Blocks.LAVA || blockstate.isIn(BlockTags.CORALS)) && (world.getBlockState(positionUp).getBlock() == Blocks.WATER || world.getBlockState(positionUp).getBlock() == Blocks.LAVA))
		{
			world.setBlockState(position, blockState, 3);
			if (random.nextFloat() < 0.25F)
			{
				world.setBlockState(positionUp, BlockTags.CORALS.getRandomElement(random).getDefaultState(), 2);
			}
			else if (random.nextFloat() < 0.05F)
			{
				world.setBlockState(positionUp, Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, Integer.valueOf(random.nextInt(4) + 1)), 2);
			}

			for (Direction direction : Direction.Plane.HORIZONTAL)
			{
				if (random.nextFloat() < 0.2F)
				{
					BlockPos blockpos1 = position.offset(direction);
					if (world.getBlockState(blockpos1).getBlock() == Blocks.WATER || world.getBlockState(blockpos1).getBlock() == Blocks.LAVA)
					{
						BlockState blockstate1 = BlockTags.WALL_CORALS.getRandomElement(random).getDefaultState().with(DeadCoralWallFanBlock.FACING, direction);
						world.setBlockState(blockpos1, blockstate1, 2);
					}
				}
			}

			return true;
		}
		else
		{
			return false;
		}
	}
}