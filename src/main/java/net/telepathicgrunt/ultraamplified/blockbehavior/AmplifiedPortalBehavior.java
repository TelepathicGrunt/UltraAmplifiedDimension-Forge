package net.telepathicgrunt.ultraamplified.blockbehavior;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.blocks.BlocksInit;
import net.telepathicgrunt.ultraamplified.capabilities.IPlayerPosAndDim;
import net.telepathicgrunt.ultraamplified.capabilities.PlayerPositionAndDimension;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.dimension.UltraAmplifiedDimension;
import net.telepathicgrunt.ultraamplified.world.feature.AmplifiedPortalFrame;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AmplifiedPortalBehavior
{

	@CapabilityInject(IPlayerPosAndDim.class)
	public static Capability<IPlayerPosAndDim> PAST_POS_AND_DIM = null;

	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{

		@SubscribeEvent
		public static void BlockRightClickEvent(PlayerInteractEvent.RightClickBlock event)
		{
			World world = event.getWorld();
			Entity entity = event.getEntity();
			MinecraftServer minecraftserver = entity.getServer();

			// Checks to see if player uses right click on amplified portal and if so
			// teleports player to other dimension.
			if (event.getWorld().getBlockState(event.getPos()) == BlocksInit.AMPLIFIEDPORTAL.get().getDefaultState())
			{
				// Extra checking to make sure it's just the player alone and not riding, being ridden, etc 
				// Also makes sure player isn't sneaking so players can crouch place blocks on the portal
				// But only teleport if we aren't in UA worldtype
				if (!world.isRemote && 
					minecraftserver.getWorld(DimensionType.OVERWORLD).getWorldType() != UltraAmplified.UltraAmplifiedWorldType &&
					!entity.isPassenger() && 
					!entity.isBeingRidden() && 
					entity.isNonBoss() 
					&& !((PlayerEntity) entity).isCrouching())
				{
					//grabs the capability attached to player for dimension hopping
					PlayerPositionAndDimension cap = (PlayerPositionAndDimension) entity.getCapability(PAST_POS_AND_DIM).orElseThrow(RuntimeException::new);

					// Gets previous dimension
					DimensionType destination;
					float pitch = 3.75F; // Looking straight ahead
					float yaw = 0; // Facing north 
					boolean enteringUA = false;

					// Player is leaving Ultra Amplified dimension
					if (entity.dimension == UltraAmplifiedDimension.ultraamplified())
					{
						if (ConfigUA.forceExitToOverworld)
						{
							// Go to Overworld directly because of config option.
							destination = DimensionType.OVERWORLD;
						}
						else
						{
							// Gets stored dimension
							destination = cap.getNonUADim();
							
							// Impressive if this is reached...........
							if(destination == null)
							{
								destination = DimensionType.OVERWORLD;
							}
						}

						// Get direction to face for Non-UA dimension
						pitch = cap.getNonUAPitch();
						yaw = cap.getNonUAYaw();
						
						// Set current UA position and rotations
						cap.setUAPos(entity.getPositionVec());
						cap.setUAPitch(entity.rotationPitch);
						cap.setUAYaw(entity.rotationYaw);
					}

					// Otherwise, take us to Ultra Amplified Dimension.
					else
					{
						destination = UltraAmplifiedDimension.ultraamplified();
						pitch = cap.getUAPitch();
						yaw = cap.getUAYaw();
						enteringUA = true;
						
						// Set current nonUA position, rotations, and dimension before teleporting
						cap.setNonUAPos(entity.getPositionVec());
						cap.setNonUADim(entity.dimension);
						cap.setNonUAPitch(entity.rotationPitch);
						cap.setNonUAYaw(entity.rotationYaw);
					}

					
					
					
					

					ServerWorld serverworld = minecraftserver.getWorld(destination);

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
							if (serverworld.getBlockState(worldOriginBlockPos.up(portalY)) == BlocksInit.AMPLIFIEDPORTAL.get().getDefaultState())
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
										if (serverworld.getBlockState(worldOriginBlockPos.add(x, 0, z)).getMaterial() == Material.AIR && 
											serverworld.getBlockState(worldOriginBlockPos.add(x, 1, z)).getMaterial() == Material.AIR)
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
						if(enteringUA)
						{
							// Will never be null because we did check above for null already.
							playerVec3Pos = cap.getUAPos();
						}
						else 
						{
							// Check for null which would be impressive if it occurs
							if(cap.getNonUAPos() == null || ConfigUA.forceExitToOverworld)
							{
								// Set player at world spawn then with Amplified Portal at feet
								// The portal will try to not replace any block and be at the next air block above non-air blocks.
								BlockPos playerBlockPos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING, serverworld.getSpawnPoint());
								BlockState blockState = serverworld.getBlockState(playerBlockPos);
								while(blockState.getMaterial() != Material.AIR && playerBlockPos.getY() < serverworld.getActualHeight() - 2)
								{
									playerBlockPos = playerBlockPos.up();
									blockState = serverworld.getBlockState(playerBlockPos);
								}
								
								serverworld.setBlockState(playerBlockPos, BlocksInit.AMPLIFIEDPORTAL.get().getDefaultState());
								
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

					
					serverworld.getChunkProvider().registerTicket(TicketType.POST_TELEPORT, playerChunkPos, 1, entity.getEntityId());

					//dunno how a sleeping player clicked on the portal but if they do, they wake up
					if (((ServerPlayerEntity) entity).isSleeping())
					{
						((ServerPlayerEntity) entity).wakeUp();
					}


					((ServerPlayerEntity) entity).teleport(
							minecraftserver.getWorld(destination), 
							playerVec3Pos.getX(), 
							playerVec3Pos.getY() + 0.2D, 
							playerVec3Pos.getZ(), 
							yaw, 
							pitch);
				}
			}

			// checks to see if player uses right click with flint and steel. If so, tries
			// to create portal if possible. only works in non-ultra amplified world types
			if (world.getWorldType() != UltraAmplified.UltraAmplifiedWorldType && 
				event.getItemStack().getItem() == Items.FLINT_AND_STEEL)
			{
				trySpawnPortal(world, event.getPos(), entity);
			}
		}


		// Fires when entering a world or dimension
		@SubscribeEvent
		public static void worldLoad(WorldEvent.Load event)
		{
			//check for if portal was made in UA and if not, make it.
			if(event.getWorld().getDimension().getType() == UltraAmplifiedDimension.ultraamplified())
			{
				IWorld worldUA = event.getWorld();
				if (!checkForGeneratedPortal(worldUA))
				{
					generatePortal(worldUA);
				}
			}
		}
	}

	// ------------------------------------------------------------------------------------//
	// Portal creation and validation check

	private static final BlockState POLISHED_GRANITE = Blocks.POLISHED_GRANITE.getDefaultState();
	private static final BlockState POLISHED_DIORITE = Blocks.POLISHED_DIORITE.getDefaultState();
	private static final BlockState POLISHED_ANDESITE_SLAB_TOP = Blocks.POLISHED_ANDESITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
	private static final BlockState POLISHED_ANDESITE_SLAB_BOTTOM = Blocks.POLISHED_ANDESITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);


	private static boolean checkForGeneratedPortal(IWorld worldUA)
	{
		BlockPos pos = new BlockPos(8, 255, 8);
		worldUA.getChunk(pos);

		while (pos.getY() >= 0)
		{
			if (worldUA.getBlockState(pos) == BlocksInit.AMPLIFIEDPORTAL.get().getDefaultState())
			{
				return true;
			}
			pos = pos.down();
		}

		return false;
	}


	private static void generatePortal(IWorld worldUA)
	{
		AmplifiedPortalFrame amplifiedportalfeature = new AmplifiedPortalFrame();
		BlockPos pos = new BlockPos(8, 255, 8);
		worldUA.getChunk(pos);

		pos = worldUA.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
		if (pos.getY() > 252)
		{
			pos = pos.down(3);
		}
		else if (pos.getY() < 6)
		{
			pos = new BlockPos(pos.getX(), 6, pos.getZ());
		}

		amplifiedportalfeature.place(worldUA, new Random(), pos, IFeatureConfig.NO_FEATURE_CONFIG);
	}


	public static boolean isValid(IWorld world, BlockPos pos, Entity entity)
	{
		ForgeRegistry<Block> registry = ((ForgeRegistry<Block>) ForgeRegistries.BLOCKS);

		// Grabs resourcelocation from config and tries to find that block to use for corners
		// Doesn't need to be Blockstate as Polished Granite has only 1 state anyway
		Block blockCorner;
		if (!ConfigUA.portalCornerBlocks.equals(""))
		{
			if (registry.containsKey(new ResourceLocation(ConfigUA.portalCornerBlocks)))
			{
				blockCorner = registry.getValue(new ResourceLocation(ConfigUA.portalCornerBlocks));
			}
			else
			{
				if (entity instanceof ServerPlayerEntity)
				{
					ITextComponent message = new StringTextComponent(
							"§eUltra Amplified Dimension: §fI could not find the block with the resourcelocation that was"
							+ " put in the portal frame corner config! Here is what was in the config: §c" + ConfigUA.portalCornerBlocks);
					entity.sendMessage(message);
				}

				return false;
			}
		}
		else
		{
			blockCorner = POLISHED_GRANITE.getBlock();
		}

		//grabs resourcelocation from config and tries to find that block to use for ceiling
		BlockState blockCeiling;
		boolean customCeiling = false;
		if (!ConfigUA.portalCeilingBlocks.equals(""))
		{
			if (registry.containsKey(new ResourceLocation(ConfigUA.portalCeilingBlocks)))
			{
				blockCeiling = registry.getValue(new ResourceLocation(ConfigUA.portalCeilingBlocks)).getDefaultState();
				customCeiling = true;
			}
			else
			{
				if (entity instanceof ServerPlayerEntity)
				{
					ITextComponent message = new StringTextComponent(
							"§eUltra Amplified Dimension: §fI could not find the block with the resourcelocation that was"
							+ " put in the portal frame ceiling config! Here is what was in the config: §c" + ConfigUA.portalCeilingBlocks);
					entity.sendMessage(message);
				}

				return false;
			}
		}
		else
		{
			blockCeiling = POLISHED_ANDESITE_SLAB_TOP;
		}

		//grabs resourcelocation from config and tries to find that block to use for floor
		BlockState blockFloor;
		boolean customFloor = false;
		if (!ConfigUA.portalFloorBlocks.equals(""))
		{
			if (registry.containsKey(new ResourceLocation(ConfigUA.portalFloorBlocks)))
			{
				blockFloor = registry.getValue(new ResourceLocation(ConfigUA.portalFloorBlocks)).getDefaultState();
				customFloor = true;
			}
			else
			{
				if (entity instanceof ServerPlayerEntity)
				{
					ITextComponent message = new StringTextComponent(
							"§eUltra Amplified Dimension: §fI could not find the block with the resourcelocation that was"
							+ " put in the portal frame floor config! Here is what was in the config: §c" + ConfigUA.portalFloorBlocks);
					entity.sendMessage(message);
				}

				return false;
			}
		}
		else
		{
			blockFloor = POLISHED_ANDESITE_SLAB_BOTTOM;
		}

		// bottom of portal frame
		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				// Floor corners
				if (Math.abs(x * z) == 1)
				{
					if (world.getBlockState(pos.add(x, -1, z)).getBlock() != blockCorner)
					{
						return false;
					}
				}
				
				// Plus shape on floor
				else
				{
					BlockState currentFloor = world.getBlockState(pos.add(x, -1, z));
					// Convert to default block if use changed the floor block in config so we can ignore properties of the block
					if(customFloor)
					{
						currentFloor = currentFloor.getBlock().getDefaultState(); 
					}
					
					
					if (currentFloor != blockFloor)
					{
						return false;
					}
				}
			}
		}

		// the center itself
		if (world.getBlockState(pos.add(0, 0, 0)) != POLISHED_DIORITE)
		{
			return false;
		}

		// top of portal frame
		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				// Top corners
				if (Math.abs(x * z) == 1)
				{
					if (world.getBlockState(pos.add(x, 1, z)).getBlock() != blockCorner)
					{
						return false;
					}
				}
				// Plus shape on ceiling
				else
				{
					BlockState currentCeiling = world.getBlockState(pos.add(x, 1, z));
					// Convert to default block if use changed the ceiling block in config so we can ignore properties of the block
					if(customCeiling)
					{
						currentCeiling = currentCeiling.getBlock().getDefaultState(); 
					}
					
					if (currentCeiling != blockCeiling)
					{
						return false;
					}
				}
			}
		}

		return true;
	}


	public static void placePortalBlocks(IWorld world, BlockPos pos)
	{
		// the portal itself
		world.setBlockState(pos.add(0, 0, 0), BlocksInit.AMPLIFIEDPORTAL.get().getDefaultState(), 18);
	}


	public static boolean trySpawnPortal(IWorld world, BlockPos pos, Entity entity)
	{

		// cannot create amplified portal in Ultra Amplified Worldtype
		if (world.getWorld().getWorldType() == UltraAmplified.UltraAmplifiedWorldType)
		{
			return false;
		}

		boolean canMakePortal = isPortal(world, pos, entity);
		if (canMakePortal)
		{
			placePortalBlocks(world, pos);
			return true;
		}
		else
		{
			return false;
		}
	}


	@Nullable
	public static boolean isPortal(IWorld world, BlockPos pos, Entity entity)
	{
		if (isValid(world, pos, entity))
		{
			return true;
		}

		return false;
	}

}
