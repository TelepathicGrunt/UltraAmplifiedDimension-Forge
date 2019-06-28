package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class NetherBridgeConfigUA implements IFeatureConfig {
   public final boolean surfaceAllow;

   public NetherBridgeConfigUA(boolean surfaceAllow) {
      this.surfaceAllow = surfaceAllow;
   }
}