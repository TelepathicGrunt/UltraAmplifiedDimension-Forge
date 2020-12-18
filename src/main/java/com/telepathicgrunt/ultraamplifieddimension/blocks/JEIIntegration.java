package com.telepathicgrunt.ultraamplifieddimension.blocks;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

@JeiPlugin
public class JEIIntegration implements IModPlugin {

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(UltraAmplifiedDimension.MODID + "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        addInfo(registration, UADBlocks.AMPLIFIED_PORTAL.get());
        addInfo(registration, UADBlocks.BIG_CACTUS_BODY_BLOCK.get());
        addInfo(registration, UADBlocks.BIG_CACTUS_CORNER_BLOCK.get());
        addInfo(registration, UADBlocks.BIG_CACTUS_MAIN_BLOCK.get());
        addInfo(registration, UADBlocks.COARSE_GLOWDIRT.get());
        addInfo(registration, UADBlocks.GLOWDIRT.get());
        addInfo(registration, UADBlocks.GLOWGRASS_BLOCK.get());
        addInfo(registration, UADBlocks.GLOWMYCELIUM.get());
        addInfo(registration, UADBlocks.GLOWPODZOL.get());
        addInfo(registration, UADBlocks.GLOWSAND.get());
        addInfo(registration, UADBlocks.GLOWSTONE_ORE.get());
        addInfo(registration, UADBlocks.RED_GLOWSAND.get());
    }


    private static void addInfo(IRecipeRegistration registration, Block block) {
        registration.addIngredientInfo(
                new ItemStack(block),
                VanillaTypes.ITEM,
                UltraAmplifiedDimension.MODID + "." + block.getRegistryName().getPath() + ".jei_description");
    }
}
