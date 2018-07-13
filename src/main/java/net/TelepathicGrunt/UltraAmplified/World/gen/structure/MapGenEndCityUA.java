package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenEndCityUA extends MapGenStructure
{
	
	//this class is not used at all. End Cities are generated in MapGenScatteredFeatureUA to reduce slowdown with world generation 
	
    private final int citySpacing = 25;
    private final int minCitySeparation = 5;
    private ChunkGeneratorOverworldUA overworldProvider;

    public static final List<Biome> field_191072_a = Arrays.<Biome>asList(new Biome[] {BiomeInit.BiomeEnd});
    
    public MapGenEndCityUA(ChunkGeneratorOverworldUA chunky)
    {
    	this.overworldProvider = chunky;
    }

    public String getStructureName()
    {
        return "EndCity";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
	    int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            i = chunkX - 1;
        }

        if (chunkZ < 0)
        {
            j = chunkZ - 1;
        }

        int k = i / citySpacing;
        int l = j / citySpacing;
        Random random = this.world.setRandomSeed(k, l, 10387319);
        k = k * citySpacing;
        l = l * citySpacing;
        k = k + random.nextInt(citySpacing - 8);
        l = l + random.nextInt(citySpacing - 8);
        
        if (k == i && l == j)
        {

    		boolean flag = this.world.getBiomeProvider().areBiomesViable(chunkX * 16 + 8, chunkZ * 16 + 8, 0, field_191072_a);
    		
    		if (flag)
    	    {
    			return true;
    	    }
        }
        
        return false;
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new MapGenEndCityUA.Start(this.world, this.overworldProvider, this.rand, chunkX, chunkZ);
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, 20, 11, 10387313, true, 100, findUnexplored);
    }
    
    private static int getYPosForStructure(int p_191070_0_, int p_191070_1_, ChunkGeneratorOverworldUA p_191070_2_)
    {
        Random random = new Random((long)(p_191070_0_ + p_191070_1_ * 10387313));
        Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
        ChunkPrimer chunkprimer = new ChunkPrimer();
        p_191070_2_.setBlocksInChunk(p_191070_0_, p_191070_1_, chunkprimer);
        int i = 5;
        int j = 5;

        if (rotation == Rotation.CLOCKWISE_90)
        {
            i = -5;
        }
        else if (rotation == Rotation.CLOCKWISE_180)
        {
            i = -5;
            j = -5;
        }
        else if (rotation == Rotation.COUNTERCLOCKWISE_90)
        {
            j = -5;
        }

        int k = chunkprimer.findGroundBlockIdx(7, 7);
        int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
        int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
        int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
        
        //the 120 is highest height that end city can spawn
        int k1 = Math.min(Math.min(Math.min(k, l), Math.min(i1, j1)), 120);
        return k1;
    }

    public static class Start extends StructureStart
    {
        private boolean isSizeable;

        public Start()
        {
        }
        
        public Start(World worldIn, ChunkGeneratorOverworldUA chunkProvider, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            this.create(worldIn, chunkProvider, random, chunkX, chunkZ);
        }

        private void create(World worldIn, ChunkGeneratorOverworldUA chunkProvider, Random rnd, int chunkX, int chunkZ)
        {
            Random random = new Random((long)(chunkX + chunkZ * 10387313));
            Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
            int i = MapGenEndCityUA.getYPosForStructure(chunkX, chunkZ, chunkProvider);

            if (i < 60)
            {
                this.isSizeable = false;
            }
            else
            {
                BlockPos blockpos = new BlockPos(chunkX * 16 + 8, i, chunkZ * 16 + 8);
                StructureEndCityPiecesUA.startHouseTower(worldIn.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, this.components, rnd);
                this.updateBoundingBox();
                this.isSizeable = true;
            }
        }
        
        public boolean isSizeableStructure()
        {
            return this.isSizeable;
        }
    }
}
