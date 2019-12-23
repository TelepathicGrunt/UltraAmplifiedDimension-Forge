package net.TelepathicGrunt.UltraAmplified.blockbehavior;


import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.TelepathicGrunt.UltraAmplified.UltraAmplified;
import net.TelepathicGrunt.UltraAmplified.Blocks.BlocksAndItemsInit;
import net.TelepathicGrunt.UltraAmplified.World.dimension.UATeleporter;
import net.TelepathicGrunt.UltraAmplified.capabilities.IPlayerPosAndDim;
import net.TelepathicGrunt.UltraAmplified.capabilities.PlayerPositionAndDimension;

@Mod.EventBusSubscriber(modid = UltraAmplified.MOD_ID)
public class AmplifiedPortalBehavior {
	private static final IBlockState POLISHED_DIORITE = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE_SMOOTH);
	private static final IBlockState POLISHED_GRANITE = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE_SMOOTH);
	private static final IBlockState STONE_BRICK_SLAB_TOP = Blocks.STONE_SLAB2.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP);
	private static final IBlockState STONE_BRICK_SLAB_BOTTOM = Blocks.STONE_SLAB2.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM);

	
	@CapabilityInject(IPlayerPosAndDim.class)
    public static Capability<IPlayerPosAndDim> PAST_POS_AND_DIM = null;
	
	@Mod.EventBusSubscriber(modid = UltraAmplified.MOD_ID)
	private static class ForgeEvents {

		@SubscribeEvent
		public static void BlockRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
			World worldIn = event.getWorld();
			Entity entityIn = event.getEntity();
			MinecraftServer minecraftserver = entityIn.getServer();

			// checks to see if player uses right click on amplified portal and if so
			// teleports player to other dimension
			if (event.getWorld().getBlockState(event.getPos()) == BlocksAndItemsInit.AMPLIFIEDPORTAL.getDefaultState())
			{
				//extra checking to make sure it's just the player alone and not riding, being ridden, etc 
				//Also makes sure player isn't sneaking so players can crouch place blocks on the portal
				if(!worldIn.isRemote && !entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && !((EntityPlayer)entityIn).isSneaking())
				{
					//grabs the capability attached to player for dimension hopping
					PlayerPositionAndDimension cap = (PlayerPositionAndDimension) entityIn.getCapability(PAST_POS_AND_DIM, EnumFacing.UP);
					boolean firstTime = false;
					
					//gets previous dimension
					DimensionType destination;
					if(cap.getDim() == null) {
						//first trip will always take player to ultra amplified dimension
						//as default dim for cap is always null when player hasn't teleported yet
						destination = UltraAmplified.UADimType;
						firstTime = true;
						
			    	   //double check for portal as this class gets made multiple times
			    	   //before one can save the fact that the portal was made.
			    	   //So a manual check is made for the portal as a double check.
			    	   if(!checkForGeneratedPortal(minecraftserver.getWorld(destination.getId()))) {
			        	   generatePortal(minecraftserver.getWorld(destination.getId()));
			    	   }
					}
					else if(cap.getDim().getId() == entityIn.dimension){
						// if our stored dimension somehow ends up being our current dimension and isn't the ultra amplified dimension, 
						// then just take us to ultra amplified dimension instead
						if(cap.getDim() != UltraAmplified.UADimType) {
							destination = UltraAmplified.UADimType;
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
							
					WorldServer serverworld = minecraftserver.getWorld(destination.getId());
					
					//gets top block in other world or original location
					BlockPos blockpos = new BlockPos(8, 0, 8);
					if(firstTime || cap.getDim().getId() == entityIn.dimension) {
						//if it is player's first time teleporting or our stored dimension was our current dimension, 
						//find top block at world origin closest to portal
						
						
						int portalY = 255;
						BlockPos pos = new BlockPos(8, 0, 8);
						
						//finds where portal block is
						while(portalY > 0) {
							if(serverworld.getBlockState(pos.up(portalY)) == BlocksAndItemsInit.AMPLIFIEDPORTAL.getDefaultState()) {
								break;
							}
							portalY--;
						}
						
						//not sure how the portal block was not found but if so, spawn player at highest piece of land
						if(portalY == 0) {
							blockpos = serverworld.getHeight(new BlockPos(10, 255, 8));
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
								blockpos = serverworld.getHeight(new BlockPos(10, 255, 8));
							}
						}
						
						
					}else {
						//otherwise, just go to where our stored location is
						blockpos = cap.getPos();
					}

					
					 //dunno how a sleeping player clicked on the portal but if they do, they wake up
			         if (((EntityPlayer)entityIn).isPlayerSleeping()) {
			            ((EntityPlayer)entityIn).wakeUpPlayer(true, true, false);
			         }
			         
	
					//store current blockpos and dim before teleporting
					cap.setDim(DimensionType.getById(entityIn.dimension));
					cap.setPos(new BlockPos(entityIn.posX, entityIn.posY, entityIn.posZ));
					
					
		            UATeleporter.teleportToDimension(event.getEntityPlayer(), destination.getId(), blockpos);
				}
			}
			

			// checks to see if player uses right click with flint and steel on Polished Diorite. 
			//If so, tries to create portal if possible. only works in non-ultra amplified world types
			if (worldIn.getWorldType() != UltraAmplified.UltraAmplifiedWorldType && 
					worldIn.getBlockState(event.getPos()) == POLISHED_DIORITE &&
				    event.getItemStack().getItem() == Items.FLINT_AND_STEEL) 
			{
				BlocksAndItemsInit.AMPLIFIEDPORTAL.trySpawnPortal(worldIn, event.getPos());
			}
		}
		

		
	    private static boolean checkForGeneratedPortal(World worldIn) {
	    	BlockPos pos = new BlockPos(8, 255, 8);
	    	worldIn.getChunkFromBlockCoords(pos);
	    	
	    	while(pos.getY() >= 0) {
	    		if(worldIn.getBlockState(pos) == BlocksAndItemsInit.AMPLIFIEDPORTAL.getDefaultState()) {
	    			return true;
	    		}
	    		pos = pos.down();
	    	}
	    	
	    	return false;
	    }
	    
	    private static void generatePortal(World worldIn) {
	    	BlockPos pos = new BlockPos(8, 255, 8);
	    	worldIn.getChunkFromBlockCoords(pos);
	    	
	    	pos = worldIn.getHeight(pos);
	    	if(pos.getY() > 252) {
	    		pos = pos.down(3);
	    	}
	    	else if(pos.getY() < 6) {
	    		pos = new BlockPos(pos.getX(), 6, pos.getZ());
	    	}

	    	  //7x7 flooring around bottom of frame
	 	   for(int x = -3; x <= 3; x++) {
	 		   for(int z = -3; z <= 3; z++) {
	 			  worldIn.setBlockState(pos.add(x, -1, z), POLISHED_GRANITE, 3);
	 		   }
	 	   }
	 	   
	 	   
	 	   //bottom of portal frame
	 	   for(int x = -1; x <= 1; x++) {
	 		   for(int z = -1; z <= 1; z++) {
	 			   if(Math.abs(x*z) == 1) {
	 				   worldIn.setBlockState(pos.add(x, 0, z), POLISHED_GRANITE, 3);
	 			   }else {
	 				   //sets slab but also waterlogs it if block it replaces is water based
	 				   worldIn.setBlockState(
	 						   pos.add(x, 0, z), 
	 						   STONE_BRICK_SLAB_BOTTOM, 
	 						   3);
	 			   }
	 		   }
	 	   }
	 	   

	 	   //the portal itself
	 	   worldIn.setBlockState(pos.add(0, 1, 0), BlocksAndItemsInit.AMPLIFIEDPORTAL.getDefaultState(), 3);

	 	   //top of portal frame
	 	   for(int x = -1; x <= 1; x++) {
	 		   for(int z = -1; z <= 1; z++) {
	 			   if(Math.abs(x*z) == 1) {
	 				   worldIn.setBlockState(pos.add(x, 2, z), POLISHED_GRANITE, 3);
	 			   }else {
	 				   //sets slab but also waterlogs it if block it replaces is water based
	 				   worldIn.setBlockState(
	 						   pos.add(x, 2, z), 
	 						   STONE_BRICK_SLAB_TOP, 
	 						   3);
	 			   }
	 		   }
	 	   }
	 	}
	}

}
