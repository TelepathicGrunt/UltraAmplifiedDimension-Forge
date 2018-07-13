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

import jline.internal.Log;
import net.TelepathicGrunt.UltraAmplified.UltraAmplified;
import net.TelepathicGrunt.UltraAmplified.Config.UAConfig;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorSettingsUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenChorusPlantUA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BiomeEndDecoratorUA extends BiomeDecoratorUA
{
    protected int chorusPerChunk = 30;
	
    protected WorldGenerator BlockChorusFlower = new WorldGenChorusPlantUA();
    protected WorldGenerator endIsland = new WorldGenEndIsland();
    
    @Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    { 
        this.chunkProviderSettingsUA = new ChunkGeneratorSettingsUA();
        this.chunkPos = pos;
        this.genDecorations(biome, worldIn, random);
        this.decorating = false;
    }
	 
	 
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
    	for (int j5 = 0; j5 < this.chunkProviderSettingsUA.endIslandCount; ++j5)
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
    }
}
