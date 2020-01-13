package net.telepathicgrunt.ultraamplified.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Bus.MOD, value = { Dist.CLIENT })
public class BlockColorManager
{

	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Bus.MOD, value = { Dist.CLIENT })
	private static class ForgeEvents
	{
		/**
		 * Register the {@link IBlockColor} handlers.
		 */
		@SubscribeEvent
		public static void onBlockColorsInit(ColorHandlerEvent.Block event)
		{
			final BlockColors blockColors = event.getBlockColors();
	
			//registers the colors for blocks that changes colors based on biome
			blockColors.register((unknown1, lightReader, pos, unknown2) ->
			{
				return lightReader != null && pos != null ? BiomeColors.getGrassColor(lightReader, pos) : GrassColors.get(0.5D, 1.0D);
			}, BlocksInit.GLOWGRASS_BLOCK.get());
		}
	
	
		/**
		 * Register the {@link IItemColor} handlers
		 */
		@SubscribeEvent
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
	
			itemColors.register(itemBlockColourHandler, BlocksInit.GLOWGRASS_BLOCK.get());
		}
	}
}