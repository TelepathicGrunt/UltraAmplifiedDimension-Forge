package net.TelepathicGrunt.UltraAmplified.World.Feature.StructureUA;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;

public class OceanRuinsPiecesUA {
   private static final ResourceLocation[] WARM_RUINS = new ResourceLocation[]{new ResourceLocation("underwater_ruin/warm_1"), new ResourceLocation("underwater_ruin/warm_2"), new ResourceLocation("underwater_ruin/warm_3"), new ResourceLocation("underwater_ruin/warm_4"), new ResourceLocation("underwater_ruin/warm_5"), new ResourceLocation("underwater_ruin/warm_6"), new ResourceLocation("underwater_ruin/warm_7"), new ResourceLocation("underwater_ruin/warm_8")};
   private static final ResourceLocation[] BRICK_RUINS = new ResourceLocation[]{new ResourceLocation("underwater_ruin/brick_1"), new ResourceLocation("underwater_ruin/brick_2"), new ResourceLocation("underwater_ruin/brick_3"), new ResourceLocation("underwater_ruin/brick_4"), new ResourceLocation("underwater_ruin/brick_5"), new ResourceLocation("underwater_ruin/brick_6"), new ResourceLocation("underwater_ruin/brick_7"), new ResourceLocation("underwater_ruin/brick_8")};
   private static final ResourceLocation[] CRACKED_RUINS = new ResourceLocation[]{new ResourceLocation("underwater_ruin/cracked_1"), new ResourceLocation("underwater_ruin/cracked_2"), new ResourceLocation("underwater_ruin/cracked_3"), new ResourceLocation("underwater_ruin/cracked_4"), new ResourceLocation("underwater_ruin/cracked_5"), new ResourceLocation("underwater_ruin/cracked_6"), new ResourceLocation("underwater_ruin/cracked_7"), new ResourceLocation("underwater_ruin/cracked_8")};
   private static final ResourceLocation[] MOSSY_RUINS = new ResourceLocation[]{new ResourceLocation("underwater_ruin/mossy_1"), new ResourceLocation("underwater_ruin/mossy_2"), new ResourceLocation("underwater_ruin/mossy_3"), new ResourceLocation("underwater_ruin/mossy_4"), new ResourceLocation("underwater_ruin/mossy_5"), new ResourceLocation("underwater_ruin/mossy_6"), new ResourceLocation("underwater_ruin/mossy_7"), new ResourceLocation("underwater_ruin/mossy_8")};
   private static final ResourceLocation[] BIG_BRICK_RUINS = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_brick_1"), new ResourceLocation("underwater_ruin/big_brick_2"), new ResourceLocation("underwater_ruin/big_brick_3"), new ResourceLocation("underwater_ruin/big_brick_8")};
   private static final ResourceLocation[] BIG_MOSSY_RUINS = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_mossy_1"), new ResourceLocation("underwater_ruin/big_mossy_2"), new ResourceLocation("underwater_ruin/big_mossy_3"), new ResourceLocation("underwater_ruin/big_mossy_8")};
   private static final ResourceLocation[] BIG_CRACKED_RUINS = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_cracked_1"), new ResourceLocation("underwater_ruin/big_cracked_2"), new ResourceLocation("underwater_ruin/big_cracked_3"), new ResourceLocation("underwater_ruin/big_cracked_8")};
   private static final ResourceLocation[] BIG_WARM_RUINS = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_warm_4"), new ResourceLocation("underwater_ruin/big_warm_5"), new ResourceLocation("underwater_ruin/big_warm_6"), new ResourceLocation("underwater_ruin/big_warm_7")};

   private static ResourceLocation getSmallRuins(Random random) {
      return WARM_RUINS[random.nextInt(WARM_RUINS.length)];
   }

   private static ResourceLocation getLargeRuins(Random random) {
      return BIG_WARM_RUINS[random.nextInt(BIG_WARM_RUINS.length)];
   }

