package com.telepathicgrunt.ultraamplifieddimension.config;

import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper;
import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;

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
		                .comment("\r\n Adds very heavy fog to make the world look more spooky and limit visibility.\n"
		                		+" This is not the same as distance fog which only applies weakly to chunks in the far distance.")
		                .translation("ultraamplified.config.dimension.heavyfog")
		                .define("heavyFog", false));

				cloudHeight = subscriber.subscribe(builder
						.comment("\r\n Maximum height for clouds to be at."
								+"\r\n Default is 245.")
						.translation("ultraamplified.config.dimension.cloudheight")
						.defineInRange("cloudHeight", 245, -50, 400));

				allowNetherPortal = subscriber.subscribe(builder
		                .comment("\r\n Lets Nether Portals be able to be created in Ultra Amplified Dimension.\n"
		                		+" Using the portal in this dimension will take you to the Nether but Nether \n"
		                		+" Portals in the Nether will take you to the Overworld instead. So this option \n"
		                		+" is good if you want a second way of escaping the Ultra Amplified Dimension.")
		                .translation("ultraamplified.config.dimension.allownetherportal")
		                .define("allowNetherPortal", false));
	
				forceExitToOverworld = subscriber.subscribe(builder
		                .comment("\r\n Makes leaving the Ultra Amplified dimension by Amplified Portal Block \r\n"
		                		+ " always places you back in the Overworld regardless of which dimension you \r\n"
		                		+ " originally came from. Use this option if this dimension becomes locked in \r\n"
		                		+ " with another dimension so you are stuck teleporting between the two and cannot \r\n"
		                		+ " get back to the Overworld\r\n")
						.translation("ultraamplified.config.dimension.forceexittooverworld")
		                .define("forceExitToOverworld", false));

			builder.pop();
		}
	}
}
