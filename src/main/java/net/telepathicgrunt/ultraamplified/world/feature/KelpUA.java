package net.telepathicgrunt.ultraamplified.world.feature;

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
	   public KelpUA(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory) {
		super(configFactory);
	}

	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos blockpos, NoFeatureConfig config) {
		      int i = 0;
		      
		      //same as vanilla kelp class but now generates at position we passed in instead of finding the top y value. 
		      //We have placement classes for a reason. Features should not be finding their own positions.
		      
		      if (world.getBlockState(blockpos).getBlock() == Blocks.WATER) {
		         BlockState iblockstate = Blocks.KELP.getDefaultState();
		         BlockState iblockstate1 = Blocks.KELP_PLANT.getDefaultState();
		         int k = 1 + random.nextInt(10);

		         for(int l = 0; l <= k; ++l) {
		            if (world.getBlockState(blockpos).getBlock() == Blocks.WATER && world.getBlockState(blockpos.up()).getBlock() == Blocks.WATER && iblockstate1.isValidPosition(world, blockpos)) {
		               if (l == k) {
		                  world.setBlockState(blockpos, iblockstate.with(KelpTopBlock.AGE, Integer.valueOf(random.nextInt(23))), 2);
		                  ++i;
		               } else {
		                  world.setBlockState(blockpos, iblockstate1, 2);
		               }
		            } else if (l > 0) {
		               BlockPos blockpos1 = blockpos.down();
		               if (iblockstate.isValidPosition(world, blockpos1) && world.getBlockState(blockpos1.down()).getBlock() != Blocks.KELP) {
		                  world.setBlockState(blockpos1, iblockstate.with(KelpTopBlock.AGE, Integer.valueOf(random.nextInt(23))), 2);
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