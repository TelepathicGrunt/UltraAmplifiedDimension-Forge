package net.telepathicgrunt.ultraamplified.blocks;


import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

public class BlocksInit {

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

	@ObjectHolder("ultra_amplified_mod:amplified_portal")
	public static AmplifiedPortalBlock AMPLIFIEDPORTAL;
	
	//registers the Blocks so they now exist in the registry
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
				new GlowstoneOreBlock(),
				new CoarseGlowdirtBlock(),
				new GlowdirtBlock(),
				new GlowgrassBlock(),
				new GlowmyceliumBlock(),
				new GlowpodzolBlock(),
				new GlowsandBlock(),
				new RedGlowsandBlock(),
				new AmplifiedPortalBlock()
				);
		
	}
	
	//registers the item version of the Blocks so they now exist in the registry
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				new BlockItem(BlocksInit.GLOWSTONE_ORE, new Item.Properties()).setRegistryName("glowstone_ore"),
				new BlockItem(BlocksInit.COARSE_GLOWDIRT, new Item.Properties()).setRegistryName("coarse_glowdirt"),
				new BlockItem(BlocksInit.GLOWDIRT, new Item.Properties()).setRegistryName("glowdirt"),
				new BlockItem(BlocksInit.GLOWGRASS_BLOCK, new Item.Properties()).setRegistryName("glowgrass_block"),
				new BlockItem(BlocksInit.GLOWMYCELIUM, new Item.Properties()).setRegistryName("glowmycelium"),
				new BlockItem(BlocksInit.GLOWPODZOL, new Item.Properties()).setRegistryName("glowpodzol"),
				new BlockItem(BlocksInit.GLOWSAND, new Item.Properties()).setRegistryName("glowsand"),
				new BlockItem(BlocksInit.REDGLOWSAND, new Item.Properties()).setRegistryName("red_glowsand"),
				new BlockItem(BlocksInit.AMPLIFIEDPORTAL, new Item.Properties()).setRegistryName("amplified_portal")
				);
	}

	
	//sets up the entry to be added to the forge registry
	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name) {
		Preconditions.checkNotNull(name, "Name to assign to entry cannot be null!");
		return setup(entry, new ResourceLocation(UltraAmplified.MODID, name));
	}

	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) {
		Preconditions.checkNotNull(entry, "Entry cannot be null!");
		Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
		entry.setRegistryName(registryName);
		return entry;
	}

}