package net.TelepathicGrunt.UltraAmplified.World.dimension;

import net.TelepathicGrunt.UltraAmplified.World.Generation.BiomeProviderUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UltraAmplifiedWorldProvider extends WorldProviderSurface{

	@Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderUA(world);
    }

	//mimics vanilla sky movement
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
	      double d0 = MathHelper.frac((double)worldTime / 24000.0D - 0.25D);
	      double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
	      return (float)(d0 * 2.0D + d1) / 3.0F;
	}


	   /**
	    * Creates the light to brightness table. It changes how light levels looks to the players but does not change the actual values of the light levels.
	    */
	   protected void generateLightBrightnessTable() {
	      for(int i = 0; i <= 15; ++i) {
	         this.lightBrightnessTable[i] = (float)i / 20.0F;
	      }

	   }
	
   /**
    * the y level at which clouds are rendered.
    */
   @SideOnly(Side.CLIENT)
   public float getCloudHeight() {
      return 256;
   }
	   
    @Override
    public int getActualHeight() {
        return 256;
    }
    
	@Override
	public boolean isSurfaceWorld() {
		return true;
	}

	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks) {
	      float f = MathHelper.cos(celestialAngle * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
	      f = MathHelper.clamp(f, 0.0F, 1.0F);
	      float f1 = 0.7529412F;
	      float f2 = 0.84705883F;
	      float f3 = 1.0F;
	      
	      //returns a multiplier between 0 and 1 and will decrease the lower down the player gets from 256
	      float multiplierOfBrightness = ((float)(Minecraft.getMinecraft().player.getPositionEyes(partialTicks).y)-85)/(171);
	      Math.min(Math.max(multiplierOfBrightness, 0), 1);
	      
	      f1 = f1 * (f * 0.94F + 0.06F)*multiplierOfBrightness;
	      f2 = f2 * (f * 0.94F + 0.06F)*multiplierOfBrightness;
	      f3 = f3 * (f * 0.91F + 0.09F)*multiplierOfBrightness;
	      return new Vec3d((double)f1, (double)f2, (double)f3);
	}
    /**
     * Returns a double value representing the Y value relative to the top of the map at which void fog is at its
     * maximum. for example, means the void fog will be at its maximum at 70 here.
     */
   @SideOnly(Side.CLIENT)
    public double getVoidFogYFactor() {
       return 70;
    }

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorOverworldUA(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
	}
	
 }