   public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random, OceanRuinConfig ruinsConfig) {
      boolean flag = random.nextFloat() <= ruinsConfig.largeProbability;
      float f = flag ? 0.9F : 0.8F;
      addRuinsToList(templateManager, pos, rotation, pieceList, random, ruinsConfig, flag, f);
      if (flag && random.nextFloat() <= ruinsConfig.clusterProbability) {
         addChildRuins(templateManager, random, rotation, pos, ruinsConfig, pieceList);
      }

   }

   private static void addChildRuins(TemplateManager templateManager, Random random, Rotation rotationIn, BlockPos pos, OceanRuinConfig ruinsConfig, List<StructurePiece> pieceList) {
      int x = pos.getX();
      int z = pos.getZ();
      BlockPos blockpos = Template.getTransformedPos(new BlockPos(15, 0, 15), Mirror.NONE, rotationIn, new BlockPos(0, 0, 0)).add(x, 0, z);
      MutableBoundingBox mutableboundingbox = MutableBoundingBox.createProper(x, 0, z, blockpos.getX(), 0, blockpos.getZ());
      BlockPos blockpos1 = new BlockPos(Math.min(x, blockpos.getX()), 0, Math.min(z, blockpos.getZ()));
      List<BlockPos> list = getPlacements(random, blockpos1.getX(), blockpos1.getZ());
      int k = MathHelper.nextInt(random, 4, 8);

      for(int l = 0; l < k; ++l) {
         if (!list.isEmpty()) {
            int i1 = random.nextInt(list.size());
            BlockPos blockpos2 = list.remove(i1);
            int j1 = blockpos2.getX();
            int k1 = blockpos2.getZ();
            Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
            BlockPos blockpos3 = Template.getTransformedPos(new BlockPos(5, 0, 6), Mirror.NONE, rotation, new BlockPos(0, 0, 0)).add(j1, 0, k1);
            MutableBoundingBox mutableboundingbox1 = MutableBoundingBox.createProper(j1, 0, k1, blockpos3.getX(), 0, blockpos3.getZ());
            if (!mutableboundingbox1.intersectsWith(mutableboundingbox)) {
               addRuinsToList(templateManager, blockpos2, rotation, pieceList, random, ruinsConfig, false, 0.8F);
            }
         }
      }

   }

   private static List<BlockPos> getPlacements(Random random, int x, int z) {
      List<BlockPos> list = Lists.newArrayList();
      list.add(new BlockPos(x - 16 + MathHelper.nextInt(random, 1, 8), 90, z + 16 + MathHelper.nextInt(random, 1, 7)));
      list.add(new BlockPos(x - 16 + MathHelper.nextInt(random, 1, 8), 90, z + MathHelper.nextInt(random, 1, 7)));
      list.add(new BlockPos(x - 16 + MathHelper.nextInt(random, 1, 8), 90, z - 16 + MathHelper.nextInt(random, 4, 8)));
      list.add(new BlockPos(x + MathHelper.nextInt(random, 1, 7), 90, z + 16 + MathHelper.nextInt(random, 1, 7)));
      list.add(new BlockPos(x + MathHelper.nextInt(random, 1, 7), 90, z - 16 + MathHelper.nextInt(random, 4, 6)));
      list.add(new BlockPos(x + 16 + MathHelper.nextInt(random, 1, 7), 90, z + 16 + MathHelper.nextInt(random, 3, 8)));
      list.add(new BlockPos(x + 16 + MathHelper.nextInt(random, 1, 7), 90, z + MathHelper.nextInt(random, 1, 7)));
      list.add(new BlockPos(x + 16 + MathHelper.nextInt(random, 1, 7), 90, z - 16 + MathHelper.nextInt(random, 4, 8)));
      return list;
   }

   private static void addRuinsToList(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random, OceanRuinConfig ruinsConfig, boolean isLarge, float integrity) {
      if (ruinsConfig.field_204031_a == OceanRuinStructure.Type.WARM) 
      {
         ResourceLocation resourcelocation = isLarge ? getLargeRuins(random) : getSmallRuins(random);
         pieceList.add(new OceanRuinsPiecesUA.Piece(templateManager, resourcelocation, pos, rotation, integrity, ruinsConfig.field_204031_a, isLarge));
      } 
      else if (ruinsConfig.field_204031_a == OceanRuinStructure.Type.COLD) 
      {
         ResourceLocation[] aresourcelocation2 = isLarge ? BIG_BRICK_RUINS : BRICK_RUINS;
         ResourceLocation[] aresourcelocation = isLarge ? BIG_CRACKED_RUINS : CRACKED_RUINS;
         ResourceLocation[] aresourcelocation1 = isLarge ? BIG_MOSSY_RUINS : MOSSY_RUINS;
         
         int i = random.nextInt(aresourcelocation2.length);
         pieceList.add(new OceanRuinsPiecesUA.Piece(templateManager, aresourcelocation2[i], pos, rotation, integrity, ruinsConfig.field_204031_a, isLarge));
         pieceList.add(new OceanRuinsPiecesUA.Piece(templateManager, aresourcelocation[i], pos, rotation, 0.7F, ruinsConfig.field_204031_a, isLarge));
         pieceList.add(new OceanRuinsPiecesUA.Piece(templateManager, aresourcelocation1[i], pos, rotation, 0.5F, ruinsConfig.field_204031_a, isLarge));
      }

   }

   public static class Piece extends TemplateStructurePiece {
      private OceanRuinStructure.Type ruinsType;
      private float integrity;
      private ResourceLocation ruinsTemplate;
      private Rotation rotation;
      private boolean isLarge;

      public Piece(TemplateManager p_i48868_1_, ResourceLocation p_i48868_2_, BlockPos p_i48868_3_, Rotation p_i48868_4_, float p_i48868_5_, OceanRuinStructure.Type p_i48868_6_, boolean p_i48868_7_) {
         super(StructureInit.ORPUA, 0);
         this.ruinsTemplate = p_i48868_2_;
         this.templatePosition = p_i48868_3_;
         this.rotation = p_i48868_4_;
         this.integrity = p_i48868_5_;
         this.ruinsType = p_i48868_6_;
         this.isLarge = p_i48868_7_;
         this.startSetup(p_i48868_1_);
      }

      public Piece(TemplateManager p_i50592_1_, CompoundNBT p_i50592_2_) {
         super(StructureInit.ORPUA, p_i50592_2_);
         this.ruinsTemplate = new ResourceLocation(p_i50592_2_.getString("Template"));
         this.rotation = Rotation.valueOf(p_i50592_2_.getString("Rot"));
         this.integrity = p_i50592_2_.getFloat("Integrity");
         this.ruinsType = OceanRuinStructure.Type.valueOf(p_i50592_2_.getString("BiomeType"));
         this.isLarge = p_i50592_2_.getBoolean("IsLarge");
         this.startSetup(p_i50592_1_);
      }
      private void startSetup(TemplateManager templateManager) {
         Template template = templateManager.getTemplateDefaulted(this.ruinsTemplate);
         PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
         this.setup(template, this.templatePosition, placementsettings);
      }

      /**
       * (abstract) Helper method to read subclass data from NBT
       */
      protected void readAdditional(CompoundNBT tagCompound) {
         super.readAdditional(tagCompound);
         tagCompound.putString("Template", this.ruinsTemplate.toString());
         tagCompound.putString("Rot", this.rotation.name());
         tagCompound.putFloat("Integrity", this.integrity);
         tagCompound.putString("BiomeType", this.ruinsType.toString());
         tagCompound.putBoolean("IsLarge", this.isLarge);
      }


      protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
         if ("chest".equals(function)) {
        	 if(ConfigUA.chestGeneration) {
        		worldIn.setBlockState(pos, Blocks.CHEST.getDefaultState().with(ChestBlock.WATERLOGGED, Boolean.valueOf(worldIn.getFluidState(pos).isTagged(FluidTags.WATER))), 2);
	            TileEntity tileentity = worldIn.getTileEntity(pos);
	            if (tileentity instanceof ChestTileEntity) {
	               ((ChestTileEntity)tileentity).setLootTable(this.isLarge ? LootTables.CHESTS_UNDERWATER_RUIN_BIG : LootTables.CHESTS_UNDERWATER_RUIN_SMALL, rand.nextLong());
	            } 
        	 }
         } else if ("drowned".equals(function)) {
            DrownedEntity entitydrowned = EntityType.DROWNED.create(worldIn.getWorld());
            entitydrowned.enablePersistence();
            entitydrowned.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
            entitydrowned.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(pos), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
            worldIn.addEntity(entitydrowned);
            if (pos.getY() > worldIn.getSeaLevel()) {
               worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            } else {
               worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 2);
            }
         }

      }

      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos) {
         this.placeSettings.func_215219_b().addProcessor(new IntegrityProcessor(this.integrity)).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
         
         
         int finalHeight = 0;
         
         if(randomIn.nextInt(3)==0) {
        	 //a third of the ruins will generate at the bottom of the world (y = 53)
        	 finalHeight = 64;
         }else {
        	 
             //The following code will check a chunk for the height that has the most land surface exposed to the sky and generate the ruins at that height
	         //This was an attempt to make it so ruins that generates off edges would generate on the land below. Seems like it work somewhat ok but not always
        	 
             //gets the highest height that this ruins can be
	         int baseHeight = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, this.templatePosition.getX(), this.templatePosition.getZ());
             
        	//correct the chunk we are in
             int xChunkCorner = (int)((this.templatePosition.getX()/16)-1)*16;
             int zChunkCorner = (int)((this.templatePosition.getZ()/16)-1)*16;
             
             //will be used to store how much of the land is at what height
             int[] heightRanges = new int[256];
             
             for(int x = 0; x < 16; x++) {
            	 for(int z = 0; z < 16; z++) {
            		 int height = Math.min(baseHeight, worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR, xChunkCorner+x, zChunkCorner+z));
                 
            		 //cannot go higher
            		 if(height < baseHeight) {
            			 for(int range = 0; range < 4; range++) {
            				 //increment the height count as we found a land at that height
                			 heightRanges[height-range]++;
            			 }
            		 }
            	 }
             }
             
             //finds the height that has the most land surface
             int count = 0;
             for(int index = 255; index > 64; index--) {
            	 if(heightRanges[index] > count) {
            		 count = heightRanges[index];
            		 finalHeight = index;
            	 }
             }
         }
         
         
         
         this.templatePosition = new BlockPos(this.templatePosition.getX(), finalHeight-1, this.templatePosition.getZ());
         BlockPos blockpos = Template.getTransformedPos(new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1), Mirror.NONE, this.rotation, new BlockPos(0, 0, 0)).add(this.templatePosition);
         this.templatePosition = new BlockPos(this.templatePosition.getX(), this.setWaterloggedBlocks(this.templatePosition, worldIn, blockpos), this.templatePosition.getZ());
         return super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, chunkPos);
      }
      
      private int setWaterloggedBlocks(BlockPos pos1, IBlockReader blockReader, BlockPos pos2) {

          for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos1, pos2)) {
             int x = blockpos.getX();
             int z = blockpos.getZ();
             int y = pos1.getY() - 1;
             BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);
             BlockState iblockstate = blockReader.getBlockState(blockpos$mutableblockpos);

             
             //sets waterlogged blocks in ruins when ruins block replaced water or another waterlogged block
             for(IFluidState ifluidstate = blockReader.getFluidState(blockpos$mutableblockpos); (iblockstate.getMaterial() == Material.AIR || ifluidstate.isTagged(FluidTags.WATER) || iblockstate.getBlock().isIn(BlockTags.ICE)) && y > 1; ifluidstate = blockReader.getFluidState(blockpos$mutableblockpos)) {
                --y;
                blockpos$mutableblockpos.setPos(x, y, z);
                iblockstate = blockReader.getBlockState(blockpos$mutableblockpos);
             }
          }

          return pos1.getY();
       }
   }

}