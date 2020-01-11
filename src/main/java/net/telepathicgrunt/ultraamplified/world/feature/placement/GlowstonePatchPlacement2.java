package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.SimplePlacement;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class GlowstonePatchPlacement2 extends SimplePlacement<ChanceRangeConfig>
{
	public GlowstonePatchPlacement2(Function<Dynamic<?>, ? extends ChanceRangeConfig> config)
	{
		super(config);
	}


	public Stream<BlockPos> getPositions(Random rand, ChanceRangeConfig config, BlockPos pos)
	{
		if (rand.nextFloat() < (ConfigUA.glowstoneVariantsSpawnrate / 100f) * config.chance)
		{
			int x = rand.nextInt(16);
			int y = rand.nextInt(config.top - config.topOffset) + config.bottomOffset;
			int z = rand.nextInt(16);
			return Stream.of(pos.add(x, y, z));
		}
		else
		{
			return Stream.empty();
		}
	}
}