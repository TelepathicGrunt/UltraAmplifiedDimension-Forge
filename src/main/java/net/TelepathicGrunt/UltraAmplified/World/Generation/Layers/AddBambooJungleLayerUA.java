package net.TelepathicGrunt.UltraAmplified.World.Generation.Layers;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.BiomeGenHelper;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum AddBambooJungleLayerUA implements IC1Transformer {
   INSTANCE;

   public int apply(INoiseRandom context, int value) {
      return (context.random(8) == 0 && ConfigUA.bambooJungle) && value == BiomeGenHelper.BAMBOO_JUNGLE ? BiomeGenHelper.JUNGLE : value;
   }
}