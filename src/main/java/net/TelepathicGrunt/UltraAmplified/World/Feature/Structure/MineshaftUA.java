package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.Random;
import java.util.function.Function;

import org.apache.logging.log4j.Level;

import com.mojang.datafixers.Dynamic;
import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;

public class MineshaftUA extends Structure<MineshaftConfigUA> {
   public MineshaftUA(Function<Dynamic<?>, ? extends MineshaftConfigUA> p_i51427_1_) {
		super(p_i51427_1_);
	}

public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
	      ((SharedSeedRandom)rand).setLargeFeatureSeed(chunkGen.getSeed(), chunkPosX, chunkPosZ);
	      Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9));
	      if ((ConfigUA.mineshaftAbovegroundAllowed || ConfigUA.mineshaftUndergroundAllowed) && chunkGen.hasStructure(biome, FeatureUA.MINESHAFT_UA)) {
	    	 return rand.nextDouble() < (double)(ConfigUA.mineshaftSpawnrate)/10000D;
	      } else {
	         return false;
	      }
	   }

	   protected boolean isEnabledIn(IWorld worldIn) {
	      return worldIn.getWorldInfo().isMapFeaturesEnabled();
	   }


	   public Structure.IStartFactory getStartFactory() {
	      return MineshaftUA.Start::new;
	   }

	   public String getStructureName() {
	      return UltraAmplified.MODID+":mineshaft";
   }

   public int getSize() {
      return 8;
   }

   public static class Start extends StructureStart {
      private MineshaftUA.Type type;

      public Start(Structure<?> p_i50437_1_, int p_i50437_2_, int p_i50437_3_, Biome p_i50437_4_, MutableBoundingBox p_i50437_5_, int p_i50437_6_, long p_i50437_7_) {
         super(p_i50437_1_, p_i50437_2_, p_i50437_3_, p_i50437_4_, p_i50437_5_, p_i50437_6_, p_i50437_7_);
      }

      public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
         MineshaftConfigUA mineshaftconfig = (MineshaftConfigUA)generator.getStructureConfig(biomeIn, FeatureUA.MINESHAFT_UA);
         this.type = mineshaftconfig.type;
         MineshaftPiecesUA.Room structuremineshaftpiecesua$room = new MineshaftPiecesUA.Room(0, this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2, this.type);
         this.components.add(structuremineshaftpiecesua$room); 
         
         structuremineshaftpiecesua$room.buildComponent(structuremineshaftpiecesua$room, this.components, this.rand);
         this.recalculateStructureSize();
         
         //makes the normal sized dirt room mineshafts spawn between 150 and 240
         //otherwise they will spawn at bottom of world
         if(structuremineshaftpiecesua$room.getBoundingBox().maxY < 100) {
             this.func_214626_a(this.rand, 150, 151 + this.rand.nextInt(70));
            // UltraAmplified.LOGGER.log(Level.DEBUG, "Aboveground Mineshaft | "+this.type.toString()+" | "+(chunkX*16)+" "+(chunkZ*16));
         }else {
             this.func_214626_a(this.rand, 10, 11);
           //  UltraAmplified.LOGGER.log(Level.DEBUG, "Underground Mineshaft | "+this.type.toString()+" | "+(chunkX*16)+" "+(chunkZ*16));
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
