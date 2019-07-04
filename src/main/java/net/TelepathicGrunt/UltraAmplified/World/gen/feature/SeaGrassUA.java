package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockSeaGrassTall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SeaGrassConfig;

public class SeaGrassUA extends Feature<SeaGrassConfig> {
	   public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkSettings, Random random, BlockPos pos, SeaGrassConfig config) {
		      int i = 0;

		      for(int j = 0; j < config.field_203237_a; ++j) {
		         int k = random.nextInt(8) - random.nextInt(8);
		         int l = random.nextInt(8) - random.nextInt(8);

			      //same as vanilla SeaGrass class but now generates at position we passed in instead of finding the top y value. 
			      //We have placement classes for a reason. Features should not be finding their own positions.
			      
		         BlockPos blockpos = new BlockPos(pos.getX() + k, pos.getY(), pos.getZ() + l);
		         
		         
		         if (worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER) {
		            boolean flag = random.nextDouble() < config.field_203238_b;
		            IBlockState iblockstate = flag ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();
		            if (iblockstate.isValidPosition(worldIn, blockpos)) {
		               if (flag) {
		                  IBlockState iblockstate1 = iblockstate.with(BlockSeaGrassTall.field_208065_c, DoubleBlockHalf.UPPER);
		                  BlockPos blockpos1 = blockpos.up();
		                  if (worldIn.getBlockState(blockpos1).getBlock() == Blocks.WATER) {
		                     worldIn.setBlockState(blockpos, iblockstate, 2);
		                     worldIn.setBlockState(blockpos1, iblockstate1, 2);
		                  }
		               } else {
		                  worldIn.setBlockState(blockpos, iblockstate, 2);
		               }

		               ++i;
		            }
		         }
		      }

		      return i > 0;
		   }
		}