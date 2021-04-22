package com.telepathicgrunt.ultraamplifieddimension.mixin.dimension;

import com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.UADBiomeProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.impl.LocateBiomeCommand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LocateBiomeCommand.class)
public class LocateBiomeCommandMixin {

    /**
     * Increase biome search radius in UAD if the original search returned no found biome
     * @author - TelepathicGrunt
     */
    @ModifyVariable(
            method = "func_241049_a_(Lnet/minecraft/command/CommandSource;Lnet/minecraft/util/ResourceLocation;)I",
            at = @At(value = "INVOKE_ASSIGN",target = "Lnet/minecraft/world/server/ServerWorld;getBiomeLocation(Lnet/minecraft/world/biome/Biome;Lnet/minecraft/util/math/BlockPos;II)Lnet/minecraft/util/math/BlockPos;"),
            ordinal = 1
    )
    private static BlockPos expandSearch(BlockPos blockPos, CommandSource source, ResourceLocation biomeID) {
        if(blockPos == null && source.getWorld().getChunkProvider().getChunkGenerator().getBiomeProvider() instanceof UADBiomeProvider){
            // Will never be null as the command already checked and validated that the biome exists
            Biome biome = source.getServer().getDynamicRegistries().getRegistry(Registry.BIOME_KEY).getOrDefault(biomeID);
            return source.getWorld().getBiomeLocation(biome, new BlockPos(source.getPos()), 36000, 36);
        }

        return blockPos;
    }
}