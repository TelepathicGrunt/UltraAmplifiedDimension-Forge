package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WorldGenVinesLongUA extends Feature<NoFeatureConfig> 
{
	   
	public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> changedBlocks, Random rand, BlockPos position, NoFeatureConfig config) {
		     
		//generates vines from given position all the way down to Y = 75 if path is clear and the given position is valid
		for (; position.getY() > 75; position = position.down()) 
		{
			if (worldIn.isAirBlock(position)) 
			{
				for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) 
				{
					IBlockState iblockstate = Blocks.VINE.getDefaultState().with(BlockVine.getPropertyFor(enumfacing), Boolean.valueOf(true));
					if (iblockstate.isValidPosition(worldIn, position)) 
					{
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
