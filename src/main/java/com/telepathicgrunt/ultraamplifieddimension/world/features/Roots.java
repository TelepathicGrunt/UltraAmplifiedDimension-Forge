package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.utils.OpenSimplexNoise;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.BlockWithRuleReplaceConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
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

		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
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
			blockpos$Mutable.setPos(position);

			//attempts to grow root branch as long as the new position is an air block. Otherwise, terminate root
			for (int length = 0; length < rootLength; length++) {

				//checks to see if air block is not higher than starting place
				currentBlockState = world.getBlockState(blockpos$Mutable);
				if (blockpos$Mutable.getY() <= position.getY() &&
						(blockConfig.target.test(currentBlockState, rand) ||
						 currentBlockState == blockConfig.state ||
						 currentBlockState == Blocks.VINE.getDefaultState()))
				{
					//set root block
					world.setBlockState(blockpos$Mutable, blockConfig.state, 2);

					//rare chance to also generate a vine
					if (rand.nextFloat() < 0.13F) {
						generateTinyVine(world, rand, blockpos$Mutable);
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
				xOffset = (int) MathHelper.clamp(this.noiseGen.eval(blockpos$Mutable.getX() * 1D + 20000 * rootNum, blockpos$Mutable.getZ() * 1D + 20000 * rootNum, blockpos$Mutable.getY() * 0.5D + 20000 * rootNum) * 15.0D, -1, 1);
				zOffset = (int) MathHelper.clamp(this.noiseGen.eval(blockpos$Mutable.getX() * 1D + 10000 * rootNum, blockpos$Mutable.getZ() * 1D + 10000 * rootNum, blockpos$Mutable.getY() * 0.5D + 10000 * rootNum) * 15.0D, -1, 1);
				yOffset = (int) MathHelper.clamp(this.noiseGen.eval(blockpos$Mutable.getX() * 0.85D - 10000 * rootNum, blockpos$Mutable.getZ() * 0.85D - 10000 * rootNum, blockpos$Mutable.getY() * 0.5D - 10000) * 15.0D * rootNum - 5.0D, -1, 1);

				//debugging
				//System.out.println(xOffset +", "+zOffset+", "+yOffset);
				blockpos$Mutable.move(xOffset, yOffset, zOffset);
			}
		}

		return true;
	}


	private void generateTinyVine(IWorld world, Random rand, BlockPos.Mutable originalPosition) {
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(originalPosition);

		//generates vines from given position down 5 blocks if path is clear and the given position is valid
		//Also won't generate vines below Y = 1.
		int length = 0;

		Direction face = Direction.Plane.HORIZONTAL.random(rand);
		blockpos$Mutable.move(face);

		//begin vine generation
		for (; blockpos$Mutable.getY() > 1 && length < 5; blockpos$Mutable.move(Direction.DOWN)) {
			if (world.isAirBlock(blockpos$Mutable)) {
				for (Direction Direction : Direction.Plane.HORIZONTAL) {
					BlockState iblockstate = Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(Direction), Boolean.TRUE);
					if (iblockstate.isValidPosition(world, blockpos$Mutable)) {
						world.setBlockState(blockpos$Mutable, iblockstate, 2);
						length++;
						break;
					}
					else if (world.getBlockState(blockpos$Mutable.up()).getBlock() == Blocks.VINE) {
						world.setBlockState(blockpos$Mutable, world.getBlockState(blockpos$Mutable.up()), 2);
						length++;
						break;
					}
				}
			}
			else {
				break;
			}
		}
	}
}