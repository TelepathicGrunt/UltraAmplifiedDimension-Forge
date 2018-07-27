package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.UAConfig;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenBoulderUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenMegaPineTreeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenStonehedge;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenSunShrine;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenTaigaMutatedUA;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeTaigaUA extends BiomeExtendedUA
{
    private static final WorldGenTaiga1 PINE_GENERATOR = new WorldGenTaiga1();
    private static final WorldGenTaiga2 SPRUCE_GENERATOR = new WorldGenTaiga2(false);
    private static final WorldGenTaigaMutatedUA SPRUCE_M_GENERATOR = new WorldGenTaigaMutatedUA(false);
    
    private static final WorldGenMegaPineTreeUA MEGA_PINE_GENERATOR = new WorldGenMegaPineTreeUA(false, false);
    private static final WorldGenMegaPineTreeUA MEGA_SPRUCE_GENERATOR = new WorldGenMegaPineTreeUA(false, true);
    
    private static final WorldGenBoulderUA TAIGA_M_ROCK_GENERATOR = new WorldGenBoulderUA(0, false);
    private static final WorldGenBoulderUA MEGA_TAIGA_ROCK_GENERATOR = new WorldGenBoulderUA(3, false);
    private static final WorldGenBoulderUA MEGA_TAIGA_M_ROCK_GENERATOR = new WorldGenBoulderUA(4, true);
    
    private static final WorldGenerator shrine = new WorldGenSunShrine();
    private static final WorldGenerator stonehedge = new WorldGenStonehedge();
    
    private final BiomeTaigaUA.Type type;

    
    //This class has 6 different kinds of taiga biomes baked into it and can get confusing. Mojang is weird.
    public BiomeTaigaUA(BiomeTaigaUA.Type typeIn, Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.type = typeIn;
        
        this.decorator = new BiomeDecoratorUA();
        this.decorator.treesPerChunk = 10;

        if (typeIn != BiomeTaigaUA.Type.MEGA && typeIn != BiomeTaigaUA.Type.MEGA_SPRUCE)
        {
        	//normal taiga biomes
            this.decorator.grassPerChunk = 1;
            this.decorator.mushroomsPerChunk = 1;
        }
        else
        {
        	//mega taiga biomes
            this.decorator.grassPerChunk = 7;
            this.decorator.deadBushPerChunk = 1;
            this.decorator.mushroomsPerChunk = 3;
        }
        
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 4, 2, 3));
    }
    

    //grabs tree based on if it is mega taiga or not.
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        if ((this.type == BiomeTaigaUA.Type.MEGA || this.type == BiomeTaigaUA.Type.MEGA_SPRUCE) && rand.nextInt(3) == 0)
        {
        	//returns massive taiga trees if in Mega Taiga biome but only 1/3rd of the time.
            return this.type != BiomeTaigaUA.Type.MEGA_SPRUCE && rand.nextInt(13) != 0 ? MEGA_PINE_GENERATOR : MEGA_SPRUCE_GENERATOR;
        }
        else
        {
        	if(this.isMutation()) 
        	{
        		//if mutated taiga or mutated Cold Taiga biomes, always generate the mutated taiga trees
        		return SPRUCE_M_GENERATOR;
        	}
        	else 
        	{
        		//taiga biome and cold taiga biome always return these trees but mega taiga returns this 2/3rds of the time
        		 return (WorldGenAbstractTree)(rand.nextInt(3) == 0 ? PINE_GENERATOR : SPRUCE_GENERATOR);
        	}
        }
    }

    
    //generates fern 4/5ths of the time and tall grass the other times to decorate this biome.
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return rand.nextInt(5) > 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }

    
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        //needs to be before stonehedges and boulders so ores/dirt/etc does not replace stonehedge's or boulder's blocks
        super.decorate(worldIn, rand, pos);
        
    	//if height is 0.6, this is a hills variant biome and thus, sun shrine and stonehedges can have a chance to generate
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
	    
	    
        if ((this.type == BiomeTaigaUA.Type.MEGA || this.type == BiomeTaigaUA.Type.MEGA_SPRUCE) && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.ROCK))
        {   //runs only for mega taigas
        	
        	if(this.isMutation()) 
        	{   //runs only if mutated mega taiga biomes
        		
        		//generates massive pillars of boulders rarely and generates large single boulders the other times.
	            if(rand.nextInt(10) == 0)
	            {
	            	for(int currentCount = 0; currentCount < 12; currentCount++) {
		                BlockPos blockpos = worldIn.getHeight(pos.add(16, 0, 16));
		                MEGA_TAIGA_M_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
	            	}
	            }
	            else 
	            {
	            	if(rand.nextInt(5) == 0) 
	            	{
		            	int x = rand.nextInt(10) + 11;
		                int z = rand.nextInt(10) + 11;
		                BlockPos blockpos = worldIn.getHeight(pos.add(x, 0, z));
		                MEGA_TAIGA_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
	            	}
	            }
    		
        	}
        	else 
        	{   //runs only if non-mutated mega taiga biome
        		
	            int maxCount = rand.nextInt(4) == 0 ? rand.nextInt(3) : 0;
	            for (int currentCount = 0; currentCount < maxCount; ++currentCount)
	            {
	                int x = rand.nextInt(10) + 11;
	                int z = rand.nextInt(10) + 11;
	                BlockPos blockpos = worldIn.getHeight(pos.add(x, 0, z));
	                MEGA_TAIGA_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
	            }
        	}
        }
        else if(this.isMutation() && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.ROCK))
        {
        	 //adds the tiny rocks to taiga m and cold taiga m biomes at 2/3rds of the time
        	 if(rand.nextInt(3) < 2)
             {
                 int x = rand.nextInt(16) + 8;
                 int z = rand.nextInt(16) + 8;
                 BlockPos blockpos = worldIn.getHeight(pos.add(x, 0, z));
                 TAIGA_M_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
             }
        }

        
        //adds some ferns into this biome
        DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        {
        	for (int currentCount = 0; currentCount < 7; ++currentCount)
	        {
	            int x = rand.nextInt(16) + 8;
	            int z = rand.nextInt(16) + 8;
	            int y = rand.nextInt(worldIn.getHeight(pos.add(x, 0, z)).getY() + 32);
	            DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(x, y, z));
	        }
        }
        
        
        
    }

    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        if (this.type == BiomeTaigaUA.Type.MEGA || this.type == BiomeTaigaUA.Type.MEGA_SPRUCE)
        {
            this.topBlock = Blocks.GRASS.getDefaultState();
            this.fillerBlock = Blocks.DIRT.getDefaultState();

            if (noiseVal > 1.75D)
            {
                this.topBlock = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
            }
            else if (noiseVal > -0.95D)
            {
                this.topBlock = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
            }
        }

        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    public static enum Type
    {
        NORMAL,
        MEGA,
        MEGA_SPRUCE;
    }
}
