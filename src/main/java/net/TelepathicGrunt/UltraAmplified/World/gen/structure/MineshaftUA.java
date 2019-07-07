package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Random;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;

import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.minecraft.init.Biomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;

public class MineshaftUA extends Structure<MineshaftConfigUA> {
   protected boolean hasStartAt(IChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
	      ((SharedSeedRandom)rand).setLargeFeatureSeed(chunkGen.getSeed(), chunkPosX, chunkPosZ);
	      Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9), Biomes.DEFAULT);
	      if (chunkGen.hasStructure(biome, FeatureUA.MINESHAFT_UA)) {
	    	  MineshaftConfigUA mineshaftconfig = (MineshaftConfigUA)chunkGen.getStructureConfig(biome, FeatureUA.MINESHAFT_UA);
	         double d0 = mineshaftconfig.field_202439_a;
	         return rand.nextDouble() < d0;
	      } else {
	         return false;
	      }
	   }

	   protected boolean isEnabledIn(IWorld worldIn) {
	      return worldIn.getWorldInfo().isMapFeaturesEnabled();
	   }

	   protected StructureStart makeStart(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom random, int x, int z) {
	      Biome biome = generator.getBiomeProvider().getBiome(new BlockPos((x << 4) + 9, 0, (z << 4) + 9), Biomes.DEFAULT);
	      return new MineshaftUA.Start(worldIn, generator, random, x, z, biome);
	   }

	   protected String getStructureName() {
	      return "Mineshaft UA";
   }

   public int getSize() {
      return 8;
   }

   public static class Start extends StructureStart {
      private MineshaftUA.Type type;

      public Start() {
      }

      public Start(IWorld worldIn, IChunkGenerator<?> chunkGenerator, SharedSeedRandom sharedRandom, int chunkX, int chunkZ, Biome biome) {
         super(chunkX, chunkZ, biome, sharedRandom, worldIn.getSeed());
         MineshaftConfigUA mineshaftconfig = (MineshaftConfigUA)chunkGenerator.getStructureConfig(biome, FeatureUA.MINESHAFT_UA);
         this.type = mineshaftconfig.type;
         MineshaftPiecesUA.Room structuremineshaftpiecesua$room = new MineshaftPiecesUA.Room(0, sharedRandom, (chunkX << 4) + 2, (chunkZ << 4) + 2, this.type);
         this.components.add(structuremineshaftpiecesua$room); 
         
         structuremineshaftpiecesua$room.buildComponent(structuremineshaftpiecesua$room, this.components, sharedRandom);
         this.recalculateStructureSize(worldIn);
         
         //makes the normal sized dirt room mineshafts spawn between 150 and 240
         //otherwise they will spawn at bottom of world
         if(structuremineshaftpiecesua$room.getBoundingBox().maxY < 100) {
             this.setRandomHeight(worldIn, sharedRandom, 150, 151 + sharedRandom.nextInt(70));
             UltraAmplified.Logger.log(Level.DEBUG, "Aboveground Mineshaft | "+this.type.toString()+" | "+(chunkX*16)+" "+(chunkZ*16));
         }else {
             this.setRandomHeight(worldIn, sharedRandom, 10, 11);
             UltraAmplified.Logger.log(Level.DEBUG, "Underground Mineshaft | "+this.type.toString()+" | "+(chunkX*16)+" "+(chunkZ*16));
         }
         
      }
   }

   public static enum Type {
	   NORMAL,
       MESA,
       ICEY,
       COLDORBIRCH,
       JUNGLE,
       TAIGA,
       DESERT,
       STONE,
       SAVANNA,
       SWAMPORDARKFOREST,
       END,
       HELL,
       OCEAN;

      public static MineshaftUA.Type byId(int id) {
         return id >= 0 && id < values().length ? values()[id] : NORMAL;
      }
   }
}
