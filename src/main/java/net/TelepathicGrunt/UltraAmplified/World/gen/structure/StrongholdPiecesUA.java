package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorchWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.StructureIO;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class StrongholdPiecesUA
{
    private static final StrongholdPiecesUA.PieceWeight[] PIECE_WEIGHTS = new StrongholdPiecesUA.PieceWeight[] {new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.Straight.class, 40, 0), new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.Prison.class, 5, 8), new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.LeftTurn.class, 20, 0), new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.RightTurn.class, 20, 0), new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.RoomCrossing.class, 10, 9), new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.StairsStraight.class, 5, 7), new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.Stairs.class, 5, 7), new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.Crossing.class, 5, 7), new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.ChestCorridor.class, 5, 16), new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.Library.class, 10, 6)
        {
            public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
            {
                return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 4;
            }
        }, new StrongholdPiecesUA.PieceWeight(StrongholdPiecesUA.PortalRoom.class, 20, 1)
        {
            public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
            {
                return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 5;
            }
        }
    };
    private static List<StrongholdPiecesUA.PieceWeight> structurePieceList;
    private static Class <? extends StrongholdPiecesUA.Stronghold > strongComponentType;
    static int totalWeight;
    private static final StrongholdPiecesUA.Stones STRONGHOLD_STONES = new StrongholdPiecesUA.Stones();

    public static void registerStrongholdPieces()
    {
        StructureIO.registerStructureComponent(StrongholdPiecesUA.ChestCorridor.class, "SHCC");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.Corridor.class, "SHFC");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.Crossing.class, "SH5C");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.LeftTurn.class, "SHLT");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.Library.class, "SHLi");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.PortalRoom.class, "SHPR");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.Prison.class, "SHPH");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.RightTurn.class, "SHRT");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.RoomCrossing.class, "SHRC");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.Stairs.class, "SHSD");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.Stairs2.class, "SHStart");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.Straight.class, "SHS");
        StructureIO.registerStructureComponent(StrongholdPiecesUA.StairsStraight.class, "SHSSD");
    }

    /**
     * sets up Arrays with the Structure pieces and their weights
     */
    public static void prepareStructurePieces()
    {
        structurePieceList = Lists.<StrongholdPiecesUA.PieceWeight>newArrayList();

        for (StrongholdPiecesUA.PieceWeight structurestrongholdpieces$pieceweight : PIECE_WEIGHTS)
        {
            structurestrongholdpieces$pieceweight.instancesSpawned = 0;
            structurePieceList.add(structurestrongholdpieces$pieceweight);
        }

        strongComponentType = null;
    }

    private static boolean canAddStructurePieces()
    {
        boolean flag = false;
        totalWeight = 0;

        for (StrongholdPiecesUA.PieceWeight structurestrongholdpieces$pieceweight : structurePieceList)
        {
            if (structurestrongholdpieces$pieceweight.instancesLimit > 0 && structurestrongholdpieces$pieceweight.instancesSpawned < structurestrongholdpieces$pieceweight.instancesLimit)
            {
                flag = true;
            }

            totalWeight += structurestrongholdpieces$pieceweight.pieceWeight;
        }

        return flag;
    }

    private static StrongholdPiecesUA.Stronghold findAndCreatePieceFactory(Class <? extends StrongholdPiecesUA.Stronghold > clazz, List<StructurePiece> p_175954_1_, Random p_175954_2_, int p_175954_3_, int p_175954_4_, int p_175954_5_, @Nullable EnumFacing p_175954_6_, int p_175954_7_)
    {
        StrongholdPiecesUA.Stronghold structurestrongholdpieces$stronghold = null;

        if (clazz == StrongholdPiecesUA.Straight.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.Straight.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StrongholdPiecesUA.Prison.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.Prison.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StrongholdPiecesUA.LeftTurn.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.LeftTurn.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StrongholdPiecesUA.RightTurn.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.RightTurn.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StrongholdPiecesUA.RoomCrossing.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.RoomCrossing.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StrongholdPiecesUA.StairsStraight.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.StairsStraight.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StrongholdPiecesUA.Stairs.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.Stairs.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StrongholdPiecesUA.Crossing.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.Crossing.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StrongholdPiecesUA.ChestCorridor.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.ChestCorridor.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StrongholdPiecesUA.Library.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.Library.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StrongholdPiecesUA.PortalRoom.class)
        {
            structurestrongholdpieces$stronghold = StrongholdPiecesUA.PortalRoom.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }

        return structurestrongholdpieces$stronghold;
    }

    private static StrongholdPiecesUA.Stronghold generatePieceFromSmallDoor(StrongholdPiecesUA.Stairs2 p_175955_0_, List<StructurePiece> p_175955_1_, Random p_175955_2_, int p_175955_3_, int p_175955_4_, int p_175955_5_, EnumFacing p_175955_6_, int p_175955_7_)
    {
        if (!canAddStructurePieces())
        {
            return null;
        }
        else
        {
            if (strongComponentType != null)
            {
                StrongholdPiecesUA.Stronghold structurestrongholdpieces$stronghold = findAndCreatePieceFactory(strongComponentType, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
                strongComponentType = null;

                if (structurestrongholdpieces$stronghold != null)
                {
                    return structurestrongholdpieces$stronghold;
                }
            }

            int j = 0;

            while (j < 5)
            {
                ++j;
                int i = p_175955_2_.nextInt(totalWeight);

                for (StrongholdPiecesUA.PieceWeight structurestrongholdpieces$pieceweight : structurePieceList)
                {
                    i -= structurestrongholdpieces$pieceweight.pieceWeight;

                    if (i < 0)
                    {
                        if (!structurestrongholdpieces$pieceweight.canSpawnMoreStructuresOfType(p_175955_7_) || structurestrongholdpieces$pieceweight == p_175955_0_.strongholdPieceWeight)
                        {
                            break;
                        }

                        StrongholdPiecesUA.Stronghold structurestrongholdpieces$stronghold1 = findAndCreatePieceFactory(structurestrongholdpieces$pieceweight.pieceClass, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);

                        if (structurestrongholdpieces$stronghold1 != null)
                        {
                            ++structurestrongholdpieces$pieceweight.instancesSpawned;
                            p_175955_0_.strongholdPieceWeight = structurestrongholdpieces$pieceweight;

                            if (!structurestrongholdpieces$pieceweight.canSpawnMoreStructures())
                            {
                                structurePieceList.remove(structurestrongholdpieces$pieceweight);
                            }

                            return structurestrongholdpieces$stronghold1;
                        }
                    }
                }
            }

            MutableBoundingBox mutableboundingbox = StrongholdPiecesUA.Corridor.findPieceBox(p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_);

            if (mutableboundingbox != null && mutableboundingbox.minY > 1)
            {
                return new StrongholdPiecesUA.Corridor(p_175955_7_, p_175955_2_, mutableboundingbox, p_175955_6_);
            }
            else
            {
                return null;
            }
        }
    }

    private static StructurePiece generateAndAddPiece(StrongholdPiecesUA.Stairs2 p_175953_0_, List<StructurePiece> p_175953_1_, Random p_175953_2_, int p_175953_3_, int p_175953_4_, int p_175953_5_, @Nullable EnumFacing p_175953_6_, int p_175953_7_)
    {
        if (p_175953_7_ > 50)
        {
            return null;
        }
        else if (Math.abs(p_175953_3_ - p_175953_0_.getBoundingBox().minX) <= 112 && Math.abs(p_175953_5_ - p_175953_0_.getBoundingBox().minZ) <= 112)
        {
            StructurePiece StructurePiece = generatePieceFromSmallDoor(p_175953_0_, p_175953_1_, p_175953_2_, p_175953_3_, p_175953_4_, p_175953_5_, p_175953_6_, p_175953_7_ + 1);

            if (StructurePiece != null)
            {
                p_175953_1_.add(StructurePiece);
                p_175953_0_.pendingChildren.add(StructurePiece);
            }

            return StructurePiece;
        }
        else
        {
            return null;
        }
    }

    public static class ChestCorridor extends StrongholdPiecesUA.Stronghold
    {
        private boolean hasMadeChest;

        public ChestCorridor()
        {
        }

        public ChestCorridor(int p_i45582_1_, Random p_i45582_2_, MutableBoundingBox p_i45582_3_, EnumFacing p_i45582_4_)
        {
            super(p_i45582_1_);
            this.setCoordBaseMode(p_i45582_4_);
            this.entryDoor = this.getRandomDoor(p_i45582_2_);
            this.boundingBox = p_i45582_3_;
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Chest", this.hasMadeChest);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.hasMadeChest = tagCompound.getBoolean("Chest");
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            this.getNextComponentNormal((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 1);
        }

        public static StrongholdPiecesUA.ChestCorridor createPiece(List<StructurePiece> p_175868_0_, Random p_175868_1_, int p_175868_2_, int p_175868_3_, int p_175868_4_, EnumFacing p_175868_5_, int p_175868_6_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175868_2_, p_175868_3_, p_175868_4_, -1, -1, 0, 5, 5, 7, p_175868_5_);
            return canStrongholdGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175868_0_, mutableboundingbox) == null ? new StrongholdPiecesUA.ChestCorridor(p_175868_6_, p_175868_1_, mutableboundingbox, p_175868_5_) : null;
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, StrongholdPiecesUA.Stronghold.Door.OPENING, 1, 1, 6);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 1, 4, Blocks.STONE_BRICKS.getDefaultState(), Blocks.STONE_BRICKS.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 1, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 2, 4, structureBoundingBoxIn);

            for (int i = 2; i <= 4; ++i)
            {
                this.setBlockState(worldIn, Blocks.STONE_BRICK_SLAB.getDefaultState(), 2, 1, i, structureBoundingBoxIn);
            }

            if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3))))
            {
                this.hasMadeChest = true;
                if(ConfigUA.chestGeneration) {
                	this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 2, 3, LootTableList.CHESTS_STRONGHOLD_CORRIDOR);
                }
            }

            return true;
    }
    }

    public static class Corridor extends StrongholdPiecesUA.Stronghold
    {
        private int steps;

        public Corridor()
        {
        }

        public Corridor(int p_i45581_1_, Random p_i45581_2_, MutableBoundingBox p_i45581_3_, EnumFacing p_i45581_4_)
        {
            super(p_i45581_1_);
            this.setCoordBaseMode(p_i45581_4_);
            this.boundingBox = p_i45581_3_;
            this.steps = p_i45581_4_ != EnumFacing.NORTH && p_i45581_4_ != EnumFacing.SOUTH ? p_i45581_3_.getXSize() : p_i45581_3_.getZSize();
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setInt("Steps", this.steps);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.steps = tagCompound.getInt("Steps");
        }

        public static MutableBoundingBox findPieceBox(List<StructurePiece> p_175869_0_, Random p_175869_1_, int p_175869_2_, int p_175869_3_, int p_175869_4_, EnumFacing p_175869_5_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, 4, p_175869_5_);
            StructurePiece structurePiece = StructurePiece.findIntersecting(p_175869_0_, mutableboundingbox);

            if (structurePiece == null)
            {
                return null;
            }
            else
            {
                if (structurePiece.getBoundingBox().minY == mutableboundingbox.minY)
                {
                    for (int j = 3; j >= 1; --j)
                    {
                        mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j - 1, p_175869_5_);

                        if (!structurePiece.getBoundingBox().intersectsWith(mutableboundingbox))
                        {
                            return MutableBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j, p_175869_5_);
                        }
                    }
                }

                return null;
            }
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            for (int i = 0; i < this.steps; ++i)
            {
                this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 0, 0, i, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 1, 0, i, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 2, 0, i, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 3, 0, i, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 4, 0, i, structureBoundingBoxIn);

                for (int j = 1; j <= 3; ++j)
                {
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 0, j, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, j, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, j, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, j, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 4, j, i, structureBoundingBoxIn);
                }

                this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 0, 4, i, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 1, 4, i, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 2, 4, i, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 3, 4, i, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 4, 4, i, structureBoundingBoxIn);
            }

            return true;
        }
       
    }

    public static class Crossing extends StrongholdPiecesUA.Stronghold
    {
        private boolean leftLow;
        private boolean leftHigh;
        private boolean rightLow;
        private boolean rightHigh;

        public Crossing()
        {
        }

        public Crossing(int p_i45580_1_, Random p_i45580_2_, MutableBoundingBox p_i45580_3_, EnumFacing p_i45580_4_)
        {
            super(p_i45580_1_);
            this.setCoordBaseMode(p_i45580_4_);
            this.entryDoor = this.getRandomDoor(p_i45580_2_);
            this.boundingBox = p_i45580_3_;
            this.leftLow = p_i45580_2_.nextBoolean();
            this.leftHigh = p_i45580_2_.nextBoolean();
            this.rightLow = p_i45580_2_.nextBoolean();
            this.rightHigh = p_i45580_2_.nextInt(3) > 0;
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("leftLow", this.leftLow);
            tagCompound.setBoolean("leftHigh", this.leftHigh);
            tagCompound.setBoolean("rightLow", this.rightLow);
            tagCompound.setBoolean("rightHigh", this.rightHigh);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.leftLow = tagCompound.getBoolean("leftLow");
            this.leftHigh = tagCompound.getBoolean("leftHigh");
            this.rightLow = tagCompound.getBoolean("rightLow");
            this.rightHigh = tagCompound.getBoolean("rightHigh");
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            int i = 3;
            int j = 5;
            EnumFacing enumfacing = this.getCoordBaseMode();

            if (enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.NORTH)
            {
                i = 8 - i;
                j = 8 - j;
            }

            this.getNextComponentNormal((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 5, 1);

            if (this.leftLow)
            {
                this.getNextComponentX((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, i, 1);
            }

            if (this.leftHigh)
            {
                this.getNextComponentX((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, j, 7);
            }

            if (this.rightLow)
            {
                this.getNextComponentZ((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, i, 1);
            }

            if (this.rightHigh)
            {
                this.getNextComponentZ((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, j, 7);
            }
        }

        public static StrongholdPiecesUA.Crossing createPiece(List<StructurePiece> p_175866_0_, Random p_175866_1_, int p_175866_2_, int p_175866_3_, int p_175866_4_, EnumFacing p_175866_5_, int p_175866_6_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175866_2_, p_175866_3_, p_175866_4_, -4, -3, 0, 10, 9, 11, p_175866_5_);
            return canStrongholdGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175866_0_, mutableboundingbox) == null ? new StrongholdPiecesUA.Crossing(p_175866_6_, p_175866_1_, mutableboundingbox, p_175866_5_) : null;
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 8, 10, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 4, 3, 0);

            if (this.leftLow)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 5, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }

            if (this.rightLow)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 3, 1, 9, 5, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }

            if (this.leftHigh)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 7, 0, 7, 9, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }

            if (this.rightHigh)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 7, 9, 7, 9, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 10, 7, 3, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 2, 1, 8, 2, 6, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 4, 4, 9, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 5, 8, 4, 9, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 4, 7, 3, 4, 9, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 5, 3, 3, 6, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 3, 3, 4, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 6, 3, 4, 6, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 1, 7, 7, 1, 8, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 9, 7, 1, 9, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 7, 7, 2, 7, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 7, 4, 5, 9, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 7, 8, 5, 9, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 7, 7, 5, 9, Blocks.STONE_SLAB.getDefaultState().with(BlockSlab.TYPE, SlabType.DOUBLE), Blocks.STONE_SLAB.getDefaultState().with(BlockSlab.TYPE, SlabType.DOUBLE), false);
            this.setBlockState(worldIn, Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.SOUTH), 6, 5, 6, structureBoundingBoxIn);
            return true;
        }
    }

    public static class LeftTurn extends StrongholdPiecesUA.Stronghold
    {
        public LeftTurn()
        {
        }

        public LeftTurn(int p_i45579_1_, Random p_i45579_2_, MutableBoundingBox p_i45579_3_, EnumFacing p_i45579_4_)
        {
            super(p_i45579_1_);
            this.setCoordBaseMode(p_i45579_4_);
            this.entryDoor = this.getRandomDoor(p_i45579_2_);
            this.boundingBox = p_i45579_3_;
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            EnumFacing enumfacing = this.getCoordBaseMode();

            if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST)
            {
                this.getNextComponentZ((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 1);
            }
            else
            {
                this.getNextComponentX((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 1);
            }
        }

        public static StrongholdPiecesUA.LeftTurn createPiece(List<StructurePiece> p_175867_0_, Random p_175867_1_, int p_175867_2_, int p_175867_3_, int p_175867_4_, EnumFacing p_175867_5_, int p_175867_6_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175867_2_, p_175867_3_, p_175867_4_, -1, -1, 0, 5, 5, 5, p_175867_5_);
            return canStrongholdGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175867_0_, mutableboundingbox) == null ? new StrongholdPiecesUA.LeftTurn(p_175867_6_, p_175867_1_, mutableboundingbox, p_175867_5_) : null;
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
            EnumFacing enumfacing = this.getCoordBaseMode();

            if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }
            else
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }

            return true;
        }
    }

    public static class Library extends StrongholdPiecesUA.Stronghold
    {
        private boolean isLargeRoom;

        public Library()
        {
        }

        public Library(int p_i45578_1_, Random p_i45578_2_, MutableBoundingBox p_i45578_3_, EnumFacing p_i45578_4_)
        {
            super(p_i45578_1_);
            this.setCoordBaseMode(p_i45578_4_);
            this.entryDoor = this.getRandomDoor(p_i45578_2_);
            this.boundingBox = p_i45578_3_;
            this.isLargeRoom = p_i45578_3_.getYSize() > 6;
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Tall", this.isLargeRoom);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.isLargeRoom = tagCompound.getBoolean("Tall");
        }

        public static StrongholdPiecesUA.Library createPiece(List<StructurePiece> p_175864_0_, Random p_175864_1_, int p_175864_2_, int p_175864_3_, int p_175864_4_, EnumFacing p_175864_5_, int p_175864_6_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 11, 15, p_175864_5_);

            if (!canStrongholdGoDeeper(mutableboundingbox) || StructurePiece.findIntersecting(p_175864_0_, mutableboundingbox) != null)
            {
                mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 6, 15, p_175864_5_);

                if (!canStrongholdGoDeeper(mutableboundingbox) || StructurePiece.findIntersecting(p_175864_0_, mutableboundingbox) != null)
                {
                    return null;
                }
            }

            return new StrongholdPiecesUA.Library(p_175864_6_, p_175864_1_, mutableboundingbox, p_175864_5_);
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
                int i = 11;

            if (!this.isLargeRoom)
            {
                i = 6;
            }

            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 13, i - 1, 14, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 4, 1, 0);
            this.generateMaybeBox(worldIn, structureBoundingBoxIn, randomIn, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.COBWEB.getDefaultState(), Blocks.COBWEB.getDefaultState(), false, false);
          

            for(int l = 1; l <= 13; ++l) {
                if ((l - 1) % 4 == 0) {
                   this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, l, 1, 4, l, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                   this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, l, 12, 4, l, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                   this.setBlockState(worldIn, Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.EAST), 2, 3, l, structureBoundingBoxIn);
                   this.setBlockState(worldIn, Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.WEST), 11, 3, l, structureBoundingBoxIn);
                   if (this.isLargeRoom) {
                      this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, l, 1, 9, l, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                      this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 6, l, 12, 9, l, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                   }
                } else {
                   this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, l, 1, 4, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                   this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, l, 12, 4, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                   if (this.isLargeRoom) {
                      this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, l, 1, 9, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                      this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 6, l, 12, 9, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                   }
                }
             }

             for(int l1 = 3; l1 < 12; l1 += 2) {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, l1, 4, 3, l1, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, l1, 7, 3, l1, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, l1, 10, 3, l1, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
             }

             if (this.isLargeRoom) {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 1, 12, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 1, 9, 5, 2, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 12, 9, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.setBlockState(worldIn, Blocks.OAK_PLANKS.getDefaultState(), 9, 5, 11, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OAK_PLANKS.getDefaultState(), 8, 5, 11, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OAK_PLANKS.getDefaultState(), 9, 5, 10, structureBoundingBoxIn);
                IBlockState iblockstate5 = Blocks.OAK_FENCE.getDefaultState().with(BlockFence.WEST, Boolean.valueOf(true)).with(BlockFence.EAST, Boolean.valueOf(true));
                IBlockState iblockstate = Blocks.OAK_FENCE.getDefaultState().with(BlockFence.NORTH, Boolean.valueOf(true)).with(BlockFence.SOUTH, Boolean.valueOf(true));
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 6, 3, 3, 6, 11, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 6, 3, 10, 6, 9, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 2, 9, 6, 2, iblockstate5, iblockstate5, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 12, 7, 6, 12, iblockstate5, iblockstate5, false);
                this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState().with(BlockFence.NORTH, Boolean.valueOf(true)).with(BlockFence.EAST, Boolean.valueOf(true)), 3, 6, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState().with(BlockFence.SOUTH, Boolean.valueOf(true)).with(BlockFence.EAST, Boolean.valueOf(true)), 3, 6, 12, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState().with(BlockFence.NORTH, Boolean.valueOf(true)).with(BlockFence.WEST, Boolean.valueOf(true)), 10, 6, 2, structureBoundingBoxIn);

                for(int i1 = 0; i1 <= 2; ++i1) {
                   this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState().with(BlockFence.SOUTH, Boolean.valueOf(true)).with(BlockFence.WEST, Boolean.valueOf(true)), 8 + i1, 6, 12 - i1, structureBoundingBoxIn);
                   if (i1 != 2) {
                      this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState().with(BlockFence.NORTH, Boolean.valueOf(true)).with(BlockFence.EAST, Boolean.valueOf(true)), 8 + i1, 6, 11 - i1, structureBoundingBoxIn);
                   }
                }

                IBlockState iblockstate6 = Blocks.LADDER.getDefaultState().with(BlockLadder.FACING, EnumFacing.SOUTH);
                this.setBlockState(worldIn, iblockstate6, 10, 1, 13, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate6, 10, 2, 13, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate6, 10, 3, 13, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate6, 10, 4, 13, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate6, 10, 5, 13, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate6, 10, 6, 13, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate6, 10, 7, 13, structureBoundingBoxIn);
                IBlockState iblockstate1 = Blocks.OAK_FENCE.getDefaultState().with(BlockFence.EAST, Boolean.valueOf(true));
                this.setBlockState(worldIn, iblockstate1, 6, 9, 7, structureBoundingBoxIn);
                IBlockState iblockstate2 = Blocks.OAK_FENCE.getDefaultState().with(BlockFence.WEST, Boolean.valueOf(true));
                this.setBlockState(worldIn, iblockstate2, 7, 9, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 6, 8, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 7, 8, 7, structureBoundingBoxIn);
                IBlockState iblockstate3 = iblockstate.with(BlockFence.WEST, Boolean.valueOf(true)).with(BlockFence.EAST, Boolean.valueOf(true));
                this.setBlockState(worldIn, iblockstate3, 6, 7, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 7, 7, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 5, 7, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 8, 7, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1.with(BlockFence.NORTH, Boolean.valueOf(true)), 6, 7, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1.with(BlockFence.SOUTH, Boolean.valueOf(true)), 6, 7, 8, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2.with(BlockFence.NORTH, Boolean.valueOf(true)), 7, 7, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2.with(BlockFence.SOUTH, Boolean.valueOf(true)), 7, 7, 8, structureBoundingBoxIn);
                IBlockState iblockstate4 = Blocks.TORCH.getDefaultState();
                this.setBlockState(worldIn, iblockstate4, 5, 8, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate4, 8, 8, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate4, 6, 8, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate4, 6, 8, 8, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate4, 7, 8, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate4, 7, 8, 8, structureBoundingBoxIn);
             }
             
            if(ConfigUA.chestGeneration) {
            	this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 3, 5, LootTableList.CHESTS_STRONGHOLD_LIBRARY);
            }
            	
            if (this.isLargeRoom)
            {
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 12, 9, 1, structureBoundingBoxIn);
                
                if(ConfigUA.chestGeneration) {
                	this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 12, 8, 1, LootTableList.CHESTS_STRONGHOLD_LIBRARY);
                }
            }

            return true;
        }
    }

    static class PieceWeight
    {
        public Class <? extends StrongholdPiecesUA.Stronghold > pieceClass;
        public final int pieceWeight;
        public int instancesSpawned;
        public int instancesLimit;

        public PieceWeight(Class <? extends StrongholdPiecesUA.Stronghold > p_i2076_1_, int p_i2076_2_, int p_i2076_3_)
        {
            this.pieceClass = p_i2076_1_;
            this.pieceWeight = p_i2076_2_;
            this.instancesLimit = p_i2076_3_;
        }

        public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
        {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }

        public boolean canSpawnMoreStructures()
        {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }
    }

    public static class PortalRoom extends StrongholdPiecesUA.Stronghold
    {
        private boolean hasSpawner;

        public PortalRoom()
        {
        }

        public PortalRoom(int p_i45577_1_, Random p_i45577_2_, MutableBoundingBox p_i45577_3_, EnumFacing p_i45577_4_)
        {
            super(p_i45577_1_);
            this.setCoordBaseMode(p_i45577_4_);
            this.boundingBox = p_i45577_3_;
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Mob", this.hasSpawner);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.hasSpawner = tagCompound.getBoolean("Mob");
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            if (componentIn != null)
            {
                ((StrongholdPiecesUA.Stairs2)componentIn).strongholdPortalRoom = this;
            }
        }

        public static StrongholdPiecesUA.PortalRoom createPiece(List<StructurePiece> p_175865_0_, Random p_175865_1_, int p_175865_2_, int p_175865_3_, int p_175865_4_, EnumFacing p_175865_5_, int p_175865_6_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175865_2_, p_175865_3_, p_175865_4_, -4, -1, 0, 11, 8, 16, p_175865_5_);
            return canStrongholdGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175865_0_, mutableboundingbox) == null ? new StrongholdPiecesUA.PortalRoom(p_175865_6_, p_175865_1_, mutableboundingbox, p_175865_5_) : null;
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 7, 15, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, StrongholdPiecesUA.Stronghold.Door.GRATES, 4, 1, 0);
            int i = 6;
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, i, 1, 1, i, 14, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, i, 1, 9, i, 14, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 1, 8, i, 2, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 14, 8, i, 14, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 2, 1, 4, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 1, 9, 1, 4, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 1, 1, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 1, 9, 1, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 3, 1, 8, 7, 1, 12, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 6, 1, 11, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);

            for (int j = 3; j < 14; j += 2)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, j, 0, 4, j, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 3, j, 10, 4, j, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
            }

            for (int i1 = 2; i1 < 9; i1 += 2)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, i1, 3, 15, i1, 4, 15, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
            }

            IBlockState iblockstate3 = Blocks.STONE_BRICK_STAIRS.getDefaultState().with(BlockStairs.FACING, EnumFacing.NORTH);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 6, 1, 7, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 2, 6, 6, 2, 7, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 3, 7, 6, 3, 7, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);

            for (int k = 4; k <= 6; ++k)
            {
                this.setBlockState(worldIn, iblockstate3, k, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, k, 2, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, k, 3, 6, structureBoundingBoxIn);
            }

            IBlockState iblockstate4 = Blocks.END_PORTAL_FRAME.getDefaultState().with(BlockEndPortalFrame.FACING, EnumFacing.NORTH);
            IBlockState iblockstate = Blocks.END_PORTAL_FRAME.getDefaultState().with(BlockEndPortalFrame.FACING, EnumFacing.SOUTH);
            IBlockState iblockstate1 = Blocks.END_PORTAL_FRAME.getDefaultState().with(BlockEndPortalFrame.FACING, EnumFacing.EAST);
            IBlockState iblockstate2 = Blocks.END_PORTAL_FRAME.getDefaultState().with(BlockEndPortalFrame.FACING, EnumFacing.WEST);
            boolean flag = true;
            boolean[] aboolean = new boolean[12];

            for (int l = 0; l < aboolean.length; ++l)
            {
                aboolean[l] = randomIn.nextFloat() > 0.9F;
                flag &= aboolean[l];
            }

            this.setBlockState(worldIn, iblockstate4.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[0])), 4, 3, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[1])), 5, 3, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[2])), 6, 3, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[3])), 4, 3, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[4])), 5, 3, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[5])), 6, 3, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[6])), 3, 3, 9, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[7])), 3, 3, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[8])), 3, 3, 11, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate2.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[9])), 7, 3, 9, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate2.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[10])), 7, 3, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate2.with(BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[11])), 7, 3, 11, structureBoundingBoxIn);

            if (flag)
            {
                IBlockState iblockstate5 = Blocks.END_PORTAL.getDefaultState();
                this.setBlockState(worldIn, iblockstate5, 4, 3, 9, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 5, 3, 9, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 6, 3, 9, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 4, 3, 10, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 5, 3, 10, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 6, 3, 10, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 4, 3, 11, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 5, 3, 11, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 6, 3, 11, structureBoundingBoxIn);
            }

            if (!this.hasSpawner)
            {
                i = this.getYWithOffset(3);
                BlockPos blockpos = new BlockPos(this.getXWithOffset(5, 6), i, this.getZWithOffset(5, 6));

                if (structureBoundingBoxIn.isVecInside(blockpos))
                {
                    this.hasSpawner = true;
                    worldIn.setBlockState(blockpos, Blocks.SPAWNER.getDefaultState(), 2);
                    TileEntity tileentity = worldIn.getTileEntity(blockpos);

                    if (tileentity instanceof TileEntityMobSpawner)
                    {
                    	((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityType(EntityType.SILVERFISH);
                    }
                }
            }

            return true;
        }
    }

    public static class Prison extends StrongholdPiecesUA.Stronghold
    {
        public Prison()
        {
        }

        public Prison(int p_i45576_1_, Random p_i45576_2_, MutableBoundingBox p_i45576_3_, EnumFacing p_i45576_4_)
        {
            super(p_i45576_1_);
            this.setCoordBaseMode(p_i45576_4_);
            this.entryDoor = this.getRandomDoor(p_i45576_2_);
            this.boundingBox = p_i45576_3_;
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            this.getNextComponentNormal((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 1);
        }

        public static StrongholdPiecesUA.Prison createPiece(List<StructurePiece> p_175860_0_, Random p_175860_1_, int p_175860_2_, int p_175860_3_, int p_175860_4_, EnumFacing p_175860_5_, int p_175860_6_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175860_2_, p_175860_3_, p_175860_4_, -1, -1, 0, 9, 5, 11, p_175860_5_);
            return canStrongholdGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175860_0_, mutableboundingbox) == null ? new StrongholdPiecesUA.Prison(p_175860_6_, p_175860_1_, mutableboundingbox, p_175860_5_) : null;
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 4, 10, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 10, 3, 3, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 1, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 4, 3, 3, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 7, 4, 3, 7, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 4, 3, 9, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 4, 4, 3, 6, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 5, 7, 3, 5, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 4, 3, 8, structureBoundingBoxIn);
            IBlockState iblockstate = Blocks.IRON_DOOR.getDefaultState().with(BlockDoor.FACING, EnumFacing.WEST);
            IBlockState iblockstate1 = Blocks.IRON_DOOR.getDefaultState().with(BlockDoor.FACING, EnumFacing.WEST).with(BlockDoor.HALF, DoubleBlockHalf.UPPER);
            this.setBlockState(worldIn, iblockstate, 4, 1, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 4, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate, 4, 1, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 4, 2, 8, structureBoundingBoxIn);
            return true;
        }
    }

    public static class RightTurn extends StrongholdPiecesUA.LeftTurn
    {
        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            EnumFacing enumfacing = this.getCoordBaseMode();

            if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST)
            {
                this.getNextComponentX((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 1);
            }
            else
            {
                this.getNextComponentZ((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 1);
            }
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
            EnumFacing enumfacing = this.getCoordBaseMode();

            if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }
            else
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }

            return true;
        }
    }

    public static class RoomCrossing extends StrongholdPiecesUA.Stronghold
    {
        protected int roomType;
        private boolean hasSpawner = false;
        
        public RoomCrossing()
        {
        }

        public RoomCrossing(int p_i45575_1_, Random p_i45575_2_, MutableBoundingBox p_i45575_3_, EnumFacing p_i45575_4_)
        {
            super(p_i45575_1_);
            this.setCoordBaseMode(p_i45575_4_);
            this.entryDoor = this.getRandomDoor(p_i45575_2_);
            this.boundingBox = p_i45575_3_;
            this.roomType = p_i45575_2_.nextInt(4);
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setInt("Type", this.roomType);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.roomType = tagCompound.getInt("Type");
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            this.getNextComponentNormal((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 4, 1);
            this.getNextComponentX((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 4);
            this.getNextComponentZ((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 4);
        }

        public static StrongholdPiecesUA.RoomCrossing createPiece(List<StructurePiece> p_175859_0_, Random p_175859_1_, int p_175859_2_, int p_175859_3_, int p_175859_4_, EnumFacing p_175859_5_, int p_175859_6_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175859_2_, p_175859_3_, p_175859_4_, -4, -1, 0, 11, 7, 11, p_175859_5_);
            return canStrongholdGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175859_0_, mutableboundingbox) == null ? new StrongholdPiecesUA.RoomCrossing(p_175859_6_, p_175859_1_, mutableboundingbox, p_175859_5_) : null;
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 6, 10, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 4, 1, 0);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 10, 6, 3, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 4, 10, 3, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

            switch (this.roomType)
            {
                case 0:

                    if (!this.hasSpawner)
                    {
                        BlockPos blockpos = new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5));
                       
                        if (structureBoundingBoxIn.isVecInside(blockpos))
                        {
                            this.hasSpawner = true;
                            worldIn.setBlockState(blockpos, Blocks.SPAWNER.getDefaultState(), 2);
                            TileEntity tileentity = worldIn.getTileEntity(blockpos);

                            if (tileentity instanceof TileEntityMobSpawner)
                            {
                            	((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityType(EntityType.SILVERFISH);
                            }
                        }
                    }

                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.WEST), 4, 3, 5, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.EAST), 6, 3, 5, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.SOUTH), 5, 3, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.NORTH), 5, 3, 6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 4, 1, 6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 6, 1, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 6, 1, 6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
                    break;

                case 1:
                    for (int i1 = 0; i1 < 5; ++i1)
                    {
                        this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 3, 1, 3 + i1, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 7, 1, 3 + i1, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 3 + i1, 1, 3, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 3 + i1, 1, 7, structureBoundingBoxIn);
                    }


                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
                    

                    if (!this.hasSpawner)
                    {
                        BlockPos blockpos = new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5));
                        
                        if (structureBoundingBoxIn.isVecInside(blockpos))
                        {
                            this.hasSpawner = true;
                            worldIn.setBlockState(blockpos, Blocks.SPAWNER.getDefaultState(), 2);
                            TileEntity tileentity = worldIn.getTileEntity(blockpos);

                            if (tileentity instanceof TileEntityMobSpawner)
                            {
                            	((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityType(EntityType.SILVERFISH);
                            }
                        }
                    }

                    
                    this.setBlockState(worldIn, Blocks.WATER.getDefaultState(), 5, 4, 5, structureBoundingBoxIn);
                    break;

                case 2: case 3:

                	
                    for (int i = 1; i <= 9; ++i)
                    {
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 1, 3, i, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 9, 3, i, structureBoundingBoxIn);
                    }

                    for (int j = 1; j <= 9; ++j)
                    {
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), j, 3, 1, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), j, 3, 9, structureBoundingBoxIn);
                    }

                    this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, 3, 5, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, 3, 5, structureBoundingBoxIn);

                    for (int k = 1; k <= 3; ++k)
                    {
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, k, 4, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, k, 4, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, k, 6, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, k, 6, structureBoundingBoxIn);
                    }


                    if (!this.hasSpawner)
                    {
                        BlockPos blockpos = new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5));
                        
                        if (structureBoundingBoxIn.isVecInside(blockpos))
                        {
                            this.hasSpawner = true;
                            worldIn.setBlockState(blockpos, Blocks.SPAWNER.getDefaultState(), 2);
                            TileEntity tileentity = worldIn.getTileEntity(blockpos);

                            if (tileentity instanceof TileEntityMobSpawner)
                            {
                            	((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityType(EntityType.SILVERFISH);
                            }
                        }
                    }
                    
                    this.setBlockState(worldIn, Blocks.TORCH.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);

                    for(int l = 2; l <= 8; ++l) {
                       this.setBlockState(worldIn, Blocks.OAK_PLANKS.getDefaultState(), 2, 3, l, structureBoundingBoxIn);
                       this.setBlockState(worldIn, Blocks.OAK_PLANKS.getDefaultState(), 3, 3, l, structureBoundingBoxIn);
                       if (l <= 3 || l >= 7) {
                          this.setBlockState(worldIn, Blocks.OAK_PLANKS.getDefaultState(), 4, 3, l, structureBoundingBoxIn);
                          this.setBlockState(worldIn, Blocks.OAK_PLANKS.getDefaultState(), 5, 3, l, structureBoundingBoxIn);
                          this.setBlockState(worldIn, Blocks.OAK_PLANKS.getDefaultState(), 6, 3, l, structureBoundingBoxIn);
                       }

                       this.setBlockState(worldIn, Blocks.OAK_PLANKS.getDefaultState(), 7, 3, l, structureBoundingBoxIn);
                       this.setBlockState(worldIn, Blocks.OAK_PLANKS.getDefaultState(), 8, 3, l, structureBoundingBoxIn);
                    }

                    IBlockState iblockstate = Blocks.LADDER.getDefaultState().with(BlockLadder.FACING, EnumFacing.WEST);
                    this.setBlockState(worldIn, iblockstate, 9, 1, 3, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate, 9, 2, 3, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate, 9, 3, 3, structureBoundingBoxIn);
                    
                    if(ConfigUA.chestGeneration) {
                        this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 4, 8, LootTableList.CHESTS_STRONGHOLD_CROSSING);
                        this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 5, 4, 2, LootTableList.CHESTS_STRONGHOLD_CROSSING);
                        this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 6, 4, 8, LootTableList.CHESTS_STRONGHOLD_CROSSING);
                        this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 8, 4, 4, LootTableList.CHESTS_STRONGHOLD_CROSSING);
                    }
            }

            return true;
        }
    }

    public static class Stairs extends StrongholdPiecesUA.Stronghold
    {
        private boolean source;

        public Stairs()
        {
        }

        public Stairs(int p_i2081_1_, Random p_i2081_2_, int p_i2081_3_, int p_i2081_4_)
        {
            super(p_i2081_1_);
            this.source = true;
            this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(p_i2081_2_));
            this.entryDoor = StrongholdPiecesUA.Stronghold.Door.OPENING;
            
            if (this.getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
            {
                this.boundingBox = new MutableBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
            }
            else
            {
                this.boundingBox = new MutableBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
            }
            
            this.boundingBox.offset(0, p_i2081_2_.nextInt(80)+60, 0);
        }

        public Stairs(int p_i45574_1_, Random p_i45574_2_, MutableBoundingBox p_i45574_3_, EnumFacing p_i45574_4_)
        {
            super(p_i45574_1_);
            this.source = false;
            this.setCoordBaseMode(p_i45574_4_);
            this.entryDoor = this.getRandomDoor(p_i45574_2_);
            this.boundingBox = p_i45574_3_;
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Source", this.source);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.source = tagCompound.getBoolean("Source");
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            if (this.source)
            {
                StrongholdPiecesUA.strongComponentType = StrongholdPiecesUA.Crossing.class;
            }

            this.getNextComponentNormal((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 1);
        }

        public static StrongholdPiecesUA.Stairs createPiece(List<StructurePiece> p_175863_0_, Random p_175863_1_, int p_175863_2_, int p_175863_3_, int p_175863_4_, EnumFacing p_175863_5_, int p_175863_6_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175863_2_, p_175863_3_, p_175863_4_, -1, -7, 0, 5, 11, 5, p_175863_5_);
            return canStrongholdGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175863_0_, mutableboundingbox) == null ? new StrongholdPiecesUA.Stairs(p_175863_6_, p_175863_1_, mutableboundingbox, p_175863_5_) : null;
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 4, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 7, 0);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, StrongholdPiecesUA.Stronghold.Door.OPENING, 1, 1, 4);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 2, 6, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 1, 5, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 1, 6, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 1, 5, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 1, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 1, 5, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 2, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 3, 3, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 3, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 3, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 3, 2, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 3, 3, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 2, 2, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 1, 1, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 1, 2, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 1, 1, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 1, 1, 3, structureBoundingBoxIn);
            return true;
        }
    }

    public static class Stairs2 extends StrongholdPiecesUA.Stairs
    {
        public StrongholdPiecesUA.PieceWeight strongholdPieceWeight;
        public StrongholdPiecesUA.PortalRoom strongholdPortalRoom;
        public List<StructurePiece> pendingChildren = Lists.<StructurePiece>newArrayList();

        public Stairs2()
        {
        }

        public Stairs2(int p_i2083_1_, Random p_i2083_2_, int p_i2083_3_, int p_i2083_4_)
        {
            super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
        }
    }

    public static class StairsStraight extends StrongholdPiecesUA.Stronghold
    {
        public StairsStraight()
        {
        }

        public StairsStraight(int p_i45572_1_, Random p_i45572_2_, MutableBoundingBox p_i45572_3_, EnumFacing p_i45572_4_)
        {
            super(p_i45572_1_);
            this.setCoordBaseMode(p_i45572_4_);
            this.entryDoor = this.getRandomDoor(p_i45572_2_);
            this.boundingBox = p_i45572_3_;
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            this.getNextComponentNormal((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 1);
        }

        public static StrongholdPiecesUA.StairsStraight createPiece(List<StructurePiece> p_175861_0_, Random p_175861_1_, int p_175861_2_, int p_175861_3_, int p_175861_4_, EnumFacing p_175861_5_, int p_175861_6_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175861_2_, p_175861_3_, p_175861_4_, -1, -7, 0, 5, 11, 8, p_175861_5_);
            return canStrongholdGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175861_0_, mutableboundingbox) == null ? new StrongholdPiecesUA.StairsStraight(p_175861_6_, p_175861_1_, mutableboundingbox, p_175861_5_) : null;
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 7, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 7, 0);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, StrongholdPiecesUA.Stronghold.Door.OPENING, 1, 1, 7);
            IBlockState iblockstate = Blocks.STONE_BRICK_STAIRS.getDefaultState().with(BlockStairs.FACING, EnumFacing.SOUTH);

            for (int i = 0; i < 6; ++i)
            {
                this.setBlockState(worldIn, iblockstate, 1, 6 - i, 1 + i, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 2, 6 - i, 1 + i, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 3, 6 - i, 1 + i, structureBoundingBoxIn);

                if (i < 5)
                {
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 1, 5 - i, 1 + i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 2, 5 - i, 1 + i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), 3, 5 - i, 1 + i, structureBoundingBoxIn);
                }
            }

            return true;
        }
    }

    static class Stones extends StructurePiece.BlockSelector
    {
        private Stones()
        {
        }

        public void selectBlocks(Random rand, int x, int y, int z, boolean p_75062_5_)
        {
            if (p_75062_5_)
            {
                float f = rand.nextFloat();
                
                if (f < (float)(ConfigUA.silverfishStrongholdSpawnrate/100))
                {
                    this.blockstate = Blocks.CRACKED_STONE_BRICKS.getDefaultState();
                }
                else if (f < 0.25F)
                {
                    this.blockstate = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
                }
                else if (f < 0.55F)
                {
                    this.blockstate = Blocks.INFESTED_STONE_BRICKS.getDefaultState();
                }
                else
                {
                    this.blockstate = Blocks.STONE_BRICKS.getDefaultState();
                }
            }
            else
            {
                this.blockstate = Blocks.CAVE_AIR.getDefaultState();
            }
        }
    }

    public static class Straight extends StrongholdPiecesUA.Stronghold
    {
        private boolean expandsX;
        private boolean expandsZ;

        public Straight()
        {
        }

        public Straight(int p_i45573_1_, Random p_i45573_2_, MutableBoundingBox p_i45573_3_, EnumFacing p_i45573_4_)
        {
            super(p_i45573_1_);
            this.setCoordBaseMode(p_i45573_4_);
            this.entryDoor = this.getRandomDoor(p_i45573_2_);
            this.boundingBox = p_i45573_3_;
            this.expandsX = p_i45573_2_.nextInt(2) == 0;
            this.expandsZ = p_i45573_2_.nextInt(2) == 0;
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Left", this.expandsX);
            tagCompound.setBoolean("Right", this.expandsZ);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.expandsX = tagCompound.getBoolean("Left");
            this.expandsZ = tagCompound.getBoolean("Right");
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            this.getNextComponentNormal((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 1);

            if (this.expandsX)
            {
                this.getNextComponentX((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 2);
            }

            if (this.expandsZ)
            {
                this.getNextComponentZ((StrongholdPiecesUA.Stairs2)componentIn, listIn, rand, 1, 2);
            }
        }

        public static StrongholdPiecesUA.Straight createPiece(List<StructurePiece> p_175862_0_, Random p_175862_1_, int p_175862_2_, int p_175862_3_, int p_175862_4_, EnumFacing p_175862_5_, int p_175862_6_)
        {
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getComponentToAddBoundingBox(p_175862_2_, p_175862_3_, p_175862_4_, -1, -1, 0, 5, 5, 7, p_175862_5_);
            return canStrongholdGoDeeper(mutableboundingbox) && StructurePiece.findIntersecting(p_175862_0_, mutableboundingbox) == null ? new StrongholdPiecesUA.Straight(p_175862_6_, p_175862_1_, mutableboundingbox, p_175862_5_) : null;
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, false, randomIn, StrongholdPiecesUA.STRONGHOLD_STONES);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, StrongholdPiecesUA.Stronghold.Door.OPENING, 1, 1, 6);
            IBlockState iblockstate = Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.EAST);
            IBlockState iblockstate1 = Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.WEST);
            this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 1, iblockstate);
            this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 1, iblockstate1);
            this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 5, iblockstate);
            this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 5, iblockstate1);

            if (this.expandsX)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 2, 0, 3, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }

            if (this.expandsZ)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 2, 4, 3, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }

            return true;
        }
    }

    abstract static class Stronghold extends StructurePiece
    {
        protected StrongholdPiecesUA.Stronghold.Door entryDoor = StrongholdPiecesUA.Stronghold.Door.OPENING;

        public Stronghold()
        {
        }

        protected Stronghold(int p_i2087_1_)
        {
            super(p_i2087_1_);
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            tagCompound.setString("EntryDoor", this.entryDoor.name());
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            this.entryDoor = StrongholdPiecesUA.Stronghold.Door.valueOf(tagCompound.getString("EntryDoor"));
        }

        protected void placeDoor(IWorld worldIn, Random p_74990_2_, MutableBoundingBox p_74990_3_, StrongholdPiecesUA.Stronghold.Door p_74990_4_, int p_74990_5_, int p_74990_6_, int p_74990_7_)
        {
            switch (p_74990_4_)
            {
                case OPENING:
                    this.fillWithBlocks(worldIn, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                    break;

                case WOOD_DOOR:
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.OAK_DOOR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.OAK_DOOR.getDefaultState().with(BlockDoor.HALF, DoubleBlockHalf.UPPER), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    break;

                case GRATES:
                    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    break;

                case IRON_DOOR:
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_DOOR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_DOOR.getDefaultState().with(BlockDoor.HALF, DoubleBlockHalf.UPPER), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BUTTON.getDefaultState().with(BlockButton.HORIZONTAL_FACING, EnumFacing.NORTH), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BUTTON.getDefaultState().with(BlockButton.HORIZONTAL_FACING, EnumFacing.SOUTH), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
            }
        }

        protected StrongholdPiecesUA.Stronghold.Door getRandomDoor(Random p_74988_1_)
        {
            int i = p_74988_1_.nextInt(5);

            switch (i)
            {
                case 0:
                case 1:
                default:
                    return StrongholdPiecesUA.Stronghold.Door.OPENING;

                case 2:
                    return StrongholdPiecesUA.Stronghold.Door.WOOD_DOOR;

                case 3:
                    return StrongholdPiecesUA.Stronghold.Door.GRATES;

                case 4:
                    return StrongholdPiecesUA.Stronghold.Door.IRON_DOOR;
            }
        }

        @Nullable
        protected StructurePiece getNextComponentNormal(StrongholdPiecesUA.Stairs2 p_74986_1_, List<StructurePiece> p_74986_2_, Random p_74986_3_, int p_74986_4_, int p_74986_5_)
        {
            EnumFacing enumfacing = this.getCoordBaseMode();

            if (enumfacing != null)
            {
                switch (enumfacing)
                {
                case NORTH:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ - 1, enumfacing, this.getComponentType());

                case SOUTH:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.maxZ + 1, enumfacing, this.getComponentType());

                case WEST:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, enumfacing, this.getComponentType());

                case EAST:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, enumfacing, this.getComponentType());
				
                default:
                	break;
                }
            }

            return null;
        }

        @Nullable
        protected StructurePiece getNextComponentX(StrongholdPiecesUA.Stairs2 p_74989_1_, List<StructurePiece> p_74989_2_, Random p_74989_3_, int p_74989_4_, int p_74989_5_)
        {
            EnumFacing enumfacing = this.getCoordBaseMode();

            if (enumfacing != null)
            {
                switch (enumfacing)
                {
                    case NORTH:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, this.getComponentType());

                case SOUTH:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, this.getComponentType());

                case WEST:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());

                case EAST:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
				
                default:
					break;
                }
            }

            return null;
        }

        @Nullable
        protected StructurePiece getNextComponentZ(StrongholdPiecesUA.Stairs2 p_74987_1_, List<StructurePiece> p_74987_2_, Random p_74987_3_, int p_74987_4_, int p_74987_5_)
        {
            EnumFacing enumfacing = this.getCoordBaseMode();

            if (enumfacing != null)
            {
                switch (enumfacing)
                {
                case NORTH:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, this.getComponentType());

                case SOUTH:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, this.getComponentType());

                case WEST:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());

                case EAST:
                    return StrongholdPiecesUA.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
				
                default:
					break;
                }
            }

            return null;
        }

        protected static boolean canStrongholdGoDeeper(MutableBoundingBox p_74991_0_)
        {
            return p_74991_0_ != null && p_74991_0_.minY > 10;
        }

        public static enum Door
        {
            OPENING,
            WOOD_DOOR,
            GRATES,
            IRON_DOOR;
        }
    }
}
