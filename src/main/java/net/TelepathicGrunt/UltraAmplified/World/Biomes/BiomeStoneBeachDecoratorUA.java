package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeStoneBeachDecoratorUA extends BiomeDecoratorUA{

	private final WorldGenerator andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), 33);
	private final WorldGenerator dioriteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), 25);
	private final WorldGenerator graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), 25);
	private final WorldGenerator ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 8);
	private final WorldGenerator coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 15);
	
	//adds additional ores and stone patches to make this a very high resource biome.
	public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
		 //generates the default decorations with the stuff below it as additonal ore generations
		 super.decorate(worldIn, random, biome, pos);
        
		 //generates additional andersite at reduced spawnrate in a bigger range than default range
		 for (int currentCount = 0; currentCount < 10; ++currentCount)
         {
             int x = random.nextInt(16);
             int y = random.nextInt(241)+10;
             int z = random.nextInt(16);
             
             if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, andesiteGen, pos.add(x,y,z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.ANDESITE))
             	this.andesiteGen.generate(worldIn, random, pos.add(x,y,z));
         }
    	 
		 //generates additional diorite at reduced spawnrate between Y = 150 and Y = 190.
    	 for (int currentCount = 0; currentCount < 3; ++currentCount)
         {
             int x = random.nextInt(16);
             int y = random.nextInt(41)+150;
             int z = random.nextInt(16);
             
             if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dioriteGen, pos.add(x,y,z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIORITE))
                 this.dioriteGen.generate(worldIn, random, pos.add(x,y,z));
               
         }
    	 
    	 //generates additional granite at reduced spawnrate between Y = 120 and Y = 170. 
    	 for (int currentCount = 0; currentCount < 3; ++currentCount)
         {
             int x = random.nextInt(16);
             int y = random.nextInt(51)+120;
             int z = random.nextInt(16);
             
             if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, graniteGen, pos.add(x,y,z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRANITE))
            	 this.graniteGen.generate(worldIn, random, pos.add(x,y,z));
         }
    	 
    	 //generates additional iron at reduced spawnrate in a bigger range than default range
    	 int ironCount = (this.chunkProviderSettingsUA.ironCount/5)*2;
    	 for (int currentCount = 0; currentCount < ironCount; ++currentCount)
         {
             int x = random.nextInt(16);
             int y = random.nextInt(246)+10;
             int z = random.nextInt(16);
             
             if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, ironGen, pos.add(x,y,z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON))
             	this.ironGen.generate(worldIn, random, pos.add(x,y,z));
         }
    	 
    	 //generates additonal coal at reduced spawnrate in a bigger range than default range
    	 int coalCount = (this.chunkProviderSettingsUA.coalCount/3)*2;
    	 for (int currentCount = 0; currentCount < coalCount; ++currentCount)
         {
             int x = random.nextInt(16);
             int y = random.nextInt(246)+10;
             int z = random.nextInt(16);
             
             if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, coalGen, pos.add(x,y,z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL))
            	 this.coalGen.generate(worldIn, random, pos.add(x,y,z));
         }
    	 
		 
		this.chunkPos = pos;
        this.decorating = false;
    }
}
