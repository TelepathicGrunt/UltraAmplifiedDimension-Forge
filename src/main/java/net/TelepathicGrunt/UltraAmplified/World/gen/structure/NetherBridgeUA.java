package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;
import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Biomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;

public class NetherBridgeUA extends Structure<NetherBridgeConfigUA> 
{
  private static final List<Biome.SpawnListEntry> field_202381_d = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.BLAZE, 10, 2, 3), new Biome.SpawnListEntry(EntityType.ZOMBIE_PIGMAN, 5, 4, 4), new Biome.SpawnListEntry(EntityType.WITHER_SKELETON, 8, 5, 5), new Biome.SpawnListEntry(EntityType.SKELETON, 2, 5, 5), new Biome.SpawnListEntry(EntityType.MAGMA_CUBE, 3, 4, 4));

   protected boolean hasStartAt(IChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
      int i = chunkPosX >> 4;
      int j = chunkPosZ >> 4;
      rand.setSeed((long)(i ^ j << 4) ^ chunkGen.getSeed());
      rand.nextInt();
      if (rand.nextInt(3) != 0) {
         return false;
      } else if (chunkPosX != (i << 4) + 4 + rand.nextInt(8)) {
         return false;
      } else if (chunkPosZ != (j << 4) + 4 + rand.nextInt(8)) {
         return false;
      } else {
         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9), Biomes.DEFAULT);
         return chunkGen.hasStructure(biome, FeatureUA.FORTRESS_UA);
      }
   }

   protected boolean isEnabledIn(IWorld worldIn) {
      return worldIn.getWorldInfo().isMapFeaturesEnabled();
   }

   protected StructureStart makeStart(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom random, int x, int z) {
      Biome biome = generator.getBiomeProvider().getBiome(new BlockPos((x << 4) + 9, 0, (z << 4) + 9), Biomes.DEFAULT);
      return new NetherBridgeUA.Start(worldIn, generator, random, x, z, biome);
   }

   protected String getStructureName() {
      return "Nether Fortress UA";
   }

   public int getSize() {
      return 8;
   }

   public List<Biome.SpawnListEntry> getSpawnList() {
      return field_202381_d;
   }

   public static class Start extends StructureStart {
	  private boolean genAboveSeaLevel;
	      
      public Start() {
      }

      public Start(IWorld worldIn, IChunkGenerator<?> chunkGenerator, SharedSeedRandom sharedRandom, int chunkX, int chunkZ, Biome biome) {
         super(chunkX, chunkZ, biome, sharedRandom, worldIn.getSeed());
         NetherBridgeConfigUA fortressconfig = (NetherBridgeConfigUA)chunkGenerator.getStructureConfig(biome, FeatureUA.FORTRESS_UA);
         this.genAboveSeaLevel = fortressconfig.surfaceAllow;
         NetherBridgePiecesUA.Start fortresspieces$start = new NetherBridgePiecesUA.Start(sharedRandom, (chunkX << 4) + 2, (chunkZ << 4) + 2);
         this.components.add(fortresspieces$start);
         
         
         
         fortresspieces$start.buildComponent(fortresspieces$start, this.components, sharedRandom);
         List<StructurePiece> list = fortresspieces$start.pendingChildren;

         while(!list.isEmpty()) {
            int i = sharedRandom.nextInt(list.size());
            StructurePiece structurepiece = list.remove(i);
            structurepiece.buildComponent(fortresspieces$start, this.components, sharedRandom);
         }

         
         this.recalculateStructureSize(worldIn);
         
         //gens aboveground 50% of time if underground is allowed and 100% of time when underground is not allowed
         if(genAboveSeaLevel && (sharedRandom.nextBoolean() || !Config.netherFortressUnderground) && Config.netherFortressAboveground) {
             this.setRandomHeight(worldIn, sharedRandom, 85, 130);
             UltraAmplified.Logger.log(Level.DEBUG, "Aboveground Nether Fortress | "+(chunkX*16)+" "+(chunkZ*16));
         }
         else if(Config.netherFortressUnderground) {
             this.setRandomHeight(worldIn, sharedRandom, 15, 30);
             UltraAmplified.Logger.log(Level.DEBUG, "Underground Nether Fortress | "+(chunkX*16)+" "+(chunkZ*16));
         }
      }
   }
}