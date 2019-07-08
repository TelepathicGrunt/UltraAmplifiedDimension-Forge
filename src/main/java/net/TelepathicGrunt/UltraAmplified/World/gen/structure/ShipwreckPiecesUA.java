package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.StructureIO;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class ShipwreckPiecesUA{
	   private static final BlockPos STRUCTURE_OFFSET = new BlockPos(4, 0, 15);
	   private static final ResourceLocation[] BEACHED_SHIPS = new ResourceLocation[]{new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")};
	   private static final ResourceLocation[] NOT_BEACHED_SHIPS = new ResourceLocation[]{new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/upsidedown_full"), new ResourceLocation("shipwreck/upsidedown_fronthalf"), new ResourceLocation("shipwreck/upsidedown_backhalf"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/upsidedown_full_degraded"), new ResourceLocation("shipwreck/upsidedown_fronthalf_degraded"), new ResourceLocation("shipwreck/upsidedown_backhalf_degraded"), new ResourceLocation("shipwreck/sideways_full_degraded"), new ResourceLocation("shipwreck/sideways_fronthalf_degraded"), new ResourceLocation("shipwreck/sideways_backhalf_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")};

	   public static void registerShipwreckPieces() {
	      StructureIO.registerStructureComponent(ShipwreckPiecesUA.Piece.class, "Shipwreck UA");
	   }

	   public static void beginGeneration(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> piecesList, Random random, ShipwreckConfig config) {
	      ResourceLocation resourcelocation = config.field_204753_a ? BEACHED_SHIPS[random.nextInt(BEACHED_SHIPS.length)] : NOT_BEACHED_SHIPS[random.nextInt(NOT_BEACHED_SHIPS.length)];
	      piecesList.add(new ShipwreckPiecesUA.Piece(templateManager, resourcelocation, pos, rotation, config.field_204753_a));
	   }

	   public static class Piece extends TemplateStructurePiece {
	      private Rotation rotation;
	      private ResourceLocation resourceLocation;
	      private boolean isBeached;

	      public Piece() {
	      }

	      public Piece(TemplateManager templateManager, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rot, boolean beachedIn) {
	         super(0);
	         this.templatePosition = pos;
	         this.rotation = rot;
	         this.resourceLocation = resourceLocationIn;
	         this.isBeached = beachedIn;
	         this.func_204754_a(templateManager);
	      }

	      /**
	       * (abstract) Helper method to write subclass data to NBT
	       */
	      protected void writeStructureToNBT(NBTTagCompound tagCompound) {
	         super.writeStructureToNBT(tagCompound);
	         tagCompound.setString("Template", this.resourceLocation.toString());
	         tagCompound.setBoolean("isBeached", this.isBeached);
	         tagCompound.setString("Rot", this.rotation.name());
	      }

	      /**
	       * (abstract) Helper method to read subclass data from NBT
	       */
	      protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager templateManager) {
	         super.readStructureFromNBT(tagCompound, templateManager);
	         this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
	         this.isBeached = tagCompound.getBoolean("isBeached");
	         this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
	         this.func_204754_a(templateManager);
	      }

	      private void func_204754_a(TemplateManager templateManager) {
	         Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
	         PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setReplacedBlock(Blocks.AIR).setMirror(Mirror.NONE).setCenterOffset(ShipwreckPiecesUA.STRUCTURE_OFFSET);
	         this.setup(template, this.templatePosition, placementsettings);
	      }

	      protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
	         if(ConfigUA.chestGeneration) {
	        	 if ("map_chest".equals(function)) {
	 	            TileEntityLockableLoot.setLootTable(worldIn, rand, pos.down(), LootTableList.CHESTS_SHIPWRECK_MAP);
	 	         } else if ("treasure_chest".equals(function)) {
	 	            TileEntityLockableLoot.setLootTable(worldIn, rand, pos.down(), LootTableList.CHESTS_SHIPWRECK_TREASURE);
	 	         } else if ("supply_chest".equals(function)) {
	 	            TileEntityLockableLoot.setLootTable(worldIn, rand, pos.down(), LootTableList.CHESTS_SHIPWRECK_SUPPLY);
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
	         return super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, p_74875_4_);
	      }
	   }
	}