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


public class AtSurfaceUnderTopLedgeWithChance extends Placement<ChanceConfig>
{
	public AtSurfaceUnderTopLedgeWithChance(Function<Dynamic<?>, ? extends ChanceConfig> configFactory)
	{
		super(configFactory);
	}


	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceConfig chancesConfig, BlockPos pos)
	{

		int x = random.nextInt(16);
		int z = random.nextInt(16);
		int yPosOfSurface = PlacingUtils.topOfSurfaceBelowHeight(world, 255, 74, pos.add(x, 0, z));

		if (random.nextFloat() >= 1.0F / chancesConfig.chance || yPosOfSurface < 75 || chancesConfig.chance == 0)
		{
			return Stream.empty();
		}

		int height = random.nextInt(yPosOfSurface - 74) + 75;

		//gets y value of a layer below top layer
		yPosOfSurface = PlacingUtils.topOfSurfaceBelowHeight(world, height, 74, pos.add(x, 0, z));

		if (yPosOfSurface < 75)
		{
			return Stream.empty();
		}

		return Stream.of(pos.add(x - 4, yPosOfSurface - 1, z - 4));
	}
}
