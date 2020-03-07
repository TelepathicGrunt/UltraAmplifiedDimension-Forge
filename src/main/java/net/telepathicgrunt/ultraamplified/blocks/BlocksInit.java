package net.telepathicgrunt.ultraamplified.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class BlocksInit
{
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, UltraAmplified.MODID);

	public static final RegistryObject<Block> AMPLIFIEDPORTAL = BLOCKS.register("amplified_portal", () -> new AmplifiedPortalBlock());

	public static final RegistryObject<Block> GLOWSTONE_ORE = BLOCKS.register("glowstone_ore", () -> new GlowstoneOreBlock());

	public static final RegistryObject<Block> COARSE_GLOWDIRT = BLOCKS.register("coarse_glowdirt", () -> new CoarseGlowdirtBlock());

	public static final RegistryObject<Block> GLOWDIRT = BLOCKS.register("glowdirt", () -> new GlowdirtBlock());

	public static final RegistryObject<Block> GLOWGRASS_BLOCK = BLOCKS.register("glowgrass_block", () -> new GlowgrassBlock());

	public static final RegistryObject<Block> GLOWMYCELIUM = BLOCKS.register("glowmycelium", () -> new GlowmyceliumBlock());

	public static final RegistryObject<Block> GLOWPODZOL = BLOCKS.register("glowpodzol", () -> new GlowpodzolBlock());

	public static final RegistryObject<Block> GLOWSAND = BLOCKS.register("glowsand", () -> new GlowsandBlock());

	public static final RegistryObject<Block> REDGLOWSAND = BLOCKS.register("red_glowsand", () -> new RedGlowsandBlock());

	public static final RegistryObject<Block> CACTUSBODYBLOCKUA = BLOCKS.register("cactus_body_block_ua", () -> new CactusBodyBlockUA());

	public static final RegistryObject<Block> CACTUSCORNERBLOCKUA = BLOCKS.register("cactus_corner_block_ua", () -> new CactusCornerBlockUA());

	public static final RegistryObject<Block> CACTUSMAINBLOCKUA = BLOCKS.register("cactus_main_block_ua", () -> new CactusMainBlockUA());

	//creative tab to hold our block items
	public static final ItemGroup ULTRAMAPLIFIED = new ItemGroup(ItemGroup.GROUPS.length, UltraAmplified.MODID)
	{
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon()
		{
			return new ItemStack(AMPLIFIEDPORTAL.get());
		}
	};


	/**
	 * registers the Blocks so they now exist in the registry
	 * 
	 * @param event - registry to add blocks to
	 */
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(new AmplifiedPortalBlock(), new GlowstoneOreBlock(), new CoarseGlowdirtBlock(), new GlowdirtBlock(), new GlowgrassBlock(), new GlowmyceliumBlock(), new GlowpodzolBlock(), new GlowsandBlock(), new RedGlowsandBlock(), new CactusBodyBlockUA(), new CactusCornerBlockUA(), new CactusMainBlockUA());
	}


	/**
	 * registers the item version of the Blocks so they now exist in the registry
	 * 
	 * @param event - registry to add items to
	 */
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(new BlockItem(BlocksInit.AMPLIFIEDPORTAL.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("amplified_portal"), new BlockItem(BlocksInit.GLOWSTONE_ORE.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowstone_ore"), new BlockItem(BlocksInit.COARSE_GLOWDIRT.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("coarse_glowdirt"),
				new BlockItem(BlocksInit.GLOWDIRT.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowdirt"), new BlockItem(BlocksInit.GLOWGRASS_BLOCK.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowgrass_block"), new BlockItem(BlocksInit.GLOWMYCELIUM.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowmycelium"),
				new BlockItem(BlocksInit.GLOWPODZOL.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowpodzol"), new BlockItem(BlocksInit.GLOWSAND.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowsand"), new BlockItem(BlocksInit.REDGLOWSAND.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("red_glowsand"),
				new BlockItem(BlocksInit.CACTUSBODYBLOCKUA.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("cactus_body_block_ua"), new BlockItem(BlocksInit.CACTUSCORNERBLOCKUA.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("cactus_corner_block_ua"), new BlockItem(BlocksInit.CACTUSMAINBLOCKUA.get(), new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("cactus_main_block_ua"));
	}

}