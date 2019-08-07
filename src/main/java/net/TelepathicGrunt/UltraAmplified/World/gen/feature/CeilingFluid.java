package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LiquidsConfig;

public class CeilingFluid extends Feature<LiquidsConfig> {
   public CeilingFluid(Function<Dynamic<?>, ? extends LiquidsConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, LiquidsConfig liquid) {
	      if (!Block.isRock(worldIn.getBlockState(position.up()).getBlock())) {
	         return false;
	      } else if (Block.isRock(worldIn.getBlockState(position.down()).getBlock())) {
	         return false;
	      } else {
	         BlockState iblockstate = worldIn.getBlockState(position);
	         if (!iblockstate.isAir(worldIn, position) && !Block.isRock(iblockstate.getBlock())) {
	            return false;
	         } else {
	            int j = 0;

                for (Direction face : Direction.Plane.HORIZONTAL) 
                {
    	            if (Block.isRock(worldIn.getBlockState(position.offset(face)).getBlock())) {
    	               ++j;
    	            }
                }

	            if (j == 4) {
	               worldIn.setBlockState(position, liquid.state.getBlockState(), 2);
	               worldIn.getPendingFluidTicks().scheduleTick(position, liquid.state.getFluid(), 0);
	            }

	            return true;
	         }
	      }
	   }
	}