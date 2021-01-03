package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
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
		BlockPos.Mutable blockposMutable1 = new BlockPos.Mutable();
		BlockPos.Mutable blockposMutable2 = new BlockPos.Mutable();
		IChunk cachedChunk = world.getChunk(pos);

		int y = cachedChunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ()) + 1;
		blockposMutable1.setPos(pos.getX(), y, pos.getZ());
		blockposMutable2.setPos(blockposMutable1).move(Direction.DOWN);

		if (biome.doesWaterFreeze(world, blockposMutable2, false)) {
			cachedChunk.setBlockState(blockposMutable2, Blocks.ICE.getDefaultState(), false);
		}

		if (biome.doesSnowGenerate(world, blockposMutable1)) {
			BlockState blockStateBottom = cachedChunk.getBlockState(blockposMutable2);

			// Extra check to follow leaves into nearby chunks and give them the snow they would've avoided
			// Run this only when on leaves and pos is on chunk edge to minimize wasted time
			int xMod = blockposMutable1.getX() & 0x000F;
			int zMod = blockposMutable1.getZ() & 0x000F;
			if (blockStateBottom.isIn(BlockTags.LEAVES) && (xMod == 0 || xMod == 15 || zMod == 0 || zMod == 15)) {
				SnowIceLayerHandlerFeature.placeSnowOnNearbyLeaves(world, biome, blockposMutable1, cachedChunk);
			}

			cachedChunk.setBlockState(blockposMutable1, Blocks.SNOW.getDefaultState(), false);
			BlockState blockstate = cachedChunk.getBlockState(blockposMutable2);
			if (blockstate.hasProperty(SnowyDirtBlock.SNOWY)) {
				cachedChunk.setBlockState(blockposMutable2, blockstate.with(SnowyDirtBlock.SNOWY, Boolean.TRUE), false);
			}
		}
		return true;
	}
}