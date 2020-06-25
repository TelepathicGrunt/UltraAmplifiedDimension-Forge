package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.ElderGuardianEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class OceanMonumentPiecesUA
{
    /**
     * --------------------------------------------------------------------------
     * |									|
     * |	HELLO READERS! IF YOU'RE HERE, YOU'RE PROBABLY			|
     * |	LOOKING FOR A TUTORIAL ON HOW TO DO STRUCTURES			|
     * |									|
     * -------------------------------------------------------------------------
     * 
     * Don't worry, I actually have a structure tutorial
     * mod already setup for you to check out! It's full
     * of comments on what does what and how to make structures.
     * 
     * Here's the link! https://github.com/TelepathicGrunt/StructureTutorialMod
     * 
     * Good luck and have fun modding!
     */
	public static class MonumentBuilding extends OceanMonumentPiecesUA.Piece
	{
		private OceanMonumentPiecesUA.RoomDefinition sourceRoom;
		private OceanMonumentPiecesUA.RoomDefinition coreRoom;
		private final List<OceanMonumentPiecesUA.Piece> childPieces = Lists.<OceanMonumentPiecesUA.Piece>newArrayList();


		public MonumentBuilding(TemplateManager p_i50665_1_, CompoundNBT p_i50665_2_)
		{
			super(StructureInitUA.OMBUA, p_i50665_2_);
		}


		public MonumentBuilding(Random rand, int x, int z, Direction direction)
		{
			super(StructureInitUA.OMBUA, 0);
			this.setCoordBaseMode(direction);
			Direction enumfacing = this.getCoordBaseMode();

			int height = rand.nextInt(18) + 67;

			if (enumfacing.getAxis() == Direction.Axis.Z)
			{
				this.boundingBox = new MutableBoundingBox(x, height, z, x + 58 - 1, height + 22, z + 58 - 1);
			}
			else
			{
				this.boundingBox = new MutableBoundingBox(x, height, z, x + 58 - 1, height + 22, z + 58 - 1);
			}

			List<OceanMonumentPiecesUA.RoomDefinition> list = this.generateRoomGraph(rand);
			this.sourceRoom.claimed = true;
			this.childPieces.add(new OceanMonumentPiecesUA.EntryRoom(enumfacing, this.sourceRoom));
			this.childPieces.add(new OceanMonumentPiecesUA.MonumentCoreRoom(enumfacing, this.coreRoom));
			List<OceanMonumentPiecesUA.MonumentRoomFitHelper> list1 = Lists.<OceanMonumentPiecesUA.MonumentRoomFitHelper>newArrayList();
			list1.add(new OceanMonumentPiecesUA.XYDoubleRoomFitHelper());
			list1.add(new OceanMonumentPiecesUA.YZDoubleRoomFitHelper());
			list1.add(new OceanMonumentPiecesUA.ZDoubleRoomFitHelper());
			list1.add(new OceanMonumentPiecesUA.XDoubleRoomFitHelper());
			list1.add(new OceanMonumentPiecesUA.YDoubleRoomFitHelper());
			list1.add(new OceanMonumentPiecesUA.FitSimpleRoomTopHelper());
			list1.add(new OceanMonumentPiecesUA.FitSimpleRoomHelper());
			label294:

			for (OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition : list)
			{
				if (!structureoceanmonumentpieces$roomdefinition.claimed && !structureoceanmonumentpieces$roomdefinition.isSpecial())
				{
					Iterator<MonumentRoomFitHelper> lvt_10_1_ = list1.iterator();
					OceanMonumentPiecesUA.MonumentRoomFitHelper structureoceanmonumentpieces$monumentroomfithelper;

					while (true)
					{
						if (!lvt_10_1_.hasNext())
						{
							continue label294;
						}

						structureoceanmonumentpieces$monumentroomfithelper = lvt_10_1_.next();

						if (structureoceanmonumentpieces$monumentroomfithelper.fits(structureoceanmonumentpieces$roomdefinition))
						{
							break;
						}
					}

					this.childPieces.add(structureoceanmonumentpieces$monumentroomfithelper.create(enumfacing, structureoceanmonumentpieces$roomdefinition, rand));
				}
			}

			int j = this.boundingBox.minY;
			int k = this.getXWithOffset(9, 22);
			int l = this.getZWithOffset(9, 22);

			for (OceanMonumentPiecesUA.Piece structureoceanmonumentpieces$piece : this.childPieces)
			{
				structureoceanmonumentpieces$piece.getBoundingBox().offset(k, j, l);
			}

			MutableBoundingBox structureboundingbox1 = MutableBoundingBox.createProper(this.getXWithOffset(1, 1), this.getYWithOffset(1), this.getZWithOffset(1, 1), this.getXWithOffset(23, 21), this.getYWithOffset(8), this.getZWithOffset(23, 21));
			MutableBoundingBox structureboundingbox2 = MutableBoundingBox.createProper(this.getXWithOffset(34, 1), this.getYWithOffset(1), this.getZWithOffset(34, 1), this.getXWithOffset(56, 21), this.getYWithOffset(8), this.getZWithOffset(56, 21));
			MutableBoundingBox structureboundingbox = MutableBoundingBox.createProper(this.getXWithOffset(22, 22), this.getYWithOffset(13), this.getZWithOffset(22, 22), this.getXWithOffset(35, 35), this.getYWithOffset(17), this.getZWithOffset(35, 35));
			int i = rand.nextInt();
			this.childPieces.add(new OceanMonumentPiecesUA.WingRoom(enumfacing, structureboundingbox1, i++));
			this.childPieces.add(new OceanMonumentPiecesUA.WingRoom(enumfacing, structureboundingbox2, i++));
			this.childPieces.add(new OceanMonumentPiecesUA.Penthouse(enumfacing, structureboundingbox));
		}


		private List<OceanMonumentPiecesUA.RoomDefinition> generateRoomGraph(Random p_175836_1_)
		{
			OceanMonumentPiecesUA.RoomDefinition[] astructureoceanmonumentpieces$roomdefinition = new OceanMonumentPiecesUA.RoomDefinition[75];

			for (int x = 0; x < 5; ++x)
			{
				for (int z = 0; z < 4; ++z)
				{
					int roomIndex = getRoomIndex(x, 0, z);
					astructureoceanmonumentpieces$roomdefinition[roomIndex] = new OceanMonumentPiecesUA.RoomDefinition(roomIndex);
				}
			}

			for (int x = 0; x < 5; ++x)
			{
				for (int z = 0; z < 4; ++z)
				{
					int roomIndex = getRoomIndex(x, 1, z);
					astructureoceanmonumentpieces$roomdefinition[roomIndex] = new OceanMonumentPiecesUA.RoomDefinition(roomIndex);
				}
			}

			for (int x = 1; x < 4; ++x)
			{
				for (int z = 0; z < 2; ++z)
				{
					int roomIndex = getRoomIndex(x, 2, z);
					astructureoceanmonumentpieces$roomdefinition[roomIndex] = new OceanMonumentPiecesUA.RoomDefinition(roomIndex);
				}
			}

			this.sourceRoom = astructureoceanmonumentpieces$roomdefinition[GRIDROOM_SOURCE_INDEX];

			for (int x = 0; x < 5; ++x)
			{
				for (int z = 0; z < 5; ++z)
				{
					for (int y = 0; y < 3; ++y)
					{
						int roomIndex = getRoomIndex(x, y, z);

						if (astructureoceanmonumentpieces$roomdefinition[roomIndex] != null)
						{
							for (Direction enumfacing : Direction.values())
							{
								int roomXPos = x + enumfacing.getXOffset();
								int roomYPos = y + enumfacing.getYOffset();
								int roomZPos = z + enumfacing.getZOffset();

								if (roomXPos >= 0 && roomXPos < 5 && roomZPos >= 0 && roomZPos < 5 && roomYPos >= 0 && roomYPos < 3)
								{
									int roomIndex2 = getRoomIndex(roomXPos, roomYPos, roomZPos);

									if (astructureoceanmonumentpieces$roomdefinition[roomIndex2] != null)
									{
										if (roomZPos == z)
										{
											astructureoceanmonumentpieces$roomdefinition[roomIndex].setConnection(enumfacing, astructureoceanmonumentpieces$roomdefinition[roomIndex2]);
										}
										else
										{
											astructureoceanmonumentpieces$roomdefinition[roomIndex].setConnection(enumfacing.getOpposite(), astructureoceanmonumentpieces$roomdefinition[roomIndex2]);
										}
									}
								}
							}
						}
					}
				}
			}

			OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition = new OceanMonumentPiecesUA.RoomDefinition(1003);
			OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = new OceanMonumentPiecesUA.RoomDefinition(1001);
			OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition2 = new OceanMonumentPiecesUA.RoomDefinition(1002);
			astructureoceanmonumentpieces$roomdefinition[GRIDROOM_TOP_CONNECT_INDEX].setConnection(Direction.UP, structureoceanmonumentpieces$roomdefinition);
			astructureoceanmonumentpieces$roomdefinition[GRIDROOM_LEFTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, structureoceanmonumentpieces$roomdefinition1);
			astructureoceanmonumentpieces$roomdefinition[GRIDROOM_RIGHTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, structureoceanmonumentpieces$roomdefinition2);
			structureoceanmonumentpieces$roomdefinition.claimed = true;
			structureoceanmonumentpieces$roomdefinition1.claimed = true;
			structureoceanmonumentpieces$roomdefinition2.claimed = true;
			this.sourceRoom.isSource = true;
			this.coreRoom = astructureoceanmonumentpieces$roomdefinition[getRoomIndex(p_175836_1_.nextInt(4), 0, 2)];
			this.coreRoom.claimed = true;
			this.coreRoom.connections[Direction.EAST.getIndex()].claimed = true;
			this.coreRoom.connections[Direction.NORTH.getIndex()].claimed = true;
			this.coreRoom.connections[Direction.EAST.getIndex()].connections[Direction.NORTH.getIndex()].claimed = true;
			this.coreRoom.connections[Direction.UP.getIndex()].claimed = true;
			this.coreRoom.connections[Direction.EAST.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
			this.coreRoom.connections[Direction.NORTH.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
			this.coreRoom.connections[Direction.EAST.getIndex()].connections[Direction.NORTH.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
			List<OceanMonumentPiecesUA.RoomDefinition> list = Lists.<OceanMonumentPiecesUA.RoomDefinition>newArrayList();

			for (OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition4 : astructureoceanmonumentpieces$roomdefinition)
			{
				if (structureoceanmonumentpieces$roomdefinition4 != null)
				{
					structureoceanmonumentpieces$roomdefinition4.updateOpenings();
					list.add(structureoceanmonumentpieces$roomdefinition4);
				}
			}

			structureoceanmonumentpieces$roomdefinition.updateOpenings();
			Collections.shuffle(list, p_175836_1_);
			list.add(structureoceanmonumentpieces$roomdefinition);
			list.add(structureoceanmonumentpieces$roomdefinition1);
			list.add(structureoceanmonumentpieces$roomdefinition2);
			return list;
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos chunkPos)
		{

			this.generateWaterBox(world, mutableBoundingBoxIn);
			this.generateWing(false, 0, world, random, mutableBoundingBoxIn);
			this.generateWing(true, 33, world, random, mutableBoundingBoxIn);
			this.generateEntranceArchs(world, random, mutableBoundingBoxIn);
			this.generateEntranceWall(world, random, mutableBoundingBoxIn);
			this.generateRoofPiece(world, random, mutableBoundingBoxIn);
			this.generateLowerWall(world, random, mutableBoundingBoxIn);
			this.generateMiddleWall(world, random, mutableBoundingBoxIn);
			this.generateUpperWall(world, random, mutableBoundingBoxIn);

			for (int j = 0; j < 7; ++j)
			{
				int k = 0;

				while (k < 7)
				{
					if (k == 0 && j == 3)
					{
						k = 6;
					}

					int l = j * 9;
					int i1 = k * 9;

					for (int j1 = 0; j1 < 4; ++j1)
					{
						for (int k1 = 0; k1 < 4; ++k1)
						{
							this.setBlockState(world, BRICKS_PRISMARINE, l + j1, 0, i1 + k1, mutableBoundingBoxIn);
							this.replaceAirAndLiquidDownwards(world, BRICKS_PRISMARINE, l + j1, -1, i1 + k1, mutableBoundingBoxIn);
						}
					}

					if (j != 0 && j != 6)
					{
						k += 6;
					}
					else
					{
						++k;
					}
				}
			}

			for (OceanMonumentPiecesUA.Piece structureoceanmonumentpieces$piece : this.childPieces)
			{
				if (structureoceanmonumentpieces$piece.getBoundingBox().intersectsWith(mutableBoundingBoxIn))
				{
					structureoceanmonumentpieces$piece.create(world, p_225577_2_, random, mutableBoundingBoxIn, chunkPos);
				}
			}

			return true;

		}


		private void generateWing(boolean p_175840_1_, int p_175840_2_, IWorld world, Random p_175840_4_, MutableBoundingBox p_175840_5_)
		{

			if (this.doesChunkIntersect(p_175840_5_, p_175840_2_, 0, p_175840_2_ + 23, 20))
			{
				this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + 0, 0, 0, p_175840_2_ + 24, 0, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

				for (int j = 0; j < 4; ++j)
				{
					this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + j, j + 1, j, p_175840_2_ + j, j + 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + j + 7, j + 5, j + 7, p_175840_2_ + j + 7, j + 5, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + 17 - j, j + 5, j + 7, p_175840_2_ + 17 - j, j + 5, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + 24 - j, j + 1, j, p_175840_2_ + 24 - j, j + 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + j + 1, j + 1, j, p_175840_2_ + 23 - j, j + 1, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + j + 8, j + 5, j + 7, p_175840_2_ + 16 - j, j + 5, j + 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + 4, 4, 4, p_175840_2_ + 6, 4, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + 7, 4, 4, p_175840_2_ + 17, 4, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + 18, 4, 4, p_175840_2_ + 20, 4, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + 11, 8, 11, p_175840_2_ + 13, 8, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.setBlockState(world, DOT_DECO_DATA, p_175840_2_ + 12, 9, 12, p_175840_5_);
				this.setBlockState(world, DOT_DECO_DATA, p_175840_2_ + 12, 9, 15, p_175840_5_);
				this.setBlockState(world, DOT_DECO_DATA, p_175840_2_ + 12, 9, 18, p_175840_5_);
				int j1 = p_175840_2_ + (p_175840_1_ ? 19 : 5);
				int k = p_175840_2_ + (p_175840_1_ ? 5 : 19);

				for (int l = 20; l >= 5; l -= 3)
				{
					this.setBlockState(world, DOT_DECO_DATA, j1, 5, l, p_175840_5_);
				}

				for (int k1 = 19; k1 >= 7; k1 -= 3)
				{
					this.setBlockState(world, DOT_DECO_DATA, k, 5, k1, p_175840_5_);
				}

				for (int l1 = 0; l1 < 4; ++l1)
				{
					int i1 = p_175840_1_ ? p_175840_2_ + (24 - (17 - l1 * 3)) : p_175840_2_ + 17 - l1 * 3;
					this.setBlockState(world, DOT_DECO_DATA, i1, 5, 5, p_175840_5_);
				}

				this.setBlockState(world, DOT_DECO_DATA, k, 5, 5, p_175840_5_);
				this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + 11, 1, 12, p_175840_2_ + 13, 7, 12, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175840_5_, p_175840_2_ + 12, 1, 11, p_175840_2_ + 12, 7, 13, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			}
		}


		private void generateEntranceArchs(IWorld world, Random p_175839_2_, MutableBoundingBox p_175839_3_)
		{
			if (this.doesChunkIntersect(p_175839_3_, 22, 5, 35, 17))
			{

				for (int i = 0; i < 4; ++i)
				{
					this.fillWithBlocks(world, p_175839_3_, 24, 2, 5 + i * 4, 24, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, p_175839_3_, 22, 4, 5 + i * 4, 23, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.setBlockState(world, BRICKS_PRISMARINE, 25, 5, 5 + i * 4, p_175839_3_);
					this.setBlockState(world, BRICKS_PRISMARINE, 26, 6, 5 + i * 4, p_175839_3_);
					this.setBlockState(world, SEA_LANTERN, 26, 5, 5 + i * 4, p_175839_3_);
					this.fillWithBlocks(world, p_175839_3_, 33, 2, 5 + i * 4, 33, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, p_175839_3_, 34, 4, 5 + i * 4, 35, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.setBlockState(world, BRICKS_PRISMARINE, 32, 5, 5 + i * 4, p_175839_3_);
					this.setBlockState(world, BRICKS_PRISMARINE, 31, 6, 5 + i * 4, p_175839_3_);
					this.setBlockState(world, SEA_LANTERN, 31, 5, 5 + i * 4, p_175839_3_);
					this.fillWithBlocks(world, p_175839_3_, 27, 6, 5 + i * 4, 30, 6, 5 + i * 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				}
			}
		}


		private void generateEntranceWall(IWorld world, Random p_175837_2_, MutableBoundingBox p_175837_3_)
		{
			if (this.doesChunkIntersect(p_175837_3_, 15, 20, 42, 21))
			{
				this.fillWithBlocks(world, p_175837_3_, 15, 0, 21, 42, 0, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 21, 12, 21, 36, 12, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 17, 11, 21, 40, 11, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 16, 10, 21, 41, 10, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 15, 7, 21, 42, 9, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 16, 6, 21, 41, 6, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 17, 5, 21, 40, 5, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 21, 4, 21, 36, 4, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 22, 3, 21, 26, 3, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 31, 3, 21, 35, 3, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 23, 2, 21, 25, 2, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 32, 2, 21, 34, 2, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175837_3_, 28, 4, 20, 29, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.setBlockState(world, BRICKS_PRISMARINE, 27, 3, 21, p_175837_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 30, 3, 21, p_175837_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 26, 2, 21, p_175837_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 31, 2, 21, p_175837_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 25, 1, 21, p_175837_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 32, 1, 21, p_175837_3_);

				for (int i = 0; i < 7; ++i)
				{
					this.setBlockState(world, DARK_PRISMARINE, 28 - i, 6 + i, 21, p_175837_3_);
					this.setBlockState(world, DARK_PRISMARINE, 29 + i, 6 + i, 21, p_175837_3_);
				}

				for (int j = 0; j < 4; ++j)
				{
					this.setBlockState(world, DARK_PRISMARINE, 28 - j, 9 + j, 21, p_175837_3_);
					this.setBlockState(world, DARK_PRISMARINE, 29 + j, 9 + j, 21, p_175837_3_);
				}

				this.setBlockState(world, DARK_PRISMARINE, 28, 12, 21, p_175837_3_);
				this.setBlockState(world, DARK_PRISMARINE, 29, 12, 21, p_175837_3_);

				for (int k = 0; k < 3; ++k)
				{
					this.setBlockState(world, DARK_PRISMARINE, 22 - k * 2, 8, 21, p_175837_3_);
					this.setBlockState(world, DARK_PRISMARINE, 22 - k * 2, 9, 21, p_175837_3_);
					this.setBlockState(world, DARK_PRISMARINE, 35 + k * 2, 8, 21, p_175837_3_);
					this.setBlockState(world, DARK_PRISMARINE, 35 + k * 2, 9, 21, p_175837_3_);
				}

			}
		}


		private void generateRoofPiece(IWorld world, Random p_175841_2_, MutableBoundingBox p_175841_3_)
		{
			if (this.doesChunkIntersect(p_175841_3_, 21, 21, 36, 36))
			{
				this.fillWithBlocks(world, p_175841_3_, 21, 0, 22, 36, 0, 36, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

				for (int i = 0; i < 4; ++i)
				{
					this.fillWithBlocks(world, p_175841_3_, 21 + i, 13 + i, 21 + i, 36 - i, 13 + i, 21 + i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, p_175841_3_, 21 + i, 13 + i, 36 - i, 36 - i, 13 + i, 36 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, p_175841_3_, 21 + i, 13 + i, 22 + i, 21 + i, 13 + i, 35 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, p_175841_3_, 36 - i, 13 + i, 22 + i, 36 - i, 13 + i, 35 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				this.fillWithBlocks(world, p_175841_3_, 25, 16, 25, 32, 16, 32, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175841_3_, 25, 17, 25, 25, 19, 25, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, p_175841_3_, 32, 17, 25, 32, 19, 25, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, p_175841_3_, 25, 17, 32, 25, 19, 32, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, p_175841_3_, 32, 17, 32, 32, 19, 32, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.setBlockState(world, BRICKS_PRISMARINE, 26, 20, 26, p_175841_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 27, 21, 27, p_175841_3_);
				this.setBlockState(world, SEA_LANTERN, 27, 20, 27, p_175841_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 26, 20, 31, p_175841_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 27, 21, 30, p_175841_3_);
				this.setBlockState(world, SEA_LANTERN, 27, 20, 30, p_175841_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 31, 20, 31, p_175841_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 30, 21, 30, p_175841_3_);
				this.setBlockState(world, SEA_LANTERN, 30, 20, 30, p_175841_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 31, 20, 26, p_175841_3_);
				this.setBlockState(world, BRICKS_PRISMARINE, 30, 21, 27, p_175841_3_);
				this.setBlockState(world, SEA_LANTERN, 30, 20, 27, p_175841_3_);
				this.fillWithBlocks(world, p_175841_3_, 28, 21, 27, 29, 21, 27, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175841_3_, 27, 21, 28, 27, 21, 29, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175841_3_, 28, 21, 30, 29, 21, 30, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175841_3_, 30, 21, 28, 30, 21, 29, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			}
		}


		private void generateLowerWall(IWorld world, Random p_175835_2_, MutableBoundingBox p_175835_3_)
		{
			if (this.doesChunkIntersect(p_175835_3_, 0, 21, 6, 58))
			{
				this.fillWithBlocks(world, p_175835_3_, 0, 0, 21, 6, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175835_3_, 4, 4, 21, 6, 4, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

				for (int i = 0; i < 4; ++i)
				{
					this.fillWithBlocks(world, p_175835_3_, i, i + 1, 21, i, i + 1, 57 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				for (int j = 23; j < 53; j += 3)
				{
					this.setBlockState(world, DOT_DECO_DATA, 5, 5, j, p_175835_3_);
				}

				this.setBlockState(world, DOT_DECO_DATA, 5, 5, 52, p_175835_3_);

				for (int k = 0; k < 4; ++k)
				{
					this.fillWithBlocks(world, p_175835_3_, k, k + 1, 21, k, k + 1, 57 - k, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				this.fillWithBlocks(world, p_175835_3_, 4, 1, 52, 6, 3, 52, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175835_3_, 5, 1, 51, 5, 3, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			}

			if (this.doesChunkIntersect(p_175835_3_, 51, 21, 58, 58))
			{
				this.fillWithBlocks(world, p_175835_3_, 51, 0, 21, 57, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175835_3_, 51, 4, 21, 53, 4, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

				for (int l = 0; l < 4; ++l)
				{
					this.fillWithBlocks(world, p_175835_3_, 57 - l, l + 1, 21, 57 - l, l + 1, 57 - l, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				for (int i1 = 23; i1 < 53; i1 += 3)
				{
					this.setBlockState(world, DOT_DECO_DATA, 52, 5, i1, p_175835_3_);
				}

				this.setBlockState(world, DOT_DECO_DATA, 52, 5, 52, p_175835_3_);
				this.fillWithBlocks(world, p_175835_3_, 51, 1, 52, 53, 3, 52, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175835_3_, 52, 1, 51, 52, 3, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			}

			if (this.doesChunkIntersect(p_175835_3_, 0, 51, 57, 57))
			{
				this.fillWithBlocks(world, p_175835_3_, 7, 0, 51, 50, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

				for (int j1 = 0; j1 < 4; ++j1)
				{
					this.fillWithBlocks(world, p_175835_3_, j1 + 1, j1 + 1, 57 - j1, 56 - j1, j1 + 1, 57 - j1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
			}
		}


		private void generateMiddleWall(IWorld world, Random p_175842_2_, MutableBoundingBox p_175842_3_)
		{
			if (this.doesChunkIntersect(p_175842_3_, 7, 21, 13, 50))
			{
				this.fillWithBlocks(world, p_175842_3_, 7, 0, 21, 13, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175842_3_, 11, 8, 21, 13, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

				for (int i = 0; i < 4; ++i)
				{
					this.fillWithBlocks(world, p_175842_3_, i + 7, i + 5, 21, i + 7, i + 5, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				for (int j = 21; j <= 45; j += 3)
				{
					this.setBlockState(world, DOT_DECO_DATA, 12, 9, j, p_175842_3_);
				}
			}

			if (this.doesChunkIntersect(p_175842_3_, 44, 21, 50, 54))
			{
				this.fillWithBlocks(world, p_175842_3_, 44, 0, 21, 50, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175842_3_, 44, 8, 21, 46, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

				for (int k = 0; k < 4; ++k)
				{
					this.fillWithBlocks(world, p_175842_3_, 50 - k, k + 5, 21, 50 - k, k + 5, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				for (int l = 21; l <= 45; l += 3)
				{
					this.setBlockState(world, DOT_DECO_DATA, 45, 9, l, p_175842_3_);
				}
			}

			if (this.doesChunkIntersect(p_175842_3_, 8, 44, 49, 54))
			{
				this.fillWithBlocks(world, p_175842_3_, 14, 0, 44, 43, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

				for (int i1 = 12; i1 <= 45; i1 += 3)
				{
					this.setBlockState(world, DOT_DECO_DATA, i1, 9, 45, p_175842_3_);
					this.setBlockState(world, DOT_DECO_DATA, i1, 9, 52, p_175842_3_);

					if (i1 == 12 || i1 == 18 || i1 == 24 || i1 == 33 || i1 == 39 || i1 == 45)
					{
						this.setBlockState(world, DOT_DECO_DATA, i1, 9, 47, p_175842_3_);
						this.setBlockState(world, DOT_DECO_DATA, i1, 9, 50, p_175842_3_);
						this.setBlockState(world, DOT_DECO_DATA, i1, 10, 45, p_175842_3_);
						this.setBlockState(world, DOT_DECO_DATA, i1, 10, 46, p_175842_3_);
						this.setBlockState(world, DOT_DECO_DATA, i1, 10, 51, p_175842_3_);
						this.setBlockState(world, DOT_DECO_DATA, i1, 10, 52, p_175842_3_);
						this.setBlockState(world, DOT_DECO_DATA, i1, 11, 47, p_175842_3_);
						this.setBlockState(world, DOT_DECO_DATA, i1, 11, 50, p_175842_3_);
						this.setBlockState(world, DOT_DECO_DATA, i1, 12, 48, p_175842_3_);
						this.setBlockState(world, DOT_DECO_DATA, i1, 12, 49, p_175842_3_);
					}
				}

				for (int j1 = 0; j1 < 3; ++j1)
				{
					this.fillWithBlocks(world, p_175842_3_, 8 + j1, 5 + j1, 54, 49 - j1, 5 + j1, 54, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				}

				this.fillWithBlocks(world, p_175842_3_, 11, 8, 54, 46, 8, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, p_175842_3_, 14, 8, 44, 43, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			}
		}


		private void generateUpperWall(IWorld world, Random p_175838_2_, MutableBoundingBox p_175838_3_)
		{
			if (this.doesChunkIntersect(p_175838_3_, 14, 21, 20, 43))
			{
				this.fillWithBlocks(world, p_175838_3_, 14, 0, 21, 20, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175838_3_, 18, 12, 22, 20, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175838_3_, 18, 12, 21, 20, 12, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);

				for (int i = 0; i < 4; ++i)
				{
					this.fillWithBlocks(world, p_175838_3_, i + 14, i + 9, 21, i + 14, i + 9, 43 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				for (int j = 23; j <= 39; j += 3)
				{
					this.setBlockState(world, DOT_DECO_DATA, 19, 13, j, p_175838_3_);
				}
			}

			if (this.doesChunkIntersect(p_175838_3_, 37, 21, 43, 43))
			{
				this.fillWithBlocks(world, p_175838_3_, 37, 0, 21, 43, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175838_3_, 37, 12, 22, 39, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175838_3_, 37, 12, 21, 39, 12, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);

				for (int k = 0; k < 4; ++k)
				{
					this.fillWithBlocks(world, p_175838_3_, 43 - k, k + 9, 21, 43 - k, k + 9, 43 - k, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				for (int l = 23; l <= 39; l += 3)
				{
					this.setBlockState(world, DOT_DECO_DATA, 38, 13, l, p_175838_3_);
				}
			}

			if (this.doesChunkIntersect(p_175838_3_, 15, 37, 42, 43))
			{
				this.fillWithBlocks(world, p_175838_3_, 21, 0, 37, 36, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175838_3_, 21, 12, 37, 36, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

				for (int i1 = 0; i1 < 4; ++i1)
				{
					this.fillWithBlocks(world, p_175838_3_, 15 + i1, i1 + 9, 43 - i1, 42 - i1, i1 + 9, 43 - i1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				for (int j1 = 21; j1 <= 36; j1 += 3)
				{
					this.setBlockState(world, DOT_DECO_DATA, j1, 13, 38, p_175838_3_);
				}
			}
		}
	}
	
	
	public static class DoubleXRoom extends OceanMonumentPiecesUA.Piece
	{
		public DoubleXRoom(Direction p_i50661_1_, OceanMonumentPiecesUA.RoomDefinition p_i50661_2_)
		{
			super(StructureInitUA.OMDXRUA, 1, p_i50661_1_, p_i50661_2_, 2, 1, 1);
		}


		public DoubleXRoom(TemplateManager p_i50662_1_, CompoundNBT p_i50662_2_)
		{
			super(StructureInitUA.OMDXRUA, p_i50662_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			if (this.roomDefinition.index / 25 > 0)
			{
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 8, 0, true);
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 0, 0, true);
			}

			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 15, 3, 0, 15, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 0, 15, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 7, 14, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 2, 0, 0, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 15, 2, 0, 15, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 0, 15, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 7, 14, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 15, 1, 0, 15, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 0, 15, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 7, 14, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 0, 10, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 2, 0, 9, 2, 3, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 3, 0, 10, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.setBlockState(world, SEA_LANTERN, 6, 2, 3, mutableBoundingBoxIn);
			this.setBlockState(world, SEA_LANTERN, 9, 2, 3, mutableBoundingBoxIn);
			this.spawnGuardian(world, mutableBoundingBoxIn, 7, 2, 5);
			this.spawnGuardian(world, mutableBoundingBoxIn, 6, 2, 5);

			return true;
		}
	}

	public static class DoubleXYRoom extends OceanMonumentPiecesUA.Piece
	{
		public DoubleXYRoom(Direction p_i50659_1_, OceanMonumentPiecesUA.RoomDefinition p_i50659_2_)
		{
			super(StructureInitUA.OMDXYRUA, 1, p_i50659_1_, p_i50659_2_, 2, 2, 1);
		}


		public DoubleXYRoom(TemplateManager p_i50660_1_, CompoundNBT p_i50660_2_)
		{
			super(StructureInitUA.OMDXYRUA, p_i50660_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			if (this.roomDefinition.index / 25 > 0)
			{
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 8, 0, true);
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 0, 0, true);
			}

			for (int i = 1; i <= 7; ++i)
			{
				BlockState iblockstate = BRICKS_PRISMARINE;

				if (i == 2 || i == 6)
				{
					iblockstate = ROUGH_PRISMARINE;
				}

				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, i, 0, 0, i, 7, iblockstate, iblockstate, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 15, i, 0, 15, i, 7, iblockstate, iblockstate, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, i, 0, 15, i, 0, iblockstate, iblockstate, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, i, 7, 14, i, 7, iblockstate, iblockstate, false);
			}

			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 1, 3, 2, 7, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 2, 4, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 5, 4, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 13, 1, 3, 13, 7, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 11, 1, 2, 12, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 11, 1, 5, 12, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 3, 5, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 1, 3, 10, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 7, 2, 10, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 5, 2, 5, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 5, 2, 10, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 5, 5, 5, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 5, 5, 10, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.setBlockState(world, BRICKS_PRISMARINE, 6, 6, 2, mutableBoundingBoxIn);
			this.setBlockState(world, BRICKS_PRISMARINE, 9, 6, 2, mutableBoundingBoxIn);
			this.setBlockState(world, BRICKS_PRISMARINE, 6, 6, 5, mutableBoundingBoxIn);
			this.setBlockState(world, BRICKS_PRISMARINE, 9, 6, 5, mutableBoundingBoxIn);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 4, 3, 6, 4, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 9, 4, 3, 10, 4, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.setBlockState(world, SEA_LANTERN, 5, 4, 2, mutableBoundingBoxIn);
			this.setBlockState(world, SEA_LANTERN, 5, 4, 5, mutableBoundingBoxIn);
			this.setBlockState(world, SEA_LANTERN, 10, 4, 2, mutableBoundingBoxIn);
			this.setBlockState(world, SEA_LANTERN, 10, 4, 5, mutableBoundingBoxIn);
			this.spawnGuardian(world, mutableBoundingBoxIn, 7, 2, 3);
			this.spawnGuardian(world, mutableBoundingBoxIn, 7, 2, 3);

			return true;

		}

	}

	public static class DoubleYRoom extends OceanMonumentPiecesUA.Piece
	{
		public DoubleYRoom(Direction p_i50657_1_, OceanMonumentPiecesUA.RoomDefinition p_i50657_2_)
		{
			super(StructureInitUA.OMDYRUA, 1, p_i50657_1_, p_i50657_2_, 1, 2, 1);
		}


		public DoubleYRoom(TemplateManager p_i50658_1_, CompoundNBT p_i50658_2_)
		{
			super(StructureInitUA.OMDYRUA, p_i50658_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			if (this.roomDefinition.index / 25 > 0)
			{
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 0, 0, true);
			}

			OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.roomDefinition.connections[Direction.UP.getIndex()];

			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 4, 0, 0, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 4, 0, 7, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 4, 0, 6, 4, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 4, 7, 6, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 4, 1, 2, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 4, 2, 1, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 4, 1, 5, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 4, 2, 6, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 4, 5, 2, 4, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 4, 5, 1, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 4, 5, 5, 4, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 4, 5, 6, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);

			this.spawnGuardian(world, mutableBoundingBoxIn, 3, 5, 3);
			this.spawnGuardian(world, mutableBoundingBoxIn, 3, 5, 4);
			OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.roomDefinition;

			for (int i = 1; i <= 5; i += 4)
			{
				int j = 0;

				if (structureoceanmonumentpieces$roomdefinition1.hasOpening[Direction.SOUTH.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 2, i, j, 2, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 5, i, j, 5, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 3, i + 2, j, 4, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
				else
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, i, j, 7, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, i + 1, j, 7, i + 1, j, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				}

				j = 7;

				if (structureoceanmonumentpieces$roomdefinition1.hasOpening[Direction.NORTH.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 2, i, j, 2, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 5, i, j, 5, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 3, i + 2, j, 4, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
				else
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, i, j, 7, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, i + 1, j, 7, i + 1, j, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				}

				int k = 0;

				if (structureoceanmonumentpieces$roomdefinition1.hasOpening[Direction.WEST.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, k, i, 2, k, i + 2, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, k, i, 5, k, i + 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, k, i + 2, 3, k, i + 2, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
				else
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, k, i, 0, k, i + 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, k, i + 1, 0, k, i + 1, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				}

				k = 7;

				if (structureoceanmonumentpieces$roomdefinition1.hasOpening[Direction.EAST.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, k, i, 2, k, i + 2, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, k, i, 5, k, i + 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, k, i + 2, 3, k, i + 2, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
				else
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, k, i, 0, k, i + 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, k, i + 1, 0, k, i + 1, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				}

				structureoceanmonumentpieces$roomdefinition1 = structureoceanmonumentpieces$roomdefinition;
			}

			return true;
		}
	}

	public static class DoubleYZRoom extends OceanMonumentPiecesUA.Piece
	{
		public DoubleYZRoom(Direction p_i50655_1_, OceanMonumentPiecesUA.RoomDefinition p_i50655_2_)
		{
			super(StructureInitUA.OMDYZRUA, 1, p_i50655_1_, p_i50655_2_, 1, 2, 2);
		}


		public DoubleYZRoom(TemplateManager p_i50656_1_, CompoundNBT p_i50656_2_)
		{
			super(StructureInitUA.OMDYZRUA, p_i50656_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.roomDefinition.connections[Direction.NORTH.getIndex()];
			OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.roomDefinition;
			OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition2 = structureoceanmonumentpieces$roomdefinition.connections[Direction.UP.getIndex()];
			OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition3 = structureoceanmonumentpieces$roomdefinition1.connections[Direction.UP.getIndex()];

			if (this.roomDefinition.index / 25 > 0)
			{
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 0, 8, true);
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 0, 0, true);
			}

			if (structureoceanmonumentpieces$roomdefinition3.connections[Direction.UP.getIndex()] == null)
			{
				this.generateBoxOnFillOnly(world, mutableBoundingBoxIn, 1, 8, 1, 6, 8, 7, ROUGH_PRISMARINE);
			}

			if (structureoceanmonumentpieces$roomdefinition2.connections[Direction.UP.getIndex()] == null)
			{
				this.generateBoxOnFillOnly(world, mutableBoundingBoxIn, 1, 8, 8, 6, 8, 14, ROUGH_PRISMARINE);
			}

			for (int i = 1; i <= 7; ++i)
			{
				BlockState iblockstate = BRICKS_PRISMARINE;

				if (i == 2 || i == 6)
				{
					iblockstate = ROUGH_PRISMARINE;
				}

				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, i, 0, 0, i, 15, iblockstate, iblockstate, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, i, 0, 7, i, 15, iblockstate, iblockstate, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, i, 0, 6, i, 0, iblockstate, iblockstate, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, i, 15, 6, i, 15, iblockstate, iblockstate, false);
			}

			for (int j = 1; j <= 7; ++j)
			{
				BlockState iblockstate1 = DARK_PRISMARINE;

				if (j == 2 || j == 6)
				{
					iblockstate1 = SEA_LANTERN;
				}

				this.fillWithBlocks(world, mutableBoundingBoxIn, 3, j, 7, 4, j, 8, iblockstate1, iblockstate1, false);
			}

			this.spawnGuardian(world, mutableBoundingBoxIn, 3, 2, 3);
			this.spawnGuardian(world, mutableBoundingBoxIn, 2, 2, 3);

			if (structureoceanmonumentpieces$roomdefinition3.hasOpening[Direction.EAST.getIndex()])
			{
				this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 4, 2, 6, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 2, 6, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 5, 6, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			}

			if (structureoceanmonumentpieces$roomdefinition3.hasOpening[Direction.WEST.getIndex()])
			{
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 4, 2, 2, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 2, 1, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 5, 1, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			}

			if (structureoceanmonumentpieces$roomdefinition2.hasOpening[Direction.WEST.getIndex()])
			{
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 4, 10, 2, 4, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 10, 1, 3, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 13, 1, 3, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			}

			if (structureoceanmonumentpieces$roomdefinition2.hasOpening[Direction.EAST.getIndex()])
			{
				this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 4, 10, 6, 4, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 10, 6, 3, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 13, 6, 3, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			}

			return true;

		}
	}

	public static class DoubleZRoom extends OceanMonumentPiecesUA.Piece
	{
		public DoubleZRoom(Direction p_i50653_1_, OceanMonumentPiecesUA.RoomDefinition p_i50653_2_)
		{
			super(StructureInitUA.OMDZRUA, 1, p_i50653_1_, p_i50653_2_, 1, 1, 2);
		}


		public DoubleZRoom(TemplateManager p_i50654_1_, CompoundNBT p_i50654_2_)
		{
			super(StructureInitUA.OMDZRUA, p_i50654_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			if (this.roomDefinition.index / 25 > 0)
			{
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 0, 8, true);
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 0, 0, true);
			}

			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 3, 0, 0, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 3, 0, 7, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 0, 7, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 15, 6, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 2, 0, 0, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 2, 0, 7, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 0, 7, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 15, 6, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 0, 0, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 0, 7, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 0, 7, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 15, 6, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 1, 1, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 1, 6, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 1, 1, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 3, 1, 6, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 13, 1, 1, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 13, 6, 1, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 13, 1, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 3, 13, 6, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 1, 6, 2, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 6, 5, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 1, 9, 2, 3, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 9, 5, 3, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 2, 6, 4, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 2, 9, 4, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 2, 7, 2, 2, 8, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 2, 7, 5, 2, 8, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.setBlockState(world, SEA_LANTERN, 2, 2, 5, mutableBoundingBoxIn);
			this.setBlockState(world, SEA_LANTERN, 5, 2, 5, mutableBoundingBoxIn);
			this.setBlockState(world, SEA_LANTERN, 2, 2, 10, mutableBoundingBoxIn);
			this.setBlockState(world, SEA_LANTERN, 5, 2, 10, mutableBoundingBoxIn);
			this.setBlockState(world, BRICKS_PRISMARINE, 2, 3, 5, mutableBoundingBoxIn);
			this.setBlockState(world, BRICKS_PRISMARINE, 5, 3, 5, mutableBoundingBoxIn);
			this.setBlockState(world, BRICKS_PRISMARINE, 2, 3, 10, mutableBoundingBoxIn);
			this.setBlockState(world, BRICKS_PRISMARINE, 5, 3, 10, mutableBoundingBoxIn);

			this.spawnGuardian(world, mutableBoundingBoxIn, 3, 2, 3);
			this.spawnGuardian(world, mutableBoundingBoxIn, 4, 2, 3);

			return true;

		}
	}

	public static class EntryRoom extends OceanMonumentPiecesUA.Piece
	{
		public EntryRoom(Direction p_i45592_1_, OceanMonumentPiecesUA.RoomDefinition p_i45592_2_)
		{
			super(StructureInitUA.OMENTRYUA, 1, p_i45592_1_, p_i45592_2_, 1, 1, 1);
		}


		public EntryRoom(TemplateManager p_i50652_1_, CompoundNBT p_i50652_2_)
		{
			super(StructureInitUA.OMENTRYUA, p_i50652_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 3, 0, 2, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 2, 0, 1, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 2, 0, 7, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 7, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 0, 2, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);

			this.spawnGuardian(world, mutableBoundingBoxIn, 3, 2, 4);
			this.spawnGuardian(world, mutableBoundingBoxIn, 4, 2, 3);

			return true;

		}
	}

	static class FitSimpleRoomHelper implements OceanMonumentPiecesUA.MonumentRoomFitHelper
	{
		private FitSimpleRoomHelper()
		{
		}


		@Override
		public boolean fits(OceanMonumentPiecesUA.RoomDefinition definition)
		{
			return true;
		}


		@Override
		public OceanMonumentPiecesUA.Piece create(Direction p_175968_1_, OceanMonumentPiecesUA.RoomDefinition p_175968_2_, Random p_175968_3_)
		{
			p_175968_2_.claimed = true;
			return new OceanMonumentPiecesUA.SimpleRoom(p_175968_1_, p_175968_2_, p_175968_3_);
		}
	}

	static class FitSimpleRoomTopHelper implements OceanMonumentPiecesUA.MonumentRoomFitHelper
	{
		private FitSimpleRoomTopHelper()
		{
		}


		@Override
		public boolean fits(OceanMonumentPiecesUA.RoomDefinition definition)
		{
			return !definition.hasOpening[Direction.WEST.getIndex()] && !definition.hasOpening[Direction.EAST.getIndex()] && !definition.hasOpening[Direction.NORTH.getIndex()] && !definition.hasOpening[Direction.SOUTH.getIndex()] && !definition.hasOpening[Direction.UP.getIndex()];
		}


		@Override
		public OceanMonumentPiecesUA.Piece create(Direction p_175968_1_, OceanMonumentPiecesUA.RoomDefinition p_175968_2_, Random p_175968_3_)
		{
			p_175968_2_.claimed = true;
			return new OceanMonumentPiecesUA.SimpleTopRoom(p_175968_1_, p_175968_2_);
		}
	}
	
	public static class MonumentCoreRoom extends OceanMonumentPiecesUA.Piece
	{
		public MonumentCoreRoom(Direction p_i50663_1_, OceanMonumentPiecesUA.RoomDefinition p_i50663_2_)
		{
			super(StructureInitUA.OMCRUA, 1, p_i50663_1_, p_i50663_2_, 2, 2, 2);
		}


		public MonumentCoreRoom(TemplateManager p_i50664_1_, CompoundNBT p_i50664_2_)
		{
			super(StructureInitUA.OMCRUA, p_i50664_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			this.generateBoxOnFillOnly(world, mutableBoundingBoxIn, 1, 8, 0, 14, 8, 14, ROUGH_PRISMARINE);
			int i = 7;
			BlockState iblockstate = BRICKS_PRISMARINE;
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 7, 0, 0, 7, 15, iblockstate, iblockstate, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 15, 7, 0, 15, 7, 15, iblockstate, iblockstate, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 7, 0, 15, 7, 0, iblockstate, iblockstate, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 7, 15, 14, 7, 15, iblockstate, iblockstate, false);

			for (i = 1; i <= 6; ++i)
			{
				iblockstate = BRICKS_PRISMARINE;

				if (i == 2 || i == 6)
				{
					iblockstate = ROUGH_PRISMARINE;
				}

				for (int j = 0; j <= 15; j += 15)
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, j, i, 0, j, i, 1, iblockstate, iblockstate, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, j, i, 6, j, i, 9, iblockstate, iblockstate, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, j, i, 14, j, i, 15, iblockstate, iblockstate, false);
				}

				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, i, 0, 1, i, 0, iblockstate, iblockstate, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 6, i, 0, 9, i, 0, iblockstate, iblockstate, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 14, i, 0, 14, i, 0, iblockstate, iblockstate, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, i, 15, 14, i, 15, iblockstate, iblockstate, false);
			}

			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 3, 6, 9, 6, 9, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState(), false);

			for (i = 3; i <= 6; i += 3)
			{
				for (int k = 6; k <= 9; k += 3)
				{
					this.setBlockState(world, SEA_LANTERN, k, i, 6, mutableBoundingBoxIn);
					this.setBlockState(world, SEA_LANTERN, k, i, 9, mutableBoundingBoxIn);
				}
			}

			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 6, 5, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 9, 5, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 1, 6, 10, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 1, 9, 10, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 5, 6, 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 9, 1, 5, 9, 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 10, 6, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 9, 1, 10, 9, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 2, 5, 5, 6, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 2, 10, 5, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 2, 5, 10, 6, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 2, 10, 10, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 7, 1, 5, 7, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 7, 1, 10, 7, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 7, 9, 5, 7, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 7, 9, 10, 7, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 7, 5, 6, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 7, 10, 6, 7, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 9, 7, 5, 14, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 9, 7, 10, 14, 7, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 1, 2, 2, 1, 3, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 2, 3, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 13, 1, 2, 13, 1, 3, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 12, 1, 2, 12, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 1, 12, 2, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 13, 3, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 13, 1, 12, 13, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 12, 1, 13, 12, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);

			this.spawnGuardian(world, mutableBoundingBoxIn, 12, 3, 11);
			this.spawnGuardian(world, mutableBoundingBoxIn, 11, 3, 11);
			this.spawnGuardian(world, mutableBoundingBoxIn, 11, 3, 12);
			this.spawnGuardian(world, mutableBoundingBoxIn, 12, 3, 12);

			return true;

		}
	}

	interface MonumentRoomFitHelper
	{
		boolean fits(OceanMonumentPiecesUA.RoomDefinition definition);


		OceanMonumentPiecesUA.Piece create(Direction p_175968_1_, OceanMonumentPiecesUA.RoomDefinition p_175968_2_, Random p_175968_3_);
	}

	public static class Penthouse extends OceanMonumentPiecesUA.Piece
	{
		public Penthouse(Direction p_i45591_1_, MutableBoundingBox p_i45591_2_)
		{
			super(StructureInitUA.OMPENTHOUSEUA, p_i45591_1_, p_i45591_2_);
		}


		public Penthouse(TemplateManager p_i50651_1_, CompoundNBT p_i50651_2_)
		{
			super(StructureInitUA.OMPENTHOUSEUA, p_i50651_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, -1, 2, 11, -1, 11, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, -1, 0, 1, -1, 11, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 12, -1, 0, 13, -1, 11, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, -1, 0, 11, -1, 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, -1, 12, 12, -1, 13, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 0, 0, 0, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 13, 0, 0, 13, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 0, 0, 12, 0, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 0, 13, 12, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);

			for (int i = 2; i <= 11; i += 3)
			{
				this.setBlockState(world, SEA_LANTERN, 0, 0, i, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 13, 0, i, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, i, 0, 0, mutableBoundingBoxIn);
			}

			this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 0, 3, 4, 0, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 9, 0, 3, 11, 0, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 4, 0, 9, 9, 0, 11, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.setBlockState(world, BRICKS_PRISMARINE, 5, 0, 8, mutableBoundingBoxIn);
			this.setBlockState(world, BRICKS_PRISMARINE, 8, 0, 8, mutableBoundingBoxIn);
			this.setBlockState(world, BRICKS_PRISMARINE, 10, 0, 10, mutableBoundingBoxIn);
			this.setBlockState(world, BRICKS_PRISMARINE, 3, 0, 10, mutableBoundingBoxIn);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 0, 3, 3, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 0, 3, 10, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 0, 10, 7, 0, 10, DARK_PRISMARINE, DARK_PRISMARINE, false);
			int l = 3;

			for (int j = 0; j < 2; ++j)
			{
				for (int k = 2; k <= 8; k += 3)
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, l, 0, k, l, 2, k, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				l = 10;
			}

			this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 0, 10, 5, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 8, 0, 10, 8, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, -1, 7, 7, -1, 8, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 6, -1, 3, 7, -1, 4, WATER, WATER, false);
			this.spawnElder(world, mutableBoundingBoxIn, 6, 1, 6);
			if (UltraAmplified.UAStructuresConfig.chestGeneration.get())
			{
				this.generateChest(world, mutableBoundingBoxIn, random, 6, 0, 8, LootTables.CHESTS_END_CITY_TREASURE);
				this.generateChest(world, mutableBoundingBoxIn, random, 7, 0, 8, LootTables.CHESTS_END_CITY_TREASURE);
			}
			return true;

		}
	}

	public abstract static class Piece extends StructurePiece
	{
		protected static final BlockState ROUGH_PRISMARINE = Blocks.PRISMARINE.getDefaultState();
		protected static final BlockState BRICKS_PRISMARINE = Blocks.PRISMARINE_BRICKS.getDefaultState();
		protected static final BlockState DARK_PRISMARINE = Blocks.DARK_PRISMARINE.getDefaultState();
		protected static final BlockState DOT_DECO_DATA = BRICKS_PRISMARINE;
		protected static final BlockState SEA_LANTERN = Blocks.SEA_LANTERN.getDefaultState();
		protected static final BlockState WATER = Blocks.WATER.getDefaultState();
		protected static final int GRIDROOM_SOURCE_INDEX = getRoomIndex(2, 0, 0);
		protected static final int GRIDROOM_TOP_CONNECT_INDEX = getRoomIndex(2, 2, 0);
		protected static final int GRIDROOM_LEFTWING_CONNECT_INDEX = getRoomIndex(0, 1, 0);
		protected static final int GRIDROOM_RIGHTWING_CONNECT_INDEX = getRoomIndex(4, 1, 0);
		protected OceanMonumentPiecesUA.RoomDefinition roomDefinition;


		protected static final int getRoomIndex(int p_175820_0_, int p_175820_1_, int p_175820_2_)
		{
			return p_175820_1_ * 25 + p_175820_2_ * 5 + p_175820_0_;
		}


		public Piece(IStructurePieceType p_i50647_1_, int p_i50647_2_)
		{
			super(p_i50647_1_, p_i50647_2_);
		}


		public Piece(IStructurePieceType p_i50648_1_, Direction p_i50648_2_, MutableBoundingBox p_i50648_3_)
		{
			super(p_i50648_1_, 1);
			this.setCoordBaseMode(p_i50648_2_);
			this.boundingBox = p_i50648_3_;
		}


		protected Piece(IStructurePieceType p_i50649_1_, int p_i45590_1_, Direction p_i45590_2_, OceanMonumentPiecesUA.RoomDefinition p_i45590_3_, int p_i45590_4_, int p_i45590_5_, int p_i45590_6_)
		{
			super(p_i50649_1_, p_i45590_1_);
			this.setCoordBaseMode(p_i45590_2_);
			this.roomDefinition = p_i45590_3_;
			int i = p_i45590_3_.index;
			int j = i % 5;
			int k = i / 5 % 5;
			int l = i / 25;

			if (p_i45590_2_ != Direction.NORTH && p_i45590_2_ != Direction.SOUTH)
			{
				this.boundingBox = new MutableBoundingBox(0, 0, 0, p_i45590_6_ * 8 - 1, p_i45590_5_ * 4 - 1, p_i45590_4_ * 8 - 1);
			}
			else
			{
				this.boundingBox = new MutableBoundingBox(0, 0, 0, p_i45590_4_ * 8 - 1, p_i45590_5_ * 4 - 1, p_i45590_6_ * 8 - 1);
			}

			switch (p_i45590_2_)
			{
				case NORTH:
					this.boundingBox.offset(j * 8, l * 4, -(k + p_i45590_6_) * 8 + 1);
					break;

				case SOUTH:
					this.boundingBox.offset(j * 8, l * 4, k * 8);
					break;

				case WEST:
					this.boundingBox.offset(-(k + p_i45590_6_) * 8 + 1, l * 4, j * 8);
					break;

				default:
					this.boundingBox.offset(k * 8, l * 4, j * 8);
			}
		}


		public Piece(IStructurePieceType p_i50650_1_, CompoundNBT p_i50650_2_)
		{
			super(p_i50650_1_, p_i50650_2_);
		}


		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void readAdditional(CompoundNBT tagCompound)
		{
		}


		protected void generateWaterBox(IWorld world, MutableBoundingBox mutableBoundingBox)
		{

			// right leg
			this.fillWithBlocks(world, mutableBoundingBox, 0, 1, 0, 24, 1, 57, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 1, 2, 1, 23, 2, 56, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 2, 3, 2, 22, 3, 55, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 3, 4, 3, 21, 4, 54, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 7, 5, 7, 17, 5, 54, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 8, 6, 8, 16, 6, 54, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 9, 7, 9, 15, 7, 54, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 10, 8, 10, 14, 8, 54, WATER, WATER, false);

			// left leg
			this.fillWithBlocks(world, mutableBoundingBox, 33, 1, 0, 57, 1, 57, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 34, 2, 1, 56, 2, 56, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 35, 3, 2, 55, 3, 55, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 36, 4, 3, 54, 4, 54, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 40, 5, 7, 50, 5, 54, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 41, 6, 8, 49, 6, 54, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 42, 7, 9, 48, 7, 54, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 43, 8, 10, 47, 8, 54, WATER, WATER, false);

			// main body
			this.fillWithBlocks(world, mutableBoundingBox, 14, 1, 22, 44, 1, 57, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 14, 2, 22, 44, 2, 56, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 14, 1, 22, 44, 8, 54, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 14, 1, 22, 44, 8, 54, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 15, 8, 21, 43, 9, 42, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 16, 10, 21, 42, 10, 41, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 17, 11, 21, 41, 11, 40, WATER, WATER, false);

			// top
			this.fillWithBlocks(world, mutableBoundingBox, 21, 12, 21, 36, 12, 36, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 22, 13, 22, 35, 13, 35, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 23, 14, 23, 34, 14, 34, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 24, 15, 24, 33, 15, 33, WATER, WATER, false);

			// creates opening in front of entrance
			this.fillWithBlocks(world, mutableBoundingBox, 25, 0, 10, 32, 3, 20, DARK_PRISMARINE, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 25, 0, 10, 32, 0, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBox, 32, 0, 10, 32, 0, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBox, 25, 0, 10, 25, 0, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBox, 25, 3, 10, 32, 3, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBox, 32, 3, 10, 32, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBox, 25, 3, 10, 25, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBox, 25, 1, 10, 25, 2, 10, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBox, 32, 1, 10, 32, 2, 10, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			this.setBlockState(world, SEA_LANTERN, 25, 1, 12, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 32, 1, 12, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 25, 1, 14, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 32, 1, 14, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 25, 1, 16, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 32, 1, 16, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 25, 1, 18, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 32, 1, 18, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 25, 1, 20, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 32, 1, 20, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 27, 1, 10, mutableBoundingBox);
			this.setBlockState(world, SEA_LANTERN, 30, 1, 10, mutableBoundingBox);
			this.fillWithBlocks(world, mutableBoundingBox, 26, 3, 11, 31, 3, 20, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 26, 1, 20, 31, 3, 20, WATER, WATER, false);
			this.fillWithBlocks(world, mutableBoundingBox, 26, 1, 21, 31, 3, 21, WATER, WATER, false);

			// clears out all solid blocks in a column until it hits non-solid blocks to
			// create an opening to entrance
			for (int y = 4; y < 16; y++)
			{
				for (int x = 26; x < 32; x++)
				{
					for (int z = 11; z < 21; z++)
					{
						if (this.getBlockStateFromPos(world, x, y, z, mutableBoundingBox).isSolid())
						{
							this.setBlockState(world, WATER, x, y, z, mutableBoundingBox);
						}
						else
						{
							x = 32;
							y = 16;
							break;
						}
					}
				}
			}
		}


		protected void generateDefaultFloor(IWorld world, MutableBoundingBox p_175821_2_, int x, int z, boolean hasOpeningDownwards)
		{
			if (hasOpeningDownwards)
			{
				this.fillWithBlocks(world, p_175821_2_, x + 0, 0, z + 0, x + 2, 0, z + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175821_2_, x + 5, 0, z + 0, x + 8 - 1, 0, z + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175821_2_, x + 3, 0, z + 0, x + 4, 0, z + 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175821_2_, x + 3, 0, z + 5, x + 4, 0, z + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, p_175821_2_, x + 3, 0, z + 2, x + 4, 0, z + 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, p_175821_2_, x + 3, 0, z + 5, x + 4, 0, z + 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, p_175821_2_, x + 2, 0, z + 3, x + 2, 0, z + 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, p_175821_2_, x + 5, 0, z + 3, x + 5, 0, z + 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			}
			else
			{
				this.fillWithBlocks(world, p_175821_2_, x + 0, 0, z + 0, x + 8 - 1, 0, z + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
			}

		}


		protected void generateBoxOnFillOnly(IWorld world, MutableBoundingBox p_175819_2_, int p_175819_3_, int p_175819_4_, int p_175819_5_, int p_175819_6_, int p_175819_7_, int p_175819_8_, BlockState p_175819_9_)
		{

			for (int i = p_175819_4_; i <= p_175819_7_; ++i)
			{
				for (int j = p_175819_3_; j <= p_175819_6_; ++j)
				{
					for (int k = p_175819_5_; k <= p_175819_8_; ++k)
					{
						if (this.getBlockStateFromPos(world, j, i, k, p_175819_2_) == WATER)
						{
							this.setBlockState(world, p_175819_9_, j, i, k, p_175819_2_);
						}
					}
				}
			}
		}


		protected boolean doesChunkIntersect(MutableBoundingBox p_175818_1_, int p_175818_2_, int p_175818_3_, int p_175818_4_, int p_175818_5_)
		{
			int i = this.getXWithOffset(p_175818_2_, p_175818_3_);
			int j = this.getZWithOffset(p_175818_2_, p_175818_3_);
			int k = this.getXWithOffset(p_175818_4_, p_175818_5_);
			int l = this.getZWithOffset(p_175818_4_, p_175818_5_);
			return p_175818_1_.intersectsWith(Math.min(i, k), Math.min(j, l), Math.max(i, k), Math.max(j, l));
		}


		protected boolean spawnElder(IWorld world, MutableBoundingBox p_175817_2_, int p_175817_3_, int p_175817_4_, int p_175817_5_)
		{
			int i = this.getXWithOffset(p_175817_3_, p_175817_5_);
			int j = this.getYWithOffset(p_175817_4_);
			int k = this.getZWithOffset(p_175817_3_, p_175817_5_);

			if (p_175817_2_.isVecInside(new BlockPos(i, j, k)))
			{
				ElderGuardianEntity entityelderguardian = EntityType.ELDER_GUARDIAN.create(world.getWorld());
				entityelderguardian.heal(entityelderguardian.getMaxHealth());
				entityelderguardian.setLocationAndAngles(i + 0.5D, j, k + 0.5D, 0.0F, 0.0F);
				entityelderguardian.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(entityelderguardian)), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
				world.addEntity(entityelderguardian);
				return true;
			}
			else
			{
				return false;
			}
		}


		protected boolean spawnGuardian(IWorld world, MutableBoundingBox p_175817_2_, int p_175817_3_, int p_175817_4_, int p_175817_5_)
		{
			int i = this.getXWithOffset(p_175817_3_, p_175817_5_);
			int j = this.getYWithOffset(p_175817_4_);
			int k = this.getZWithOffset(p_175817_3_, p_175817_5_);

			if (p_175817_2_.isVecInside(new BlockPos(i, j, k)))
			{
				GuardianEntity entityguardian = EntityType.GUARDIAN.create(world.getWorld());
				entityguardian.heal(entityguardian.getMaxHealth());
				entityguardian.setLocationAndAngles(i + 0.5D, j, k + 0.5D, 0.0F, 0.0F);
				entityguardian.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(entityguardian)), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
				world.addEntity(entityguardian);
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	static class RoomDefinition
	{
		int index;
		OceanMonumentPiecesUA.RoomDefinition[] connections = new OceanMonumentPiecesUA.RoomDefinition[6];
		boolean[] hasOpening = new boolean[6];
		boolean claimed;
		boolean isSource;
		int scanIndex;


		public RoomDefinition(int p_i45584_1_)
		{
			this.index = p_i45584_1_;
		}


		public void setConnection(Direction facing, OceanMonumentPiecesUA.RoomDefinition definition)
		{
			this.connections[facing.getIndex()] = definition;
			definition.connections[facing.getOpposite().getIndex()] = this;
		}


		public void updateOpenings()
		{
			for (int i = 0; i < 6; ++i)
			{
				this.hasOpening[i] = this.connections[i] != null;
			}
		}


		public boolean findSource(int p_175959_1_)
		{
			if (this.isSource)
			{
				return true;
			}
			else
			{
				this.scanIndex = p_175959_1_;

				for (int i = 0; i < 6; ++i)
				{
					if (this.connections[i] != null && this.hasOpening[i] && this.connections[i].scanIndex != p_175959_1_ && this.connections[i].findSource(p_175959_1_))
					{
						return true;
					}
				}

				return false;
			}
		}


		public boolean isSpecial()
		{
			return this.index >= 75;
		}


		public int countOpenings()
		{
			int i = 0;

			for (int j = 0; j < 6; ++j)
			{
				if (this.hasOpening[j])
				{
					++i;
				}
			}

			return i;
		}
	}

	public static class SimpleRoom extends OceanMonumentPiecesUA.Piece
	{
		private int mainDesign;


		public SimpleRoom(Direction p_i45587_1_, OceanMonumentPiecesUA.RoomDefinition p_i45587_2_, Random p_i45587_3_)
		{
			super(StructureInitUA.OMSIMPLEUA, 1, p_i45587_1_, p_i45587_2_, 1, 1, 1);
			this.mainDesign = p_i45587_3_.nextInt(3);
		}


		public SimpleRoom(TemplateManager p_i50646_1_, CompoundNBT p_i50646_2_)
		{
			super(StructureInitUA.OMSIMPLEUA, p_i50646_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			if (this.roomDefinition.index / 25 > 0)
			{
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 0, 0, true);
			}

			boolean flag = this.mainDesign != 0 && random.nextBoolean() && !this.roomDefinition.hasOpening[Direction.DOWN.getIndex()] && !this.roomDefinition.hasOpening[Direction.UP.getIndex()] && this.roomDefinition.countOpenings() > 1;

			if (this.mainDesign == 0)
			{
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 0, 2, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 3, 0, 2, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 2, 0, 0, 2, 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 0, 2, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.setBlockState(world, SEA_LANTERN, 1, 2, 1, mutableBoundingBoxIn);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 0, 7, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 3, 0, 7, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 2, 0, 7, 2, 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 2, 0, 6, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.setBlockState(world, SEA_LANTERN, 6, 2, 1, mutableBoundingBoxIn);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 5, 2, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 3, 5, 2, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 2, 5, 0, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 7, 2, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.setBlockState(world, SEA_LANTERN, 1, 2, 6, mutableBoundingBoxIn);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 5, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 3, 5, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 2, 5, 7, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 2, 7, 6, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.setBlockState(world, SEA_LANTERN, 6, 2, 6, mutableBoundingBoxIn);

				if (this.roomDefinition.hasOpening[Direction.SOUTH.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 3, 0, 4, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
				else
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 3, 0, 4, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 2, 0, 4, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 0, 4, 1, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				if (this.roomDefinition.hasOpening[Direction.NORTH.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 3, 7, 4, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
				else
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 3, 6, 4, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 2, 7, 4, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 6, 4, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				if (this.roomDefinition.hasOpening[Direction.WEST.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 3, 3, 0, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
				else
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 3, 3, 1, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 2, 3, 0, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 3, 1, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				if (this.roomDefinition.hasOpening[Direction.EAST.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 3, 3, 7, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
				else
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 3, 3, 7, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 2, 3, 7, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 3, 7, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
			}
			else if (this.mainDesign == 1)
			{
				this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 1, 2, 2, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 2, 1, 5, 2, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 5, 5, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 5, 1, 2, 5, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.setBlockState(world, SEA_LANTERN, 2, 2, 2, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 2, 2, 5, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 5, 2, 5, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 5, 2, 2, mutableBoundingBoxIn);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 0, 1, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 1, 0, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 7, 1, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 6, 0, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 7, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 6, 7, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 1, 0, 7, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 1, 7, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.setBlockState(world, ROUGH_PRISMARINE, 1, 2, 0, mutableBoundingBoxIn);
				this.setBlockState(world, ROUGH_PRISMARINE, 0, 2, 1, mutableBoundingBoxIn);
				this.setBlockState(world, ROUGH_PRISMARINE, 1, 2, 7, mutableBoundingBoxIn);
				this.setBlockState(world, ROUGH_PRISMARINE, 0, 2, 6, mutableBoundingBoxIn);
				this.setBlockState(world, ROUGH_PRISMARINE, 6, 2, 7, mutableBoundingBoxIn);
				this.setBlockState(world, ROUGH_PRISMARINE, 7, 2, 6, mutableBoundingBoxIn);
				this.setBlockState(world, ROUGH_PRISMARINE, 6, 2, 0, mutableBoundingBoxIn);
				this.setBlockState(world, ROUGH_PRISMARINE, 7, 2, 1, mutableBoundingBoxIn);

				if (!this.roomDefinition.hasOpening[Direction.SOUTH.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 0, 6, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				if (!this.roomDefinition.hasOpening[Direction.NORTH.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 7, 6, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				if (!this.roomDefinition.hasOpening[Direction.WEST.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 3, 1, 0, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 2, 1, 0, 2, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 1, 0, 1, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				if (!this.roomDefinition.hasOpening[Direction.EAST.getIndex()])
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 3, 1, 7, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 2, 1, 7, 2, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 1, 7, 1, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}
			}
			else if (this.mainDesign == 2)
			{
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);

			}

			this.spawnGuardian(world, mutableBoundingBoxIn, 3, 2, 2);
			this.spawnGuardian(world, mutableBoundingBoxIn, 3, 2, 3);

			if (flag)
			{
				this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 3, 4, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 2, 3, 4, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 3, 3, 4, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			}

			return true;

		}
	}

	public static class SimpleTopRoom extends OceanMonumentPiecesUA.Piece
	{
		public SimpleTopRoom(Direction p_i50644_1_, OceanMonumentPiecesUA.RoomDefinition p_i50644_2_)
		{
			super(StructureInitUA.OMSIMPLETUA, 1, p_i50644_1_, p_i50644_2_, 1, 1, 1);
		}


		public SimpleTopRoom(TemplateManager p_i50645_1_, CompoundNBT p_i50645_2_)
		{
			super(StructureInitUA.OMSIMPLETUA, p_i50645_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			if (this.roomDefinition.index / 25 > 0)
			{
				this.generateDefaultFloor(world, mutableBoundingBoxIn, 0, 0, true);
			}

			for (int i = 1; i <= 6; ++i)
			{
				for (int j = 1; j <= 6; ++j)
				{
					if (random.nextInt(3) != 0)
					{
						int k = 2 + (random.nextInt(4) == 0 ? 0 : 1);
						this.fillWithBlocks(world, mutableBoundingBoxIn, i, k, j, i, 3, j, Blocks.WET_SPONGE.getDefaultState(), Blocks.WET_SPONGE.getDefaultState(), false);
					}
				}
			}

			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
			this.fillWithBlocks(world, mutableBoundingBoxIn, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);

			this.spawnGuardian(world, mutableBoundingBoxIn, 3, 2, 5);
			this.spawnGuardian(world, mutableBoundingBoxIn, 3, 2, 5);

			return true;

		}
	}

	public static class WingRoom extends OceanMonumentPiecesUA.Piece
	{
		private int mainDesign;


		public WingRoom(Direction p_i45585_1_, MutableBoundingBox p_i45585_2_, int p_i45585_3_)
		{
			super(StructureInitUA.OMWRUA, p_i45585_1_, p_i45585_2_);
			this.mainDesign = p_i45585_3_ & 1;
		}


		public WingRoom(TemplateManager p_i50643_1_, CompoundNBT p_i50643_2_)
		{
			super(StructureInitUA.OMWRUA, p_i50643_2_);
		}

		protected int horizontalPos = -1;


		@Override
		public boolean create(IWorld world, ChunkGenerator<?> p_225577_2_, Random random, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_74875_4_)
		{

			if (this.mainDesign == 0)
			{
				for (int i = 0; i < 4; ++i)
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, 10 - i, 3 - i, 20 - i, 12 + i, 3 - i, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				}

				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 0, 6, 15, 0, 16, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 0, 6, 6, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 16, 0, 6, 16, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 7, 7, 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 15, 1, 7, 15, 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 1, 6, 9, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 13, 1, 6, 15, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 8, 1, 7, 9, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 13, 1, 7, 14, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 9, 0, 5, 13, 0, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 0, 7, 12, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 8, 0, 10, 8, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 14, 0, 10, 14, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, false);

				for (int i1 = 18; i1 >= 7; i1 -= 3)
				{
					this.setBlockState(world, SEA_LANTERN, 6, 3, i1, mutableBoundingBoxIn);
					this.setBlockState(world, SEA_LANTERN, 16, 3, i1, mutableBoundingBoxIn);
				}

				this.setBlockState(world, SEA_LANTERN, 10, 0, 10, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 12, 0, 10, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 10, 0, 12, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 12, 0, 12, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 8, 3, 6, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 14, 3, 6, mutableBoundingBoxIn);
				this.setBlockState(world, BRICKS_PRISMARINE, 4, 2, 4, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 4, 1, 4, mutableBoundingBoxIn);
				this.setBlockState(world, BRICKS_PRISMARINE, 4, 0, 4, mutableBoundingBoxIn);
				this.setBlockState(world, BRICKS_PRISMARINE, 18, 2, 4, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 18, 1, 4, mutableBoundingBoxIn);
				this.setBlockState(world, BRICKS_PRISMARINE, 18, 0, 4, mutableBoundingBoxIn);
				this.setBlockState(world, BRICKS_PRISMARINE, 4, 2, 18, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 4, 1, 18, mutableBoundingBoxIn);
				this.setBlockState(world, BRICKS_PRISMARINE, 4, 0, 18, mutableBoundingBoxIn);
				this.setBlockState(world, BRICKS_PRISMARINE, 18, 2, 18, mutableBoundingBoxIn);
				this.setBlockState(world, SEA_LANTERN, 18, 1, 18, mutableBoundingBoxIn);
				this.setBlockState(world, BRICKS_PRISMARINE, 18, 0, 18, mutableBoundingBoxIn);
				this.setBlockState(world, BRICKS_PRISMARINE, 9, 7, 20, mutableBoundingBoxIn);
				this.setBlockState(world, BRICKS_PRISMARINE, 13, 7, 20, mutableBoundingBoxIn);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 6, 0, 21, 7, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 15, 0, 21, 16, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);

				if (UltraAmplified.UAStructuresConfig.chestGeneration.get())
				{
					this.generateChest(world, mutableBoundingBoxIn, random, 11, 1, 17, LootTables.CHESTS_END_CITY_TREASURE);
					this.generateChest(world, mutableBoundingBoxIn, random, 12, 1, 17, LootTables.CHESTS_END_CITY_TREASURE);
				}

				this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 4, 21, 12, 6, 21, WATER, WATER, false);

				this.spawnElder(world, mutableBoundingBoxIn, 11, 2, 16);

			}
			else if (this.mainDesign == 1)
			{
				this.fillWithBlocks(world, mutableBoundingBoxIn, 9, 3, 18, 13, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 9, 0, 18, 9, 2, 18, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 13, 0, 18, 13, 2, 18, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				int j1 = 9;

				for (int l = 0; l < 2; ++l)
				{
					this.setBlockState(world, BRICKS_PRISMARINE, j1, 6, 20, mutableBoundingBoxIn);
					this.setBlockState(world, SEA_LANTERN, j1, 5, 20, mutableBoundingBoxIn);
					this.setBlockState(world, BRICKS_PRISMARINE, j1, 4, 20, mutableBoundingBoxIn);
					j1 = 13;
				}

				this.fillWithBlocks(world, mutableBoundingBoxIn, 7, 3, 7, 15, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
				j1 = 10;

				for (int k1 = 0; k1 < 2; ++k1)
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, j1, 0, 10, j1, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, j1, 0, 12, j1, 6, 12, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.setBlockState(world, SEA_LANTERN, j1, 0, 10, mutableBoundingBoxIn);
					this.setBlockState(world, SEA_LANTERN, j1, 0, 12, mutableBoundingBoxIn);
					this.setBlockState(world, SEA_LANTERN, j1, 4, 10, mutableBoundingBoxIn);
					this.setBlockState(world, SEA_LANTERN, j1, 4, 12, mutableBoundingBoxIn);
					j1 = 12;
				}

				j1 = 8;

				for (int l1 = 0; l1 < 2; ++l1)
				{
					this.fillWithBlocks(world, mutableBoundingBoxIn, j1, 0, 7, j1, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					this.fillWithBlocks(world, mutableBoundingBoxIn, j1, 0, 14, j1, 2, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
					j1 = 14;
				}

				this.fillWithBlocks(world, mutableBoundingBoxIn, 8, 3, 8, 8, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, false);
				this.fillWithBlocks(world, mutableBoundingBoxIn, 14, 3, 8, 14, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, false);

				if (UltraAmplified.UAStructuresConfig.chestGeneration.get())
				{
					this.generateChest(world, mutableBoundingBoxIn, random, 11, 4, 9, LootTables.CHESTS_END_CITY_TREASURE);
					this.generateChest(world, mutableBoundingBoxIn, random, 10, 4, 9, LootTables.CHESTS_END_CITY_TREASURE);
				}

				this.fillWithBlocks(world, mutableBoundingBoxIn, 10, 4, 21, 12, 6, 21, WATER, WATER, false);

				this.spawnElder(world, mutableBoundingBoxIn, 11, 5, 13);
			}

			return true;

		}
	}

	static class XDoubleRoomFitHelper implements OceanMonumentPiecesUA.MonumentRoomFitHelper
	{
		private XDoubleRoomFitHelper()
		{
		}


		@Override
		public boolean fits(OceanMonumentPiecesUA.RoomDefinition definition)
		{
			return definition.hasOpening[Direction.EAST.getIndex()] && !definition.connections[Direction.EAST.getIndex()].claimed;
		}


		@Override
		public OceanMonumentPiecesUA.Piece create(Direction p_175968_1_, OceanMonumentPiecesUA.RoomDefinition p_175968_2_, Random p_175968_3_)
		{
			p_175968_2_.claimed = true;
			p_175968_2_.connections[Direction.EAST.getIndex()].claimed = true;
			return new OceanMonumentPiecesUA.DoubleXRoom(p_175968_1_, p_175968_2_);
		}
	}

	static class XYDoubleRoomFitHelper implements OceanMonumentPiecesUA.MonumentRoomFitHelper
	{
		private XYDoubleRoomFitHelper()
		{
		}


		@Override
		public boolean fits(OceanMonumentPiecesUA.RoomDefinition definition)
		{
			if (definition.hasOpening[Direction.EAST.getIndex()] && !definition.connections[Direction.EAST.getIndex()].claimed && definition.hasOpening[Direction.UP.getIndex()] && !definition.connections[Direction.UP.getIndex()].claimed)
			{
				OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition = definition.connections[Direction.EAST.getIndex()];
				return structureoceanmonumentpieces$roomdefinition.hasOpening[Direction.UP.getIndex()] && !structureoceanmonumentpieces$roomdefinition.connections[Direction.UP.getIndex()].claimed;
			}
			else
			{
				return false;
			}
		}


		@Override
		public OceanMonumentPiecesUA.Piece create(Direction p_175968_1_, OceanMonumentPiecesUA.RoomDefinition p_175968_2_, Random p_175968_3_)
		{
			p_175968_2_.claimed = true;
			p_175968_2_.connections[Direction.EAST.getIndex()].claimed = true;
			p_175968_2_.connections[Direction.UP.getIndex()].claimed = true;
			p_175968_2_.connections[Direction.EAST.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
			return new OceanMonumentPiecesUA.DoubleXYRoom(p_175968_1_, p_175968_2_);
		}
	}

	static class YDoubleRoomFitHelper implements OceanMonumentPiecesUA.MonumentRoomFitHelper
	{
		private YDoubleRoomFitHelper()
		{
		}


		@Override
		public boolean fits(OceanMonumentPiecesUA.RoomDefinition definition)
		{
			return definition.hasOpening[Direction.UP.getIndex()] && !definition.connections[Direction.UP.getIndex()].claimed;
		}


		@Override
		public OceanMonumentPiecesUA.Piece create(Direction p_175968_1_, OceanMonumentPiecesUA.RoomDefinition p_175968_2_, Random p_175968_3_)
		{
			p_175968_2_.claimed = true;
			p_175968_2_.connections[Direction.UP.getIndex()].claimed = true;
			return new OceanMonumentPiecesUA.DoubleYRoom(p_175968_1_, p_175968_2_);
		}
	}

	static class YZDoubleRoomFitHelper implements OceanMonumentPiecesUA.MonumentRoomFitHelper
	{
		private YZDoubleRoomFitHelper()
		{
		}


		@Override
		public boolean fits(OceanMonumentPiecesUA.RoomDefinition definition)
		{
			if (definition.hasOpening[Direction.NORTH.getIndex()] && !definition.connections[Direction.NORTH.getIndex()].claimed && definition.hasOpening[Direction.UP.getIndex()] && !definition.connections[Direction.UP.getIndex()].claimed)
			{
				OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition = definition.connections[Direction.NORTH.getIndex()];
				return structureoceanmonumentpieces$roomdefinition.hasOpening[Direction.UP.getIndex()] && !structureoceanmonumentpieces$roomdefinition.connections[Direction.UP.getIndex()].claimed;
			}
			else
			{
				return false;
			}
		}


		@Override
		public OceanMonumentPiecesUA.Piece create(Direction p_175968_1_, OceanMonumentPiecesUA.RoomDefinition p_175968_2_, Random p_175968_3_)
		{
			p_175968_2_.claimed = true;
			p_175968_2_.connections[Direction.NORTH.getIndex()].claimed = true;
			p_175968_2_.connections[Direction.UP.getIndex()].claimed = true;
			p_175968_2_.connections[Direction.NORTH.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
			return new OceanMonumentPiecesUA.DoubleYZRoom(p_175968_1_, p_175968_2_);
		}
	}

	static class ZDoubleRoomFitHelper implements OceanMonumentPiecesUA.MonumentRoomFitHelper
	{
		private ZDoubleRoomFitHelper()
		{
		}


		@Override
		public boolean fits(OceanMonumentPiecesUA.RoomDefinition definition)
		{
			return definition.hasOpening[Direction.NORTH.getIndex()] && !definition.connections[Direction.NORTH.getIndex()].claimed;
		}


		@Override
		public OceanMonumentPiecesUA.Piece create(Direction p_175968_1_, OceanMonumentPiecesUA.RoomDefinition p_175968_2_, Random p_175968_3_)
		{
			OceanMonumentPiecesUA.RoomDefinition structureoceanmonumentpieces$roomdefinition = p_175968_2_;

			if (!p_175968_2_.hasOpening[Direction.NORTH.getIndex()] || p_175968_2_.connections[Direction.NORTH.getIndex()].claimed)
			{
				structureoceanmonumentpieces$roomdefinition = p_175968_2_.connections[Direction.SOUTH.getIndex()];
			}

			structureoceanmonumentpieces$roomdefinition.claimed = true;
			structureoceanmonumentpieces$roomdefinition.connections[Direction.NORTH.getIndex()].claimed = true;
			return new OceanMonumentPiecesUA.DoubleZRoom(p_175968_1_, structureoceanmonumentpieces$roomdefinition);
		}
	}
}
