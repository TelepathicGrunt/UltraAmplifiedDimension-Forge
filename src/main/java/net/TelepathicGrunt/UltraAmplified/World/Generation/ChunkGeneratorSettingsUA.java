package net.telepathicgrunt.ultraamplified.World.Generation;

import net.telepathicgrunt.ultraamplified.Config.UAConfig;

//holds values that is set in configs at start of world generation 
//May be removed in 1.13 when configs work on a per world basis.
public class ChunkGeneratorSettingsUA {
	
	public final float coordinateScale;
    public final float heightScale;
    public final float upperLimitScale;
    public final float lowerLimitScale;
    public final float depthNoiseScaleX;
    public final float depthNoiseScaleZ;
    public final float depthNoiseScaleExponent;
    public final float mainNoiseScaleX;
    public final float mainNoiseScaleY;
    public final float mainNoiseScaleZ;
    public final float baseSize;
    public final float stretchY;
    public final float biomeDepthWeight;
    public final float biomeDepthOffset;
    public final float biomeScaleWeight;
    public final float biomeScaleOffset;
    
    public final int seaLevel;
    public final boolean useLavaOceans;
    
    public final boolean useCaves;
    public final int caveCavityCount;
    public final boolean useRavines;
    public final int ravineCount;
    
    public final boolean lootChests;
    
    public final boolean useDungeons;
    public final int dungeonChance;
    public final boolean useStrongholds;
    public final float strongholdDistance;
    public final int strongholdSpread;
    public final int strongholdCount;
    public final boolean useVillages;
    public final int villageRarity;
    public final boolean useMineShafts;
    public final float mineshaftSpawnrate;
    public final boolean useTemples;
    public final int templeRarity;
    public final boolean useMonuments;
    public final int monumentsRarity;
    public final boolean useMansions;
    public final int mansionRarity;
    
    public final boolean useWaterLakes;
    public final int waterLakeChance;
    public final boolean useLavaLakes;
    public final int lavaLakeChance;
    public final boolean useSlimeLakes;
    
    public final int fixedBiome;
    public final int biomeSize;
    public final int riverSize;
    
    public final int dirtSize;
    public final int dirtCount;
    public final int dirtMinHeight;
    public final int dirtMaxHeight;
    public final int gravelSize;
    public final int gravelCount;
    public final int gravelMinHeight;
    public final int gravelMaxHeight;
    public final int graniteSize;
    public final int graniteCount;
    public final int graniteMinHeight;
    public final int graniteMaxHeight;
    public final int dioriteSize;
    public final int dioriteCount;
    public final int dioriteMinHeight;
    public final int dioriteMaxHeight;
    public final int andesiteSize;
    public final int andesiteCount;
    public final int andesiteMinHeight;
    public final int andesiteMaxHeight;
    
    public final int coalSize;
    public final int coalCount;
    public final int coalMinHeight;
    public final int coalMaxHeight;
    public final int ironSize;
    public final int ironCount;
    public final int ironMinHeight;
    public final int ironMaxHeight;
    public final int goldSize;
    public final int goldCount;
    public final int goldMinHeight;
    public final int goldMaxHeight;
    public final int mesaGoldCount;
    public final int redstoneSize;
    public final int redstoneCount;
    public final int redstoneMinHeight;
    public final int redstoneMaxHeight;
    public final int diamondSize;
    public final int diamondCount;
    public final int diamondMinHeight;
    public final int diamondMaxHeight;
    public final int lapisSize;
    public final int lapisCount;
    public final int lapisCenterHeight;
    public final int lapisSpread;
    
    public final float emeraldCountPercentage;
    public final int silverfishCount;
    
    public final int quartzCount;
    public final int glowstoneCount;
    public final int magmaCount;
    public final int lavaCount;
    
    public final int waterfallCount;
    public final int undergroundWaterfallCount;
    public final int lavafallCount;
    public final int undergroundLavafallCount;
    
    public final int endIslandCount;
    
    public final boolean bambooForest;
    public final boolean plains;
    public final boolean desert;
    public final boolean forest;
    public final boolean taiga;
    public final boolean extremeHills;
    public final boolean swamplands;
    public final boolean nether;
    public final boolean end;
    public final boolean iceFlats;
    public final boolean iceMountain;
    public final boolean mushroom;
    public final boolean stoneBeach;
    public final boolean jungle;
    public final boolean coldBeach;
    public final boolean birchForest;
    public final boolean roofedForest;
    public final boolean coldTaiga;
    public final boolean megaTaiga;
    public final boolean savanna;
    public final boolean mesa;
    public final boolean mesaBryce;
    public final boolean iceSpike;
    

