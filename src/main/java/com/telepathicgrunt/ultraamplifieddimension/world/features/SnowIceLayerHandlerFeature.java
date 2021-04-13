package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADFeatures;
import com.telepathicgrunt.ultraamplifieddimension.utils.BiomeSetsHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;


public class SnowIceLayerHandlerFeature extends Feature<NoFeatureConfig>
{
	public SnowIceLayerHandlerFeature(Codec<NoFeatureConfig> p_i51435_1_) {
		super(p_i51435_1_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos position, NoFeatureConfig config) {
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

		for (int xOffset = 0; xOffset < 16; xOffset++) {
			for (int zOffset = 0; zOffset < 16; zOffset++) {
				blockpos$Mutable.setPos(position).move(xOffset, 0, zOffset);
				Biome biome = world.getBiome(blockpos$Mutable);
				if (BiomeSetsHelper.FROZEN_BIOMES.contains(biome)) {
					UADFeatures.SNOW_ICE_ALL_LAYERS.get().generate(world, generator, random, blockpos$Mutable, config);
				}
				else if (BiomeSetsHelper.COLD_OCEAN_BIOMES.contains(biome)) {
					UADFeatures.SNOW_LAYER_WITHOUT_ICE.get().generate(world, generator, random, blockpos$Mutable, config);
				}
				else {
					UADFeatures.SNOW_ICE_TOP_LAYER.get().generate(world, generator, random, blockpos$Mutable, config);
				}
			}
		}

		return true;
	}

	public static void placeSnowOnNearbyLeaves(ISeedReader world, Biome biome, BlockPos.Mutable blockposMutable1, IChunk cachedChunk) {
		BlockPos.Mutable nearbyPos = new BlockPos.Mutable();
		BlockPos.Mutable nearbyPosBelow = new BlockPos.Mutable();
		IChunk chunk = cachedChunk;
		int range = 5;
		for (int xOffset = -range; xOffset <= range; xOffset++) {
			for (int zOffset = -range; zOffset <= range; zOffset++) {
				nearbyPos.setPos(blockposMutable1).move(xOffset, 0, zOffset);
				nearbyPosBelow.setPos(nearbyPos).move(Direction.DOWN);

				// Only run in chunks outside our current chunk
				if (nearbyPos.getX() >> 4 != cachedChunk.getPos().x || nearbyPos.getZ() >> 4 != cachedChunk.getPos().z){
					if(nearbyPos.getX() >> 4 != chunk.getPos().x || nearbyPos.getZ() >> 4 != chunk.getPos().z){
						chunk = world.getChunk(nearbyPos);
					}

					BlockState nearbyBlockStateTop = chunk.getBlockState(nearbyPos);
					BlockState nearbyBlockStateBottom = chunk.getBlockState(nearbyPosBelow);

					if ((nearbyBlockStateTop.isAir() || nearbyBlockStateTop.matchesBlock(Blocks.VINE)) &&
						doesSnowGenerate(world, biome, nearbyPos) &&
						nearbyBlockStateBottom.isIn(BlockTags.LEAVES))
					{
						chunk.setBlockState(nearbyPos, Blocks.SNOW.getDefaultState(), false);

						if (nearbyBlockStateBottom.hasProperty(SnowyDirtBlock.SNOWY)) {
							chunk.setBlockState(nearbyPosBelow, nearbyBlockStateBottom.with(SnowyDirtBlock.SNOWY, true), false);
						}
					}
				}
			}
		}
	}

	public static boolean doesSnowGenerate(IWorldReader worldIn, Biome biome, BlockPos pos) {
		if (!(biome.getTemperature(pos) >= 0.15F)) {
			if (pos.getY() >= 0 && pos.getY() < 256) {
				return Blocks.SNOW.getDefaultState().isValidPosition(worldIn, pos);
			}

		}
		return false;
	}
}