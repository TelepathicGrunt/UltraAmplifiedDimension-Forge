package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Random;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.gen.feature.structure.ShipwreckPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;

public class ShipwreckUA extends Structure<ShipwreckConfig> {

	protected ChunkPos getStartPositionForPosition(IChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
      int i = Config.scatteredSpawnrate;
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
      ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, this.getSeedModifier());
      k1 = k1 * i;
      l1 = l1 * i;
      k1 = k1 + random.nextInt(i - j);
      l1 = l1 + random.nextInt(i - j);
      return new ChunkPos(k1, l1);
   }

   protected boolean isEnabledIn(IWorld worldIn) {
      return worldIn.getWorldInfo().isMapFeaturesEnabled();
   }

   protected String getStructureName() {
	      return "Shipwreck_UA";
   }

   public int getSize() {
      return 3;
   }

   protected StructureStart makeStart(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom random, int x, int z) {
      Biome biome = generator.getBiomeProvider().getBiome(new BlockPos((x << 4) + 9, 0, (z << 4) + 9), (Biome)null);
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
	  private boolean isValid;
      public Start() {
      }

      public Start(IWorld worldIn, IChunkGenerator<?> chunkGenerator, SharedSeedRandom random, int chunkX, int chunkZ, Biome biome) {
         super(chunkX, chunkZ, biome, random, worldIn.getSeed());
         Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
         
         int randHeight = random.nextInt(80)+150;
    	 BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
         
         ChunkPrimer chunkprimer = new ChunkPrimer(new ChunkPos(chunkX, chunkZ), UpgradeData.EMPTY);
         chunkGenerator.makeBase(chunkprimer);
         
         //finds surface on water
         while(randHeight > 50 && chunkprimer.getBlockState(blockpos.up(randHeight)).getMaterial() != Material.WATER) {
        	 randHeight--;
         }
         
         //finds bottom of water body
         while(randHeight > 50 && chunkprimer.getBlockState(blockpos.up(randHeight)).getMaterial() == Material.WATER) {
        	 randHeight--;
         }
         
         if (randHeight <= 50) {
            this.isValid = false;
         } else {
        	 blockpos = blockpos.up(randHeight);
        	 
        	 //Our shipwreck can generate all kinds of variants regardless of what biome it is in
        	 ShipwreckConfig newShipwreckConfig = new ShipwreckConfig(random.nextBoolean() ? true : false);
        	 
             ShipwreckPieces.func_204760_a(worldIn.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, this.components, random, newShipwreckConfig);
	         this.recalculateStructureSize(worldIn);
             this.setRandomHeight(worldIn, random, randHeight, randHeight+1);
	         
	         UltraAmplified.Logger.log(Level.DEBUG, "Shipwreck | "+(chunkX*16)+" "+(chunkZ*16));
	         this.isValid = true;
         }
         
         this.recalculateStructureSize(worldIn);
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