package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Random;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.minecraft.init.Biomes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class OceanRuinsUA extends Structure<OceanRuinConfig> {
   
	protected ChunkPos getStartPositionForPosition(IChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
	      int maxDistance = ConfigUA.oceanRuinsSpawnrate;
	      int minDistance = 8;
	      if(maxDistance < 9) {
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
	      return "Ocean Ruins UA";
	   }

	   public int getSize() {
	      return 3;
	   }

	   protected StructureStart makeStart(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom random, int x, int z) {
	      Biome biome = generator.getBiomeProvider().getBiome(new BlockPos((x << 4) + 9, 0, (z << 4) + 9), Biomes.PLAINS);
	      return new OceanRuinsUA.Start(worldIn, generator, random, x, z, biome);
	   }

	   protected int getSeedModifier() {
	      return 14357621;
	   }

	   protected boolean hasStartAt(IChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
		      ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
		      if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
		         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos(chunkPosX * 16 + 9, 0, chunkPosZ * 16 + 9),  Biomes.PLAINS);
		         if ((ConfigUA.oceanRuinsGeneration) && chunkGen.hasStructure(biome, this)) {
		            return true;
		         }
	      }
	      return false;
	   }

	   
	   public static class Start extends StructureStart {
	      public Start() {
	      }

	      public Start(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom sharedSeed, int chunkX, int chunkZ, Biome biome) {
	         super(chunkX, chunkZ, biome, sharedSeed, worldIn.getSeed());
	         OceanRuinConfig oceanruinconfig = (OceanRuinConfig)generator.getStructureConfig(biome, FeatureUA.OCEAN_RUIN_UA);
	         int i = chunkX * 16;
	         int j = chunkZ * 16;
	         BlockPos blockpos = new BlockPos(i, 90, j);
	         Rotation rotation = Rotation.values()[sharedSeed.nextInt(Rotation.values().length)];
	         TemplateManager templatemanager = worldIn.getSaveHandler().getStructureTemplateManager();
	         OceanRuinsPiecesUA.start(templatemanager, blockpos, rotation, this.components, sharedSeed, oceanruinconfig);
	         this.recalculateStructureSize(worldIn);
		     UltraAmplified.Logger.log(Level.DEBUG, "Ocean Ruins | "+(chunkX*16)+" "+(chunkZ*16));
	      }

	   }
}