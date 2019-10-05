package net.telepathicgrunt.ultraamplified.world.feature.structure;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class MineshaftConfigUA implements IFeatureConfig {
   public final MineshaftUA.Type type;

   public MineshaftConfigUA(MineshaftUA.Type type) {
      this.type = type;
   }
   

   public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
      return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("type"), ops.createInt(this.type.ordinal()))));
   }

   public static <T> MineshaftConfigUA deserialize(Dynamic<T> p_214679_0_) {
	   MineshaftUA.Type s = MineshaftUA.Type.byId(p_214679_0_.get("type").asInt(0));
      return new MineshaftConfigUA(s);
   }
}