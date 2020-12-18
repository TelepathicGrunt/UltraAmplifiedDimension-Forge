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
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position);
		BlockPos.Mutable blockpos$Mutable2 = new BlockPos.Mutable().setPos(position);
		IChunk chunk = world.getChunk(position);

		while (blockpos$Mutable.getY() > chunkGenerators.getSeaLevel() + 1) {
			if (chunk.getBlockState(blockpos$Mutable).isAir()) {
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					BlockState iblockstate = Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(direction), true);
					blockpos$Mutable2.setPos(blockpos$Mutable).move(Direction.UP);
					if (iblockstate.isValidPosition(world, blockpos$Mutable)) {
						chunk.setBlockState(blockpos$Mutable, iblockstate, false);
						break;
					}
					else if (chunk.getBlockState(blockpos$Mutable2).isIn(Blocks.VINE)) {
						chunk.setBlockState(blockpos$Mutable, chunk.getBlockState(blockpos$Mutable2), false);
						break;
					}
				}
			}

			blockpos$Mutable.move(Direction.DOWN);
		}

		return true;
	}
}
