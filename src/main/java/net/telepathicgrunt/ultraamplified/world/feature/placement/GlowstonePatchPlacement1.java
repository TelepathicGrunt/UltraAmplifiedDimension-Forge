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
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class GlowstonePatchPlacement1 extends Placement<ChanceConfig>
{
	public GlowstonePatchPlacement1(Function<Dynamic<?>, ? extends ChanceConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceConfig chancesConfig, BlockPos pos)
	{

		int x = random.nextInt(16);
		int z = random.nextInt(16);
		float chance = (ConfigUA.glowstoneVariantsSpawnrate / 909f) * chancesConfig.chance;

		//Chance needs to be less than random number to generate patch
		if (random.nextFloat() >= chance || chance == 0)
		{
			return Stream.empty();
		}

		//gets y value of a layer below top layer
		int yPosOfSurface = PlacingUtils.topOfSurfaceBelowHeight(world, 255, 75, pos.add(x, 0, z)); //Finds top layer
		int height = random.nextInt(yPosOfSurface - 74) + 75;
		height = PlacingUtils.topOfSurfaceBelowHeight(world, height, 74, pos.add(x, 0, z));

		if (height < 75) //make sure new height is not too low
		{
			return Stream.empty();
		}

		//offset so patch will be centered correctly when generating
		return Stream.of(pos.add(x - 4, height - 1, z - 4));
	}
}
