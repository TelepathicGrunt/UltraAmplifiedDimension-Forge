package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillagePieces;

public class VillageUA  extends Structure<VillageUAConfig> {
   public String getStructureName() {
	      return "Village UA";
   }

   public int getSize() {
      return 8;
   }

   protected boolean isEnabledIn(IWorld worldIn) {
      return worldIn.getWorldInfo().isMapFeaturesEnabled();
   }

   protected ChunkPos getStartPositionForPosition(IChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
      int i = Config.villageSpawnrate;
      int j = 8;
      if(Config.scatteredSpawnrate < 9 ) {
    	  j = Config.scatteredSpawnrate - 1;
      }
      int k = x + i * spacingOffsetsX;
      int l = z + i * spacingOffsetsZ;
      int i1 = k < 0 ? k - i + 1 : k;
      int j1 = l < 0 ? l - i + 1 : l;
      int k1 = i1 / i;
      int l1 = j1 / i;
      ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 10387312);
      k1 = k1 * i;
      l1 = l1 * i;
      k1 = k1 + random.nextInt(i - j);
      l1 = l1 + random.nextInt(i - j);
      return new ChunkPos(k1, l1);
   }

   protected boolean hasStartAt(IChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
      ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
      if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9), Biomes.DEFAULT);
         return chunkGen.hasStructure(biome, FeatureUA.VILLAGE_UA); 
      } else {
         return false;
      }
   }

   protected StructureStart makeStart(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom random, int x, int z) {
      Biome biome = generator.getBiomeProvider().getBiome(new BlockPos((x << 4) + 9, 0, (z << 4) + 9), Biomes.DEFAULT);
      return new VillageUA.Start(worldIn, generator, random, x, z, biome); 
   }

   
   public static class Start extends StructureStart {
      private boolean hasMoreThanTwoComponents;

      public Start() {
      }

      public Start(IWorld p_i48753_1_, IChunkGenerator<?> generator, SharedSeedRandom random, int chunkX, int chunkZ, Biome biome) {
         super(chunkX, chunkZ, biome, random, p_i48753_1_.getSeed());
         VillageUAConfig villageconfig = (VillageUAConfig)generator.getStructureConfig(biome, FeatureUA.VILLAGE_UA); 
         List<VillagePiecesUA.PieceWeightUA> list = VillagePiecesUA.getStructureVillageWeightedPieceList(random, villageconfig.field_202461_a);
         VillagePiecesUA.Start villagepieces$start = new VillagePiecesUA.Start(0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageconfig, biome);
         this.components.add(villagepieces$start);
         villagepieces$start.buildComponent(villagepieces$start, this.components, random);
         List<StructurePiece> list1 = villagepieces$start.pendingRoads;
         List<StructurePiece> list2 = villagepieces$start.pendingHouses;

         while(!list1.isEmpty() || !list2.isEmpty()) {
            if (list1.isEmpty()) {
               int i = random.nextInt(list2.size());
               StructurePiece structurepiece = list2.remove(i);
               structurepiece.buildComponent(villagepieces$start, this.components, random);
            } else {
               int j = random.nextInt(list1.size());
               StructurePiece structurepiece2 = list1.remove(j);
               structurepiece2.buildComponent(villagepieces$start, this.components, random);
            }
         }

         this.recalculateStructureSize(p_i48753_1_);
         int k = 0;

         for(StructurePiece structurepiece1 : this.components) {
            if (!(structurepiece1 instanceof VillagePieces.Road)) {
               ++k;
            }
         }

         this.hasMoreThanTwoComponents = k > 2;
         UltraAmplified.Logger.log(Level.DEBUG,villageconfig.type+" Village | "+chunkX*16+", "+chunkZ*16);
      }


      public boolean isSizeableStructure() {
         return this.hasMoreThanTwoComponents;
      }

      public void writeAdditional(NBTTagCompound tagCompound) {
         super.writeAdditional(tagCompound);
         tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
      }

      public void readAdditional(NBTTagCompound tagCompound) {
         super.readAdditional(tagCompound);
         this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
      }
   }
}
