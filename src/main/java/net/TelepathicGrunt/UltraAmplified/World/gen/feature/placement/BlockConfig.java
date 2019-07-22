package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class BlockConfig implements IFeatureConfig {
   public final Block block;
   private static ForgeRegistry<Block> BlockRegistry = ((ForgeRegistry<Block>)ForgeRegistries.BLOCKS);

   public BlockConfig(Block blockIn) {
      this.block = blockIn;
   }

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("block"), ops.createInt(BlockRegistry.getID(block)))));
	}
	

   public static <T> BlockConfig deserialize(Dynamic<T> p_222853_0_) {
      Block block = BlockRegistry.getValue(p_222853_0_.get("block").asInt(0));
      return new BlockConfig(block);
   }
}