package com.telepathicgrunt.ultraamplifieddimension.mixin.dimension;

import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkGenerator.class)
public interface ChunkGeneratorAccessor {
    @Accessor("biomeProvider")
    BiomeProvider getbiomeProvider();
}