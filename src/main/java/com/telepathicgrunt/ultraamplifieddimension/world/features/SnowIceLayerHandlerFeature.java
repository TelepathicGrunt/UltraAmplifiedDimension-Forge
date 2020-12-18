package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADFeatures;
import com.telepathicgrunt.ultraamplifieddimension.utils.BiomeSetsHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
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
}