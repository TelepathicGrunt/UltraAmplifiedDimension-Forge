package net.TelepathicGrunt.UltraAmplified.Blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.TelepathicGrunt.UltraAmplified.UltraAmplified;


public class AmplifiedPortalBlock extends Block
{

	public AmplifiedPortalBlock()
	{
		super(Material.GLASS);
        this.setLightLevel(1.0F);
		this.setHardness(5.0F);
		this.setResistance(3600000.0F);

		this.setUnlocalizedName("amplified_portal");
		setRegistryName("amplified_portal");
	}



	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.SOLID;
	}


	/**
	 * mining portal block in ultra amplified dimension will be denied if it is the highest Amplified Portal Block at x=8,
	 * z=8
	 *
	 * :Forge notes: Called when a player removes a block. This is responsible for actually destroying the block, and the
	 * block is intact at time of call. This is called regardless of whether the player can harvest the block or not.
	 *
	 * Return true if the block is actually destroyed.
	 *
	 * Note: When used in multiplayer, this is called on both client and server sides!
	 *
	 * @param state       The current state.
	 * @param world       The current world
	 * @param player      The player damaging the block, may be null
	 * @param pos         Block position in world
	 * @param willHarvest True if Block.harvestBlock will be called after this, if the return in true. Can be useful to
	 *                    delay the destruction of tile entities till after harvestBlock
	 * @param fluid       The current fluid state at current position
	 * @return True if the block is actually destroyed.
	 */
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{

		// if player is in creative mode, just remove block completely
		if (player != null && player.isCreative())
		{
			super.onBlockHarvested(world, pos, state, player);
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			return true;
		}

		// otherwise, check to see if we are mining the highest portal block at world
		// origin in UA dimension
		else
		{
			// if we are in UA dimension
			if (player.dimension == UltraAmplified.UADimType.getId())
			{

				// if we are at default portal coordinate
				if (pos.getX() == 8 && pos.getZ() == 8)
				{

					// finds the highest portal at world origin
					BlockPos posOfHighestPortal = new BlockPos(pos.getX(), 255, pos.getZ());
					while (posOfHighestPortal.getY() >= 0)
					{
						Block blockToCheck = world.getBlockState(posOfHighestPortal).getBlock();
						if (blockToCheck == BlocksAndItemsInit.AMPLIFIEDPORTAL)
						{
							break;
						}

						posOfHighestPortal = posOfHighestPortal.down();
					}

					// if this block being broken is the highest portal, return false to end method
					// and not break the portal block
					if (posOfHighestPortal.getY() == pos.getY())
					{
						return false;
					}
				}
			}
		}

		// otherwise, allow the block to break
		super.onBlockHarvested(world, pos, state, player);
		return world.destroyBlock(pos, false);
	}


	public boolean trySpawnPortal(World worldIn, BlockPos pos)
	{

		// cannot create amplified portal in Ultra Amplified Worldtype
		if (worldIn.getWorldType() == UltraAmplified.UltraAmplifiedWorldType)
		{
			return false;
		}

		boolean canMakePortal = this.isPortal(worldIn, pos);
		if (canMakePortal)
		{
			placePortalBlocks(worldIn, pos);
			return true;
		}
		else
		{
			return false;
		}
	}


	@Nullable
	public boolean isPortal(World worldIn, BlockPos pos)
	{
		if (isValid(worldIn, pos))
		{
			return true;
		}

		return false;
	}


	/**
	 * faster particle movement than normal EndPortal block
	 */
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		double d0 = (double) ((float) pos.getX() + (rand.nextFloat() * 3 - 1));
		double d1 = (double) ((float) pos.getY() + (rand.nextFloat() * 3 - 1));
		double d2 = (double) ((float) pos.getZ() + (rand.nextFloat() * 3 - 1));
		worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}


	// has no item form
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return ItemStack.EMPTY;
	}

	// ------------------------------------------------------------------------------------//
	// Portal creation and validation check

	private static final IBlockState POLISHED_GRANITE = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE_SMOOTH);
	private static final IBlockState POLISHED_DIORITE = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE_SMOOTH);
	private static final IBlockState STONE_BRICK_SLAB_TOP = Blocks.STONE_SLAB2.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP);
	private static final IBlockState STONE_BRICK_SLAB_BOTTOM = Blocks.STONE_SLAB2.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM);


	public boolean isValid(World worldIn, BlockPos pos)
	{

		// bottom of portal frame
		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				if (Math.abs(x * z) == 1)
				{
					if (worldIn.getBlockState(pos.add(x, -1, z)) != POLISHED_GRANITE)
					{
						return false;
					}
				}
				else
				{
					if (worldIn.getBlockState(pos.add(x, -1, z)) != STONE_BRICK_SLAB_BOTTOM)
					{
						return false;
					}
				}
			}
		}

		// the center itself
		if (worldIn.getBlockState(pos.add(0, 0, 0)) != POLISHED_DIORITE)
		{
			return false;
		}

		// top of portal frame
		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				if (Math.abs(x * z) == 1)
				{
					if (worldIn.getBlockState(pos.add(x, 1, z)) != POLISHED_GRANITE)
					{
						return false;
					}
				}
				else
				{
					if (worldIn.getBlockState(pos.add(x, 1, z)) != STONE_BRICK_SLAB_TOP)
					{
						return false;
					}
				}
			}
		}

		return true;
	}


	public void placePortalBlocks(World worldIn, BlockPos pos)
	{
		// the portal itself
		worldIn.setBlockState(pos.add(0, 0, 0), BlocksAndItemsInit.AMPLIFIEDPORTAL.getDefaultState(), 18);
	}

}
