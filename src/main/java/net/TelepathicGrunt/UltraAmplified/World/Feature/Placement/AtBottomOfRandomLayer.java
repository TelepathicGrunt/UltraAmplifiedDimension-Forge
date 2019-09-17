package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;

public class AtBottomOfRandomLayer extends Placement<CountRangeConfig> {
	public AtBottomOfRandomLayer(Function<Dynamic<?>, ? extends CountRangeConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, CountRangeConfig placementConfig, BlockPos pos) {

		return IntStream.range(0, placementConfig.count).mapToObj((p_215051_3_) -> {
			int x = random.nextInt(16);
			int z = random.nextInt(16);

			int height = random.nextInt(placementConfig.maximum - placementConfig.bottomOffset) + placementConfig.bottomOffset;

			// gets y value of a layer below top layer
			int bottomYLayer = YPositionOfBottomOfLayer(worldIn, height, random, pos.add(x, 0, z), placementConfig);

			if (bottomYLayer > placementConfig.maximum) {
				return null;
			}
			return pos.add(x, bottomYLayer, z);

		}).filter(Objects::nonNull);
	}

	private int YPositionOfBottomOfLayer(IWorld worldIn, int height, Random random, BlockPos pos, CountRangeConfig placementConfig) {

		// if height is inside a non-air block, move up until we reached an air block
		while (height < 255) {
			if (worldIn.isAirBlock(pos.add(0, height, 0))) {
				break;
			}

			height++;
		}

		// if height is an air block, move up until we reached a solid block. We are now
		// on the bottom of a piece of land
		while (height < 255) {
			if (!worldIn.isAirBlock(pos.add(0, height, 0))) {
				break;
			}

			height++;
		}

		return height > 255 ? 255 : height;
	}
}
