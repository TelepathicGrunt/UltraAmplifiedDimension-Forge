package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.SwampHutPiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SwampHutPiece.class)
public abstract class SwampHutPieceMixin {
    
    /**
     * @author TelepathicGrunt
     * @reason Place Witch Huts on top of land properly
     */
    @Inject(
            method = "func_230383_a_(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/world/gen/feature/structure/StructureManager;Lnet/minecraft/world/gen/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/MutableBoundingBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At(target = "Lnet/minecraft/world/gen/feature/structure/SwampHutPiece;fillWithBlocks(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/util/math/MutableBoundingBox;IIIIIILnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Z)V",
                    value = "INVOKE",
                    ordinal = 0)
    )
    private void uad_fixedYHeightForUAD(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(chunkGenerator instanceof UADChunkGenerator){
            MutableBoundingBox box = ((SwampHutPiece)(Object)this).getBoundingBox();
            ((SwampHutPiece)(Object)this).getBoundingBox().offset(0, chunkGenerator.getHeight(box.minX + (box.getXSize() / 2), box.minZ + (box.getZSize() / 2), Heightmap.Type.WORLD_SURFACE_WG) - box.minY + 2, 0);
        }
    }
}