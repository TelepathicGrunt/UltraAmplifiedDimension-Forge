package com.telepathicgrunt.ultraamplifieddimension.blocks;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import com.telepathicgrunt.ultraamplifieddimension.capabilities.IPlayerPosAndDim;
import com.telepathicgrunt.ultraamplifieddimension.capabilities.PlayerPositionAndDimension;
import com.telepathicgrunt.ultraamplifieddimension.dimension.AmplifiedPortalCreation;
import com.telepathicgrunt.ultraamplifieddimension.dimension.UADDimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nonnull;
import java.util.Random;


public class AmplifiedPortalBlock extends Block
{
	@CapabilityInject(IPlayerPosAndDim.class)
	public static Capability<IPlayerPosAndDim> PAST_POS_AND_DIM = null;
	
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);


	public AmplifiedPortalBlock()
	{
		super(Properties.create(Material.GLASS, MaterialColor.BLACK).setLightLevel((blockState) -> 15).hardnessAndResistance(5.0F, 3600000.0F));
	}

	@Nonnull
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public ActionResultType onBlockActivated(BlockState thisBlockState, World world, BlockPos position, PlayerEntity playerEntity, Hand playerHand, BlockRayTraceResult raytraceResult)
	{
		// Extra checking to make sure it's just the player alone and not riding, being ridden, etc 
		// Also makes sure player isn't sneaking so players can crouch place blocks on the portal
		// But only teleport if we aren't in UA worldtype
		if (!world.isRemote &&
			!playerEntity.isPassenger() &&
			!playerEntity.isBeingRidden() &&
			playerEntity.isNonBoss() &&
			!playerEntity.isCrouching())
		{
			MinecraftServer minecraftserver = playerEntity.getServer();

			//grabs the capability attached to player for dimension hopping
			PlayerPositionAndDimension cap = (PlayerPositionAndDimension) playerEntity.getCapability(PAST_POS_AND_DIM).orElseThrow(RuntimeException::new);

			// Gets previous dimension
			RegistryKey<World> destinationKey;
			float pitch;
			float yaw;
			boolean enteringUA = false;

			// Player is leaving Ultra Amplified dimension
			if (playerEntity.world.getDimensionKey().equals(UADDimension.UAD_WORLD_KEY)) {
				if (UltraAmplifiedDimension.UADimensionConfig.forceExitToOverworld.get())
				{
					// Go to Overworld directly because of config option.
					destinationKey = World.OVERWORLD;
				}
				else {
					// Gets stored dimension
					destinationKey = cap.getNonUADim();

					// Impressive if this is reached...........
					if (destinationKey == null) {
						destinationKey = World.OVERWORLD;
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
			else {
				destinationKey = UADDimension.UAD_WORLD_KEY;
				pitch = cap.getUAPitch();
				yaw = cap.getUAYaw();
				enteringUA = true;

				// Set current nonUA position, rotations, and dimension before teleporting
				cap.setNonUAPos(playerEntity.getPositionVec());
				cap.setNonUADim(playerEntity.world.getDimensionKey());
				cap.setNonUAPitch(playerEntity.rotationPitch);
				cap.setNonUAYaw(playerEntity.rotationYaw);
			}

			//Get the world itself. If the world doesn't exist, get Overworld instead.
			ServerWorld destinationWorld = minecraftserver.getWorld(destinationKey);
			if(destinationWorld == null){
				destinationWorld = minecraftserver.getWorld(World.OVERWORLD);
			}


			//Create portal in UA if it hasn't been made yet in the dimension
			if(destinationKey.equals(UADDimension.UAD_WORLD_KEY)) {
				if (!AmplifiedPortalCreation.checkForGeneratedPortal(destinationWorld)) {
					AmplifiedPortalCreation.generatePortal(destinationWorld);
				}
			}

			// Gets top block in other world or original location
			Vector3d playerVec3Pos;
			ChunkPos playerChunkPos;
			if (enteringUA && cap.getUAPos() == null) {
				// If it is player's first time teleporting to UA dimension, 
				// find top block at world origin closest to portal
				BlockPos worldOriginBlockPos = new BlockPos(10, 0, 8);
				playerChunkPos = new ChunkPos(worldOriginBlockPos);

				int portalY = world.func_234938_ad_();

				//finds where portal block is
				while (portalY > 0) {
					if (destinationWorld.getBlockState(worldOriginBlockPos.up(portalY)) == UADBlocks.AMPLIFIEDPORTAL.get().getDefaultState()) {
						break;
					}
					portalY--;
				}

				//not sure how the portal block was not found but if so, spawn player at highest piece of land
				if (portalY == 0) {
					playerVec3Pos = Vector3d.copyCentered(destinationWorld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, worldOriginBlockPos)).add(0, 0.5D, 0);
				}
				else {
					//portal was found so try to find 2 air spaces around it that the player can spawn at
					worldOriginBlockPos = worldOriginBlockPos.up(portalY - 1);
					boolean validSpaceFound = false;

					for (int x = -2; x < 3; x++) {
						for (int z = -2; z < 3; z++) {
							if (x == -2 || x == 2 || z == -2 || z == 2) {
								if (destinationWorld.getBlockState(worldOriginBlockPos.add(x, 0, z)).getMaterial() == Material.AIR && destinationWorld.getBlockState(worldOriginBlockPos.add(x, 1, z)).getMaterial() == Material.AIR) {
									//valid space for player is found
									worldOriginBlockPos = worldOriginBlockPos.add(x, 0, z);
									validSpaceFound = true;
									z = 3;
									x = 3;
								}
							}
						}
					}

					if (!validSpaceFound) {
						//no valid space found around portal. get top solid block instead
						worldOriginBlockPos = destinationWorld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, world.func_234938_ad_(), 8));
					}

					playerVec3Pos = Vector3d.copyCentered(worldOriginBlockPos).add(0, -0.3D, 0); // Set where player spawns
				}

			}
			else {
				// Otherwise, just go to where our stored location is
				if (enteringUA) {
					// Will never be null because we did check above for null already.
					playerVec3Pos = cap.getUAPos();
				}
				else {
					// Check for null which would be impressive if it occurs
					if (cap.getNonUAPos() == null || UltraAmplifiedDimension.UADimensionConfig.forceExitToOverworld.get())
					{
						// Set player at world spawn then with Amplified Portal at feet
						// The portal will try to not replace any block and be at the next air block above non-air blocks.
						BlockPos playerBlockPos = destinationWorld.getHeight(Heightmap.Type.MOTION_BLOCKING, destinationWorld.getSpawnPoint());
						BlockState blockState = destinationWorld.getBlockState(playerBlockPos);
						while (blockState.getMaterial() != Material.AIR && playerBlockPos.getY() < destinationWorld.func_234938_ad_() - 2) {
							playerBlockPos = playerBlockPos.up();
							blockState = destinationWorld.getBlockState(playerBlockPos);
						}

						destinationWorld.setBlockState(playerBlockPos, UADBlocks.AMPLIFIEDPORTAL.get().getDefaultState());

						playerVec3Pos = Vector3d.copyCentered(playerBlockPos).add(0, 0.5D, 0);
					}
					else {
						// Get position in non UA dimension as it isn't null
						playerVec3Pos = cap.getNonUAPos();
					}
				}
				playerChunkPos = new ChunkPos(new BlockPos(playerVec3Pos));
			}

			destinationWorld.getChunkProvider().registerTicket(TicketType.POST_TELEPORT, playerChunkPos, 1, playerEntity.getEntityId());

			//dunno how a sleeping player clicked on the portal but if they do, they wake up
			if (playerEntity.isSleeping()) {
				playerEntity.wakeUp();
			}

			playerEntity.fallDistance = 0;
			((ServerPlayerEntity) playerEntity).teleport(destinationWorld, playerVec3Pos.getX(), playerVec3Pos.getY() + 0.2D, playerVec3Pos.getZ(), yaw, pitch);
			return ActionResultType.SUCCESS;
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
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid)
	{

		// if player is in creative mode, just remove block completely
		if (player != null && player.isCreative()) {
			getBlock().onBlockHarvested(world, pos, state, player);
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			return true;
		}

		// otherwise, check to see if we are mining the highest portal block at world
		// origin in UA dimension
		else {
			// if we are in UA dimension
			if (world.getDimensionKey().equals(UADDimension.UAD_WORLD_KEY)) {

				// if we are at default portal coordinate
				if (pos.getX() == 8 && pos.getZ() == 8) {

					// finds the highest portal at world origin
					BlockPos posOfHighestPortal = new BlockPos(pos.getX(), world.func_234938_ad_(), pos.getZ());
					while (posOfHighestPortal.getY() >= 0) {
						Block blockToCheck = world.getBlockState(posOfHighestPortal).getBlock();
						if (blockToCheck == UADBlocks.AMPLIFIEDPORTAL.get()) {
							break;
						}

						posOfHighestPortal = posOfHighestPortal.down();
					}

					// if this block being broken is the highest portal, return false to end method
					// and not break the portal block
					if (posOfHighestPortal.getY() == pos.getY()) {
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
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}

	
	/**
	 * Spawns with tons of particles upon creation
	 */
	@Deprecated
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
		createLotsOfParticles((ServerWorld)world, pos, world.rand);
	}

	public void createLotsOfParticles(ServerWorld world, BlockPos position, Random random) {
		double xPos = (double) position.getX() + 0.5D;
		double yPos = (double) position.getY() + 0.5D;
		double zPos = (double) position.getZ() + 0.5D;
		double xOffset = (random.nextFloat() - 0.4D) * 0.8D;
		double zOffset = (random.nextFloat() - 0.4D) * 0.8D;

		world.spawnParticle(ParticleTypes.FLAME, xPos, yPos, zPos, 50, xOffset, 0, zOffset, random.nextFloat() * 0.1D + 0.05D);
	}


	/**
	 * more frequent particles than normal EndPortal block
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World world, BlockPos pos, Random rand) {
		double d0 = pos.getX() + (rand.nextFloat() * 3 - 1);
		double d1 = pos.getY() + (rand.nextFloat() * 3 - 1);
		double d2 = pos.getZ() + (rand.nextFloat() * 3 - 1);
		world.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}
}
