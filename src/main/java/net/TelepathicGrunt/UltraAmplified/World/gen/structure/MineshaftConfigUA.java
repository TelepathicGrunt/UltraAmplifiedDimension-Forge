package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class MineshaftConfigUA implements IFeatureConfig {
   public final double rarity;
   public final MineshaftUA.Type type;

   public MineshaftConfigUA(double p_i48676_1_, MineshaftUA.Type p_i48676_3_) {
      this.rarity = p_i48676_1_/10000;
      this.type = p_i48676_3_;
   }
   

   public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
      return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("type"), ops.createInt(this.type.ordinal()), ops.createString("rarity"), ops.createDouble(this.rarity*10000))));
   }

   public static <T> MineshaftConfigUA deserialize(Dynamic<T> p_214679_0_) {
	   MineshaftUA.Type s = MineshaftUA.Type.byId(p_214679_0_.get("type").asInt(0));
      double i = p_214679_0_.get("rarity").asDouble(0);
      return new MineshaftConfigUA(i, s);
   }
}