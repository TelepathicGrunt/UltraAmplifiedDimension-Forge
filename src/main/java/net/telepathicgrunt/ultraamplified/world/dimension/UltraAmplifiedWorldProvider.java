package net.telepathicgrunt.ultraamplified.world.dimension;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.generation.UABiomeProvider;
import net.telepathicgrunt.ultraamplified.world.generation.UAChunkGenerator;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UltraAmplifiedWorldProvider extends Dimension
{

	public UltraAmplifiedWorldProvider(World world, DimensionType typeIn)
	{
		super(world, typeIn, 1.0f); //set 1.0f. I think it has to do with maximum brightness?

		/**
		 * Creates the light to brightness table. It changes how light levels looks to the players but does not change the
		 * actual values of the light levels.
		 */
		for (int i = 0; i <= 15; ++i)
		{
			this.lightBrightnessTable[i] = i / 20.0F;
		}
	}

	@Override
	public ChunkGenerator<?> createChunkGenerator()
	{
		return new UAChunkGenerator(world, new UABiomeProvider(world), ChunkGeneratorType.SURFACE.createSettings());
	}


	@Override
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid)
	{
		BlockPos blockpos = this.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 90, 8));
		return this.world.getGroundAboveSeaLevel(blockpos).getMaterial().blocksMovement() ? blockpos : null;
	}


	@Override
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid)
	{
		return this.findSpawn(new ChunkPos(posX, posZ), checkValid);
	}


	//mimics vanilla sky movement
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		double d0 = MathHelper.frac(worldTime / 24000.0D - 0.25D);
		double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
		return (float) (d0 * 2.0D + d1) / 3.0F;
	}


	/**
	 * the y level at which clouds are rendered.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public float getCloudHeight()
	{
		return ConfigUA.yMaximum + 2;
	}


	@Override
	public int getActualHeight()
	{
		return 256;
	}


	@Override
	public boolean isNether()
	{
		return false;
	}


	@Override
	public boolean isSurfaceWorld()
	{
		return true;
	}


	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks)
	{
		float f = MathHelper.cos(celestialAngle * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		float f1 = 0.7529412F;
		float f2 = 0.84705883F;
		float f3 = 1.0F;

		//returns a multiplier between 0 and 1 and will decrease the lower down the player gets from 256
		float multiplierOfBrightness = ((float) (Minecraft.getInstance().player.getEyePosition(partialTicks).y) - 85) / (ConfigUA.yMaximum - 85);
		Math.min(Math.max(multiplierOfBrightness, 0), 1);

		f1 = f1 * (f * 0.94F + 0.06F) * multiplierOfBrightness;
		f2 = f2 * (f * 0.94F + 0.06F) * multiplierOfBrightness;
		f3 = f3 * (f * 0.91F + 0.09F) * multiplierOfBrightness;
		return new Vec3d(f1, f2, f3);
	}


	/**
	 * Returns a double value representing the Y value relative to the top of the map at which void fog is at its maximum.
	 * for example, means the void fog will be at its maximum at 70 here.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public double getVoidFogYFactor()
	{
		return 70;
	}


	@Override
	public boolean canRespawnHere()
	{
		return !ConfigUA.bedExplodes; //negate as bed will explode when respawn is set to false
	}


	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return ConfigUA.heavyFog;
	}


	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{
		@SubscribeEvent
		public static void ChangeTimeToDay(PlayerWakeUpEvent event)
		{
			if (event.getPlayer().world instanceof ServerWorld && event.getPlayer().dimension == UltraAmplifiedDimension.ultraamplified())
			{
				ServerWorld serverWorld = (ServerWorld) event.getPlayer().world;

				//checks if all players are asleep
				boolean everyoneSleeping = false;
				if (!serverWorld.getPlayers().isEmpty())
				{
					int i = 0;
					int j = 0;

					for (ServerPlayerEntity serverplayerentity : serverWorld.getPlayers())
					{
						if (serverplayerentity.isSpectator())
						{
							++i;
						}
						else if (serverplayerentity.isSleeping())
						{
							++j;
						}
					}
					everyoneSleeping = j > 0 && j >= serverWorld.getPlayers().size() - i;
				}

				//set time to morning and
				if (everyoneSleeping)
				{
					ServerWorld overworld = DimensionManager.getWorld(serverWorld.getServer(), DimensionType.OVERWORLD, false, false);

					if (serverWorld.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE))
					{
						long rawTime = serverWorld.getDayTime() + 24000L;
						long time = rawTime - rawTime % 24000L;
						overworld.setDayTime(time);
						overworld.getWorldInfo().setDayTime(time);
					}
				}
			}
		}
	}
}
