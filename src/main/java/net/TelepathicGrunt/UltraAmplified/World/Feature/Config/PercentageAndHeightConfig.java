package net.telepathicgrunt.ultraamplified.world.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class PercentageAndHeightConfig implements IPlacementConfig {
	   public final float percentage;
	   public final int height;

	   public PercentageAndHeightConfig(float percentageIn, int heightIn) {
	      this.percentage = percentageIn;
	      this.height = heightIn;
   }
	   

		@Override
		public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
			return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("percentage"), ops.createFloat(this.percentage), ops.createString("height"), ops.createInt(this.height))));
		}
		
	   public static PercentageAndHeightConfig deserialize(Dynamic<?> p_214723_0_) {
		   float percentage = p_214723_0_.get("percentage").asFloat(0.0f);
	       int height = p_214723_0_.get("height").asInt(0);
	      return new PercentageAndHeightConfig(percentage, height);
	   }
}