package net.telepathicgrunt.ultraamplified.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.ultraamplified.utils.ConfigHelper;
import net.telepathicgrunt.ultraamplified.utils.ConfigHelper.ConfigValueListener;


@Mod.EventBusSubscriber
public class UAFeaturesConfig
{
	public static class UAFeaturesConfigValues
	{
		public ConfigValueListener<Integer> coalOreSpawnrate;
		public ConfigValueListener<Integer> ironOreSpawnrate;
		public ConfigValueListener<Integer> redstoneOreSpawnrate;
		public ConfigValueListener<Integer> lapisOreSpawnrate;
		public ConfigValueListener<Integer> diamondOreSpawnrate;
		public ConfigValueListener<Integer> goldOreSpawnrate;
		public ConfigValueListener<Integer> emeraldOreSpawnrate;
		public ConfigValueListener<Integer> silverfishSpawnrate;
		public ConfigValueListener<Integer> quartzOreSpawnrate;
		public ConfigValueListener<Integer> glowstoneSpawnrate;
		public ConfigValueListener<Integer> magmaSpawnrate;
		public ConfigValueListener<Integer> lavaSpawnrate;
		public ConfigValueListener<Integer> glowstoneVariantsSpawnrate;
		public ConfigValueListener<Boolean> rootGen;
		
		public ConfigValueListener<Integer> dungeonSpawnrate;
		public ConfigValueListener<Integer> ravineSpawnrate;
		public ConfigValueListener<Integer> caveCavitySpawnrate;
		public ConfigValueListener<Integer> oceanCaveSpawnrate;
		public ConfigValueListener<Boolean> columnGen;
		public ConfigValueListener<Integer> columnSpawnrate;
		public ConfigValueListener<Integer> rampSpawnrate;
		public ConfigValueListener<Boolean> rampGen;
		public ConfigValueListener<Integer> honeyLakeGen;
		public ConfigValueListener<Integer> slimeLakeGen;
		public ConfigValueListener<Integer> waterLakeGen;
		public ConfigValueListener<Integer> lavaLakeGen;
		public ConfigValueListener<Integer> waterfallSpawnrate;
		public ConfigValueListener<Integer> lavafallSpawnrate;
		public ConfigValueListener<Integer> endIslandSpawnrate;
		public ConfigValueListener<Integer> endIslandMaxHeight;


		public UAFeaturesConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{
			builder.push("Feature Options");
				builder.push("General Feature Options");
			
					dungeonSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Dungeons will spawn.\r\n " 
			                		+ "0 for no Dungeons and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.feature.dungeonspawnrate")
			                .defineInRange("dungeonSpawnrate", 30, 0, 1000));
		
					ravineSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Ravines will spawn.\r\n " 
			                		+ "0 for no Ravines and 100 for max spawnrate.")
			                .translation("ultraamplified.config.feature.ravinespawnrate")
			                .defineInRange("ravineSpawnrate", 25, 0, 100));
		
					caveCavitySpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Cave Cavity will spawn.\r\n " 
			                		+ "0 for no Cave Cavity and 22 for max spawnrate.")
			                .translation("ultraamplified.config.feature.cavecavityspawnrate")
			                .defineInRange("caveCavitySpawnrate", 5, 0, 22));
		
					oceanCaveSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Underwater Caves will spawn in ocean biomes.\r\n " 
			                		+ "0 for no Underwater Caves and 100 for max spawnrate.")
			                .translation("ultraamplified.config.feature.oceancavespawnrate")
			                .defineInRange("oceanCaveSpawnrate", 20, 0, 100));
		
					columnGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether columns/pillars features spawn or not."+
			                	 "\r\n Note: You need to close and reopen world for changes to take effect.")
			                .translation("ultraamplified.config.feature.columngen")
			                .define("columnGen", true));

					columnSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often will column/pillars spawn."+
			                	 "\r\n Default is 2 with 40 being an insane amount of columns."+
			                	 "\r\n Note: You need to close and reopen world for changes to take effect.")
			                .translation("ultraamplified.config.feature.columnspawnrate")
			                .defineInRange("columnSpawnrate", 2, 1, 40));
					
					rampGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether ramp features spawn or not."+
			                	 "\r\n Note: You need to close and reopen world for changes to take effect.")
			                .translation("ultraamplified.config.feature.rampgen")
			                .define("rampGen", true));

					rampSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often will ramps spawn."+
			                	 "\r\n Default is 2 with 40 being an insane amount of ramps."+
			                	 "\r\n Note: You need to close and reopen world for changes to take effect.")
			                .translation("ultraamplified.config.feature.rampspawnrate")
			                .defineInRange("rampSpawnrate", 2, 1, 40));
					
					honeyLakeGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether Honey Lakes spawn or not."
			                	+"\r\n 1 for maximum spawnrate and 1000 for no lakes")
			                .translation("ultraamplified.config.feature.honeylakegen")
			                .defineInRange("honeyLakeGen", 16, 1, 1000));
		
					slimeLakeGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether Slime Lakes spawn or not."
			                	+"\r\n 1 for maximum spawnrate and 1000 for no lakes")
			                .translation("ultraamplified.config.feature.slimelakegen")
			                .defineInRange("slimeLakeGen", 7, 1, 1000));
		
					waterLakeGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether Water Lakes spawn or not."
			                	+"\r\n 1 for maximum spawnrate and 1000 for no lakes")
			                .translation("ultraamplified.config.feature.waterlakegen")
			                .defineInRange("waterLakeGen", 4, 1, 1000));
		
					lavaLakeGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether Lava Lakes spawn or not."
			                	+"\r\n 1 for maximum spawnrate and 1000 for no lakes")
			                .translation("ultraamplified.config.feature.lavalakegen")
			                .defineInRange("lavaLakeGen", 10, 1, 1000));
		
					glowstoneVariantsSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n Controls how often patches of Glowdirt and other modded Glowstone variants spawn.\r\n " 
			                		+ "0 for no patches and 1000 for max amount of patches.")
			                .translation("ultraamplified.config.feature.glowstonevariantsspawnrate")
			                .defineInRange("glowstoneVariantsSpawnrate", 50, 0, 1000));
		
					rootGen = subscriber.subscribe(builder
			                .comment("\r\n Controls whether roots and short vines spawn or not on the underside of the floating land.")
			                .translation("ultraamplified.config.feature.rootgen")
			                .define("rootGen", true));
		
					waterfallSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often waterfalls will spawn." 
			                		+ "\n " 
			                		+ "0 for no waterfalls and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.terrain.waterfallspawnrate")
			                .defineInRange("waterfallSpawnrate", 35, 0, 1000));
		
					lavafallSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often lavafalls will spawn." 
			                		+ "\n " 
			                		+ "0 for no lavafalls and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.terrain.lavafallspawnrate")
			                .defineInRange("lavafallSpawnrate", 14, 0, 1000));
	
				builder.pop();

				
				builder.push("End Fields Islands Options");

        				endIslandSpawnrate = subscriber.subscribe(builder
        		                .comment("\r\n How often End Islands will spawn in the End Biome." 
        		                		+ "\n " 
        		                		+ "0 for no End Islands and 100 for max spawnrate.")
        		                .translation("ultraamplified.config.terrain.endislandspawnrate")
        		                .defineInRange("endIslandSpawnrate", 6, 0, 100));
        
        				endIslandMaxHeight = subscriber.subscribe(builder
        		                .comment("\r\n The maximum height that End Islands can spawn at in the End Field Biome.")
        		                .translation("ultraamplified.config.terrain.endislandmaxheight")
        		                .defineInRange("endIslandMaxHeight", 254, 0, 256));
        				
				builder.pop();

	
				builder.push("Main Ores Options");

					coalOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Coal Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Coal Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.coalorespawnrate")
			                .defineInRange("coalOreSpawnrate", 35, 0, 1000));
		
					ironOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Iron Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Iron Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.ironorespawnrate")
			                .defineInRange("ironOreSpawnrate", 50, 0, 1000));
		
					redstoneOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Redstone Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Redstone Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.redstoneorespawnrate")
			                .defineInRange("redstoneOreSpawnrate", 12, 0, 1000));
		
					lapisOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Lapis Lazuli Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Lapis Lazuli Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.lapisorespawnrate")
			                .defineInRange("lapisOreSpawnrate", 2, 0, 1000));
		
					diamondOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Diamond Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Diamond Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.diamondorespawnrate")
			                .defineInRange("diamondOreSpawnrate", 1, 0, 1000));
		
					goldOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Gold Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Gold Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.goldorespawnrate")
			                .defineInRange("goldOreSpawnrate", 2, 0, 1000));

				builder.push("Rocky Fields Ores and Features Options");
		
					emeraldOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Emerald Ores will spawn in all Rocky Fields variant biomes in the form of a percentage." 
			                		+ "\n " 
			                		+ "0 for no Emerald Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.emeraldorespawnrate")
			                .defineInRange("emeraldOreSpawnrate", 100, 0, 1000));
		
					silverfishSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Silverfish Blocks will spawn in all Rocky Fields variant biomes." 
			                		+ "\n " 
			                		+ "0 for no Silverfish Blocks and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.silverfishspawnrate")
			                .defineInRange("silverfishSpawnrate", 18, 0, 1000));
		
				builder.pop();

				builder.push("Netherland Ores and Features Options");
	
					quartzOreSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Quartz Ores will spawn." 
			                		+ "\n " 
			                		+ "0 for no Quartz Ores and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.quartzorespawnrate")
			                .defineInRange("quartzOreSpawnrate", 14, 0, 1000));
		
					glowstoneSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Glowstone will spawn in Netherland biome." 
			                		+ "\n " 
			                		+ "0 for no Glowstone and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.glowstonespawnrate")
			                .defineInRange("glowstoneSpawnrate", 20, 0, 1000));
		
					magmaSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often Magma Blocks will spawn below Y = 100 in Netherland biome." 
			                		+ "\n " 
			                		+ "0 for no Magma Blocks and 1000 for max spawnrate." 
			                		+ "\n " 
			                		+ "Note: Will not affect the flat Magma layer separating water from lava below sealevel.")
			                .translation("ultraamplified.config.ore.magmaspawnrate")
			                .defineInRange("magmaSpawnrate", 5, 0, 1000));
		
					lavaSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How often single Lava Blocks will spawn in Netherland biome." 
			                		+ "\n " 
			                		+ "0 for no single Lava Blocks and 1000 for max spawnrate.")
			                .translation("ultraamplified.config.ore.lavaspawnrate")
			                .defineInRange("lavaSpawnrate", 70, 0, 1000));
		
				builder.pop();
			builder.pop();
		}
	}
}
