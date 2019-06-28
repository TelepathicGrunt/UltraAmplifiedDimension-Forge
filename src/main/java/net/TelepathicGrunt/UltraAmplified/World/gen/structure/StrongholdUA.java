package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;
import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.minecraft.init.Biomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.structure.StrongholdConfig;
import net.minecraft.world.gen.feature.structure.StrongholdPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;

public class StrongholdUA extends Structure<StrongholdConfig> {
   /** is spawned false and set true once the defined BiomeGenBases were compared with the present ones */
   private boolean ranBiomeCheck;
   private ChunkPos[] structureCoords;
   private long seed;

   protected boolean hasStartAt(IChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
      if (this.seed != chunkGen.getSeed()) {
         this.resetData();
      }

      if (!this.ranBiomeCheck) {
         this.reinitializeData(chunkGen);
         this.ranBiomeCheck = true;
      }

      for(ChunkPos chunkpos : this.structureCoords) {
         if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
            return true;
         }
      }

      return false;
   }

   /**
    * Resets the current available data on the stronghold structure, since biome checks and existing structure
    * coordinates are needed to properly generate strongholds.
    */
   private void resetData() {
      this.ranBiomeCheck = false;
      this.structureCoords = null;
   }

   protected boolean isEnabledIn(IWorld worldIn) {
      return worldIn.getWorldInfo().isMapFeaturesEnabled();
   }

   protected StructureStart makeStart(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom random, int x, int z) {
      Biome biome = generator.getBiomeProvider().getBiome(new BlockPos((x << 4) + 9, 0, (z << 4) + 9), Biomes.DEFAULT);
      int i = 0;

      StrongholdUA.Start strongholdstructure$start;
      for(strongholdstructure$start = new StrongholdUA.Start(worldIn, random, x, z, biome, i++); strongholdstructure$start.getComponents().isEmpty() || ((StrongholdPiecesUA.Stairs2)strongholdstructure$start.getComponents().get(0)).strongholdPortalRoom == null; strongholdstructure$start = new StrongholdUA.Start(worldIn, random, x, z, biome, i++)) {
         ;
      }

      return strongholdstructure$start;
   }

   protected String getStructureName() {
      return "Stronghold UA";
   }

   public int getSize() {
      return 8;
   }

   @Nullable
   public BlockPos findNearest(World worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, BlockPos pos, int radius, boolean p_211405_5_) {
      if (!chunkGenerator.getBiomeProvider().hasStructure(this)) {
         return null;
      } else {
         if (this.seed != worldIn.getSeed()) {
            this.resetData();
         }

         if (!this.ranBiomeCheck) {
            this.reinitializeData(chunkGenerator);
            this.ranBiomeCheck = true;
         }

         BlockPos blockpos = null;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);
         double d0 = Double.MAX_VALUE;

         for(ChunkPos chunkpos : this.structureCoords) {
            blockpos$mutableblockpos.setPos((chunkpos.x << 4) + 8, 112, (chunkpos.z << 4) + 8);
            double d1 = blockpos$mutableblockpos.distanceSq(pos);
            if (blockpos == null) {
               blockpos = new BlockPos(blockpos$mutableblockpos);
               d0 = d1;
            } else if (d1 < d0) {
               blockpos = new BlockPos(blockpos$mutableblockpos);
               d0 = d1;
            }
         }

         return blockpos;
      }
   }

   /**
    * Re-initializes the stronghold information needed to generate strongholds. Due to the requirement to rely on seeds
    * and other settings provided by the chunk generator, each time the structure is used on a different seed, this can
    * be called multiple times during the game lifecycle.
    */
   private void reinitializeData(IChunkGenerator<?> generator) {
      this.seed = generator.getSeed();
      List<Biome> list = Lists.newArrayList();

      for(Biome biome : IRegistry.field_212624_m) {
         if (biome != null && generator.hasStructure(biome, FeatureUA.STRONGHOLD_UA)) {
            list.add(biome);
         }
      }

      int distance = (int)Config.strongholdDistance;
      int numberOfStrongholds = Config.strongholdNumberPerWorld;
      int spread = Config.strongholdSpread;
      this.structureCoords = new ChunkPos[numberOfStrongholds];
      int j = 0;
      Long2ObjectMap<StructureStart> long2objectmap = generator.getStructureReferenceToStartMap(this);
      synchronized(long2objectmap) {
         for(StructureStart structurestart : long2objectmap.values()) {
            if (j < this.structureCoords.length) {
               this.structureCoords[j++] = new ChunkPos(structurestart.getChunkPosX(), structurestart.getChunkPosZ());
            }
         }
      }

      Random random = new Random();
      random.setSeed(generator.getSeed());
      double d1 = random.nextDouble() * Math.PI * 2.0D;
      int k = long2objectmap.size();
      if (k < this.structureCoords.length) {
         int l = 0;
         int i1 = 0;

         for(int j1 = 0; j1 < this.structureCoords.length; ++j1) {
            double d0 = (double)(4 * distance + distance * i1 * 6) + (random.nextDouble() - 0.5D) * (double)distance * 2.5D;
            int k1 = (int)Math.round(Math.cos(d1) * d0);
            int l1 = (int)Math.round(Math.sin(d1) * d0);
            BlockPos blockpos = generator.getBiomeProvider().findBiomePosition((k1 << 4) + 8, (l1 << 4) + 8, 112, list, random);
            if (blockpos != null) {
               k1 = blockpos.getX() >> 4;
               l1 = blockpos.getZ() >> 4;
            }

            if (j1 >= k) {
               this.structureCoords[j1] = new ChunkPos(k1, l1);
            }

            d1 += (Math.PI * 2D) / (double)spread;
            ++l;
            if (l == spread) {
               ++i1;
               l = 0;
               spread = spread + 2 * spread / (i1 + 1);
               spread = Math.min(spread, this.structureCoords.length - j1);
               d1 += random.nextDouble() * Math.PI * 2.0D;
            }
         }
      }

   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(IWorld worldIn, SharedSeedRandom random, int chunkX, int chunkZ, Biome p_i48716_5_, int seed) {
         super(chunkX, chunkZ, p_i48716_5_, random, worldIn.getSeed() + (long)seed);
         StrongholdPiecesUA.prepareStructurePieces();
         StrongholdPiecesUA.Stairs2 strongholdpieces$stairs2 = new StrongholdPiecesUA.Stairs2(0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2);
         this.components.add(strongholdpieces$stairs2);
         strongholdpieces$stairs2.buildComponent(strongholdpieces$stairs2, this.components, random);
         List<StructurePiece> list = strongholdpieces$stairs2.pendingChildren;

         while(!list.isEmpty()) {
            int i = random.nextInt(list.size());
            StructurePiece structurepiece = list.remove(i);
            structurepiece.buildComponent(strongholdpieces$stairs2, this.components, random);
         }

         this.recalculateStructureSize(worldIn);
         
         this.setRandomHeight(worldIn, random, 100, 120);
         UltraAmplified.Logger.log(Level.DEBUG, "Stronghold | "+(chunkX*16)+" "+(chunkZ*16));
      }
   }
}