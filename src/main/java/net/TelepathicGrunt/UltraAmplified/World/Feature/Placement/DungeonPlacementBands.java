package net.TelepathicGrunt.UltraAmplified.World.Feature.Placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.Placement;

public class DungeonPlacementBands extends Placement<DungeonRoomConfig> {
   public DungeonPlacementBands(Function<Dynamic<?>, ? extends DungeonRoomConfig> configFactoryIn) {
		super(configFactoryIn);
	}

public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, DungeonRoomConfig placementConfig, BlockPos pos) {
	      int count = ConfigUA.dungeonSpawnrate;

	      return IntStream.range(0, count).mapToObj((p_215051_3_) -> {
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
	         
	         return pos.add(x, y, z);
	      });
	   }
	}