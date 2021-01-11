package com.telepathicgrunt.ultraamplifieddimension.world.structures;

import com.telepathicgrunt.ultraamplifieddimension.mixin.structures.MineshaftRoomAccessor;
import com.telepathicgrunt.ultraamplifieddimension.mixin.structures.StructurePieceAccessor;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.MineshaftPieces;

public class MineshaftPiecesUtils {
    // called in structures/MineshaftPiecesMixin
    public static void generateFloorRoom(ISeedReader world, MineshaftPieces.Room room, MutableBoundingBox mutableBoundingBox){
        MutableBoundingBox box = room.getBoundingBox();
        ((StructurePieceAccessor)room).uad_callFillWithBlocks(world, mutableBoundingBox, box.minX, box.minY, box.minZ, box.maxX, box.minY, box.maxZ, Blocks.COARSE_DIRT.getDefaultState(), Blocks.CAVE_AIR.getDefaultState(), false);
    }

    // called in structures/MineshaftPiecesMixin
    public static void generateLargeRoom(ISeedReader world, MineshaftPieces.Room room, MutableBoundingBox mutableBoundingBox){
        MutableBoundingBox box = room.getBoundingBox();
        box.expandTo(new MutableBoundingBox(
                mutableBoundingBox.minX,
                box.minY,
                mutableBoundingBox.minZ,
                mutableBoundingBox.maxX,
                box.maxY + Math.min(140, 225 - box.minY),
                mutableBoundingBox.maxZ));

        // floor
        ((StructurePieceAccessor)room).uad_callFillWithBlocks(world, mutableBoundingBox, box.minX, box.minY, box.minZ, box.maxX + 8, box.minY, box.maxZ, Blocks.COARSE_DIRT.getDefaultState(), Blocks.CAVE_AIR.getDefaultState(), false);
        ((StructurePieceAccessor)room).uad_callFillWithBlocks(world, mutableBoundingBox, box.minX + 3, box.minY + 1, box.minZ + 3, box.maxX - 1, box.minY + 4, box.maxZ - 1, Blocks.CAVE_AIR.getDefaultState(), Blocks.CAVE_AIR.getDefaultState(), false);

        for (MutableBoundingBox MutableBoundingBox : ((MineshaftRoomAccessor)room).uad_getConnectedRooms()) {
            ((StructurePieceAccessor)room).uad_callFillWithBlocks(world, mutableBoundingBox, MutableBoundingBox.minX, MutableBoundingBox.maxY - 2, MutableBoundingBox.minZ, MutableBoundingBox.maxX, MutableBoundingBox.maxY, MutableBoundingBox.maxZ, Blocks.CAVE_AIR.getDefaultState(), Blocks.CAVE_AIR.getDefaultState(), false);
        }

        // wall
        ((StructurePieceAccessor)room).uad_callRandomlyRareFillWithBlocks(world, mutableBoundingBox, box.minX + 3, box.minY + 4, box.minZ + 3, box.maxX - 3, box.maxY, box.maxZ - 3, Blocks.CAVE_AIR.getDefaultState(), false);
        MineshaftPiecesUtils.updateLiquidBlocks(room, world, box, box.minX - 1, box.minY + 4, box.minZ - 1, box.maxX + 1, box.maxY, box.maxZ + 1);
    }

    // Prevents walls of water that doesnt flow or move.
    public static void updateLiquidBlocks(MineshaftPieces.Room room, IWorld world, MutableBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        float f = maxX - minX + 1;
        float f1 = maxY - minY + 1;
        float f2 = maxZ - minZ + 1;
        float f3 = minX + f / 2.0F;
        float f4 = minZ + f2 / 2.0F;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int y = minY; y <= maxY; ++y) {
            float yModifier = (y - minY) / f1;

            for (int x = minX; x <= maxX; ++x) {
                float xModifier = (x - f3) / (f * 0.5F);

                for (int z = minZ; z <= maxZ; ++z) {
                    float zModifier = (z - f4) / (f2 * 0.5F);
                    if (!((StructurePieceAccessor) room).uad_callGetBlockStateFromPos(world, x, y, z, boundingboxIn).getFluidState().isEmpty()) {
                        float threshold = (xModifier * xModifier) + (yModifier * yModifier) + (zModifier * zModifier);
                        if (threshold <= 1.05F) {
                            mutable.setPos(((StructurePieceAccessor) room).uad_callGetXWithOffset(x, z), ((StructurePieceAccessor) room).uad_callGetYWithOffset(y), ((StructurePieceAccessor) room).uad_callGetZWithOffset(x, z));

                            FluidState ifluidstate = world.getFluidState(mutable);
                            if (!ifluidstate.isEmpty()) {
                                world.getPendingFluidTicks().scheduleTick(mutable, ifluidstate.getFluid(), 0);

                            }
                        }
                    }
                }
            }
        }
    }
}
