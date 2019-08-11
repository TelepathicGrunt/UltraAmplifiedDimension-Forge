package net.TelepathicGrunt.UltraAmplified.World.Feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.World.Feature.Config.BlockConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;

public class SingleBlock extends Feature<BlockConfig> {
	   public SingleBlock(Function<Dynamic<?>, ? extends BlockConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random rand, BlockPos pos, BlockConfig blockConfig) {
	      
	         if (worldIn.isAirBlock(pos) && 
	        	(worldIn.getBlockState(pos.down()) == worldIn.getBiome(pos).getSurfaceBuilderConfig().getTop() ||
	        	 worldIn.getBlockState(pos.down()) == worldIn.getBiome(pos).getSurfaceBuilderConfig().getUnder() )
	        	) {
	            worldIn.setBlockState(pos, blockConfig.block.getDefaultState(), 2);
	         }

		      return true;
		   }
	
	
	
	
}