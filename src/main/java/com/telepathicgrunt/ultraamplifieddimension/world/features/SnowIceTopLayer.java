package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;


public class SnowIceTopLayer extends Feature<NoFeatureConfig>
{
	public SnowIceTopLayer(Codec<NoFeatureConfig> p_i51435_1_) {
		super(p_i51435_1_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		Biome biome = world.getBiome(pos);
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
		BlockPos.Mutable blockpos$mutable1 = new BlockPos.Mutable();

		int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ());
		blockpos$mutable.setPos(pos.getX(), y, pos.getZ());
		blockpos$mutable1.setPos(blockpos$mutable).move(Direction.DOWN);

		if (biome.doesWaterFreeze(world, blockpos$mutable1, false)) {
			world.setBlockState(blockpos$mutable1, Blocks.ICE.getDefaultState(), 2);
		}

		if (biome.doesSnowGenerate(world, blockpos$mutable)) {
			world.setBlockState(blockpos$mutable, Blocks.SNOW.getDefaultState(), 2);
			BlockState blockstate = world.getBlockState(blockpos$mutable1);
			if (blockstate.hasProperty(SnowyDirtBlock.SNOWY)) {
				world.setBlockState(blockpos$mutable1, blockstate.with(SnowyDirtBlock.SNOWY, Boolean.TRUE), 2);
			}
		}
		return true;
	}
}