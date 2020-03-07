package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class GlowstonePlacement extends Placement<NoPlacementConfig>
{
	public GlowstonePlacement(Function<Dynamic<?>, ? extends NoPlacementConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, NoPlacementConfig placementConfig, BlockPos pos)
	{
		return IntStream.range(0, random.nextInt(random.nextInt(ConfigUA.glowstoneSpawnrate) + 1)).mapToObj((p_215051_3_) ->
		{
			int x = random.nextInt(16);
			int y = random.nextInt(250) + 4;
			int z = random.nextInt(16);
			return pos.add(x, y, z);
		});
	}
}