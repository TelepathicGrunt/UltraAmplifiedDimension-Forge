package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BlockConfig implements IFeatureConfig {
   public final Block block;

   public BlockConfig(Block blockIn) {
      this.block = blockIn;
   }

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("block"), ops.createInt(Registry.BLOCK.getId(block)))));
	}
	

   public static <T> BlockConfig deserialize(Dynamic<T> p_222853_0_) {
      Block block = Registry.BLOCK.getByValue(p_222853_0_.get("block").asInt(0));
      return new BlockConfig(block);
   }
}