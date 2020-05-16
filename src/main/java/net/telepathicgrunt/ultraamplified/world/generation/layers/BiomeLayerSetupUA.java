package net.telepathicgrunt.ultraamplified.world.generation.layers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.primitives.Ints;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.biome.UABiomes;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeLayerSetupUA
{

	public static List<BiomeEntry> icyBiomesList;
	public static List<BiomeEntry> coolBiomesList;
	public static List<BiomeEntry> warmBiomesList;
	public static List<BiomeEntry> hotBiomesList;
	public static List<BiomeEntry> jungleBiomesList;
	public static List<BiomeEntry> giantTreeTaigaBiomesList;
	public static List<BiomeEntry> badlandsBiomesList;
	public static List<BiomeEntry> oceanBiomesList;
	public static boolean noOcean = false;
	private static ForgeRegistry<Biome> BiomeRegistry = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES);


	public BiomeLayerSetupUA()
	{
	    	// Update what biomes are used when re-entering a world without closing Minecraft.
		setupBiomeEntries();
		
		//setup biome edge map again form scratch as some edges are config based
		BiomeGenHelper.setBiomeEdgeMap();
		
		//make ocean layer grab all used oceans and their IDs for biome layout gen
		AddOceansLayerUA.syncOceanList();
		
		//Setup what m variants are mapped and not
		UABiomes.mapMBiomes();
	}

	
	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{
		/**
		 * Updates the biome layout based on the config changes. (fires before the world has a chance to load too)
		 */
		@SubscribeEvent
		public static void updateBiomeLayouts(final ModConfig.Reloading event)
		{
		    	// Update what biomes are used when re-entering a world without closing Minecraft.
			setupBiomeEntries(); 
			
			//setup biome edge map again form scratch as some edges are config based
			BiomeGenHelper.setBiomeEdgeMap();
			
			//make ocean layer grab all used oceans and their IDs for biome layout gen
			AddOceansLayerUA.syncOceanList();
			
			//Setup what m variants are mapped and not
			UABiomes.mapMBiomes();
		}
	}
	
	/**
	 * Takes user's config to determine what modded biome to import,
	 * what UA biome to use, fill in biome lists that doesn't have
	 * many biomes, and lets BiomeLayerPickerUA know if there's oceans
	 * or not.
	 * 
	 * Call this method before the world has a chance to even load or 
	 * generate a single biome. Best time for this is in the
	 * ModConfig.Reloading event.
	 */
	private static void setupBiomeEntries()
	{
	    resetBiomeLists();
	    importModdedBiomes();
	    addUABiomeEntriesToLists();
	    fillSparceBiomeLists();
	    isUAOceanPresent();
	}

	
	/**
	 * Empties and initializes all the temperature based biome entry lists
	 */
	private static void resetBiomeLists() 
	{
		icyBiomesList = new ArrayList<BiomeEntry>();
		coolBiomesList = new ArrayList<BiomeEntry>();
		warmBiomesList = new ArrayList<BiomeEntry>();
		hotBiomesList = new ArrayList<BiomeEntry>();
		jungleBiomesList = new ArrayList<BiomeEntry>();
		giantTreeTaigaBiomesList = new ArrayList<BiomeEntry>();
		badlandsBiomesList = new ArrayList<BiomeEntry>();
		oceanBiomesList = new ArrayList<BiomeEntry>();
		noOcean = false;
	}

	
	/**
	 * Will fill up all the land-based temperature range biome entry lists with modded biomes.
	 * The modded biomes will be from either the registry or BiomeManager (overworld biomes)
	 * based on what the user's config specifies.
	 */
	private static void importModdedBiomes() 
	{
		//grabs what the user entered.
		//is done as linked list so we can remove entries later if needed since Arrays.asList creates a fixed size list.
		List<String> blacklistedMods = new ArrayList<String>();
		List<String> blacklistedTempList = new LinkedList<String>(Arrays.asList(UltraAmplified.UAConfig.blacklistedBiomeList.get().split(",")));
		for (int i = 0; i < blacklistedTempList.size(); i++)
		{
			blacklistedTempList.set(i, blacklistedTempList.get(i).trim());
		}

		//finds all strings that doesn't have a path and adds it to blacklistedMods as it's a mod's namespace
		for (String entry : blacklistedTempList)
		{
			if (entry.contains(":*"))
			{
				blacklistedMods.add(entry.split(":")[0]);
			}
		}

		//will go through all biomes and add modded biomes that arent UA biomes to biome list
		//will attempt to place the in right spot based on temperature
		if (UltraAmplified.UAConfig.importAllModdedBiomes.get())
		{
			for (Biome biome : ForgeRegistries.BIOMES.getValues())
			{

				ResourceLocation rl = BiomeRegistry.getKey(biome);
				if (rl == null) continue; //pre-caution. Shouldn't ever happen tho.

				String namespace = rl.getNamespace();
				if (blacklistedMods.contains(namespace) || UltraAmplified.UAConfig.blacklistedBiomeList.get().contains(rl.toString()))
					continue; //this modded biome is blacklisted 
				

				if (!namespace.equals("minecraft") && !namespace.equals(UltraAmplified.MODID))
				{

					//default weight of 20 which seems good enough 
					BiomeEntry addedBiome = new BiomeEntry(biome, 20);
					float biomeTemperature = biome.getDefaultTemperature();

					//finds what area the biome should go based on temperature
					//which means even modded biomes without any category
					//will get placed in the correct temp range.
					if (biomeTemperature < 0.1f)
					    icyBiomesList.add(addedBiome);
					
					else if (biomeTemperature >= 0.1f && biomeTemperature < 0.5f)
					    coolBiomesList.add(addedBiome);
					
					else if (biomeTemperature >= 0.5f && biomeTemperature < 1.0f)
					    warmBiomesList.add(addedBiome);
					
					else
					    hotBiomesList.add(addedBiome); //greater than 1.0f 

				}
			}
		}
		//only imports the modded biomes already registered in the biome dictionary (Other mods wanted their biome to generate in Overworld)
		else if (UltraAmplified.UAConfig.importOverworldModdedBiomes.get())
		{
			for (BiomeType tempRange : BiomeType.values())
			{
			    	//Removes vanilla entries.
				//Make sure no biome's weight is less than 10 or else they get overwhelmed by all other biomes.
			    	List<BiomeEntry> biomesInTempRange = BiomeManager.getBiomes(tempRange).stream()
					.filter(biomeEntry -> !biomeEntry.biome.getRegistryName().getNamespace().equals("minecraft"))
					.map(biomeEntry -> biomeEntry.itemWeight < 10 ? new BiomeEntry(biomeEntry.biome, 10) : biomeEntry)
					.collect(Collectors.toList());
				
				
				if (biomesInTempRange != null) {
				    switch(tempRange) {
					case ICY :
					    icyBiomesList.addAll(biomesInTempRange);
					    break;
					case COOL :
					    coolBiomesList.addAll(biomesInTempRange);
					    break;
					case WARM :
					    warmBiomesList.addAll(biomesInTempRange);
					    break;
					case DESERT :
					    hotBiomesList.addAll(biomesInTempRange);
					    break;
				    }
				}
			}
		}
	}

	
	/**
	 * Will add all of the basic UA biomes to their respective biome entry list. 
	 * Variants such as hills or some M forms will be done to the base UA biome 
	 * in other biome layers in the biome provider.
	 */
	private static void addUABiomeEntriesToLists() 
	{
		//adds our ultra amplified version of the vanilla biomes while 
	    	//checking to see if they are allowed by the user through the config

		//deserts
		if (UltraAmplified.UAConfig.desert.get())
			hotBiomesList.add(new BiomeEntry(UABiomes.DESERT, 40));
		if (UltraAmplified.UAConfig.savanna.get())
			hotBiomesList.add(new BiomeEntry(UABiomes.SAVANNA, 40));
		if (UltraAmplified.UAConfig.plains.get())
			hotBiomesList.add(new BiomeEntry(UABiomes.PLAINS, 20));
		if (UltraAmplified.UAConfig.netherland.get())
			hotBiomesList.add(new BiomeEntry(UABiomes.NETHERLAND, 30));

		//warm
		if (UltraAmplified.UAConfig.forest.get())
			warmBiomesList.add(new BiomeEntry(UABiomes.FOREST, 28));
		if (UltraAmplified.UAConfig.darkForest.get())
			warmBiomesList.add(new BiomeEntry(UABiomes.DARK_FOREST, 28));
		if (UltraAmplified.UAConfig.rockyField.get())
			warmBiomesList.add(new BiomeEntry(UABiomes.ROCKY_FIELD, 28));
		if (UltraAmplified.UAConfig.plains.get())
			warmBiomesList.add(new BiomeEntry(UABiomes.PLAINS, 28));
		if (UltraAmplified.UAConfig.birchForest.get())
			warmBiomesList.add(new BiomeEntry(UABiomes.BIRCH_FOREST, 28));
		if (UltraAmplified.UAConfig.swamplands.get())
			warmBiomesList.add(new BiomeEntry(UABiomes.SWAMP, 28));
		if (UltraAmplified.UAConfig.mushroom.get())
			warmBiomesList.add(new BiomeEntry(UABiomes.MUSHROOM_FIELDS, 8));

		//cool
		if (UltraAmplified.UAConfig.forest.get())
			coolBiomesList.add(new BiomeEntry(UABiomes.FOREST, 32));
		if (UltraAmplified.UAConfig.rockyField.get())
			coolBiomesList.add(new BiomeEntry(UABiomes.ROCKY_FIELD, 32));
		if (UltraAmplified.UAConfig.taiga.get())
			coolBiomesList.add(new BiomeEntry(UABiomes.TAIGA, 32));
		if (UltraAmplified.UAConfig.plains.get())
			coolBiomesList.add(new BiomeEntry(UABiomes.PLAINS, 12));
		if (UltraAmplified.UAConfig.stonePlains.get())
			coolBiomesList.add(new BiomeEntry(UABiomes.STONE_PLAINS, 17));
		if (UltraAmplified.UAConfig.endField.get())
			coolBiomesList.add(new BiomeEntry(UABiomes.END_FIELD, 32));
		if (UltraAmplified.UAConfig.mushroom.get())
			coolBiomesList.add(new BiomeEntry(UABiomes.MUSHROOM_FIELDS, 8));

		//icy
		if (UltraAmplified.UAConfig.snowyTundra.get())
			icyBiomesList.add(new BiomeEntry(UABiomes.SNOWY_TUNDRA, 45));
		else
		{
			//turns snowy tundra into ice spike only if config has snowy tundra off and ice spike on
			if (UltraAmplified.UAConfig.iceSpike.get())
				icyBiomesList.add(new BiomeEntry(UABiomes.ICE_SPIKES, 26));

			//turns snowy tundra into ice mountain only if config has snowy tundra off and ice mountain on
			if (UltraAmplified.UAConfig.icedTerrain.get())
				icyBiomesList.add(new BiomeEntry(UABiomes.ICED_TERRAIN, 26));
		}

		if (UltraAmplified.UAConfig.icedTerrain.get())
			icyBiomesList.add(new BiomeEntry(UABiomes.ICED_TERRAIN, 17));
		if (UltraAmplified.UAConfig.snowyTaiga.get())
			icyBiomesList.add(new BiomeEntry(UABiomes.SNOWY_TAIGA, 26));
		if (UltraAmplified.UAConfig.frozenDesert.get())
			icyBiomesList.add(new BiomeEntry(UABiomes.FROZEN_DESERT, 6));

		//special biomes lists used to replace vanilla ones such as mesa, jungles, etc

		if (UltraAmplified.UAConfig.jungle.get())
			jungleBiomesList.add(new BiomeEntry(UABiomes.JUNGLE, 70));
		if (UltraAmplified.UAConfig.bambooJungle.get())
			jungleBiomesList.add(new BiomeEntry(UABiomes.BAMBOO_JUNGLE, 30));
		if (UltraAmplified.UAConfig.giantTreeTaiga.get())
			giantTreeTaigaBiomesList.add(new BiomeEntry(UABiomes.GIANT_TREE_TAIGA, 10));
		if (UltraAmplified.UAConfig.badlands.get())
		{
			badlandsBiomesList.add(new BiomeEntry(UABiomes.SANDLESS_BADLANDS, 20));
			badlandsBiomesList.add(new BiomeEntry(UABiomes.WOODED_BADLANDS, 10));
		}
		//turns mesa completely into spiky/dissected plateau badlands only if config has mesa off and spiky badlands on
		else if (UltraAmplified.UAConfig.spikyBadlands.get())
		{
			badlandsBiomesList.add(new BiomeEntry(UABiomes.SPIKY_BADLANDS, 10));
			badlandsBiomesList.add(new BiomeEntry(UABiomes.DISSECTED_PLATEAU_BADLANDS, 10));
		}
		
		
		/* 
		 * Ocean list will not have modded oceans as to get the UA ocean shape,
		 * we would need to change modded biome surface builder and add a new 
		 * feature to make it look ok which is overkill. Best to leave oceans
		 * as UA oceans for now.
		 **/
		if (UltraAmplified.UAConfig.warmOcean.get())
		    	oceanBiomesList.add(new BiomeEntry(UABiomes.WARM_OCEAN, 10));
		if (UltraAmplified.UAConfig.lukewarmOcean.get())
		    	oceanBiomesList.add(new BiomeEntry(UABiomes.LUKEWARM_OCEAN, 10));
		if (UltraAmplified.UAConfig.ocean.get())
		    	oceanBiomesList.add(new BiomeEntry(UABiomes.OCEAN, 10));
		if (UltraAmplified.UAConfig.coldOcean.get())
		    	oceanBiomesList.add(new BiomeEntry(UABiomes.COLD_OCEAN, 10));
		if (UltraAmplified.UAConfig.frozenOcean.get())
		    	oceanBiomesList.add(new BiomeEntry(UABiomes.FROZEN_OCEAN, 10));
	}

	/**
	 * Goes through all land-based biome lists and if it holds 3 or less biomes,
	 * it will add other nearby temperature biome list's biome entries to the
	 * sparce biome until it has at least 5 biomes again.
	 * 
	 * If all land biome lists has no biomes, then UA ocean will be used for all land biomes.
	 * 
	 * If all lists has no biomes, then vanilla ocean will be used for all lists.
	 */
	private static void fillSparceBiomeLists() {
	    // this is used to help fill any biome list that is empty due to player turning off all of its biome.
	    // otherwise, we will crash if a biome list is empty
	    List<List<BiomeEntry>> listOfBiomeLists = new ArrayList<List<BiomeEntry>>();
	    listOfBiomeLists.add(badlandsBiomesList);
	    listOfBiomeLists.add(jungleBiomesList);
	    listOfBiomeLists.add(hotBiomesList);
	    listOfBiomeLists.add(warmBiomesList);
	    listOfBiomeLists.add(coolBiomesList);
	    listOfBiomeLists.add(giantTreeTaigaBiomesList);
	    listOfBiomeLists.add(icyBiomesList);

	    for (int listIndex = 0; listIndex < listOfBiomeLists.size(); listIndex++) {
		if (listOfBiomeLists.get(listIndex).isEmpty()) {

		    // switches direction of the list it checks outward from the current list
		    int direction = -1;
		    for (int offsetIndex = 0; offsetIndex < listOfBiomeLists.size(); offsetIndex++) {
			// clamp range to not do index out of bounds error
			int newIndex = Ints.constrainToRange(listIndex + (offsetIndex * direction), 0, listOfBiomeLists.size() - 1);

			// adds up to 4 biomes from the other biome list into the sparce biome list
			if (!listOfBiomeLists.get(newIndex).isEmpty()) 
			{
			    listOfBiomeLists.get(listIndex).addAll(
				    listOfBiomeLists.get(newIndex)
				    	.subList(0, Ints.constrainToRange(listOfBiomeLists.get(newIndex).size(), 1, 4)));
			 
			    break;
			}

			direction *= -1;
		    }

		    // if this is reached and our current biome list is still empty, then all lists are empty.
		    // check for oceans and if that too is empty, then just fill with vanilla ocean
		    if (listOfBiomeLists.get(listIndex).size() == 0) {

			if (oceanBiomesList.size() == 0) {
			    oceanBiomesList.add(new BiomeEntry(Biomes.OCEAN, 10));
			}

			for (List<BiomeEntry> list : listOfBiomeLists) {
			    list.addAll(oceanBiomesList);
			}
		    }
		}
	    }
	}
	
	/**
	 * Checks to see if player turned off all UA oceans by config.
	 * If so, set noOcean to true so the BiomeLayerPickerUA class
	 * knows to replace oceans with land instead.
	 */
	private static void isUAOceanPresent() 
	{
	    if (!UltraAmplified.UAConfig.ocean.get() && 
		    !UltraAmplified.UAConfig.coldOcean.get() && 
		    !UltraAmplified.UAConfig.frozenOcean.get() && 
		    !UltraAmplified.UAConfig.lukewarmOcean.get() && 
		    !UltraAmplified.UAConfig.warmOcean.get()) 
	    {
		noOcean = true;
	    }
	}
}
