package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

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

public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, LiquidsConfig config) {
	      if (!config.field_227366_f_.contains(worldIn.getBlockState(position.up()).getBlock())) {
	         return false;
	      } else if (config.field_227366_f_.contains(worldIn.getBlockState(position.down()).getBlock())) {
	         return false;
	      } else {
	         BlockState iblockstate = worldIn.getBlockState(position);
	         if (!iblockstate.isAir(worldIn, position) && !config.field_227366_f_.contains(iblockstate.getBlock())) {
	            return false;
	         } else {
	            int j = 0;

                for (Direction face : Direction.Plane.HORIZONTAL) 
                {
    	            if (config.field_227366_f_.contains(worldIn.getBlockState(position.offset(face)).getBlock())) {
    	               ++j;
    	            }
                }

	            if (j == 4) {
	               worldIn.setBlockState(position, config.state.getBlockState(), 2);
	               worldIn.getPendingFluidTicks().scheduleTick(position, config.state.getFluid(), 0);
	            }

	            return true;
	         }
	      }
	   }
	}