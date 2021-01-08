package com.telepathicgrunt.ultraamplifieddimension.mixin.dimension;

import net.minecraft.util.IObjectIntIterable;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BiomeContainer.class)
public interface BiomeContainerAccessor {
    @Accessor
    IObjectIntIterable<Biome> getBiomeRegistry();
}
