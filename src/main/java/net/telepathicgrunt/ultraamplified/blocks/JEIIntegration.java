package net.telepathicgrunt.ultraamplified.blocks;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.telepathicgrunt.ultraamplified.UltraAmplified;

@JeiPlugin
public class JEIIntegration implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid() {
	return new ResourceLocation(UltraAmplified.MODID+"jei_plugin");
    }
    
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
	addInfo(registration, UABlocks.AMPLIFIEDPORTAL.get());
	addInfo(registration, UABlocks.CACTUSBODYBLOCKUA.get());
	addInfo(registration, UABlocks.CACTUSCORNERBLOCKUA.get());
	addInfo(registration, UABlocks.CACTUSMAINBLOCKUA.get());
	addInfo(registration, UABlocks.COARSE_GLOWDIRT.get());
	addInfo(registration, UABlocks.GLOWDIRT.get());
	addInfo(registration, UABlocks.GLOWGRASS_BLOCK.get());
	addInfo(registration, UABlocks.GLOWMYCELIUM.get());
	addInfo(registration, UABlocks.GLOWPODZOL.get());
	addInfo(registration, UABlocks.GLOWSAND.get());
	addInfo(registration, UABlocks.GLOWSTONE_ORE.get());
	addInfo(registration, UABlocks.REDGLOWSAND.get());
    }

    
    private static void addInfo(IRecipeRegistration registration, Block block) {
	registration.addIngredientInfo(
		new ItemStack(block), 
		VanillaTypes.ITEM, 
		UltraAmplified.MODID+"."+block.getRegistryName().getPath()+".jei_description");
    }
}
