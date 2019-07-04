package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public class GenLayerBiomeDebug implements IC0Transformer 
{

	   @SuppressWarnings("deprecation")
	   private static final int TESTING_BIOME = IRegistry.field_212624_m.getId(BiomeInit.DEEP_FROZEN_OCEAN);
	   
	   public GenLayerBiomeDebug(WorldType p_i48641_1_, OverworldGenSettings p_i48641_2_) {
	   }

	   public int apply(IContext context, int value) {
	       return TESTING_BIOME;
	   }

	}