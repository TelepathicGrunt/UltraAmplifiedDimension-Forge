package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TripWireBlock;
import net.minecraft.block.TripWireHookBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class JungleTemplePiecesUA  extends ScatteredStructurePiece {
	   private boolean mainChest;
	   private boolean hiddenChest;
	   private boolean trap1;
	   private boolean trap2;
	   private static final JungleTemplePiecesUA.MossStoneSelector MOSS_STONE_SELECTOR = new JungleTemplePiecesUA.MossStoneSelector();
	   public static final ResourceLocation CHESTS_JUNGLE_TEMPLE_UA = new ResourceLocation("ultra_amplified_mod:chests/jungle_temple_ua");
	   public static final ResourceLocation CHESTS_JUNGLE_TEMPLE_DISPENSER_UA = new ResourceLocation("ultra_amplified_mod:chests/jungle_temple_dispenser_ua");

	   public JungleTemplePiecesUA(Random random, int x, int y, int z) {
	      super(StructureInitUA.TEJPUA, random, x, y, z, 12, 10, 15);
	   }

	   public JungleTemplePiecesUA(TemplateManager p_i51351_1_, CompoundNBT p_i51351_2_) {
	      super(StructureInitUA.TEJPUA, p_i51351_2_);
	      this.mainChest = p_i51351_2_.getBoolean("placedMainChest");
	      this.hiddenChest = p_i51351_2_.getBoolean("placedHiddenChest");
	      this.trap1 = p_i51351_2_.getBoolean("placedTrap1");
	      this.trap2 = p_i51351_2_.getBoolean("placedTrap2");
	   }

	   protected void readAdditional(CompoundNBT tagCompound) {
	      super.readAdditional(tagCompound);
	      tagCompound.putBoolean("placedMainChest", this.mainChest);
	      tagCompound.putBoolean("placedHiddenChest", this.hiddenChest);
	      tagCompound.putBoolean("placedTrap1", this.trap1);
	      tagCompound.putBoolean("placedTrap2", this.trap2);
	   }


	   public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos) {
	     
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, -4, 0, this.width - 1, 0, this.depth - 1, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 1, 2, 9, 2, 2, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 1, 12, 9, 2, 12, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 1, 3, 2, 2, 11, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, 1, 3, 9, 2, 11, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 1, 10, 6, 1, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 13, 10, 6, 13, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 2, 1, 6, 12, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 10, 3, 2, 10, 6, 12, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 3, 2, 9, 3, 12, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 6, 2, 9, 6, 12, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 3, 7, 3, 8, 7, 11, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 8, 4, 7, 8, 10, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 3, 1, 3, 8, 2, 11);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 4, 3, 6, 7, 3, 9);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 2, 4, 2, 9, 5, 12);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 4, 6, 5, 7, 6, 9);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 5, 7, 6, 6, 7, 8);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 5, 1, 2, 6, 2, 2);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 5, 2, 12, 6, 2, 12);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 5, 5, 1, 6, 5, 1);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 5, 5, 13, 6, 5, 13);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 5, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 10, 5, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 5, 9, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 10, 5, 9, structureBoundingBoxIn);

	         for(int i = 0; i <= 14; i += 14) {
	            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 4, i, 2, 5, i, false, randomIn, MOSS_STONE_SELECTOR);
	            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 4, i, 4, 5, i, false, randomIn, MOSS_STONE_SELECTOR);
	            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 7, 4, i, 7, 5, i, false, randomIn, MOSS_STONE_SELECTOR);
	            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, 4, i, 9, 5, i, false, randomIn, MOSS_STONE_SELECTOR);
	         }

	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 6, 0, 6, 6, 0, false, randomIn, MOSS_STONE_SELECTOR);

	         for(int l = 0; l <= 11; l += 11) {
	            for(int j = 2; j <= 12; j += 2) {
	               this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, l, 4, j, l, 5, j, false, randomIn, MOSS_STONE_SELECTOR);
	            }

	            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, l, 6, 5, l, 6, 5, false, randomIn, MOSS_STONE_SELECTOR);
	            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, l, 6, 9, l, 6, 9, false, randomIn, MOSS_STONE_SELECTOR);
	         }

	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 7, 2, 2, 9, 2, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, 7, 2, 9, 9, 2, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 7, 12, 2, 9, 12, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, 7, 12, 9, 9, 12, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 9, 4, 4, 9, 4, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 7, 9, 4, 7, 9, 4, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 9, 10, 4, 9, 10, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 7, 9, 10, 7, 9, 10, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 9, 7, 6, 9, 7, false, randomIn, MOSS_STONE_SELECTOR);
	         BlockState iblockstate3 = Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST);
	         BlockState iblockstate4 = Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);
	         BlockState iblockstate = Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
	         BlockState iblockstate1 = Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
	         this.setBlockState(worldIn, iblockstate1, 5, 9, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 6, 9, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 5, 9, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate, 6, 9, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 4, 0, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 5, 0, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 6, 0, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 7, 0, 0, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 4, 1, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 4, 2, 9, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 4, 3, 10, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 7, 1, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 7, 2, 9, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate1, 7, 3, 10, structureBoundingBoxIn);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 4, 1, 9, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 7, 1, 9, 7, 1, 9, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 10, 7, 2, 10, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 4, 5, 6, 4, 5, false, randomIn, MOSS_STONE_SELECTOR);
	         this.setBlockState(worldIn, iblockstate3, 4, 4, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate4, 7, 4, 5, structureBoundingBoxIn);

	         for(int k = 0; k < 4; ++k) {
	            this.setBlockState(worldIn, iblockstate, 5, 0 - k, 6 + k, structureBoundingBoxIn);
	            this.setBlockState(worldIn, iblockstate, 6, 0 - k, 6 + k, structureBoundingBoxIn);
	            this.fillWithAir(worldIn, structureBoundingBoxIn, 5, 0 - k, 7 + k, 6, 0 - k, 9 + k);
	         }

	         this.fillWithAir(worldIn, structureBoundingBoxIn, 1, -3, 12, 10, -1, 13);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 1, -3, 1, 3, -1, 13);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 1, -3, 1, 9, -1, 5);

	         for(int i1 = 1; i1 <= 13; i1 += 2) {
	            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, -3, i1, 1, -2, i1, false, randomIn, MOSS_STONE_SELECTOR);
	         }

	         for(int j1 = 2; j1 <= 12; j1 += 2) {
	            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, -1, j1, 3, -1, j1, false, randomIn, MOSS_STONE_SELECTOR);
	         }

	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, -2, 1, 4, -2, 1, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 7, -2, 1, 9, -2, 1, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 6, -3, 1, 6, -3, 1, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 6, -1, 1, 6, -1, 1, false, randomIn, MOSS_STONE_SELECTOR);
	         this.setBlockState(worldIn, Blocks.TRIPWIRE_HOOK.getDefaultState().with(TripWireHookBlock.FACING, Direction.EAST).with(TripWireHookBlock.ATTACHED, Boolean.valueOf(true)), 1, -3, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.TRIPWIRE_HOOK.getDefaultState().with(TripWireHookBlock.FACING, Direction.WEST).with(TripWireHookBlock.ATTACHED, Boolean.valueOf(true)), 4, -3, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.TRIPWIRE.getDefaultState().with(TripWireBlock.EAST, Boolean.valueOf(true)).with(TripWireBlock.WEST, Boolean.valueOf(true)).with(TripWireBlock.ATTACHED, Boolean.valueOf(true)), 2, -3, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.TRIPWIRE.getDefaultState().with(TripWireBlock.EAST, Boolean.valueOf(true)).with(TripWireBlock.WEST, Boolean.valueOf(true)).with(TripWireBlock.ATTACHED, Boolean.valueOf(true)), 3, -3, 8, structureBoundingBoxIn);
	         BlockState iblockstate5 = Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE).with(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE), 5, -3, 7, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 5, -3, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 5, -3, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 5, -3, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 5, -3, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate5, 5, -3, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 4, -3, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE).with(RedstoneWireBlock.WEST, RedstoneSide.UP), 5, -3, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.EAST, RedstoneSide.SIDE).with(RedstoneWireBlock.WEST, RedstoneSide.UP), 4, -2, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 3, -3, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.EAST, RedstoneSide.SIDE), 3, -1, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.EAST, RedstoneSide.SIDE), 2, -1, 1, structureBoundingBoxIn);
	         if (!this.trap1) {
	            this.trap1 = this.createDispenser(worldIn, structureBoundingBoxIn, randomIn, 3, -2, 1, Direction.NORTH, CHESTS_JUNGLE_TEMPLE_DISPENSER_UA);
	            this.trap1 = this.createDispenser(worldIn, structureBoundingBoxIn, randomIn, 2, -2, 1, Direction.NORTH, CHESTS_JUNGLE_TEMPLE_DISPENSER_UA);
	         }

	         this.setBlockState(worldIn, Blocks.VINE.getDefaultState().with(VineBlock.SOUTH, Boolean.valueOf(true)), 3, -2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.VINE.getDefaultState().with(VineBlock.SOUTH, Boolean.valueOf(true)), 2, -2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.TRIPWIRE_HOOK.getDefaultState().with(TripWireHookBlock.FACING, Direction.NORTH).with(TripWireHookBlock.ATTACHED, Boolean.valueOf(true)), 7, -3, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.TRIPWIRE_HOOK.getDefaultState().with(TripWireHookBlock.FACING, Direction.SOUTH).with(TripWireHookBlock.ATTACHED, Boolean.valueOf(true)), 7, -3, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.TRIPWIRE.getDefaultState().with(TripWireBlock.NORTH, Boolean.valueOf(true)).with(TripWireBlock.SOUTH, Boolean.valueOf(true)).with(TripWireBlock.ATTACHED, Boolean.valueOf(true)), 7, -3, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.TRIPWIRE.getDefaultState().with(TripWireBlock.NORTH, Boolean.valueOf(true)).with(TripWireBlock.SOUTH, Boolean.valueOf(true)).with(TripWireBlock.ATTACHED, Boolean.valueOf(true)), 7, -3, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.TRIPWIRE.getDefaultState().with(TripWireBlock.NORTH, Boolean.valueOf(true)).with(TripWireBlock.SOUTH, Boolean.valueOf(true)).with(TripWireBlock.ATTACHED, Boolean.valueOf(true)), 7, -3, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.EAST, RedstoneSide.SIDE), 8, -3, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.WEST, RedstoneSide.SIDE).with(RedstoneWireBlock.SOUTH, RedstoneSide.UP), 9, -3, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE).with(RedstoneWireBlock.SOUTH, RedstoneSide.UP), 9, -2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 9, -1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 9, -2, 6, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 9, -3, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 9, -3, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 9, -2, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 8, -2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE), 9, -1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE), 9, -1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE), 9, -1, 2, structureBoundingBoxIn);
	         if (!this.trap2) {
	            this.trap2 = this.createDispenser(worldIn, structureBoundingBoxIn, randomIn, 9, -2, 3, Direction.WEST, CHESTS_JUNGLE_TEMPLE_DISPENSER_UA);
	            this.trap2 = this.createDispenser(worldIn, structureBoundingBoxIn, randomIn, 9, -2, 4, Direction.WEST, CHESTS_JUNGLE_TEMPLE_DISPENSER_UA);
	            this.trap2 = this.createDispenser(worldIn, structureBoundingBoxIn, randomIn, 9, -2, 2, Direction.WEST, CHESTS_JUNGLE_TEMPLE_DISPENSER_UA);
	         }

	         this.setBlockState(worldIn, Blocks.VINE.getDefaultState().with(VineBlock.EAST, Boolean.valueOf(true)), 8, -1, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.VINE.getDefaultState().with(VineBlock.EAST, Boolean.valueOf(true)), 8, -2, 3, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.VINE.getDefaultState().with(VineBlock.EAST, Boolean.valueOf(true)), 8, -1, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.VINE.getDefaultState().with(VineBlock.EAST, Boolean.valueOf(true)), 8, -2, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.VINE.getDefaultState().with(VineBlock.EAST, Boolean.valueOf(true)), 8, -1, 4, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.VINE.getDefaultState().with(VineBlock.EAST, Boolean.valueOf(true)), 8, -2, 4, structureBoundingBoxIn);
	         if (!this.mainChest) {
	            this.mainChest = this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 9, -3, 3, CHESTS_JUNGLE_TEMPLE_UA);
	         }

	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 9, -3, 2, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 8, -3, 1, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 4, -3, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5, -2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5, -1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 6, -3, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 7, -2, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 7, -1, 5, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 8, -3, 5, structureBoundingBoxIn);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, -1, 1, 9, -1, 1, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, -1, 1, 8, -1, 5, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithAir(worldIn, structureBoundingBoxIn, 8, -3, 8, 10, -1, 10);
	         this.setBlockState(worldIn, Blocks.CHISELED_STONE_BRICKS.getDefaultState(), 8, -2, 11, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.CHISELED_STONE_BRICKS.getDefaultState(), 9, -2, 11, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.CHISELED_STONE_BRICKS.getDefaultState(), 10, -2, 11, structureBoundingBoxIn);
	         BlockState iblockstate2 = Blocks.LEVER.getDefaultState().with(LeverBlock.HORIZONTAL_FACING, Direction.NORTH).with(LeverBlock.FACE, AttachFace.WALL);
	         this.setBlockState(worldIn, iblockstate2, 8, -2, 12, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate2, 9, -2, 12, structureBoundingBoxIn);
	         this.setBlockState(worldIn, iblockstate2, 10, -2, 12, structureBoundingBoxIn);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, -3, 8, 8, -3, 10, false, randomIn, MOSS_STONE_SELECTOR);
	         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 10, -3, 8, 10, -3, 10, false, randomIn, MOSS_STONE_SELECTOR);
	         this.setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 10, -2, 9, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE), 8, -2, 9, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE), 8, -2, 10, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 10, -1, 9, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.STICKY_PISTON.getDefaultState().with(PistonBlock.FACING, Direction.UP), 9, -2, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.STICKY_PISTON.getDefaultState().with(PistonBlock.FACING, Direction.WEST), 10, -2, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.STICKY_PISTON.getDefaultState().with(PistonBlock.FACING, Direction.WEST), 10, -1, 8, structureBoundingBoxIn);
	         this.setBlockState(worldIn, Blocks.REPEATER.getDefaultState().with(RepeaterBlock.HORIZONTAL_FACING, Direction.NORTH), 10, -2, 10, structureBoundingBoxIn);
	         if (!this.hiddenChest) {
	            this.hiddenChest = this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 9, -3, 10, CHESTS_JUNGLE_TEMPLE_UA);
	            this.hiddenChest = this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 9, -3, 9, CHESTS_JUNGLE_TEMPLE_UA);
	            this.hiddenChest = this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 9, -3, 8, CHESTS_JUNGLE_TEMPLE_UA);
	         }

	         return true;
	   }

	   static class MossStoneSelector extends StructurePiece.BlockSelector {
	      private MossStoneSelector() {
	      }

	      
	      public void selectBlocks(Random rand, int x, int y, int z, boolean wall) {
	         if (rand.nextFloat() < 0.08F) 
	         {
		        this.blockstate = Blocks.INFESTED_COBBLESTONE.getDefaultState();
		     }
	         else if (rand.nextFloat() < 0.48F) 
	         {
	            this.blockstate = Blocks.COBBLESTONE.getDefaultState();
	         } else 
	         {
	            this.blockstate = Blocks.MOSSY_COBBLESTONE.getDefaultState();
	         }

	      }
	   }
	}