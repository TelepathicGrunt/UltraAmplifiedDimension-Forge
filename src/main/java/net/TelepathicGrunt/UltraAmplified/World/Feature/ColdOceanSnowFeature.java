package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;

public class ColdOceanSnowFeature extends Feature<NoFeatureConfig> {
   public ColdOceanSnowFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> p_212245_2_, Random p_212245_3_, BlockPos blockPos, NoFeatureConfig p_212245_5_) {
	      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
	      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

	      for(int xOffset = 0; xOffset < 16; xOffset++) {
	         for(int zOffset = 0; zOffset < 16; zOffset++) {
	            int x = blockPos.getX() + xOffset;
	            int z = blockPos.getZ() + zOffset;
	            int y = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, x, z);
	            blockpos$mutableblockpos.setPos(x, y, z);
	            blockpos$mutableblockpos1.setPos(blockpos$mutableblockpos).move(Direction.DOWN, 1);
	            Biome biome = worldIn.getBiome(blockpos$mutableblockpos);
	            
	            //decorates only cold ocean with just snow
	            if(biome == BiomeInit.COLD_OCEAN || biome == BiomeInit.DEEP_COLD_OCEAN) {
	            	if (blockpos$mutableblockpos.getY() >= 0 && blockpos$mutableblockpos.getY() < 256 && worldIn.getLightFor(LightType.BLOCK, blockpos$mutableblockpos) < 10) {
		                
	            		BlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);
		                if (iblockstate.isAir(worldIn, blockpos$mutableblockpos) && Blocks.SNOW.getDefaultState().isValidPosition(worldIn, blockpos$mutableblockpos)) {
		                   
		                	worldIn.setBlockState(blockpos$mutableblockpos, Blocks.SNOW.getDefaultState(), 2);
			                BlockState iblockstate2 = worldIn.getBlockState(blockpos$mutableblockpos1);
			                
			                if (iblockstate2.has(SnowyDirtBlock.SNOWY)) {
			                   worldIn.setBlockState(blockpos$mutableblockpos1, iblockstate2.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
			                }
		                }
	            	}
	            }
	            //does normal default snow/ice for non-cold ocean bordering this biome
	            else {
	            	 if (biome.doesWaterFreeze(worldIn, blockpos$mutableblockpos1, false)) {
	            		 worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.ICE.getDefaultState(), 2);
	                  }

	                  if (biome.doesSnowGenerate(worldIn, blockpos$mutableblockpos)) {
	                	  worldIn.setBlockState(blockpos$mutableblockpos, Blocks.SNOW.getDefaultState(), 2);
	                     BlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos1);
	                     if (iblockstate.has(SnowyDirtBlock.SNOWY)) {
	                    	 worldIn.setBlockState(blockpos$mutableblockpos1, iblockstate.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
	                     }
	                  }
	            }
	         }
	      }

	      return true;
	   }
	}