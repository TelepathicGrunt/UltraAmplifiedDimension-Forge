package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Random;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.init.Biomes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;

public class ShipwreckUA extends Structure<ShipwreckConfig> {

	protected ChunkPos getStartPositionForPosition(IChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
      int maxDistance = ConfigUA.shipwreckSpawnrate;
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
	      return "Shipwreck UA";
   }

   public int getSize() {
      return 3;
   }

   protected StructureStart makeStart(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom random, int x, int z) {
      Biome biome = generator.getBiomeProvider().getBiome(new BlockPos((x << 4) + 9, 0, (z << 4) + 9), Biomes.PLAINS);
      return new ShipwreckUA.Start(worldIn, generator, random, x, z, biome);
   }

   protected int getSeedModifier() {
      return 165745295;
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
      public Start() {
      }

      public Start(IWorld worldIn, IChunkGenerator<?> chunkGenerator, SharedSeedRandom random, int chunkX, int chunkZ, Biome biome) {
         super(chunkX, chunkZ, biome, random, worldIn.getSeed());
         Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
         
         int xOffset = 8;
         int zOffset = 8;
         if (rotation == Rotation.CLOCKWISE_90) {
            zOffset = 16;
         } else if (rotation == Rotation.CLOCKWISE_180) {
            xOffset = 0;
            zOffset = 16;
         } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
            xOffset = 0;
            zOffset = 16;
         }
         
         
         
         int randHeight = random.nextInt(130)+90;
    	 BlockPos blockpos = new BlockPos(chunkX * 16 + xOffset, 0, chunkZ * 16+zOffset);
         
         ChunkPrimer chunkprimer = new ChunkPrimer(new ChunkPos(blockpos.getX()/16, blockpos.getZ()/16), UpgradeData.EMPTY);
         chunkGenerator.makeBase(chunkprimer);
         
         //finds surface on water
         while(randHeight > 50 && chunkprimer.getBlockState(blockpos.up(randHeight)).getFluidState().isEmpty()) {
        	 randHeight--;
         }
         
         //finds bottom of water body
         while(randHeight > 50 && !chunkprimer.getBlockState(blockpos.up(randHeight)).getFluidState().isEmpty()) {
        	 randHeight--;
         }
     
    	 //without offset
    	 BlockPos blockpos2 = new BlockPos(chunkX * 16, randHeight-2, chunkZ * 16);
    	 
    	 //Our shipwreck can generate all kinds of variants regardless of what biome it is in
    	 ShipwreckConfig newShipwreckConfig = new ShipwreckConfig(random.nextBoolean() ? true : false);
    	 
         ShipwreckPiecesUA.beginGeneration(worldIn.getSaveHandler().getStructureTemplateManager(), blockpos2, rotation, this.components, random, newShipwreckConfig);
         this.recalculateStructureSize(worldIn);
         
         UltraAmplified.Logger.log(Level.DEBUG, "Shipwreck | "+blockpos.getX()+" "+this.boundingBox.minY+" "+blockpos.getZ());
     
         this.recalculateStructureSize(worldIn);
      }

   }
}