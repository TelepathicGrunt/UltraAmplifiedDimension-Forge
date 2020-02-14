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
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
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

			// checks to see if player uses right click on amplified portal and if so
			// teleports player to other dimension
			if (event.getWorld().getBlockState(event.getPos()) == BlocksInit.AMPLIFIEDPORTAL.get().getDefaultState())
			{
				//extra checking to make sure it's just the player alone and not riding, being ridden, etc 
				//Also makes sure player isn't sneaking so players can crouch place blocks on the portal
				if (!world.isRemote && !entity.isPassenger() && !entity.isBeingRidden() && entity.isNonBoss() && !((PlayerEntity) entity).isCrouching())
				{
					//grabs the capability attached to player for dimension hopping
					PlayerPositionAndDimension cap = (PlayerPositionAndDimension) entity.getCapability(PAST_POS_AND_DIM).orElseThrow(RuntimeException::new);

					// Gets previous dimension
					DimensionType destination;
					boolean enteringUA = false;

					// Player is leaving Ultra Amplified dimension
					if (entity.dimension == UltraAmplifiedDimension.ultraamplified())
					{
						if (ConfigUA.forceExitToOverworld)
						{
							// go to Overworld directly because of config option.
							destination = DimensionType.OVERWORLD;
						}
						else
						{
							//gets stored dimension
							destination = cap.getNonUADim();
						}
						
						//set current UA position
						cap.setUAPos(entity.getPosition());
					}

					// Otherwise, take us to Ultra Amplified Dimension.
					else
					{
						destination = UltraAmplifiedDimension.ultraamplified();
						enteringUA = true;
						
						//set current nonUA position and dimension before teleporting
						cap.setNonUAPos(entity.getPosition());
						cap.setNonUADim(entity.dimension);
					}

					
					
					
					

					ServerWorld serverworld = minecraftserver.getWorld(destination);

					//gets top block in other world or original location
					BlockPos blockpos = new BlockPos(8, 0, 8);
					ChunkPos chunkpos;
					if (enteringUA && cap.getUAPos() == null)
					{
						//if it is player's first time teleporting to UA dimension, 
						//find top block at world origin closest to portal

						chunkpos = new ChunkPos(new BlockPos(10, 255, 8));

						int portalY = 255;
						BlockPos pos = new BlockPos(8, 0, 8);

						//finds where portal block is
						while (portalY > 0)
						{
							if (serverworld.getBlockState(pos.up(portalY)) == BlocksInit.AMPLIFIEDPORTAL.get().getDefaultState())
							{
								break;
							}
							portalY--;
						}

						//not sure how the portal block was not found but if so, spawn player at highest piece of land
						if (portalY == 0)
						{
							blockpos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 255, 8));
						}
						else
						{
							//portal was found so try to find 2 air spaces around it that the player can spawn at
							pos = pos.up(portalY - 1);
							boolean validSpaceFound = false;

							for (int x = -2; x < 3; x++)
							{
								for (int z = -2; z < 3; z++)
								{
									if (x == -2 || x == 2 || z == -2 || z == 2)
									{
										if (serverworld.getBlockState(pos.add(x, 0, z)).getMaterial() == Material.AIR && serverworld.getBlockState(pos.add(x, 1, z)).getMaterial() == Material.AIR)
										{
											//valid space for player is found
											blockpos = pos.add(x, 0, z);
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
								blockpos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 255, 8));
							}
						}

					}
					else
					{
						//otherwise, just go to where our stored location is
						if(enteringUA)
						{
							blockpos = cap.getUAPos();
						}
						else 
						{
							blockpos = cap.getNonUAPos();
						}
						chunkpos = new ChunkPos(blockpos);
					}

					
					serverworld.getChunkProvider().registerTicket(TicketType.POST_TELEPORT, chunkpos, 1, entity.getEntityId());

					//dunno how a sleeping player clicked on the portal but if they do, they wake up
					if (((ServerPlayerEntity) entity).isSleeping())
					{
						((ServerPlayerEntity) entity).wakeUp();
					}


					((ServerPlayerEntity) entity).teleport(minecraftserver.getWorld(destination), blockpos.getX() + 0.5D, blockpos.getY() + 1, blockpos.getZ() + 0.5D, entity.rotationYaw, entity.rotationPitch);
				}
			}

			// checks to see if player uses right click with flint and steel. If so, tries
			// to create portal if possible. only works in non-ultra amplified world types
			if (world.getWorldType() != UltraAmplified.UltraAmplifiedWorldType && event.getItemStack().getItem() == Items.FLINT_AND_STEEL)
			{
				trySpawnPortal(world, event.getPos(), entity);
			}
		}


		// Fires just before the teleportation to new dimension begins
		@SubscribeEvent
		public static void entityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
		{
//			if (event.getEntity() instanceof PlayerEntity)
//			{
//				// Updates the past dimension that the player is leaving
//				PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
//				PlayerPositionAndDimension cap = (PlayerPositionAndDimension) playerEntity.getCapability(PAST_POS_AND_DIM).orElseThrow(RuntimeException::new);
//				
//				//player is not entering UA dimension
//				if(playerEntity.dimension != UltraAmplifiedDimension.ultraamplified()) 
//				{
//					cap.setNonUADim(playerEntity.dimension);
//				}
//			}
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

		//grabs resourcelocation from config and tries to find that block to use for corners
		BlockState blockCorner;
		if (!ConfigUA.portalCornerBlocks.equals(""))
		{
			if (registry.containsKey(new ResourceLocation(ConfigUA.portalCornerBlocks)))
			{
				blockCorner = registry.getValue(new ResourceLocation(ConfigUA.portalCornerBlocks)).getDefaultState();
			}
			else
			{
				if (entity instanceof ServerPlayerEntity)
				{
					ITextComponent message = new StringTextComponent("Ultra Amplified Dimension: The block resourcelocation that is in the config for the portal frame corner cannot be found! Here is what was put in: " + ConfigUA.portalCornerBlocks);
					entity.sendMessage(message);
				}

				return false;
			}
		}
		else
		{
			blockCorner = POLISHED_GRANITE;
		}

		//grabs resourcelocation from config and tries to find that block to use for ceiling
		BlockState blockCeiling;
		if (!ConfigUA.portalCeilingBlocks.equals(""))
		{
			if (registry.containsKey(new ResourceLocation(ConfigUA.portalCeilingBlocks)))
			{
				blockCeiling = registry.getValue(new ResourceLocation(ConfigUA.portalCeilingBlocks)).getDefaultState();

				if (blockCeiling.getBlock() instanceof SlabBlock)
				{
					blockCeiling = blockCeiling.with(SlabBlock.TYPE, SlabType.TOP);
				}
			}
			else
			{
				if (entity instanceof ServerPlayerEntity)
				{
					ITextComponent message = new StringTextComponent("Ultra Amplified Dimension: The block resourcelocation that is in the config for the portal frame ceiling cannot be found! Here is what was put in: " + ConfigUA.portalCeilingBlocks);
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
		if (!ConfigUA.portalFloorBlocks.equals(""))
		{
			if (registry.containsKey(new ResourceLocation(ConfigUA.portalFloorBlocks)))
			{
				blockFloor = registry.getValue(new ResourceLocation(ConfigUA.portalFloorBlocks)).getDefaultState();

				if (blockFloor.getBlock() instanceof SlabBlock)
				{
					blockFloor = blockFloor.with(SlabBlock.TYPE, SlabType.BOTTOM);
				}
			}
			else
			{
				if (entity instanceof ServerPlayerEntity)
				{
					ITextComponent message = new StringTextComponent("Ultra Amplified Dimension: The block resourcelocation that is in the config for the portal frame floor cannot be found! Here is what was put in: " + ConfigUA.portalFloorBlocks);
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
				if (Math.abs(x * z) == 1)
				{
					if (world.getBlockState(pos.add(x, -1, z)) != blockCorner)
					{
						return false;
					}
				}
				else
				{
					if (world.getBlockState(pos.add(x, -1, z)) != blockFloor)
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
				if (Math.abs(x * z) == 1)
				{
					if (world.getBlockState(pos.add(x, 1, z)) != blockCorner)
					{
						return false;
					}
				}
				else
				{
					if (world.getBlockState(pos.add(x, 1, z)) != blockCeiling)
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
