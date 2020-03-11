package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.world.biome.UABiomes;


public class ContainUndergroundLiquids extends Feature<NoFeatureConfig>
{
	public ContainUndergroundLiquids(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	// Used to keep track of what block to use to fill in certain air/liquids
	protected BlockState replacementBlock = Blocks.STONE.getDefaultState();
	protected static final BlockState STONE = Blocks.STONE.getDefaultState();

	private static Map<Biome, BlockState> fillerBiomeMap;


	/**
	 * Have to make this map in UltraAmplified setup method since the biomes needs to be initialized first
	 */
	public static void setFillerMap()
	{
		if (fillerBiomeMap == null)
		{
			fillerBiomeMap = new HashMap<Biome, BlockState>();

			fillerBiomeMap.put(UABiomes.NETHERLAND, Blocks.NETHERRACK.getDefaultState());
			fillerBiomeMap.put(UABiomes.ICED_TERRAIN, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(UABiomes.ICE_SPIKES, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(UABiomes.DEEP_FROZEN_OCEAN, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(UABiomes.FROZEN_OCEAN, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(UABiomes.BARREN_END_FIELD, Blocks.END_STONE.getDefaultState());
			fillerBiomeMap.put(UABiomes.END_FIELD, Blocks.END_STONE.getDefaultState());
		}
	}


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, NoFeatureConfig configBlock)
	{
		BlockState currentblock;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX(), 0, position.getZ());  //set y to 0

		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				blockpos$Mutable.setPos(position.getX() + x, 61, position.getZ() + z);
				for (; blockpos$Mutable.getY() > 10; blockpos$Mutable.move(Direction.DOWN))
				{

					currentblock = world.getBlockState(blockpos$Mutable);
					

					// move down until we hit a water filled block
					while (currentblock.getFluidState().isEmpty() && 
							!currentblock.getFluidState().isTagged(FluidTags.WATER) && 
							blockpos$Mutable.getY() > 10)
					{
						currentblock = world.getBlockState(blockpos$Mutable.move(Direction.DOWN));
					}

					//if too low, break and go to next xz coordinate
					if (blockpos$Mutable.getY() <= 10)
					{
						break;
					}

					// y value is now fully set for rest of code
					// checks to see if we are touching an air block
					for (Direction face : Direction.Plane.HORIZONTAL)
					{
						blockpos$Mutable.move(face);
						currentblock = world.getBlockState(blockpos$Mutable);
						if (currentblock.getMaterial() == Material.AIR)
						{
							//grabs what block to use based on what biome we are in
							replacementBlock = fillerBiomeMap.get(world.getBiome(blockpos$Mutable));
							if (replacementBlock == null)
							{
								replacementBlock = STONE;
							}

							world.setBlockState(blockpos$Mutable, replacementBlock, 2);
						}
						blockpos$Mutable.move(face.getOpposite());
					}
					
					for (Direction face : Direction.Plane.VERTICAL)
					{
						blockpos$Mutable.move(face);
						currentblock = world.getBlockState(blockpos$Mutable);
						if (currentblock.getMaterial() == Material.AIR)
						{
							//grabs what block to use based on what biome we are in
							replacementBlock = fillerBiomeMap.get(world.getBiome(blockpos$Mutable));
							if (replacementBlock == null)
							{
								replacementBlock = STONE;
							}

							world.setBlockState(blockpos$Mutable, replacementBlock, 2);
						}
						blockpos$Mutable.move(face.getOpposite());
					}
					
					
					//old code that looks for air spot touching water
//
//					// move down until we hit an cave air block
//					while (currentblock.getBlock() != Blocks.CAVE_AIR && blockpos$Mutable.getY() > 10)
//					{
//						currentblock = world.getBlockState(blockpos$Mutable.move(Direction.DOWN));
//					}
//
//					//if too low, break and go to next xz coordinate
//					if (blockpos$Mutable.getY() <= 10)
//					{
//						break;
//					}
//
//					// y value is now fully set for rest of code
//					// checks to see if we are touching a liquid block
//					notContainedFlag = false;
//
//					for (Direction face : Direction.Plane.HORIZONTAL)
//					{
//
//						currentblock = world.getBlockState(blockpos$Mutable.offset(face));
//						if (!currentblock.getFluidState().isEmpty())
//						{
//							notContainedFlag = true;
//						}
//					}
//
//					currentblock = world.getBlockState(blockpos$Mutable.up());
//					if (!currentblock.getFluidState().isEmpty())
//					{
//						notContainedFlag = true;
//					}
//
//					currentblock = world.getBlockState(blockpos$Mutable.down());
//					if (blockpos$Mutable.down().getY() > 10 && !currentblock.getFluidState().isEmpty())
//					{
//						notContainedFlag = true;
//					}
//
//					// this air block is touching liquid, time to make it solid
//					if (notContainedFlag)
//					{
//						//grabs what block to use based on what biome we are in
//						replacementBlock = fillerBiomeMap.get(world.getBiome(blockpos$Mutable));
//						if (replacementBlock == null)
//						{
//							replacementBlock = STONE;
//						}
//
//						world.setBlockState(blockpos$Mutable, replacementBlock, 2);
//					}
				}
			}
		}
		return true;

	}
}