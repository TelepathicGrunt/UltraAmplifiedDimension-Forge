package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.CoralClawFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class CoralClaw extends CoralClawFeature{
	   public CoralClaw(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49898_1_) {
	      super(p_i49898_1_);
	   }

	   @Override
	   protected boolean func_204624_b(IWorld p_204624_1_, Random p_204624_2_, BlockPos p_204624_3_, BlockState p_204624_4_) {
	      BlockPos blockpos = p_204624_3_.up();
	      BlockState blockstate = p_204624_1_.getBlockState(p_204624_3_);
	      if ((blockstate.getBlock() == Blocks.WATER || blockstate.getBlock() == Blocks.LAVA || blockstate.isIn(BlockTags.CORALS)) && (p_204624_1_.getBlockState(blockpos).getBlock() == Blocks.WATER ||  p_204624_1_.getBlockState(blockpos).getBlock() == Blocks.LAVA)) {
	         p_204624_1_.setBlockState(p_204624_3_, p_204624_4_, 3);
	         if (p_204624_2_.nextFloat() < 0.25F) {
	            p_204624_1_.setBlockState(blockpos, BlockTags.CORALS.getRandomElement(p_204624_2_).getDefaultState(), 2);
	         } else if (p_204624_2_.nextFloat() < 0.05F) {
	            p_204624_1_.setBlockState(blockpos, Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, Integer.valueOf(p_204624_2_.nextInt(4) + 1)), 2);
	         }

	         for(Direction direction : Direction.Plane.HORIZONTAL) {
	            if (p_204624_2_.nextFloat() < 0.2F) {
	               BlockPos blockpos1 = p_204624_3_.offset(direction);
	               if (p_204624_1_.getBlockState(blockpos1).getBlock() == Blocks.WATER || p_204624_1_.getBlockState(blockpos1).getBlock() == Blocks.LAVA) {
	                  BlockState blockstate1 = BlockTags.WALL_CORALS.getRandomElement(p_204624_2_).getDefaultState().with(DeadCoralWallFanBlock.FACING, direction);
	                  p_204624_1_.setBlockState(blockpos1, blockstate1, 2);
	               }
	            }
	         }

	         return true;
	      } else {
	         return false;
	      }
	   }
	}