package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class AtSurfaceWithChanceDesertWell extends Placement<ChanceConfig> {
public AtSurfaceWithChanceDesertWell(Function<Dynamic<?>, ? extends ChanceConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceConfig placementConfig, BlockPos pos) {
    
	if(!ConfigUA.miniStructureGeneration) {
		return Stream.empty();
	}
		   
   	  if (random.nextFloat() < 1.0F / (float)placementConfig.chance) {
         int i = random.nextInt(16);
         int j = random.nextInt(16);
         BlockPos blockpos = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(i, 0, j));
         return Stream.of(blockpos);
      }

      return Stream.empty();
   }
}