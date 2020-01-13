package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;

public class SingleBlock extends Feature<BlockConfig> {
	   public SingleBlock(Function<Dynamic<?>, ? extends BlockConfig> configFactory) {
		super(configFactory);
	}

	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random rand, BlockPos pos, BlockConfig blockConfig) {
	      
	         if (world.isAirBlock(pos) && 
	        	(world.getBlockState(pos.down()) == world.getBiome(pos).getSurfaceBuilderConfig().getTop() ||
	        	 world.getBlockState(pos.down()) == world.getBiome(pos).getSurfaceBuilderConfig().getUnder() )
	        	) {
	            world.setBlockState(pos, blockConfig.block.getDefaultState(), 2);
	         }

		      return true;
		   }
	
	
	
	
}