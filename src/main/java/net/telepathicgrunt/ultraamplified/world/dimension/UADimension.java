package net.telepathicgrunt.ultraamplified.world.dimension;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.FluidTags;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.feature.carver.CaveCavityCarver;
import net.telepathicgrunt.ultraamplified.world.generation.UABiomeProvider;
import net.telepathicgrunt.ultraamplified.world.generation.UAChunkGenerator;


public class UADimension extends Dimension
{
	//Used to keep track of time in this dimension and whether we synced the time yet at startup
	private boolean syncedTimeFromMemory = false;
	private long time = 0;

	public UADimension(World world, DimensionType typeIn)
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
	
	/**
	 * Terrain and biome generators for our dimension to use.
	 */
	@Override
	public ChunkGenerator<?> createChunkGenerator()
	{
		CaveCavityCarver.setSeed(world.getSeed());
		return new UAChunkGenerator(world, new UABiomeProvider(world), ChunkGeneratorType.SURFACE.createSettings());
	}


	/**
	 * Saves all info to memory. In this case, we just save time.
	 */
	@Override
	public void onWorldSave()
	{
		if(!world.isRemote)
		{
			UAWorldSavedData.get(world).setTimeUA(time);
			UAWorldSavedData.get(world).markDirty();
		}
	}
	
	
	/**
	 * This is only called on server side for some reason.
	 * Increments the time while syncing the time to client every 100 ticks.
	 */
	@Override
	public void tick()
	{
		if(!world.isRemote)
		{
			//sync time from memory at startup
			if(!syncedTimeFromMemory) {
				time = UAWorldSavedData.get(world).getTimeUA();
				TimeSyncNetworkPacket.UpdateTimePacket.sendToClient(this.time);
				syncedTimeFromMemory = true;
			}
			
			
			if(world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE))
			{
				time++;
				
				//sync client every 100 ticks
				if(time % 100 == 0)
					TimeSyncNetworkPacket.UpdateTimePacket.sendToClient(this.time);
			}
		}
	}

	/**
	 * Will calculate whether it is day or nighttime for UA dimension.
	 */
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
	 * Return our version of time for UA dimension.
	 */
	@Override
	public long getWorldTime()
    {
        return time;
    }
	
	/**
	 * Update the time in this dimension on serverside when players use the
	 * time command and sync to the client.  
	 */
	@Override
	public void setWorldTime(long timeIn)
    {
		//Will not update time every tick with the second check here as we update our time in tick()
		if(!world.isRemote && world.getWorldInfo().getDayTime() + 1L != timeIn)
		{
			time = timeIn;
			UAWorldSavedData.get(world).setTimeUA(time);
			UAWorldSavedData.get(world).markDirty();
			TimeSyncNetworkPacket.UpdateTimePacket.sendToClient(this.time);
		}
		
		super.setWorldTime(timeIn); //make overworld time continue to pass.
    }
	
	/**
	 * Used for specifically syncing the time on client side.
	 */
	public void setWorldTimeClientSided(long timeIn)
    {
		time = timeIn;
    }

	/**
	 * Make moon phase be based on our time
	 */
	@Override
	public int getMoonPhase(long worldTime)
	{
		return (int) (time / 24000L % 8L + 8L) % 8;
	}
	   
	/**
	 * Used for position of the sun/moon/stars. This mimics vanilla's sky speed.
	 */
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
		return UltraAmplified.UATerrainConfig.yMaximum.get() + 2;
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


	/**
	 * Color of the fog for distant chunks and the color of the bottom half of the sky block.
	 * In this case, the fog will darken as the player gets lower
	 */
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
		float multiplierOfBrightness = ((float) (Minecraft.getInstance().player.getEyePosition(partialTicks).y) - 85) / (UltraAmplified.UATerrainConfig.yMaximum.get() - 85);
		Math.min(Math.max(multiplierOfBrightness, 0), 1);

		f1 = f1 * (f * 0.94F + 0.06F) * multiplierOfBrightness;
		f2 = f2 * (f * 0.94F + 0.06F) * multiplierOfBrightness;
		f3 = f3 * (f * 0.91F + 0.09F) * multiplierOfBrightness;
		return new Vec3d(f1, f2, f3);
	}

	/**
	 * The height at which void fog (the bottom half of the sky map) will start appearing
	 * and will show up as whatever color getFogColor() returns.
	 * 0 is y = 0 and 1 is y = 255.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public double getVoidFogYFactor()
	{
		return 1D;
	}

	/**
	 * Dense fog everywhere. Severely limits how far once can see.
	 */
	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return UltraAmplified.UADimensionConfig.heavyFog.get();
	}

	/**
	 * Used for setting respawn points by beds. If set to false, the bed will explode.
	 */
	@Override
	public boolean canRespawnHere()
	{
		return !UltraAmplified.UADimensionConfig.bedExplodes.get();
	}
	
	/**
	 * Spawn is always at world origin for Ultra Amplified Dimension.
	 */
	@Override
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid)
	{
		BlockPos blockpos = this.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 90, 8));
		
		if(this.world.getBlockState(blockpos).getFluidState().isTagged(FluidTags.LAVA)) 
			this.world.setBlockState(blockpos, Blocks.OBSIDIAN.getDefaultState(), 3); //makes spawn safe
		
		return blockpos;
	}

	@Override
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid)
	{
		return this.findSpawn(new ChunkPos(posX, posZ), checkValid);
	}
}
