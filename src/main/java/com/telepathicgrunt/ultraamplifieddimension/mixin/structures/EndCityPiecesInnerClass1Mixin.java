package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net/minecraft/world/gen/feature/structure/EndCityPieces$1")
public abstract class EndCityPiecesInnerClass1Mixin {

//    /**
//     * @author TelepathicGrunt
//     * @reason Make End Cities larger.
//     */
//    @ModifyVariable(
//            method = "generate(Lnet/minecraft/world/gen/feature/template/TemplateManager;ILnet/minecraft/world/gen/feature/structure/EndCityPieces$CityTemplate;Lnet/minecraft/util/math/BlockPos;Ljava/util/List;Ljava/util/Random;)Z",
//            at = @At(value = "STORE"), name = "i"
//    )
//    private int largerCity(int i, TemplateManager templateManager, int depth, EndCityPieces.CityTemplate cityTemplate, BlockPos pos, List<StructurePiece> structurePieceList) {
//        for(StructurePiece piece : structurePieceList){
//            if(piece instanceof MarkerPiece){
//                return 2;
//            }
//        }
//
//        return i;
//    }
}