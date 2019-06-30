package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Random;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.structure.StructureIO;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class WitchHutPiecesUA extends ScatteredStructurePiece {
   private boolean witch;

   public static void registerPieces() {
      StructureIO.registerStructureComponent(WitchHutPiecesUA.class, "TeSH");
   }

   public WitchHutPiecesUA() {
   }

   public WitchHutPiecesUA(Random random, int x, int y, int z) {
      super(random, x, y, z, 7, 7, 9);
   }


   protected void writeStructureToNBT(NBTTagCompound tagCompound) {
      super.writeStructureToNBT(tagCompound);
      tagCompound.setBoolean("Witch", this.witch);
   }


   protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager templateManager) {
      super.readStructureFromNBT(tagCompound, templateManager);
      this.witch = tagCompound.getBoolean("Witch");
   }


   public boolean addComponentParts(IWorld worldIn, Random random, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos) {
      if (!this.func_202580_a(worldIn, structureBoundingBoxIn, 0)) {
         return false;
      } else {
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 5, 1, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 2, 5, 4, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 4, 1, 0, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 2, 3, 3, 2, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 3, 1, 3, 6, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 3, 5, 3, 6, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 7, 4, 3, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 2, 1, 3, 2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 2, 5, 3, 2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 7, 1, 3, 7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 7, 5, 3, 7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 2, 3, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 3, 3, 7, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 3, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.POTTED_RED_MUSHROOM.getDefaultState(), 1, 3, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.BREWING_STAND.getDefaultState(), 2, 3, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.CAULDRON.getDefaultState().with(BlockCauldron.LEVEL, 2), 4, 2, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 1, 2, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 5, 2, 1, structureBoundingBoxIn);
         IBlockState iblockstate = Blocks.SPRUCE_STAIRS.getDefaultState().with(BlockStairs.FACING, EnumFacing.NORTH);
         IBlockState iblockstate1 = Blocks.SPRUCE_STAIRS.getDefaultState().with(BlockStairs.FACING, EnumFacing.EAST);
         IBlockState iblockstate2 = Blocks.SPRUCE_STAIRS.getDefaultState().with(BlockStairs.FACING, EnumFacing.WEST);
         IBlockState iblockstate3 = Blocks.SPRUCE_STAIRS.getDefaultState().with(BlockStairs.FACING, EnumFacing.SOUTH);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 6, 4, 1, iblockstate, iblockstate, false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 2, 0, 4, 7, iblockstate1, iblockstate1, false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 4, 2, 6, 4, 7, iblockstate2, iblockstate2, false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 8, 6, 4, 8, iblockstate3, iblockstate3, false);
         this.setBlockState(worldIn, iblockstate.with(BlockStairs.SHAPE, StairsShape.OUTER_RIGHT), 0, 4, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, iblockstate.with(BlockStairs.SHAPE, StairsShape.OUTER_LEFT), 6, 4, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, iblockstate3.with(BlockStairs.SHAPE, StairsShape.OUTER_LEFT), 0, 4, 8, structureBoundingBoxIn);
         this.setBlockState(worldIn, iblockstate3.with(BlockStairs.SHAPE, StairsShape.OUTER_RIGHT), 6, 4, 8, structureBoundingBoxIn);

       //following code is a modified version from JavaMan7's youtube tutorial about making structures (But went into detail about how to put potions in brewing stands)
         TileEntity tileentity = worldIn.getTileEntity(new BlockPos(this.getXWithOffset(2, 6), this.getYWithOffset(3), this.getZWithOffset(2, 6)));
         
         if (tileentity instanceof TileEntityBrewingStand)
         {
            	 int potionSlot = 1 + random.nextInt(3);
            	 
            	 for (int j = 0; j < potionSlot; j++) {
            		 
            		 int potionType = random.nextInt(9);
            		
            		 ItemStack potion=null;
		           	
            		 //5/9 chance
            		 if(potionType < 5) {
            			 potion = new ItemStack(Items.POTION);
            			 PotionUtils.addPotionToItemStack(potion, PotionTypes.STRONG_LEAPING);
            	 	 }
            		 //3/9 chance
            		 else if(potionType < 8) {
            			 potion = new ItemStack(Items.POTION);
            			 PotionUtils.addPotionToItemStack(potion, PotionTypes.STRONG_POISON); 
            		 }
            		 //1/9 chance
            		 else{
            			 potion = new ItemStack(Items.POTION);
            			 PotionUtils.addPotionToItemStack(potion, PotionTypes.LONG_NIGHT_VISION);
		           	 }
            	 
	               	 ((TileEntityBrewingStand)tileentity).setInventorySlotContents(j,potion);
	             }
         }
         
         
         for(int i = 2; i <= 7; i += 5) {
            for(int j = 1; j <= 5; j += 4) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.OAK_LOG.getDefaultState(), j, -1, i, structureBoundingBoxIn);
            }
         }

         if (!this.witch) {
            int l = this.getXWithOffset(2, 5);
            int i1 = this.getYWithOffset(2);
            int k = this.getZWithOffset(2, 5);
            if (structureBoundingBoxIn.isVecInside(new BlockPos(l, i1, k))) {
               this.witch = true;
               for(int i = 0; i < 2; i++) {
                   EntityWitch entitywitch = new EntityWitch(worldIn.getWorld());
                   entitywitch.enablePersistence();
                   entitywitch.setLocationAndAngles((double)l + 0.5D, (double)i1, (double)k + 0.5D, 0.0F, 0.0F);
                   entitywitch.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(l, i1, k)), (IEntityLivingData)null, (NBTTagCompound)null);
                   worldIn.spawnEntity(entitywitch);
               }
            }
         }

         return true;
      }
   }
}