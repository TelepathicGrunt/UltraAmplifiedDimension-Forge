package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenIcePath;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenIceSpikeUA;

public class BiomeSnowUA extends BiomeExtendedUA
{
    private final boolean superIcy;
    private final WorldGenIceSpikeUA iceSpike = new WorldGenIceSpikeUA();
    private final WorldGenIcePath icePatch = new WorldGenIcePath(4);

    public BiomeSnowUA(boolean superIcyIn, Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.decorator = new BiomeDecoratorUA();
        
        //gives the default top block for Ice Spike and Ice Mountain biome.
        this.superIcy = superIcyIn;
        if (superIcyIn)
        {
        	//ice spike biome
            this.topBlock = Blocks.SNOW.getDefaultState();
        }
        else if(this.getBaseHeight() > 0.5F) 
        {
        	//ice mountain biome
        	this.topBlock = Blocks.SNOW.getDefaultState();
        	this.fillerBlock = Blocks.ICE.getDefaultState();
        }

        //sets cold biome mobs to spawn and replaces skeletons entry with strays and reduced spawnrate skeleton entry
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 10, 2, 3));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPolarBear.class, 1, 1, 2));
        
        Iterator<Biome.SpawnListEntry> iterator = this.spawnableMonsterList.iterator();
        while (iterator.hasNext())
        {
            Biome.SpawnListEntry biome$spawnlistentry = (Biome.SpawnListEntry)iterator.next();

            if (biome$spawnlistentry.entityClass == EntitySkeleton.class)
            {
                iterator.remove();
            }
        }
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 20, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityStray.class, 80, 4, 4));
    }

    
    //decreased the spawn rate of mobs for this biome
    public float getSpawningChance()
    {
        return 0.07F;
    }

    
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
    	//creates ice spike and ice patches only for Ice Spike Biome
        if (this.superIcy && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.ICE))
        {
            for (int currentCount = 0; currentCount < 3; ++currentCount)
            {
                int x = rand.nextInt(16) + 8;
                int z = rand.nextInt(16) + 8;
                this.iceSpike.generate(worldIn, rand, worldIn.getHeight(pos.add(x, 0, z)));
            }

            for (int currentCount = 0; currentCount < 2; ++currentCount)
            {
                int x = rand.nextInt(16) + 8;
                int z = rand.nextInt(16) + 8;
                this.icePatch.generate(worldIn, rand, worldIn.getHeight(pos.add(x, 0, z)));
            }
        }

        //runs default decorator to add other decorations
        super.decorate(worldIn, rand, pos);
    }
    

    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
    
    //make all trees that do generate in a snowy biome to be taiga trees 
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return new WorldGenTaiga2(false);
    }
}
