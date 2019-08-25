package net.TelepathicGrunt.UltraAmplified.World.Feature.StructureUA;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class VillageUAConfig implements IFeatureConfig {
   public final int terrainType;
   public final VillagePiecesUA.Type type;

   public VillageUAConfig(int terraintypeIn, VillagePiecesUA.Type typeIn) {
      this.terrainType = terraintypeIn;
      this.type = typeIn;
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
      return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("type"), ops.createInt(this.type.ordinal()), ops.createString("terrainType"), ops.createInt(this.terrainType))));
   }

   public static <T> VillageUAConfig deserialize(Dynamic<T> p_214679_0_) {
	  VillagePiecesUA.Type s = VillagePiecesUA.Type.typeById(p_214679_0_.get("type").asInt(0));
      int i = p_214679_0_.get("terrainType").asInt(0);
      return new VillageUAConfig(i, s);
   }
}