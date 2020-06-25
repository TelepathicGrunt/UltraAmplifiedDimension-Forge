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
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.feature.config.ChanceAndTypeConfig;


public class AtCenterSurfaceMiniFeature extends Placement<ChanceAndTypeConfig>
{
	public AtCenterSurfaceMiniFeature(Function<Dynamic<?>, ? extends ChanceAndTypeConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceAndTypeConfig placementConfig, BlockPos pos)
	{

		float chance;

		//hacky workaround as biome/feature registration happens at MC startup before the config file is loaded in a world. 
		//We have to read the config file here as placement is being found to have the config file be read in real time.
		switch (placementConfig.type)
		{
			case SUNSHRINE:
				chance = (int) (UltraAmplified.UAStructuresConfig.sunShrineSpawnrate.get() * placementConfig.chanceModifier);
				break;

			case STONEHENGE:
				chance = (int) (UltraAmplified.UAStructuresConfig.stonehengeSpawnrate.get() * placementConfig.chanceModifier);
				break;

			default:
				chance = 0;
				break;
		}

		if (random.nextFloat() < 1.0F / chance)
		{
			BlockPos blockpos = world.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(8, 0, 8));
			return Stream.of(blockpos);
		}

		return Stream.empty();
	}
}