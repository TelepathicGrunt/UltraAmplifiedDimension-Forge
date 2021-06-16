package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.EndCityStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EndCityStructure.Start.class)
public abstract class EndCityStructureStartMixin {

    /**
     * @author TelepathicGrunt
     * @reason Make End Cities not be placed so high in Ultra Amplified Dimension's dimension and cause the game to die due to out of bounds world deadlock.
     */
    @ModifyVariable(
            method = "func_230364_a_(Lnet/minecraft/util/registry/DynamicRegistries;Lnet/minecraft/world/gen/ChunkGenerator;Lnet/minecraft/world/gen/feature/template/TemplateManager;IILnet/minecraft/world/biome/Biome;Lnet/minecraft/world/gen/feature/NoFeatureConfig;)V",
            at = @At(value = "STORE", ordinal = 0), ordinal = 2
    )
    private int uad_fixedYHeightForUAD(int y, DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator) {
        if(chunkGenerator instanceof UADChunkGenerator){
            return 105;
        }
        return y;
    }
}