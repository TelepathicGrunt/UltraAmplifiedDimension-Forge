package com.telepathicgrunt.ultraamplifieddimension.config;

import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper;
import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class UAStructuresConfig
{
	public static class UAStructuresConfigValues
	{
		public ConfigValueListener<Boolean> chestGeneration;
		public ConfigValueListener<Integer> sunShrineSpawnrate;
		public ConfigValueListener<Integer> stonehengeSpawnrate;
		public ConfigValueListener<Integer> hangingRuinsSpawnrate;
		public ConfigValueListener<Boolean> miniStructureGeneration;
		
		public ConfigValueListener<Integer> villageSpawnrate;
		public ConfigValueListener<Integer> villageZombieSpawnrate;
		public ConfigValueListener<Integer> mineshaftSpawnrate;
		public ConfigValueListener<Boolean> mineshaftAbovegroundAllowed;
		public ConfigValueListener<Boolean> mineshaftUndergroundAllowed;
		public ConfigValueListener<Integer> mansionSpawnrate;
		public ConfigValueListener<Integer> desertTempleSpawnrate;
		public ConfigValueListener<Integer> jungleTempleSpawnrate;
		public ConfigValueListener<Integer> iglooSpawnrate;
		public ConfigValueListener<Integer> mushroomTempleSpawnrate;
		public ConfigValueListener<Integer> iceSpikeTempleSpawnrate;
		public ConfigValueListener<Integer> witchHutSpawnrate;
		public ConfigValueListener<Integer> oceanMonumentSpawnrate;
		public ConfigValueListener<Integer> oceanRuinsSpawnrate;
		public ConfigValueListener<Integer> shipwreckSpawnrate;
		public ConfigValueListener<Integer> strongholdSpawnrate;
		public ConfigValueListener<Double> silverfishStrongholdSpawnrate;
		public ConfigValueListener<Boolean> allowSilverfishSpawnerStronghold;
		public ConfigValueListener<Double> strongholdSizeSH;
		public ConfigValueListener<Integer> netherFortressSpawnrate;
		public ConfigValueListener<Integer> stoneFortressSpawnrate;
		public ConfigValueListener<Boolean> allowSilverfishSpawnerFortress;
		public ConfigValueListener<Boolean> allowNaturalSilverfishFortress;
		public ConfigValueListener<Integer> endCitySpawnrate;
		public ConfigValueListener<Integer> pillageOutpostRarity;


		public UAStructuresConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{
			builder.push("Structure/Feature Options");
	
				builder.push("Mini-structure Options");
		
					miniStructureGeneration = subscriber.subscribe(builder
			                .comment("\r\n Controls whether Desert Wells, Hay Piles, Sun Shrines, Stonehenges, and Crosses spawn or not.")
			                .translation("ultraamplified.config.structure.ministructuregeneration")
			                .define("miniStructureGeneration", true));
		
					sunShrineSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How rare are Sun Shrines.\r\n " 
			                		+ "1 for Sun Shrines spawning in most chunks and 1000 for very rare spawn.\r\n " 
			                		+ "Spawns mainly in relic variant of biomes.")
			                .translation("ultraamplified.config.structure.sunshrinespawnrate")
			                .defineInRange("sunShrineSpawnrate", 130, 1, 1000));
		
					stonehengeSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How rare are Stonehenges.\r\n " 
			                		+ "1 for Stonehenges spawning in most chunks and 1000 for very rare spawn.\r\n " 
			                		+ "Spawns mainly in relic variant of biomes.")
			                .translation("ultraamplified.config.structure.stonehengespawnrate")
			                .defineInRange("stonehengeSpawnrate", 15, 1, 1000));
		
					hangingRuinsSpawnrate = subscriber.subscribe(builder
			                .comment("\r\n How rare are Hanging Ruins.\r\n " 
			                		+ "1000 for Hanging Ruins spawning in most chunks and 0 for no spawn.\r\n " 
			                		+ "NOTE: this is backwards than other spawnrates.\r\n " 
			                		+ "This is because other structures need have finer control on chances less than 1%.\r\n " 
			                		+ "Spawns in most biomes except for oceans, Netherland, and Iced Terrain Biomes and more often in Rocky Fields variants and Stone Fields biomes.")
							.translation("ultraamplified.config.structure.hangingruinsspawnrate")
			                .defineInRange("hangingRuinsSpawnrate", 60, 0, 1000));
	
				builder.pop();
	
				builder.push("Structure Options");
		
					chestGeneration = subscriber.subscribe(builder
			                .comment("\r\n Controls whether loot chests spawn or not in all structures.")
			                .translation("ultraamplified.config.structure.chestgeneration")
			                .define("chestGeneration", true));
		
					builder.push("Villages");
			
						villageSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Villages.\r\n " 
				                		+ "1 for Village spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.villagespawnrate")
				                .defineInRange("villageSpawnrate", 16, 1, 101));
			
						villageZombieSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n What percentage of Classic Styled Villages are Zombie Villages.\r\n " 
				                		+ "0 for no Zombie Village spawning and 100 for all Villages being zombified.")
				                .translation("ultraamplified.config.structure.villagezombiespawnrate")
				                .defineInRange("villageZombieSpawnrate", 10, 0, 100));
		
					builder.pop();
		
					builder.push("Mineshaft");
			
						mineshaftSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How often Mineshafts will spawn.\r\n " 
				                		+ "0 for no Mineshafts and 1000 for max spawnrate.")
				                .translation("ultraamplified.config.structure.mineshaftspawnrate")
				                .defineInRange("mineshaftSpawnrate", 22, 0, 1000));
			
						mineshaftAbovegroundAllowed = subscriber.subscribe(builder
				                .comment("\r\n Can aboveground floating Mineshafts spawn?")
				                .translation("ultraamplified.config.structure.mineshaftabovegroundallowed")
				                .define("mineshaftAbovegroundAllowed", true));
			
						mineshaftUndergroundAllowed = subscriber.subscribe(builder
				                .comment("\r\n Can undergound giant pit Mineshafts spawn?")
				                .translation("ultraamplified.config.structure.mineshaftundergroundallowed")
				                .define("mineshaftUndergroundAllowed", true));
		
					builder.pop();
		
					builder.push("Woodland Mansion");
			
						mansionSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Woodland Mansion." 
				                		+ "\n " 
				                		+ "1 for Woodland Mansion spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.mansionspawnrate")
				                .defineInRange("mansionSpawnrate", 18, 1, 101));
		
					builder.pop();
		
					builder.push("Desert Temple");
			
						desertTempleSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Desert Temples." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.deserttemplespawnrate")
				                .defineInRange("desertTempleSpawnrate", 20, 1, 101));
		
					builder.pop();
		
					builder.push("Jungle Temple");
			
						jungleTempleSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Jungle Temples." 
				                		+ "\n " 
				                		+ "1 for a spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.jungletemplespawnrate")
				                .defineInRange("jungleTempleSpawnrate", 20, 1, 101));
		
					builder.pop();
		
					builder.push("Igloo");
			
						iglooSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are igloos." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.igloospawnrate")
				                .defineInRange("iglooSpawnrate", 14, 1, 101));
		
					builder.pop();
		
					builder.push("Mushroom Temple");
			
						mushroomTempleSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Mushroom Temples." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.mushroomtemplespawnrate")
				                .defineInRange("mushroomTempleSpawnrate", 12, 1, 101));
		
					builder.pop();
		
					builder.push("Ice Spike Temple");
				
						iceSpikeTempleSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Ice Spike Temples." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.icespiketemplespawnrate")
				                .defineInRange("iceSpikeTempleSpawnrate", 20, 1, 101));
		
					builder.pop();
		
					builder.push("Witch Hut");
			
						witchHutSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Witch Huts." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.witchhutspawnrate")
				                .defineInRange("witchHutSpawnrate", 14, 1, 101));
		
					builder.pop();
		
					builder.push("Ocean Monument");
			
						oceanMonumentSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Ocean Monuments." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.oceanmonumentspawnrate")
				                .defineInRange("oceanMonumentSpawnrate", 22, 1, 101));
		
					builder.pop();
		
					builder.push("Ocean Ruins");
			
						oceanRuinsSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Ocean Ruins." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.oceanruinsspawnrate")
				                .defineInRange("oceanRuinsSpawnrate", 7, 1, 101));
		
					builder.pop();
		
					builder.push("Shipwreck");
				
						shipwreckSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Shipwrecks." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.shipwreckspawnrate")
				                .defineInRange("shipwreckSpawnrate", 7, 1, 101));
		
					builder.pop();
		
					builder.push("Stronghold");
			
						strongholdSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Strongholds." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 501 for no spawn.")
				                .translation("ultraamplified.config.structure.strongholdspawnrate")
				                .defineInRange("strongholdSpawnrate", 62, 1, 501));
			
						silverfishStrongholdSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How often Silverfish Blocks will generate in Strongholds." 
				                		+ "\n " 
				                		+ "0 for no Silverfish Blocks and 100 for max spawnrate.")
				                .translation("ultraamplified.config.structure.silverfishstrongholdspawnrate")
				                .defineInRange("silverfishStrongholdSpawnrate", 4D, 0, 100));
			
						allowSilverfishSpawnerStronghold = subscriber.subscribe(builder
				                .comment("\r\n Can Silverfish Mob Spawners generate in Stronghold?.")
				                .translation("ultraamplified.config.structure.allowsilverfishspawnerstronghold")
				                .define("allowSilverfishSpawnerStronghold", true));
			
						strongholdSizeSH = subscriber.subscribe(builder
								.comment("\r\n How large the Stronghold is on average as a percentage." 
										+ "\r\n Note: The Stonghold is much larger by default. To get something "
										+ "\r\n closer to vanilla stronghold size, use the value of 60."
										+ "\n "
										+ "10 for supertiny Strongholds and 2000 for supermassive Strongholds.")
								.translation("repurposedstructures.config.structure.stronghold.strongholdsizesh")
								.defineInRange("strongholdSizeSH", 100D, 10, 2000));
					
					builder.pop();
		
					builder.push("Nether Fortress");
			
						netherFortressSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Nether Fortresses that spawn "
				                	+"\r\n aboveground in the Netherland biome (between y = 85 and 130)." 
				                	+ "\n " 
				                	+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.netherfortressspawnrate")
				                .defineInRange("netherFortressSpawnrate", 6, 1, 101));
			
					builder.pop();
		
					builder.push("Stone Fortress");
			
						stoneFortressSpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are Stone Fortresses that spawn underground (below y = 65)." 
				                	+ "\n " 
				                	+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.stonefortressspawnrate")
				                .defineInRange("stoneFortressSpawnrate", 14, 1, 101));
			
						allowSilverfishSpawnerFortress = subscriber.subscribe(builder
				                .comment("\r\n Can Silverfish Mob Spawners generate in Stone Fortresses?")
				                .translation("ultraamplified.config.structure.allowsilverfishspawnerfortress")
				                .define("allowSilverfishSpawnerFortress", true));
			
						allowNaturalSilverfishFortress = subscriber.subscribe(builder
				                .comment("\r\n Can Silverfish spawn naturally over time in Stone Fortresses?")
				                .translation("ultraamplified.config.structure.allownaturalsilverfishfortress")
				                .define("allowNaturalSilverfishFortress", true));
				
					builder.pop();
		
					builder.push("End City");
				
						endCitySpawnrate = subscriber.subscribe(builder
				                .comment("\r\n How rare are End Cities." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.endcityspawnrate")
				                .defineInRange("endCitySpawnrate", 22, 1, 101));
				
					builder.pop();
		
					builder.push("Outpost");
			
						pillageOutpostRarity = subscriber.subscribe(builder
				                .comment("\r\n How rare are Pillager Outposts." 
				                		+ "\n " 
				                		+ "1 for spawning in most chunks and 101 for no spawn.")
				                .translation("ultraamplified.config.structure.pillageoutpostrarity")
				                .defineInRange("pillageOutpostRarity", 20, 1, 101));
					
					builder.pop();
				builder.pop();
			builder.pop();
		}
	}
}
