package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;


public class ContainLiquidForOceans extends Feature<NoFeatureConfig>
{
	public ContainLiquidForOceans(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private final static BlockState ICE = Blocks.ICE.getDefaultState();

	private final static BlockState[] DEAD_CORAL_ARRAY = { Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultState() };


	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, NoFeatureConfig configBlock)
	{

		boolean notContainedFlag;
		BlockState currentblock;
		BlockState blockAbove;
		Biome oceanBiome = null;
		boolean bordersOcean = false;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX(), 0, position.getZ());  //set y to 0
		BlockPos.Mutable blockpos$MutableAbove = new BlockPos.Mutable(blockpos$Mutable);

		//checks to see if there is an ocean biome in this chunk
		//breaks out of nested loop if ocean if found so oceanBiome holds the ocean
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				//only check along chunk edges for better performance
				if (!(z == 0 || z == 16 || x == 0 || x == 16))
				{
					continue;
				}

				blockpos$Mutable.setPos(position.getX() + x, 0, position.getZ() + z);
				oceanBiome = world.getBiome(blockpos$Mutable);
				if (BiomeGenHelper.isOcean(oceanBiome))
				{
					bordersOcean = true;
					x = 16;
					break;
				}
			}
		}

		//does not do anything if there is no ocean biome
		if (!bordersOcean)
		{
			return false;
		}

		//ocean biome was found and thus, is not null. Can safely contain all water in this chunk
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				boolean useCoralTop = oceanBiome.getSurfaceBuilderConfig().getTop() == DEAD_CORAL_ARRAY[0];
				boolean useCoralBottom = oceanBiome.getSurfaceBuilderConfig().getTop() == DEAD_CORAL_ARRAY[0];

				for (int y = 256; y > ConfigUA.seaLevel - 10; y--)
				{

					blockpos$Mutable.setPos(position.getX() + x, y, position.getZ() + z);
					currentblock = world.getBlockState(blockpos$Mutable);

					//move down until we hit a liquid block
					while (currentblock.getFluidState().isEmpty() && blockpos$Mutable.getY() > 10)
					{
						blockpos$Mutable.move(Direction.DOWN);
						currentblock = world.getBlockState(blockpos$Mutable);
					}
					if (blockpos$Mutable.getY() == 10)
					{
						continue;
					}

					//y value is now fully set for rest of code
					notContainedFlag = false;

					/*
					 * //must be solid all around even diagonally for(int x2 = -1; x2 < 2; x2++) { for(int z2 = -1; z2 < 2; z2++) {
					 * 
					 * material = world.getBlockState(blockpos$Mutable.west(x2).north(z2)).getMaterial();
					 * 
					 * if(!material.isSolid() && material != Material.WATER ) { notContainedFlag = true; } } }
					 */

					//Adjacent blocks must be solid    
					for (Direction face : Direction.Plane.HORIZONTAL)
					{
						currentblock = world.getBlockState(blockpos$Mutable.offset(face));

						//detects air, snow, etc and ignores Ice as ice is not solid and has a fluid state
						if (currentblock != ICE && !currentblock.isSolid() && currentblock.getFluidState().isEmpty())
						{
							notContainedFlag = true;
						}
					}

					
					blockpos$MutableAbove.setPos(blockpos$Mutable).move(Direction.UP);
					if (notContainedFlag)
					{
						if (y < 256)
						{
							blockAbove = world.getBlockState(blockpos$MutableAbove);

							if (blockAbove.isSolid() || !blockAbove.getFluidState().isEmpty())
							{

								//if above is solid or water, place second config block
								world.setBlockState(blockpos$Mutable, oceanBiome.getSurfaceBuilderConfig().getUnder(), 2);
							}

							//place first config block if no solid block above and below
							else
							{
								//if config top block is dead coral, randomly chooses any dead coral block to place
								if (useCoralTop)
								{
									world.setBlockState(blockpos$Mutable, DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
								}
								else
								{
									world.setBlockState(blockpos$Mutable, oceanBiome.getSurfaceBuilderConfig().getTop(), 2);
								}
							}
						}

						//place first config block if too high
						//if config top block is dead coral, randomly chooses any dead coral block to place
						else if (useCoralTop)
						{
							world.setBlockState(blockpos$Mutable, DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
						}
						else
						{
							world.setBlockState(blockpos$Mutable, oceanBiome.getSurfaceBuilderConfig().getTop(), 2);
						}
					}
					else
					{
						//water block is contained 

						blockAbove = world.getBlockState(blockpos$MutableAbove);

						//if above is middle block, replace above block with third config block so middle block (sand/gravel) cannot fall.
						if (blockAbove == oceanBiome.getSurfaceBuilderConfig().getUnder())
						{
							if (useCoralBottom)
							{
								world.setBlockState(blockpos$MutableAbove, DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], 2);
							}
							else
							{
								world.setBlockState(blockpos$MutableAbove, ((SurfaceBuilderConfig) oceanBiome.getSurfaceBuilderConfig()).getUnderWaterMaterial(), 2);
							}
						}
					}

				}
			}
		}
		return true;

	}
}