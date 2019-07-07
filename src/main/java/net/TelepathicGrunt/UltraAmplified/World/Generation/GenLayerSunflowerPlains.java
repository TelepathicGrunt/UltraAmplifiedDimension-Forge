package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum GenLayerSunflowerPlains implements IC1Transformer {
   INSTANCE;

   public int apply(IContext context, int value) {
      return (context.random(200) == 0 && Config.mutatedBiomeSpawnrate != 0) && value == BiomeGenHelper.PLAINS ? BiomeGenHelper.SUNFLOWER_PLAINS : value;
   }
}