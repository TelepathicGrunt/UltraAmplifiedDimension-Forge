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

public class AtBottomOfRandomLayerUA extends BasePlacement<CountRangeConfig> {
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, CountRangeConfig placementConfig, Feature<C> featureIn, C featureConfig) {

	   for(int i = 0; i < placementConfig.count; i++) {
		   int x = random.nextInt(16);
		   int z = random.nextInt(16);
		   
		   int height = random.nextInt(placementConfig.maxHeight - placementConfig.minHeight) + placementConfig.minHeight;
		   
		     //gets y value of a layer below top layer
		   int bottomYLayer = YPositionOfBottomOfLayer(worldIn, height, random, pos.add(x, 0 ,z), placementConfig);
		     
		     if(bottomYLayer > placementConfig.maxHeight) {
		    	 return false;
		     }
			
		   featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x, bottomYLayer, z), featureConfig);
			 
	   }
	    return true;
	}
   
   
	private int YPositionOfBottomOfLayer(IWorld worldIn, int height, Random random, BlockPos pos, CountRangeConfig placementConfig) {
		
         //if height is inside a non-air block, move up until we reached an air block
         while(height < placementConfig.maxHeight) {
        	 if(worldIn.isAirBlock(pos.add(0, height, 0))) {
        		 break;
        	 }
        	 
        	 height++;
         }
         
         //if height is an air block, move up until we reached a solid block. We are now on the bottom of a piece of land
         while(height < placementConfig.maxHeight) {
        	 if(!worldIn.isAirBlock(pos.add(0, height, 0))) {
        		 break;
        	 }
        	 
        	 height++;
         }
         
		return height > 255 ? 255 : height;
	}
}
