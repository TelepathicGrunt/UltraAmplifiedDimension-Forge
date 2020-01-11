package net.telepathicgrunt.ultraamplified.world.feature;

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

public class VinesShort extends Feature<NoFeatureConfig> 
{
	   
	public VinesShort(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory) {
		super(configFactory);
	}

	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlocks, Random rand, BlockPos position, NoFeatureConfig config) {
	
		//generates vines from given position down 6 blocks if path is clear and the given position is valid
		//Also won't generate vines below Y = 15.
		int length = 0;
		
		for (; position.getY() > 15 && length < 6; position = position.down()) 
		{
			if (world.isAirBlock(position)) 
			{
				for (Direction Direction : Direction.Plane.HORIZONTAL) 
				{
					BlockState iblockstate = Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(Direction), Boolean.valueOf(true));
					if (iblockstate.isValidPosition(world, position)) 
					{
						world.setBlockState(position, iblockstate, 2);
						break;
					} 
					else if (world.getBlockState(position.up()).getBlock() == Blocks.VINE) 
					{
						world.setBlockState(position, world.getBlockState(position.up()), 2);
						length++;
						break;
					}
				}
			}
		}

		return true;
	}
}