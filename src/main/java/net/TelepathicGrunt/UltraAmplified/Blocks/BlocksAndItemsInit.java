package net.telepathicgrunt.ultraamplified.Blocks;


import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@ObjectHolder(UltraAmplified.MOD_ID)
public class BlocksAndItemsInit
{

	//creative tab to hold our block items
	static final CreativeTabs UA = (new CreativeTabs(UltraAmplified.MOD_ID) {

		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(AMPLIFIEDPORTAL);
		}
		
	});
	
	//Instances of the modded blocks to use anywhere else in this mod
	@ObjectHolder("ultra_amplified_mod:amplified_portal")
	public static AmplifiedPortalBlock AMPLIFIEDPORTAL;

	

	/**
	 * registers the Blocks so they now exist in the registry
	 * 
	 * @param event - registry to add blocks to
	 */
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(
				new AmplifiedPortalBlock()
				.setCreativeTab(UA)
		);
	}


	/**
	 * registers the item version of the Blocks so they now exist in the registry
	 * 
	 * @param event - registry to add items to
	 */
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(
				new ItemBlock(BlocksAndItemsInit.AMPLIFIEDPORTAL)
				.setRegistryName(BlocksAndItemsInit.AMPLIFIEDPORTAL.getRegistryName())
		);
	}

}