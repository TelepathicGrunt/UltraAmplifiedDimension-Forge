package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.villager.IVillagerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.BrewingStandTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;


public class VillagePiecesUA
{
	private static final IVillagerType[] VILLAGER_SKIN_ENUM = {
			IVillagerType.PLAINS,
			IVillagerType.DESERT,
			IVillagerType.SAVANNA,
			IVillagerType.TAIGA,
			IVillagerType.SNOW,
			IVillagerType.SWAMP,
			IVillagerType.JUNGLE,
			IVillagerType.TAIGA,
			IVillagerType.JUNGLE,
			IVillagerType.TAIGA,
			IVillagerType.DESERT,
			IVillagerType.SNOW,
	};
	
	

	  public static List<PieceWeightUA> getStructureVillageWeightedPieceList(Random random, int size) {
	      List<PieceWeightUA> list = Lists.newArrayList();
	      list.add(new VillagePiecesUA.PieceWeightUA(VillagePiecesUA.FlatTopTinyHouse.class, 4, MathHelper.nextInt(random, 2 + size, 4 + size * 2)));
	      list.add(new VillagePiecesUA.PieceWeightUA(VillagePiecesUA.Church.class, 20, MathHelper.nextInt(random, 0 + size, 1 + size)));
	      list.add(new VillagePiecesUA.PieceWeightUA(VillagePiecesUA.LibraryHouse.class, 20, MathHelper.nextInt(random, 0 + size, 2 + size)));
	      list.add(new VillagePiecesUA.PieceWeightUA(VillagePiecesUA.TinyHouse.class, 3, MathHelper.nextInt(random, 2 + size, 5 + size * 3)));
	      list.add(new VillagePiecesUA.PieceWeightUA(VillagePiecesUA.WorkHouse.class, 15, MathHelper.nextInt(random, 0 + size, 2 + size)));
	      list.add(new VillagePiecesUA.PieceWeightUA(VillagePiecesUA.Field1.class, 3, MathHelper.nextInt(random, 1 + size, 4 + size)));
	      list.add(new VillagePiecesUA.PieceWeightUA(VillagePiecesUA.Field2.class, 3, MathHelper.nextInt(random, 2 + size, 4 + size * 2)));
	      list.add(new VillagePiecesUA.PieceWeightUA(VillagePiecesUA.Blacksmith.class, 15, MathHelper.nextInt(random, 0, 1 + size)));
	      list.add(new VillagePiecesUA.PieceWeightUA(VillagePiecesUA.LargeHouse.class, 8, MathHelper.nextInt(random, 0 + size, 3 + size * 2)));
	      Iterator<PieceWeightUA> iterator = list.iterator();

	      while(iterator.hasNext()) {
	         if ((iterator.next()).villagePiecesLimit == 0) {
	            iterator.remove();
	         }
	      }

	      return list;
	   }

	   private static int updatePieceWeight(List<VillagePiecesUA.PieceWeightUA> p_75079_0_) {
	      boolean flag = false;
	      int i = 0;

	      for(VillagePiecesUA.PieceWeightUA villagepieces$pieceweight : p_75079_0_) {
	         if (villagepieces$pieceweight.villagePiecesLimit > 0 && villagepieces$pieceweight.villagePiecesSpawned < villagepieces$pieceweight.villagePiecesLimit) {
	            flag = true;
	         }

	         i += villagepieces$pieceweight.villagePieceWeight;
	      }

	      return flag ? i : -1;
	   }

	   private static VillagePiecesUA.Village findAndCreateComponentFactory(VillagePiecesUA.Start start, VillagePiecesUA.PieceWeightUA weight, List<StructurePiece> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, Direction facing, int componentType) {
	      Class<? extends VillagePiecesUA.Village> oclass = weight.villagePieceClass;
	      VillagePiecesUA.Village villagepieces$village = null;
	      if (oclass == VillagePiecesUA.FlatTopTinyHouse.class) {
	         villagepieces$village = VillagePiecesUA.FlatTopTinyHouse.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
	      } else if (oclass == VillagePiecesUA.Church.class) {
	         villagepieces$village = VillagePiecesUA.Church.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
	      } else if (oclass == VillagePiecesUA.LibraryHouse.class) {
	         villagepieces$village = VillagePiecesUA.LibraryHouse.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
	      } else if (oclass == VillagePiecesUA.TinyHouse.class) {
	         villagepieces$village = VillagePiecesUA.TinyHouse.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
	      } else if (oclass == VillagePiecesUA.WorkHouse.class) {
	         villagepieces$village = VillagePiecesUA.WorkHouse.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
	      } else if (oclass == VillagePiecesUA.Field1.class) {
	         villagepieces$village = VillagePiecesUA.Field1.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
	      } else if (oclass == VillagePiecesUA.Field2.class) {
	         villagepieces$village = VillagePiecesUA.Field2.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
	      } else if (oclass == VillagePiecesUA.Blacksmith.class) {
	         villagepieces$village = VillagePiecesUA.Blacksmith.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
	      } else if (oclass == VillagePiecesUA.LargeHouse.class) {
	         villagepieces$village = VillagePiecesUA.LargeHouse.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
	      }

	      return villagepieces$village;
	   }

