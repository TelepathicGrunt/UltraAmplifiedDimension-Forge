package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockDirtSnowy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SnowFeatureUA extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> p_212245_2_, Random p_212245_3_, BlockPos blockPos, NoFeatureConfig p_212245_5_) {
	      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
	      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

	      for(int xOffset = 0; xOffset < 16; xOffset++) {
	         for(int zOffset = 0; zOffset < 16; zOffset++) {
	            int x = blockPos.getX() + xOffset;
	            int z = blockPos.getZ() + zOffset;
	            int y = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, x, z);
	            blockpos$mutableblockpos.setPos(x, y, z);
	            blockpos$mutableblockpos1.setPos(blockpos$mutableblockpos).move(EnumFacing.DOWN, 1);
	            Biome biome = worldIn.getBiome(blockpos$mutableblockpos);
	            
	            if (biome.doesSnowGenerate(worldIn, blockpos$mutableblockpos)) {
	               worldIn.setBlockState(blockpos$mutableblockpos, Blocks.SNOW.getDefaultState(), 2);
	               IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos1);
	               if (iblockstate.has(BlockDirtSnowy.SNOWY)) {
	                  worldIn.setBlockState(blockpos$mutableblockpos1, iblockstate.with(BlockDirtSnowy.SNOWY, Boolean.valueOf(true)), 2);
	               }
	            }
	         }
	      }

	      return true;
	   }
	}