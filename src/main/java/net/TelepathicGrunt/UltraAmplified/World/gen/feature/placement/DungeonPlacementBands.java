package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
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
	      int count = ConfigUA.dungeonSpawnrate;

	      for(int currentCount = 0; currentCount < count; ++currentCount) {
	         int x = random.nextInt(16);
	         int z = random.nextInt(16);
	         int y;
	         int rand = random.nextInt(100);
	         
	         //30% chance
            if(rand < 30) {
            	 y = random.nextInt(26)+75;
            	 //range: 75 - 100
            }
             //35% chance
            else if(rand < 65) 
            {
        		 y = random.nextInt(160)+1;
        		 //range 1 - 160
        	}
             //35%
            else 
        	{
            	 y = random.nextInt(86)+160;
            	 //range: 160 - 245
        	}
	         
	         featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x, y, z), featureConfig);
	      }

	      return true;
	   }
	}