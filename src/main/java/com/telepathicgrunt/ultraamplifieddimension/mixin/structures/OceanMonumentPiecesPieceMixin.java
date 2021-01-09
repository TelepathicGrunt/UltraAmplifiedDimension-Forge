package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.structure.OceanMonumentPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OceanMonumentPieces.Piece.class)
public abstract class OceanMonumentPiecesPieceMixin {

    @Inject(
            method = "makeOpening(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/util/math/MutableBoundingBox;IIIIII)V",
            at = @At(value = "HEAD"), cancellable = true
    )
    private void noWater(ISeedReader world, MutableBoundingBox boundingBoxIn, int x1, int y1, int z1, int x2, int y2, int z2, CallbackInfo ci) {
        if(world.getWorld().getChunkProvider().getChunkGenerator() instanceof UADChunkGenerator){
            if(Math.abs(x1 - x2) > 6 || Math.abs(y1 - y2) > 6 || Math.abs(z1 - z2) > 6){
                // prevent the terrain carving with liquids everywhere
                ci.cancel();
            }
        }
    }
}