package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.utils.OpenSimplexNoise;
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;

import java.util.Random;
import java.util.function.Function;


public class Roots extends Feature<BlockConfig>
{
	public Roots(Function<Dynamic<?>, ? extends BlockConfig> configFactory)
	{
		super(configFactory);
	}

	protected long seed;
	protected OpenSimplexNoise noiseGen;


	public void setSeed(long seed)
	{
		if (this.seed != seed || this.noiseGen == null)
		{
			this.noiseGen = new OpenSimplexNoise(seed);
			this.seed = seed;
		}
	}


	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, BlockConfig blockConfig)
	{
		setSeed(world.getSeed());

		//wont generate root if config is turned off
		//won't generate root on leaves, water, etc.
		//Only solid blocks can have roots
		if (!UltraAmplified.UAFeaturesConfig.rootGen.get() || !world.getBlockState(position).isSolid())
		{
			return false;
		}

		position = position.down();
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		BlockState currentBlockState;
		int xOffset;
		int zOffset;
		int yOffset;

		//increase the number of roots the higher up in the world it is
		int numOfRoots = 1 + (position.getY() - 70) / 50;
		for (int rootNum = 1; rootNum < numOfRoots + 1; rootNum++)
		{

			//set mutable block pos back to the starting block of the roots
			blockpos$Mutable.setPos(position);

			//makes root length increase the higher up in the world it is
			int rootLength = 2 + (position.getY() - 70) / 22;

			//attempts to grow root branch as long as the new position is an air block. Otherwise, terminate root
			for (int length = 0; length < rootLength; length++)
			{

				//checks to see if air block is not higher than starting place
				currentBlockState = world.getBlockState(blockpos$Mutable);
				if (blockpos$Mutable.getY() <= position.getY() && (currentBlockState.getMaterial() == Material.AIR || currentBlockState == blockConfig.block.getDefaultState() || currentBlockState == Blocks.VINE.getDefaultState()))
				{
					//set root block
					world.setBlockState(blockpos$Mutable, blockConfig.block.getDefaultState(), 2);

					//rare chance to also generate a vine
					if (rand.nextFloat() < 0.13F)
					{
						generateTinyVine(world, rand, blockpos$Mutable);
					}

				}
				//The position was either too high or was a solid block ( not a root) and so ends this branch
				else
				{
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


	private void generateTinyVine(IWorld world, Random rand, BlockPos position)
	{
		//generates vines from given position down 5 blocks if path is clear and the given position is valid
		//Also won't generate vines below Y = 1.
		int length = 0;

		Direction face = Direction.Plane.HORIZONTAL.random(rand);
		position.offset(face);

		//begin vine generation
		for (; position.getY() > 1 && length < 5; position = position.down())
		{
			if (world.isAirBlock(position))
			{
				for (Direction Direction : Direction.Plane.HORIZONTAL)
				{
					BlockState iblockstate = Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(Direction), Boolean.valueOf(true));
					if (iblockstate.isValidPosition(world, position))
					{
						world.setBlockState(position, iblockstate, 2);
						length++;
						break;
					}
					else if (world.getBlockState(position.up()).getBlock() == Blocks.VINE)
					{
						world.setBlockState(position, world.getBlockState(position.up()), 2);
						length++;
						break;
					}
				}
			}
			else
			{
				break;
			}
		}
	}
}