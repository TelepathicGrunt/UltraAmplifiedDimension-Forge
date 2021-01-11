package com.telepathicgrunt.ultraamplifieddimension.mixin.features;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(Template.class)
public interface TemplateInvoker {

    @Accessor("blocks")
    List<Template.Palette> uad_getBlocks();

    @Accessor("entities")
    List<Template.EntityInfo> uad_getEntities();

    @Accessor("size")
    BlockPos uad_getSize();

    @Invoker("addEntitiesToWorld")
    void uad_invokeSpawnEntities(IServerWorld serverIWorld, BlockPos pos, PlacementSettings placementIn);
}
