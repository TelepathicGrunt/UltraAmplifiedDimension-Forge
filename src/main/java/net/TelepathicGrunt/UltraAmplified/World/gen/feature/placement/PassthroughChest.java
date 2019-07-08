package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;
import net.minecraft.world.gen.placement.NoPlacementConfig;

public class PassthroughChest extends BasePlacement<NoPlacementConfig> {
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, NoPlacementConfig placementConfig, Feature<C> featureIn, C featureConfig) {
      
	   //needed so we can prevent vanilla treasure chest from spawning if config is off
	   if(!ConfigUA.chestGeneration) {
		   return false;
	   }
	   
	   return featureIn.func_212245_a(worldIn, chunkGenerator, random, pos, featureConfig);
   }
}