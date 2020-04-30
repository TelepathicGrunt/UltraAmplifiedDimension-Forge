package net.telepathicgrunt.ultraamplified.extrabehavior;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.dimension.UADimensionRegistration;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpawnLocationChange
{
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{

//		@SubscribeEvent
//		/**
//		 * Change default dimension and spawn point
//		 */
//		public static void setPlayerInitialSpawn(PlayerEvent.PlayerRespawnEvent event)
//		{
//			PlayerEntity player = event.getPlayer();
//			World world = player.world;
//			if(!world.isRemote && !event.getPlayer().getBedPosition().isPresent()) {
//				ServerWorld uaWorld = player.getServer().getWorld(UADimensionRegistration.ultraamplified());
//				Random rand = new Random(uaWorld.getSeed());
//				BlockPos position = new BlockPos(5000 + rand.nextInt(1000) * (rand.nextBoolean() ? -1 : 1), 255, 5000 + rand.nextInt(1000) * (rand.nextBoolean() ? -1 : 1));
//				
//				((ServerWorld)uaWorld).getChunk(position);
//				position = new BlockPos(position.getX(), uaWorld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, position.getX(), position.getZ()), position.getZ());
//
//				((ServerPlayerEntity) player).teleport(
//						uaWorld, 
//						position.getX() + 0.5D, 
//						position.getY() + 0.2D, 
//						position.getZ() + 0.5D, 
//						0, 
//						3.75F);
//				
//				uaWorld.setBlockState(position.down(), Blocks.POLISHED_GRANITE.getDefaultState(), 3);
//				event.setResult(Result.DENY);
//				
//				ReflectionHelper()
//				
//				if (player.getServer().canCreateBonusChest(enable))
//				{
//					ConfiguredFeature<?, ?> configuredfeature = Feature.BONUS_CHEST.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
//					configuredfeature.place(uaWorld, uaWorld.getChunkProvider().getChunkGenerator(), uaWorld.rand, position);
//				}
//			}
//		}
//		
//		/**
//		 * Change default dimension and spawn point
//		 */
//		public static void setPlayerInitialSpawn(PlayerEvent.PlayerChangedDimensionEvent event)
//		{
//			PlayerEntity player = event.getPlayer();
//			if(!player.world.isRemote && player.world.getDimension().getType() == UADimensionRegistration.ultraamplified()) {
//				ServerWorld world = (ServerWorld) player.world;
//
//			}
//		}
	}
}
