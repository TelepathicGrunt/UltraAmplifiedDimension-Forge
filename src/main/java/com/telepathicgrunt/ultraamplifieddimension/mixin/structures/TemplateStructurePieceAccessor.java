package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.Template;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TemplateStructurePiece.class)
public interface TemplateStructurePieceAccessor {
    @Accessor
    Template getTemplate();

    @Accessor
    BlockPos getTemplatePosition();
}
