package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;


public class LedgeUndersideMiniFeature extends Placement<ChanceAndTypeConfig>
{
	public LedgeUndersideMiniFeature(Function<Dynamic<?>, ? extends ChanceAndTypeConfig> configFactory)
	{
		super(configFactory);
	}


	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceAndTypeConfig placementConfig, BlockPos pos)
	{

		float chance;

		//hacky workaround as biome/feature registration happens at MC startup before the config file is loaded in a world. 
		//We have to read the config file here as placement is being found to have the config file be read in real time.
		switch (placementConfig.type)
		{
			case HANGING_RUINS:
				chance = (int) (ConfigUA.hangingRuinsSpawnrate * placementConfig.chanceModifier);
				break;

			default:
				chance = 0;
				break;
		}

		//more logical to do chance like this as this feature does not need to have chances less than 1% while other features/structures do
		//We do it based on percentage
		if (random.nextFloat() * 100 < chance)
		{
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			int yPosOfSurface = YPositionOfBottomOfLayer(world, random, pos.add(x, random.nextInt(174) + 74, z));

			//cannot be too low or high 
			if (yPosOfSurface < 75 || yPosOfSurface > 248)
			{
				return Stream.empty();
			}

			return Stream.of(pos.add(x - 4, yPosOfSurface - 1, z - 4));
		}
		else
		{
			return Stream.empty();
		}

	}


	private int YPositionOfBottomOfLayer(IWorld world, Random random, BlockPos pos)
	{
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(pos);

		// if height is inside a non-air block, move up until we reached an air block
		while (blockpos$Mutable.getY() < 255)
		{
			if (world.isAirBlock(blockpos$Mutable))
			{
				break;
			}

			blockpos$Mutable.move(Direction.UP);
		}

		// if height is an air block, move up until we reached a solid block. We are now
		// on the bottom of a piece of land
		while (blockpos$Mutable.getY() < 255)
		{
			if (!world.isAirBlock(blockpos$Mutable))
			{
				break;
			}

			blockpos$Mutable.move(Direction.UP);
		}

		return blockpos$Mutable.getY() > 255 ? 255 : blockpos$Mutable.getY();
	}
}
