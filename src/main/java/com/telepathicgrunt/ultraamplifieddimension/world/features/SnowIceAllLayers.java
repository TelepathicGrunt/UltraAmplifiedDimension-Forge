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
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;


public class SnowIceAllLayers extends Feature<NoFeatureConfig>
{
	public SnowIceAllLayers(Codec<NoFeatureConfig> configFactory) {
		super(configFactory);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, NoFeatureConfig config) {

		Biome biome = world.getBiome(pos);
		BlockPos.Mutable blockposMutable1 = new BlockPos.Mutable();
		BlockPos.Mutable blockposMutable2 = new BlockPos.Mutable();
		IChunk cachedChunk = world.getChunk(pos);

		for (int y = chunkGenerator.getMaxBuildHeight(); y >= chunkGenerator.getSeaLevel(); --y) {

			blockposMutable1.setPos(pos.getX(), y, pos.getZ());
			blockposMutable2.setPos(blockposMutable1).move(Direction.DOWN);

			BlockState blockStateTop = cachedChunk.getBlockState(blockposMutable1);
			BlockState blockStateBottom = cachedChunk.getBlockState(blockposMutable2);
			if ((blockStateTop.isAir() || blockStateTop.matchesBlock(Blocks.VINE)) && !blockStateBottom.isAir()) {

				if (!blockStateBottom.getFluidState().isEmpty() && biome.doesWaterFreeze(world, blockposMutable2, false)) {
					cachedChunk.setBlockState(blockposMutable2, Blocks.ICE.getDefaultState(), false);
				}

				if (SnowIceLayerHandlerFeature.doesSnowGenerate(world, biome, blockposMutable1)) {
					// Extra check to follow leaves into nearby chunks and give them the snow they would've avoided
					// Run this only when on leaves and pos is on chunk edge to minimize wasted time
					int xMod = blockposMutable1.getX() & 0x000F;
					int zMod = blockposMutable1.getZ() & 0x000F;
					if (blockStateBottom.isIn(BlockTags.LEAVES) && (xMod == 0 || xMod == 15 || zMod == 0 || zMod == 15)) {
						SnowIceLayerHandlerFeature.placeSnowOnNearbyLeaves(world, biome, blockposMutable1, cachedChunk);
					}

					cachedChunk.setBlockState(blockposMutable1, Blocks.SNOW.getDefaultState(), false);
					if (blockStateBottom.hasProperty(SnowyDirtBlock.SNOWY)) {
						cachedChunk.setBlockState(blockposMutable2, blockStateBottom.with(SnowyDirtBlock.SNOWY, true), false);
					}
				}
			}
		}
		return true;
	}
}