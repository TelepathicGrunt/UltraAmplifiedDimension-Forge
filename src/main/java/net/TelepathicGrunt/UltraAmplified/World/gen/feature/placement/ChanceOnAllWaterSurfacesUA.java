package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;

public class ChanceOnAllWaterSurfacesUA extends BasePlacement<PercentageAndFrequencyConfig> {
	
	private static final IBlockState WATER = Blocks.WATER.getDefaultState();
	
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, PercentageAndFrequencyConfig pfConfig, Feature<C> featureIn, C featureConfig) {
	   int lowestHeight = 40;
	   
	   for(int i = 0; i < pfConfig.frequency; i++) {
		   
	       int height = 255;
		   
	       while(height > lowestHeight) {
		         int x = random.nextInt(16);
		         int z = random.nextInt(16);
		         

		         //if height is inside am air block, move down until we reached a water block
		         while(height > lowestHeight) {
		        	 if(worldIn.getBlockState(pos.add(x, height, z)) == WATER) {
		        		 break;
		        	 }
		        	 
		        	 height--;
		         }
		         

		         if (height <= lowestHeight) {
		            break;
		         }
		         else if(random.nextFloat() < pfConfig.percentage) {
			         featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x, height+1, z), featureConfig);
		         }
		         
		         //height is a water block, move down until we reached an air block. We are now on the top surface of a body of water
		         while(height > lowestHeight) {
		        	 if(worldIn.isAirBlock(pos.add(x, height, z)))
		        	 {
		        		 break;
		        	 }
		        	 
		        	 height--;
		         }
		         
		     }
	       
		  }
	      return true;
	   }
	}