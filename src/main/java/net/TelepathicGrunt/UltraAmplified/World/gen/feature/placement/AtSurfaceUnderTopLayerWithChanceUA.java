package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;
import net.minecraft.world.gen.placement.ChanceConfig;

public class AtSurfaceUnderTopLayerWithChanceUA extends BasePlacement<ChanceConfig> {
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, ChanceConfig chancesConfig, Feature<C> featureIn, C featureConfig) {

	   int x = random.nextInt(16);
	   int z = random.nextInt(16);
	   int topYLayer = YPositionOfBelowLayer(worldIn, 255, random, pos.add(x, 0 ,z));
	   
	   if(topYLayer < 75) {
		   return false;
	   }
	   
	   int height = random.nextInt(topYLayer-74) + 75;
	   
	     //gets y value of a layer below top layer
	     topYLayer = YPositionOfBelowLayer(worldIn, height, random, pos.add(x, 0 ,z));
	     
	     if(topYLayer < 75) {
	    	 return false;
	     }
	
		 if (random.nextFloat() < 1.0F / chancesConfig.chance) {
			featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x-4, topYLayer-1, z-4), featureConfig);
		 }
	      
	
	    return true;
	}
   
	private int YPositionOfBelowLayer(IWorld worldIn, int height, Random random, BlockPos pos) {
		
         
         //if height is inside a non-air block, move down until we reached an air block
         while(height > 74) {
        	 if(worldIn.isAirBlock(pos.add(0, height, 0))) {
        		 break;
        	 }
        	 
        	 height--;
         }
         
         //if height is an air block, move down until we reached a solid block. We are now on the surface of a piece of land
         while(height > 74) {
        	 if(!worldIn.isAirBlock(pos.add(0, height, 0))) {
        		 break;
        	 }
        	 
        	 height--;
         }
         
		return height;
	}
}
