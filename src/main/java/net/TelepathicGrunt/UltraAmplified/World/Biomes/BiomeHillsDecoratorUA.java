package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeHillsDecoratorUA extends BiomeDecoratorUA{

	private final WorldGenerator silverfishSpawner = new WorldGenMinable(Blocks.MONSTER_EGG.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE), 9);
    private final WorldGenerator emeralds = new EmeraldGenerator();
	
    
	public void decorate(World worldIn, Random rand, Biome biome, BlockPos pos)
    {
    	super.decorate(worldIn, rand, biome, pos);
    	
    	//generates emerald ores throughout the chunk. The number to spawn per chunk varies greatly between 20 and 55 but can be modified by config setting
		int numberToSpawn = (int)((20 + rand.nextInt(35))*BiomeDecoratorUA.chunkProviderSettingsUA.emeraldCountPercentage);
	    for (int currentCount = 0; currentCount < numberToSpawn; currentCount++)
	    {
	    	int x = rand.nextInt(16) + 8;
            int y = rand.nextInt(251) + 4;
            int z = rand.nextInt(16) + 8;
            if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, emeralds, pos.add(x, y, z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.EMERALD))
            	emeralds.generate(worldIn, rand, pos.add(x, y, z));
	    }
    	

	    //generates a silverfish stone block through the biome
        for (int numPerChunk = 0; numPerChunk < BiomeDecoratorUA.chunkProviderSettingsUA.silverfishCount; ++numPerChunk)
        {
            int x = rand.nextInt(16);
            int y = rand.nextInt(251) + 4;
            int z = rand.nextInt(16);
            if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, silverfishSpawner, pos.add(x, y, z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.SILVERFISH))
            	this.silverfishSpawner.generate(worldIn, rand, pos.add(x, y, z));
        }
       
        
		
		this.chunkPos = pos;
        this.genDecorations(biome, worldIn, rand);
        this.decorating = false;
    }
	
	
	 private static class EmeraldGenerator extends WorldGenerator
     {
		@Override
        public boolean generate(World worldIn, Random rand, BlockPos pos)
        {
            net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
            if (state.getBlock().isReplaceableOreGen(state, worldIn, pos, net.minecraft.block.state.pattern.BlockMatcher.forBlock(Blocks.STONE)))
            {
                worldIn.setBlockState(pos, Blocks.EMERALD_ORE.getDefaultState(), 16 | 2);
            }
                
            return true;
        }
     }
	
}
