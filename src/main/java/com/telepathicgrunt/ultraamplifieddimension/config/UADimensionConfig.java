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
		public ConfigValueListener<Boolean> bedExplodes;
		public ConfigValueListener<Boolean> heavyFog;
		public ConfigValueListener<Integer> cloudHeight;
		public ConfigValueListener<Boolean> forceExitToOverworld;
		public ConfigValueListener<Boolean> allowNetherPortal;
		public ConfigValueListener<String> portalActivationItem;
		public ConfigValueListener<String> portalCornerBlocks;
		public ConfigValueListener<String> portalCeilingBlocks;
		public ConfigValueListener<String> portalFloorBlocks;

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

				bedExplodes = subscriber.subscribe(builder
		                .comment("\r\n Should beds explode in the Ultra Amplified Dimension?")
		                .translation("ultraamplified.config.dimension.bedexplodes")
		                .define("bedExplodes", false));
				
				allowNetherPortal = subscriber.subscribe(builder
		                .comment("\r\n Lets Nether Portals be able to be created in Ultra Amplified DImension.\n" 
		                		+" Using the portal in this dimension will take you to the Nether but Nether \n"
		                		+" Portals in the Nether will take you to the Overworld instead. So this option \n"
		                		+" is good if you want a second way of escaping the Ultra Amplified Dimension.")
		                .translation("ultraamplified.config.dimension.allownetherportal")
		                .define("allowNetherPortal", false));
	
				forceExitToOverworld = subscriber.subscribe(builder
		                .comment("\r\n Should beds explode in the Ultra Amplified Dimension?" 
		                		+ " Makes leaving the Ultra Amplified dimension always places you back\r\n" 
		                		+ " in the Overworld regardless of which dimension you originally \r\n" 
		                		+ " came from. Use this option if this dimension becomes locked in  \r\n" 
		                		+ " with another dimension so you are stuck teleporting between the \r\n" 
		                		+ " two and cannot get back to the Overworld\r\n")
						.translation("ultraamplified.config.dimension.forceexittooverworld")
		                .define("forceExitToOverworld", false));
	
				portalActivationItem = subscriber.subscribe(builder
		                .comment("\r\n What item the player needs to hold and right click on the\r\n" 
		                		+" Polished Diorite to create the portal?\r\n" 
		                		+" The default entry is minecraft:flint_and_steel\r\n"
		                		+" Note: set this to \"\" to disable portal creation completely.")
		                .translation("ultraamplified.config.dimension.portalactivationitem")
		                .define("portalActivationItem", "minecraft:flint_and_steel"));
	
				portalCornerBlocks = subscriber.subscribe(builder
		                .comment("\r\n What corner blocks should we use to make a portal frame instead of Polish Granite?\r\n " 
		                		+ "The default entry is minecraft:polish_granite")
		                .translation("ultraamplified.config.dimension.portalcornerblocks")
		                .define("portalCornerBlocks", "minecraft:polished_granite"));
	
				portalCeilingBlocks = subscriber.subscribe(builder
		                .comment("\r\n What ceiling blocks should we use to make a portal frame instead of Polish Andesite Slab?\r\n " 
		                		+ "The default entry is minecraft:polish_andesite")
		                .translation("ultraamplified.config.dimension.portalceilingblocks")
		                .define("portalCeilingBlocks", "minecraft:polished_andesite_slab"));
	
				portalFloorBlocks = subscriber.subscribe(builder
		                .comment("\r\n What floor blocks should we use to make a portal frame instead of Polish Andesite Slab?\r\n " 
		                		+ "The default entry is minecraft:polish_andesite")
		                .translation("ultraamplified.config.dimension.portalfloorblocks")
		                .define("portalFloorBlocks", "minecraft:polished_andesite_slab"));
				
			builder.pop();
		}
	}
}
