package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class MineshaftConfigUA implements IFeatureConfig {
   public final double field_202439_a;
   public final MineshaftUA.Type type;

   public MineshaftConfigUA(double p_i48676_1_, MineshaftUA.Type p_i48676_3_) {
      this.field_202439_a = p_i48676_1_/10000;
      this.type = p_i48676_3_;
   }
}