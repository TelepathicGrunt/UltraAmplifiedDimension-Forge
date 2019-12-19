package net.telepathicgrunt.ultraamplified.blocks;

import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;


public class BlocksInit
{

	//creative tab to hold our block items
	public static final ItemGroup UA = new ItemGroup(ItemGroup.GROUPS.length, UltraAmplified.MODID)
	{
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon()
		{
			return new ItemStack(AMPLIFIEDPORTAL);
		}
	};
	public static final ItemGroup ULTRAMAPLIFIED = UA;

	//Instances of the modded blocks to use anywhere else in this mod
	@ObjectHolder("ultra_amplified_mod:amplified_portal")
	public static AmplifiedPortalBlock AMPLIFIEDPORTAL;

	@ObjectHolder("ultra_amplified_mod:glowstone_ore")
	public static GlowstoneOreBlock GLOWSTONE_ORE;

	@ObjectHolder("ultra_amplified_mod:coarse_glowdirt")
	public static CoarseGlowdirtBlock COARSE_GLOWDIRT;

	@ObjectHolder("ultra_amplified_mod:glowdirt")
	public static GlowdirtBlock GLOWDIRT;

	@ObjectHolder("ultra_amplified_mod:glowgrass_block")
	public static GlowgrassBlock GLOWGRASS_BLOCK;

	@ObjectHolder("ultra_amplified_mod:glowmycelium")
	public static GlowmyceliumBlock GLOWMYCELIUM;

	@ObjectHolder("ultra_amplified_mod:glowpodzol")
	public static GlowpodzolBlock GLOWPODZOL;

	@ObjectHolder("ultra_amplified_mod:glowsand")
	public static GlowsandBlock GLOWSAND;

	@ObjectHolder("ultra_amplified_mod:red_glowsand")
	public static RedGlowsandBlock REDGLOWSAND;

	@ObjectHolder("ultra_amplified_mod:cactus_body_block_ua")
	public static CactusBodyBlockUA CACTUSBODYBLOCKUA;

	@ObjectHolder("ultra_amplified_mod:cactus_corner_block_ua")
	public static CactusCornerBlockUA CACTUSCORNERBLOCKUA;

	@ObjectHolder("ultra_amplified_mod:cactus_main_block_ua")
	public static CactusMainBlockUA CACTUSMAINBLOCKUA;


	/**
	 * registers the Blocks so they now exist in the registry
	 * 
	 * @param event - registry to add blocks to
	 */
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
				new AmplifiedPortalBlock(), 
				new GlowstoneOreBlock(), 
				new CoarseGlowdirtBlock(), 
				new GlowdirtBlock(),
				new GlowgrassBlock(), 
				new GlowmyceliumBlock(), 
				new GlowpodzolBlock(), 
				new GlowsandBlock(), 
				new RedGlowsandBlock(), 
				new CactusBodyBlockUA(),
				new CactusCornerBlockUA(), 
				new CactusMainBlockUA());
	}


	/**
	 * registers the item version of the Blocks so they now exist in the registry
	 * 
	 * @param event - registry to add items to
	 */
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new BlockItem(BlocksInit.AMPLIFIEDPORTAL, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("amplified_portal"),
				new BlockItem(BlocksInit.GLOWSTONE_ORE, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowstone_ore"),
				new BlockItem(BlocksInit.COARSE_GLOWDIRT, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("coarse_glowdirt"),
				new BlockItem(BlocksInit.GLOWDIRT, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowdirt"),
				new BlockItem(BlocksInit.GLOWGRASS_BLOCK, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowgrass_block"),
				new BlockItem(BlocksInit.GLOWMYCELIUM, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowmycelium"),
				new BlockItem(BlocksInit.GLOWPODZOL, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowpodzol"),
				new BlockItem(BlocksInit.GLOWSAND, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("glowsand"),
				new BlockItem(BlocksInit.REDGLOWSAND, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("red_glowsand"),
				new BlockItem(BlocksInit.CACTUSBODYBLOCKUA, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("cactus_body_block_ua"),
				new BlockItem(BlocksInit.CACTUSCORNERBLOCKUA, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("cactus_corner_block_ua"),
				new BlockItem(BlocksInit.CACTUSMAINBLOCKUA, new Item.Properties().group(ULTRAMAPLIFIED)).setRegistryName("cactus_main_block_ua"));
	}

}