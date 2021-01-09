package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import com.telepathicgrunt.ultraamplifieddimension.world.structures.OceanMonumentPiecesUtils;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.OceanMonumentPieces;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(OceanMonumentPieces.MonumentBuilding.class)
public abstract class OceanMonumentPiecesMonumentBuildingMixin {

    /**
     * Prevents the water carving of monuments
     * @author TelepathicGrunt
     * @reason Make Ocean Monuments not turn everything around them into water in Ultra Amplified Dimension.
     */
    @ModifyVariable(
            method = "func_230383_a_(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/world/gen/feature/structure/StructureManager;Lnet/minecraft/world/gen/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/MutableBoundingBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At(value = "STORE", ordinal = 0), ordinal = 0
    )
    private int noWater(int i, ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox mutableBoundingBox) {
        if(chunkGenerator instanceof UADChunkGenerator){
            // Places water correctly that conforms to the monument's shape
            OceanMonumentPiecesUtils.generateWaterBox(world, chunkGenerator, ((OceanMonumentPieces.MonumentBuilding)(Object)this), mutableBoundingBox);

            // This causes the normal ridiculous makeOpening to not place any water as it's min y passed in is 0.
            return -1;
        }

        return i;
    }
}