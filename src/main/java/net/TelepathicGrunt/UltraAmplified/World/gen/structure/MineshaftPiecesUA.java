package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftUA.Type;
import net.minecraft.block.BlockEndRod;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockTorchWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.StructureIO;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class MineshaftPiecesUA
{
    public static void registerStructurePieces()
    {
    	StructureIO.registerStructureComponent(MineshaftPiecesUA.Corridor.class, "MSCorridor");
    	StructureIO.registerStructureComponent(MineshaftPiecesUA.Cross.class, "MSCrossing");
    	StructureIO.registerStructureComponent(MineshaftPiecesUA.Room.class, "MSRoom");
        StructureIO.registerStructureComponent(MineshaftPiecesUA.Stairs.class, "MSStairs");
    }

    private static MineshaftPiecesUA.Peice createRandomShaftPiece(List<StructurePiece> p_189940_0_, Random p_189940_1_, int p_189940_2_, int p_189940_3_, int p_189940_4_, @Nullable EnumFacing p_189940_5_, int p_189940_6_, MineshaftUA.Type p_189940_7_)
    {
        int i = p_189940_1_.nextInt(100);

        if (i >= 80)
        {
            MutableBoundingBox MutableBoundingBox = MineshaftPiecesUA.Cross.findCrossing(p_189940_0_, p_189940_1_, p_189940_2_, p_189940_3_, p_189940_4_, p_189940_5_);

            if (MutableBoundingBox != null)
            {
                return new MineshaftPiecesUA.Cross(p_189940_6_, p_189940_1_, MutableBoundingBox, p_189940_5_, p_189940_7_);
            }
        }
        else if (i >= 70)
        {
            MutableBoundingBox MutableBoundingBox1 = MineshaftPiecesUA.Stairs.findStairs(p_189940_0_, p_189940_1_, p_189940_2_, p_189940_3_, p_189940_4_, p_189940_5_);

            if (MutableBoundingBox1 != null)
            {
                return new MineshaftPiecesUA.Stairs(p_189940_6_, p_189940_1_, MutableBoundingBox1, p_189940_5_, p_189940_7_);
            }
        }
        else
        {
            MutableBoundingBox MutableBoundingBox2 = MineshaftPiecesUA.Corridor.findCorridorSize(p_189940_0_, p_189940_1_, p_189940_2_, p_189940_3_, p_189940_4_, p_189940_5_);

            if (MutableBoundingBox2 != null)
            {
                return new MineshaftPiecesUA.Corridor(p_189940_6_, p_189940_1_, MutableBoundingBox2, p_189940_5_, p_189940_7_);
            }
        }

        return null;
    }

    private static MineshaftPiecesUA.Peice generateAndAddPiece(StructurePiece p_189938_0_, List<StructurePiece> p_189938_1_, Random p_189938_2_, int p_189938_3_, int p_189938_4_, int p_189938_5_, EnumFacing p_189938_6_, int p_189938_7_)
    {
        if (p_189938_7_ > 8)
        {
            return null;
        }
        else if (Math.abs(p_189938_3_ - p_189938_0_.getBoundingBox().minX) <= 80 && Math.abs(p_189938_5_ - p_189938_0_.getBoundingBox().minZ) <= 80)
        {
        	MineshaftUA.Type mapgenmineshaft$type = ((MineshaftPiecesUA.Peice)p_189938_0_).mineShaftType;
            MineshaftPiecesUA.Peice structuremineshaftpieces$peice = createRandomShaftPiece(p_189938_1_, p_189938_2_, p_189938_3_, p_189938_4_, p_189938_5_, p_189938_6_, p_189938_7_ + 1, mapgenmineshaft$type);

            if (structuremineshaftpieces$peice != null)
            {
                p_189938_1_.add(structuremineshaftpieces$peice);
                structuremineshaftpieces$peice.buildComponent(p_189938_0_, p_189938_1_, p_189938_2_);
            }

            return structuremineshaftpieces$peice;
        }
        else
        {
            return null;
        }
    }

    public static class Corridor extends MineshaftPiecesUA.Peice
    {
        private boolean hasRails;
        private boolean hasSpiders;
        private boolean spawnerPlaced;
        private int sectionCount;

        public Corridor()
        {
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("hr", this.hasRails);
            tagCompound.setBoolean("sc", this.hasSpiders);
            tagCompound.setBoolean("hps", this.spawnerPlaced);
            tagCompound.setInt("Num", this.sectionCount);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.hasRails = tagCompound.getBoolean("hr");
            this.hasSpiders = tagCompound.getBoolean("sc");
            this.spawnerPlaced = tagCompound.getBoolean("hps");
            this.sectionCount = tagCompound.getInt("Num");
        }

        public Corridor(int p_i47140_1_, Random p_i47140_2_, MutableBoundingBox p_i47140_3_, EnumFacing p_i47140_4_, MineshaftUA.Type p_i47140_5_)
        {
            super(p_i47140_1_, p_i47140_5_);
            this.setCoordBaseMode(p_i47140_4_);
            this.boundingBox = p_i47140_3_;
            this.hasRails = p_i47140_2_.nextInt(3) == 0;
            this.hasSpiders = !this.hasRails && p_i47140_2_.nextInt(23) == 0;

            if (this.getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
            {
                this.sectionCount = p_i47140_3_.getZSize() / 5;
            }
            else
            {
                this.sectionCount = p_i47140_3_.getXSize() / 5;
            }
        }

        public static MutableBoundingBox findCorridorSize(List<StructurePiece> p_175814_0_, Random rand, int x, int y, int z, EnumFacing facing)
        {
            MutableBoundingBox MutableBoundingBox = new MutableBoundingBox(x, y, z, x, y + 2, z);
            int i;

            for (i = rand.nextInt(3) + 2; i > 0; --i)
            {
                int j = i * 5;

                switch (facing)
                {
                    case NORTH:
                    default:
                        MutableBoundingBox.maxX = x + 2;
                        MutableBoundingBox.minZ = z - (j - 1);
                        break;

                    case SOUTH:
                        MutableBoundingBox.maxX = x + 2;
                        MutableBoundingBox.maxZ = z + (j - 1);
                        break;

                    case WEST:
                        MutableBoundingBox.minX = x - (j - 1);
                        MutableBoundingBox.maxZ = z + 2;
                        break;

                    case EAST:
                        MutableBoundingBox.maxX = x + (j - 1);
                        MutableBoundingBox.maxZ = z + 2;
                }

                if (StructurePiece.findIntersecting(p_175814_0_, MutableBoundingBox) == null)
                {
                    break;
                }
            }

            return i > 0 ? MutableBoundingBox : null;
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            int i = this.getComponentType();
            int j = rand.nextInt(4);
            EnumFacing enumfacing = this.getCoordBaseMode();

            if (enumfacing != null)
            {
                switch (enumfacing)
                {
                    case NORTH:
                    default:
                        if (j <= 1)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, enumfacing, i);
                        }
                        else if (j == 2)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, EnumFacing.WEST, i);
                        }
                        else
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, EnumFacing.EAST, i);
                        }

                        break;

                    case SOUTH:
                        if (j <= 1)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, enumfacing, i);
                        }
                        else if (j == 2)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.WEST, i);
                        }
                        else
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.EAST, i);
                        }

                        break;

                    case WEST:
                        if (j <= 1)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, enumfacing, i);
                        }
                        else if (j == 2)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
                        }
                        else
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
                        }

                        break;

                    case EAST:
                        if (j <= 1)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, enumfacing, i);
                        }
                        else if (j == 2)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
                        }
                        else
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
                        }
                }
            }

            if (i < 8)
            {
                if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.SOUTH)
                {
                    for (int i1 = this.boundingBox.minX + 3; i1 + 3 <= this.boundingBox.maxX; i1 += 5)
                    {
                        int j1 = rand.nextInt(5);

                        if (j1 == 0)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, i1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i + 1);
                        }
                        else if (j1 == 1)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, i1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i + 1);
                        }
                    }
                }
                else
                {
                    for (int k = this.boundingBox.minZ + 3; k + 3 <= this.boundingBox.maxZ; k += 5)
                    {
                        int l = rand.nextInt(5);

                        if (l == 0)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, k, EnumFacing.WEST, i + 1);
                        }
                        else if (l == 1)
                        {
                            MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, k, EnumFacing.EAST, i + 1);
                        }
                    }
                }
            }
        }

        protected boolean generateChest(IWorld worldIn, MutableBoundingBox structurebb, Random randomIn, int x, int y, int z, ResourceLocation loot)
        {
            BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));

            if (structurebb.isVecInside(blockpos) && worldIn.getBlockState(blockpos).getMaterial() == Material.AIR)
            {
                IBlockState iblockstate = Blocks.RAIL.getDefaultState().with(BlockRail.SHAPE, randomIn.nextBoolean() ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST);
                this.setBlockState(worldIn, iblockstate, x, y, z, structurebb);
                EntityMinecartChest entityminecartchest = new EntityMinecartChest(worldIn.getWorld(), (double)((float)blockpos.getX() + 0.5F), (double)((float)blockpos.getY() + 0.5F), (double)((float)blockpos.getZ() + 0.5F));
                entityminecartchest.setLootTable(loot, randomIn.nextLong());
                worldIn.spawnEntity(entityminecartchest);
                return true;
            }
            else
            {
                return false;
            }
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox MutableBoundingBoxIn, ChunkPos p_74875_4_)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, MutableBoundingBoxIn))
            {
                return false;
            }
            else
            {
                int i1 = this.sectionCount * 5 - 1;
                IBlockState iblockstate = this.getPlanksBlock();
                this.fillWithBlocks(worldIn, MutableBoundingBoxIn, 0, 0, 0, 2, 1, i1, CAVE_AIR, CAVE_AIR, false);
                this.generateMaybeBox(worldIn, MutableBoundingBoxIn, randomIn, 0.8F, 0, 2, 0, 2, 2, i1, CAVE_AIR, CAVE_AIR, false, false);

                if (this.hasSpiders)
                {
                    this.generateMaybeBox(worldIn, MutableBoundingBoxIn, randomIn, 0.6F, 0, 0, 0, 2, 1, i1, Blocks.COBWEB.getDefaultState(), CAVE_AIR, false, true);
                }

                for (int j1 = 0; j1 < this.sectionCount; ++j1)
                {
                    int k1 = 2 + j1 * 5;
                    this.placeSupport(worldIn, MutableBoundingBoxIn, 0, 0, k1, 2, 2, randomIn);
                    this.placeCobWeb(worldIn, MutableBoundingBoxIn, randomIn, 0.1F, 0, 2, k1 - 1);
                    this.placeCobWeb(worldIn, MutableBoundingBoxIn, randomIn, 0.1F, 2, 2, k1 - 1);
                    this.placeCobWeb(worldIn, MutableBoundingBoxIn, randomIn, 0.1F, 0, 2, k1 + 1);
                    this.placeCobWeb(worldIn, MutableBoundingBoxIn, randomIn, 0.1F, 2, 2, k1 + 1);
                    this.placeCobWeb(worldIn, MutableBoundingBoxIn, randomIn, 0.05F, 0, 2, k1 - 2);
                    this.placeCobWeb(worldIn, MutableBoundingBoxIn, randomIn, 0.05F, 2, 2, k1 - 2);
                    this.placeCobWeb(worldIn, MutableBoundingBoxIn, randomIn, 0.05F, 0, 2, k1 + 2);
                    this.placeCobWeb(worldIn, MutableBoundingBoxIn, randomIn, 0.05F, 2, 2, k1 + 2);

                    if(Config.chestGeneration) {
	                    if (randomIn.nextInt(50) == 0)
	                    {
	                        this.generateChest(worldIn, MutableBoundingBoxIn, randomIn, 2, 0, k1 - 1, LootTableList.CHESTS_ABANDONED_MINESHAFT);
	                    }
	
	                    if (randomIn.nextInt(50) == 0)
	                    {
	                        this.generateChest(worldIn, MutableBoundingBoxIn, randomIn, 0, 0, k1 + 1, LootTableList.CHESTS_ABANDONED_MINESHAFT);
	                    }
                    }

                    if (this.hasSpiders && !this.spawnerPlaced)
                    {
                        int l1 = this.getYWithOffset(0);
                        int i2 = k1 - 1 + randomIn.nextInt(3);
                        int j2 = this.getXWithOffset(1, i2);
                        int k2 = this.getZWithOffset(1, i2);
                        BlockPos blockpos = new BlockPos(j2, l1, k2);

                        if (MutableBoundingBoxIn.isVecInside(blockpos) && this.getSkyBrightness(worldIn, 1, 0, i2, MutableBoundingBoxIn))
                        {
                            this.spawnerPlaced = true;
                            worldIn.setBlockState(blockpos, Blocks.SPAWNER.getDefaultState(), 2);
                            TileEntity tileentity = worldIn.getTileEntity(blockpos);

                            if (tileentity instanceof TileEntityMobSpawner)
                            {
                                ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityType(EntityType.CAVE_SPIDER);
                            }
                        }
                    }
                }

                for (int l2 = 0; l2 <= 2; ++l2)
                {
                    for (int i3 = 0; i3 <= i1; ++i3)
                    {
                        IBlockState iblockstate3 = this.getBlockStateFromPos(worldIn, l2, -1, i3, MutableBoundingBoxIn);

                        if (iblockstate3.getMaterial() == Material.AIR)
                        {
                            this.setBlockState(worldIn, iblockstate, l2, -1, i3, MutableBoundingBoxIn);
                        }
                    }
                }

                if (this.hasRails)
                {
                    IBlockState iblockstate1 = Blocks.RAIL.getDefaultState().with(BlockRail.SHAPE, RailShape.NORTH_SOUTH);

                    for (int j3 = 0; j3 <= i1; ++j3)
                    {
                        IBlockState iblockstate2 = this.getBlockStateFromPos(worldIn, 1, -1, j3, MutableBoundingBoxIn);
                        
                        if (iblockstate2.getMaterial() != Material.AIR)
                        {
                            float f = this.getSkyBrightness(worldIn, 1, 0, j3, MutableBoundingBoxIn) ? 0.7F : 0.9F;
                            this.randomlyPlaceBlock(worldIn, MutableBoundingBoxIn, randomIn, f, 1, 0, j3, iblockstate1);
                        }
                    }
                }

                return true;
            }
        }

        private void placeSupport(IWorld worldIn, MutableBoundingBox boundingBox, int x, int p_189921_4_, int z, int y, int p_189921_7_, Random random)
        {
            if (this.isSupportingBox(worldIn, boundingBox, x, p_189921_7_, y, z))
            {
                IBlockState iblockstate = this.getPlanksBlock();
                IBlockState iblockstate1 = this.getFenceBlock();
                IBlockState iblockstate2 = CAVE_AIR;
                this.fillWithBlocks(worldIn, boundingBox, x, p_189921_4_, z, x, y - 1, z, iblockstate1, iblockstate2, false);
                this.fillWithBlocks(worldIn, boundingBox, p_189921_7_, p_189921_4_, z, p_189921_7_, y - 1, z, iblockstate1, iblockstate2, false);

                if (random.nextInt(4) == 0)
                {
                    this.fillWithBlocks(worldIn, boundingBox, x, y, z, x, y, z, iblockstate, iblockstate2, false);
                    this.fillWithBlocks(worldIn, boundingBox, p_189921_7_, y, z, p_189921_7_, y, z, iblockstate, iblockstate2, false);
                }
                else
                {
                    this.fillWithBlocks(worldIn, boundingBox, x, y, z, p_189921_7_, y, z, iblockstate, iblockstate2, false);
                    
                    if(this.mineShaftType == Type.END) {
                    		if(random.nextFloat() < 0.08F) {
                    		this.randomlyPlaceBlock(worldIn, boundingBox, random, 1F, x, y, z - 1, Blocks.END_ROD.getDefaultState().with(BlockEndRod.FACING, EnumFacing.SOUTH));
                    		this.randomlyPlaceBlock(worldIn, boundingBox, random, 1F, x, y, z + 1, Blocks.END_ROD.getDefaultState().with(BlockEndRod.FACING, EnumFacing.NORTH));
                    	}
                    	
                    	if(random.nextFloat() < 0.08F) {
	                    	this.randomlyPlaceBlock(worldIn, boundingBox, random, 1F, x + 2, y, z - 1, Blocks.END_ROD.getDefaultState().with(BlockEndRod.FACING, EnumFacing.SOUTH));
		                    this.randomlyPlaceBlock(worldIn, boundingBox, random, 1F, x + 2, y, z + 1, Blocks.END_ROD.getDefaultState().with(BlockEndRod.FACING, EnumFacing.NORTH));
                    	}
                    }
                    else if(this.mineShaftType == Type.HELL) 
                    {
                    	this.randomlyPlaceBlock(worldIn, boundingBox, random, 0.15F, x + 1, y, z, Blocks.GLOWSTONE.getDefaultState());
                    }
                    else {
	                    this.randomlyPlaceBlock(worldIn, boundingBox, random, 0.08F, x + 1, y, z - 1, Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.SOUTH));
	                    this.randomlyPlaceBlock(worldIn, boundingBox, random, 0.08F, x + 1, y, z + 1, Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, EnumFacing.NORTH));
                    }
                }
            }
        }

        private void placeCobWeb(IWorld p_189922_1_, MutableBoundingBox p_189922_2_, Random p_189922_3_, float p_189922_4_, int p_189922_5_, int p_189922_6_, int p_189922_7_)
        {
            if (this.getSkyBrightness(p_189922_1_, p_189922_5_, p_189922_6_, p_189922_7_, p_189922_2_))
            {
                this.randomlyPlaceBlock(p_189922_1_, p_189922_2_, p_189922_3_, p_189922_4_, p_189922_5_, p_189922_6_, p_189922_7_, Blocks.COBWEB.getDefaultState());
            }
        }

    }

    public static class Cross extends MineshaftPiecesUA.Peice
    {
        private EnumFacing corridorDirection;
        private boolean isMultipleFloors;

        public Cross()
        {
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("tf", this.isMultipleFloors);
            tagCompound.setInt("D", this.corridorDirection.getHorizontalIndex());
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.isMultipleFloors = tagCompound.getBoolean("tf");
            this.corridorDirection = EnumFacing.byHorizontalIndex(tagCompound.getInt("D"));
        }

        public Cross(int p_i47139_1_, Random p_i47139_2_, MutableBoundingBox p_i47139_3_, @Nullable EnumFacing p_i47139_4_, MineshaftUA.Type p_i47139_5_)
        {
            super(p_i47139_1_, p_i47139_5_);
            this.corridorDirection = p_i47139_4_;
            this.boundingBox = p_i47139_3_;
            this.isMultipleFloors = p_i47139_3_.getYSize() > 3;
        }

        public static MutableBoundingBox findCrossing(List<StructurePiece> listIn, Random rand, int x, int y, int z, EnumFacing facing)
        {
            MutableBoundingBox MutableBoundingBox = new MutableBoundingBox(x, y, z, x, y + 2, z);

            if (rand.nextInt(4) == 0)
            {
                MutableBoundingBox.maxY += 4;
            }

            switch (facing)
            {
                case NORTH:
                default:
                    MutableBoundingBox.minX = x - 1;
                    MutableBoundingBox.maxX = x + 3;
                    MutableBoundingBox.minZ = z - 4;
                    break;

                case SOUTH:
                    MutableBoundingBox.minX = x - 1;
                    MutableBoundingBox.maxX = x + 3;
                    MutableBoundingBox.maxZ = z + 3 + 1;
                    break;

                case WEST:
                    MutableBoundingBox.minX = x - 4;
                    MutableBoundingBox.minZ = z - 1;
                    MutableBoundingBox.maxZ = z + 3;
                    break;

                case EAST:
                    MutableBoundingBox.maxX = x + 3 + 1;
                    MutableBoundingBox.minZ = z - 1;
                    MutableBoundingBox.maxZ = z + 3;
            }

            return StructurePiece.findIntersecting(listIn, MutableBoundingBox) != null ? null : MutableBoundingBox;
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            int i = this.getComponentType();

            switch (this.corridorDirection)
            {
                case NORTH:
                default:
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
                    break;

                case SOUTH:
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
                    break;

                case WEST:
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
                    break;

                case EAST:
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
            }

            if (this.isMultipleFloors)
            {
                if (rand.nextBoolean())
                {
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
                }

                if (rand.nextBoolean())
                {
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
                }

                if (rand.nextBoolean())
                {
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
                }

                if (rand.nextBoolean())
                {
                    MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
                }
            }
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox MutableBoundingBoxIn, ChunkPos p_74875_4_) 
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, MutableBoundingBoxIn))
            {
                return false;
            }
            else
            {
                IBlockState iblockstate = this.getPlanksBlock();

                if (this.isMultipleFloors)
                {
                    this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, CAVE_AIR, CAVE_AIR, false);
                    this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, CAVE_AIR, CAVE_AIR, false);
                    this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, CAVE_AIR, CAVE_AIR, false);
                    this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, CAVE_AIR, CAVE_AIR, false);
                    this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, CAVE_AIR, CAVE_AIR, false);
                }
                else
                {
                    this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, CAVE_AIR, CAVE_AIR, false);
                    this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, CAVE_AIR, CAVE_AIR, false);
                }

                this.placeSupportPillar(worldIn, MutableBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
                this.placeSupportPillar(worldIn, MutableBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);
                this.placeSupportPillar(worldIn, MutableBoundingBoxIn, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
                this.placeSupportPillar(worldIn, MutableBoundingBoxIn, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);

                for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i)
                {
                    for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j)
                    {
                        if (this.getBlockStateFromPos(worldIn, i, this.boundingBox.minY - 1, j, MutableBoundingBoxIn).getMaterial() == Material.AIR && this.getSkyBrightness(worldIn, i, this.boundingBox.minY - 1, j, MutableBoundingBoxIn))
                        {
                            this.setBlockState(worldIn, iblockstate, i, this.boundingBox.minY - 1, j, MutableBoundingBoxIn);
                        }
                    }
                }

                return true;
            }
        }

        private void placeSupportPillar(IWorld p_189923_1_, MutableBoundingBox p_189923_2_, int p_189923_3_, int p_189923_4_, int p_189923_5_, int p_189923_6_)
        {
            if (this.getBlockStateFromPos(p_189923_1_, p_189923_3_, p_189923_6_ + 1, p_189923_5_, p_189923_2_).getMaterial() != Material.AIR)
            {
                this.fillWithBlocks(p_189923_1_, p_189923_2_, p_189923_3_, p_189923_4_, p_189923_5_, p_189923_3_, p_189923_6_, p_189923_5_, this.getPlanksBlock(), CAVE_AIR, false);
            }
        }
    }

    abstract static class Peice extends StructurePiece
    {
        protected MineshaftUA.Type mineShaftType;

        public Peice()
        {
        }

        public Peice(int p_i47138_1_, MineshaftUA.Type p_i47138_2_)
        {
            super(p_i47138_1_);
            this.mineShaftType = p_i47138_2_;
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            tagCompound.setInt("MST", this.mineShaftType.ordinal());
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            this.mineShaftType = MineshaftUA.Type.byId(tagCompound.getInt("MST"));
        }

        protected IBlockState getPlanksBlock()
        {
            switch (this.mineShaftType)
            {
                case MESA:
                    return Blocks.DARK_OAK_PLANKS.getDefaultState();
                    
                case ICEY:
                    return Blocks.ICE.getDefaultState();
                    
                case COLDORBIRCH:
                    return Blocks.BIRCH_PLANKS.getDefaultState();
                    
                case JUNGLE:
                    return Blocks.JUNGLE_PLANKS.getDefaultState();
                    
                case TAIGA:
                    return Blocks.SPRUCE_PLANKS.getDefaultState();
                    
                case DESERT:
                    return Blocks.SMOOTH_SANDSTONE.getDefaultState();
                    
                case END:
                    return Blocks.PURPUR_BLOCK.getDefaultState();
                    
                case HELL:
                    return Blocks.NETHER_BRICKS.getDefaultState();
                    
                case STONE:
                    return Blocks.SMOOTH_STONE.getDefaultState();
                    
                case SAVANNA:
                    return Blocks.ACACIA_PLANKS.getDefaultState();
                    
                case SWAMPORDARKFOREST:
                    return Blocks.GRASS_BLOCK.getDefaultState();
                    
                case NORMAL:
                default:
                    return Blocks.OAK_PLANKS.getDefaultState();
            }
        }

        protected IBlockState getFenceBlock()
        {
        	switch (this.mineShaftType)
            {
                case MESA:
                    return Blocks.DARK_OAK_FENCE.getDefaultState();
                    
                case ICEY:
                    return Blocks.ICE.getDefaultState();
                    
                case COLDORBIRCH:
                    return Blocks.BIRCH_FENCE.getDefaultState();
                    
                case JUNGLE:
                    return Blocks.JUNGLE_FENCE.getDefaultState();
                    
                case TAIGA:
                    return Blocks.SPRUCE_FENCE.getDefaultState();
                    
                case DESERT:
                    return Blocks.CHISELED_SANDSTONE.getDefaultState();
                    
                case END:
                    return Blocks.PURPUR_BLOCK.getDefaultState();
                    
                case HELL:
                    return Blocks.NETHER_BRICKS.getDefaultState();
                    
                case STONE:
                    return Blocks.COBBLESTONE_WALL.getDefaultState();
                    
                case SAVANNA:
                    return Blocks.ACACIA_FENCE.getDefaultState();
                    
                case SWAMPORDARKFOREST:
                    return Blocks.DARK_OAK_LOG.getDefaultState();
                    
                case NORMAL:
                default:
                    return Blocks.OAK_FENCE.getDefaultState();
            }
        }

        protected boolean isSupportingBox(IWorld p_189918_1_, MutableBoundingBox p_189918_2_, int p_189918_3_, int p_189918_4_, int p_189918_5_, int p_189918_6_)
        {
            for (int i = p_189918_3_; i <= p_189918_4_; ++i)
            {
                if (this.getBlockStateFromPos(p_189918_1_, i, p_189918_5_ + 1, p_189918_6_, p_189918_2_).getMaterial() == Material.AIR)
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static class Room extends MineshaftPiecesUA.Peice
    {
        private final List<MutableBoundingBox> roomsLinkedToTheRoom = Lists.<MutableBoundingBox>newLinkedList();

        public Room()
        {
        }

        public Room(int p_i47137_1_, Random p_i47137_2_, int p_i47137_3_, int p_i47137_4_, MineshaftUA.Type p_i47137_5_)
        {
            super(p_i47137_1_, p_i47137_5_);
            this.mineShaftType = p_i47137_5_;
            
            //if the pit rooms are not allowed, makes this boolean always true.
            //if pits are allowed and normal rooms are not allowed, set to always false.
            //else if both are allowed, runs RNG to determine which room to generate.
            boolean normalRoom;
            if(Config.mineshaftUndergroundAllowed) 
            {
            	if(Config.mineshaftAbovegroundAllowed) 
            	{
            		normalRoom = p_i47137_2_.nextInt(5) < 3;
            	}
            	else 
            	{
            		normalRoom = false;
            	}
            }else 
            {
            	normalRoom = true;
            }
            
            
            
            if(normalRoom) {
            	//normal dirt room mineshaft
            	this.boundingBox = new MutableBoundingBox(p_i47137_3_, 20, p_i47137_4_, p_i47137_3_ + 7 + p_i47137_2_.nextInt(6), 30, p_i47137_4_ + 7 + p_i47137_2_.nextInt(6));
            }
            else{
            	//giant pit-like dirt room mineshafts
            	int height = p_i47137_2_.nextInt(90);
            	this.boundingBox = new MutableBoundingBox(p_i47137_3_, 20, p_i47137_4_, p_i47137_3_ + 7 + p_i47137_2_.nextInt(6), 150 + height, p_i47137_4_ + 7 + p_i47137_2_.nextInt(6));
            }
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            int i = this.getComponentType();
            int k = this.boundingBox.getYSize() - 3 - 1;

            if (k <= 0)
            {
                k = 1;
            }

            int l;

            for (int j = 0; j < this.boundingBox.getXSize(); j = l + 4)
            {
                l = j + rand.nextInt(this.boundingBox.getXSize());

                if (l + 3 > this.boundingBox.getXSize())
                {
                    break;
                }

                MineshaftPiecesUA.Peice structuremineshaftpieces$peice = MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + l, this.boundingBox.minY + rand.nextInt(k) + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);

                if (structuremineshaftpieces$peice != null)
                {
                    MutableBoundingBox MutableBoundingBox = structuremineshaftpieces$peice.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new MutableBoundingBox(MutableBoundingBox.minX, MutableBoundingBox.minY, this.boundingBox.minZ, MutableBoundingBox.maxX, MutableBoundingBox.maxY, this.boundingBox.minZ + 1));
                }
            }

            for (int i1 = 0; i1 < this.boundingBox.getXSize(); i1 = l + 4)
            {
                l = i1 + rand.nextInt(this.boundingBox.getXSize());

                if (l + 3 > this.boundingBox.getXSize())
                {
                    break;
                }

                MineshaftPiecesUA.Peice structuremineshaftpieces$peice1 = MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + l, this.boundingBox.minY + rand.nextInt(k) + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);

                if (structuremineshaftpieces$peice1 != null)
                {
                    MutableBoundingBox MutableBoundingBox1 = structuremineshaftpieces$peice1.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new MutableBoundingBox(MutableBoundingBox1.minX, MutableBoundingBox1.minY, this.boundingBox.maxZ - 1, MutableBoundingBox1.maxX, MutableBoundingBox1.maxY, this.boundingBox.maxZ));
                }
            }

            for (int j1 = 0; j1 < this.boundingBox.getZSize(); j1 = l + 4)
            {
                l = j1 + rand.nextInt(this.boundingBox.getZSize());

                if (l + 3 > this.boundingBox.getZSize())
                {
                    break;
                }

                MineshaftPiecesUA.Peice structuremineshaftpieces$peice2 = MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY + rand.nextInt(k) + 1, this.boundingBox.minZ + l, EnumFacing.WEST, i);

                if (structuremineshaftpieces$peice2 != null)
                {
                    MutableBoundingBox MutableBoundingBox2 = structuremineshaftpieces$peice2.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new MutableBoundingBox(this.boundingBox.minX, MutableBoundingBox2.minY, MutableBoundingBox2.minZ, this.boundingBox.minX + 1, MutableBoundingBox2.maxY, MutableBoundingBox2.maxZ));
                }
            }

            for (int k1 = 0; k1 < this.boundingBox.getZSize(); k1 = l + 4)
            {
                l = k1 + rand.nextInt(this.boundingBox.getZSize());

                if (l + 3 > this.boundingBox.getZSize())
                {
                    break;
                }

                StructurePiece StructurePiece = MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + rand.nextInt(k) + 1, this.boundingBox.minZ + l, EnumFacing.EAST, i);

                if (StructurePiece != null)
                {
                    MutableBoundingBox MutableBoundingBox3 = StructurePiece.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new MutableBoundingBox(this.boundingBox.maxX - 1, MutableBoundingBox3.minY, MutableBoundingBox3.minZ, this.boundingBox.maxX, MutableBoundingBox3.maxY, MutableBoundingBox3.maxZ));
                }
            }
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox MutableBoundingBoxIn, ChunkPos p_74875_4_)
        {
        	IBlockState flooring;
        	
        	if(this.mineShaftType == MineshaftUA.Type.HELL) 
            {
        		flooring = Blocks.SOUL_SAND.getDefaultState();
            }
        	else {
        		flooring = Blocks.COARSE_DIRT.getDefaultState();
        	}
        	
        	
        	if(this.boundingBox.getYSize() > 100) {
                this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX-10, this.boundingBox.minY, this.boundingBox.minZ-10, this.boundingBox.maxX+7, this.boundingBox.minY, this.boundingBox.maxZ+10, flooring, CAVE_AIR, false);
                this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX-3, this.boundingBox.minY + 1, this.boundingBox.minZ-3, this.boundingBox.maxX+1, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ+3, CAVE_AIR, CAVE_AIR, false);

                for (MutableBoundingBox MutableBoundingBox : this.roomsLinkedToTheRoom)
                {
                    this.fillWithBlocks(worldIn, MutableBoundingBoxIn, MutableBoundingBox.minX, MutableBoundingBox.maxY - 2, MutableBoundingBox.minZ, MutableBoundingBox.maxX, MutableBoundingBox.maxY, MutableBoundingBox.maxZ, CAVE_AIR, CAVE_AIR, false);
                }

                this.randomlyRareFillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX-3, this.boundingBox.minY + 4, this.boundingBox.minZ-3, this.boundingBox.maxX+1, this.boundingBox.maxY, this.boundingBox.maxZ+3, CAVE_AIR, false);
                return true;
       		}
        	else {
        		this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, flooring, CAVE_AIR, false);
                this.fillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, CAVE_AIR, CAVE_AIR, false);

                for (MutableBoundingBox MutableBoundingBox : this.roomsLinkedToTheRoom)
                {
                    this.fillWithBlocks(worldIn, MutableBoundingBoxIn, MutableBoundingBox.minX, MutableBoundingBox.maxY - 2, MutableBoundingBox.minZ, MutableBoundingBox.maxX, MutableBoundingBox.maxY, MutableBoundingBox.maxZ, CAVE_AIR, CAVE_AIR, false);
                }

                this.randomlyRareFillWithBlocks(worldIn, MutableBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, CAVE_AIR, false);
                return true;
        	}
        }

        public void offset(int x, int y, int z)
        {
            super.offset(x, y, z);

            for (MutableBoundingBox MutableBoundingBox : this.roomsLinkedToTheRoom)
            {
                MutableBoundingBox.offset(x, y, z);
            }
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            NBTTagList nbttaglist = new NBTTagList();

            for (MutableBoundingBox mutableBoundingBox : this.roomsLinkedToTheRoom)
            {
            	 nbttaglist.add((INBTBase)mutableBoundingBox.toNBTTagIntArray());
            }

            tagCompound.setTag("Entrances", nbttaglist);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            NBTTagList nbttaglist = tagCompound.getList("Entrances", 11);

            for(int i = 0; i < nbttaglist.size(); ++i) {
               this.roomsLinkedToTheRoom.add(new MutableBoundingBox(nbttaglist.getIntArray(i)));
            }

         }
    }

    public static class Stairs extends MineshaftPiecesUA.Peice
    {
        public Stairs()
        {
        }

        public Stairs(int p_i47136_1_, Random p_i47136_2_, MutableBoundingBox p_i47136_3_, EnumFacing p_i47136_4_, MineshaftUA.Type p_i47136_5_)
        {
            super(p_i47136_1_, p_i47136_5_);
            this.setCoordBaseMode(p_i47136_4_);
            this.boundingBox = p_i47136_3_;
        }

        public static MutableBoundingBox findStairs(List<StructurePiece> listIn, Random rand, int x, int y, int z, EnumFacing facing)
        {
            MutableBoundingBox MutableBoundingBox = new MutableBoundingBox(x, y - 5, z, x, y + 2, z);

            switch (facing)
            {
                case NORTH:
                default:
                    MutableBoundingBox.maxX = x + 2;
                    MutableBoundingBox.minZ = z - 8;
                    break;

                case SOUTH:
                    MutableBoundingBox.maxX = x + 2;
                    MutableBoundingBox.maxZ = z + 8;
                    break;

                case WEST:
                    MutableBoundingBox.minX = x - 8;
                    MutableBoundingBox.maxZ = z + 2;
                    break;

                case EAST:
                    MutableBoundingBox.maxX = x + 8;
                    MutableBoundingBox.maxZ = z + 2;
            }

            return StructurePiece.findIntersecting(listIn, MutableBoundingBox) != null ? null : MutableBoundingBox;
        }

        public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
        {
            int i = this.getComponentType();
            EnumFacing enumfacing = this.getCoordBaseMode();

            if (enumfacing != null)
            {
                switch (enumfacing)
                {
                    case NORTH:
                    default:
                        MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
                        break;

                    case SOUTH:
                        MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
                        break;

                    case WEST:
                        MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, i);
                        break;

                    case EAST:
                        MineshaftPiecesUA.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, i);
                }
            }
        }

        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox MutableBoundingBoxIn, ChunkPos p_74875_4_)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, MutableBoundingBoxIn))
            {
                return false;
            }
            else
            {
                this.fillWithBlocks(worldIn, MutableBoundingBoxIn, 0, 5, 0, 2, 7, 1, CAVE_AIR, CAVE_AIR, false);
                this.fillWithBlocks(worldIn, MutableBoundingBoxIn, 0, 0, 7, 2, 2, 8, CAVE_AIR, CAVE_AIR, false);

                for (int i = 0; i < 5; ++i)
                {
                    this.fillWithBlocks(worldIn, MutableBoundingBoxIn, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, CAVE_AIR, CAVE_AIR, false);
                }

                return true;
            }
        }
    }
}
