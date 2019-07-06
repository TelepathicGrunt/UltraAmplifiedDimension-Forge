package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;

public class ChanceOnAllWaterBottomsUA extends BasePlacement<PercentageAndFrequencyConfig> {
	

	protected static final Set<IBlockState> UNACCEPTABLE_BLOCKS = 
    		Stream.of(
	    		Blocks.PRISMARINE.getDefaultState(),
	    		Blocks.PRISMARINE_BRICKS.getDefaultState(),
	    		Blocks.DARK_PRISMARINE.getDefaultState(),
	    		Blocks.SEA_LANTERN.getDefaultState()
    		).collect(Collectors.toCollection(HashSet::new));
	
	
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, PercentageAndFrequencyConfig pfConfig, Feature<C> featureIn, C featureConfig) {
       int lowestHeight = 40;
	   
	   for(int i = 0; i < pfConfig.frequency; i++) {
		   
	       int height = 255;
		   
	       while(height > lowestHeight) {
		         int x = random.nextInt(16);
		         int z = random.nextInt(16);
		         

		         //if height is inside a non-water block, move down until we reached a water block
		         while(height > lowestHeight) {
		        	 if(!worldIn.getBlockState(pos.add(x, height, z)).getFluidState().isEmpty()) {
		        		 break;
		        	 }
		        	 
		        	 height--;
		         }
		         
		        
		         //if height is a water block, move down until we reached a non-water block. We are now on the bottom surface of a body of water
		         //ignores Ocean Monument blocks
		         IBlockState currentBlock;
		         while(height > lowestHeight) {
		        	 currentBlock = worldIn.getBlockState(pos.add(x, height, z));
		        	 
		        	 if(currentBlock.getFluidState().isEmpty() && 
		        		!UNACCEPTABLE_BLOCKS.contains(currentBlock))
		        	 {
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
		         
		     }
	       
		  }
	      return true;
	   }
	}