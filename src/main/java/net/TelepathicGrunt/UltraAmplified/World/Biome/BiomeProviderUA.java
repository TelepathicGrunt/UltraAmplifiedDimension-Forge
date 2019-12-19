package net.TelepathicGrunt.UltraAmplified.World.Biome;

import java.lang.reflect.Field;

import jline.internal.Log;
import net.TelepathicGrunt.UltraAmplified.World.WorldSettingsUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorSettingsUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.GenLayerBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.GenLayerHillsAndAmplifiedUA;
import net.TelepathicGrunt.UltraAmplified.World.WorldTypes.WorldTypeLargeBiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.WorldTypes.WorldTypeUA;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRareBiome;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class BiomeProviderUA extends BiomeProvider{


	public BiomeProviderUA(long seed, WorldType worldType, String chunkProviderSettings)
    {
		super();
		
        if (!(worldType instanceof WorldTypeUA || worldType instanceof WorldTypeLargeBiomeUA))
        {
            throw new RuntimeException("Error: WorldType is not UltraAmplified... How did you get this error?!");
        }    
    
        WorldSettingsUA settings = new WorldSettingsUA(chunkProviderSettings);
       
        GenLayer[] agenlayer = initializeAllBiomeGenerators(seed, worldType, settings);
        agenlayer = getModdedBiomeGenerators(worldType, seed, agenlayer);
        
        try {
	        Field genBiomeField = ReflectionHelper.findField(BiomeProvider.class,"genBiomes", "field_76944_d");
	        genBiomeField.set(this, agenlayer[0]);
        }
        catch(Exception e) {
        	Log.warn("BiomeProviderUA error with setting genBiome: "+e.getMessage());
        }
        
        try {
        	Field genBiomeField = ReflectionHelper.findField(BiomeProvider.class, "biomeIndexLayer", "field_76945_e");
	        genBiomeField.set(this, agenlayer[1]);
        }
        catch(Exception e) {
        	Log.warn("BiomeProviderUA error with setting biomeIndexLayer: "+e.getMessage());
        }
    }
	
	public BiomeProviderUA(World world)
    {
        this(world.getSeed(), world.getWorldInfo().getTerrainType(), world.getWorldInfo().getGeneratorOptions());
    }
    
	
	

    public GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldType, WorldSettingsUA p_180781_3_)
    {
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
        int i = 4;
        int j = 4;

        
        if (worldType instanceof WorldTypeLargeBiomeUA)
        {
            i = 6;
        }

        GenLayer lvt_7_1_ = GenLayerZoom.magnify(1000L, genlayer4, 0);
        GenLayerRiverInit genlayerriverinit = new GenLayerRiverInit(100L, lvt_7_1_);
        GenLayerBiomeUA lvt_8_1_ = new GenLayerBiomeUA(200L, genlayer4, worldType, p_180781_3_);
        GenLayer genlayer6 = GenLayerZoom.magnify(1000L, lvt_8_1_, 2);
        GenLayerBiomeEdge genlayerbiomeedge = new GenLayerBiomeEdge(1000L, genlayer6);
        GenLayer lvt_9_1_ = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        GenLayer genlayerhills = new GenLayerHillsAndAmplifiedUA(1000L, genlayerbiomeedge, lvt_9_1_);
        GenLayer genlayer5 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        genlayer5 = GenLayerZoom.magnify(1000L, genlayer5, j);
        //GenLayerRiver genlayerriver = new GenLayerRiver(1L, genlayer5);
        GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayer5);
        genlayerhills = new GenLayerRareBiome(1001L, genlayerhills);

        for (int k = 0; k < i; ++k)
        {
            genlayerhills = new GenLayerZoom((long)(1000 + k), genlayerhills);

            if (k == 0)
            {
                genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
            }

            if (k == 1 || i == 1)
            {
                genlayerhills = new GenLayerShore(1000L, genlayerhills);
            }
        }

        GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
        GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
        GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
        genlayerrivermix.initWorldGenSeed(seed);
        genlayer3.initWorldGenSeed(seed);
        return new GenLayer[] {genlayerrivermix, genlayer3, genlayerrivermix};
    }
	
}