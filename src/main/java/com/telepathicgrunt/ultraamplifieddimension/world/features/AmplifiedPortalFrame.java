package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;


public class AmplifiedPortalFrame extends Feature<NoFeatureConfig>
{

	private static final BlockState POLISHED_GRANITE = Blocks.POLISHED_GRANITE.getDefaultState();
	private static final BlockState POLISHED_ANDESITE_SLAB_TOP = Blocks.POLISHED_ANDESITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
	private static final BlockState POLISHED_ANDESITE_SLAB_BOTTOM = Blocks.POLISHED_ANDESITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);


	public AmplifiedPortalFrame()
	{
		super(NoFeatureConfig.field_236558_a_);
	}


	//need to be made due to extending feature
	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
	{
		return false;
	}


	//is called in AmplifiedPortalBehavior which doesn't have a chunk generator passed in
	public boolean generate(ISeedReader world, Random rand, BlockPos pos, NoFeatureConfig config)
	{
		//7x7 flooring around bottom of frame
		for (int x = -3; x <= 3; x++)
		{
			for (int z = -3; z <= 3; z++)
			{
				world.setBlockState(pos.add(x, -1, z), POLISHED_GRANITE, 3);
			}
		}

		//bottom of portal frame
		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				if (Math.abs(x * z) == 1)
				{
					world.setBlockState(pos.add(x, 0, z), POLISHED_GRANITE, 3);
				}
				else
				{
					//sets slab but also waterlogs it if block it replaces is water based
					world.setBlockState(pos.add(x, 0, z), POLISHED_ANDESITE_SLAB_BOTTOM.with(SlabBlock.WATERLOGGED, world.getBlockState(pos.add(x, 0, z)).getMaterial() == Material.WATER), 3);
				}
			}
		}

		//the portal itself
		world.setBlockState(pos.add(0, 1, 0), UADBlocks.AMPLIFIEDPORTAL.get().getDefaultState(), 3);

		//top of portal frame
		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				if (Math.abs(x * z) == 1)
				{
					world.setBlockState(pos.add(x, 2, z), POLISHED_GRANITE, 3);
				}
				else
				{
					//sets slab but also waterlogs it if block it replaces is water based
					world.setBlockState(pos.add(x, 2, z), POLISHED_ANDESITE_SLAB_TOP.with(SlabBlock.WATERLOGGED, world.getBlockState(pos.add(x, 2, z)).getMaterial() == Material.WATER), 3);
				}
			}
		}

		return true;
	}
}
