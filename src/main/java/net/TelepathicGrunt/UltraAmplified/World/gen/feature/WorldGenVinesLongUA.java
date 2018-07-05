package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenVinesLongUA extends WorldGenerator
{
	public boolean generate(World worldIn, Random rand, BlockPos position) 
	{
		for (; position.getY() > 40; position = position.down()) 
		{
			if (worldIn.isAirBlock(position)) 
			{
				for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings()) 
				{
					if (Blocks.VINE.canPlaceBlockOnSide(worldIn, position, enumfacing)) 
					{
						IBlockState iblockstate = Blocks.VINE.getDefaultState()
								.withProperty(BlockVine.NORTH, Boolean.valueOf(enumfacing == EnumFacing.SOUTH))
								.withProperty(BlockVine.EAST, Boolean.valueOf(enumfacing == EnumFacing.WEST))
								.withProperty(BlockVine.SOUTH, Boolean.valueOf(enumfacing == EnumFacing.NORTH))
								.withProperty(BlockVine.WEST, Boolean.valueOf(enumfacing == EnumFacing.EAST));
						worldIn.setBlockState(position, iblockstate, 2);
						break;
					} 
					else if (worldIn.getBlockState(position.up()).getBlock() == Blocks.VINE) 
					{
						worldIn.setBlockState(position, worldIn.getBlockState(position.up()), 2);
						break;
					}
				}
			}
		}

		return true;
	}
}
