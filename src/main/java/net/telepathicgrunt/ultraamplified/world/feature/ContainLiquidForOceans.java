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
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;


public class ContainLiquidForOceans extends Feature<NoFeatureConfig>
{
	public ContainLiquidForOceans(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private final static BlockState ICE = Blocks.ICE.getDefaultState();
	private final static BlockState SNOW = Blocks.SNOW.getDefaultState();

	private final static BlockState[] DEAD_CORAL_ARRAY = { Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultState() };


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, NoFeatureConfig configBlock)
	{
		//checks to see if there is an ocean biome in this chunk
		//breaks out of nested loop if ocean if found so oceanBiome holds the ocean
		Biome oceanBiome = getOceanInChunk(world, position);

		//does not do anything if there is no ocean biome
		if (oceanBiome == null)
		{
			return false;
		}

		boolean containedFlag;
		BlockState currentblock;
		BlockState blockAbove;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX(), 0, position.getZ());  //set y to 0
		BlockPos.Mutable blockpos$MutableAbove = new BlockPos.Mutable(blockpos$Mutable);

		//ocean biome was found and thus, is not null. Can safely contain all water in this chunk
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				boolean useCoralTop = oceanBiome.getSurfaceBuilderConfig().getTop() == DEAD_CORAL_ARRAY[0];
				boolean useCoralBottom = oceanBiome.getSurfaceBuilderConfig().getTop() == DEAD_CORAL_ARRAY[0];
				blockpos$Mutable.setPos(position.getX() + x, 256, position.getZ() + z);

				for (; blockpos$Mutable.getY() >= UltraAmplified.UATerrainConfig.seaLevel.get(); blockpos$Mutable.move(Direction.DOWN))
				{

					currentblock = world.getBlockState(blockpos$Mutable);

					//move down until we hit a liquid block
					while (currentblock.getFluidState().isEmpty() && blockpos$Mutable.getY() >= UltraAmplified.UATerrainConfig.seaLevel.get())
					{
						blockpos$Mutable.move(Direction.DOWN);
						currentblock = world.getBlockState(blockpos$Mutable);
					}

					//too low now, break out of the loop and move to next xz coordinate
					if (blockpos$Mutable.getY() < UltraAmplified.UATerrainConfig.seaLevel.get())
					{
						break;
					}
					//y value is now fully set for rest of code

					/*
					 * // Keep this here in case we want to change behavior later // Must be solid all around even diagonally for(int x2 =
					 * -1; x2 < 2; x2++) { for(int z2 = -1; z2 < 2; z2++) {
					 * 
					 * material = world.getBlockState(blockpos$Mutable.west(x2).north(z2)).getMaterial();
					 * 
					 * if(!material.isSolid() && material != Material.WATER ) { notContainedFlag = true; } } }
					 */

					//Adjacent blocks must be solid    
					containedFlag = true;
					for (Direction face : Direction.Plane.HORIZONTAL)
					{
						currentblock = world.getBlockState(blockpos$Mutable.offset(face));

						// If the block is snow or not solid without liquid, set contains to false.
						// Yes, snow layers are considered solid and need a second check.
						if ((!currentblock.isSolid() && currentblock.getFluidState().isEmpty() && currentblock != ICE) || currentblock == SNOW)
						{
							containedFlag = false;
							break;
						}
					}

					blockpos$MutableAbove.setPos(blockpos$Mutable).move(Direction.UP);

					if (containedFlag)
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
					else
					{
						//water is not contained
						if (blockpos$Mutable.getY() < 256)
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

				}
			}
		}
		return true;

	}


	/**
	 * Gets the first ocean biome found within the edges of the chunk.
	 */
	private Biome getOceanInChunk(IWorld world, BlockPos originalPosition)
	{
		Biome biomeAtLocation = null;
		BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable();

		//checks to see if there is an ocean biome in this chunk
		//breaks out of nested loop if ocean if found so oceanBiome holds the ocean
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				//only check along chunk edges for better performance
				if ((x != 0 && x != 15) && (z != 0 && z != 15))
				{
					continue;
				}

				mutableBlockPos.setPos(originalPosition.getX() + x, 0, originalPosition.getZ() + z);
				biomeAtLocation = world.getBiome(mutableBlockPos);
				if (BiomeGenHelper.isOcean(biomeAtLocation))
				{
					return biomeAtLocation;
				}
			}
		}

		return null;
	}
}