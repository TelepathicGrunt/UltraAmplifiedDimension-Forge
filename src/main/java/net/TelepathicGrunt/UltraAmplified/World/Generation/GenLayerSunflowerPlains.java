package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum GenLayerSunflowerPlains implements IC1Transformer {
   INSTANCE;

   public int apply(INoiseRandom context, int value) {
      return (context.random(200) == 0 && ConfigUA.mutatedBiomeSpawnrate != 0) && value == BiomeGenHelper.PLAINS ? BiomeGenHelper.SUNFLOWER_PLAINS : value;
   }
}