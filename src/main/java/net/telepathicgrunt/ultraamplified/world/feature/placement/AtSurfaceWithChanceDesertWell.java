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
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class AtSurfaceWithChanceDesertWell extends Placement<ChanceConfig>
{
	public AtSurfaceWithChanceDesertWell(Function<Dynamic<?>, ? extends ChanceConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceConfig placementConfig, BlockPos pos)
	{

		if (!UltraAmplified.UAStructuresConfig.miniStructureGeneration.get())
		{
			return Stream.empty();
		}

		if (random.nextFloat() < 1.0F / placementConfig.chance)
		{
			int i = random.nextInt(16);
			int j = random.nextInt(16);
			BlockPos blockpos = world.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(i, 0, j));
			return Stream.of(blockpos);
		}

		return Stream.empty();
	}
}