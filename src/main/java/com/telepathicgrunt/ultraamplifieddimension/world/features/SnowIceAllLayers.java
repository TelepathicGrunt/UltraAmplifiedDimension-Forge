package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
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
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		BlockPos.Mutable blockpos$Mutable1 = new BlockPos.Mutable();

		for (int y = chunkGenerator.getMaxBuildHeight(); y > chunkGenerator.getSeaLevel(); --y) {

			blockpos$Mutable.setPos(pos.getX(), y, pos.getZ());
			blockpos$Mutable1.setPos(blockpos$Mutable).move(Direction.DOWN);

			if (world.getBlockState(blockpos$Mutable).getMaterial() == Material.AIR && world.getBlockState(blockpos$Mutable1).getMaterial() != Material.AIR) {

				if (!world.getBlockState(blockpos$Mutable1).getFluidState().isEmpty() && biome.doesWaterFreeze(world, blockpos$Mutable1, false)) {
					world.setBlockState(blockpos$Mutable1, Blocks.ICE.getDefaultState(), 2);
				}

				if (biome.doesSnowGenerate(world, blockpos$Mutable)) {
					world.setBlockState(blockpos$Mutable, Blocks.SNOW.getDefaultState(), 2);
					BlockState iblockstate = world.getBlockState(blockpos$Mutable1);

					if (iblockstate.hasProperty(SnowyDirtBlock.SNOWY)) {
						world.setBlockState(blockpos$Mutable1, iblockstate.with(SnowyDirtBlock.SNOWY, Boolean.TRUE), 2);
					}
				}
			}
		}
		return true;
	}
}