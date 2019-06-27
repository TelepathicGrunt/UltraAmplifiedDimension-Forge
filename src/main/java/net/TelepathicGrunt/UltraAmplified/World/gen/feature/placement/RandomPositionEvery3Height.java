package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;
import net.minecraft.world.gen.placement.FrequencyConfig;

public class RandomPositionEvery3Height extends BasePlacement<FrequencyConfig> {
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, FrequencyConfig countConfig, Feature<C> featureIn, C featureConfig) {
		   int numberOfChunkAttempts = Math.max(countConfig.frequency, 1);
		   
		   for(int count = 0; count < numberOfChunkAttempts; ++count) {
			   
			   //offset the attempt so there's less chances of patches without grass
			   int startHeight = worldIn.getSeaLevel()-count;
			   if(startHeight <= 0) {
				   startHeight = 1;
			   }
		   
		      for(int height = startHeight; height <= 255; height += 3) {
		         int k = random.nextInt(16);
		         int i1 = random.nextInt(16);
		         featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(k, height, i1), featureConfig);
		      }
		   }

	      return true;
	   }

}