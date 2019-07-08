package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;
import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Biomes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.SwampHutConfig;

public class WitchHutUA extends Structure<SwampHutConfig> {
   private static final List<Biome.SpawnListEntry> field_202384_d = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.WITCH, 1, 1, 1));

	protected ChunkPos getStartPositionForPosition(IChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
      int maxDistance = Config.witchHutSpawnrate;
      int minDistance = 8;
      if(maxDistance < 9 ) {
    	  minDistance = maxDistance - 1;
      }
      int k = x + maxDistance * spacingOffsetsX;
      int l = z + maxDistance * spacingOffsetsZ;
      int i1 = k < 0 ? k - maxDistance + 1 : k;
      int j1 = l < 0 ? l - maxDistance + 1 : l;
      int k1 = i1 / maxDistance;
      int l1 = j1 / maxDistance;
      ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, this.getSeedModifier());
      k1 = k1 * maxDistance;
      l1 = l1 * maxDistance;
      k1 = k1 + random.nextInt(maxDistance - minDistance);
      l1 = l1 + random.nextInt(maxDistance - minDistance);
      return new ChunkPos(k1, l1);
   }

   protected boolean isEnabledIn(IWorld worldIn) {
      return worldIn.getWorldInfo().isMapFeaturesEnabled();
   }
   
   protected String getStructureName() {
      return "Swamp Hut UA";
   }

   public int getSize() {
      return 3;
   }

   protected StructureStart makeStart(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom random, int x, int z) {
      Biome biome = generator.getBiomeProvider().getBiome(new BlockPos((x << 4) + 9, 0, (z << 4) + 9), Biomes.PLAINS);
      return new WitchHutUA.Start(worldIn, generator, random, x, z, biome);
   }

   protected int getSeedModifier() {
      return 14357620;
   }

   public List<Biome.SpawnListEntry> getSpawnList() {
      return field_202384_d;
   }

   public boolean func_202383_b(IWorld p_202383_1_, BlockPos p_202383_2_) {
      StructureStart structurestart = this.getStart(p_202383_1_, p_202383_2_);
      if (structurestart != NO_STRUCTURE && structurestart instanceof WitchHutUA.Start && !structurestart.getComponents().isEmpty()) {
         StructurePiece structurepiece = structurestart.getComponents().get(0);
         return structurepiece instanceof WitchHutPiecesUA;
      } else {
         return false;
      }
   }

   protected boolean hasStartAt(IChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
	      ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
	      if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
	         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos(chunkPosX * 16 + 9, 0, chunkPosZ * 16 + 9),  Biomes.PLAINS);
	         if (chunkGen.hasStructure(biome, this)) {
	            return true;
	         }
      }
      return false;
   }

   public static class Start extends StructureStart {
	  private boolean isValid;
      public Start() {
      }

      public Start(IWorld worldIn, IChunkGenerator<?> chunkGen, SharedSeedRandom random, int x, int z, Biome biomeIn) {
         super(x, z, biomeIn, random, worldIn.getSeed());

         Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
         int i = 5;
         int j = 5;
         if (rotation == Rotation.CLOCKWISE_90) {
            i = -5;
         } else if (rotation == Rotation.CLOCKWISE_180) {
            i = -5;
            j = -5;
         } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
            j = -5;
         }
         
         ChunkPrimer chunkprimer = new ChunkPrimer(new ChunkPos(x, z), UpgradeData.EMPTY);
         chunkGen.makeBase(chunkprimer);
         int k = chunkprimer.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, 7, 7);
         int l = chunkprimer.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, 7, 7 + j);
         int i1 = chunkprimer.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, 7 + i, 7);
         int j1 = chunkprimer.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, 7 + i, 7 + j);
         int y = Math.min(Math.min(k, l), Math.min(i1, j1));
         y =  Math.min(y, 244);
         
         if (y < 70) {
            this.isValid = false;
         } else {
        	 WitchHutPiecesUA swamphutpiece = new WitchHutPiecesUA(random, x * 16, y, z * 16);
	         this.components.add(swamphutpiece);
	         this.recalculateStructureSize(worldIn);
	         UltraAmplified.Logger.log(Level.DEBUG, "Witch Hut | "+(x*16)+" "+(z*16));
	         this.isValid = true;
         }
      }

      public boolean isSizeableStructure() {
         return this.isValid;
      }

      //Forge: Fix losing of 'valid' flag on world reload. TODO: Remove in 1.14 as vanilla fixed.
      public void writeAdditional(net.minecraft.nbt.NBTTagCompound tag) {
         super.writeAdditional(tag);
         tag.setBoolean("Valid", this.isValid);
      }
      public void readAdditional(net.minecraft.nbt.NBTTagCompound tag) {
         super.readAdditional(tag);
         this.isValid = tag.hasKey("Valid") && tag.getBoolean("Valid");
      }
   }
}