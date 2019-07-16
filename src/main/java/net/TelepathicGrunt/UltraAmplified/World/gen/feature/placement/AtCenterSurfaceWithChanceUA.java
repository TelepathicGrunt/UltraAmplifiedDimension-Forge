package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

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

public class AtCenterSurfaceWithChanceUA extends Placement<ChanceConfig> {
	public AtCenterSurfaceWithChanceUA(Function<Dynamic<?>, ? extends ChanceConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceConfig placementConfig, BlockPos pos) {

		if (random.nextFloat() < 1.0F / (float) placementConfig.chance) {
			BlockPos blockpos = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(8, 0, 8));
			return Stream.of(blockpos);
		}
		
		return Stream.empty();
	}
}