package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.utils.OpenSimplexNoise;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.BlockWithRuleReplaceConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


public class Roots extends Feature<BlockWithRuleReplaceConfig>
{
	public Roots(Codec<BlockWithRuleReplaceConfig> configFactory) {
		super(configFactory);
	}

	protected long seed;
	protected OpenSimplexNoise noiseGen;


	public void setSeed(long seed) {
		if (this.seed != seed || this.noiseGen == null) {
			this.noiseGen = new OpenSimplexNoise(seed);
			this.seed = seed;
		}
	}


	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, BlockWithRuleReplaceConfig blockConfig) {
		setSeed(world.getSeed());

		BlockPos.Mutable blockposMutable = new BlockPos.Mutable().setPos(position);
		IChunk cachedChunk = world.getChunk(blockposMutable);
		BlockState currentBlockState;
		int xOffset;
		int zOffset;
		int yOffset;

		//increase the number of roots the higher up in the world it is
		int numOfRoots = 1 + (position.getY() - chunkGenerator.getSeaLevel()) / 50;
		//makes root length increase the higher up in the world it is
		int rootLength = 2 + (position.getY() - chunkGenerator.getSeaLevel()) / 22;

		for (int rootNum = 1; rootNum < numOfRoots + 1; rootNum++) {

			//set mutable block pos back to the starting block of the roots
			blockposMutable.setPos(position);
			if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
				cachedChunk = world.getChunk(blockposMutable);

			//attempts to grow root branch as long as the new position is an air block. Otherwise, terminate root
			for (int length = 0; length < rootLength; length++) {

				//checks to see if air block is not higher than starting place
				currentBlockState = cachedChunk.getBlockState(blockposMutable);
				if (blockposMutable.getY() <= position.getY() &&
						(blockConfig.target.test(currentBlockState, rand) ||
						 currentBlockState == blockConfig.state ||
						 currentBlockState == Blocks.VINE.getDefaultState()))
				{
					// Make sure it is under ledge and not going off to the side which looks weird.
					boolean isUnderLedge = false;
					int upwardOffset = 1;
					blockposMutable.move(Direction.UP);
					for(; upwardOffset < 8; upwardOffset++){
						BlockState blockState = cachedChunk.getBlockState(blockposMutable);
						if(blockState.isSolid() && !blockState.isIn(BlockTags.LEAVES) && !blockState.isIn(BlockTags.LOGS)){
							isUnderLedge = true;
							break;
						}
						blockposMutable.move(Direction.UP);
					}

					if(isUnderLedge){
						blockposMutable.move(Direction.DOWN, upwardOffset); // Move back to current pos.

						//set root block
						cachedChunk.setBlockState(blockposMutable, blockConfig.state, false);

						//rare chance to also generate a vine
						if (rand.nextFloat() < 0.13F) {
							generateTinyVine(world, cachedChunk, rand, blockposMutable);
						}
					}
				}
				//The position was either too high or was a solid block ( not a root) and so ends this branch
				else {
					break;
				}

				//debugging
				//UltraAmplified.LOGGER.info(this.noiseGen.func_205563_a(Mutable.getX() * 1.6D-10000*rootNum, Mutable.getZ() * 1.6D-10000*rootNum, Mutable.getY()*1D-10000) * 15.0D *rootNum - 5.0D);

				//move to next place to grow root to
				//range is clamped to -1 to 1 due to int rounding
				xOffset = (int) MathHelper.clamp(this.noiseGen.eval(blockposMutable.getX() * 1D + 20000 * rootNum, blockposMutable.getZ() * 1D + 20000 * rootNum, blockposMutable.getY() * 0.25D + 20000 * rootNum) * 15.0D, -1, 1);
				zOffset = (int) MathHelper.clamp(this.noiseGen.eval(blockposMutable.getX() * 1D + 10000 * rootNum, blockposMutable.getZ() * 1D + 10000 * rootNum, blockposMutable.getY() * 0.25D + 10000 * rootNum) * 15.0D, -1, 1);
				yOffset = (int) MathHelper.clamp(this.noiseGen.eval(blockposMutable.getX() * 0.85D - 10000 * rootNum, blockposMutable.getZ() * 0.85D - 10000 * rootNum, blockposMutable.getY() * 0.5D - 10000) * 15.0D * rootNum - 5.0D, -1, 1);

				//debugging
				//System.out.println(xOffset +", "+zOffset+", "+yOffset);
				blockposMutable.move(xOffset, yOffset, zOffset);

				if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
					cachedChunk = world.getChunk(blockposMutable);
			}
		}

		return true;
	}


	private void generateTinyVine(IWorld world, IChunk cachedChunkIn, Random rand, BlockPos.Mutable originalPosition) {
		BlockPos.Mutable blockposMutable = new BlockPos.Mutable().setPos(originalPosition);

		//generates vines from given position down 5 blocks if path is clear and the given position is valid
		//Also won't generate vines below Y = 1.
		int length = 0;
		blockposMutable.move(Direction.Plane.HORIZONTAL.random(rand));

		IChunk cachedChunk = cachedChunkIn;
		if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
			cachedChunk = world.getChunk(blockposMutable);

		//begin vine generation
		for (; blockposMutable.getY() > 1 && length < 5; blockposMutable.move(Direction.DOWN)) {
			if (cachedChunk.getBlockState(blockposMutable).isAir()) {
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					BlockState currentBlockState = Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(direction), Boolean.TRUE);
					if(currentBlockState.isAir()){
						if (currentBlockState.isValidPosition(world, blockposMutable)) {
							cachedChunk.setBlockState(blockposMutable, currentBlockState, false);
							length++;
							break;
						}
						else {
							BlockState aboveState = cachedChunk.getBlockState(blockposMutable.move(Direction.UP));
							blockposMutable.move(Direction.DOWN); // Move back to original pos.

							if (aboveState.isIn(Blocks.VINE)) {
								cachedChunk.setBlockState(blockposMutable, aboveState, false);
								length++;
								break;
							}
						}
					}
				}
			}
			else {
				break;
			}
		}
	}
}