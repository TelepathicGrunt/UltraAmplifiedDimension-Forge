package net.TelepathicGrunt.UltraAmplified.World.Feature.Placement;

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

public class EmeraldPlacement extends Placement<CountRangeConfig> {
   public EmeraldPlacement(Function<Dynamic<?>, ? extends CountRangeConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, CountRangeConfig placementConfig, BlockPos pos) {
	     return IntStream.range(0, (int) ((20 + random.nextInt(35))*((double)(placementConfig.count)/100))).mapToObj((p_215051_3_) -> {
	         int x = random.nextInt(16);
	         int y = random.nextInt(placementConfig.maximum - placementConfig.topOffset) + placementConfig.bottomOffset;
	         int z = random.nextInt(16);
	         return pos.add(x, y, z);
	      });
	   }
	}