package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.structure.ShipwreckPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ShipwreckPieces.Piece.class)
public interface ShipwreckPiecesPieceAccessor {
    @Accessor("rotation")
    Rotation uad_getRotation();
}
