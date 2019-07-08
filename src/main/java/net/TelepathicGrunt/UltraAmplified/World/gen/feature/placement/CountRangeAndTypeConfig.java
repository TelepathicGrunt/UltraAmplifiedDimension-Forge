package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

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
}