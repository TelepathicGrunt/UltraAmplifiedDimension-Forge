package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.blocks.CactusBodyBlockUA;
import net.telepathicgrunt.ultraamplified.blocks.CactusCornerBlockUA;
import net.telepathicgrunt.ultraamplified.blocks.CactusMainBlockUA;
import net.telepathicgrunt.ultraamplified.blocks.UABlocks;


public class BetterCactus extends Feature<NoFeatureConfig>
{

	public BetterCactus(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private final int height = 9;


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> p_212245_2_, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
	{
		//randomly set this cactus to a random spot. (thus passed in position must be the corner of the 4 loaded chunks)
		BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

		if (world.isAirBlock(blockpos) && world.getBlockState(blockpos.down()).isIn(BlockTags.SAND))
		{

			//gets height with some variations
			//then gets left and right side between 2 and maximum height - 3
			//then finds the direction that the cactus will be facing
			int maxHeight = height + rand.nextInt(2);
			int frontSideHeight = 2 + rand.nextInt(maxHeight - 4);
			int backSideHeight = 2 + rand.nextInt(maxHeight - 4);
			Direction cactusFacing = Direction.byHorizontalIndex(rand.nextInt(4));

			//create cactus from ground up
			for (int currentHeight = 0; currentHeight < maxHeight && world.isAirBlock(blockpos.up(currentHeight)); currentHeight++)
			{
				if (blockpos.up(currentHeight).getY() <= 254 && (currentHeight == frontSideHeight || currentHeight == backSideHeight))
				{
					//will make at least one branch

					//finds what the center should be
					if (frontSideHeight == backSideHeight)
					{
						world.setBlockState(blockpos.up(currentHeight), UABlocks.CACTUSBODYBLOCKUA.get().getDefaultState().with(CactusBodyBlockUA.FACING, cactusFacing), 18);
					}
					else
					{
						world.setBlockState(blockpos.up(currentHeight), UABlocks.CACTUSCORNERBLOCKUA.get().getDefaultState().with(CactusCornerBlockUA.FACING, currentHeight == frontSideHeight ? cactusFacing.getOpposite() : cactusFacing), 18);
					}

					//create the branches off of cactus
					if (currentHeight == frontSideHeight)
					{
						createBranch(world, rand, blockpos.up(currentHeight), cactusFacing, rand.nextInt(maxHeight - frontSideHeight - 2) + 2);
					}
					if (currentHeight == backSideHeight)
					{
						createBranch(world, rand, blockpos.up(currentHeight), cactusFacing.getOpposite(), rand.nextInt(maxHeight - backSideHeight - 2) + 2);
					}

				}
				else
				{
					//places normal vertical cactus
					world.setBlockState(blockpos.up(currentHeight), UABlocks.CACTUSMAINBLOCKUA.get().getDefaultState().with(CactusMainBlockUA.FACING, Direction.UP), 18);
				}
			}

		}

		//cactus finished generating
		return true;
	}


	/**
	 * Will create branch but will stop early if any spot along the way is not an air block
	 * 
	 * @param world           - world to check and place blocks in
	 * @param rand            - rng
	 * @param position        - position of center of cactus that branch is coming off of
	 * @param branchDirection - direction that the branch will go
	 */
	private void createBranch(IWorld world, Random rand, BlockPos position, Direction branchDirection, int maxHeightUp)
	{

		//horizontal part of branch first
		position = position.offset(branchDirection);
		if (world.isAirBlock(position))
		{
			world.setBlockState(position, UABlocks.CACTUSMAINBLOCKUA.get().getDefaultState().with(CactusMainBlockUA.FACING, branchDirection), 18);
		}
		else
		{
			return;
		}

		//corner
		position = position.offset(branchDirection);
		if (world.isAirBlock(position))
		{
			world.setBlockState(position, UABlocks.CACTUSCORNERBLOCKUA.get().getDefaultState().with(CactusCornerBlockUA.FACING, branchDirection), 18);
		}
		else
		{
			return;
		}

		//upward part of branch
		for (int currentHeight = 1; currentHeight < maxHeightUp && position.up(currentHeight).getY() <= world.getMaxHeight(); currentHeight++)
		{
			if (world.isAirBlock(position.up(currentHeight)))
			{
				world.setBlockState(position.up(currentHeight), UABlocks.CACTUSMAINBLOCKUA.get().getDefaultState().with(CactusMainBlockUA.FACING, Direction.UP), 18);

			}
			else
			{
				return;
			}
		}

	}
}
