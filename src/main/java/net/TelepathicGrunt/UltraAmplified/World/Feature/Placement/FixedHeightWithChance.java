package net.TelepathicGrunt.UltraAmplified.World.Feature.Placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.World.Feature.Config.PercentageAndHeightConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.Placement;

public class FixedHeightWithChance extends Placement<PercentageAndHeightConfig> {
	
	public FixedHeightWithChance(Function<Dynamic<?>, ? extends PercentageAndHeightConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, PercentageAndHeightConfig percentageAndHeightConfig, BlockPos pos) {
	   
   	   if(random.nextFloat() < percentageAndHeightConfig.percentage) {
	   			
   		    pos = pos.up(percentageAndHeightConfig.height);
   		   
   		 return Stream.of(pos);
   	   }

       return Stream.empty();
   }
}