package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;


public class ChanceOnAllLiquidBottoms extends Placement<PercentageAndFrequencyConfig>
{

	public ChanceOnAllLiquidBottoms(Function<Dynamic<?>, ? extends PercentageAndFrequencyConfig> configFactory)
	{
		super(configFactory);
	}

	// Needed to prevent placing features like coral inside ocean monuments
	protected static final Set<BlockState> UNACCEPTABLE_BLOCKS = 
				ImmutableSet.of(
					Blocks.PRISMARINE.getDefaultState(), 
					Blocks.PRISMARINE_BRICKS.getDefaultState(), 
					Blocks.DARK_PRISMARINE.getDefaultState(), 
					Blocks.SEA_LANTERN.getDefaultState()
				);


	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, PercentageAndFrequencyConfig pfConfig, BlockPos pos)
	{
		int lowestHeight = 40;

		ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(pos);

		for (int i = 0; i < pfConfig.frequency; i++)
		{

			blockpos$Mutable.setPos(0, 255, 0);

			while (blockpos$Mutable.getY() > lowestHeight)
			{
				int x = random.nextInt(16);
				int z = random.nextInt(16);
				blockpos$Mutable.setPos(pos.getX() + x, blockpos$Mutable.getY(), pos.getZ()+z);

				//if height is inside a non-water block, move down until we reached a water block
				while (blockpos$Mutable.getY() > lowestHeight)
				{
					if (!world.getBlockState(blockpos$Mutable).getFluidState().isEmpty())
					{
						break;
					}

					blockpos$Mutable.move(Direction.DOWN);
				}

				//Height is a water block, move down until we reached a non-water block.
				//When done, we are now on the bottom surface of a body of water. (Ignores Ocean Monument blocks)
				BlockState currentBlock;
				while (blockpos$Mutable.getY() > lowestHeight)
				{
					currentBlock = world.getBlockState(blockpos$Mutable);

					if (currentBlock.getFluidState().isEmpty() && !UNACCEPTABLE_BLOCKS.contains(currentBlock))
					{
						break;
					}

					blockpos$Mutable.move(Direction.DOWN);
				}

				if (blockpos$Mutable.getY() <= lowestHeight)
				{
					break;
				}
				else if (random.nextFloat() < pfConfig.percentage)
				{
					blockPosList.add(blockpos$Mutable.up());
				}

			}

		}

		return IntStream.range(0, blockPosList.size()).mapToObj((p_215051_3_) ->
		{
			return blockPosList.remove(0);
		}).filter(Objects::nonNull);
	}
}