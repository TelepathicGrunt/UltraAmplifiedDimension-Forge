package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class TwiceSurfaceWithChance extends Placement<ChanceConfig> {
   public TwiceSurfaceWithChance(Function<Dynamic<?>, ? extends ChanceConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceConfig placementConfig, BlockPos pos) {
	      if (random.nextFloat() < 1.0F / (float)placementConfig.chance) {
	         int x = random.nextInt(16);
	         int z = random.nextInt(16);
	         int height = random.nextInt(180)+75;
	         
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
	            return Stream.empty();
	         }

	         Stream.of(pos.add(x, height, z));
	      }

	      return Stream.empty();
	   }
	}