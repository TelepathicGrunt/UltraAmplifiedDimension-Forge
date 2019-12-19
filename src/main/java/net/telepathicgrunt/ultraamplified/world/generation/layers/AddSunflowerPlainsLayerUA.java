package net.telepathicgrunt.ultraamplified.world.generation.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;

public enum AddSunflowerPlainsLayerUA implements IC1Transformer {
   INSTANCE;

   public int apply(INoiseRandom context, int value) {
      return (context.random(200) == 0 && ConfigUA.mutatedBiomeSpawnrate != 0) && value == BiomeGenHelper.PLAINS ? BiomeGenHelper.SUNFLOWER_PLAINS : value;
   }
}