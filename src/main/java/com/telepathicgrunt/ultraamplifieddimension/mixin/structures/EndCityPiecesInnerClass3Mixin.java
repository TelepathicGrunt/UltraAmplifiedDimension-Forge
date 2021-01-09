package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net/minecraft/world/gen/feature/structure/EndCityPieces$3")
public abstract class EndCityPiecesInnerClass3Mixin {
//
//    /**
//     * @author TelepathicGrunt
//     * @reason Make End Cities larger.
//     */
//    @ModifyConstant(
//            method = "generate(Lnet/minecraft/world/gen/feature/template/TemplateManager;ILnet/minecraft/world/gen/feature/structure/EndCityPieces$CityTemplate;Lnet/minecraft/util/math/BlockPos;Ljava/util/List;Ljava/util/Random;)Z",
//            slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", shift = At.Shift.BEFORE)),
//            constant = @Constant(intValue = 3)
//    )
//    private int largerCity(int value, TemplateManager templateManager, int depth, EndCityPieces.CityTemplate cityTemplate, BlockPos pos, List<StructurePiece> structurePieceList) {
//        for(StructurePiece piece : structurePieceList){
//            if(piece instanceof MarkerPiece){
//                return Integer.MIN_VALUE;
//            }
//        }
//
//        return value;
//    }
}