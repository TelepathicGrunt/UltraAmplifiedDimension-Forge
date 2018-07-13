package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import jline.internal.Log;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenBirchMTree;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenBirchTreeUA;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeForestUA extends BiomeExtendedUA
{
    protected static final WorldGenBirchTree BIRCH_TREE = new WorldGenBirchTree(false, false);
    protected static final WorldGenBirchTreeUA TALL_BIRCH_TREE = new WorldGenBirchTreeUA(false, false);
    protected static final WorldGenCanopyTree ROOF_TREE = new WorldGenCanopyTree(false);
    protected static final WorldGenBirchMTree BIRCH_M_TREE_GENERATOR = new WorldGenBirchMTree(false);
    private final BiomeForestUA.Type type;

    public BiomeForestUA(BiomeForestUA.Type typeIn, Biome.BiomeProperties properties)
    {
        super(properties);
        this.decorator = new BiomeDecoratorUA();
        
        this.type = typeIn;
        this.decorator.treesPerChunk = 10;
        this.decorator.grassPerChunk = 2;

        if (this.type == BiomeForestUA.Type.FLOWER)
        {
            this.decorator.treesPerChunk = 6;
            this.decorator.flowersPerChunk = 200;
            this.decorator.grassPerChunk = 1;
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 4, 2, 3));
        }

        if (this.type == BiomeForestUA.Type.NORMAL)
        {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 5, 4, 4));
        }

        if (this.type == BiomeForestUA.Type.ROOFED)
        {
            this.decorator.treesPerChunk = -999;
        }
        
        if (this.type == BiomeForestUA.Type.FLOWER) //Needs to be done here so we have access to this.type
        {
            this.flowers.clear();
            for (BlockFlower.EnumFlowerType type : BlockFlower.EnumFlowerType.values())
            {
                if (type.getBlockType() == BlockFlower.EnumFlowerColor.YELLOW) continue;
                if (type == BlockFlower.EnumFlowerType.BLUE_ORCHID) type = BlockFlower.EnumFlowerType.POPPY;
                addFlower(net.minecraft.init.Blocks.RED_FLOWER.getDefaultState().withProperty(net.minecraft.init.Blocks.RED_FLOWER.getTypeProperty(), type), 10);
            }
        }
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
    	if (this.type == BiomeForestUA.Type.ROOFED && rand.nextInt(3) > 0)
        {
            return ROOF_TREE;
        }
    	else if (this.type == BiomeForestUA.Type.BIRCH) {
        	return TALL_BIRCH_TREE;
        }
        else if (rand.nextInt(5) != 0)
        {
            return (WorldGenAbstractTree)(rand.nextInt(10) == 0 ? BIG_TREE_FEATURE : TREE_FEATURE);
        }
        else
        {
            return BIRCH_TREE;
        }
	}

    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos)
    {
        if (this.type == BiomeForestUA.Type.FLOWER)
        {
            double d0 = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue((double)pos.getX() / 48.0D, (double)pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
            BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(d0 * (double)BlockFlower.EnumFlowerType.values().length)];
            return blockflower$enumflowertype == BlockFlower.EnumFlowerType.BLUE_ORCHID ? BlockFlower.EnumFlowerType.POPPY : blockflower$enumflowertype;
        }
        else
        {
            return super.pickRandomFlower(rand, pos);
        }
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        if (this.type == BiomeForestUA.Type.ROOFED)
        {
            this.roofedForestShitBecauseSomeoneNamedThisVariableInAShitWay(worldIn, rand, pos);
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        {
        int i = rand.nextInt(5) - 3;

        if (this.type == BiomeForestUA.Type.FLOWER)
        {
            i += 2;
        }
        

        this.addDoublePlants(worldIn, rand, pos, i);
        }
        super.decorate(worldIn, rand, pos);
    }

    protected void roofedForestShitBecauseSomeoneNamedThisVariableInAShitWay(World worldIn, Random rand, BlockPos pos)
    {
    	pos = pos.add(0, -pos.getY() + 75, 0);
    	
    	for(int yUp = 0; yUp < 175; yUp++) {
	    	
	        for (int i = 0; i < 4; ++i)
	        {
	            for (int j = 0; j < 4; ++j)
	            {

	        		if(rand.nextInt(3) == 0) {
		        			
		                int k = i * 4 + 1 + 8 + rand.nextInt(3);
		                int l = j * 4 + 1 + 8 + rand.nextInt(3);
		                BlockPos blockpos = pos.add(k, yUp, l);

		        			
		                if (rand.nextInt(19) == 0 && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), blockpos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM))
		                {
		                    WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
		                    worldgenbigmushroom.generate(worldIn, rand, blockpos);
		                }
		                else if (net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), blockpos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE))
		                {
		                    WorldGenAbstractTree worldgenabstracttree = this.getRandomTreeFeature(rand);
		                    worldgenabstracttree.setDecorationDefaults();
		
		                    if (worldgenabstracttree.generate(worldIn, rand, blockpos))
		                    {
		                        worldgenabstracttree.generateSaplings(worldIn, rand, blockpos);
		                    }
		                }
		            }
		        }
    		}
    	}
    }

    protected void addDoublePlants(World p_185378_1_, Random p_185378_2_, BlockPos p_185378_3_, int p_185378_4_)
    {
        for (int i = 0; i < p_185378_4_; ++i)
        {
            int j = p_185378_2_.nextInt(3);

            if (j == 0)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
            }
            else if (j == 1)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
            }
            else if (j == 2)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
            }

            for (int k = 0; k < 5; ++k)
            {
                int l = p_185378_2_.nextInt(16) + 8;
                int i1 = p_185378_2_.nextInt(16) + 8;
                int j1 = p_185378_2_.nextInt(p_185378_1_.getHeight(p_185378_3_.add(l, 0, i1)).getY() + 32);

                if (DOUBLE_PLANT_GENERATOR.generate(p_185378_1_, p_185378_2_, new BlockPos(p_185378_3_.getX() + l, j1, p_185378_3_.getZ() + i1)))
                {
                    break;
                }
            }
        }
    }

    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrain2(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
    public Class <? extends Biome > getBiomeClass()
    {
        return BiomeForestUA.class;
    }

    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        int i = super.getGrassColorAtPos(pos);
        return this.type == BiomeForestUA.Type.ROOFED ? (i & 16711422) + 2634762 >> 1 : i;
    }

    public static enum Type
    {
        NORMAL,
        FLOWER,
        BIRCH,
        ROOFED;
    }
}
