package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;

public class FixedHeightWithChance extends BasePlacement<PercentageAndHeightConfig> {
	
	public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, PercentageAndHeightConfig percentageAndHeightConfig, Feature<C> featureIn, C featureConfig) {
	   
   	   if(random.nextFloat() < percentageAndHeightConfig.percentage) {
	   			
   		    pos = pos.up(percentageAndHeightConfig.height);
   		   
		    featureIn.func_212245_a(worldIn, chunkGenerator, random, pos, featureConfig);
   	   }
   	   else {
   		   return false;
	   
       }

       return true;
   }
}