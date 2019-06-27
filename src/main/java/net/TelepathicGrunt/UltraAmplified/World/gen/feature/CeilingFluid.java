package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LiquidsConfig;

public class CeilingFluid extends Feature<LiquidsConfig> {
   public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkSettings, Random random, BlockPos position, LiquidsConfig liquid) {
	      if (!Block.isRock(worldIn.getBlockState(position.up()).getBlock())) {
	         return false;
	      } else if (Block.isRock(worldIn.getBlockState(position.down()).getBlock())) {
	         return false;
	      } else {
	         IBlockState iblockstate = worldIn.getBlockState(position);
	         if (!iblockstate.isAir(worldIn, position) && !Block.isRock(iblockstate.getBlock())) {
	            return false;
	         } else {
	            int j = 0;
	            
	            if (Block.isRock(worldIn.getBlockState(position.west()).getBlock())) {
	               ++j;
	            }

	            if (Block.isRock(worldIn.getBlockState(position.east()).getBlock())) {
	               ++j;
	            }

	            if (Block.isRock(worldIn.getBlockState(position.north()).getBlock())) {
	               ++j;
	            }

	            if (Block.isRock(worldIn.getBlockState(position.south()).getBlock())) {
	               ++j;
	            }

	            if (j == 4) {
	               worldIn.setBlockState(position, liquid.field_202459_a.getDefaultState().getBlockState(), 2);
	               worldIn.getPendingFluidTicks().scheduleTick(position, liquid.field_202459_a, 0);
	            }

	            return true;
	         }
	      }
	   }
	}