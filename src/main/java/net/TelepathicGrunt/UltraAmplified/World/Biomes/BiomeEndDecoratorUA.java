package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenChorusPlantUA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeEndDecoratorUA extends BiomeDecorator
{
    private static final LoadingCache<Long, WorldGenSpikes.EndSpike[]> SPIKE_CACHE = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).<Long, WorldGenSpikes.EndSpike[]>build(new BiomeEndDecoratorUA.SpikeCacheLoader());
    private final WorldGenSpikes spikeGen = new WorldGenSpikes();
    protected int chorusPerChunk = 30;
	protected int islandPerChunk = 10;
	
    protected WorldGenerator BlockChorusFlower = new WorldGenChorusPlantUA();
    protected WorldGenerator endIsland = new WorldGenEndIsland();

	
	 public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
		
	 
        this.chunkPos = pos;
        this.genDecorations(biome, worldIn, random);
        this.decorating = false;
    }
	 
	 
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {

    	for (int j5 = 0; j5 < this.islandPerChunk; ++j5)
        {
            int l9 = random.nextInt(16) + 8;
            int k13 = random.nextInt(16) + 8;
            int l16 = worldIn.getHeight(this.chunkPos.add(l9, 0, k13)).getY() * 2;

            if (l16 > 7)
            {
                int j19 = random.nextInt(l16);
                this.endIsland.generate(worldIn, random, this.chunkPos.add(l9, j19, k13));
            }
        }
    	
    	for (int j5 = 0; j5 < this.chorusPerChunk; ++j5)
        {
            int l9 = random.nextInt(16) + 8;
            int k13 = random.nextInt(16) + 8;
            int l16 = worldIn.getHeight(this.chunkPos.add(l9, 0, k13)).getY() * 2;

            if (l16 > 0)
            {
                int j19 = random.nextInt(l16);
                this.BlockChorusFlower.generate(worldIn, random, this.chunkPos.add(l9, j19, k13));
            }
        }
    	
        WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = getSpikesForWorld(worldIn);

        for (WorldGenSpikes.EndSpike worldgenspikes$endspike : aworldgenspikes$endspike)
        {
            if (worldgenspikes$endspike.doesStartInChunk(this.chunkPos))
            {
                this.spikeGen.setSpike(worldgenspikes$endspike);
                this.spikeGen.generate(worldIn, random, new BlockPos(worldgenspikes$endspike.getCenterX(), 45, worldgenspikes$endspike.getCenterZ()));
            }
        }
    }

    public static WorldGenSpikes.EndSpike[] getSpikesForWorld(World p_185426_0_)
    {
        Random random = new Random(p_185426_0_.getSeed());
        long i = random.nextLong() & 65535L;
        return (WorldGenSpikes.EndSpike[])SPIKE_CACHE.getUnchecked(Long.valueOf(i));
    }

    static class SpikeCacheLoader extends CacheLoader<Long, WorldGenSpikes.EndSpike[]>
    {
        private SpikeCacheLoader()
        {
        }

        public WorldGenSpikes.EndSpike[] load(Long p_load_1_) throws Exception
        {
            List<Integer> list = Lists.newArrayList(ContiguousSet.create(Range.closedOpen(Integer.valueOf(0), Integer.valueOf(10)), DiscreteDomain.integers()));
            Collections.shuffle(list, new Random(p_load_1_.longValue()));
            WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = new WorldGenSpikes.EndSpike[10];

            for (int i = 0; i < 10; ++i)
            {
                int j = (int)(42.0D * Math.cos(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
                int k = (int)(42.0D * Math.sin(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
                int l = ((Integer)list.get(i)).intValue();
                int i1 = 2 + l / 3;
                int j1 = 76 + l * 3;
                boolean flag = l == 1 || l == 2;
                aworldgenspikes$endspike[i] = new WorldGenSpikes.EndSpike(j, k, i1, j1, flag);
            }

            return aworldgenspikes$endspike;
        }
    }
}
