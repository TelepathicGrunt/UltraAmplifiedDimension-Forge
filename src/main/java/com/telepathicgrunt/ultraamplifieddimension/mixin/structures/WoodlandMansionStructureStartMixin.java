package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADChunkGenerator;
import com.telepathicgrunt.ultraamplifieddimension.world.structures.markerpieces.MarkerPiece;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.EndCityStructure;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.WoodlandMansionStructure;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WoodlandMansionStructure.Start.class)
public abstract class WoodlandMansionStructureStartMixin {

    /**
     * @author TelepathicGrunt
     * @reason Make Woodland Mansions not be placed so high in Ultra Amplified Dimension's dimension and cause the game to die due to out of bounds world deadlock.
     */
    @ModifyVariable(
            method = "func_230364_a_(Lnet/minecraft/util/registry/DynamicRegistries;Lnet/minecraft/world/gen/ChunkGenerator;Lnet/minecraft/world/gen/feature/template/TemplateManager;IILnet/minecraft/world/biome/Biome;Lnet/minecraft/world/gen/feature/NoFeatureConfig;)V",
            at = @At(value = "STORE"), name = "i2"
    )
    private int fixedYHeightForUAD(int i2, DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator) {
        if(chunkGenerator instanceof UADChunkGenerator){
            return Math.min(i2, 215);
        }
        return i2;
    }
}