package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.HeightConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


public class VinesShort extends Feature<HeightConfig>
{

	public VinesShort(Codec<HeightConfig> configFactory) {
		super(configFactory);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerators, Random rand, BlockPos position, HeightConfig config) {

		//generates vines from given position down 6 blocks if path is clear and the given position is valid
		//Also won't generate vines below Y = 15.
		int length = 0;

		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position);
		BlockPos.Mutable blockpos$Mutable2 = new BlockPos.Mutable().setPos(position);
		IChunk chunk = world.getChunk(position);

		while (blockpos$Mutable.getY() > 15 && length < config.height) {
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
						length++;
						break;
					}
				}
			}
			blockpos$Mutable.move(Direction.DOWN);
		}

		return true;
	}
}