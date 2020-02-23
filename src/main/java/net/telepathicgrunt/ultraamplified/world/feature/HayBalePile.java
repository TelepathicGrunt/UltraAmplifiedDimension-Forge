package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class HayBalePile extends Feature<NoFeatureConfig>
{

	public HayBalePile(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private final BlockState hayBaleNS = Blocks.HAY_BLOCK.getDefaultState().with(HayBlock.AXIS, Direction.Axis.Z);
	private final BlockState hayBaleEW = Blocks.HAY_BLOCK.getDefaultState().with(HayBlock.AXIS, Direction.Axis.X);
	private final BlockState railNSFlat = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.NORTH_SOUTH);
	private final BlockState railNSAscending = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.ASCENDING_NORTH);
	private final BlockState railNSDecending = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.ASCENDING_SOUTH);
	private final BlockState railEWFlat = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.EAST_WEST);
	private final BlockState railEWAscending = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.ASCENDING_EAST);
	private final BlockState railEWDecending = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.ASCENDING_WEST);


	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
	{

		if (!ConfigUA.miniStructureGeneration)
		{
			return false;
		}

		//north/south
		if (rand.nextBoolean())
		{

			//makes sure it is generating completely on land and not over water or air
			if (this.isBlockNormalCube(world, position.down().north(2), false) && this.isBlockNormalCube(world, position.down().south(2), false) && this.isBlockNormalCube(world, position.down().east(2).north(), false) && this.isBlockNormalCube(world, position.down().west(2).north(), false) && this.isBlockNormalCube(world, position.down().east(2).south(), false) && this.isBlockNormalCube(world, position.down().west(2).south(), false))
			{
				for (int i = -2; i <= 2; i++)
				{
					world.setBlockState(position.north(i), hayBaleNS, 2);
					world.setBlockState(position.north(i).up(), hayBaleNS, 2);
					world.setBlockState(position.north(i).west(), hayBaleNS, 2);
					world.setBlockState(position.north(i).east(), hayBaleNS, 2);
				}

				world.setBlockState(position.north().up(2), railEWFlat, 2);
				world.setBlockState(position.west(2).north(), railEWAscending, 2);
				world.setBlockState(position.west().north().up(), railEWAscending, 2);
				world.setBlockState(position.east(2).north(), railEWDecending, 2);
				world.setBlockState(position.east().north().up(), railEWDecending, 2);

				world.setBlockState(position.south().up(2), railEWFlat, 2);
				world.setBlockState(position.west(2).south(), railEWAscending, 2);
				world.setBlockState(position.west().south().up(), railEWAscending, 2);
				world.setBlockState(position.east(2).south(), railEWDecending, 2);
				world.setBlockState(position.east().south().up(), railEWDecending, 2);
			}
		}

		//East/West
		else
		{

			//makes sure it is generating completely on land and not over water or air
			if (this.isBlockNormalCube(world, position.down().west(2), false) && this.isBlockNormalCube(world, position.down().east(2), false) && this.isBlockNormalCube(world, position.down().north(2).west(), false) && this.isBlockNormalCube(world, position.down().north(2).east(), false) && this.isBlockNormalCube(world, position.down().south(2).east(), false) && this.isBlockNormalCube(world, position.down().south(2).west(), false))
			{

				for (int i = -2; i <= 2; i++)
				{
					world.setBlockState(position.east(i), hayBaleEW, 2);
					world.setBlockState(position.east(i).up(), hayBaleEW, 2);
					world.setBlockState(position.east(i).north(), hayBaleEW, 2);
					world.setBlockState(position.east(i).south(), hayBaleEW, 2);
				}

				world.setBlockState(position.east().up(2), railNSFlat, 2);
				world.setBlockState(position.north(2).east(), railNSDecending, 2);
				world.setBlockState(position.north().east().up(), railNSDecending, 2);
				world.setBlockState(position.south(2).east(), railNSAscending, 2);
				world.setBlockState(position.south().east().up(), railNSAscending, 2);

				world.setBlockState(position.west().up(2), railNSFlat, 2);
				world.setBlockState(position.north(2).west(), railNSDecending, 2);
				world.setBlockState(position.north().west().up(), railNSDecending, 2);
				world.setBlockState(position.south(2).west(), railNSAscending, 2);
				world.setBlockState(position.south().west().up(), railNSAscending, 2);
			}
		}

		return true;
	}


	/**
	 * Checks if a block's material is opaque, and that it takes up a full cube
	 */
	private boolean isBlockNormalCube(IWorld world, BlockPos pos, boolean _default)
	{
		if (pos.getY() > 255)
		{
			return false;
		}
		else
		{
			return world.getBlockState(pos).isNormalCube(world, pos);
		}
	}
}
