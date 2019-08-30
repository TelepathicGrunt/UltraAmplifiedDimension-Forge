package net.telepathicgrunt.ultraamplified.handclickevents;


import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.block.Block;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.blocks.BlocksInit;
import net.telepathicgrunt.ultraamplified.world.dimension.UltraAmplifiedDimension;

@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LeftAndRightClickEvents {
	

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
				if(!worldIn.isRemote && !entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
				{
					DimensionType destination = worldIn.dimension.getType() != UltraAmplifiedDimension.ultraamplified()
							? UltraAmplifiedDimension.ultraamplified()
							: DimensionType.OVERWORLD;
							
					MinecraftServer minecraftserver = entityIn.getServer();
					ServerWorld serverworld = minecraftserver.getWorld(destination);
					
					//gets top block in other world
					BlockPos blockpos = new BlockPos(10, 60, 8);
					Block block;
					
					//load chunk beforehand
					ChunkPos chunkpos = new ChunkPos(blockpos);
					minecraftserver.getWorld(destination).getChunkProvider().func_217228_a(TicketType.POST_TELEPORT, chunkpos, 1, entityIn.getEntityId());
					
			         if (((ServerPlayerEntity)entityIn).isSleeping()) {
			            ((ServerPlayerEntity)entityIn).wakeUpPlayer(true, true, false);
			         }
			         
					//checks a 2 by 2 by 5 area to replace any portal with air to prevent infinite loops or being stuck by badly place portals
					blockpos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 255, 8));
					for(int x = -1; x < 1; x++) {
						for(int z = -1; z < 1; z++) {
							for(int y = -2; y < 3; y++) {
								block = serverworld.getBlockState(blockpos.add(x, y, z)).getBlock();
								
								if(block == BlocksInit.AMPLIFIEDPORTAL) {
									serverworld.setBlockState(blockpos.add(x, y, z), Blocks.AIR.getDefaultState());
								}
							}
						}	
					}
					
					
					blockpos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 255, 8));
	
					
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
