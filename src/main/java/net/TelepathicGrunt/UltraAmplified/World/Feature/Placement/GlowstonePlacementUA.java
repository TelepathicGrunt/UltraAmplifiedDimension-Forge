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
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;

public class GlowstonePlacementUA extends Placement<FrequencyConfig> {
   public GlowstonePlacementUA(Function<Dynamic<?>, ? extends FrequencyConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, FrequencyConfig placementConfig, BlockPos pos) {
	   return IntStream.range(0, random.nextInt(random.nextInt(placementConfig.count) + 1)).mapToObj((p_215051_3_) -> {
	         int x = random.nextInt(16);
	         int y = random.nextInt(250) + 4;
	         int z = random.nextInt(16);
	         return pos.add(x, y, z);
	      });
	   }
	}