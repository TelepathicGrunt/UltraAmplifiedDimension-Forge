package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenSwampMutatedUA;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenDesertWells;
import net.minecraft.world.gen.feature.WorldGenFossils;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeSwampUA extends BiomeExtendedUA
{
    protected static final IBlockState WATER_LILY = Blocks.WATERLILY.getDefaultState();
    private final WorldGenerator crossGenerator = new CrossGenerator();
    protected static final WorldGenSwampMutatedUA SWAMP_MUTATED_FEATURE = new WorldGenSwampMutatedUA();
    
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
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySlime.class, 1, 1, 1));
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return this.isMutation() ? SWAMP_MUTATED_FEATURE : SWAMP_FEATURE;
    }
    
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        double d0 = GRASS_COLOR_NOISE.getValue((double)pos.getX() * 0.0225D, (double)pos.getZ() * 0.0225D);
        return d0 < -0.1D ? 5011004 : 6975545;
    }

    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return 6975545;
    }

    @Override
    public void addDefaultFlowers()
    {
        addFlower(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.BLUE_ORCHID), 10);
    }

    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos)
    {
        return BlockFlower.EnumFlowerType.BLUE_ORCHID;
    }

    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        double d0 = GRASS_COLOR_NOISE.getValue((double)x * 0.25D, (double)z * 0.25D);

        if (d0 > 0.0D)
        {
            int i = x & 15;
            int j = z & 15;

            for (int k = 250; k >= 76; k = k - 2)
            {
                if (chunkPrimerIn.getBlockState(j, k, i).getMaterial() != Material.AIR && chunkPrimerIn.getBlockState(j, k+1, i).getMaterial() == Material.AIR)
                {
                	if(this.isMutation()) 
                	{
                		if (chunkPrimerIn.getBlockState(j, k, i).getBlock() != Blocks.WATER)
 	                    {
 	                        chunkPrimerIn.setBlockState(j, k, i, WATER);
 	
 	                        if (d0 < 0.12D)
 	                        {
 	                            chunkPrimerIn.setBlockState(j, k + 1, i, WATER_LILY);
 	                        }
 	                    }
                	}
                	else 
                	{
	                    if ((k >= 80 && k <= 200) && chunkPrimerIn.getBlockState(j, k, i).getBlock() != Blocks.WATER)
	                    {
	                        chunkPrimerIn.setBlockState(j, k, i, WATER);
	
	                        if (d0 < 0.12D)
	                        {
	                            chunkPrimerIn.setBlockState(j, k + 1, i, WATER_LILY);
	                        }
	                    }
                	}

                    break;
                }
            }
        }

        this.generateBiomeTerrain2(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FOSSIL))
            if (rand.nextInt(50) == 0)
	        {
	            (new WorldGenFossils()).generate(worldIn, rand, pos);
	        }
        
	        if (this.isMutation() && rand.nextInt(50) == 0)
	        {
	            int i = rand.nextInt(16) + 8;
	            int j = rand.nextInt(16) + 8;
	            BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(pos.add(i, 0, j));
	            crossGenerator.generate(worldIn, rand, blockpos);
	        }
        
        super.decorate(worldIn, rand, pos);
    }
    
    
    private static class CrossGenerator extends WorldGenerator
    {
	@Override
       public boolean generate(World worldIn, Random rand, BlockPos pos)
       {
           
		  for(int i = -2; i < 6; i++) {
			  worldIn.setBlockState(pos.up(i), Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), 16 | 2);
		  }
	
		  for(int i = -2; i < 3; i++) {
			  worldIn.setBlockState(pos.up(4).east(i), Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.X), 16 | 2);
		  }
          
		  BlockPos positionTemp = pos.down(2).north();
		  if(!worldIn.isAirBlock(positionTemp) && worldIn.getBlockState(positionTemp) != Blocks.WATER.getDefaultState() && worldIn.getBlockState(positionTemp) != Blocks.LAVA.getDefaultState() ) {
			  worldIn.setBlockState(positionTemp, Blocks.SKULL.getDefaultState(), 2);
		  }
		  
		  
		  positionTemp = pos.down(3);
		  if(!worldIn.isAirBlock(positionTemp) && worldIn.getBlockState(positionTemp) != Blocks.WATER.getDefaultState() && worldIn.getBlockState(positionTemp) != Blocks.LAVA.getDefaultState() && rand.nextBoolean()) {
			  worldIn.setBlockState(positionTemp, Blocks.CHEST.getDefaultState(), 2);
			  
			  TileEntity tileentity = worldIn.getTileEntity(positionTemp);

	          if (tileentity instanceof TileEntityChest)
	          {
	              ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_JUNGLE_TEMPLE, rand.nextLong());
	          }
		  }
			  
          return true;
       }
    }
}
