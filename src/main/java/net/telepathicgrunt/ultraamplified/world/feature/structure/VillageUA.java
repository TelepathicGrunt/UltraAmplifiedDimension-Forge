package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;
import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.structure.VillagePieces;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;

public class VillageUA  extends Structure<VillageConfigUA> {
   public VillageUA(Function<Dynamic<?>, ? extends VillageConfigUA> p_i51427_1_) {
		super(p_i51427_1_);
	}

   public String getStructureName() {
	      return UltraAmplified.MODID+":village";
   }

   public int getSize() {
      return 8;
   }

   protected boolean isEnabledIn(IWorld worldIn) {
      return worldIn.getWorldInfo().isMapFeaturesEnabled();
   }

   protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
      int maxDistance = ConfigUA.villageSpawnrate;
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
      ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 10387312);
      k1 = k1 * maxDistance;
      l1 = l1 * maxDistance;
      k1 = k1 + random.nextInt(maxDistance - minDistance);
      l1 = l1 + random.nextInt(maxDistance - minDistance);
      return new ChunkPos(k1, l1);
   }

   public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
      ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
      if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9));
         if(ConfigUA.villageSpawnrate != 101 && chunkGen.hasStructure(biome, FeatureUA.VILLAGE_UA)) {
        	return true;  
         }
      } 
     return false;
   }

   public Structure.IStartFactory getStartFactory() {
      return VillageUA.Start::new;
   }

   
   public static class Start extends StructureStart {
	   public Start(Structure<?> p_i51110_1_, int p_i51110_2_, int p_i51110_3_, Biome p_i51110_4_, MutableBoundingBox p_i51110_5_, int p_i51110_6_, long p_i51110_7_) {
         super(p_i51110_1_, p_i51110_2_, p_i51110_3_, p_i51110_4_, p_i51110_5_, p_i51110_6_, p_i51110_7_);
      }

      public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
         VillageConfigUA villageconfig = (VillageConfigUA)generator.getStructureConfig(biomeIn, FeatureUA.VILLAGE_UA); 
         
         //if we are requesting a village type that vanilla already can make,
         //we then generate a vanilla village. 
         //This sets up the config that vanilla needs
         VillageConfig vanillaVillageConfig = new VillageConfig("village/plains/town_centers", 6);
         boolean genVanillaVillage = false;
         if(villageconfig.type == VillagePiecesUA.Type.OAK) {
        	 genVanillaVillage = true;
         }
         else if(villageconfig.type == VillagePiecesUA.Type.SANDSTONE) {
        	 vanillaVillageConfig = new VillageConfig("village/desert/town_centers", 6);
        	 genVanillaVillage = true;
         }
         else if(villageconfig.type == VillagePiecesUA.Type.ACACIA) {
        	 vanillaVillageConfig = new VillageConfig("village/savanna/town_centers", 6);
        	 genVanillaVillage = true;
         }
         else if(villageconfig.type == VillagePiecesUA.Type.SNOWYSPRUCE) {
        	 vanillaVillageConfig = new VillageConfig("village/snowy/town_centers", 6);
        	 genVanillaVillage = true;
         }
         else if(villageconfig.type == VillagePiecesUA.Type.SPRUCE) {
        	 vanillaVillageConfig = new VillageConfig("village/taiga/town_centers", 6);
        	 genVanillaVillage = true;
         }
         
         if(genVanillaVillage) {
        	 //generates a vanilla village so if villages change in future updates, we pull the new villages automatically
        	 BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
             VillagePieces.func_214838_a(generator, templateManagerIn, blockpos, this.components, this.rand, vanillaVillageConfig);
             this.recalculateStructureSize();
         }else {
        	 //generates our own kind of village
        	 List<VillagePiecesUA.PieceWeightUA> list = VillagePiecesUA.getStructureVillageWeightedPieceList(this.rand, this.rand.nextInt(3));
             VillagePiecesUA.Start villagepiecesua$start = new VillagePiecesUA.Start(0, this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageconfig, biomeIn);
             this.components.add(villagepiecesua$start);
             villagepiecesua$start.buildComponent(villagepiecesua$start, this.components, this.rand);
             List<StructurePiece> list1 = villagepiecesua$start.pendingRoads;
             List<StructurePiece> list2 = villagepiecesua$start.pendingHouses;

             while(!list1.isEmpty() || !list2.isEmpty()) {
                if (list1.isEmpty()) {
                   int i = this.rand.nextInt(list2.size());
                   StructurePiece structurepiece = list2.remove(i);
                   structurepiece.buildComponent(villagepiecesua$start, this.components, this.rand);
                } else {
                   int j = this.rand.nextInt(list1.size());
                   StructurePiece structurepiece2 = list1.remove(j);
                   structurepiece2.buildComponent(villagepiecesua$start, this.components, this.rand);
                }
             }
         }
        
         

         this.recalculateStructureSize();
        // UltraAmplified.LOGGER.log(Level.DEBUG,villageconfig.type+" Village | "+chunkX*16+", "+chunkZ*16);
      }
   }
}
