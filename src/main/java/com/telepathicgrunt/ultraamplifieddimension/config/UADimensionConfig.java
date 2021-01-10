package com.telepathicgrunt.ultraamplifieddimension.config;

import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper;
import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class UADimensionConfig
{
	public static class UADimensionConfigValues
	{
		public ConfigValueListener<Boolean> heavyFog;
		public ConfigValueListener<Integer> cloudHeight;
		public ConfigValueListener<Boolean> forceExitToOverworld;
		public ConfigValueListener<Boolean> allowNetherPortal;

		public UADimensionConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{

			builder.push("General Dimension Options");
			
				heavyFog = subscriber.subscribe(builder
		                .comment("\r\n Adds very heavy fog to make the world look more spoky and limit visibility.\n" 
		                		+" This is not the same as distance fog which does not make chunks near you foggy.")
		                .translation("ultraamplified.config.dimension.heavyfog")
		                .define("heavyFog", false));

				cloudHeight = subscriber.subscribe(builder
						.comment("\r\n Maxium height for clouds to be at."
								+"\r\n Default is 245.")
						.translation("ultraamplified.config.dimension.cloudheight")
						.defineInRange("cloudHeight", 245, 0, 2147483646));

				allowNetherPortal = subscriber.subscribe(builder
		                .comment("\r\n Lets Nether Portals be able to be created in Ultra Amplified DImension.\n" 
		                		+" Using the portal in this dimension will take you to the Nether but Nether \n"
		                		+" Portals in the Nether will take you to the Overworld instead. So this option \n"
		                		+" is good if you want a second way of escaping the Ultra Amplified Dimension.")
		                .translation("ultraamplified.config.dimension.allownetherportal")
		                .define("allowNetherPortal", false));
	
				forceExitToOverworld = subscriber.subscribe(builder
		                .comment("\r\n Makes leaving the Ultra Amplified dimension always places you back\r\n"
		                		+ " in the Overworld regardless of which dimension you originally \r\n"
		                		+ " came from. Use this option if this dimension becomes locked in  \r\n" 
		                		+ " with another dimension so you are stuck teleporting between the \r\n" 
		                		+ " two and cannot get back to the Overworld\r\n")
						.translation("ultraamplified.config.dimension.forceexittooverworld")
		                .define("forceExitToOverworld", false));

			builder.pop();
		}
	}
}