	   private static VillagePiecesUA.Village generateComponent(VillagePiecesUA.Start start, List<StructurePiece> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, Direction facing, int componentType) {
	      int i = updatePieceWeight(start.structureVillageWeightedPieceList);
	      if (i <= 0) {
	         return null;
	      } else {
	         int j = 0;

	         while(j < 5) {
	            ++j;
	            int k = rand.nextInt(i);

	            for(VillagePiecesUA.PieceWeightUA villagepieces$pieceweight : start.structureVillageWeightedPieceList) {
	               k -= villagepieces$pieceweight.villagePieceWeight;
	               if (k < 0) {
	                  if (!villagepieces$pieceweight.canSpawnMoreVillagePiecesOfType(componentType) || villagepieces$pieceweight == start.lastPlaced && start.structureVillageWeightedPieceList.size() > 1) {
	                     break;
	                  }

	                  VillagePiecesUA.Village villagepieces$village = findAndCreateComponentFactory(start, villagepieces$pieceweight, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
	                  if (villagepieces$village != null) {
	                     ++villagepieces$pieceweight.villagePiecesSpawned;
	                     start.lastPlaced = villagepieces$pieceweight;
	                     if (!villagepieces$pieceweight.canSpawnMoreVillagePieces()) {
	                        start.structureVillageWeightedPieceList.remove(villagepieces$pieceweight);
	                     }

	                     return villagepieces$village;
	                  }
	               }
	            }
	         }

	         MutableBoundingBox mutableboundingbox = VillagePiecesUA.Torch.findPieceBox(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing);
	         if (mutableboundingbox != null) {
	            return new VillagePiecesUA.Torch(start, componentType, rand, mutableboundingbox, facing);
	         } else {
	            return null;
	         }
	      }
	   }

	   private static StructurePiece generateAndAddComponent(VillagePiecesUA.Start start, List<StructurePiece> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, Direction facing, int componentType) {
	      if (componentType > 50) {
	         return null;
	      } else if (Math.abs(structureMinX - start.getBoundingBox().minX) <= 112 && Math.abs(structureMinZ - start.getBoundingBox().minZ) <= 112) {
	         StructurePiece structurepiece = generateComponent(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType + 1);
	         if (structurepiece != null) {
	            structureComponents.add(structurepiece);
	            start.pendingHouses.add(structurepiece);
	            return structurepiece;
	         } else {
	            return null;
	         }
	      } else {
	         return null;
	      }
	   }

	   private static StructurePiece generateAndAddRoadPiece(VillagePiecesUA.Start start, List<StructurePiece> p_176069_1_, Random rand, int p_176069_3_, int p_176069_4_, int p_176069_5_, Direction facing, int p_176069_7_) {
	      if (p_176069_7_ > 3 + start.terrainType) {
	         return null;
	      } else if (Math.abs(p_176069_3_ - start.getBoundingBox().minX) <= 112 && Math.abs(p_176069_5_ - start.getBoundingBox().minZ) <= 112) {
	         MutableBoundingBox mutableboundingbox = VillagePiecesUA.Path.findPieceBox(start, p_176069_1_, rand, p_176069_3_, p_176069_4_, p_176069_5_, facing);
	         if (mutableboundingbox != null && mutableboundingbox.minY > 10) {
	            StructurePiece structurepiece = new VillagePiecesUA.Path(start, p_176069_7_, rand, mutableboundingbox, facing);
	            p_176069_1_.add(structurepiece);
	            start.pendingRoads.add(structurepiece);
	            return structurepiece;
	         } else {
	            return null;
	         }
	      } else {
	         return null;
	      }
	   }

	   public static class Church extends VillagePiecesUA.Village {
		   
	      protected Church(VillagePiecesUA.Start start, int type, Random rand, MutableBoundingBox boundingBoxIn, Direction facing) {
	         super(start, StructureInit.VISTUA, type);
	         this.setCoordBaseMode(facing);
	         this.boundingBox = boundingBoxIn;
	      }

	      public Church(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VISTUA, p_i50111_2_);
	      }


	      public static VillagePiecesUA.Church createPiece(VillagePiecesUA.Start start, List<StructurePiece> p_175854_1_, Random rand, int p_175854_3_, int p_175854_4_, int p_175854_5_, Direction facing, int p_175854_7_) {
	         MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175854_3_, p_175854_4_, p_175854_5_, 0, 0, 0, 5, 12, 9, facing);
	         return canVillageGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175854_1_, mutableboundingbox) == null ? new VillagePiecesUA.Church(start, p_175854_7_, rand, mutableboundingbox, facing) : null;
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 12 - 1, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.MOSSY_STONE_BRICKS.getDefaultState());
	         BlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH));
	         BlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST));
	         BlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST));
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 3, 0, 8, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 3, 10, 0, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 10, 3, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 10, 3, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 0, 4, 7, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 4, 4, 4, 7, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 8, 3, 4, 8, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 4, 3, 10, 4, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 5, 3, 5, 7, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, iblockstate, iblockstate, false);
	         this.setBlockState(worldIn, iblockstate, 0, 11, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 4, 11, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 2, 11, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 2, 11, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 1, 1, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 1, 1, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 2, 1, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 3, 1, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 3, 1, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 1, 1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 2, 1, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 3, 1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.BREWING_STAND.getDefaultState(), 2, 1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate2, 1, 2, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 3, 2, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 3, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 4, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 4, 3, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 6, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 7, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 4, 6, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 4, 7, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 2, 6, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 2, 7, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 2, 6, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 2, 7, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 3, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 4, 3, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 2, 3, 8, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.SOUTH, 2, 4, 7, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.EAST, 1, 4, 6, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.WEST, 3, 4, 6, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.NORTH, 2, 4, 5, structureBoundingBoxIn);
	         BlockState iblockstate4 = Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.WEST);

	       //following code is a modified version from JavaMan7's youtube tutorial about making structures (But went into detail about how to put potions in brewing stands)
	         TileEntity tileentity = worldIn.getTileEntity(new BlockPos(this.getXWithOffset(2, 5), this.getYWithOffset(1), this.getZWithOffset(2, 5)));
	         
	         if (tileentity instanceof BrewingStandTileEntity)
	         {
	            	 int potionSlot = 1 + randomIn.nextInt(3);
	            	 
	            	 for (int j = 0; j < potionSlot; j++) {
	            		 
	            		 int potionType = randomIn.nextInt(9);
	            		
	            		 ItemStack potion=null;
			           	
	            		 //6/9 chance
	            		 if(potionType < 6) {
	            			 potion = new ItemStack(Items.POTION);
	            			 PotionUtils.addPotionToItemStack(potion, Potions.STRONG_LEAPING);
	            	 	 }
	            		 //1/9 chance
	            		 else if(potionType < 7) {
	            			 potion = new ItemStack(Items.POTION);
	            			 PotionUtils.addPotionToItemStack(potion, Potions.STRONG_POISON); 
	            		 }
	            		 //2/9 chance
	            		 else{
	            			 potion = new ItemStack(Items.POTION);
	            			 PotionUtils.addPotionToItemStack(potion, Potions.LONG_NIGHT_VISION);
			           	 }
	            	 
		               	 ((BrewingStandTileEntity)tileentity).setInventorySlotContents(j,potion);
		             }
	         }
	         
	         
	         for(int i = 1; i <= 9; ++i) {
	            this.setBlockState(worldIn, iblockstate4, 3, i, 3, structureBoundingBoxIn);
	         }

	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
	         this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, Direction.NORTH);
	         if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR ) {
	            this.setBlockState(worldIn, iblockstate1, 2, 0, -1, structureBoundingBoxIn);
	            if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH) {
	               this.setBlockState(worldIn, Blocks.GRASS_BLOCK.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
	            }
	         }

	         for(int k = 0; k < 9; ++k) {
	            for(int j = 0; j < 5; ++j) {
	               this.clearCurrentPositionBlocksUpwards(worldIn, j, 12, k, structureBoundingBoxIn);
	               this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j, -1, k, structureBoundingBoxIn);
	            }
	         }

	         this.spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1, randomIn);
	         return true;
	      }

	      protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession) {
	         return 2;
	      }
	   }

	   public static class Field1 extends VillagePiecesUA.Village {
	      /** First crop type for this field. */
	      private BlockState cropTypeA;
	      /** Second crop type for this field. */
	      private BlockState cropTypeB;
	      /** Third crop type for this field. */
	      private BlockState cropTypeC;
	      /** Fourth crop type for this field. */
	      private BlockState cropTypeD;

	      
	      protected Field1(VillagePiecesUA.Start start, int type, Random rand, MutableBoundingBox boundingBoxIn, Direction facing) {
	         super(start, StructureInit.VIDFUA, type);
	         this.setCoordBaseMode(facing);
	         this.boundingBox = boundingBoxIn;
	         cropTypeA = randomCrop(rand);
	         cropTypeB = randomCrop(rand);
	         cropTypeC = randomCrop(rand);
	         cropTypeD = randomCrop(rand);
	      }

	      public Field1(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VIDFUA, p_i50111_2_);
	         this.cropTypeA = NBTUtil.readBlockState(p_i50111_2_.getCompound("cropTypeA"));
	         this.cropTypeB = NBTUtil.readBlockState(p_i50111_2_.getCompound("cropTypeB"));
	         this.cropTypeC = NBTUtil.readBlockState(p_i50111_2_.getCompound("cropTypeC"));
	         this.cropTypeD = NBTUtil.readBlockState(p_i50111_2_.getCompound("cropTypeD"));
	      }

	      /**
	       * (abstract) Helper method to read subclass data from NBT
	       */
	      protected void readAdditional(CompoundNBT tagCompound) {
		      super.readAdditional(tagCompound);
	    	  tagCompound.put("cropTypeA", NBTUtil.writeBlockState(cropTypeA));
	    	  tagCompound.put("cropTypeB", NBTUtil.writeBlockState(cropTypeB));
	    	  tagCompound.put("cropTypeC", NBTUtil.writeBlockState(cropTypeC));
	    	  tagCompound.put("cropTypeD", NBTUtil.writeBlockState(cropTypeD));
	      }
	      
	      private static BlockState randomCrop(Random rand) {
		         switch(rand.nextInt(10)) {
		         case 0:
		         case 1:
		            return Blocks.CARROTS.getDefaultState();
		         case 2:
		         case 3:
		            return Blocks.POTATOES.getDefaultState();
		         case 4:
		            return Blocks.BEETROOTS.getDefaultState();
		         default:
		            return Blocks.WHEAT.getDefaultState();
		         }
		      }
	      
	      public static VillagePiecesUA.Field1 createPiece(VillagePiecesUA.Start start, List<StructurePiece> p_175851_1_, Random rand, int p_175851_3_, int p_175851_4_, int p_175851_5_, Direction facing, int p_175851_7_) {
	         MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 13, 4, 9, facing);
	         return canVillageGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175851_1_, mutableboundingbox) == null ? new VillagePiecesUA.Field1(start, p_175851_7_, rand, mutableboundingbox, facing) : null;
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.OAK_LOG.getDefaultState());
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 1, 8, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 1, 11, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 0, 0, 12, 0, 8, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 11, 0, 0, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 11, 0, 8, iblockstate, iblockstate, false);
	         this.setBlockState(worldIn, Blocks.COMPOSTER.getDefaultState(), 6, 1, 0, structureBoundingBoxIn);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 1, 9, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);

	         for(int i = 1; i <= 7; ++i) {
	            CropsBlock CropsBlock = (CropsBlock)this.cropTypeA.getBlock();
	            int j = CropsBlock.getMaxAge();
	            int k = j / 3;
	            this.setBlockState(worldIn, this.cropTypeA.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, k, j))), 1, 1, i, structureBoundingBoxIn);
	            this.setBlockState(worldIn, this.cropTypeA.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, k, j))), 2, 1, i, structureBoundingBoxIn);
	            CropsBlock = (CropsBlock)this.cropTypeB.getBlock();
	            int l = CropsBlock.getMaxAge();
	            int i1 = l / 3;
	            this.setBlockState(worldIn, this.cropTypeB.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, i1, l))), 4, 1, i, structureBoundingBoxIn);
	            this.setBlockState(worldIn, this.cropTypeB.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, i1, l))), 5, 1, i, structureBoundingBoxIn);
	            CropsBlock = (CropsBlock)this.cropTypeC.getBlock();
	            int j1 = CropsBlock.getMaxAge();
	            int k1 = j1 / 3;
	            this.setBlockState(worldIn, this.cropTypeC.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, k1, j1))), 7, 1, i, structureBoundingBoxIn);
	            this.setBlockState(worldIn, this.cropTypeC.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, k1, j1))), 8, 1, i, structureBoundingBoxIn);
	            CropsBlock = (CropsBlock)this.cropTypeD.getBlock();
	            int l1 = CropsBlock.getMaxAge();
	            int i2 = l1 / 3;
	            this.setBlockState(worldIn, this.cropTypeD.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, i2, l1))), 10, 1, i, structureBoundingBoxIn);
	            this.setBlockState(worldIn, this.cropTypeD.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, i2, l1))), 11, 1, i, structureBoundingBoxIn);
	         }

	         for(int j2 = 0; j2 < 9; ++j2) {
	            for(int k2 = 0; k2 < 13; ++k2) {
	               this.clearCurrentPositionBlocksUpwards(worldIn, k2, 4, j2, structureBoundingBoxIn);
	               this.replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), k2, -1, j2, structureBoundingBoxIn);
	            }
	         }

	         return true;
	      }
	   }

	   public static class Field2 extends VillagePiecesUA.Village {
	      /** First crop type for this field. */
	      private BlockState cropTypeA;
	      /** Second crop type for this field. */
	      private BlockState cropTypeB;

	      protected Field2(VillagePiecesUA.Start start, int type, Random rand, MutableBoundingBox boundingBoxIn, Direction facing) {
	         super(start, StructureInit.VIFUA, type);
	         this.setCoordBaseMode(facing);
	         this.boundingBox = boundingBoxIn;
	         cropTypeA = randomCrop(rand);
	         cropTypeB = randomCrop(rand);
	      }

	      public Field2(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VIFUA, p_i50111_2_);
	         this.cropTypeA = NBTUtil.readBlockState(p_i50111_2_.getCompound("cropTypeA"));
	         this.cropTypeB = NBTUtil.readBlockState(p_i50111_2_.getCompound("cropTypeB"));
	      }

	      /**
	       * (abstract) Helper method to read subclass data from NBT
	       */
	      protected void readAdditional(CompoundNBT tagCompound) {
			  super.readAdditional(tagCompound);
	    	  tagCompound.put("cropTypeA", NBTUtil.writeBlockState(cropTypeA));
	    	  tagCompound.put("cropTypeB", NBTUtil.writeBlockState(cropTypeB));
	      }
	      
	      private static BlockState randomCrop(Random rand) {
	         switch(rand.nextInt(10)) {
	         case 0:
	         case 1:
	            return Blocks.CARROTS.getDefaultState();
	         case 2:
	         case 3:
	            return Blocks.POTATOES.getDefaultState();
	         case 4:
	            return Blocks.BEETROOTS.getDefaultState();
	         default:
	            return Blocks.WHEAT.getDefaultState();
	         }
	      }

	      public static VillagePiecesUA.Field2 createPiece(VillagePiecesUA.Start start, List<StructurePiece> p_175852_1_, Random rand, int p_175852_3_, int p_175852_4_, int p_175852_5_, Direction facing, int p_175852_7_) {
	         MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175852_3_, p_175852_4_, p_175852_5_, 0, 0, 0, 7, 4, 9, facing);
	         return canVillageGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175852_1_, mutableboundingbox) == null ? new VillagePiecesUA.Field2(start, p_175852_7_, rand, mutableboundingbox, facing) : null;
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.OAK_LOG.getDefaultState());
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 6, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 0, 0, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 5, 0, 8, iblockstate, iblockstate, false);
	         this.setBlockState(worldIn, Blocks.COMPOSTER.getDefaultState(), 0, 1, 8, structureBoundingBoxIn);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);

	         for(int i = 1; i <= 7; ++i) {
	            CropsBlock CropsBlock = (CropsBlock)this.cropTypeA.getBlock();
	            int j = CropsBlock.getMaxAge();
	            int k = j / 3;
	            this.setBlockState(worldIn, this.cropTypeA.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, k, j))), 1, 1, i, structureBoundingBoxIn);
	            this.setBlockState(worldIn, this.cropTypeA.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, k, j))), 2, 1, i, structureBoundingBoxIn);
	            CropsBlock = (CropsBlock)this.cropTypeB.getBlock();
	            int l = CropsBlock.getMaxAge();
	            int i1 = l / 3;
	            this.setBlockState(worldIn, this.cropTypeB.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, i1, l))), 4, 1, i, structureBoundingBoxIn);
	            this.setBlockState(worldIn, this.cropTypeB.with(CropsBlock.getAgeProperty(), Integer.valueOf(MathHelper.nextInt(randomIn, i1, l))), 5, 1, i, structureBoundingBoxIn);
	         }

	         for(int j1 = 0; j1 < 9; ++j1) {
	            for(int k1 = 0; k1 < 7; ++k1) {
	               this.clearCurrentPositionBlocksUpwards(worldIn, k1, 4, j1, structureBoundingBoxIn);
	               this.replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), k1, -1, j1, structureBoundingBoxIn);
	            }
	         }

	         return true;
	      }
	   }

	   public static class WorkHouse extends VillagePiecesUA.Village {
		   protected WorkHouse(VillagePiecesUA.Start start, int type, Random rand, MutableBoundingBox boundingBoxIn, Direction facing) {
	         super(start, StructureInit.VIPHUA, type);
	         this.setCoordBaseMode(facing);
	         this.boundingBox = boundingBoxIn;
	      }

	      public WorkHouse(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VIPHUA, p_i50111_2_);
	      }

		      

	      public static VillagePiecesUA.WorkHouse createPiece(VillagePiecesUA.Start start, List<StructurePiece> p_175857_1_, Random rand, int p_175857_3_, int p_175857_4_, int p_175857_5_, Direction facing, int p_175857_7_) {
	         MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175857_3_, p_175857_4_, p_175857_5_, 0, 0, 0, 9, 7, 11, facing);
	         return canVillageGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175857_1_, mutableboundingbox) == null ? new VillagePiecesUA.WorkHouse(start, p_175857_7_, rand, mutableboundingbox, facing) : null;
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 7 - 1, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
	         BlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH));
	         BlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH));
	         BlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST));
	         BlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.OAK_PLANKS.getDefaultState());
	         BlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.OAK_LOG.getDefaultState());
	         BlockState iblockstate6 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 8, 0, 10, Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), false);
	         this.setBlockState(worldIn, iblockstate, 6, 0, 6, structureBoundingBoxIn);
	         BlockState iblockstate7 = iblockstate6;
	         BlockState iblockstate8 = iblockstate6;
	         if(iblockstate.has(FenceBlock.SOUTH)) {
		          iblockstate7 = iblockstate6.with(FenceBlock.NORTH, Boolean.valueOf(true)).with(FenceBlock.SOUTH, Boolean.valueOf(true));
		          iblockstate8 = iblockstate6.with(FenceBlock.WEST, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true));
		          this.setBlockState(worldIn, iblockstate6.with(FenceBlock.SOUTH, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true)), 2, 1, 10, structureBoundingBoxIn);
		          this.setBlockState(worldIn, iblockstate6.with(FenceBlock.SOUTH, Boolean.valueOf(true)).with(FenceBlock.WEST, Boolean.valueOf(true)), 8, 1, 10, structureBoundingBoxIn);
	         }
	         else {
	        	 this.setBlockState(worldIn, iblockstate6, 2, 1, 10, structureBoundingBoxIn);
		         this.setBlockState(worldIn, iblockstate6, 8, 1, 10, structureBoundingBoxIn);
	         }
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 1, 9, iblockstate7, iblockstate7, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 6, 8, 1, 9, iblockstate7, iblockstate7, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 10, 7, 1, 10, iblockstate8, iblockstate8, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, iblockstate4, iblockstate4, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 1, 0, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 7, 1, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, iblockstate4, iblockstate4, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 3, 5, iblockstate4, iblockstate4, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, iblockstate4, iblockstate4, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 8, 4, 4, iblockstate4, iblockstate4, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, iblockstate4, iblockstate4, false);
	         this.setBlockState(worldIn, iblockstate4, 0, 4, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate4, 0, 4, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate4, 8, 4, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate4, 8, 4, 3, structureBoundingBoxIn);
	         BlockState iblockstate9 = iblockstate1;
	         BlockState iblockstate10 = iblockstate2;

	         for(int i = -1; i <= 2; ++i) {
	            for(int j = 0; j <= 8; ++j) {
	               this.setBlockState(worldIn, iblockstate9, j, 4 + i, i, structureBoundingBoxIn);
	               this.setBlockState(worldIn, iblockstate10, j, 4 + i, 5 - i, structureBoundingBoxIn);
	            }
	         }

	         this.setBlockState(worldIn, iblockstate5, 0, 2, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 0, 2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 8, 2, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 8, 2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 8, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 8, 2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 2, 2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 3, 2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 5, 2, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 2, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, getBiomeSpecificBlockState(Blocks.OAK_PRESSURE_PLATE.getDefaultState()), 2, 2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.LOOM.getDefaultState(), 3, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate4, 1, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate9, 2, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 1, 1, 3, structureBoundingBoxIn);
	         BlockState iblockstate11 = Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 1, 7, 0, 3, iblockstate11, iblockstate11, false);
	         this.setBlockState(worldIn, Blocks.SMOKER.getDefaultState(), 6, 1, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.SMOKER.getDefaultState(), 6, 1, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, 3), 6, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.NORTH, 2, 3, 1, structureBoundingBoxIn);
	         this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, Direction.NORTH);
	         if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
	            this.setBlockState(worldIn, iblockstate9, 2, 0, -1, structureBoundingBoxIn);
	            if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH) {
	               this.setBlockState(worldIn, Blocks.GRASS_BLOCK.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
	            }
	         }

	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.SOUTH, 6, 3, 4, structureBoundingBoxIn);
	         this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 6, 1, 5, Direction.SOUTH);

	         for(int l = 0; l < 5; ++l) {
	            for(int k = 0; k < 9; ++k) {
	               this.clearCurrentPositionBlocksUpwards(worldIn, k, 7, l, structureBoundingBoxIn);
	               this.replaceAirAndLiquidDownwards(worldIn, iblockstate, k, -1, l, structureBoundingBoxIn);
	            }
	         }

	         this.spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2, randomIn);
	         return true;
	      }
	   }

	   public static class LibraryHouse extends VillagePiecesUA.Village {
		   protected LibraryHouse(VillagePiecesUA.Start start, int type, Random rand, MutableBoundingBox boundingBoxIn, Direction facing) {
	         super(start, StructureInit.VIBHUA, type);
	         this.setCoordBaseMode(facing);
	         this.boundingBox = boundingBoxIn;
	      }

	      public LibraryHouse(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VIBHUA, p_i50111_2_);
	      }

	      public static VillagePiecesUA.LibraryHouse createPiece(VillagePiecesUA.Start start, List<StructurePiece> p_175850_1_, Random rand, int p_175850_3_, int p_175850_4_, int p_175850_5_, Direction facing, int p_175850_7_) {
	         MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175850_3_, p_175850_4_, p_175850_5_, 0, 0, 0, 9, 9, 6, facing);
	         return canVillageGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175850_1_, mutableboundingbox) == null ? new VillagePiecesUA.LibraryHouse(start, p_175850_7_, rand, mutableboundingbox, facing) : null;
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 9 - 1, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
	         BlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH));
	         BlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH));
	         BlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST));
	         BlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.OAK_PLANKS.getDefaultState());
	         BlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH));
	         BlockState iblockstate6 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, iblockstate, iblockstate, false);

	         for(int i = -1; i <= 2; ++i) {
	            for(int j = 0; j <= 8; ++j) {
	               this.setBlockState(worldIn, iblockstate1, j, 6 + i, i, structureBoundingBoxIn);
	               this.setBlockState(worldIn, iblockstate2, j, 6 + i, 5 - i, structureBoundingBoxIn);
	            }
	         }

	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, iblockstate4, iblockstate4, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, iblockstate4, iblockstate4, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, iblockstate4, iblockstate4, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, iblockstate4, iblockstate4, false);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 4, 2, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 5, 2, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 6, 2, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 4, 3, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 5, 3, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 6, 3, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 3, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 3, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 8, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 8, 2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 8, 3, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 8, 3, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 2, 2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 3, 2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 5, 2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 6, 2, 5, structureBoundingBoxIn);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, iblockstate4, iblockstate4, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, iblockstate4, iblockstate4, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
	         this.setBlockState(worldIn, iblockstate4, 7, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 7, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 6, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 5, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 4, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 3, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 6, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, getBiomeSpecificBlockState(Blocks.OAK_PRESSURE_PLATE.getDefaultState()), 6, 2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 4, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, getBiomeSpecificBlockState(Blocks.OAK_PRESSURE_PLATE.getDefaultState()), 4, 2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.CARTOGRAPHY_TABLE.getDefaultState(), 7, 1, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, getBiomeSpecificBlockState(Blocks.TORCH.getDefaultState()), 7, 2, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.CARTOGRAPHY_TABLE.getDefaultState(), 1, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, getBiomeSpecificBlockState(Blocks.TORCH.getDefaultState()), 1, 2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.BOOKSHELF.getDefaultState(), 2, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.LECTERN.getDefaultState(), 5, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.LECTERN.getDefaultState(), 6, 1, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
	         this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, Direction.NORTH);
	         if (this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
	            this.setBlockState(worldIn, iblockstate5, 1, 0, -1, structureBoundingBoxIn);
	            if (this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH) {
	               this.setBlockState(worldIn, Blocks.GRASS_BLOCK.getDefaultState(), 1, -1, -1, structureBoundingBoxIn);
	            }
	         }

	         for(int l = 0; l < 6; ++l) {
	            for(int k = 0; k < 9; ++k) {
	               this.clearCurrentPositionBlocksUpwards(worldIn, k, 9, l, structureBoundingBoxIn);
	               this.replaceAirAndLiquidDownwards(worldIn, iblockstate, k, -1, l, structureBoundingBoxIn);
	            }
	         }

	         this.spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1, randomIn);
	         return true;
	      }
	   }

	   public static class Blacksmith extends VillagePiecesUA.Village {
	      private boolean hasMadeChest;

	      protected Blacksmith(VillagePiecesUA.Start start, int type, Random rand, MutableBoundingBox boundingBoxIn, Direction facing) {
	         super(start, StructureInit.VISUA, type);
	         this.setCoordBaseMode(facing);
	         this.boundingBox = boundingBoxIn;
	      }

	      public Blacksmith(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VISUA, p_i50111_2_);
	         this.hasMadeChest = p_i50111_2_.getBoolean("hasMadeChest");
	      }

	      /**
	       * (abstract) Helper method to read subclass data from NBT
	       */
	      protected void readAdditional(CompoundNBT tagCompound) {
	    	 super.readAdditional(tagCompound);
	         tagCompound.putBoolean("hasMadeChest", this.hasMadeChest);
	      }
	      
	      public static VillagePiecesUA.Blacksmith createPiece(VillagePiecesUA.Start start, List<StructurePiece> p_175855_1_, Random rand, int p_175855_3_, int p_175855_4_, int p_175855_5_, Direction facing, int p_175855_7_) {
	         MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175855_3_, p_175855_4_, p_175855_5_, 0, 0, 0, 10, 6, 7, facing);
	         return canVillageGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175855_1_, mutableboundingbox) == null ? new VillagePiecesUA.Blacksmith(start, p_175855_7_, rand, mutableboundingbox, facing) : null;
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
	         BlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH));
	         BlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST));
	         BlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.OAK_PLANKS.getDefaultState());
	         BlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH));
	         BlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.OAK_LOG.getDefaultState());
	         BlockState iblockstate6 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 9, 4, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 0, 6, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 9, 4, 6, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 9, 5, 6, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 8, 5, 5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, iblockstate3, iblockstate3, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 4, 0, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 4, 0, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 4, 6, iblockstate5, iblockstate5, false);
	         this.setBlockState(worldIn, iblockstate3, 3, 3, 1, structureBoundingBoxIn);
	         this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 3, 1, 1, Direction.SOUTH);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 3, 2, iblockstate3, iblockstate3, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 5, 3, 3, iblockstate3, iblockstate3, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 5, iblockstate3, iblockstate3, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 6, 5, 3, 6, iblockstate3, iblockstate3, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 5, 3, 0, iblockstate6, iblockstate6, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 0, 9, 3, 0, iblockstate6, iblockstate6, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 9, 4, 6, iblockstate, iblockstate, false);
	         this.setBlockState(worldIn, this.getBiomeSpecificBlockState(Blocks.LAVA.getDefaultState()), 7, 1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, this.getBiomeSpecificBlockState(Blocks.LAVA.getDefaultState()), 8, 1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, Boolean.valueOf(true)).with(PaneBlock.SOUTH, Boolean.valueOf(true)), 9, 2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, Boolean.valueOf(true)), 9, 2, 4, structureBoundingBoxIn);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 4, 8, 2, 5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.setBlockState(worldIn, iblockstate, 6, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.BLAST_FURNACE.getDefaultState().with(FurnaceBlock.FACING, Direction.SOUTH), 6, 2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.BLAST_FURNACE.getDefaultState().with(FurnaceBlock.FACING, Direction.SOUTH), 6, 3, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.SMITHING_TABLE.getDefaultState(), 8, 1, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.STONECUTTER.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.ANVIL.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 2, 2, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 4, 2, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 2, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, getBiomeSpecificBlockState(Blocks.OAK_PRESSURE_PLATE.getDefaultState()), 2, 2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GRINDSTONE.getDefaultState(), 5, 1, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 5, 1, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 1, 1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 2, 1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate2, 1, 1, 4, structureBoundingBoxIn);
	         if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5)))) {
	            this.hasMadeChest = true;
	            this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 5, 1, 5, LootTables.CHESTS_VILLAGE_VILLAGE_WEAPONSMITH);
	         }

	         for(int i = 6; i <= 8; ++i) {
	            if (this.getBlockStateFromPos(worldIn, i, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, i, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
	               this.setBlockState(worldIn, iblockstate4, i, 0, -1, structureBoundingBoxIn);
	               if (this.getBlockStateFromPos(worldIn, i, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH) {
	                  this.setBlockState(worldIn, Blocks.GRASS_BLOCK.getDefaultState(), i, -1, -1, structureBoundingBoxIn);
	               }
	            }
	         }

	         for(int k = 0; k < 7; ++k) {
	            for(int j = 0; j < 10; ++j) {
	               this.clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
	               this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j, -1, k, structureBoundingBoxIn);
	            }
	         }

	         this.spawnVillagers(worldIn, structureBoundingBoxIn, 7, 1, 1, 1, randomIn);
	         return true;
	      }

	      protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession) {
	         return 3;
	      }
	   }

	   public static class LargeHouse extends VillagePiecesUA.Village {
	      private boolean hasMadeChest;

	      protected LargeHouse(VillagePiecesUA.Start start, int p_i45573_1_, Random p_i45573_2_, MutableBoundingBox boundingBoxIn, Direction facing) {
	         super(start, StructureInit.VITRHUA, p_i45573_1_);;
	         this.setCoordBaseMode(facing);
	         this.boundingBox = boundingBoxIn;
	      }

	      public LargeHouse(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VITRHUA, p_i50111_2_);
	         this.hasMadeChest = p_i50111_2_.getBoolean("hasMadeChest");
	      }

	      /**
	       * (abstract) Helper method to read subclass data from NBT
	       */
	      protected void readAdditional(CompoundNBT tagCompound) {
		     super.readAdditional(tagCompound);
	         tagCompound.putBoolean("hasMadeChest", this.hasMadeChest);
	      }


	      public static VillagePiecesUA.LargeHouse createPiece(VillagePiecesUA.Start start, List<StructurePiece> p_175849_1_, Random rand, int p_175849_3_, int p_175849_4_, int p_175849_5_, Direction facing, int p_175849_7_) {
	         MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175849_3_, p_175849_4_, p_175849_5_, 0, 0, 0, 9, 7, 12, facing);
	         return canVillageGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175849_1_, mutableboundingbox) == null ? new VillagePiecesUA.LargeHouse(start, p_175849_7_, rand, mutableboundingbox, facing) : null;
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 7 - 1, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
	         BlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH));
	         BlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH));
	         BlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST));
	         BlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST));
	         BlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.OAK_PLANKS.getDefaultState());
	         BlockState iblockstate6 = this.getBiomeSpecificBlockState(Blocks.OAK_LOG.getDefaultState());
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, iblockstate5, iblockstate5, false);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD).with(HorizontalBlock.HORIZONTAL_FACING, Direction.WEST), 3, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD).with(HorizontalBlock.HORIZONTAL_FACING, Direction.WEST), 3, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD).with(HorizontalBlock.HORIZONTAL_FACING, Direction.WEST), 3, 1, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD).with(HorizontalBlock.HORIZONTAL_FACING, Direction.WEST), 3, 1, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT).with(HorizontalBlock.HORIZONTAL_FACING, Direction.WEST), 4, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT).with(HorizontalBlock.HORIZONTAL_FACING, Direction.WEST), 4, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT).with(HorizontalBlock.HORIZONTAL_FACING, Direction.WEST), 4, 1, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT).with(HorizontalBlock.HORIZONTAL_FACING, Direction.WEST), 4, 1, 8, structureBoundingBoxIn);

	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD).with(HorizontalBlock.HORIZONTAL_FACING, Direction.EAST), 7, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD).with(HorizontalBlock.HORIZONTAL_FACING, Direction.EAST), 7, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD).with(HorizontalBlock.HORIZONTAL_FACING, Direction.EAST), 7, 1, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD).with(HorizontalBlock.HORIZONTAL_FACING, Direction.EAST), 7, 1, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT).with(HorizontalBlock.HORIZONTAL_FACING, Direction.EAST), 6, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT).with(HorizontalBlock.HORIZONTAL_FACING, Direction.EAST), 6, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT).with(HorizontalBlock.HORIZONTAL_FACING, Direction.EAST), 6, 1, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT).with(HorizontalBlock.HORIZONTAL_FACING, Direction.EAST), 6, 1, 8, structureBoundingBoxIn);
	         
	         this.setBlockState(worldIn, Blocks.FLETCHING_TABLE.getDefaultState(), 5, 1, 9, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.LOOM.getDefaultState(), 6, 1, 9, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, 2), 4, 1, 9, structureBoundingBoxIn);
	         if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(3, 8), this.getYWithOffset(1), this.getZWithOffset(3, 6)))) {
	            this.hasMadeChest = true;
	            this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 1, 6, LootTables.CHESTS_VILLAGE_VILLAGE_FLETCHER);
	         }
	         if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(7, 8), this.getYWithOffset(1), this.getZWithOffset(7, 6)))) {
	            this.hasMadeChest = true;
	            this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 7, 1, 6, LootTables.CHESTS_VILLAGE_VILLAGE_PLAINS_HOUSE);
	         }
	         this.setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 3, 1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 7, 1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, getBiomeSpecificBlockState(Blocks.TORCH.getDefaultState()), 3, 2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, getBiomeSpecificBlockState(Blocks.TORCH.getDefaultState()), 7, 2, 5, structureBoundingBoxIn);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, iblockstate5, iblockstate5, false);
	         this.setBlockState(worldIn, iblockstate5, 0, 4, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 0, 4, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 8, 4, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 8, 4, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 8, 4, 4, structureBoundingBoxIn);
	         BlockState iblockstate7 = iblockstate1;
	         BlockState iblockstate8 = iblockstate2;
	         BlockState iblockstate9 = iblockstate4;
	         BlockState iblockstate10 = iblockstate3;

	         for(int i = -1; i <= 2; ++i) {
	            for(int j = 0; j <= 8; ++j) {
	               this.setBlockState(worldIn, iblockstate7, j, 4 + i, i, structureBoundingBoxIn);
	               if ((i > -1 || j <= 1) && (i > 0 || j <= 3) && (i > 1 || j <= 4 || j >= 6)) {
	                  this.setBlockState(worldIn, iblockstate8, j, 4 + i, 5 - i, structureBoundingBoxIn);
	               }
	            }
	         }

	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, iblockstate5, iblockstate5, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, iblockstate5, iblockstate5, false);

	         for(int k = 4; k >= 1; --k) {
	            this.setBlockState(worldIn, iblockstate5, k, 2 + k, 7 - k, structureBoundingBoxIn);

	            for(int k1 = 8 - k; k1 <= 10; ++k1) {
	               this.setBlockState(worldIn, iblockstate10, k, 2 + k, k1, structureBoundingBoxIn);
	            }
	         }

	         this.setBlockState(worldIn, iblockstate5, 6, 6, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 7, 5, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate4, 6, 6, 4, structureBoundingBoxIn);

	         for(int l = 6; l <= 8; ++l) {
	            for(int l1 = 5; l1 <= 10; ++l1) {
	               this.setBlockState(worldIn, iblockstate9, l, 12 - l, l1, structureBoundingBoxIn);
	            }
	         }

	         this.setBlockState(worldIn, iblockstate6, 0, 2, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 0, 2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 4, 2, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 5, 2, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 6, 2, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 8, 2, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 8, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 8, 2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 8, 2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 8, 2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 8, 2, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 8, 2, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 8, 2, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 8, 2, 9, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 2, 2, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 2, 2, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 2, 2, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 2, 2, 9, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 4, 4, 10, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 5, 4, 10, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate6, 6, 4, 10, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 5, 5, 10, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.NORTH, 2, 3, 1, structureBoundingBoxIn);
	         this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, Direction.NORTH);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
	            this.setBlockState(worldIn, iblockstate7, 2, 0, -1, structureBoundingBoxIn);
	            if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH) {
	               this.setBlockState(worldIn, Blocks.GRASS_BLOCK.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
	            }
	         }

	         for(int i1 = 0; i1 < 5; ++i1) {
	            for(int i2 = 0; i2 < 9; ++i2) {
	               this.clearCurrentPositionBlocksUpwards(worldIn, i2, 7, i1, structureBoundingBoxIn);
	               this.replaceAirAndLiquidDownwards(worldIn, iblockstate, i2, -1, i1, structureBoundingBoxIn);
	            }
	         }

	         for(int j1 = 5; j1 < 11; ++j1) {
	            for(int j2 = 2; j2 < 9; ++j2) {
	               this.clearCurrentPositionBlocksUpwards(worldIn, j2, 7, j1, structureBoundingBoxIn);
	               this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j2, -1, j1, structureBoundingBoxIn);
	            }
	         }

	         this.spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2, randomIn);
	         return true;
	      }
	   }

	   public static class FlatTopTinyHouse extends VillagePiecesUA.Village {
	      private boolean isRoofAccessible;


	      protected FlatTopTinyHouse(VillagePiecesUA.Start start, int p_i45573_1_, Random rand, MutableBoundingBox boundingBoxIn, Direction p_i45573_4_) {
	         super(start, StructureInit.VISHUA, p_i45573_1_);
	         this.boundingBox = boundingBoxIn;
	         this.isRoofAccessible = rand.nextBoolean();
	      }

	      public FlatTopTinyHouse(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VISHUA, p_i50111_2_);
	         this.isRoofAccessible = p_i50111_2_.getBoolean("isRoofAccessible");
	      }

	      /**
	       * (abstract) Helper method to read subclass data from NBT
	       */
	      protected void readAdditional(CompoundNBT tagCompound) {
			 super.readAdditional(tagCompound);
	         tagCompound.putBoolean("isRoofAccessible", this.isRoofAccessible);
	      }

	      public static VillagePiecesUA.FlatTopTinyHouse createPiece(VillagePiecesUA.Start start, List<StructurePiece> p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, Direction facing, int p_175858_7_) {
	         MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, facing);
	         return StructurePiece.findIntersecting(p_175858_1_, mutableboundingbox) != null ? null : new VillagePiecesUA.FlatTopTinyHouse(start, p_175858_7_, rand, mutableboundingbox, facing);
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
	         BlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_PLANKS.getDefaultState());
	         BlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH));
	         BlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.OAK_LOG.getDefaultState());
	         BlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, iblockstate3, iblockstate3, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, iblockstate1, iblockstate1, false);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD), 1, 1, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT), 1, 1, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.FLETCHING_TABLE.getDefaultState(), 1, 1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 0, 1, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 0, 2, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 0, 3, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 4, 1, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 4, 2, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 4, 3, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 0, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 0, 2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 0, 3, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 4, 1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 4, 2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 4, 3, 4, structureBoundingBoxIn);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, iblockstate1, iblockstate1, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, iblockstate1, iblockstate1, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, iblockstate1, iblockstate1, false);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)), 2, 2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 4, 2, 2, structureBoundingBoxIn);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 1, 3, 0, iblockstate1, iblockstate1, false);
	         this.setBlockState(worldIn, iblockstate1, 2, 3, 0, structureBoundingBoxIn);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, iblockstate1, iblockstate1, false);
		     this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, Direction.NORTH);
	         if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
	            this.setBlockState(worldIn, iblockstate2, 2, 0, -1, structureBoundingBoxIn);
	            if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH) {
	               this.setBlockState(worldIn, Blocks.GRASS_BLOCK.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
	            }
	         }

	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         if (this.isRoofAccessible) {
	        	 
	            for(int k = 0; k <= 4; ++k) {
	               for(int l = 0; l <= 4; ++l) {
	                  boolean flag = k == 0 || k == 4;
	                  boolean flag1 = l == 0 || l == 4;
	                  if (flag || flag1) {
	                     boolean flag2 = k == 0 || k == 4;
	                     boolean flag3 = l == 0 || l == 4;
	                     
	                     //checks if block has face different direction
	                     if(iblockstate.has(FenceBlock.SOUTH)) {
	                    	BlockState iblockstate5 = iblockstate4.with(FenceBlock.SOUTH, Boolean.valueOf(flag2 && l != 0)).with(FenceBlock.NORTH, Boolean.valueOf(flag2 && l != 4)).with(FenceBlock.WEST, Boolean.valueOf(flag3 && k != 0)).with(FenceBlock.EAST, Boolean.valueOf(flag3 && k != 4));
	                     	this.setBlockState(worldIn, iblockstate5, k, 5, l, structureBoundingBoxIn);
	                     }
	                     else {
	                    	 this.setBlockState(worldIn, iblockstate4, k, 5, l, structureBoundingBoxIn);
	                     }
	                  }
	               }
	            }
	         }

	         if (this.isRoofAccessible) {
	            BlockState iblockstate6 = Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.SOUTH);
	            this.setBlockState(worldIn, iblockstate6, 3, 1, 3, structureBoundingBoxIn);
	            this.setBlockState(worldIn, iblockstate6, 3, 2, 3, structureBoundingBoxIn);
	            this.setBlockState(worldIn, iblockstate6, 3, 3, 3, structureBoundingBoxIn);
	            this.setBlockState(worldIn, iblockstate6, 3, 4, 3, structureBoundingBoxIn);
	         }

	         this.placeTorch(worldIn, Direction.NORTH, 2, 3, 1, structureBoundingBoxIn);

	         for(int i1 = 0; i1 < 5; ++i1) {
	            for(int j1 = 0; j1 < 5; ++j1) {
	               this.clearCurrentPositionBlocksUpwards(worldIn, j1, 6, i1, structureBoundingBoxIn);
	               this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j1, -1, i1, structureBoundingBoxIn);
	            }
	         }

	         this.spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1, randomIn);
	         return true;
	      }
	   }

	   public static class Path extends VillagePiecesUA.Village {
	      private int length;

	      protected Path(VillagePiecesUA.Start start, int p_i45573_1_, Random p_i45573_2_, MutableBoundingBox boundingBoxIn, Direction facing) {
	         super(start, StructureInit.VISRUA, p_i45573_1_);
	         this.setCoordBaseMode(facing);
	         this.boundingBox = boundingBoxIn;
	         this.length = Math.max(boundingBoxIn.getXSize(), boundingBoxIn.getZSize());
	      }

	      public Path(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VISRUA, p_i50111_2_);
	         this.length = p_i50111_2_.getInt("length");
	      }

	      /**
	       * (abstract) Helper method to read subclass data from NBT
	       */
	      protected void readAdditional(CompoundNBT tagCompound) {
			 super.readAdditional(tagCompound);
	         tagCompound.putInt("length", this.length);
	      }

	      /**
	       * Initiates construction of the Structure Component picked, at the current Location of StructGen
	       */
	      public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand) {
	         boolean flag = false;

	         for(int i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5)) {
	            StructurePiece structurepiece = this.getNextComponentNN((VillagePiecesUA.Start)componentIn, listIn, rand, 0, i);
	            if (structurepiece != null) {
	               i += Math.max(structurepiece.getBoundingBox().getXSize(), structurepiece.getBoundingBox().getZSize());
	               flag = true;
	            }
	         }

	         for(int j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5)) {
	            StructurePiece structurepiece1 = this.getNextComponentPP((VillagePiecesUA.Start)componentIn, listIn, rand, 0, j);
	            if (structurepiece1 != null) {
	               j += Math.max(structurepiece1.getBoundingBox().getXSize(), structurepiece1.getBoundingBox().getZSize());
	               flag = true;
	            }
	         }

	         Direction enumfacing = this.getCoordBaseMode();
	         if (flag && rand.nextInt(3) > 0 && enumfacing != null) {
	            switch(enumfacing) {
	            case NORTH:
	            default:
	               VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, Direction.WEST, this.getComponentType());
	               break;
	            case SOUTH:
	               VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, Direction.WEST, this.getComponentType());
	               break;
	            case WEST:
	               VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType());
	               break;
	            case EAST:
	               VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType());
	            }
	         }

	         if (flag && rand.nextInt(3) > 0 && enumfacing != null) {
	            switch(enumfacing) {
	            case NORTH:
	            default:
	               VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, Direction.EAST, this.getComponentType());
	               break;
	            case SOUTH:
	               VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, Direction.EAST, this.getComponentType());
	               break;
	            case WEST:
	               VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType());
	               break;
	            case EAST:
	               VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType());
	            }
	         }

	      }

	      public static MutableBoundingBox findPieceBox(VillagePiecesUA.Start start, List<StructurePiece> p_175848_1_, Random rand, int p_175848_3_, int p_175848_4_, int p_175848_5_, Direction facing) {
	         for(int i = 7 * MathHelper.nextInt(rand, 3, 5); i >= 7; i -= 7) {
	            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, i, facing);
	            if (StructurePiece.findIntersecting(p_175848_1_, mutableboundingbox) == null) {
	               return mutableboundingbox;
	            }
	         }

	         return null;
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.GRASS_PATH.getDefaultState());
	         BlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_PLANKS.getDefaultState());
	         BlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.GRAVEL.getDefaultState());
	         BlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
	         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
	         this.boundingBox.minY = 1000;
	         this.boundingBox.maxY = 0;

	         for(int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i) {
	            for(int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j) {
	               blockpos$mutableblockpos.setPos(i, 64, j);
	               if (structureBoundingBoxIn.isVecInside(blockpos$mutableblockpos)) {
	                  int k = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutableblockpos.getX(), blockpos$mutableblockpos.getZ());
	                  blockpos$mutableblockpos.setPos(blockpos$mutableblockpos.getX(), k, blockpos$mutableblockpos.getZ()).move(Direction.DOWN);
	                  if (blockpos$mutableblockpos.getY() < worldIn.getSeaLevel()) {
	                     blockpos$mutableblockpos.setY(worldIn.getSeaLevel() - 1);
	                  }

	                  while(blockpos$mutableblockpos.getY() >= worldIn.getSeaLevel() - 1) {
	                     BlockState iblockstate4 = worldIn.getBlockState(blockpos$mutableblockpos);
	                     Block block = iblockstate4.getBlock();
	                     if (block == Blocks.GRASS_BLOCK && worldIn.isAirBlock(blockpos$mutableblockpos.up())) {
	                        worldIn.setBlockState(blockpos$mutableblockpos, iblockstate, 2);
	                        break;
	                     }

	                     if (iblockstate4.getMaterial().isLiquid()) {
	                        worldIn.setBlockState(new BlockPos(blockpos$mutableblockpos), iblockstate1, 2);
	                        break;
	                     }

	                     if (block == Blocks.STONE || block == Blocks.SAND || block == Blocks.RED_SAND || block == Blocks.SANDSTONE || block == Blocks.CHISELED_SANDSTONE || block == Blocks.CUT_SANDSTONE || block == Blocks.RED_SANDSTONE || block == Blocks.CHISELED_SANDSTONE || block == Blocks.CUT_SANDSTONE || block == Blocks.END_STONE || block == Blocks.NETHERRACK || block == Blocks.SNOW_BLOCK || block == Blocks.ORANGE_TERRACOTTA) {
	                        worldIn.setBlockState(blockpos$mutableblockpos, iblockstate2, 2);
	                        worldIn.setBlockState(blockpos$mutableblockpos.down(), iblockstate3, 2);
	                        break;
	                     }

	                     blockpos$mutableblockpos.move(Direction.DOWN);
	                  }

	                  this.boundingBox.minY = Math.min(this.boundingBox.minY, blockpos$mutableblockpos.getY());
	                  this.boundingBox.maxY = Math.max(this.boundingBox.maxY, blockpos$mutableblockpos.getY());
	               }
	            }
	         }

	         return true;
	      }
	   }

	   public static class PieceWeightUA{
	      /** The Class object for the represantation of this village piece. */
	      public Class<? extends VillagePiecesUA.Village> villagePieceClass;
	      public final int villagePieceWeight;
	      public int villagePiecesSpawned;
	      public int villagePiecesLimit;

	      public PieceWeightUA(Class<? extends VillagePiecesUA.Village> classIn, int weightIn, int limitIn) {
	         this.villagePieceClass = classIn;
	         this.villagePieceWeight = weightIn;
	         this.villagePiecesLimit = limitIn;
	      }

	      public boolean canSpawnMoreVillagePiecesOfType(int componentType) {
	         return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
	      }

	      public boolean canSpawnMoreVillagePieces() {
	         return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
	      }
	   }

	   public static class Start extends VillagePiecesUA.Well {
	      /** World terrain type, 0 for normal, 1 for flap map */
	      public int terrainType;
	      public VillagePiecesUA.PieceWeightUA lastPlaced;
	      /**
	       * Contains List of all spawnable Structure Piece Weights. If no more Pieces of a type can be spawned, they are
	       * removed from this list
	       */
	      public List<VillagePiecesUA.PieceWeightUA> structureVillageWeightedPieceList;
	      public List<StructurePiece> pendingHouses = Lists.newArrayList();
	      public List<StructurePiece> pendingRoads = Lists.newArrayList();
	      public @javax.annotation.Nullable net.minecraft.world.biome.Biome biome;


         public Start(TemplateManager p_i50253_1_, CompoundNBT p_i50253_2_) {
            super(StructureInit.VISTARTUA, p_i50253_2_);
         }
	      
	      public Start(int p_i48769_1_, Random p_i48769_2_, int p_i48769_3_, int p_i48769_4_, List<VillagePiecesUA.PieceWeightUA> p_i48769_5_, VillageUAConfig p_i48769_6_, net.minecraft.world.biome.Biome biome) {
	         super((VillagePiecesUA.Start)null, 0, p_i48769_2_, p_i48769_3_, p_i48769_4_);
	         this.structureVillageWeightedPieceList = p_i48769_5_;
	         this.terrainType = p_i48769_6_.terrainType;
	         this.structureType = p_i48769_6_.type;
	         this.func_202579_a(this.structureType);
	         this.isZombieInfested = p_i48769_2_.nextInt(50) == 0;
	         this.biome = biome;
	      }
	   }

	   public static class Torch extends VillagePiecesUA.Village {
		   protected Torch(VillagePiecesUA.Start start, int type, Random rand, MutableBoundingBox boundingBoxIn, Direction facing) {
	         super(start, StructureInit.VILUA, type);
	         this.setCoordBaseMode(facing);
	         this.boundingBox = boundingBoxIn;
	      }

	      public Torch(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VILUA, p_i50111_2_);
	      }


	      public static MutableBoundingBox findPieceBox(VillagePiecesUA.Start start, List<StructurePiece> p_175856_1_, Random rand, int p_175856_3_, int p_175856_4_, int p_175856_5_, Direction facing) {
	         MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175856_3_, p_175856_4_, p_175856_5_, 0, 0, 0, 3, 4, 2, facing);
	         return StructurePiece.findIntersecting(p_175856_1_, mutableboundingbox) != null ? null : mutableboundingbox;
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 3, 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.setBlockState(worldIn, iblockstate, 1, 0, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 1, 1, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 1, 2, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, getBiomeSpecificBlockState(Blocks.BLACK_WOOL.getDefaultState()), 1, 3, 0, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.EAST, 2, 3, 0, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.NORTH, 1, 3, 1, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.WEST, 0, 3, 0, structureBoundingBoxIn);
	         this.placeTorch(worldIn, Direction.SOUTH, 1, 3, -1, structureBoundingBoxIn);
	         return true;
	      }
	   }

	   public static enum Type {
		 
		  
		  //vanilla types
		  OAK(1),
		  SANDSTONE(2),
		  ACACIA(3),
		  SPRUCE(4),
		  SNOWYSPRUCE(5),
		   
		  //UA types
	      DARK(5),
	      JUNGLE(6),
	      STONE(7),
	      END(8),
	      HELL(9),
	      BADLANDS(10),
		  ICY(11);
		  
	      private final int field_202605_e;

	      private Type(int p_i48768_3_) {
	         this.field_202605_e = p_i48768_3_;
	      }

	      public int func_202604_a() {
	         return this.field_202605_e;
	      }

	      public static VillagePiecesUA.Type typeById(int p_202603_0_) {
	         VillagePiecesUA.Type[] avillagepieces$type = values();
	         return p_202603_0_ >= 0 && p_202603_0_ < avillagepieces$type.length ? avillagepieces$type[p_202603_0_] : DARK;
	      }
	   }

	   public abstract static class Village extends StructurePiece {
	      protected int averageGroundLvl = -1;
	      /** The number of villagers that have been spawned in this component. */
	      private int villagersSpawned;
	      protected VillagePiecesUA.Type structureType;
	      protected boolean isZombieInfested;


	      protected Village(VillagePiecesUA.Start start, IStructurePieceType struct, int type) {
	         super(struct, type);
	         if (start != null) {
	            this.structureType = start.structureType;
	            this.isZombieInfested = start.isZombieInfested;
	         }
	      }

	      public Village(IStructurePieceType p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(p_i50111_1_, p_i50111_2_);
	         this.structureType = VillagePiecesUA.Type.valueOf(p_i50111_2_.getString("Type"));
	         this.isZombieInfested = p_i50111_2_.getBoolean("Zombie");
	         this.villagersSpawned = p_i50111_2_.getInt("VCount");
	         this.averageGroundLvl = p_i50111_2_.getInt("HPos");
	      }

	      /**
	       * (abstract) Helper method to read subclass data from NBT
	       */
	      protected void readAdditional(CompoundNBT tagCompound) {
	         tagCompound.putString("Type", this.structureType.name());
	         tagCompound.putBoolean("Zombie", this.isZombieInfested);
	         tagCompound.putInt("VCount", this.villagersSpawned);
	         tagCompound.putInt("HPos", this.averageGroundLvl);
	      }

	      
	      /**
	       * Gets the next village component, with the bounding box shifted -1 in the X and Z direction.
	       */
	      @Nullable
	      protected StructurePiece getNextComponentNN(VillagePiecesUA.Start start, List<StructurePiece> structureComponents, Random rand, int p_74891_4_, int p_74891_5_) {
	         Direction enumfacing = this.getCoordBaseMode();
	         if (enumfacing != null) {
	            switch(enumfacing) {
	            case NORTH:
	            default:
	               return VillagePiecesUA.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, Direction.WEST, this.getComponentType());
	            case SOUTH:
	               return VillagePiecesUA.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, Direction.WEST, this.getComponentType());
	            case WEST:
	               return VillagePiecesUA.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType());
	            case EAST:
	               return VillagePiecesUA.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType());
	            }
	         } else {
	            return null;
	         }
	      }

	      /**
	       * Gets the next village component, with the bounding box shifted +1 in the X and Z direction.
	       */
	      @Nullable
	      protected StructurePiece getNextComponentPP(VillagePiecesUA.Start start, List<StructurePiece> structureComponents, Random rand, int p_74894_4_, int p_74894_5_) {
	         Direction enumfacing = this.getCoordBaseMode();
	         if (enumfacing != null) {
	            switch(enumfacing) {
	            case NORTH:
	            default:
	               return VillagePiecesUA.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, Direction.EAST, this.getComponentType());
	            case SOUTH:
	               return VillagePiecesUA.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, Direction.EAST, this.getComponentType());
	            case WEST:
	               return VillagePiecesUA.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType());
	            case EAST:
	               return VillagePiecesUA.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType());
	            }
	         } else {
	            return null;
	         }
	      }


	      protected int getBestGroundLevel(IWorld worldIn, MutableBoundingBox structurebb) {
	         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

	         int halfXWidth = this.boundingBox.getXSize();
	         int halfZWidth = this.boundingBox.getZSize();
	         int x = this.boundingBox.minX;
	         int z = this.boundingBox.minZ; 
	         int[] heightArray = new int[halfXWidth*halfZWidth]; 
	         
	         for(int j = 0; j < this.boundingBox.getXSize(); j++) {
	        	 for(int k = 0; k < this.boundingBox.getZSize(); k++) {
	        		 blockpos$mutableblockpos.setPos(x+j, this.boundingBox.minY, z+k);
		  	         if (structurebb.isVecInside(blockpos$mutableblockpos)) {
		  	        	heightArray[k + j*this.boundingBox.getZSize()] = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos$mutableblockpos).getY();
		  	         }
		         }
	         }

	         return findPopular(heightArray);
	      }
	      

	      /**
	       * Deletes all continuous blocks from selected position upwards. Stops at hitting air.
	       */
	      protected void clearCurrentPositionBlocksUpwards(IWorld worldIn, int x, int y, int z, MutableBoundingBox structurebb) {
	         BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));
	         if (structurebb.isVecInside(blockpos)) {
	            while(!worldIn.isAirBlock(blockpos) && blockpos.getY() < 255) {
	               worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
	               blockpos = blockpos.up();
	            }

	         }
	      }
	      
	      //Source: https://stackoverflow.com/a/8545681
	      //O(n log n) complexity
	      public int findPopular(int[] heightArray) {

	    	    if (heightArray == null || heightArray.length == 0)
	    	        return -1;

	    	    Arrays.sort(heightArray);

	    	    int previous = heightArray[0];
	    	    int popular = heightArray[0];
	    	    int count = 1;
	    	    int maxCount = 1;

	    	    for (int i = 1; i < heightArray.length; i++) {
	    	    	if(heightArray[i] == 0) {
	    	    		continue;
	    	    	}
	    	    	else if (heightArray[i] == previous)
	    	            count++;
	    	        else {
	    	            if (count > maxCount) {
	    	                popular = heightArray[i-1];
	    	                maxCount = count;
	    	            }
	    	            previous = heightArray[i];
	    	            count = 1;
	    	        }
	    	    }

	    	    return count > maxCount ? heightArray[heightArray.length-1] : popular;
	    	}
	      

	      protected static boolean canVillageGoDeeper(MutableBoundingBox structurebb) {
	         return structurebb != null && structurebb.minY > 10;
	      }

	      /**
	       * Spawns a number of villagers in this component. Parameters: world, component bounding box, x offset, y offset,
	       * z offset, number of villagers
	       */
	      protected void spawnVillagers(IWorld worldIn, MutableBoundingBox structurebb, int x, int y, int z, int count, Random randomIn) {
	         if (this.villagersSpawned < count) {
	            for(int i = this.villagersSpawned; i < count; ++i) {
	               int j = this.getXWithOffset(x + i, z);
	               int k = this.getYWithOffset(y);
	               int l = this.getZWithOffset(x + i, z);
	               if (!structurebb.isVecInside(new BlockPos(j, k, l))) {
	                  break;
	               }

	               ++this.villagersSpawned;
	               if (this.isZombieInfested) {
	                  ZombieVillagerEntity entityzombievillager = EntityType.ZOMBIE_VILLAGER.create(worldIn.getWorld());
	                  entityzombievillager.setLocationAndAngles((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
	                  entityzombievillager.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(entityzombievillager)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
	                  entityzombievillager.enablePersistence();
	                  worldIn.addEntity(entityzombievillager);
	               } else {
	                  VillagerEntity entityvillager = EntityType.VILLAGER.create(worldIn.getWorld());
	                  entityvillager.setLocationAndAngles((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
	                  entityvillager.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(entityvillager)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
	                  entityvillager.setVillagerData(new VillagerData(VILLAGER_SKIN_ENUM[this.structureType.ordinal()], VillagerProfession.NONE, 1));
	                  worldIn.addEntity(entityvillager);
	               }
	            }

	         }
	      }


	      protected BlockState getBiomeSpecificBlockState(BlockState blockstateIn) {
	         Block block = blockstateIn.getBlock();
	         
	         if (this.structureType == VillagePiecesUA.Type.SANDSTONE) {
	             if (block.isIn(BlockTags.LOGS) || block == Blocks.COBBLESTONE) {
	                return Blocks.SANDSTONE.getDefaultState();
	             }

	             if (block.isIn(BlockTags.PLANKS)) {
	                return Blocks.CUT_SANDSTONE.getDefaultState();
	             }

	             if (block == Blocks.OAK_STAIRS) {
	                return Blocks.SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
	             }

	             if (block == Blocks.COBBLESTONE_STAIRS) {
	                return Blocks.SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
	             }

	             if (block == Blocks.GRAVEL) {
	                return Blocks.SANDSTONE.getDefaultState();
	             }

	             if (block == Blocks.OAK_PRESSURE_PLATE) {
	                return Blocks.BIRCH_PRESSURE_PLATE.getDefaultState();
	             }
	          } else if (this.structureType == VillagePiecesUA.Type.SPRUCE) {
	             if (block.isIn(BlockTags.LOGS)) {
	                return Blocks.SPRUCE_LOG.getDefaultState().with(LogBlock.AXIS, blockstateIn.get(LogBlock.AXIS));
	             }

	             if (block.isIn(BlockTags.PLANKS)) {
	                return Blocks.SPRUCE_PLANKS.getDefaultState();
	             }

	             if (block == Blocks.OAK_STAIRS) {
	                return Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
	             }

	             if (block == Blocks.OAK_FENCE) {
	                return Blocks.SPRUCE_FENCE.getDefaultState();
	             }

	             if (block == Blocks.OAK_PRESSURE_PLATE) {
	                return Blocks.SPRUCE_PRESSURE_PLATE.getDefaultState();
	             }
	          } else if (this.structureType == VillagePiecesUA.Type.ACACIA) {
	             if (block.isIn(BlockTags.LOGS)) {
	                return Blocks.ACACIA_LOG.getDefaultState().with(LogBlock.AXIS, blockstateIn.get(LogBlock.AXIS));
	             }

	             if (block.isIn(BlockTags.PLANKS)) {
	                return Blocks.ACACIA_PLANKS.getDefaultState();
	             }

	             if (block == Blocks.OAK_STAIRS) {
	                return Blocks.ACACIA_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
	             }

	             if (block == Blocks.COBBLESTONE) {
	                return Blocks.ACACIA_LOG.getDefaultState().with(LogBlock.AXIS, Direction.Axis.Y);
	             }

	             if (block == Blocks.OAK_FENCE) {
	                return Blocks.ACACIA_FENCE.getDefaultState();
	             }

	             if (block == Blocks.OAK_PRESSURE_PLATE) {
	                return Blocks.ACACIA_PRESSURE_PLATE.getDefaultState();
	             }
	          }
	         if (this.structureType == VillagePiecesUA.Type.DARK) {
	            if (block.isIn(BlockTags.LOGS)) {
	               return Blocks.DARK_OAK_LOG.getDefaultState();
	            }

	            if (block.isIn(BlockTags.PLANKS)) {
	               return Blocks.DARK_OAK_PLANKS.getDefaultState();
	            }

	            if (block == Blocks.OAK_STAIRS) {
	               return Blocks.DARK_OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
	            }

	            if (block == Blocks.COBBLESTONE_STAIRS) {
	               return Blocks.DARK_OAK_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
	            }

	            if (block == Blocks.OAK_FENCE) {
	               return Blocks.DARK_OAK_FENCE.getDefaultState();
	            }
	            
	            if (block == Blocks.COBBLESTONE_WALL) {
	               return Blocks.DARK_OAK_FENCE.getDefaultState();
	            }
	            
	            if (block == Blocks.GRAVEL) {
	               return Blocks.COARSE_DIRT.getDefaultState();
	            }

	            if (block == Blocks.OAK_PRESSURE_PLATE) {
	               return Blocks.DARK_OAK_PRESSURE_PLATE.getDefaultState();
	            }
	            
	            if (block == Blocks.COBBLESTONE) {
	               return Blocks.COARSE_DIRT.getDefaultState();
	            }
	         } else if (this.structureType == VillagePiecesUA.Type.JUNGLE) {
	            if (block.isIn(BlockTags.LOGS)) {
	               return Blocks.JUNGLE_LOG.getDefaultState().with(LogBlock.AXIS, blockstateIn.get(LogBlock.AXIS));
	            }

	            if (block.isIn(BlockTags.PLANKS)) {
	               return Blocks.JUNGLE_PLANKS.getDefaultState();
	            }

	            if (block == Blocks.OAK_STAIRS) {
	               return Blocks.JUNGLE_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
	            }

	            if (block == Blocks.OAK_FENCE) {
	               return Blocks.JUNGLE_FENCE.getDefaultState();
	            }

	            if (block == Blocks.COBBLESTONE_WALL) {
	               return Blocks.JUNGLE_FENCE.getDefaultState();
	            }
	            
	            if (block == Blocks.OAK_PRESSURE_PLATE) {
	               return Blocks.JUNGLE_PRESSURE_PLATE.getDefaultState();
	            }

	            if (block == Blocks.COBBLESTONE) {
	               return Blocks.MOSSY_STONE_BRICKS.getDefaultState();
	            }
	         } else if (this.structureType == VillagePiecesUA.Type.STONE) {
	            if (block.isIn(BlockTags.LOGS)) {
	               return Blocks.STONE.getDefaultState();
	            }

	            if (block.isIn(BlockTags.PLANKS)) {
	               return Blocks.ANDESITE.getDefaultState();
	            }

	            if (block == Blocks.OAK_STAIRS) {
	               return Blocks.STONE_SLAB.getDefaultState();
	            }

	            if (block == Blocks.COBBLESTONE) {
	               return Blocks.STONE.getDefaultState();
	            }
	            
	            if (block == Blocks.DIRT) {
	               return Blocks.STONE.getDefaultState();
	            }

	            if (block == Blocks.OAK_FENCE) {
	               return Blocks.ANDESITE.getDefaultState();
	            }

	            if (block == Blocks.OAK_PRESSURE_PLATE) {
	               return Blocks.STONE_PRESSURE_PLATE.getDefaultState();
	            }
	            
	            if (block == Blocks.GRAVEL) {
	               return Blocks.ANDESITE.getDefaultState();
	            }

	            if (block == Blocks.MOSSY_STONE_BRICKS) {
	               return Blocks.CRACKED_STONE_BRICKS.getDefaultState();
	            }
	         }
	         else if (this.structureType == VillagePiecesUA.Type.END) {
		            if (block.isIn(BlockTags.LOGS)) {
		               return Blocks.PURPUR_BLOCK.getDefaultState();
		            }

		            if (block.isIn(BlockTags.PLANKS)) {
		               return Blocks.END_STONE_BRICKS.getDefaultState();
		            }

		            if (block == Blocks.OAK_STAIRS) {
		               return Blocks.PURPUR_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
		            }

		            if (block == Blocks.COBBLESTONE_STAIRS) {
		            	return Blocks.PURPUR_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
		            }

	                if (blockstateIn.getBlock() == Blocks.WALL_TORCH)
	                {
	                    return Blocks.END_ROD.getDefaultState().with(EndRodBlock.FACING, blockstateIn.get(WallTorchBlock.HORIZONTAL_FACING));
	                }

		            if (block == Blocks.TORCH) {
		               return Blocks.END_ROD.getDefaultState().with(EndRodBlock.FACING, Direction.UP);
		            }
		            
		            if (block == Blocks.COBBLESTONE) {
		               return Blocks.PURPUR_BLOCK.getDefaultState();
		            }
		            
		            if (block == Blocks.DIRT) {
		               return Blocks.PURPUR_PILLAR.getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y);
		            }

		            if (block == Blocks.OAK_FENCE) {
		               return Blocks.PURPUR_BLOCK.getDefaultState();
		            }

		            if (block == Blocks.COBBLESTONE_WALL) {
		               return Blocks.END_STONE_BRICKS.getDefaultState();
		            }
		            
		            if (block == Blocks.OAK_PRESSURE_PLATE) {
		               return Blocks.PURPUR_SLAB.getDefaultState();
		            }
		            
		            if (block == Blocks.GRAVEL) {
		               return Blocks.PURPUR_PILLAR.getDefaultState();
		            }

		            if (block == Blocks.MOSSY_STONE_BRICKS) {
		               return Blocks.END_STONE_BRICKS.getDefaultState();
		            }
		         }
	         else if (this.structureType == VillagePiecesUA.Type.HELL) {
		            if (block.isIn(BlockTags.LOGS)) {
		               return Blocks.NETHER_BRICKS.getDefaultState();
		            }

		            if (block.isIn(BlockTags.PLANKS)) {
		               return Blocks.NETHER_BRICKS.getDefaultState();
		            }

		            if (block == Blocks.OAK_STAIRS) {
		               return Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
		            }

		            if (block == Blocks.COBBLESTONE_STAIRS) {
		            	return Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
		            }
	                
		            if (block == Blocks.COBBLESTONE) {
		               return Blocks.NETHER_BRICKS.getDefaultState();
		            }
		            
		            if (block == Blocks.DIRT) {
		               return Blocks.SOUL_SAND.getDefaultState();
		            }

		            if (block == Blocks.OAK_FENCE) {
		               return Blocks.NETHER_BRICK_FENCE.getDefaultState();
		            }

		            if (block == Blocks.COBBLESTONE_WALL) {
		               return Blocks.NETHER_BRICK_FENCE.getDefaultState();
		            }
		            
		            if (block == Blocks.OAK_PRESSURE_PLATE) {
		               return Blocks.NETHER_BRICK_SLAB.getDefaultState();
		            }
		            
		            if (block == Blocks.BLACK_WOOL) {
		               return Blocks.GLOWSTONE.getDefaultState();
		            }

		            if (block == Blocks.WALL_TORCH) {
		               return Blocks.REDSTONE_WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, blockstateIn.get(WallTorchBlock.HORIZONTAL_FACING));
		            }

		            if (block == Blocks.TORCH) {
		               return Blocks.REDSTONE_TORCH.getDefaultState();
		            }
		            
		            if (block == Blocks.MOSSY_STONE_BRICKS) {
		               return Blocks.BLACK_TERRACOTTA.getDefaultState();
		            }
		         }
	         else if (this.structureType == VillagePiecesUA.Type.BADLANDS) {
		            if (block.isIn(BlockTags.LOGS)) {
		               return Blocks.RED_TERRACOTTA.getDefaultState();
		            }

		            if (block.isIn(BlockTags.PLANKS)) {
		               return Blocks.RED_TERRACOTTA.getDefaultState();
		            }

		            if (block == Blocks.OAK_STAIRS) {
		               return Blocks.RED_SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
		            }

		            if (block == Blocks.COBBLESTONE_STAIRS) {
		            	return Blocks.RED_SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, blockstateIn.get(StairsBlock.FACING));
		            }
	                
		            if (block == Blocks.COBBLESTONE) {
		               return Blocks.CHISELED_RED_SANDSTONE.getDefaultState();
		            }
		            
		            if (block == Blocks.DIRT) {
		               return Blocks.RED_SAND.getDefaultState();
		            }

		            if (block == Blocks.OAK_FENCE) {
		               return Blocks.DARK_OAK_FENCE.getDefaultState();
		            }

		            if (block == Blocks.COBBLESTONE_WALL) {
		               return Blocks.DARK_OAK_FENCE.getDefaultState();
		            }
		            
		            if (block == Blocks.OAK_PRESSURE_PLATE) {
		               return Blocks.DARK_OAK_PRESSURE_PLATE.getDefaultState();
		            }
		            
		            if (block == Blocks.GRAVEL) {
		               return Blocks.RED_SANDSTONE.getDefaultState();
		            }

		            if (block == Blocks.MOSSY_STONE_BRICKS) {
		               return Blocks.CHISELED_RED_SANDSTONE.getDefaultState();
		            }
		         }
	         else if (this.structureType == VillagePiecesUA.Type.ICY) {
		            if (block.isIn(BlockTags.LOGS)) {
		               return Blocks.SNOW_BLOCK.getDefaultState();
		            }

		            if (block.isIn(BlockTags.PLANKS)) {
		               return Blocks.SNOW_BLOCK.getDefaultState();
		            }

		            if (block == Blocks.OAK_STAIRS) {
		               return Blocks.PACKED_ICE.getDefaultState();
		            }

		            if (block == Blocks.COBBLESTONE_STAIRS) {
		            	return Blocks.PACKED_ICE.getDefaultState();
		            }
	                
		            if (block == Blocks.COBBLESTONE) {
		               return Blocks.ICE.getDefaultState();
		            }
		            
		            if (block == Blocks.DIRT) {
		               return Blocks.ICE.getDefaultState();
		            }

		            if (block == Blocks.OAK_FENCE) {
		               return Blocks.PACKED_ICE.getDefaultState();
		            }
		            
		            if (block == Blocks.COBBLESTONE_WALL) {
		               return Blocks.PACKED_ICE.getDefaultState();
		            }

		            if (block == Blocks.OAK_PRESSURE_PLATE) {
		               return Blocks.SNOW.getDefaultState();
		            }
		            
		            if (block == Blocks.GRAVEL) {
		               return Blocks.ICE.getDefaultState();
		            }
		            
		            if (block == Blocks.WALL_TORCH) {
		               return Blocks.REDSTONE_WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, blockstateIn.get(WallTorchBlock.HORIZONTAL_FACING));
		            }

		            if (block == Blocks.TORCH) {
		               return Blocks.REDSTONE_TORCH.getDefaultState();
		            }
		            
		            if (block == Blocks.MOSSY_STONE_BRICKS) {
		               return Blocks.BLUE_ICE.getDefaultState();
		            }
		            
		            if (block == Blocks.LAVA) {
		               return Blocks.LIGHT_BLUE_CONCRETE_POWDER.getDefaultState();
		            }
		         }

	         return blockstateIn;
	      }

	      protected DoorBlock biomeDoor() {
	         if (this.structureType == VillagePiecesUA.Type.DARK) {
	            return (DoorBlock)Blocks.DARK_OAK_DOOR;
	         } 
	         else if(this.structureType == VillagePiecesUA.Type.END){
	        	 return (DoorBlock)Blocks.BIRCH_DOOR;
	         } 
	         else if(this.structureType == VillagePiecesUA.Type.HELL){
	        	 return (DoorBlock)Blocks.DARK_OAK_DOOR;
	         } 
	         else if(this.structureType == VillagePiecesUA.Type.JUNGLE){
	        	 return (DoorBlock)Blocks.JUNGLE_DOOR;
	         } 
	         else if(this.structureType == VillagePiecesUA.Type.BADLANDS){
	        	 return (DoorBlock)Blocks.ACACIA_DOOR;
	         } 
	         else if(this.structureType == VillagePiecesUA.Type.ICY){
	        	 return (DoorBlock)Blocks.BIRCH_DOOR;
	         } 
	         else if(this.structureType == VillagePiecesUA.Type.STONE){
	        	 return (DoorBlock)Blocks.SPRUCE_DOOR;
	         } 
	         else {
	            return (DoorBlock)Blocks.OAK_DOOR;
	         }
	      }

	      protected void generateDoor(IWorld worldIn, MutableBoundingBox sbb, Random rand, int x, int y, int z, Direction facing, DoorBlock door) {
	         this.setBlockState(worldIn, door.getDefaultState().with(DoorBlock.FACING, facing), x, y, z, sbb);
	         this.setBlockState(worldIn, door.getDefaultState().with(DoorBlock.FACING, facing).with(DoorBlock.HALF, DoubleBlockHalf.UPPER), x, y + 1, z, sbb);
	      }
	      
	      protected void createVillageDoor(IWorld p_189927_1_, MutableBoundingBox p_189927_2_, Random p_189927_3_, int p_189927_4_, int p_189927_5_, int p_189927_6_, Direction p_189927_7_) {
	         if (!this.isZombieInfested) {
	            this.generateDoor(p_189927_1_, p_189927_2_, p_189927_3_, p_189927_4_, p_189927_5_, p_189927_6_, Direction.NORTH, this.biomeDoor());
	         }

	      }

	      protected void placeTorch(IWorld p_189926_1_, Direction p_189926_2_, int p_189926_3_, int p_189926_4_, int p_189926_5_, MutableBoundingBox p_189926_6_) {
	         if (!this.isZombieInfested) {
	            this.setBlockState(p_189926_1_, getBiomeSpecificBlockState(Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, p_189926_2_)), p_189926_3_, p_189926_4_, p_189926_5_, p_189926_6_);
	         }

	      }

	      /**
	       * Replaces air and liquid from given position downwards. Stops when hitting anything else than air or liquid
	       */
	      protected void replaceAirAndLiquidDownwards(IWorld worldIn, BlockState blockstateIn, int x, int y, int z, MutableBoundingBox boundingboxIn) {
	         BlockState iblockstate = this.getBiomeSpecificBlockState(blockstateIn);
	         super.replaceAirAndLiquidDownwards(worldIn, iblockstate, x, y, z, boundingboxIn);
	      }

	      protected void func_202579_a(VillagePiecesUA.Type p_202579_1_) {
	         this.structureType = p_202579_1_;
	      }
	   }

	   public static class Well extends VillagePiecesUA.Village {
		   

          protected Well(IStructurePieceType p_i50287_1_, CompoundNBT p_i50287_2_) {
             super(p_i50287_1_, p_i50287_2_);
          }
          
	      public Well(VillagePiecesUA.Start start, int type, Random rand, int x, int z) {
			super(start, StructureInit.VIWUA, 0);
	         this.setCoordBaseMode(Direction.Plane.HORIZONTAL.random(rand));
	         if (this.getCoordBaseMode().getAxis() == Direction.Axis.Z) {
	            this.boundingBox = new MutableBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
	         } else {
	            this.boundingBox = new MutableBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
	         }
	      }

	      public Well(TemplateManager p_i50665_1_, CompoundNBT p_i50665_2_) {
		         super(StructureInit.VIWUA, p_i50665_2_);
	      }

	      /**
	       * Initiates construction of the Structure Component picked, at the current Location of StructGen
	       */
	      public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand) {
	         VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, Direction.WEST, this.getComponentType());
	         VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, Direction.EAST, this.getComponentType());
	         VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType());
	         VillagePiecesUA.generateAndAddRoadPiece((VillagePiecesUA.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType());
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 3, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
	         BlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE_WALL.getDefaultState());
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 4, 12, 4, iblockstate, Blocks.WATER.getDefaultState(), false);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 12, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, 12, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 12, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, 12, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 1, 13, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 1, 14, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 4, 13, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 4, 14, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 1, 13, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 1, 14, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 4, 13, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 4, 14, 4, structureBoundingBoxIn);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 15, 1, 4, 15, 4, iblockstate, iblockstate, false);
	         this.setBlockState(worldIn, Blocks.BELL.getDefaultState(), 2, 13, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.BARREL.getDefaultState(), 4, 13, 3, structureBoundingBoxIn);

	         for(int i = 0; i <= 5; ++i) {
	            for(int j = 0; j <= 5; ++j) {
	               if (j == 0 || j == 5 || i == 0 || i == 5) {
	                  this.setBlockState(worldIn, iblockstate, j, 11, i, structureBoundingBoxIn);
	                  this.clearCurrentPositionBlocksUpwards(worldIn, j, 12, i, structureBoundingBoxIn);
	               }
	            }
	         }

	         return true;
	      }
	   }

	   public static class TinyHouse extends VillagePiecesUA.Village {
	      private boolean isTallHouse;
	      private int tablePosition;

	      protected TinyHouse(VillagePiecesUA.Start start, int type, Random rand, MutableBoundingBox boundingBoxIn, Direction facing) {
	         super(start, StructureInit.VISMHUA, type);
	         this.setCoordBaseMode(facing);
	         this.boundingBox = boundingBoxIn;
	      }

	      public TinyHouse(TemplateManager p_i50111_1_, CompoundNBT p_i50111_2_) {
	         super(StructureInit.VISMHUA, p_i50111_2_);
	         this.isTallHouse = p_i50111_2_.getBoolean("isTallHouse");
	         this.tablePosition = p_i50111_2_.getInt("tablePosition");
	      }

	      /**
	       * (abstract) Helper method to read subclass data from NBT
	       */
	      protected void readAdditional(CompoundNBT tagCompound) {
			 super.readAdditional(tagCompound);
	         tagCompound.putBoolean("isTallHouse", this.isTallHouse);
	         tagCompound.putInt("tablePosition", this.tablePosition);
	      }
	      
	      public static VillagePiecesUA.TinyHouse createPiece(VillagePiecesUA.Start start, List<StructurePiece> p_175853_1_, Random rand, int p_175853_3_, int p_175853_4_, int p_175853_5_, Direction facing, int p_175853_7_) {
	         MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175853_3_, p_175853_4_, p_175853_5_, 0, 0, 0, 4, 6, 5, facing);
	         return canVillageGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175853_1_, mutableboundingbox) == null ? new VillagePiecesUA.TinyHouse(start, p_175853_7_, rand, mutableboundingbox, facing) : null;
	      }

	      /**
	       * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	       * the end, it adds Fences...
	       */
	      public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
	         if (this.averageGroundLvl < 0) {
	            this.averageGroundLvl = this.getBestGroundLevel(worldIn, structureBoundingBoxIn);
	            if (this.averageGroundLvl < 0) {
	               return true;
	            }

	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
	         }

	         BlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
	         BlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_PLANKS.getDefaultState());
	         BlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH));
	         BlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.OAK_LOG.getDefaultState());
	         BlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 3, 0, 4, iblockstate, iblockstate, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 3, Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), false);
	         if (this.isTallHouse) {
	            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 2, 4, 3, iblockstate3, iblockstate3, false);
	         } else {
	            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 2, 5, 3, iblockstate3, iblockstate3, false);
	         }

	         this.setBlockState(worldIn, iblockstate3, 1, 4, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 2, 4, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 1, 4, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 2, 4, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 0, 4, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 0, 4, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 0, 4, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 3, 4, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 3, 4, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate3, 3, 4, 3, structureBoundingBoxIn);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0, iblockstate3, iblockstate3, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, iblockstate3, iblockstate3, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 4, iblockstate3, iblockstate3, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 4, 3, 3, 4, iblockstate3, iblockstate3, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, iblockstate1, iblockstate1, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 1, 3, 3, 3, iblockstate1, iblockstate1, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, iblockstate1, iblockstate1, false);
	         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 3, 4, iblockstate1, iblockstate1, false);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.SOUTH, Boolean.valueOf(true)).with(PaneBlock.NORTH, Boolean.valueOf(true)), 3, 2, 2, structureBoundingBoxIn);
	         if (this.tablePosition > 0) {
	        	 if(iblockstate.has(FenceBlock.SOUTH)) {
	        		 this.setBlockState(worldIn, iblockstate4.with(FenceBlock.NORTH, Boolean.valueOf(true)).with(this.tablePosition == 1 ? FenceBlock.WEST : FenceBlock.EAST, Boolean.valueOf(true)), this.tablePosition, 1, 3, structureBoundingBoxIn);
	        	 }
	        	 else {
	        		 this.setBlockState(worldIn, iblockstate4, this.tablePosition, 1, 3, structureBoundingBoxIn);
		         }
	            this.setBlockState(worldIn, Blocks.OAK_PRESSURE_PLATE.getDefaultState(), this.tablePosition, 2, 3, structureBoundingBoxIn);
	            
	            if(this.tablePosition == 1) {
			         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD), 2, 1, 3, structureBoundingBoxIn);
			         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT), 2, 1, 2, structureBoundingBoxIn);
	            }else {
			         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD), 1, 1, 3, structureBoundingBoxIn);
			         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT), 1, 1, 2, structureBoundingBoxIn);
	            }
	         }else {
		         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.HEAD), 1, 1, 3, structureBoundingBoxIn);
		         this.setBlockState(worldIn, Blocks.RED_BED.getDefaultState().with(BedBlock.PART, BedPart.FOOT), 1, 1, 2, structureBoundingBoxIn);
	         }

	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
	         this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, Direction.NORTH);
	         if (this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
	            this.setBlockState(worldIn, iblockstate2, 1, 0, -1, structureBoundingBoxIn);
	            if (this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH) {
	               this.setBlockState(worldIn, Blocks.GRASS_BLOCK.getDefaultState(), 1, -1, -1, structureBoundingBoxIn);
	            }
	         }

	         for(int i = 0; i < 5; ++i) {
	            for(int j = 0; j < 4; ++j) {
	               this.clearCurrentPositionBlocksUpwards(worldIn, j, 6, i, structureBoundingBoxIn);
	               this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j, -1, i, structureBoundingBoxIn);
	            }
	         }

	         this.spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1, randomIn);
	         return true;
	      }
	   }
	}

