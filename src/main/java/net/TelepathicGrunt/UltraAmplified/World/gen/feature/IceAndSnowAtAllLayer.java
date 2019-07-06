package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.minecraft.block.BlockDirtSnowy;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class IceAndSnowAtAllLayer extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkSettings, Random random, BlockPos pos, NoFeatureConfig config) {
	      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
	      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

	      for(int xOffset = 0; xOffset < 16; ++xOffset) {
	         for(int zOffset = 0; zOffset < 16; ++zOffset) {
	            int x = pos.getX() + xOffset;
	            int z = pos.getZ() + zOffset;
		         for(int y = 256; y > Config.seaLevel-1; --y) {
		        	 
	        		blockpos$mutableblockpos.setPos(x, y, z);
		            blockpos$mutableblockpos1.setPos(blockpos$mutableblockpos).move(EnumFacing.DOWN, 1);
		            
		            
		        	if(worldIn.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.AIR &&
		        	    worldIn.getBlockState(blockpos$mutableblockpos1).getMaterial() != Material.AIR) 
		        	{
			            Biome biome = worldIn.getBiome(blockpos$mutableblockpos);
			            if (!worldIn.getBlockState(blockpos$mutableblockpos1).getFluidState().isEmpty() &&
			            	biome.doesWaterFreeze(worldIn, blockpos$mutableblockpos1, false)) 
			            {
			               worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.ICE.getDefaultState(), 2);
			            }
			            
			            if (biome.doesSnowGenerate(worldIn, blockpos$mutableblockpos)) {
			               worldIn.setBlockState(blockpos$mutableblockpos, Blocks.SNOW.getDefaultState(), 2);
			               IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos1);
			               if (iblockstate.has(BlockDirtSnowy.SNOWY)) {
			                  worldIn.setBlockState(blockpos$mutableblockpos1, iblockstate.with(BlockDirtSnowy.SNOWY, Boolean.valueOf(true)), 2);
			               }
			            }
		        	}
		         }
	         }
	      }

	      return true;
	   }
	}