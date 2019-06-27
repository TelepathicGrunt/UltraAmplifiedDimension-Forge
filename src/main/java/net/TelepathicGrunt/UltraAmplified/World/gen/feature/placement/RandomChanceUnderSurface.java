package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;
import net.minecraft.world.gen.placement.ChanceConfig;

public class RandomChanceUnderSurface extends BasePlacement<ChanceConfig> {

public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, ChanceConfig chanceConfig, Feature<C> featureIn, C featureConfig) {
		   if (random.nextFloat() < 1.0F / (float)chanceConfig.chance) {
		         int i = random.nextInt(16);
		         int j = random.nextInt(16);
		         int height = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(i, 0, j)).getY() * 2;
		         
		 		if (height > 75) {
		 			height -= 75;
		 		}
		         
		         if (height <= 0) {
		            return false;
		         }
		         
		         int y = random.nextInt(height)+75;

		         featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(i, y, j), featureConfig);
		      }

	      return true;
	   }

}