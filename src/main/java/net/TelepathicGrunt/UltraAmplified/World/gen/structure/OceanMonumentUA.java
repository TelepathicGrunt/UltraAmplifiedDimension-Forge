package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class OceanMonumentUA extends Structure<NoFeatureConfig> 
{
    public OceanMonumentUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_) {
		super(p_i51427_1_);
	}

	private static final List<Biome.SpawnListEntry> MONUMENT_ENEMIES = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.GUARDIAN, 1, 2, 4));

    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
       int maxDistance = ConfigUA.monumentRarity;
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
       ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 10387313);
       k1 = k1 * maxDistance;
       l1 = l1 * maxDistance;
       k1 = k1 + (random.nextInt(maxDistance - minDistance) + random.nextInt(maxDistance - minDistance)) / 2;
       l1 = l1 + (random.nextInt(maxDistance - minDistance) + random.nextInt(maxDistance - minDistance)) / 2;
       return new ChunkPos(k1, l1);
    }
    

    public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
       ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
       if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
          for(Biome biome : chunkGen.getBiomeProvider().getBiomesInSquare(chunkPosX * 16 + 9, chunkPosZ * 16 + 9, 16)) {
             if (!ConfigUA.oceanMonumentGeneration || !chunkGen.hasStructure(biome, FeatureUA.OCEAN_MONUMENT_UA)) {
                return false;
             }
          }

          return true;
       } else {
          return false;
       }
    }

    protected boolean isEnabledIn(IWorld worldIn) {
       return worldIn.getWorldInfo().isMapFeaturesEnabled();
    }

    public Structure.IStartFactory getStartFactory() {
       return OceanMonumentUA.Start::new;
    }
    
    public String getStructureName()
    {
        return "Ocean Monument UA";
    }

    public int getSize() {
       return 8;
    }

    public List<Biome.SpawnListEntry> getSpawnList() {
       return MONUMENT_ENEMIES;
    }

    public static class Start extends StructureStart
    {
        private boolean wasCreated;
        public Start(Structure<?> p_i50437_1_, int p_i50437_2_, int p_i50437_3_, Biome p_i50437_4_, MutableBoundingBox p_i50437_5_, int p_i50437_6_, long p_i50437_7_) {
           super(p_i50437_1_, p_i50437_2_, p_i50437_3_, p_i50437_4_, p_i50437_5_, p_i50437_6_, p_i50437_7_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
        	this.generate(chunkX, chunkZ);
        }

        private void generate(int chunkX, int chunkZ) {
        	  int i = chunkX * 16 - 29;
              int j = chunkZ * 16 - 29;
              Direction enumfacing = Direction.Plane.HORIZONTAL.random(this.rand);
              this.components.add(new OceanMonumentPiecesUA.MonumentBuilding(this.rand, i, j, enumfacing));
              this.recalculateStructureSize();
              
              UltraAmplified.Logger.log(Level.DEBUG, "Ocean Monument | "+(chunkX*16)+" "+(chunkZ*16));

              this.wasCreated = true;
        }
        /**
         * Keeps iterating Structure Pieces and spawning them until the checks tell it to stop
         */
        public void generateStructure(IWorld worldIn, Random rand, MutableBoundingBox structurebb, ChunkPos pos) {
           if (!this.wasCreated) {
              this.components.clear();
              this.generate(this.getChunkPosX(), this.getChunkPosZ());
           }

           super.generateStructure(worldIn, rand, structurebb, pos);
        }
    }
}
