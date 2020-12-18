package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SphereReplaceConfig;

import java.util.Random;


public class SphereReplaceDry extends Feature<SphereReplaceConfig>
{
	public SphereReplaceDry(Codec<SphereReplaceConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos position, SphereReplaceConfig config) {
		int placedBlocks = 0;
		int radius = config.radius.func_242259_a(random);
		if (radius > 2) {
			radius = random.nextInt(config.radius.func_242259_a(random) - 2) + 2;
		}

		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position);

		for (int x = position.getX() - radius; x <= position.getX() + radius; ++x) {
			for (int z = position.getZ() - radius; z <= position.getZ() + radius; ++z) {

				int trueX = x - position.getX();
				int trueZ = z - position.getZ();
				if (trueX * trueX + trueZ * trueZ <= radius * radius) {
					for (int y = position.getY() - config.field_242809_d; y <= position.getY() + config.field_242809_d; ++y) {
						blockpos$Mutable.setPos(x, y, z);
						BlockState blockstate = world.getBlockState(blockpos$Mutable);

						for (BlockState blockstate1 : config.targets) {
							if (blockstate1.getBlock() == blockstate.getBlock()) {
								world.setBlockState(blockpos$Mutable, config.state, 2);
								++placedBlocks;
								break;
							}
						}
					}
				}
			}
		}

		return placedBlocks > 0;
	}
}