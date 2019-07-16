package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
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

public class ChanceOnAllWaterSurfacesUA extends Placement<PercentageAndFrequencyConfig> {
	
	public ChanceOnAllWaterSurfacesUA(Function<Dynamic<?>, ? extends PercentageAndFrequencyConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	private static final BlockState WATER = Blocks.WATER.getDefaultState();
	
   public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, PercentageAndFrequencyConfig pfConfig, BlockPos pos) {
	   int lowestHeight = 40;

       ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();
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
		        	 blockPosList.add(pos.add(x, height+1, z));
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
	   
	    return IntStream.range(0, blockPosList.size()).mapToObj((p_215051_3_) -> {
	    	return blockPosList.remove(0);
	    }).filter(Objects::nonNull);
	   }
	}