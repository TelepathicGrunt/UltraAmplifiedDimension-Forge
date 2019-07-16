package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ContainWaterConfig implements IFeatureConfig {
   public final BlockState topBlock;
   public final BlockState middleBlock;
   public final BlockState bottomBlock;

   public ContainWaterConfig(BlockState blockState, BlockState blockState2, BlockState blockState3) {
      this.topBlock = blockState;
      this.middleBlock = blockState2;
      this.bottomBlock = blockState3;
   }

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(
				  ops.createString("top_block"), ops.createInt(Registry.BLOCK.getId(topBlock.getBlock())), 
				  ops.createString("middle_block"), ops.createInt(Registry.BLOCK.getId(middleBlock.getBlock())), 
				  ops.createString("bottom_block"), ops.createInt(Registry.BLOCK.getId(bottomBlock.getBlock())))));
   }


   public static <T> ContainWaterConfig deserialize(Dynamic<T> p_222853_0_) {
      BlockState topBlock = Registry.BLOCK.getByValue(p_222853_0_.get("top_block").asInt(0)).getDefaultState();
      BlockState middleBlock = Registry.BLOCK.getByValue(p_222853_0_.get("middle_block").asInt(0)).getDefaultState();
      BlockState bottomBlock = Registry.BLOCK.getByValue(p_222853_0_.get("bottom_block").asInt(0)).getDefaultState();
      return new ContainWaterConfig(topBlock, middleBlock, bottomBlock);
   }
}