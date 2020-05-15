package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;


public class BlueIceWaterfall extends Feature<NoFeatureConfig>
{

	public BlueIceWaterfall(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private static final BlockState BLUE_ICE = Blocks.BLUE_ICE.getDefaultState();
	private static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
	private static final BlockState SNOW = Blocks.SNOW.getDefaultState();
	private static final BlockState ICE = Blocks.ICE.getDefaultState();
	private static final BlockState AIR = Blocks.AIR.getDefaultState();

	protected static final Set<Block> acceptableBlocks = ImmutableSet.of(Blocks.ICE, Blocks.SNOW_BLOCK, Blocks.PACKED_ICE);


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig fluidConfig)
	{

		//creates a waterfall of blue ice that has a puddle at bottom
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

		if (!acceptableBlocks.contains(world.getBlockState(blockpos$Mutable.up()).getBlock()))
		{
			return false;
		}
		else
		{
			//checks if we are in the side of a wall with air exposed on one side

			int numberOfSolidSides = 0;
			int neededNumberOfSides = 0;

			if (!acceptableBlocks.contains(world.getBlockState(blockpos$Mutable.down()).getBlock()))
			{
				neededNumberOfSides = 4;
			}
			else
			{
				neededNumberOfSides = 3;
			}

			Direction emptySpot = Direction.NORTH;

			for (Direction face : Direction.Plane.HORIZONTAL)
			{

				if (acceptableBlocks.contains(world.getBlockState(blockpos$Mutable.offset(face)).getBlock()))
				{
					++numberOfSolidSides;
				}
				else
				{
					emptySpot = face;
				}
			}

			//position valid. begin making ice waterfall
			if (numberOfSolidSides == neededNumberOfSides)
			{

				//initial starting point of icefall
				world.setBlockState(blockpos$Mutable, BLUE_ICE, 2);

				//in wall, offset to out of wall
				if (numberOfSolidSides == 3)
				{
					//set what direction the open side of the wall is
					blockpos$Mutable.move(emptySpot);
					world.setBlockState(blockpos$Mutable, BLUE_ICE, 2);
				}

				//places blue ice downward until it hit solid block
				while (true)
				{
					if (blockpos$Mutable.getY() <= 1)
					{
						return false;
					}

					blockpos$Mutable.move(Direction.DOWN); //move down to check below
					if (world.getBlockState(blockpos$Mutable).getMaterial() == Material.AIR || world.getBlockState(blockpos$Mutable) == BLUE_ICE || world.getBlockState(blockpos$Mutable) == SNOW_BLOCK)
					{
						world.setBlockState(blockpos$Mutable, BLUE_ICE, 2);
						continue; //restart loop to keep moving downward
					}

					blockpos$Mutable.move(Direction.UP); //move back up as downward is blocked off
					boolean spotFound = false;

					//goes around ledge
					for (Direction face : Direction.Plane.HORIZONTAL)
					{

						if ((world.getBlockState(blockpos$Mutable.offset(face)).getMaterial() == Material.AIR || world.getBlockState(blockpos$Mutable.offset(face)) == BLUE_ICE) && (world.getBlockState(blockpos$Mutable.down().offset(face)).getMaterial() == Material.AIR || world.getBlockState(blockpos$Mutable.down().offset(face)) == BLUE_ICE))
						{
							blockpos$Mutable.move(emptySpot);
							world.setBlockState(blockpos$Mutable, BLUE_ICE, 2);
							blockpos$Mutable.move(Direction.DOWN);
							world.setBlockState(blockpos$Mutable, BLUE_ICE, 2);
							spotFound = true;

							if (blockpos$Mutable.getY() <= 1)
							{
								return false;
							}
							else
							{
								break;
							}
						}
					}

					if (!spotFound)
					{
						break;
					}

				}

				//creates blue ice puddle at bottom
				int width = rand.nextInt(2) + 2;
				for (int y = blockpos$Mutable.getY() - 1; y < blockpos$Mutable.getY() + 1; ++y)
				{
					for (int x = -width; x <= width; ++x)
					{
						for (int z = -width; z <= width; ++z)
						{
							if (x * x + z * z <= width * width)
							{
								if (y > 1 && y < world.getMaxHeight())
								{

									BlockPos blockpos = new BlockPos(x + blockpos$Mutable.getX(), y, z + blockpos$Mutable.getZ());
									BlockState block = world.getBlockState(blockpos);

									//replace solid and liquid blocks
									if (block.isSolid() || !block.getFluidState().isEmpty() || block == ICE)
									{
										world.setBlockState(blockpos, BLUE_ICE, 2);
									}
								}
								else
								{
									break;
								}

							}
						}
					}

					width++;
				}

				//remove thin snow above the highest blue ice puddle
				for (int x = -width; x <= width; ++x)
				{
					for (int z = -width; z <= width; ++z)
					{
						if (x * x + z * z <= width * width)
						{
							BlockPos blockpos = new BlockPos(x + blockpos$Mutable.getX(), blockpos$Mutable.getY() + 1, z + blockpos$Mutable.getZ());
							BlockState block = world.getBlockState(blockpos);

							if (block == SNOW)
							{
								world.setBlockState(blockpos, AIR, 2);
							}
						}
					}
				}

			}
			return true;
		}
	}

}