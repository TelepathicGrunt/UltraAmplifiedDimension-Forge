package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;
import net.minecraft.world.gen.placement.DungeonRoomConfig;

public class DungeonPlacementBands extends BasePlacement<DungeonRoomConfig> {
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, DungeonRoomConfig placementConfig, Feature<C> featureIn, C featureConfig) {
	      int i = placementConfig.count;

	      for(int j = 0; j < i; ++j) {
	         int x = random.nextInt(16);
	         int z = random.nextInt(16);
	         int y;
	         int rand = random.nextInt(1000);
	         
	         //4% chance
            if(rand < 40) {
            	 y = random.nextInt(11)+75;
            	 //range: 75 - 85
            }
             //2% chance
            else if(rand < 60) 
            {
        		 y = random.nextInt(180);
        		 //range 0 - 179
        	}
             //94%
            else 
        	{
            	 y = random.nextInt(66)+180;
            	 //range: 180 - 245
        	}
	         
	         featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x, y, z), featureConfig);
	      }

	      return true;
	   }
	}