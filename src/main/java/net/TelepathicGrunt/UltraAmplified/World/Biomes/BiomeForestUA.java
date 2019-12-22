package net.telepathicgrunt.ultraamplified.World.Biomes;

import java.util.Random;

import net.telepathicgrunt.ultraamplified.Config.UAConfig;
import net.telepathicgrunt.ultraamplified.World.Biome.BiomeDecoratorUA;
import net.telepathicgrunt.ultraamplified.World.Biome.BiomeExtendedUA;
import net.telepathicgrunt.ultraamplified.World.gen.feature.WorldGenBirchTreeUA;
import net.telepathicgrunt.ultraamplified.World.gen.feature.WorldGenStonehenge;
import net.telepathicgrunt.ultraamplified.World.gen.feature.WorldGenSunShrine;
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
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeForestUA extends BiomeExtendedUA
{
    protected static final WorldGenBirchTree BIRCH_TREE = new WorldGenBirchTree(false, false);
    protected static final WorldGenBirchTreeUA TALL_BIRCH_TREE = new WorldGenBirchTreeUA(false);
    protected static final WorldGenCanopyTree ROOF_TREE = new WorldGenCanopyTree(false);
    
    private static final WorldGenerator shrine = new WorldGenSunShrine();
    private static final WorldGenerator stonehenge = new WorldGenStonehenge();
    
    private final BiomeForestUA.Type type;

    
    
    
	public static enum Type
    {
        NORMAL,
        FLOWER,
        BIRCH,
        ROOFED;
    }
	
	
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
        	//this is because roofed forests use a different tree generator to spawn dark oak trees
            this.decorator.treesPerChunk = -999;
        }
        
        if (this.type == BiomeForestUA.Type.FLOWER) //replaces blue flowers in flower forests and adds other flowers in form of red flower? I think? So confusing
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

    //generates the trees specific for each forests. Some forests get a mix of trees
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
    	if (this.type == BiomeForestUA.Type.ROOFED && rand.nextInt(3) > 0)
        {
    		//roofed forest
            return ROOF_TREE;
        }
    	else if (this.type == BiomeForestUA.Type.BIRCH) {
    		//birch forest
        	return TALL_BIRCH_TREE;
        }
        else if (rand.nextInt(5) != 0)
        {
        	//normal forest
        	//flower forest
        	//roofed forest
            return (WorldGenAbstractTree)(rand.nextInt(10) == 0 ? BIG_TREE_FEATURE : TREE_FEATURE);
        }
        else
        {
        	//normal forest
        	//flower forest
        	//roofed forest
            return BIRCH_TREE;
        }
	}

    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos)
    {
        if (this.type == BiomeForestUA.Type.FLOWER)
        {
        	//grass color noise value is used to define patches of land and generate one kind of flower for those patches of land
            double d0 = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue((double)pos.getX() / 48.0D, (double)pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
            
            BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(d0 * (double)BlockFlower.EnumFlowerType.values().length)];
            
            //replaces blue orchid with poppy flowers instead
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
        	//This was originally name this.addMushrooms. I want to strangle whoever named this method that incorrect name! lol
            this.roofedForestTreesAndLargeMushrooms(worldIn, rand, pos);
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
        

        //needs to be before stones so ores/dirt/etc does not replace stone's blocks
        super.decorate(worldIn, rand, pos);
        
        
        //if height is 0.6, this is a hills variant biome and thus, sun shrine and stones can have a chance to generate
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
		        stonehenge.generate(worldIn, rand, position);
        	}
	    }
    }

    protected void roofedForestTreesAndLargeMushrooms(World worldIn, Random rand, BlockPos pos)
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

    protected void addDoublePlants(World worldIn, Random random, BlockPos blockPos, int numberOfPlants)
    {
        for (int currentCount = 0; currentCount < numberOfPlants; ++currentCount)
        {
            int randomFlowerType = random.nextInt(3);

            if (randomFlowerType == 0)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
            }
            else if (randomFlowerType == 1)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
            }
            else if (randomFlowerType == 2)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
            }

            for (int i = 0; i < 5; ++i)
            {
                int x = random.nextInt(16) + 8;
                int z = random.nextInt(16) + 8;
                int j1 = random.nextInt(worldIn.getHeight(blockPos.add(x, 0, z)).getY() + 32);

                if (DOUBLE_PLANT_GENERATOR.generate(worldIn, random, new BlockPos(blockPos.getX() + x, j1, blockPos.getZ() + z)))
                {
                    break;
                }
            }
        }
    }

    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
    //is apparently important. Don't touch lol
    public Class <? extends Biome > getBiomeClass()
    {
        return BiomeForestUA.class;
    }

    //sets the color of the grass based on what forest biome it is in
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        int i = super.getGrassColorAtPos(pos);
        return this.type == BiomeForestUA.Type.ROOFED ? (i & 16711422) + 2634762 >> 1 : i;
    }
}
