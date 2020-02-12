package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;


public class TwiceSurfaceWithChance extends Placement<ChanceConfig>
{
	public TwiceSurfaceWithChance(Function<Dynamic<?>, ? extends ChanceConfig> configFactory)
	{
		super(configFactory);
	}


	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceConfig placementConfig, BlockPos pos)
	{
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(pos);
		if (random.nextFloat() < 1.0F / (float) placementConfig.chance)
		{
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			int height = random.nextInt(180) + 75;

			//gets y value of a layer below top layer
			int yPosOfSurface = PlacingUtils.topOfSurfaceBelowHeight(world, height, 74, pos.add(x, 0, z));
			blockpos$Mutable.setPos(pos.getX() + x, yPosOfSurface, pos.getZ() + z);

			if (blockpos$Mutable.getY() <= 74) // won't generate at 74 itself.
			{
				return Stream.empty();
			}
			
			Stream.of(blockpos$Mutable);
		}

		return Stream.empty();
	}
}