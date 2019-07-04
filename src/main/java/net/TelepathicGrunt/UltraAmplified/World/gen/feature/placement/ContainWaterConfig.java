package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ContainWaterConfig implements IFeatureConfig {
   public final Block topBlock;
   public final Block middleBlock;
   public final Block bottomBlock;

   public ContainWaterConfig(Block blockIn, Block blockIn2, Block blockIn3) {
      this.topBlock = blockIn;
      this.middleBlock = blockIn2;
      this.bottomBlock = blockIn3;
   }
}