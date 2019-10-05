package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;

public class IceAndSnowAtAllLayer extends Feature<NoFeatureConfig> {
   public IceAndSnowAtAllLayer(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos pos, NoFeatureConfig config) {
	      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
	      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

	      for(int xOffset = 0; xOffset < 16; ++xOffset) {
	         for(int zOffset = 0; zOffset < 16; ++zOffset) {
	            int x = pos.getX() + xOffset;
	            int z = pos.getZ() + zOffset;
	            Biome biome = worldIn.getBiome(blockpos$mutableblockpos.setPos(x, 60, z));
	            
		         for(int y = 256; y > ConfigUA.seaLevel-1; --y) {
		        	 
	        		blockpos$mutableblockpos.setPos(x, y, z);
		            blockpos$mutableblockpos1.setPos(blockpos$mutableblockpos).move(Direction.DOWN, 1);
		            
		            
		        	if(worldIn.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.AIR &&
		        	    worldIn.getBlockState(blockpos$mutableblockpos1).getMaterial() != Material.AIR) 
		        	{
		        		//does not freeze top of Cold Ocean and Deep Cold Ocean biomes
			            if (!worldIn.getBlockState(blockpos$mutableblockpos1).getFluidState().isEmpty() &&
			            	biome.doesWaterFreeze(worldIn, blockpos$mutableblockpos1, false) &&
			            	biome != BiomeInit.COLD_OCEAN &&
			            	biome != BiomeInit.DEEP_COLD_OCEAN) 
			            {
			               worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.ICE.getDefaultState(), 2);
			            }
			            
			            if (biome.doesSnowGenerate(worldIn, blockpos$mutableblockpos)) {
			               worldIn.setBlockState(blockpos$mutableblockpos, Blocks.SNOW.getDefaultState(), 2);
			               BlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos1);
			               if (iblockstate.has(SnowyDirtBlock.SNOWY)) {
			                  worldIn.setBlockState(blockpos$mutableblockpos1, iblockstate.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
			               }
			            }

		               //does not check rest of height if it is in Ice Mountain biome
		               if(biome == BiomeInit.ICE_MOUNTAIN) {
		            	   break;
		               }
		        	}
		         }
	         }
	      }

	      return true;
	   }
	}