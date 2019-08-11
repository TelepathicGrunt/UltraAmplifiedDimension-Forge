package net.TelepathicGrunt.UltraAmplified.World.Feature.Config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class CountRangeAndTypeConfig implements IPlacementConfig {
   public final int chance;
   public final Type type;

   public CountRangeAndTypeConfig(int chanceIn, Type typeIn) {
      this.chance = chanceIn;
      this.type = typeIn;
   }
   
   public static enum Type {
	   LAVA,
	   WATER,
	   SLIME;
   }

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("chance"), ops.createInt(this.chance), ops.createString("type"), ops.createString(this.type.name()))));
	}
	
   public static CountRangeAndTypeConfig deserialize(Dynamic<?> p_214723_0_) {
      int chance = p_214723_0_.get("chance").asInt(0);
      Type type = Type.valueOf(p_214723_0_.get("type").asString("WATER"));
      return new CountRangeAndTypeConfig(chance, type);
   }
}