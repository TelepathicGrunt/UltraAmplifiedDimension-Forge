package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Iterator;
import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.UAConfig;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenBetterCactusUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenStonehedge;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenSunShrine;
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
	protected static final IBlockState SANDSTONE_DEFAULT = Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.DEFAULT);
	protected static final IBlockState SAND = Blocks.SAND.getDefaultState();
	
	private static final WorldGenerator tallCactus = new WorldGenBetterCactusUA(8);
    private static final WorldGenerator shrine = new WorldGenSunShrine();
    private static final WorldGenerator stonehedge = new WorldGenStonehedge();
    private static final WorldGenerator fossil = new WorldGenFossils();
    
    public BiomeDesertUA(Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.decorator = new BiomeDecoratorUA();
        
        //mutation form has slightly different properties
        if(this.isMutation()) {
	        this.topBlock = SANDSTONE_SMOOTH;
	        this.fillerBlock = SANDSTONE_SMOOTH; 
	        this.decorator.deadBushPerChunk = 30;
	        this.decorator.cactiPerChunk = 0;
        }else {
        	this.topBlock = SAND;
 	        this.fillerBlock = SAND;
	        this.decorator.deadBushPerChunk = 2;
	        
        	//This spawnrate may be a tad high...
	        this.decorator.cactiPerChunk = 100;
        }
        
        this.decorator.treesPerChunk = -999;
        this.decorator.reedsPerChunk = 50;
        
        
        //sets mobs that spawn in this biome
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
        //needs to be before stonehedges so ores/dirt/etc does not replace stonehedge's blocks
        super.decorate(worldIn, rand, pos);

        //generates fossils around every 60th chunk
        if(UAConfig.StructuresOptions.biomeBasedStructuresOptions.miniStructureGeneration && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FOSSIL))
	        if (rand.nextInt(60) == 0)
	        {
	        	fossil.generate(worldIn, rand, pos);
	        }
        
        
        if(this.isMutation()) {
        	//generates taller cactus instead of shorter ones. 
        	//The spawnrate may be a tad high...
        	 if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CACTUS))
     	        for (int j5 = 0; j5 < 100; ++j5)
     	        {
 	                int j19 = rand.nextInt(180) + 70;
 	                tallCactus.generate(worldIn, rand, pos.add(16, j19, 16));
     	        }
               
        	//generates desert wells with a higher spawnrate than the non-mutated desert
        	if(UAConfig.StructuresOptions.biomeBasedStructuresOptions.miniStructureGeneration && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DESERT_WELL)) 
	        	if (rand.nextInt(10) == 0)
		        {
		            int i = rand.nextInt(16) + 8;
		            int j = rand.nextInt(16) + 8;
		            BlockPos blockpos = worldIn.getHeight(pos.add(i, 0, j)).up();
		            new WorldGenDesertWells().generate(worldIn, rand, blockpos);
		        }
        }
        else {
        	//generates desert wells around every 100 chunks
        	if(UAConfig.StructuresOptions.biomeBasedStructuresOptions.miniStructureGeneration && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DESERT_WELL)) 
	        	if (rand.nextInt(100) == 0)
		        {
		            int i = rand.nextInt(16) + 8;
		            int j = rand.nextInt(16) + 8;
		            BlockPos blockpos = worldIn.getHeight(pos.add(i, 0, j)).up();
		            new WorldGenDesertWells().generate(worldIn, rand, blockpos);
		        }
        }
        
        //if height is 0.6, this is a hills variant biome and thus, sun shrine can have a chance to generate
	    if (UAConfig.StructuresOptions.biomeBasedStructuresOptions.miniStructureGeneration && this.getBaseHeight() == 0.6F) 
	    {
        	if(rand.nextInt(130) == 0) {
		        int x = rand.nextInt(16) + 8;
		        int z = rand.nextInt(16) + 8;
		        BlockPos position = worldIn.getTopSolidOrLiquidBlock(pos.add(x, 0, z));
		        
		        //attempt to generate sun shrine but the shrine code will check to make sure the position is ok for it to spawn
		        shrine.generate(worldIn, rand, position);
        	}
        	

        	if(rand.nextInt(15) == 0) {
		        BlockPos position = worldIn.getTopSolidOrLiquidBlock(pos.add(16, 0, 16));
		        
		        //attempt to generate sun shrine but the shrine code will check to make sure the position is ok for it to spawn
		        stonehedge.generate(worldIn, rand, position);
        	}
	    }
    }
    
    
    //If this is a mutated desert, generates smooth sandstone terrain with patches of sand
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
    	if(this.isMutation()) {
	    	if (noiseVal > 1.80D)
	        {
	            this.topBlock = SAND;
		        this.fillerBlock = SANDSTONE_SMOOTH; 
	        }
	        else if (noiseVal > -0.5D)
	        {
	            this.topBlock = SANDSTONE_SMOOTH;
		        this.fillerBlock = SANDSTONE_SMOOTH; 
	        }
    	}
    	
        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
}
