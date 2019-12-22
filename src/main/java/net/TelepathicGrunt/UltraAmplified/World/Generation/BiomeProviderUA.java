package net.telepathicgrunt.ultraamplified.World.Generation;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class BiomeProviderUA extends BiomeProvider{

    private ChunkGeneratorSettingsUA settings;

	public BiomeProviderUA(long seed, WorldType worldType, String options)
    {
		super();
		
        
        this.settings = new ChunkGeneratorSettingsUA();
        
        
        //generates the world and biome layouts
        GenLayer[] agenlayer = initializeAllBiomeGenerators(seed, worldType, this.settings);
        agenlayer = getModdedBiomeGenerators(worldType, seed, agenlayer);
	    genBiomes = agenlayer[0];
        biomeIndexLayer = agenlayer[1];
    }
	
	public BiomeProviderUA(World world)
    {
        this(world.getSeed(), world.getWorldInfo().getTerrainType(), world.getWorldInfo().getGeneratorOptions());
    }
    
	
	
	//this is how minecraft generates all the biomes layout
    public GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldType, ChunkGeneratorSettingsUA settings)
    {
    	//All this stuff below to genlayer4 seems like voodoo to me lol
        GenLayer genlayer = new GenLayerIsland(1L);
        genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
        GenLayerAddIsland genlayeraddisland = new GenLayerAddIsland(1L, genlayer);
        GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
        GenLayerAddIsland genlayeraddisland1 = new GenLayerAddIsland(2L, genlayerzoom);
        genlayeraddisland1 = new GenLayerAddIsland(50L, genlayeraddisland1);
        genlayeraddisland1 = new GenLayerAddIsland(70L, genlayeraddisland1);
        GenLayerRemoveTooMuchOcean genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(2L, genlayeraddisland1);
        GenLayerAddSnow genlayeraddsnow = new GenLayerAddSnow(2L, genlayerremovetoomuchocean);
        GenLayerAddIsland genlayeraddisland2 = new GenLayerAddIsland(3L, genlayeraddsnow);
        GenLayerEdge genlayeredge = new GenLayerEdge(2L, genlayeraddisland2, GenLayerEdge.Mode.COOL_WARM);
        genlayeredge = new GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
        genlayeredge = new GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
        GenLayerZoom genlayerzoom1 = new GenLayerZoom(2002L, genlayeredge);
        genlayerzoom1 = new GenLayerZoom(2003L, genlayerzoom1);
        GenLayerAddIsland genlayeraddisland3 = new GenLayerAddIsland(4L, genlayerzoom1);
        GenLayerAddMushroomIsland genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland3);
        GenLayerDeepOcean genlayerdeepocean = new GenLayerDeepOcean(4L, genlayeraddmushroomisland);
        GenLayer genlayer4 = GenLayerZoom.magnify(1000L, genlayerdeepocean, 0);
        //End of voodoo stuff!
        
        int biomeSize = settings.biomeSize;
        int riverSize = 4;

        //UltraAmplified.logger.warn("Biome size set to: " + i);

        GenLayer lvt_7_1_ = GenLayerZoom.magnify(1000L, genlayer4, 0);
        GenLayerRiverInit genlayerriverinit = new GenLayerRiverInit(100L, lvt_7_1_);
        
        //generates the biome layout. EXTREMELY IMPORTANT
        GenLayerBiomeUA lvt_8_1_ = new GenLayerBiomeUA(200L, genlayer4, worldType, settings);
        GenLayer genlayer6 = GenLayerZoom.magnify(1000L, lvt_8_1_, 2);
        GenLayerBiomeEdge genlayerbiomeedge = new GenLayerBiomeEdge(1000L, genlayer6);
        GenLayer lvt_9_1_ = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        
        //generates hills and M variants of biomes. Treat with special care as this is precious... 
        GenLayer genlayerhills = new GenLayerHillsAndAmplifiedUA(1000L, genlayerbiomeedge, lvt_9_1_, settings);
        GenLayer genlayer5 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        genlayer5 = GenLayerZoom.magnify(1000L, genlayer5, riverSize);
        GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayer5);

        //Expands the biome layout by what we set the variable 'biomeSize' as
        //4 is default biome size
        for (int currentIteration = 0; currentIteration < biomeSize; ++currentIteration)
        {
            genlayerhills = new GenLayerZoom((long)(1000 + currentIteration), genlayerhills);

            if (currentIteration == 0)
            {
                genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
            }

            if (currentIteration == 1 || biomeSize == 1)
            {
                genlayerhills = new GenLayerShore(1000L, genlayerhills);
            }
        }

        GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
        GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
        GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
        genlayerrivermix.initWorldGenSeed(seed);
        genlayer3.initWorldGenSeed(seed);
        //finished making biome layout
        
        return new GenLayer[] {genlayerrivermix, genlayer3, genlayerrivermix};
    }
	
}
