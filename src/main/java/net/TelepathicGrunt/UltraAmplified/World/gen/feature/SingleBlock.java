package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.BlockConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class SingleBlock extends Feature<BlockConfig> {
	   public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkSettings, Random rand, BlockPos pos, BlockConfig blockConfig) {
	      
	         if (worldIn.isAirBlock(pos) && 
	        	(worldIn.getBlockState(pos.down()) == worldIn.getBiome(pos).getSurfaceBuilderConfig().getTop() ||
	        	 worldIn.getBlockState(pos.down()) == worldIn.getBiome(pos).getSurfaceBuilderConfig().getMiddle() )
	        	) {
	            worldIn.setBlockState(pos, blockConfig.block.getDefaultState(), 2);
	         }

		      return true;
		   }
		}