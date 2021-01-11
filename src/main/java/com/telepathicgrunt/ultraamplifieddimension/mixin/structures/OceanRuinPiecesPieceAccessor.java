package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.structure.OceanRuinPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(OceanRuinPieces.Piece.class)
public interface OceanRuinPiecesPieceAccessor {
    @Accessor("rotation")
    Rotation uad_getRotation();
}
