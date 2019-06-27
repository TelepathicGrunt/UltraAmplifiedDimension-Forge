package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;

public class EmeraldPlacement extends BasePlacement<CountRangeConfig> {
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, CountRangeConfig placementConfig, Feature<C> featureIn, C featureConfig) {
	      for(int i = 0; i < (20 + random.nextInt(35))*((double)(placementConfig.count)/100); ++i) {
	         int j = random.nextInt(16);
	         int k = random.nextInt(placementConfig.maxHeight - placementConfig.maxHeightBase) + placementConfig.minHeight;
	         int l = random.nextInt(16);
	         featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(j, k, l), featureConfig);
	      }

	      return true;
	   }
	}