package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;

public class BiomeMushroomIslandUA extends BiomeExtendedUA
{
    public BiomeMushroomIslandUA(Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.topBlock = Blocks.MYCELIUM.getDefaultState();
        
        this.decorator = new BiomeDecoratorUA();
        
        this.decorator.treesPerChunk = -100;
        this.decorator.flowersPerChunk = -100;
        this.decorator.grassPerChunk = -100;
        this.decorator.mushroomsPerChunk = 2;
        this.decorator.bigMushroomsPerChunk = 2;
        
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityMooshroom.class, 8, 4, 8));
    }
    
    
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
}
