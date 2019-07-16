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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class NetherBridgeUA extends Structure<NetherBridgeConfigUA> 
{
  public NetherBridgeUA(Function<Dynamic<?>, ? extends NetherBridgeConfigUA> p_i51427_1_) {
		super(p_i51427_1_);
	}

private static final List<Biome.SpawnListEntry> NETHER_FORTRESS_ENEMIES = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.BLAZE, 10, 2, 3), new Biome.SpawnListEntry(EntityType.ZOMBIE_PIGMAN, 5, 4, 4), new Biome.SpawnListEntry(EntityType.WITHER_SKELETON, 8, 5, 5), new Biome.SpawnListEntry(EntityType.SKELETON, 2, 5, 5), new Biome.SpawnListEntry(EntityType.MAGMA_CUBE, 3, 4, 4));

   public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
	   if(ConfigUA.netherFortressAboveground || ConfigUA.netherFortressUnderground) {
		  int i = chunkPosX >> 4;
	      int j = chunkPosZ >> 4;
	      rand.setSeed((long)(i ^ j << 4) ^ chunkGen.getSeed());
	      rand.nextInt();
	      if (rand.nextInt(3) != 0) {
	         return false;
	      } else if (chunkPosX != (i << 4) + 4 + rand.nextInt(8)) {
	         return false;
	      } else if (chunkPosZ != (j << 4) + 4 + rand.nextInt(8)) {
	         return false;
	      } else {
	         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9));
	         return chunkGen.hasStructure(biome, FeatureUA.FORTRESS_UA);
	      }
	   }
	   
      return false;
   }

   protected boolean isEnabledIn(IWorld worldIn) {
      return worldIn.getWorldInfo().isMapFeaturesEnabled();
   }

   public Structure.IStartFactory getStartFactory() {
      return NetherBridgeUA.Start::new;
   }


   public String getStructureName() {
      return "Nether Fortress UA";
   }

   public int getSize() {
      return 8;
   }

   public List<Biome.SpawnListEntry> getSpawnList() {
      return NETHER_FORTRESS_ENEMIES;
   }

   public static class Start extends StructureStart {
	  private boolean genAboveSeaLevel;
	      
	  public Start(Structure<?> p_i50437_1_, int p_i50437_2_, int p_i50437_3_, Biome p_i50437_4_, MutableBoundingBox p_i50437_5_, int p_i50437_6_, long p_i50437_7_) {
         super(p_i50437_1_, p_i50437_2_, p_i50437_3_, p_i50437_4_, p_i50437_5_, p_i50437_6_, p_i50437_7_);
      }

      public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
         NetherBridgeConfigUA fortressconfig = (NetherBridgeConfigUA)generator.getStructureConfig(biomeIn, FeatureUA.FORTRESS_UA);
         this.genAboveSeaLevel = fortressconfig.surfaceAllow;
         NetherBridgePiecesUA.Start fortresspieces$start = new NetherBridgePiecesUA.Start(this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
         this.components.add(fortresspieces$start);
         
         
         
         fortresspieces$start.buildComponent(fortresspieces$start, this.components, this.rand);
         List<StructurePiece> list = fortresspieces$start.pendingChildren;

         while(!list.isEmpty()) {
            int i = this.rand.nextInt(list.size());
            StructurePiece structurepiece = list.remove(i);
            structurepiece.buildComponent(fortresspieces$start, this.components, this.rand);
         }

         
         this.recalculateStructureSize();
         
         //gens aboveground 50% of time if underground is allowed and 100% of time when underground is not allowed
         if(genAboveSeaLevel && (this.rand.nextBoolean() || !ConfigUA.netherFortressUnderground) && ConfigUA.netherFortressAboveground) {
             this.func_214626_a(this.rand, 85, 130);
             UltraAmplified.Logger.log(Level.DEBUG, "Aboveground Nether Fortress | "+(chunkX*16)+" "+(chunkZ*16));
         }
         else if(ConfigUA.netherFortressUnderground) {
             this.func_214626_a(this.rand, 15, 30);
             UltraAmplified.Logger.log(Level.DEBUG, "Underground Nether Fortress | "+(chunkX*16)+" "+(chunkZ*16));
         }
      }
   }
}