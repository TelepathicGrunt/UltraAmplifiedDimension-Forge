package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpTopBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class KelpUA extends Feature<NoFeatureConfig> {
	   public KelpUA(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos blockpos, NoFeatureConfig config) {
		      int i = 0;
		      
		      //same as vanilla kelp class but now generates at position we passed in instead of finding the top y value. 
		      //We have placement classes for a reason. Features should not be finding their own positions.
		      
		      if (worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER) {
		         BlockState iblockstate = Blocks.KELP.getDefaultState();
		         BlockState iblockstate1 = Blocks.KELP_PLANT.getDefaultState();
		         int k = 1 + random.nextInt(10);

		         for(int l = 0; l <= k; ++l) {
		            if (worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER && worldIn.getBlockState(blockpos.up()).getBlock() == Blocks.WATER && iblockstate1.isValidPosition(worldIn, blockpos)) {
		               if (l == k) {
		                  worldIn.setBlockState(blockpos, iblockstate.with(KelpTopBlock.AGE, Integer.valueOf(random.nextInt(23))), 2);
		                  ++i;
		               } else {
		                  worldIn.setBlockState(blockpos, iblockstate1, 2);
		               }
		            } else if (l > 0) {
		               BlockPos blockpos1 = blockpos.down();
		               if (iblockstate.isValidPosition(worldIn, blockpos1) && worldIn.getBlockState(blockpos1.down()).getBlock() != Blocks.KELP) {
		                  worldIn.setBlockState(blockpos1, iblockstate.with(KelpTopBlock.AGE, Integer.valueOf(random.nextInt(23))), 2);
		                  ++i;
		               }
		               break;
		            }

		            blockpos = blockpos.up();
		         }
		      }

		      return i > 0;
		   }
		}