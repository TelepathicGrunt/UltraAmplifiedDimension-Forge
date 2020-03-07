package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class RandomChanceUnderSurface extends Placement<ChanceConfig>
{

	public RandomChanceUnderSurface(Function<Dynamic<?>, ? extends ChanceConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceConfig chanceConfig, BlockPos pos)
	{
		if (random.nextFloat() < 1.0F / chanceConfig.chance)
		{
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			int height = world.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(x, 0, z)).getY();
			int y = random.nextInt((int) (height * ((ConfigUA.seaLevel / (height + 1F))) + 1F)); //scale range to be between height + seaLevel

			//Generates a range between sealevel and highest land with increased chance below ConfigUA.seaLevel number of the range itself
			if (y > ConfigUA.seaLevel)
			{
				y -= ConfigUA.seaLevel;
			}

			if (y <= 0)
			{
				y += ConfigUA.seaLevel;
			}

			return Stream.of(pos.add(x, y, z));
		}

		return Stream.empty();
	}

}