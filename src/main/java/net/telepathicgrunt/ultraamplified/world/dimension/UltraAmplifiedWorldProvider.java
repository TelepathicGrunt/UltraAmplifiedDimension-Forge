package net.telepathicgrunt.ultraamplified.world.dimension;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.extrabehavior.MessageHandler;
import net.telepathicgrunt.ultraamplified.world.feature.carver.CaveCavityCarver;
import net.telepathicgrunt.ultraamplified.world.generation.UABiomeProvider;
import net.telepathicgrunt.ultraamplified.world.generation.UAChunkGenerator;


public class UltraAmplifiedWorldProvider extends Dimension
{
	private boolean syncedTimeFromMemory = false;
	private long time = 0;

	public UltraAmplifiedWorldProvider(World world, DimensionType typeIn)
	{
		super(world, typeIn, 1.0f);
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
	public void onWorldSave()
	{
		if(!world.isRemote)
		{
			UAWorldSavedData.get(world).setTimeUA(time);
			UAWorldSavedData.get(world).markDirty();
		}
	}

	@Override
	public boolean isDaytime() {
		long timeOfDay = (time % 24000);
		if(timeOfDay - 13000 > 0 && timeOfDay - 13000 < 10000)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Called when the world is updating entities. Only used in WorldProviderEnd to update the DragonFightManager in
	 * Vanilla.
	 */
	@Override
	public void tick()
	{
		if(!world.isRemote)
		{
			if(!syncedTimeFromMemory) {
				time = UAWorldSavedData.get(world).getTimeUA();
				MessageHandler.UpdateTimePacket.sendToClient(this.time);
				syncedTimeFromMemory = true;
			}
			
			time++;
			if(time % 100 == 0)
				MessageHandler.UpdateTimePacket.sendToClient(this.time);
		}
	}

	@Override
	public long getWorldTime()
    {
        return time;
    }


	@Override
	public void setWorldTime(long timeIn)
    {
		if(!world.isRemote && world.getWorldInfo().getDayTime() + 1L != timeIn)
		{
			time = timeIn;
			UAWorldSavedData.get(world).setTimeUA(time);
			UAWorldSavedData.get(world).markDirty();
			MessageHandler.UpdateTimePacket.sendToClient(this.time);
		}
    }
	
	public void setWorldTimeClientSided(long timeIn)
    {
		time = timeIn;
    }
	   
	@Override
	public ChunkGenerator<?> createChunkGenerator()
	{
		CaveCavityCarver.setSeed(world.getSeed());
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
		double d0 = MathHelper.frac(time / 24000.0D - 0.25D);
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
		return UltraAmplified.UAConfig.yMaximum.get() + 2;
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
		@SuppressWarnings("resource")
		float multiplierOfBrightness = ((float) (Minecraft.getInstance().player.getEyePosition(partialTicks).y) - 85) / (UltraAmplified.UAConfig.yMaximum.get() - 85);
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
		return !UltraAmplified.UAConfig.bedExplodes.get(); //negate as bed will explode when respawn is set to false
	}


	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return UltraAmplified.UAConfig.heavyFog.get();
	}
}
