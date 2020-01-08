package net.telepathicgrunt.ultraamplified.world.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class LakeCountRangeAndTypeConfig implements IPlacementConfig {
   public final int chance;
   public final Type type;

   public LakeCountRangeAndTypeConfig(int chanceIn, Type typeIn) {
      this.chance = chanceIn;
      this.type = typeIn;
   }
   
   public static enum Type {
	   LAVA,
	   WATER,
	   SLIME,
	   HONEY;
   }

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(
				ops.createString("chance"), ops.createInt(this.chance), 
				ops.createString("type"), ops.createString(this.type.name())
				)));
	}
	
   public static LakeCountRangeAndTypeConfig deserialize(Dynamic<?> p_214723_0_) {
      int chance = p_214723_0_.get("chance").asInt(0);
      Type type = Type.valueOf(p_214723_0_.get("type").asString("WATER"));
      return new LakeCountRangeAndTypeConfig(chance, type);
   }
}