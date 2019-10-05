package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class ShipwreckPiecesUA{
	   private static final BlockPos STRUCTURE_OFFSET = new BlockPos(4, 0, 15);
	   private static final ResourceLocation[] BEACHED_SHIPS = new ResourceLocation[]{new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")};
	   private static final ResourceLocation[] NOT_BEACHED_SHIPS = new ResourceLocation[]{new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/upsidedown_full"), new ResourceLocation("shipwreck/upsidedown_fronthalf"), new ResourceLocation("shipwreck/upsidedown_backhalf"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/upsidedown_full_degraded"), new ResourceLocation("shipwreck/upsidedown_fronthalf_degraded"), new ResourceLocation("shipwreck/upsidedown_backhalf_degraded"), new ResourceLocation("shipwreck/sideways_full_degraded"), new ResourceLocation("shipwreck/sideways_fronthalf_degraded"), new ResourceLocation("shipwreck/sideways_backhalf_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")};
	   private static int heightOffset = 0;
	   
	   public static void beginGeneration(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> piecesList, Random random, ShipwreckConfig config) {
	      ResourceLocation resourcelocation = config.isBeached ? BEACHED_SHIPS[random.nextInt(BEACHED_SHIPS.length)] : NOT_BEACHED_SHIPS[random.nextInt(NOT_BEACHED_SHIPS.length)];
	      piecesList.add(new ShipwreckPiecesUA.Piece(templateManager, resourcelocation, pos, rotation, config.isBeached));
	   }

	   public static class Piece extends TemplateStructurePiece {
	      private Rotation rotation;
	      private ResourceLocation resourceLocation;
	      private boolean isBeached;

	      public Piece(TemplateManager p_i48904_1_, ResourceLocation p_i48904_2_, BlockPos p_i48904_3_, Rotation p_i48904_4_, boolean p_i48904_5_) {
	          super(StructureInitUA.SHIPWRECKUA, 0);
	          this.templatePosition = p_i48904_3_;
	          this.rotation = p_i48904_4_;
	          this.resourceLocation = p_i48904_2_;
	          this.isBeached = p_i48904_5_;
	          this.func_204754_a(p_i48904_1_);
	       }

	       public Piece(TemplateManager p_i50445_1_, CompoundNBT p_i50445_2_) {
	          super(StructureInitUA.SHIPWRECKUA, p_i50445_2_);
	          this.resourceLocation = new ResourceLocation(p_i50445_2_.getString("Template"));
	          this.isBeached = p_i50445_2_.getBoolean("isBeached");
	          this.rotation = Rotation.valueOf(p_i50445_2_.getString("Rot"));
	          this.func_204754_a(p_i50445_1_);
	       }

	       /**
	        * (abstract) Helper method to read subclass data from NBT
	        */
	       protected void readAdditional(CompoundNBT tagCompound) {
	          super.readAdditional(tagCompound);
	          tagCompound.putString("Template", this.resourceLocation.toString());
	          tagCompound.putBoolean("isBeached", this.isBeached);
	          tagCompound.putString("Rot", this.rotation.name());
	       }

	      private void func_204754_a(TemplateManager templateManager) {
	         Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
	         PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(ShipwreckPiecesUA.STRUCTURE_OFFSET).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
	         this.setup(template, this.templatePosition, placementsettings);
	      }

	      protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
	         if(ConfigUA.chestGeneration) {
	        	 if ("map_chest".equals(function)) {
	 	            LockableLootTileEntity.setLootTable(worldIn, rand, pos.down(), LootTables.CHESTS_SHIPWRECK_MAP);
	 	         } else if ("treasure_chest".equals(function)) {
	 	            LockableLootTileEntity.setLootTable(worldIn, rand, pos.down(), LootTables.CHESTS_SHIPWRECK_TREASURE);
	 	         } else if ("supply_chest".equals(function)) {
	 	            LockableLootTileEntity.setLootTable(worldIn, rand, pos.down(), LootTables.CHESTS_SHIPWRECK_SUPPLY);
	 	         }
	         }
	         else {
	        	 
	        	 //removes chest 
	        	 if(worldIn.getBlockState(pos.down()).getFluidState().isEmpty()) {
		        	 worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 2);
	        	 }else {
	        		 worldIn.setBlockState(pos.down(), Blocks.WATER.getDefaultState(), 2);
	        	 }
	         }
	    	  

	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	    	  
	    	  //applies a height offset that the rest of the shipwreck will use
	    	  if(heightOffset == 0) {
	    		 int xOffset = 8;
    	         int zOffset = 8;
    	         if (rotation == Rotation.CLOCKWISE_90) {
    	            zOffset = 16;
    	         } else if (rotation == Rotation.CLOCKWISE_180) {
    	            xOffset = 0;
    	            zOffset = 16;
    	         } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
    	            xOffset = 0;
    	            zOffset = 16;
    	         }
    	         
    	         
    	         int randHeight = randomIn.nextInt(130)+90;
    	    	 BlockPos blockpos = new BlockPos(templatePosition.getX() + xOffset, 0, templatePosition.getZ() + zOffset);
    	         

    	         //finds surface on water
    	         while(randHeight > 65 && worldIn.getBlockState(blockpos.up(randHeight)).getFluidState().isEmpty()) {
    	        	 randHeight--;
    	         }
    	         
    	         //finds bottom of water body
    	         while(randHeight > 65 && !worldIn.getBlockState(blockpos.up(randHeight)).getFluidState().isEmpty()) {
    	        	 randHeight--;
    	         }
    	     
    	         
    	         heightOffset = randHeight-2;
	    	  }
     	      templatePosition = new BlockPos(templatePosition.getX(), heightOffset, templatePosition.getZ());

	         return super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, p_74875_4_);
	      }
	   }
	}