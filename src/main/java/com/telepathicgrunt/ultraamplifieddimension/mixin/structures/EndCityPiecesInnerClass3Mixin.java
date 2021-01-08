package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import com.telepathicgrunt.ultraamplifieddimension.world.structures.markerpieces.MarkerPiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.EndCityPieces;
import net.minecraft.world.gen.feature.structure.EndCityStructure;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

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