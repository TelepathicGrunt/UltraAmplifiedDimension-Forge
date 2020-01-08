package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;

public class TreeJungleShrub extends AbstractTreeFeature<BaseTreeFeatureConfig> {
	   private final BlockState leaf;
	   private final BlockState trunk;

	   public TreeJungleShrub(Function<Dynamic<?>, ? extends BaseTreeFeatureConfig> p_i225808_1_, BlockState trunk, BlockState leaf) {
	      super(p_i225808_1_);
	      this.trunk = trunk;
	      this.leaf = leaf;
	   }

	   public boolean func_225557_a_(IWorldGenerationReader world, Random rand, BlockPos position, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox p_208519_5_, BaseTreeFeatureConfig p_225557_7_) {
		  
 	      if (isSoil(world, position.down(), p_225557_7_.getSapling())) {
         	this.setBlockState(world, position, this.trunk);

	         for(int i = position.getY(); i <= position.getY() + 2; ++i) {
	            int j = i - position.getY();
	            int k = 2 - j;

	            for(int l = position.getX() - k; l <= position.getX() + k; ++l) {
	               int i1 = l - position.getX();

	               for(int j1 = position.getZ() - k; j1 <= position.getZ() + k; ++j1) {
	                  int k1 = j1 - position.getZ();
	                  if (Math.abs(i1) != k || Math.abs(k1) != k || rand.nextInt(3) == 0) {
	                     BlockPos blockpos = new BlockPos(l, i, j1);
	                     if (isAirOrLeaves(world, blockpos)) {
	                     	this.setBlockState(world, blockpos, this.leaf);
	                     }
	                  }
	               }
	            }
	         }
	      }

	      return true;
	   }
	}