package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;

public class BiomeEndUA extends BiomeExtendedUA
{

	protected static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
	
    public BiomeEndUA(Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.topBlock = END_STONE;
        this.fillerBlock = END_STONE;
        
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
    }

    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
    //we use the decorator specifically for the end instead of BiomeDecoratorUA. 
    //This is so we don't waste time generating trees or other stuff and only decorate using stuff specific to the end.
    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeEndDecoratorUA());
    }
    
    //purple colored grass
    @Override
    public int getModdedBiomeGrassColor(int original) {
    	return 7625106;
    }
    
    //purple colored leaves
    @Override
    public int getModdedBiomeFoliageColor(int original) {
    	return 5329028;
    }
    
    //black sky when in end biome
    public int getSkyColorByTemp(float currentTemperature)
    {
        return 0;
    }
}
