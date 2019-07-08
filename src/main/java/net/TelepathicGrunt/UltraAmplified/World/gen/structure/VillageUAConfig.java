package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class VillageUAConfig implements IFeatureConfig {
   public final int terrainType;
   public final VillagePiecesUA.Type type;

   public VillageUAConfig(int terraintypeIn, VillagePiecesUA.Type typeIn) {
      this.terrainType = terraintypeIn;
      this.type = typeIn;
   }
}