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
	public GlowstonePatchPlacement1(Function<Dynamic<?>, ? extends ChanceConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}


	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceConfig chancesConfig, BlockPos pos)
	{

		int x = random.nextInt(16);
		int z = random.nextInt(16);
		int topYLayer = YPositionOfBelowLayer(world, 255, random, pos.add(x, 0, z)); //Finds top layer
		float chance = (ConfigUA.glowstoneVariantsSpawnrate / 909f) * chancesConfig.chance;

		//Chance needs to be less than random number to generate patch
		if (random.nextFloat() >= chance || topYLayer < 75 || chance == 0)
		{
			return Stream.empty();
		}

		//gets y value of a layer below top layer
		int height = random.nextInt(topYLayer - 74) + 75;
		topYLayer = YPositionOfBelowLayer(world, height, random, pos.add(x, 0, z));

		if (topYLayer < 75) //make sure new height is not too low
		{
			return Stream.empty();
		}

		//offset so patch will be centered correctly when generating
		return Stream.of(pos.add(x - 4, topYLayer - 1, z - 4));
	}


	private int YPositionOfBelowLayer(IWorld world, int height, Random random, BlockPos pos)
	{
		//if height is inside a non-air block, move down until we reached an air block
		while (height > 74)
		{
			if (world.isAirBlock(pos.add(0, height, 0)))
			{
				break;
			}

			height--;
		}

		//if height is an air block, move down until we reached a solid block. We are now on the surface of a piece of land
		while (height > 74)
		{
			if (!world.isAirBlock(pos.add(0, height, 0)))
			{
				break;
			}

			height--;
		}

		return height;
	}
}
