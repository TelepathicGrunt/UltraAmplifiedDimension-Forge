package net.telepathicgrunt.ultraamplified.blockbehavior;


import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
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
					}
					else if(cap.getDim() == entityIn.dimension){
						//if our stored dimension somehow ends up being our current dimension, just take us to overworld instead
						destination = DimensionType.OVERWORLD;
					}
					else {
						//gets stored dimension
						destination = cap.getDim();
					}
							
					MinecraftServer minecraftserver = entityIn.getServer();
					ServerWorld serverworld = minecraftserver.getWorld(destination);
					
					//gets top block in other world or original location
					BlockPos blockpos;
					ChunkPos chunkpos;
					if(firstTime || cap.getDim() == entityIn.dimension) {
						//if it is player's first time teleporting or our stored dimension was our current dimension, find top block at world origin
						chunkpos = new ChunkPos(new BlockPos(10, 255, 8));
						serverworld.getChunkProvider().getChunk(chunkpos.x, chunkpos.z, true);
						blockpos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 255, 8));
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
	}

}
