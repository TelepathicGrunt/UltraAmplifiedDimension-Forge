package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.SimplePlacement;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.CountRangeAndTypeConfig;

public class GeneralPlacement extends SimplePlacement<CountRangeAndTypeConfig> {
   public GeneralPlacement(Function<Dynamic<?>, ? extends CountRangeAndTypeConfig> config) {
	      super(config);
	   }

	   public Stream<BlockPos> getPositions(Random rand, CountRangeAndTypeConfig config, BlockPos pos) {
		   int count;
		   
		   //hacky workaround as biome/feature registration happens at MC startup before the config file is loaded in a world. 
		   //We have to read the config file here as placement is being found to have the config file be read in real time.
		   switch(config.type) {
			   case GLOWSTONE_VARIANT_PATCH:
				   float result = ConfigUA.glowstoneVariantsSpawnrate * config.countModifier;
				   //if the resulting count is less than one, then we switch to probability
				   if(result < 1 && rand.nextFloat() < ConfigUA.glowstoneVariantsSpawnrate * config.countModifier) {
					   count = 1;
				   }else {
					   count = (int)result;
				   }
				   
				   break;

			   case GLOWSTONE:
				   count = (int) (ConfigUA.glowstoneSpawnrate * config.countModifier);
				   break;
				   
			   case MAGMA:
				   count = (int) (ConfigUA.magmaSpawnrate * config.countModifier);
				   break;

			   case QUARTZ:
				   count = (int) (ConfigUA.quartzOreSpawnrate * config.countModifier);
				   break;
				   
			   case EMERALD:
				   count = (int) ((20 + rand.nextInt(35))*((double)(ConfigUA.emeraldOreSpawnrate * config.countModifier)/100));
				   break;

			   case SILVERFISH:
				   count = (int) (ConfigUA.silverfishSpawnrate * config.countModifier);
				   break;
				   
			   case COAL:
				   count = (int) (ConfigUA.coalOreSpawnrate * config.countModifier);
				   break;
				   
			   case IRON:
				   count = (int) (ConfigUA.ironOreSpawnrate * config.countModifier);
				   break;
				   
			   case GOLD:
				   count = (int) (ConfigUA.goldOreSpawnrate * config.countModifier);
				   break;
				   
			   case REDSTONE:
				   count = (int) (ConfigUA.redstoneOreSpawnrate * config.countModifier);
				   break;
				   
			   case DIAMOND:
				   count = (int) (ConfigUA.diamondOreSpawnrate * config.countModifier);
				   break;
				   
			   default:
				   count = (int) config.countModifier;
				   break;
		   }
		   
		   
		   
	      return IntStream.range(0, count).mapToObj((p_215061_3_) -> {
	         int x = rand.nextInt(16);
	         
	         int y = rand.nextInt(config.maximum - config.topOffset) + 
	        		 (config.sealevelBased ? ConfigUA.seaLevel - config.bottomOffset : config.bottomOffset);
	         
	         int z = rand.nextInt(16);
	         return pos.add(x, y, z);
	      });
	   }
	}