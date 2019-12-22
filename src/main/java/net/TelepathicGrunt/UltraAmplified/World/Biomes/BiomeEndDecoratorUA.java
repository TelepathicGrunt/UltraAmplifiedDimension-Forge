package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorSettingsUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenChorusPlantUA;

public class BiomeEndDecoratorUA extends BiomeDecoratorUA
{
    protected WorldGenerator endIsland = new WorldGenEndIsland();
    protected WorldGenerator BlockChorusFlower = new WorldGenChorusPlantUA();
    
    //fixed value. Maybe I should turn this into a config option... hmmm...
    protected int chorusPerChunk = 30;
    
    //decorates what we need instead of all the other stuff in default decorate
    @Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    { 
        BiomeDecoratorUA.chunkProviderSettingsUA = new ChunkGeneratorSettingsUA();
        this.chunkPos = pos;
        this.genDecorations(biome, worldIn, random);
        this.decorating = false;
    }
	 
	 
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
    	//generates end islands. These may be why end biome are slower to load
    	for (int currentCount = 0; currentCount < BiomeDecoratorUA.chunkProviderSettingsUA.endIslandCount; ++currentCount)
        {
            int x = random.nextInt(16) + 8;
            int z = random.nextInt(16) + 8;
            int y = random.nextInt(243) + 10;
            this.endIsland.generate(worldIn, random, this.chunkPos.add(x, y, z));
        }

    	//generates chorus plant. These may be why end biome are slower to load
    	//we pass in 16 for x and z because the chorus class generates randomly around the passed in value. Not exactly on x and z.
    	for (int currentCount = 0; currentCount < this.chorusPerChunk; ++currentCount)
        {
            int y = random.nextInt(247)+8;
            this.BlockChorusFlower.generate(worldIn, random, this.chunkPos.add(16, y, 16));
        }
    }
}
