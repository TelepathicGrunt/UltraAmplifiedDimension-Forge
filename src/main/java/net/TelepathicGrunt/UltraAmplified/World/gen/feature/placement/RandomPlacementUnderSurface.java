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
import net.minecraft.world.gen.placement.FrequencyConfig;

public class RandomPlacementUnderSurface  extends BasePlacement<FrequencyConfig> {

public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, FrequencyConfig countConfig, Feature<C> featureIn, C featureConfig) {
	   int count = random.nextInt(Math.max(countConfig.frequency, 1));
	 
	   for(int currentCount = 0; currentCount < count; ++currentCount) {
         int i = random.nextInt(16);
         int j = random.nextInt(16);
         int maxHeight = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(i, 0, j)).getY() - 10;
         
           for(int height = 75; height < maxHeight; height++) {
        	   //rechoose x and z so feature spawns in slightly different places at each height
        	    i = random.nextInt(16);
                j = random.nextInt(16);

		         featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(i, height, j), featureConfig);
	      }
	   }

	      return true;
   }

}