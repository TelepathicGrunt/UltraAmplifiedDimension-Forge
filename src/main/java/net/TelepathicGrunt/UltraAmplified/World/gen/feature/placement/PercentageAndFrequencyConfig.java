package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class PercentageAndFrequencyConfig  implements IPlacementConfig {
	   public final float percentage;
	   public final int frequency;

	   public PercentageAndFrequencyConfig(float percentageIn, int frequencyIn) {
	      this.percentage = percentageIn;
	      this.frequency = frequencyIn;
   }
}