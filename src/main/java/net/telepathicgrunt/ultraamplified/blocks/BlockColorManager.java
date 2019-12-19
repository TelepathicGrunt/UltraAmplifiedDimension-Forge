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
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Bus.MOD)
public class BlockColorManager
{

	/**
	 * Register the {@link IBlockColor} handlers.
	 */
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onBlockColorsInit(ColorHandlerEvent.Block event)
	{
		final BlockColors blockColors = event.getBlockColors();

		//registers the colors for blocks that changes colors based on biome
		blockColors.register((p_210225_0_, p_210225_1_, p_210225_2_, p_210225_3_) ->
		{
			return p_210225_1_ != null && p_210225_2_ != null ? BiomeColors.getGrassColor(p_210225_1_, p_210225_2_) : GrassColors.get(0.5D, 1.0D);
		}, BlocksInit.GLOWGRASS_BLOCK);
	}


	/**
	 * Register the {@link IItemColor} handlers
	 */
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
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

		itemColors.register(itemBlockColourHandler, BlocksInit.GLOWGRASS_BLOCK);
	}
}