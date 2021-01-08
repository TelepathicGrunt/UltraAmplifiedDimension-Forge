package com.telepathicgrunt.ultraamplifieddimension.world.structures.markerpieces;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;

import java.util.Random;

public class MarkerPiece extends TemplateStructurePiece {
    public MarkerPiece(IStructurePieceType structurePieceTypeIn, int componentTypeIn) {
        super(structurePieceTypeIn, componentTypeIn);
        this.boundingBox = new MutableBoundingBox();
        this.templatePosition = new BlockPos(0,0,0);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
    }
}
