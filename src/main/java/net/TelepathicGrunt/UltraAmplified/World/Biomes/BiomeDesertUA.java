package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Iterator;
import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenBetterCactusUA;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenDesertWells;
import net.minecraft.world.gen.feature.WorldGenFossils;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDesertUA extends BiomeExtendedUA
{
	protected static final IBlockState SANDSTONE_SMOOTH = Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH);
	private final WorldGenerator tallCactus = new WorldGenBetterCactusUA(8);
	
    public BiomeDesertUA(Biome.BiomeProperties properties)
    {
        super(properties);
        this.decorator = new BiomeDecoratorUA();
        this.spawnableCreatureList.clear();
        
        if(this.isMutation()) {
	        this.topBlock = SANDSTONE_SMOOTH;
	        this.fillerBlock = SANDSTONE_SMOOTH; 
	        this.decorator.deadBushPerChunk = 30;
	        this.decorator.cactiPerChunk = 0;
        }else {
        	this.topBlock = Blocks.SAND.getDefaultState();
 	        this.fillerBlock = Blocks.SAND.getDefaultState();
	        this.decorator.deadBushPerChunk = 2;
	        this.decorator.cactiPerChunk = 100;
        }
        
        this.decorator.treesPerChunk = -999;
        this.decorator.reedsPerChunk = 50;
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 4, 2, 3));
        Iterator<Biome.SpawnListEntry> iterator = this.spawnableMonsterList.iterator();

        while (iterator.hasNext())
        {
            Biome.SpawnListEntry biome$spawnlistentry = (Biome.SpawnListEntry)iterator.next();

            if (biome$spawnlistentry.entityClass == EntityZombie.class || biome$spawnlistentry.entityClass == EntityZombieVillager.class)
            {
                iterator.remove();
            }
        }

        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombie.class, 19, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombieVillager.class, 1, 1, 1));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityHusk.class, 80, 4, 4));
    }

    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        super.decorate(worldIn, rand, pos);

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FOSSIL))
	        if (rand.nextInt(60) == 0)
	        {
	            new WorldGenFossils().generate(worldIn, rand, pos);
	        }
        
        
        if(this.isMutation()) {
        	
        	 if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CACTUS))
     	        for (int j5 = 0; j5 < 100; ++j5)
     	        {
 	                int j19 = rand.nextInt(180) + 70;
 	                tallCactus.generate(worldIn, rand, pos.add(16, j19, 16));
     	        }
               
        	
        	if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DESERT_WELL)) 
	        	if (rand.nextInt(10) == 0)
		        {
		            int i = rand.nextInt(16) + 8;
		            int j = rand.nextInt(16) + 8;
		            BlockPos blockpos = worldIn.getHeight(pos.add(i, 0, j)).up();
		            new WorldGenDesertWells().generate(worldIn, rand, blockpos);
		        }
        }
        else {
        	if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DESERT_WELL)) 
	        	if (rand.nextInt(100) == 0)
		        {
		            int i = rand.nextInt(16) + 8;
		            int j = rand.nextInt(16) + 8;
		            BlockPos blockpos = worldIn.getHeight(pos.add(i, 0, j)).up();
		            new WorldGenDesertWells().generate(worldIn, rand, blockpos);
		        }
        }
    }
    
    
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
    	if(this.isMutation()) {
	    	if (noiseVal > 1.80D)
	        {
	            this.topBlock = Blocks.SAND.getDefaultState();
		        this.fillerBlock = SANDSTONE_SMOOTH; 
	        }
	        else if (noiseVal > -0.5D)
	        {
	            this.topBlock = SANDSTONE_SMOOTH;
		        this.fillerBlock = SANDSTONE_SMOOTH; 
	        }
    	}
    	
        this.generateBiomeTerrain2(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
}
