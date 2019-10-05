package net.telepathicgrunt.ultraamplified.world.generation.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;

public enum AddBambooJungleLayerUA implements IC1Transformer {
   INSTANCE;

   public int apply(INoiseRandom context, int value) {
      return (context.random(8) == 0 && ConfigUA.bambooJungle) && value == BiomeGenHelper.BAMBOO_JUNGLE ? BiomeGenHelper.JUNGLE : value;
   }
}