package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockSeaPickle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.CountConfig;

public class SeaPickleUA extends Feature<CountConfig> {
   public boolean func_212245_a(IWorld worldIn, IChunkGenerator<?> generator, Random random, BlockPos pos, CountConfig configCount) {
	      int howManyPickleGenerated = 0;

	      for(int count = 0; count < configCount.count; ++count) {
	         int xOffset = random.nextInt(8) - random.nextInt(8);
	         int zOffset = random.nextInt(8) - random.nextInt(8);
	         
	         //same as vanilla SeaPickle class but now generates at position we passed in instead of finding the top y value. 
		      //We have placement classes for a reason. Features should not be finding their own positions.
		      
	         BlockPos blockpos = new BlockPos(pos.getX() + xOffset, pos.getY(), pos.getZ() + zOffset);
	         IBlockState pickle = Blocks.SEA_PICKLE.getDefaultState().with(BlockSeaPickle.PICKLES, Integer.valueOf(random.nextInt(4) + 1));
	         
	         if (worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER && pickle.isValidPosition(worldIn, blockpos)) {
	            worldIn.setBlockState(blockpos, pickle, 2);
	            ++howManyPickleGenerated;
	         }
	      }

	      return howManyPickleGenerated > 0;
	   }
	}