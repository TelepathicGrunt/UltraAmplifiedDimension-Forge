package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import com.telepathicgrunt.ultraamplifieddimension.world.structures.OceanStructurePiecesUtils;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.ShipwreckPieces;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(ShipwreckPieces.Piece.class)
public abstract class ShipwreckPiecesPieceMixin {

    /**
     * @author TelepathicGrunt
     * @reason Make Shipwrecks be placed at various heights under ledges as well in Ultra Amplified Dimension.
     */
    @ModifyVariable(
            method = "func_230383_a_(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/world/gen/feature/structure/StructureManager;Lnet/minecraft/world/gen/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/MutableBoundingBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At(value = "STORE"), name = "i1"
    )
    private int fixedYHeightForUAD(int i1, ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random) {
        if (chunkGenerator instanceof UADChunkGenerator) {
            ShipwreckPieces.Piece piece = ((ShipwreckPieces.Piece)(Object)this);
            return OceanStructurePiecesUtils.getNewLedgeHeight(world, chunkGenerator, random, ((TemplateStructurePieceAccessor)piece).getTemplate(), ((ShipwreckPiecesPieceAccessor)piece).getRotation(), ((TemplateStructurePieceAccessor)piece).getTemplatePosition());
        }
        return i1;
    }
}