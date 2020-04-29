package net.telepathicgrunt.ultraamplified.world.dimension;

import java.util.function.BiFunction;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.utils.RegUtil;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UADimensionRegistration
{
	private static final ResourceLocation ULTRAAMPLIFIED_ID = new ResourceLocation(UltraAmplified.MODID, "ultraamplified");


	public static DimensionType ultraamplified()
	{
		return DimensionType.byName(ULTRAAMPLIFIED_ID);
	}

	public static final ModDimension ULTRAAMPLIFIED = new ModDimension()
	{
		@Override
		public BiFunction<World, DimensionType, ? extends Dimension> getFactory()
		{
			return UADimension::new;
		}
	};

	//registers the dimension
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{
		@SubscribeEvent
		public static void registerDimensions(RegisterDimensionsEvent event)
		{
			if (DimensionType.byName(ULTRAAMPLIFIED_ID) == null)
			{
				DimensionManager.registerDimension(ULTRAAMPLIFIED_ID, ULTRAAMPLIFIED, null, true);
			}
		}
	}


	@SubscribeEvent
	public static void registerModDimensions(RegistryEvent.Register<ModDimension> event)
	{
		RegUtil.generic(event.getRegistry()).add("ultraamplified", ULTRAAMPLIFIED);
	}

}
