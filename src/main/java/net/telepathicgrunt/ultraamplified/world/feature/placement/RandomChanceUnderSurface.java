package net.telepathicgrunt.ultraamplified.world.feature.placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class RandomChanceUnderSurface extends Placement<ChanceConfig> {

public RandomChanceUnderSurface(Function<Dynamic<?>, ? extends ChanceConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, ChanceConfig chanceConfig, BlockPos pos) {
		   if (random.nextFloat() < 1.0F / (float)chanceConfig.chance) {
		         int x = random.nextInt(16);
		         int z = random.nextInt(16);
		         int height = world.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(x, 0, z)).getY() * 2;
		         
		 		if (height > 75) {
		 			height -= 75;
		 		}
		         
		         if (height <= 0) {
		            return Stream.empty();
		         }
		         
		         int y = random.nextInt(height)+75;

		         return Stream.of(pos.add(x, y, z));
		      }
		   
		   return Stream.empty();
	   }

}