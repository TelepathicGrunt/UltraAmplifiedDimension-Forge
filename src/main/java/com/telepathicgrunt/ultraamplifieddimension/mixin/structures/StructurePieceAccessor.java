package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StructurePiece.class)
public interface StructurePieceAccessor {
    @Invoker("getBlockStateFromPos")
    BlockState uad_callGetBlockStateFromPos(IBlockReader worldIn, int x, int y, int z, MutableBoundingBox boundingboxIn);

    @Invoker("getXWithOffset")
    int uad_callGetXWithOffset(int x, int z);

    @Invoker("getYWithOffset")
    int uad_callGetYWithOffset(int y);

    @Invoker("getZWithOffset")
    int uad_callGetZWithOffset(int x, int z);

    @Invoker("randomlyRareFillWithBlocks")
    void uad_callRandomlyRareFillWithBlocks(ISeedReader worldIn, MutableBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState blockstateIn, boolean excludeAir);

    @Invoker("fillWithBlocks")
    void uad_callFillWithBlocks(ISeedReader worldIn, MutableBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, BlockState boundaryBlockState, BlockState insideBlockState, boolean existingOnly);

    @Invoker("setBlockState")
    void uad_callSetBlockState(ISeedReader worldIn, BlockState blockstateIn, int x, int y, int z, MutableBoundingBox boundingboxIn);
}
