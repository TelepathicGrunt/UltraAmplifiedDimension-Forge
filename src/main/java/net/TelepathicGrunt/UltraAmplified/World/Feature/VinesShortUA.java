package net.TelepathicGrunt.UltraAmplified.World.Feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class VinesShortUA extends Feature<NoFeatureConfig> 
{
	   
	public VinesShortUA(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> changedBlocks, Random rand, BlockPos position, NoFeatureConfig config) {
	
		//generates vines from given position down 6 blocks if path is clear and the given position is valid
		//Also won't generate vines below Y = 15.
		int length = 0;
		
		for (; position.getY() > 15 && length < 6; position = position.down()) 
		{
			if (worldIn.isAirBlock(position)) 
			{
				for (Direction Direction : Direction.Plane.HORIZONTAL) 
				{
					BlockState iblockstate = Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(Direction), Boolean.valueOf(true));
					if (iblockstate.isValidPosition(worldIn, position)) 
					{
						worldIn.setBlockState(position, iblockstate, 2);
						break;
					} 
					else if (worldIn.getBlockState(position.up()).getBlock() == Blocks.VINE) 
					{
						worldIn.setBlockState(position, worldIn.getBlockState(position.up()), 2);
						length++;
						break;
					}
				}
			}
		}

		return true;
	}
}