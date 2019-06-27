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

public class AtSurfaceWithChanceUA extends BasePlacement<ChanceConfig> {
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, ChanceConfig chancesConfig, Feature<C> featureIn, C featureConfig) {
	 
	   int height = random.nextInt(180)+75;
	   
	   while(height > 74) {
	         int x = random.nextInt(16);
	         int z = random.nextInt(16);
	         
	         //if height is inside a non-air block, move down until we reached an air block
	         while(height > 74) {
	        	 if(worldIn.isAirBlock(pos.add(x, height, z))) {
	        		 break;
	        	 }
	        	 
	        	 height--;
	         }
	         
	         //if height is an air block, move down until we reached a solid block. We are now on the surface of a piece of land
	         while(height > 74) {
	        	 if(!worldIn.isAirBlock(pos.add(x, height, z))) {
	        		 break;
	        	 }
	        	 
	        	 height--;
	         }
	         
	         if (height <= 74) {
	            return false;
	         }
			if (random.nextFloat() < 1.0F / chancesConfig.chance) {
				featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x, height, z), featureConfig);
			 }
	      }

	      return true;
	   }
	}