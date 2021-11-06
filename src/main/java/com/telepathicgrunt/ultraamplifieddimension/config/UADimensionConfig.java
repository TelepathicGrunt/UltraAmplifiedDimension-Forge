package com.telepathicgrunt.ultraamplifieddimension.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class UADimensionConfig {
	public static final ForgeConfigSpec GENERAL_SPEC;

	public static ForgeConfigSpec.BooleanValue heavyFog;
	public static ForgeConfigSpec.IntValue cloudHeight;
	public static ForgeConfigSpec.BooleanValue netherLighting;
	public static ForgeConfigSpec.ConfigValue<String> skyType;
	public static ForgeConfigSpec.BooleanValue forceExitToOverworld;
	public static ForgeConfigSpec.BooleanValue allowNetherPortal;

	static {
		ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
		setupConfig(configBuilder);
		GENERAL_SPEC = configBuilder.build();
	}

	private static void setupConfig(ForgeConfigSpec.Builder builder) {

		builder.push("General Dimension Options");

			heavyFog = builder
					.comment("\n Adds very heavy fog to make the world look more spooky and limit visibility.",
							" This is not the same as distance fog which only applies weakly to chunks in the far distance.")
					.translation("ultraamplified.config.dimension.heavyfog")
					.define("heavyFog", false);

			cloudHeight = builder
					.comment("\n Maximum height for clouds to be at.",
							" Default is 245.")
					.translation("ultraamplified.config.dimension.cloudheight")
					.defineInRange("cloudHeight", 245, -50, 400);


			netherLighting = builder
					.comment("\n Darkens the dimension to be Nether's lighting which is darker skylight but has ambient lighting.")
					.translation("ultraamplified.config.dimension.netherlighting")
					.define("netherLighting", false);


			skyType = builder
					.comment("\n What sky type the dimension should use. Only values allowed are NORMAL, END, and NONE.")
					.translation("ultraamplified.config.dimension.skytype")
					.define("skyType", "NORMAL");

			allowNetherPortal = builder
					.comment("\n Lets Nether Portals be able to be created in Ultra Amplified Dimension.",
							" Using the portal in this dimension will take you to the Nether but Nether",
							" Portals in the Nether will take you to the Overworld instead. So this option",
							" is good if you want a second way of escaping the Ultra Amplified Dimension.")
					.translation("ultraamplified.config.dimension.allownetherportal")
					.define("allowNetherPortal", false);

			forceExitToOverworld = builder
					.comment("\n Makes leaving the Ultra Amplified dimension by Amplified Portal Block",
							" always places you back in the Overworld regardless of which dimension you",
							" originally came from. Use this option if this dimension becomes locked in",
							" with another dimension so you are stuck teleporting between the two and cannot",
							" get back to the Overworld.")
					.translation("ultraamplified.config.dimension.forceexittooverworld")
					.define("forceExitToOverworld", false);

		builder.pop();
	}
}