    public ChunkGeneratorSettingsUA()
    {
    	//terrain
    	this.coordinateScale = 684.412F;
        this.heightScale = 684.412F;
        this.upperLimitScale = 512.0F;
        this.lowerLimitScale = 512.0F;
        this.depthNoiseScaleX = 200.0F;
        this.depthNoiseScaleZ = 200.0F;
        this.depthNoiseScaleExponent = 0.5F;
        this.mainNoiseScaleX = 80.0F;
        this.mainNoiseScaleY = 160.0F;
        this.mainNoiseScaleZ = 80.0F;
        this.baseSize = 8.5F;
        this.stretchY = 12.0F;
        this.biomeDepthWeight = 1.0F;
        this.biomeDepthOffset = 0.0F;
        this.biomeScaleWeight = 1.0F;
        this.biomeScaleOffset = 0.0F;
        this.seaLevel = UAConfig.biomeOptions.seaLevel;
        this.useLavaOceans = UAConfig.biomeOptions.lavaOcean;
        
       
        //Structures
    	this.useCaves = UAConfig.StructuresOptions.caveCavitySpawnrate == 0 ? false : true;
		this. caveCavityCount = UAConfig.StructuresOptions.caveCavitySpawnrate;
		this.useRavines = UAConfig.StructuresOptions.ravineSpawnrate == 0 ? false : true;
		this.ravineCount = UAConfig.StructuresOptions.ravineSpawnrate;
		this.useDungeons = UAConfig.StructuresOptions.dungeonSpawnrate == 0 ? false : true;
		this.dungeonChance = UAConfig.StructuresOptions.dungeonSpawnrate;
	    this.useStrongholds = UAConfig.StructuresOptions.biomeBasedStructuresOptions.strongholdGeneration;
        this.strongholdCount = UAConfig.StructuresOptions.biomeBasedStructuresOptions.strongholdNumberPerWorld;
        this.strongholdDistance = UAConfig.StructuresOptions.biomeBasedStructuresOptions.strongholdDistance;
        this.strongholdSpread = UAConfig.StructuresOptions.biomeBasedStructuresOptions.strongholdSpread;
        
        this.useVillages = UAConfig.StructuresOptions.biomeBasedStructuresOptions.villageGeneration;
        this.villageRarity = UAConfig.StructuresOptions.biomeBasedStructuresOptions.villageSpawnrate + 2;
        
        this.useMineShafts = UAConfig.StructuresOptions.biomeBasedStructuresOptions.mineshaftAbovegroundAllowed || UAConfig.StructuresOptions.biomeBasedStructuresOptions.mineshaftUndergroundAllowed;
        this.mineshaftSpawnrate = (float) UAConfig.StructuresOptions.biomeBasedStructuresOptions.mineshaftSpawnrate;
        
        this.useTemples =  UAConfig.StructuresOptions.biomeBasedStructuresOptions.scatteredGeneration;
        this.templeRarity =  UAConfig.StructuresOptions.biomeBasedStructuresOptions.scatteredSpawnrate + 2;
        
        this.useMonuments =  UAConfig.StructuresOptions.biomeBasedStructuresOptions.monumentGeneration;
        this.monumentsRarity =  UAConfig.StructuresOptions.biomeBasedStructuresOptions.monumentRarity + 4;
        
        this.useMansions =  UAConfig.StructuresOptions.biomeBasedStructuresOptions.mansionGeneration;
        this.mansionRarity =  UAConfig.StructuresOptions.biomeBasedStructuresOptions.mansionSpawnrate + 6;
        
        this.lootChests = UAConfig.StructuresOptions.biomeBasedStructuresOptions.chestGeneration;
        
        
        //Decorative Blobs
        this.dirtSize = 33;
        this.dirtCount = 10;
        this.dirtMinHeight = 0;
        this.dirtMaxHeight = 175;
        this.gravelSize = 33;
        this.gravelCount = 8;
        this.gravelMinHeight = 0;
        this.gravelMaxHeight = 256;
        this.graniteSize = 33;
        this.graniteCount = 17;
        this.graniteMinHeight = 0;
        this.graniteMaxHeight = 100;
        this.dioriteSize = 33;
        this.dioriteCount = 14;
        this.dioriteMinHeight = 0;
        this.dioriteMaxHeight = 150;
        this.andesiteSize = 33;
        this.andesiteCount = 20;
        this.andesiteMinHeight = 0;
        this.andesiteMaxHeight = 200;
        
        
        //Ores
        this.coalSize = 17;
        this.coalCount = UAConfig.oreAndFeatures.mainOresOptions.coalOreSpawnrate;
        this.coalMinHeight = 0;
        this.coalMaxHeight = 240;
        this.ironSize = 9;
        this.ironCount = UAConfig.oreAndFeatures.mainOresOptions.ironOreSpawnrate;
        this.ironMinHeight = 0;
        this.ironMaxHeight = 200;
        this.goldSize = 9;
        this.goldCount = UAConfig.oreAndFeatures.mainOresOptions.goldOreSpawnrate;
        this.goldMinHeight = 0;
        this.goldMaxHeight = 50;
        this.mesaGoldCount = (int)(70F*((float)UAConfig.oreAndFeatures.mainOresOptions.goldOreSpawnrate/2));
        this.redstoneSize = 8;
        this.redstoneCount = UAConfig.oreAndFeatures.mainOresOptions.redstoneOreSpawnrate;
        this.redstoneMinHeight = 0;
        this.redstoneMaxHeight = 25;
        this.diamondSize = 8;
        this.diamondCount = UAConfig.oreAndFeatures.mainOresOptions.diamondOreSpawnrate;
        this.diamondMinHeight = 0;
        this.diamondMaxHeight = 25;
        this.lapisSize = 7;
        this.lapisCount = UAConfig.oreAndFeatures.mainOresOptions.lapisOreSpawnrate;
        this.lapisCenterHeight = 20;
        this.lapisSpread = 20;
        this.quartzCount = UAConfig.oreAndFeatures.netherOresOptions.quartzOreSpawnrate;
		this.glowstoneCount = UAConfig.oreAndFeatures.netherOresOptions.glowstoneSpawnrate;
		this.emeraldCountPercentage = (float)(UAConfig.oreAndFeatures.extremeHillsOresOptions.emeraldOreSpawnrate/100);
		
		
		//Sorta Ores
		this.silverfishCount = UAConfig.oreAndFeatures.extremeHillsOresOptions.silverfishSpawnrate;
		this.magmaCount = UAConfig.oreAndFeatures.netherOresOptions.magmaSpawnrate;
		this.lavaCount = UAConfig.oreAndFeatures.netherOresOptions.lavaSpawnrate;
        
		
		//Decorations
        this.waterfallCount = (int)(35*(UAConfig.biomeOptions.waterfallSpawnrate/100));
	    this.undergroundWaterfallCount = (UAConfig.biomeOptions.waterfallSpawnrate == 0) ? 0 : (int)(2/(UAConfig.biomeOptions.waterfallSpawnrate/100))+1;
	    this.lavafallCount = (int)(14*(UAConfig.biomeOptions.lavafallSpawnrate/100));
	    this.undergroundLavafallCount = (int)(4*(UAConfig.biomeOptions.lavafallSpawnrate/100));
	    this.endIslandCount = (int)(8*(UAConfig.biomeOptions.endIslandSpawnrate/100));
	    this.useWaterLakes = UAConfig.StructuresOptions.waterLakeGen;
        this.waterLakeChance = 4;
        this.useLavaLakes = UAConfig.StructuresOptions.lavaLakeGen;
        this.lavaLakeChance = 8;
        this.useSlimeLakes = UAConfig.StructuresOptions.slimeLakeGen;
        
        
	    //Biomes
        this.fixedBiome = -1;
        this.biomeSize = UAConfig.biomeOptions.biomeSize;
        this.riverSize = 4;
        this.bambooForest = UAConfig.biomeOptions.biomeSelectionOptions.bambooForest;
        this.plains = UAConfig.biomeOptions.biomeSelectionOptions.plains;
        this.desert = UAConfig.biomeOptions.biomeSelectionOptions.desert;
        this.forest = UAConfig.biomeOptions.biomeSelectionOptions.forest;
        this.taiga = UAConfig.biomeOptions.biomeSelectionOptions.taiga;
        this.extremeHills = UAConfig.biomeOptions.biomeSelectionOptions.extremeHills;
        this.swamplands = UAConfig.biomeOptions.biomeSelectionOptions.swamplands;
        this.end = UAConfig.biomeOptions.biomeSelectionOptions.end;
        this.nether = UAConfig.biomeOptions.biomeSelectionOptions.nether;
        this.iceFlats = UAConfig.biomeOptions.biomeSelectionOptions.iceFlats;
        this.iceMountain = UAConfig.biomeOptions.biomeSelectionOptions.iceMountain;
        this.mushroom = UAConfig.biomeOptions.biomeSelectionOptions.mushroom;
        this.stoneBeach = UAConfig.biomeOptions.biomeSelectionOptions.stoneBeach;
        this.jungle = UAConfig.biomeOptions.biomeSelectionOptions.jungle;
        this.coldBeach = UAConfig.biomeOptions.biomeSelectionOptions.coldBeach;
        this.birchForest = UAConfig.biomeOptions.biomeSelectionOptions.birchForest;
        this.roofedForest = UAConfig.biomeOptions.biomeSelectionOptions.roofedForest;
        this.coldTaiga = UAConfig.biomeOptions.biomeSelectionOptions.coldTaiga;
        this.megaTaiga = UAConfig.biomeOptions.biomeSelectionOptions.megaTaiga;
        this.savanna = UAConfig.biomeOptions.biomeSelectionOptions.savanna;
        this.mesa = UAConfig.biomeOptions.biomeSelectionOptions.mesa;
        this.mesaBryce = UAConfig.biomeOptions.biomeSelectionOptions.mesaBryce;
        this.iceSpike = UAConfig.biomeOptions.biomeSelectionOptions.iceSpike;
        
    }
}
