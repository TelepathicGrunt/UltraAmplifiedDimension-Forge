package net.telepathicgrunt.ultraamplified.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;


public class CactusCornerBlockUA extends HorizontalBlock implements net.minecraftforge.common.IPlantable
{
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	protected static final VoxelShape HITBOX_DIMENSIONS = Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 16.0D);
	protected static final VoxelShape OUTLINE_DIMENSION = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);


	public CactusCornerBlockUA()
	{
		super(Block.Properties.create(Material.CACTUS).tickRandomly().hardnessAndResistance(0.4F).sound(SoundType.CLOTH));
		setRegistryName("cactus_corner_block_ua");
		this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)).with(FACING, Direction.NORTH));
	}


	public void tick(BlockState state, World worldIn, BlockPos pos, Random random)
	{
		if (!worldIn.isAreaLoaded(pos, 1))
			return; // Forge: prevent growing cactus from loading unloaded chunks with block update
		if (!state.isValidPosition(worldIn, pos))
		{
			worldIn.destroyBlock(pos, true);
		}
		else
		{
			BlockPos blockpos = pos.up();
			if (worldIn.isAirBlock(blockpos))
			{

				int j = state.get(AGE);
				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, true))
				{
					if (j == 15)
					{
						worldIn.setBlockState(blockpos, BlocksInit.CACTUSBODYBLOCKUA.getDefaultState());
						BlockState blockstate = state.with(AGE, Integer.valueOf(0));
						worldIn.setBlockState(pos, blockstate, 4);
						blockstate.neighborChanged(worldIn, blockpos, this, pos, false);
					}
					else
					{
						worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(j + 1)), 4);
					}
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
				}

			}
		}
	}


	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}


	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(AGE);
		builder.add(FACING);
	}


	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return HITBOX_DIMENSIONS;
	}


	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return OUTLINE_DIMENSION;
	}


	public boolean isSolid(BlockState state)
	{
		return true;
	}


	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state. For example,
	 * fences make their connections to the passed in state if possible, and wet concrete powder immediately returns its
	 * solidified counterpart. Note that this method should ideally consider only the specific face passed in.
	 */
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		if (!stateIn.isValidPosition(worldIn, currentPos))
		{
			worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
		}

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}


	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		for (Direction direction : Direction.Plane.HORIZONTAL)
		{
			if (worldIn.getFluidState(pos.offset(direction)).isTagged(FluidTags.LAVA))
			{
				return false;
			}
		}

		return this.canCactusSurviveHere(worldIn, pos, state.get(FACING), this) && !worldIn.getBlockState(pos.up()).getMaterial().isLiquid();
	}


	public boolean canCactusSurviveHere(IBlockReader worldIn, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable)
	{
		BlockState belowBlock = worldIn.getBlockState(pos.down());

		if (belowBlock.getMaterial() == Material.AIR)
		{

			BlockState offsetBlock = worldIn.getBlockState(pos.offset(facing.getOpposite()));
			if (offsetBlock.getBlock() != Blocks.CACTUS && offsetBlock.getMaterial() == Material.CACTUS)
			{
				//handling two edge cases
				//Case 1: attached to horizontal main cactus block that has corner blocks on both sides but neither corner block has a valid block below
				//Case 2: two corner blocks are facing each otehr with no valid space below
				if (offsetBlock.getBlock() == BlocksInit.CACTUSMAINBLOCKUA)
				{
					return ((CactusMainBlockUA) offsetBlock.getBlock()).isValidPosition(offsetBlock, (IWorldReader) worldIn, pos.offset(facing.getOpposite()));
				}
				else if (offsetBlock.getBlock() == this)
				{
					belowBlock = worldIn.getBlockState(pos.offset(facing.getOpposite()).down());
					//any sand or modded cactus block below
					if (belowBlock.getBlock() == Blocks.SAND || belowBlock.getBlock() == Blocks.RED_SAND
							|| (belowBlock.getBlock() != Blocks.CACTUS && belowBlock.getMaterial() == Material.CACTUS))
					{
						return true;
					}
				}
				else
				{
					return true;
				}
			}

			return false;
		}
		else
		{
			belowBlock = worldIn.getBlockState(pos.down());

			//any sand or modded cactus block below
			if (belowBlock.getBlock() == Blocks.SAND || belowBlock.getBlock() == Blocks.RED_SAND
					|| (belowBlock.getBlock() != Blocks.CACTUS && belowBlock.getMaterial() == Material.CACTUS))
			{
				return true;
			}
		}

		return false;
	}


	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
	{
		entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}


	/**
	 * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
	 * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
	 */
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}


	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
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
