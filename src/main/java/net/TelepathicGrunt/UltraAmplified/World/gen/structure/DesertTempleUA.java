package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Random;
import java.util.function.Function;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;
import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class DesertTempleUA extends Structure<NoFeatureConfig> {
   
	public DesertTempleUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_) {
		super(p_i51427_1_);
	}

	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
	      int maxDistance = ConfigUA.desertTempleSpawnrate;
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
	      ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 14357617);
	      k1 = k1 * maxDistance;
	      l1 = l1 * maxDistance;
	      k1 = k1 + random.nextInt(maxDistance - minDistance);
	      l1 = l1 + random.nextInt(maxDistance - minDistance);
	      return new ChunkPos(k1, l1);
	   }

	   public String getStructureName() {
	      return "Desert Temple UA";
	   }


	   public int getSize() {
	      return 3;
	   }

	   public Structure.IStartFactory getStartFactory() {
	      return DesertTempleUA.Start::new;
	   }

	   public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
	      ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
	      if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
	         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos(chunkPosX * 16 + 9, 0, chunkPosZ * 16 + 9));
	         if ((ConfigUA.desertTempleSpawnrate != 101) && chunkGen.hasStructure(biome, this)) {
	            return true;
	         }
	      }
	      return false;
	   }

	   
	   public static class Start extends StructureStart {
		  public Start(Structure<?> p_i50437_1_, int p_i50437_2_, int p_i50437_3_, Biome p_i50437_4_, MutableBoundingBox p_i50437_5_, int p_i50437_6_, long p_i50437_7_) {
	         super(p_i50437_1_, p_i50437_2_, p_i50437_3_, p_i50437_4_, p_i50437_5_, p_i50437_6_, p_i50437_7_);
	      }

	      public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
	         Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
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

	         int k = (chunkX << 4) + 7;
	         int l = (chunkZ << 4) + 7;
	         int i1 = generator.func_222531_c(k, l, Heightmap.Type.WORLD_SURFACE_WG);
	         int j1 = generator.func_222531_c(k, l + j, Heightmap.Type.WORLD_SURFACE_WG);
	         int k1 = generator.func_222531_c(k + i, l, Heightmap.Type.WORLD_SURFACE_WG);
	         int l1 = generator.func_222531_c(k + i, l + j, Heightmap.Type.WORLD_SURFACE_WG);
	         int y = Math.min(Math.min(i1, j1), Math.min(k1, l1));
	         y =  Math.min(y, 244);
	         
	         if (y >= 70) {
		         DesertTemplePiecesUA desertpyramidpiece = new DesertTemplePiecesUA(this.rand, chunkX * 16, y, chunkZ * 16);
		         this.components.add(desertpyramidpiece);
		         this.recalculateStructureSize();
		         UltraAmplified.Logger.log(Level.DEBUG, "Desert Temple | "+(chunkX*16)+" "+(chunkZ*16));
		     }
	      }
	   }
	}