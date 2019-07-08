package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;
import net.minecraft.world.gen.placement.ChanceConfig;

public class AtSurfaceWithChanceDesertWell extends BasePlacement<ChanceConfig> {
public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, ChanceConfig placementConfig, Feature<C> featureIn, C featureConfig) {
    
	if(!ConfigUA.miniStructureGeneration) {
		return false;
	}
		   
   	  if (random.nextFloat() < 1.0F / (float)placementConfig.chance) {
         int i = random.nextInt(16);
         int j = random.nextInt(16);
         BlockPos blockpos = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(i, 0, j));
         featureIn.func_212245_a(worldIn, chunkGenerator, random, blockpos, featureConfig);
      }

      return true;
   }
}