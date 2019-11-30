package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class MushroomTemplePiecesUA {
	   private static final ResourceLocation MUSHROOM_TEMPLE = new ResourceLocation(UltraAmplified.MODID+":mushroom_temple");
	   private static final Map<ResourceLocation, BlockPos> OFFSET1 = ImmutableMap.of(MUSHROOM_TEMPLE, new BlockPos(0, 4, 0));
	   private static final Map<ResourceLocation, BlockPos> OFFSET2 = ImmutableMap.of(MUSHROOM_TEMPLE, new BlockPos(-8, -1, -8));
	   private static final ResourceLocation CHESTS_MUSHROOM_TEMPLE_UA = new ResourceLocation(UltraAmplified.MODID+":chests/mushroom_temple_ua");

	  
	   public static void start(TemplateManager templateManagerIn, BlockPos pos, Rotation rotationIn, List<StructurePiece> structurePieceList, Random randomIn) {
	    
	      structurePieceList.add(new MushroomTemplePiecesUA.Piece(templateManagerIn, MUSHROOM_TEMPLE, pos, rotationIn, 0));
	   }

	   public static class Piece extends TemplateStructurePiece {
	      private ResourceLocation resourceLocation;
	      private Rotation rotation;

	      public Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos pos, Rotation rotation, int downDepth) {
	    	 super(StructureInitUA.MTUA, 0);
	         this.resourceLocation = resourceLocation;
	         BlockPos blockpos = MushroomTemplePiecesUA.OFFSET2.get(resourceLocation);
	         this.templatePosition = pos.add(blockpos.getX(), blockpos.getY() - downDepth, blockpos.getZ());
	         this.rotation = rotation;
	         this.setupPiece(templateManager);
	      }


	      public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
	         super(StructureInitUA.MTUA, tagCompound);
	         this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
	         this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
	         this.setupPiece(templateManagerIn);
	      }
	      
	      private void setupPiece(TemplateManager templateManager) {
	         Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
	         PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(MushroomTemplePiecesUA.OFFSET1.get(this.resourceLocation));
	         this.setup(template, this.templatePosition, placementsettings);
	      }

	      /**
	       * (abstract) Helper method to write subclass data to NBT
	       */
	      protected void readAdditional(CompoundNBT tagCompound) {
	          super.readAdditional(tagCompound);
	          tagCompound.putString("Template", this.resourceLocation.toString());
	          tagCompound.putString("Rot", this.rotation.name());
	       }

	      protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
	         if ("chest".equals(function)) {
	            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
	            TileEntity tileentity = worldIn.getTileEntity(pos.down());
	            if (tileentity instanceof ChestTileEntity) {
	            	if(ConfigUA.chestGeneration) {
	 	               ((ChestTileEntity)tileentity).setLootTable(CHESTS_MUSHROOM_TEMPLE_UA, rand.nextLong());
	            	}else {
	            		worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 2);
	            	}
	            }

	         }
	      }

	      
	      
	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos) {
	    	 PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(MushroomTemplePiecesUA.OFFSET1.get(this.resourceLocation));
	         BlockPos blockpos = MushroomTemplePiecesUA.OFFSET2.get(this.resourceLocation);
	         BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(3 - blockpos.getX(), 0, 0 - blockpos.getZ())));
	         
	          int i = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
	          
	          if(i > 244) {
	        	  i = 244;
	          }

	         BlockPos blockpos2 = this.templatePosition;
	         this.templatePosition = this.templatePosition.add(0, i - 90 - 1, 0);
	         boolean flag = super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, chunkPos);
	         
	         this.templatePosition = blockpos2;
	         return flag;
	      }
	   }
	}