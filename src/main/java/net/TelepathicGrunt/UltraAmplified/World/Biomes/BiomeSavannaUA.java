package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.UAConfig;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenFossils;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeSavannaUA extends BiomeExtendedUA
{
    private static final WorldGenSavannaTree SAVANNA_TREE = new WorldGenSavannaTree(false);
    private static final WorldGenerator fossil = new WorldGenFossils();

    public BiomeSavannaUA(Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.decorator = new BiomeDecoratorUA();
        
        this.decorator.treesPerChunk = 1;
        this.decorator.flowersPerChunk = 4;
        this.decorator.grassPerChunk = 20;
        
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityHorse.class, 1, 2, 6));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityDonkey.class, 1, 1, 1));

        if (this.getBaseHeight() > 1.1F)
        {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityLlama.class, 8, 4, 4));
        }
    }

    //grabs savanna trees and rarely a regular oak tree
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return (WorldGenAbstractTree)(rand.nextInt(5) > 0 ? SAVANNA_TREE : TREE_FEATURE);
    }

    
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
    	//generates double tall grass at any level
        DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
        
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
        {    
        	for (int currentCount = 0; currentCount < 7; ++currentCount)
	        {
	            int x = rand.nextInt(16) + 8;
	            int z = rand.nextInt(16) + 8;
	            int y = rand.nextInt(worldIn.getHeight(pos.add(x, 0, z)).getY() + 32);
	            DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(x, y, z));
	        }
        }
        
        //generates fossils 
        if(UAConfig.StructuresOptions.biomeBasedStructuresOptions.miniStructureGeneration && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FOSSIL))
        {    
        	if (rand.nextInt(70) == 0)
	        {
        		fossil.generate(worldIn, rand, pos);
	        }
        }
        
        //runs the default decorations for the other decoration this biome needs
        super.decorate(worldIn, rand, pos);
    }

    
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
    //I guess this is important. Don't touch
    public Class <? extends Biome > getBiomeClass()
    {
        return BiomeSavannaUA.class;
    }
}
