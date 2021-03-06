package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;


public class VinesLong extends Feature<NoFeatureConfig>
{

	public VinesLong(Codec<NoFeatureConfig> configFactory) {
		super(configFactory);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerators, Random rand, BlockPos position, NoFeatureConfig config) {

		//generates vines from given position all the way down to sealevel + 1 if path is clear and the given position is valid
		BlockPos.Mutable blockposMutable = new BlockPos.Mutable().setPos(position);
		IChunk chunk = world.getChunk(position);

		while (blockposMutable.getY() > chunkGenerators.getSeaLevel() + 1) {
			if (chunk.getBlockState(blockposMutable).isAir()) {
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					BlockState blockState = Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(direction), true);
					if (blockState.isValidPosition(world, blockposMutable)) {
						chunk.setBlockState(blockposMutable, blockState, false);
						break;
					}
					else {
						BlockState aboveBlockstate = chunk.getBlockState(blockposMutable.move(Direction.UP));
						blockposMutable.move(Direction.DOWN); // Move back to original pos.

						if (aboveBlockstate.isIn(Blocks.VINE)) {
							chunk.setBlockState(blockposMutable, aboveBlockstate, false);
							break;
						}
					}
				}
			}

			blockposMutable.move(Direction.DOWN);
		}

		return true;
	}
}
