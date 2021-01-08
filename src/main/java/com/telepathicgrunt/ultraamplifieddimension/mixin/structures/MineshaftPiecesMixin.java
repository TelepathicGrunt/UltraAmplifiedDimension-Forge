package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import com.telepathicgrunt.ultraamplifieddimension.world.structures.MineshaftPiecesUtils;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.DesertPyramidPiece;
import net.minecraft.world.gen.feature.structure.MineshaftPieces;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MineshaftPieces.Room.class)
public abstract class MineshaftPiecesMixin {

    /**
     * @author TelepathicGrunt
     * @reason Creates giant Mineshaft pit room and updates all fluids within the giant room Mineshafts in Ultra Amplified Dimension
     */
    @Inject(
            method = "func_230383_a_(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/world/gen/feature/structure/StructureManager;Lnet/minecraft/world/gen/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/MutableBoundingBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At(value = "TAIL")
    )
    private void giantRoom(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (chunkGenerator instanceof UADChunkGenerator) {
            if(random.nextFloat() < 0.25f){
                MineshaftPiecesUtils.generateLargeRoom(world, ((MineshaftPieces.Room)(Object)this), mutableBoundingBox);
            }
            else{
                MineshaftPiecesUtils.generateFloorRoom(world, ((MineshaftPieces.Room)(Object)this), mutableBoundingBox);
            }
        }
    }
}