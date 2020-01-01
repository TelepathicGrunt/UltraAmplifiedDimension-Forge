package net.telepathicgrunt.ultraamplified.world.generation;

import java.util.List;

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.PatrolSpawner;
import net.minecraft.world.spawner.PhantomSpawner;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;
import net.telepathicgrunt.ultraamplified.world.spawner.CatSpawnerUA;

public class UAChunkGenerator extends NoiseChunkGeneratorUA<OverworldGenSettings> {
   private static final float[] field_222576_h = Util.make(new float[25], (p_222575_0_) -> {
	      for(int i = -2; i <= 2; ++i) {
	         for(int j = -2; j <= 2; ++j) {
	            float f = 10.0F / MathHelper.sqrt((float)(i * i + j * j) + 0.2F);
	            p_222575_0_[i + 2 + (j + 2) * 5] = f;
	         }
	      }

	   });
	   private final OctavesNoiseGenerator depthNoise;
	   private final PhantomSpawner phantomSpawner = new PhantomSpawner();
	   private final PatrolSpawner patrolSpawner = new PatrolSpawner();
	   private final CatSpawnerUA catSpawner = new CatSpawnerUA();

	   public UAChunkGenerator(IWorld worldIn, BiomeProvider provider, OverworldGenSettings settingsIn) {
	      super(worldIn, provider, 4, 8, 256, settingsIn);
	      this.randomSeed.skip(2620);
	      this.depthNoise = new OctavesNoiseGenerator(this.randomSeed, 15, 0);
	   }

	   public void spawnMobs(WorldGenRegion region) {
	      int i = region.getMainChunkX();
	      int j = region.getMainChunkZ();
	      Biome biome = region.func_226691_t_((new ChunkPos(i, j)).asBlockPos());
	      SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
	      sharedseedrandom.setDecorationSeed(region.getSeed(), i << 4, j << 4);
	      WorldEntitySpawner.performWorldGenSpawning(region, biome, i, j, sharedseedrandom);
	   }

	   protected void fillNoiseColumn(double[] areaArrayIn, int x, int z) {
	      this.setupPerlinNoiseGenerators(areaArrayIn, x, z, ConfigUA.secretSetting ? 117104.946D : ConfigUA.xzTerrainModifier, ConfigUA.secretSetting ? 468419.786D : ConfigUA.yTerrainModifier, ConfigUA.xzScaleModifier, ConfigUA.secretSetting ? 73.1905915D : ConfigUA.yScaleModifier, 8.555149841308594D, 4.277574920654297D, 3, -10);
	   }

	   protected double func_222545_a(double p_222545_1_, double p_222545_3_, int p_222545_5_) {
	      double d1 = ((double)p_222545_5_ - (8.5D + p_222545_1_ * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / p_222545_3_;
	      if (d1 < 0.0D) {
	         d1 *= 4.0D;
	      }

	      return d1;
	   }

	   protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ) {
	      double[] adouble = new double[2];
	      float f = 0.0F;
	      float f1 = 0.0F;
	      float f2 = 0.0F;
	      int y = this.getSeaLevel();
	      float f3 = this.biomeProvider.func_225526_b_(noiseX, y, noiseZ).getDepth();

	      for(int j = -2; j <= 2; ++j) {
	         for(int k = -2; k <= 2; ++k) {
	            Biome biome = this.biomeProvider.func_225526_b_(noiseX + j, y, noiseZ + k);
	            float depthWeight = 0; //biome.getDepth();
	            float scaleWeight = 0; //biome.getScale();
	            
	            //Does not take into account the biome's base height and scale.
                //Making the terrain's height based on biomes took away some of the magic and coolness.
                //Thus, all biomes now have a uniformed base and scale applied to their terrain.
                          //offset + scale
                depthWeight = 1.0F + (0.0F + 0.4F) * 2F;
                scaleWeight = 1.0F + (0.0F + 0.3F) * 12F;

	            float f6 = field_222576_h[j + 2 + (k + 2) * 5] / (depthWeight + 2.0F);
	            if (biome.getDepth() > f3) {
	               f6 /= 2.0F;
	            }

	            f += scaleWeight * f6;
	            f1 += depthWeight * f6;
	            f2 += f6;
	         }
	      }

	      f = f / f2;
	      f1 = f1 / f2;
	      f = f * 0.9F + 0.1F;
	      f1 = (f1 * 4.0F - 1.0F) / 8.0F;
	      adouble[0] = (double)f1 + this.func_222574_c(noiseX, noiseZ);
	      adouble[1] = (double)f;
	      return adouble;
	   }

	   private double func_222574_c(int p_222574_1_, int p_222574_2_) {
	      double d0 = this.depthNoise.getValue((double)(p_222574_1_ * 200), 10.0D, (double)(p_222574_2_ * 200), 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
	      if (d0 < 0.0D) {
	         d0 = -d0 * 0.3D;
	      }

	      d0 = d0 * 3.0D - 2.0D;
	      if (d0 < 0.0D) {
	         d0 = d0 / 28.0D;
	      } else {
	         if (d0 > 1.0D) {
	            d0 = 1.0D;
	         }

	         d0 = d0 / 40.0D;
	      }

	      return d0;
	   }

	   
	   //fix
	   public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification creatureType, BlockPos pos) {
	      if (FeatureUA.WITCH_HUT_UA.func_202383_b(this.world, pos)) {
	         if (creatureType == EntityClassification.MONSTER) {
	            return FeatureUA.WITCH_HUT_UA.getSpawnList();
	         }

	         if (creatureType == EntityClassification.CREATURE) {
	            return FeatureUA.WITCH_HUT_UA.getCreatureSpawnList();
	         }
	      } else if (creatureType == EntityClassification.MONSTER) {
	         if (FeatureUA.PILLAGER_OUTPOST_UA.isPositionInStructure(this.world, pos)) {
	            return FeatureUA.PILLAGER_OUTPOST_UA.getSpawnList();
	         }

	         if (FeatureUA.OCEAN_MONUMENT_UA.isPositionInStructure(this.world, pos)) {
	            return FeatureUA.OCEAN_MONUMENT_UA.getSpawnList();
	         }
	      }

	      return super.getPossibleCreatures(creatureType, pos);
	   }

	   public void spawnMobs(ServerWorld worldIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs) {
	      this.phantomSpawner.tick(worldIn, spawnHostileMobs, spawnPeacefulMobs);
	      this.patrolSpawner.tick(worldIn, spawnHostileMobs, spawnPeacefulMobs);
	      this.catSpawner.tick(worldIn, spawnHostileMobs, spawnPeacefulMobs);
	   }

	   public int getGroundHeight() {
	      return this.world.getSeaLevel() + 1;
	   }

	   public int getSeaLevel() {
	      return ConfigUA.seaLevel;
	   }

	}