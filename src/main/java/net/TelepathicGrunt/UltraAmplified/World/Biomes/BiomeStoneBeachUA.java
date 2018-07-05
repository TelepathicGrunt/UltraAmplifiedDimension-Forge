package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeStoneBeachUA extends BiomeExtendedUA
{
	private final WorldGenerator andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), 33);
	private final WorldGenerator dioriteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), 25);
	private final WorldGenerator graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), 25);
	private final WorldGenerator ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 8);
	private final WorldGenerator coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 15);
	
    public BiomeStoneBeachUA(Biome.BiomeProperties properties)
    {
        super(properties);
        this.decorator = new BiomeDecoratorUA();
        
        this.topBlock = Blocks.STONE.getDefaultState();
        this.fillerBlock = Blocks.STONE.getDefaultState();
        this.decorator.treesPerChunk = -999;
        this.decorator.deadBushPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.cactiPerChunk = 0;
    }
    
    /**
     * returns the chance a creature has to spawn.
     */
    public float getSpawningChance()
    {
        return 0.15F;
    }
    
    public void decorate(World worldIn, Random random, BlockPos pos)
    {
    	 for (int numPerChunk = 0; numPerChunk < 10; ++numPerChunk)
         {
             int x = random.nextInt(16);
             int y = random.nextInt(241)+10;
             int z = random.nextInt(16);
             
             if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, andesiteGen, pos.add(x,y,z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.ANDESITE))
             	this.andesiteGen.generate(worldIn, random, pos.add(x,y,z));
             
         }
    	 
    	 for (int numPerChunk = 0; numPerChunk < 3; ++numPerChunk)
         {
             int x = random.nextInt(16);
             int y = random.nextInt(41)+150;
             int z = random.nextInt(16);
             
             if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dioriteGen, pos.add(x,y,z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIORITE))
                 this.dioriteGen.generate(worldIn, random, pos.add(x,y,z));
               
         }
    	 
    	 for (int numPerChunk = 0; numPerChunk < 3; ++numPerChunk)
         {
             int x = random.nextInt(16);
             int y = random.nextInt(51)+120;
             int z = random.nextInt(16);
             
             if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, graniteGen, pos.add(x,y,z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRANITE))
            	 this.graniteGen.generate(worldIn, random, pos.add(x,y,z));
         }
    	 
    	 for (int numPerChunk = 0; numPerChunk < 20; ++numPerChunk)
         {
             int x = random.nextInt(16);
             int y = random.nextInt(246)+10;
             int z = random.nextInt(16);
             
             if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, ironGen, pos.add(x,y,z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON))
             	this.ironGen.generate(worldIn, random, pos.add(x,y,z));
         }
    	 
    	 for (int numPerChunk = 0; numPerChunk < 10; ++numPerChunk)
         {
             int x = random.nextInt(16);
             int y = random.nextInt(246)+10;
             int z = random.nextInt(16);
             
             if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, coalGen, pos.add(x,y,z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL))
            	 this.coalGen.generate(worldIn, random, pos.add(x,y,z));
         }
    	 
        super.decorate(worldIn, random, pos);
    }
}
