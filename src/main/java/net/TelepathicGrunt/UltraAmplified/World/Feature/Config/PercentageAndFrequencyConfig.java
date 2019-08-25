package net.telepathicgrunt.ultraamplified.world.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class PercentageAndFrequencyConfig  implements IPlacementConfig {
	   public final float percentage;
	   public final int frequency;

	   public PercentageAndFrequencyConfig(float percentageIn, int frequencyIn) {
	      this.percentage = percentageIn;
	      this.frequency = frequencyIn;
   }

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("percentage"), ops.createFloat(this.percentage), ops.createString("frequency"), ops.createInt(this.frequency))));
	}
	

   public static PercentageAndFrequencyConfig deserialize(Dynamic<?> p_214723_0_) {
	   float chance = p_214723_0_.get("percentage").asFloat(0.0f);
	   int type = p_214723_0_.get("frequency").asInt(0);
      return new PercentageAndFrequencyConfig(chance, type);
   }
}