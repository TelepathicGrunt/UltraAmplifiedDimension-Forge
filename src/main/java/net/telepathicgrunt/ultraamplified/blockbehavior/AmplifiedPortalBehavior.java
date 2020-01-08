package net.telepathicgrunt.ultraamplified.blockbehavior;


import java.util.Random;

import javax.annotation.Nullable;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.blocks.BlocksInit;
import net.telepathicgrunt.ultraamplified.capabilities.IPlayerPosAndDim;
import net.telepathicgrunt.ultraamplified.capabilities.PlayerPositionAndDimension;
import net.telepathicgrunt.ultraamplified.world.dimension.UltraAmplifiedDimension;
import net.telepathicgrunt.ultraamplified.world.feature.AmplifiedPortalFrame;

@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AmplifiedPortalBehavior {
	
	
	@CapabilityInject(IPlayerPosAndDim.class)
    public static Capability<IPlayerPosAndDim> PAST_POS_AND_DIM = null;
	
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents {

		@SubscribeEvent
		public static void BlockRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
			World world = event.getWorld();
			Entity entityIn = event.getEntity();
			MinecraftServer minecraftserver = entityIn.getServer();

			// checks to see if player uses right click on amplified portal and if so
			// teleports player to other dimension
			if (event.getWorld().getBlockState(event.getPos()) == BlocksInit.AMPLIFIEDPORTAL.get().getDefaultState())
			{
				//extra checking to make sure it's just the player alone and not riding, being ridden, etc 
				//Also makes sure player isn't sneaking so players can crouch place blocks on the portal
				if(!world.isRemote && !entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && !((PlayerEntity)entityIn).isCrouching())
				{
					//grabs the capability attached to player for dimension hopping
					PlayerPositionAndDimension cap = (PlayerPositionAndDimension) entityIn.getCapability(PAST_POS_AND_DIM).orElseThrow(RuntimeException::new);
					boolean firstTime = false;
					
					//gets previous dimension
					DimensionType destination;
					if(cap.getDim() == null) {
						//first trip will always take player to ultra amplified dimension
						//as default dim for cap is always null when player hasn't teleported yet
						destination = UltraAmplifiedDimension.ultraamplified();
						firstTime = true;
						
			    	   //double check for portal as this class gets made multiple times
			    	   //before one can save the fact that the portal was made.
			    	   //So a manual check is made for the portal as a double check.
			    	   if(!checkForGeneratedPortal(minecraftserver.getWorld(destination))) {
			        	   generatePortal(minecraftserver.getWorld(destination));
			    	   }
					}
					else if(cap.getDim() == entityIn.dimension){
						// if our stored dimension somehow ends up being our current dimension and isn't the ultra amplified dimension, 
						// then just take us to ultra amplified dimension instead
						if(cap.getDim() != UltraAmplifiedDimension.ultraamplified()) {
							destination = UltraAmplifiedDimension.ultraamplified();
						}
						//if the store dimension and player dimension is ultra amplified world, take us to overworld
						else {
							destination = DimensionType.OVERWORLD;
						}
					}
					else {
						//gets stored dimension
						destination = cap.getDim();
					}
							
					ServerWorld serverworld = minecraftserver.getWorld(destination);
					
					//gets top block in other world or original location
					BlockPos blockpos = new BlockPos(8, 0, 8);
					ChunkPos chunkpos;
					if(firstTime || cap.getDim() == entityIn.dimension) {
						//if it is player's first time teleporting or our stored dimension was our current dimension, 
						//find top block at world origin closest to portal
						
						chunkpos = new ChunkPos(new BlockPos(10, 255, 8));
						
						int portalY = 255;
						BlockPos pos = new BlockPos(8, 0, 8);
						
						//finds where portal block is
						while(portalY > 0) {
							if(serverworld.getBlockState(pos.up(portalY)) == BlocksInit.AMPLIFIEDPORTAL.get().getDefaultState()) {
								break;
							}
							portalY--;
						}
						
						//not sure how the portal block was not found but if so, spawn player at highest piece of land
						if(portalY == 0) {
							blockpos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 255, 8));
						}else {
							//portal was found so try to find 2 air spaces around it that the player can spawn at
							pos = pos.up(portalY-1);
							boolean validSpaceFound = false;
							
							for(int x = -2; x < 3; x++) 
							{
								for(int z = -2; z < 3; z++) 
								{
									if(x == -2 || x == 2 || z == -2 || z == 2) 
									{
										if(serverworld.getBlockState(pos.add(x, 0, z)).getMaterial() == Material.AIR &&
											serverworld.getBlockState(pos.add(x, 1, z)).getMaterial() == Material.AIR) 
										{
											//valid space for player is found
											blockpos = pos.add(x, 0, z);
											validSpaceFound = true;
											z=3;
											x=3;
										}
									}
								}
							}
							
							if(!validSpaceFound) {
								//no valid space found around portal. get top solid block instead
								blockpos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 255, 8));
							}
						}
						
						
					}else {
						//otherwise, just go to where our stored location is
						blockpos = cap.getPos();
						chunkpos = new ChunkPos(blockpos);
					}

					serverworld.getChunkProvider().func_217228_a(TicketType.POST_TELEPORT, chunkpos, 1, entityIn.getEntityId());
					
					 //dunno how a sleeping player clicked on the portal but if they do, they wake up
			         if (((ServerPlayerEntity)entityIn).isSleeping()) {
			            ((ServerPlayerEntity)entityIn).wakeUp();
			         }
			         
	
					//store current blockpos and dim before teleporting
					cap.setDim(entityIn.dimension);
					cap.setPos(entityIn.getPosition());
					
					
					((ServerPlayerEntity)entityIn).teleport(
							minecraftserver.getWorld(destination), 
							blockpos.getX()+0.5D, 
							blockpos.getY()+1, 
							blockpos.getZ()+0.5D, 
							entityIn.rotationYaw, 
							entityIn.rotationPitch);
				}
			}
			

			// checks to see if player uses right click with flint and steel. If so, tries
			// to create portal if possible. only works in non-ultra amplified world types
			if (world.getWorldType() != UltraAmplified.UltraAmplifiedWorldType && 
				    event.getItemStack().getItem() == Items.FLINT_AND_STEEL) 
			{
				trySpawnPortal(world, event.getPos());
			}
		}
		

		// ------------------------------------------------------------------------------------//
		// Portal creation and validation check

		private static final BlockState POLISHED_GRANITE = Blocks.POLISHED_GRANITE.getDefaultState();
		private static final BlockState POLISHED_DIORITE = Blocks.POLISHED_DIORITE.getDefaultState();
		private static final BlockState POLISHED_ANDESITE_SLAB_TOP = Blocks.POLISHED_ANDESITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
		private static final BlockState POLISHED_ANDESITE_SLAB_BOTTOM = Blocks.POLISHED_ANDESITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);

	    private static boolean checkForGeneratedPortal(World world) {
	    	BlockPos pos = new BlockPos(8, 255, 8);
	    	world.getChunkAt(pos);
	    	
	    	while(pos.getY() >= 0) {
	    		if(world.getBlockState(pos) == BlocksInit.AMPLIFIEDPORTAL.get().getDefaultState()) {
	    			return true;
	    		}
	    		pos = pos.down();
	    	}
	    	
	    	return false;
	    }
	    
	    
	    private static void generatePortal(World world) {
	    	AmplifiedPortalFrame amplifiedportalfeature = new AmplifiedPortalFrame();
	    	BlockPos pos = new BlockPos(8, 255, 8);
	    	world.getChunkAt(pos);
	    	
	    	pos = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
	    	if(pos.getY() > 252) {
	    		pos = pos.down(3);
	    	}
	    	else if(pos.getY() < 6) {
	    		pos = new BlockPos(pos.getX(), 6, pos.getZ());
	    	}

	    	amplifiedportalfeature.place(world, new Random(), pos, IFeatureConfig.NO_FEATURE_CONFIG);
	 	}
	    
	    
		public static boolean isValid(IWorld world, BlockPos pos)
		{

			// bottom of portal frame
			for (int x = -1; x <= 1; x++)
			{
				for (int z = -1; z <= 1; z++)
				{
					if (Math.abs(x * z) == 1)
					{
						if (world.getBlockState(pos.add(x, -1, z)) != POLISHED_GRANITE)
						{
							return false;
						}
					}
					else
					{
						if (world.getBlockState(pos.add(x, -1, z)) != POLISHED_ANDESITE_SLAB_BOTTOM)
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
						if (world.getBlockState(pos.add(x, 1, z)) != POLISHED_GRANITE)
						{
							return false;
						}
					}
					else
					{
						if (world.getBlockState(pos.add(x, 1, z)) != POLISHED_ANDESITE_SLAB_TOP)
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

		public static boolean trySpawnPortal(IWorld world, BlockPos pos)
		{

			// cannot create amplified portal in Ultra Amplified Worldtype
			if (world.getWorld().getWorldType() == UltraAmplified.UltraAmplifiedWorldType)
			{
				return false;
			}

			boolean canMakePortal = isPortal(world, pos);
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
		public static boolean isPortal(IWorld world, BlockPos pos)
		{
			if (isValid(world, pos))
			{
				return true;
			}

			return false;
		}

	}

}
