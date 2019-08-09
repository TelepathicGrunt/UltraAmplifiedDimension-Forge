package net.TelepathicGrunt.UltraAmplified.World.Generation.Layers;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public class BiomeDebugLayer implements IC0Transformer 
{

   @SuppressWarnings("deprecation")
   private static final int TESTING_BIOME = Registry.BIOME.getId(BiomeInit.PLAINS);
   
   public BiomeDebugLayer(WorldType p_i48641_1_, OverworldGenSettings p_i48641_2_) {
   }

   public int apply(INoiseRandom context, int value) {
       return TESTING_BIOME;
   }


}