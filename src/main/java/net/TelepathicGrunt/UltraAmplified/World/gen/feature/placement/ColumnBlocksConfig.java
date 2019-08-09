package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class ColumnBlocksConfig implements IFeatureConfig {
   public final BlockState topBlock;
   public final BlockState middleBlock;
   public final BlockState insideBlock;
   private static ForgeRegistry<Block> BlockRegistry = ((ForgeRegistry<Block>)ForgeRegistries.BLOCKS);

   public ColumnBlocksConfig(BlockState blockState, BlockState blockState2, BlockState blockState3) {
      this.topBlock = blockState;
      this.middleBlock = blockState2;
      this.insideBlock = blockState3;
   }

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(
				  ops.createString("top_block"), ops.createInt(BlockRegistry.getID(topBlock.getBlock())), 
				  ops.createString("middle_block"), ops.createInt(BlockRegistry.getID(middleBlock.getBlock())), 
				  ops.createString("inside_block"), ops.createInt(BlockRegistry.getID(insideBlock.getBlock())))));
   }

   public static <T> ColumnBlocksConfig deserialize(Dynamic<T> p_222853_0_) {
      BlockState topBlock = BlockRegistry.getValue(p_222853_0_.get("top_block").asInt(0)).getDefaultState();
      BlockState middleBlock = BlockRegistry.getValue(p_222853_0_.get("middle_block").asInt(0)).getDefaultState();
      BlockState insideBlock = BlockRegistry.getValue(p_222853_0_.get("inside_block").asInt(0)).getDefaultState();
      return new ColumnBlocksConfig(topBlock, middleBlock, insideBlock);
   }
}