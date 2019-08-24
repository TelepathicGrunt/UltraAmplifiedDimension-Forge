package net.TelepathicGrunt.UltraAmplified.World.Feature.Structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.util.SharedSeedRandom;
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

   protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
      int maxDistance = ConfigUA.strongholdSpawnrate;
      int minDistance = maxDistance - 10;
      if(maxDistance < 12 ) {
         minDistance = maxDistance - 1;
      }
      int k = x + maxDistance * spacingOffsetsX;
      int l = z + maxDistance * spacingOffsetsZ;
      int i1 = k < 0 ? k - maxDistance + 1 : k;
      int j1 = l < 0 ? l - maxDistance + 1 : l;
      int k1 = i1 / maxDistance;
      int l1 = j1 / maxDistance;
      ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 143523564);
      k1 = k1 * maxDistance;
      l1 = l1 * maxDistance;
      k1 = k1 + random.nextInt(maxDistance - minDistance);
      l1 = l1 + random.nextInt(maxDistance - minDistance);
      return new ChunkPos(k1, l1);
   }


   public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {

      ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
      if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos(chunkPosX * 16 + 9, 0, chunkPosZ * 16 + 9));
         if ((ConfigUA.strongholdSpawnrate != 501) && chunkGen.hasStructure(biome, this)) {
            return true;
         }
      }

      return false;
   }

   public Structure.IStartFactory getStartFactory() {
      return StrongholdUA.Start::new;
   }

   public String getStructureName() {
      return "Stronghold";
   }

   public int getSize() {
      return 8;
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