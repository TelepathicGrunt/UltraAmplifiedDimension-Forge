package net.telepathicgrunt.ultraamplified.world.generation.layers;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;

public class BiomeDebugLayer implements IC0Transformer 
{

   @SuppressWarnings("deprecation")
   private static final int TESTING_BIOME = Registry.BIOME.getId(BiomeInit.MODIFIED_BADLANDS_PLATEAU);
   
   public BiomeDebugLayer(WorldType p_i48641_1_, OverworldGenSettings p_i48641_2_) {
   }

   public int apply(INoiseRandom context, int value) {
       return TESTING_BIOME;
   }


}