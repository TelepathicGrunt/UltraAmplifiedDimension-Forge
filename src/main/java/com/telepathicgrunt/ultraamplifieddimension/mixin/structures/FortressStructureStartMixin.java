package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.FortressStructure;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FortressStructure.Start.class)
public abstract class FortressStructureStartMixin {

    /**
     * @author TelepathicGrunt
     * @reason Raise Nether Fortresses in UAD to between y = 88 and 135
     */
    @Inject(
            method = "func_230364_a_(Lnet/minecraft/util/registry/DynamicRegistries;Lnet/minecraft/world/gen/ChunkGenerator;Lnet/minecraft/world/gen/feature/template/TemplateManager;IILnet/minecraft/world/biome/Biome;Lnet/minecraft/world/gen/feature/NoFeatureConfig;)V",
            at = @At(value = "TAIL")
    )
    private void adjustHeight(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config, CallbackInfo ci) {
        if(chunkGenerator instanceof UADChunkGenerator){
            ((StructureStartAccessor)this).callFunc_214626_a(((StructureStartAccessor)this).getRand(), 88, 135);
        }
    }
}