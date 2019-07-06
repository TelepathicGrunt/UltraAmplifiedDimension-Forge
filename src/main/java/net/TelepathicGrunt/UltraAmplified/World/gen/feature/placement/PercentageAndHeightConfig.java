package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class PercentageAndHeightConfig implements IPlacementConfig {
	   public final float percentage;
	   public final int height;

	   public PercentageAndHeightConfig(float percentageIn, int heightIn) {
	      this.percentage = percentageIn;
	      this.height = heightIn;
   }
}