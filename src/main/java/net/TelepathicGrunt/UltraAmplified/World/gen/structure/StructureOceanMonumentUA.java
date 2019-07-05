package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.structure.OceanMonumentConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;

public class StructureOceanMonumentUA extends Structure<OceanMonumentConfig> 
{
    //public static final List<Biome> SPAWN_BIOMES = Arrays.<Biome>asList(new Biome[] { BiomeInit.BiomeJungle, BiomeInit.BiomeJungleEdge, BiomeInit.BiomeJungleEdgeM, BiomeInit.BiomeJungleHills, BiomeInit.BiomeJungleM, Biomes.DEEP_OCEAN});
    private static final List<Biome.SpawnListEntry> MONUMENT_ENEMIES = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.GUARDIAN, 1, 2, 4));

    protected ChunkPos getStartPositionForPosition(IChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
       int maxDistance = Config.monumentRarity;
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
    

    protected boolean hasStartAt(IChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
       ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
       if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
          for(Biome biome : chunkGen.getBiomeProvider().getBiomesInSquare(chunkPosX * 16 + 9, chunkPosZ * 16 + 9, 16)) {
             if (!chunkGen.hasStructure(biome, FeatureUA.OCEAN_MONUMENT_UA)) {
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

    protected StructureStart makeStart(IWorld worldIn, IChunkGenerator<?> generator, SharedSeedRandom random, int x, int z) {
       Biome biome = generator.getBiomeProvider().getBiome(new BlockPos((x << 4) + 9, 0, (z << 4) + 9), Biomes.DEFAULT);
       return new StructureOceanMonumentUA.Start(worldIn, generator, random, x, z, biome);
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
  	    private boolean generateHigh;
        private final Set<ChunkPos> processed = Sets.<ChunkPos>newHashSet();
        private boolean wasCreated;

        public Start()
        {
        }

        public Start(IWorld worldIn, IChunkGenerator<?> chunkGenerator, SharedSeedRandom random, int chunkX, int chunkZ, Biome biome) {
            super(chunkX, chunkZ, biome, random, worldIn.getSeed());
            
            OceanMonumentConfig fortressconfig = (OceanMonumentConfig)chunkGenerator.getStructureConfig(biome, FeatureUA.OCEAN_MONUMENT_UA);
            //this.genAboveSeaLevel = OceanMonumentConfig.surfaceAllow;
            
            this.create(worldIn, random, chunkX, chunkZ);
         }

        private void create(IBlockReader worldIn, Random random, int chunkX, int chunkZ) 
        {
        	  int i = chunkX * 16 - 29;
              int j = chunkZ * 16 - 29;
              EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(random);
              this.components.add(new OceanMonumentPiecesUA.MonumentBuilding(random, i, j, enumfacing));
              this.recalculateStructureSize(worldIn);
              
              UltraAmplified.Logger.log(Level.DEBUG, "Ocean Monument | "+(chunkX*16)+" "+(chunkZ*16));
              
            
              this.wasCreated = true;
        }

        public void generateStructure(IWorld worldIn, Random rand, MutableBoundingBox structurebb, ChunkPos p_75068_4_) {
            if (!this.wasCreated) {
               this.components.clear();
               this.create(worldIn, rand, this.getChunkPosX(), this.getChunkPosZ());
            }

            super.generateStructure(worldIn, rand, structurebb, p_75068_4_);
         }

         public void notifyPostProcessAt(ChunkPos pair) {
            super.notifyPostProcessAt(pair);
            this.processed.add(pair);
         }

         public void writeAdditional(NBTTagCompound tagCompound) {
            super.writeAdditional(tagCompound);
            NBTTagList nbttaglist = new NBTTagList();

            for(ChunkPos chunkpos : this.processed) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.setInt("X", chunkpos.x);
               nbttagcompound.setInt("Z", chunkpos.z);
               nbttaglist.add((INBTBase)nbttagcompound);
            }

            tagCompound.setTag("Processed", nbttaglist);
         }

         public void readAdditional(NBTTagCompound tagCompound) {
            super.readAdditional(tagCompound);
            if (tagCompound.contains("Processed", 9)) {
               NBTTagList nbttaglist = tagCompound.getList("Processed", 10);

               for(int i = 0; i < nbttaglist.size(); ++i) {
                  NBTTagCompound nbttagcompound = nbttaglist.getCompound(i);
                  this.processed.add(new ChunkPos(nbttagcompound.getInt("X"), nbttagcompound.getInt("Z")));
               }
            }

         }
    }
}
