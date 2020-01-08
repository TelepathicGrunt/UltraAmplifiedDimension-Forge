package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SeaGrassConfig;

public class SeaGrassUA extends Feature<SeaGrassConfig> {
	   public SeaGrassUA(Function<Dynamic<?>, ? extends SeaGrassConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos pos, SeaGrassConfig config) {
		      int i = 0;

		      for(int j = 0; j < config.count; ++j) {
		         int k = random.nextInt(8) - random.nextInt(8);
		         int l = random.nextInt(8) - random.nextInt(8);

			      //same as vanilla SeaGrass class but now generates at position we passed in instead of finding the top y value. 
			      //We have placement classes for a reason. Features should not be finding their own positions.
			      
		         BlockPos blockpos = new BlockPos(pos.getX() + k, pos.getY(), pos.getZ() + l);
		         
		         
		         if (world.getBlockState(blockpos).getBlock() == Blocks.WATER) {
		            boolean flag = random.nextDouble() < config.tallProbability;
		            BlockState iblockstate = flag ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();
		            if (iblockstate.isValidPosition(world, blockpos)) {
		               if (flag) {
		                  BlockState iblockstate1 = iblockstate.with(TallSeaGrassBlock.field_208065_c, DoubleBlockHalf.UPPER);
		                  BlockPos blockpos1 = blockpos.up();
		                  if (world.getBlockState(blockpos1).getBlock() == Blocks.WATER) {
		                     world.setBlockState(blockpos, iblockstate, 2);
		                     world.setBlockState(blockpos1, iblockstate1, 2);
		                  }
		               } else {
		                  world.setBlockState(blockpos, iblockstate, 2);
		               }

		               ++i;
		            }
		         }
		      }

		      return i > 0;
		   }
		}