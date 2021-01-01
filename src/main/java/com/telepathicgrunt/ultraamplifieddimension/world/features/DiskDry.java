package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.DiskDryConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


public class DiskDry extends Feature<DiskDryConfig>
{
	public DiskDry(Codec<DiskDryConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos position, DiskDryConfig config) {
		int placedBlocks = 0;
		int radius = config.radius.func_242259_a(random);
		if (radius > 2) {
			radius = random.nextInt(radius - 2) + 2;
		}

		BlockPos.Mutable blockposMutable = new BlockPos.Mutable().setPos(position);
		IChunk cachedChunk = world.getChunk(blockposMutable);

		for (int x = position.getX() - radius; x <= position.getX() + radius; ++x) {
			for (int z = position.getZ() - radius; z <= position.getZ() + radius; ++z) {

				int trueX = x - position.getX();
				int trueZ = z - position.getZ();
				if (trueX * trueX + trueZ * trueZ <= radius * radius) {
					blockposMutable.setPos(x, position.getY(), z);
					if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
						cachedChunk = world.getChunk(blockposMutable);

					blockposMutable.move(Direction.DOWN, config.half_height); // start at bottom of half height
					for (int y = -config.half_height; y <= config.half_height; ++y) {
						BlockState blockState = cachedChunk.getBlockState(blockposMutable);

						for (BlockState targetBlockState : config.targets) {
							if (targetBlockState.getBlock() == blockState.getBlock()) {
								cachedChunk.setBlockState(blockposMutable, config.state, false);
								++placedBlocks;

								blockState = cachedChunk.getBlockState(blockposMutable.move(Direction.UP));
								if(blockState.isIn(Blocks.SNOW) && !blockState.isValidPosition(world, blockposMutable)){
									cachedChunk.setBlockState(blockposMutable, Blocks.AIR.getDefaultState(), false);
								}
								blockposMutable.move(Direction.DOWN);
								break;
							}
						}

						blockposMutable.move(Direction.UP);
					}
				}
			}
		}

		return placedBlocks > 0;
	}
}