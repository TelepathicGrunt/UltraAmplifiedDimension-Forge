package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class IceSpikeTempleUA extends Structure<NoFeatureConfig> {
   
	public IceSpikeTempleUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_) {
		super(p_i51427_1_);
	}

	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
	      int maxDistance = ConfigUA.iceSpikeTempleSpawnrate;
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
	
	   
	   public String getStructureName() {
	      return UltraAmplified.MODID+":ice_spike_temple";
	   }

	   public int getSize() {
	      return 3;
	   }

	   public Structure.IStartFactory getStartFactory() {
	      return IceSpikeTempleUA.Start::new;
	   }

	   protected int getSeedModifier() {
	      return 14357621;
	   }

	   public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
	      ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
	      if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
	         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos(chunkPosX * 16 + 9, 0, chunkPosZ * 16 + 9));
	         if (ConfigUA.iceSpikeTempleSpawnrate != 101 && chunkGen.hasStructure(biome, this)) {
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
	    	  //
	         int xOffset = 9;
	         int zOffset = 20;
	         if (rotation == Rotation.CLOCKWISE_90) {
	            xOffset = -20;
	            zOffset = 9;
	         } else if (rotation == Rotation.CLOCKWISE_180) {
	            xOffset = -9;
	            zOffset = -20;
	         } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
		        xOffset = 20;
	            zOffset = -9;
	         }
	         

	         int x = (chunkX << 4) + 7;
	         int z = (chunkZ << 4) + 7;
	         int surfaceY = generator.func_222531_c(x + xOffset, z + zOffset, Heightmap.Type.WORLD_SURFACE_WG);
	         int y =  Math.min(surfaceY, 230);
	         
	         if (y >= 70) {
		         BlockPos blockpos = new BlockPos(x, y, z);
		         IceSpikeTemplePiecesUA.start(templateManagerIn, blockpos, rotation, this.components, this.rand);
		         this.recalculateStructureSize();
			     //UltraAmplified.LOGGER.log(Level.DEBUG, "Ice Spike Temple | "+(blockpos.getX() + xOffset)+" "+blockpos.getY()+" "+(blockpos.getZ() + zOffset));
	         }
	      }

	   }
}