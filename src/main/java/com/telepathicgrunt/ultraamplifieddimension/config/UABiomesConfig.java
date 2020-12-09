package com.telepathicgrunt.ultraamplifieddimension.config;

import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import com.telepathicgrunt.ultraamplifieddimension.utils.ConfigHelper.ConfigValueListener;


@Mod.EventBusSubscriber
public class UABiomesConfig
{
	public static class UABiomesConfigValues
	{
		public ConfigValueListener<Integer> biomeSize;
		public ConfigValueListener<Integer> mutatedBiomeSpawnrate;
		public ConfigValueListener<Boolean> plains;
		public ConfigValueListener<Boolean> desert;
		public ConfigValueListener<Boolean> forest;
		public ConfigValueListener<Boolean> taiga;
		public ConfigValueListener<Boolean> rockyField;
		public ConfigValueListener<Boolean> swamplands;
		public ConfigValueListener<Boolean> netherland;
		public ConfigValueListener<Boolean> endField;
		public ConfigValueListener<Boolean> snowyTundra;
		public ConfigValueListener<Boolean> icedTerrain;
		public ConfigValueListener<Boolean> mushroom;
		public ConfigValueListener<Boolean> stonePlains;
		public ConfigValueListener<Boolean> bambooJungle;
		public ConfigValueListener<Boolean> jungle;
		public ConfigValueListener<Boolean> frozenDesert;
		public ConfigValueListener<Boolean> birchForest;
		public ConfigValueListener<Boolean> darkForest;
		public ConfigValueListener<Boolean> snowyTaiga;
		public ConfigValueListener<Boolean> giantTreeTaiga;
		public ConfigValueListener<Boolean> savanna;
		public ConfigValueListener<Boolean> badlands;
		public ConfigValueListener<Boolean> spikyBadlands;
		public ConfigValueListener<Boolean> iceSpike;
		public ConfigValueListener<Boolean> frozenOcean;
		public ConfigValueListener<Boolean> coldOcean;
		public ConfigValueListener<Boolean> ocean;
		public ConfigValueListener<Boolean> lukewarmOcean;
		public ConfigValueListener<Boolean> warmOcean;


		public UABiomesConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{
			builder.push("Biome Options");
	
				biomeSize = subscriber.subscribe(builder
		                .comment("\r\n How large the biomes are." 
		                		+ "\n " 
		                		+ "Bigger number means bigger biomes.")
		                .translation("ultraamplified.config.biome.biomesize")
		                .defineInRange("biomeSize", 3, 1, 8));
	
				mutatedBiomeSpawnrate = subscriber.subscribe(builder
		                .comment("\r\n How often the mutated form of a biome will generate" 
		                		+ "\n " 
		                		+ "0 for no mutated biomes and 10 for all biomes to be mutated.")
		                .translation("ultraamplified.config.biome.mutatedbiomespawnrate")
		                .defineInRange("mutatedBiomeSpawnrate", 2, 0, 10));
	
				builder.push("Biome Toggle Options");
	
        				plains = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.plains")
        		                .define("plains", true));
        	
        				desert = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.desert")
        		                .define("desert", true));
        	
        				forest = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.forest")
        		                .define("forest", true));
        	
        				taiga = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.taiga")
        		                .define("taiga", true));
        	
        				rockyField = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.rockyfield")
        		                .define("rockyField", true));
        	
        				swamplands = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.swamplands")
        		                .define("swamplands", true));
        	
        				netherland = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.netherland")
        		                .define("netherland", true));
        	
        				endField = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.end")
        		                .define("end", true));
        	
        				snowyTundra = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.snowytundra")
        		                .define("snowyTundra", true));
        	
        				icedTerrain = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.icedterrain")
        		                .define("icedTerrain", true));
        	
        				mushroom = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.mushroom")
        		                .define("mushroom", true));
        	
        				stonePlains = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.stoneplains")
        		                .define("stonePlains", true));
        	
        				bambooJungle = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.bamboojungle")
        		                .define("bambooJungle", true));
        	
        				jungle = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.jungle")
        		                .define("jungle", true));
        	
        				frozenDesert = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.frozendesert")
        		                .define("frozenDesert", true));
        	
        				birchForest = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.birchforest")
        		                .define("birchForest", true));
        	
        				darkForest = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.darkforest")
        		                .define("darkForest", true));
        	
        				snowyTaiga = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.snowytaiga")
        		                .define("snowyTaiga", true));
        	
        				giantTreeTaiga = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.gianttreetaiga")
        		                .define("giantTreeTaiga", true));
        	
        				savanna = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.savanna")
        		                .define("savanna", true));
        	
        				badlands = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.badlands")
        		                .define("badlands", true));
        	
        				spikyBadlands = subscriber.subscribe(builder
        		                .comment("\r\n Should Spiky Badlands and Dissected Plateau Badlands be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.spikybadlands")
        		                .define("spikyBadlands", true));
        	
        				iceSpike = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.icespike")
        		                .define("iceSpike", true));
        	
        				frozenOcean = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.frozenocean")
        		                .define("frozenOcean", true));
        	
        				coldOcean = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.coldocean")
        		                .define("coldOcean", true));
        	
        				ocean = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.ocean")
        		                .define("ocean", true));
        	
        				lukewarmOcean = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.lukewarmocean")
        		                .define("lukewarmOcean", true));
        	
        				warmOcean = subscriber.subscribe(builder
        		                .comment("\r\n Should this biome be allowed to spawn?")
        		                .translation("ultraamplified.config.biome.warmocean")
        		                .define("warmOcean", true));
    				builder.pop();
			builder.pop();
		}
	}
}
