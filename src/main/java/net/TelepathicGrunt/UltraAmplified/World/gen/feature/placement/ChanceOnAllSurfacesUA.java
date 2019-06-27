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

public class ChanceOnAllSurfacesUA extends BasePlacement<PercentageAndFrequencyConfig> {
	private final IBlockState SAND =  Blocks.SAND.getDefaultState();
	private final IBlockState SOULSAND =  Blocks.SOUL_SAND.getDefaultState();
	
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, PercentageAndFrequencyConfig pfConfig, Feature<C> featureIn, C featureConfig) {
	   
	   for(int i = 0; i < pfConfig.frequency; i++) {
		   
	       int height = 255;
		   
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
		        	 if(worldIn.getBiome(pos.add(x, height, z)).getSurfaceBuilderConfig().getTop() == worldIn.getBlockState(pos.add(x, height, z)) ||
		        		worldIn.getBlockState(pos.add(x, height, z)) == SAND ||
		        		worldIn.getBlockState(pos.add(x, height, z)) == SOULSAND) 
		        	 {
		        		 break;
		        	 }
		        	 
		        	 height--;
		         }
		         
		         
		         if (height <= 74) {
		            break;
		         }
		         else if(random.nextFloat() < pfConfig.percentage) {
			         featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x, height, z), featureConfig);
		         }
		         
		     }
	       
		  }
	      return true;
	   }
	}