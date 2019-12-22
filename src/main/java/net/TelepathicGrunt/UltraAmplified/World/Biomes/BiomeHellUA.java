package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

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
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;

public class BiomeHellUA extends BiomeExtendedUA
{
	
	protected static final IBlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
	protected static final IBlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
	protected static final IBlockState MAGMA = Blocks.MAGMA.getDefaultState();
    
	
    public BiomeHellUA(Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.topBlock = NETHERRACK;
        this.fillerBlock = NETHERRACK;
        
        //set nether monsters to spawn only
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhast.class, 50, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 2, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 4, 4));

    }
    
    //we use the decorator specifically for Nether instead of BiomeDecoratorUA. 
    //This is so we don't waste time generating trees or other stuff and only decorate using stuff specific to the Nether.
    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeHellDecoratorUA());
    }
    
    //dead-looking grass
    @Override
    public int getModdedBiomeGrassColor(int original) {
    	return 9259264;
    }
    
    //dead-looking trees
    @Override
    public int getModdedBiomeFoliageColor(int original) {
    	return 8075008;
    }
    
    //makes sky a dark red color for spookiness
    public int getSkyColorByTemp(float currentTemperature)
    {
        return 2621440;
    }
    
    
    //generates the top of terrain as mostly netherrack with patches of soul sand and thin lines of magma blocks
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

        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
}
