package net.telepathicgrunt.ultraamplified.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


public class CactusMainBlockUA extends DirectionalBlock implements net.minecraftforge.common.IPlantable
{
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
	public static final DirectionProperty VALIDFACING = DirectionProperty.create("facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP);
	public static final DirectionProperty FACING = VALIDFACING;

	protected static final VoxelShape HITBOX_DIMENSIONS = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
	protected static final VoxelShape OUTLINE_DIMENSION = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);


	public CactusMainBlockUA()
	{
		super(Block.Properties.create(Material.CACTUS).tickRandomly().hardnessAndResistance(0.4F).sound(SoundType.CLOTH));
		setRegistryName("cactus_main_block_ua");
		this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)).with(FACING, Direction.UP));
	}


	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		if (!world.isAreaLoaded(pos, 1))
			return; // Forge: prevent growing cactus from loading unloaded chunks with block update
		if (!state.isValidPosition(world, pos))
		{
			world.destroyBlock(pos, true);
		}
		else
		{
			BlockPos blockpos = pos.up();
			if (world.isAirBlock(blockpos))
			{
				int i;

				for (i = 1; world.getBlockState(pos.down(i)).getBlock() == this
						|| world.getBlockState(pos.down(i)).getBlock() == BlocksInit.CACTUSBODYBLOCKUA.get()
						|| world.getBlockState(pos.down(i)).getBlock() == BlocksInit.CACTUSCORNERBLOCKUA.get(); ++i)
				{ ; }

				if (i < 3)
				{
					int j = state.get(AGE);
					if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, blockpos, state, true))
					{
						if (j == 15)
						{
							world.setBlockState(blockpos, this.getDefaultState());
							BlockState blockstate = state.with(AGE, Integer.valueOf(0));
							world.setBlockState(pos, blockstate, 4);
							blockstate.neighborChanged(world, blockpos, this, pos, false);
						}
						else
						{
							world.setBlockState(pos, state.with(AGE, Integer.valueOf(j + 1)), 4);
						}
						net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, pos, state);
					}
				}
			}
		}
	}


	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		Direction direction = context.getFace().getOpposite();
		return this.getDefaultState().with(FACING, direction.getAxis() == Direction.Axis.Y ? Direction.UP : direction);
	}


	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(AGE);
		builder.add(FACING);
	}


	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		return HITBOX_DIMENSIONS;
	}


	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		return OUTLINE_DIMENSION;
	}


	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state. For example,
	 * fences make their connections to the passed in state if possible, and wet concrete powder immediately returns its
	 * solidified counterpart. Note that this method should ideally consider only the specific face passed in.
	 */
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
	{
		if (!stateIn.isValidPosition(world, currentPos))
		{
			world.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
		}

		return super.updatePostPlacement(stateIn, facing, facingState, world, currentPos, facingPos);
	}


	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
	{

		//up needs a cactus block below
		if (state.get(FACING) == Direction.UP)
		{

			//cannot have lava next to it
			for (Direction direction : Direction.Plane.HORIZONTAL)
			{
				if (world.getFluidState(pos.offset(direction)).isTagged(FluidTags.LAVA))
				{
					return false;
				}
			}

			return validBlockBelow(world, pos.down());
		}

		//sideways needs 1 body or corner cactus block next to it with valid block below that and no lava nearby too
		else
		{
			boolean hasMainCactusBody = false;

			for (Direction direction : Direction.Plane.HORIZONTAL)
			{

				//skip all axis that isn't aligned with this block's axis
				if (state.get(FACING).getAxis() != direction.getAxis())
				{
					continue;
				}

				BlockState blockstate = world.getBlockState(pos.offset(direction));

				//NO LAVA ALLOWED
				if (world.getFluidState(pos.offset(direction)).isTagged(FluidTags.LAVA))
				{
					return false;
				}

				//if modded cactus is next to this block, check to see if it has a valid block below.
				//this is so this block can be placed onto main cactus body by hand and will break if 
				//main cactus body is breaking even though this block may have a upward branch on the other side.
				if (blockstate.getBlock() != Blocks.CACTUS && blockstate.getBlock() != this && blockstate.getMaterial() == Material.CACTUS)
				{
					if (validBlockBelow(world, pos.offset(direction).down()))
					{
						hasMainCactusBody = true;
					}
				}
			}

			return hasMainCactusBody;
		}
	}


	/**
	 * Will return true if it is sand or modded cactus at desired position
	 * 
	 * @param world - current world to check in
	 * @param pos     - position of where to check
	 * @return - is sand or modded cactus at pos
	 */
	private boolean validBlockBelow(IWorldReader world, BlockPos pos)
	{
		BlockState blockToCheck = world.getBlockState(pos);
		if (blockToCheck.getBlock() == Blocks.SAND || blockToCheck.getBlock() == Blocks.RED_SAND
				|| (blockToCheck.getBlock() != Blocks.CACTUS && blockToCheck.getMaterial() == Material.CACTUS))
		{
			return true;
		}
		return false;
	}


	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entityIn)
	{
		entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}


	public boolean allowsMovement(BlockState state, IBlockReader world, BlockPos pos, PathType type)
	{
		return false;
	}


	@Override
	public net.minecraftforge.common.PlantType getPlantType(IBlockReader world, BlockPos pos)
	{
		return net.minecraftforge.common.PlantType.Desert;
	}


	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos)
	{
		return getDefaultState();
	}
}
