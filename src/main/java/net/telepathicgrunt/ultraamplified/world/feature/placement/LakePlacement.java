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
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.LakeCountRangeAndTypeConfig;


public class LakePlacement extends Placement<LakeCountRangeAndTypeConfig>
{
	public LakePlacement(Function<Dynamic<?>, ? extends LakeCountRangeAndTypeConfig> configFactory)
	{
		super(configFactory);
	}


	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, LakeCountRangeAndTypeConfig lakeConfig, BlockPos pos)
	{

		int x = random.nextInt(16) - 8;
		int z = random.nextInt(16) - 8;

		switch (lakeConfig.type)
		{
			case LAVA_ALGORITHM:
			{

				if (!ConfigUA.lavaLakeGen)
				{
					return Stream.empty();
				}

				if (random.nextInt(lakeConfig.chance / 10) == 0)
				{
					int y = random.nextInt(random.nextInt(chunkGenerator.getMaxHeight() - 8) + 8);
					if (y < world.getSeaLevel() || random.nextInt(lakeConfig.chance / 8) == 0)
					{
						return Stream.of(pos.add(x, y, z));
					}
				}

			}
			case ICE_ALGORITHM:
			{
				if (random.nextInt(lakeConfig.chance / 10) == 0)
				{
					int y = random.nextInt(random.nextInt(chunkGenerator.getMaxHeight() - 8) + 8);
					if (y < world.getSeaLevel() || random.nextInt(lakeConfig.chance / 8) == 0)
					{
						return Stream.of(pos.add(x, y, z));
					}
				}

			}
			case WATER_ALGORITHM:
			{

				if (!ConfigUA.waterLakeGen)
				{
					return Stream.empty();
				}

				if (random.nextInt(lakeConfig.chance) == 0)
				{
					int y = random.nextInt(chunkGenerator.getMaxHeight());
					return Stream.of(pos.add(x, y, z));
				}

			}
			case SLIME_ALGORITHM:
			{
				if (!ConfigUA.slimeLakeGen)
				{
					return Stream.empty();
				}

				if (random.nextInt(lakeConfig.chance) == 0)
				{

					// counteract the -8 I do in the actual generation of Slime Lakes so Slime Lakes generate
					// around chosen position instead of in corner like the water and lava lakes do
					x += 8;
					z += 8;

					int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX() + x, pos.getZ() + z);
					if (y > world.getSeaLevel() && y <= 170)
					{
						return Stream.of(new BlockPos(pos.getX() + x, y - 2, pos.getZ() + z));
					}
				}
			}
			case HONEY_ALGORITHM:
			{
				if (!ConfigUA.honeyLakeGen)
				{//change to honey
					return Stream.empty();
				}

				if (random.nextInt(lakeConfig.chance) == 0)
				{

					// counteract the -8 I do in the actual generation of Honey Lakes so Honey Lakes generate
					// around chosen position instead of in corner like the water and lava lakes do
					x += 8;
					z += 8;

					int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX() + x, pos.getZ() + z);
					if (y > world.getSeaLevel() && y <= 170)
					{
						return Stream.of(new BlockPos(pos.getX() + x, y - 2, pos.getZ() + z));
					}
				}
			}
			default:
				break;
		}

		return Stream.empty();
	}
}