package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.BasePlacement;

public class AtSurfaceThroughWaterWithExtraUA extends BasePlacement<AtSurfaceWithExtraConfig> {
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, AtSurfaceWithExtraConfig chancesConfig, Feature<C> featureIn, C featureConfig) {
	   int c = chancesConfig.baseCount;
       if (random.nextFloat() < chancesConfig.extraChance) {
          c += chancesConfig.extraCount;
       }
      
	   boolean airWaterFlag = false;
	   boolean airWaterBlock = true;
	   for (int i = 0; i < c; i++) {
	         int x = random.nextInt(16);
	         int z = random.nextInt(16);
	         int height = 250;
	          
	         
	         while(height > 60) {
	        	 
	        	 airWaterBlock = worldIn.isAirBlock(pos.add(x, height, z)) || worldIn.getBlockState(pos.add(x, height, z)) == Blocks.WATER.getDefaultState();
	        	 
	        	 //if height is at an air/water block and previous block was a solid block, store the fact that we are in an air/water block now
	        	 if(!airWaterFlag && airWaterBlock) {
	        		 airWaterFlag = true;
	        	 }
	        	 
	        	 
	        	 //if height is an solid block and last block was air/water block, we are now on the surface of a piece of land. Generate feature now
	        	 else if(airWaterFlag && !airWaterBlock) {
	        		 
	        		 featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x, height+1, z), featureConfig);
	        		 airWaterFlag = false;
	        	 }
	        	 
	        	 //move down
	        	 height--;
	         }

	      }

	      return true;
	   }
	}