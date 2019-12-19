package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.UAConfig;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenCross;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenSwampMutatedUA;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenFossils;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeSwampUA extends BiomeExtendedUA
{
    protected static final IBlockState WATER_LILY = Blocks.WATERLILY.getDefaultState();
    
    private static final WorldGenSwampMutatedUA SWAMP_MUTATED_FEATURE = new WorldGenSwampMutatedUA();
    private static final WorldGenerator CROSS_GENERATOR = new WorldGenCross();
    private static final WorldGenerator fossil = new WorldGenFossils();
    
    public BiomeSwampUA(Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.decorator = new BiomeDecoratorUA();
        
        this.decorator.treesPerChunk = 2;
        this.decorator.flowersPerChunk = 1;
        this.decorator.deadBushPerChunk = 1;
        this.decorator.mushroomsPerChunk = 12;
        this.decorator.reedsPerChunk = 175;
        this.decorator.clayPerChunk = 10;
        this.decorator.waterlilyPerChunk = 5;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.grassPerChunk = 6;
        
        //increased slime spawnrate and how many spawn in a group if in mutated swampland biome.
        if(this.isMutation()) 
        {
        	this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySlime.class, 4, 1, 5));
        }
        else 
        {
        	this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySlime.class, 1, 1, 1));
        }
       
    }

    //returns swamp tree or mutated swamp tree depending on if this biome is mutated or not
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return this.isMutation() ? SWAMP_MUTATED_FEATURE : SWAMP_FEATURE;
    }
    
    //colors the grass in darker patches specified by Grass Color Noise 
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        double noise = GRASS_COLOR_NOISE.getValue((double)pos.getX() * 0.0225D, (double)pos.getZ() * 0.0225D);
        return noise < -0.1D ? 5011004 : 6975545;
    }

    //all leaves are a darker green regardless where they are in biome unlike grass
    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return 6975545;
    }

    //add blue orchids flower to this biome
    @Override
    public void addDefaultFlowers()
    {
        addFlower(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.BLUE_ORCHID), 10);
    }

    //all flowers in this biome are blue orchids and nothing else
    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos)
    {
        return BlockFlower.EnumFlowerType.BLUE_ORCHID;
    }

    
    //generates water blocks and water lily randomly in even block height by using grass color noise in a specific way
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        double noise = GRASS_COLOR_NOISE.getValue((double)x * 0.25D, (double)z * 0.25D);

        if (noise > 0.0D)
        {
            int chunkX = x & 15;
            int chunkZ = z & 15;

            for (int k = 250; k >= 76; k = k - 2)
            {
                if (chunkPrimerIn.getBlockState(chunkZ, k, chunkX).getMaterial() != Material.AIR && chunkPrimerIn.getBlockState(chunkZ, k+1, chunkX).getMaterial() == Material.AIR)
                {
                	if(this.isMutation()) 
                	{
                		if (chunkPrimerIn.getBlockState(chunkZ, k, chunkX).getBlock() != Blocks.WATER)
 	                    {
 	                        //adds water in every even height between Y = 76 and Y = 250 in Mutated Swamplands.
 	                        chunkPrimerIn.setBlockState(chunkZ, k, chunkX, WATER);
 	
 	                        if (noise < 0.12D)
 	                        {
 	                            chunkPrimerIn.setBlockState(chunkZ, k + 1, chunkX, WATER_LILY);
 	                        }
 	                    }
                	}
                	else 
                	{
	                    if ((k >= 80 && k <= 200) && chunkPrimerIn.getBlockState(chunkZ, k, chunkX).getBlock() != Blocks.WATER)
	                    {
	                    	//adds water in every even height between Y = 80 and Y = 200 in non-mutated Swamplands.
	                        chunkPrimerIn.setBlockState(chunkZ, k, chunkX, WATER);
	
	                        if (noise < 0.12D)
	                        {
	                            chunkPrimerIn.setBlockState(chunkZ, k + 1, chunkX, WATER_LILY);
	                        }
	                    }
                	}

                    break;
                }
            }
        }

        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
    	//generates fossils at a high spawnrate
        if(UAConfig.StructuresOptions.biomeBasedStructuresOptions.miniStructureGeneration && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FOSSIL))
        {    
        	if (rand.nextInt(50) == 0)
	        {
	            fossil.generate(worldIn, rand, pos);
	        }
        }
        
        //adds crosses to mutated swamplands but only if mini structures are allowed
        if (this.isMutation() && UAConfig.StructuresOptions.biomeBasedStructuresOptions.miniStructureGeneration && rand.nextInt(50) == 0)
        {
            int x = rand.nextInt(16) + 8;
            int z = rand.nextInt(16) + 8;
            BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(pos.add(x, 0, z));
            CROSS_GENERATOR.generate(worldIn, rand, blockpos);
        }
        
        //adds the default decorations needed
        super.decorate(worldIn, rand, pos);
    }
}
