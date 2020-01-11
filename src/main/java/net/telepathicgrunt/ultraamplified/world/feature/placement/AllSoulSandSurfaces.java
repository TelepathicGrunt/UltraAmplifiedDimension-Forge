package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;


public class AllSoulSandSurfaces extends Placement<PercentageAndFrequencyConfig>
{
	public AllSoulSandSurfaces(Function<Dynamic<?>, ? extends PercentageAndFrequencyConfig> configFactory)
	{
		super(configFactory);
	}

	private final BlockState SOULSAND = Blocks.SOUL_SAND.getDefaultState();


	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, PercentageAndFrequencyConfig pfConfig,
			BlockPos pos)
	{

		ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();

		for (int i = 0; i < pfConfig.frequency; i++)
		{
			int height = 255;

			while (height > 74)
			{
				int x = random.nextInt(16);
				int z = random.nextInt(16);

				//if height is inside a non-air block, move down until we reached an air block
				while (height > 74)
				{
					if (world.isAirBlock(pos.add(x, height, z)))
					{
						break;
					}

					height--;
				}

				//if height is an air block, move down until we reached a solid block. We are now on the surface of a piece of land
				while (height > 74)
				{
					if (world.getBlockState(pos.add(x, height, z)) == SOULSAND)
					{
						break;
					}

					height--;
				}

				if (height <= 74)
				{
					break;
				}
				else if (random.nextFloat() < pfConfig.percentage)
				{
					blockPosList.add(pos.add(x, height, z));
				}
			}
		}

		return IntStream.range(0, blockPosList.size()).mapToObj((p_215051_3_) ->
		{
			return blockPosList.remove(0);
		}).filter(Objects::nonNull);
	}
}