package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum GenLayerSunflowerPlains implements IC1Transformer {
   INSTANCE;

   private static final int PLAINS = IRegistry.field_212624_m.getId(BiomeInit.BiomePlains);
   private static final int SUNFLOWER_PLAINS = IRegistry.field_212624_m.getId(BiomeInit.BiomePlainsM);

   public int apply(IContext context, int value) {
      return context.random(57) == 0 && value == PLAINS ? SUNFLOWER_PLAINS : value;
   }
}