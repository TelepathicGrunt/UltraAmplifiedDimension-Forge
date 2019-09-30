package net.telepathicgrunt.ultraamplified.blockbehavior;


import java.util.Random;

import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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
			World worldIn = event.getWorld();
			Entity entityIn = event.getEntity();
			MinecraftServer minecraftserver = entityIn.getServer();

			// checks to see if player uses right click on amplified portal and if so
			// teleports player to other dimension
			if (event.getWorld().getBlockState(event.getPos()) == BlocksInit.AMPLIFIEDPORTAL.getDefaultState())
			{
				//extra checking to make sure it's just the player alone and not riding, being ridden, etc 
				if(!worldIn.isRemote && !entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
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
						//if the store dimension and player dimension is ultra amplified world, take up to overworld
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
							if(serverworld.getBlockState(pos.up(portalY)) == BlocksInit.AMPLIFIEDPORTAL.getDefaultState()) {
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
			            ((ServerPlayerEntity)entityIn).wakeUpPlayer(true, true, false);
			         }
			         
	
					//store current blockpos and dim before teleporting
					cap.setDim(entityIn.dimension);
					cap.setPos(new BlockPos(entityIn.posX, entityIn.posY, entityIn.posZ));
					
					
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
			if (worldIn.getWorldType() != UltraAmplified.UltraAmplified && 
				    event.getItemStack().getItem() == Items.FLINT_AND_STEEL) 
			{
				BlocksInit.AMPLIFIEDPORTAL.trySpawnPortal(worldIn, event.getPos());
			}
		}
		

		//clicking on portal block in any dimension other than ultra amplified will turn the portal to polished diorite
		//In ultra amplified dimension, turns any diorite not at x=8, z=8 into diorite
		@SubscribeEvent
		public static void BlockLeftClickEvent(PlayerInteractEvent.LeftClickBlock event) {
			if(event.getWorld().getBlockState(event.getPos()) == BlocksInit.AMPLIFIEDPORTAL.getDefaultState()) {
				
				if (event.getWorld().getDimension().getType() == UltraAmplifiedDimension.ultraamplified()) {
						if(event.getPos().getX() != 8 || event.getPos().getZ() != 8) {
							event.getWorld().setBlockState(event.getPos(), Blocks.POLISHED_DIORITE.getDefaultState());
						}
				}else {
					event.getWorld().setBlockState(event.getPos(), Blocks.POLISHED_DIORITE.getDefaultState());
				}
			}
		}
		

	    private static boolean checkForGeneratedPortal(World worldIn) {
	    	BlockPos pos = new BlockPos(8, 255, 8);
	    	worldIn.getChunkAt(pos);
	    	
	    	while(pos.getY() >= 0) {
	    		if(worldIn.getBlockState(pos) == BlocksInit.AMPLIFIEDPORTAL.getDefaultState()) {
	    			return true;
	    		}
	    		pos = pos.down();
	    	}
	    	
	    	return false;
	    }
	    
	    private static void generatePortal(World worldIn) {
	    	AmplifiedPortalFrame amplifiedportalfeature = new AmplifiedPortalFrame();
	    	BlockPos pos = new BlockPos(8, 255, 8);
	    	worldIn.getChunkAt(pos);
	    	
	    	pos = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
	    	if(pos.getY() > 252) {
	    		pos = pos.down(3);
	    	}
	    	else if(pos.getY() < 6) {
	    		pos = new BlockPos(pos.getX(), 6, pos.getZ());
	    	}

	    	amplifiedportalfeature.place(worldIn, worldIn.getChunkProvider().getChunkGenerator(), new Random(), pos, IFeatureConfig.NO_FEATURE_CONFIG);
	 	}
	}

}
