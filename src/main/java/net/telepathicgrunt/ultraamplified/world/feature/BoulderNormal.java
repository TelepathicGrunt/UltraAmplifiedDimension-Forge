package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class BoulderNormal extends Feature<BlockBlobConfig>
{
	public BoulderNormal(Function<Dynamic<?>, ? extends BlockBlobConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}

	private final static BlockState mossyCobblestone = Blocks.MOSSY_COBBLESTONE.getDefaultState();
	private final static BlockState cobblestone = Blocks.COBBLESTONE.getDefaultState();
	private final static BlockState andesite = Blocks.ANDESITE.getDefaultState();
	private final static BlockState coalOre = Blocks.COAL_ORE.getDefaultState();
	private final static BlockState ironOre = Blocks.IRON_ORE.getDefaultState();
	private final static BlockState diamondOre = Blocks.DIAMOND_ORE.getDefaultState();
	private final int startRadius = 3;


	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkgen, Random rand, BlockPos position, BlockBlobConfig config)
	{
		BlockState blockState = world.getBlockState(position);

		//Will keeps moving down position until it finds valid ground to generate on while ignoring other boulders
		while (position.getY() >= 6)
		{
			if (blockState.getMaterial() == Material.AIR || 
				(blockState.getBlock() != Blocks.GRASS_BLOCK && !func_227250_b_(blockState.getBlock())))
			{
				//block was air or a non-dirt/grass block. Thus move down one.
				position = position.down();
				blockState = world.getBlockState(position);
			}
			else
			{
				break; //We hit a valid spot to generate a boulder, time to exit loop
			}
		}

		//if the height is too low or high, just quit.
		if (position.getY() <= 6 || position.getY() >= 250)
		{
			return false;
		}

		//we are at a valid spot to generate a boulder now. Begin generation.
		int radius = startRadius;

		for (int currentCount = 0; radius >= 0 && currentCount < 3; ++currentCount)
		{
			int x = radius + rand.nextInt(2);
			int y = radius + rand.nextInt(2);
			int z = radius + rand.nextInt(2);
			float calculatedDistance = (float) (x + y + z) * 0.333F + 0.5F;

			for (BlockPos blockpos : BlockPos.getAllInBoxMutable(position.add(-x, -y, -z), position.add(x, y, z)))
			{
				if (blockpos.distanceSq(position) <= (double) (calculatedDistance * calculatedDistance))
				{
					//adds the blocks for generation in this boulder
					//note, if user turns off an ore, that ore's chance is dumped into the below ore for generation
					int randomChance = rand.nextInt(1400);

					// 1/1400th chance for diamond ore
					if (ConfigUA.diamondOreSpawnrate != 0 && randomChance == 0)
					{
						world.setBlockState(blockpos, diamondOre, 4);
					}

					// 39/1400th chance for iron ore
					else if (ConfigUA.ironOreSpawnrate != 0 && randomChance <= 40)
					{
						world.setBlockState(blockpos, ironOre, 4);
					}

					// 60/1400th chance for coal ore
					else if (ConfigUA.coalOreSpawnrate != 0 && randomChance <= 100)
					{
						world.setBlockState(blockpos, coalOre, 4);
					}

					// 300/1400th chance for andesite
					else if (randomChance <= 400)
					{
						world.setBlockState(blockpos, andesite, 4);
					}

					// 300/1400th chance for cobblestone
					else if (randomChance <= 700)
					{
						world.setBlockState(blockpos, cobblestone, 4);
					}

					// 700/1400th chance for mossyCobblestone
					else
					{
						world.setBlockState(blockpos, mossyCobblestone, 4);
					}
				}
			}
			position = position.add(-(radius + 1) + rand.nextInt(2 + radius * 2), 0 - rand.nextInt(2), -(radius + 1) + rand.nextInt(2 + radius * 2));

		}
		//finished generating the boulder
		return true;
	}
}
