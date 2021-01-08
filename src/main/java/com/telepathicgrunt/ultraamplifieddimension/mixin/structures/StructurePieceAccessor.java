package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StructurePiece.class)
public interface StructurePieceAccessor {
    @Invoker
    BlockState callGetBlockStateFromPos(IBlockReader worldIn, int x, int y, int z, MutableBoundingBox boundingboxIn);

    @Invoker
    int callGetXWithOffset(int x, int z);

    @Invoker
    int callGetYWithOffset(int y);

    @Invoker
    int callGetZWithOffset(int x, int z);

    @Invoker
    void callRandomlyRareFillWithBlocks(ISeedReader worldIn, MutableBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState blockstateIn, boolean excludeAir);

    @Invoker
    void callFillWithBlocks(ISeedReader worldIn, MutableBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, BlockState boundaryBlockState, BlockState insideBlockState, boolean existingOnly);
}
