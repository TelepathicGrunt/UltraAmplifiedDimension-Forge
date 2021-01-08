package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Random;

@Mixin(StructureStart.class)
public interface StructureStartAccessor {
    @Invoker
    void callRecalculateStructureSize();

    @Invoker
    void callFunc_214626_a(Random p_214626_1_, int p_214626_2_, int p_214626_3_);

    @Accessor
    SharedSeedRandom getRand();

    @Accessor
    MutableBoundingBox getBounds();

    @Accessor
    List<StructurePiece> getComponents();
}
