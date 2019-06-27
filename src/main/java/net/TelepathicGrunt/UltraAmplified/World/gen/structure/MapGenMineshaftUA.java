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

public class MapGenMineshaftUA extends Structure<MineshaftConfigUA> {
   protected boolean hasStartAt(IChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
	      ((SharedSeedRandom)rand).setLargeFeatureSeed(chunkGen.getSeed(), chunkPosX, chunkPosZ);
	      Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9), Biomes.DEFAULT);
	      UltraAmplified.Logger.log(Level.DEBUG, "Mineshaft Attempted");
	      if (chunkGen.hasStructure(biome, FeatureUA.MINESHAFT_UA)) {
		      UltraAmplified.Logger.log(Level.DEBUG, "Mineshaft Allowed");
	    	  MineshaftConfigUA mineshaftconfig = (MineshaftConfigUA)chunkGen.getStructureConfig(biome, FeatureUA.MINESHAFT_UA);
	         double d0 = mineshaftconfig.field_202439_a / 10000;
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
	      return new MapGenMineshaftUA.Start(worldIn, generator, random, x, z, biome);
	   }

	   protected String getStructureName() {
	      return "UA Mineshaft";
   }

   public int getSize() {
      return 8;
   }

   public static class Start extends StructureStart {
      private MapGenMineshaftUA.Type type;

      public Start() {
      }

      public Start(IWorld p_i48759_1_, IChunkGenerator<?> p_i48759_2_, SharedSeedRandom p_i48759_3_, int p_i48759_4_, int p_i48759_5_, Biome p_i48759_6_) {
         super(p_i48759_4_, p_i48759_5_, p_i48759_6_, p_i48759_3_, p_i48759_1_.getSeed());
         MineshaftConfigUA mineshaftconfig = (MineshaftConfigUA)p_i48759_2_.getStructureConfig(p_i48759_6_, FeatureUA.MINESHAFT_UA);
         this.type = mineshaftconfig.type;
         StructureMineshaftPiecesUA.Room structuremineshaftpiecesua$room = new StructureMineshaftPiecesUA.Room(0, p_i48759_3_, (p_i48759_4_ << 4) + 2, (p_i48759_5_ << 4) + 2, this.type);
         this.components.add(structuremineshaftpiecesua$room);
         structuremineshaftpiecesua$room.buildComponent(structuremineshaftpiecesua$room, this.components, p_i48759_3_);
         this.recalculateStructureSize(p_i48759_1_);

         //makes the normal sized dirt room mineshafts spawn between 150 and 240
         //otherwise they will spawn at bottom of world
         if(structuremineshaftpiecesua$room.getBoundingBox().maxY < 100) {
        	 structuremineshaftpiecesua$room.getBoundingBox().offset(0, 150 + p_i48759_3_.nextInt(70), 0);
         }
         
         UltraAmplified.Logger.log(Level.DEBUG, "Mineshaft | "+this.type.toString()+" | "+(p_i48759_4_*16)+" "+(p_i48759_5_*16));
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
       HELL;

      public static MapGenMineshaftUA.Type byId(int id) {
         return id >= 0 && id < values().length ? values()[id] : NORMAL;
      }
   }
}
