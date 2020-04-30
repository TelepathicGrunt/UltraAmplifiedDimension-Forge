package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.utils.PlacingUtils;


public class AtSurfaceRoofedForest extends Placement<AtSurfaceWithExtraConfig>
{
	public AtSurfaceRoofedForest(Function<Dynamic<?>, ? extends AtSurfaceWithExtraConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, AtSurfaceWithExtraConfig chancesConfig, BlockPos position)
	{
		int maxAttempt = chancesConfig.count;
		if (random.nextFloat() < chancesConfig.extraChance)
		{
			maxAttempt += chancesConfig.extraCount;
		}

		ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

		for (int currentAttempt = 0; currentAttempt < maxAttempt; currentAttempt++)
		{
			int xOffset = random.nextInt(16);
			int zOffset = random.nextInt(16);
			blockpos$Mutable.setPos(position.getX() + xOffset, 250, position.getZ() + zOffset);

			while (blockpos$Mutable.getY() > 74)
			{
				int yPosOfSurface = PlacingUtils.topOfSurfaceBelowHeight(world, blockpos$Mutable.getY(), 74, blockpos$Mutable);

				//gets difference and sets mutable height to yPosOfSurface + 1
				blockPosList.add(new BlockPos(blockpos$Mutable.move(Direction.DOWN, (blockpos$Mutable.getY() - yPosOfSurface) + 1)));
				blockpos$Mutable.move(Direction.DOWN);
			}
		}

		return IntStream.range(0, blockPosList.size()).mapToObj((p_215051_3_) ->
		{
			return blockPosList.remove(0);
		}).filter(Objects::nonNull);

	}
}