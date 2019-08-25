package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;

public class RandomPositionEvery5Height extends Placement<FrequencyConfig> {
   public RandomPositionEvery5Height(Function<Dynamic<?>, ? extends FrequencyConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, FrequencyConfig countConfig, BlockPos pos) {
		   int numberOfChunkAttempts = random.nextInt(Math.max(countConfig.count, 1)+1);
	       ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();
		   
		   for(int count = 0; count < numberOfChunkAttempts; ++count) {
			   
			   //offset the attempt so there's less chances of patches without grass
			   int startHeight = worldIn.getSeaLevel()-count;
			   if(startHeight <= 0) {
				   startHeight = 1;
			   }
		   
		      for(int height = startHeight; height <= 255; height += 5) {
		         int x = random.nextInt(16);
		         int z = random.nextInt(16);
		         blockPosList.add(pos.add(x, height, z));
		      }
		   }
		   
		   return IntStream.range(0, blockPosList.size()).mapToObj((p_215051_3_) -> {
		    	return blockPosList.remove(0);
		    });
	   }

}