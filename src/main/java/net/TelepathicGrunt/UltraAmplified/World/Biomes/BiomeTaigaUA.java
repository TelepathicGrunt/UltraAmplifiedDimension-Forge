package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenBoulderUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenMegaPineTreeUA;
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
    private static final WorldGenBoulderUA MEGA_TAIGA_ROCK_GENERATOR = new WorldGenBoulderUA(3, false);
    private static final WorldGenBoulderUA MEGA_TAIGA_M_ROCK_GENERATOR = new WorldGenBoulderUA(4, true);
    private static final WorldGenBoulderUA TAIGA_M_ROCK_GENERATOR = new WorldGenBoulderUA(0, false);
    private final BiomeTaigaUA.Type type;

    public BiomeTaigaUA(BiomeTaigaUA.Type typeIn, Biome.BiomeProperties properties)
    {
        super(properties);
        this.decorator = new BiomeDecoratorUA();
        
        this.type = typeIn;
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 4, 2, 3));
        this.decorator.treesPerChunk = 10;

        if (typeIn != BiomeTaigaUA.Type.MEGA && typeIn != BiomeTaigaUA.Type.MEGA_SPRUCE)
        {
            this.decorator.grassPerChunk = 1;
            this.decorator.mushroomsPerChunk = 1;
        }
        else
        {
            this.decorator.grassPerChunk = 7;
            this.decorator.deadBushPerChunk = 1;
            this.decorator.mushroomsPerChunk = 3;
        }
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        if ((this.type == BiomeTaigaUA.Type.MEGA || this.type == BiomeTaigaUA.Type.MEGA_SPRUCE) && rand.nextInt(3) == 0)
        {
            return this.type != BiomeTaigaUA.Type.MEGA_SPRUCE && rand.nextInt(13) != 0 ? MEGA_PINE_GENERATOR : MEGA_SPRUCE_GENERATOR;
        }
        else
        {
        	if(this.isMutation()) 
        	{
        		return SPRUCE_M_GENERATOR;
        	}else 
        	{
        		 return (WorldGenAbstractTree)(rand.nextInt(3) == 0 ? PINE_GENERATOR : SPRUCE_GENERATOR);
        	}
        }
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return rand.nextInt(5) > 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }

    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        if ((this.type == BiomeTaigaUA.Type.MEGA || this.type == BiomeTaigaUA.Type.MEGA_SPRUCE) && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.ROCK))
        {
        	if(this.isMutation()) 
        	{
	            if(rand.nextInt(10) == 0)
	            {
	            	for(int i = 0; i < 12; i++) {
		                BlockPos blockpos = worldIn.getHeight(pos.add(16, 0, 16));
		                MEGA_TAIGA_M_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
	            	}
	            }
	            else 
	            {
	            	if(rand.nextInt(5) == 0) 
	            	{
		            	int k = rand.nextInt(10) + 11;
		                int l = rand.nextInt(10) + 11;
		                BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
		                MEGA_TAIGA_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
	            	}
	            }
    		
        	}
        	else 
        	{
	            int i = rand.nextInt(4) == 0 ? rand.nextInt(3) : 0;
	
	            for (int j = 0; j < i; ++j)
	            {
	                int k = rand.nextInt(10) + 11;
	                int l = rand.nextInt(10) + 11;
	                BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
	                MEGA_TAIGA_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
	            }
        	}
        }
        else if(this.isMutation() && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.ROCK)){
        	 if(rand.nextInt(3) < 2)
             {
                 int k = rand.nextInt(16) + 8;
                 int l = rand.nextInt(16) + 8;
                 BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
                 TAIGA_M_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
             }
        }

        DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        for (int i1 = 0; i1 < 7; ++i1)
        {
            int j1 = rand.nextInt(16) + 8;
            int k1 = rand.nextInt(16) + 8;
            int l1 = rand.nextInt(worldIn.getHeight(pos.add(j1, 0, k1)).getY() + 32);
            DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j1, l1, k1));
        }

        super.decorate(worldIn, rand, pos);
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

        this.generateBiomeTerrain2(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    public static enum Type
    {
        NORMAL,
        MEGA,
        MEGA_SPRUCE;
    }
}
