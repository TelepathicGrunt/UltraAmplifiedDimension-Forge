package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class NetherBridgeConfigUA implements IFeatureConfig {
   public final boolean surfaceAllow;

   public NetherBridgeConfigUA(boolean surfaceAllow) {
      this.surfaceAllow = surfaceAllow;
   }
   
   public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
      return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("surfaceAllow"), ops.createBoolean(this.surfaceAllow))));
   }

   public static <T> NetherBridgeConfigUA deserialize(Dynamic<T> p_214679_0_) {
      boolean i = p_214679_0_.get("surfaceAllow").asBoolean(false);
      return new NetherBridgeConfigUA(i);
   }
}