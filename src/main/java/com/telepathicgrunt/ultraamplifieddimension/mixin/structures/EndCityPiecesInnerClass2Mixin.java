package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.world.structures.markerpieces.MarkerPiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.structure.EndCityPieces;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(targets = "net/minecraft/world/gen/feature/structure/EndCityPieces$2")
public abstract class EndCityPiecesInnerClass2Mixin {

//    @Inject(
//            method = "generate(Lnet/minecraft/world/gen/feature/template/TemplateManager;ILnet/minecraft/world/gen/feature/structure/EndCityPieces$CityTemplate;Lnet/minecraft/util/math/BlockPos;Ljava/util/List;Ljava/util/Random;)Z",
//            at = @At("HEAD")
//    )
//    private void test(TemplateManager p_191086_1_, int p_191086_2_, EndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructurePiece> p_191086_5_, Random p_191086_6_, CallbackInfoReturnable<Boolean> cir){
//        int t = 5;
//    }
//
//    /**
//     * @author TelepathicGrunt
//     * @reason Make End Cities larger.
//     */
//    @ModifyVariable(
//            method = "generate(Lnet/minecraft/world/gen/feature/template/TemplateManager;ILnet/minecraft/world/gen/feature/structure/EndCityPieces$CityTemplate;Lnet/minecraft/util/math/BlockPos;Ljava/util/List;Ljava/util/Random;)Z",
//            at = @At(value = "STORE"), name = "i", ordinal = 0
//    )
//    private int largerCity(int i, TemplateManager templateManager, int depth, EndCityPieces.CityTemplate cityTemplate, BlockPos pos, List<StructurePiece> structurePieceList) {
//        for(StructurePiece piece : structurePieceList){
//            if(piece instanceof MarkerPiece){
//                return 4;
//            }
//        }
//
//        return i;
//    }
}