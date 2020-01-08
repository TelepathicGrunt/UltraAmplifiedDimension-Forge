package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.AbstractBigMushroomFeature;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

public class BigRedMushroomTempFix extends AbstractBigMushroomFeature {
   public BigRedMushroomTempFix(Function<Dynamic<?>, ? extends BigMushroomFeatureConfig> p_i49863_1_) {
	      super(p_i49863_1_);
	   }

	   protected void mushroomCap(IWorld p_225564_1_, Random p_225564_2_, BlockPos p_225564_3_, int p_225564_4_, BlockPos.Mutable p_225564_5_, BigMushroomFeatureConfig p_225564_6_) {
	      for(int i = p_225564_4_ - 3; i <= p_225564_4_; ++i) {
	         int j = i < p_225564_4_ ? p_225564_6_.field_227274_c_ : p_225564_6_.field_227274_c_ - 1;
	         int k = p_225564_6_.field_227274_c_ - 2;

	         for(int l = -j; l <= j; ++l) {
	            for(int i1 = -j; i1 <= j; ++i1) {
	               boolean flag = l == -j;
	               boolean flag1 = l == j;
	               boolean flag2 = i1 == -j;
	               boolean flag3 = i1 == j;
	               boolean flag4 = flag || flag1;
	               boolean flag5 = flag2 || flag3;
	               if (i >= p_225564_4_ || flag4 != flag5) {
	                  p_225564_5_.setPos(p_225564_3_).move(l, i, i1);
	                  
	     	         //fixed forge bug where it was only placing block in solid blocks instead of air or leaves
	                  if (p_225564_1_.getBlockState(p_225564_5_).canBeReplacedByLeaves(p_225564_1_, p_225564_5_)) {
	                     this.setBlockState(p_225564_1_, p_225564_5_, p_225564_6_.field_227272_a_.func_225574_a_(p_225564_2_, p_225564_3_).with(HugeMushroomBlock.UP, Boolean.valueOf(i >= p_225564_4_ - 1)).with(HugeMushroomBlock.WEST, Boolean.valueOf(l < -k)).with(HugeMushroomBlock.EAST, Boolean.valueOf(l > k)).with(HugeMushroomBlock.NORTH, Boolean.valueOf(i1 < -k)).with(HugeMushroomBlock.SOUTH, Boolean.valueOf(i1 > k)));
	                  }
	               }
	            }
	         }
	      }

	   }

	   protected int func_225563_a_(int p_225563_1_, int p_225563_2_, int p_225563_3_, int p_225563_4_) {
	      int i = 0;
	      if (p_225563_4_ < p_225563_2_ && p_225563_4_ >= p_225563_2_ - 3) {
	         i = p_225563_3_;
	      } else if (p_225563_4_ == p_225563_2_) {
	         i = p_225563_3_;
	      }

	      return i;
	   }

	   protected void mushroomStem(IWorld p_227210_1_, Random p_227210_2_, BlockPos p_227210_3_, BigMushroomFeatureConfig p_227210_4_, int p_227210_5_, BlockPos.Mutable p_227210_6_) {
	      for(int i = 0; i < p_227210_5_; ++i) {
	         p_227210_6_.setPos(p_227210_3_).move(Direction.UP, i);
	         
	         //fixed forge bug where it was only placing block in solid blocks instead of air or leaves
	         if (p_227210_1_.getBlockState(p_227210_6_).canBeReplacedByLeaves(p_227210_1_, p_227210_6_)) {
	            this.setBlockState(p_227210_1_, p_227210_6_, p_227210_4_.field_227273_b_.func_225574_a_(p_227210_2_, p_227210_3_));
	         }
	      }

	   }

	   protected boolean validatePosition(IWorld p_227209_1_, BlockPos p_227209_2_, int p_227209_3_, BlockPos.Mutable p_227209_4_, BigMushroomFeatureConfig p_227209_5_) {
	      int i = p_227209_2_.getY();
	      if (i >= 1 && i + p_227209_3_ + 1 < p_227209_1_.getMaxHeight()) {

	    	  //forge fix as it forgot check for mycelium for mushrooms
	         Block block = p_227209_1_.getBlockState(p_227209_2_.down()).getBlock();
	         if (!func_227250_b_(block) && block != Blocks.MYCELIUM) {
	            return false;
	         } else {
	            for(int j = 0; j <= p_227209_3_; ++j) {
	               int k = this.func_225563_a_(-1, -1, p_227209_5_.field_227274_c_, j);

	               for(int l = -k; l <= k; ++l) {
	                  for(int i1 = -k; i1 <= k; ++i1) {
	                     BlockState blockstate = p_227209_1_.getBlockState(p_227209_4_.setPos(p_227209_2_).move(l, j, i1));
	                     if (!blockstate.isAir(p_227209_1_, p_227209_4_) && !blockstate.isIn(BlockTags.LEAVES)) {
	                        return false;
	                     }
	                  }
	               }
	            }

	            return true;
	         }
	      } else {
	         return false;
	      }
	   }
	   
	   @Override
	   public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, BigMushroomFeatureConfig config) {
	      int i = this.func_227211_a_(rand);
	      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
	      if (!this.validatePosition(world, pos, i, blockpos$mutable, config)) {
	         return false;
	      } else {
	         this.mushroomCap(world, rand, pos, i, blockpos$mutable, config);
	         this.mushroomStem(world, rand, pos, config, i, blockpos$mutable);
	         return true;
	      }
	   }

	   
	   //isn't called in our place()
		@Override
		protected void func_225564_a_(IWorld p_225564_1_, Random p_225564_2_, BlockPos p_225564_3_, int p_225564_4_,
				Mutable p_225564_5_, BigMushroomFeatureConfig p_225564_6_) {
		}
	}