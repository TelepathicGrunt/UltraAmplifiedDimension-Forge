package net.telepathicgrunt.ultraamplified.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.utils.ConfigHelper;
import net.telepathicgrunt.ultraamplified.utils.ConfigHelper.ConfigValueListener;


@Mod.EventBusSubscriber
public class UATerrainConfig
{
	public static class UATerrainConfigValues
	{
		public ConfigValueListener<Double> xzTerrainModifier;
		public ConfigValueListener<Double> xzScaleModifier;
		public ConfigValueListener<Double> yTerrainModifier;
		public ConfigValueListener<Double> yScaleModifier;
		public ConfigValueListener<Integer> yMaximum;
		public ConfigValueListener<Boolean> secretSetting;
		public ConfigValueListener<Integer> seaLevel;
		public ConfigValueListener<Boolean> lavaOcean;
		
		public UATerrainConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{

			builder.push("Terrain Options");
	
				secretSetting = subscriber.subscribe(builder
		                .comment("\r\n Does something neat! Give it a try lol\n " 
		                		+ "You might want to use this on a fresh new world...\n " 
		                		+ "Changes the terrain's look!")
		                .translation("ultraamplified.config.terrain.secretsettings")
		                .define("secretSettings", false));
	
				yMaximum = subscriber.subscribe(builder
		                .comment("\r\n Maxium height the terrain can generate up to."
		                	+"\r\n Default is 245.")
		                .translation("ultraamplified.config.terrain.ymaximum")
		                .defineInRange("yMaximum", 245, 100, 2147483646));
	
				xzTerrainModifier = subscriber.subscribe(builder
		                .comment("\r\n Changes the xz terrain modifier.\n " 
		                		+ "I believe lower numbers will make the layers longer in the xz plane.\n " 
		                		+ "Default value is 684.412D")
		                .translation("ultraamplified.config.terrain.xzterrainmodifier")
		                .defineInRange("xzTerrainModifier", 684.412D, 1D, 10000000D));
	
				xzScaleModifier = subscriber.subscribe(builder
		                .comment("\r\n Changes the xz terrain scale.\n " 
		                		+ "Not exactly sure what this does.\n " 
		                		+ "Default value is 8.55515F")
		                .translation("ultraamplified.config.terrain.xzscalemodifier")
		                .defineInRange("xzScaleModifier", 8.55515F, 1D, 10000000D));
	
				yTerrainModifier = subscriber.subscribe(builder
		                .comment("\r\n Changes the y terrain modifier.\n " 
		                		+ "I believe lower numbers will make less layers and thicken layers that do spawn.\n " 
		                		+ "Default value is 68419.786D")
		                .translation("ultraamplified.config.terrain.yterrainmodifier")
		                .defineInRange("yTerrainModifier", 68419.786D, 1D, 10000000D));
	
				yScaleModifier = subscriber.subscribe(builder
		                .comment("\r\n Changes the y terrain scale.\n " 
		                		+ "Not exactly sure what this does.\n " 
		                		+ "Default value is 428.613D")
		                .translation("ultraamplified.config.terrain.yscalemodifier")
		                .defineInRange("yScaleModifier", 428.613D, 1D, 10000000D));
	
				seaLevel = subscriber.subscribe(builder
		                .comment("\r\n Sea Level. Default is 75.")
		                .translation("ultraamplified.config.terrain.sealevel")
		                .defineInRange("seaLevel", 75, 0, 250));
	
				lavaOcean = subscriber.subscribe(builder
		                .comment("\r\n Replace the water at sea level with lava instead. DO NOT CHANGE THIS OPTION IN AN ALREADY GENERATED DIMENSION. IT WILL CRASH DUE TO LAVA OCEAN UPDATING NEXT TO OCEAN WATER!!")
		                .translation("ultraamplified.config.terrain.lavaocean")
		                .define("lavaOcean", false));
	
			builder.pop();

		}
	}
}
