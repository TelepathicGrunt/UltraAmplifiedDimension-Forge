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
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;


public class AtSurfaceBelowTopLayerWithExtra extends Placement<AtSurfaceWithExtraConfig>
{
	public AtSurfaceBelowTopLayerWithExtra(Function<Dynamic<?>, ? extends AtSurfaceWithExtraConfig> configFactory)
	{
		super(configFactory);
	}


	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, AtSurfaceWithExtraConfig chancesConfig, BlockPos position)
	{
		int maxAttempt = chancesConfig.count;
		if (random.nextFloat() < chancesConfig.extraChance)
		{
			maxAttempt += chancesConfig.extraCount;
		}

		ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(0, 0, 0);

		for (int currentAttempt = 0; currentAttempt < maxAttempt; currentAttempt++)
		{
			//set us at top of chunk
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			int startHeight = world.getHeight(Heightmap.Type.MOTION_BLOCKING, position.add(x, 0, z)).getY();
			blockpos$Mutable.setPos(position.add(x, startHeight, z));
			
			//loop and find random surfaces for every ledge below
			while(blockpos$Mutable.getY() > 74)
			{
				x = random.nextInt(16);
				z = random.nextInt(16);
				
				blockpos$Mutable = new BlockPos.Mutable(position.getX() + x, blockpos$Mutable.getY(), position.getZ() + z);
				int yPosOfSurface = PlacingUtils.topOfSurfaceBelowHeight(world, blockpos$Mutable.getY() - 1, 74, position.add(x, 0, z));
				
				//set to new height and add the position to the list
				blockPosList.add(blockpos$Mutable.move(Direction.DOWN, blockpos$Mutable.getY() - yPosOfSurface));
			}
		}

		return IntStream.range(0, blockPosList.size()).mapToObj((p_215051_3_) ->
		{
			return blockPosList.remove(0);
		}).filter(Objects::nonNull);
	}
}