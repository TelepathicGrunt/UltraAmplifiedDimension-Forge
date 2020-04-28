package net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class OceanSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public OceanSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_)
	{
		super(p_i51310_1_);
	}

	private static final BlockState AIR = Blocks.AIR.getDefaultState();
	private static final BlockState WATER = Blocks.WATER.getDefaultState();
	private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	private final static BlockState[] DEAD_CORAL_ARRAY = { Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultState(), Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultState() };


	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		this.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
	}


	protected void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int xStart, int zStart, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState topBlock, BlockState middleBlock, BlockState bottomBlock, int seaLevel)
	{
		BlockState liquid = UltraAmplified.UAConfig.lavaOcean.get() ? LAVA : WATER;
		int x = xStart & 15;
		int z = zStart & 15;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		BlockState bottom = topBlock;
		BlockState middle = middleBlock;
		BlockState aboveBlock = middleBlock;
		BlockState above2Block = middleBlock;
		BlockState above3Block = middleBlock;
		BlockState above4Block = middleBlock;
		BlockState currentBlock;
		boolean useCoral = bottomBlock == DEAD_CORAL_ARRAY[0];

		for (int y = startHeight; y >= seaLevel - 10; --y)
		{
			blockpos$Mutable.setPos(x, y, z);
			currentBlock = chunkIn.getBlockState(blockpos$Mutable);

			if (currentBlock.getBlock() != null)
			{
				if (currentBlock == defaultBlock)
				{
					//turns terrain into water to manipulate later
					bottom = liquid;
					chunkIn.setBlockState(blockpos$Mutable, bottom, false);
					currentBlock = bottom;
				}
				else if (!aboveBlock.getFluidState().isEmpty() && currentBlock.getMaterial() == Material.AIR && y < 256)
				{

					if (above2Block.getMaterial() == Material.AIR)
					{
						//sets very bottom of terrain to bottom block
						bottom = topBlock;
						chunkIn.setBlockState(blockpos$Mutable.up(), bottom, false);
					}
					else
					{

						//removes two block high land
						if (above3Block.getMaterial() == Material.AIR)
						{
							bottom = AIR;
							middle = AIR;
						}
						//Chooses blocks for bottom of terrain
						else
						{
							if (useCoral)
							{
								//if coral is passed in, randomly chooses a coral block
								bottom = DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)];
							}
							else
							{
								bottom = bottomBlock;
							}

							middle = middleBlock;
						}

						//makes bottom of terrain now solid
						//doesn't need to check if we are too high for other three checks since the aboveBlocks variables starts with a non-water block

						chunkIn.setBlockState(blockpos$Mutable.up(), bottom, false);
						currentBlock = bottom;

						if (!above2Block.getFluidState().isEmpty())
							chunkIn.setBlockState(blockpos$Mutable.up(2), middle, false);
						if (!above3Block.getFluidState().isEmpty())
							chunkIn.setBlockState(blockpos$Mutable.up(3), middle, false);
						if (!above4Block.getFluidState().isEmpty())
							chunkIn.setBlockState(blockpos$Mutable.up(4), middle, false);
					}

				}
			}

			above4Block = above3Block;
			above3Block = above2Block;
			above2Block = aboveBlock;
			aboveBlock = currentBlock;
		}

		//detects if we are a Lukewarm Ocean, Warm Ocean, or ocean plus their deep variants.
		//Adds sand/gravel bottom towards bottom of terrain gen between 40 - 70
		if (middleBlock == Blocks.SAND.getDefaultState() || bottomBlock == Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState())
		{
			blockpos$Mutable.setPos(x, 70, z);
			aboveBlock = chunkIn.getBlockState(blockpos$Mutable);
			above2Block = chunkIn.getBlockState(blockpos$Mutable.up());

			for (int y = 69; y >= 40; --y)
			{
				blockpos$Mutable.setPos(x, y, z);
				currentBlock = chunkIn.getBlockState(blockpos$Mutable);

				//detects if above block is solid and has solid block below and liquid block above.
				//if true, set above block to either sand or gravel
				if (currentBlock.getBlock() != null && currentBlock.isSolid())
				{
					if (!above2Block.getFluidState().isEmpty() && aboveBlock.isSolid())
					{
						if (middleBlock == Blocks.SAND.getDefaultState() || random.nextFloat() < 0.8F)
						{
							chunkIn.setBlockState(blockpos$Mutable.up(), middleBlock, false);
							aboveBlock = middleBlock;
						}
					}
				}

				above2Block = aboveBlock;
				aboveBlock = currentBlock;
			}
		}
	}
}