package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.world.feature.config.PercentageAndFrequencyConfig;

public class ChanceOnAllLiquidBottoms extends Placement<PercentageAndFrequencyConfig> {
	

	public ChanceOnAllLiquidBottoms(Function<Dynamic<?>, ? extends PercentageAndFrequencyConfig> configFactoryIn) {
		super(configFactoryIn);
	}


	protected static final Set<BlockState> UNACCEPTABLE_BLOCKS = 
    		Stream.of(
	    		Blocks.PRISMARINE.getDefaultState(),
	    		Blocks.PRISMARINE_BRICKS.getDefaultState(),
	    		Blocks.DARK_PRISMARINE.getDefaultState(),
	    		Blocks.SEA_LANTERN.getDefaultState()
    		).collect(Collectors.toCollection(HashSet::new));
	
	
   public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, PercentageAndFrequencyConfig pfConfig, BlockPos pos) {
       int lowestHeight = 40;

       ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();
       
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
		         BlockState currentBlock;
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
		        	 blockPosList.add(pos.add(x, height+1, z));
		         }
		         
		     }
	       
		  }
	   
	   return IntStream.range(0, blockPosList.size()).mapToObj((p_215051_3_) -> {
	    	return blockPosList.remove(0);
	    }).filter(Objects::nonNull);
	   }
	}