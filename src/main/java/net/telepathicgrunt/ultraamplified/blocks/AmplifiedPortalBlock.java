package net.telepathicgrunt.ultraamplified.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.capabilities.IPlayerPosAndDim;
import net.telepathicgrunt.ultraamplified.capabilities.PlayerPositionAndDimension;
import net.telepathicgrunt.ultraamplified.extrabehavior.AmplifiedPortalCreation;
import net.telepathicgrunt.ultraamplified.world.dimension.UADimensionRegistration;


public class AmplifiedPortalBlock extends Block
{
	@CapabilityInject(IPlayerPosAndDim.class)
	public static Capability<IPlayerPosAndDim> PAST_POS_AND_DIM = null;
	
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);


	public AmplifiedPortalBlock()
	{
		super(Block.Properties.create(Material.GLASS, MaterialColor.BLACK).lightValue(15).hardnessAndResistance(5.0F, 3600000.0F));

		setRegistryName("amplified_portal");
	}


	public TileEntity createNewTileEntity(IBlockReader world)
	{
		return new EndPortalTileEntity();
	}


	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}

	@Override
	@SuppressWarnings("deprecation")
	public ActionResultType onBlockActivated(BlockState thisBlockState, World world, BlockPos position, PlayerEntity playerEntity, Hand playerHand, BlockRayTraceResult raytraceResult)
	{
		MinecraftServer minecraftserver = playerEntity.getServer();

		// Extra checking to make sure it's just the player alone and not riding, being ridden, etc 
		// Also makes sure player isn't sneaking so players can crouch place blocks on the portal
		// But only teleport if we aren't in UA worldtype
		if (!world.isRemote && !playerEntity.isPassenger() && !playerEntity.isBeingRidden() && playerEntity.isNonBoss() && !playerEntity.isCrouching())
		{
			//grabs the capability attached to player for dimension hopping
			PlayerPositionAndDimension cap = (PlayerPositionAndDimension) playerEntity.getCapability(PAST_POS_AND_DIM).orElseThrow(RuntimeException::new);

			// Gets previous dimension
			DimensionType destination;
			float pitch = 3.75F; // Looking straight ahead
			float yaw = 0; // Facing north 
			boolean enteringUA = false;

			// Player is leaving Ultra Amplified dimension
			if (playerEntity.dimension == UADimensionRegistration.ultraamplified())
			{
				if (UltraAmplified.UAConfig.forceExitToOverworld.get())
				{
					// Go to Overworld directly because of config option.
					destination = DimensionType.OVERWORLD;
				}
				else
				{
					// Gets stored dimension
					destination = cap.getNonUADim();

					// Impressive if this is reached...........
					if (destination == null)
					{
						destination = DimensionType.OVERWORLD;
					}
				}

				// Get direction to face for Non-UA dimension
				pitch = cap.getNonUAPitch();
				yaw = cap.getNonUAYaw();

				// Set current UA position and rotations
				cap.setUAPos(playerEntity.getPositionVec());
				cap.setUAPitch(playerEntity.rotationPitch);
				cap.setUAYaw(playerEntity.rotationYaw);
			}

			// Otherwise, take us to Ultra Amplified Dimension.
			else
			{
				destination = UADimensionRegistration.ultraamplified();
				pitch = cap.getUAPitch();
				yaw = cap.getUAYaw();
				enteringUA = true;

				// Set current nonUA position, rotations, and dimension before teleporting
				cap.setNonUAPos(playerEntity.getPositionVec());
				cap.setNonUADim(playerEntity.dimension);
				cap.setNonUAPitch(playerEntity.rotationPitch);
				cap.setNonUAYaw(playerEntity.rotationYaw);
			}

			ServerWorld serverworld = minecraftserver.getWorld(destination);
			
			//Create portal in UA if it hasn't been made yet in the dimension
			if(destination == UADimensionRegistration.ultraamplified()) {
				if (!AmplifiedPortalCreation.checkForGeneratedPortal(serverworld))
				{
					AmplifiedPortalCreation.generatePortal(serverworld);
				}
			}

			// Gets top block in other world or original location
			Vec3d playerVec3Pos = new Vec3d(8.5D, 0, 8.5D);
			ChunkPos playerChunkPos;
			if (enteringUA && cap.getUAPos() == null)
			{
				// If it is player's first time teleporting to UA dimension, 
				// find top block at world origin closest to portal
				BlockPos worldOriginBlockPos = new BlockPos(10, 0, 8);
				playerChunkPos = new ChunkPos(worldOriginBlockPos);

				int portalY = 255;

				//finds where portal block is
				while (portalY > 0)
				{
					if (serverworld.getBlockState(worldOriginBlockPos.up(portalY)) == UABlocks.AMPLIFIEDPORTAL.get().getDefaultState())
					{
						break;
					}
					portalY--;
				}

				//not sure how the portal block was not found but if so, spawn player at highest piece of land
				if (portalY == 0)
				{
					playerVec3Pos = new Vec3d(serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, worldOriginBlockPos)).add(0.5D, 1D, 0.5D);
				}
				else
				{
					//portal was found so try to find 2 air spaces around it that the player can spawn at
					worldOriginBlockPos = worldOriginBlockPos.up(portalY - 1);
					boolean validSpaceFound = false;

					for (int x = -2; x < 3; x++)
					{
						for (int z = -2; z < 3; z++)
						{
							if (x == -2 || x == 2 || z == -2 || z == 2)
							{
								if (serverworld.getBlockState(worldOriginBlockPos.add(x, 0, z)).getMaterial() == Material.AIR && serverworld.getBlockState(worldOriginBlockPos.add(x, 1, z)).getMaterial() == Material.AIR)
								{
									//valid space for player is found
									worldOriginBlockPos = worldOriginBlockPos.add(x, 0, z);
									validSpaceFound = true;
									z = 3;
									x = 3;
								}
							}
						}
					}

					if (!validSpaceFound)
					{
						//no valid space found around portal. get top solid block instead
						worldOriginBlockPos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 255, 8));
					}

					playerVec3Pos = new Vec3d(worldOriginBlockPos).add(0.5D, 0.2D, 0.5D); // Set where player spawns
				}

			}
			else
			{
				// Otherwise, just go to where our stored location is
				if (enteringUA)
				{
					// Will never be null because we did check above for null already.
					playerVec3Pos = cap.getUAPos();
				}
				else
				{
					// Check for null which would be impressive if it occurs
					if (cap.getNonUAPos() == null || UltraAmplified.UAConfig.forceExitToOverworld.get())
					{
						// Set player at world spawn then with Amplified Portal at feet
						// The portal will try to not replace any block and be at the next air block above non-air blocks.
						BlockPos playerBlockPos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING, serverworld.getSpawnPoint());
						BlockState blockState = serverworld.getBlockState(playerBlockPos);
						while (blockState.getMaterial() != Material.AIR && playerBlockPos.getY() < serverworld.getActualHeight() - 2)
						{
							playerBlockPos = playerBlockPos.up();
							blockState = serverworld.getBlockState(playerBlockPos);
						}

						serverworld.setBlockState(playerBlockPos, UABlocks.AMPLIFIEDPORTAL.get().getDefaultState());

						playerVec3Pos = new Vec3d(playerBlockPos).add(0.5D, 1D, 0.5D);
					}
					else
					{
						// Get position in non UA dimension as it isn't null
						playerVec3Pos = cap.getNonUAPos();
					}
				}
				playerChunkPos = new ChunkPos(new BlockPos(playerVec3Pos));
			}

			serverworld.getChunkProvider().registerTicket(TicketType.POST_TELEPORT, playerChunkPos, 1, playerEntity.getEntityId());

			//dunno how a sleeping player clicked on the portal but if they do, they wake up
			if (((ServerPlayerEntity) playerEntity).isSleeping())
			{
				((ServerPlayerEntity) playerEntity).wakeUp();
			}

			playerEntity.fallDistance = 0;
			((ServerPlayerEntity) playerEntity).teleport(minecraftserver.getWorld(destination), playerVec3Pos.getX(), playerVec3Pos.getY() + 0.2D, playerVec3Pos.getZ(), yaw, pitch);
		}
		
		return super.onBlockActivated(thisBlockState, world, position, playerEntity, playerHand, raytraceResult);
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
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid)
	{

		// if player is in creative mode, just remove block completely
		if (player != null && player.isCreative())
		{
			getBlock().onBlockHarvested(world, pos, state, player);
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			return true;
		}

		// otherwise, check to see if we are mining the highest portal block at world
		// origin in UA dimension
		else
		{
			// if we are in UA dimension
			if (world.getDimension().getType() == UADimensionRegistration.ultraamplified())
			{

				// if we are at default portal coordinate
				if (pos.getX() == 8 && pos.getZ() == 8)
				{

					// finds the highest portal at world origin
					BlockPos posOfHighestPortal = new BlockPos(pos.getX(), 255, pos.getZ());
					while (posOfHighestPortal.getY() >= 0)
					{
						Block blockToCheck = world.getBlockState(posOfHighestPortal).getBlock();
						if (blockToCheck == UABlocks.AMPLIFIEDPORTAL.get())
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
		getBlock().onBlockHarvested(world, pos, state, player);
		return world.removeBlock(pos, false);
	}

	// has no item form
	@Override
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state)
	{
		return ItemStack.EMPTY;
	}


	
	/**
	 * Spawns with tons of particles upon creation
	 */
	@Deprecated
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		createLotsOfParticles(state, (ServerWorld)world, pos, world.rand);
	}


	@OnlyIn(Dist.CLIENT)
	public void createLotsOfParticles(BlockState blockState, ServerWorld world, BlockPos position, Random random)
	{
		double xPos = (double) position.getX() + 0.5D;
		double yPos = (double) position.getY() + 0.5D;
		double zPos = (double) position.getZ() + 0.5D;
		double xOffset = (random.nextFloat() - 0.4D) * 0.8D;
		double zOffset = (random.nextFloat() - 0.4D) * 0.8D;

		world.spawnParticle(ParticleTypes.FLAME, xPos, yPos, zPos, 50, xOffset, 0, zOffset, random.nextFloat()*0.1D+0.05D);
	}


	/**
	 * more frequent particles than normal EndPortal block
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World world, BlockPos pos, Random rand)
	{
		double d0 = pos.getX() + (rand.nextFloat() * 3 - 1);
		double d1 = pos.getY() + (rand.nextFloat() * 3 - 1);
		double d2 = pos.getZ() + (rand.nextFloat() * 3 - 1);
		world.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}
}
