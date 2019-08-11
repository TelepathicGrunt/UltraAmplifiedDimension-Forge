package net.TelepathicGrunt.UltraAmplified.World.Feature.Structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.FeatureUA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.registries.ForgeRegistries;

public class StrongholdUA extends Structure<NoFeatureConfig> {
   public StrongholdUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_) {
		super(p_i51427_1_);
	}

/** is spawned false and set true once the defined BiomeGenBases were compared with the present ones */
   private boolean ranBiomeCheck;
   private ChunkPos[] structureCoords;
   private final List<StructureStart> structureList = Lists.newArrayList();
   private long seed;

   public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
      if (this.seed != chunkGen.getSeed()) {
         this.resetData();
      }

      if (!this.ranBiomeCheck) {
         this.reinitializeData(chunkGen);
         this.ranBiomeCheck = true;
      }

      for(ChunkPos chunkpos : this.structureCoords) {
         if ((ConfigUA.strongholdGeneration) && chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
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
      this.structureList.clear();
   }

   protected boolean isEnabledIn(IWorld worldIn) {
      return worldIn.getWorldInfo().isMapFeaturesEnabled();
   }

   public Structure.IStartFactory getStartFactory() {
      return StrongholdUA.Start::new;
   }

   public String getStructureName() {
      return "Stronghold UA";
   }

   public int getSize() {
      return 8;
   }


   @Nullable
   public BlockPos findNearest(World worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, BlockPos pos, int radius, boolean p_211405_5_) {
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
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
         double d0 = Double.MAX_VALUE;

         for(ChunkPos chunkpos : this.structureCoords) {
            blockpos$mutableblockpos.setPos((chunkpos.x << 4) + 8, 32, (chunkpos.z << 4) + 8);
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
   private void reinitializeData(ChunkGenerator<?> generator) {
      this.seed = generator.getSeed();
      List<Biome> list = Lists.newArrayList();

      for(Biome biome : ForgeRegistries.BIOMES) {
         if (biome != null && generator.hasStructure(biome, FeatureUA.STRONGHOLD_UA)) {
            list.add(biome);
         }
      }

      int distance = (int)ConfigUA.strongholdDistance;
      int numberOfStrongholds = ConfigUA.strongholdNumberPerWorld;
      int spread = ConfigUA.strongholdSpread;
      this.structureCoords = new ChunkPos[numberOfStrongholds];
      int j = 0;
      for(StructureStart structurestart : this.structureList) {
         if (j < this.structureCoords.length) {
            this.structureCoords[j++] = new ChunkPos(structurestart.getChunkPosX(), structurestart.getChunkPosZ());
         }
      }

      Random random = new Random();
      random.setSeed(generator.getSeed());
      double d1 = random.nextDouble() * Math.PI * 2.0D;
      int k = j;
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
	  public Start(Structure<?> p_i50780_1_, int p_i50780_2_, int p_i50780_3_, Biome p_i50780_4_, MutableBoundingBox p_i50780_5_, int p_i50780_6_, long p_i50780_7_) {
         super(p_i50780_1_, p_i50780_2_, p_i50780_3_, p_i50780_4_, p_i50780_5_, p_i50780_6_, p_i50780_7_);
      }

      public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
         StrongholdPiecesUA.prepareStructurePieces();
         StrongholdPiecesUA.Stairs2 strongholdpieces$stairs2 = new StrongholdPiecesUA.Stairs2(this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
         this.components.add(strongholdpieces$stairs2);
         strongholdpieces$stairs2.buildComponent(strongholdpieces$stairs2, this.components, this.rand);
         List<StructurePiece> list = strongholdpieces$stairs2.pendingChildren;

         while(!list.isEmpty()) {
            int i = this.rand.nextInt(list.size());
            StructurePiece structurepiece = list.remove(i);
            structurepiece.buildComponent(strongholdpieces$stairs2, this.components, this.rand);
         }

         this.recalculateStructureSize();
         
         this.func_214626_a(this.rand, 100, 120);
         UltraAmplified.Logger.log(Level.DEBUG, "Stronghold | "+(chunkX*16)+" "+(chunkZ*16));
      }
   }
}