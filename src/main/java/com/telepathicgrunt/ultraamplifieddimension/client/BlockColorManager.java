package com.telepathicgrunt.ultraamplifieddimension.client;

import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;


public class BlockColorManager
{
	public static void onBlockColorsInit(ColorHandlerEvent.Block event)
	{
		final BlockColors blockColors = event.getBlockColors();

		//registers the colors for blocks that changes colors based on biome
		blockColors.register((unknown1, lightReader, pos, unknown2) ->
				lightReader != null && pos != null ? BiomeColors.getGrassColor(lightReader, pos) : GrassColors.get(0.5D, 1.0D), UADBlocks.GLOWGRASS_BLOCK.get());
	}


	public static void onItemColorsInit(ColorHandlerEvent.Item event)
	{
		final BlockColors blockColors = event.getBlockColors();
		final ItemColors itemColors = event.getItemColors();

		// Use the Block's colour handler for an ItemBlock
		final IItemColor itemBlockColourHandler = (stack, tintIndex) ->
		{
			final BlockState state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
			return blockColors.getColor(state, null, null, tintIndex);
		};

		itemColors.register(itemBlockColourHandler, UADBlocks.GLOWGRASS_BLOCK.get());
	}
}