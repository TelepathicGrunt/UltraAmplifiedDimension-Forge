package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.BasePlacement;

public class AtSurfaceRoofedForestUA extends BasePlacement<AtSurfaceWithExtraConfig> {
	   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, AtSurfaceWithExtraConfig chancesConfig, Feature<C> featureIn, C featureConfig) {
		   int c = chancesConfig.baseCount;
	       if (random.nextFloat() < chancesConfig.extraChance) {
	          c += chancesConfig.extraCount;
	       }
	      
		   boolean airFlag = false;
		   boolean airBlock = true;
		   for (int i = 0; i < c; i++) {
			   int height = 250;
			   
		         while(height > 74) {
		        	 int x = random.nextInt(16);
			         int z = random.nextInt(16);
			         
		        	 airBlock = worldIn.isAirBlock(pos.add(x, height, z));
		        	 
		        	 //if height is is an air block and previous block was a solid block, store the fact that we are in an air block now
		        	 if(!airFlag && airBlock) {
		        		 airFlag = true;
		        	 }
		        	 
		        	 
		        	 //if height is an solid block and last block was air block, we are now on the surface of a piece of land. Generate feature now
		        	 else if(airFlag && !airBlock) {
		        		 featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x, height, z), featureConfig); 
		  			     
		        		 airFlag = false;
		        	 }
		        	 
		        	 //move down
		        	 height--;
				       
			      }
		      }

		      return true;
		   }
		}