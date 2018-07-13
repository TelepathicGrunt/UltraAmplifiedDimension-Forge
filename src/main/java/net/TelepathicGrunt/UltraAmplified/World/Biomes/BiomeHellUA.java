package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeHellUA extends BiomeExtendedUA
{
	
	protected static final IBlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
	protected static final IBlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
	protected static final IBlockState MAGMA = Blocks.MAGMA.getDefaultState();
    
	
    public BiomeHellUA(Biome.BiomeProperties properties)
    {
        super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhast.class, 50, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 2, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 4, 4));

        this.topBlock = NETHERRACK;
        this.fillerBlock = NETHERRACK;
    }
    
    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeHellDecoratorUA());
    }
    
    @Override
    public int getModdedBiomeGrassColor(int original) {
    	return 9259264;
    }
    
    @Override
    public int getModdedBiomeFoliageColor(int original) {
    	return 8075008;
    }
    
    /**
     * takes temperature, returns color
     */
    public int getSkyColorByTemp(float currentTemperature)
    {
        return 2621440;
    }
    
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
    	this.topBlock = NETHERRACK;
        this.fillerBlock = NETHERRACK;

        
        if (noiseVal > 1.75D)
        {
        	this.topBlock = NETHERRACK;
            this.fillerBlock = NETHERRACK;
        }
        else if (noiseVal > -0.5D)
        {
        	this.topBlock = SOUL_SAND;
            this.fillerBlock = SOUL_SAND;
        }
        

        if((noiseVal > -3.7 && noiseVal < -3.6) || (noiseVal > -1.6 && noiseVal < -1.5) || (noiseVal > 3.7 && noiseVal < 3.8)) {
        	this.topBlock = MAGMA;
            this.fillerBlock = NETHERRACK;
        }

        this.generateBiomeTerrain2(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
}
