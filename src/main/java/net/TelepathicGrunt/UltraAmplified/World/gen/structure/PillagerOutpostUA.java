package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
import net.minecraft.world.gen.feature.structure.PillagerOutpostPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class PillagerOutpostUA extends Structure<PillagerOutpostConfig> {
   private static final List<Biome.SpawnListEntry> field_214558_a = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.PILLAGER, 1, 1, 1));

   public PillagerOutpostUA(Function<Dynamic<?>, ? extends PillagerOutpostConfig> p_i51470_1_) {
      super(p_i51470_1_);
   }

   public String getStructureName() {
      return "Pillager_Outpost_UA";
   }

   public int getSize() {
      return 3;
   }

   public List<Biome.SpawnListEntry> getSpawnList() {
      return field_214558_a;
   }

   public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
      ((SharedSeedRandom)rand).setLargeFeatureSeed(chunkGen.getSeed(), chunkPosX, chunkPosZ);
      Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9));
      
      if (ConfigUA.pillageOutpostRarity != 101 && chunkGen.hasStructure(biome, FeatureUA.PILLAGER_OUTPOST_UA)) {
    	  PillagerOutpostConfig pillagerconfig = (PillagerOutpostConfig)chunkGen.getStructureConfig(biome, FeatureUA.PILLAGER_OUTPOST_UA);
    	  
    	  if(rand.nextFloat() < pillagerconfig.probability) {
	         for(int k = chunkPosX - 3; k <= chunkPosX + 3; ++k) {
	            for(int l = chunkPosZ - 3; l <= chunkPosZ + 3; ++l) {
	               if (FeatureUA.VILLAGE_UA.hasStartAt(chunkGen, rand, k, l)) {
	                  return false;
	               }
	            }
	         }
	         
	         return true;
    	  }
      }

      return false;
   }

   public Structure.IStartFactory getStartFactory() {
      return PillagerOutpostUA.Start::new;
   }

   protected int getSeedModifier() {
      return 165745296;
   }

   public static class Start extends MarginedStructureStart {
      public Start(Structure<?> p_i50497_1_, int p_i50497_2_, int p_i50497_3_, Biome p_i50497_4_, MutableBoundingBox p_i50497_5_, int p_i50497_6_, long p_i50497_7_) {
         super(p_i50497_1_, p_i50497_2_, p_i50497_3_, p_i50497_4_, p_i50497_5_, p_i50497_6_, p_i50497_7_);
      }

      public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
    	  
    	 int offsetX = 7;
    	 int offsetZ = 7;
         int x = (chunkX << 4) + offsetX;
         int z = (chunkZ << 4) + offsetZ;
         int i1 = generator.func_222531_c(x, z, Heightmap.Type.WORLD_SURFACE_WG);
         int j1 = generator.func_222531_c(x, z + 1, Heightmap.Type.WORLD_SURFACE_WG);
         int k1 = generator.func_222531_c(x + 1, z, Heightmap.Type.WORLD_SURFACE_WG);
         int l1 = generator.func_222531_c(x + 1, z + 1, Heightmap.Type.WORLD_SURFACE_WG);
         int y = Math.max(Math.max(i1, j1), Math.max(k1, l1));
         
         if (y >= 70 && y <= 200) {
        	 BlockPos blockpos = new BlockPos(chunkX * 16, y, chunkZ * 16);
        	 PillagerOutpostPieces.func_215139_a(generator, templateManagerIn, blockpos, this.components, this.rand);
             this.recalculateStructureSize();
             UltraAmplified.Logger.log(Level.DEBUG, "Pillager Outpost | "+(x)+" "+(y+1)+" "+(z));
         }
      }
   }
}