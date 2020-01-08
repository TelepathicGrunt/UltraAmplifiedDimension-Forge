package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

public class AtSurfaceThroughWaterWithExtra extends Placement<AtSurfaceWithExtraConfig> {
   public AtSurfaceThroughWaterWithExtra(Function<Dynamic<?>, ? extends AtSurfaceWithExtraConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, AtSurfaceWithExtraConfig chancesConfig, BlockPos pos) {
	   int c = chancesConfig.count;
       if (random.nextFloat() < chancesConfig.extraChance) {
          c += chancesConfig.extraCount;
       }
      
	   boolean airWaterFlag = false;
	   boolean airWaterBlock = true;
       ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();
       
	   for (int i = 0; i < c; i++) {
	         int x = random.nextInt(16);
	         int z = random.nextInt(16);
	         int height = 250;
	          
	         
	         while(height > 60) {
	        	 
	        	 airWaterBlock = world.isAirBlock(pos.add(x, height, z)) || world.getBlockState(pos.add(x, height, z)) == Blocks.WATER.getDefaultState();
	        	 
	        	 //if height is at an air/water block and previous block was a solid block, store the fact that we are in an air/water block now
	        	 if(!airWaterFlag && airWaterBlock) {
	        		 airWaterFlag = true;
	        	 }
	        	 
	        	 
	        	 //if height is an solid block and last block was air/water block, we are now on the surface of a piece of land. Generate feature now
	        	 else if(airWaterFlag && !airWaterBlock) {
	        		 
	        		 blockPosList.add(pos.add(x, height+1, z));
	        		 airWaterFlag = false;
	        	 }
	        	 
	        	 //move down
	        	 height--;
	         }

	      }


	    return IntStream.range(0, blockPosList.size()).mapToObj((p_215051_3_) -> {
	    	return blockPosList.remove(0);
	    }).filter(Objects::nonNull);
	    
	   }
	}