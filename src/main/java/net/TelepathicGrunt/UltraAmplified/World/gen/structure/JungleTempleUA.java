package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Random;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
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
import net.minecraft.world.gen.feature.structure.JunglePyramidConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;

public class JungleTempleUA extends Structure<JunglePyramidConfig> {
  
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
	      return "Jungle Pyramid UA";
	   }

	   public int getSize() {
	      return 3;
	   }

	   protected StructureStart makeStart(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom random, int x, int z) {
	      Biome biome = generator.getBiomeProvider().getBiome(new BlockPos((x << 4) + 9, 0, (z << 4) + 9), Biomes.PLAINS);
	      return new JungleTempleUA.Start(worldIn, generator, random, x, z, biome);
	   }

	   protected int getSeedModifier() {
	      return 14357619;
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

	      public Start(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom sharedSeed, int chunkX, int chunkZ, Biome biome) {
		         super(chunkX, chunkZ, biome, sharedSeed, worldIn.getSeed());
	         
	         Rotation rotation = Rotation.values()[sharedSeed.nextInt(Rotation.values().length)];
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
	         
	         ChunkPrimer chunkprimer = new ChunkPrimer(new ChunkPos(chunkX, chunkZ), UpgradeData.EMPTY);
	         generator.makeBase(chunkprimer);
	         int k = chunkprimer.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, 7, 7);
	         int l = chunkprimer.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, 7, 7 + j);
	         int i1 = chunkprimer.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, 7 + i, 7);
	         int j1 = chunkprimer.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, 7 + i, 7 + j);
	         int y = Math.min(Math.min(k, l), Math.min(i1, j1));
	         y =  Math.min(y, 244);
	         
	         if (y < 70) {
	            this.isValid = false;
	         } else {
	        	 JungleTemplePiecesUA junglepyramidpiece = new JungleTemplePiecesUA(sharedSeed, chunkX * 16, y, chunkZ * 16);
		         this.components.add(junglepyramidpiece);
		         this.recalculateStructureSize(worldIn);
		         UltraAmplified.Logger.log(Level.DEBUG, "Jungle Temple | "+(chunkX*16)+" "+(chunkZ*16));
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