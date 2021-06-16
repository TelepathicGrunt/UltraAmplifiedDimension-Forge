package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import com.telepathicgrunt.ultraamplifieddimension.world.structures.OceanStructurePiecesUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.ShipwreckPieces;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ShipwreckPieces.Piece.class)
public abstract class ShipwreckPiecesPieceMixin {

    /**
     * @author TelepathicGrunt
     * @reason Make Shipwrecks be placed at various heights under ledges as well in Ultra Amplified Dimension.
     */
    @Inject(
            method = "func_230383_a_(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/world/gen/feature/structure/StructureManager;Lnet/minecraft/world/gen/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/MutableBoundingBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/structure/TemplateStructurePiece;func_230383_a_(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/world/gen/feature/structure/StructureManager;Lnet/minecraft/world/gen/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/MutableBoundingBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)Z")
    )
    private void uad_fixedYHeightForUAD(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPosX, BlockPos chunkPosZ, CallbackInfoReturnable<Boolean> cir) {
        if (chunkGenerator instanceof UADChunkGenerator) {
            ShipwreckPieces.Piece piece = ((ShipwreckPieces.Piece)(Object)this);
            int newHeight = OceanStructurePiecesUtils.getNewLedgeHeight(world, chunkGenerator, random, ((TemplateStructurePieceAccessor)piece).uad_getTemplate(), ((ShipwreckPiecesPieceAccessor)piece).uad_getRotation(), ((TemplateStructurePieceAccessor)piece).uad_getTemplatePosition());
            BlockPos oldPos = ((TemplateStructurePieceAccessor)piece).uad_getTemplatePosition();
            ((TemplateStructurePieceAccessor)piece).uad_setTemplatePosition(new BlockPos(oldPos.getX(), newHeight, oldPos.getZ()));
        }
    }
}